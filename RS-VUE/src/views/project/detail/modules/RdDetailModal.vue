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
    <a-form :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="记账日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                format="YYYY-MM-DD"
                v-decorator="['rdDate', {rules:[{required: true, message: '请选择记账日期'}]}]"
                placeholder="请选择记账日期"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="凭证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['accNumber', {rules:[{required: true, message: '请输入凭证号'}]}]"
                placeholder="请输入凭证号"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="摘要" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['summary', {rules:[{required: true, message: '请输入摘要'}]}]"
                placeholder="请输入摘要"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="借方" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0"
                :precision="2"
                v-decorator="['debit', {rules:[{required: true, message: '请输入借方'}]}]"
                placeholder="请输入借方"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="贷方" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0"
                :precision="2"
                v-decorator="['credit', {rules:[{required: true, message: '请输入贷方'}]}]"
                placeholder="请输入贷方"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="方向" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['direction', {rules:[{required: true, message: '请输入方向'}]}]"
                placeholder="请输入方向"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="余额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0"
                :precision="2"
                v-decorator="['balance', {rules:[{required: true, message: '请输入余额'}]}]"
                placeholder="请输入余额"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>
<script>

import moment from 'moment'
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
      id: null,
      visible: false,
      confirmLoading: false,
      accType: 1,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add (accType) {
      this.accType = accType
      this.title = '添加费用明细'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = null
    },
    edit (record, accType) {
      this.accType = accType
      this.title = `编辑费用明细[${record.accNumber}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['rdDate'])
      })
    },
    moment,
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          values.accType = this.accType
          if (values.id === null) {
            this.$http.post('/rdAccountDetail/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$message.success('添加成功')
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/rdAccountDetail/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$message.success('更新成功')
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
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
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
