import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import { VueAxios } from './axios'
import notification from 'ant-design-vue/es/notification'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import moment from 'moment'

const Qs = require('qs')
// eslint-disable-next-line camelcase
const baseUrl = window.yskc.baseApiUrl
// 创建 axios 实例
const service = axios.create({
  baseURL: baseUrl,
  timeout: 180000 // 请求超时时间
})
// 创建导出axios 实例，【返回response，可调用response.headers[]】
const exportServe = axios.create({
  baseURL: baseUrl,
  timeout: 600000 // 请求超时时间(10分钟)
})

export function getBaseUrl () {
  return baseUrl
}

const err = error => {
  if (error.response) {
    const data = error.response.data
    const token = Vue.ls.get(ACCESS_TOKEN)
    if (error.response.status === 403) {
      notification.error({
        message: '错误',
        description: data.errorMessage
      })
      return Promise.resolve({ success: false })
    }
    if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
      console.log('认证失败!')
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 1500)
        })
      }
      return Promise.resolve({ success: false })
    }
  }
  return Promise.reject(error)
}

// request interceptor
service.interceptors.request.use(request, err)
exportServe.interceptors.request.use(request, err)
// response interceptor
exportServe.interceptors.response.use(response => {
  const res = response
  return res
}, err)
service.interceptors.response.use(response => {
  const res = response.data
  return res
}, err)

function request (config) {
  const token = Vue.ls.get(ACCESS_TOKEN)
  if (token) {
    config.headers['Access-Token'] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  if (config.method === 'post' && config.data) {
    for (const key in config.data) {
      if (moment.isMoment(config.data[key])) {
        config.data[key] = config.data[key].format('YYYY-MM-DD HH:mm:ss')
      } else if (typeof config.data[key] === 'string') {
        config.data[key] = config.data[key].trim()
      }
    }
  } else if (config.method === 'get' && config.params) {
    config.paramsSerializer = function (params) {
      return Qs.stringify(params, { arrayFormat: 'repeat', allowDots: true })
    }
    for (const key in config.params) {
      if (moment.isMoment(config.params[key])) {
        config.params[key] = config.params[key].format('YYYY-MM-DD HH:mm:ss')
      }
    }
  }
  return config
}

const installer = {
  vm: {},
  install (Vue) {
    Vue.use(VueAxios, service)
  }
}

export { installer as VueAxios, service as axios, exportServe }
