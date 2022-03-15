import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


// 引入 element
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"

import axios from 'axios'
Vue.config.productionTip = false
Vue.prototype.$axios = axios

// 设置全局使用
Vue.use(Element)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
