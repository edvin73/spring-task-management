package task.management.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TASK_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class User {
	
	@Id
	@GeneratedValue(generator = "SEQ_TASK_USER")
	@SequenceGenerator(name = "SEQ_TASK_USER", sequenceName = "SEQ_TASK_USER", allocationSize = 1)
	@Column(name = "USER_ID", length = 5)
	private Integer userId;
	
	@Column(name = "FIRST_NAME", length = 20, nullable = false)
	private String firstName;
	
	@Column(name = "LAST_NAME", length = 30, nullable = false)
	private String lastName;
	
	@Column(name = "STATUS", length = 1, nullable = false)
	private String status;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
	@Column(name = "CREATED_ON")
	private LocalDateTime createdOn;
 
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
	@Column(name = "MODIFIED_ON")
	private LocalDateTime modifiedOn;
 

}
