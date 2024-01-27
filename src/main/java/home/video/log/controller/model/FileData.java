package home.video.log.controller.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import home.video.log.entity.AudioFormat;
import home.video.log.entity.File;
import home.video.log.entity.Folder;
import home.video.log.entity.Scene;
import home.video.log.entity.VideoFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileData {
	
	private Long fileId;
	
	private String fileName;
	private LocalDateTime createDatetime;
	private Double fileSizeMb;
	private int videoBitRateKbps;
	private int audioBitRateKbps;
	private double frameRateFps;	
	private FileFolder folder;
	private FileAudioFormat audioFormat;
	private FileVideoFormat videoFormat;
	private Map<Long, String> scenes = new HashMap<>();
	
	
	public FileData (File file) {
		
		fileId = file.getFileId();
		fileName = file.getFileName();
		createDatetime = file.getCreateDatetime();
		fileSizeMb = file.getFileSizeMb();
		videoBitRateKbps = file.getVideoBitRateKbps();
		audioBitRateKbps = file.getAudioBitRateKbps();
		frameRateFps = file.getFrameRateFps();
		folder = new FileFolder(file.getFolder());
		audioFormat = new FileAudioFormat(file.getAudioFormat());
		videoFormat = new FileVideoFormat(file.getVideoFormat());
		
		for (Scene scene : file.getScenes()) {
			scenes.put(scene.getSceneId(), scene.getSceneTitle());
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class FileFolder {
		
		private Long folderId;
		private String folderName;
		
		public FileFolder (Folder folder) {
			folderId = folder.getFolderId();
			folderName = folder.getFolderName();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class FileAudioFormat {
		
		private Long audioFormatId;
		private String audioFormatName;
		private int audioChannels;
		private int audioSampleRateHz;
		
		public FileAudioFormat (AudioFormat audioFormat) {
			
			audioFormatId = audioFormat.getAudioFormatId();
			audioFormatName = audioFormat.getAudioFormatName();
			audioChannels = audioFormat.getAudioChannels();
			audioSampleRateHz = audioFormat.getAudioSampleRateHz();			
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class FileVideoFormat {
		
		private Long videoFormatId;
		private String videoFormatName;
		private String videoType;
		private int frameHeight;
		private int frameWidth;
		private int videoChannels;
		private Long videoScanTypeId;
		private String videoScanTypeName;
		
		public FileVideoFormat (VideoFormat videoFormat) {
			
			videoFormatId = videoFormat.getVideoFormatId();
			videoFormatName = videoFormat.getVideoFormatName();
			videoType = videoFormat.getVideoType();
			frameHeight = videoFormat.getFrameHeight();
			frameWidth = videoFormat.getFrameWidth();
			videoChannels = videoFormat.getVideoChannels();
			videoScanTypeId = videoFormat.getVideoScanType().getVideoScanTypeId();
			videoScanTypeName = videoFormat.getVideoScanType().getVideoScanTypeName();
		}
	}	
	
}
