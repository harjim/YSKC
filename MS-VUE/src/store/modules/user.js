import Vue from 'vue'
import { login, getInfo, logout, loginByCode } from '@/api/login'
import { ACCESS_TOKEN } from '@/store/mutation-types'

function doLogin (commit, params, method) {
  return new Promise((resolve, reject) => {
    method(params).then(response => {
      if (response.success) {
        const token = response.data
        Vue.ls.set(ACCESS_TOKEN, token, 7 * 24 * 60 * 60 * 1000)
        commit('SET_TOKEN', token)
        resolve(response)
      } else {
        reject(new Error(response.errorMessage))
      }
    }).catch(error => {
      reject(error)
    })
  })
}
const user = {
  state: {
    token: '',
    realName: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { realName }) => {
      state.realName = realName
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    setToken ({ commit }, token) {
      commit('SET_TOKEN', token)
    },
    // 登录
    Login ({ commit }, userInfo) {
      return doLogin(commit, userInfo, login)
    },
    LoginByCode ({ commit }, userInfo) {
      return doLogin(commit, userInfo, loginByCode)
    },
    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          if (response.success) {
            const data = response.data
            data.user.avatar = data.user.avatar || '/avatar.jpg'
            commit('SET_INFO', data.user)
            commit('SET_NAME', { realName: data.user.realName })
            commit('SET_AVATAR', data.user.avatar)
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
        logout(state.token).then(() => {
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          Vue.ls.remove(ACCESS_TOKEN)
        })
      })
    }

  }
}

export default user
