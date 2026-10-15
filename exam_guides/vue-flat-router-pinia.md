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

