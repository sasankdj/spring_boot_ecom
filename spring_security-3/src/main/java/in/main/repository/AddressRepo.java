package in.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.main.entities.Address;
import in.main.entities.User;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {

	Address findByAddress(String address);

	List<Address> findByUser(User u);

}
