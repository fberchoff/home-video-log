package home.video.log.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class VideoScanType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long videoScanTypeId;
	
	private String videoScanTypeName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany (mappedBy = "videoScanType", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<VideoFormat> videoFormats = new HashSet<>();

}
