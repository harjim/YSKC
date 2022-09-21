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
            <a-form-item
              label="凭证号"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input
                v-decorator="['accNumber', {rules:[{required: false, message: '请输入凭证号'}]}]"
                placeholder="请输入凭证号"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="能源名称"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input
                v-decorator="['ename', {rules:[{required: true, message: '请输入能源名称'}]}]"
                placeholder="请输入能源名称"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="部门"
            >
              <a-input
                v-decorator="['deptName', {rules:[{required: false, message: '请输入部门'}]}]"
                placeholder="请输入部门"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="发生日期"
            >
              <a-date-picker
                format="YYYY-MM-DD"
                v-decorator="['occDate',{rules:[{required: true, message: '请选择发生日期'}]}]"
                placeholder="请选择发生日期"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="单价"
            >
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入单价"
                :precision="6"
                v-decorator="['unitPrice', {rules:[{required: false, message: '请输入单价'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="数量"
            >
              <a-input-number
                :precision="6"
                :max="$store.state.totalMax"
                v-decorator="['quantity', {rules:[{required: false, message: '请输入数量'}]}]"
                placeholder="请输入数量"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="金额"
            >
              <a-input-number
                :max="$store.state.totalMax"
                :precision="6"
                v-decorator="['totalAmount', {rules:[{required: false, message: '请输入金额'}]}]"
                placeholder="金额"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="分配金额"
            >
              <a-input-number
                :max="$store.state.totalMax"
                :precision="6"
                v-decorator="['amount', {rules:[{required: true, message: '请输入分配金额'}]}]"
                placeholder="分配金额"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="单位"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input
                v-decorator="['unit', {rules:[{required: false, message: '请输入单位'}]}]"
                placeholder="请输入单位"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="科目"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <select-down
                ref="selectDown"
                treeType="account"
                v-decorator="['accountTitleId', {rules:[{required: false, message: '请选择科目'}]}]"
                placeholder="请选择科目"
                @select="(val)=>getSelectVal(val, 'accountTitle')"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="备注"
              :labelCol=" {xs: { span: 24 },sm: { span: 3 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
              :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
            >
              <a-textarea
                v-decorator="['remark']"
                :rows="3"
                maxlength="200"
              ></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>
<script>
import { SelectDown } from '@/components'
export default {
  components: {
    SelectDown
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
      id: -1,
      visible: false,
      etype: 0,
      confirmLoading: false,
      accountTitleId: undefined,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add (etype) {
      this.etype = etype
      this.title = '添加' + (this.etype === 20100 ? '动力' : '燃料')
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.accountTitleId = undefined
      this.$nextTick(() => {
        this.$refs.selectDown.setValue(0)
      })
      this.id = -1
    },
    edit (record) {
      this.etype = record.type
      this.title = `编辑${this.etype === 20100 ? '动力' : '燃料'}[${record.ename}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.accountTitleId = record.accountTitleId
      this.$nextTick(() => {
        this.$refs.selectDown.setValue(record.accountTitleId)
        this.$initForm(this.form, record, ['occDate'])
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          values.accountTitleId = this.accountTitleId
          values.type = this.etype
          if (values.id === -1) {
            this.$http.post('/energy/addEnergy', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/energy/updateEnergy', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
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
    },
    getSelectVal (value, type) {
      this.accountTitleId = value
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
