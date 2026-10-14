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
