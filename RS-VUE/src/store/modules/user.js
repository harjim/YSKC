import Vue from 'vue'
import { login, getInfo } from '@/api/login'
import { ACCESS_TOKEN, ISADMIN } from '@/store/mutation-types'
import { axios } from '@/utils/request'
import { getStore } from '@/utils/storage'

const user = {
  state: {
    token: '',
    name: '',
    avatar: '',
    info: '',
    companyId: ''
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_COMPANYID: (state, companyId) => {
      state.companyId = companyId
    },
    SET_NAME: (state, { name }) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      // 设置当前是否为MS登录
      Vue.ls.set(ISADMIN, userInfo.isAdmin)
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          if (response.success) {
            const result = response.data
            Vue.ls.set(ACCESS_TOKEN, result.token, 7 * 24 * 60 * 60 * 1000)
            commit('SET_TOKEN', result.token)
            commit('SET_COMPANYID', result.companyId)
            resolve(result)
          } else {
            reject(new Error(response.errorMessage))
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          if (response.success) {
            const userInfo = response.data.user
            userInfo.avatar = userInfo.avatar || '/avatar.jpg'
            commit('SET_INFO', userInfo)
            commit('SET_NAME', { name: userInfo.realName })
            commit('SET_AVATAR', userInfo.avatar)
            // 根据用户信息取的当前公司默认年
            const storeYear = getStore('RS_CURRENT_YEAR' + userInfo.companyId)
            const currentYear = storeYear || new Date().getFullYear()
            commit('CHANGE_CURRENT_YEAR', currentYear, { root: true })
          } else if (response.errorCode === '1007') {
            // 登录过期
            commit('SET_TOKEN', '')
            Vue.ls.remove(ACCESS_TOKEN)
          } else {
            reject(new Error(response.errorMessage))
          }
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        axios.post('/user/logout').then(() => {
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_COMPANYID', '')
          Vue.ls.remove(ACCESS_TOKEN)
        }
        )
      })
    }

  }
}

export default user
