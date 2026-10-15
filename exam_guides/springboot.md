# Spring Boot Backend

## Project Structure

```text
use/
â”œâ”€â”€ src/
â”‚   â””â”€â”€ main/
â”‚       â”œâ”€â”€ java/
â”‚       â”‚   â””â”€â”€ com/
â”‚       â”‚       â””â”€â”€ example/
â”‚       â”‚           â””â”€â”€ use/
â”‚       â”‚               â”œâ”€â”€ controller/
â”‚       â”‚               â”‚   â””â”€â”€ ProductController.java
â”‚       â”‚               â”œâ”€â”€ model/
â”‚       â”‚               â”‚   â””â”€â”€ Product.java
â”‚       â”‚               â”œâ”€â”€ repository/
â”‚       â”‚               â”‚   â””â”€â”€ ProductRepository.java
â”‚       â”‚               â”œâ”€â”€ service/
â”‚       â”‚               â”‚   â””â”€â”€ ProductService.java
â”‚       â”‚               â””â”€â”€ UseApplication.java
â”‚       â””â”€â”€ resources/
â”‚           â””â”€â”€ application.properties
```

## `src/main/resources/application.properties`

```properties
spring.application.name=use
```

## `src/main/java/com/example/use/UseApplication.java`

```java
package com.example.use;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UseApplication {

    public static void main(String[] args) {
        SpringApplication.run(UseApplication.class, args);
    }

}
```

## `src/main/java/com/example/use/model/Product.java`

```java
package com.example.use.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private int stock;
}
```

## `src/main/java/com/example/use/repository/ProductRepository.java`

```java
package com.example.use.repository;

import com.example.use.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> findByName(String name);
    List<Product> findByPriceLessThan(double price);
    List<Product> findByStockGreaterThan(int minStock);
}
```

## `src/main/java/com/example/use/service/ProductService.java`

```java
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
    public ProductService( ProductRepository repo){
        this.repo = repo;
    }

    public List<Product> getProducts()
    {
        return repo.findAll();
    }

    public Product getProductById(String id){
        return repo.findById(id).orElse(null);
    }

    public Product saveProduct(Product product){
        return repo.save(product);
    }

    public void deleteProduct(String id){
        repo.deleteById(id);
    }

    public Product updateProduct(String id, Product updatedData)
    {
        Product existing = repo.findById(id).orElse(null);
        if(existing != null){
            existing.setName(updatedData.getName());
            existing.setPrice(updatedData.getPrice());
            existing.setStock(updatedData.getStock());
            return repo.save(existing);
        }
        return null;
    }

    public List<Product> searchByName(String name){
        return repo.findByName(name);
    }

}
```

## `src/main/java/com/example/use/controller/ProductController.java`

```java
package com.example.use.controller;

import com.example.use.model.Product;
import com.example.use.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins="*")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service)
    {
        this.service = service;
    }

    @GetMapping
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id)
    {
        return service.getProductById(id);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product)
    {
        return service.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id,@RequestBody Product product)
    {
        return service.updateProduct(id,product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id)
    {
        service.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name)
    {
        return service.searchByName(name);
    }
}
```

# POSTMAN API TESTING

## Endpoints Overview

| Operation | Method | URL | Request Body | Expected Response |
|---|---|---|---|---|
| Create Product | `POST` | `http://localhost:8080/api/products` | JSON object | 200 OK (created product with id) |
| Get All Products | `GET` | `http://localhost:8080/api/products` | None | 200 OK (JSON array of products) |
| Get Product by ID | `GET` | `http://localhost:8080/api/products/6acc5cc42577d09cacd3c23e` | None | 200 OK (product object or null) |
| Update Product | `PUT` | `http://localhost:8080/api/products/6acc5cc42577d09cacd3c23e` | JSON object | 200 OK (updated product object or null) |
| Delete Product | `DELETE` | `http://localhost:8080/api/products/6acc5cc42577d09cacd3c23e` | None | 200 OK (void) |
| Search Products | `GET` | `http://localhost:8080/api/products/search?name=Headphones` | None | 200 OK (JSON array of matching products) |

## Create Product

Method: `POST`

URL:
`http://localhost:8080/api/products`

Headers:
`Content-Type: application/json`

Body â†’ raw â†’ JSON:

```json
{
  "name": "Headphones",
  "price": 1999.0,
  "stock": 10
}
```

Expected Response:
200 OK with created product object including generated MongoDB id:
```json
{
  "id": "6acc5cc42577d09cacd3c23e",
  "name": "Headphones",
  "price": 1999.0,
  "stock": 10
}
```

## Get All Products

Method: `GET`

URL:
`http://localhost:8080/api/products`

Headers:
`Accept: application/json`

Body: None.

Expected Response:
200 OK with JSON array of all stored products:
```json
[
  {
    "id": "6acc5cc42577d09cacd3c23e",
    "name": "Headphones",
    "price": 1999.0,
    "stock": 10
  }
]
```

## Get Product by ID

Method: `GET`

URL:
`http://localhost:8080/api/products/6acc5cc42577d09cacd3c23e`

Headers:
`Accept: application/json`

Path Parameter: id = 6acc5cc42577d09cacd3c23e (Use actual ID returned by POST or GET; never use literal curly braces).

Body: None.

Expected Response:
200 OK with product object (or null if not found):
```json
{
  "id": "6acc5cc42577d09cacd3c23e",
  "name": "Headphones",
  "price": 1999.0,
  "stock": 10
}
```

## Update Product

Method: `PUT`

URL:
`http://localhost:8080/api/products/6acc5cc42577d09cacd3c23e`

Headers:
`Content-Type: application/json`

Path Parameter: id = 6acc5cc42577d09cacd3c23e (Use actual ID returned by POST or GET; never use literal curly braces).

Body â†’ raw â†’ JSON:

```json
{
  "name": "Headphones v2",
  "price": 2499.0,
  "stock": 8
}
```

Expected Response:
200 OK with updated product object (or null if not found):
```json
{
  "id": "6acc5cc42577d09cacd3c23e",
  "name": "Headphones v2",
  "price": 2499.0,
  "stock": 8
}
```

## Delete Product

Method: `DELETE`

URL:
`http://localhost:8080/api/products/6acc5cc42577d09cacd3c23e`

Path Parameter: id = 6acc5cc42577d09cacd3c23e (Use actual ID returned by POST or GET; never use literal curly braces).

Body: None.

Expected Response:
200 OK with empty response body.

## Search Products by Name

Method: `GET`

URL:
`http://localhost:8080/api/products/search?name=Headphones`

Headers:
`Accept: application/json`

Query Parameter: name=Headphones

Body: None.

Expected Response:
200 OK with JSON array of matching products:
```json
[
  {
    "id": "6acc5cc42577d09cacd3c23e",
    "name": "Headphones",
    "price": 1999.0,
    "stock": 10
  }
]
```


