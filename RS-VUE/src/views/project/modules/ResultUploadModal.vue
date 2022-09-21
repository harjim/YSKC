<template>
  <a-modal
    :title="title"
    :width="width"
    v-model="visible"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :afterClose="close"
  >
    <a-spin :spinning="spinning" class="clearfix">
      <a-form @submit="handleSubmit" :form="form">
        <a-row :gutter="24" v-if="isNewReportCommit">
          <a-col :md="24" :sm="24">
            <a-form-item
              label="项目"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :validateStatus="status"
              :help="help">
              <a-select
                showSearch
                :allowClear="true"
                placeholder="请选择项目"
                v-decorator="['projectId', {rules:[{required: true, message: '请选择项目'}]}]"
                :disabled="isDisable"
                @change="selectProjectChange"
              >
                <a-select-option
                  v-for="project in projectList"
                  :key="project.id"
                  :value="project.id"
                >{{ project.rdTitle }} - {{ project.pname }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="isNewReportCommit">
          <a-col :md="24" :sm="24">
            <a-form-item label="项目阶段" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                :allowClear="true"
                placeholder="请选择项目阶段"
                v-decorator="['stageKey', {rules:[{required: false, message: '请选择项目阶段'}]}]"
                :disabled="isDisable"
                @change="selectStageChange"
              >
                <a-select-option
                  v-for="stage in stageList"
                  :key="stage.id"
                  :value="stage.stageKey"
                >{{ stage.stageType }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="isNewReportCommit" >
          <a-col :md="24" :sm="24">
            <a-form-item label="文件类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                :allowClear="true"
                placeholder="请选择文件类型"
                v-decorator="['listId', {rules:[{required: true, message: '请选择文件类型'}]}]"
                :disabled="isDisable"
                @change="selectFileTypeChange"
              >
                <a-select-option
                  v-for="fileType in typeList"
                  :key="fileType.id"
                  :value="fileType.id"
                >{{ fileType.docName }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <a-form-item
              label="文件名"
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
            >
              <a-input
                v-decorator="['documentName', {rules:[{required: true, message: '请输入文件名'}]}]"
                placeholder="请输入文件名"
                :disabled="isDisable"
                @change="documentNameChange"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <a-form-item
              label="附件:"
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
            >
              <a-col :span="19">
                <a-input
                  v-decorator="['doc', {rules:[{required: true, message: '请上传附件'}]}]"
                  :placeholder="placeholder"
                  :disabled="true"
                />
              </a-col>
              <a-col :span="2">
                <a-upload
                  :fileList="fileList"
                  :multiple="false"
                  :beforeUpload="beforeUpload"
                  :accept="accept"
                >
                  <a-button :disabled="isDisable">
                    <a-icon type="upload" />选择
                  </a-button>
                </a-upload>
              </a-col>
            </a-form-item>
          </a-col>
        </a-row>
        <!-- <a-progress :percent="percent" v-if="showProgress" /> -->
        <div v-if="showTemplate">
          <a :href="templateNameUrl">{{ templateName }}</a>
        </div>
        <div>
          <font>{{ message }}</font>
        </div>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
import { SelectProject } from '@/components'
export default {
  name: 'ResultUploadModal',
  props: {
    // 窗口宽度
    width: {
      type: Number,
      default: 600
    },
    // 标题
    title: {
      type: String,
      default: '数据导入'
    },
    // 文本框文案
    placeholder: {
      type: String,
      default: '请选择文件'
    },
    // 模板名称
    templateName: {
      type: String,
      default: undefined
    },
    // 模板下载地址
    templateNameUrl: {
      type: String,
      default: undefined
    },
    // 上传地址
    action: {
      type: String,
      required: true
    },
    // 上传参数对象
    paramData: {
      type: Object,
      default: undefined
    },
    // 上传参数名称
    paramKey: {
      type: String,
      default: 'paramKey'
    },
    // 是否显示进度条
    showProgress: {
      type: Boolean,
      default: false
    },
    year: {
      type: Number,
      required: true
    },
    isNewReportCommit: { // 是否查新专利 true表示不是查新专利上传； false表示查新专利上传
      type: Boolean,
      default: true
    }
  },
  components: {
    SelectProject
  },
  computed: {
    showTemplate () {
      return typeof this.templateNameUrl !== 'undefined'
    }
  },
  data () {
    return {
      spinning: false,
      tableField: undefined,
      percent: 0,
      fileList: [],
      uploadFile: undefined,
      fileName: '',
      message: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      projectList: [],
      stageList: [],
      typeList: [],
      labelCol: { xs: { span: 24 }, sm: { span: 4 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 18 } },
      status: undefined,
      help: undefined,
      isDisable: false,
      projectId: undefined,
      accept: 'image/png,image/jpg,image/jpeg,image/gif,application/pdf'
    }
  },
  created () {
    // this.loadProject()
    if (this.isNewReportCommit) {
      this.getTypes()
    }
  },
  methods: {
    selectProjectChange (value) {
      this.projectId = value
      this.loadStage(value)
      this.form.setFieldsValue({ stageKey: undefined })
    },
    selectStageChange (value) {
      // this.getTypes()
    },
    selectFileTypeChange (value) {

    },
    loadProject () {
      this.spinning = true
      this.$http.get('/project/getSelectList', { params: { year: this.year, sign: 0 } })
        .then(res => {
          if (res.data && res.success) {
            this.projectList = res.data
            if (!res.data.length) {
              this.status = 'error'
              this.help = '当前年份：' + this.year + '没有项目，请重新选择年份'
              this.isDisable = true
            }
          }
          this.spinning = false
        })
    },
    loadStage (value) {
      this.$http.get('/sysDocument/getStage', { params: { projectId: value } })
        .then(res => {
          if (res.data && res.success) {
            this.stageList = res.data
          }
        })
    },
    getTypes () {
      return this.$http.get('/sysDocument/getFileType')
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.typeList = res.data
            if (this.isNewReportCommit) {
              this.typeList = this.typeList.filter((item) => {
                return item.id * 1 !== 3 // 过滤查新类型 3表示查新类型
              })
            }
            return this.typeList
          }
        })
    },
    documentNameChange (e) {
      this.documentName = e.target.value
    },
    downloadTemplate () {
      var keys = []
      Object.keys(this.tableField.fieldTitleObject).map(a => {
        const item = this.tableField.fieldTitleObject[a]
        if (item.importField) {
          if (item.required) {
            keys.push(this.tableField.fieldTitleObject[a].title + `(必填)`)
            return this.tableField.fieldTitleObject[a].title + `(必填)`
          } else {
            keys.push(this.tableField.fieldTitleObject[a].title)
            return this.tableField.fieldTitleObject[a].title
          }
        }
        return a
      })
      var sheetData = []
      var sheetFilter = Object.keys(this.tableField.fieldTitleObject)
      this.$exportJsonData(this.templateName, keys, sheetFilter, sheetData)
    },
    // 判断是否符合接受的文件类型
    checkAccept (type) {
      if (this.accept.length === 0) {
        return true
      }
      const acceptList = this.accept.split(',')
      if (acceptList.includes(type)) {
        return true
      } else {
        return false
      }
    },
    beforeUpload (file) {
      if (!this.checkAccept(file.type)) {
        this.$message.error('不支持上传该格式的文件')
        return false
      }
      this.fileName = file.name
      this.form.setFieldsValue({ doc: file.name })
      if (this.documentName === '') {
        this.form.setFieldsValue({ documentName: file.name.split('.')[0] })
      }
      this.uploadFile = file
      return false
    },
    show (tableField, projectId) {
      this.form.resetFields()
      this.status = undefined
      this.help = undefined
      this.isDisable = false
      this.confirmLoading = false
      this.fileName = ''
      this.documentName = ''
      this.percent = 0
      this.tableField = tableField
      if (this.tableField && typeof this.tableField.fieldTitle === 'undefined') {
        this.tableField.fieldTitle = JSON.stringify(this.tableField.fieldTitleObject)
      }
      if (!this.isNewReportCommit) {
        this.projectId = projectId
        // this.loadStage(projectId)
      } else {
        this.loadProject()
      }
      this.visible = true
    },
    close () {
      this.visible = false
      this.fileName = ''
      this.documentName = ''
      this.percent = 0
      this.status = undefined
      this.help = undefined
      this.confirmLoading = false
      this.isDisable = false
    },
    onChange (value) {
      this.value = value
    },
    handleSubmit () {
      if (this.isDisable) {
        this.visible = false
        return
      }
      if (this.fileName === '') {
        this.$message.warning('请选择导入的文件')
        return
      }

      this.form.validateFields((errors, values) => {
        if (!errors) {
          if (values.documentName.trim() === '') {
            this.$message.info('请输入文件名')
            return
          }
          if (!this.$checkFileSize(this.uploadFile, this.$message)) {
            return
          }
          const model = {
            year: this.year,
            stageKey: values.stageKey,
            projectId: this.projectId,
            documentName: values.documentName,
            listId: values.listId,
            projectType: 1,
            fileType: 0
          }
          if (!this.isNewReportCommit) {
            model.listId = 3 // 3表示查新类型
          }
          const param = new FormData()
          param.append('file', this.uploadFile)
          for (const key in model) {
            param.append(key, model[key])
          }
          const config = {
            // 添加请求头
            headers: { 'Content-Type': 'multipart/form-data' }
          }
          this.confirmLoading = true
          this.$http.post(this.action, param, config).then(res => {
            if (res.success) {
              this.$emit('onSuccess')
              this.close()
            } else {
              this.$emit('error', res.errorMessage)
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

<style scoped>
</style>
