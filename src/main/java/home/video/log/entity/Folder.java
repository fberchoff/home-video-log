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
public class Folder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long folderId;
	
	@EqualsAndHashCode.Exclude
	private String folderName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "storage_location_id", nullable = false)
	private StorageLocation storageLocation;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany (mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
	Set<File> files = new HashSet<>();

}
