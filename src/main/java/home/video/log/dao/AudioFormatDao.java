package home.video.log.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import home.video.log.entity.AudioFormat;

public interface AudioFormatDao extends JpaRepository<AudioFormat, Long> {

}
