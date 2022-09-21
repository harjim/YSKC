<template>
  <a-modal
    title="修改密码"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="原密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            type="password"
            placeholder="原密码"
            v-decorator="['oldPassword', {rules: [{required: true, message: '原密码不能为空！'}]}]"
          />
        </a-form-item>
        <a-form-item label="新密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            type="password"
            placeholder="新密码"
            v-decorator="['newPassword', {rules: [{required: true, min: 6, message: '请输入至少六个字符！'}], validateTrigger: ['change', 'blur']}]"
          />
        </a-form-item>
        <a-form-item label="确认新密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            type="password"
            placeholder="确认新密码"
            v-decorator="['rePassword', {rules: [{required: true, min: 6, message: '请输入至少六个字符！'}, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}]"
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
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      visible: false,
      confirmLoading: false,

      form: this.$form.createForm(this)
    }
  },
  methods: {
    init () {
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post('/user/updatePassword', values)
            .then(res => {
              if (res.data) {
                this.$message.success('修改密码成功')
                this.visible = false
              } else {
                this.$message.error('修改密码失败')
              }
            }).finally(() => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.visible = false
    },
    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('newPassword')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    }
  }
}
</script>
