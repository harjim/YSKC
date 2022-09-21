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
        <a-row :gutter="24" v-if="haveMonth">
          <a-form-item
            label="月份"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
          >
            <a-month-picker
              placeholder
              style="width:230px"
              v-decorator="['docMonth', {rules:[{required: true, message: '请选择月份'}]}]"
            />
          </a-form-item>
        </a-row>
        <a-row :gutter="24">
          <a-form-item
            label="文件名"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
          >
            <a-input
              v-decorator="['documentName', {rules:[{required: true, message: '请输入文件名'}]}]"
              @change="documentNameChange"
            />
          </a-form-item>
        </a-row>
        <a-row :gutter="24">
          <a-form-item
            label="附件类型"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
          >
            <a-select
              v-decorator="['buildType', {rules:[{required: true, message: '请选择附件类型'}]}]">
              <a-select-option value="0">制度文件</a-select-option>
              <a-select-option value="1">佐证材料</a-select-option>
            </a-select>
          </a-form-item>
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
        <a-row :gutter="12">
          <a-col :span="24">
            <div v-if="samplePath" style="padding-left:15%;">
              <a @click="downloadSampleFile">下载模板</a>
            </div>
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
    </div>
  </a-modal>
</template>
<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import moment from 'moment'
export default {
  name: 'UploadFile',
  props: {
    // 月份
    haveMonth: {
      type: Boolean,
      default: false
    },
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
    // 图片限制
    accept: {
      type: String,
      default: ''
    },
    listType: {
      type: Number,
      default: 0
    }
  },
  computed: {
    showTemplate () {
      return typeof this.templateNameUrl !== 'undefined'
    },
    sampleFileName () {
      if (this.samplePath) {
        const arr = this.samplePath.split('/')
        return arr[arr.length - 1].substr(13)
      }
      return ''
    }
  },
  data () {
    return {
      samplePath: undefined,
      domName,
      tableField: undefined,
      percent: 0,
      fileList: [],
      fileItem: {},
      fileName: '',
      message: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      documentName: ''
    }
  },
  mounted () {
  },
  methods: {
    documentNameChange (e) {
      this.documentName = e.target.value
    },
    getContainer () {
      return popupContainer(this.domName)
    },
    downloadSampleFile () {
      this.$exportData('/beian/download', { filePath: this.samplePath }, this.sampleFileName, this.$message)
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
    show (tableField, samplePath) {
      this.samplePath = samplePath
      this.form.resetFields()
      this.visible = true
      this.fileName = ''
      this.documentName = ''
      this.percent = 0
      this.tableField = tableField
      if (this.tableField && typeof this.tableField.fieldTitle === 'undefined') {
        this.tableField.fieldTitle = JSON.stringify(this.tableField.fieldTitleObject)
      }
      this.fileItem = {}
    },
    init (record) {

    },
    close () {
      this.$emit('close')
      this.visible = false
      this.fileName = ''
      this.documentName = ''
      this.percent = 0
      this.fileItem = {}
    },
    onChange (value) {
      this.value = value
    },
    handleSubmit () {
      if (this.haveMonth && this.form.getFieldValue('docMonth') == null) {
        this.$message.info('请选择月份')
        return
      }
      if (this.fileName === '') {
        this.$message.info('请选择导入的文件')
        return
      }
      // if (this.documentName.trim() === '') {
      //   this.$message.info('请输入文件名')
      //   return
      // }

      this.confirmLoading = true
      const param = new FormData()
      param.append('fileName', this.fileName)
      param.append('file', this.fileItem)
      param.append('key', 'uploadFile') // 没有实质的用处,记录与附件接口要求传递,要求传 2021-02-19 询问过
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        if (this.haveMonth) {
          param.append('docMonth', moment(values.docMonth).format('YYYY-MM-DD'))
        }
        this.documentName = values.documentName
        param.append('documentName', values.documentName)
        param.append('buildType', values.buildType)
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
        this.$http.post(this.action, param, config).then(res => {
          if (res.success) {
            this.$emit('success', this.fileItem.name, res.data)
            this.$emit('onSuccess', this.fileItem.name, res.data)
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
      })
    }
  }
}
</script>

<style scoped>
</style>
