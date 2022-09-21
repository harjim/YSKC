<template>
  <a-modal
    :width="300"
    :visible="visible"
    title="设置电费单价"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item label="电费单价" :labelCol="{ span: 8 }" :wrapperCol="{ span: 16 }">
        <a-input-number
          :min="0.000001"
          :precision="6"
          placeholder="请输入电费单价"
          v-decorator="['powerUnitPrice', {rules:[{required: true, message: '请输入电费单价'}]}]"
          style="width:150px;"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>

export default {
  components: {
  },
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
      confirmLoading: false,
      projectId: 0,
      monthDate: undefined,
      visible: false,
      form: this.$form.createForm(this)
    }
  },
  created () {
  },
  methods: {
    show (projectId, monthDate) {
      this.form.resetFields()
      this.projectId = projectId
      this.monthDate = monthDate
      this.visible = true
      this.confirmLoading = false
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          values.projectId = this.projectId
          values.month = this.monthDate
          this.$http.post('/projectRdEquipment/setPowerUnitPrice', values)
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
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>
