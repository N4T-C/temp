
<template>
  <div>
    <h1>Employee List</h1>

    <button @click="getEmployees">Load Employees</button>

    <ul>
      <li v-for="employee in employees" :key="employee.employeeId">
        ID: {{ employee.employeeId }} |
        Name: {{ employee.employeeName }} |
        Department: {{ employee.department }} |
        Salary: {{ employee.basicSalary }} |
        Designation: {{ employee.designation }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      employees: []
    }
  },

  mounted() {
    this.getEmployees()
  },

  methods: {
    async getEmployees() {
      const response = await fetch(
        'http://localhost:8080/api/ITemployees'
      )

      this.employees = await response.json()
    }
  }
}
</script>
