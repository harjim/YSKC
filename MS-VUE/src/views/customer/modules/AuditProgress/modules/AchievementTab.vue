<!--
 * @Author: zdf
 * @Date: 2021-09-03 16:33:37
 * @LastEditors: zdf
 * @LastEditTime: 2022-02-25 10:21:15
 * @Description: 成果审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\AchievementTab.vue
-->
<template>
  <tab-layout>
    <template #up>
      <a-form ref="form" layout="inline">
        <a-form-item label="成果名称">
          <a-input v-model="params.achievementName" placeholder="请输入成果名称"/>
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
          :expand-config="{lazy: true, loadMethod: loadMethod,visibleMethod: fileVisible}"
          height="auto"
          queryUrl="/rsAchievement/getList"
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
          <template #auditStatusSlot="{row}">
            {{ row.status !== null ? statusMap[row.status] : '-' }}
          </template>
          <template #fileCntSlot="{row}">{{ row.fileCnt || '-' }}</template>
          <template #fileCntContent="{row}">
            <a-table
              rowKey="id"
              size="small"
              :dataSource="row.files"
              :pagination="false"
              style="background: white;"
            >
              <a-table-column title="阶段" data-index="stageKey" align="center" :width="100">
                <template slot-scope="text">
                  {{ stageMap[text] }}
                </template>
              </a-table-column>
              <a-table-column title="转化结果" data-index="converResult" align="left" :width="100">
                <template slot-scope="text">
                  {{ converResults[text] }}
                </template>
              </a-table-column>
              <a-table-column title="证明材料" data-index="docName" align="left" :width="100"/>
              <a-table-column title="文件名" data-index="fileName" align="left" :width="300">
                <template slot-scope="t,child">
                  <span>
                    <a @click="onDownloadFile(child.filepath,child.fileName)">{{ t }}</a>
                  </span>
                  <span>
                    <a-tooltip style="cursor:pointer" placement="top" @click="onPreview(child.filepath,child.fileName)">
                      <template slot="title">
                        <span>预览</span>
                      </template>
                      <a-icon type="eye" style="margin-left:5px" />
                    </a-tooltip>
                  </span>
                </template>
              </a-table-column>
              <a-table-column title="上传时间" data-index="lastUpdateTime" align="center" :width="130"/>
            </a-table>
          </template>
          <!-- <template #stageKeySlot="{row}">
            {{ stageMap[row.stageKey] }}
          </template>
          <template #fileSlot="{row}">
            <template v-if="row.filePath">
              <a @click="onDownloadFile(row.filePath,row.fileName)" title="点击下载">{{ row.fileName || '-' }}</a>
              <a-icon
                v-if="row.filePath"
                style="cursor:pointer;margin-left:5px"
                placement="top"
                @click="() =>onPreview(row.filePath,row.fileName)"
                title="预览"
                type="eye"
              />
            </template>
          </template> -->
        </ystable>
        <preview-modal ref="previewModal"/>
        <batch-audit-result-modal ref="batchAuditResultModal" @refresh="updataStatus"/>
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
import BatchAuditResultModal from './BatchAuditResultModal'
import BatchActivateModal from './BatchActivateModal'
import Template from '@/views/doc/Template.vue'
export default {
  name: 'PatentTab',
  components: {
    ystable,
    AuditLog,
    PreviewModal,
    TabLayout,
    BatchAuditResultModal,
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
  created () {
    this.$getDictionary(6).then(res => {
      for (const stage of res) {
        this.stageMap[stage.key] = stage.value
      }
    })
  },
  mounted () {
    this.params.year = this.record.year
    this.params.companyId = this.record.companyId
    // this.callBack()
  },
  data () {
    return {
      statusMap,
      types: { 0: '专利', 1: '版权', 2: '集成电路布图设计', 3: '其他' },
      sources: { 0: '自主研发', 1: '受让、受赠、并购', 2: '其他' },
      converTypes: { 0: '自行投资转化', 1: '向他人转让该技术成果', 2: '许可他人使用该科技成果', 3: '以该科技成果作为合作条件，与他人共同实施转化', 4: '以该科技成果作价投资，折算股份或者出资比例' },
      converResults: { 0: '新产品', 1: '新设备', 2: '新技术服务', 3: '样品/样机', 4: '新服务' },
      column: [
        { type: 'checkbox', width: 60, headerAlign: 'center', align: 'center' },
        { type: 'seq', title: '序号', width: 60, headerAlign: 'center', align: 'center', slots: { default: 'seqSlot' } },
        { field: 'achievementName', title: '成果名称', minWidth: 180, headerAlign: 'center', align: 'left', remoteSort: true },
        { field: 'rdTitle', title: 'RD', minWidth: 130, headerAlign: 'center', align: 'left', remoteSort: true },
        { field: 'pname', title: '项目名称', minWidth: 180, headerAlign: 'center', align: 'left', remoteSort: true },
        { field: 'fileCnt', title: '文件数', width: 120, headerAlign: 'center', align: 'left', type: 'expand', slots: { default: 'fileCntSlot', content: 'fileCntContent' }, remoteSort: true },
        { field: 'status', title: '状态', minWidth: 90, headerAlign: 'center', align: 'left', slots: { default: 'auditStatusSlot' }, remoteSort: true },
        { field: 'type', title: '成果类型', width: 100, headerAlign: 'center', align: 'left', remoteSort: true, formatter: ({ row }) => this.types[row.type] },
        { field: 'source', title: '成果来源', width: 100, headerAlign: 'center', align: 'left', remoteSort: true, formatter: ({ row }) => this.sources[row.source] },
        { field: 'converType', title: '转化形式', width: 120, headerAlign: 'center', align: 'left', remoteSort: true, formatter: ({ row }) => this.converTypes[row.converType] },
        { field: 'lastUploadTime', title: '最后上传时间', width: 130, headerAlign: 'center', align: 'center', remoteSort: true },
        { field: 'description', title: '成果说明', width: 230, headerAlign: 'center', remoteSort: true }
        // { field: 'stageKey', title: '项目阶段', minWidth: 150, headerAlign: 'center', align: 'left', slots: { default: 'stageKeySlot' } },
        // { field: 'year', title: '年份', minWidth: 80, headerAlign: 'center', align: 'left' },
        // { field: 'listName', title: '文件类型', minWidth: 100, headerAlign: 'center', align: 'left' },
        // { field: 'filePath', title: '文件名', minWidth: 200, headerAlign: 'center', align: 'left', slots: { default: 'fileSlot' } },
        // { field: 'createTime', title: '上传时间', minWidth: 200, headerAlign: 'center', align: 'left' }
      ],
      spinningLog: false,
      selectedRows: [],
      params: {
        year: undefined,
        companyId: undefined,
        companyName: undefined
      },
      logGetParams: {
        achievementId: undefined
      },
      active: {
        name: '',
        index: 0,
        process: 0
      },
      showHeaderChk: true,
      stageMap: {},
      typeList: [],
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
    fileVisible ({ row }) {
      return Boolean(row.fileCnt)
    },
    loadMethod ({ row }) {
      row.files = []
      return new Promise(resolve => {
        this.$http.get('/rsAchievement/getFiles', { params: { achievementId: row.id } }).then(res => {
          if (res.success) {
            if (res.data) {
              row.files = res.data
            } else {
              this.$message.error('获取成果文件失败')
            }
          }
          resolve()
        })
      })
    },
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
      this.$refs.batchAuditResultModal.show(this.batchAuditList)
    },
    onBatchActivate () {
      this.$refs.batchActivateModal.projectAchievementShow('批量激活成果', this.batchActivateList)
    },
    onCellClick ({ row, rowIndex }) {
      this.logGetParams.achievementId = row.id
      this.active.index = rowIndex
      this.active.process = row.process
      this.active.name = row.achievementName
      this.$refs.table.setCurrentRow(row)
      this.cellClickId = row.id
    },
    completed ({ data: { data } }) {
      this.selectedRows = []
      if (!data.length) { return }
      let selectedIndex = data.findIndex(item => item.id === this.cellClickId)
      selectedIndex = selectedIndex === -1 ? 0 : selectedIndex
      this.logGetParams.achievementId = data[selectedIndex].id
      this.$refs.table.setCurrentRow(data[selectedIndex])
      this.active.process = data[selectedIndex].process
      this.active.name = data[selectedIndex].achievementName
      this.$refs.auditLog.handleGetAuditLog()
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
