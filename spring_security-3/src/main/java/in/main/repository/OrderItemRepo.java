package in.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.main.entities.OrderItem;
import in.main.entities.Orders;
import in.main.entities.User;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer>{

	List<OrderItem> getByOrder(Orders orders);

	void deleteByOrder(Orders or);

	
}
