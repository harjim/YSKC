<!--
 * @Author: your name
 * @Date: 2022-02-17 08:12:54
 * @LastEditors: lzh
 * @LastEditTime: 2022-02-17 16:57:28
 * @Description: In User Settings Edit
 * @FilePath: \RS-VUE\src\views\project\modules\ConceptFilesModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="600"
    v-model="visible"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form" v-bind="{labelCol, wrapperCol}">
      <a-form-item label="文件名">
        <a-input
          v-decorator="['documentName', {rules:[{required: true, message: '请输入文件名'}]}]"
          placeholder="请输入文件名"
        />
      </a-form-item>
      <a-form-item label="附件">
        <a-col :span="19">
          <a-input
            v-decorator="['doc', {rules:[{required: true, message: '请上传附件'}]}]"
            placeholder="请选择附件"
            :disabled="true"
          />
        </a-col>
        <a-col :span="2">
          <a-upload
            :fileList="fileList"
            :multiple="false"
            :beforeUpload="beforeUpload"
          >
            <a-button>
              <a-icon type="upload" />选择
            </a-button>
          </a-upload>
        </a-col>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      title: '',
      visible: false,
      confirmLoading: false,
      labelCol: { span: 5 },
      wrapperCol: { span: 19 },
      form: this.$form.createForm(this),
      fileList: [],
      uploadFile: undefined
    }
  },
  methods: {
    show (record, row) {
      this.record = { ...record }
      this.row = row
      this.title = `上传材料`
      this.form.resetFields()
      this.confirmLoading = false
      this.visible = true
    },
    beforeUpload (file) {
      const isImg = /^image\/*/.test(file.type)
      if (!isImg) {
        this.$message.error('只能上传图片')
        this.fileList = []
        return false
      }
      if (file && file.size > 1024 * 1024 * 10) {
        this.$message.error(`上传文件不得超过10M。`)
        this.fileList = []
        return false
      }
      this.form.setFieldsValue({ doc: file.name, documentName: file.name.slice(0, file.name.lastIndexOf('.')) })
      this.uploadFile = file
      return false
    },
    handleSubmit () {
      if (!this.uploadFile) {
        this.$message.warning('请选择附件')
        return
      }
      this.form.validateFields((errors, values) => {
        if (!errors) {
          const param = new FormData()
          param.append('file', this.uploadFile)
          param.append('fileName', values.documentName)
          param.append('projectId', this.record.projectId)
          param.append('docFileId', this.record.pdocFileId)

          const config = {
            // 添加请求头
            headers: { 'Content-Type': 'multipart/form-data' }
          }
          this.confirmLoading = true
          this.$http.post('/project/uploadFile', param, config).then(res => {
            if (res.success) {
              this.$message.success('添加成功')
              this.$emit('success', this.row)
            } else {
              this.$message.error(res.errorMessage || '添加失败')
            }
            return res
          }).catch(res => {
            this.$emit('error', res.message)
          }).finally(res => {
            this.visible = false
            this.confirmLoading = false
          })
        }
      })
    }
  }
}
</script>
