package home.video.log.controller.model;

import java.util.HashMap;
import java.util.Map;
import home.video.log.entity.Project;
import home.video.log.entity.SubjectCategory;
import home.video.log.entity.SubjectSubcategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectCategoryData {

	private Long subjectCategoryId;
	
	private String subjectCategoryName;
	private SubjectCategoryProject project;
	private Map<Long, String> subjectSubCategories = new HashMap<>();
	
	public SubjectCategoryData (SubjectCategory subjectCategory) {
		
		subjectCategoryId = subjectCategory.getSubjectCategoryId();
		subjectCategoryName  = subjectCategory.getSubjectCategoryName();
		project = new SubjectCategoryProject(subjectCategory.getProject());
		
		for (SubjectSubcategory subjectSubcategory : subjectCategory.getSubjectSubCategories()) {
			subjectSubCategories.put(subjectSubcategory.getSubjectSubcategoryId(),
					                 subjectSubcategory.getSubjectSubcategoryName());
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class SubjectCategoryProject {
		
		private Long projectId;
		private String projectName;
		
		public SubjectCategoryProject (Project project) {
			projectId = project.getProjectId();
			projectName = project.getProjectName();
		}		
	}
}
