import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import user from './modules/user'
import sysDictionary from './modules/sysDictionary'
import permission from './modules/permission'
import getters from './getters'
import doc from './modules/doc'
import enums from './modules/enums'
import common from './modules/common'
import { setStore } from '@/utils/storage'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    user,
    permission,
    sysDictionary,
    doc,
    enums,
    common
  },
  state: {
    currentYear: 0,
    auditStatus: 0,
    totalMax: 9999999999,
    project: undefined,
    maxOrder: 1000000,
    generalLedgerYear: 2021 // 辅助帐新版开始年份
  },
  mutations: {
    CHANGE_CURRENT_YEAR (state, y) {
      state.currentYear = y
      setStore('RS_CURRENT_YEAR' + user.state.info.companyId, y)
    },
    SET_AUDIT_STATUS (state, auditStatus) {
      state.auditStatus = auditStatus
    },
    SET_PROJECT_INFO (state, projectInfo) {
      state.project = projectInfo
    }
  },
  actions: {
    changeYear ({ commit }, y) {
      commit('CHANGE_CURRENT_YEAR', y)
    },
    setAuditStatus ({ commit }, auditStatus) {
      commit('SET_AUDIT_STATUS', auditStatus)
    },
    setProjectInfo ({ commit }, projectInfo) {
      commit('SET_PROJECT_INFO', projectInfo)
    }
  },
  getters
})
