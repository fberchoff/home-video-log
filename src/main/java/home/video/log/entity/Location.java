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
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long locationId;
	
	@EqualsAndHashCode.Exclude
	private String locationName;
	
	@EqualsAndHashCode.Exclude
	private String locationStreet;
	
	@EqualsAndHashCode.Exclude
	private String locationCity;
	
	@EqualsAndHashCode.Exclude
	private String locationState;
	
	@EqualsAndHashCode.Exclude
	private String locationZip;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn (name = "location_type_id", nullable = false)
	private LocationType locationType;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany (mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Scene> scenes = new HashSet<>();

}
