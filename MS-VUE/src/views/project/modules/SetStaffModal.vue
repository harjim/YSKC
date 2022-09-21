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
      <template v-if="currentType.title !== '业务人员'">
        <a-form-item :label="currentType.title" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <search-select
            style="width: 250px"
            :paramData="{ noLeave: true }"
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="true"
            :placeholder="currentType.title"
            v-decorator="['staffs', { rules: [{ required: true, message: `${currentType.title}不能为空` }] }]"
          />
        </a-form-item>
        <a-form-item label="是否追加:" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-checkbox @change="onChange">
          </a-checkbox>
        </a-form-item>
      </template>
      <template v-else>
        <a-form-item label="业务人员" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <search-select
            style="width: 250px"
            url="/user/userForSelect"
            :paramData="{ hasDept: true, noLeave: true }"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="业务人员"
            v-decorator="['ower', { rules: [{ required: true, message: `业务人员不能为空` }] }]"
            @change="owerChange"
          />
        </a-form-item>
        <a-form-item label="谈单经理" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <search-select
            style="width: 250px"
            url="/user/userForSelect"
            :paramData="{ noLeave: true }"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="谈单经理"
            v-decorator="['business', { rules: [{ required: true, message: `谈单经理不能为空` }] }]"
          />
        </a-form-item>
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <dept-select
            ref="deptSelect"
            style="width: 250px"
            placeholder="所属部门"
            v-decorator="['deptId', { rules: [{ required: true, message: `所属部门不能为空` }] }]"
          />
        </a-form-item>
      </template>
    </a-form>
  </a-modal>
</template>

<script>
import { SearchSelect, DeptSelect } from '@/components/Selects'
const mTypeArr = [
  { title: '技术人员', url: '/project/setProjectTechIds' },
  { title: '财务人员', url: '/project/setProjectFinanceIds' },
  { title: '业务人员', url: 'project/setProjectOwerIds' }
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
      this.currentType = mTypeArr[mType - 1]
      this.projectIds = projectIds
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      validateFields((errors, values) => {
        if (!errors) {
          const params = { projectIds: this.projectIds }
          params.add = false
          if (this.currentType.title !== '业务人员') {
            params.staffIds = values.staffs.map(a => a.id)
            params.add = this.isAdd
          } else {
            params.owerId = values.ower.id
            params.businessId = values.business.id
            params.deptId = values.deptId
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
