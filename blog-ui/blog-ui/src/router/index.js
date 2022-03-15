import Vue from 'vue'
import VueRouter from 'vue-router'
import Blogs from "@/views/Blogs";
import Login from "@/views/Login";
import BlogEdit from "@/views/BlogEdit";
import BlogDetail from "@/views/BlogDetail";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    // component: Blogs // 首页直接跳到 Blogs 页面
    redirect:{name: "Blogs"} // 通过重定向到 Blogs 页面上
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit
  },
  {
    path: '/blog/:blogId/',
    name: 'BlogDetail',
    component: BlogDetail
  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit
  },

  // {
  //   path: '/login',
  //   name: 'login',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
