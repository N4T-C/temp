import { createRouter, createWebHistory } from 'vue-router'
import ProductsView from '../views/ProductsView.vue'
import CartView from '../views/CartView.vue'
import AboutView from '../views/AboutView.vue'
import ContactView from '../views/ContactView.vue'

const routes = [
  { 
    path: '/', 
    component: ProductsView,
    // Nested child routes rendered right underneath the Products list
    children: [
      { path: 'about', component: AboutView },
      { path: 'contact', component: ContactView }
    ]
  },
  { path: '/cart', component: CartView }
]

export default createRouter({
  history: createWebHistory(),
  routes
})
