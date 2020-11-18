import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import VeeValidate from 'vee-validate'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import './assets/style.css'
Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.config.productionTip = false

Vue.use(VeeValidate)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)
new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
