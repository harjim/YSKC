<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spin">
      <a-alert v-if="errorMsg!==''" :message="errorMsg" type="warning" style="width:600px;" />
      <vxe-grid
        id="project:summaryReport"
        ref="table"
        :data="tableData"
        tree-config
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :toolbar="{ custom: true, zoom:true, refresh:{query:search} }"
        :customConfig="{storage: { visible: true, resizable: true } }"
      >
        <template v-slot:buttons>
          <span style="margin-right:10px" v-if="$auth('project:summaryReport:auxiliary')">
            <a-button type="primary" @click="exportSummary()">导出辅助帐</a-button>
          </span>
          <span style="margin-right:10px" v-if="$auth('project:summaryReport:exportDetail')">
            <a-button type="primary" @click="exportSummaryData()">导出优惠明细表</a-button>
          </span>
          <span style="margin-right:10px" v-if="$auth('project:summaryReport:export')">
            <a-button type="primary" @click="exportSummaryReport()">导出归集表</a-button>
          </span>
          <span style="margin-right:10px" v-if="$auth('project:summaryReport:export')">
            <a-button type="primary" @click="$refs.exportRDorMonth.show('rd')">按RD导出</a-button>
          </span>
          <span style="margin-right:10px" v-if="$auth('project:summaryReport:export')">
            <a-button type="primary" @click="$refs.exportRDorMonth.show('month')">按月份导出</a-button>
          </span>
          <span style="margin-right:10px">
            <a-button
              type="primary"
              v-if="$auth('project:summaryReport:view')"
              @click="changeSearch"
            >切换</a-button>
          </span>
        </template>
        <vxe-table-column
          title="RD"
          field="rdNumber"
          :width="110"
          show-header-overflow
          show-overflow="tooltip"
          tree-node
        ></vxe-table-column>
        <vxe-table-column
          title="项目名称"
          field="pname"
          :min-width="150"
          show-header-overflow
          show-overflow="tooltip"
          align="left"
        ></vxe-table-column>
        <vxe-table-column
          title="时间"
          field="month"
          :width="150"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        />

        <vxe-table-column
          v-for="item in costTypes"
          :key="item.value"
          :field="`${item.value}`"
          :title="item.title"
          width="90"
        >
          <template v-slot="{row}">{{ row.totalFunds[item.value] }}</template>
        </vxe-table-column>
        <template v-if="!noOutsourcing">
          <vxe-table-column
            field="inside"
            title="国内委托费用"
            width="110"
          >
            <template v-slot="{row}">{{ row.totalFunds['inside'] }}</template>
          </vxe-table-column>
          <vxe-table-column
            field="outside"
            title="国外委托费用"
            width="110"
          >
            <template v-slot="{row}">{{ row.totalFunds['outside'] }}</template>
          </vxe-table-column>
        </template>
        <vxe-table-column
          title="合计"
          field="rdTotal"
          key="rdTotal"
          :width="140"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        >
          <template v-slot="{row}">{{ getTotal(row.totalFunds).toFixed(2) }}</template>
        </vxe-table-column>
      </vxe-grid>
    </a-spin>
    <ExportRDorMonthModal ref="exportRDorMonth" />
    <ExportGeneralLedgerModal ref="exportGeneralLedgerModal" @ok="exportGeneralLedger"/>
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import { TreeKeyMap } from '@/components'
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'
import ExportRDorMonthModal from './modules/ExportRDorMonthModal'
import ExportGeneralLedgerModal from './modules/ExportGeneralLedgerModal'
export default {
  mixins: [yearMiXin],
  name: 'SummaryReport',
  components: {
    TreeKeyMap,
    ystable,
    ExportRDorMonthModal,
    ExportGeneralLedgerModal
  },
  created () {
    this.costTypes = this.$getCostTypes()
    this.getData()
  },
  data () {
    return {
      queryParam: {},
      tableData: [],
      errorMsg: '',
      costTypes: [],
      spin: false,
      child: false,
      noOutsourcing: true
    }
  },
  methods: {
    changeSearch () {
      this.child = !this.child
      this.getData()
    },
    getTotal (funds) {
      var total = 0
      for (const key in funds) {
        total += parseFloat(funds[key])
      }
      return total
    },
    ...mapGetters(['userInfo']),
    exportSummary () {
      if (this.$store.state.generalLedgerYear <= this.currentYear) {
        this.$refs.exportGeneralLedgerModal.show(this.currentYear)
      } else {
        this.exportGeneralLedger(true)
      }
    },
    exportGeneralLedger (old) {
      this.spin = true
      this.$exportData('/project/exportGeneralLedger', { year: this.currentYear, old }, `${this.userInfo().companyName}${this.currentYear}年辅助账总表.xls`, this.$message).then(res => {
        this.spin = false
      })
    },
    exportSummaryData () {
      this.spin = true
      this.$exportData('/projectYearFee/exportDetailData', { year: this.currentYear }, `${this.currentYear}年优惠明细表.xls`, this.$message).then(res => {
        this.spin = false
      })
    },
    exportSummaryReport () {
      if (!this.tableData || this.tableData.length <= 0) {
        this.$message.warning('当前无可导出的数据')
        return
      }
      this.spin = true
      this.$exportData('/aggregation/exportSummaryReport', { year: this.currentYear }, `${this.userInfo().companyName}${this.currentYear}年归集总表.xls`, this.$message).then(res => {
        this.spin = false
      })
    },
    search () {
      this.getData()
    },
    getData (child) {
      this.spin = true
      this.tableData = []
      this.queryParam.year = this.currentYear
      this.queryParam.child = this.child
      this.noOutsourcing = true
      this.$http.get('/project/getDataReportFunds', { params: this.queryParam })
        .then(res => {
          if (res.success) {
            this.noOutsourcing = res.data.noOutsourcing
            const list = res.data.list
            if (list === null) {
              this.errorMsg = '当前年份：' + this.currentYear + '没有项目，请重新选择年份'
              return
            }
            this.errorMsg = ''
            const totalRow = {}
            list.forEach(item => {
              for (const key in item.totalFunds) {
                if (!totalRow[key] && totalRow[key] !== 0) {
                  totalRow[key] = 0
                }
                totalRow[key] = (Number(totalRow[key]) + Number(item.totalFunds[key])).toFixed(2)
              }
            })
            list.push({ rdNumber: '合计', totalFunds: totalRow })
            this.tableData = list
          }
          return this.tableData
        }).finally(() => {
          this.spin = false
        })
    }
  }
}
</script>
