package home.video.log.authorization;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/home_video_log/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;

	@Tag(name = "01. User Authentication", description = "User authentication operations")
	@Operation(description = "Registers a new user (returns JWT token and refresh token)",
	   summary = "Register a new user")
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request) {
		
		return ResponseEntity.ok(service.register(request));
	}

	@Tag(name = "01. User Authentication", description = "User authentication operations")
	@Operation(description = "Authenticates a user (returns JWT token and refresh token)",
	   summary = "Authenticate a user")
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request) {
		
		return ResponseEntity.ok(service.authenticate(request));
		
	}
	
	@Tag(name = "01. User Authentication", description = "User authentication operations")
	@Operation(description = "Refreshes JWT token (returns new JWT token and refresh token)",
	   summary = "Refresh JWT token")	
	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		service.refreshToken(request, response);
		
	}

}
