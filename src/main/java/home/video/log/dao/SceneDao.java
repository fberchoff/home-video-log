package home.video.log.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.Scene;

public interface SceneDao extends JpaRepository<Scene, Long> {
	
	Set<Scene> findAllBySceneIdIn(Set<Long> sceneIds);

}
