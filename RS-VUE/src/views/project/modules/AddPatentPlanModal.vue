<template>
  <a-modal
    :width="500"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :afterClose="afterClose"
  >
    <a-spin tip="文件上传中，请稍等....." :spinning="uploadStatus">
      <a-form @submit="handleSubmit" :form="form" ref="form">
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item label="项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                :allowClear="true"
                placeholder="请输选择项目"
                v-decorator="['projectId', {rules:[{required: true, message: '请输选择项目'}]}]"
                @change="selectChange"
              >
                <a-select-option
                  v-for="project in projectList"
                  :key="project.id"
                  :value="project.id"
                >
                  {{ `${project.rdTitle ? project.rdTitle + '-': '' }${ project.pname }` }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item label="申请名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入申请名称"
                v-decorator="['patentName', {rules:[{required: true, message: '请输入申请名称'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item label="交底书" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-col :span="18">
                <a-input
                  placeholder="请上传附件"
                  v-decorator="['docFileName', {rules:[{required: true, message: '请上传附件'}]}]"
                  :disabled="true"
                />
              </a-col>
              <a-col :span="2">
                <a-upload
                  :showUploadList="false"
                  :multiple="false"
                  :beforeUpload="beforeUpload"
                  @change="uploadChange"
                >
                  <a-button style="margin-left: 10px;">
                    <a-icon type="upload" />选择
                  </a-button>
                </a-upload>
              </a-col>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item label="发明人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入发明人"
                v-decorator="['inventor', {rules:[{required: false, message: '请输入发明人'}] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item label="发明人信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-upload
                :multiple="true"
                :fileList="fileList"
                :beforeUpload="inventorBeforeUpload"
                :showUploadList="{ showRemoveIcon: true }"
                @change="inventorChange"
                @preview="onPreview"
                @download="downloadfile"
                v-decorator="['inventorInfo',{ rules: [{required:false,message:'请上传附件'}] }]"
              >
                <a-button style="margin-top: 8px;" >
                  <a-icon type="upload" />选择
                </a-button>
              </a-upload>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea
                :auto-size="{ minRows: 3, maxRows: 5 }"
                placeholder="请输入描述"
                v-decorator="['description']"
              ></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import yearMixin from '@/utils/yearMixin'
export default {
  mixins: [yearMixin],
  data () {
    return {
      title: '',
      visible: false,
      fileList: [],
      projectList: [],
      id: undefined,
      projectId: undefined,
      file: {
        path: '',
        ame: ''
      },
      year: '',
      uploadStatus: false,
      typeModal: false, // true表示添加模态框 false 表示编辑模态框
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 }
      }
    }
  },
  created () {
    this.loadProject()
  },
  methods: {
    search () {
      this.loadProject()
    },
    add (currentYear) {
      this.title = '添加专利立项'
      this.typeModal = true
      this.form.resetFields()
      this.visible = true
      this.year = currentYear
    },
    edit (record, currentYear) {
      this.title = '编辑专利立项'
      this.typeModal = false
      this.year = currentYear
      this.id = record.id
      this.visible = true
      this.form.resetFields()
      this.file.name = record.docName
      this.file.path = record.disclosureParperPath
      this.projectId = record.projectId
      if (record.inventorInfo) {
        const temp = []
        this.translateStrToList(record.inventorInfo).forEach(url => {
          const name = url.substring(url.lastIndexOf('/') + 14, url.length)
          temp.push({
            uid: name,
            name,
            status: 'done',
            url
          })
        })
        this.fileList = temp
      }
      this.$nextTick(() => {
        this.form.setFieldsValue({
          projectId: record.projectId,
          patentName: record.patentName,
          docFileName: record.docName,
          inventor: record.inventor,
          description: record.description
        })
      })
    },
    afterClose () {
      this.id = undefined
      this.title = ''
      this.file.name = ''
      this.file.path = ''
      this.fileList = []
      this.form.resetFields()
      this.uploadStatus = false
    },
    handleSubmit () {
      // 判断是否上传文件中
      if (this.uploadStatus) {
        this.$message.warning('文件在上传中，请稍后再提交')
        return
      }
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          if (this.projectId) {
            values.projectId = this.projectId
          }
          if (this.file.path) {
            values.filePath = this.file.path
          }
          if (this.fileList) {
            values.inventorInfo = this.translateListToStr(this.fileList)
          }
          if (this.id) {
            values.id = this.id
          }
          values.year = this.year
          // true表示添加模态框 false 表示编辑模态框
          this.$http.post(this.typeModal ? '/patentPlan/addPatentPlan' : '/patentPlan/editPatentPlan', values).then(res => {
            if (res.success && res.data) {
              this.visible = false
              this.$emit('ok', false)
            } else {
              this.$message.error(res.errorMessage || '操作失败')
            }
          })
        }
      })
    },
    loadProject () {
      this.$http.get('/project/getSelectList', { params: { year: this.currentYear, sign: 2 } })
        .then(res => {
          this.projectList = []
          if (res.data != null && res.data.length > 0) {
            this.projectList = res.data
          }
          this.projectList.push({ id: -1, rdTitle: '', pname: '其他' })
        })
    },
    selectChange (value) {
      this.projectId = value
    },
    inventorBeforeUpload (file, key) {
      this.file.name = file.name
      this.uploadStatus = true // 上传状态中
      const param = new FormData()
      param.append('file', file)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/patentPlan/upload', param, config).then(res => {
        if (res.success) {
          this.uploadStatus = false
          this.fileList.push({
            uid: res.data.fileName,
            name: res.data.fileName,
            status: 'done',
            url: res.data.filePath
          })
        }
      }).catch(res => {
      })
      return false
    },
    inventorChange (file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
      }
    },
    beforeUpload (file, key) {
      this.file.name = file.name
      this.uploadStatus = true // 上传状态中
      const param = new FormData()
      param.append('file', file)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/patentPlan/upload', param, config).then(res => {
        if (res.success) {
          this.uploadStatus = false
          this.file.path = res.data.filePath
        }
      }).catch(res => {
      })
      return false
    },
    uploadChange () {
      const { form: { setFieldsValue } } = this
      setFieldsValue({ docFileName: this.file.name })
    },
    translateListToStr (fileLists) {
      if (!fileLists) return
      return fileLists.map((file) => { return file.url }).join(',')
    },
    translateStrToList (str) {
      if (!str) return
      return str.split(',')
    },
    onPreview () { },
    downloadfile () { }
  }
}
</script>

<style lang="less" scoped>
.upload-list {
  display:flex;
  /deep/ .ant-upload-list-text {
     width: 275px;
    // div:first-child {
    //   margin-top: -8px;
    // }
  }
}
// .upload-list-inline /deep/ .ant-upload-list-item {
//   // float: left;
//   // width: 200px;
//   // margin-right: 8px;
// }
// .upload-list-inline  /deep/  .ant-upload-animate-enter {
//   animation-name: uploadAnimateInlineIn;
// }
// .upload-list-inline  /deep/  .ant-upload-animate-leave {
//   animation-name: uploadAnimateInlineOut;
// }
</style>
