<!--
 * @Author: ldx
 * @Date: 2020-08-21 16:54:40
 * @LastEditTime: 2020-08-22 11:42:02
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\dataAggregation\modules\SetRdAmountModal.vue
-->
<template>
  <a-modal
    title="设置研发费用"
    :width="500"
    :visible="isVisible"
    :maskClose="false"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="研发费用:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入研发费用"
              v-decorator="['rdAmount', {rules:[{required: true, message: '请输入研发费用'}]}]"
              :precision="2"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
export default {
  name: 'SetRdAmountModal',
  props: {
    url: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      isVisible: false,
      form: this.$form.createForm(this),
      params: {},
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
          values.setAmount = values.rdAmount
          const params = Object.assign(values, this.params)
          this.$http.post(this.url, params)
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
