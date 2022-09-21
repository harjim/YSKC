<template>
  <a-spin
    size="large"
    tip="正在跳转，请稍后..."
    :spinning="spinning"
  >
    <div class="spin-content">
      即将跳转到系统.......
    </div>
  </a-spin>
</template>
<script>
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'
export default {
  data () {
    return {
      spinning: false
    }
  },
  mounted () {
    this.dingLogin('code')
  },
  methods: {
    ...mapActions(['Login', 'Logout']),
    dingLogin (name) {
      const self = this
      var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
      var r = window.location.search.substr(1).match(reg)
      if (r !== null) {
        this.spinning = true
        const code = unescape(r[2])
        const loginParams = {}
        loginParams.loginType = 1
        loginParams.userName = '1'
        loginParams.password = '1'
        loginParams.loginTmpCode = code
        this.Login(loginParams)
          .then(res => self.loginSuccess(res))
          .catch(err => self.requestFailed(err))
        return unescape(r[2])
      }
      return null
    },
    loginSuccess (res) {
      this.spinning = false
      this.$router.push({ name: 'index' })
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
      this.$notification['error']({
        message: '错误',
        description: err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
      this.$router.push({ name: 'index' })
    }
  }
}
</script>
<style scoped>
  .spin-content {
    border: 1px solid #91d5ff;
    background-color: #e6f7ff;
    padding: 30px;
  }
</style>
