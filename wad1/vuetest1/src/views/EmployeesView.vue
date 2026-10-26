<script setup>
import { onMounted } from 'vue'
import { useEmployeeStore } from '../stores/employeeStore'

const store = useEmployeeStore()
onMounted(() => store.fetchITEmployees())
</script>

<template>
  <div>
    <h2>IT Employees ({{ store.itEmployees.length }})</h2>
    <button @click="store.fetchITEmployees">Refresh List</button>

    <ul v-if="store.itEmployees.length > 0">
      <li v-for="emp in store.itEmployees" :key="emp.employeeId">
        ID: {{ emp.employeeId }} | Name: {{ emp.employeeName }} | Designation: {{ emp.designation }} | Salary: {{ emp.basicSalary }}
      </li>
    </ul>
    <p v-else>No IT employees found.</p>

    <hr />

    <router-link to="/about">About</router-link> |
    <router-link to="/contact">Contact</router-link>

    <router-view />
  </div>
</template>
