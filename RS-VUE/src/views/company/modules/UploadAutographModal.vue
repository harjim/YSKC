<!--
 * @Author: zdf
 * @Date: 2022-04-20 18:51:47
 * @LastEditTime: 2022-07-28 09:51:04
 * @LastEditors: zdf
 * @Description: 上传签名
 * @FilePath: \RS-VUE\src\views\company\modules\UploadAutographModal.vue
-->
<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    @cancel="closeModal"
    :maskClosable="false"
    :confirmLoading="confirmLoading">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <div class="clearfix container-wrapper">
        <a-row>
          <a-col :span="20">
            <a-input
              placeholder="请上传文件"
              :disabled="true"
              :value="fileName"
            />
          </a-col>
          <a-col :span="3">
            <a-upload
              :fileList="fileList"
              :accept="accept"
              :multiple="false"
              :beforeUpload="beforeUpload"
              @change="handleChange"
            >
              <a-button>
                <a-icon type="upload" />选择</a-button>
            </a-upload>
          </a-col>
        </a-row>
        <a-alert
          v-if="alertVisible"
          type="error"
        >
          <span
            slot="message"
            v-html="message"
          />
        </a-alert>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      fileList: [],
      accept: 'image/png,image/jpg,image/jpeg,image/gif',
      title: '',
      visible: false,
      confirmLoading: false,
      params: {},
      spinning: false,
      fileName: '',
      fileItem: {},
      alertVisible: false,
      message: ''
    }
  },
  methods: {
    show (params) {
      this.title = `上传[${params.ename}]签名`
      this.params = params
      this.visible = true
    },
    handleSubmit () {
      this.confirmLoading = true
      if (this.fileName === '') {
        this.alertVisible = true
        this.message = '请选择文件'
        this.confirmLoading = false
        return
      }
      const param = new FormData()
      param.append('file', this.fileItem)
      param.append('id', this.params.id)
      param.append('enumber', this.params.enumber)
      const config = { headers: { 'Content-Type': 'multipart/form-data' } }
      this.$http.post('/employee/uploadAutograph', param, config).then(res => {
        if (res.success && res.data) {
          this.$message.success('上传成功')
          this.$emit('ok')
          this.closeModal()
        } else {
          this.$message.error(res.errorMessage || '上传失败')
        }
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    handleChange (info) {
      if (!this.$checkFileSize(info.file, this.$message, 1048576)) { // 1048576 1M
        this.fileName = ''
        this.fileItem = undefined
        this.alertVisible = true
        this.message = '上传失败，您上传的附件大于1M！'
        return
      }
      this.fileName = info.file.name
      this.fileItem = info.file
      this.alertVisible = !this.fileName
    },
    beforeUpload (file) {
      return false
    },
    closeModal () {
      this.fileName = ''
      this.visible = false
      this.alertVisible = false
      this.confirmLoading = false
      this.fileItem = {}
    }
  }
}
</script>

<style>

</style>
