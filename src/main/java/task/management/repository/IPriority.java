package task.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.management.model.Priority;

@Repository
public interface IPriority extends JpaRepository<Priority, Integer> {

}
