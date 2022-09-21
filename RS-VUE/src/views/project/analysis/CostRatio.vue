<template>
  <a-spin tip="请稍后..." :spinning="spin">
    <span v-if="this.$auth('project:costAnalysis:costRatio:search')">
      <vxe-grid
        id="project:costAnalysis:costRatio"
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
            @click="exportReport"
            :disabled="disabled"
            v-if="$auth('project:costAnalysis:costRatio:export')"
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
        <template v-for="(item, index) in headerRow">
          <template v-if="index < headerRow.length - 1">
            <vxe-table-column :title="item.title" :key="`g${index}`" align="center">
              <vxe-table-column
                title="预计"
                width="80"
                :field="`es${item.rdIndex}`"
                header-align="center"
                align="right"
              ></vxe-table-column>
              <vxe-table-column
                title="实际归集"
                width="80"
                :field="`rd${item.rdIndex}`"
                header-align="center"
                align="right"
              ></vxe-table-column>
              <vxe-table-column
                title="实际预测比"
                width="90"
                :field="`ratio${item.rdIndex}`"
                header-align="center"
                align="right"
              ></vxe-table-column>
            </vxe-table-column>
          </template>
          <template v-else>
            <template v-if="index <= 3">
              <vxe-table-column
                :key="`h${index}`"
                title="合计"
                width="100"
                :field="`rd${item.rdIndex}`"
                header-align="center"
                align="right"
              ></vxe-table-column>
            </template>
            <template v-else>
              <vxe-table-column
                :key="`h${index}`"
                title="合计"
                width="100"
                :field="`rd${item.rdIndex}`"
                header-align="center"
                align="right"
              ></vxe-table-column>
            </template>
          </template>
        </template>
      </vxe-grid>
    </span>
    <span v-else>没有查询权限,请联系管理员</span>
  </a-spin>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
const defaultData = [
  { id: 1, rd: '1、人员人工费用', isHeader: true },
  { id: 2, type: '工资', key: 'salary', key2: 'salaryValue' },
  { id: 3, type: '五险一金', key: 'insurance', key2: 'insuranceValue' },
  { id: 4, rd: '2、直接投入费用', isHeader: true },
  { id: 5, type: '材料', key: 'material', key2: 'materialValue' },
  { id: 6, type: '燃料', key: 'fuel', key2: 'fuelValue' },
  { id: 7, type: '动力', key: 'stimulus', key2: 'stimulusValue' },
  { id: 8, type: '试制', key: 'trialProd', key2: 'trialProdValue' },
  { id: 9, type: '试检', key: 'trialTest', key2: 'trialTestValue' },
  { id: 10, type: '检测', key: 'inspection', key2: 'inspectionValue' },
  { id: 11, rd: '3、折旧费用', isHeader: true },
  { id: 12, type: '仪器', key: 'lab', key2: 'labValue' },
  { id: 13, type: '设备', key: 'prod', key2: 'prodValue' },
  { id: 14, rd: '4、无形资产摊销', isHeader: true },
  { id: 15, type: '软件摊销', key: 'softAmortization', key2: 'softAmortizationValue' },
  { id: 16, type: '专利摊销', key: 'patentAmortization', key2: 'patentAmortizationValue' },
  { id: 17, type: '其他摊销', key: 'otherAmortization', key2: 'otherAmortizationValue' },
  { id: 18, rd: '5、新产品设计费等', isHeader: true },
  { id: 19, type: '产品设计', key: 'design', key2: 'designValue' },
  { id: 20, type: '工艺规程', key: 'techProcedure', key2: 'techProcedureValue' },
  { id: 21, type: '临床试验', key: 'clinicalTrials', key2: 'clinicalTrialsValue' },
  { id: 22, type: '勘探', key: 'explore', key2: 'exploreValue' },
  { id: 23, rd: '6、其他相关费用', isHeader: true },
  { id: 24, type: '资料', key: 'book', key2: 'bookValue' },
  { id: 25, type: '研发成果', key: 'rdProduction', key2: 'rdProductionValue' },
  { id: 26, type: '知识产权', key: 'copyRight', key2: 'copyRightValue' },
  { id: 27, type: '福利', key: 'benefits', key2: 'benefitsValue' },
  { id: 28, type: '差旅费', key: 'travel', key2: 'travelValue' },
  { id: 29, type: '其他', key: 'other', key2: 'otherValue' }
]
export default {
  mixins: [yearMiXin],
  data () {
    return {
      spin: false,
      baseTableData: [],
      headerRow: [],
      disabled: true
    }
  },
  created () {
    this.baseTableData = this.$deepClone(defaultData)
  },
  mounted () {
    this.search()
  },

  methods: {
    search () {
      this.spin = true
      this.disabled = true
      // if (!this.$auth('project:costAnalysis:costRatio:search')) {
      //   this.$message.info('没有查询权限,请联系管理员')
      //   this.spin = false
      //   this.disabled = true
      //   return
      // }
      this.$http.get('/summary/getTotalSummaries', { params: { year: this.currentYear } })
        .then(res => {
          var copyData = this.$deepClone(defaultData)
          var copyHeadRow = []
          var totalRow = { id: 30, type: '合计' }
          if (res.success && res.data && res.data.length > 0) {
            this.disabled = false
            res.data.forEach(item => {
              const rdIndex = item.rdIndex
              const node = { rdIndex: rdIndex, title: item.rdTitle }
              copyHeadRow.push(node)
              copyData.forEach(base => {
                if (base.isHeader) {
                  return
                }
                base[`es${rdIndex}`] = parseFloat(item[base.key2]) === 0.0 ? '0.00' : parseFloat(item[base.key2]).toFixed(2)
                base[`rd${rdIndex}`] = item[base.key]
                if (base[`es${rdIndex}`] === '0.00') {
                  base[`ratio${rdIndex}`] = '0.00%'
                } else {
                  base[`ratio${rdIndex}`] = ((parseFloat(item[base.key]) / parseFloat(item[base.key2])) * 100).toFixed(2) + '%'
                }
                totalRow[`es${rdIndex}`] = totalRow[`es${rdIndex}`] ? parseFloat(totalRow[`es${rdIndex}`]) + parseFloat(item[base.key2]) : parseFloat(item[base.key2])
                totalRow[`rd${rdIndex}`] = totalRow[`rd${rdIndex}`] ? parseFloat(totalRow[`rd${rdIndex}`]) + parseFloat(item[base.key]) : parseFloat(item[base.key])
              })
            })
          }
          copyHeadRow.forEach(item => {
            totalRow[`rd${item.rdIndex}`] = parseFloat(totalRow[`rd${item.rdIndex}`]).toFixed(2)
            totalRow[`es${item.rdIndex}`] = parseFloat(totalRow[`es${item.rdIndex}`]).toFixed(2)
          })
          copyData.push(totalRow)
          this.headerRow = copyHeadRow
          this.baseTableData = copyData
        }).finally(res => {
          this.spin = false
        })
    },
    exportReport () {
      const sheetHeaderList = [['资金预算对比分析表', ''], [`${this.currentYear}年度`, ''], ['项目', '二级分类'], ['', '']]
      var titleHeadItem = sheetHeaderList[0]
      var yearHeadItem = sheetHeaderList[1]
      var headerFirstItem = sheetHeaderList[2]
      var headerSecItem = sheetHeaderList[3]
      const merges = [`A3:A4`, `B3:B4`]
      const headKey = ['rd', 'type']
      const colWidthList = [20, 10]
      var index = 2
      this.headerRow.filter(a => a.title != null).map(a => {
        titleHeadItem.push('')
        yearHeadItem.push('')
        titleHeadItem.push('')
        yearHeadItem.push('')
        titleHeadItem.push('')
        yearHeadItem.push('')
        headerFirstItem.push(a.title)
        headerFirstItem.push('')
        headerFirstItem.push('')
        headerSecItem.push('预计费用')
        headerSecItem.push('实际归集')
        headerSecItem.push('实际预测比')
        headKey.push(`es${a.rdIndex}`)
        headKey.push(`rd${a.rdIndex}`)
        headKey.push(`ratio${a.rdIndex}`)
        colWidthList.push(20)
        colWidthList.push(20)
        colWidthList.push(20)
        merges.push(`${this.$getExcelHeadTitle(index)}3:${this.$getExcelHeadTitle(index + 2)}3`)
        index += 3
      })
      merges.push(`A1:${this.$getExcelHeadTitle(index)}1`)
      merges.push(`A2:${this.$getExcelHeadTitle(index)}2`)
      titleHeadItem.push('')
      yearHeadItem.push('')
      merges.push(`${this.$getExcelHeadTitle(index)}3:${this.$getExcelHeadTitle(index)}4`)
      headerFirstItem.push('总计')
      headerSecItem.push('')
      headKey.push('rd0')
      colWidthList.push(10)
      const exportData = this.$deepClone(this.baseTableData)
      const fileName = `${this.currentYear}-资金预算对比分析表`
      this.$exportJsonData(fileName,
        [],
        headKey,
        exportData,
        colWidthList,
        merges,
        [],
        sheetHeaderList)
    }
  }
}
</script>

<style>
</style>
