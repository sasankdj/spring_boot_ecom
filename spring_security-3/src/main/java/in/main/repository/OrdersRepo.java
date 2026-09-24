package in.main.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.main.entities.OrderItem;
import in.main.entities.Orders;
import in.main.entities.User;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Integer>{

	List<Orders> findByUser(User u, Sort sort);

	void deleteByUser(User user);

	List<Orders> findByUser(User u);

		
	
}
