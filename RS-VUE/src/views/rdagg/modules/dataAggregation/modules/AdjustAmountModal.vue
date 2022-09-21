
<template>
  <a-modal
    title="调整费用"
    :width="500"
    :visible="isVisible"
    :maskClose="false"
    @cancel="isVisible = false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <template v-if="isEmployee">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="工资" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入工资"
                v-decorator="['amount', {rules:[{required: false, message: '请输入工资'}]}]"
                :precision="2"
                :max="$store.state.totalMax"
                :min="-$store.state.totalMax"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="五险一金:" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入五险一金"
                v-decorator="['otherAmount', {rules:[{required: false, message: '请输入五险一金'}]}]"
                :precision="2"
                :max="$store.state.totalMax"
                :min="-$store.state.totalMax"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </template>
      <template v-else>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="调整费用:" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入调整费用"
                v-decorator="['amount', {rules:[{required: true, message: '请输入调整费用'}]}]"
                :precision="2"
                :max="$store.state.totalMax"
                :min="-$store.state.totalMax"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </template>
    </a-form>
  </a-modal>
</template>
<script>
export default {
  name: 'AdjustAmountModal',
  props: {
    isEmployee: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      isVisible: false,
      form: this.$form.createForm(this),
      params: {},
      confirmLoading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      }
    }
  },
  methods: {
    show (params) {
      this.form.resetFields()
      this.isVisible = true
      this.params = params
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const params = Object.assign(values, this.params)
          this.$http.post('/project/setAdjustAmount', params)
            .then(res => {
              if (res.success) {
                this.isVisible = false
                this.$emit('ok')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
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
<style lang='less' scoped>
</style>
