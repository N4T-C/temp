<script setup>
import { ref } from 'vue'
import axios from 'axios'

const name = ref('')
const price = ref('')
const quantity = ref('')
const message = ref('')

async function createProduct() {
  await axios.post('/api/products', {
    name: name.value,
    price: Number(price.value),
    quantity: Number(quantity.value)
  })
  name.value = ''
  price.value = ''
  quantity.value = ''
  message.value = 'Product created.'
}
</script>

<template>
  <section>
    <h2>Create Product</h2>
    <form @submit.prevent="createProduct">
      <p><label>Name: <input v-model="name" required></label></p>
      <p><label>Price: <input v-model="price" type="number" step="0.01" required></label></p>
      <p><label>Quantity: <input v-model="quantity" type="number" required></label></p>
      <button type="submit">Create</button>
    </form>
    <p>{{ message }}</p>
  </section>
</template>
