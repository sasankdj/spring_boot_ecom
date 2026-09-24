package in.main.service;

import in.main.jwt.JwtService;

import in.main.repository.OrderItemRepo;
import in.main.repository.OrdersRepo;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.main.entities.LoginReq;
import in.main.entities.OrderItem;
import in.main.entities.Orders;
import in.main.entities.SignupReq;
import in.main.entities.User;
import in.main.repository.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	private final OrdersService ordersService;
	private final OrdersRepo ordersRepo;
	private final OrderItemRepo orderItemRepo;
	private final JwtService jwtService;
	private final UserRepo repo;
	private final PasswordEncoder encoder;

	UserService(UserRepo repo, PasswordEncoder encoder, JwtService jwtService, OrderItemRepo orderItemRepo, OrdersRepo ordersRepo, OrdersService ordersService) {
		this.repo = repo;
		this.encoder = encoder;
		this.jwtService = jwtService;
		this.orderItemRepo = orderItemRepo;
		this.ordersRepo = ordersRepo;
		this.ordersService = ordersService;
	}
	
	
	public User signUp(SignupReq u) {
		User user1 = repo.findByUsername(u.getUsername());
		if(user1==null) {
			
		User user= new User();
		user.setEmail(u.getEmail());
		user.setUsername(u.getUsername());
		user.setPassword(encoder.encode(u.getPassword()));
		user.setRole(u.getRole());
		repo.save(user);
		return user;
		}
		return user1;
		
	}
	
	public String login(LoginReq lr) throws UsernameNotFoundException{
		User u = repo.findByUsername(lr.getUsername());
		if(u==null) {
			throw new UsernameNotFoundException("user not found");
		}
		return jwtService.generateToken(lr.getUsername());
	}
	
	
	public List<User> getUsers(){
		return repo.findAll();
	}
	
	public User getUser(int id) {
		return repo.findById(id).orElseThrow();
	}


	public String getRole(String key) {
		String username = jwtService.extractUsername(key);
		User u =repo.findByUsername(username);
		return u.getRole();
	}

	@Transactional
	public String deleteUser(int id) {
		Optional<User> o = repo.findById(id);
		if(o.isPresent()) {
			User u = o.get();
		List<Orders> li =ordersRepo.findByUser(u);
		for(Orders or:li) {
			orderItemRepo.deleteByOrder(or);
		}
//		orderItemRepo.deleteByOrder(ordersService.getOrders(o.get()));
			ordersRepo.deleteByUser(o.get());
			repo.deleteById(id);
			return "Success";
		}
		return "Failed";
	}
}
