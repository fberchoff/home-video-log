package home.video.log.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
				name = "Frank Berchoff",
				email = "frank@funmail.fun"
				),
				description = "OpenApi documentation for Home Video Log",
				title = "OpenApi specification - Home Video Log",
				version = "1.0"),
		servers = {
				@Server(
						description = "Local Environment",
						url = "http://localhost:8080")
		},
		security = {
				@SecurityRequirement(
						name = "Bearer Authorization")
		}
	)

@SecurityScheme(
		name="Bearer Authorization",
		description = "You can first acquire a JWT token by invoking the authenticate " +
		              "endpoint within the user authentication controller. From the response, copy " +
				      "the access token and paste it here.",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
		)
public class OpenApiConfig {

}
