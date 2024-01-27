package home.video.log.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import home.video.log.entity.Token;

public interface TokenDao extends JpaRepository<Token, Long> {
	
	@Query("SELECT token " +
		   "FROM Token token " +
		      "INNER JOIN User user " +
		        "ON token.user.userId = user.userId " +
		   "WHERE user.userId = :userId " +
		     "AND (token.tokenExpired = false or token.tokenRevoked = false)")
	List<Token> findAllValidTokensByUser(@Param("userId") Long userId);	
	
	Optional<Token> findByToken(String token);

}
