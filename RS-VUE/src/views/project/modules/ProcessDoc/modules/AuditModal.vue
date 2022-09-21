<!--
 * @Author: ldx
 * @Date: 2021-01-21 11:03:51
 * @LastEditTime: 2022-03-14 17:30:34
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\ProcessDoc\modules\AuditModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    :bodyStyle="{ height: '65vh', maxHeight: '65vh' }"
    size="small"
    okText="提交"
    @ok="submitBtn">
    <template #footer>
      <a-button type="default" @click="isVisible = false">取消</a-button>
      <a-button type="primary" @click="submitBtn" :disabled="!fileIds.length > 0">提交</a-button>
    </template>
    <div id="tableBox" style="height:100%;">
      <vxe-grid
        :data="tableData"
        height="auto"
        size="small"
        ref="table"
        highlight-current-row
        highlight-hover-row
        show-overflow="title"
        resizable
        auto-resize
      >
        <vxe-table-column
          title="阶段"
          header-align="center"
          align="left"
          :width="100"
        >
          <template v-slot="{ row }"> {{ stageMap[row.stage] }} </template>
        </vxe-table-column>
        <vxe-table-column
          field="docFileName"
          title="文档名称"
          header-align="center"
          align="left"
          :width="180"
        />
        <vxe-table-column
          field="checked"
          title="是否提交"
          header-align="center"
          align="center"
          :width="80"
        >
          <template v-slot="{ row }" >
            <a-checkbox
              :checked="row.isSelected && isEditStatus(row.status)"
              :disabled="!isEditStatus(row.status) || !row.finished"
              :id="`${row.id}`"
              @change="(e) => checkboxChange(e,row)">
            </a-checkbox>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="状态"
          header-align="center"
          align="left"
          :width="90"
          sortable
          field="status"
        >
          <template v-slot="{ row }">{{ statusMap[row.status] }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="审核意见"
          header-align="center"
          align="left"
        >
          <template v-slot="{ row }">{{ row.suggestion }}</template>
        </vxe-table-column>
      </vxe-grid>
    </div>

  </a-modal>
</template>
<script>
import { isEditStatus, isExportStatus, statusMap, statusColor } from '@/utils/processDoc/auditStatus'
import ystable from '@/components/Table/'
import { cloneDeep } from 'lodash'
const stageMap = {
  '100': '规划阶段',
  '200': '研究阶段',
  '300': '设计开发',
  '400': '设计验证',
  '500': '实验验证',
  '600': '试验证',
  '700': '试制',
  '800': '项目验收',
  '900': '项目收尾'
}
export default {
  name: 'AuditModal',
  components: {
    ystable
  },
  props: {
    currentYear: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      statusMap,
      statusColor,
      stageMap,
      isVisible: false,
      title: '',
      tableData: [],
      projectId: -1,
      fileIds: []
    }
  },
  methods: {
    isEditStatus,
    isExportStatus,
    show (title, checkedFiles, projectId) {
      this.isVisible = true
      this.title = title
      this.tableData = this.sortTableData(cloneDeep(checkedFiles))
      this.fileIds = []
      this.tableData.forEach((item) => {
        if (item.isSelected && this.isEditStatus(item.status)) {
          this.fileIds.push(item.id)
        }
      })
      this.projectId = projectId
    },
    sortTableData (checkedFiles) {
      return checkedFiles.sort((a, b) => {
        return a.stage * 1 - b.stage * 1
      })
    },
    afterClose () {
      this.isVisible = false
      this.title = ''
      this.tableData = []
      this.projectId = -1
      this.fileIds = []
      // this.$emit('OnModalClose')
    },
    submitBtn () {
      const self = this
      this.$confirm({
        title: '您确认提交审核吗?',
        onOk () {
          self.handleSubmit()
        },
        onCancel () {}
      })
    },
    handleSubmit () {
      if (!this.fileIds.length) { return }
      const paramObj = {
        projectId: this.projectId,
        docFileId: this.fileIds,
        year: this.currentYear
      }
      this.$http.post('/projectAudit/submitDocAudit', paramObj).then((res) => {
        if (res.data && res.success) {
          this.$emit('refresh')
          this.isVisible = false
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
      })
    },
    checkboxChange (e, row) {
      row.isSelected = e.target.checked
      this.fileIds = []
      this.tableData.forEach((item) => {
        if (item.isSelected && this.isEditStatus(item.status)) {
          this.fileIds.push(item.id)
        }
      })
    }
  }
}
</script>
<style lang='less' scoped>
#tableBox /deep/ .vxe-table .c--title {
  width: 100% !important;
}
</style>
