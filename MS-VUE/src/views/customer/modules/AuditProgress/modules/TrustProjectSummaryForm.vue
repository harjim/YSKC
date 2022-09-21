<!--
 * @Author: lzh
 * @Date: 2021-09-13 11:14:00
 * @LastEditors: lzh
 * @LastEditTime: 2021-09-13 14:18:13
 * @Description: 辅助账
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\TrustProjectSummaryForm.vue
-->
<template>
  <div>
    <vxe-table
      id="TrustProjectSummaryForm"
      row-id="id"
      ref="table"
      size="small"
      :data="tableData"
      highlight-hover-row
      highlight-current-row
      show-overflow
      resizable
      auto-resize
    >
      <vxe-table-column type="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column field="fileName" min-width="150" title="文件名称" align="left" header-align="center">
        <template #default="{row}">
          <a title="点击下载" @click="downloadFile(row)">{{ row.fileName }}{{ getExtension(row.filePath) }}</a>
          <a-tooltip style="cursor:pointer" placement="top" @click="onPreview(row)">
            <template slot="title">
              <span>预览</span>
            </template>
            <a-icon type="eye" style="margin-left:5px" />
          </a-tooltip>
        </template>
      </vxe-table-column>
      <vxe-table-column field="uploadTime" width="150" title="记录日期" align="center" header-align="center">
        <template #default="{row}">
          {{ row.uploadTime | DayFormat }}
        </template>
      </vxe-table-column>
    </vxe-table>
    <preview-modal url="/sysDocument/appendixPreview" ref="previewModal"></preview-modal>
  </div>
</template>
<script>
import PreviewModal from '@/components/PreviewModal'
export default {
  name: 'TrustProjectSummaryForm',
  components: {
    PreviewModal
  },
  props: {
    docFile: {
      type: Object,
      required: true
    }
  },
  created () {
    this.hsaFileDataNull = false
    this.getFileList()
  },
  data () {
    return {
      tableData: []
    }
  },
  watch: {
    docFile () {
      this.getFileList()
    }
  },
  methods: {
    getFileList () {
      if (this.docFile.data) {
        const data = JSON.parse(this.docFile.data)
        this.tableData = data.list
      }
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
    }
  }
}
</script>

<style>
</style>
