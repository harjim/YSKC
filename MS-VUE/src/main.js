// ie polyfill
import '@babel/polyfill'

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/'
import { VueAxios } from './utils/request'
import myPlugin from './utils/myPlugin'
import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/index.css'

import VeLine from 'v-charts/lib/line.common'
import VeBar from 'v-charts/lib/bar.common'
import VeHistogram from 'v-charts/lib/histogram.common'
import VePie from 'v-charts/lib/pie.common'
import 'v-charts/lib/style.css'
import './assets/css/customPrint.css'
// mock
// import './mock'
import bootstrap from './core/bootstrap'
import './core/use'
import './permission' // permission control
import './utils/filter' // global filter
import Print from './utils/print/index'
import directive from '@/utils/directive'

Vue.use(VXETable)
Vue.config.productionTip = false

// mount axios Vue.$http and this.$http
Vue.use(Print)
Vue.use(VueAxios)
Vue.use(myPlugin)
Vue.component(VeLine.name, VeLine)
Vue.component(VeBar.name, VeBar)
Vue.component(VeHistogram.name, VeHistogram)
Vue.component(VePie.name, VePie)

Object.keys(directive).forEach(key => {
  Vue.directive(`${key}`, directive[key])
})

new Vue({
  router,
  store,
  created () {
    bootstrap()
  },
  render: h => h(App)
}).$mount('#app')
