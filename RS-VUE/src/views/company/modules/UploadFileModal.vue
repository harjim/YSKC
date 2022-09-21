<template>
  <a-modal
    :title="title"
    :width="width"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <div class="clearfix">
      <a-form @submit="handleSubmit" :form="form">
        <a-row :gutter="24">
          <a-col :md="8" :sm="24">
            <a-form-item
              label="年份:"
              :labelCol=" {xs: { span: 24 },sm: { span: 12 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 12 } }"
            >
              <year-select
                v-decorator="['year', {rules:[{required: true, message: '请选择年份'}]}]"
                @change="yearChange"
                placeholder="请选择年份"
                style="width:80px;float:left"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="8" :sm="24">
            <a-form-item
              label="文件名"
              :labelCol=" {xs: { span: 24 },sm: { span: 12 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 12 } }"
            >
              <a-input
                v-decorator="['documentName', {rules:[{required: true, message: '请输入文件名'}]}]"
                style="width:450px"
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
import YearSelect from '@/components/YearSelect'
export default {
  components: {
    YearSelect
  },
  name: 'UploadFileModal',
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
      default: '请选择导入的文件'
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
    }
  },
  computed: {
    showTemplate () {
      return typeof this.templateNameUrl !== 'undefined'
    }
  },
  data () {
    return {
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
      return false
    },
    handleChange (info) {
      this.fileName = info.file.name
      this.fileItem = info.file
      const { form: { setFieldsValue } } = this
      setFieldsValue({ doc: info.file.name })
      if (this.documentName === '') {
        setFieldsValue({ documentName: info.file.name.split('.')[0] })
      }
    },
    show (tableField) {
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
      if (this.form.getFieldValue('year') == null) {
        this.$message.info('请选择年份')
        return
      }
      if (this.fileName === '') {
        this.$message.info('请选择文件')
        return
      }
      const param = new FormData()
      param.append('fileName', this.fileName)
      param.append('file', this.fileItem)
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        param.append('year', values.year)
        this.documentName = values.documentName
        param.append('documentName', values.documentName)
      })
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
      if (this.documentName.trim() === '') {
        this.$message.info('请输入文件名')
        return
      }
      this.confirmLoading = true
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
    },
    yearChange (year) {
      this.form.setFields({ year: { value: year } })
    }
  }
}
</script>

<style scoped>
</style>
