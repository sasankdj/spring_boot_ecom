package in.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.main.entities.User;
@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
