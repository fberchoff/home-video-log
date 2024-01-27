package home.video.log.controller.model;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import home.video.log.entity.Actor;
import home.video.log.entity.File;
import home.video.log.entity.Location;
import home.video.log.entity.Scene;
import home.video.log.entity.SubjectSubcategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SceneData {
	
	private Long sceneId;
	
	private String sceneTitle;
	private String sceneSummary;
	private LocalTime sceneStartTime;
	private LocalTime sceneEndTime;
	private int sceneLengthSec;
	private int sceneRating;	
	private SceneFile file;	
	private SceneLocation location;	
	private Map<Long, String> actors = new HashMap<>();
	private Map<Long, String> subjectSubcategories = new HashMap<>();
	
	
	public SceneData(Scene scene) {
		
		sceneId = scene.getSceneId();
		sceneTitle = scene.getSceneTitle();
		sceneSummary = scene.getSceneSummary();
		sceneStartTime = scene.getSceneStartTime();
		sceneEndTime = scene.getSceneEndTime();
		sceneLengthSec = scene.getSceneLengthSec();
		sceneRating = scene.getSceneRating();		
		file = new SceneFile(scene.getFile());		
		location = new SceneLocation(scene.getLocation());
		
		for (Actor actor : scene.getActors()) {
			actors.put(actor.getActorId(), actor.getActorFirstName() + " " + actor.getActorLastName());
		}
			
		for (SubjectSubcategory subjectSubcategory : scene.getSubjectSubcategories()) {
			subjectSubcategories.put(subjectSubcategory.getSubjectSubcategoryId()
					               , subjectSubcategory.getSubjectSubcategoryName());
		}		
	}
	
	
	@Data
	@NoArgsConstructor
	public static class SceneFile {
		
		private Long fileId;
		private String fileName;
		
		public SceneFile(File file) {
			fileId = file.getFileId();
			fileName = file.getFileName();
		}
		
	}
	
	
	@Data
	@NoArgsConstructor
	public static class SceneLocation {
		
		private Long locationId;
		private String locationName;
		
		public SceneLocation(Location location) {
			locationId = location.getLocationId();
			locationName = location.getLocationName();
		}
	}	

}
