<template>
  <div class="login_container">
    <div class="content_container" :class="['content_container_'+app.device]">
      <div class="info_container" :class="['info_container_'+app.device]">
        <div class="top" :class="['top_'+app.device]" >
          您好，<br/>
          欢迎使用创新体系标准化服务云
        </div>
        <div class="middle" :class="['middle_'+app.device]">
          <img src="/images/userLayout/login_image.png" height="190" width="258">
        </div>
        <div class="bottom" :class="['bottom_'+app.device]">
          <div class="item" v-for="(item) in items" :key="item.key">
            <div class="title">{{ item.title }}</div>
            <div class="content">{{ item.value }}</div>
          </div>
        </div>
      </div>
      <div class="form_container" :class="['form_container_'+app.device]">
        <div class="title" :class="['title_'+app.device]">登录</div>
        <div class="content" :class="['content_'+app.device]">
          <a-form
            id="form"
            ref="formLogin"
            :form="form"
            @submit="handleSubmit"
          >
            <div class="form_label" :class="['form_label_'+ app.device]">企业账号</div>
            <a-form-item>
              <a-input
                class="form_input"
                :class="['form_input_'+ app.device]"
                autocomplete="off"
                type="text"
                placeholder="请输入您的企业账号"
                v-decorator="['userName', {rules: [{ required: true, message: '请输入您的企业账号' }, { validator: handleUsernameOrEmail }], validateTrigger: 'change'}]"
              >
              </a-input>
            </a-form-item>
            <div class="form_label" :class="['form_label_'+ app.device]">密码</div>
            <a-form-item>
              <a-input
                class="form_input"
                :class="['form_input_'+ app.device]"
                type="password"
                autocomplete="false"
                placeholder="请输入您的密码"
                v-decorator="['password', {rules: [{ required: true, message: '请输入您的密码' }], validateTrigger: 'blur'}]"
              >
              </a-input>
            </a-form-item>

            <a-form-item :class="['row_checkbox_'+ app.device]">
              <a-checkbox
                class="form_checkbox"
                :class="['form_checkbox_'+ app.device]"
                v-decorator="['rememberMe',{valuePropName: 'checked'}]"
              >自动登录</a-checkbox>
            </a-form-item>

            <a-form-item class="form_btn_wrap">
              <a-button
                htmlType="submit"
                class="form_btn login"
                :class="['form_btn_'+ app.device]"
                :loading="state.loginBtn"
                :disabled="state.loginBtn"
              >登录</a-button>
              <a-button
                class="form_btn register"
                :class="['form_btn_'+ app.device]"
                @click="onRegister"
              >注册</a-button>
            </a-form-item>
          </a-form>
        </div>
      </div>
    </div>
    <div class="footer" :class="['footer_'+ app.device]">
      <div class="frist_item">创新于时 创值赋能</div>
      <div class="last_item">共享协同、高效集成、管理决策、风险控制</div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import { timeFix } from '@/utils/util'

export default {
  name: 'Login',
  data () {
    return {
      loginBtn: false,
      // login type: 0 email, 1 username, 2 telephone
      loginType: 0,
      form: this.$form.createForm(this),
      isAdmin: false,
      state: {
        time: 60,
        loginBtn: false
      },
      items: [
        { key: 1, title: '企业总数', value: 0 },
        { key: 2, title: '科研人员数', value: 0 },
        { key: 3, title: '科研项目数', value: 0 },
        { key: 4, title: '专利数量', value: 0 }
      ]
    }
  },
  computed: {
    ...mapState(['app'])
  },
  created () {
    this.Logout()
    this.StatData()
  },
  methods: {
    ...mapActions(['Login', 'Logout']),
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
    StatData () {
      this.$http.get('/home/getStat').then(res => {
        this.items[0].value = res.data.customerCnt
        this.items[1].value = res.data.memberCnt
        this.items[2].value = res.data.rdCnt
        this.items[3].value = res.data.patentCnt
      })
    },
    handleSubmit (e) {
      e.preventDefault()
      const {
        form: { validateFields },
        state,
        Login
      } = this
      state.loginBtn = true
      const validateFieldsKey = ['userName', 'password']
      validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          const loginParams = { ...values }
          loginParams.loginType = 0
          loginParams.isAdmin = false
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
    loginSuccess (res) {
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
      this.$notification['error']({
        message: '错误',
        description: err.errorMessage || err.message || ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
    },
    onRegister () {
      this.$router.push({ name: 'register' })
    }
  }
}
</script>

<style lang="less" scoped>
@import './login.less';
</style>
