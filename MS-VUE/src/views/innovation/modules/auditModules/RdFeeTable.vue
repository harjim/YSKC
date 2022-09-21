<!--
 * @Author: zdf
 * @Date: 2022-04-25 16:38:01
 * @LastEditTime: 2022-05-06 17:23:51
 * @LastEditors: zdf
 * @Description: 费用表格
 * @FilePath: \MS-VUE\src\views\innovation\modules\auditModules\RdFeeTable.vue
-->
<template>
  <div>
    <vxe-grid
      ref="table"
      :data="tableData"
      highlight-hover-row
      highlight-current-row
      show-overflow="title"
      auto-resize
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :expand-config="{lazy: true, loadMethod: loadMethod}"
      :checkbox-config="{ checkMethod: canCheckMethod,showHeader: showHeaderChk}"
      :loading="loading"
      row-id="id"
      :toolbar="{ refresh: { query: queryData }, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
    >
      <template v-slot:toolbar_buttons>
        <a-button
          v-if="showAudit && control.audit"
          @click="audits"
          :disabled="!selectAuditKeys.length"
          type="primary"
          style="margin-right: 10px;"
        >审核</a-button>
        <a-button
          v-if="showAudit && control.activate"
          @click="$refs.activateModal.show(selectActivateKeys)"
          :disabled="!selectActivateKeys.length"
          type="primary"
          style="margin-right: 10px;"
        >激活</a-button>
      </template>

      <vxe-table-column type="checkbox" width="50"/>
      <vxe-table-column
        title="项目名称"
        field="rdTitle"
        min-width="220"
        max-width="320"
        showHeaderOverflow
        type="expand">
        <template #default="{row}">{{ `${row.rdTitle}-${row.pname}` }}</template>
        <template #content="{row}">
          <div v-if="costType.tabs">
            <a-tabs :defaultActiveKey="defaultActive(row.projectId)" :key="row.projectId">
              <a-tab-pane v-for="(v,k) in costType.tabs" :key="`${row.projectId}_${k}`" :tab="v">
                <component
                  :key="`${row.projectId}${k}`"
                  :is="feeDetailTable"
                  :tableConfig="getCurConfig(row.projectId,k)"
                  :queryParams="queryParams"
                />
              </a-tab-pane>
            </a-tabs>
          </div>
          <component
            v-else
            :key="`${row.projectId}${rdType}`"
            :is="feeDetailTable"
            :tableConfig="getCurConfig(row.projectId,rdType)"
            :queryParams="queryParams"
          />
        </template>
      </vxe-table-column>
      <vxe-table-column title="状态" v-if="showAudit" field="status" min-width="80" showHeaderOverflow>
        <template #default="{row}"><a-badge :color="statusColor[row.status]" :text="getStatusName(row.status)" /></template>
      </vxe-table-column>
      <vxe-table-column title="当前处理人" field="auditUsers" min-width="120" showHeaderOverflow/>
      <template v-if="rdType=== 10000">
        <vxe-table-column
          title="工资"
          field="salary"
          align="right"
          width="120"
          showHeaderOverflow>
          <template #default="{row}">{{ toBit(row.salary) }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="五险一金"
          field="insurance"
          align="right"
          width="120"
          showHeaderOverflow>
          <template #default="{row}">{{ toBit(row.insurance) }}</template>
        </vxe-table-column>
      </template>
      <template v-if="rdType=== 30000">
        <vxe-table-column
          title="设备"
          field="prod"
          align="right"
          width="120"
          showHeaderOverflow>
          <template #default="{row}">{{ toBit(row.prod) }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="仪器"
          field="lab"
          align="right"
          width="120"
          showHeaderOverflow>
          <template #default="{row}">{{ toBit(row.lab) }}</template>
        </vxe-table-column>
      </template>
      <vxe-table-column
        title="合计"
        field="totalAmount"
        align="right"
        min-width="140"
        showHeaderOverflow>
        <template #default="{row}">{{ toBit(row.totalAmount) }}</template>
      </vxe-table-column>
      <vxe-table-column
        v-if="showAudit && control.auditLog"
        title="操作"
        align="center"
        width="100"
        showHeaderOverflow>
        <template #default="{row}">
          <a @click="$refs.auditLog.show(row.instanceId,`${row.rdTitle}-${costType.title}${m}`)">审核日志</a>
        </template>
      </vxe-table-column>
    </vxe-grid>
    <audit-log-modal ref="auditLog"/>
    <audit-confirm-modal ref="auditConfirm" @ok="auditOk"/>
    <activate-modal ref="activateModal" @ok="auditOk"/>
  </div>
</template>

<script>
import { getConfigParams, toBit } from './FeeDetailConfig'
import AuditLogModal from './AuditLogModal.vue'
import FeeDetailTable from './FeeDetailTable.vue'
import AuditConfirmModal from '@/views/customer/modules/AuditConfirmModal.vue'
import { getStatusName, statusColor, isExportStatus } from '@/utils/processDoc/auditStatus'
import ActivateModal from '@/views/flow/modules/ActivateModal.vue'
export default {
  components: {
    AuditConfirmModal,
    AuditLogModal,
    ActivateModal
  },
  props: {
    queryParams: {
      type: Object,
      required: true
    },
    rdType: {
      type: Number,
      required: true
    },
    costType: {
      type: Object,
      required: true
    },
    url: {
      type: String,
      default: '/rdFeeAudit/getRdFunds'
    },
    m: {
      type: String,
      default: ''
    },
    showAudit: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      feeDetailTable: FeeDetailTable,
      statusColor,
      showHeaderChk: false,
      selectAuditKeys: [],
      selectActivateKeys: [],
      tableData: [],
      loading: false,
      configMap: {},
      control: {
        audit: this.$auth('innovation:rdFeeAudit:audit'),
        auditLog: this.$auth('innovation:rdFeeAudit:auditLog'),
        activate: this.$auth('innovation:rdFeeAudit:activate')
      }
    }
  },
  created () {
    // this.tableData = [{ rdTitle: '2021RD04', pname: '冷凝器精准组装定位及在线焊接工艺技术的研发', status: '1', auditUsers: '3332323' }]
    this.queryData()
  },
  methods: {
    toBit,
    getConfigParams,
    getStatusName,
    isExportStatus,
    auditOk () {
      this.queryData()
      this.$emit('freshCnt')
    },
    defaultActive (projectId) {
      return `${projectId}_${Object.keys(this.costType.tabs)[0]}`
    },
    queryData () {
      this.loading = true
      this.selectAuditKeys = []
      this.selectActivateKeys = []
      this.$http.get(this.url, { params: Object.assign({ rdType: this.rdType, children: this.costType.children }, this.queryParams) }).then(res => {
        let tableData = []
        if (res.success && res.data) {
          tableData = res.data
          this.showHeaderChk = tableData.some(a => a.hasPermision || this.isExportStatus(a.status))
          if (!this.showAudit && tableData.length) {
            this.$emit('loadData', tableData[0])
          }
        } else {
          this.$message.error(res.errorMessage || '加载失败')
        }
        this.tableData = tableData
      }).finally(() => {
        this.loading = false
      })
    },
    canCheckMethod ({ row }) {
      return row.hasPermision || this.isExportStatus(row.status)
    },
    selectChange ({ records }) {
      const auditKeys = []
      const activateKeys = []
      records.forEach(item => {
        if (item.hasPermision) {
          auditKeys.push(item.instanceId)
          return
        }
        if (this.isExportStatus(item.status)) {
          activateKeys.push(item.instanceId)
        }
      })
      this.selectAuditKeys = auditKeys
      this.selectActivateKeys = activateKeys
    },
    loadMethod ({ row }) {
      return new Promise(resolve => {
        row.load = true
        resolve()
      })
    },
    getCurConfig (projectId, rdType) {
      let config = this.configMap[`${projectId}_${rdType}`]
      if (!config) {
        config = this.getConfigParams(projectId, rdType)
        this.configMap[`${projectId}_${rdType}`] = config
      }
      return config
    },
    audits () {
      this.$refs.auditConfirm.open(this.selectAuditKeys)
    }
  }
}
</script>

<style lang="less" scoped>
* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis > .vxe-body--expanded-cell{
  padding:0 10px 10px 10px;
  overflow: auto;
}
</style>
