<!--
 * @Author: zdf
 * @Date: 2021-10-28 15:51:33
 * @LastEditTime: 2021-11-12 08:31:18
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \MS-VUE\src\views\patent\modules\SetEngineerModal.vue
-->
<template>
  <a-modal
    :width="400"
    :visible="visible"
    @cancel="closeModal"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    title="分配工程师"
    @ok="handleSubmit"
  >
    <a-form :form="form">
      <a-form-item label="工程师" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <search-select
          style="width: 250px"
          url="/user/userForSelect"
          :paramData="{ hasDept: false, noLeave: true }"
          searchField="realName"
          sTitle="realName"
          :multiple="false"
          placeholder="请选择工程师"
          v-decorator="['engineer', { rules: [{ required: true, message: `请选择工程师` }] }]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { SearchSelect } from '@/components/Selects'
export default {
  components: {
    SearchSelect
  },
  props: {
    url: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      ids: [],
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 6 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 16 },
        sm: { span: 16 }
      }
    }
  },
  methods: {
    showModal (ids) {
      this.ids = ids
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post(this.url, { ids: this.ids, engineerId: values.engineer.id }).then(res => {
            if (res.success && res.data) {
              this.$message.success('分配成功')
              this.$emit('ok')
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage || '分配失败')
              this.confirmLoading = false
            }
          })
        }
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
