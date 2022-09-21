<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="['username', {rules:[{required: true, message: '请输入用户名'}]}]"
              @blur="checkUsername"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="密码">
            <a-input v-decorator="['password', {rules:[{required: true, message: '请输入密码'}]}]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="备注"
            :labelCol="{ xs: { span: 24 },sm: { span:3}}"
            :wrapperCol="{ xs: { span: 24 },sm: { span: 19 }}"
          >
            <a-textarea v-decorator="['remark']" :rows="4"></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  components: {
  },
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
      visible: false,
      confirmLoading: false,
      id: 0,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.title = '添加账号'
      this.form.resetFields()
      this.visible = true
    },
    edit (record) {
      this.id = record.id
      this.title = '修改账号[' + record.username + ']'
      this.visible = true
      this.form.resetFields()
      this.$nextTick(() => {
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === 0) {
            this.$http.post('/patentAccount/addPatentAccount', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                  this.form.resetFields()
                } else {
                  this.$message.info(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/patentAccount/updatePatentAccount', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', false)
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
    },
    checkUsername () {
      const userName = this.form.getFieldValue('username')
      if (userName) {
        this.$http.get('/patentAccount/checkUsername', { params: { userName: userName, id: this.id } })
          .then(res => {
            if (!res.data) {
              this.checked = false
              this.form.setFields({ 'username': { value: userName, errors: [new Error('该用用户名已存在，请输入其他用户名！')] } })
            } else {
              this.checked = true
              this.form.setFields({ 'username': { value: userName } })
            }
          })
      }
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
