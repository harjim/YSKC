<template>
  <a-card
    :bordered="false"
    style="height: 100%; overflow:auto"
    :bodyStyle="{ height: '100%', overflow: 'auto'}"
  >
    <a-form layout="inline">
      <select-project ref="selectProject" @projectSelect="projectSelected" :year="currentYear" :sign="2"/>
    </a-form>
    <div v-if="projectId">
      <div>
        <a-form layout="inline" v-if="$auth('project:rdSalaryAccounting:view')">
          <a-form-item label="月份">
            <a-select
              style="width:165px;"
              v-model="monthValue"
              placeholder="请选择月份"
              :options="monthOptions"
            ></a-select>
          </a-form-item>
          <!-- <a-form-item>
            <a-button type="primary" @click="query(true)" v-if="$auth('project:rdSalaryAccounting:view')">查询</a-button>
          </a-form-item> -->
        </a-form>
      </div>
      <ystable
        ref="table"
        rowId="enumber"
        queryUrl="/projectRdEmployee/getRdAccountingList"
        :params="queryParam"
        :checkbox-config="{checkMethod:canChecked,showHeader: showHeaderChk}"
        highlight-hover-row
        highlight-current-row
        show-header-overflow
        resizable
        auto-resize
        @checkbox-change="onSelectChange"
        @checkbox-all="onSelectChange"
        :toolbar="{ zoom: true, custom: true, refresh: true }"
        @completed="({ data }) => completed(data)"
        show-overflow="title"
      >
        <template slot="buttons">
          <template v-if="$auth('project:rdSalaryAccounting:agg')">
            <a-button
              type="primary"
              :disabled="!this.selectedRowKeys || this.selectedRowKeys.length <= 0"
              @click="accountingRdSalary"
            >
              核算选中人员
            </a-button>
            <a-button
              style="margin-left: 10px"
              type="primary"
              @click="accountingAllRdSalary"
            >
              核算所有人员
            </a-button>
          </template>
          <span style="padding-left: 10px">
            总计：
            <a style="font-weight: 600">{{ headerData.total ? headerData.total : '-' }}</a>
          </span>
          <span style="padding-left: 10px">
            工资：
            <a style="font-weight: 600">{{ headerData.pay ? headerData.pay : '-' }}</a>
          </span>
          <span style="padding-left: 10px">
            五险一金：
            <a style="font-weight: 600">{{ headerData.insuranceFund ? headerData.insuranceFund : '-' }}</a>
          </span>
        </template>
        <vxe-table-column
          type="checkbox"
          :width="40"
          fixed="left"
        />
        <vxe-table-column
          title="工号"
          field="enumber"
          :min-width="120"
          fixed="left"
          remoteSort
        />
        <vxe-table-column
          title="姓名"
          field="ename"
          :min-width="120"
          remoteSort
          fixed="left"
        />
        <vxe-table-column
          title="部门"
          field="deptName"
          :width="100"
          remoteSort
        />
        <vxe-table-column
          align="center"
          title="人员类型"
          field="etype"
          :width="100"
          remoteSort
        >
          <template slot-scope="{ row }">
            {{ row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : '' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="总工时"
          :width="100"
          field="workHours"
          remoteSort
        >
          <template slot-scope="{ row }">
            {{ row.workHours ? Number(row.workHours) : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="应发工资"
          :width="120"
          field="pay"
          remoteSort
        >
          <template slot-scope="{ row }">
            {{ row.pay ? row.pay : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="五险一金"
          :width="120"
          field="insuranceFund"
          remoteSort
        >
          <template slot-scope="{ row }">
            {{ row.insuranceFund ? row.insuranceFund : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="计划工时"
          :width="110"
          field="planTime"
          remoteSort
        >
          <template slot-scope="{ row }">
            {{ row.planTime ? row.planTime : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="研发工时"
          :width="110"
          field="attendanceHour"
        >
          <template slot-scope="{ row }">
            {{ row.attendanceHour ? row.attendanceHour : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="已核算工时"
          :width="114"
          field="rdHour"
          remoteSort
        > <template slot-scope="{ row }">
          {{ row.rdHour ? row.rdHour : '-' }}
        </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="研发工资"
          :width="120"
          field="rdPay"
          remoteSort
        >
          <template slot-scope="{ row }">
            <span>{{ row.rdPay ? row.rdPay : '-' }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="研发五险一金"
          :width="130"
          field="rdInsuranceFund"
          remoteSort
        >
          <template slot-scope="{ row }">
            <span>{{ row.rdInsuranceFund ? row.rdInsuranceFund : '-' }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="研发占比"
          :width="100"
          field="rdRatio"
        >
          <template slot-scope="{ row }">
            <span v-if="row.rdHour && !isNaN(row.rdHour) && row.workHours && Number(row.workHours) !== 0">
              {{ toBit(row.rdHour / row.workHours, 6) }}
            </span>
            <span v-else>-</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="right"
          title="研发合计"
          :width="100"
          field="total"
        >
          <template slot-scope="{ row }">
            <span>{{ loadTotal(row) }}</span>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
  </a-card>
</template>
<script>
import SelectProject from '@/components/SelectProject'
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
function toBit (v, bit) {
  let b = 2
  if (bit || bit >= 0) {
    b = bit
  }
  let div = 1
  for (let i = 0; i < b; i++) {
    div = div * 10
  }

  // 保留双倍小数位，确保四舍五入不会丢失精度
  v = Number(v).toFixed(b * 2)
  return (Math.round(v * div) / div).toFixed(b)
}
export default {
  components: {
    SelectProject,
    ystable
  },
  mixins: [yearMiXin],
  data () {
    return {
      first: true,
      selectedRowKeys: [],
      queryParam: {},
      headerData: {},
      projectId: undefined,
      monthValue: '0',
      showHeaderChk: true
    }
  },
  created () {
    this.queryParam.year = this.currentYear
    this.queryParam.month = moment([this.currentYear, parseInt(this.monthValue), 1, 0, 0, 0, 0])
  },
  watch: {
    monthValue (m) {
      this.monthValue = m
      this.query(true)
    }
  },
  methods: {
    toBit,
    moment,
    loadTotal (row) {
      if (!row.rdPay || !row.rdInsuranceFund) {
        return '-'
      }
      return toBit(Number(row.rdPay) + Number(row.rdInsuranceFund))
    },
    completed (data) {
      this.selectedRowKeys = []
      this.headerData.pay = 0
      this.headerData.insuranceFund = 0
      this.headerData.total = 0
      if (!data) {
        return
      }
      let count = 0
      for (const item of data.data) {
        if (item.workHours && item.pay && item.attendanceHour) {
          count++
        }
      }
      if (!count) {
        this.showHeaderChk = false
      } else {
        this.showHeaderChk = true
      }
      if (!data.header) {
        return
      }
      this.headerData.pay = data.header.pay
      this.headerData.insuranceFund = data.header.insuranceFund
      if (this.headerData.pay && this.headerData.insuranceFund) {
        this.headerData.total = toBit(Number(this.headerData.pay) + Number(this.headerData.insuranceFund))
      }
    },
    query (refresh) {
      this.$nextTick(() => {
        this.queryParam.year = this.currentYear
        this.queryParam.month = moment([this.currentYear, parseInt(this.monthValue), 1, 0, 0, 0, 0])
        if (this.$refs.table) {
          this.$refs.table.refresh(refresh)
        }
      })
    },
    onSelectChange ({ records }) {
      this.selectedRowKeys = records.map(a => a.enumber)
    },
    projectSelected (projectId) {
      this.projectId = projectId
      this.queryParam.projectId = projectId
      if (!this.first && projectId) {
        this.query(true)
      }
      this.first = false
    },
    canChecked ({ row }) {
      return row.workHours && row.pay && row.attendanceHour
    },
    accountingRdSalary () {
      this.accounting('您确定要核算选中人员的研发工资吗?',
        { enumbers: this.selectedRowKeys, month: this.queryParam.month, projectId: this.projectId },
        false, '/projectRdEmployee/accountingRdSalary')
    },
    accountingAllRdSalary () {
      this.accounting('您确定要核算所有可核算人员的研发工资吗?',
        { month: this.queryParam.month, projectId: this.projectId },
        true, '/projectRdEmployee/accountingAllRdSalary')
    },
    accounting (title, value, refresh, url) {
      const self = this
      this.$confirm({
        title: title,
        onOk () {
          return self.$http.post(url, value)
            .then(res => {
              if (res.success) {
                self.$message.success('核算成功')
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '核算失败')
              }
              self.query(refresh)
            })
        },
        onCancel () {
        }
      })
    }
  }

}
</script>
<style scoped>

</style>
