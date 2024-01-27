package home.video.log.entity;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Scene {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sceneId;
	
	@EqualsAndHashCode.Exclude
	private String sceneTitle;
	
	@EqualsAndHashCode.Exclude
	private String sceneSummary;
	
	@EqualsAndHashCode.Exclude
	private LocalTime sceneStartTime;
	
	@EqualsAndHashCode.Exclude
	private LocalTime sceneEndTime;
	
	@EqualsAndHashCode.Exclude
	private int sceneLengthSec;
	
	@EqualsAndHashCode.Exclude
	private int sceneRating;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn (name = "file_id", nullable = false)
	private File file;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn (name = "location_id", nullable = false)
	private Location location;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (cascade = CascadeType.PERSIST)
	@JoinTable (
			name = "scene_subcategory",
			joinColumns = @JoinColumn (name = "scene_id"),
			inverseJoinColumns = @JoinColumn (name = "subject_subcategory_id"))	
	private Set<SubjectSubcategory> subjectSubcategories = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (cascade = CascadeType.PERSIST)
	@JoinTable (
			name = "scene_actor",
			joinColumns = @JoinColumn (name = "scene_id"),
			inverseJoinColumns = @JoinColumn (name = "actor_id"))
	private Set<Actor> actors = new HashSet<>();

}
