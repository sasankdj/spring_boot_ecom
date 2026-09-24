package in.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.main.entities.Cart;
import in.main.entities.Product;
import in.main.entities.User;
import in.main.repository.CartRepo;
import in.main.repository.ProductRepo;
import in.main.repository.UserRepo;

@Service
public class CartService {
	
	private final UserRepo userRepo;
	private final CartRepo cartRepo;
	private final ProductRepo productRepo;

	CartService(UserRepo userRepo, CartRepo cartRepo, ProductRepo productRepo) {
		this.userRepo = userRepo;
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
	}
	
	public Cart addToCart(int productId, String username) {
		User u = userRepo.findByUsername(username);
		Product p =  productRepo.findById(productId).orElseThrow();
		Optional<Cart> c=cartRepo.findByProductId(productId);
		if(c.isPresent()) {
			Cart cart = c.get();
			cart.setQuantity(cart.getQuantity()+1);
			cartRepo.save(cart);
			return cart;
		}
		else {
			
		Cart cart = new Cart();
		cart.setProduct(p);
		cart.setUser(u);
		cart.setQuantity(1);
		cartRepo.save(cart);
		return cart;
		}
	}
	
	public List<Cart> getCart(String username){
		 User u = userRepo.findByUsername(username);
		return cartRepo.findByUser(u);
		
	}
	
	public String removeFromCart(int cartId) {
		cartRepo.deleteById(cartId);
		return "deleted succesfully";
	}
	
	public Cart updateQuantity(int cartId, int quantity) {
		Cart cart = cartRepo.findById(cartId).orElseThrow();
		cart.setQuantity(quantity);
		return cartRepo.save(cart);
	}
	
	public String deleteItem(int cartId) throws Exception{
		Optional<Cart> o= cartRepo.findById(cartId);
		if(o.isPresent()) {
			
			cartRepo.delete(o.get());
		}
		else {
			throw new Exception("product not found");
		}
		return "success";
	}
}
