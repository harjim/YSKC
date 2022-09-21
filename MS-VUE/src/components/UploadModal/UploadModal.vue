<template>
  <a-modal :title="title" :width="width" v-model="visible" @ok="handleSubmit" :maskClosable="false">
    <div class="clearfix">
      <a-row>
        <a-col :span="20">
          <a-input :placeholder="placeholder" :disabled="true" :value="fileName" />
        </a-col>
        <a-col :span="3">
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
      </a-row>
      <a-progress :percent="percent" v-if="showProgress" />
      <a-alert v-if="message" type="error">
        <span slot="message" v-html="message" />
      </a-alert>
      <div v-if="showTemplate">
        <a :href="templateNameUrl">{{ templateName }}</a>
      </div>
      <!-- <div>
        <font>{{ message }}</font>
      </div> -->
    </div>
  </a-modal>
</template>
<script>
export default {
  name: 'UploadModal',
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
      default: ''
    },
    // 模板下载地址
    templateNameUrl: {
      type: String,
      default: ''
    },
    // 上传地址
    action: {
      type: String,
      required: true
    },
    // 上传参数对象
    paramData: {
      type: Object,
      default: null
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
      percent: 0,
      fileList: [],
      fileItem: {},
      fileName: '',
      message: '',
      visible: false,
      form: this.$form.createForm(this)
    }
  },
  mounted () {
  },
  methods: {
    beforeUpload (file) {
      return false
    },
    handleChange (info) {
      this.fileName = info.file.name
      this.fileItem = info.file
    },
    show () {
      this.visible = true
      this.message = ''
      this.fileName = ''
      this.percent = 0
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
    handleSubmit () {
      const param = new FormData()
      param.append('file', this.fileItem)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' },
        // 添加上传进度监听事件
        onUploadProgress: e => {
          var completeProgress = ((e.loaded / e.total * 100) | 0)
          this.percent = completeProgress
        }
      }
      param.append(this.paramKey, this.paramData)
      this.$http.post(this.action, param, config).then(res => {
        if (res.success && res.data) {
          this.$emit('success', this.fileItem.name, res.data)
          if (res.errorMessage) {
            this.message = res.errorMessage
          }
          this.visible = false
        } else {
          this.message = res.errorMessage
          this.$emit('error', this.fileItem.name, res.errorMessage)
        }
        return res
      }).catch(res => {
        this.message = res.errorMessage
        this.$emit('error', this.fileItem.name, res.errorMessage)
      })
    }
  }
}
</script>

<style scoped>
</style>
