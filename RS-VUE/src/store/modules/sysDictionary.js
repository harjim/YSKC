import { axios } from '@/utils/request'
export function getDictionary (parameter) {
  return axios({
    url: '/sysDictionary/getDictionary',
    method: 'get',
    params: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
export function getHighTecIndustryModels (parameter) {
  return axios({
    url: '/sysDictionary/getHighTecIndustryModels',
    method: 'get',
    params: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
const sysDictionary = {
  state: {
    addressCode: [],
    highTecIndustry: [],
    highTecIndustryTree: []
  },
  mutations: {
    setAddressCode: (state, info) => {
      state.addressCode = info
    },
    setHighTecIndustry: (state, info) => {
      state.highTecIndustry = info
    },
    setHighTecIndustryTree: (state, info) => {
      state.highTecIndustryTree = info
    }
  },
  getters: {
    addressCode: state => state.addressCode,
    highTecIndustry: state => state.highTecIndustry
  },
  actions: {
    GetHighTecIndustryTree ({ commit }) {
      return new Promise((resolve, reject) => {
        getDictionary({ type: 2 }).then(response => {
          if (response.success) {
            const result = response.data
            commit('setHighTecIndustryTree', result)
            resolve(result)
          } else {
            resolve([])
          }
        }).catch(() => {
          resolve([])
        })
      })
    },
    GetHighTecIndustry ({ commit }) {
      return new Promise((resolve, reject) => {
        getHighTecIndustryModels().then(response => {
          if (response.success) {
            const result = response.data
            commit('setHighTecIndustry', result)
            resolve(result)
          } else {
            resolve([])
          }
        }).catch(() => {
          resolve([])
        })
      })
    },
    GetAddressCode ({ commit }) {
      return new Promise((resolve, reject) => {
        getDictionary({ type: 1 }).then(response => {
          if (response.success) {
            const result = response.data
            commit('setAddressCode', result)
            resolve(result)
          } else {
            resolve([])
          }
        }).catch(() => {
          resolve([])
        })
      })
    }
  }
}
export default sysDictionary
