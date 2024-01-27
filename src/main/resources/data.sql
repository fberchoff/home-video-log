INSERT INTO `user` VALUES (1,'johnnyb','$2a$10$5PM2.rPBKETyHojhQ6SjwOlcFX8zfpErKGoC2Z0AIjME8IzZruJOW','johnnybinton@gmail.com','(908) 334-8715','Johnny','Binton','ADMIN')
                         ,(2,'harold','$2a$10$DP5fNcV.o50M9wqUzUuZO.QhcZVHTiOIdcgANVD6xU64DE8VnpLcW','harold@gmail.com','(908) 555-1212','Harold','Cooper','USER');

INSERT INTO `project` VALUES (1,1,'2023 Holiday Compilation'),(2,2,'Harold\'s 2024 Holidays');

INSERT INTO `audio_format` VALUES (1,'AAC 48,000 Hz, 256 kbps, Stereo',2,48000)
                                 ,(2,'Dolby AC-3 48,000 Hz, Stereo ',2,48000)
								 ,(3,'AAC 44,100 Hz, 97 kbps, Mono',1,44100);

INSERT INTO `video_scan_type` VALUES (1,'Progressive'),(2,'Interlaced');								 

INSERT INTO `video_format` VALUES (1,1,'MP4 AVC 1920x1080 progressive','MP4 AVC',1080,1920,1)
                                 ,(2,1,'MPEG-2 AVC 1920x1080 progressive','MPEG-2 AVC',1080,1920,1)
								 ,(3,1,'MP4 AVC 1280x720 progressive','MP4 AVC',720,1280,1);

INSERT INTO `subject_category` VALUES (1,1,'Holidays')
                                     ,(2,1,'Summer Activities')
									 ,(3,1,'Weather')
									 ,(4,2,'Cooper Family Holidays')
									 ,(5,2,'Spring Time Fun')
									 ,(6,2,'Summer Fun')
									 ,(7,2,'Winter Fun');

INSERT INTO `subject_subcategory` VALUES (1,1,'Independence Day')
                                        ,(2,1,'Labor Day')
										,(3,1,'Halloween')
										,(4,2,'Sporting Events')
										,(5,3,'Snow')
										,(6,4,'New Years Day')
										,(7,4,'Mother\'s Day')
										,(8,5,'Memorial Day')
										,(9,6,'Trip to Lake George')
										,(10,6,'Point Pleasant Boardwalk')
										,(11,7,'Ski Trip');

INSERT INTO `storage_location` VALUES (1,1,'SanDisk Extreme SSD #1 Root','E:\\')
                                     ,(2,1,'Internal Drive #1','C:\\users\\jbash\\pictures\\')
									 ,(3,2,'Harold PC C Drive','C:\\users\\hcooper\\videos\\')
									 ,(4,2,'Seagate External Drive','E:\\Family Videos\\');

INSERT INTO `location_type` VALUES (1,1,'Baseball Stadium')
                                  ,(2,1,'Roadway')
								  ,(3,2,'Residence')
								  ,(4,2,'Vacation Destination')
								  ,(5,2,'Resort');

INSERT INTO `location` VALUES (1,1,'Trenton Thunder Ballpark','1 Thunder Road','Trenton','NJ','08611')
                             ,(2,2,'Meadow Road','Meadow Road','Bedminster','NJ','07921')
							 ,(3,3,'15 Smith Drive','Smith Drive','Branchburg','NJ','08876')
							 ,(4,5,'Great Escape Lodge',' 89 Six Flags Drive','Queensbury','NY','12804')
							 ,(5,4,'Fort William Henry','48 Canada St.','Lake George','NY','12845')
							 ,(6,5,'Whiteface Lodge','7 Whiteface Inn Ln','Lake Placid','NY','12946');

INSERT INTO `folder` VALUES (3,1,'July Clips')
                           ,(4,1,'September Clips')
						   ,(5,1,'Halloween 2023')
						   ,(6,2,'camera roll')
						   ,(7,3,'January 2023')
						   ,(8,3,'July 2023')
						   ,(9,3,'January 2024')
						   ,(10,4,'Video Backups');

INSERT INTO `file` VALUES (1,3,1,1,'20230710_191240.mp4','2023-07-10 19:12:40',65.50,27506,249,59.55)
                         ,(2,6,2,2,'20170320203021.m2ts','2017-03-20 20:30:21',74.10,25895,256,59.94)
						 ,(3,6,3,3,'WP_20140218_003.mp4','2014-02-18 06:43:14',34.70,10404,97,29.80)
						 ,(4,6,3,1,'WP_20150725_005.mp4','2015-07-25 19:10:42',23.40,21860,97,30.00)
						 ,(5,7,1,1,'20221231_231132.mp4','2022-12-31 23:11:32',54.20,26432,247,59.94)
						 ,(6,7,1,1,'20230101_000000.mp4','2023-01-01 00:00:00',71.50,27501,249,59.55)
						 ,(7,8,2,2,'20230704152443.m2ts','2023-07-04 15:24:43',101.30,25756,256,59.94)
						 ,(8,9,2,2,'20240115215423.m2ts','2024-01-01 21:54:23',31.60,25654,256,59.94)
						 ,(9,10,3,2,'20240513101816.m2ts','2024-05-13 10:18:16',25.30,25550,256,59.94)
						 ,(10,10,3,3,'20240513_001.mp4','2024-05-13 10:30:47',69.90,26901,251,59.94)
						 ,(11,10,3,3,'20240513_002.mp4','2024-05-13 10:35:10',65.40,26800,250,59.94);
						 
INSERT INTO `actor` VALUES (1,1,'Jenny','Craig','1932-08-07')
                          ,(2,1,'Johnny','Driver','1990-06-10');
						  
INSERT INTO `scene` VALUES (1,4,1,'A Day at the Ballpark','Watching a Binghampton Mets game at the Trenton Thunder ballpark.','00:00:00','00:00:08',8,2)
                          ,(3,3,2,'Driving to Work in Snow','Driving to work on an extreme snow day and almost crashing.','00:00:00','00:00:27',27,2)
						  ,(4,5,3,'Harold Doing a Shot','Harold does a shot right before the ball drops','00:15:12','00:15:54',42,4)
						  ,(5,7,3,'Sarah Cooking the Burgers','Sarah cooks the hamburgers at our July 4 BBQ','02:32:10','04:01:23',101,3)
						  ,(6,7,3,'Harold makes the Winning Toss','Harold makes the winning toss in the beanbag toss competition','01:10:15','01:11:20',65,5);
						  
INSERT INTO `scene_actor` VALUES (1,1)
                                ,(3,2);
								
INSERT INTO `scene_subcategory` VALUES (1,4)
                                      ,(3,5);