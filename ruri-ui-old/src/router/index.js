import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('../views/Index.vue')
    },
    {
      path: '/reader',
      name: 'reader',
      component: () => import('../views/Reader.vue')
    }
  ]
})

export default router
