<!--
 * @Author: zdf
 * @Date: 2021-08-09 09:12:38
 * @LastEditTime: 2021-09-23 15:43:11
 * @LastEditors: zdf
 * @Description: 月工作报表
 * @FilePath: \MS-VUE\src\views\innovation\MonthReport.vue
-->
<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="月份">
        <a-month-picker
          v-model="queryParams.month"
          placeholder="请选择月份"
          format="YYYY-MM"
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
        queryUrl="/monthlyReport/getMonthList"
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
          title="月份"
          field="workMonth"
          width="120"
          align="center"
          header-align="center"
          fixed="left"
          remoteSort>
          <template v-slot="{row}">
            {{ moment(row.workMonth).format('YYYY-MM') }}
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
          title="客户数"
          field="companyCnt"
          fixed="left"
          width="90"
          align="left"
          :formatter="formatterCell"
          header-align="center"
          remoteSort/>
        <vxe-table-column
          title="服务次数"
          field="serviceCnt"
          width="100"
          align="right"
          header-align="center"
          :formatter="formatterCell"
          remoteSort
        >
        </vxe-table-column>
        <vxe-table-column
          v-for="(t,index) in cols"
          :key="index"
          :title="t.title"
          header-align="center">
          <vxe-table-column
            title="提交"
            :field="`${t.col}Commit`"
            width="80"
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
          <vxe-table-column
            title="驳回"
            :field="`${t.col}Reject`"
            width="80"
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
          <vxe-table-column
            title="通过"
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
            field="commit"
            width="80"
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
          <vxe-table-column
            title="驳回"
            field="reject"
            width="80"
            align="right"
            header-align="center"
            :formatter="formatterCell">
          </vxe-table-column>
          <vxe-table-column
            title="通过"
            field="done"
            width="80"
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
        search: this.$auth('dailyReport:monthReport:search')
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
        month: this.queryParams.month ? moment(this.queryParams.month).format('YYYY-MM-01 00:00:00') : undefined,
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
