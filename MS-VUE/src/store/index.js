import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import user from './modules/user'
import permission from './modules/permission'
import enums from './modules/enums'
import service from './modules/service'
import workRecord from './modules/workRecord'
import contract from './modules/contract'
import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    user,
    permission,
    enums,
    service,
    workRecord,
    contract
  },
  state: {
    totalMax: 9999999999,
    maxOrder: 1000000
  },
  mutations: {},
  actions: {},
  getters
})
