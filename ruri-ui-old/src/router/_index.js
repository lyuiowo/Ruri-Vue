import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../_views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '首页',
      component: HomeView
    },
    {
      path: '/about',
      name: '关于',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../_views/AboutView.vue')
    },
    {
      path: '/my',
      name: '我的',
      component: () => import('../_views/MyView.vue')
    },
    {
      path: '/login',
      name: '登录',
      component: () => import('../_views/LoginView.vue')
    },
    {
      path: '/register',
      name: '注册',
      component: () => import('../_views/RegisterView.vue')
    }
  ]
})

export default router
