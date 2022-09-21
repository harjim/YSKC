<template>
  <a-modal
    title="修改密码"
    :width="640"
    :keyboard="false"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    :closable="closable"
    :maskClosable="false"
    :footer="null"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="原密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-if="first===true"
            type="password"
            placeholder="初始密码为123456"
            v-decorator="['oldPassword', {rules: [{required: true, message: '原密码不能为空！'}]}]"
          />
          <a-input
            v-else
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
      <div style="text-align: right;margin-top:10px">
        <a-button type="primary" @click="handleCancel">取消</a-button>
        <a-button type="primary" @click="handleSubmit" style="margin-left:20px">提交</a-button>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'ModyPassword',
  data () {
    return {
      closable: true,
      first: false,
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
    ...mapActions(['Logout']),
    init (first) {
      this.first = first
      this.closable = !first
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
                this.$message.info('修改密码成功')
                this.visible = false
              } else {
                this.$message.warn('修改密码失败')
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
      if (this.closable) {
        this.visible = false
      } else {
        this.confirmLoading = true
        this.Logout({}).then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 10)
        })
      }
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
