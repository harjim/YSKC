<template>
  <a-spin size="large" tip="正在跳转，请稍后..." :spinning="spinning">
    <div class="spin-content">{{ errMsg }}</div>
  </a-spin>
</template>
<script>
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'
export default {
  data () {
    return {
      spinning: false,
      errMsg: ''
    }
  },
  mounted () {
    var self = this
    window.dd.ready(function () {
      window.dd.runtime.permission.requestAuthCode({
        corpId: 'ding543f60a6e3104cf835c2f4657eb6378f',
        onSuccess: function (info) {
          const loginParams = {}
          loginParams.loginType = 1
          loginParams.userName = '1'
          loginParams.password = '1'
          loginParams.isAdmin = true
          loginParams.loginTmpCode = info.code
          self.Login(loginParams)
            .then(res => self.loginSuccess(res))
            .catch(err => self.requestFailed(err))
        },
        onFail: function (err) {
          self.$notification.success({
            message: '错误',
            description: err
          })
        }
      })
    })
  },
  methods: {
    ...mapActions(['Login', 'Logout']),
    dingLogin (name) {
      var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
      var r = window.location.search.substr(1).match(reg)
      if (r !== null) {
        this.spinning = true
        const code = unescape(r[2])
        const loginParams = {}
        loginParams.loginType = 1
        loginParams.userName = '1'
        loginParams.password = '1'
        loginParams.isAdmin = true
        loginParams.loginTmpCode = code
        this.Login(loginParams)
          .then(res => this.loginSuccess(res))
          .catch(err => this.requestFailed(err))
        return unescape(r[2])
      }
      return null
    },
    loginSuccess (res) {
      this.spinning = false
      this.$router.push({ name: 'home' })
      // 延迟 1 秒显示欢迎信息
      setTimeout(() => {
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`
        })
      }, 1000)
    },
    requestFailed (err) {
      this.spinning = false
      this.errMsg = err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试'
    }
  }
}
</script>
<style scoped>
.spin-content {
  border: 1px solid #91d5ff;
  background-color: #e6f7ff;
  padding: 30px;
  text-align: center;
}
</style>
