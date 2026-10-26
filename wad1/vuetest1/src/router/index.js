import { createRouter, createWebHistory } from 'vue-router'
import EmployeesView from '../views/EmployeesView.vue'
import AddEmployeeView from '../views/AddEmployeeView.vue'
import AnalyticsView from '../views/AnalyticsView.vue'
import AboutView from '../views/AboutView.vue'
import ContactView from '../views/ContactView.vue'

const routes = [
  {
    path: '/',
    component: EmployeesView,
    // Nested child routes underneath the Employee list (matches use/frontend pattern)
    children: [
      { path: 'about', component: AboutView },
      { path: 'contact', component: ContactView }
    ]
  },
  { path: '/add', component: AddEmployeeView },
  { path: '/analytics', component: AnalyticsView }
]

export default createRouter({
  history: createWebHistory(),
  routes
})
