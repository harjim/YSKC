<!--
 * @Author: zdf
 * @Date: 2021-08-21 08:05:14
 * @LastEditTime: 2022-03-02 10:56:53
 * @LastEditors: lzh
 * @Description: 专利文件
 * @FilePath: \MS-VUE\src\components\Patent\PatentFiles.vue
-->
<template>
  <a-spin :spinning="spinning" tip="请稍后...">
    <a-card :title="title" style="width:100%;height:100%;" :bordered="bordered">
      <a-row v-for="(item,index) in patentFileTypes" :key="item.value" style="padding:8px;">
        <a-col :span="contentSpan">
          <div class="patentFile">
            <a-col :span="4" style="padding-left:20px;">
              {{ `${index + 1}.${item.label}` }}
            </a-col>
            <a-col :span="20" style="height:100%;line-height:20px;padding-top:11px;padding-bottom:10px;padding-right:10px;">
              <template v-if="files[item.value]">
                <span v-for="(file,fileIndex) in files[item.value]" :key="file.id" style="margin-right:5px">
                  <a title="点击下载" @click="downloadFile(file)" v-if="control.download">
                    {{ file.fileName }}
                  </a>
                  <span v-else>
                    {{ file.fileName }}
                  </span>
                  <a-tooltip style="cursor:pointer" placement="top" @click="onPreview(file.filePath,file.fileName)" v-if="control.preview">
                    <template slot="title">
                      <span>预览</span>
                    </template>
                    <a-icon type="eye" style="margin-left:5px" />
                  </a-tooltip>
                  <a-popconfirm
                    title="是否确定删除?"
                    @confirm="delFile(file.id,fileIndex,files[item.value])"
                    style="color:red;margin-left:5px"
                    v-if="control.deleteFile && item.value !== 0"
                  >
                    <a-tooltip placement="top">
                      <template slot="title">
                        <span>删除</span>
                      </template>
                      <a-icon type="close" />
                    </a-tooltip>
                  </a-popconfirm>
                </span>
              </template>
            </a-col>
          </div>
        </a-col>
        <a-col :span="24-contentSpan">
          <div v-if="control.upload && canUpload && item.value !== 0" style="height:45px;line-height:45px;text-align:center;width:100%;padding-left:10px;">
            <a-upload
              :fileList="[]"
              :multiple="false"
              :beforeUpload="(file) => beforeUpload(file,item.value)"
            >
              <a-button >上传</a-button>
            </a-upload>
          </div>
        </a-col>
      </a-row>

    </a-card>
  </a-spin>
</template>

<script>
export default {
  props: {
    row: {
      type: Object,
      required: true
    },
    files: {
      type: Object,
      required: true
    },
    title: {
      type: String,
      default: ''
    },
    contentSpan: {
      type: Number,
      default: 22
    },
    control: {
      type: Object,
      required: true
    },
    onPreview: {
      type: Function,
      required: true
    },
    canUpload: {
      type: Boolean,
      default: false
    },
    bordered: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      patentFileTypes: [],
      spinning: false
    }
  },
  watch: {
    row () {
      this.spinning = false
    }
  },
  created () {
    this.patentFileTypes = this.$getPatentFileTyps()
  },
  methods: {
    downloadFile (file) {
      this.$exportData('/PatentFile/download', { id: file.id }, file.name, this.$message)
    },
    delFile (id, index, files) {
      this.spinning = true
      this.$http.post('/PatentFile/delPatentFile', { id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          files.splice(index, 1)
          this.$emit('fileChange')
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
        this.spinning = false
      })
    },
    beforeUpload (file, fileType) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const postParams = { patentNo: this.row.patentNo, fileType }
      if (this.row.id) {
        postParams.patentId = this.row.id
      }
      const param = new FormData()
      param.append('file', file)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      Object.keys(postParams).forEach(key => {
        param.append(key, postParams[key])
      })
      this.spinning = true
      this.$http.post('/PatentFile/uploadPatentFile', param, config).then(res => {
        if (res.success && res.data) {
          if (!this.files[fileType]) {
            this.files[fileType] = []
          }
          this.files[fileType].push(res.data)
          this.$emit('fileChange')
        } else {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员！')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(res => {
        this.spinning = false
      })
      return false
    }
  }
}
</script>

<style>
    .patentFile{
        display: flex;
        width:100%;
        background-color:#F4F8FE;
        min-height:45px;
        line-height:45px;
    }
</style>
