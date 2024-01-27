package home.video.log.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import home.video.log.dao.TokenDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
	
	private final TokenDao tokenDao;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		final String authHeader = request.getHeader("Authorization");
		
		final String jwt;
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		jwt = authHeader.substring(7);
		
		var storedToken = tokenDao.findByToken(jwt)
				.orElse(null);
		
		if (storedToken != null) {
			storedToken.setTokenExpired(true);
			storedToken.setTokenRevoked(true);
			tokenDao.save(storedToken);
		}
	}

}
