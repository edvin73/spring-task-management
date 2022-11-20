package task.management.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import task.management.config.AppParam;
import task.management.dto.PriorityDTO;
import task.management.dto.TaskDTO;
import task.management.dto.UserDTO;
import task.management.error.ApiNotFoundException;
import task.management.model.Priority;
import task.management.model.Task;
import task.management.model.TaskFile;
import task.management.model.User;
import task.management.service.FileService;
import task.management.service.TaskService;

@RestController
@CrossOrigin(origins = {"http://edvin.com:4500", "http://localhost:4500"})
@RequestMapping(value = "/api/v1")
public class TaskController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private FileService fileService;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/tasks")
	public ResponseEntity<?> getAllTasks( ) {
		
		List<Task> tasks = taskService.getAllTasks();
		
		List<TaskDTO> tasksDTO = new ArrayList<>();
		
		tasks.forEach( t -> {
			
			TaskDTO dto = modelMapper.map(t, TaskDTO.class);
			dto.setAssignedTo(modelMapper.map(t.getAssignedTo(), UserDTO.class));
			dto.setTaskPriority(modelMapper.map(t.getTaskPriority(), PriorityDTO.class));
			
			tasksDTO.add(dto);
		});
		
		return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/tasks/{id}")
	public ResponseEntity<?> getTaskById( @PathVariable (name = "id") Integer id) {
		
		Task task = taskService.getTaskById(id);
		
		if(task==null) {
			throw new ApiNotFoundException(AppParam.NO_DATA_FOUND);
		}
		
		TaskDTO dto = modelMapper.map(task, TaskDTO.class);
		
		User assigned = task.getAssignedTo();
		UserDTO assignedDTO = modelMapper.map(assigned, UserDTO.class);
		dto.setAssignedTo(assignedDTO);
		
		Priority prio = task.getTaskPriority();
		PriorityDTO prioDTO = modelMapper.map(prio, PriorityDTO.class);
		dto.setTaskPriority(prioDTO);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping(value = "/tasks")
	public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
		
		logger.debug("taskDTO : {} ", taskDTO);
		
		if(!taskService.validateTaskDTO(taskDTO)) {
			throw new ApiNotFoundException(AppParam.MISSING_DATA);
		}
		
		Task task = modelMapper.map(taskDTO, Task.class);
		
		UserDTO assignedDTO = taskDTO.getAssignedTo();
		User assigned = modelMapper.map(assignedDTO, User.class);
		task.setAssignedTo(assigned);
		
		PriorityDTO prioDTO = taskDTO.getTaskPriority();
		Priority prio = modelMapper.map(prioDTO, Priority.class);
		task.setTaskPriority(prio);
		
		Task newTask = taskService.createTask(task);
		
		return new ResponseEntity<>(newTask, HttpStatus.OK);
	}
	
	@PutMapping(value = "/tasks/{id}")
	public ResponseEntity<?> modifyTask(@RequestBody TaskDTO taskDTO,
										@PathVariable (name = "id") Integer id) {
		
		logger.debug("taskDTO : {} ", taskDTO);
		
		if(!taskService.validateTaskDTO(taskDTO)) {
			throw new ApiNotFoundException(AppParam.MISSING_DATA);
		}
		
		Task task = modelMapper.map(taskDTO, Task.class);
		
		UserDTO assignedDTO = taskDTO.getAssignedTo();
		User assigned = modelMapper.map(assignedDTO, User.class);
		task.setAssignedTo(assigned);
		
		PriorityDTO prioDTO = taskDTO.getTaskPriority();
		Priority prio = modelMapper.map(prioDTO, Priority.class);
		task.setTaskPriority(prio);
		
		Task newTask = taskService.updateTask(id, task);
		
		return new ResponseEntity<>(newTask, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/tasks/{id}")
	public ResponseEntity<?> deleteTaskById(@PathVariable (name = "id") Integer id) {
		
		Task task = taskService.getTaskById(id);
		
		if(task == null) {
			throw new ApiNotFoundException(AppParam.NO_DATA_FOUND);
		}
		
		taskService.deleteTaskById(id);
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@GetMapping(value = "/prios")
	public ResponseEntity<?> getPrios( ) {
		
		List<Priority> prios = taskService.getAllPriorities();
		
		return new ResponseEntity<>(prios, HttpStatus.OK);
	}
	
	@GetMapping(value = "/prios/{id}")
	public ResponseEntity<?> getPrioById( @PathVariable (name = "id") Integer id) {
		
		Priority prio = taskService.getPriorityById(id);
		
		if(prio==null) {
			throw new ApiNotFoundException(AppParam.NO_DATA_FOUND);
		}
		
		return new ResponseEntity<>(prio, HttpStatus.OK);
	}
	
	@PostMapping(value = "/prios")
	public ResponseEntity<?> createPrio(@RequestBody PriorityDTO priorityDTO) {
		
		if(priorityDTO.getPriorityName().isEmpty()) {
			throw new ApiNotFoundException("Priority Name is missing");
		}
		
		if(priorityDTO.getPriorityCode().isEmpty()) {
			throw new ApiNotFoundException("Priority Code is missing");
		}
		
		Priority priority = modelMapper.map(priorityDTO, Priority.class);
		
		Priority prio = taskService.createPriority(priority);
		
		return new ResponseEntity<>(prio, HttpStatus.OK);
	}
	
	@PutMapping(value = "/prios/{id}")
	public ResponseEntity<?> updatePrio(@RequestBody PriorityDTO priorityDTO,  
										@PathVariable (name = "id") Integer id) {
		
		if(taskService.getPriorityById(id)==null) {
			throw new ApiNotFoundException(AppParam.NO_DATA_FOUND);
		}
		
		if(priorityDTO.getPriorityName().isEmpty()) {
			throw new ApiNotFoundException("Priority Name is missing");
		}
		
		if(priorityDTO.getPriorityCode().isEmpty()) {
			throw new ApiNotFoundException("Priority Code is missing");
		}
		
		Priority priority = modelMapper.map(priorityDTO, Priority.class);
		
		Priority prio = taskService.updatePriority(id, priority);
		
		return new ResponseEntity<>(prio, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/prios/{id}")
	public ResponseEntity<?> deletePrio(@PathVariable (name = "id") Integer id) {
		
		Priority prio = taskService.getPriorityById(id);
		
		if(prio==null) {
			throw new ApiNotFoundException(AppParam.NO_DATA_FOUND);
		} 
		
		taskService.deletePriorityById(id);
		
		return new ResponseEntity<>(prio, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users")
	public ResponseEntity<?> getUsers( ) {
		
		List<User> users = taskService.getUsers();
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(name = "id") Integer id) {
		
		User user = taskService.getUserById(id);
		
		if(user == null) {
			throw new ApiNotFoundException(AppParam.UNKNOWN_USER_MSG + id);
		}
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping(value = "/users")
	public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
		 
		
		if(userDTO.getFirstName().isEmpty()) {
			throw new ApiNotFoundException("First Name is missing");
		}
		
		if(userDTO.getLastName().isEmpty()) {
			throw new ApiNotFoundException("Last Name is missing");
		}
		
		User user = modelMapper.map(userDTO, User.class);
		
		User newUser = taskService.createUser(user);
				
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	
	@PutMapping(value = "/users/{id}")
	public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO, @PathVariable(name = "id") Integer id) {
		 
		
		if(taskService.getUserById(id)==null) {
			throw new ApiNotFoundException(AppParam.UNKNOWN_USER_MSG + id);
		}
		
		if(userDTO.getFirstName().isEmpty()) {
			throw new ApiNotFoundException("First Name is missing");
		}
		
		if(userDTO.getLastName().isEmpty()) {
			throw new ApiNotFoundException("Last Name is missing");
		}
		
		User user = modelMapper.map(userDTO, User.class);
		
		User newUser = taskService.updateUser(id, user);
				
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable(name = "id") Integer id) {
		
		User user = taskService.getUserById(id);
		
		if(user == null) {
			throw new ApiNotFoundException(AppParam.UNKNOWN_USER_MSG + id);
		}
		
		taskService.deleteUserById(id);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tasks/{taskId}/upload", method = RequestMethod.POST)
    public ResponseEntity<?> handleFileUpload(@PathVariable(name = "taskId") Integer taskId,
    											@RequestParam(value = "file") MultipartFile file) throws IOException {
        TaskFile taskFile = taskService.save(taskId, file);
        
        return new ResponseEntity<>(taskFile, HttpStatus.OK);
    }
	
	@GetMapping(value = "/tasks/{taskId}/files" )
    public ResponseEntity<?> getFiles(@PathVariable(name = "taskId") Integer taskId) throws IOException {
        
		List<TaskFile> taskFiles = taskService.getTaskFiles(taskId);
        
        return new ResponseEntity<>(taskFiles, HttpStatus.OK);
    }
	
	 
	@GetMapping("/tasks/{taskId}/files/{fileId}")
	public ResponseEntity<?> downloadFile(@PathVariable(name = "taskId") Integer taskId,
												@PathVariable(name = "fileId") Integer fileId) throws Exception {
		
		 
		Task task = taskService.getTaskById(taskId);
		 
	
	      try {
	    	  	TaskFile dbFile = taskService.getTaskFileById(fileId);
	    	  	
	    	  	logger.debug("dbFile {}" , dbFile.getFileName());
	    	  	
	            return ResponseEntity.ok() 
	            	   .header("Content-Type: application/pdf")
	            	   .header("Content-Description: File Transfer")
	                   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dbFile.getFileName()  )
	                   .body(new ByteArrayResource(dbFile.getTaskFile()));
	      } catch(Exception e) {
	    	  			logger.error(e.getMessage());
	               throw new Exception("Error downloading file");
	      } 
	       
	    }
	 
}
