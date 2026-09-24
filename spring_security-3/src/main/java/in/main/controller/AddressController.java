package in.main.controller;

import in.main.jwt.JwtService;
import in.main.repository.AddressRepo;
import in.main.repository.UserRepo;
import in.main.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.main.entities.Address;
import in.main.entities.User;
import in.main.service.AddressService;

@RestController
@RequestMapping("/checkout")
@CrossOrigin(origins = "*")
public class AddressController {
	
	private final AddressRepo addressRepo;
	private final UserRepo userRepo;
	private final UserService userService;
	private final JwtService jwtService;
	private final AddressService addressService;

	AddressController(AddressService addressService, JwtService jwtService, UserService userService, UserRepo userRepo, AddressRepo addressRepo) {
		this.addressService = addressService;
		this.jwtService = jwtService;
		this.userService = userService;
		this.userRepo = userRepo;
		this.addressRepo = addressRepo;
	}
	
	@PostMapping
	public Address saveAddress(@RequestBody Address address,Principal principal) {
		return addressService.saveAddress(principal.getName(), address);
	}
	@GetMapping
	public List<Address> getAddress(@RequestHeader("Authorization") String token){
		String username=jwtService.extractUsername(token.substring(7));
	
			User u = userRepo.findByUsername(username);
			return addressRepo.findByUser(u);
		
	}
}
