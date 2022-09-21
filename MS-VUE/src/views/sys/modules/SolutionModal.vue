<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="900"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="12" class="colSpan">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="标题" style="padding:0;">
            <a-input v-decorator="['title', {rules:[{required: true, message: '请输入标题'}]}]" />
          </a-form-item>
        </a-col>
        <a-col v-if="!isBase" :span="12" class="colSpan">
          <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['customer', {rules:[{required: false, message: '请输入客户名称'}]}]" />
          </a-form-item>
        </a-col>
        <a-col :span="12" class="colSpan">
          <a-form-item label="服务项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['serveProject', {rules:[{required: false, message: '请输入服务项目'}]}]" />
          </a-form-item>
        </a-col>
        <a-col :span="12" class="colSpan">
          <a-form-item label="行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['industry', {rules:[{required: false, message: '请输入行业'}]}]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24" v-if="!isBase" class="colSpan">
          <a-form-item
            :labelCol=" { xs: { span: 24 },sm: { span: 24 }}"
            :wrapperCol=" { xs: { span: 24 },sm: { span: 23 }}"
            label="问题描述">
            <a-textarea v-decorator="['descraption', {rules:[{required: false, message: '请输入问题描述'}]}]"></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24" class="colSpan">
          <a-form-item
            :labelCol=" { xs: { span: 24 },sm: { span: 24 }}"
            :wrapperCol=" { xs: { span: 24 },sm: { span: 23 }}"
            :label="contentLabel">
            <!-- <wang-editor key="content" v-decorator="['content', {rules:[{required: true, message: '解决方案'}],validateTrigger: ['blur','submit']}]" :dir="dir" placeholder="解决方案" /> -->
            <wang-editor key="content" v-decorator="['content', {rules:[{required: true, message: contentLabel}]}]" :dir="dir" :placeholder="contentLabel" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24" class="colSpan">
          <a-form-item
            label="附件"
            :labelCol="{ sm: { span: 24 } }"
            :wrapperCol="{ sm: { span: 24 } }"
          >
            <a-upload
              :fileList="fileList"
              :multiple="true"
              @change="UploadHandleChange"
              :beforeUpload="beforeUpload"
              :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon: true}"
              @preview="onPreview"
              @download="downloadfile"
              accept=".doc,.docx,.xls,.xlsx,.pdf"
            >
              <a-button>
                <a-icon type="upload"/>上传附件
              </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import WangEditor from '@/components/Editor/WangEditor.vue'
import moment from 'moment'
export default {
  components: {
    WangEditor
  },
  props: {
    isBase: {
      type: Boolean,
      required: true
    },
    type: {
      type: Number,
      required: true
    },
    preview: {
      type: Function,
      required: true
    }
  },
  data () {
    return {
      fileList: [],
      visible: false,
      id: undefined,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 22 }
      },
      dir: `/solution/${moment().format('YYYY-MM-DD')}/`,
      form: this.$form.createForm(this),
      title: '',
      rate: 0,
      confirmLoading: false,
      contentLabel: ''
    }
  },
  methods: {
    moment,
    add () {
      this.fileList = []
      this.contentLabel = this.isBase ? '基础知识' : '解决方案'
      this.title = '添加' + this.contentLabel
      this.id = undefined
      this.visible = true
      this.rate = 0
      this.confirmLoading = false
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    edit (row) {
      this.fileList = []
      this.rate = row.rate
      this.title = `编辑[${row.title}]`
      this.id = row.id
      this.visible = true
      this.confirmLoading = false
      this.$nextTick(() => {
        this.form.resetFields()
        if (row.filePath) {
          const temp = []
          row.filePath.split(',').forEach(item => {
            const name = item.substring(item.lastIndexOf('/') + 14, item.length)
            temp.push({
              uid: name,
              name,
              status: 'done',
              url: item
            })
          })
          this.fileList = temp
        }
        this.$initForm(this.form, row)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        const paths = []
        if (this.fileList.length !== 0) {
          this.fileList.forEach(item => {
            paths.push(item.url)
          })
          if (paths.length > 0) {
            values.filePath = paths.join(',')
          }
        } else {
          values.filePaths = []
        }
        values.rate = this.rate
        values.type = this.type
        let url = '/solution/add'
        const edit = Boolean(this.id)
        if (edit) {
          values.id = this.id
          url = '/solution/edit'
        }
        this.$http.post(url, values).then(res => {
          if (res.success && res.data) {
            this.visible = false
            this.$emit('ok', !edit)
          } else {
            this.$message.error(res.errorMessage || edit ? '更新失败' : '添加失败')
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    downloadfile (file) {
      this.$exportData('/patentBuying/download', { filePath: file.url }, file.name, this.$message)
    },
    onPreview (file) {
      // console.log('onPreview', file)
      this.preview(file.url, file.name)
    },
    beforeUpload (file, key) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      param.append('dir', this.dir)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/serviceLog/upload', param, config).then(res => {
        if (res.success) {
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
    /**
     * @description: 上传改变事件监听
     * @param {*} file
     * @return {*} null
     */
    UploadHandleChange (file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
      }
    }
  }
}
</script>

<style>
.colSpan{
  margin-bottom:-9px;
}
  .colSpan .ant-form-item-label{
    text-align:left;
    padding-bottom: 8px;
  }
  .colSpan .ant-form-item-label > label::after {
    content: '';
}
.colSpan .ant-form{
  padding-left: 36px;
  padding-right: 36px;
}
</style>
