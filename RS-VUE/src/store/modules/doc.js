/*
 * @Author: ldx
 * @Date: 2020-12-29 10:18:34
 * @LastEditTime: 2021-01-05 14:34:36
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\store\modules\Doc.js
 */
const doc = {
  state: {
    projectId: undefined,
    project: {},
    docId: undefined,
    file: {},
    stage: {}
  },
  mutations: {
    SET_PROJECT_ID (state, projectId) {
      state.projectId = projectId
    },
    SET_SELECTED_PROJECT (state, selectedProject) {
      state.project = selectedProject
    },
    SET_DOC_ID (state, docId) {
      state.docId = docId
    },
    SET_FILE (state, file) {
      state.file = file
    },
    SET_STAGE (state, stage) {
      state.stage = stage
    }
  },
  actions: {
    setProjectId ({ commit }, projectId) {
      commit('SET_PROJECT_ID', projectId)
    },
    setSelectedProject ({ commit }, selectedProject) {
      commit('SET_SELECTED_PROJECT', selectedProject)
    },
    setDocId ({ commit }, docId) {
      commit('SET_DOC_ID', docId)
    },
    setFile ({ commit }, file) {
      commit('SET_FILE', file)
    },
    setStage ({ commit }, stage) {
      commit('SET_STAGE', stage)
    }
  },
  getters: {
    getProjectId (state) {
      return state.projectId
    },
    getDocId (state) {
      return state.docId
    }
  }
}

export default doc
