package home.video.log.controller.model;

import java.util.HashMap;
import java.util.Map;

import home.video.log.entity.VideoFormat;
import home.video.log.entity.VideoScanType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoScanTypeData {
	
	private Long videoScanTypeId;
	
	private String videoScanTypeName;
	private Map<Long, String> videoFormats = new HashMap<>();
	
	public VideoScanTypeData (VideoScanType videoScanType) {
		videoScanTypeId = videoScanType.getVideoScanTypeId();
		videoScanTypeName = videoScanType.getVideoScanTypeName();
		
		for(VideoFormat videoFormat : videoScanType.getVideoFormats()) {
			videoFormats.put(videoFormat.getVideoFormatId(), videoFormat.getVideoFormatName());
		}
	}

}
