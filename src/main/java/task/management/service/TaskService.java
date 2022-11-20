package task.management.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import task.management.dto.TaskDTO;
import task.management.error.ApiNotFoundException;
import task.management.model.Priority;
import task.management.model.Task;
import task.management.model.TaskFile;
import task.management.model.User;
import task.management.repository.IPriority;
import task.management.repository.ITask;
import task.management.repository.ITaskFile;
import task.management.repository.IUser;

@Service
public class TaskService {
	
	@Autowired
	private ITask taskRepository;
	@Autowired
	private IUser userRepository;
	@Autowired
	private IPriority priorityRepository;
	@Autowired
	private ITaskFile fileRepository;
	
	public boolean validateTaskDTO(TaskDTO taskDTO) {
		if(taskDTO.getTaskTitle()== null) {
			return false;
		}
		
		if(taskDTO.getAssignedTo() == null) {
			return false;
		}
		
		if(taskDTO.getTaskPriority() == null) {
			return false;
		}
		
		if(taskDTO.getStartDate() == null) {
			return false;
		}
		
		if(taskDTO.getEndDate() == null) {
			return false;
		}
		
		return true;
	}
	
	public boolean validateTask(Task task) {
		if(task.getTaskTitle().isEmpty()) {
			return false;
		}
		
		if(task.getAssignedTo() == null) {
			return false;
		}
		
		if(task.getTaskPriority() == null) {
			return false;
		}
		
		if(task.getStartDate() == null) {
			return false;
		}
		
		if(task.getEndDate() == null) {
			return false;
		}
		
		return true;
	}
	
	public Task getTaskById(Integer id) {
		return taskRepository.findById(id).orElse( null); 
	}
	
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	public Task createTask(Task task) {
		
		task.setCreatedOn(LocalDateTime.now());
		task.setAccomplished("N");
		
		return taskRepository.save(task);
	}
	
	public Task updateTask(Integer id, Task task) {
		
		Task existingTask = this.getTaskById(id);
		
		if(existingTask != null) {
			
			existingTask.setTaskTitle(task.getTaskTitle());
			existingTask.setTaskDescription(task.getTaskDescription());
			existingTask.setAccomplished(task.getAccomplished());
			existingTask.setAssignedTo(task.getAssignedTo());
			existingTask.setStartDate(task.getStartDate());
			existingTask.setEndDate(task.getEndDate());
			existingTask.setTaskPriority(task.getTaskPriority());
			
			existingTask.setModifiedOn(LocalDateTime.now());
			
			return taskRepository.save(existingTask);
		} else {
			return null;
		}		 
	}
	
	public Task deleteTaskById(Integer id) {
		
		Task existingTask = this.getTaskById(id);
		
		if(existingTask != null) { 
			taskRepository.delete(existingTask);
			return existingTask;
		} else {
			return null;
		}		 
	}
	
	public List<Priority> getAllPriorities( ) {
		return priorityRepository.findAll();
	}
	
	public Priority getPriorityById(Integer id) {
		return priorityRepository.findById(id).orElse(null);
	}
	
	public Priority createPriority(Priority priority) {
		priority.setCreatedOn(LocalDateTime.now());
		return priorityRepository.save(priority);
	}
	
	public Priority deletePriorityById(Integer id) {
		
		Priority priority = getPriorityById(id);
		
		if(priority != null) {
			priorityRepository.delete(priority);
			
			return priority;
		} else {
			return null;
		}
	}
	
	public Priority updatePriority(Integer id, Priority priority) {
		
		Priority oldPriority = getPriorityById(id);
		
		if(oldPriority != null) {
			
			oldPriority.setPriorityCode(priority.getPriorityCode());
			oldPriority.setPriorityName(priority.getPriorityName());
			oldPriority.setModifiedOn(LocalDateTime.now());
			return priorityRepository.save(oldPriority);
		} else {
			return null;
		}
		
	}
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public User getUserById(Integer id) {
		return userRepository.findById(id).orElse( null); 
	}
	
	public User createUser(User user) {
		user.setStatus("A");
		user.setCreatedOn(LocalDateTime.now());
		return userRepository.save(user);
	}
	
	public User updateUser(Integer id, User user) {
		
		User oldUser = getUserById(id);	
		oldUser.setFirstName(user.getFirstName()); 
		oldUser.setLastName(user.getLastName());  
		
		oldUser.setModifiedOn(LocalDateTime.now());
		return userRepository.save(oldUser);
	}
	
	public User deleteUserById(Integer id) {
		
		User oldUser = getUserById(id);	
		
		userRepository.delete(oldUser);
		return oldUser;
		
	}
	
	public TaskFile save(Integer taskId, MultipartFile file) throws IOException {
		
		
		Task task = this.getTaskById(taskId);

        TaskFile taskFile = new TaskFile();
        taskFile.setTask(task);
        taskFile.setFileName(file.getOriginalFilename()); 
        taskFile.setCraetedOn(LocalDateTime.now());
        
        if(file.getOriginalFilename() != null) {
        	taskFile.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toUpperCase());
        } else {
        	taskFile.setExtension("");
        }
        taskFile.setTaskFile(file.getBytes());
        return fileRepository.save(taskFile); 
    }
	
	public List<TaskFile> getTaskFiles(Integer taskId) {
		
		Task task = this.getTaskById(taskId);
		
		return fileRepository.findAllByTask(task);
		
	}
	
	public TaskFile getTaskFileById(Integer fileId) {
		
		return fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File doesn't exists"));
				
	}
 
}
