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
          <a-col :span="12">
            <a-form-item label="设计名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                :disabled="!isCreat"
                placeholder="请输入设计名称"
                v-decorator="['dname', {rules:[{required: true, message: '请输入设计名称'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="设计日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                format="YYYY-MM-DD"
                placeholder="请选择设计日期"
                v-decorator="['designDate',{rules:[{required: true, message: '请选择设计日期'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="设计费用" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                placeholder="请输入设计费用"
                style="width:100%"
                v-decorator="['dFee', {rules:[{required: true, message: '请输入设计费用'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门">
              <a-input
                placeholder="请输入部门"
                v-decorator="['deptName', {rules:[{required: false, message: '请输入部门'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="科目" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <select-down
                ref="selectDown"
                treeType="account"
                v-decorator="['accountTitleId', {rules:[{required: false, message: '请选择科目'}]}]"
                placeholder="请选择科目"
                @select="getSelectVal"
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
              <a-textarea v-decorator="['remark']" :rows="3" maxlength="200"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import SelectDown from '@/components/SelectDown'
export default {
  components: {
    SelectDown
  },
  name: 'DesignModal',
  data () {
    return {
      title: '',
      rdDeptTree: [],
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
      isCreat: false,
      accountTree: [],
      form: this.$form.createForm(this)
    }
  },
  created () {
  },
  methods: {
    add () {
      this.title = '添加研发设计'
      this.confirmLoading = false
      this.visible = true
      this.isCreat = true
      this.form.resetFields()
      this.id = -1
      this.$nextTick(() => {
        this.$refs.selectDown.setValue(0)
      })
    },
    edit (record) {
      this.title = `编辑研发设计[${record.dname}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['designDate'])
        this.$refs.selectDown.setValue(record.accountTitleId)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            values.id = this.id
            this.$http.post('/design/addDesign', values)
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
            this.$http.post('/design/updateDesign', values)
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
    },
    getSelectVal (val) {
      this.form.setFieldsValue({ accountTitleId: val })
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
