package home.video.log.controller.model;

import java.util.HashMap;
import java.util.Map;
import home.video.log.entity.File;
import home.video.log.entity.Folder;
import home.video.log.entity.StorageLocation;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FolderData {
	
	private Long folderId;
	
	private String folderName;
	private FolderStorageLocation storageLocation;
	private Map<Long, String> files = new HashMap<>();
	
	public FolderData (Folder folder) {
		
		folderId = folder.getFolderId();
		folderName = folder.getFolderName();
		storageLocation = new FolderStorageLocation(folder.getStorageLocation());
		
		for (File file : folder.getFiles()) {
			files.put(file.getFileId(), file.getFileName());
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class FolderStorageLocation {
		
		private Long storageLocationId;
		private String storageLocationName;
		private String storageLocationPath;
		
		public FolderStorageLocation (StorageLocation storageLocation) {
			storageLocationId = storageLocation.getStorageLocationId();
			storageLocationName = storageLocation.getStorageLocationName();
			storageLocationPath = storageLocation.getStorageLocationPath();
		}
	}
}
