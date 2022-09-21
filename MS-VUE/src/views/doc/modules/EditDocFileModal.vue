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
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-card :bordered="false">
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="多个" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-radio-group v-decorator="['multiple', { rules: [{ required: true, message: '请输入多个' }] }]">
                  <a-radio :value="true">是</a-radio>
                  <a-radio :value="false">否</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="启用" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-radio-group v-decorator="['enabled', { rules: [{ required: true, message: '请输入禁用' }] }]">
                  <a-radio :value="true">是</a-radio>
                  <a-radio :value="false">否</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="必须" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-radio-group v-decorator="['required', { rules: [{ required: true, message: '请输入必须' }] }]">
                  <a-radio :value="true">是</a-radio>
                  <a-radio :value="false">否</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="需要编辑" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-radio-group v-decorator="['needEdit', { rules: [{ required: true, message: '请输入必须' }] }]">
                  <a-radio :value="true">是</a-radio>
                  <a-radio :value="false">否</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="所属" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select v-decorator="['owner', { rules: [{ required: true, message: '请输入必须' }] }]">
                  <a-select-option :key="'0'">技术</a-select-option>
                  <a-select-option :key="'1'">财务</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="默认模板" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  v-decorator="['defaultTemplateId', { rules: [{ required: true, message: '请选择默认模板' }] }]"
                >
                  <a-select-option v-for="(item, index) in docTemps" :key="index" :value="String(item.id)">
                    {{ item.templateName }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="限制唯一" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  v-decorator="['uniqueness', { rules: [{ required: true, message: '请选择限制唯一' }] }]"
                >
                  <a-select-option :key="'0'">不限制</a-select-option>
                  <a-select-option :key="'1'">阶段</a-select-option>
                  <a-select-option :key="'2'">月份</a-select-option>
                  <a-select-option :key="'3'">年份</a-select-option>
                  <a-select-option :key="'4'">项目</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <!-- <a-progress :percent="percent" v-if="true" /> -->
        </a-card>
      </a-form>
    </a-spin>
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
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      isAdd: false,
      fileList: [],
      fileItem: {},
      fileName: '',
      percent: 0,
      docTemps: []
    }
  },
  created () {},
  methods: {
    handleChange (info) {
      this.fileName = info.file.name
      this.fileItem = info.file
      if (this.documentName === '') {
        const {
          form: { setFieldsValue }
        } = this
        setFieldsValue({ documentName: info.file.name.split('.')[0] })
      }
    },
    beforeUpload (file) {
      return false
    },
    changeType (type) {
      return this.$http.get('/docList/selectMaxSeq', { params: { listType: type } }).then(res => {
        if (res.data) {
          this.$initForm(this.form, { seq: res.data })
        } else {
          this.$initForm(this.form, { seq: 1 })
        }
        return res.data
      })
    },
    edit (record) {
      this.form.resetFields()
      this.fileList = []
      this.fileItem = {}
      this.fileName = ''
      this.title = '编辑[' + record.docName + ']'
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.loadDefaultFile(record.id)
        this.$initForm(this.form, record)
      })
      this.isAdd = true
    },
    loadDefaultFile (id) {
      return this.$http.get('/docList/findDocFileId', { params: { docFileId: id } }).then(res => {
        if (res.success && res.data) {
          this.docTemps = res.data
        } else {
          this.docTemps = []
          this.$message.errorMessage(res.errorMessage ? res.errorMessage : '获取模板列表失败')
        }
      })
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id !== 0) {
            values.id = this.id
            this.$http
              .post('/docList/edit', values)
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

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
