package home.video.log.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import home.video.log.entity.File;

public interface FileDao extends JpaRepository<File, Long> {
		
	@Query("SELECT file " +
	       "FROM File file " +
		   "WHERE file.folder.storageLocation.project.user.userName = :userName " +
	       "  AND file.folder.storageLocation.project.projectId = :projectId")
	List<File> findAllByUserNameAndProjectId(
			@Param("userName") String userName,
			@Param("projectId") Long projectId);
	
	

}
