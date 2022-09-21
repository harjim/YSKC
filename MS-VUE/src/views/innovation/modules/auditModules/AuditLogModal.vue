<!--
 * @Author: zdf
 * @Date: 2022-04-26 15:05:41
 * @LastEditTime: 2022-04-29 08:27:34
 * @LastEditors: zdf
 * @Description: 审核日志弹窗
 * @FilePath: \MS-VUE\src\views\innovation\modules\auditModules\AuditLogModal.vue
-->
<template>
  <a-modal
    :zIndex="1001"
    :width="1000"
    style="top: 0px;"
    :visible="visible"
    :getContainer="popupContainer"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false">
    <vxe-grid
      ref="table"
      :loading="loading"
      highlight-hover-row
      highlight-current-row
      show-overflow="title"
      resizable
      auto-resize
      size="small"
      :data="tableData"
      :toolbar="{ refresh: { query: getAuditLog }}"
    >
      <vxe-table-column type="seq" title="序号" width="60"></vxe-table-column>
      <vxe-table-column
        title="节点名称"
        field="nodeName"
        width="140"
        align="left"
      >
      </vxe-table-column>
      <vxe-table-column
        title="审批状态"
        field="pass"
        width="100"
        align="left"
      >
        <template v-slot="{row}">
          <a-badge :color="statusColor[row.status]" :text="getStatusName(row.status)" />
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="参与者"
        field="auditor"
        align="left"
      >
        <template v-slot="{row}">
          {{ row.auditor ? row.auditor : '-' }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="审批意见"
        field="suggestion"
        align="left"
      >
      </vxe-table-column>
      <vxe-table-column
        title="审批时间"
        field="createTime"
        width="160"
        align="center"
      >
      </vxe-table-column>
    </vxe-grid>
    <template slot="footer">
      <a-button @click="closeModal">关闭</a-button>
    </template>
  </a-modal>
</template>

<script>
import { popupContainer } from '@/views/customer/modules/AuditProgress/modules/js/screenFullMountDom'
import { getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
export default {
  data () {
    return {
      visible: false,
      statusColor,
      loading: false,
      title: '',
      tableData: [],
      instanceId: undefined
    }
  },
  methods: {
    popupContainer,
    getStatusName,
    show (instanceId, title) {
      this.title = `审核日志${title}`
      this.tableData = []
      this.instanceId = instanceId
      this.visible = true
      this.getAuditLog()
    },
    closeModal () {
      this.visible = false
    },
    getAuditLog () {
      if (!this.instanceId) {
        return
      }
      this.loading = true
      this.$http.get('/rdFeeAudit/getAuditLog', { params: { instanceId: this.instanceId } }).then(res => {
        let tableData = []
        if (res.success && res.data) {
          tableData = res.data
        } else {
          this.$message.error(res.errorMessage || '获取审核日志失败')
        }
        this.tableData = tableData
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style>

</style>
