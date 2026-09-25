package in.main.service;

import in.main.repository.AddressRepo;
import in.main.repository.OrdersRepo;
import in.main.repository.ProductRepo;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import in.main.entities.Address;
import in.main.entities.Cart;
import in.main.entities.OrderItem;
import in.main.entities.OrderReq;
import in.main.entities.Orders;
import in.main.entities.Product;
import in.main.entities.User;
import in.main.repository.CartRepo;
import in.main.repository.OrderItemRepo;
import in.main.repository.UserRepo;

@Service
public class OrdersService {

	private final AddressRepo addressRepo;
	private final ProductRepo productRepo;
	private final OrderItemRepo orderItemRepo;
	private final OrdersRepo ordersRepo;
	private final UserRepo userRepo;
	private final CartRepo cartRepo;

	public OrdersService(CartRepo cartRepo, UserRepo userRepo, OrdersRepo ordersRepo, OrderItemRepo orderItemRepo,
			ProductRepo productRepo, AddressRepo addressRepo) {
		this.cartRepo = cartRepo;
		this.userRepo = userRepo;
		this.ordersRepo = ordersRepo;
		this.orderItemRepo = orderItemRepo;
		this.productRepo = productRepo;
		this.addressRepo = addressRepo;
	}

	public Orders placeOrder(String username) {
		User user = userRepo.findByUsername(username);
		List<Cart> li = cartRepo.findByUser(user);

		Orders order = new Orders();
		double total = li.stream().mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice()).sum();

		order.setOrderDate(LocalDateTime.now());
		order.setStatus("PENDING");
		order.setTotalAmount(total);
		order.setUser(user);
//		order.setAddress(address);
		order = ordersRepo.save(order);

		for (Cart cart : li) {
			Product product = cart.getProduct();

			if (product.getStock() < cart.getQuantity()) {
				throw new RuntimeException(product.getName() + " is out of stock");
			}
			product.setStock(product.getStock() - cart.getQuantity());
			productRepo.save(product);
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setPrice(cart.getProduct().getPrice());
			item.setProduct(cart.getProduct());
			item.setQuantity(cart.getQuantity());

			orderItemRepo.save(item);
		}
		cartRepo.deleteAll(li);
		return order;
	}

	public Orders placeOrder(String username, OrderReq orderReq) {
		User user = userRepo.findByUsername(username);
		Optional<Address> a = addressRepo.findById(orderReq.getId());

		List<Cart> li = cartRepo.findByUser(user);

		Orders order = new Orders();
		double total = li.stream().mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice()).sum();

		order.setOrderDate(LocalDateTime.now());
		order.setStatus("PENDING");
		order.setTotalAmount(total);
		order.setUser(user);
		if (a.isPresent()) {

			order.setAddress(a.get());
		}
		order = ordersRepo.save(order);

		for (Cart cart : li) {
			Product product = cart.getProduct();

			if (product.getStock() < cart.getQuantity()) {
				throw new RuntimeException(product.getName() + " is out of stock");
			}
			product.setStock(product.getStock() - cart.getQuantity());
			productRepo.save(product);
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setPrice(cart.getProduct().getPrice());
			item.setProduct(cart.getProduct());
			item.setQuantity(cart.getQuantity());

			orderItemRepo.save(item);
		}
		cartRepo.deleteAll(li);
		return order;
	}

	public List<Orders> getOrders() {
		return ordersRepo.findAll();
	}

	public List<Orders> getOrders(User u) {
		Sort.Direction dir = Sort.Direction.DESC;
		return ordersRepo.findByUser(u, Sort.by(dir, "id"));
	}

	public List<OrderItem> getItems(int id) {
		Orders orders = ordersRepo.findById(id).get();
		return orderItemRepo.getByOrder(orders);
	}

	public Orders getOrder(int id) {
		Optional<Orders> o = ordersRepo.findById(id);
		if (o.isPresent()) {
			return o.get();
		}
		return null;
	}

	public Orders updateOrder(int id, String status) {
		Optional<Orders> o = ordersRepo.findById(id);
		if (o.isPresent()) {
			Orders orders = o.get();
			orders.setStatus(status);
			ordersRepo.save(orders);
			return orders;
		}
		return null;
	}
}
