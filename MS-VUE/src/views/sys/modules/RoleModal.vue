<template>
  <a-modal
    :width="640"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    :maskClosable="false"
    @cancel="visible = false"
  >
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-form-item label="角色名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['roleName', {rules:[{required: true, message: '请输入角色名称'}]}]" />
        </a-form-item>
        <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['remark']"></a-textarea>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  name: 'RoleModal',
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.title = '添加角色'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
    },
    edit (record) {
      this.title = '修改角色[' + record.roleName + ']'
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record)
        // setFieldsValue({ roleName: record.roleName, remark: record.remark, id: record.id })
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            this.$http.post('/role/add', { roleName: values.roleName, remark: values.remark })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/role/update', { roleName: values.roleName, remark: values.remark, roleId: this.id })
              .finally(res => {
                this.confirmLoading = false
                this.visible = false
                this.$emit('ok', false)
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
