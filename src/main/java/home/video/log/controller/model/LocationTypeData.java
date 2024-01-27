package home.video.log.controller.model;

import java.util.HashMap;
import java.util.Map;
import home.video.log.entity.Location;
import home.video.log.entity.LocationType;
import home.video.log.entity.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationTypeData {
	
	private Long locationTypeId;
	
	private String locationTypeName;
	private LocationTypeProject project;
	Map<Long, String> locations = new HashMap<>();
	
	public LocationTypeData(LocationType locationType) {
		locationTypeId = locationType.getLocationTypeId();
		locationTypeName = locationType.getLocationTypeName();
		project = new LocationTypeProject(locationType.getProject());
		
		for (Location location : locationType.getLocations()) {
			locations.put(location.getLocationId(), location.getLocationName());
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class LocationTypeProject {

		private Long projectId;
		private String projectName;
		
		public LocationTypeProject(Project project) {
			projectId = project.getProjectId();
			projectName = project.getProjectName();
		}
	}

}
