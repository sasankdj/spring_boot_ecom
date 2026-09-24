package in.main.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.main.entities.Category;
import in.main.entities.Product;
import in.main.repository.ProductRepo;

@Service
public class ProductService {
	
	private final ProductRepo repo;


	ProductService(ProductRepo repo) {
		this.repo = repo;
	}
	
	public Product addProduct(Product p ) {
		return repo.save(p);
	}
	
	public List<Product> addProducts(List<Product> li){
		return repo.saveAll(li);
	}
	
	public List<Product> getProducts(){
		return repo.findAll();
		
	}
	

	public Product getProduct(int id) {
		return repo.findById(id).orElseThrow();
	}

	public String deleteProduct(int id) {
		repo.deleteById(id);
		return "success";
	}

	public List<Product> getProductByName(String name) {
		return repo.findByNameContainingIgnoreCase(name);
		
	}

	public List<Product> getProductByCategory(String category) {
		return repo.findByCategoryContainingIgnoreCase(category);
		
	}

	public List<Category> getCategories() {
		List<Product> li = repo.findAll();
		HashMap<String,String> m = new HashMap<String, String>();
		for(Product p : li) {
			if(!m.containsKey(p.getCategory())) {
				m.put(p.getCategory(), p.getImageUrl());
			}
		}
		List<Category> al = new ArrayList<Category>();
		for(String s:m.keySet()) {
			
			al.add(new Category(s,m.get(s)));
			
		}
		return al;
	}

	public List<Product> sortProducts(String category, String sortBy, String direction) {
		if(sortBy==null || direction==null)
		{
			return repo.findByCategoryContainingIgnoreCase(category);
		}
		Sort.Direction dir = direction.equalsIgnoreCase("asc")?Sort.Direction.ASC:Sort.Direction.DESC;
		return repo.findByCategory(category,Sort.by(dir,sortBy));
	}

	public Product editProduct(int id) {
		Optional<Product> o =repo.findById(id);
		if(o.isPresent()) {
			
			return o.get();
		}
		return null;
	}
}
