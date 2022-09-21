<!--
 * @Author: ldx
 * @Date: 2021-07-28 09:40:34
 * @LastEditTime: 2021-07-28 14:16:27
 * @LastEditors: ldx
 * @Description: 设置定稿名称modal
 * @FilePath: \MS-VUE\src\views\patent\modules\SetFinalizedNameModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="500"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="定稿名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['finalizedName', {rules: [{ required: true, message: '请输入定稿名称' }] }]"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
import { setName } from '@/api/patent/applyList'
export default {
  name: 'SetFianliedName',
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 }
      },
      isVisible: false,
      title: '',
      form: this.$form.createForm(this),
      confirmLoading: false,
      updateId: undefined
    }
  },
  methods: {
    show (record, title = '设置定稿名称') {
      this.updateId = record.id
      this.isVisible = true
      this.title = `${title}[${record.companyName}]`
    },
    edit (record, title = '编辑定稿名称') {
      this.updateId = record.id
      this.isVisible = true
      this.title = `${title}[${record.companyName}]`
      const formData = { finalizedName: undefined }
      if (record.finalizedName) {
        formData.finalizedName = record.finalizedName
      }
      this.form.setFieldsValue(formData)
    },
    afterClose () {
      this.title = ''
      this.updateId = undefined
      this.confirmLoading = false
      this.form.resetFields()
    },
    handleSubmit () {
      this.confirmLoading = true
      this.form.validateFields((errors, values) => {
        if (errors) return
        if (this.updateId) { values.id = this.updateId }
        console.log('values', values)
        setName(values).then(data => {
          if (data) {
            this.isVisible = false
            this.$emit('success', true)
          }
        }).catch(error => {
          this.message.error(error.message || '系统异常，请联系管理员！')
          this.confirmLoading = false
        })
      })
    }
  }
}
</script>
<style lang='less' scoped>
</style>
