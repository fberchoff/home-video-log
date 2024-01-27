package home.video.log.controller.model;

import home.video.log.entity.VideoFormat;
import home.video.log.entity.VideoScanType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoFormatData {
	
	private Long videoFormatId;
	
	private String videoFormatName;
	private String videoType;
	private int frameHeight;
	private int frameWidth;
	private int videoChannels;
	private VideoFormatVideoScanType videoScanType;
	
	
	public VideoFormatData (VideoFormat videoFormat) {
		
		videoFormatId = videoFormat.getVideoFormatId();
		videoFormatName = videoFormat.getVideoFormatName();
		videoType = videoFormat.getVideoType();
		frameHeight = videoFormat.getFrameHeight();
		frameWidth = videoFormat.getFrameWidth();
		videoChannels = videoFormat.getVideoChannels();
		videoScanType = new VideoFormatVideoScanType(videoFormat.getVideoScanType());
		
	}
	
	
	@Data
	@NoArgsConstructor
	public static class VideoFormatVideoScanType {
		
		private Long videoScanTypeId;
		private String videoScanTypeName;
		
		public VideoFormatVideoScanType (VideoScanType videoScanType) {
			videoScanTypeId = videoScanType.getVideoScanTypeId();
			videoScanTypeName = videoScanType.getVideoScanTypeName();
		}
	}

}
