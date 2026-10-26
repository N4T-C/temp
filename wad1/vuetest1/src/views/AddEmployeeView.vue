<script setup>
import { useEmployeeStore } from '../stores/employeeStore'

const store = useEmployeeStore()
</script>

<template>
  <div>
    <h2>Add New Employee</h2>
    <form @submit.prevent="store.addEmployee">
      <div>
        <label>Employee ID: </label>
        <input v-model="store.newEmployee.employeeId" required />
      </div>
      <div>
        <label>Employee Name: </label>
        <input v-model="store.newEmployee.employeeName" required />
      </div>
      <div>
        <label>Department: </label>
        <input v-model="store.newEmployee.department" required />
      </div>
      <div>
        <label>Basic Salary: </label>
        <input v-model="store.newEmployee.basicSalary" required />
      </div>
      <div>
        <label>Designation: </label>
        <input v-model="store.newEmployee.designation" required />
      </div>
      <div>
        <button type="submit">Submit</button>
      </div>
    </form>
  </div>
</template>
