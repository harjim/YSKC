<template>
  <a-modal
    :title="title"
    :width="700"
    v-model="isVisible"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="isVisible = false"
    :afterClose="resetData"
  >
    <div class="clearfix">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="文件名"
              :labelCol=" {xs: { span: 4 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 20 },sm: { span: 20 } }"
            >
              <a-input
                v-decorator="['documentName', {rules:[{required: true, message: '请输入文件名'}]}]"
                style="width:433px"
                @change="documentNameChange"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-form-item
            label="附件"
            :labelCol=" {xs: { span: 4 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 20 },sm: { span: 20 } }"
            style="margin-left:-10px"
          >
            <a-col :span="16">
              <a-input
                v-decorator="['doc', {rules:[{required: true, message: '请上传附件'}]}]"
                placeholder="请上传附件"
                :disabled="true"
              />
            </a-col>
            <a-col :span="4">
              <a-upload
                :accept="accept"
                :multiple="false"
                :showUploadList="false"
                :beforeUpload="beforeUpload"
              >
                <a-button>
                  <a-icon type="upload" />选择
                </a-button>
              </a-upload>
            </a-col>
          </a-form-item>
        </a-row>
      </a-form>
    </div>
  </a-modal>
</template>
<script>
export default {
  name: 'UploadModal',
  props: {
    patternMap: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      file: undefined,
      fileName: undefined,
      fileSuffix: undefined,
      isVisible: false,
      form: this.$form.createForm(this),
      projectId: undefined,
      title: '',
      record: undefined,
      accept: ''
    }
  },
  mounted () {
  },
  methods: {
    documentNameChange (e) {
      this.fileName = e.target.value
    },
    beforeUpload (file) {
      if (this.verifyFileType(file, this.record.pattern)) {
        // const index = file.name.indexOf('.')
        const fileSuffixIndex = file.name.lastIndexOf('.')
        this.fileSuffix = file.name.substring(fileSuffixIndex, file.name.length)
        const name = this.fileName = file.name.substring(0, fileSuffixIndex)
        this.form.setFieldsValue({ documentName: name, doc: file.name })
        this.file = file
      } else {
        this.$message.warning('此材料文件限制上传文件为' + this.record.pattern)
      }
      return false
    },
    verifyFileType (file, pattern) {
      if (pattern === '所有文件') {
        return true
      }
      const index = file.name.lastIndexOf('.')
      const fileType = file.name.substring(index).toLocaleLowerCase()
      const acceptAry = this.accept.split(',')
      if (acceptAry.includes(fileType)) {
        return true
      }
    },
    show (record, projectId) {
      this.isVisible = true
      this.projectId = projectId
      const acceptAry = record.pattern.split(',')
      if (acceptAry.length) {
        for (const pattern of acceptAry) {
          this.accept += this.patternMap[pattern] + ','
        }
      }
      this.title = `[${record.itemName}]-上传文件`
      this.record = record
      this.file = undefined
    },
    resetData () {
      this.file = undefined
      this.fileName = undefined
      this.fileSuffix = undefined
      this.isVisible = false
      this.projectId = undefined
      this.title = ''
      this.record = undefined
      this.form.resetFields()
      this.accept = ''
    },
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          if (!this.form.getFieldValue('doc')) {
            this.$message.info('请选择导入的文件！')
            return
          }
          if (!this.form.getFieldValue('documentName')) {
            this.$message.info('请输入文件名！')
            return
          }
          // 给Param添加需要上传的文件
          const param = new FormData()
          param.append('file', this.file)
          param.append('fileName', this.fileName + this.fileSuffix)
          const paramObj = { stageListId: this.record.id, projectId: this.projectId }
          if (this.record.id) {
            paramObj['id'] = this.record.id
            paramObj['projectId'] = this.projectId
          }
          for (const key in paramObj) {
            param.append(key, paramObj[key])
          }
          // 设置请求头
          const config = { headers: { 'Content-Type': 'multipart/form-data' } }
          this.$http.post('/techAttachments/upload', param, config).then((res) => {
            if (res.data && res.success) {
              this.record.files.push(res.data)
              this.isVisible = false
              this.$message.success('操作成功')
              this.resetData()
            } else {
              this.$message.error(res.errorMessage)
            }
          }).catch((error) => {
            this.$message.error(error.message)
          })
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
