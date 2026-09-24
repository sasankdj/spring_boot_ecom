package in.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.main.entities.LoginReq;
import in.main.entities.SignupReq;
import in.main.entities.User;
import in.main.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {

	private final UserService service;

	UserController(UserService service) {
		this.service = service;
	}
	
		@GetMapping("/welcome")
		public String welcome() {
			return "hello ";
		}
		
		@PostMapping("/signup")
		public User signup(@RequestBody SignupReq sr) {
			return service.signUp(sr);
		}
		
		@PostMapping("/login")
		public String login(@RequestBody LoginReq lr) {
			return service.login(lr);
		}
		@GetMapping("/role")
		public String getRole(@RequestParam String key) {
			return service.getRole(key);
		}
		@GetMapping("/users")
		@PreAuthorize("hasRole('ADMIN')")
		public List<User> getUsers(){
			return service.getUsers();
		}
		@DeleteMapping("/users/{id}")
		@PreAuthorize("hasRole('ADMIN')")
		public String deleteUser(@PathVariable int id) {
			return service.deleteUser(id);
		}
		
		@GetMapping("/user/{id}")
		public User getUser(@PathVariable int id) {
			return service.getUser(id);
		}
		
		@GetMapping("/who")
		@PreAuthorize("hasRole('ADMIN')")
		public String who(Authentication auth) {
			return auth.getAuthorities().toString();
		}
}
