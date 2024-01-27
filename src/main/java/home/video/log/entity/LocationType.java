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
public class LocationType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationTypeId;
	
	@EqualsAndHashCode.Exclude
	private String locationTypeName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "locationType", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Location> locations = new HashSet<>();

}
