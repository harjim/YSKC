<!--
 * @Author: zdf
 * @Date: 2021-08-18 09:40:01
 * @LastEditTime: 2021-08-18 10:13:51
 * @LastEditors: zdf
 * @Description: 设置领料信息
 * @FilePath: \RS-VUE\src\views\dataentry\modules\SetPickerModal.vue
-->
<template>
  <a-modal
    title="设置领料信息"
    :visible="visible"
    @ok="handleOk"
    width="350px"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    @cancel="closeModal">
    <a-form :form="form">
      <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 14 }" label="领料人">
        <a-input v-decorator="['picker',{rules: [{ required: true, message: '请输入领料人' }]}]" />
      </a-form-item>

      <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 14 }" label="制单人">
        <a-input v-decorator="['biller',{rules: [{ required: true, message: '请输入制单人' }]}]" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      confirmLoading: false,
      ids: [],
      form: this.$form.createForm(this)
    }
  },
  methods: {
    open (ids) {
      this.ids = ids
      this.visible = true
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    handleOk () {
      this.confirmLoading = true
      this.form.validateFields((error, values) => {
        if (!error) {
          values.ids = this.ids
          this.$http.post('/material/setPicker', values).then(res => {
            if (res.success && res.data) {
              this.$message.success('设置成功')
              this.$emit('ok')
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage || '设置失败，请联系管理员')
              this.confirmLoading = false
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    closeModal () {
      this.visible = false
      this.confirmLoading = false
    }
  }
}
</script>

<style>

</style>
