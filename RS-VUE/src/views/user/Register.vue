<template>
  <div class="register_container">
    <div class="form_container" :class="[device]">
      <div class="title" :class="['title_'+ device]">
        注册账号
      </div>
      <a-form
        layout="vertical"
        ref="formRegister"
        :form="form"
        id="formRegister"
        class="form_items"
        :class="['form_'+device]">
        <div style="margin-right: 24px">
          <a-form-item label="用户名">
            <a-input
              class="form_input"
              :class="['form_input_'+ device]"
              placeholder="用户名"
              v-decorator="['userName', {rules: [{ required: true, message: '请输入用户名', whitespace:true }, { validator: this.checkUser }], validateTrigger: ['change', 'blur']}]"
            ></a-input>
          </a-form-item>

          <a-popover
            placement="leftTop"
            :trigger="['focus']"
            style=""
            :getPopupContainer="(trigger) => trigger.parentElement"
            v-model="state.passwordLevelChecked"
          >
            <template slot="content">
              <div :style="{ width: '200px' }">
                <div :class="['user-register', passwordLevelClass]">
                  强度：
                  <span>{{ passwordLevelName }}</span>
                </div>
                <a-progress
                  :percent="state.percent"
                  :showInfo="false"
                  :strokeColor=" passwordLevelColor "
                />
                <div style="margin-top: 10px;">
                  <span>请至少输入 6 个字符。请不要使用容易被猜到的密码。</span>
                </div>
              </div>
            </template>
            <a-form-item
              label="密码"
            >
              <a-input
                class="form_input"
                :class="['form_input_'+ device]"
                type="password"
                @click="handlePasswordInputClick"
                autocomplete="false"
                placeholder="至少6位密码，区分大小写"
                v-decorator="['password', {rules: [{ required: true, message: '至少6位密码，区分大小写', whitespace:true}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}]"
              ></a-input>
            </a-form-item>
          </a-popover>

          <a-form-item
            label="确认密码"
          >
            <a-input
              class="form_input"
              :class="['form_input_'+ device]"
              type="password"
              autocomplete="false"
              placeholder="确认密码"
              v-decorator="['password2', {rules: [{ required: true, message: '至少6位密码，区分大小写', whitespace:true }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}]"
            ></a-input>
          </a-form-item>
          <a-form-item
            label="联系人"
          >
            <a-input
              class="form_input"
              :class="['form_input_'+ device]"
              type="text"
              placeholder="联系人"
              v-decorator="['linkMan', {rules: [{ required: true, message: '请输入联系人', whitespace:true }], validateTrigger: ['change', 'blur']}]"
            ></a-input>
          </a-form-item>
        </div>
        <div>
          <a-form-item
            label="公司名"
          >
            <a-input
              class="form_input"
              :class="['form_input_'+ device]"
              type="text"
              placeholder="公司名"
              v-decorator="['companyName', {rules: [{ required: true, message: '请输入公司名', whitespace:true}, { validator: this.checkCompany }], validateTrigger: ['change', 'blur']}]"
            ></a-input>
          </a-form-item>
          <a-form-item
            label="公司地址"
          >
            <a-input
              class="form_input"
              :class="['form_input_'+ device]"
              type="text"
              placeholder="公司地址"
              v-decorator="['companyAddress', {rules: [{ required: true, message: '请输入公司地址', whitespace:true }], validateTrigger: ['change', 'blur']}]"
            ></a-input>
          </a-form-item>
          <a-form-item
            label="所属行业"
          >
            <!-- <a-tree-select
              multiple
              :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
              :tree-data="treeData"
              :maxTagCount="device === 'desktop' ? 5 : device === 'minDesktop' ? 1 : 1"
              :maxTagTextLength="device === 'desktop' ? 3 : device === 'minDesktop' ? 2 :8 "
              v-decorator="['industryCode', {rules:[{required: true, message: '请选择所属行业'}]}]"
              placeholder="请选择所属行业"
            /> -->
            <a-select
              size="default"
              placeholder="所属行业"
              v-decorator="['industryCode', {rules:[{required: true, message: '请选择所属行业'}], validateTrigger: ['change', 'blur']}]"
              mode="multiple"
              :maxTagCount="device === 'desktop' ? 5 : device === 'minDesktop' ? 1 : 1"
              :maxTagTextLength="device === 'desktop' ? 3 : device === 'minDesktop' ? 2 :8 "
            >
              <a-select-option value="A">农、林、牧、渔业</a-select-option>
              <a-select-option value="B">采矿业</a-select-option>
              <a-select-option value="C">制造业</a-select-option>
              <a-select-option value="D">电力、热力、燃气及水的生产和供应业</a-select-option>
              <a-select-option value="E">建筑业</a-select-option>
              <a-select-option value="F">批发和零售业</a-select-option>
              <a-select-option value="G">交通运输、仓储和邮政业</a-select-option>
              <a-select-option value="H">住宿和餐饮业</a-select-option>
              <a-select-option value="I">信息传输、软件和信息技术服务业</a-select-option>
              <a-select-option value="J">金融业</a-select-option>
              <a-select-option value="K">房地产业</a-select-option>
              <a-select-option value="L">租赁和商务服务业</a-select-option>
              <a-select-option value="M">科学研究和技术服务业</a-select-option>
              <a-select-option value="N">水利、环境和公共设施管理业</a-select-option>
              <a-select-option value="O">居民服务、修理和其他服务业</a-select-option>
              <a-select-option value="P">教育</a-select-option>
              <a-select-option value="Q">卫生和社会工作</a-select-option>
              <a-select-option value="R">文化、体育和娱乐业</a-select-option>
              <a-select-option value="S">公共管理、社会保障和社会组织</a-select-option>
              <a-select-option value="T">国际组织</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item
            label="联系电话"
          >
            <a-input
              class="form_input"
              :class="['form_input_'+ device]"
              type="text"
              placeholder="联系电话"
              v-decorator="['linkTel', {rules: [{ required: true, message: '请输入联系电话' }], validateTrigger: ['change', 'blur']}]"
            ></a-input>
          </a-form-item>
        </div>
      </a-form>
      <div>
        <a-button
          class="register_btn"
          :class="['register_btn_' + device]"
          :loading="registerBtn"
          @click="handleSubmit"
          :disabled="registerBtn"
        >注册</a-button>
      </div>
      <div>
        <router-link class="login" :class="['login_' + device]" :to="{ name: 'login' }">已注册账号</router-link>
      </div>
      <!-- <a-row :gutter="[0,6]" type="flex" justify="center" align="middle">
          <a-col :span="12">
            <a-button
              size="large"
              type="danger"
              htmlType="submit"
              class="register-button"
              :loading="registerBtn"
              @click.stop.prevent="handleSubmit"
              :disabled="registerBtn"
            >注册</a-button>
          </a-col>
        </a-row>
        <a-row :gutter="[0,6]" type="flex" justify="center" align="middle">
          <a-col :span="12" style="text-align: center;">
            <router-link class="login" :to="{ name: 'login' }">已注册账号</router-link>
          </a-col>
        </a-row> -->
      <!-- </div> -->
    </div>
  </div>
</template>

<script>
import { mixinDevice } from '@/utils/mixin.js'
import { getSmsCaptcha } from '@/api/login'

const levelNames = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}
export default {
  name: 'Register',
  components: {
  },
  mixins: [mixinDevice],
  data () {
    return {
      form: this.$form.createForm(this),
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      registerBtn: false,
      treeData: []
    }
  },
  // created () {
  //   this.$getDictionary(15).then(res => {
  //     this.treeData = res.map((item) => {
  //       item['selectable'] = false
  //       return item
  //     })
  //     this.transfromTreeData(this.treeData)
  //   })
  // },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  methods: {
    checkUser (rule, value, callback) {
      if (value && value.trim() !== '') {
        this.$http.get('/user/registerCheckUser', { params: { userName: value } })
          .then(res => {
            if (res.data) {
              callback(new Error('用户名已存在,请重新输入'))
            } else {
              callback()
            }
          })
      } else {
        callback()
      }
    },
    checkCompany (rule, value, callback) {
      if (value && value.trim() !== '') {
        this.$http.get('/company/registerCheckCompany', { params: { companyName: value } })
          .then(res => {
            if (res.data === 2) {
              callback(new Error('公司名已存在,请重新输入'))
            } else {
              callback()
            }
          })
      } else {
        callback()
      }
    },
    handlePasswordLevel (rule, value, callback) {
      if (!value || value.trim() === '') {
        callback()
        return
      }
      let level = 0

      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      this.state.passwordLevel = level
      this.state.percent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error('密码强度不够'))
      }
    },

    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback()
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback(new Error('至少6位密码，区分大小写'))
    },
    handlePhoneCheck (rule, value, callback) {
      callback()
    },
    handlePasswordInputClick () {
      if (!this.isMobile()) {
        this.state.passwordLevelChecked = true
        return
      }
      this.state.passwordLevelChecked = false
    },
    handleSubmit () {
      const { form: { validateFields }, state, $router } = this
      const validateFieldsKey = ['userName', 'password', 'password2', 'linkMan', 'companyName', 'companyAddress', 'industryCode', 'linkTel']
      validateFields(validateFieldsKey, { force: true }, (error, values) => {
        if (error) { return }
        state.passwordLevelChecked = false
        this.$http.post('/user/register', values)
          .then(res => {
            if (res.success && res.data) {
              this.visible = false
              this.$emit('ok', true)
              $router.push({ name: 'registerResult', params: { ...values } })
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '注册失败')
            }
          }).finally(res => {
            this.confirmLoading = false
          })
      })
    },

    getCaptcha (e) {
      e.preventDefault()
      const { form: { validateFields }, state, $message, $notification } = this

      validateFields(['mobile'], { force: true },
        (err, values) => {
          if (!err) {
            state.smsSendBtn = true

            const interval = window.setInterval(() => {
              if (state.time-- <= 0) {
                state.time = 60
                state.smsSendBtn = false
                window.clearInterval(interval)
              }
            }, 1000)

            const hide = $message.loading('验证码发送中..', 0)

            getSmsCaptcha({ mobile: values.mobile }).then(res => {
              setTimeout(hide, 2500)
              $notification['success']({
                message: '提示',
                description: '验证码获取成功，您的验证码为：' + res.result.captcha,
                duration: 8
              })
            }).catch(err => {
              setTimeout(hide, 1)
              clearInterval(interval)
              state.time = 60
              state.smsSendBtn = false
              this.requestFailed(err)
            })
          }
        }
      )
    },
    requestFailed (err) {
      this.$notification['error']({
        message: '错误',
        description: ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
      this.registerBtn = false
    },
    /**
     * @description: 转化组件使用的数据
     * @param {*} data
     * @return {*}
     */
    transfromTreeData (data) {
      for (const item of data) {
        item['title'] = item['value']
        item['value'] = item['key']
        if (item['children'].length) {
          this.transfromTreeData(item['children'])
        } else {
          delete item['children']
        }
      }
    }
  }
}
</script>
<style lang="less" scoped>
@import './register.less';
</style>
