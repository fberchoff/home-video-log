package home.video.log.controller.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import home.video.log.entity.Actor;
import home.video.log.entity.Project;
import home.video.log.entity.Scene;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActorData {
	
	private Long actorId;
	private String actorFirstName;
	private String actorLastName;
	private LocalDate actorBirthDate;
	private ActorProject project;
	private Map<Long, String> scenes = new HashMap<>();
	
	
	public ActorData(Actor actor) {
		
		actorId = actor.getActorId();
		actorFirstName = actor.getActorFirstName();
		actorLastName = actor.getActorLastName();
		actorBirthDate = actor.getActorBirthDate();
		project = new ActorProject(actor.getProject());
		
		for (Scene scene : actor.getScenes()) {
			scenes.put(scene.getSceneId(), scene.getSceneTitle());
		}
	}
	
	
	@Data
	@NoArgsConstructor
	public static class ActorProject {

		private Long projectId;
		private String projectName;
		
		public ActorProject(Project project) {
			projectId = project.getProjectId();
			projectName = project.getProjectName();
		}
	}

}
