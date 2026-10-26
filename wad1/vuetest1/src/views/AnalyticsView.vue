<script setup>
import { onMounted } from 'vue'
import { useEmployeeStore } from '../stores/employeeStore'

const store = useEmployeeStore()
onMounted(() => store.fetchTotalNetSalary())
</script>

<template>
  <div>
    <h2>Payroll Analytics</h2>
    <p>Total Net Salary for HR Employees: {{ store.totalNetSalary }}</p>
    <button @click="store.fetchTotalNetSalary">Refresh Total</button>
  </div>
</template>
