<!--
 * @Author: ldx
 * @Date: 2021-03-19 16:49:39
 * @LastEditTime: 2021-07-20 14:40:18
 * @LastEditors: ldx
 * @Description: ys上传组件
 * @FilePath: \RS-VUE\src\components\YsUpload\YsUpload.vue
-->

<template>
  <!-- <a-upload
    v-bind="$attrs"
    name="file"
    :action="action"
    :data="params"
    :multiple="true"
    :headers="headers"
    :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon: true}"
    @change="handleChange"
  > -->
  <a-upload
    name="file"
    :action="action"
    :data="params"
    :multiple="true"
    :headers="headers"
    :fileList="fileList"
    :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon: true}"
    @change="handleChange"
    @preview="onPreview"
    @download="onDownloadFile"
  >
    <a-button> <a-icon type="upload" />上传</a-button>
  </a-upload>
</template>

<script>
import Vue from 'vue'
import { mapGetters } from 'vuex'
import { ACCESS_TOKEN } from '@/store/mutation-types'
export default {
  name: 'YsUpload',
  props: {
    url: {
      type: String,
      required: true
    },
    params: {
      type: Object,
      default: () => {}
    },
    value: {
      type: Array,
      default: () => undefined
    },
    previwFun: {
      type: Function,
      default: undefined
    },
    downloadFun: {
      type: Function,
      default: undefined
    }
  },
  watch: {
    value: {
      immediate: true,
      deep: true,
      handler (v) {
        this.fileList = v
        const paths = this.transFormFilePath(this.fileList)
        this.$emit('path', paths)
      }
    }
  },
  computed: {
    ...mapGetters(['companyId']),
    action () {
      // http://localhost:8001/api/beian/upload
      return this.baseUrl + this.url
    }
  },
  data () {
    return {
      fileList: [],
      token: undefined,
      headers: { 'Access-Token': undefined },
      baseUrl: process.env.NODE_ENV === 'production' && process.env.VUE_APP_PREVIEW !== 'true' ? '/api/' : 'http://localhost:8001/api/',
      defaultFileList: []
    }
  },
  mounted () {
    this.headers['Access-Token'] = this.getToken()
    this.headers['CompanyId'] = this.companyId
  },
  methods: {
    getToken () {
      const token = Vue.ls.get(ACCESS_TOKEN)
      return token
    },
    handleChange (info) {
      const { file, fileList } = info
      this.fileList = fileList
      if (file.status === 'done') {
        const list = fileList.map(item => {
          if (!item.url) {
            if (item.response && item.response.success) {
              this.$set(item, 'url', item.response.data.filePath)
            }
          }
          return item
        })
        this.fileList = list
      }
      if (file.status === 'error') {
        this.fileList = this.fileList.filter(item => {
          if (item.uid === file.uid) {
            if (item.response) {
              this.$message.error(`${file.name}-${file.response.errorMessage || '上传失败'}`)
            } else {
              this.$message.error(`网络超时，${file.name}上传失败`)
            }
            return false
          } else {
            return true
          }
        })
      }
      this.$emit('change', this.fileList)
      const paths = this.transFormFilePath(this.fileList)
      this.$emit('path', paths)
    },
    transFormFilePath (fileList) {
      if (!fileList) return ''
      if (fileList.some(item => item.status !== 'done')) return ''
      const pathAry = fileList.map(item => item.url)
      return pathAry.join()
    },
    onPreview (file) {
      this.previwFun({ name: file.name, path: file.url })
    },
    onDownloadFile (file) {
      this.downloadFun({ name: file.name, path: file.url })
    }
  }
}
// TODO 未完成 2021-03-25
// TODO 优化 2021-07-16
</script>
<style>

</style>
