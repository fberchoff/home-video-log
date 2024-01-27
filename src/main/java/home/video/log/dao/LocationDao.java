package home.video.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

}
