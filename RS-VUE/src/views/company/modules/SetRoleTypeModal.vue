<!--
 * @Author: zdf
 * @Date: 2022-04-20 15:35:28
 * @LastEditTime: 2022-04-20 18:41:42
 * @LastEditors: zdf
 * @Description: 设置角色类型
 * @FilePath: \RS-VUE\src\views\company\modules\SetRoleTypeModal.vue
-->
<template>
  <a-modal
    title="设置角色"
    style="top: 20px;"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    @cancel="closeModal"
    :maskClosable="false"
    :confirmLoading="confirmLoading">
    <a-form :form="form">
      <a-row>
        <a-form-item
          label="角色类型"
          :labelCol="{span:6}"
          :wrapperCol="{span:16}">
          <a-select v-decorator="['roleType', {rules:[{required: true, message: '请选择角色类型'}]}]">
            <a-select-option value="0">普通</a-select-option>
            <a-select-option value="1">项目统筹</a-select-option>
          </a-select>
        </a-form-item>
      </a-row>
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
    show (ids) {
      this.ids = ids
      this.form.resetFields()
      this.visible = true
    },
    handleSubmit () {
      this.confirmLoading = true
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        this.$http.post('/employee/setRoleType', { ids: this.ids, roleType: values.roleType }).then(res => {
          if (res.success && res.data) {
            this.$message.success('设置成功')
            this.$emit('ok')
            this.closeModal()
          } else {
            this.$message.error(res.errorMessage || '设置失败')
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    closeModal () {
      this.visible = false
      this.confirmLoading = false
      this.ids = []
    }
  }

}
</script>

<style>

</style>
