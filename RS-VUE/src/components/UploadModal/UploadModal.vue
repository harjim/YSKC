<template>
  <a-modal
    :title="title"
    :width="width"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    wrapClassName="upload-modal-wrap"
    :bodyStyle="bodyStyle"
    destroyOnClose
  >
    <a-spin
      :tip="tip"
      :spinning="spinning"
    >
      <div class="clearfix container-wrapper">
        <a-row>
          <a-col :span="20">
            <a-input
              :placeholder="placeholder"
              :disabled="true"
              :value="fileName"
            />
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
        <a-progress
          :percent="percent"
          v-if="showProgress"
        />
        <a-alert
          v-if="alertVisible"
          type="error"
        >
          <span
            slot="message"
            v-html="message"
          />
        </a-alert>
        <div v-if="showTemplate">
          <a :href="templateNameUrl">{{ templateName }}</a>
        </div>
        <div v-if="tableField!=null">
          <a @click="downloadTemplate">下载模板</a>
        </div>
        <slot></slot>
      </div>
    </a-spin>
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
    // 二次确认地址
    confirmAction: {
      type: String,
      default: undefined
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
    sampleData: {
      type: Array,
      default: undefined
    },
    // 文件大小最大值限制
    fileMaxSize: {
      type: Number,
      default: 20
    },
    bodyStyle: {
      type: Object,
      default: () => {}
    },
    // 获取附加参数回调，需返回Object或false，false会return
    // eslint-disable-next-line vue/require-default-prop
    getAdditionalEvent: {
      type: Function
    }
  },
  computed: {
    showTemplate () {
      return typeof this.templateNameUrl !== 'undefined'
    },
    alertVisible () {
      return this.message !== ''
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
      tip: '请稍后...',
      spinning: false
    }
  },
  mounted () {
  },
  methods: {
    downloadTemplate () {
      var sheetFilter = []
      var keys = []
      Object.keys(this.tableField.fieldTitleObject).map(a => {
        const item = this.tableField.fieldTitleObject[a]
        if (item && item.importField) {
          sheetFilter.push(a)
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
      if (this.sampleData) {
        var datas = [{ sheetHeader: keys, sheetFilter: sheetFilter, sheetData: sheetData, sheetName: this.templateName }]
        datas.push({ sheetHeader: keys, sheetFilter: sheetFilter, sheetData: this.sampleData, sheetName: '样例数据' })
        this.$exportJson(this.templateName, datas)
      } else {
        this.$exportJsonData(this.templateName, keys, sheetFilter, sheetData)
      }
    },
    beforeUpload (file) {
      return false
    },
    handleChange (info) {
      const isLt = info.file.size / 1024 / 1024 < this.fileMaxSize
      if (!isLt) {
        this.fileName = ''
        this.fileItem = {}
        this.$message.error('文件大小必须小于' + this.fileMaxSize + 'M')
      } else {
        this.fileName = info.file.name
        this.fileItem = info.file
      }
      this.message = ''
      this.percent = 0
    },
    show (tableField) {
      this.visible = true
      this.fileName = ''
      this.message = ''
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
    handleSubmit () {
      if (this.fileName === '') {
        this.$message.info('请选择文件')
        return
      }
      const param = new FormData()
      // 执行附加函数
      if (this.getAdditionalEvent) {
        // 返回Object或者false，false表示不通过校验
        const additionalParmas = this.getAdditionalEvent()
        if (additionalParmas) {
          Object.keys(additionalParmas).map(key => {
            param.append(key, additionalParmas[key])
          })
        } else {
          return
        }
      }
      param.append('file', this.fileItem)
      param.append('fileName', this.fileName)
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
      this.confirmLoading = true
      this.spinning = true
      this.$http.post(this.action, param, config).then(res => {
        if (res.data.result === 1) {
          // this.message = res.data.message
          // console.log(res.data.message)
          this.confirmImport(param, config, res.data.message)
        } else if (res.success) {
          this.$emit('onSuccess', this.fileItem.name, res.data)
          this.visible = false
        } else {
          this.message = res.errorMessage
        }
      }).catch(res => {
        if (res && res.message && res.message.includes('timeout')) {
          this.message = '导入超时'
        } else {
          this.message = '导入失败'
        }
      }).finally(res => {
        this.confirmLoading = false
        this.spinning = false
      })
    },
    confirmImport (param, config, message) {
      this.spinning = true
      const self = this
      this.$confirm({
        title: message,
        content: '注:同一单号,日期,物料编码为重复数据',
        onOk () {
          return self.$http.post(self.confirmAction, param, config).then(res => {
            if (res.success) {
              self.$emit('onSuccess', self.fileItem.name, res.data)
              self.visible = false
            } else {
              self.message = res.errorMessage
            }
            return res
          }).catch(res => {
            if (res && res.message && res.message.includes('timeout')) {
              self.message = '导入超时'
            } else {
              self.message = '导入失败'
            }
          }).finally(res => {
            self.confirmLoading = false
            self.spinning = false
          })
        },
        onCancel () {
          self.spinning = false
        }
      })
    }
  }
}
</script>

<style lang="less">
.upload-modal-wrap {
  .ant-modal {
    top: 50px;
  }
  .ant-spin-nested-loading, .ant-spin-container {
    height: 100%;
  }
  .container-wrapper {
    height: 100%;
    display: flex;
    flex-direction: column;
  }
}
</style>
