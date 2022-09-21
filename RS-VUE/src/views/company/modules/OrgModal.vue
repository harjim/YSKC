<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级部门">
        <select-down
          treeType="dept"
          ref="deptSelect"
          placeholder="请选择部门"
          v-decorator="['id',{rules:[{required: true, message: '请选择部门'}]}]"
          @select="selectValue"
        />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门名称">
        <a-input v-decorator="['deptName', {rules:[{required: true, message: '请输入部门名称'}]}]" />
      </a-form-item>
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="描述"
        :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 300 ? 300 : r.length}/300)`}"
      >
        <a-textarea :rows="5" v-decorator="['remark']" maxlength="300" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import SelectDown from '@/components/SelectDown'
export default {
  components: {
    SelectDown
  },
  name: 'OrgModal',
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
      parentId: 0,
      title: '',
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add (record) {
      this.title = '添加下级部门'
      const { form: { setFieldsValue } } = this
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
      this.$nextTick(() => {
        setFieldsValue({ id: record.id.toString() })
        if (this.$refs.deptSelect) {
          this.$refs.deptSelect.setValue(record.id)
        }
      })
    },
    edit (record) {
      this.title = `编辑部门[${record.deptName}]`
      const { form: { setFieldsValue } } = this
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.parentId = record.parentId
      this.$nextTick(() => {
        setFieldsValue({ id: record.parentId.toString(), deptName: record.deptName, remark: record.remark })
        if (this.$refs.deptSelect) {
          this.$refs.deptSelect.setValue(record.parentId)
        }
      })
    },
    addSNDept (record) {
      this.title = '添加平级部门'
      const { form: { setFieldsValue } } = this
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
      this.$nextTick(() => {
        setFieldsValue({ id: record.parentId.toString() })
        if (this.$refs.deptSelect) {
          this.$refs.deptSelect.setValue(record.parentId)
        }
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
          if (this.id < 0) {
            this.$http.post('/dept/addDept', { parentId: values.id, deptName: values.deptName, remark: values.remark })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                  this.$getTree('dept', true).then(res => {
                    this.$refs.deptSelect.loadTree()
                  })
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            if (Number(values.id) === Number(this.id)) {
              this.$message.info('上级部门不能为部门本身')
              this.confirmLoading = false
              return
            }
            this.$http.post('/dept/updateDept', { parentId: values.id, deptName: values.deptName, remark: values.remark, id: this.id }).then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', false)
                this.$getTree('dept', true).then(res => {
                  this.$refs.deptSelect.loadTree()
                })
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
              }
            })
              .finally(res => {
                this.confirmLoading = false
              })
          }
          // else if (this.id === -2) {
          //   if (Number(values.id) === Number(this.id)) {
          //     this.$message.info('上级部门不能为部门本身')
          //     this.confirmLoading = false
          //     return
          //   }
          //   this.$http.post('/dept/addSubordinateDept', { parentId: values.id, deptName: values.deptName, remark: values.remark })
          //     .then(res => {
          //       if (res.success && res.data) {
          //         this.visible = false
          //         this.confirmLoading = false
          //         this.$emit('ok', true)
          //       }
          //     }).finally(res => {
          //       this.confirmLoading = false
          //     })
          // }
        } else {
          this.confirmLoading = false
        }
      })
    },
    selectValue (deptId) {
      this.form.setFieldsValue({ id: deptId })
    }
  }
}
</script>

<style scoped>
</style>
