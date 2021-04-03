package com.firebasecrud.controllers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firebasecrud.entities.Product;
import com.firebasecrud.services.ProductService;

@RestController
@RequestMapping(value = "/api")
public class ProductController {
	
	@Autowired
    private ProductService service;
	
	@GetMapping("/products")
	public List<Product> findAll() throws ExecutionException, InterruptedException  {
		return service.findAll();		
	}
	
	@GetMapping("/products/{id}")
	public Map<String, Object> findById(@PathVariable String id) throws ExecutionException, InterruptedException {
		return service.findById(id);
	}
	
	@PostMapping("/products")
	public void save(@RequestBody Product product) throws ExecutionException, InterruptedException{
		service.save(product);
	}
	
	@PutMapping("/products/{id}")
    public void update(@PathVariable String id,  @RequestBody Product obj) throws ExecutionException, InterruptedException {
		service.update(id, obj);
	}
	
	@DeleteMapping("/products/{id}")
    public void delete(@PathVariable String id){
		service.delete(id);
	}
}










