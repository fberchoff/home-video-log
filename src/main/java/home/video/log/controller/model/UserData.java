package home.video.log.controller.model;

import java.util.HashSet;
import java.util.Set;

import home.video.log.entity.Project;
import home.video.log.entity.Role;
import home.video.log.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserData {
	private Long userId;
	
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userFirstName;
	private String userLastName;
	private Role userRole;
	private Set<UserProject> projects = new HashSet<>();
	
	public UserData (User user) {
		userId = user.getUserId();
		userName = user.getUsername();
		userEmail = user.getUserEmail();
		userPhone = user.getUserPhone();
		userFirstName = user.getUserFirstName();
		userLastName = user.getUserLastName();
		userRole = user.getUserRole();
		
		for (Project project : user.getProjects()) {
			projects.add(new UserProject(project));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class UserProject {
		
		private Long projectId;
		private String projectName;
		
		public UserProject (Project project) {
			projectId = project.getProjectId();
			projectName = project.getProjectName();
		}
	}

}
