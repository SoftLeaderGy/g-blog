import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    userInfo: JSON.parse(sessionStorage.getItem("userinfo"))
  },
  getters: {
    // 类似于java中的 get()
    getUser: state => {
      return state.userInfo
    },
    getToken: state => {
      return state.token
    }
  },
  mutations: {
    // 类似于Java中的 set()
    SET_TOKEN: (state, token) => {
      // 赋值给全局变量
      state.token = token
      // 将token存储在本地的浏览器的localStorage 中，保证浏览器关闭的时候我们的token还在
      localStorage.setItem("token",token)
    },

    SET_USERINFO: (state, userInfo) => {
      // 赋值给全局变量
      state.userInfo = userInfo
      // 将token存储在本地的浏览器的localStorage 中，保证浏览器关闭的时候我们的token还在
      localStorage.setItem("userInfo",JSON.stringify(userInfo))
      sessionStorage.setItem("userInfo",userInfo)
    },

    // 删除之后，要把全局变量和localStorage存的变量
    REMOVE_INFO: (state) => {
      state.userInfo = ''
      state.userInfo = {}
      localStorage.setItem("userInfo",'')
      sessionStorage.setItem("userInfo",'')
      localStorage.setItem("token",'')
    }
    },
  actions: {
  },
  modules: {
  }
})
