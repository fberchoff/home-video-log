package home.video.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.VideoFormat;

public interface VideoFormatDao extends JpaRepository<VideoFormat, Long> {

}
