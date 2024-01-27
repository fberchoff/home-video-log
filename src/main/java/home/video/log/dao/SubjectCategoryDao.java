package home.video.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.SubjectCategory;

public interface SubjectCategoryDao extends JpaRepository<SubjectCategory, Long> {

}
