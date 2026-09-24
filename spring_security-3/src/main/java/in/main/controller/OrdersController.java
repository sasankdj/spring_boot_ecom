package in.main.controller;

import in.main.repository.ProductRepo;
import in.main.repository.UserRepo;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.main.entities.OrderItem;
import in.main.entities.Orders;
import in.main.entities.User;
import in.main.jwt.JwtService;
import in.main.service.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrdersController {
	
	private final ProductRepo productRepo;
	private final UserRepo userRepo;
	private final OrdersService ordersService;
	private final JwtService jwtService;

	OrdersController(OrdersService ordersService, JwtService jwtService, UserRepo userRepo, ProductRepo productRepo) {
		this.ordersService = ordersService;
		this.jwtService = jwtService;
		this.userRepo = userRepo;
		this.productRepo = productRepo;
	}
	public String getUsername(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		return jwtService.extractUsername(token);
	}
	
	
	@PostMapping("/place")
	public Orders placeOrder(HttpServletRequest request) {
		String username = getUsername(request);
		return ordersService.placeOrder(username);
	}
	
	@GetMapping("/get")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Orders> getOrders(){
		return ordersService.getOrders();
	}
	@GetMapping("/getOrders")
	public List<Orders> getOrders(HttpServletRequest request){
		User user= userRepo.findByUsername(getUsername(request));
		return ordersService.getOrders(user);
	}
	
	@GetMapping("/get/{id}")
	public List<OrderItem> getItems(@PathVariable int id){
		return ordersService.getItems(id);
	}
	
	@GetMapping("/getOrder/{id}")
	public Orders getOrder(@PathVariable int id) {
		return ordersService.getOrder(id);
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Orders upateOrder(@PathVariable int id,@RequestParam(required = true) String status) {
		return ordersService.updateOrder(id,status);
	}
	
	
}
