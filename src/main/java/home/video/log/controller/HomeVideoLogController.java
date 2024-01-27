package home.video.log.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import home.video.log.controller.model.ActorData;
import home.video.log.controller.model.AudioFormatData;
import home.video.log.controller.model.FileData;
import home.video.log.controller.model.FolderData;
import home.video.log.controller.model.LocationData;
import home.video.log.controller.model.LocationTypeData;
import home.video.log.controller.model.ProjectData;
import home.video.log.controller.model.SceneData;
import home.video.log.controller.model.StorageLocationData;
import home.video.log.controller.model.SubjectCategoryData;
import home.video.log.controller.model.SubjectSubcategoryData;
import home.video.log.controller.model.UserData;
import home.video.log.controller.model.VideoFormatData;
import home.video.log.controller.model.VideoScanTypeData;
import home.video.log.service.HomeVideoLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/home_video_log")
@Slf4j
//*@Tag(name = "Home Video Log")
public class HomeVideoLogController {
	@Autowired
	private HomeVideoLogService homeVideoLogService;

	
//* Retrieve user details for a user
	
	@Tag(name = "02. User", description = "User operations")
	@Operation(description = "Gets information about a user",
			   summary = "Get details of a user")
	@GetMapping("/user/{userName}")
	public UserData retrieveUserByName(@PathVariable String userName) {
		log.info("Retrieving user with Name={}", userName);
		return homeVideoLogService.retrieveUserByName(userName);
	}


//* Retrieve the list of all projects for a user
	
	@Tag(name = "04. Project", description = "Project level operations")
	@Operation(description = "Gets the list of projects belonging to a given user",
			   summary = "Get list of projects for a user")	
	@GetMapping("/user/{userName}/project")
	public List<ProjectData> retrieveAllProjectsByUserName(@PathVariable String userName) {
		log.info("Returning projects for user name={}", userName);
		return homeVideoLogService.retrieveAllProjectsByUserName(userName);
		
	}

	
//* Add a new project
	
	@Tag(name = "04. Project", description = "Project level Operations")
	@Operation(description = "Add a new project for a given user",
			   summary = "Add a new project")
	@PostMapping("/user/{userName}/project")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProjectData insertProject(@PathVariable String userName
            , @io.swagger.v3.oas.annotations.parameters.RequestBody(
           		 content = @io.swagger.v3.oas.annotations.media.Content(
           				 examples = {
           						 @ExampleObject(
           		value = "{\"projectName\": \"My Wonderful Family\", \"user\": {\"userName\": \"Harold\"}}"
           		)  }))
            						@RequestBody ProjectData projectData) {
		log.info("Creating project {} for user with name={}", projectData, userName);
		return homeVideoLogService.saveProject(userName, projectData);		
	}
	

//* Add a new video scan type.
//* 
//* This is an administrative function.  Only users with the ADMIN role can use this.
	
	@Tag(name = "03. Administration", description = "Administrative operations")
	@Operation(description = "Add a new video scan type",
			   summary = "Add a new video scan type")
	@PostMapping("/videoscantype")	
	public VideoScanTypeData insertVideoScanType(
            							@io.swagger.v3.oas.annotations.parameters.RequestBody(
           		 content = @io.swagger.v3.oas.annotations.media.Content(
           				 examples = {
           						 @ExampleObject(
           		value = "{\"videoScanTypeName\": \"Progressive\"}"
           		)  }))
            							@RequestBody VideoScanTypeData videoScanTypeData) {
		log.info("Creating video scan type {}", videoScanTypeData);
		return homeVideoLogService.saveVideoScanType(videoScanTypeData);		
	}
	
	
//* Add a new video format.
//*	
//* This is an administrative function.  Only users with the ADMIN role can use this.

	@Tag(name = "03. Administration", description = "Administrative operations")
	@Operation(description = "Add a new video format",
			   summary = "Add a new video format")
	@PostMapping("/videoscantype/{videoScanTypeId}/videoformat")
	public VideoFormatData insertVideoFormat(@PathVariable Long videoScanTypeId
            								,@io.swagger.v3.oas.annotations.parameters.RequestBody(
            									content = @io.swagger.v3.oas.annotations.media.Content(
            											examples = {
            													@ExampleObject(
            	value = "{\"videoFormatName\": \"MP4 AVC 1280x720 progressive\", \"videoType\": \"MP4 AVC\", \"frameHeight\": 720, \"frameWidth\": 1280, \"videoChannels\": 1, \"videoScanType\": {\"videoScanTypeId\": 1}}"
            	)  }))
          
			                                 @RequestBody VideoFormatData videoFormatData) {
		log.info("Creating video format {} with video scan type ID={}", videoFormatData, videoScanTypeId);
		return homeVideoLogService.saveVideoFormat(videoScanTypeId, videoFormatData);
	}

	
//* Add a new audio format.
	//*	
	//* This is an administrative function.  Only users with the ADMIN role can use this.
	
	@Tag(name = "03. Administration", description = "Administrative operations")
	@Operation(description = "Create a new audio format",
			   summary = "Create a new audio format")
	@PostMapping("/audioformat")
	public AudioFormatData insertAudioFormat(
										@io.swagger.v3.oas.annotations.parameters.RequestBody(
				content = @io.swagger.v3.oas.annotations.media.Content(
						examples = {
								@ExampleObject(
				value = "{\"audioFormatName\": \"AAC 44,100 Hz, 97 kbps, Mono\", \"audioChannels\": 2, \"audioSampleRateHz\": 44100}"
				)  }))
										@RequestBody AudioFormatData audioFormatData) {
		log.info("Creating audio format {}", audioFormatData);
		return homeVideoLogService.saveAudioFormat(audioFormatData);		
	}

	
//* Retrieve the list of all storage locations for a project
	
	@Tag(name = "05. Storage Location", description = "Storage location operations")
	@Operation(description = "Retrieves a list of all storage locations for a given project " +
            							"(must provide owner's user ID)",
			   summary = "Get a list of all storage locations for a project")
	@GetMapping("/user/{userName}/project/{projectId}/storagelocation")
	public List<StorageLocationData> retrieveAllStorageLocationsByUserNameAndProjectId
										(@PathVariable String userName
									   , @PathVariable Long projectId) {
		log.info("Returning storage locations for user name={} and project ID={}", userName, projectId);
		return homeVideoLogService.retrieveAllStorageLocationsByUserNameAndProjectId(userName, projectId);		
	}
	
	
//* Retrieve details of a storage location

	@Tag(name = "05. Storage Location", description = "Storage location operations")
	@Operation(description = "Gets information on a storage location (must provide owner's user ID)",
			   summary = "Get details of a storage location")
	@GetMapping("/user/{userName}/storagelocation/{storageLocationId}")
	public StorageLocationData retrieveStorageLocationById
										(@PathVariable String userName
									   , @PathVariable Long storageLocationId) {
		log.info("Retrieving storage location with user name={} and storage location ID={}", userName, storageLocationId);
		return homeVideoLogService.retrieveStorageLocationById(userName, storageLocationId);		
	}
	
	
//* Add a new storage location to a project

	@Tag(name = "05. Storage Location", description = "Storage location operations")
	@Operation(description = "Adds a new storage location to a given project " +
            "(must provide owner's user ID)",
			summary = "Add a new storage location to a project")
	@PostMapping("/user/{userName}/project/{projectId}/storagelocation")
	@ResponseStatus(code = HttpStatus.CREATED)
	public StorageLocationData insertStorageLocation(@PathVariable String userName
													,@PathVariable Long projectId
									                ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
										                		 content = @io.swagger.v3.oas.annotations.media.Content(
										                				 examples = {
										                						 @ExampleObject(
				value = "{\"storageLocationName\": \"Western Digital External Drive\", \"storageLocationPath\": \"E:\\\\Family Videos\\\\\"}"
				)  }))
										             @RequestBody StorageLocationData storageLocationData) {
		log.info("Creating storage location {} for project with ID={}", storageLocationData, projectId);
		return homeVideoLogService.saveStorageLocation(userName, projectId, storageLocationData);
		
	}
	
	
//* Retrieve details of a subject category

	@Tag(name = "08. Subject Category", description = "Subject category operations")
	@Operation(description = "Gets information about a subject category (must provide " +
            "owner's user ID)",
			   summary = "Get details of a subject category")	
	@GetMapping("/user/{userName}/subjectcategory/{subjectCategoryId}")
	public SubjectCategoryData retrieveSubjectCategoryById(@PathVariable String userName
														  ,@PathVariable Long subjectCategoryId) {
		log.info("Retrieving subject category with user name={} and subject category ID={}"
														, userName, subjectCategoryId);
		return homeVideoLogService.retrieveSubjectCategoryById(userName, subjectCategoryId);
	}
	
	
//* Add a new subject category to a project

	@Tag(name = "08. Subject Category", description = "Subject category operations")
	@Operation(description = "Adds a new subject category for a project " +
            "(must provide owner's user ID)",
			   summary = "Add a new subject category for a project")	
	@PostMapping("/user/{userName}/project/{projectId}/subjectcategory")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SubjectCategoryData insertSubjectCategory(@PathVariable String userName
													,@PathVariable Long projectId
									                ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
									                		content = @io.swagger.v3.oas.annotations.media.Content(
									                				examples = {
									                						@ExampleObject(
									                								value = "{\"subjectCategoryName\": \"Football\"}"
										                		  		)  }))
										             @RequestBody SubjectCategoryData subjectCategoryData) {
		log.info("Creating subject category {} for project with ID={}", subjectCategoryData, projectId);
		return homeVideoLogService.saveSubjectCategory(userName, projectId, subjectCategoryData);		
	}
	
	
//* Update a subject category

	@Tag(name = "08. Subject Category", description = "Subject category operations")
	@Operation(description = "Updates a subject category for a project " +
            "(must provide owner's user ID)",
			   summary = "Update a subject category for a project")	
	@PutMapping("/user/{userName}/project/{projectId}/subjectcategory/{subjectCategoryId}")
	public SubjectCategoryData updateSubjectCategory(@PathVariable String userName
													,@PathVariable Long projectId
													,@PathVariable Long subjectCategoryId
									                ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
									                		content = @io.swagger.v3.oas.annotations.media.Content(
									                				examples = {
									                						@ExampleObject(
									                								value = "{\"subjectCategoryName\": \"Baseball\"}"
										                		  		)  }))
										             @RequestBody SubjectCategoryData subjectCategoryData) {
		subjectCategoryData.setSubjectCategoryId(subjectCategoryId);
		log.info("Updating subject category {}", subjectCategoryData);
		return homeVideoLogService.saveSubjectCategory(userName, projectId, subjectCategoryData);		
	}
	
	
//* Add a new location type to a project

	@Tag(name = "10. Location Type", description = "Location type operations")
	@Operation(description = "Adds a new location type to a project " +
            "(must provide owner's user ID)",
			   summary = "Add a new location type to a project")
	@PostMapping("/user/{userName}/project/{projectId}/locationtype")
	@ResponseStatus(code = HttpStatus.CREATED)	
	public LocationTypeData insertlocationType(@PathVariable String userName
			                                  ,@PathVariable Long projectId
			    			                  ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
			 				                		 content = @io.swagger.v3.oas.annotations.media.Content(
			 				                				 examples = {
			 				                						 @ExampleObject(
			 	value = "{\"locationTypeName\": \"Residence\"}"
			 	)  }))
			 				                  @RequestBody LocationTypeData locationTypeData) {
		log.info("Creating location type {} for project with ID={}", locationTypeData, projectId);
		return homeVideoLogService.saveLocationType(userName, projectId, locationTypeData);		
	}
	
	
//* Add a new location

	@Tag(name = "11. Location", description = "Location operations")
	@Operation(description = "Adds a new location of a given location type " +
            "(must provide owner's user ID)",
			   summary = "Add a new location of a given location type")
	@PostMapping("/user/{userName}/locationtype/{locationTypeId}/location")
	@ResponseStatus(code = HttpStatus.CREATED)	
	public LocationData insertLocation(@PathVariable String userName
			                          ,@PathVariable Long locationTypeId
					                  ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
						                		 content = @io.swagger.v3.oas.annotations.media.Content(
						                				 examples = {
						                						 @ExampleObject(
				value = "{\"locationName\": \"Fort William Henry\", \"locationStreet\": \"48 Canada St.\", \"locationCity\": \"Lake George\", \"locationState\": \"NY\", \"locationZip\": \"12946\"}"
				)  }))
						               @RequestBody LocationData locationData) {
		log.info("Creating location {} for location type with ID={}", locationData, locationTypeId);
		return homeVideoLogService.saveLocation(userName, locationTypeId, locationData);		
	}
	
	
//* Retrieve details of a folder

	@Tag(name = "06. Folder", description = "Folder operations")
	@Operation(description = "Gets information on a folder (must provide owner's user ID)",
			   summary = "Get details of a folder")
	@GetMapping("/user/{userName}/folder/{folderId}")
	public FolderData retrieveFolderById(@PathVariable String userName, @PathVariable Long folderId) {
			log.info("Retrieving folder with user name={} and folder ID={}", userName, folderId);
			return homeVideoLogService.retrieveFolderById(userName, folderId);
	}
	
	
//* Add a new folder to a storage location

	@Tag(name = "06. Folder", description = "Folder operations")
	@Operation(description = "Add a new folder to a given storage location " +
            "(must provide owner's user ID)",
			summary = "Add a new folder to a given storage location")
	@PostMapping("/user/{userName}/storagelocation/{storageLocationId}/folder")
	@ResponseStatus(code = HttpStatus.CREATED)	
	public FolderData insertFolder(@PathVariable String userName
			                      ,@PathVariable Long storageLocationId
				                  ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
					                		 content = @io.swagger.v3.oas.annotations.media.Content(
					                				 examples = {
					                						 @ExampleObject(
					value = "{\"folderName\": \"Bad Memories\"}"
					)  }))
					               @RequestBody FolderData folderData) {
		log.info("Creating folder {} for storage location with ID={}", folderData, storageLocationId);
		return homeVideoLogService.saveFolder(userName, storageLocationId, folderData);		
	}
	
	
//* Retrieve details of a file

	@Tag(name = "07. File", description = "File operations")
	@Operation(description = "Gets information on a file (must provide owner's user ID)",
			   summary = "Get details of a file")
	@GetMapping("/user/{userName}/file/{fileId}")
	public FileData retrieveFileById(@PathVariable String userName, @PathVariable Long fileId) {
		log.info("Retrieving file with user name={} and file ID={}", userName, fileId);
		return homeVideoLogService.retrieveFileById(userName, fileId);
	}
	
	
//* Retrieve the list of all files for a project

	@Tag(name = "07. File", description = "File operations")
	@Operation(description = "Gets a list of all files for a given project " +
            "(must provide owner's user ID)",
			   summary = "Get a list of all files for a project")
	@GetMapping("/user/{userName}/project/{projectId}/file")
	public List<FileData> retrieveAllFilesByUserNameAndProjectId(@PathVariable String userName
			                                                   , @PathVariable Long projectId) {
		log.info("Returning files for user name={} and project ID={}", userName, projectId);
		return homeVideoLogService.retrieveAllFilesByUserNameAndProjectId(userName, projectId);
	}

	
//* Add a file to a folder
	
	@Tag(name = "07. File", description = "File operations")
	@Operation(description = "Add a new file to a given folder " +
            "(must provide owner's user ID)",
			summary = "Add a file to a folder")	
	@PostMapping("/user/{userName}/folder/{folderId}/file")
	@ResponseStatus(code = HttpStatus.CREATED)
	public FileData insertFile(@PathVariable String userName
			                  ,@PathVariable Long folderId
			                  ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
				                		 content = @io.swagger.v3.oas.annotations.media.Content(
				                				 examples = {
				                						 @ExampleObject(
				value = "{\"fileName\": \"20240117_120000.mp4\", \"createDatetime\": \"2024-01-01T00:02:00\", \"fileSizeMb\": 65.3, \"videoBitRateKbps\": 27000, \"audioBitRateKbps\": 250, \"frameRateFps\": 59.94, \"audioFormat\": {\"audioFormatId\": 1}, \"videoFormat\": {\"videoFormatId\": 1}}"
				)  }))
				               @RequestBody FileData fileData) {
		log.info("Creating file {} for folder with ID={}", fileData, folderId);
		return homeVideoLogService.saveFile(userName
				                          , folderId
				                          , fileData.getAudioFormat().getAudioFormatId()
				                          , fileData.getVideoFormat().getVideoFormatId()
				                          , fileData);		
	}
	
	
//* Update a file

	@Tag(name = "07. File", description = "File operations")
	@Operation(description = "Updates a file of a given folder " +
            "(must provide owner's user ID)",
			summary = "Update a file of a folder")	
	@PutMapping("/user/{userName}/folder/{folderId}/file/{fileId}")
	public FileData updateFile(@PathVariable String userName
			                 , @PathVariable Long folderId
			                 , @PathVariable Long fileId
			                 , @io.swagger.v3.oas.annotations.parameters.RequestBody(
			                		 content = @io.swagger.v3.oas.annotations.media.Content(
			                				 examples = {
			                						 @ExampleObject(
			value = "{\"fileName\": \"20240117_120000.mp4\", \"createDatetime\": \"2024-01-01T00:02:00\", \"fileSizeMb\": 65.3, \"videoBitRateKbps\": 27000, \"audioBitRateKbps\": 250, \"frameRateFps\": 59.94, \"audioFormat\": {\"audioFormatId\": 1}, \"videoFormat\": {\"videoFormatId\": 1}}"
			)  }))
			                 @RequestBody FileData fileData) {
		fileData.setFileId(fileId);
		log.info("Updating file {}", fileData);
		return homeVideoLogService.saveFile(userName
				                          , folderId
				                          , fileData.getAudioFormat().getAudioFormatId()
				                          , fileData.getVideoFormat().getVideoFormatId()
				                          , fileData);
	}
	
	
//* Delete a file

	@Tag(name = "07. File", description = "File operations")
	@Operation(description = "Deletes a file (must provide owner's user ID)",
			summary = "Delete a file")
	@DeleteMapping("/user/{userName}/file/{fileId}")
	public Map<String, String> deleteFileById(@PathVariable String userName, @PathVariable Long fileId) {
		log.info("Deleting file with ID={}", fileId);
		
		homeVideoLogService.deleteFileById(userName, fileId);

		return Map.of("message", "Deletion of file with ID=" + fileId
				+ " was succesful.");
	}
	
	
//* Retrieve details of an actor

	@Tag(name = "13. Actor", description = "Actor operations")
	@Operation(description = "Gets information on an actor (must provide owner's user ID)",
			   summary = "Get details of an actor")
	@GetMapping("/user/{userName}/actor/{actorId}")
	public ActorData retrieveActorById(@PathVariable String userName, @PathVariable Long actorId) {
		log.info("Retrieving actor with user name={} and actor ID={}", userName, actorId);
		return homeVideoLogService.retrieveActorById(userName, actorId);
	}

	
//* Add an actor
	
	@Tag(name = "13. Actor", description = "Actor operations")
	@Operation(description = "Adds a new actor to a given project " +
            "(must provide owner's user ID). Each included scene is a key-value pair of scene ID and scene name. " +
			"Scenes must already exist. Scene name is required, but not used to update actual scene name.",
			   summary = "Add a new actor to a project")
	@PostMapping("/user/{userName}/project/{projectId}/actor")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ActorData insertActor(@PathVariable String userName
								,@PathVariable Long projectId
				                , @io.swagger.v3.oas.annotations.parameters.RequestBody(
				                		 content = @io.swagger.v3.oas.annotations.media.Content(
				                				 examples = {
				                						 @ExampleObject(
				value = "{\"actorFirstName\": \"Harold\", \"actorLastName\": \"Kasik\", \"actorBirthDate\": \"1968-10-01\", \"scenes\": {\"5\": \"Not Used\", \"6\": \"Not Used\"}}"
				)  }))
				                 @RequestBody ActorData actorData) {
		log.info("Creating actor {} for project with ID={}", actorData, projectId);
		return homeVideoLogService.saveActor(userName, projectId, actorData);		
	}
	
	
//* Update an actor

	@Tag(name = "13. Actor", description = "Actor operations")
	@Operation(description = "Updates an actor for a given project " +
            "(must provide owner's user ID). Each included scene is a key-value pair of scene ID and scene name. " +
            "Scenes must already exist. Scene name is required, but not used to update actual scene name.",
			   summary = "Update an actor for a given project")	
	@PutMapping("/user/{userName}/project/{projectId}/actor/{actorId}")
	public ActorData updateActor(@PathVariable String userName
								,@PathVariable Long projectId
								,@PathVariable Long actorId
				                , @io.swagger.v3.oas.annotations.parameters.RequestBody(
				                		 content = @io.swagger.v3.oas.annotations.media.Content(
				                				 examples = {
				                						 @ExampleObject(
				value = "{\"actorFirstName\": \"Harold\", \"actorLastName\": \"Kasik\", \"actorBirthDate\": \"1968-10-01\", \"scenes\": {\"5\": \"Not Used\", \"6\": \"Not Used\"}}"
				)  }))
				                 @RequestBody ActorData actorData) {
		actorData.setActorId(actorId);
		log.info("Updating actor {}", actorData);
		return homeVideoLogService.saveActor(userName, projectId, actorData);		
	}
	
	
//* Delete an actor

	@Tag(name = "13. Actor", description = "Actor operations")
	@Operation(description = "Deletes an actor (must provide owner's user ID)",
			   summary = "Delete an actor")
	@DeleteMapping("/user/{userName}/actor/{actorId}")
	public Map<String, String> deleteActorById(@PathVariable String userName
			                                 , @PathVariable Long actorId) {
		log.info("Deleting actor with ID={}", actorId);
		
		homeVideoLogService.deleteActorById(userName, actorId);
		
		return Map.of("message", "Deletion of actor with ID=" + actorId
				+ " was succesful.");		
	}
	
	
//* Add a subject subcategory to a subject category

	@Tag(name = "09. Subject Subcategory", description = "Subject subcategory operations")
	@Operation(description = "Adds a new subject subcategory to a given subject category. " +
            "(must provide owner's user ID). Each included scene is a key-value pair of scene ID and scene name. " +
            "Scenes must already exist. Scene name is required, but not used to update actual scene name.",
			   summary = "Add a new subject subcategory to a subject category")
	@PostMapping("/user/{userName}/subjectcategory/{subjectCategoryId}/subjectsubcategory")
	@ResponseStatus(code = HttpStatus.CREATED)	
	public SubjectSubcategoryData insertSubjectsubcategory(@PathVariable String userName
			                                              ,@PathVariable Long subjectCategoryId
			                			                  ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
			             				                		 content = @io.swagger.v3.oas.annotations.media.Content(
			             				                				 examples = {
			             				                						 @ExampleObject(
			     value = "{\"subjectSubcategoryName\": \"Off to the Beach\", \"scenes\": {\"5\": \"Not Used\", \"6\": \"Not Used\"}}"
			     )  }))
			             				                  @RequestBody SubjectSubcategoryData subjectSubcategoryData) {
		log.info("Creating subject subcategory {} for subject category with ID={}", subjectSubcategoryData, subjectCategoryId);
		return homeVideoLogService.saveSubjectSubcategory(userName, subjectCategoryId, subjectSubcategoryData);		
	}
	
	
//* Retrieve details of a scene

	@Tag(name = "12. Scene", description = "Scene operations")
	@Operation(description = "Gets information on a scene (must provide owner's user ID)",
			   summary = "Get details of a scene")	
	@GetMapping("/user/{userName}/scene/{sceneId}")
	public SceneData retrieveSceneById(@PathVariable String userName, @PathVariable Long sceneId) {
		log.info("Retrieving scene with user name={} and scene ID={}", userName, sceneId);
		return homeVideoLogService.retrieveSceneById(userName, sceneId);		
	}
	
	
//* Add a new scene

	@Tag(name = "12. Scene", description = "Scene operations")
	@Operation(description = "Adds a new scene to a given file " +
            " (must provide owner's user ID).  Included actors and subcategories are key-value pairs " +
			"of ID and name. Names are required but not used to update underlying instances.",
			   summary = "Add a scene to a file")
	@PostMapping("/user/{userName}/file/{fileId}/scene")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SceneData insertScene(@PathVariable String userName
								,@PathVariable Long fileId
				                ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
					                	content = @io.swagger.v3.oas.annotations.media.Content(
					                			examples = {
					                				@ExampleObject(
				value = "{\"sceneTitle\": \"Harold Takes a Spill\", \"sceneSummary\": \"Harold goes too fast and hits a tree.\", \"sceneStartTime\": \"13:34:15\", \"sceneEndTime\": \"13:46:27\", \"sceneLengthSec\": 732, \"sceneRating\": 2, \"location\": {\"locationId\": 6}, \"actors\": {\"2\": \"Not used\"}, \"subjectSubcategories\": {\"4\": \"Not used\"}}"
				)  }))					               
								 @RequestBody SceneData sceneData) {
		log.info("Creating scene {} for file with ID={}", sceneData, fileId);
		return homeVideoLogService.saveScene(userName
										   , fileId
										   , sceneData.getLocation().getLocationId()
										   , sceneData);
		
	}
	
	
//* Update a scene

	@Tag(name = "12. Scene", description = "Scene operations")
	@Operation(description = "Updates a scene from a given file " +
            " (must provide owner's user ID).  Included actors and subcategories are key-value pairs " +
			"of ID and name.  Names are required but not used to update the underlying instances.",
			   summary = "Update a scene from a given file")	
	@PutMapping("/user/{userName}/file/{fileId}/scene/{sceneId}")
	public SceneData updateScene(@PathVariable String userName
								,@PathVariable Long fileId
								,@PathVariable Long sceneId
				                ,@io.swagger.v3.oas.annotations.parameters.RequestBody(
					                	content = @io.swagger.v3.oas.annotations.media.Content(
					                			examples = {
					                				@ExampleObject(
				value = "{\"sceneTitle\": \"Harold Takes a Spill\", \"sceneSummary\": \"Harold goes too fast and hits a tree.\", \"sceneStartTime\": \"13:34:15\", \"sceneEndTime\": \"13:46:27\", \"sceneLengthSec\": 732, \"sceneRating\": 2, \"location\": {\"locationId\": 6}, \"actors\": {\"2\": \"Not used\"}, \"subjectSubcategories\": {\"4\": \"Not used\"}}"
				)  }))					               
								 @RequestBody SceneData sceneData) {
		sceneData.setSceneId(sceneId);
		log.info("Updating scene {}", sceneData);
		return homeVideoLogService.saveScene(userName
										   , fileId
										   , sceneData.getLocation().getLocationId()
										   , sceneData);		
	}
	
	
//* Delete a scene

	@Tag(name = "12. Scene", description = "Scene operations")
	@Operation(description = "Deletes a scene (must provide owner's user ID)",
			   summary = "Delete a scene")		
	@DeleteMapping("/user/{userName}/scene/{sceneId}")
	public Map<String, String> deleteSceneByID(@PathVariable String userName
			                                 , @PathVariable Long sceneId) {
		log.info("Deleting scene with ID={}", sceneId);
		
		homeVideoLogService.deleteSceneById(userName, sceneId);
		
		return Map.of("message", "Deletion of scene with ID=" + sceneId
				+ " was succesful.");
	}

}
