<!-- 研发费用总表 -->
<template>
  <a-card>
    <div style="height: 100%; flex: 1;">
      <a-spin :spinning="spinning">
        <a-alert :message="errorMsg" banner v-if="errorMsg !== null" />
        <vxe-grid
          v-else
          ref="xTable"
          max-height="95%"
          highlight-hover-row
          show-footer
          show-footer-overflow
          show-overflow
          resizable
          auto-resize
          :toolbar="{ custom: true, zoom:true, refresh: { query:search } }"
          :data="tableData"
          :tree-config="{
            transform: true,
            children: 'children',
            lazy: true,
            hasChild: 'hasChild',
            loadMethod: loadChildrenMethod
          }"
          :row-config="{ keyField:'id', isCurrent: true }"
          :footer-method="tableData.length !== 0 ? amountTotal : null"
          @footer-cell-click="showAmount"
          :footer-cell-class-name="footerClassName"
        >
          <!-- :footer-cell-class-name="amountAuth ? 'canClick' : ''" -->
          <vxe-table-column
            title="RD"
            field="rdNumber"
            :width="110"
            show-header-overflow
            show-overflow="tooltip"
            fixed="left"
            tree-node
            sortable
          ></vxe-table-column>
          <vxe-table-column
            title="项目名称"
            field="pname"
            :min-width="150"
            show-header-overflow
            show-overflow="tooltip"
            fixed="left"
            align="left"
            sortable
          ></vxe-table-column>
          <vxe-table-column
            title="时间"
            field="month"
            :width="150"
            show-header-overflow
            show-overflow="tooltip"
            fixed="left"
            align="center"
            sortable
          />
          <vxe-table-column
            v-for="item in costTypes"
            show-header-overflow
            show-overflow="tooltip"
            :min-width="110"
            :key="item.value"
            :field="`${item.value}`"
            :title="item.title"
            width="100"
            align="right"
            sortable
          >
            <template v-slot="{row}">
              <a v-if="$auth('project:data:agg') && (item.title !== '试制' || row.isTrialProd)" @click="show(row, item.type)">{{ row.totalFunds[item.value] | NumberFormat }}</a>
              <span v-else>{{ row.totalFunds[item.value] | NumberFormat }}</span>
            </template>
          </vxe-table-column>
          <template v-if="!noOutsourcing">
            <vxe-table-column
              show-header-overflow
              show-overflow="tooltip"
              field="inside"
              title="国内委托费用"
              width="110"
              align="right"
              sortable
            >
              <template v-slot="{row}">{{ row.totalFunds['inside'] | NumberFormat }}</template>
            </vxe-table-column>
            <vxe-table-column
              show-header-overflow
              show-overflow="tooltip"
              field="outside"
              title="国外委托费用"
              width="110"
              align="right"
              sortable
            >
              <template v-slot="{row}">{{ row.totalFunds['outside'] | NumberFormat }}</template>
            </vxe-table-column>
          </template>
          <vxe-table-column
            title="合计"
            field="rdTotal"
            key="rdTotal"
            :width="140"
            show-header-overflow
            show-overflow="tooltip"
            fixed="right"
            align="right"
            sortable
          >
            <template v-slot="{row}">{{ getTotal(row.totalFunds).toFixed(2) | NumberFormat }}</template>
          </vxe-table-column>
          <template #footer>
            111
          </template>
        </vxe-grid>
      </a-spin>
    </div>
    <DataEntryModal ref="dataEntry" @refresh="refresh"/>
    <SubsidiaryLedger ref="subsidiary" :visible.sync="subsidiaryVisible" :year="currentYear" :rdType="subsidiaryRdType" :projectList="projectList"></SubsidiaryLedger>
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import { TreeKeyMap } from '@/components'
import yearMiXin from '@/utils/yearMixin'
import DataEntryModal from './modules/DataEntryModal'
import SubsidiaryLedger from './modules/SubsidiaryLedger.vue'
export default {
  name: 'Aggregation',
  mixins: [yearMiXin],
  components: {
    mapGetters,
    TreeKeyMap,
    DataEntryModal,
    SubsidiaryLedger
  },
  data () {
    return {
      tableData: [],
      noOutsourcing: true,
      costTypes: [],
      errorMsg: null,
      // 合计行数据
      amount: null,
      spinning: false,
      amountAuth: this.$auth('rdagg:aggregation:view'),
      subsidiaryVisible: false,
      subsidiaryRdType: null,
      projectList: []
    }
  },
  mounted () {
    this.costTypes = this.$getCostTypes()
    this.getData()
  },
  filters: {
    NumberFormat (num) {
      return num === undefined || num === null || isNaN(num) ? '--' : num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
    }
  },
  methods: {
    ...mapGetters(['userInfo']),
    search () {
      this.getData()
    },
    // 获取数据
    getData () {
      this.tableData = []
      this.noOutsourcing = true
      if (!this.currentYear) {
        return
      }
      this.spinning = true
      this.$http.get('/rdAgg/getDataReportFunds', { params: { year: this.currentYear } })
        .then(res => {
          if (res.success) {
            this.noOutsourcing = res.data.noOutsourcing
            if (JSON.stringify(res.data) === '{}') {
              this.errorMsg = '当前年份：' + this.currentYear + ' 没有项目，请重新选择年份'
              return
            }
            this.amount = res.data.companyFunds
            this.errorMsg = null
            this.projectList = res.data.list.map(elem => ({ 'rdTitle': elem.rdNumber, 'rdPname': elem.pname, 'month': elem.month }))
            this.tableData = [...res.data.list].map(elem => {
              const totalFunds = {
                '100': '-',
                '101': '-',
                '200': '-',
                '201': '-',
                '202': '-',
                '203': '-',
                '205': '-',
                '206': '-',
                '207': '-',
                '300': '-',
                '301': '-',
                '400': '-',
                '500': '-',
                '604': '-',
                '699': '-'
              }
              if (elem !== null && elem.hasOwnProperty('id') && elem.id !== null) {
                elem.hasChild = true
                if (JSON.stringify(elem.totalFunds) === '{}') {
                  elem.totalFunds = { ...totalFunds }
                }
              }
              elem.isTrialProd = this.trialProd(elem, 1)
              return elem
            })
          }
        }).finally(() => {
          this.spinning = false
        })
    },
    // 合计工具
    getTotal (funds) {
      var total = 0
      for (const key in funds) {
        total += parseFloat(funds[key])
      }
      return total
    },
    // 异步加载子节点
    loadChildrenMethod ({ row }) {
      return new Promise(resolve => {
        let childList = []
        this.$http.get('/rdAgg/getProjectData', { params: { year: this.currentYear, projectId: row.id } })
          .then(res => {
            if (res.success) {
              const emptyData = {
                'pnames': row.pname,
                'id': '',
                'rdNumber': '',
                'month': '',
                'pname': '',
                'totalFunds': {
                  '100': '-',
                  '101': '-',
                  '200': '-',
                  '201': '-',
                  '202': '-',
                  '203': '-',
                  '205': '-',
                  '206': '-',
                  '207': '-',
                  '300': '-',
                  '301': '-',
                  '400': '-',
                  '500': '-',
                  '604': '-',
                  '699': '-',
                  'month': ''
                },
                'hasChild': false,
                tBeginDate: row.tBeginDate,
                tEndDate: row.tEndDate

              }
              const startMonth = Number(res.data.beginMonth)
              const endMonth = Number(res.data.endMonth)
              // 处理数据，当未归集时，显示默认
              const resData = res.data.list
              let i = 0
              childList = [...Array.from({ length: endMonth - startMonth + 1 }, (_, index) => {
                emptyData.month = startMonth + index + '月份'
                emptyData.id = `${row.id}-${index}`
                return { ...emptyData }
              })].map(elem => {
                let data
                if (i < resData.length && elem.month === resData[i].month) {
                  data = resData[i++]
                  data.tBeginDate = row.tBeginDate
                  data.tEndDate = row.tEndDate
                } else {
                  data = elem
                }
                data.isTrialProd = row.isTrialProd && this.trialProd(data, 2)
                return data
              })
              resolve(childList)
            }
          })
      })
    },
    // 展示项目详情抽屉
    show (rowData, selectKey) {
      this.$refs.dataEntry.show(rowData, selectKey)
    },
    // 合计行
    amountTotal ({ columns, data }) {
      const amount = []
      const self = this
      columns.forEach((element, index) => {
        if (index === 0) {
          amount.push('合计')
        } else if (element.property === 'rdTotal') {
          amount.push(self.$options.filters.NumberFormat(self.amount && self.amount.totalFunds ? self.getTotal(self.amount.totalFunds).toFixed(2) : null))
        } else if (index > 2 && index < 20) {
          amount.push(self.$options.filters.NumberFormat(self.amount && self.amount.totalFunds ? self.amount.totalFunds[element.property] : null))
        } else if (index >= 20) {
          amount.push('--')
        } else {
          amount.push(null)
        }
      })
      return [amount]
    },
    refresh () {
      this.$refs.xTable.toolbar.refresh.query()
    },
    trialProd (row, type) {
      if (type === 1) {
        if (row.totalFunds[203] !== '-' && Number(row.totalFunds[203]) !== 0) {
          return true
        }
        if (row.tBeginDate !== undefined && row.tEndDate !== undefined && row.tBeginDate !== null && row.tEndDate !== null) {
          if (this.currentYear >= Number(row.tBeginDate.substr(0, 4)) && this.currentYear <= Number(row.tEndDate.substr(0, 4))) {
            return row.trialProd
          } else {
            return false
          }
        }
        return false
      } else {
        if (row.totalFunds[203] !== '-' && Number(row.totalFunds[203]) !== 0) {
          return true
        }
        if (row.tBeginDate !== undefined && row.tEndDate !== undefined && row.tBeginDate !== null && row.tEndDate !== null) {
          return Number(row.month.slice(0, -2)) >= Number(row.tBeginDate.substr(5, 2)) && Number(row.month.slice(0, -2)) <= Number(row.tEndDate.substr(5, 2))
        }
        return false
      }
    },
    showAmount ({ column }) {
      if (this.amountAuth && !['rdNumber', 'pname', 'month', 'inside', 'outside', 'rdTotal'].includes(column.property)) {
        this.subsidiaryVisible = true
        this.subsidiaryRdType = column.property
      }
    },
    footerClassName ({ $columnIndex }) {
      if (this.amountAuth && ($columnIndex > 2 && $columnIndex < 20)) {
        return 'canClick'
      }
      return ''
    }
  }
}
</script>

<style scoped lang="less">
//  .no-touch {
//   overflow: hidden;
//  }
 /deep/ .ant-card-body {
    display: flex;
    flex-direction: column;
    justify-content: center;
    height: 100%;
 }
 .ant-card-bordered {
  height: 100%;
 }
 /deep/ .canClick:not(:first-child, :last-child ) {
  color: #1890ff;
  cursor: pointer;
 }
 .vxe-grid {
  height: 100%;
 }
</style>
