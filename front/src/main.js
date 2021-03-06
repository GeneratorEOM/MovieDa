import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import '@babel/polyfill'
import axios from 'axios'
import BootstrapVue from 'bootstrap-vue'


Vue.use(BootstrapVue)

Vue.prototype.$axios = axios


Vue.config.productionTip = false

export const eventBus = new Vue()

new Vue({
  router,
  store,
  vuetify,
  beforeCreate() {
    this.$store.dispatch('loginCheck')
  },
  render: h => h(App)
}).$mount('#app')
