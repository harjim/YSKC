<template>
  <a-modal
    :width="400"
    :visible="visible"
    @cancel="closeModal"
    :maskClosable="false"
    :title="`分配${currentType.title}人员`"
    @ok="handleSubmit"
  >
    <a-form :form="form">
      <a-form-item :label="`${currentType.title}负责人`" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <search-select
          style="width: 250px"
          :paramData="{ noLeave: true }"
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :placeholder="`请选择${currentType.title}负责人`"
          v-decorator="['master', { rules: [{ required: true, message: `${currentType.title}负责人不能为空` }] }]"
        />
      </a-form-item>
      <a-form-item label="辅助人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <search-select
          style="width: 250px"
          :paramData="{ noLeave: true }"
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="true"
          placeholder="请选择辅助人员"
          v-decorator="['staffs', { rules: [{ required: false, message: `辅助人员不能为空` }] }]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { SearchSelect } from '@/components/Selects'
const mTypeArr = [
  { title: '技术', url: '/innovationProject/setTechIds' },
  { title: '财务', url: '/innovationProject/setFinanceIds' }
]
export default {
  components: {
    SearchSelect
  },
  data () {
    return {
      title: '',
      innovationIds: [],
      visible: false,
      currentType: {},
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
    showModal (innovationIds, mType) {
      this.currentType = mTypeArr[mType - 1]
      this.innovationIds = innovationIds
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          const params = { innovationIds: this.innovationIds, masterId: values.master.id }
          if (values.staffs) {
            params.userIds = values.staffs.map(a => a.id)
          }
          this.$http.post(this.currentType.url, params).then(res => {
            if (res.success && res.data) {
              this.$message.success(`分配${this.currentType.title}人员成功`)
              this.$emit('ok')
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage || `分配${this.currentType.title}人员失败`)
            }
          })
        }
      })
    },
    closeModal () {
      this.visible = false
      this.innovationIds = []
    }
  }
}
</script>

<style>
</style>
