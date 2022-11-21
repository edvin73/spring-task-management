package task.management.model;

import java.sql.Blob;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TASK_FILE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class TaskFile {
	
	@Id
	@GeneratedValue(generator = "SEQ_TASK_FILE")
	@SequenceGenerator(name = "SEQ_TASK_FILE", sequenceName = "SEQ_TASK_FILE", allocationSize = 1)
	@Column(name = "FILE_ID", length = 5)
	private Integer fileId;
	
	@ManyToOne
	@JoinColumn(name = "TASK_ID")
	private Task task;
	
	@Column(name = "FILE_NAME", length = 100)	
	private String fileName;
	
	@Column(name = "EXTENSION", length = 10)
	private String extension;
	
	@Lob
	@Column(name = "TASK_FILE")
	private byte[] taskFile;
	
	@Column(name = "CREATED_ON")
	private LocalDateTime craetedOn;

}
