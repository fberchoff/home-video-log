package home.video.log.security.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(req ->
					req.requestMatchers(
							"/home_video_log/auth/**",
							"/v2/api-docs",
							"/v3/api-docs",
							"/v3/api-docs/**",
							"/swagger-resources",
							"/swagger-resources/**",
							"/configuration/ui",
							"/configuration/security",
							"/swagger-ui/**",
							"/webjars/**",
							"swagger-ui.html")
			.permitAll()
			.anyRequest()
			.authenticated()					
					)
			.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.logout()
			.logoutUrl("/home_video_log/auth/logout")
			.addLogoutHandler(logoutHandler)
			.logoutSuccessHandler(
				(request, response, authentication) -> SecurityContextHolder.clearContext());				
		
		return http.build();
	}

}
