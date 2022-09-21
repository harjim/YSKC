<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级部门">
        <dept-select-only
          ref="deptSelect"
          v-decorator="['deptId', {rules:[{required: true, message: '请选择上级部门'}]}]"
          @deptFullPath="path=>fullPath = path"
          @deptSelect="deptSelect"
          style="width:100%;"
        />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门名称">
        <a-input
          placeholder="请输入部门名称"
          v-decorator="['deptName', {rules:[{required: true, message: '请输入部门名称'}]}]"
        />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="描述">
        <a-textarea :rows="5" v-decorator="['remark']" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import DeptSelectOnly from '@/components/DeptSelectOnly'
export default {
  name: 'DeptModal',
  components: {
    DeptSelectOnly
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      id: 0,
      fullPath: '',
      editFullPath: '',
      mdl: {},
      level: 0,
      deptId: undefined,
      form: this.$form.createForm(this),
      permissions: [],
      autoExpandParent: true,
      deptTree: [],
      title: ''
    }
  },
  mounted () {
    this.loadTree()
  },
  methods: {
    add () {
      this.title = '添加部门'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
      this.$nextTick(() => {
        if (this.$refs.deptSelect) {
          this.$refs.deptSelect.setDept()
        }
      })
    },
    edit (record) {
      this.loadTree()
      this.title = `编辑部门[${record.deptName}]`
      const { form: { setFieldsValue } } = this
      this.form.resetFields()
      this.visible = true
      this.level = record.level
      this.id = record.id
      this.editFullPath = record.fullPath
      this.$nextTick(() => {
        if (this.$refs.deptSelect) {
          this.$refs.deptSelect.setDept(record.parentId)
        }
        setFieldsValue({ deptId: record.parentId.toString(), deptName: record.deptName, remark: record.remark })
      })
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleCancel () {
      this.close()
    },
    onChange (value) {
      this.value = value
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            this.$http.post('/dept/addDept', { parentId: values.deptId, deptName: values.deptName, remark: values.remark, fullPath: this.fullPath })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/dept/updateDept', { parentId: values.deptId, deptName: values.deptName, remark: values.remark, id: this.id, level: this.level, fullPath: this.editFullPath })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    loadTree () {
      this.$http.get('/dept/tree')
        .then(res => {
          this.deptTree = res.data
        })
    },
    deptSelect (id) {
      this.deptId = id
      this.form.setFieldsValue({ deptId: id })
    }
  }
}
</script>

<style scoped>
</style>
