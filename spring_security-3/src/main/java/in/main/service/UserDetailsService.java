package in.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.main.repository.UserRepo;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	private final UserRepo repo;

	UserDetailsService(UserRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		in.main.entities.User u = repo.findByUsername(username);
		if(u==null) {
			throw new UsernameNotFoundException("user not found");
		}
		
		return User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole()).build();
	}

}
