import { defineStore } from 'pinia'
import { axios } from '../utils/http'
import * as dd from 'dingtalk-jsapi'

const getDDCode = async () => {
  /**
   * 等dd加载完
  const ready = new Promise((resolve) => {
    dd.ready(resolve)
  })
  await ready
  */
  return dd.runtime.permission
    .requestAuthCode({
      corpId: 'ding543f60a6e3104cf835c2f4657eb6378f'
    })
    .then((res) => {
      return { success: true, data: res }
    })
    .catch((err) => {
      return Promise.resolve({
        success: false,
        errorMessage: dd.env.platform === 'notInDingTalk' ? '当前功能只适合在钉钉手机端用' : err.errorMessage
      })
    })
}

const loginByCode = async () => {
  const ddResult = await getDDCode()
  if (ddResult.success) {
    return axios.post('/index/loginByCode', ddResult.data).catch((err) => {
      return Promise.resolve({
        success: false,
        errorMessage: err.message
      })
    })
  }
  return ddResult
}

const loginByUnionId = async () => {
  return axios.post('/index/loginByUnionId', { code: 'LTYSZObwyruftL30xIl5SgiEiE' }).catch((err) => {
    return Promise.resolve({
      success: false,
      errorMessage: err.message
    })
  })
}

const login = import.meta.env.MODE === 'development' ? loginByUnionId : loginByCode

const getUserInfo = () => {
  return axios.get('/index/getInfo')
}

export const useUserStore = defineStore('app_user', {
  state: () => {
    return {
      info: undefined,
      permDataMap: undefined
    }
  },
  actions: {
    async getInfo() {
      const token = localStorage.getItem('token')
      let result = { success: false }
      if (token) {
        result = await getUserInfo()
      }
      if (!result.success) {
        const loginResult = await login()
        if (loginResult.success) {
          localStorage.setItem('token', loginResult.data)
          result = await getUserInfo()
        } else {
          result = loginResult
        }
      }
      if (result.success) {
        const { avatar, realName, permDataMap, userName } = result.data.user
        this.info = { avatar, realName, userName }
        this.permDataMap = permDataMap
      }
      return result
    }
  }
})
