<!--
 * @Author: ldx
 * @Date: 2020-11-09 08:42:13
 * @LastEditTime: 2020-11-09 10:30:40
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\modules\setAllotterPerson.vue
-->
<template>
  <a-modal
    :width="400"
    :visible="visible"
    @cancel="closeModal"
    :maskClosable="false"
    :title="title"
    @ok="handleSubmit"
  >
    <a-form :form="form">
      <a-form-item label="分配负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <search-select
          style="width: 250px"
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="false"
          placeholder="分配负责人"
          v-decorator="['person', { rules: [{ required: true, message: `分配负责人不能为空` }] }]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { SearchSelect, DeptSelect } from '@/components/Selects'
export default {
  components: {
    SearchSelect,
    DeptSelect
  },
  data () {
    return {
      title: '',
      selectedRows: [],
      visible: false,
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
    showModal (selectedRows) {
      this.title = '分配负责人'
      this.selectedRows = selectedRows
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          for (const row of this.selectedRows) {
            row.managerId = values.person.id
          }
          this.$http.post('/rsProduct/allocatingStaff', this.selectedRows).then(res => {
            if (res.success && res.data) {
              this.$message.success('操作成功')
              this.$emit('ok', false)
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : `操作失败`)
            }
          })
        }
      })
    },
    closeModal () {
      this.title = ''
      this.visible = false
      this.selectedRows = []
    }
  }
}
</script>

<style>
</style>
