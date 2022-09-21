import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import moment from 'moment'
import {
  VueAxios
} from './axios'
import notification from 'ant-design-vue/es/notification'
import message from 'ant-design-vue/es/message'
import {
  ACCESS_TOKEN,
  ISADMIN
} from '@/store/mutation-types'
const Qs = require('qs')
const baseUrl = '/api/' //             process.env.NODE_ENV === 'production' && process.env.VUE_APP_PREVIEW !== 'true' ? '/api/' : 'http://localhost: 8001/api/'
// 创建 axios 实例
const service = axios.create({
  baseURL: baseUrl, // api base_url
  timeout: 180000, // 请求超时时间
  headers: {
    'Cache-Control': 'no-cache',
    'Pragma': 'no-cache'
  }
})

// 创建导出axios 实例，【返回response，可调用response.headers[]】
const exportServe = axios.create({
  baseURL: baseUrl,
  timeout: 600000 // 请求超时时间 (10分钟响应)
})

export function getBaseUrl () {
  return baseUrl
}
function err (error) {
  if (error.response) {
    const data = error.response.data
    const token = Vue.ls.get(ACCESS_TOKEN)

    if (error.response.status === 403) {
      notification.error({
        message: '错误',
        description: data.errorMessage
      })
      store.dispatch('Logout').then(() => {
        setTimeout(() => {
          window.location.reload()
        }, 1500)
      })
    } else if (error.response.status === 401) {
      const isAdmin = Vue.ls.get(ISADMIN)
      if (isAdmin) {
        message.warning('登录失效,请从我的客户重新进入!', 5)
        return Promise.reject(error)
      } else if (!(data.result && data.result.isLogin)) {
        if (token && error.config.url.indexOf('/user/info') === -1) {
          store.dispatch('Logout').then(() => {
            setTimeout(() => {
              window.location.reload()
            }, 1500)
          })
        }
      }
    }
  }
  return Promise.reject(error)
}

function requestConfig (config) {
  const token = Vue.ls.get(ACCESS_TOKEN)
  // const isAdmin = Vue.ls.get(ISADMIN)
  // if (isAdmin) {
  config.headers['CompanyId'] = store.getters.companyId
  // }
  if (token) {
    config.headers['Access-Token'] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  if (config.method === 'post' && config.data && !Array.isArray(config.data)) {
    const data = { ...config.data }
    for (const key in data) {
      if (moment.isMoment(data[key])) {
        data[key] = data[key].format('YYYY-MM-DD HH:mm:ss')
      } else if (typeof data[key] === 'string') {
        data[key] = data[key].trim()
      }
      config.data = data
    }
  } else if (config.method === 'get' && config.params) {
    config.paramsSerializer = function (params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
    if (!Array.isArray(config.params)) {
      const params = { ...config.params }
      for (const key in params) {
        if (moment.isMoment(params[key])) {
          params[key] = params[key].format('YYYY-MM-DD HH:mm:ss')
        }
      }
      config.params = params
    }
  }
  return config
}

// request interceptor
service.interceptors.request.use(requestConfig, err)
exportServe.interceptors.request.use(requestConfig, err)

// response interceptor
service.interceptors.response.use((response) => {
  const resData = response.data
  return resData
}, err)

exportServe.interceptors.response.use((response) => {
  return response
}, err)

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, service)
  }
}

export {
  installer as VueAxios,
  service as axios,
  exportServe
}
