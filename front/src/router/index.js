import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'

Vue.use(VueRouter)

// 라우터 가드
const rejectAuthUser = (to, from, next) => {
  if(store.state.isLogin === true) {
    // 이미 로그인 된 유저
    alert("이미 로그인 하셨습니다")
    next("/")
  }else {
    next()
  }
}

const onlyAuthUser = (to, from, next) => {
  if(store.state.isLogin === false) {
    // 로그인 되지 않은 유저
    alert("로그인이 필요합니다")
    next("/")
  }else {
    next()
  }
}

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')
  },
  {
    path: '/:id/detail',
    name: 'Detail',
    component: () => import(/* webpackChunkName: "about" */ '../views/Detail.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    beforeEnter: rejectAuthUser,
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/mypage',
    name: 'MyPage',
    beforeEnter: onlyAuthUser,
    component: () => import(/* webpackChunkName: "about" */ '../views/MyPage.vue')
  },
  {
    path: '/*',
    redirect: { name: 'Home' }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
