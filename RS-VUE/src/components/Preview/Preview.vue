<!--
 * @Author:
 * @Date: 2020-11-25 16:36:08
 * @LastEditTime: 2022-02-28 10:13:05
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\components\Preview\Preview.vue
-->
<template>
  <a-spin
    :spinning="fileLoading"
    tip="文档加载中...."
  >
    <div v-if="errorMessage" style="color:red;">{{ errorMessage }}</div>
    <div>
      <iframe v-if="resData" id="myFrame" :src="resData" frameborder="0" style="width: 100%; height: 790px"></iframe>
      <img v-if="imgData" :src="imgData" style="max-width: 100%">
    </div>
  </a-spin>
</template>

<script>
const fileTypes = {
  'png': 'image/png',
  'jpg': 'image/jpeg',
  'jpeg': 'image/jpeg',
  'gif': 'image/gif',
  'txt': 'text/plain;charset=GBK',
  'pdf': 'application/pdf',
  'xml': 'text/xml',
  'xls': 'text/html',
  'xlsx': 'text/html',
  'doc': 'text/html',
  'docx': 'text/html',
  'ppt': 'text/html',
  'pptx': 'text/html'
}
export default {
  name: 'Preview',
  components: {
  },
  props: {
    filePath: {
      type: String,
      default: ''
    },
    url: {
      type: String,
      default: '/document/preview'
    },
    docName: {
      type: String,
      default: undefined
    }
  },
  created () {
    this.$nextTick(() => {
      this.get()
    })
  },
  watch: {
    filePath (v) {
      this.get()
    },
    url () {
      this.get()
    }
  },
  data () {
    return {
      fileTypes,
      fileLoading: false,
      errorMessage: undefined,
      resData: undefined,
      imgData: undefined
    }
  },
  methods: {
    getFileType () {
      var dName = this.docName ? this.docName : this.filePath
      if (!dName) {
        return null
      }
      dName = dName.toLowerCase()
      for (const k in this.fileTypes) {
        if (dName.endsWith(k)) { return this.fileTypes[k] }
      }
      return null
    },
    get () {
      this.errorMessage = undefined
      this.resData = undefined
      this.imgData = undefined
      this.message = ''
      if (!this.filePath || this.filePath.trim() === '') {
        return
      }
      const docType = this.getFileType()
      if (!docType) {
        this.errorMessage = '预览失败，文件格式不支持'
        return
      }
      this.fileLoading = true
      this.$http.get(this.url, { params: { filePath: this.filePath }, responseType: 'blob' })
        .then(res => {
          if (res.size < 1024) {
            var reader = new FileReader()
            reader.readAsText(res)
            const self = this
            reader.onload = function () {
              if (reader.result && reader.result.indexOf('errorMessage') >= 0) {
                const result = JSON.parse(reader.result)
                // message.error()
                self.errorMessage = result.errorMessage && result.errorMessage !== '系统异常' ? result.errorMessage : '预览失败，请稍后再试'
              } else {
                const blob = new Blob([res], { type: docType + ';charset=UTF-8;' })
                if (docType.startsWith('image')) {
                  self.imgData = URL.createObjectURL(blob)
                } else {
                  self.resData = URL.createObjectURL(blob)
                }
              }
            }
          } else {
            const blob = new Blob([res], { type: docType + ';charset=UTF-8;' })
            if (docType.startsWith('image')) {
              this.imgData = URL.createObjectURL(blob)
            } else {
              this.resData = URL.createObjectURL(blob)
            }
          }
        }).finally(res => { this.fileLoading = false })
    }
  }

}
</script>
<style lang="less" scoped>
.dialog-content {
  height: 800px;
  overflow-y: auto;
  overflow-x: auto;
}
</style>
