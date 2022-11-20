package task.management.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TASK")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Task {
	
	@Id
	@GeneratedValue(generator = "SEQ_TASK")
	@SequenceGenerator(name = "SEQ_TASK", sequenceName = "SEQ_TASK", allocationSize = 1)
	@Column(name = "TASK_ID", length = 5)
	private Integer taskId;
	
	@Column(name = "TASK_TITLE", length = 20)
	private String taskTitle;
	
	@Column(name = "TASK_DESCRIPTION", length = 50)
	private String taskDescription;
	
	@ManyToOne
	@JoinColumn(name = "PRIORITY_ID")
	private Priority taskPriority;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User assignedTo;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
	@Column(name = "START_DATE")
	private LocalDateTime startDate;
	
//	@Column(name = "START_TIME")
//	private LocalDateTime startTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")  
	@Column(name = "END_DATE")
	private LocalDateTime endDate;
	
//	@Column(name = "END_TIME")
//	private LocalDateTime endTime;
	
	@Column(name = "ACCOMPLISHED", length = 1, nullable = false)
	private String accomplished;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
	@Column(name = "CREATED_ON")
	private LocalDateTime createdOn;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
	@Column(name = "MODIFIED_ON")
	private LocalDateTime modifiedOn;
}
