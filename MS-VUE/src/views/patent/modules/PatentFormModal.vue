<!--
 * @Author: ldx
 * @Date: 2021-07-10 08:53:31
 * @LastEditTime: 2021-09-10 11:05:21
 * @LastEditors: zdf
 * @Description: 专利
 * @FilePath: \MS-VUE\src\views\patent\modules\PatentFormModal.vue
-->
<template>
  <a-modal
    :width="500"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :afterClose="afterClose"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="6">
        <a-col :span="24">
          <a-form-item label="专利号/申请号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              style="width:280px"
              placeholder="请输入专利号"
              v-decorator="['patentNo', {rules:[{required: true, message: '请输入专利号/申请号'},{ validator: this.checkPatentNo }]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="6">
        <a-col :span="24">
          <a-form-item label="专利名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              style="width:280px"
              placeholder="请输入专利名称"
              v-decorator="['patentName', {rules:[{required: true, message: '请输入专利名称'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="6">
        <a-col :span="24">
          <a-form-item label="专利类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select style="width:280px" v-decorator="['mainType', {rules:[{required: true, message: '请输入专利类型'}]}]" placeholder="请选择专利类型">
              <a-select-option value="1">发明专利</a-select-option>
              <a-select-option value="2">实用新型</a-select-option>
              <a-select-option value="3">外观设计</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="6">
      </a-row>
      <a-row :gutter="6">
        <a-col :span="24">
          <a-form-item label="发明人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              style="width:280px"
              placeholder="请输入发明人"
              v-decorator="['inventor']"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="6">
        <a-col :span="24">
          <a-form-item label="申请日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              style="width:280px"
              format="YYYY-MM-DD"
              placeholder="请选择申请日期"
              v-decorator="['applyDateTime']"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="6">
        <a-col :span="24">
          <a-form-item label="专利资料" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <input style="display:none;" v-decorator="[ 'fileFiles', { rules:[{ required: true, message: '请上传附件' }]} ]" />
            <a-upload
              :fileList="fileFiles"
              :multiple="false"
              @change="(file) => changeUpload(file,fileFiles)"
              :beforeUpload="(file,fileList) => beforeUpload(file,fileList,fileFiles)"
              :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon: true}"
              @preview="onPreview"
              @download="onDownloadFile"
            >
              <a-button>
                <a-icon type="upload" />上传附件
              </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import moment from 'moment'
import { savePatentSea, upload } from '@/api/patent/demandList'
export default {
  name: 'PatentFormModal',
  components: {
  },
  props: {
    previewCB: {
      type: Function,
      required: true
    }
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      confirmLoading: false,
      visible: false,
      form: this.$form.createForm(this),
      id: undefined,
      fileFiles: []
    }
  },
  methods: {
    checkPatentNo (rule, value, callback) {
      if (value) {
        this.$http.get('/patentBuying/limitPatentNo', { params: { id: this.id, patentNo: value } })
          .then(res => {
            if (!res.success) {
              callback(new Error(res.errorMessage))
            } else {
              callback()
            }
          })
      } else {
        callback()
      }
    },
    moment,
    show () {
      this.title = '添加专利'
      this.form.resetFields()
      this.id = 0
      this.visible = true
      this.confirmLoading = false
    },
    edit (record) {
      this.title = `编辑专利`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.fileFiles = this.transFormStrToAry(record.filePath)
        this.$initForm(this.form, record)
        this.form.setFieldsValue({ fileFiles: this.fileFiles ? this.fileFiles : undefined })
        if (record.applyDateTime) {
          this.form.setFieldsValue({
            'applyDateTime': moment(record.applyDateTime, 'YYYY-MM-DD HH:mm:ss')
          })
        }
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        if (this.id) { values.id = this.id }
        values.filePath = this.transFormFilePath(values.fileFiles)
        savePatentSea(values).then(data => {
          if (data) {
            this.visible = false
            this.$emit('success')
            this.$message.success('操作成功！')
          }
        }).catch(error => {
          this.$message.error(error.message || '系统异常，请联系管理员！')
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    afterClose () {
      this.confirmLoading = false
      this.visible = false
      this.id = 0
      this.fileFiles = []
    },
    onPreview ({ url: path, name }) {
      this.previewCB({ path, name })
    },
    onDownloadFile (file) {
      if (file.status === 'done') {
        this.$exportData('/patentBuying/download', { filePath: file.url }, file.name, this.$message)
      } else if (file.status === 'uploading') {
        this.$message.error('未保存不可以下载！')
      }
    },
    transFormFilePath (fileList) {
      const tempPath = fileList.map(item => item.url)
      return tempPath.join()
    },
    beforeUpload (file, fileList, files) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/patentSea/')
      upload(param).then(data => {
        if (data) {
          files.push({
            uid: data.filePath,
            name: data.fileName,
            status: 'done',
            url: data.filePath
          })
          this.form.setFieldsValue({ fileFiles: files })
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
      return false
    },
    changeUpload (file, files) {
      if (file.file.status === 'removed') {
        const index = files.findIndex(item => item.uid === file.file.uid)
        files.splice(index, 1)
        this.form.setFieldsValue({ fileList: files.length ? files : undefined })
      }
    },
    transFormStrToAry (filePaths) {
      if (!filePaths) return
      const files = []
      filePaths.split(',').forEach(path => {
        const name = path.substring(path.lastIndexOf('/') + 14)
        files.push({
          uid: path,
          name,
          status: 'done',
          url: path
        })
      })
      return files
    }
  }
}
</script>

<style  lang="less" scoped>
</style>
