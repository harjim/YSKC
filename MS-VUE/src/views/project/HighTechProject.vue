
<template>
  <a-card :bordered="false">
    <a-spin
      tip="请稍后..."
      :spinning="spin"
    >
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input
            style="width:160px;"
            v-model="queryParams.companyName"
            placeholder="项目名称"
          />
        </a-form-item>
        <a-form-item label="项目类型">
          <a-input
            style="width:160px;"
            v-model="queryParams.productName"
            placeholder="项目类型"
          />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select-only
            ref="deptSelect"
            @deptFullPath="(path) => (queryParams.fullPath = path)"
            style="width: 160px"
          />
        </a-form-item>
        <a-form-item label="年份">
          <a-select
            style="width:160px;"
            v-model="queryParams.year"
            placeholder="项目年份"
            :allowClear="true"
          >
            <a-select-option v-for="y in years" :key="y">{{ y }}</a-select-option>
          </a-select>
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
        <a-form-item label="业务员">
          <search-select
            style="width: 160px"
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="业务员"
            v-model="queryParams.owerId"
          />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            v-if="$auth('project:highTechProject:search')"
            @click="refresh(false)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <ys-table
        ref="yst"
        :resizable="true"
        auto-resize
        highlight-hover-row
        show-overflow
        show-header-overflow
        :loading="loading"
        queryUrl="/projectSummary/getData"
        export-config
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        @completed="completed"
        :params="getParams()"
        :expand-config="{accordion: true}"
        :toolbar="{
          custom: true,
          zoom:true,
          refresh:true
        }"
      >
        <!-- <vxe-table-column
          type="checkbox"
          width="40"
        /> -->
        <vxe-table-column
          type="seq"
          title="序号"
          :width="70"
          align="center"
        ></vxe-table-column>
        <vxe-table-column
          type="expand"
          title="客户名称"
          field="companyName"
          remoteSort
          header-align="center"
          :min-width="120"
        >
          <template v-slot="{ row }">
            <span>{{ row.companyName }}</span>
          </template>
          <template v-slot:content="{ row }">
            <vxe-grid
              ref="subyst"
              auto-resize
              :resizable="true"
              highlight-hover-row
              show-overflow
              show-header-overflow
              border
              size="small"
              :data="row.summaryData"
              :expand-config="{lazy: true, loadMethod: loadMethod,visibleMethod: rdDeptVisible}"
            >
              <vxe-table-column
                title="项目年份"
                field="year"
                :width="120"
                align="center"
              ></vxe-table-column>
              <vxe-table-column
                title="研发架构"
                type="expand"
                :width="80"
                align="center"
                v-if="$auth('project:highTechProject:rdOrg')"
              >
                <template v-slot="{ row }">
                  <span>{{ row.rdDeptLevel?'' : '无' }}</span>
                </template>
                <template v-slot:content="{ row }">
                  <rdDept
                    :rdDepts="row.rdDepts"
                    style="position:relative;"
                  />
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="规划立项数"
                field="cnt"
                :width="120"
                align="center"
              >
                <template v-slot="{ row }">
                  <span>{{ row.cnt?row.cnt:'-' }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="技术立项"
                show-header-overflow
                field="rdCount"
                :width="100"
                align="center"
                remoteSort
              >
                <template v-slot="{ row }">
                  <a
                    v-if="$auth('project:highTechProject:rdDetail') && row.rdCount"
                    @click="$refs.rsProjectListModal.show(row)"
                  >{{ row.rdCount }}</a>
                  <span v-else>{{ row.rdCount?row.rdCount:'-' }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="人数"
                field="employeeCount"
                :width="120"
                align="center"
              >
                <template v-slot="{ row }">
                  <span>{{ row.employeeCount?row.employeeCount:'-' }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="研发人数"
                field="rdEmployeeCount"
                :width="120"
                align="center"
              >
                <template v-slot="{ row }">
                  <span>{{ row.rdEmployeeCount?row.rdEmployeeCount:'-' }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="设备数"
                field="equipmentCount"
                :width="120"
                align="center"
              >
                <template v-slot="{ row }">
                  <span>{{ row.equipmentCount?row.equipmentCount:'-' }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="研发设备数"
                field="rdEquipmentCount"
                :width="120"
                align="center"
              >
                <template v-slot="{ row }">
                  <span>{{ row.rdEquipmentCount?row.rdEquipmentCount:'-' }}</span>
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
                    v-if="$auth('project:highTechProject:rdDetail') && row.budget"
                    @click="$refs.rsProjectListModal.show(row)"
                  >{{ (row.budget/10000).toFixed(2) }}</a>
                  <span v-else>{{ row.budget? (row.budget/10000).toFixed(2): '-' }}</span>
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
                  <a @click="$refs.rsSummaryReportModal.show(row)" v-if=" $auth('project:highTechProject:rdDetail') && row.rdFunds">{{ (row.rdFunds/10000).toFixed(2) }}</a>
                  <span v-else>{{ row.budget? (row.budget/10000).toFixed(2): '-' }}</span>
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
                    v-if="$auth('project:highTechProject:preview') && row.docFileCount"
                    title="预览过程文件"
                    @click="showPreview(row.year,row.companyId)"
                  >{{ row.docFileCount }}</a>
                  <span v-else>{{ row.docFileCount?row.docFileCount:'-' }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="操作人"
                field="operationRealName"
                :min-width="120"
                align="center"
              >
                <template v-slot="{ row }">
                  <span>{{ row.operationRealName?row.operationRealName:'-' }}</span>
                </template>
              </vxe-table-column>
              <!-- <vxe-table-column
                title="操作时间"
                field="operationTime"
                :min-width="120"
                align="center"
              >
                <template v-slot="{ row }">
                  <span>{{ row.operationTime?row.operationTime:'-' }}</span>
                </template>
              </vxe-table-column> -->
              <vxe-table-column
                title="操作时间"
                field="operationTime"
                :width="160"
                align="center"
                remoteSort
              >
                <template v-slot="{ row }">
                  <span v-if="$auth('project:highTechProject:rdDetail') && row.operationTime">
                    <a @click="showDrawer({ year:row.year, companyId:row.companyId }, `[${row.companyName}] - [${row.year}年]` )">{{ row.operationTime?row.operationTime:'-' }}</a>
                  </span>
                  <span v-else>
                    {{ row.operationTime?row.operationTime:'-' }}
                  </span>
                </template>
              </vxe-table-column>
            </vxe-grid>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="所属部门"
          field="deptName"
          :width="160"
          align="left"
          show-header-overflow
          show-overflow="tooltip"
          remoteSort
        />
        <vxe-table-column
          title="项目类型"
          field="productName"
          :width="180"
          remoteSort
        >
        </vxe-table-column>
        <vxe-table-column
          title="年份"
          field="year"
          :width="100"
          remoteSort
          align="center"
        />
        <vxe-table-column
          title="技术人员"
          show-header-overflow
          field="techRealName"
          :width="150"
          align="left"
        />
        <vxe-table-column
          title="财务人员"
          show-header-overflow
          field="financeRealName"
          :width="150"
          align="left"
        />
        <vxe-table-column
          title="业务员"
          show-header-overflow
          field="owerName"
          :width="110"
          align="left"
        />
        <vxe-table-column
          title="创建时间"
          show-header-overflow
          field="createTime"
          remoteSort
          :width="160"
          align="center"
        />
      </ys-table>
    </a-spin>
    <a-modal
      :title="drawerTitle"
      :width="500"
      :visible="drawerVisible"
      :footer="null"
      @cancel="drawerVisible = false"
    >
      <vxe-table
        bordered
        ref="table2"
        :data="DrawerTableData"
      >
        <vxe-table-column
          title="阶段"
          field="stageName"
          :width="150"
          align="center"
          :formatter="formatterCell"
        />
        <vxe-table-column
          title="操作人"
          field="realName"
          :width="150"
          align="center"
          show-header-overflow
          :formatter="formatterCell"
        />
        <vxe-table-column
          title="操作时间"
          field="operationTime"
          :width="150"
          align="center"
          show-header-overflow
          :formatter="formatterCell"
        />
      </vxe-table>
    </a-modal>
    <rs-project-list-modal ref="rsProjectListModal" />
    <rs-summary-report-modal ref="rsSummaryReportModal" />
    <!-- <docPreview ref="docPreview" :isAuth="true"></docPreview> -->
  </a-card>
</template>
<script>
import RdDept from '../customer/modules/RdDept'
import ysTable from '@/components/Table/ystable'
import RsProjectListModal from '../customer/modules/RsProjectListModal'
import { YearSelect, SearchSelect, DeptSelect } from '@/components/Selects'
import RsSummaryReportModal from '../customer/modules/RsSummaryReportModal'
import { DeptSelectOnly } from '@/components'
const startYear = (new Date()).getFullYear() - 4
const endYear = (new Date()).getFullYear() + 6
const years = []
for (let y = startYear; y < endYear; y++) {
  years.unshift(y)
}
const row = [
  // { stageId: 10000, stageName: '基本信息' },
  { stageId: 10001, stageName: '花名册' },
  { stageId: 10002, stageName: '设备列表' },
  { stageId: 20000, stageName: '规划项目' },
  // { stageId: 30000, stageName: '立项准备' },
  { stageId: 30001, stageName: '研发架构' },
  { stageId: 30002, stageName: '研发设备' },
  { stageId: 30003, stageName: '研发人员' },
  // { stageId: 40000, stageName: '技术立项' },
  { stageId: 40001, stageName: '项目管理' },
  { stageId: 40002, stageName: '项目成员' },
  { stageId: 40003, stageName: '项目设备' },
  { stageId: 50000, stageName: '预算' },
  { stageId: 60000, stageName: '项目归集' },
  { stageId: 70000, stageName: '过程文档' }
]
export default {
  name: 'HighTechProject',
  components: {
    ysTable,
    YearSelect,
    DeptSelectOnly,
    SearchSelect,
    RdDept,
    DeptSelect,
    RsProjectListModal,
    RsSummaryReportModal
    // DocPreview
  },
  data () {
    return {
      spin: false,
      loading: false,
      data: [],
      queryParams: {},
      selectRowIds: [],
      selectedRows: [],
      form: this.$form.createForm(this),
      years,
      drawerVisible: false,
      drawerTitle: '',
      DrawerTableData: []
    }
  },
  methods: {
    rdDeptVisible ({ row }) {
      return Boolean(row.rdDeptLevel)
    },
    // 表格复选框事件
    selectChange ({ records }) {
      this.selectedRows = records
      this.selectRowIds = records.map(item => {
        return item.id
      })
    },
    formatterCell ({ cellValue }) {
      if (cellValue) {
        return cellValue
      }
      return '-'
    },
    getParams () {
      const params = {
        companyName: this.queryParams.companyName,
        productName: this.queryParams.productName,
        year: this.queryParams.year,
        fullPath: this.queryParams.fullPath
      }
      if (this.queryParams.tIds) params.tIds = this.queryParams.tIds.map(a => a.id)
      if (this.queryParams.fIds) params.fIds = this.queryParams.fIds.map(a => a.id)
      if (this.queryParams.owerId) params.owerId = this.queryParams.owerId.id
      return params
    },
    completed (res) {
      this.data = res.data.data
    },
    showDrawer (queryParam, title) {
      this.$http.get('/projectProgress/getProgressDetail', { params: queryParam }).then(
        res => {
          const data = res.data
          const stageObj = {}
          data.forEach(item => {
            stageObj[item.stage] = item
          })
          const newRow = row.map(rowItem => {
            const key = rowItem.stageId
            if (stageObj[key]) {
              rowItem.realName = stageObj[key].realName
              rowItem.operationTime = stageObj[key].operationTime
            } else {
              rowItem.realName = undefined
              rowItem.operationTime = undefined
            }
            return rowItem
          })
          this.DrawerTableData = newRow
          this.drawerVisible = true
          this.drawerTitle = title
        })
    },
    loadMethod ({ row }) {
      row.rdDepts = []
      return new Promise(resolve => {
        this.$http.get('/projectProgress/getRdDept', { params: { year: row.year, companyId: row.companyId } })
          .then(res => {
            if (res.success) {
              if (res.data && res.data.length > 0) {
                row.rdDepts = res.data
              }
            } else {
              this.$message.error(`获取[${row.companyName}][${row.year}年]研发架构失败，请联系管理员。`)
            }
            resolve()
          })
      })
    },
    showPreview (year, companyId) {
      this.$message.info('功能暂停使用')
      // this.$refs.docPreview.show(year, companyId)
    },
    refresh (isEdit, postData) {
      if (isEdit) {
        for (const row of this.data) {
          if (row.id === postData.id) {
            Object.assign(row, postData)
          }
        }
      } else {
        this.$refs.yst.refresh(true)
      }
      // this.$refs.yst.refresh(refresh)
    }
  }
}
</script>

<style lang="less" scpoed>
</style>
