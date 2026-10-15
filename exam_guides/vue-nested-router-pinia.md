## Variant 1: Nested Vue Router with Pinia Global State Management

### Project Structure

```text
frontend/
â”œâ”€â”€ index.html
â”œâ”€â”€ package.json
â”œâ”€â”€ vite.config.js
â””â”€â”€ src/
    â”œâ”€â”€ App.vue
    â”œâ”€â”€ main.js
    â”œâ”€â”€ router/
    â”‚   â””â”€â”€ index.js
    â”œâ”€â”€ stores/
    â”‚   â””â”€â”€ productStore.js
    â”œâ”€â”€ views/
    â”‚   â”œâ”€â”€ AboutView.vue
    â”‚   â”œâ”€â”€ CartView.vue
    â”‚   â”œâ”€â”€ ContactView.vue
    â”‚   â”œâ”€â”€ ProductsView.vue
    â”‚   â””â”€â”€ TeamView.vue
    â””â”€â”€ components/
        â”œâ”€â”€ CartItem.vue
        â””â”€â”€ ProductCard.vue
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
  "name": "use-frontend",
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
        {{ p.name }} - â‚¹{{ p.price }} (Stock: {{ p.stock }})
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
      {{ item.name }} - â‚¹{{ item.price }}
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
  {{ p.name }} - â‚¹{{ p.price }}
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


