<template>
  <a-card>
    <a-form layout="inline">
      <div class="clearfix" style="width:600px" v-if="$auth('company:org:attachUpload')">
        <a-row>
          <a-col :span="18">
            <a-input :placeholder="placeholder" :disabled="true" />
          </a-col>
          <a-col :span="3">
            <a-upload
              :multiple="false"
              :beforeUpload="beforeUpload"
              :width="600"
              :fileList="fileList"
            >
              <a-button>
                <a-icon type="upload" />选择
              </a-button>
            </a-upload>
          </a-col>
        </a-row>
        <a-progress :percent="percent" v-if="showProgress" />
        <a @click="downloadFile">{{ fileName }}</a>
        <a-popconfirm title="是否确定删除?" @confirm="del" style="color:red" v-if="fileName.length>0">
          <a>×</a>
        </a-popconfirm>
        <div>
          <font>{{ message }}</font>
        </div>
      </div>
      <preview :filePath="filePath" :docName="fileName"></preview>
    </a-form>
  </a-card>
</template>

<script>
import { UploadModal, Preview } from '@/components'
import yearMiXin from '@/utils/yearMixin'

export default {
  mixins: [yearMiXin],
  name: 'AnnualReportUploadFile',
  components: {
    Preview,
    UploadModal
  },
  data () {
    return {
      fileLoading: false,
      showProgress: false,
      placeholder: '请选择导入的文件',
      fileName: '',
      paramKey: 'tableField',
      title: '上传文件',
      action: '/sysDocument/insertOrUpdateFile',
      tableField: undefined,
      percent: 0,
      fileList: [],
      fileItem: {},
      message: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      filePath: '',
      id: 0
    }
  },
  computed: {
    showTemplate () {
      return typeof this.templateNameUrl !== 'undefined'
    }
  },
  watch: {
    currentYear (newId) {
      this.fileName = ''
      this.queryFilePath()
      this.form.resetFields()
      this.fileList = []
    }
  },
  created () {
    this.queryFilePath()
  },
  methods: {
    search () {
      this.queryFilePath()
    },
    downloadFile () {
      this.$exportData('/sysDocument/downloadFile', { id: this.id }, this.fileName, this.$message)
    },
    del () {
      this.$http.post('/sysDocument/del', { id: this.id }).then(res => {
        this.$message.success('删除成功')
        this.filePath = ''
        this.fileName = ''
      })
    },
    queryFilePath () {
      this.$http.get('/sysDocument/queryDocument', { params: { year: this.currentYear, fileType: 1007, pageNo: 0, pageSize: 10 } })
        .then(res => {
          if (res.success && res.data.data.length !== 0) {
            this.id = res.data.data[0].id
            this.filePath = res.data.data[0].filePath
            this.fileName = res.data.data[0].fileName
          } else {
            this.filePath = ''
            this.fileName = ''
          }
        })
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    success (fileName, resData) {
      if (resData) {
        this.$message.success(fileName + '上传成功')
        this.queryFilePath()
      } else {
        this.$message.error(fileName + '上传失败')
      }
      this.fileName = fileName
    },
    error (fileName, errorMessage) {
      this.$message.error(fileName + '上传失败')
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
    beforeUpload (file) {
      this.handleSubmit(file)
      // this.fileList = []
      return false
    },
    handleChange (info) {
      this.fileName = info.file.name
      this.fileItem = info.file
    },
    show (tableField) {
      this.visible = true
      this.fileName = ''
      this.percent = 0
      this.tableField = tableField
      if (this.tableField && typeof this.tableField.fieldTitle === 'undefined') {
        this.tableField.fieldTitle = JSON.stringify(this.tableField.fieldTitleObject)
      }
      this.fileItem = {}
    },
    close () {
      this.$emit('close')
      this.visible = false
      this.fileName = ''
      this.percent = 0
      this.fileItem = {}
    },
    onChange (value) {
      this.value = value
    },
    handleSubmit (file) {
      if (!this.$auth('company:org:attachUpload')) {
        this.$notification.error({ message: '无此权限，请跟管理员联系！' })
        return
      }
      this.showProgress = true
      const param = new FormData()
      this.fileName = file.name
      param.append('file', file)
      param.append('fileName', file.name)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' },
        // 添加上传进度监听事件
        onUploadProgress: e => {
          var completeProgress = ((e.loaded / e.total * 100) | 0)
          this.percent = completeProgress
        }
      }
      if (this.paramData !== undefined) {
        Object.keys(this.paramData).map(a => {
          param.append(a, this.paramData[a])
        })
      }
      if (this.tableField !== undefined) {
        Object.keys(this.tableField).map(a => {
          param.append(a, this.tableField[a])
        })
      }
      param.append('fileType', 1007)
      param.append('year', this.currentYear)
      param.append('projectType', 1)
      param.append('projectId', 0)
      this.confirmLoading = true
      this.$http.post(this.action, param, config).then(res => {
        if (res.success) {
          // if(res.data.imgUrl!==null)
          this.success(file.name, res.data)
        } else {
          this.error(file.name, res.errorMessage)
        }
        return res
      }).catch(res => {
        this.error(file.name)
      }).finally(res => {
        this.visible = false
        this.confirmLoading = false
        this.showProgress = false
      })
    }
  }
}
</script>
