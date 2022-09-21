<template>
  <a-spin tip="请稍后..." :spinning="spin">
    <span v-if="$auth('project:costAnalysis:rDCostRatio:search')">
      <vxe-grid
        id="project:costAnalysis:rDCostRatio"
        ref="table"
        highlight-hover-row
        show-overflow
        show-header-overflow
        resizable
        auto-resize
        border="full"
        :data="baseTableData"
        :toolbar="{ refresh: {query:search}, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
        :customConfig="{storage: { visible: true, resizable: true } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button
            type="primary"
            @click="exportData"
            v-if="$auth('project:costAnalysis:rDCostRatio:export')"
          >导出</a-button>
          <span style="margin-left: 12px;">单位：元</span>
        </template>
        <vxe-table-column
          field="rd"
          title="项目"
          width="150"
          header-align="center"
          align="left"
          fixed="left"
        ></vxe-table-column>
        <vxe-table-column
          field="type"
          title="二级分类"
          width="100"
          header-align="center"
          align="left"
          fixed="left"
        ></vxe-table-column>
        <vxe-table-column
          field="totalCost"
          title="企业成本总数"
          width="110"
          header-align="center"
          align="left"
          fixed="left"
        >
          <template v-slot="{ row }">
            <span v-if="$auth('project:costAnalysis:rDCostRatio:edit')">
              <a-input-number
                v-if="showTotalCost(row)"
                :min="0"
                style="width:80px;"
                :precision="2"
                @blur="(value)=>blur(value,row)"
                @change="(value)=>onChange(value,row)"
                v-model="row.totalCost"
              ></a-input-number>
              <span
                v-else-if="row.id===30"
                style="max-width:150px"
              >{{ row.totalCost | MoneyFormat }}</span>
            </span>
            <span v-else>
              <span v-if="row.id===30" style="max-width:150px">{{ row.totalCost | MoneyFormat }}</span>
            </span>
          </template>
        </vxe-table-column>
        <template v-for="(item, index) in headerRow">
          <template v-if="index < headerRow.length - 1">
            <vxe-table-column
              :field="`rd${item.rdIndex}`"
              :title="`${item.title}`"
              width="100"
              header-align="center"
              align="right"
              :key="`rd${index}`"
            ></vxe-table-column>
          </template>
          <template v-else>
            <vxe-table-column
              :field="`rd${item.rdIndex}`"
              title="合计"
              width="100"
              header-align="center"
              :key="`h${index}`"
              align="right"
            ></vxe-table-column>
          </template>
        </template>
        <vxe-table-column
          field="ratio"
          title="研发费与企业总成本数占比%"
          width="80"
          header-align="center"
          align="right"
        >
          <template v-slot="{ row }">
            <span
              v-if="(showTotalCost(row)||row.id===30)&&row.totalCost!==0"
            >{{ setRatio(true,row)|MoneyFormat }}</span>
            <span
              v-else-if="(showTotalCost(row)||row.id===30)&&row.totalCost===0"
            >{{ setRatio(false,row)|MoneyFormat }}</span>
          </template>
        </vxe-table-column>
      </vxe-grid>
    </span>
    <span v-else>没有查询权限，请联系管理员！</span>
  </a-spin>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
const defaultData = [
  { id: 1, rd: '1、人员人工费用', isHeader: true },
  { id: 2, type: '工资', key: 'salary', totalCost: 0, ratio: 0, costEnumType: 10000 },
  { id: 3, type: '五险一金', key: 'insurance', totalCost: 0, ratio: 0, costEnumType: 10001 },
  { id: 4, rd: '2、直接投入费用', isHeader: true },
  { id: 5, type: '材料', key: 'material', totalCost: 0, ratio: 0, costEnumType: 20000 },
  { id: 6, type: '动力', key: 'stimulus', totalCost: 0, ratio: 0, costEnumType: 20100 },
  { id: 7, type: '燃料', key: 'fuel', totalCost: 0, ratio: 0, costEnumType: 20200 },
  { id: 8, type: '试制', key: 'trialProd', totalCost: 0, ratio: 0, costEnumType: 20300 },
  { id: 9, type: '试检', key: 'trialTest', totalCost: 0, ratio: 0, costEnumType: 20400 },
  { id: 10, type: '检测', key: 'inspection', totalCost: 0, ratio: 0, costEnumType: 20500 },
  { id: 11, rd: '3、折旧费用', isHeader: true },
  { id: 12, type: '仪器', key: 'lab', totalCost: 0, ratio: 0, costEnumType: 30100 },
  { id: 13, type: '设备', key: 'prod', totalCost: 0, ratio: 0, costEnumType: 30000 },
  { id: 14, rd: '4、无形资产摊销', isHeader: true },
  { id: 15, type: '软件摊销', key: 'softAmortization', totalCost: 0, ratio: 0, costEnumType: 40000 },
  { id: 16, type: '专利摊销', key: 'patentAmortization', totalCost: 0, ratio: 0, costEnumType: 40100 },
  { id: 17, type: '其他摊销', key: 'otherAmortization', totalCost: 0, ratio: 0, costEnumType: 40200 },
  { id: 18, rd: '5、新产品设计费等', isHeader: true },
  { id: 19, type: '产品设计', key: 'design', totalCost: 0, ratio: 0, costEnumType: 50000 },
  { id: 20, type: '工艺规程', key: 'techProcedure', totalCost: 0, ratio: 0, costEnumType: 50100 },
  { id: 21, type: '临床试验', key: 'clinicalTrials', totalCost: 0, ratio: 0, costEnumType: 50200 },
  { id: 22, type: '勘探', key: 'explore', totalCost: 0, ratio: 0, costEnumType: 50300 },
  { id: 23, rd: '6、其他相关费用', isHeader: true },
  { id: 24, type: '资料', key: 'book', totalCost: 0, ratio: 0, costEnumType: 60000 },
  { id: 25, type: '研发成果', key: 'rdProduction', totalCost: 0, ratio: 0, costEnumType: 60100 },
  { id: 26, type: '知识产权', key: 'copyRight', totalCost: 0, ratio: 0, costEnumType: 60200 },
  { id: 27, type: '福利', key: 'benefits', totalCost: 0, ratio: 0, costEnumType: 60300 },
  { id: 28, type: '差旅费', key: 'travel', totalCost: 0, ratio: 0, costEnumType: 60400 },
  { id: 29, type: '其他', key: 'other', totalCost: 0, ratio: 0, costEnumType: 69900 }
]
export default {
  mixins: [yearMiXin],
  data () {
    return {
      currentYear: this.$store.state.currentYear,
      colWidth: 50,
      spin: false,
      fixed: true,
      scroll: {},
      x: 1780,
      baseTableData: [],
      headerRow: [],
      companyCostList: [],
      searchCost: undefined
    }
  },
  created () {
    this.baseTableData = this.$deepClone(defaultData) // JSON.parse(JSON.stringify(defaultData))
    this.searchCost = this.searchCompanyCost()
  },
  mounted () {
    this.search()
  },
  watch: {
  },
  methods: {
    searchCompanyCost () {
      const self = this
      return this.$http.get('/summary/getCompanyCostList', { params: { year: this.currentYear } })
        .then(res => {
          if (res.success && res.data) {
            res.data.map(item => {
              var info = self.baseTableData.filter(a => a.costEnumType === item.rdType)
              if (info) {
                self.$set(info[0], 'totalCost', item.rdFunds)
              }
            })
            this.companyCostList = res.data
          }
        })
    },
    blur (value, record) {
      var oldRecords = this.companyCostList.filter(a => a.rdType === record.costEnumType)
      if (oldRecords && oldRecords.length > 0 && oldRecords[0].rdFunds !== record.totalCost) {
        this.spin = true
        var items = this.baseTableData.filter(a => a.id === 30)
        items[0].totalCost = 0
        this.baseTableData.map(a => {
          if (a.id !== 30 && typeof a.totalCost !== 'undefined') {
            items[0].totalCost += a.totalCost
          }
        })
        this.$http.post('/summary/saveCompanyCost', { year: this.currentYear, rdType: record.costEnumType, rdFunds: record.totalCost }).then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
          } else {
            this.$message.error('保存失败')
          }
          this.searchCompanyCost()
        }).finally(res => {
          this.spin = false
        })
      }
    },
    setRatio (isSet, record) {
      if (isSet) {
        record.ratio = (record.rd0 / record.totalCost)
      }
      return record.ratio
    },
    onChange (value, record) {
      var items = this.baseTableData.filter(a => a.id === 30)
      items[0].totalCost = 0
      this.baseTableData.map(a => {
        if (a.id !== 30 && typeof a.totalCost !== 'undefined') {
          items[0].totalCost += parseFloat(a.totalCost)
        }
      })
    },
    showTotalCost (record) {
      return !record.isHeader && record.id !== 30
    },
    exportData () {
      const multiHeader = [['研发费占成本总额占比分析表', '', ''], [`${this.currentYear}年度`, '', '']]
      const header = ['项目', '二级分类', '企业成本总数']
      const headKey = ['rd', 'type', 'totalCost']
      const colWidthList = [20, 10, 20]
      this.headerRow.filter(a => a.title != null).map(a => {
        header.push(a.title)
        headKey.push(`rd${a.rdIndex}`)
        colWidthList.push(10)
        multiHeader.map(merge => {
          merge.push('')
        })
      })
      multiHeader.map(merge => {
        merge.push('')
        merge.push('')
      })
      header.push('总计')
      headKey.push('rd0')
      header.push('研发费与企业总成本数占比%')
      headKey.push('ratio')
      colWidthList.push(10)
      colWidthList.push(26)
      multiHeader.push(header)
      const merges = [`A1:${this.$getExcelHeadTitle(header.length - 1)}1`, `A2:${this.$getExcelHeadTitle(header.length - 1)}2`]
      const exportData = this.$deepClone(this.baseTableData)// JSON.parse(JSON.stringify(this.datas))
      const fileName = `${this.currentYear}-研发费占成本总额占比分析表`
      this.$exportJsonData(fileName,
        [],
        headKey,
        exportData,
        colWidthList,
        merges,
        [],
        multiHeader)
    },
    getTotalSummaries () {
      this.spin = true
      this.$http.get('/summary/getTotalSummaries', { params: { year: this.currentYear } })
        .then(res => {
          var copyData = JSON.parse(JSON.stringify(defaultData))
          var copyHeadRow = []
          var totalRow = { id: 30, type: '合计', totalCost: 0, ratio: 0 }
          this.companyCostList.map(a => { totalRow.totalCost += a.rdFunds })
          if (res.success && res.data && res.data.length > 0) {
            // 获取项目数
            const dataLength = res.data.length - 1
            if (dataLength > 4) {
              this.x = 1780 + (50 * (dataLength - 4))
            } else if (dataLength === 3) {
              this.x = 1524
            } else if (dataLength === 2) {
              this.x = 1464
            } else if (dataLength === 1) {
              this.x = 1364
            }
            this.scroll = { x: this.x }
            this.colWidth = (this.x - 570) / ((dataLength) + 1)
            this.fixed = true
            res.data.forEach(item => {
              const rdIndex = item.rdIndex
              const node = { rdIndex: rdIndex, title: item.rdTitle }
              copyHeadRow.push(node)
              copyData.forEach(base => {
                if (base.isHeader) {
                  return
                }
                const tempCostList = this.companyCostList.filter(a => a.rdType === base.costEnumType)
                if (tempCostList && tempCostList.length > 0) {
                  base.totalCost = parseFloat(tempCostList[0].rdFunds)
                }
                base[`rd${rdIndex}`] = item[base.key]
                totalRow[`rd${rdIndex}`] = totalRow[`rd${rdIndex}`] ? (parseFloat(totalRow[`rd${rdIndex}`]) + parseFloat(item[base.key])).toFixed(2) : parseFloat(item[base.key]).toFixed(2)
              })
            })
          } else {
            this.disabled = true
            this.fixed = false
            this.scroll = {}
          }
          copyHeadRow.forEach(item => {
            totalRow[`rd${item.rdIndex}`] = parseFloat(totalRow[`rd${item.rdIndex}`])
          })
          copyData.push(totalRow)
          this.headerRow = copyHeadRow
          this.baseTableData = copyData
        }).finally(res => {
          this.spin = false
        })
    },
    search () {
      this.searchCompanyCost().then(a => this.getTotalSummaries())
    }
  }
}
</script>

<style>
</style>
