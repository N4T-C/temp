<script setup>
import { ref } from 'vue'
import axios from 'axios'

const id = ref('')
const message = ref('')

async function deleteStudent() {
  await axios.delete(`/api/students/${id.value}`)
  id.value = ''
  message.value = 'Student deleted.'
}
</script>

<template>
  <section>
    <h2>Delete Student</h2>
    <form @submit.prevent="deleteStudent">
      <p><label>Student ID: <input v-model="id" required></label></p>
      <button type="submit">Delete</button>
    </form>
    <p>{{ message }}</p>
  </section>
</template>
