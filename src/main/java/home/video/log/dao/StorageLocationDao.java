package home.video.log.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import home.video.log.entity.StorageLocation;

public interface StorageLocationDao extends JpaRepository<StorageLocation, Long> {
	
@Query("SELECT storageLocation " +
       "FROM StorageLocation storageLocation " +
	   "WHERE storageLocation.project.user.userName = :userName " +
       "  AND storageLocation.project.projectId = :projectId")
List<StorageLocation> findAllByUserNameAndProjectId(
		@Param("userName") String userName,
		@Param("projectId") Long projectId);

}
