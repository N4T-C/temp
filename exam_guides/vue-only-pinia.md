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

