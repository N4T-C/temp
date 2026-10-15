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
