<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spin">
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input placeholder="请输入客户名称" v-model="queryParams.companyName" />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select style="width:165px;" v-model="queryParams.deptId" />
        </a-form-item>
        <a-form-item label="项目年份">
          <year-select style="width:120px;" v-model="queryParams.year" placeholder="项目年份" />
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
          <span class="table-page-search-submitButtons" v-if="control.search">
            <a-button type="primary" @click="search(true)">查询</a-button>
          </span>
        </a-form-item>
      </a-form>
      <ystable
        :toolbar="{
          custom: true,
          zoom: true,
          refresh: true
        }"
        queryUrl="/rdFeeAudit/getList"
        :params="getParams()"
        ref="table"
      >
        <vxe-table-column type="seq" title="序号" width="50" fixed="left"></vxe-table-column>
        <vxe-table-column
          title="客户名称"
          show-overflow="tooltip"
          field="companyName"
          :width="240"
          fixed="left"
          remoteSort
          show-header-overflow
        >
          <template v-slot="{ row }">
            <a style="padding-right:3px;" @click="requestCustomer(row)" title="点击进入创新标准化管理系统" v-if="control.jumpPage">
              <a-icon type="right-circle" />
            </a>
            <span>{{ row.companyName }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="项目年份"
          fixed="left"
          show-header-overflow
          field="year"
          :width="110"
          align="center"
          remoteSort />
        <vxe-table-column
          title="所属部门"
          field="deptName"
          :width="160"
          align="left"
          show-header-overflow
          show-overflow="tooltip"
        />
        <vxe-table-column
          title="待审/完成/提交"
          :width="140"
          fixed="left"
          align="right"
          remoteSort
          field="ongoingCnt">
          <template v-slot="{row}">
            <a
              v-if="$auth('innovation:rdFeeAudit:detail')"
              title="点击查看详细信息"
              @click="showAuditDrawer1(row)">
              <span style="color:red;" >{{ row.auditCnt ? row.auditCnt : '-' }}</span>/<span style="color:green;" >{{ row.doneCnt ? row.doneCnt : '-' }}</span>/<span style="color:blue;">{{ row.ongoingCnt ? row.ongoingCnt : '-' }}</span>
            </a>
          </template>
        </vxe-table-column>
        <vxe-table-column title="业务员" show-header-overflow field="owerName" :width="130" align="left" />
        <vxe-table-column title="技术人员" show-header-overflow field="techRealName" :width="130" align="left" />
        <vxe-table-column title="财务人员" show-header-overflow field="financeRealName" :width="130" align="left" />
        <vxe-table-column
          :title="item"
          v-for="(item, index) in months"
          :key="index"
          show-header-overflow
          field="January"
          :width="130"
          align="right"
        >
          <template v-slot="{ row }">
            <!-- <a
              style="margin-left:12px;"
control.detail
              title="点击查看详细信息"
 v-if="control.detail"
            >
              {{ row.mfam[`m${i}`] }}
            </a> -->
            <a-badge
              v-if="control.detail"
              :offset="[10,-2]"
              :count="row.monthFundMap[`${index}`]? row.monthFundMap[`${index}`].auditCnt: 0"
              :overflow-count="99">
              <a style="margin-left:12px;" title="点击查看详细信息" @click="showAuditDrawer(row, index)">
                <template v-if="row.monthFundMap[`${index}`]">{{ toBit(row.monthFundMap[`${index}`].amount) }}</template><template v-else>--</template>
              </a>
            </a-badge>
            <div v-else><template v-if="row.monthFundMap[`${index}`]">{{ toBit(row.monthFundMap[`${index}`].amount) }}</template><template v-else>--</template></div>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="合计"
          show-header-overflow
          field="rdFunds"
          :width="140"
          align="right"
          remoteSort>
          <template v-slot="{row}">{{ toBit(row.rdFunds) }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="更新时间"
          show-header-overflow
          field="operationTime"
          :width="150"
          align="center"
          remoteSort/>
      </ystable>
    </a-spin>
    <rd-fee-audit-drawer ref="auditDrawerTab" @ok="search()"/>
    <rd-fee-audit-drawer-new ref="auditDrawer" :params="auditParams" :visible.sync="auditVisible" @ok="search()" :months="months" />
  </a-card>
</template>
<script>

import { toBit } from './modules/auditModules/FeeDetailConfig'
import moment from 'moment'
import RdFeeAuditDrawer from './modules/RdFeeAuditDrawer'
import RdFeeAuditDrawerNew from './modules/RdFeeAuditDrawerNew'
import { SearchSelect, YearSelect, DeptSelect } from '@/components/Selects'
import ystable from '@/components/Table/ystable'
const months = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
export default {
  components: {
    RdFeeAuditDrawer,
    SearchSelect,
    YearSelect,
    DeptSelect,
    RdFeeAuditDrawerNew,
    ystable
  },
  data () {
    return {
      months,
      spin: false,
      queryParams: {},
      control: {
        search: this.$auth('innovation:rdFeeAudit:search'),
        detail: this.$auth('innovation:rdFeeAudit:detail'),
        jumpPage: this.$auth('innovation:rdFeeAudit:jumpPage')
      },
      auditVisible: false,
      auditParams: null
    }
  },
  computed: {
  },
  methods: {
    moment,
    toBit,
    getParams () {
      const params = {
        companyName: this.queryParams.companyName,
        deptId: this.queryParams.deptId,
        year: this.queryParams.year
      }
      if (this.queryParams.tIds) {
        params.tIds = this.queryParams.tIds.map(a => a.id)
      }
      if (this.queryParams.fIds) {
        params.fIds = this.queryParams.fIds.map(a => a.id)
      }
      return params
    },
    search (refresh) {
      setTimeout(() => {
        this.$refs.table.refresh(refresh)
      }, 200)
    },
    requestCustomer (record) {
      this.$companyJump(this, { id: record.customerId, companyId: record.companyId })
    },
    // 显示审批抽屉页面
    showAuditDrawer (row, index) {
      this.$refs.auditDrawerTab.show({ m: months[index], ...row, month: moment([row.year, index, 1, 0, 0, 0, 0]) })
    },
    showAuditDrawer1 (row) {
      this.auditParams = row
      this.auditVisible = true
    }
  }
}
</script>

<style>
.pleft {
  padding-left: 20px;
}
</style>
