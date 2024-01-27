package home.video.log.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import home.video.log.entity.Project;

public interface ProjectDao extends JpaRepository<Project, Long> {
	
@Query("SELECT project " +
       "FROM Project project " +
	   "WHERE project.user.userName = :userName ")
List<Project> findAllByUserName(
		@Param("userName") String userName);

}
