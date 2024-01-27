package home.video.log.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.SubjectSubcategory;

public interface SubjectSubcategoryDao extends JpaRepository<SubjectSubcategory, Long> {
	
	Set<SubjectSubcategory> findAllBySubjectSubcategoryIdIn(Set<Long> subjectSubcategoryIds);

}
