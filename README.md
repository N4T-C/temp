# Spring Boot Backend

## Project Structure

```text
natuse/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── natuse/
│       │               ├── controller/
│       │               │   └── ControllerNat.java
│       │               ├── model/
│       │               │   └── ModelNat.java
│       │               ├── repository/
│       │               │   └── RepositoryNat.java
│       │               ├── service/
│       │               │   └── ServiceNat.java
│       │               └── NatuseApplication.java
│       └── resources/
│           └── application.properties
```

## `src/main/resources/application.properties`

```properties
spring.application.name=natuse
```

## `src/main/java/com/example/natuse/NatuseApplication.java`

```java
package com.example.natuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NatuseApplication {

    public static void main(String[] args) {
        SpringApplication.run(NatuseApplication.class, args);
    }

}
```

## `src/main/java/com/example/natuse/model/ModelNat.java`

```java
package com.example.natuse.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ModelNat {
    @Id
    private String id;
    private String name;
    private double price;
    private int stock;
}
```

## `src/main/java/com/example/natuse/repository/RepositoryNat.java`

```java
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
```

## `src/main/java/com/example/natuse/service/ServiceNat.java`

```java
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
```

## `src/main/java/com/example/natuse/controller/ControllerNat.java`

```java
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

Body → raw → JSON:

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

Body → raw → JSON:

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

# Vue.js Frontend

## Variant 1: Nested Vue Router with Pinia Global State Management

### Project Structure

```text
frontend/
├── index.html
├── package.json
├── vite.config.js
└── src/
    ├── App.vue
    ├── main.js
    ├── router/
    │   └── index.js
    ├── stores/
    │   └── productStore.js
    ├── views/
    │   ├── AboutView.vue
    │   ├── CartView.vue
    │   ├── ContactView.vue
    │   ├── ProductsView.vue
    │   └── TeamView.vue
    └── components/
        ├── CartItem.vue
        └── ProductCard.vue
```

### `index.html`

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Natuse Vue Store</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.js"></script>
  </body>
</html>
```

### `package.json`

```json
{
  "name": "natuse-frontend",
  "version": "1.0.0",
  "private": true,
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "pinia": "^4.1.0",
    "vue": "^3.5.42",
    "vue-router": "^5.4.0"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^6.0.8",
    "vite": "^8.2.2"
  }
}
```

### `vite.config.js`

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173
  }
})
```

### `src/main.js`

```javascript
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.mount('#app')
```

### `src/App.vue`

```vue
<script setup>
import { useProductStore } from './stores/productStore'

const store = useProductStore()
</script>

<template>
  <div>
    <h1>Online Store</h1>
    <nav>
      <router-link to="/">Products</router-link> |
      <router-link to="/cart">Cart ({{ store.cart.length }})</router-link>
    </nav>
    <hr />

    <router-view />
  </div>
</template>
```

### `src/router/index.js`

```javascript
import { createRouter, createWebHistory } from 'vue-router'
import ProductsView from '../views/ProductsView.vue'
import CartView from '../views/CartView.vue'
import AboutView from '../views/AboutView.vue'
import ContactView from '../views/ContactView.vue'

const routes = [
  { 
    path: '/', 
    component: ProductsView,
    // Nested child routes rendered right underneath the Products list
    children: [
      { path: 'about', component: AboutView },
      { path: 'contact', component: ContactView }
    ]
  },
  { path: '/cart', component: CartView }
]

export default createRouter({
  history: createWebHistory(),
  routes
})
```

### `src/stores/productStore.js`

```javascript
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useProductStore = defineStore('product', () => {
  const products = ref([])
  const cart = ref([])

  // 1. Fetch from Spring Boot backend
  async function fetchProducts() {
    const res = await fetch('http://localhost:8080/api/products')
    products.value = await res.json()
  }

  // 2. Add to Cart
  function addToCart(p) {
    cart.value.push(p)
  }

  // 3. Remove from Cart
  function removeFromCart(id) {
    cart.value = cart.value.filter(item => item.id !== id)
  }

  return { products, cart, fetchProducts, addToCart, removeFromCart }
})
```

### `src/views/ProductsView.vue`

```vue
<script setup>
import { onMounted } from 'vue'
import { useProductStore } from '../stores/productStore'

const store = useProductStore()
onMounted(() => store.fetchProducts())
</script>

<template>
  <div>
    <h2>Products</h2>
    <ul>
      <li v-for="p in store.products" :key="p.id">
        {{ p.name }} - ₹{{ p.price }} (Stock: {{ p.stock }})
        <button @click="store.addToCart(p)">+ Add to Cart</button>
      </li>
    </ul>

    <hr />

    <!-- Nested links placed right below the product list -->
    <router-link to="/about">About</router-link> |
    <router-link to="/contact">Contact</router-link>

    <!-- Renders the clicked view directly underneath -->
    <router-view />
  </div>
</template>
```

### `src/views/CartView.vue`

```vue
<script setup>
import { useProductStore } from '../stores/productStore'

const store = useProductStore()
</script>

<template>
  <h2>Shopping Cart</h2>
  <p v-if="store.cart.length === 0">Your cart is empty.</p>
  <ul v-else>
    <li v-for="item in store.cart" :key="item.id" style="margin: 8px 0;">
      {{ item.name }} - ₹{{ item.price }}
      <button @click="store.removeFromCart(item.id)" style="margin-left: 10px;">Remove</button>
    </li>
  </ul>
</template>
```

### `src/views/AboutView.vue`

```vue
<template>
  <p>hi im natc</p>
</template>
```

### `src/views/ContactView.vue`

```vue
<template>
  <p>natc: 9999999999</p>
</template>
```

### `src/views/TeamView.vue`

```vue
<template>
  <div style="padding: 10px; border-left: 3px solid #16a34a; background: #ffffff;">
    <h4>Our Team</h4>
    <p>Engineered by: Nathan & College Lab Team</p>
  </div>
</template>
```

### `src/components/ProductCard.vue`

```vue
<template>
  <button @click="$emit('add', p)">+ Cart</button>
  <button @click="$emit('del', p.id)">Del</button>
  {{ p.name }} - ₹{{ p.price }}
</template>
<script>
export default { props: ['p'] }
</script>
```

### `src/components/CartItem.vue`

```vue
<template>
  <button @click="$emit('rem', i.id)">X</button>
  {{ i.name }} (x{{ i.quantity }})
</template>
<script>
export default { props: ['i'] }
</script>
```

## Variant 2: Flat Vue Router Routes (No Nested Children, with Pinia)

### Project Structure

```text
frontend/
├── index.html
├── package.json
├── vite.config.js
└── src/
    ├── App.vue
    ├── main.js
    ├── router/
    │   └── index.js
    ├── stores/
    │   └── productStore.js
    └── views/
        ├── AboutView.vue
        ├── CartView.vue
        ├── ContactView.vue
        └── ProductsView.vue
```

### `index.html`

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Natuse Vue Store</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.js"></script>
  </body>
</html>
```

### `package.json`

```json
{
  "name": "natuse-frontend",
  "version": "1.0.0",
  "private": true,
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "pinia": "^4.1.0",
    "vue": "^3.5.42",
    "vue-router": "^5.4.0"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^6.0.8",
    "vite": "^8.2.2"
  }
}
```

### `vite.config.js`

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173
  }
})
```

### `src/main.js`

```javascript
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.mount('#app')
```

### `src/App.vue`

```vue
<script setup>
import { useProductStore } from './stores/productStore'

const store = useProductStore()
</script>

<template>
  <div>
    <h1>Online Store</h1>
    <nav>
      <router-link to="/">Products</router-link> |
      <router-link to="/cart">Cart ({{ store.cart.length }})</router-link> |
      <router-link to="/about">About</router-link> |
      <router-link to="/contact">Contact</router-link>
    </nav>
    <hr />

    <router-view />
  </div>
</template>
```

### `src/router/index.js`

```javascript
import { createRouter, createWebHistory } from 'vue-router'
import ProductsView from '../views/ProductsView.vue'
import CartView from '../views/CartView.vue'
import AboutView from '../views/AboutView.vue'
import ContactView from '../views/ContactView.vue'

const routes = [
  { path: '/', component: ProductsView },
  { path: '/cart', component: CartView },
  { path: '/about', component: AboutView },
  { path: '/contact', component: ContactView }
]

export default createRouter({
  history: createWebHistory(),
  routes
})
```

### `src/stores/productStore.js`

```javascript
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useProductStore = defineStore('product', () => {
  const products = ref([])
  const cart = ref([])

  // 1. Fetch from Spring Boot backend
  async function fetchProducts() {
    const res = await fetch('http://localhost:8080/api/products')
    products.value = await res.json()
  }

  // 2. Add to Cart
  function addToCart(p) {
    cart.value.push(p)
  }

  // 3. Remove from Cart
  function removeFromCart(id) {
    cart.value = cart.value.filter(item => item.id !== id)
  }

  return { products, cart, fetchProducts, addToCart, removeFromCart }
})
```

### `src/views/ProductsView.vue`

```vue
<script setup>
import { onMounted } from 'vue'
import { useProductStore } from '../stores/productStore'

const store = useProductStore()
onMounted(() => store.fetchProducts())
</script>

<template>
  <div>
    <h2>Products</h2>
    <ul>
      <li v-for="p in store.products" :key="p.id">
        {{ p.name }} - ₹{{ p.price }} (Stock: {{ p.stock }})
        <button @click="store.addToCart(p)">+ Add to Cart</button>
      </li>
    </ul>
  </div>
</template>
```

### `src/views/CartView.vue`

```vue
<script setup>
import { useProductStore } from '../stores/productStore'

const store = useProductStore()
</script>

<template>
  <h2>Shopping Cart</h2>
  <p v-if="store.cart.length === 0">Your cart is empty.</p>
  <ul v-else>
    <li v-for="item in store.cart" :key="item.id" style="margin: 8px 0;">
      {{ item.name }} - ₹{{ item.price }}
      <button @click="store.removeFromCart(item.id)" style="margin-left: 10px;">Remove</button>
    </li>
  </ul>
</template>
```

### `src/views/AboutView.vue`

```vue
<template>
  <h2>About</h2>
  <p>hi im natc</p>
</template>
```

### `src/views/ContactView.vue`

```vue
<template>
  <h2>Contact</h2>
  <p>natc: 9999999999</p>
</template>
```

## Variant 3: Props and $emit Component Communication (Without Pinia, Without Router)

### Project Structure

```text
frontend/
├── index.html
├── package.json
├── vite.config.js
└── src/
    ├── App.vue
    ├── main.js
    └── components/
        ├── CartItem.vue
        └── ProductCard.vue
```

### `index.html`

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Natuse Vue Store</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.js"></script>
  </body>
</html>
```

### `package.json`

```json
{
  "name": "vue-frontend",
  "version": "1.0.0",
  "private": true,
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "vue": "^3.5.42"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^6.0.8",
    "vite": "^8.2.2"
  }
}
```

### `vite.config.js`

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173
  }
})
```

### `src/main.js`

```javascript
import { createApp } from 'vue'
import App from './App.vue'

createApp(App).mount('#app')
```

### `src/App.vue`

```vue
<template>
  <div>
    <h1>Product Store</h1>

    <h2>Products</h2>
    <ProductCard 
      v-for="p in products" 
      :key="p.id" 
      :product="p" 
      @add-to-cart="cart.push($event)" 
    />

    <hr />

    <h2>Cart ({{ cart.length }})</h2>
    <p v-if="cart.length === 0">Your cart is empty.</p>
    <CartItem
      v-for="item in cart"
      :key="item.id"
      :item="item"
      @remove-from-cart="removeFromCart"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ProductCard from './components/ProductCard.vue'
import CartItem from './components/CartItem.vue'

const products = ref([])
const cart = ref([])

function removeFromCart(id) {
  cart.value = cart.value.filter(item => item.id !== id)
}

onMounted(async () => {
  const res = await fetch('http://localhost:8080/api/products')
  products.value = await res.json()
})
</script>
```

### `src/components/ProductCard.vue`

```vue
<template>
  <div>
    <span>{{ product.name }} - ₹{{ product.price }}</span>
    <button @click="$emit('add-to-cart', product)">Add</button>
  </div>
</template>

<script>
export default {
  props: ['product']
}
</script>
```

### `src/components/CartItem.vue`

```vue
<template>
  <div>
    <span>{{ item.name }} - ₹{{ item.price }}</span>
    <button @click="$emit('remove-from-cart', item.id)">Remove</button>
  </div>
</template>

<script>
export default {
  props: ['item']
}
</script>
```

## Variant 4: Pinia-Based Global State Management (Without Router)

### Project Structure

```text
frontend/
├── index.html
├── package.json
├── vite.config.js
└── src/
    ├── App.vue
    ├── main.js
    ├── stores/
    │   └── productStore.js
    └── components/
        ├── CartItem.vue
        └── ProductCard.vue
```

### `index.html`

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Natuse Vue Store</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.js"></script>
  </body>
</html>
```

### `package.json`

```json
{
  "name": "vue-pinia-store",
  "version": "1.0.0",
  "private": true,
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "pinia": "^4.1.0",
    "vue": "^3.5.42"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^6.0.8",
    "vite": "^8.2.2"
  }
}
```

### `vite.config.js`

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173
  }
})
```

### `src/main.js`

```javascript
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'

const app = createApp(App)
app.use(createPinia())
app.mount('#app')
```

### `src/App.vue`

```vue
<script setup>
import { onMounted } from 'vue'
import { useProductStore } from './stores/productStore'
import ProductCard from './components/ProductCard.vue'
import CartItem from './components/CartItem.vue'

const store = useProductStore()

onMounted(() => {
  store.fetchProducts()
})
</script>

<template>
  <div>
    <h1>Online Store</h1>

    <h2>Products</h2>
    <ProductCard
      v-for="product in store.products"
      :key="product.id"
      :product="product"
    />

    <hr />

    <h2>Cart ({{ store.cart.length }})</h2>
    <p v-if="store.cart.length === 0">Your cart is empty.</p>
    <CartItem
      v-for="item in store.cart"
      :key="item.id"
      :item="item"
    />
  </div>
</template>
```

### `src/stores/productStore.js`

```javascript
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useProductStore = defineStore('product', () => {
  const products = ref([])
  const cart = ref([])

  // 1. Fetch from Spring Boot backend
  async function fetchProducts() {
    const res = await fetch('http://localhost:8080/api/products')
    products.value = await res.json()
  }

  // 2. Add to Cart
  function addToCart(p) {
    cart.value.push(p)
  }

  // 3. Remove from Cart
  function removeFromCart(id) {
    cart.value = cart.value.filter(item => item.id !== id)
  }

  return { products, cart, fetchProducts, addToCart, removeFromCart }
})
```

### `src/components/ProductCard.vue`

```vue
<script setup>
import { useProductStore } from '../stores/productStore'

defineProps(['product'])
const store = useProductStore()
</script>

<template>
  <div style="margin: 8px 0;">
    <span>{{ product.name }} - ₹{{ product.price }} (Stock: {{ product.stock }})</span>
    <button @click="store.addToCart(product)" style="margin-left: 10px;">+ Add to Cart</button>
  </div>
</template>
```

### `src/components/CartItem.vue`

```vue
<script setup>
import { useProductStore } from '../stores/productStore'

defineProps(['item'])
const store = useProductStore()
</script>

<template>
  <div style="margin: 8px 0;">
    <span>{{ item.name }} - ₹{{ item.price }}</span>
    <button @click="store.removeFromCart(item.id)" style="margin-left: 10px;">Remove</button>
  </div>
</template>
```

## Variant 5: Single-File App.vue (No Router, No Pinia)

### Project Structure

```text
frontend/
├── index.html
├── package.json
├── vite.config.js
└── src/
    ├── App.vue
    └── main.js
```

### `index.html`

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Natuse Vue Store</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.js"></script>
  </body>
</html>
```

### `package.json`

```json
{
  "name": "vue-frontend",
  "version": "1.0.0",
  "private": true,
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "vue": "^3.5.42"
  },
  "devDependencies": {
    "@vitejs/plugin-vue": "^6.0.8",
    "vite": "^8.2.2"
  }
}
```

### `vite.config.js`

```javascript
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173
  }
})
```

### `src/main.js`

```javascript
import { createApp } from 'vue'
import App from './App.vue'

createApp(App).mount('#app')
```

### `src/App.vue`

```vue
<script setup>
import { ref, onMounted } from 'vue'

const products = ref([])

async function getProducts() {
  const response = await fetch('http://localhost:8080/api/products')
  products.value = await response.json()
}

onMounted(getProducts)
</script>

<template>
  <h1>Product List</h1>

  <ul>
    <li v-for="product in products" :key="product.id">
      {{ product.name }} - ₹{{ product.price }} (Stock: {{ product.stock }})
    </li>
  </ul>
</template>
```


# Voting Eligibility Servlet — Complete Lab Execution Guide

*Tomcat 9 • Ubuntu / WSL • `javax.servlet` • POST form • Annotation-based mapping*

This practical accepts a name and age, then displays whether the person is eligible to vote. It uses the same annotation-based approach as Hello Servlet. **No MySQL and no `web.xml` are required.**

## Step 1 — Go to the Tomcat directory

```bash
cd /mnt/c/Directory/coding/web-dev/Practical_Exam/temp/Servlet/apache-tomcat-9.0.87
```

Use your own Tomcat 9 path if it is different.

## Step 2 — Create the application folders

```bash
mkdir -p webapps/voting-servlet/WEB-INF/classes
cd webapps/voting-servlet
```

This creates a separate application named `voting-servlet`, so your existing Hello Servlet is not overwritten.

## Step 3 — Create `index.html`

```bash
nano index.html
```

Paste:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Voting Eligibility</title>
</head>
<body>
    <h2>Voting Eligibility</h2>
    <form action="vote" method="post">
        Name: <input type="text" name="name" required><br><br>
        Age: <input type="number" name="age" required><br><br>
        <input type="submit" value="Check Eligibility">
    </form>
</body>
</html>
```

Save in Nano: **Ctrl+O**, press **Enter**, then **Ctrl+X**.

## Step 4 — Create `VoteServlet.java`

```bash
nano VoteServlet.java
```

Paste:

```java
package com;

import java.io.PrintWriter;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");

        try {
            PrintWriter out = response.getWriter();

            String name = request.getParameter("name");
            int age = Integer.parseInt(request.getParameter("age"));

            out.println("<html><body>");
            out.println("<h2>Name: " + name + "</h2>");
            out.println("<h2>Age: " + age + "</h2>");

            if (age > 18) {
                out.println("<h2>Eligible to vote</h2>");
            } else {
                out.println("<h2>Not eligible to vote</h2>");
            }

            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Save: **Ctrl+O**, press **Enter**, then **Ctrl+X**.

The `@WebServlet("/vote")` annotation maps the URL to this servlet, so `web.xml` is not needed. Since this uses Tomcat 9, keep the `javax.servlet` imports.

## Step 5 — Compile the servlet

Run this while inside the `voting-servlet` folder:

```bash
javac -cp "../../lib/*" -d WEB-INF/classes VoteServlet.java
```

If compilation succeeds, `javac` prints no errors. Check the compiled class:

```bash
ls WEB-INF/classes/com
```

Expected output:

```text
VoteServlet.class
```

## Step 6 — Check the folder structure

```text
voting-servlet/
├── index.html
├── VoteServlet.java
└── WEB-INF/
    └── classes/
        └── com/
            └── VoteServlet.class
```

There is no `web.xml` in this annotation-based version.

## Step 7 — Start or restart Tomcat

Return to the Tomcat root:

```bash
cd /mnt/c/Directory/coding/web-dev/Practical_Exam/temp/Servlet/apache-tomcat-9.0.87
```

If Tomcat is not already running, start it:

```bash
./bin/startup.sh
```

If you need to restart after recompiling, run:

```bash
./bin/shutdown.sh
./bin/startup.sh
```

Do not start a second Tomcat instance if it is already running.

## Step 8 — Test in the browser

Open:

**http://localhost:8080/voting-servlet/**

Enter a name and age, then click **Check Eligibility**.

Examples:

- Name: Arun, Age: 20 → **Eligible to vote**
- Name: Meena, Age: 18 → **Not eligible to vote**
- Name: Ravi, Age: 16 → **Not eligible to vote**

The condition is exactly as requested: age greater than 18 is eligible; age 18 or below is not eligible.

## Step 9 — Common mistakes

- **404 Not Found:** Verify the application folder name, compilation output, and `@WebServlet("/vote")`.
- **405 Method Not Allowed:** The form must use `method="post"` because the servlet implements `doPost()`.
- **Compile error:** Run `javac` from the `voting-servlet` folder and check that `../../lib/*` points to Tomcat's `lib` folder.
- **Cannot connect to `localhost:8080`:** Check whether Tomcat is running and whether another application occupies port 8080.
- **Tomcat 9:** Use `javax.servlet` imports, not `jakarta.servlet` imports.

## One-minute memory sheet

```bash
1. cd <tomcat-root>
2. mkdir -p webapps/voting-servlet/WEB-INF/classes
3. cd webapps/voting-servlet
4. nano index.html
5. nano VoteServlet.java
6. javac -cp "../../lib/*" -d WEB-INF/classes VoteServlet.java
7. ls WEB-INF/classes/com
8. cd <tomcat-root>
9. ./bin/startup.sh
10. Open http://localhost:8080/voting-servlet/
```

**Remember:** HTML form → POST `/vote` → `@WebServlet("/vote")` → `VoteServlet.doPost()` → display name, age, and voting eligibility.



