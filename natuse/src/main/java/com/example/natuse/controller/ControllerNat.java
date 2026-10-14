package com.example.natuse.controller;

import com.example.natuse.model.ModelNat;
import com.example.natuse.service.ServiceNat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins="*")
public class ControllerNat {
    private final ServiceNat service;

    @Autowired
    public ControllerNat(ServiceNat service)
    {
        this.service = service;
    }

    @GetMapping
    public List<ModelNat> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public ModelNat getProductById(@PathVariable String id)
    {
        return service.getProductById(id);
    }

    @PostMapping
    public ModelNat saveProduct(@RequestBody ModelNat product)
    {
        return service.saveProduct(product);
    }

    @PutMapping("/{id}")
    public ModelNat updateProduct(@PathVariable String id,@RequestBody ModelNat product)
    {
        return service.updateProduct(id,product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id)
    {
        service.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<ModelNat> searchByName(@RequestParam String name)
    {
        return service.searchByName(name);
    }
}

