<template>
  <a-card :bordered="false">
    <a-spin
      tip="请稍后..."
      :spinning="spin"
    >
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input
            placeholder="请输入客户名称"
            v-model="queryParams.companyName"
          />
        </a-form-item>
        <a-form-item label="所属集团">
          <a-input
            placeholder="请输入所属集团"
            v-model="queryParams.groupName"
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
        <a-form-item label="客户级别">
          <a-select style="width:165px;" v-model="queryParams.companyLevel" placeholder="请选择客户级别" allowClear>
            <a-select-option v-for="item in companyLevelOptions" :key="item.value" :value="item.label">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <span
            class="table-page-search-submitButtons"
            v-if="$auth('project:projectProgress:search')"
          >
            <a-button
              type="primary"
              @click="search(true)"
            >查询</a-button>
          </span>
        </a-form-item>
      </a-form>
      <ystable
        :expand-config="{lazy: true, loadMethod: loadMethod,visibleMethod: rdDeptVisible}"
        :toolbar="{
          custom: true,
          zoom:true,
          refresh:true
        }"
        queryUrl="/projectProgress/getList"
        :params="getParams()"
        ref="table"
        @completed="({data:{footer}})=> footerData = footer"
      >
        <template v-slot:buttons>
          <div style="padding:8px 0;">
            <span>
              <a-button
                type="primary"
                v-if="$auth('customer:projectProgress:export')"
                @click="exportDataList()"
              >导出</a-button>
            </span>
            <b>
              查新报告数:
              <a>{{ footerData.reportCnt ? footerData.reportCnt : '--' }}</a>
            </b>
            <b class="pleft">
              专利申请数:
              <a>{{ footerData.patentApplyCnt ? footerData.patentApplyCnt : '--' }}</a>
            </b>
            <b class="pleft">
              规划立项数:
              <a>{{ footerData.cnt ? footerData.cnt : '--' }}</a>
            </b>
            <b class="pleft">
              项目名称:
              <a>{{ footerData.rdCount ? footerData.rdCount : '--' }}</a>
            </b>
            <b class="pleft">
              预算(万元):
              <a>{{ footerData.budget ? (footerData.budget / 10000).toFixed(2): '--' }}</a>
            </b>
            <b class="pleft">
              归集(万元):
              <a>{{ footerData.rdFunds ? (footerData.rdFunds / 10000).toFixed(2) : '--' }}</a>
            </b>
            <b class="pleft">
              过程材料数:
              <a>{{ footerData.docFileCount ? footerData.docFileCount : '--' }}</a>
            </b>
          </div>
        </template>

        <vxe-table-column
          type="seq"
          title="序号"
          width="50"
          fixed="left"
        ></vxe-table-column>
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
            <a
              @click="requestCustomer(row)"
              title="点击进入创新标准化管理系统"
              v-if="$auth('customer:my:jumpPage')"
            >{{ row.companyName }}</a>
            <span v-else>{{ row.companyName }}</span>
          </template>
        </vxe-table-column>

        <vxe-table-column
          title="所属集团"
          :width="240"
          fixed="left"
          field="groupName"
          show-header-overflow
          show-overflow="tooltip"
        >
          <template v-slot="{row}">
            {{ row.groupName || '--' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="级别"
          field="companyLevel"
          :width="72"
          fixed="left"
          align="center"
          remoteSort
          show-header-overflow
        >
          <template v-slot="{ row }">{{ row.companyLevel?row.companyLevel:'--' }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="待审/完成/提交"
          :width="140"
          fixed="left"
          align="right"
          v-if="$auth('customer:projectProgress:view')"
          remoteSort
          field="ongoingTotal">
          <template v-slot="{row}">
            <a
              style="margin-left:12px;"
              @click="showAuditDrawer(row,1,true)"
              title="点击进入审批"
            >
              <span style="color:red;" >{{ row.auditTotal ? row.auditTotal : '-' }}</span>/<span style="color:green;" >{{ row.doneTotal ? row.doneTotal : '-' }}</span>/<span style="color:blue;">{{ getAuditTotal(row) }}</span>
            </a>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="所属部门"
          field="deptName"
          :width="160"
          align="left"
          show-header-overflow
          show-overflow="tooltip"
        />
        <vxe-table-column
          title="项目年份"
          show-header-overflow
          field="year"
          :width="110"
          align="center"
          remoteSort
        />
        <vxe-table-column
          title="项目种类"
          field="typesStr"
          width="140"/>
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
          title="业务员"
          show-header-overflow
          field="owerName"
          :width="110"
          align="left"
        />
        <!-- <vxe-table-column
          title="研发架构"
          type="expand"
          :width="80"
          v-if="$auth('project:projectProgress:rdOrg')"
        >
          <template v-slot="{ row }">
            <span>{{ row.rdDeptLevel?'' : '无' }}</span>
          </template>
          <template v-slot:content="{ row }">
            <div style="height: 250px; position: relative; overflow: auto;">
              <img v-if="row.orgPath" style="position: absolute; left: 20px ;z-index: 2;" :src="`${row.orgPath}?v=${now}`" alt="研发组织架构"/>
              <a-alert v-else message="组织架构图片未生成,请到企业端研发组织架构界面生成组织架构图片" type="info" show-icon style="color: red; margin: 15px  0px; position: absolute; left: 20px; z-index: 2;"/>
            </div>
          </template>
        </vxe-table-column> -->
        <vxe-table-column
          title="规划立项数"
          show-header-overflow
          field="cnt"
          :width="120"
          align="right"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="项目名称"
          show-header-overflow
          field="rdCount"
          :width="100"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <!-- <a
              v-if="$auth('project:projectProgress:rdDetail')"
              @click="$refs.rsProjectListModal.show(row)"
            >{{ row.rdCount }}</a> -->
            <a
              v-if="$auth('project:projectProgress:rdDetail')"
              @click="showAuditDrawer(row,4)"
            >{{ row.rdCount }}</a>
            <span v-else>{{ row.rdCount }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="立项报告"
          field="reportNum"
          :width="110"
          align="center"
        >
          <template v-slot="{row}">
            <a v-if="$auth('customer:projectProgress:preview')" @click="showAuditDrawer(row,5)">{{ row.reportNum }}</a>
            <span v-else>{{ row.reportNum }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="过程材料数"
          show-header-overflow
          field="docFileCount"
          :width="120"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <a
              v-if="$auth('customer:projectProgress:preview')"
              title="预览过程文件"
              @click="showAuditDrawer(row,6)"
            >{{ row.docFileCount }}</a>
            <!-- <a
              v-if="$auth('customer:projectProgress:preview')"
              title="预览过程文件"
              @click="showPreview(row.year,row.companyId)"
            >{{ row.docFileCount }}</a> -->
            <span v-else>{{ row.docFileCount }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="延续上年立项数"
          field="lastRdCnt"
          width="140"
          :formatter="formatterCell"
          align="right"
          remoteSort/>
        <vxe-table-column
          title="专利申请数"
          field="patentApplyCnt"
          align="right"
          :formatter="formatterCell"
          width="120"
          remoteSort/>
        <vxe-table-column
          title="体系文档数"
          field="buildCnt"
          align="right"
          :formatter="formatterCell"
          width="120"
          remoteSort/>
        <vxe-table-column
          title="人数"
          show-header-overflow
          field="employeeCount"
          :width="120"
          align="right"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="研发人数"
          show-header-overflow
          field="rdEmployeeCount"
          :width="120"
          align="right"
          remoteSort
        >
          <template v-slot="{row}">
            <a v-if="$auth('customer:projectProgress:view')" @click="showAuditDrawer(row,2)">{{ row.rdEmployeeCount }}</a>
            <span v-else>{{ row.rdEmployeeCount }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          show-header-overflow
          field="employeeOpenidCnt"
          :width="100"
          align="right"
          title="已绑人数"
          remoteSort
        >
          <template v-slot:header><div title="研发考勤绑定人数">已绑人数</div></template>
          <template v-slot="{row}">
            <a v-if="$auth('customer:projectProgress:view')" @click="showAuditDrawer(row,2)">{{ row.employeeOpenidCnt }}</a>
            <span v-else>{{ row.employeeOpenidCnt }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="设备数"
          show-header-overflow
          field="equipmentCount"
          :width="120"
          align="right"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="研发设备数"
          show-header-overflow
          field="rdEquipmentCount"
          :width="120"
          align="right"
          remoteSort
        >
          <template v-slot="{row}">
            <a v-if="$auth('customer:projectProgress:view')" @click="showAuditDrawer(row,3)">{{ row.rdEquipmentCount }}</a>
            <span v-else>{{ row.rdEquipmentCount }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="预算(万元)"
          show-header-overflow
          field="budget"
          :width="120"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <a
              v-if="$auth('project:projectProgress:rdDetail')"
              @click="$refs.rsProjectListModal.show(row)"
            >{{ row.budget? (row.budget).toFixed(2): '' }}</a>
            <span v-else>{{ row.budget? (row.budget).toFixed(2): '' }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="归集(万元)"
          show-header-overflow
          field="rdFunds"
          :width="140"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <a @click="$refs.rsSummaryReportModal.show(row)">{{ row.rdFunds? (row.rdFunds/10000).toFixed(2): '' }}</a>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="操作人"
          field="operationRealName"
          :width="110"
          align="left"
        ></vxe-table-column>
        <vxe-table-column
          title="操作时间"
          show-header-overflow
          field="operationTime"
          :width="160"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <a @click="$refs.operationTime.open({ year:row.year, companyId:row.companyId }, `[${row.companyName}] - [${row.year}年]` )">{{ row.operationTime }}</a>
          </template>
        </vxe-table-column></ystable>
    </a-spin>
    <rs-project-list-modal ref="rsProjectListModal" />
    <rs-summary-report-modal ref="rsSummaryReportModal" />
    <audit-drawer-tabs ref="auditDrawerTab" :search="search"/>
    <operation-time-modal ref="operationTime" />
  </a-card>
</template>
<script>
import { SearchSelect, YearSelect, DeptSelect } from '@/components/Selects'
import RsProjectListModal from './modules/RsProjectListModal'
import RsSummaryReportModal from './modules/RsSummaryReportModal'
import ystable from '@/components/Table/ystable'
import AuditDrawerTabs from './modules/AuditProgress/AuditDrawerTabs'
import OperationTimeModal from './modules/OperationTimeModal'
import { now } from 'lodash'
import { mapState } from 'vuex'

export default {
  components: {
    SearchSelect,
    YearSelect,
    DeptSelect,
    RsProjectListModal,
    RsSummaryReportModal,
    ystable,
    AuditDrawerTabs,
    OperationTimeModal
  },
  data () {
    return {
      footerData: {},
      spin: false,
      queryParams: {},
      now: now()
    }
  },
  computed: {
    ...mapState({
      companyLevelOptions: state => state.enums.companyLevelOptions
    })
  },
  methods: {
    getAuditTotal (row) {
      let total = 0
      if (row.doneTotal) {
        total = row.doneTotal
      }
      if (row.ongoingTotal) {
        total = Number(total) + Number(row.ongoingTotal)
      }
      if (total) {
        return total
      }
      return '-'
    },
    rdDeptVisible ({ row }) {
      return Boolean(row.rdDeptLevel)
    },
    getParams () {
      const params = { companyName: this.queryParams.companyName, groupName: this.queryParams.groupName, deptId: this.queryParams.deptId, year: this.queryParams.year, sign: 1, companyLevel: this.queryParams.companyLevel }
      if (this.queryParams.tIds) { params.tIds = this.queryParams.tIds.map(a => a.id) }
      if (this.queryParams.fIds) { params.fIds = this.queryParams.fIds.map(a => a.id) }
      return params
    },
    search (refresh) {
      setTimeout(() => { this.$refs.table.refresh(refresh) }, 200)
    },
    requestCustomer (record) {
      this.$companyJump(this, { id: record.customerId, companyId: record.companyId })
    },
    exportDataList () {
      this.spin = true
      this.$exportData('/projectProgress/export', this.getParams(), `项目进度列表.xls`, this.$message).then(res => {
        this.spin = false
      })
    },
    formatterCell ({ cellValue }) {
      return cellValue || '-'
    },
    loadMethod ({ row }) {
      row.orgPath = undefined
      return new Promise(resolve => {
        this.$http.get('/projectProgress/getOrgPath', { params: { year: row.year, companyId: row.companyId } })
          .then(res => {
            if (res.success) {
              if (res.data && res.data.length > 0) {
                row.orgPath = res.data
              }
            } else {
              this.$message.error(`获取[${row.companyName}][${row.year}年]研发架构失败，请联系管理员。`)
            }
            resolve()
          })
      })
    },
    // 显示审批抽屉页面
    showAuditDrawer (record, activeKey) {
      this.$refs.auditDrawerTab.show(record, activeKey)
    }
  }
}
</script>

<style>
.pleft {
  padding-left: 20px;
}
</style>
