<!--
 * @Author: lzh
 * @Date: 2021-09-10 14:58:42
 * @LastEditors: hm
 * @LastEditTime: 2022-09-14 11:51:07
 * @Description: 委托项目辅助账 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\TrustProjectSummaryForm.vue
-->

<template>
  <div>
    <vxe-table
      id="TrustContractForm"
      row-id="id"
      ref="table"
      size="small"
      :data="content.list"
      highlight-hover-row
      highlight-current-row
      show-overflow
      resizable
      auto-resize
    >
      <vxe-table-column type="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column field="fileName" min-width="200" title="文件名称" align="left" header-align="center">
        <template #default="{row}">
          <a title="点击下载" @click="downloadFile(row)">{{ row.fileName }}{{ getExtension(row.filePath) }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column field="uploadTime" width="150" title="记录日期" align="center" header-align="center">
        <template #default="{row}">
          {{ row.uploadTime | DayFormat }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="操作" width="150" align="center" header-align="center">
        <template #default="{row}">
          <a type="primary" @click="onPreview(row)">预览</a>
          <a-divider type="vertical"></a-divider>
          <!-- <a type="primary" @click="onEdit(row)">编辑</a>
          <a-divider type="vertical"></a-divider> -->
          <a type="primary" @click="removeRow(row)" :disabled="!isEdit">移除</a>
        </template>
      </vxe-table-column>
    </vxe-table>
    <a-button
      title="上传附件"
      type="dashed"
      style="width: 100%;font-weight: bolder"
      :disabled="!isEdit"
      @click="onUpload">上传文件</a-button>
    <upload
      action="/import/importImages"
      ref="upload"
      :paramData="{docFileId,projectId}"
      @success="success"
      :projectId="projectId"
      accept="image/png,image/jpg,image/jpeg,image/gif,application/pdf"></upload>
    <preview-modal url="/document/appendixPreview" ref="previewModal"></preview-modal>
  </div>
</template>
<script>
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import moment from 'moment'
import PreviewModal from '@/components/PreviewModal'
import upload from '@/components/UploadModal/TrustUpload.vue'

export default {
  name: 'TrustProjectSummaryForm',
  components: {
    PreviewModal,
    upload
  },

  created () {
    this.content = getTemplateContent('TrustProjectSummaryForm')
    this.BPContent = getTemplateContent('TrustProjectSummaryForm')
  },

  props: {
    docId: {
      type: Number,
      required: true
    },
    docFileId: {
      type: Number,
      required: true
    },
    projectId: {
      type: Number,
      required: true
    },
    project: {
      type: Object,
      required: true
    },
    isEdit: {
      type: Boolean,
      required: true
    },
    stage: {
      type: Object,
      required: true
    },
    callbackEvent: {
      type: Function,
      default: () => { }
    }
  },
  data () {
    return {
      content: {
        list: []
      },
      BPContent: ''
    }
  },
  methods: {
    moment,
    success (data) {
      this.content.list = [...this.content.list, data]
      this.callbackEvent()
    },
    onUpload () {
      this.$refs.upload.show(this.stage)
    },
    removeRow (record) {
      const _this = this
      this.$confirm({
        title: '您确定删除吗?',
        onOk () {
          if (_this.content.list.length <= 0) return
          const idx = _this.content.list.findIndex(value => {
            return value.id === record.id
          })
          _this.content.list.splice(idx, 1)
          _this.callbackEvent()
        }
      })
    },
    getExtension (path) {
      if (!path) return
      return path.substring(path.lastIndexOf('.'))
    },
    downloadFile (record) {
      const txt = this.getExtension(record.filePath)
      this.$exportData('/sysDocument/downloadAttachment', { path: record.filePath, fileName: record.fileName + txt }, record.fileName + txt, this.$message)
    },
    onPreview (record) {
      const txt = this.getExtension(record.filePath)
      this.$refs.previewModal.show(record.filePath, record.fileName + txt)
    },
    onEdit (record) {
      this.$refs.upload.edit(this.stage, record)
    }
  }
}
</script>

<style>
</style>
