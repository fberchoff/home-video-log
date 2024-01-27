package home.video.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.LocationType;

public interface LocationTypeDao extends JpaRepository<LocationType, Long> {

}
