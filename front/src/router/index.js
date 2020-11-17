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
    path: '/movie/list',
    name: 'MovieList',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/MovieList.vue')
  },
  {
    path: '/movie/:id',
    name: 'MovieDetail',
    component: () => import(/* webpackChunkName: "about" */ '../views/movie/MovieDetail.vue')
  },
  {
    path: '/user/login',
    name: 'UserLogin',
    beforeEnter: rejectAuthUser,
    component: () => import(/* webpackChunkName: "about" */ '../views/user/UserLogin.vue')
  },
  {
    path: '/user/mypage',
    name: 'UserMypage',
    beforeEnter: onlyAuthUser,
    component: () => import(/* webpackChunkName: "about" */ '../views/user/UserMypage.vue')
  },
  {
    path: '/user/join',
    name: 'UserJoin',
    component: () => import(/* webpackChunkName: "about" */ '../views/user/UserJoin.vue')
  },
  {
    path: '/board/list',
    name: 'BoardList',
    component: () => import(/* webpackChunkName: "about" */ '../views/board/BoardList.vue')
  },
  {
    path: '/board/:idx',
    name: 'BoardDetail',
    component: () => import(/* webpackChunkName: "about" */ '../views/board/BoardDetail.vue')
  },
  {
    path: '/board/write',
    name: 'BoardWrite',
    beforeEnter: onlyAuthUser,
    component: () => import(/* webpackChunkName: "about" */ '../views/board/BoardWrite.vue')
  },
  {
    path: '/board/modify',
    name: 'BoardModify',
    beforeEnter: onlyAuthUser,
    component: () => import(/* webpackChunkName: "about" */ '../views/board/BoardModify.vue')
  },
  {
    path: '/*',
    redirect: { name: 'MovieList' }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
