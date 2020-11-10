import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: null,
    isLogin: false,
    isLoginError: false,
  },
  mutations: {
    // 로그인이 성공했을 때,
    loginSuccess(state, payload) {
      state.isLogin = true
      state.isLoginError = false
      state.userInfo = payload
    },
    // 로그인이 실패했을 때,
    loginError(state) {
      state.isLogin = false
      state.isLoginError = true
    },
    logout(state) {
      state.isLogin = false
      state.isLoginError = false
      state.userInfo = null
    }

  },
  actions: {

    login({ commit }, loginObj) {
      // Login.vue 에서 가져온 loginObj 를 파라미터로 서버에 전송
      // 토큰을 이용하여 로그인 처리를 해야 하지만 그건 나중에 ..... 
      let config = {     
          email: loginObj.email,
          password: loginObj.password    
      }
      // axios.post 사용해서 서버로 post 방식 요청 content-type: json
      axios.post('http://localhost:8080/api/login', config)
        .then(res => {
          console.log(res)
          // 아이디, 비번이 일치하지 않는다면 서버에서 0 을 리턴
          if (res.data === "") {
            commit('loginError')
          }else { 
            let userInfo = {
              idx: res.data.idx,
              email: res.data.email,
              password: res.data.password,
              name: res.data.name,
              gender: res.data.gender
            }  
            commit('loginSuccess', userInfo)
            sessionStorage.setItem("token", res.data)
            // 로그인 처리 후 홈으로 이동
            router.push({
              name: 'Home'
            })
          }

        })
        // 요청 실패
        .catch(err => {
          console.log(err)
        })
    },

    logout({ commit }) {
      // 로그아웃
      commit('logout')
      // 세션 삭제
      sessionStorage.removeItem("token")
      // 홈 이동
      router.push({
        name: 'Home'
      })
    },
    // 새로고침할때 로그인 유지하기   
    loginCheck({commit}) {
      // 세션에 있는 토큰 저장
      let email = sessionStorage.getItem('token') 
      // 토큰이 존재하면 로그인 됨
      // 로그인 처리
      let config = {
        params: {
          "token": email
        }
      }
      if(email != null) {
        axios.get('http://localhost:8080/api/user', config)
          .then(res => {
            console.log(res)      
            let userInfo = {
              idx: res.data.idx,
              email: res.data.email,
              password: res.data.password,
              name: res.data.name,
              gender: res.data.gender
            }
            
            commit('loginSuccess', userInfo)
          })
          .catch(err => {
            console.log(err)
          })
      }
    }
  },
  modules: {
  }
})
