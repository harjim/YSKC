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
      <a-form @submit="handleSubmit" :form="form">
        <a-row :gutter="24" v-show="isShowFileName">
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
        <!-- <a-progress :percent="percent" v-if="showProgress" /> -->
        <div>
          <font>{{ message }}</font>
        </div>
      </a-form>
    </div>
  </a-modal>
</template>
<script>
import { popupContainer, domName } from '@/views/customer/modules/AuditProgress/modules/js/screenFullMountDom'
export default {
  name: 'UploadFile',
  props: {
    // 窗口宽度
    width: {
      type: Number,
      default: 600
    },
    // 标题
    title: {
      type: String,
      default: '上传附件'
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
    // 图片限制
    accept: {
      type: String,
      default: ''
    },
    isShowFileName: {
      type: Boolean,
      default: true
    }
  },
  computed: {

  },
  data () {
    return {
      domName,
      percent: 0,
      fileList: [],
      fileItem: {},
      fileName: '',
      message: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      documentName: '',
      postParams: {}
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
    show (params) {
      this.postParams = {}
      Object.assign(this.postParams, params)
      this.form.resetFields()
      this.visible = true
      this.fileName = ''
      this.documentName = ''
      this.percent = 0
      this.fileItem = {}
    },
    close () {
      this.$emit('close')
      this.visible = false
      this.fileName = ''
      this.documentName = ''
      this.percent = 0
      this.fileItem = {}
      this.postParams = {}
    },
    onChange (value) {
      this.value = value
    },
    handleSubmit () {
      if (this.fileName === '') {
        this.$message.info('请选择导入的文件')
        return
      }
      const param = new FormData()
      param.append('file', this.fileItem)
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        this.documentName = values.documentName
      })
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      Object.keys(this.postParams).forEach(key => {
        param.append(key, this.postParams[key])
      })
      if (this.documentName.trim() === '') {
        this.$message.info('请输入文件名')
        return
      }
      this.confirmLoading = true
      this.$http.post(this.action, param, config).then(res => {
        if (res.success) {
          this.$emit('success', this.fileItem.name, res.data)
        } else {
          this.$emit('error', this.fileItem.name, res.errorMessage)
        }
        return res
      }).catch(res => {
        this.$emit('error', this.fileItem.name)
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
