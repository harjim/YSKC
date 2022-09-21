<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    @cancel="visible = false"
  >
    <a-form :form="form">
      <a-card title="账户信息" :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                :disabled="! isCreat"
                v-decorator="['userName', {rules:[{required: true, message: '请输入用户名'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="真实姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['realName', {rules:[{required: true, message: '请输入真实姓名'}]}]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['tel', {rules:[{required: true, message: '请输入联系电话'}]}]" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="性别">
              <a-radio-group v-decorator="['gender']">
                <a-radio value="1">男</a-radio>
                <a-radio value="2">女</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="isCreat">
          <a-col :span="12">
            <a-form-item label="初始密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['password', {rules:[{required: true, message: '请输入初始密码'}]}]" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
      <a-card title="组织机构" :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-tree-select
                showSearch
                v-decorator="['deptIdArr',{rules:[{required: true, message: '请选择部门'}]}]"
                :dropdownStyle="{ maxHeight: '350px', overflow: 'scroll' }"
                :treeData="deptTree"
                treeNodeFilterProp="title"
                placeholder="请选择部门"
                treeDefaultExpandAll
                multiple
                :maxTagCount="3"
              ></a-tree-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="职位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['postion', {rules:[{required: true, message: '请输入职位'}]}]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="管理类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select mode="multiple" placeholder="请选择管理类型" allowClear v-decorator="['mtypesArr', {rules:[{required: false, message: '请选择管理类型'}]}]">
                <a-select-option value="1">技术人员</a-select-option>
                <a-select-option value="2">财务人员</a-select-option>
                <a-select-option value="3">业务员</a-select-option>
                <a-select-option value="4">谈单经理</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item label="备注">
              <a-textarea v-decorator="['remark']" :rows="4"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 17 }
      },
      id: 0,
      deptTree: [],
      visible: false,
      resultParam: {},
      isCreat: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      oldDeptId: []
    }
  },
  methods: {
    add (deptTree) {
      this.title = '添加员工'
      this.isCreat = true
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
      this.deptTree = deptTree
    },
    edit (record, deptTree) {
      this.isCreat = false
      this.title = '修改员工[' + record.userName + ']'
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.deptTree = deptTree
      this.oldDeptId = record.deptIdArr
      this.$nextTick(() => {
        if (record.mtypes) {
          record.mtypesArr = record.mtypes.split(',')
        }
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      this.visible = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          if (values.mtypesArr && values.mtypesArr.length) {
            values.mtypes = values.mtypesArr.join(',')
            delete values.mtypesArr
          }
          if (!values.id || values.id <= 0) {
            this.$http.post('/user/addUser', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$message.success('添加成功')
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.oldDeptId = this.oldDeptId
            this.$http.post('/user/updateUser', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$message.success('更新成功')
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>
