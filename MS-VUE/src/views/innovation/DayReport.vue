<!--
 * @Author: zdf
 * @Date: 2021-08-06 16:04:54
 * @LastEditTime: 2021-09-23 15:43:01
 * @LastEditors: zdf
 * @Description: 日工作报表
 * @FilePath: \MS-VUE\src\views\innovation\DayReport.vue
-->
<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input
          placeholder="请输入客户名称"
          v-model="queryParams.companyName"
        />
      </a-form-item>
      <a-form-item label="日期">
        <a-date-picker
          v-model="queryParams.workDate"
          placeholder="请选择日期"
          format="YYYY-MM-DD"
        />
      </a-form-item>
      <a-form-item label="所属部门">
        <dept-select style="width:165px;" v-model="queryParams.deptId" />
      </a-form-item>
      <a-form-item label="技术人员">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="true"
          placeholder="技术人员"
          style="width:165px;"
          v-model="queryParams.userId"
        />
      </a-form-item>
      <a-form-item>
        <span
          class="table-page-search-submitButtons"
        >
          <a-button
            v-if="control.search"
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </span>
      </a-form-item>
    </a-form>
    <div>
      <ystable
        ref="table"
        queryUrl="/dailyReport/getDayList"
        :params="getParams()"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        border
        :toolbar="{
          custom: true,
          zoom:true,
          refresh:true
        }"
      >
        <vxe-table-column
          title="日期"
          field="workDate"
          width="120"
          align="center"
          header-align="center"
          fixed="left"
          remoteSort>
          <template v-slot="{row}">
            {{ moment(row.workDate).format('YYYY-MM-DD') }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="技术人员"
          field="realName"
          fixed="left"
          width="120"
          align="left"
          header-align="center"
          remoteSort/>
        <vxe-table-column
          title="客户名称"
          field="companyName"
          fixed="left"
          width="260"
          align="left"
          header-align="center"
          remoteSort/>
        <vxe-table-column
          v-for="(t,index) in cols"
          :key="index"
          :title="t.title"
          header-align="center">
          <vxe-table-column
            title="提交"
            :field="`${t.col}Commit`"
            width="80"
            remoteSort
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
          <vxe-table-column
            remoteSort
            title="驳回"
            :field="`${t.col}Reject`"
            width="80"
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
          <vxe-table-column
            title="通过"
            remoteSort
            :field="`${t.col}Done`"
            width="80"
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
        </vxe-table-column>
        <vxe-table-column
          title="合计"
          header-align="center">
          <vxe-table-column
            title="提交"
            remoteSort
            field="commit"
            width="80"
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
          <vxe-table-column
            title="驳回"
            field="reject"
            remoteSort
            width="80"
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
          <vxe-table-column
            title="通过"
            field="done"
            width="80"
            remoteSort
            align="right"
            header-align="center"
            :formatter="formatterCell"
          >
          </vxe-table-column>
        </vxe-table-column>
      </ystable>
    </div>
  </a-card>
</template>

<script>
import { SearchSelect, DeptSelect } from '@/components/Selects'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
export default {
  components: {
    ystable,
    SearchSelect,
    DeptSelect
  },
  data () {
    return {
      control: {
        search: this.$auth('dailyReport:dayReport:search')
      },
      cols: [],
      queryParams: {}
    }
  },
  created () {
    this.$getDailyReportCols().then(res => {
      this.cols = res
    })
  },
  methods: {
    moment,
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    formatterCell ({ cellValue }) {
      return cellValue || '-'
    },
    getParams () {
      const params = { companyName: this.queryParams.companyName,
        workDate: this.queryParams.workDate ? moment(this.queryParams.workDate).format('YYYY-MM-DD 00:00:00') : undefined,
        userId: this.queryParams.userId,
        deptId: this.queryParams.deptId }
      if (Array.isArray(params.userId) && params.userId.length) {
        params.userIds = params.userId.map(a => a.id)
      }
      return params
    }
  }
}
</script>

<style>

</style>
