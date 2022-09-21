<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="物料名称:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入物料名称"
              v-decorator="['mname', {rules:[{required: true, message: '请输入物料名称'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="物料编码:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入物料编码" v-decorator="['mcode']" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="领用日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              format="YYYY-MM-DD"
              placeholder="请选择领用日期"
              v-decorator="['acqDate', {rules:[{required: true, message: '请选择领用日期'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="出库单号:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入出库单号" v-decorator="['billNo']" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="金额:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :max="$store.state.totalMax"
              placeholder="请输入金额"
              :precision="6"
              v-decorator="['totalAmount', {rules:[{required: true, message: '请输入金额'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="单价:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入单价"
              :precision="6"
              :min="0"
              :max="$store.state.totalMax"
              v-decorator="['unitPrice', {rules:[{required: true, message: '请输入单价'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="数量:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :max="$store.state.totalMax"
              :precision="6"
              placeholder="请输入数量"
              v-decorator="['quantity', {rules:[{required: true, message: '请输入数量'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="单位:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入单位" v-decorator="['unit', {rules:[{required: true, message: '请输入单位'}]}]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="规格型号:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入规格型号" v-decorator="['specification']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="凭证号:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入凭证号" v-decorator="['accNumber']" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="部门:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入部门" v-decorator="['deptName']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="领料人:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入领料人" v-decorator="['picker']" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="制单人:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入制单人" v-decorator="['biller']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="记帐人:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入记帐人" v-decorator="['booker']" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="审核人:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入审核人" v-decorator="['auditor']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="仓库:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入仓库" v-decorator="['warehouse']" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="用途:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入用途" v-decorator="['purpose']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="科目"
            validateStatus="success"
          >
            <select-down
              ref="accountSelet"
              treeType="account"
              v-decorator="['accountTitleId', {rules:[{required: false, message: '请选择科目'}]}]"
              @select="accountSelected"
              placeholder="请选择科目"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="备注"
            :labelCol=" {xs: { span: 24 },sm: { span: 3 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
            :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
          >
            <a-textarea v-decorator="['remark']" maxlength="200" :rows="3"></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { SelectDown } from '@/components'
export default {
  name: 'MaterialModal',
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
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      type: 0,
      currentYear: 2019
    }
  },
  methods: {
    accountSelected (accountTitleId) {
      this.form.setFieldsValue({ accountTitleId: accountTitleId })
    },
    add (type, currentYear) {
      this.type = type
      this.title = '添加物料'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = 0
      this.currentYear = currentYear
      this.$nextTick(() => {
        this.$refs.accountSelet.setValue(0)
      })
    },
    edit (record, type, currentYear) {
      this.type = type
      this.title = `编辑物料[${record.mname}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.currentYear = currentYear
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['acqDate'])
        if (record.rdDeptId === 0) {
          this.$initForm(this.form, { rdDeptId: null })
        }
        if (record.accountTitleId === 0) {
          this.$initForm(this.form, { accountTitleId: null })
        }
        this.$refs.accountSelet.setValue(record.accountTitleId > 0 ? record.accountTitleId : 0)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          // 设为默认0
          values.rdDeptId = 0
          values.id = this.id
          values.type = this.type
          if (values.id === 0) {
            this.$http.post('/material/add', values)
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
            this.$http.post('/material/edit', values)
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
