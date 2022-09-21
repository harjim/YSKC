<template>
  <a-modal
    :width="400"
    :visible="visible"
    :title="title"
    @ok="resetPw"
    @cancel="visible = false"
    :maskClosable="false"
  >
    <a-spin :spinning="confirmLoading">
      <a-form @submit="resetPw" :form="form">
        <a-form-item label="重置密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['password', {rules:[{required: true, message: '请至少输入6位密码', validator: checkPassword}],validateTrigger: ['blur']}]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

export default {
  data () {
    return {
      visible: false,
      title: '',
      id: '',
      userName: '',
      password: '',
      confirmLoading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      form: this.$form.createForm(this)
    }
  },
  methods: {
    showReset (id, userName) {
      this.title = '重置密码[' + userName + ']'
      this.password = ''
      this.visible = true
      this.id = id
      this.userName = userName
      this.$nextTick(() => {
        this.$initForm(this.form, { password: this.password })
      })
    },
    resetPw () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post('/user/resetPassword', { id: this.id, userName: this.userName, password: this.password })
            .then(res => {
              if (res.success) {
                this.visible = false
                this.$message.success('重置[' + this.userName + ']密码成功')
              } else {
                this.$message.info(res.errorMessage)
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    checkPassword (rule, value, callback) {
      this.password = value
      if (value.length < 6) {
        callback(new Error())
      }
      callback()
    }
  }
}
</script>
