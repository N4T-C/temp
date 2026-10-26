import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useEmployeeStore = defineStore('employee', () => {
  const itEmployees = ref([])
  const totalNetSalary = ref(0)
  const newEmployee = ref({
    employeeId: '',
    employeeName: '',
    department: 'IT',
    basicSalary: '',
    designation: ''
  })

  // 1. Fetch IT Employees
  async function fetchITEmployees() {
    try {
      const res = await fetch('http://localhost:8080/api/ITemployees')
      itEmployees.value = await res.json()
    } catch (e) {
      console.error(e)
    }
  }

  // 2. Fetch Total Net Salary (HR)
  async function fetchTotalNetSalary() {
    try {
      const res = await fetch('http://localhost:8080/api/analytics/totalNET')
      totalNetSalary.value = await res.json()
    } catch (e) {
      console.error(e)
    }
  }

  // 3. Add Employee (POST)
  async function addEmployee() {
    try {
      const res = await fetch('http://localhost:8080/api/employee/addNew', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newEmployee.value)
      })
      if (res.ok) {
        alert('Employee added successfully!')
        newEmployee.value = { employeeId: '', employeeName: '', department: 'IT', basicSalary: '', designation: '' }
        fetchITEmployees()
      }
    } catch (e) {
      console.error(e)
    }
  }

  return {
    itEmployees,
    totalNetSalary,
    newEmployee,
    fetchITEmployees,
    fetchTotalNetSalary,
    addEmployee
  }
})
