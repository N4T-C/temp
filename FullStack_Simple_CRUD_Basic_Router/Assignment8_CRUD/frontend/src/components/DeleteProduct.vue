<script setup>
import { ref } from 'vue'
import axios from 'axios'

const id = ref('')
const message = ref('')

async function deleteProduct() {
  await axios.delete(`/api/products/${id.value}`)
  id.value = ''
  message.value = 'Product deleted.'
}
</script>

<template>
  <section>
    <h2>Delete Product</h2>
    <form @submit.prevent="deleteProduct">
      <p><label>Product ID: <input v-model="id" required></label></p>
      <button type="submit">Delete</button>
    </form>
    <p>{{ message }}</p>
  </section>
</template>
