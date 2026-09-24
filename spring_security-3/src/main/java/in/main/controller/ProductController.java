package in.main.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.main.entities.Category;
import in.main.entities.Product;
import in.main.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

	private final ProductService service;

	ProductController(ProductService service) {
		this.service = service;
	}
	
	@PostMapping("/product")
	public Product addProduct(@RequestBody Product p) {
		return service.addProduct(p);
	}
	
	@PostMapping("/products")
	public List<Product> addProducts(@RequestBody List<Product> li) {
		return service.addProducts(li);
	}
	
	@GetMapping("/products")
	public List<Product> getProducts(){
		return service.getProducts();
	}
	
	@GetMapping("/products/search")
	public List<Product> getProductByName(@RequestParam String name){
		return service.getProductByName(name);
	}
	
	@GetMapping("/products/category")
	public List<Product> getProductByCategory(@RequestParam String category){
		return service.getProductByCategory(category);
	}
	@GetMapping("/products/getcategories")
	public List<Category> getCategories(){
		return service.getCategories();
	}
	
	@GetMapping("/products/sort")
	public List<Product> sortProducts( String category,@RequestParam(required = false) String sortBy,
			@RequestParam(required = false) String direction ){
		return service.sortProducts(category,sortBy,direction);
	}
	
	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable int id) {
		return service.getProduct(id);
	}
	
	@DeleteMapping("/product/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteProduct(@PathVariable int id) {
		return service.deleteProduct(id);
		
	}
	
	@PutMapping("/product/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Product editProduct(@PathVariable int id) {
		return service.editProduct(id);
	}
}
