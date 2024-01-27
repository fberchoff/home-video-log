package home.video.log.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.User;

public interface UserDao extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String userName);

}
