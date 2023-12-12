import './assets/main.css'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router/index'

const app = createApp(App)

app.use(ElementPlus, {
	darkMode: {
		enabled: false, // 禁用 Element Plus 的暗黑模式
		preference: 'no-preference' // 禁用 Vue 的暗黑模式
	}
})
app.use(router)

app.mount('#app')
