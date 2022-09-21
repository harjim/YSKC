<template>
  <a-modal
    title="设置比例/废品单价"
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
          <a-form-item label="成品比例:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入成品比例"
              v-decorator="['ratio', {rules:[{required: false, message: '请输入成品比例'}]}]"
              :precision="2"
              :max="1"
              :min="0"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="废品单价:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入废品单价"
              v-decorator="['price', {rules:[{required: false, message: '请输入废品单价'}]}]"
              :max="$store.state.totalMax"
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
      list: []
    }
  },
  methods: {
    set (list) {
      this.list = list
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        var hasModify = false
        if (values.ratio || values.ratio === 0) {
          hasModify = true
          this.list.forEach(item => {
            item.finishQuantity = (values.ratio * item.used).toFixed(6)
            item.wasteQuantity = (Number(item.used) - Number(item.finishQuantity)).toFixed(6)
            item.finishAmount = item.finishQuantity * item.finishUnitPrice
          })
        }
        if (values.price || values.price === 0) {
          hasModify = true
          this.list.forEach(item => {
            item.wasteUnitPrice = values.price.toFixed(6)
            item.wasteAmount = (item.wasteQuantity * item.wasteUnitPrice).toFixed(6)
          })
        }
        this.list.forEach(item => {
          item.rdAmount = ((item.used * item.unitPrice) - Number(item.finishAmount ? item.finishAmount : 0) - Number(item.wasteAmount ? item.wasteAmount : 0))
        })
        if (hasModify) {
          this.$emit('ok', this.list)
        }
        this.confirmLoading = false
        this.visible = false
      })
    }
  }
}
</script>

<style>
</style>
