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

