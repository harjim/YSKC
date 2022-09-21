<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="900"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="模板名称" :labelCol="{ sm: { span: 3 } }" :wrapperCol="{ sm: { span: 19 } }">
            <a-input
              placeholder="请输入模板名称"
              v-decorator="['templateName', { rules: [{ required: true, message: '请输入模板名称' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="文件名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入文件名"
              v-decorator="['docTemplateName', { rules: [{ required: true, message: '请输入文件名' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="默认模板" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group
              v-decorator="['defaultTemplate', { rules: [{ required: true, message: '请选择是否为默认模板' }] }]"
            >
              <a-radio :value="true">是</a-radio>
              <a-radio :value="false">否</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="启用" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group v-decorator="['enabled', { rules: [{ required: true, message: '请选择是否启用' }] }]">
              <a-radio :value="true">是</a-radio>
              <a-radio :value="false">否</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="模板类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group v-decorator="['type', { rules: [{ required: true, message: '请选择模板类型' }] }]">
              <a-radio :value="0">普通</a-radio>
              <a-radio :value="1">委托</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
export default {
  name: 'DocListModal',
  components: {},
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
        sm: { span: 18 }
      },
      visible: false,
      confirmLoading: false,
      id: 0,
      docFileId: 0,
      form: this.$form.createForm(this)
    }
  },
  created () {},
  methods: {
    add (docFileId) {
      this.title = '添加模板'
      this.visible = true
      this.form.resetFields()
      this.docFileId = docFileId
    },
    edit (row) {
      this.fileList = []
      this.fileItem = {}
      this.fileName = ''
      this.title = '编辑[' + row.docName + ']'
      this.visible = true
      this.id = row.id
      this.$nextTick(() => {
        this.$initForm(this.form, row)
      })
      this.isAdd = true
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === 0) {
            values.docFileId = this.docFileId
            this.$http
              .post('/docList/save', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                  this.form.resetFields()
                } else {
                  this.$message.info(res.errorMessage)
                }
              })
              .finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http
              .post('/patentMaster/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', false)
                } else {
                  this.$message.info(res.errorMessage)
                }
              })
              .finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    afterClose () {
      this.isDisabled = false
      this.form.resetFields()
    }
  }
}
</script>
