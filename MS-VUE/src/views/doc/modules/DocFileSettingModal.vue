<template>
  <a-modal
    :width="400"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    @cancel="visible = false"
    :maskClosable="false"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-form-item label="自动添加" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-decorator="['autoAdd', {rules:[{required: true, message: '请选择是否自动添加'}]}]">
            <a-radio :value="true">是</a-radio>
            <a-radio :value="false">否</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-row>
      <a-row :gutter="24">
        <a-form-item label="按月添加" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-decorator="['monthly', {rules:[{required: true, message: '请选择是否按月添加'}]}]">
            <a-radio :value="true">是</a-radio>
            <a-radio :value="false">否</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-row>
      <a-row :gutter="24">
        <a-form-item label="月份前缀" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-decorator="['mPrefix', {rules:[{required: true, message: '请选择是否添加月份前缀'}]}]">
            <a-radio :value="true">是</a-radio>
            <a-radio :value="false">否</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-row>
      <a-row :gutter="24">
        <a-form-item label="需要编辑" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group v-decorator="['needEdit', {rules:[{required: true, message: '请选择是否需要编辑'}]}]">
            <a-radio :value="true">是</a-radio>
            <a-radio :value="false">否</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-row>
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
        sm: { span: 18 }
      },
      id: 0,
      index: -1,
      stageKey: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.form.resetFields()
      this.title = '添加'
      this.confirmLoading = false
      this.visible = true
      this.id = 0
    },
    edit (record, index) {
      this.form.resetFields()
      this.confirmLoading = false
      this.title = '设置[' + record.docName + ']'
      this.visible = true
      this.id = record.id
      this.stageKey = record.stageKey
      this.index = index
      this.$nextTick(() => {
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post('/docFileStage/updateDocFileStage', Object.assign({ id: this.id }, values)).then(res => {
            if (res.success && res.data) {
              this.$emit('editOk', this.stageKey, this.index, values)
              this.$message.success('设置成功')
              this.visible = false
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
            }
          }).finally(() => {
            this.confirmLoading = false
          })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>
