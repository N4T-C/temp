<script setup>
import { ref } from 'vue'
import axios from 'axios'

const id = ref('')
const name = ref('')
const price = ref('')
const quantity = ref('')
const message = ref('')

async function updateProduct() {
  await axios.put(`/api/products/${id.value}`, {
    name: name.value,
    price: Number(price.value),
    quantity: Number(quantity.value)
  })
  message.value = 'Product updated.'
}
</script>

<template>
  <section>
    <h2>Update Product</h2>
    <form @submit.prevent="updateProduct">
      <p><label>Product ID: <input v-model="id" required></label></p>
      <p><label>Name: <input v-model="name" required></label></p>
      <p><label>Price: <input v-model="price" type="number" step="0.01" required></label></p>
      <p><label>Quantity: <input v-model="quantity" type="number" required></label></p>
      <button type="submit">Update</button>
    </form>
    <p>{{ message }}</p>
  </section>
</template>
