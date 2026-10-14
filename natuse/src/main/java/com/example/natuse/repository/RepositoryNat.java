package com.example.natuse.repository;

import com.example.natuse.model.ModelNat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryNat extends MongoRepository<ModelNat,String> {
    List<ModelNat> findByName(String name);
    List<ModelNat> findByPriceLessThan(double price);
    List<ModelNat> findByStockGreaterThan(int minStock);
}
