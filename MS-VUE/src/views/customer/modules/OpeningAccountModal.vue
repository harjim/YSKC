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
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-card :bordered="false">
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  @blur="checkuserName"
                  v-decorator="['userName', {rules:[{required: true, message: '请输入用户名'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-decorator="['password', {rules: [{ required: true, message: '至少6位密码，区分大小写' }], validateTrigger: ['change', 'blur']}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'
export default {
  components: {
    STable,
    Ellipsis
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
      id: 0,
      companyId: undefined,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      entity: {},
      checked: true
    }
  },
  methods: {
    open (record) {
      this.title = '开通账号[' + record.companyName + ']'
      this.confirmLoading = false
      this.visible = true
      this.entity = record
    },
    handleSubmit () {
      if (!this.checked) {
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values = Object.assign(this.entity, values)
          values.customerId = this.entity.id
          this.$http.post('/customer/openingAccount', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', true)
                this.form.resetFields()
              } else {
                this.$message.error(res.errorMessage)
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    checkuserName () {
      const userName = this.form.getFieldValue('userName')
      if (userName) {
        this.$http.get('/rsUser/checkuserName', { params: { userName: userName } })
          .then(res => {
            if (!res.data) {
              this.checked = false
              this.form.setFields({ 'userName': { value: userName, errors: [new Error('该用户名已存在，请输入其他用户名！')] } })
            } else {
              this.checked = true
              this.form.setFields({ 'userName': { value: userName } })
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
</style>
