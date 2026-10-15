package com.example.crud.service;

import com.example.crud.model.Product;
import com.example.crud.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return repository.findById(id);
    }

    public Product addProduct(Product product) {
        product.setId(null);
        return repository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        product.setId(id);
        return repository.save(product);
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }
}
