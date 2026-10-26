<script setup>
import { useEmployeeStore } from './stores/employeeStore'

const store = useEmployeeStore()
</script>

<template>
  <div>
    <h1>Employee & Payroll Management</h1>
    <nav>
      <router-link to="/">IT Employees</router-link> |
      <router-link to="/add">Add Employee</router-link> |
      <router-link to="/analytics">Analytics (HR Salary)</router-link>
    </nav>
    <hr />

    <router-view />
  </div>
</template>
