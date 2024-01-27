package home.video.log.entity;

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
public class SubjectSubcategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subjectSubcategoryId;
	
	@EqualsAndHashCode.Exclude
	private String subjectSubcategoryName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "subject_category_id", nullable = false)
	private SubjectCategory subjectCategory;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (mappedBy = "subjectSubcategories")
	private Set<Scene> scenes = new HashSet<>();

}
