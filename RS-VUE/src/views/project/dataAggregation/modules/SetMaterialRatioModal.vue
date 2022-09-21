<template>
  <a-modal
    title="设置损耗率"
    style="top: 20px;"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="损耗率:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入损耗率"
              v-decorator="['ratio', {rules:[{required: true, message: '请输入损耗率'}]}]"
              :precision="6"
              :max="1"
              :min="0"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      form: this.$form.createForm(this),
      visible: false,
      confirmLoading: false,
      params: {}
    }
  },
  methods: {
    set (params) {
      this.params = params
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post('/projectMaterial/setDepreciationRatio', Object.assign({ depreciationRatio: values.ratio }, this.params)).then(res => {
            if (res.success && res.data) {
              this.$message.success('设置成功')
              this.visible = false
              this.$emit('ok')
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
            }
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

<style>
</style>
