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
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;
	
//	@EqualsAndHashCode.Exclude
//	private String userId;
	
	@EqualsAndHashCode.Exclude
	private String projectName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SubjectCategory> subjectCategories = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<StorageLocation> storageLocations = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LocationType> locationTypes = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany (mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Actor> actors = new HashSet<>();
	
	
}
