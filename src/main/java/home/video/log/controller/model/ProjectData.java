package home.video.log.controller.model;

import home.video.log.entity.Project;
import home.video.log.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectData {
	
	private Long projectId;	

	private String projectName;
	private ProjectUser user;
	
	public ProjectData(Project project) {
		
		projectId = project.getProjectId();
		projectName = project.getProjectName();
		user = new ProjectUser(project.getUser());
		
	}

@Data
@NoArgsConstructor	
	public static class ProjectUser {
		
		private Long userId;
		private String userName;
		private String userFirstName;
		private String userLastName;
		private String userEmail;
		private String userPhone;
		
		public ProjectUser(User user) {
			userId = user.getUserId();
			userName = user.getUsername();
			userFirstName = user.getUserFirstName();
			userLastName = user.getUserLastName();
			userEmail = user.getUserEmail();
			userPhone = user.getUserPhone();
		}
	}

}
