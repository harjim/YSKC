<!--
 * @Author: zdf
 * @Date: 2021-10-25 12:01:20
 * @LastEditTime: 2021-10-25 13:53:21
 * @LastEditors: zdf
 * @Description: 设置人员总数
 * @FilePath: \RS-VUE\src\views\rdorg\modules\SettingAmountModal.vue
-->
<template>
  <a-modal
    :title="`设置${year}人员总数`"
    style="top: 20px;"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading">
    <a-form
      :form="form"
    >
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="人员总数"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              :min="0"
              :max="$store.state.maxOrder"
              :precision="0"
              style="width:100%;"
              placeholder="人员总数"
              v-decorator="['employeeAmount', {rules:[{required: true, message: '请输入人员总数'}]}]" />
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
      year: undefined,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
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
    open (year) {
      this.form.resetFields()
      this.year = year
      this.visible = true
    },
    handleSubmit () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        values.ryear = this.year
        this.$http.post('/report/saveEmployeeAmount', values).then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
            this.$emit('ok')
            this.closeModal()
          } else {
            this.$message.error(res.errorMessage || '保存失败')
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    closeModal () {
      this.confirmLoading = false
      this.visible = false
    }
  }
}
</script>

<style>

</style>
