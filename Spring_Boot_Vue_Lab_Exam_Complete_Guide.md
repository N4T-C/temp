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
