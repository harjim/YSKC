<template>
  <div class="main">
    <a-form
      v-if="notInDingTalk"
      id="formLogin"
      class="user-layout-login"
      ref="formLogin"
      :form="form"
      @submit="handleSubmit"
    >
      <a-tabs
        :activeKey="customActiveKey"
        :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
        @change="handleTabClick"
      >
        <a-tab-pane tab="钉钉登录" key="dingdingKey" forceRender>
          <div id="login_container"></div>
        </a-tab-pane>
        <a-tab-pane key="userLogin" tab="系统登录">
          <a-form-item>
            <a-input
              size="large"
              type="text"
              autocomplete="false"
              placeholder="账户"
              v-decorator="[
                'userName',
                {rules: [{ required: true, message: '请输入帐户名' }, { validator: handleUsernameOrEmail }], validateTrigger: 'change'}
              ]"
            >
              <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }" />
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input
              size="large"
              type="password"
              autocomplete="false"
              placeholder="密码"
              v-decorator="[
                'password',
                {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }" />
            </a-input>
          </a-form-item>
        </a-tab-pane>
      </a-tabs>

      <!-- <a-form-item>
        <a-checkbox v-decorator="['rememberMe']">自动登录</a-checkbox>
      </a-form-item>-->

      <a-form-item style="margin-top:24px">
        <a-button
          v-if="customActiveKey==='userLogin'"
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
        >确定</a-button>
      </a-form-item>
    </a-form>
    <template v-else>
      <a-spin tip="登录中，请稍后..." :spinning="spinning">
        <a @click="loginByCode" v-if="loginFail"><b style="color:red;">自动登录失败，请稍后重试...</b></a>
      </a-spin>
    </template>
  </div>
</template>

<script>
import * as dd from 'dingtalk-jsapi'
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'

export default {
  data () {
    return {
      customActiveKey: 'dingdingKey',
      loginBtn: false,
      // login type: 0 email, 1 username, 2 telephone
      loginType: 0,
      form: this.$form.createForm(this),
      state: {
        time: 60,
        loginBtn: false,
        // login type: 0 email, 1 username, 2 telephone
        loginType: 0,
        smsSendBtn: false
      },
      notInDingTalk: true,
      spinning: false,
      loginFail: false
    }
  },
  created () {
    this.notInDingTalk = dd.env.platform === 'notInDingTalk'
    if (this.notInDingTalk) {
      const code = this.getQuery('code')
      this.handleTabClick('dingdingKey')
      if (code) {
        this.handLogin(1, code)
      }
    } else {
      this.loginByCode()
    }
  },
  methods: {
    getQuery (name) {
      var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
      var r = window.location.search.substr(1).match(reg)
      if (r !== null) {
        return unescape(r[2])
      }
      return null
    },
    ...mapActions(['Login', 'Logout', 'LoginByCode']),
    // handler
    handleUsernameOrEmail (rule, value, callback) {
      const { state } = this
      const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (regex.test(value)) {
        state.loginType = 0
      } else {
        state.loginType = 1
      }
      callback()
    },
    handleTabClick (key) {
      this.customActiveKey = key
      this.form.resetFields()
      if (this.customActiveKey === 'dingdingKey') {
        this.$nextTick(() => {
          var url = `https://oapi.dingtalk.com/connect/qrconnect?appid=${window.yskc.dingDingAppId}&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=${window.yskc.baseUrl}/ding/dingLogin`
          window.DDLogin({
            id: 'login_container', // 这里需要你在自己的页面定义一个HTML标签并设置id，例如<div id="login_container"></div>或<span id="login_container"></span>
            goto: encodeURIComponent(url), // 请参考注释里的方式
            style: 'border:none;background-color:#FFFFFF;',
            width: '365',
            height: '400'
          })
          var hanndleMessage = function (event) {
            var data = event.data
            var origin = event.origin
            if (origin === 'https://login.dingtalk.com' || origin === 'https://pre-login.dingtalk.com') {
              window.location = `https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=${window.yskc.dingDingAppId}&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=${window.yskc.baseUrl}/ding/dingLogin&loginTmpCode=${data}`
            }
          }
          if (typeof window.addEventListener !== 'undefined') {
            window.addEventListener('message', hanndleMessage, false)
          } else if (typeof window.attachEvent !== 'undefined') {
            window.attachEvent('onmessage', hanndleMessage)
          }
        })
      }
    },
    handleSubmit (e) {
      e.preventDefault()
      const {
        form: { validateFields },
        state,
        // customActiveKey,
        Login
      } = this

      state.loginBtn = true

      const validateFieldsKey = ['userName', 'password'] // customActiveKey === 'tab1' ? ['userName', 'password'] : ['mobile', 'captcha']

      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          const loginParams = { ...values }
          delete loginParams.username
          loginParams.loginType = 0
          loginParams.userName = values.userName
          loginParams.password = values.password
          Login(loginParams)
            .then(res => this.loginSuccess(res))
            .catch(err => this.requestFailed(err))
            .finally(() => {
              state.loginBtn = false
            })
        } else {
          setTimeout(() => {
            state.loginBtn = false
          }, 600)
        }
      })
    },
    loginByCode () {
      this.spinning = true
      this.loginFail = false
      const self = this
      dd.runtime.permission.requestAuthCode({
        corpId: window.yskc.corpId,
        onSuccess: ({ code }) => {
          self.LoginByCode({ code })
            .then(res => self.loginSuccess(res))
            .catch(err => {
              self.loginFail = true
              self.requestFailed(err)
            })
            .finally(() => {
              self.spinning = false
            })
        },
        onFail: () => {
          self.requestFailed({})
          self.spinning = false
          self.loginFail = true
        }
      })
    },
    loginSuccess (res) {
      this.$router.push({ name: 'index' }).catch(() => {})
      // 延迟 1 秒显示欢迎信息
      setTimeout(() => {
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`
        })
      }, 1000)
    },
    requestFailed (err) {
      this.$notification['error']({
        message: '错误',
        description: err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
    }
  }
}
</script>

<style lang="less" scoped>
.user-layout-login {
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
