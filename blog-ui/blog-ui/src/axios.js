import axios from 'axios'
import Element from 'element-ui'

import router from './router'
import store from './store'

axios.defaults.baseURL = "http://localhost:8090/"

// axios请求的前置拦截
axios.interceptors.request.use(config => {
    console.log("请求前置拦截=============")
    return config
})


axios.interceptors.response.use(response => {
    console.log("请求后置拦截=============")
    let res = response.data
    console.log("===============")
    console.log("返回结果 ===>" + res)
    console.log("===============")
    if(res.code === 200){
        return response
    } else {
        Element.Message.error(res.msg)
        return Promise.reject(response.data.msg)  // 如果报错 并且拦截到错误以后，直接结束这个请求，程序不回往下走了,防止后续逻辑报错。
    }
    // return response
},

    // 拦截后台报错
error =>{
    debugger
    console.log(error)
    // 如果是401 证明是没有权限，需要跳转到登录页面，并清空浏览器的缓存的用户信息，及token
    if(error.response.data.code === 401){
        Element.Message.error(error.response.msg)
        store.commit("REMOVE_INFO") // 删除浏览器的本地信息
        router.push("/login") // 返回登陆页面
    }

    Element.Message.error(error.response.data.msg)
    return Promise.reject(error) // 结束请求后续逻辑
    }
)
