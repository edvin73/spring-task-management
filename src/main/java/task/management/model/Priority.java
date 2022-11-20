package task.management.model;

import java.time.LocalDate;
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
@Table(name = "PRIORITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Priority {
	
	@Id
	@GeneratedValue(generator = "SEQ_PRIORITY")
	@SequenceGenerator(name = "SEQ_PRIORITY", sequenceName = "SEQ_PRIORITY", allocationSize = 1)
	@Column(name = "PRIORITY_ID", length = 2)
	private Integer priorityId;
	
	@Column(name = "PRIORITY_NAME", length = 20, nullable = false)
	private String priorityName;
	
	@Column(name = "PRIORITY_CODE", length = 2, nullable = false)
	private String priorityCode;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
	@Column(name = "CREATED_ON")
	private LocalDateTime createdOn;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
	@Column(name = "MODIFIED_ON")
	private LocalDateTime modifiedOn;
}
