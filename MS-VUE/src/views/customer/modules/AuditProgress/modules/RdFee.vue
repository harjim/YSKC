<!--
 * @Author: zdf
 * @Date: 2022-04-28 11:42:42
 * @LastEditTime: 2022-04-29 14:56:43
 * @LastEditors: zdf
 * @Description: 研发费
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\RdFee.vue
-->
<template>
  <tab-layout>
    <template #up>
      <rd-fee-table
        :costType="costType"
        :queryParams="{rdFeeId:record.dataId,rdType:record.rdType,month:month,companyId:record.companyId}"
        :rdType="record.rdType"
        :showAudit="false"
        @loadData="loadData"
        url="/rdFeeAudit/getRdFund"/>
    </template>
    <template #down>
      <audit-log ref="auditLog" :params="logGetParams" :title="title" ></audit-log>
    </template>
  </tab-layout>
</template>

<script>

import AuditLog from './AuditLog'
import TabLayout from './TabLayout.vue'
import RdFeeTable from '@/views/innovation/modules/auditModules/RdFeeTable.vue'
import { auditCostTypesMap } from '@/views/innovation/modules/auditModules/FeeDetailConfig'
export default {
  components: {
    RdFeeTable,
    AuditLog,
    TabLayout
  },
  props: {
    record: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      auditCostTypesMap,
      logGetParams: {},
      title: '',
      month: '',
      costType: {}
    }
  },
  created () {
    this.costType = auditCostTypesMap[this.record.rdType]
    this.logGetParams.rdFeeId = this.record.dataId
  },
  methods: {
    loadData (row) {
      this.month = `${row.month}-01 00:00:00`
      this.title = `${row.rdTitle}-${row.month}-${this.costType.title}`
      this.$nextTick(() => {
        if (this.$refs.auditLog && this.$refs.auditLog.handleGetAuditLog) {
          this.$refs.auditLog.handleGetAuditLog()
        }
      })
    }
  }
}
</script>
<style>
</style>
