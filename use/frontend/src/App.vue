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

