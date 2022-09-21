<!--
 * @Author: zdf
 * @Date: 2021-07-23 13:45:37
 * @LastEditTime: 2021-07-27 17:31:35
 * @LastEditors: zdf
 * @Description: 项目列表
 * @FilePath: \MS-VUE\src\views\innovation\InnovationProject.vue
-->
<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input
          placeholder="请输入客户名称"
          @pressEnter="search(true)"
          v-model="queryParams.companyName"
        />
      </a-form-item>
      <a-form-item label="所属部门">
        <dept-select
          style="width:165px;"
          v-model="queryParams.deptId"
        />
      </a-form-item>
      <a-form-item label="项目年份">
        <year-select
          style="width:120px;"
          v-model="queryParams.year"
          placeholder="项目年份"
        />
      </a-form-item>
      <a-form-item label="技术人员">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="true"
          placeholder="技术人员"
          style="width:165px;"
          v-model="queryParams.tIds"
        />
      </a-form-item>
      <a-form-item label="财务人员">
        <search-select
          style="width:165px;"
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="true"
          placeholder="财务人员"
          v-model="queryParams.fIds"
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
    <ystable
      ref="table"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      show-footer
      @completed="completed"
      :footer-method="footerMethod"
      :params="getParams()"
      queryUrl="/innovationProject/getList"
      :toolbar="{custom: true,zoom:true,refresh:true}"
    >
      <template #buttons>
        <span
          style="margin-right:10px;"
          v-if="control.relateTech"
        >
          <a-button
            type="primary"
            :disabled="!selectRowIds.length"
            @click="$refs.setStaff.showModal(selectRowIds,1)"
          >分配技术人员</a-button>
        </span>
        <span
          style="margin-right:10px;"
          v-if="control.relateFina"
        >
          <a-button
            type="primary"
            :disabled="!selectRowIds.length"
            @click="$refs.setStaff.showModal(selectRowIds,2)"
          >分配财务人员</a-button>
        </span>
      </template>
      <vxe-table-column
        type="checkbox"
        width="40"
        fixed="left"
        align="center"
      />
      <vxe-table-column
        type="seq"
        title="序号"
        width="50"
        fixed="left"
      />
      <vxe-table-column
        title="客户名称"
        field="companyName"
        width="220"
        fixed="left"
        remoteSort
      />
      <vxe-table-column
        title="年份"
        field="year"
        width="100"
        align="center"
        remoteSort
      />
      <vxe-table-column
        title="所属部门"
        field="deptName"
        width="160"
        remoteSort
      />
      <vxe-table-column
        title="业务员"
        show-header-overflow
        field="owerName"
        :width="110"
        align="left"
      />
      <vxe-table-column
        title="技术人员"
        show-header-overflow
        field="techRealName"
        :width="110"
        align="left"
      />
      <vxe-table-column
        title="财务人员"
        show-header-overflow
        field="financeRealName"
        :width="110"
        align="left"
      />
      <vxe-table-column
        title="规划立项数"
        field="cnt"
        width="120"
        :formatter="formatterCell"
        remoteSort
        align="right"
      />
      <vxe-table-column
        title="实际立项数"
        field="rdCount"
        width="120"
        align="right"
        :formatter="formatterCell"
        remoteSort
      >
        <template v-slot="{row}">
          {{ `${row.rdCount || '-'}/${row.reportDoneCnt || '-'}` }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="延续上年立项数"
        field="lastRdCnt"
        width="140"
        :formatter="formatterCell"
        align="right"
        remoteSort
      />
      <vxe-table-column
        title="专利申请数"
        field="patentApplyCnt"
        align="right"
        :formatter="formatterCell"
        width="120"
        remoteSort
      />
      <vxe-table-column
        title="体系文档数"
        field="buildCnt"
        align="right"
        :formatter="formatterCell"
        width="120"
        remoteSort
      />
      <vxe-table-column
        title="项目种类"
        field="typesStr"
        width="140"
      />
      <vxe-table-column
        title="操作时间"
        field="operationTime"
        width="150"
        align="center"
        remoteSort
      >
        <template v-slot="{row}">
          <a @click="$refs.operationTime.open({ year:row.year, companyId:row.companyId }, `[${row.companyName}] - [${row.year}年]` )">{{ row.operationTime
          }}</a>
        </template>
      </vxe-table-column>
    </ystable>
    <operation-time-modal ref="operationTime" />
    <set-staff-modal
      ref="setStaff"
      @ok="search"
    />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { DeptSelect, SearchSelect, YearSelect } from '@/components/Selects'
import OperationTimeModal from '@/views/customer/modules/OperationTimeModal'
import SetStaffModal from './modules/SetStaffModal'

export default {
  name: 'InnovationProject',
  components: {
    ystable,
    OperationTimeModal,
    SearchSelect,
    YearSelect,
    DeptSelect,
    SetStaffModal
  },
  data () {
    return {
      selectRowIds: [],
      queryParams: {},
      footer: {},
      control: {
        search: this.$auth('innovation:innovationProject:search'),
        relateTech: this.$auth('innovation:innovationProject:relateTech'),
        relateFina: this.$auth('innovation:innovationProject:relateFina')
      }
    }
  },
  methods: {
    completed (data) {
      this.selectRowIds = []
      if (data.data.footer) {
        this.footer = data.data.footer
      } else {
        this.footer = {}
      }
    },
    formatterCell ({ cellValue }) {
      return cellValue || '-'
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    getParams () {
      const params = {
        companyName: this.queryParams.companyName,
        deptId: this.queryParams.deptId,
        year: this.queryParams.year,
        sign: 1
      }
      if (this.queryParams.tIds) { params.tIds = this.queryParams.tIds.map(a => a.id) }
      if (this.queryParams.fIds) { params.fIds = this.queryParams.fIds.map(a => a.id) }
      return params
    },
    selectChange ({ records }) {
      this.selectRowIds = records.map(item => item.id)
    },
    footerMethod ({ columns, data }) {
      return [
        columns.map((a, index) => {
          if (index === 0) {
            return ''
          }
          if (index === 1) {
            return '合计'
          }
          if (index === 9) {
            return `${this.footer.rdCount || '-'}/${this.footer.reportDoneCnt || '-'}`
          }
          return this.footer[a.property] || '-'
        })
      ]
    }
  }
}
</script>

<style>

</style>
