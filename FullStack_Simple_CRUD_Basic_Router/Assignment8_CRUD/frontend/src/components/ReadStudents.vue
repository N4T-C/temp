<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const students = ref([])

onMounted(async () => {
  const response = await axios.get('/api/students')
  students.value = response.data
})
</script>

<template>
  <section>
    <h2>Read Students</h2>
    <table border="1" cellpadding="6">
      <thead>
        <tr><th>ID</th><th>Name</th><th>Age</th><th>Course</th></tr>
      </thead>
      <tbody>
        <tr v-for="student in students" :key="student.id">
          <td>{{ student.id }}</td>
          <td>{{ student.name }}</td>
          <td>{{ student.age }}</td>
          <td>{{ student.course }}</td>
        </tr>
      </tbody>
    </table>
  </section>
</template>
