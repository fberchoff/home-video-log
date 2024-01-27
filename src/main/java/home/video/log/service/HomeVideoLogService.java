package home.video.log.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import home.video.log.dao.ActorDao;
import home.video.log.dao.AudioFormatDao;
import home.video.log.dao.FileDao;
import home.video.log.dao.FolderDao;
import home.video.log.dao.LocationDao;
import home.video.log.dao.LocationTypeDao;
import home.video.log.dao.ProjectDao;
import home.video.log.dao.SceneDao;
import home.video.log.dao.StorageLocationDao;
import home.video.log.dao.SubjectCategoryDao;
import home.video.log.dao.SubjectSubcategoryDao;
import home.video.log.dao.UserDao;
import home.video.log.dao.VideoFormatDao;
import home.video.log.dao.VideoScanTypeDao;
import home.video.log.entity.Actor;
import home.video.log.entity.AudioFormat;
import home.video.log.entity.File;
import home.video.log.entity.Folder;
import home.video.log.entity.Location;
import home.video.log.entity.LocationType;
import home.video.log.entity.Project;
import home.video.log.entity.Role;
import home.video.log.entity.Scene;
import home.video.log.entity.StorageLocation;
import home.video.log.entity.SubjectCategory;
import home.video.log.entity.SubjectSubcategory;
import home.video.log.entity.User;
import home.video.log.entity.VideoFormat;
import home.video.log.entity.VideoScanType;
import lombok.Data;

@Service
public class HomeVideoLogService {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private FolderDao folderDao;
	
	@Autowired
	private AudioFormatDao audioFormatDao;
	
	@Autowired
	private VideoScanTypeDao videoScanTypeDao;
	
	@Autowired
	private VideoFormatDao videoFormatDao;
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private LocationTypeDao locationTypeDao;
	
	@Autowired
	private ActorDao actorDao;
	
	@Autowired
	private SubjectSubcategoryDao subjectSubcategoryDao;
	
	@Autowired
	private SceneDao sceneDao;
	
	@Autowired
	private StorageLocationDao storageLocationDao;
	
	@Autowired
	private SubjectCategoryDao subjectCategoryDao;
	
	
	//* Retrieve a user
	
	/* If the user is not an ADMIN, they can only see their own user details. The logged in
	 * user name will be compared to the user name on the request.  If they are different,
	 * an exception will be thrown.  The exception is purposely vague ("No data found")
	 * because we don't want the potentially malicious user to even know if a user with the
	 * name they requested even exists.
	 */	
		@Transactional(readOnly = true)
		public UserData retrieveUserByName(String userName) {
			AuthUser authUser = new AuthUser();
			if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
				User user = findUserByName(userName);
				return new UserData(user);
			}
			else {
				throw new NoSuchElementException("No data found.");
			}
		}
		
		
	//* Retrieve all projects for a user
		
	/* If the user is not an ADMIN, they can only see their own projects. The logged in
	 * user name will be compared to the user name on the request.  If they are different,
	 * an exception will be thrown.  The exception is purposely vague ("No data found")
	 * because we don't want the potentially malicious user to even know if a user with the
	 * name they requested even exists.
	 */
	@Transactional(readOnly = true)
	public List<ProjectData> retrieveAllProjectsByUserName(String userName) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			List<Project> projects =
					projectDao.findAllByUserName(userName);
			List<ProjectData> response = new LinkedList<>();
			
			for (Project project : projects) {
				ProjectData pd = new ProjectData(project);
				response.add(pd);
			}
			return response;
		}
		else {
			throw new NoSuchElementException("No data found.");					
		}		
	}

	
	//* Save a project
	
	/* If the user is not an ADMIN, they can only add their own projects.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	*/
	@Transactional(readOnly = false)
	public ProjectData saveProject(String userName, ProjectData projectData) {
		
		AuthUser authUser = new AuthUser();
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			User user = findUserByName(userName);
			
			Project project = findOrCreateProject(projectData.getProjectId());
			setProjectFields(project, projectData);
			
			project.setUser(user);
			
			Project dbProject = projectDao.save(project);
			
			return new ProjectData(dbProject);			
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
					                         + " does not exist.");
		}
		
	}
	
	private Project findOrCreateProject(Long projectId) {
		
		Project project;
		
		if (Objects.isNull(projectId)) {
			project = new Project();
		}
		else {
			project = findProjectById(projectId);
		}
		
		return project;
	}
	
	private void setProjectFields(Project project, ProjectData projectData) {
		project.setProjectName(projectData.getProjectName());
	}
	
	
	//* Retrieve all storage locations for a project
	
	/* If the user is not an ADMIN, they can only see their own project and storage locations.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */	
	@Transactional(readOnly = true)
	public List<StorageLocationData> retrieveAllStorageLocationsByUserNameAndProjectId
												(String userName, Long projectId) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			List<StorageLocation> storageLocations =
					storageLocationDao.findAllByUserNameAndProjectId(userName, projectId);
			List<StorageLocationData> response = new LinkedList<>();
			
			for (StorageLocation storageLocation : storageLocations) {
				StorageLocationData sld = new StorageLocationData(storageLocation);
				response.add(sld);
			}
			return response;
		}
		else {
			throw new NoSuchElementException("No data found.");
			}
	}

	
	//* Retrieve a storage location
	
	/* If the user is not an ADMIN, they can only see their own storage locations.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */
	@Transactional (readOnly=true)
	public StorageLocationData retrieveStorageLocationById(String userName
														 , Long storageLocationId) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			// First let's validate that the provided user exists
			findUserByName(userName);
			
			StorageLocation storageLocation = findStorageLocationById(storageLocationId);
			
			if (!storageLocation.getProject().getUser().getUsername().equals(userName)) {
				throw new IllegalStateException("Storage location with ID=" + storageLocationId +
						" is not owned by user with name=" + userName);				
			}
			return new StorageLocationData(storageLocation);			
		}
		else {
			throw new NoSuchElementException("No data found.");
		}
	}
	
	
	//* Save a storage location

	/* If the user is not an ADMIN, they can only add their own storage locations.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	*/
	@Transactional (readOnly = false)
	public StorageLocationData saveStorageLocation(String userName, Long projectId,
			  StorageLocationData storageLocationData) {

		AuthUser authUser = new AuthUser();

		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			Project project = findProjectById(projectId);

			if (project.getUser().getUsername().equals(userName)) {
				
				StorageLocation storageLocation = findOrCreateStorageLocation(storageLocationData.getStorageLocationId());
				setStorageLocationFields(storageLocation, storageLocationData);
				
				storageLocation.setProject(project);
				project.getStorageLocations().add(storageLocation);
				
				StorageLocation dbStorageLocation = storageLocationDao.save(storageLocation);
				
				return new StorageLocationData(dbStorageLocation);
			}
			else {
				throw new
				NoSuchElementException("Project for User= " + userName +
						" and ID=" + projectId + " was not found.");
			}			
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
                    + " does not exist.");
		}		
	}
	
	private StorageLocation findOrCreateStorageLocation (Long storageLocationId) {
		
		StorageLocation storageLocation;
		
		if (Objects.isNull(storageLocationId)) {
			storageLocation = new StorageLocation();
		}
		else {
			storageLocation = findStorageLocationById(storageLocationId);
		}
		
		return storageLocation;
	}
	
	private void setStorageLocationFields(StorageLocation storageLocation, StorageLocationData storageLocationData) {
		storageLocation.setStorageLocationName(storageLocationData.getStorageLocationName());
		storageLocation.setStorageLocationPath(storageLocationData.getStorageLocationPath());
	}
	
	
	//* Save a folder
	
	/* If the user is not an ADMIN, they can only add their own folders.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */	
	@Transactional (readOnly = false)
	public FolderData saveFolder(String userName, Long storageLocationId, FolderData folderData) {
		
		AuthUser authUser = new AuthUser();
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			StorageLocation storageLocation = findStorageLocationById(storageLocationId);
			
			if (storageLocation.getProject().getUser().getUsername().equals(userName)) {
				
				Folder folder = findOrCreateFolder(folderData.getFolderId());
				setFolderFields(folder, folderData);
				
				folder.setStorageLocation(storageLocation);
				storageLocation.getFolders().add(folder);
				
				Folder dbFolder = folderDao.save(folder);
				
				return new FolderData(dbFolder);
			}
			else {
				throw new 
				  NoSuchElementException("Storage location for User= " + userName +
						  				" and ID=" + storageLocationId + " was not found.");				
			}
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
                    + " does not exist.");			
		}		
	}
	
	private Folder findOrCreateFolder (Long folderId) {
		
		Folder folder;
		
		if (Objects.isNull(folderId)) {
			folder = new Folder();
		}
		else {
			folder = findFolderById(folderId);
		}
		
		return folder;
	}
	
	private void setFolderFields(Folder folder, FolderData folderData) {
		folder.setFolderName(folderData.getFolderName());
	}
	
	
	//* Retrieve a location type
	
	/* If the user is not an ADMIN, they can only see their own location types.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */	
	@Transactional (readOnly=true)
	public LocationTypeData retrieveLocationTypeById(String userName
	                                               , Long locationTypeId) {
	    AuthUser authUser = new AuthUser();
	    if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
	    	// First let's validate that the provided user exists
	        findUserByName(userName);
	        
	        LocationType locationType = findLocationTypeById(locationTypeId);
	        
	        if (!locationType.getProject().getUser().getUsername().equals(userName)) {
	        	throw new IllegalStateException("Location Type with ID=" + locationTypeId +
						" is not owned by user with name=" + userName);
	        }
	        return new LocationTypeData(locationType);
	    }
	    else {
	    	throw new NoSuchElementException("No data found.");
	    }	                                            	   
	}
	
	
	//* Save a location type

	/* If the user is not an ADMIN, they can only add their own location types.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	*/
	@Transactional (readOnly = false)
	public LocationTypeData saveLocationType(String userName, Long projectId
			                                   , LocationTypeData locationTypeData) {

		AuthUser authUser = new AuthUser();

		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			Project project = findProjectById(projectId);

			if (project.getUser().getUsername().equals(userName)) {
				
				LocationType locationType
					= findOrCreateLocationType(locationTypeData.getLocationTypeId());
				setLocationTypeFields(locationType, locationTypeData);
				
				locationType.setProject(project);
				project.getLocationTypes().add(locationType);
				
				LocationType dbLocationType = locationTypeDao.save(locationType);
				
				return new LocationTypeData(dbLocationType);
			}
			else {
				throw new
					NoSuchElementException("Project for User= " + userName +
						  " and ID=" + projectId + " was not found.");				
			}
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
                    + " does not exist.");			
		}		
	}
	
	private LocationType findOrCreateLocationType (Long locationTypeId) {
		
		LocationType locationType;
		
		if (Objects.isNull(locationTypeId)) {
			locationType = new LocationType();
		}
		else {
			locationType = findLocationTypeById(locationTypeId);
		}
		
		return locationType;
	}
	
	private void setLocationTypeFields(LocationType locationType, LocationTypeData locationTypeData) {
		locationType.setLocationTypeName(locationTypeData.getLocationTypeName());
	}	

	
	//* Save a location
	
	/* If the user is not an ADMIN, they can only add their own locations.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	*/	
	@Transactional (readOnly = false)
	public LocationData saveLocation(String userName, Long locationTypeId, LocationData locationData) {
		
		AuthUser authUser = new AuthUser();
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			LocationType locationType = findLocationTypeById(locationTypeId);
			
			if (locationType.getProject().getUser().getUsername().equals(userName)) {
				
				Location location = findOrCreateLocation(locationData.getLocationId());
				setLocationFields(location, locationData);
				
				location.setLocationType(locationType);
				locationType.getLocations().add(location);
				
				Location dbLocation = locationDao.save(location);
				
				return new LocationData(dbLocation);
			}
			else {
				throw new 
				  NoSuchElementException("Location type for User= " + userName +
						  				" and ID=" + locationTypeId + " was not found.");				
			}
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
                    + " does not exist.");			
		}		
	}
	
	private Location findOrCreateLocation (Long locationId) {
		
		Location location;
		
		if (Objects.isNull(locationId)) {
			location = new Location();
		}
		else {
			location = findLocationById(locationId);
		}
		
		return location;
	}
	
	private void setLocationFields(Location location, LocationData locationData) {
		location.setLocationName(locationData.getLocationName());
		location.setLocationStreet(locationData.getLocationStreet());
		location.setLocationCity(locationData.getLocationCity());
		location.setLocationState(locationData.getLocationState());
		location.setLocationZip(locationData.getLocationZip());
	}
	

	//* Retrieve a subject category
	
	/* If the user is not an ADMIN, they can only see their own subject categories.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */
	@Transactional (readOnly=true)
	public SubjectCategoryData retrieveSubjectCategoryById(String userName
														 , Long subjectCategoryId) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			// First let's validate that the provided user exists
			findUserByName(userName);
			
			SubjectCategory subjectCategory = findSubjectCategoryById(subjectCategoryId);
			
			if (!subjectCategory.getProject().getUser().getUsername().equals(userName)) {
				throw new IllegalStateException("Subject category with ID=" + subjectCategoryId +
						" is not owned by user with name=" + userName);				
			}
			return new SubjectCategoryData(subjectCategory);			
		}
		else {
			throw new NoSuchElementException("No data found.");
		}		
	}
	
	
	//* Save a subject category

	/* If the user is not an ADMIN, they can only add subject categories to their own projects.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	 */
	@Transactional (readOnly = false)
	public SubjectCategoryData saveSubjectCategory(String userName, Long projectId,
			SubjectCategoryData subjectCategoryData) {

		AuthUser authUser = new AuthUser();

		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			Project project = findProjectById(projectId);

			if (project.getUser().getUsername().equals(userName)) {

				SubjectCategory subjectCategory 
						= findOrCreateSubjectCategory(subjectCategoryData.getSubjectCategoryId());
				setSubjectCategoryFields(subjectCategory, subjectCategoryData);

				subjectCategory.setProject(project);
				project.getSubjectCategories().add(subjectCategory);

				SubjectCategory dbSubjectCategory = subjectCategoryDao.save(subjectCategory);

				return new SubjectCategoryData(dbSubjectCategory);
			}
			else {
				throw new
					NoSuchElementException("Project for User= " + userName +
							" and ID=" + projectId + " was not found.");			                     
			}
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
                    + " does not exist.");				                     
		}		
	}

	private SubjectCategory findOrCreateSubjectCategory (Long subjectCategoryId) {

		SubjectCategory subjectCategory;

		if (Objects.isNull(subjectCategoryId)) {
			subjectCategory = new SubjectCategory();
		}
		else {
			subjectCategory = findSubjectCategoryById(subjectCategoryId);
		}

		return subjectCategory;
	}

	private void setSubjectCategoryFields(SubjectCategory subjectCategory,
							SubjectCategoryData subjectCategoryData) {
		subjectCategory.setSubjectCategoryName(subjectCategoryData.getSubjectCategoryName());
	}	
	
	
	//* Retrieve a folder
	
	/* If the user is not an ADMIN, they can only see their own folders.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */		
	@Transactional (readOnly = true)
	public FolderData retrieveFolderById(String userName, Long folderId) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			// First let's validate that the provided user exists
			findUserByName(userName);
			
			Folder folder = findFolderById(folderId);
			
			if (!folder.getStorageLocation().getProject().getUser().getUsername().equals(userName)) {
				throw new IllegalStateException("Folder with ID=" + folderId +
						" is not owned by user with name=" + userName);
			}
			return new FolderData(folder);
		}
		else {
			throw new NoSuchElementException("No data found.");
		}
	}
	
	
	//* Retrieve a file

	/* If the user is not an ADMIN, they can only see their own files.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */	
	@Transactional(readOnly = true)
	public FileData retrieveFileById(String userName, Long fileId) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {		
			// First let's validate that the provided user exists
			findUserByName(userName);
		
			File file = findFileById(fileId);
		
			if (!file.getFolder()
					 .getStorageLocation()
					 .getProject()
					 .getUser()
					 .getUsername().equals(userName)) {
				throw new IllegalStateException("File with ID=" + fileId +
						" is not owned by user with name=" + userName);
			}		
			return new FileData(file);
		}
		else {
			throw new NoSuchElementException("No data found.");
		}
	}
	
	
	//* Retrieve all files for a project

	/* If the user is not an ADMIN, they can only see their own project and files.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */	
	@Transactional(readOnly = true)
	public List<FileData> retrieveAllFilesByUserNameAndProjectId(String userName, Long projectId) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {		
			List<File> files = 
					fileDao.findAllByUserNameAndProjectId(userName, projectId);
			List<FileData> response = new LinkedList<>();
		
			for (File file : files) {
				FileData fd = new FileData(file);
				response.add(fd);
			}				
			return response;
		}
		else {
			throw new NoSuchElementException("No data found.");
			}		
	}

	
	//* Save a file
	
	/* If the user is not an ADMIN, they can only add files to their own projects.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	 * 
	 * If the logged in user is an ADMIN or the same user that is on the request, we will check
	 * to make sure that the folder they want to add the file to belongs to them. If the folder
	 * belongs to someone other than the requested user, we will report that the folder was not found.
	 */	
	@Transactional(readOnly = false)
	public FileData saveFile(String userName, Long folderId,
			                 Long audioFormatId, Long videoFormatId, FileData fileData) {
		
		AuthUser authUser = new AuthUser();
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			Folder folder = findFolderById(folderId);
			
			if (folder.getStorageLocation()
					  .getProject()
					  .getUser()
					  .getUsername()
					  .equals(userName)) {
				AudioFormat audioFormat = findAudioFormatById(audioFormatId);
				VideoFormat videoFormat = findVideoFormatById(videoFormatId);
			
				File file = findOrCreateFile(fileData.getFileId());
				setFileFields(file, fileData);
			
				file.setFolder(folder);
				folder.getFiles().add(file);
			
				file.setAudioFormat(audioFormat);
				audioFormat.getFiles().add(file);
			
				file.setVideoFormat(videoFormat);
				videoFormat.getFiles().add(file);
			
				File dbFile = fileDao.save(file);			
			
				return new FileData(dbFile);
			}
			else {
				throw new 
				  NoSuchElementException("Folder for User= " + userName + 
						                     " and ID=" + folderId + " was not found.");
			}
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
					                         + " does not exist.");
		}
		
	}	
	
	private File findOrCreateFile(Long fileId) {
		
		File file;
		
		if (Objects.isNull(fileId)) {
			file = new File();
		}
		else {
			file = findFileById(fileId);
		}		
		
		return file;
	}

	private void setFileFields(File file, FileData fileData) {
		file.setFileName(fileData.getFileName());
		file.setCreateDatetime(fileData.getCreateDatetime());
		file.setFileSizeMb(fileData.getFileSizeMb());
		file.setVideoBitRateKbps(fileData.getVideoBitRateKbps());
		file.setAudioBitRateKbps(fileData.getAudioBitRateKbps());
		file.setFrameRateFps(fileData.getFrameRateFps());		
	}

	
	//* Delete a file
	
	/* If the user is not an ADMIN, they can only delete their own files.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */		
	@Transactional (readOnly = false)
	public void deleteFileById(String userName, Long fileId) {
		AuthUser authUser = new AuthUser();
		File file = findFileById(fileId);
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {		
			// First let's validate that the provided user exists
			findUserByName(userName);
		
			if (!file.getFolder().getStorageLocation().getProject().getUser().getUsername().equals(userName)) {
				throw new IllegalStateException("File with ID=" + fileId +
						" is not owned by user with name=" + userName);
			}
		}
		else {
			throw new NoSuchElementException("No data found.");
		}
		
		fileDao.delete(file);		
	}
	
	
	//* Retrieve a scene

	/* If the user is not an ADMIN, they can only see their own scenes.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */
	@Transactional (readOnly = true)
	public SceneData retrieveSceneById(String userName, Long sceneId) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {		
			// First let's validate that the provided user exists
			findUserByName(userName);
			
			Scene scene = findSceneById(sceneId);
			
			if (!scene.getLocation()
					  .getLocationType()
					  .getProject()
					  .getUser()
					  .getUsername().equals(userName)) {
				throw new IllegalStateException("Scene with ID=" + sceneId +
						" is not owned by user with name=" + userName);				
			}
			return new SceneData(scene);
		}
		else {
			throw new NoSuchElementException("No data found.");
		}
		
	}
	
	
	//* Save a scene

	/* If the user is not an ADMIN, they can only add scenes to their own projects.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	 * 
	 * If the logged in user is an ADMIN or the same user that is on the request, we will check
	 * to make sure that the file they want to add the scene to belongs to them. If the file
	 * belongs to someone other than the requested user, we will report that the file was not found.
	 */	
	@Transactional(readOnly = false)	
	public SceneData saveScene(String userName, Long fileId, Long locationId
			                    , SceneData sceneData) {
		
		AuthUser authUser = new AuthUser();
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			File file = findFileById(fileId);
			
			Project project = file.getFolder().getStorageLocation().getProject();
			
			if (project
					.getUser()
					.getUsername()
					.equals(userName)) {
				Location location = findLocationById(locationId);
	
				/* We need to make sure that the given location is under the same project
				 * as the file for which we are adding or updating a scene
				 */
				if (!location.getLocationType()
						     .getProject()
						     .getProjectId()
						     .equals(project.getProjectId())) {
					throw new NoSuchElementException("Location with ID=" + locationId +
	                        " was not found for project with ID= " + project.getProjectId() + ".");
				}
				
				/* We need to make sure that the given actors are all under the same project
				 * as the file for which we are adding or updating a scene
				 */
				Set<Actor> actors = actorDao.findAllByActorIdIn(sceneData.getActors().keySet());
				for (Actor actor : actors) {
					if (!actor.getProject().getProjectId().equals(project.getProjectId())) {
						throw new NoSuchElementException("Actor with ID=" + actor.getActorId() +
								" was not found for project with ID= " + project.getProjectId() + ".");
					}
				}
				
				/* We need to make sure that the given subject sub categories are all under the same
				 * project as the file for which we are adding or updating a scene
				 */
				Set<SubjectSubcategory> subjectSubcategories = 
						                       subjectSubcategoryDao
						                      .findAllBySubjectSubcategoryIdIn(sceneData.getSubjectSubcategories()
						                      .keySet());
				for (SubjectSubcategory subjectSubcategory : subjectSubcategories) {
					if (!subjectSubcategory.getSubjectCategory().getProject().getProjectId()
							.equals(project.getProjectId())) {
						throw new NoSuchElementException("Subject Subcategory with ID=" +
															subjectSubcategory.getSubjectSubcategoryId() +
								" was not found for project with ID= " + project.getProjectId() + ".");
					}
				}
				
				Scene scene = findOrCreateScene(project.getProjectId(), sceneData.getSceneId());
				setSceneFields(scene, sceneData);
				
				scene.setFile(file);
				file.getScenes().add(scene);
				
				scene.setLocation(location);
				location.getScenes().add(scene);
				
				for (Actor actor : actors) {
					actor.getScenes().add(scene);
					scene.getActors().add(actor);
				}
				
				for (SubjectSubcategory subjectSubcategory : subjectSubcategories) {
					subjectSubcategory.getScenes().add(scene);
					scene.getSubjectSubcategories().add(subjectSubcategory);
				}
				
				Scene dbScene = sceneDao.save(scene);
				
				return new SceneData(dbScene);
				
			}
			else {
				throw new
				  NoSuchElementException("File for User= " + userName +
						  					" and ID=" + fileId + " was not found.");				                     
			}
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
					                         + " does not exist.");
		}
	}

	private Scene findOrCreateScene(Long projectId, Long sceneId) {
	
		Scene scene;
		
		if (Objects.isNull(sceneId)) {
			scene = new Scene();
		}
		else {
			scene = findSceneById(sceneId);
			if (!projectId.equals(scene.getLocation().getLocationType().getProject().getProjectId())) {
				throw new
				  NoSuchElementException("Scene with ID=" + sceneId + " was not found.");
				
			}
		}
		
		return scene;
	}

	private void setSceneFields(Scene scene, SceneData sceneData) {
		scene.setSceneTitle(sceneData.getSceneTitle());
		scene.setSceneSummary(sceneData.getSceneSummary());
		scene.setSceneStartTime(sceneData.getSceneStartTime());
		scene.setSceneEndTime(sceneData.getSceneEndTime());
		scene.setSceneLengthSec(sceneData.getSceneLengthSec());
		scene.setSceneRating(sceneData.getSceneRating());		
	}

	
	//* Delete a scene
	
	/* If the user is not an ADMIN, they can only delete their own scenes.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */		
	@Transactional (readOnly = false)
	public void deleteSceneById(String userName, Long sceneId) {
		AuthUser authUser = new AuthUser();
		Scene scene = findSceneById(sceneId);
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {		
			// First let's validate that the provided user exists
			findUserByName(userName);
			
			if (!scene.getLocation().getLocationType().getProject().getUser().getUsername().equals(userName)) {
				throw new IllegalStateException("Scene with ID=" + sceneId +
						" is not owned by user with name=" + userName);
			}		
		}
		else {
			throw new NoSuchElementException("No data found.");
		}
		
		sceneDao.delete(scene);
	}
	
	
	//* Retrieve an actor

	/* If the user is not an ADMIN, they can only see their own actors.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */	
	@Transactional(readOnly = true)
	public ActorData retrieveActorById(String userName, Long actorId) {
		AuthUser authUser = new AuthUser();
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {		
			// First let's validate that the provided user exists
			findUserByName(userName);
			
			Actor actor = findActorById(actorId);
			
			if (!actor.getProject().getUser().getUsername().equals(userName)) {
				throw new IllegalStateException("Actor with ID=" + actorId +
						" is not owned by user with name=" + userName);				
			}
			return new ActorData(actor);
		}
		else {
			throw new NoSuchElementException("No data found.");
		}
	}
	
	
	//* Save an actor

	/* If the user is not an ADMIN, they can only add actors to their own projects.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	 * 
	 * If the logged in user is an ADMIN or the same user that is on the request, we will check
	 * to make sure that the project they want to add the actor to belongs to them. If the project
	 * belongs to someone other than the requested user, we will report that the project was 
	 * not found.
	 */
	@Transactional (readOnly = false)
	public ActorData saveActor(String userName, Long projectId, ActorData actorData) {
		
		AuthUser authUser = new AuthUser();
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			Project project = findProjectById(projectId);
			
			if (project.getUser().getUsername().equals(userName)) {
				
				/* We need to make sure that the given scenes are all under the same project
				 * as the actor that we are adding or updating
				 */
				Set<Scene> scenes = sceneDao.findAllBySceneIdIn(actorData.getScenes().keySet());
				for (Scene scene : scenes) {
					if (!scene.getLocation()
						.getLocationType()
						.getProject()
						.getProjectId().equals(project.getProjectId())) {
						throw new NoSuchElementException("Scene with ID=" + scene.getSceneId() +
								" was not found for project with ID= " +
												project.getProjectId() + "."); 
						
					}
				}
				
				Actor actor = findOrCreateActor(actorData.getActorId());
				setActorFields(actor, actorData);
				
				actor.setProject(project);
				project.getActors().add(actor);
				
				for (Scene scene : scenes) {
					scene.getActors().add(actor);
					actor.getScenes().add(scene);
				}
				
				Actor dbActor = actorDao.save(actor);
				
				return new ActorData(dbActor);
			}
			else {
				throw new
				  NoSuchElementException("Project for User= " + userName +
						  					" and ID=" + projectId + " was not found.");				                     
			}			
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
					                         + " does not exist.");
		}		
	}

	private Actor findOrCreateActor (Long actorId) {
		
		Actor actor;
		
		if (Objects.isNull(actorId)) {
			actor = new Actor();
		}
		else {
			actor = findActorById(actorId);
		}
		
		return actor;
	}

	private void setActorFields(Actor actor, ActorData actorData) {
		actor.setActorFirstName(actorData.getActorFirstName());
		actor.setActorLastName(actorData.getActorLastName());
		actor.setActorBirthDate(actorData.getActorBirthDate());
	}
	
	
	//* Delete an actor

	/* If the user is not an ADMIN, they can only delete their own actors.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely vague
	 * ("No data found") because we don't want the potentially malicious user to even know
	 * if a user with the name they requested even exists.
	 */		
	@Transactional (readOnly = false)
	public void deleteActorById(String userName, Long actorId) {
		AuthUser authUser = new AuthUser();
		Actor actor = findActorById(actorId);
	
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {		
			// First let's validate that the provided user exists
			findUserByName(userName);
			
			if (!actor.getProject().getUser().getUsername().equals(userName)) {
				throw new IllegalStateException("Actor with ID=" + actorId +
						" is not owned by user with name=" + userName);			
			}		
		}
		else {
			throw new NoSuchElementException("No data found.");
		}
		
		actorDao.delete(actor);
	}
	
	
	//* Save subject subcategory
	
	/* If the user is not an ADMIN, they can only add subcategories to their own categories.
	 * The logged in user name will be compared to the user name of the request.  If they
	 * are different, an exception will be thrown.  The exception is purposely misleading
	 * ("User with ID does not exist") because we don't want the potentially malicious user
	 * to even know if a user with the name they requested even exists.
	 * 
	 * If the logged in user is an ADMIN or the same user that is on the request, we will check
	 * to make sure that the category they want to add the subcategory to belongs to them. If the
	 * category belongs to someone other than the requested user, we will report that the category was 
	 * not found.
	 */	
	@Transactional(readOnly = false)
	public SubjectSubcategoryData saveSubjectSubcategory(String userName, Long subjectCategoryId
													     ,SubjectSubcategoryData subjectSubcategoryData) {
		
		AuthUser authUser = new AuthUser();
		
		if (userName.equals(authUser.getUserName()) || authUser.userIsAdmin) {
			SubjectCategory subjectCategory = findSubjectCategoryById(subjectCategoryId);
			
			Project project = subjectCategory.getProject();
			
			if (project.getUser().getUsername().equals(userName)) {
				
				/* We need to make sure that the given scenes are all under the same project
				 * as the subject subcategory that we are adding or updating
				 */
				Set<Scene> scenes = sceneDao.findAllBySceneIdIn(subjectSubcategoryData.getScenes().keySet());
				for (Scene scene : scenes) {
					if (!scene.getLocation()
						.getLocationType()
						.getProject()
						.getProjectId().equals(project.getProjectId())) {
						throw new NoSuchElementException("Scene with ID=" + scene.getSceneId() +
								" was not found for project with ID= " +
												project.getProjectId() + "."); 
						
					}				
				}
				
				SubjectSubcategory subjectSubcategory 
						= findOrCreateSubjectSubcategory(subjectSubcategoryData.getSubjectSubcategoryId());
				setSubjectSubcategoryFields(subjectSubcategory, subjectSubcategoryData);
				
				subjectSubcategory.setSubjectCategory(subjectCategory);
				subjectCategory.getSubjectSubCategories().add(subjectSubcategory);
				
				for (Scene scene : scenes) {
					scene.getSubjectSubcategories().add(subjectSubcategory);
					subjectSubcategory.getScenes().add(scene);			
				}
				
				SubjectSubcategory dbSubjectSubcategory = subjectSubcategoryDao.save(subjectSubcategory);
				
				return new SubjectSubcategoryData(dbSubjectSubcategory);
			}
			else {
				throw new 
				  NoSuchElementException("Subject category for User= " + userName + 
						                     " and ID=" + subjectCategoryId + " was not found.");
			}
		}
		else {
			throw new IllegalStateException("User with ID= " + userName
					                         + " does not exist.");
		}		
	}
	
	private SubjectSubcategory findOrCreateSubjectSubcategory (Long subjectSubcategoryId) {
		
		SubjectSubcategory subjectSubcategory;
		
		if (Objects.isNull(subjectSubcategoryId)) {
			subjectSubcategory = new SubjectSubcategory();
		}
		else {
			subjectSubcategory = findSubjectSubcategoryById(subjectSubcategoryId);
		}
		
		return subjectSubcategory;
	}
	
	private void setSubjectSubcategoryFields (SubjectSubcategory subjectSubcategory
										, SubjectSubcategoryData subjectSubcategoryData) {
		subjectSubcategory.setSubjectSubcategoryName(subjectSubcategoryData.getSubjectSubcategoryName());
	}
	
	
	//* Save a video scan type
	
	/* Only an ADMIN can add video scan types.  If the user is not an ADMIN, the
	 * request will be rejected.
	*/
	@Transactional (readOnly = false)
	public VideoScanTypeData saveVideoScanType(VideoScanTypeData videoScanTypeData) {
		
		AuthUser authUser = new AuthUser();
		
		if (authUser.userIsAdmin) {
			
			VideoScanType videoScanType = findOrCreateVideoScanType(videoScanTypeData.getVideoScanTypeId());
			setVideoScanTypeFields(videoScanType, videoScanTypeData);
			
			VideoScanType dbVideoScanType =  videoScanTypeDao.save(videoScanType);
			
			return new VideoScanTypeData(dbVideoScanType);
		}
		else {
			throw new AccessDeniedException("Request is denied");
		}		
	}
	
	private VideoScanType findOrCreateVideoScanType(Long videoScanTypeId) {
		
		VideoScanType videoScanType;
		
		if (Objects.isNull(videoScanTypeId)) {
			videoScanType = new VideoScanType();
		}
		else {
			videoScanType = findVideoScanTypeById(videoScanTypeId);
		}
		
		return videoScanType;
	}
	
	private void setVideoScanTypeFields(VideoScanType videoScanType, VideoScanTypeData videoScanTypeData) {
		videoScanType.setVideoScanTypeName(videoScanTypeData.getVideoScanTypeName());
	}
	
	
	//* Save a video format
	
	/* Only an ADMIN can add video formats.  If the user is not an ADMIN, the
	 * request will be rejected.
	*/	
	@Transactional (readOnly = false)
	public VideoFormatData saveVideoFormat(Long videoScanTypeId, VideoFormatData videoFormatData) {
		
		AuthUser authUser = new AuthUser();
		
		if (authUser.userIsAdmin) {
			VideoScanType videoScanType = findVideoScanTypeById(videoScanTypeId);
			
			VideoFormat videoFormat = findOrCreateVideoFormat(videoFormatData.getVideoFormatId());
			setVideoFormatFields(videoFormat, videoFormatData);
			
			videoFormat.setVideoScanType(videoScanType);
			videoScanType.getVideoFormats().add(videoFormat);
			
			VideoFormat dbVideoFormat = videoFormatDao.save(videoFormat);
			
			return new VideoFormatData(dbVideoFormat);
		}
		else {
			throw new AccessDeniedException("Request is denied");
		}		
	}
	
	private VideoFormat findOrCreateVideoFormat(Long videoFormatId) {
		
		VideoFormat videoFormat;
		
		if (Objects.isNull(videoFormatId)) {
			videoFormat = new VideoFormat();
		}
		else {
			videoFormat = findVideoFormatById(videoFormatId);
		}
		
		return videoFormat;
	}
	
	private void setVideoFormatFields(VideoFormat videoFormat, VideoFormatData videoFormatData) {
		videoFormat.setVideoFormatName(videoFormatData.getVideoFormatName());
		videoFormat.setVideoType(videoFormatData.getVideoType());
		videoFormat.setFrameHeight(videoFormatData.getFrameHeight());
		videoFormat.setFrameWidth(videoFormatData.getFrameWidth());
		videoFormat.setVideoChannels(videoFormatData.getVideoChannels());
	}
	
	
	//* Save an audio format
	
	/* Only an ADMIN can add audio formats.  If the user is not an ADMIN, the
	 * request will be rejected.
	*/	
	@Transactional (readOnly = false)
	public AudioFormatData saveAudioFormat(AudioFormatData audioFormatData) {
		
		AuthUser authUser = new AuthUser();
		
		if (authUser.userIsAdmin) {
			
			AudioFormat audioFormat = findOrCreateAudioFormat(audioFormatData.getAudioFormatId());
			setAudioFormatFields(audioFormat, audioFormatData);
			
			AudioFormat dbAudioFormat = audioFormatDao.save(audioFormat);
			
			return new AudioFormatData(dbAudioFormat);
		}
		else {
			throw new AccessDeniedException("Request is denied");
		}		
	}
	
	private AudioFormat findOrCreateAudioFormat(Long audioFormatId) {
		
		AudioFormat audioFormat;
		
		if (Objects.isNull(audioFormatId)) {
			audioFormat = new AudioFormat();
		}
		else {
			audioFormat = findAudioFormatById(audioFormatId);
		}
		
		return audioFormat;
	}	
	
	private void setAudioFormatFields(AudioFormat audioFormat, AudioFormatData audioFormatData) {
		audioFormat.setAudioFormatName(audioFormatData.getAudioFormatName());
		audioFormat.setAudioChannels(audioFormatData.getAudioChannels());
		audioFormat.setAudioSampleRateHz(audioFormatData.getAudioSampleRateHz());
	}

	
	private User findUserByName(String userName) {
		return userDao.findByUserName(userName).orElseThrow(
				() -> new NoSuchElementException("User with Name=" + userName + " was not found."));
	}

	private Project findProjectById(Long projectId) {
		return projectDao.findById(projectId).orElseThrow(
				() -> new NoSuchElementException("Project with ID=" + projectId + 
																" was not found."));
	}
	
	private SubjectCategory findSubjectCategoryById(Long subjectCategoryId) {
		return subjectCategoryDao.findById(subjectCategoryId).orElseThrow(
				() -> new NoSuchElementException("Subject category with ID=" +
								subjectCategoryId + " was not found."));
	}
	
	private StorageLocation findStorageLocationById(Long storageLocationId) {
		return storageLocationDao.findById(storageLocationId).orElseThrow(
				() -> new NoSuchElementException("Storage location with ID=" +
								storageLocationId + " was not found."));
	}

	private Folder findFolderById(Long folderId) {
		return folderDao.findById(folderId).orElseThrow(
				() -> new NoSuchElementException("Folder with ID=" + folderId + " was not found."));
	}

	private File findFileById(Long fileId) {
		return fileDao.findById(fileId).orElseThrow(
				() -> new NoSuchElementException("File with ID=" + fileId + " was not found."));
	}

	private Location findLocationById(Long locationId) {
		return locationDao.findById(locationId).orElseThrow(
				() -> new NoSuchElementException("Location with ID=" + locationId 
	            		+ " was not found."));
	}
	
	private LocationType findLocationTypeById(Long locationTypeId) {
		return locationTypeDao.findById(locationTypeId).orElseThrow(
				() -> new NoSuchElementException("Location type with ID=" + locationTypeId 
						+ " was not found."));
	}

	private AudioFormat findAudioFormatById(Long audioFormatId) {
		return audioFormatDao.findById(audioFormatId).orElseThrow(
				() -> new NoSuchElementException("Audio format with ID=" + audioFormatId 
						                                                + " was not found."));
	}
	
	private VideoScanType findVideoScanTypeById(Long videoScanTypeId) {
		return videoScanTypeDao.findById(videoScanTypeId).orElseThrow(
				() -> new NoSuchElementException("Video Scan Type with ID=" + videoScanTypeId 
                        + " was not found."));
	}
	
	private VideoFormat findVideoFormatById(Long videoFormatId) {
		return videoFormatDao.findById(videoFormatId).orElseThrow(
				() -> new NoSuchElementException("Video format with ID=" + videoFormatId 
						                                                + " was not found."));
	}		

	private Scene findSceneById(Long sceneId) {
		return sceneDao.findById(sceneId).orElseThrow(
				() -> new NoSuchElementException("Scene with ID=" + sceneId 
																		+ " was not found."));
	}
	
	private Actor findActorById(Long actorId) {
		return actorDao.findById(actorId).orElseThrow(
				() -> new NoSuchElementException("Actor with ID=" + actorId 
																		+ " was not found."));
	}
	
	private SubjectSubcategory findSubjectSubcategoryById(Long subjectSubcategoryId) {
		return subjectSubcategoryDao.findById(subjectSubcategoryId).orElseThrow(
				() -> new NoSuchElementException("Subject subcategory with ID=" + subjectSubcategoryId 
						+ " was not found."));
	}

	
/* The following class represents the logged in user (authorized user) of the current
   execution thread.
*/
	@Data
	private class AuthUser {
		
		private String userName;		
		private boolean userIsAdmin;
		
		AuthUser() {			
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
			@SuppressWarnings("unchecked")
			List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
			String adminRole = Role.ADMIN.toString();
			
			userIsAdmin = authorities.stream().anyMatch(authority -> authority.getAuthority().equals(adminRole));
			
			userName = authentication.getName();			
		}		
		
	}
}
