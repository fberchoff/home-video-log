package home.video.log.controller.model;

import home.video.log.entity.AudioFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AudioFormatData {
	
	private Long audioFormatId;
	
	private String audioFormatName;	
	private int audioChannels;	
	private int audioSampleRateHz;
	
	
	public AudioFormatData (AudioFormat audioFormat) {
		audioFormatId = audioFormat.getAudioFormatId();
		audioFormatName = audioFormat.getAudioFormatName();
		audioChannels = audioFormat.getAudioChannels();
		audioSampleRateHz = audioFormat.getAudioSampleRateHz();
	}
	

}
