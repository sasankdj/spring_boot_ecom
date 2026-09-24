package in.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.main.entities.Cart;
import in.main.entities.User;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{

	List<Cart> findByUser(User u);

	Optional<Cart> findByProductId(int productId);

}
