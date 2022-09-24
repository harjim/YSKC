import axios from 'axios'

const service = axios.create({
  baseURL: '/api/mobile',
  timeout: 180000 // 请求超时时间
})
//请求拦截
service.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token') || ''
    if (token) {
      config.headers['Access-Token'] = token
    }

    return config
  },
  (err) => {
    console.log(err)
    return Promise.reject(err)
  }
)
// 响应拦截
service.interceptors.response.use(
  (res) => {
    return res.data
  },
  (err) => {
    console.log(err.response)
    if (err && err.response && err.response.status) {
      switch (err.response.status) {
        case 401:
          return Promise.resolve({
            success: false,
            errorCode: '1005',
            errorMessage: '非法请求,请重新登录!'
          })
        case 403:
          return Promise.resolve({
            success: false,
            errorCode: '1009',
            errorMessage: '无此权限，请跟管理员联系！'
          })
        default:
          break
      }
    }
    return Promise.reject(err)
  }
)

const installer = {
  install: (app) => {
    app.config.globalProperties.$http = service
  }
}

export { installer as VueAxios, service as axios }
