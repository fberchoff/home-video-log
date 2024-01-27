package home.video.log.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Actor {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long actorId;
	
	@EqualsAndHashCode.Exclude
	private String actorFirstName;
	
	@EqualsAndHashCode.Exclude
	private String actorLastName;
	
	@EqualsAndHashCode.Exclude
	private LocalDate actorBirthDate;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn (name = "project_id", nullable = false)
	private Project project;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (mappedBy = "actors")
	private Set<Scene> scenes = new HashSet<>();

}
