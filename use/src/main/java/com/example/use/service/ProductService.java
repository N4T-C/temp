package com.example.use.service;

import com.example.use.model.Product;
import com.example.use.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    @Autowired
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product getProductById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return repo.save(product);
    }

    public void deleteProduct(String id) {
        repo.deleteById(id);
    }

    public Product updateProduct(String id, Product updatedData) {
        Product existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(updatedData.getName());
            existing.setPrice(updatedData.getPrice());
            existing.setStock(updatedData.getStock());
            return repo.save(existing);
        }
        return null;
    }

    public List<Product> searchByName(String name) {
        return repo.findByName(name);
    }
}
