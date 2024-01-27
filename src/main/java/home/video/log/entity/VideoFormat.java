package home.video.log.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class VideoFormat {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long videoFormatId;
	
	@EqualsAndHashCode.Exclude
	private String videoFormatName;
	
	@EqualsAndHashCode.Exclude
	private String videoType;
	
	@EqualsAndHashCode.Exclude
	private int frameHeight;
	
	@EqualsAndHashCode.Exclude
	private int frameWidth;
	
	@EqualsAndHashCode.Exclude
	private int videoChannels;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "video_scan_type_id", nullable = false)
	private VideoScanType videoScanType;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany (mappedBy = "videoFormat", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<File> files = new HashSet<>();

}
