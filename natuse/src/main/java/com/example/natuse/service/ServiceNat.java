package com.example.natuse.service;

import com.example.natuse.model.ModelNat;
import com.example.natuse.repository.RepositoryNat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceNat {
    private final RepositoryNat repo;

    @Autowired
    public ServiceNat( RepositoryNat repo){
        this.repo = repo;
    }

    public List<ModelNat> getProducts()
    {
        return repo.findAll();
    }

    public ModelNat getProductById(String id){
        return repo.findById(id).orElse(null);
    }

    public ModelNat saveProduct(ModelNat product){
        return repo.save(product);
    }

    public void deleteProduct(String id){
        repo.deleteById(id);
    }

    public ModelNat updateProduct(String id, ModelNat updatedData)
    {
        ModelNat existing = repo.findById(id).orElse(null);
        if(existing != null){
            existing.setName(updatedData.getName());
            existing.setPrice(updatedData.getPrice());
            existing.setStock(updatedData.getStock());
            return repo.save(existing);
        }
        return null;
    }

    public List<ModelNat> searchByName(String name){
        return repo.findByName(name);
    }

}
