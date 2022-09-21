<template>
  <a-modal
    :title="title"
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
          <a-form-item label="所属部门:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-tree-select
              showSearch
              v-decorator="['deptIdArr',{rules:[{required: true, message: '请选择所属部门'}]}]"
              :dropdownStyle="{ maxHeight: '350px', overflow: 'scroll' }"
              :treeData="deptTree"
              treeNodeFilterProp="title"
              placeholder="请选择所属部门"
              treeDefaultExpandAll
              multiple
            ></a-tree-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'SetRdDept',
  deptTree: [],
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      userList: [],
      deptTree: []
    }
  },
  created () {
  },
  methods: {
    add (userList, deptTree) {
      this.title = '设置部门'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.userList = userList
      this.deptTree = deptTree
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.userList = this.userList
          this.$http.post('/user/setDept', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage)
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>
