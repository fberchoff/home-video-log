package home.video.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.VideoScanType;

public interface VideoScanTypeDao extends JpaRepository<VideoScanType, Long> {

}
