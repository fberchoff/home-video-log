package home.video.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.Folder;

public interface FolderDao extends JpaRepository<Folder, Long> {

}
