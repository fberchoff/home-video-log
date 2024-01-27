package home.video.log.controller.model;

import java.util.HashMap;
import java.util.Map;

import home.video.log.entity.Scene;
import home.video.log.entity.SubjectCategory;
import home.video.log.entity.SubjectSubcategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectSubcategoryData {
	
	private Long subjectSubcategoryId;
	
	private String subjectSubcategoryName;
	private SubjectSubcategorySubjectCategory subjectCategory;
	private Map<Long, String> scenes = new HashMap<>();
	
	public SubjectSubcategoryData (SubjectSubcategory subjectSubcategory) {
		subjectSubcategoryId = subjectSubcategory.getSubjectSubcategoryId();
		subjectSubcategoryName = subjectSubcategory.getSubjectSubcategoryName();
		subjectCategory = new SubjectSubcategorySubjectCategory(subjectSubcategory.getSubjectCategory());
		
		for (Scene scene : subjectSubcategory.getScenes()) {
			scenes.put(scene.getSceneId(), scene.getSceneTitle());
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class SubjectSubcategorySubjectCategory {
		
		private Long subjectCategoryId;
		private String subjectCategoryName;
		
		public SubjectSubcategorySubjectCategory (SubjectCategory subjectCategory) {
			subjectCategoryId = subjectCategory.getSubjectCategoryId();
			subjectCategoryName = subjectCategory.getSubjectCategoryName();
		}
	}

}
