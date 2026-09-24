package in.main.controller;

import in.main.service.OrdersService;
import in.main.service.ProductService;
import in.main.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.main.entities.Dashboard;
import in.main.entities.Orders;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {
	
	private final UserService userService;
	private final ProductService productService;
	private final OrdersService ordersService;

	AdminController(OrdersService ordersService, ProductService productService, UserService userService) {
		this.ordersService = ordersService;
		this.productService = productService;
		this.userService = userService;
	}

	@GetMapping("/dashboard")
	public Dashboard getDashBoard() {
		Dashboard d = new Dashboard();
		d.setTotalProducts(productService.getProducts().size());
		d.setTotalOrders(ordersService.getOrders().size());
		d.setTotalUsers(userService.getUsers().size());
		double revenue=0;
		for(Orders o : ordersService.getOrders()) {
			revenue+=o.getTotalAmount();
		}
		d.setTotalRevenue(revenue);
		return d;
	}
	
}
