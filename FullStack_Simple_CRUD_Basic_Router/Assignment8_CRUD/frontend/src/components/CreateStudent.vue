<script setup>
import { ref } from 'vue'
import axios from 'axios'

const name = ref('')
const age = ref('')
const course = ref('')
const message = ref('')

async function createStudent() {
  await axios.post('/api/students', {
    name: name.value,
    age: Number(age.value),
    course: course.value
  })
  name.value = ''
  age.value = ''
  course.value = ''
  message.value = 'Student created.'
}
</script>

<template>
  <section>
    <h2>Create Student</h2>
    <form @submit.prevent="createStudent">
      <p><label>Name: <input v-model="name" required></label></p>
      <p><label>Age: <input v-model="age" type="number" required></label></p>
      <p><label>Course: <input v-model="course" required></label></p>
      <button type="submit">Create</button>
    </form>
    <p>{{ message }}</p>
  </section>
</template>
