-- MySQL dump 10.13  Distrib 8.1.0, for Win64 (x86_64)
--
-- Host: localhost    Database: home_video_log
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `scene_actor`;
DROP TABLE IF EXISTS `scene_subcategory`;
DROP TABLE IF EXISTS `scene`;
DROP TABLE IF EXISTS `actor`;
DROP TABLE IF EXISTS `file`;
DROP TABLE IF EXISTS `folder`;
DROP TABLE IF EXISTS `location`;
DROP TABLE IF EXISTS `location_type`;
DROP TABLE IF EXISTS `storage_location`;
DROP TABLE IF EXISTS `video_format`;
DROP TABLE IF EXISTS `video_scan_type`;
DROP TABLE IF EXISTS `audio_format`;
DROP TABLE IF EXISTS `subject_subcategory`;
DROP TABLE IF EXISTS `subject_category`;
DROP TABLE IF EXISTS `project`;
DROP TABLE IF EXISTS `token`;
DROP TABLE IF EXISTS `user`;

--
-- Table structure for table `user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id`   bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(128) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `user_email` varchar(128) NOT NULL,
  `user_phone` varchar(128) DEFAULT NULL,
  `user_first_name` varchar(128) NOT NULL,
  `user_last_name` varchar(128) DEFAULT NULL,
  `user_role`      varchar(128) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `user_email_UNIQUE` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `token_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `token` varchar(2048) NOT NULL,
  `token_type` varchar(128) NOT NULL,
  `token_expired` boolean NOT NULL,
  `token_revoked` boolean NOT NULL,
  PRIMARY KEY (`token_id`),
  CONSTRAINT `fk_user_token` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `project_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `project_name` varchar(128) NOT NULL,
  PRIMARY KEY (`project_id`),
  KEY `fk_project_User_idx` (`user_id`),
  CONSTRAINT `fk_project_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subject_category`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject_category` (
  `subject_category_id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `subject_category_name` varchar(128) NOT NULL,
  PRIMARY KEY (`subject_category_id`),
  KEY `fk_subject_category_project1_idx` (`project_id`),
  CONSTRAINT `fk_subject_category_project1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subject_subcategory`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject_subcategory` (
  `subject_subcategory_id` bigint NOT NULL AUTO_INCREMENT,
  `subject_category_id` bigint NOT NULL,
  `subject_subcategory_name` varchar(128) NOT NULL,
  PRIMARY KEY (`subject_subcategory_id`),
  KEY `fk_subject_subcategory_subject_category1_idx` (`subject_category_id`),
  CONSTRAINT `fk_subject_subcategory_subject_category1` FOREIGN KEY (`subject_category_id`) REFERENCES `subject_category` (`subject_category_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audio_format`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audio_format` (
  `audio_format_id` bigint NOT NULL AUTO_INCREMENT,
  `audio_format_name` varchar(128) NOT NULL,
  `audio_channels` int DEFAULT NULL,
  `audio_sample_rate_hz` int DEFAULT NULL,
  PRIMARY KEY (`audio_format_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_scan_type`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_scan_type` (
  `video_scan_type_id` bigint NOT NULL AUTO_INCREMENT,
  `video_scan_type_name` varchar(45) NOT NULL,
  PRIMARY KEY (`video_scan_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `video_format`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_format` (
  `video_format_id` bigint NOT NULL AUTO_INCREMENT,
  `video_scan_type_id` bigint NOT NULL,
  `video_format_name` varchar(128) NOT NULL,
  `video_type` varchar(128) DEFAULT NULL,
  `frame_height` int DEFAULT NULL,
  `frame_width` int DEFAULT NULL,
  `video_channels` int DEFAULT NULL,
  PRIMARY KEY (`video_format_id`),
  KEY `video_scan_type_idx` (`video_scan_type_id`),
  CONSTRAINT `video_scan_type` FOREIGN KEY (`video_scan_type_id`) REFERENCES `video_scan_type` (`video_scan_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `storage_location`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_location` (
  `storage_location_id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `storage_location_name` varchar(128) NOT NULL,
  `storage_location_path` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`storage_location_id`),
  KEY `fk_storage_location_project1_idx` (`project_id`),
  CONSTRAINT `fk_storage_location_project1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location_type`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_type` (
  `location_type_id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `location_type_name` varchar(128) NOT NULL,
  PRIMARY KEY (`location_type_id`),
  KEY `fk_location_type_project1_idx` (`project_id`),
  CONSTRAINT `fk_location_type_project1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `location_id` bigint NOT NULL AUTO_INCREMENT,
  `location_type_id` bigint NOT NULL,
  `location_name` varchar(128) NOT NULL,
  `location_street` varchar(128) DEFAULT NULL,
  `location_city` varchar(128) DEFAULT NULL,
  `location_state` varchar(128) DEFAULT NULL,
  `location_zip` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`location_id`),
  KEY `fk_location_location_type1_idx` (`location_type_id`),
  CONSTRAINT `fk_location_location_type1` FOREIGN KEY (`location_type_id`) REFERENCES `location_type` (`location_type_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `folder`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `folder` (
  `folder_id` bigint NOT NULL AUTO_INCREMENT,
  `storage_location_id` bigint NOT NULL,
  `folder_name` varchar(128) NOT NULL,
  PRIMARY KEY (`folder_id`),
  KEY `fk_Folder_storage_location1_idx` (`storage_location_id`),
  CONSTRAINT `fk_Folder_storage_location1` FOREIGN KEY (`storage_location_id`) REFERENCES `storage_location` (`storage_location_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `folder_id` bigint NOT NULL,
  `audio_format_id` bigint NOT NULL,
  `video_format_id` bigint NOT NULL,
  `file_name` varchar(128) NOT NULL,
  `create_datetime` datetime DEFAULT NULL,
  `file_size_mb` decimal(12,2) DEFAULT NULL,
  `video_bit_rate_kbps` int DEFAULT NULL,
  `audio_bit_rate_kbps` int DEFAULT NULL,
  `frame_rate_fps` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `fk_file_audio_format1_idx` (`audio_format_id`),
  KEY `fk_file_video_format1_idx` (`video_format_id`),
  KEY `fk_file_Folder1_idx` (`folder_id`),
  CONSTRAINT `fk_file_audio_format1` FOREIGN KEY (`audio_format_id`) REFERENCES `audio_format` (`audio_format_id`),
  CONSTRAINT `fk_file_Folder1` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`folder_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_file_video_format1` FOREIGN KEY (`video_format_id`) REFERENCES `video_format` (`video_format_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `actor`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actor` (
  `actor_id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` bigint NOT NULL,
  `actor_first_name` varchar(128) NOT NULL,
  `actor_last_name` varchar(128) DEFAULT NULL,
  `actor_birth_date` date DEFAULT NULL,
  PRIMARY KEY (`actor_id`),
  KEY `fk_actor_project1_idx` (`project_id`),
  CONSTRAINT `fk_actor_project1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scene`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scene` (
  `scene_id` bigint NOT NULL AUTO_INCREMENT,
  `file_id` bigint NOT NULL,
  `location_id` bigint NOT NULL,
  `scene_title` varchar(128) NOT NULL,
  `scene_summary` varchar(500) DEFAULT NULL,
  `scene_start_time` time DEFAULT NULL,
  `scene_end_time` time DEFAULT NULL,
  `scene_length_sec` int DEFAULT NULL,
  `scene_rating` int DEFAULT NULL,
  PRIMARY KEY (`scene_id`),
  KEY `fk_scene_location1_idx` (`location_id`),
  KEY `fk_scene_file1_idx` (`file_id`),
  CONSTRAINT `fk_scene_file1` FOREIGN KEY (`file_id`) REFERENCES `file` (`file_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_scene_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scene_subcategory`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scene_subcategory` (
  `scene_id` bigint NOT NULL,
  `subject_subcategory_id` bigint NOT NULL,
  PRIMARY KEY (`scene_id`,`subject_subcategory_id`),
  KEY `fk_scene_has_subject_subcategory_subject_subcategory1_idx` (`subject_subcategory_id`),
  KEY `fk_scene_has_subject_subcategory_scene1_idx` (`scene_id`),
  CONSTRAINT `fk_scene_has_subject_subcategory_scene1` FOREIGN KEY (`scene_id`) REFERENCES `scene` (`scene_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_scene_has_subject_subcategory_subject_subcategory1` FOREIGN KEY (`subject_subcategory_id`) REFERENCES `subject_subcategory` (`subject_subcategory_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scene_actor`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scene_actor` (
  `scene_id` bigint NOT NULL,
  `actor_id` bigint NOT NULL,
  PRIMARY KEY (`scene_id`,`actor_id`),
  KEY `fk_scene_has_actor_actor1_idx` (`actor_id`),
  KEY `fk_scene_has_actor_scene1_idx` (`scene_id`),
  CONSTRAINT `fk_scene_has_actor_actor1` FOREIGN KEY (`actor_id`) REFERENCES `actor` (`actor_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_scene_has_actor_scene1` FOREIGN KEY (`scene_id`) REFERENCES `scene` (`scene_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping routines for database 'home_video_log'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-20 16:45:21
