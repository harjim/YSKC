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
    :title="docName || '预览'"
    style="top: 20px;"
    :width="1200"
    :height="800"
    :maskClosable="false"
    :getContainer="popupContainer"
    v-model="show"
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
      show: this.visible,
      resData: undefined,
      errorMessage: undefined,
      fileLoading: false
    }
  },
  props: {
    url: {
      type: String,
      default: '/document/preview'
    },
    filePath: {
      type: String,
      default: ''
    },
    docName: {
      type: String,
      default: ''
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    visible (newValue) {
      this.show = newValue
    }
  },
  methods: {
    popupContainer,
    handleCancel () {
      Object.assign(this.$data, this.$options.data.call(this))
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
