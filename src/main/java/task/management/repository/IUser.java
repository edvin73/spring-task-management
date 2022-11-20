package task.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.management.model.User;

@Repository
public interface IUser extends JpaRepository<User, Integer> {

}
