package task.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.management.model.Task;
import task.management.model.TaskFile;

@Repository
public interface ITaskFile extends JpaRepository<TaskFile, Integer> {

	public List<TaskFile> findAllByTask(Task task);

}
