import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'
import CreateProduct from './components/CreateProduct.vue'
import ReadProducts from './components/ReadProducts.vue'
import UpdateProduct from './components/UpdateProduct.vue'
import DeleteProduct from './components/DeleteProduct.vue'
import CreateStudent from './components/CreateStudent.vue'
import ReadStudents from './components/ReadStudents.vue'
import UpdateStudent from './components/UpdateStudent.vue'
import DeleteStudent from './components/DeleteStudent.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', redirect: '/read' },
    { path: '/create', component: CreateProduct },
    { path: '/read', component: ReadProducts },
    { path: '/update', component: UpdateProduct },
    { path: '/delete', component: DeleteProduct },
    { path: '/students/create', component: CreateStudent },
    { path: '/students/read', component: ReadStudents },
    { path: '/students/update', component: UpdateStudent },
    { path: '/students/delete', component: DeleteStudent }
  ]
})

createApp(App).use(router).mount('#app')
