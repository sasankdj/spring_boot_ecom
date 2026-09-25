package in.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.main.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

	List<Product> findByNameContainingIgnoreCase(String name);

	List<Product> findByCategoryContainingIgnoreCase(String category);

	List<Product> findByCategory(String category, Sort by);

	Optional<Product> findByNameIgnorCase(String name);
	
	
	
}
