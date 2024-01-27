package home.video.log.controller.model;

import java.util.HashMap;
import java.util.Map;

import home.video.log.entity.Location;
import home.video.log.entity.LocationType;
import home.video.log.entity.Scene;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationData {
	
	private Long locationId;
	
	private String locationName;
	private String locationStreet;
	private String locationCity;
	private String locationState;
	private String locationZip;
	private LocationLocationType locationType;
	private Map<Long, String> scenes = new HashMap<>();
	
	public LocationData(Location location) {
		locationId = location.getLocationId();
		locationName = location.getLocationName();
		locationStreet = location.getLocationStreet();
		locationCity = location.getLocationCity();
		locationState = location.getLocationState();
		locationZip = location.getLocationZip();
		locationType = new LocationLocationType(location.getLocationType());
		
		for (Scene scene : location.getScenes()) {
			scenes.put(scene.getSceneId(), scene.getSceneTitle());
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class LocationLocationType {
		
		private Long locationTypeId;
		private String locationTypeName;
		
		public LocationLocationType (LocationType locationType) {
			locationTypeId = locationType.getLocationTypeId();
			locationTypeName = locationType.getLocationTypeName();
		}
	}

}
