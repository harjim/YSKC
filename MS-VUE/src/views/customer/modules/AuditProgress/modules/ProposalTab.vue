<!--
 * @Author: lzh
 * @Date: 2021-09-02 15:34:46
 * @LastEditors: lzh
 * @LastEditTime: 2022-01-06 14:22:21
 * @Description: 提案管理Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\ProposalTab.vue
-->

<template>
  <tab-layout>
    <template #up>
      <a-form ref="form" layout="inline">
        <a-form-item label="标题">
          <a-input v-model="params.title" placeholder="请输入标题"/>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="$refs.table.refresh(true)" >查询</a-button>
        </a-form-item>
        <a-form-item>
          <a-button v-if="$auth('customer:projectProgress:auditBatch')" type="primary" style="margin-right: 10px;" @click="onBatchAudit" :disabled="batchAuditList.length <=0 " >批量审核</a-button>
          <a-button v-if="$auth('customer:projectProgress:activateFlows')" type="primary" style="margin-right: 10px;" @click="onBatchActivate" :disabled="batchActivateList.length <=0 " >批量激活</a-button>
        </a-form-item>
      </a-form>
      <div class="table_wrap" style="height: calc(100% - 40px);" >
        <ystable
          ref="table"
          highlight-hover-row
          highlight-current-row
          show-overflow="title"
          resizable
          auto-resize
          size="small"
          height="auto"
          queryUrl="/proposalManagement/getList"
          :checkbox-config="{ checkMethod: checCheckboxkMethod,showHeader: showHeaderChk}"
          @cell-click="onCellClick"
          @completed="completed"
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          :params="params"
          :columns="column"
        >
          <template #seqSlot="{row,rowIndex}">
            <a-badge :dot="row.hasPermission" :offset="[0,-7]">
            </a-badge>
            {{ rowIndex + 1 }}
          </template>
          <template #typeSlot="{row}">
            {{ proposalTypes[row.type] }}
          </template>
          <template #auditStatusSlot="{row}">
            {{ row.status !== null ? statusMap[row.status] : '-' }}
          </template>
          <template #fileSlot="{row}">
            <template v-if="row.filePath">
              <span v-for="(fpath,index) in row.filePath.split(',')" :key="index">
                <a @click="onDownloadFile(fpath)" title="点击下载">{{ fpath.substring(fpath.lastIndexOf('/') + 14) || '-' }}</a>
                <a-icon
                  v-if="fpath"
                  style="cursor:pointer;margin-left:5px;margin-right:5px"
                  placement="top"
                  @click="() =>onPreview(fpath)"
                  title="预览"
                  type="eye"
                />
              </span>
            </template>

          </template>
        </ystable>
        <preview-modal ref="previewModal"/>
        <batch-audit-proposal-modal ref="batchAuditProposalModal" @refresh="updataStatus"/>
        <batch-activate-modal ref="batchActivateModal" @refresh="updataStatus" />
      </div>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" :title="active.name" :patentProcess="active.process"></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import AuditLog from './AuditLog'
import TabLayout from './TabLayout.vue'
import { statusMap } from '@/utils/processDoc/auditStatus'
import PreviewModal from '@/components/PreviewModal'
import BatchAuditProposalModal from './BatchAuditProposalModal.vue'
import BatchActivateModal from './BatchActivateModal'
import Template from '@/views/doc/Template.vue'
export default {
  name: 'PatentTab',
  components: {
    ystable,
    AuditLog,
    PreviewModal,
    TabLayout,
    BatchAuditProposalModal,
    BatchActivateModal,
    Template
  },
  props: {
    record: {
      type: Object,
      default: () => { return {} }
    },
    mode: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    this.params.year = this.record.year
    this.params.companyId = this.record.companyId
    // this.callBack()
  },
  data () {
    const proposalTypes = ['立项素材', '现场图片', '立项对接表', '体系文件提案', '专利提案', '费用规划', '其他提案']
    return {
      proposalTypes,
      statusMap,
      column: [
        { type: 'checkbox', width: 60, headerAlign: 'center', align: 'center' },
        { type: 'seq', title: '序号', width: 60, headerAlign: 'center', align: 'center', slots: { default: 'seqSlot' } },
        { field: 'title', title: '标题', minWidth: 150, headerAlign: 'center', align: 'left' },
        { field: 'type', title: '提案类型', minWidth: 80, headerAlign: 'center', align: 'left', slots: { default: 'typeSlot' } },
        { field: 'status', title: '状态', minWidth: 90, headerAlign: 'center', align: 'left', slots: { default: 'auditStatusSlot' } },
        { field: 'year', title: '年份', minWidth: 200, headerAlign: 'center', align: 'left' },
        { title: '附件', minWidth: 200, headerAlign: 'center', align: 'left', slots: { default: 'fileSlot' } },
        { field: 'remark', title: '备注', minWidth: 180, headerAlign: 'center', align: 'left' }
      ],
      spinningLog: false,
      selectedRows: [],
      params: {
        year: undefined,
        companyId: undefined,
        title: undefined,
        companyName: undefined
      },
      logGetParams: {
        proposalId: undefined
      },
      active: {
        name: '',
        index: 0,
        process: 0
      },
      showHeaderChk: true,
      cellClickId: 0
    }
  },
  computed: {
    batchAuditList () {
      return this.selectedRows.filter(item => item.hasPermission)
    },
    batchActivateList () {
      return this.selectedRows.filter(item => item.status === 1)
    }
  },
  methods: {
    moment,
    callBack () {
      if (this.$refs.auditLog && this.$refs.auditLog.handleGetAuditLog) {
        this.$refs.auditLog.handleGetAuditLog()
      }
    },
    updataStatus () {
      this.$refs.table.refresh()
      this.$emit('updataStatus')
    },
    /**
     * @description: 下载文档
     * @param {*} record 行记录
     * @param {*} type type: 1 交底书 2.上传资料
     * @return {*}
     */
    onDownloadFile (url, name) {
      this.$exportData('/patentBuying/download', { filePath: url }, name, this.$message)
    },
    onPreview (filePath, name) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$nextTick(() => {
        this.$refs.previewModal.show(filePath, name)
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRows = records
    },
    checCheckboxkMethod ({ row }) { return row.hasPermission || row.status === 1 },
    onBatchAudit () {
      this.$refs.batchAuditProposalModal.show(this.batchAuditList)
    },
    onBatchActivate () {
      this.$refs.batchActivateModal.projectProposalShow('批量激活提案', this.batchActivateList)
    },
    onCellClick ({ row, rowIndex }) {
      this.logGetParams.proposalId = row.id
      this.active.index = rowIndex
      this.active.name = row.title
      this.active.process = row.process
      this.$refs.table.setCurrentRow(row)
      this.cellClickId = row.id
    },
    completed ({ data: { data } }) {
      this.selectedRows = []
      if (!data.length) { return }
      let selectedIndex = data.findIndex(item => item.id === this.cellClickId)
      selectedIndex = selectedIndex === -1 ? 0 : selectedIndex
      this.logGetParams.proposalId = data[selectedIndex].id
      this.$refs.table.setCurrentRow(data[selectedIndex])
      this.active.name = data[selectedIndex].title
      this.active.process = data[selectedIndex].process
      if (data && data.length) {
        this.showHeaderChk = data.some(item => { return item.hasPermission || item.status === 1 })
      } else {
        this.showHeaderChk = false
      }
    }
  }
}
</script>

<style lang="less" scoped>
@import '../css/AuditDrawerTab.less';
* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis>.vxe-body--expanded-cell {
  overflow: auto;
}
</style>
