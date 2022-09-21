<template>
  <a-modal
    :width="1400"
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
            <a-form-item label="支出类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['ctype', {rules:[{required: true, message: '请选择支出类别'}]}]"
                :allowClear="true"
                placeholder="请选择支出类别"
              >
                <a-select-option value="1">设备</a-select-option>
                <a-select-option value="2">建设费</a-select-option>
                <a-select-option value="3">铺底流动资金</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="支出内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['cname', {rules:[{required: true, message: '请输入支出内容'}]}]"
                placeholder="请输入项目名称"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="规格 型号">
              <a-input
                v-decorator="['model', {rules:[{required: false, message: '请输入规格 型号'}]}]"
                placeholder="请输入规格 型号"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="企业填报金额">
              <a-input-number
                :min="0"
                :precision="2"
                v-decorator="['fillAmount', {rules:[{required: true, message: '请输入企业填报金额'}]}]"
                placeholder="请输入企业填报金额"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="支出时间"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :help="()=>{const r = form.getFieldValue('payDates'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
            >
              <a-textarea
                v-decorator="['payDates', {rules:[{required: true, message: '请输入支出时间'}]}]"
                :rows="3"
                maxlength="200"
                placeholder="请输入支出时间"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="发票记账凭证字号"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :help="()=>{const r = form.getFieldValue('invoiceVoucher'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
            >
              <a-textarea
                v-decorator="['invoiceVoucher', {rules:[{required: true, message: '请输入发票记账凭证字号'}]}]"
                maxlength="200"
                :rows="3"
                placeholder="请输入发票记账凭证字号"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="收款单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['payee', {rules:[{required: true, message: '请输入收款单位'}]}]"
                placeholder="请输入收款单位"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="发票号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['invoiceNumber', {rules:[{required: true, message: '请输入发票号码'}]}]"
                placeholder="请输入发票号码"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="发票日期">
              <a-date-picker
                format="YYYY-MM-DD"
                v-decorator="['invoiceDate',{rules:[{required: true, message: '请选择发票日期'}]}]"
                placeholder="请选择发票日期"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="是否银行转账">
              <a-checkbox :checked="isBankTransfer" @change="isBankTransfer = !isBankTransfer">是</a-checkbox>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="银行转账时间"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :help="()=>{const r = form.getFieldValue('bankTransferDates'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
            >
              <a-textarea
                maxlength="200"
                v-decorator="['bankTransferDates', {rules:[{required: false, message: '请输入银行转账时间'}]}]"
                :rows="3"
                placeholder="请输入银行转账时间"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="银行转账记账凭证字号"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :help="()=>{const r = form.getFieldValue('bankVoucher'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
            >
              <a-textarea
                maxlength="200"
                v-decorator="['bankVoucher', {rules:[{required: false, message: '请输入银行转账记账凭证字号'}]}]"
                :rows="3"
                placeholder="请输入银行转账记账凭证字号"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="合同/协议编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['contractNumber', {rules:[{required: true, message: '请输入合同/协议编号'}]}]"
                placeholder="请输入合同/协议编号"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="合同/协议日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                format="YYYY-MM-DD"
                v-decorator="['contractDate',{rules:[{required: true, message: '请选择合同/协议日期'}]}]"
                placeholder="请选择合同/协议日期"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="数量">
              <a-input-number
                :min="1"
                :precision="0"
                v-decorator="['quantity', {rules:[{required: false, message: '请输入数量'}]}]"
                placeholder="请输入数量"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="审计确定金额">
              <a-input-number
                :min="0"
                :precision="2"
                v-decorator="['auditAmount', {rules:[{required: true, message: '请输入审计确定金额'}]}]"
                placeholder="请输入审计确定金额"
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
      isBankTransfer: false,
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
      projectId: null,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add (projectId) {
      this.title = '添加项目支出'
      this.confirmLoading = false
      this.isBankTransfer = false
      this.projectId = projectId
      this.visible = true
      this.form.resetFields()
      this.id = null
    },
    edit (record) {
      this.title = `编辑项目支出[${record.cname}]`
      this.form.resetFields()
      this.visible = true
      this.isBankTransfer = record.isBankTransfer
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['contractDate', 'invoiceDate'])
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
          values.isBankTransfer = this.isBankTransfer
          values.projectId = this.projectId
          if (values.id === null) {
            this.$http.post('/techProjectCost/add', values)
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
            this.$http.post('/techProjectCost/update', values)
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
