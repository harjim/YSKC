<template>
  <a-modal
    :width="400"
    :visible="visible"
    @cancel="closeModal"
    :maskClosable="false"
    :title="`分配${currentType.title}`"
    @ok="handleSubmit"
  >
    <a-form :form="form">
      <a-form-item :label="currentType.title" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <search-select
          style="width: 250px"
          :paramData="{ noLeave: true }"
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="currentType.title === '工程师'"
          :placeholder="currentType.title"
          v-decorator="['staffs', { rules: [{ required: true, message: `${currentType.title}不能为空` }] }]"
        />
      </a-form-item>
      <a-form-item v-if="currentType.title === '工程师'" label="是否追加:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-checkbox :checked="isAdd" @change="onChange">
        </a-checkbox>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { SearchSelect, DeptSelect } from '@/components/Selects'
const mTypeArr = [
  { title: '业务员', url: '/patentAppliaction/setOwner' },
  { title: '工程师', url: '/patentAppliaction/setEngineer' }
]
export default {
  components: {
    SearchSelect,
    DeptSelect
  },
  data () {
    return {
      title: '',
      projectIds: [],
      visible: false,
      currentType: {},
      isAdd: false,
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
    showModal (projectIds, mType) {
      this.currentType = mTypeArr[mType]
      this.projectIds = projectIds
      this.isAdd = false
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      validateFields((errors, values) => {
        if (!errors) {
          const params = {
            projectIds: this.projectIds,
            staffIds: this.currentType.title === '工程师' ? values.staffs.map(a => a.id) : [values.staffs.id],
            add: this.isAdd
          }
          this.$http.post(this.currentType.url, params).then(res => {
            if (res.success && res.data) {
              this.$message.success(`分配${this.currentType.title}成功`)
              this.$emit('ok')
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : `分配${this.currentType.title}失败`)
            }
          })
        }
      })
    },
    closeModal () {
      this.visible = false
      this.projectIds = []
    },
    owerChange (d) {
      var deptId
      if (d) {
        deptId = d.deptId
      }
      this.form.setFields({ deptId: { value: deptId } })
    },
    onChange (value) {
      this.isAdd = value.target.checked
    }
  }
}
</script>

<style></style>
