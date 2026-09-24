package in.main.controller;


import in.main.repository.CartRepo;
import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.main.entities.Cart;
import in.main.jwt.JwtService;
import in.main.service.CartService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {
	
	private final CartRepo cartRepo;
	private final CartService service;
	private final JwtService jwtService;


	CartController(CartService service, JwtService jwtService, CartRepo cartRepo) {
		this.service = service;
		this.jwtService = jwtService;
		this.cartRepo = cartRepo;
	}
	
	public String getUsername(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		return jwtService.extractUsername(token);
	}
	
	@PostMapping("/add/{id}")
	public Cart addToCart(@PathVariable int id, HttpServletRequest request) {
		return service.addToCart(id, getUsername(request));
	}
	
	@GetMapping("/get")
	public List<Cart> getCart(HttpServletRequest request){
		return service.getCart(getUsername(request));
	}
	
	@PutMapping("/{cartId}/{quantity}")
	public Cart updateCart(@PathVariable int cartId, @PathVariable int quantity) {
		return service.updateQuantity(cartId, quantity);
	}
	
	@DeleteMapping("/delete")
	public String deleteCart() {
		cartRepo.deleteAll();
		return "success";
	}
	@DeleteMapping("/delete/{cartId}")
	public String deleteItem(@PathVariable int cartId) throws Exception {
		return service.deleteItem(cartId);
	}
}
