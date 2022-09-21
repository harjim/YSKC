<!--
 * @Author: ldx
 * @Date: 2021-07-12 17:51:59
 * @LastEditTime: 2021-09-10 15:13:05
 * @LastEditors: zdf
 * @Description: 完成上传凭证
 * @FilePath: \MS-VUE\src\views\patent\modules\FileUploadModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="400"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-form :form="form">
      <a-row>
        <a-col :span="24">
          <a-form-item :label="isSell ? '转让资料' : '采购凭证'" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-upload
              :fileList="fileFiles"
              :multiple="true"
              @change="(file) => changeUpload(file,fileFiles)"
              :beforeUpload="(file,fileList) => beforeUpload(file,fileList,fileFiles)"
              :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon: true}"
              v-decorator="[ 'upload', { rules:[ { required: true, type:'array', transform: transformUpload, message: '请上传附件' } ] } ]"
              @preview="onPreview"
              @download="onDownloadFile"
            >
              <a-button style="width:270px;">
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
import { upload } from '@/api/patent/demandList'
export default {
  name: 'FileUploadModal',
  props: {
    previewCB: {
      type: Function,
      required: true
    }
  },
  data () {
    return {
      isVisible: false,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      form: this.$form.createForm(this),
      fileFiles: [],
      record: undefined,
      demandRecord: undefined,
      isSell: false
    }
  },
  methods: {
    show (record, demandRecord, title = '上传采购凭证', isSell) {
      this.isVisible = true
      this.title = title
      this.record = record
      this.demandRecord = demandRecord
      this.isSell = isSell
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    afterClose () {
      this.fileFiles = []
      this.record = undefined
      this.demandRecord = this.demandRecord
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (errors) return
        const postParams = {}
        Object.assign(postParams, this.record)
        postParams.status = 2
        postParams.filePath = this.transFormFilePath(this.fileFiles)
        postParams.demandId = this.demandRecord.id
        if (!this.isSell) {
          this.$emit('submitStatus', this.record, postParams, [2, 3])
        } else {
          this.$emit('updateSellFile', this.record, postParams.filePath)
        }
        this.isVisible = false
      })
    },
    beforeUpload (file, fileList, files) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/PurDocuments/')
      upload(param).then(data => {
        if (data) {
          files.push({
            uid: data.filePath,
            name: data.fileName,
            status: 'done',
            url: data.filePath
          })
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
      return false
    },
    transFormFilePath (fileList) {
      const tempPath = fileList.map(item => item.url)
      return tempPath.join(',')
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
    changeUpload (file, files) {
      if (file.file.status === 'removed') {
        const index = files.findIndex(item => item.uid === file.file.uid)
        files.splice(index, 1)
      }
    },
    transformUpload (value) {
      if (value && value.fileList && value.fileList.length) {
        return value.fileList
      }
      return null
    }
  }
}
</script>
<style lang='less' scoped>
</style>
