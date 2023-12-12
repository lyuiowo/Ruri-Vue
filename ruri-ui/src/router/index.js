import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: '/',
			name: 'index',
			component: () => import('../views/IndexView.vue')
		},
		{
			path: '/idea',
			name: 'idea',
			component: () => import('../views/IdeaView.vue')
		},
		{
			path: '/square',
			name: 'square',
			component: () => import('../views/SquareVue.vue')
		},
		{
			path: '/about',
			name: 'about',
			component: () => import('../views/About.vue')
		},
		{
			path: '/writer',
			name: 'writer',
			component: () => import('../views/WriterView.vue'),
			beforeEnter: (to, from, next) => {
				document.querySelector('.navigation-wrapper').style.display = 'none'
				next()
			}
		}
	]
})

export default router
