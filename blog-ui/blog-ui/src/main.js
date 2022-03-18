import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './axios.js' // 请求拦截

// 全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

// 引入 element
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"

import axios from 'axios'
import './permission'
Vue.config.productionTip = false
Vue.prototype.$axios = axios

// 使用mavonEditor编辑器
Vue.use(mavonEditor)
// 设置全局使用
Vue.use(Element)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
