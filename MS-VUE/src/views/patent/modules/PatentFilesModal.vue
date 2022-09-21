<!--
 * @Author: zdf
 * @Date: 2021-08-24 08:34:22
 * @LastEditTime: 2021-08-24 13:40:24
 * @LastEditors: zdf
 * @Description: 专利文件弹窗
 * @FilePath: \MS-VUE\src\views\patent\modules\PatentFilesModal.vue
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
      <patent-files
        :bordered="false"
        :row="{patentNo}"
        :files="files"
        :control="{upload: true,download: true,preview:true,deleteFile: true}"
        :canUpload="true"
        :onPreview="onPreview"/>
    </a-spin>
    <a-row style="text-align:right;">
      <a-button @click="closeModal">关闭</a-button>
    </a-row>
    <preview-modal ref="previewModal"/>
  </a-modal>
</template>

<script>
import patentFiles from '@/components/Patent/PatentFiles.vue'
import PreviewModal from '@/components/PreviewModal'
export default {
  components: {
    patentFiles,
    PreviewModal
  },
  data () {
    return {
      visible: false,
      title: '',
      patentNo: '',
      files: {},
      spinning: false
    }
  },
  methods: {
    show (patentNo, patentName) {
      this.spinning = false
      this.title = `${patentName}-资料`
      this.visible = true
      this.patentNo = patentNo
      this.loadFile(patentNo)
    },
    loadFile (patentNo) {
      this.spinning = true
      this.$http.get('/patentPlan/getPatentFiles', { params: { patentNo } }).then(res => {
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
    onPreview (path, name) {
      this.$refs.previewModal.show(path, name)
    }
  }
}
</script>

  <style>

  </style>
