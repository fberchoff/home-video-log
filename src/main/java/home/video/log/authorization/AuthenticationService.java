package home.video.log.authorization;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import home.video.log.dao.TokenDao;
import home.video.log.dao.UserDao;
import home.video.log.entity.Role;
import home.video.log.entity.Token;
import home.video.log.entity.TokenType;
import home.video.log.entity.User;
import home.video.log.security.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserDao userDao;
	private final TokenDao tokenDao;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.userName(request.getUserName())
				.userEmail(request.getEmail())
				.userPhone(request.getPhone())
				.userFirstName(request.getFirstName())
				.userLastName(request.getLastName())
				.password(passwordEncoder.encode(request.getPassword()))
				.userRole(Role.USER)
				.build();
		
		var savedUser = userDao.save(user);
		
		var jwtToken = jwtService.generateToken(user);		
		var refreshToken = jwtService.generateRefreshToken(user);
		
		saveUserToken(savedUser, jwtToken);
		
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getUserName(),
							request.getPassword()
							)
				);
		
		String userName = request.getUserName();
		
		var user = userDao.findByUserName(request.getUserName()).orElseThrow(
				() -> new NoSuchElementException("User with Name=" + userName + " does not exist."));
		
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
		}
	
	private void revokeAllUserTokens(User user) {
		
		var validUserTokens = tokenDao.findAllValidTokensByUser(user.getUserId());
		
		if (validUserTokens.isEmpty()) {
			return;
		}
		
		validUserTokens.forEach(t -> {
			t.setTokenExpired(true);
			t.setTokenRevoked(true);
		});
		
		tokenDao.saveAll(validUserTokens);		
	}

	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.tokenRevoked(false)
				.tokenExpired(false)
				.build();
		
		tokenDao.save(token);
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String authUserName;
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		refreshToken = authHeader.substring(7);
		authUserName = jwtService.extractUserName(refreshToken);
		
		if (authUserName != null) {
			var user = userDao.findByUserName(authUserName)
					.orElseThrow();			
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}		
		
	}		
		
	}
	


