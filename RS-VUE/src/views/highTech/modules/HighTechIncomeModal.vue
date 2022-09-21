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
            <a-form-item label="记帐日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                format="YYYY-MM-DD"
                placeholder="请选择记帐日期"
                v-decorator="['bookDate',{rules:[{required: true, message: '请选择记帐日期'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="凭证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入凭证号"
                v-decorator="['voucherNo', {rules:[{required: true, message: '请输入凭证号'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="品名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入品名"
                v-decorator="['productName', {rules:[{required: true, message: '请输入品名'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="数量">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                placeholder="请输入数量"
                style="width:100%"
                v-decorator="['quantity', {rules:[{required: true, message: '请输入数量'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单价(元)">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                placeholder="请输入单价"
                style="width:100%"
                v-decorator="['unitPrice', {rules:[{required: true, message: '请输入单价'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="高新收入(元)">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                placeholder="请输入高新收入"
                style="width:100%"
                v-decorator="['income', {rules:[{required: true, message: '请输入高新收入'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="对应客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入对应客户"
                v-decorator="['client', {rules:[{required: true, message: '请输入对应客户'}]}]"
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
  name: 'HighTechIncomeModal',
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
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  created () {
  },
  methods: {
    add () {
      this.title = '添加高品收入'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
    },
    edit (record) {
      this.title = `编辑[${record.bookDate}-${record.productName}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['bookDate'])
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            this.$http.post('/highTechIncome/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/highTechIncome/edit', values)
              .then(res => {
                if (res.success && res.data) {
                  this.confirmLoading = false
                  this.visible = false
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
                }
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
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
