<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const products = ref([])

onMounted(async () => {
  const response = await axios.get('/api/products')
  products.value = response.data
})
</script>

<template>
  <section>
    <h2>Read Products</h2>
    <table border="1" cellpadding="6">
      <thead>
        <tr><th>ID</th><th>Name</th><th>Price</th><th>Quantity</th></tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.id }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.price }}</td>
          <td>{{ product.quantity }}</td>
        </tr>
      </tbody>
    </table>
  </section>
</template>
