package in.main.service;

import in.main.repository.AddressRepo;
import in.main.repository.UserRepo;
import org.springframework.stereotype.Service;

import in.main.entities.Address;
import in.main.entities.User;

@Service
public class AddressService {
	
	private final AddressRepo addressRepo;
	private final UserRepo userRepo;

	AddressService(UserRepo userRepo, AddressRepo addressRepo) {
		this.userRepo = userRepo;
		this.addressRepo = addressRepo;
	}

	public Address saveAddress(String username, Address address) {
		User u =userRepo.findByUsername(username);
		Address test = addressRepo.findByAddress(address.getAddress());
		if(test!=null && test.getAddress().equals(address.getAddress())) {
			return test;
		}
		Address a= new Address();
		a.setUser(u);
		a.setAddress(address.getAddress());
		a.setCity(address.getCity());
		a.setEmail(address.getEmail());
		a.setFullName(address.getFullName());
	
		a.setPhoneNumber(address.getPhoneNumber());
		a.setState(address.getState());
		
		return addressRepo.save(a);
		
		
	}
}
