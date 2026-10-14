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
