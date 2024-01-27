package home.video.log.controller.model;

import java.util.HashMap;
import java.util.Map;
import home.video.log.entity.Folder;
import home.video.log.entity.Project;
import home.video.log.entity.StorageLocation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StorageLocationData {
	
	private Long storageLocationId;
	
	private String storageLocationName;	
	private String storageLocationPath;
	private StorageLocationProject project;
	private Map<Long, String> folders = new HashMap<>();
	
	public StorageLocationData(StorageLocation storageLocation) {
		storageLocationId = storageLocation.getStorageLocationId();
		storageLocationName = storageLocation.getStorageLocationName();
		storageLocationPath = storageLocation.getStorageLocationPath();
		project = new StorageLocationProject(storageLocation.getProject());
		
		for (Folder folder : storageLocation.getFolders()) {
			folders.put(folder.getFolderId(), folder.getFolderName());
		}		
	}

	@Data
	@NoArgsConstructor
	public static class StorageLocationProject {
		
		private Long projectId;
		private String projectName;
		
		public StorageLocationProject(Project project) {
			projectId = project.getProjectId();
			projectName = project.getProjectName();
		}
		
	}
}
