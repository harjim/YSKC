<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col>
            <a-form-item label="密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['password', {rules: [{ required: true, message: '至少6位密码，区分大小写' }], validateTrigger: ['change', 'blur']}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      flag: 0,
      id: 0,
      gender: 0,
      status: 0,
      userName: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    reset (record) {
      this.title = '重置密码'
      this.isCreat = true
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.flag = -1
      this.id = record.id
      this.gender = record.gender
      this.status = record.status
      this.userName = record.userName
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.flag === -1) {
            values.id = this.id
            values.userName = this.userName
            values.gender = this.gender
            values.status = this.status
            this.$http.post('/rsUser/updatePassword', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$message.success('重置密码成功')
                  this.$emit('ok', true)
                } else {
                  this.$message.info(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
