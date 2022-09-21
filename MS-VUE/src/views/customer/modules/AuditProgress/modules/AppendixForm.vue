<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2021-11-25 09:31:23
 * @LastEditors: zdf
 * @Description:记录及附件
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\AppendixForm.vue
-->
<template>
  <div>
    <vxe-table
      id="AppendixForm"
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
      <vxe-table-column field="fileName" min-width="200" title="文件名称" align="left" header-align="center">
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
      <vxe-table-column field="fileType" min-width="200" title="文件类型" align="center" header-align="center">
        <template #default="{row}">
          {{ fileTypeList[row.fileType] }}
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
  name: 'AppendixForm',
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
      tableData: [],
      fileTypeList: {
        1: '会议纪要',
        2: '实验记录',
        3: '试制排期',
        4: '试制报表',
        5: '试制报告',
        6: '技术培训',
        7: '物料清单（BOM）',
        8: '作业指导书（SOP）',
        9: '发明专利',
        10: '实用新型专利',
        11: '外观设计专利',
        12: '计算机软件著作权',
        13: '集成电路布图设计',
        14: '科技查新报告',
        15: '论文期刊',
        16: '国家标准',
        17: '行业标准',
        18: '地方标准',
        19: '团体标准',
        20: '企业标准',
        21: '技术原理',
        22: '技术图纸',
        23: '技术方案',
        24: '技术规范',
        25: '工艺规程',
        26: '检测报告',
        27: '样品样机',
        99: '其他'
      }
    }
  },
  watch: {
    docFile () {
      this.getFileList()
    }
  },
  methods: {
    getFileList () {
      this.$http.get('/sysDocument/getAttachments', {
        params: {
          docFileId: this.docFile.id,
          projectId: this.docFile.projectId
        }
      }).then(res => {
        if (res.success) {
          this.tableData = res.data || []
        } else {
          this.$message.error(res.errorMessage || '获取记录及附件失败')
        }
      })
    },
    getExtension (path) {
      if (!path) return
      return path.substring(path.lastIndexOf('.'))
    },
    downloadFile (record) {
      const txt = this.getExtension(record.filePath)
      this.$exportData('/sysDocument/downloadAttachment', {
        path: record.filePath,
        fileName: record.fileName + txt
      }, record.fileName + txt, this.$message)
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
