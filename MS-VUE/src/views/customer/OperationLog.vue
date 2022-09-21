<!--
 * @Author: zdf
 * @Date: 2021-10-25 14:15:58
 * @LastEditTime: 2022-01-20 09:37:30
 * @LastEditors: zdf
 * @Description: 操作日志
 * @FilePath: \MS-VUE\src\views\customer\OperationLog.vue
-->
<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="姓名">
        <a-input placeholder="请输入姓名" v-model="queryParams.realName" />
      </a-form-item>
      <a-form-item label="所属部门">
        <dept-select-only ref="deptSelect" @deptFullPath="path => (queryParams.fullPath = path)" style="width:165px;" />
      </a-form-item>
      <a-form-item label="操作日期">
        <a-date-picker
          v-model="queryParams.beginDate"
          placeholder="开始日期"
          format="YYYY-MM-DD"
          :disabledDate="disabledBeginDate"
          @openChange="handleStartOpenChange"
        />-
        <a-date-picker
          v-model="queryParams.endDate"
          placeholder="结束日期"
          format="YYYY-MM-DD"
          :disabledDate="disabledEndDate"
          :open="endOpen"
          @openChange="handleEndOpenChange"
        />
      </a-form-item>
      <a-form-item v-if="$auth('customer:operationLog:search')">
        <a-button type="primary" @click="search">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      :params="queryParams"
      highlight-current-row
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      queryUrl="/sysLog/getOperationLogUserList"
      :toolbar="{custom: true,zoom:true,refresh:true}"
    >
      <template v-slot:buttons><span>
        <a-button style="margin-right:8px;" @click="clickDay(7)">近7天</a-button>
        <a-button style="margin-right:8px;" @click="clickDay(15)">近15天</a-button>
        <a-button style="margin-right:8px;" @click="clickDay(30)">近30天</a-button>
        <a-button style="margin-right:8px;" @click="syncLog()" :loading="syncLoading" type="primary">同步</a-button>
      </span></template>
      <vxe-table-column type="seq" width="50" title="序号"/>
      <vxe-table-column
        title="姓名"
        field="realName"
        width="200"
        showHeaderOverflow
        showOverflow
        remoteSort/>
      <vxe-table-column
        title="电话"
        field="tel"
        width="160"
        showHeaderOverflow
        showOverflow
        remoteSort/>
      <vxe-table-column
        title="所属部门"
        field="deptName"
        width="260"
        showHeaderOverflow
        showOverflow
        remoteSort
      />
      <vxe-table-column
        title="职位"
        field="position"
        width="180"
        showHeaderOverflow
        remoteSort
        showOverflow
      />
      <vxe-table-column
        title="操作次数"
        field="operationCnt"
        width="100"
        align="right"
        showHeaderOverflow
        sortable
        showOverflow
      />
      <vxe-table-column
        title="最后操作时间"
        field="rsLastOperationTime"
        width="160"
        align="center"
        showHeaderOverflow
        remoteSort
        showOverflow
      />
      <vxe-table-column
        title="操作"
        width="120"
        showHeaderOverflow
        showOverflow
        v-if="$auth('customer:operationLog:viewLog')"
        align="center"
      >
        <template v-slot="{row}">
          <a @click="$refs.operationLog.show(row.id,row.realName)">查看日志</a>
        </template>
      </vxe-table-column>
    </ystable>
    <operation-log-modal ref="operationLog"/>
  </a-card>
</template>

<script>
import moment from 'moment'
import OperationLogModal from './modules/OperationLogModal'
import DeptSelectOnly from '@/components/DeptSelectOnly'
import ystable from '@/components/Table/ystable'
export default {
  components: {
    DeptSelectOnly,
    ystable,
    OperationLogModal
  },
  data () {
    return {
      queryParams: { beginDate: undefined, endDate: undefined },
      endOpen: false,
      syncLoading: false
    }
  },
  methods: {
    moment,
    clickDay (day) {
      if (day) {
        this.queryParams.beginDate = this.moment().subtract(day, 'days')
        this.queryParams.endDate = this.moment()
      }
      this.search()
    },
    search () {
      this.$refs.table.refresh(true)
    },
    syncLog () {
      this.syncLoading = true
      this.$http.post('/sysLog/syncLog').then(res => {
        if (res.success && res.data) {
          this.$message.success('同步成功')
          setTimeout(() => {
            this.search()
          }, 500)
        } else {
          this.$message.error(res.errorMessage || '同步失败')
        }
        this.syncLoading = false
      })
    },
    disabledBeginDate (beginDate) {
      const endValue = this.queryParams.endDate
      if (!beginDate || !endValue) {
        return false
      }
      return beginDate.valueOf() > endValue.valueOf()
    },
    disabledEndDate (endDate) {
      const beginDate = this.queryParams.beginDate
      if (!endDate || !beginDate) {
        return false
      }
      return beginDate.valueOf() >= endDate.valueOf()
    },
    handleStartOpenChange (open) {
      if (!open) {
        this.endOpen = true
      }
    },
    handleEndOpenChange (open) {
      this.endOpen = open
    }
  }
}
</script>

<style>

</style>
