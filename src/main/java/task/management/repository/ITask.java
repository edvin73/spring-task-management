package task.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.management.model.Task;

@Repository
public interface ITask extends JpaRepository<Task, Integer> {

}
