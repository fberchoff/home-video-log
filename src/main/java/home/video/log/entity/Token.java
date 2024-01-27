package home.video.log.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	
	String token;
	
	@Enumerated(EnumType.STRING)
	@EqualsAndHashCode.Exclude	
	private TokenType tokenType;	
	
	@EqualsAndHashCode.Exclude	
	boolean tokenExpired;
	
	@EqualsAndHashCode.Exclude	
	boolean tokenRevoked;

}
