<!--
 * @Author: zdf
 * @Date: 2021-08-24 13:34:38
 * @LastEditTime: 2022-06-30 11:45:55
 * @LastEditors: zdf
 * @Description: 专利文件
 * @FilePath: \RS-VUE\src\views\project\modules\PatentFilesModal.vue
-->
<template>
  <a-modal
    :width="900"
    :visible="visible"
    :title="title"
    :footer="null"
    :maskClosable="false"
    @cancel="closeModal"
  >
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-row v-for="(item,index) in patentFileTypes" :key="item.value" style="padding:8px;">
        <a-col :span="22">
          <div class="patentFile">
            <a-col :span="4" style="padding-left:20px;">
              {{ `${index + 1}.${item.label}` }}
            </a-col>
            <a-col :span="20" style="height:100%;line-height:20px;padding-top:11px;padding-bottom:10px;padding-right:10px;">
              <template v-if="files[item.value]">
                <span v-for="(file) in files[item.value]" :key="file.id" style="margin-right:5px;">
                  <a title="点击下载" @click="downloadFile(file)">
                    {{ file.fileName }}
                  </a>
                  <a-tooltip style="cursor:pointer" placement="top" @click="onPreview(file.filePath,file.fileName)">
                    <template slot="title">
                      <span>预览</span>
                    </template>
                    <a-icon type="eye" style="margin-left:5px" />
                  </a-tooltip>
                  <a-popconfirm
                    title="是否确定删除?"
                    @confirm="delFile(file.id,fileIndex,files[item.value])"
                    style="color:red;margin-left:5px"
                    v-if="control.edit && item.value === 9"
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
        <a-col :span="2" v-if="control.edit && item.value === 9">
          <div style="height:45px;line-height:45px;text-align:center;width:100%;padding-left:10px;">
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
    </a-spin>
    <a-row style="text-align:right;">
      <a-button @click="closeModal">关闭</a-button>
    </a-row>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      patentFileTypes: [
        { value: 0, label: '交底书' },
        { value: 1, label: '初稿' },
        { value: 2, label: '定稿' },
        { value: 3, label: '受理通知书' },
        { value: 10, label: '受理缴费' },
        { value: 4, label: '实质审查资料' },
        { value: 5, label: '授权通知书' },
        { value: 11, label: '办登缴费' },
        { value: 6, label: '知识产权证书' },
        { value: 7, label: '缴费收据' },
        { value: 9, label: '说明书' },
        { value: 8, label: '其他' }],
      visible: false,
      title: '',
      files: {},
      spinning: false,
      patentNo: undefined,
      control: {
        edit: this.$auth('project:patent:list:edit')
      }
    }
  },
  methods: {
    show (patentNo, patentName) {
      this.patentNo = patentNo
      this.spinning = false
      this.title = `${patentName}-资料`
      this.visible = true
      this.loadFile(patentNo)
    },
    loadFile (patentNo) {
      this.spinning = true
      this.$http.get('/patentDetail/getPatentFiles', { params: { patentNo } }).then(res => {
        if (res.success && res.data) {
          this.files = res.data
        } else {
          this.files = {}
          this.$message.error(res.errorMessage || '获取专利资料失败')
        }
        this.spinning = false
      })
    },
    closeModal () {
      this.spinning = false
      this.visible = false
    },
    beforeUpload (file, fileType) {
      if (file && file.size > 10485760) {
        this.$message.error(`上传失败，您上传的附件大于10M！`)
        return
      }
      const postParams = { patentNo: this.patentNo, fileType }
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
      this.$http.post('/patentDetail/uploadPatentFile', param, config).then(res => {
        if (res.success && res.data) {
          if (!this.files[fileType]) {
            this.files[fileType] = []
          }
          this.files[fileType].push(res.data)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(res => {
        this.spinning = false
      })
      return false
    },
    delFile (id, index, files) {
      this.spinning = true
      this.$http.post('/patentDetail/delPatentFile', { id }).then(res => {
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
    onPreview (path, name) {
      this.$preview({
        filePath: path,
        docName: name || '',
        visible: true
      })
    },
    downloadFile (file) {
      this.$exportData('/patentDetail/downloadPatentFile', { id: file.id }, file.fileName, this.$message)
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
