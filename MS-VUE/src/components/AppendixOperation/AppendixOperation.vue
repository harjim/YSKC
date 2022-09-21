<!--
 * @Author: ldx
 * @Date: 2021-07-22 11:12:41
 * @LastEditTime: 2021-08-02 16:42:43
 * @LastEditors: ldx
 * @Description: 附件预览与下载组件
 * @FilePath: \MS-VUE\src\components\AppendixOperation\AppendixOperation.vue
-->
<template>
  <span>
    <a v-if="isPreview" title="点击预览" @click="onPreview">
      {{ fileName }}
    </a>
    <span v-else>{{ fileName }}</span>
    <a-tooltip v-if="isPreview" style="cursor:pointer" placement="top" @click="onPreview">
      <template slot="title">
        <span>预览</span>
      </template>
      <a-icon type="eye" style="margin-left:5px" />
    </a-tooltip>
    <a-tooltip v-if="isDownload" style="cursor:pointer" placement="top" @click="onDowload">
      <template slot="title">
        <span>下载</span>
      </template>
      <a-icon type="download" style="margin-left:5px" />
    </a-tooltip>
    <a-popconfirm
      v-if="isDel"
      title="是否确定删除?"
      @confirm="onDownloadFile"
      style="color:red;margin-left:5px"
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
<script>
export default {
  name: 'AppendixOperation',
  props: {
    fileName: {
      type: String,
      required: true
    },
    filePath: {
      type: String,
      required: true
    },
    record: {
      type: Object,
      default: () => { return undefined }
    },
    isDownload: {
      type: Boolean,
      default: true
    },
    isPreview: {
      type: Boolean,
      default: true
    },
    isDel: {
      type: Boolean,
      default: false
    },
    params: {
      type: Object,
      default: () => { return undefined }
    }
  },
  data () {
    return {

    }
  },
  methods: {
    onPreview () {
      this.$emit('preview', { filePath: this.filePath, fileName: this.fileName }, this.record, this.params)
    },
    onDowload () {
      this.$emit('download', { filePath: this.filePath, fileName: this.fileName }, this.record, this.params)
    },
    onDel () {
      this.$emit('del', { filePath: this.filePath, fileName: this.fileName }, this.record, this.params)
    }
  }
}
</script>
<style lang="less" scoped>
</style>
