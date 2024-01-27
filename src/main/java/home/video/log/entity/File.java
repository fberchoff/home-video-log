package home.video.log.entity;

import java.time.LocalDateTime;
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
public class File {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileId;
	
	@EqualsAndHashCode.Exclude
	private String fileName;
	
	@EqualsAndHashCode.Exclude
	private LocalDateTime createDatetime;
	
	@EqualsAndHashCode.Exclude
	private double fileSizeMb;
	
	@EqualsAndHashCode.Exclude
	private int videoBitRateKbps;
	
	@EqualsAndHashCode.Exclude
	private int audioBitRateKbps;
	
	@EqualsAndHashCode.Exclude
	private double frameRateFps;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn (name = "folder_id", nullable = false)
	private Folder folder;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn (name = "audio_format_id", nullable = false)
	private AudioFormat audioFormat;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn (name = "video_format_id", nullable = false)
	private VideoFormat videoFormat;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany (mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Scene> scenes = new HashSet<>();

}
