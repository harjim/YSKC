// ie polyfill
import '@babel/polyfill'

import Vue from 'vue'
// import Vue2OrgTree from 'vue2-org-tree'
import App from './App.vue'
import router from './router'
import store from './store/'
import { VueAxios } from './utils/request'
import myPlugin from './utils/myPlugin'
import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/index.css'
import VueParticles from 'vue-particles'

// mock
// import './mock'

import bootstrap from './core/bootstrap'
import './core/use'
import './permission' // permission control
import './utils/filter' // global filter

Vue.config.productionTip = false

// Vue.use(Vue2OrgTree)
// mount axios Vue.$http and this.$http
Vue.use(VueParticles)
Vue.use(VueAxios)
Vue.use(myPlugin)
Vue.use(VXETable)

new Vue({
  router,
  store,
  created () {
    bootstrap()
  },
  render: h => h(App)
}).$mount('#app')
