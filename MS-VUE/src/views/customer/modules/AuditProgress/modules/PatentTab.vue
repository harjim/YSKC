<!--
 * @Author: ldx
 * @Date: 2021-01-22 14:22:01
 * @LastEditTime: 2022-01-06 14:22:07
 * @LastEditors: lzh
 * @Description: 专利审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\PatentTab.vue
-->
<template>
  <tab-layout>
    <template #up>
      <a-form ref="form" layout="inline">
        <a-form-item label="申请名称">
          <a-input v-model="params.patentName" placeholder="请输入申请名称"/>
        </a-form-item>
        <a-form-item>
          <!-- <a-button type="primary" v-if="$auth('patent:auditList:query')" @click="$refs.table.refresh(true)" >查询</a-button> -->
          <a-button type="primary" @click="$refs.table.refresh(true)" >查询</a-button>
        </a-form-item>
        <a-form-item>
          <!-- <a-button v-if="$auth('patent:auditList:audit')" type="primary" style="margin-right: 10px;" @click="onBatchAudit" :disabled="selectedRows.length <=0 " >批量审核</a-button> -->
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
          queryUrl="/patentPlan/getAuditPatents"
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
          <template #pnameSlot="{row}">
            {{ row.rdTitle || '其他' }}
          </template>
          <template #auditStatusSlot="{row}">
            {{ row.status !== null ? statusMap[row.status] : '-' }}
          </template>
          <template #fileSlot="{row}">
            <template v-if="row.fileModels">
              <span v-for="(file,index) in row.fileModels" :key="index">
                <a v-if="$auth('project:patentPlan:download')" @click="onDownloadFile(file.filePath,file.fileName)" title="点击下载交底书">{{ file.fileName || '-' }}</a>
                <template v-else>
                  {{ file.fileName || '-' }}
                </template>
                <a-icon
                  v-if="file.filePath"
                  style="cursor:pointer;margin-left:5px"
                  placement="top"
                  @click="() =>onPreview(file.filePath,file.fileName)"
                  title="预览"
                  type="eye"
                />
              </span>
            </template>

          </template>
        </ystable>
        <preview-modal ref="previewModal"/>
        <batch-audit-patent-modal ref="batchAuditPatentModal" @refresh="updataStatus"/>
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
import BatchAuditPatentModal from '@/views/patent/modules/BatchAuditPatentModal'
import BatchActivateModal from './BatchActivateModal'
import Template from '@/views/doc/Template.vue'
export default {
  name: 'PatentTab',
  components: {
    ystable,
    AuditLog,
    PreviewModal,
    TabLayout,
    BatchAuditPatentModal,
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
    return {
      statusMap,
      column: [
        { type: 'checkbox', width: 60, headerAlign: 'center', align: 'center' },
        { type: 'seq', title: '序号', width: 60, headerAlign: 'center', align: 'center', slots: { default: 'seqSlot' } },
        { field: 'patentName', title: '申请名称', minWidth: 150, headerAlign: 'center', align: 'left' },
        { field: 'year', title: '年份', minWidth: 80, headerAlign: 'center', align: 'left' },
        { title: '项目名称', minWidth: 150, headerAlign: 'center', align: 'left', slots: { default: 'pnameSlot' } },
        { field: 'firstInventor', title: '发明人', minWidth: 100, headerAlign: 'center', align: 'left' },
        { field: 'status', title: '状态', minWidth: 90, headerAlign: 'center', align: 'left', slots: { default: 'auditStatusSlot' } },
        { field: 'files', title: '交底书', minWidth: 200, headerAlign: 'center', align: 'left', slots: { default: 'fileSlot' } },
        { field: 'code', title: '身份证号码或统一信息代码', minWidth: 180, headerAlign: 'center', align: 'left' }
      ],
      spinningLog: false,
      selectedRows: [],
      params: {
        year: undefined,
        companyId: undefined,
        patentName: undefined,
        companyName: undefined
      },
      logGetParams: {
        patentPlanId: undefined
      },
      active: {
        name: '',
        index: 0,
        process: 0
      },
      showHeaderChk: true
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
      this.$refs.batchAuditPatentModal.show(this.batchAuditList)
    },
    onBatchActivate () {
      this.$refs.batchActivateModal.projectPatentShow('批量激活专利', this.batchActivateList)
    },
    onCellClick ({ row, rowIndex }) {
      this.logGetParams.patentPlanId = row.id
      this.active.index = rowIndex
      this.active.name = row.patentName
      this.active.process = row.process
      this.$refs.table.setCurrentRow(row)
    },
    completed ({ data: { data } }) {
      this.selectedRows = []
      if (!data.length) { return }
      this.logGetParams.patentPlanId = data[0].id
      this.$refs.table.setCurrentRow(data[0])
      this.active.name = data[0].patentName
      this.active.process = data[0].process
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
