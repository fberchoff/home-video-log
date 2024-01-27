package home.video.log.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.Actor;

public interface ActorDao extends JpaRepository<Actor, Long> {
	
	Set<Actor> findAllByActorIdIn(Set<Long> actorIds);

}
