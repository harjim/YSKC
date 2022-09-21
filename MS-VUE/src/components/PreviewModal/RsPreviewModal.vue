<!--
 * @Author:
 * @Date: 2020-08-03 18:57:07
 * @LastEditTime: 2021-04-12 17:46:09
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\components\PreviewModal\PreviewModal.vue
-->
<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="1200"
    :height="800"
    :maskClosable="false"
    :getContainer="popupContainer"
    v-model="visible"
    :zIndex="2000"
  >
    <preview :filePath="filePath" :url="url" :docName="docName" />
    <template slot="footer">
      <a-button key="back" @click="handleCancel">关闭</a-button>
    </template>
  </a-modal>
</template>
<script>
import Preview from '../Preview/Preview'
import { popupContainer } from '@/docTemplate/Templates/js/screenFullMountDom.js'
export default {
  name: 'PreviewModal',
  components: {
    Preview
  },
  data () {
    return {
      title: '预览',
      visible: false,
      filePath: undefined,
      docName: undefined,
      resData: undefined,
      errorMessage: undefined,
      fileLoading: false
    }
  },
  props: {
    url: {
      type: String,
      default: '/document/preview'
    }
  },
  methods: {
    popupContainer,
    handleCancel (e) {
      this.visible = false
    },
    show (filePath, docName) {
      this.title = docName
      this.visible = true
      this.filePath = filePath
      this.docName = docName
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
