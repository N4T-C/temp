# Natuse Full-Stack Lab Exam Reference Guide

Backend Base URL: `http://localhost:8080/api/products`  
Frontend Base URL: `http://localhost:5173/`

---

## 📌 TABLE OF CONTENTS
1. [What is Currently Running (Pinia + Vue Router + Nested Routing)](#1-what-is-currently-running)
2. [What to Do if Faculty Says: "NO Pinia, Only Props and $emit"](#2-alternative-a-no-pinia-only-props-and-emit)
3. [What to Do if Faculty Says: "NO Nested Routing, Flat Routes Only"](#3-alternative-b-flat-routing-no-nested-children)
4. [What to Do if Faculty Says: "NO Routing At All, Single File Only"](#4-alternative-c-single-file-appvue-only)
5. [Complete Postman CRUD Endpoints & JSON Body](#5-postman-crud-reference)

---

## 1. What is Currently Running
Your frontend currently has:
* **Pinia Global Store** (`src/stores/productStore.js`) managing products and cart state.
* **Top-Level Routes**: `/` (Products), `/cart` (Cart), `/about` (About).
* **Nested Child Routes**: `/about/team` and `/about/contact` rendered inside `AboutView.vue`'s `<router-view />`.

### How Nested Routing Works in Code:
In `src/router/index.js`:
```javascript
{
  path: '/about',
  component: AboutView,
  children: [
    { path: 'team', component: TeamView },       // URL: /about/team
    { path: 'contact', component: ContactView }   // URL: /about/contact
  ]
}
```
Inside `AboutView.vue`, there is a second `<router-view />` where the child component gets injected.

---

## 2. Alternative A: "NO Pinia, Only Props and $emit"
If the teacher asks for standard parent-to-child component communication without Pinia:

### Concept:
* **Parent (`App.vue`)** owns the data (`products = []`, `cart = []`).
* **Parent -> Child:** Passes data down using `:product="p"` (Props).
* **Child -> Parent:** Sends actions up using `@click="$emit('add-to-cart', product)"` (Custom Events).

### Child Component (`ProductCard.vue` - 8 lines):
```vue
<template>
  <div>
    <span>{{ product.name }} - ₹{{ product.price }}</span>
    <button @click="$emit('add-to-cart', product)">Add</button>
  </div>
</template>
<script>
export default { props: ['product'] }
</script>
```

### Parent Component (`App.vue`):
```vue
<template>
  <ProductCard 
    v-for="p in products" 
    :key="p.id" 
    :product="p" 
    @add-to-cart="cart.push($event)" 
  />
</template>
<script setup>
import { ref, onMounted } from 'vue'
import ProductCard from './components/ProductCard.vue'

const products = ref([])
const cart = ref([])

onMounted(async () => {
  const res = await fetch('http://localhost:8080/api/products')
  products.value = await res.json()
})
</script>
```

---

## 3. Alternative B: "Flat Routing (No Nested Children)"
If the teacher wants pages/routes, but asks you not to use nested `children`:

Just make all routes top-level in `src/router/index.js`:
```javascript
const routes = [
  { path: '/', component: ProductsView },
  { path: '/cart', component: CartView },
  { path: '/about', component: AboutView },
  { path: '/contact', component: ContactView }
]
```
And link directly to them in `App.vue`:
```html
<router-link to="/">Products</router-link> |
<router-link to="/cart">Cart</router-link> |
<router-link to="/about">About</router-link> |
<router-link to="/contact">Contact</router-link>
```

---

## 4. Alternative C: "Single-File App.vue (No Router, No Pinia)"
The absolute minimal 20-line single-file script:
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

---

## 5. Postman CRUD Reference

| Operation | Method | URL | Body (raw JSON) |
|---|---|---|---|
| **Get All** | `GET` | `http://localhost:8080/api/products` | *(None)* |
| **Create** | `POST` | `http://localhost:8080/api/products` | `{"name":"Headphones","price":1999.0,"stock":10}` |
| **Get One** | `GET` | `http://localhost:8080/api/products/<id>` | *(None)* |
| **Update** | `PUT` | `http://localhost:8080/api/products/<id>` | `{"name":"Headphones v2","price":2499.0,"stock":8}` |
| **Delete** | `DELETE`| `http://localhost:8080/api/products/<id>` | *(None)* |
| **Search** | `GET` | `http://localhost:8080/api/products/search?name=Head` | *(None)* |

> ⚠️ **IMPORTANT:** Never put curly braces `{}` around the ID in the URL!  
> ✅ `http://localhost:8080/api/products/6acc5cc42577d09cacd3c23e`
