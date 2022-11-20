package task.management.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

	private Integer taskId; 
	private String taskTitle; 
	private String taskDescription; 
	private PriorityDTO taskPriority; 
	private UserDTO assignedTo; 
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
	private LocalDateTime startDate; 
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm")   
	private LocalDateTime endDate;  
	private String accomplished; 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
	private LocalDateTime createdOn; 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
	private LocalDateTime modifiedOn;

}
