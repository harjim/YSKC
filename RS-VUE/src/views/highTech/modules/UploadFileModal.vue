<!--
 * @Author: ldx
 * @Date: 2021-05-31 11:35:31
 * @LastEditTime: 2021-07-13 18:42:37
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\highTech\modules\UploadFileModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="width"
    v-model="visible"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :getContainer="getContainer"
  >
    <div class="clearfix">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :md="8" :sm="24">
            <a-form-item
              label="文件名"
              :labelCol=" {xs: { span: 24 },sm: { span: 12 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 12 } }"
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
            label="附件:"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 20 } }"
            style="margin-left:-10px"
          >
            <a-col :span="18">
              <a-input
                v-decorator="['doc', {rules:[{required: true, message: '请上传附件'}]}]"
                :placeholder="placeholder"
                :disabled="true"
              />
            </a-col>
            <a-col :span="2">
              <a-upload
                :accept="accept"
                :fileList="fileList"
                :multiple="false"
                :beforeUpload="beforeUpload"
                @change="handleChange"
              >
                <a-button>
                  <a-icon type="upload" />选择
                </a-button>
              </a-upload>
            </a-col>
          </a-form-item>
        </a-row>
        <div>
          <font>{{ message }}</font>
        </div>
      </a-form>
    </div>
  </a-modal>
</template>
<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
export default {
  name: 'UploadFileModal',
  props: {
    // 窗口宽度
    width: {
      type: Number,
      default: 600
    },
    // 文本框文案
    placeholder: {
      type: String,
      default: '请选择文件'
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
    // 图片限制
    accept: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      domName,
      fileList: [],
      fileItem: {},
      fileName: '',
      message: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      documentName: '',
      type: undefined,
      title: '上传文件'
    }
  },
  methods: {
    documentNameChange (e) {
      this.documentName = e.target.value
    },
    getContainer () {
      return popupContainer(this.domName)
    },
    beforeUpload (file) {
      return false
    },
    handleChange (info) {
      this.fileName = info.file.name
      this.fileItem = info.file
      const { form: { setFieldsValue } } = this
      setFieldsValue({ doc: info.file.name })
      if (this.documentName === '') {
        const index = this.fileName.lastIndexOf('.')
        const documentName = this.fileName.substring(0, index)
        setFieldsValue({ documentName })
      }
    },
    show (type, typeName) {
      this.form.resetFields()
      this.visible = true
      this.fileName = ''
      this.documentName = ''
      this.fileItem = {}
      this.type = type
      this.title = `上传文件【${typeName}】`
    },
    close () {
      this.$emit('close')
      this.visible = false
      this.fileName = ''
      this.documentName = ''
      this.fileItem = {}
      this.type = undefined
    },
    handleSubmit () {
      if (this.fileName === '') {
        this.$message.info('请选择导入的文件')
        return
      }
      this.confirmLoading = true
      const param = new FormData()
      param.append('fileName', this.fileName)
      param.append('file', this.fileItem)
      param.append('type', this.type)
      this.form.validateFields((errors, values) => {
        this.documentName = values.documentName
        param.append('documentName', values.documentName)
      })
      if (this.documentName.trim() === '') {
        this.$message.info('请输入文件名')
        return
      }
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      if (this.paramData !== undefined) {
        Object.keys(this.paramData).map(field => {
          param.append(field, this.paramData[field])
        })
      }
      this.$http.post(this.action, param, config).then(res => {
        if (res.success) {
          this.$emit('success', this.fileItem.name, '上传成功！', this.type, res.data)
        } else {
          this.$emit('error', this.fileItem.name, res.errorMessage || '系统异常请联系管理员！')
        }
      }).catch(res => {
        this.$emit('error', this.fileItem.name, res.message || '系统异常请联系管理员！')
      }).finally(res => {
        this.visible = false
        this.confirmLoading = false
      })
    }
  }
}
</script>

<style scoped>
</style>
