<script setup>
import { ref } from 'vue'
import axios from 'axios'

const id = ref('')
const name = ref('')
const age = ref('')
const course = ref('')
const message = ref('')

async function updateStudent() {
  await axios.put(`/api/students/${id.value}`, {
    name: name.value,
    age: Number(age.value),
    course: course.value
  })
  message.value = 'Student updated.'
}
</script>

<template>
  <section>
    <h2>Update Student</h2>
    <form @submit.prevent="updateStudent">
      <p><label>Student ID: <input v-model="id" required></label></p>
      <p><label>Name: <input v-model="name" required></label></p>
      <p><label>Age: <input v-model="age" type="number" required></label></p>
      <p><label>Course: <input v-model="course" required></label></p>
      <button type="submit">Update</button>
    </form>
    <p>{{ message }}</p>
  </section>
</template>
