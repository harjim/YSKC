<!--
 * @Author: ldx
 * @Date: 2021-01-22 14:21:18
 * @LastEditTime: 2022-01-06 08:22:48
 * @LastEditors: lzh
 * @Description: 项目名称审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\ProjectTab.vue
-->
<template>
  <tab-layout>
    <template #up>
      <div class="table_wrap">
        <vxe-grid
          id="projectTab-/projectProgress/queryProject"
          ref="table"
          :data="projectTableData"
          highlight-hover-row
          show-overflow="title"
          show-header-overflow
          highlight-current-row
          @cell-click="onCellClick"
          @completed="completed"
          :checkbox-config="{ checkMethod: checCheckboxkMethod,showHeader: showHeaderChk}"
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          :expand-config="{lazy: true, loadMethod: loadMethod}"
          resizable
          size="small"
          auto-resize
          height="100%"
          :customConfig="{storage: { visible: true, resizable: true } }"
          :columns="column"
        >
          <template #top>
            <a-button v-if="$auth('customer:projectProgress:auditBatch')" :disabled="batchAuditList.length <= 0" type="primary" @click="onBatchAudit" style="margin-right: 10px; margin-bottom: 5px;">批量审核</a-button>
            <a-button v-if="$auth('customer:projectProgress:activateFlows')" :disabled="batchActivateList.length <= 0" type="primary" @click="onBatchActivate" style="margin-right: 10px; margin-bottom: 5px;">批量激活</a-button>
            <a-button v-if="$auth('customer:projectProgress:checkdup')" :disabled="selectedRows.length <= 0" type="primary" @click="$refs.checkModal.open(selectedRows)" style="margin-right: 10px; margin-bottom: 5px;">查重</a-button>
            <span class="span_wrap">项目数: <span class="value">{{ existProjectNum }}</span></span>
            <span class="span_wrap">总预算: <span class="value">{{ budgetNum.toFixed(2) }}万元</span></span>
          </template>
          <template #titleSlot="{row}">
            {{ row.pname }}
          </template>
          <template #contentSlot="{row}">
            <a-tabs default-active-key="1" tab-position="left">
              <a-tab-pane key="1" tab="基本信息">
                <project-info :record="row"></project-info>
              </a-tab-pane>
              <a-tab-pane key="2" tab="项目成员">
                <ystable
                  ref="yst"
                  :resizable="true"
                  auto-resize
                  highlight-hover-row
                  show-overflow
                  show-header-overflow
                  queryUrl="/projectProgress/getMemberDataList"
                  :params="{projectId: row.id, companyId: params.companyId, year: record.year}"
                  export-config
                  :expand-config="{accordion: true}"
                >
                  <vxe-table-column title="工号" field="enumber" fixed="left" width="100"/>
                  <vxe-table-column title="姓名" field="ename" fixed="left" width="100"/>
                  <vxe-table-column title="职位" field="position" width="100"/>
                  <vxe-table-column title="人员类型" field="etype" width="100">
                    <template v-slot="{row}">
                      {{ !row.etype || row.etype === -1 ? '' : $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label }}
                    </template>
                  </vxe-table-column>
                  <vxe-table-column title="研发部门" field="fullName" width="100"/>
                  <vxe-table-column title="项目角色" field="role" width="100"/>
                  <vxe-table-column title="项目负责人" field="master" width="100">
                    <template v-slot="{row}">{{ row.master ? '是': '' }}</template>
                  </vxe-table-column>
                  <vxe-table-column title="加入日期" field="entryDate" width="100"/>
                  <vxe-table-column title="出生日期" field="birthday" width="100"/>
                  <vxe-table-column title="职称" field="title" width="100"/>
                  <vxe-table-column title="专业" field="specialities" width="100"/>
                  <vxe-table-column title="入职日期" field="edate" width="100"/>
                </ystable>
              </a-tab-pane>
              <a-tab-pane key="3" tab="资产清单">
                <ystable
                  ref="yst"
                  :resizable="true"
                  auto-resize
                  highlight-hover-row
                  show-overflow
                  show-header-overflow
                  queryUrl="/projectProgress/getEquipmentDataList"
                  :params="{projectId: row.id, companyId: params.companyId, year: record.year}"
                  export-config
                  :expand-config="{accordion: true}"
                >
                  <vxe-table-column title="资产代码" field="ecode" fixed="left" width="100"/>
                  <vxe-table-column title="设备名称" field="ename" fixed="left" width="100"/>
                  <vxe-table-column title="设备型号" field="emodal" width="100"/>
                  <vxe-table-column title="设备类型" field="etype" width="100">
                    <template v-slot="{row}">{{ row.etype ? $getEnums('equipmentEnum').find(item => item.value === Number(row.etype)).label: '' }}</template>
                  </vxe-table-column>
                  <vxe-table-column title="研发部门" field="fullName" width="100"/>
                  <vxe-table-column title="加入日期" field="entryDate" width="100"/>
                  <vxe-table-column title="原值" field="unitPrice" width="100"/>
                  <vxe-table-column title="项目作用" field="effect" width="100"/>
                  <vxe-table-column title="部门" field="deptName" width="100"/>
                  <vxe-table-column title="车间" field="workshop" width="100"/>
                  <vxe-table-column title="产线" field="productLine" width="100"/>
                  <vxe-table-column title="工艺段" field="processSection" width="100"/>
                </ystable>
              </a-tab-pane>
              <a-tab-pane key="4" tab="阶段">
                <stage-form :queryParam="{ projectId: row.id, companyId: params.companyId }"/>
              </a-tab-pane>
            </a-tabs>
          </template>
          <template #seqSlot="{row,rowIndex}">
            <a-badge :dot="row.hasPermission" :offset="[0,-7]">
            </a-badge>
            {{ rowIndex + 1 }}
          </template>
          <template #fullnameSlot="{row}">
            <span v-if="row.fullname && row.fullname !== null">
              {{ row.fullname }}
            </span>
            <span v-else>
              {{ `${row.deptName || '' }` }}{{ row.deptName && row.workshop ? '/' : '' }}{{
                `${row.workshop || ''}` }}{{ (row.deptName || row.workshop) && row.productLine ? '/' : '' }}{{
                `${row.productLine || ''}` }}{{ (row.deptName || row.workshop || row.productLine) && row.processSection ? '/' : '' }}{{
                `${row.processSection || ''}` }}
            </span>
          </template>
        </vxe-grid>
        <batch-audit-modal ref="batchAuditModal" @refresh="updataStatus"></batch-audit-modal>
        <batch-activate-modal ref="batchActivateModal" @refresh="updataStatus"></batch-activate-modal>
        <project-check-modal v-if="$auth('customer:projectProgress:checkdup')" ref="checkModal" checkUrl="/projectcheck/getDuplicateNameList" />
      </div>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" title="项目名称"></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import BatchAuditModal from './BatchAuditModal'
import BatchActivateModal from './BatchActivateModal'
import ProjectInfo from './ProjectInfo.vue'
import ystable from '@/components/Table/ystable'
import AuditLog from './AuditLog'
import TabLayout from './TabLayout.vue'
import StageForm from './projectTabStageForm.vue'
import ProjectCheckModal from './duplicate/ProjectCheckModal.vue'
export default {
  name: 'ProjectTab',
  components: {
    ystable,
    AuditLog,
    TabLayout,
    BatchAuditModal,
    BatchActivateModal,
    ProjectInfo,
    StageForm,
    ProjectCheckModal
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
    this.loadData()
  },
  data () {
    return {
      column: [
        { type: 'checkbox', width: 40, headerAlign: 'center', align: 'center' },
        { type: 'seq', title: '序号', width: 50, headerAlign: 'center', align: 'center', slots: { default: 'seqSlot' } },
        { field: 'rdTitle', title: 'RD', width: 100, headerAlign: 'center', align: 'left', showOverflow: true },
        // { title: 'RD', type: 'expand', width: 100, headerAlign: 'center', align: 'left', slots: { default: 'titleSlot', content: 'contentSlot' } },
        // { field: 'pname', title: '项目名称', minWidth: 200, headerAlign: 'center', align: 'left' },
        { type: 'expand', title: '项目名称', minWidth: 200, headerAlign: 'center', align: 'left', slots: { default: 'titleSlot', content: 'contentSlot' }, showOverflow: true },
        { field: 'fullname', title: '部门', width: 140, headerAlign: 'center', align: 'left', slots: { default: 'fullnameSlot' }, showOverflow: true },
        { field: 'ename', title: '项目负责人', width: 120, headerAlign: 'center', align: 'left', showOverflow: true },
        { field: 'budget',
          title: '预算(万元)',
          width: 100,
          headerAlign: 'center',
          align: 'right',
          formatter: ({ cellValue, row }) => {
            if (row.budget !== null) {
              return row.budget.toFixed(2)
            } else {
              return '--'
            }
          },
          showOverflow: true
        },
        { field: 'beginAndEndDate',
          title: '项目周期',
          width: 200,
          headerAlign: 'center',
          align: 'center',
          formatter: ({ cellValue, row }) => {
            return `${row.beginDate} ~ ${row.endDate}`
          },
          showOverflow: true
        }
      ],
      projectTableData: [],
      spinningLog: false,
      existProjectNum: 0,
      budgetNum: 0,
      params: {
        year: undefined,
        companyId: undefined
      },
      logGetParams: {
        rsProjectId: undefined
        // projectId: undefined,
        // moduleId: undefined
      },
      selectedRows: [],
      active: {
        name: '',
        index: 0
      },
      showHeaderChk: true,
      deptNameKeyMap: {}
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
    loadData () {
      this.$http.get('/projectProgress/queryProject', { params: this.params })
        .then(res => {
          if (res.data) {
            this.existProjectNum = res.data.length
            this.budgetNum = 0
            for (let index = 0; index < res.data.length; index++) {
              if (res.data[index].budget !== null) {
                // eslint-disable-next-line no-self-assign
                res.data[index].budget = res.data[index].budget
                const element = res.data[index]
                this.budgetNum += element.budget
              }
            }
            this.projectTableData = res.data
            this.completed(this.projectTableData)
          }
        })
    },
    callBack () {
      this.loadData()
      if (this.$refs.auditLog && this.$refs.auditLog.handleGetAuditLog) {
        this.$refs.auditLog.handleGetAuditLog()
      }
    },
    updataStatus () {
      this.loadData()
      this.$emit('updataStatus')
    },
    checCheckboxkMethod ({ row }) {
      return row.hasPermission || row.status === 1
    },
    onCellClick ({ row, rowIndex }) {
      this.logGetParams.rsProjectId = row.id
      this.active.index = rowIndex
      this.active.name = row.pname
      this.$refs.table.setCurrentRow(row)
    },
    completed (data) {
      this.selectedRows = []
      if (!data.length) { return }
      this.logGetParams.rsProjectId = data[0].id
      this.$refs.table.setCurrentRow(data[0])
      this.active.name = data[0].pname
      if (data && data.length) {
        this.showHeaderChk = data.some(item => {
          return item.hasPermission || item.status === 1
        })
      } else {
        this.showHeaderChk = false
      }
    },
    loadMethod () {
      return Promise.resolve()
    },
    onBatchAudit () {
      this.$refs.batchAuditModal.projectAuditShow('批量审核立项项目', this.batchAuditList)
    },
    onBatchActivate () {
      this.$refs.batchActivateModal.projectAuditShow('批量激活立项项目', this.batchActivateList)
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRows = records
    },
    setKeyMap (tree, allKey, fullPath, minValue) {
      tree.forEach(t => {
        if (t.parentId < minValue) {
          allKey[t.value] = ''
        } else {
          allKey[t.value] = `${fullPath ? fullPath + '/' : ''}${t.title}`
        }
        if (t.children && t.children.length > 0) {
          return this.setKeyMap(t.children, allKey, allKey[t.value], minValue)
        }
      })
      return allKey
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
