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
