<template>
  <a-spin id="spin" :spinning="spining" style="height: 100%; overflow:auto">
    <a-card
      :bordered="false"
      style="height: 100%; overflow:auto"
      :bodyStyle="{ height: '100%', overflow: 'auto'}"
    >
      <span v-if="$auth('project:rdSalaryDetail:search')">
        <div>
          <a-form layout="inline">
            <a-form-item label="月份">
              <a-select
                style="width:165px;"
                v-model="actvieKey"
                placeholder="请选择月份"
                :options="monthOptions"
              ></a-select>
            </a-form-item>
            <a-form-item>
              <a-button
                type="primary"
                @click="getData"
                v-if="$auth('project:rdSalaryDetail:search')"
              >查询</a-button>
            </a-form-item>
          </a-form>
        </div>
        <div>
          <vxe-grid
            id="project:rdSalaryDetail"
            ref="table"
            highlight-hover-row
            show-overflow
            resizable
            auto-resize
            :data="tableData"
            :toolbar="{ custom: true, zoom:true, refresh:{query:getData} }"
            :customConfig="{storage: { visible: true, resizable: true } }"
          >
            <template v-slot:buttons>
              <span>
                <a-button
                  type="primary"
                  @click="exportData"
                  v-if="$auth('project:rdSalaryDetail:export')"
                >导出</a-button>
              </span>
            </template>
            <vxe-table-column
              title="费用类型"
              field="accountName"
              key="accountName"
              :width="120"
              show-header-overflow
              show-overflow="tooltip"
            ></vxe-table-column>
            <vxe-table-column
              title="车间"
              field="workshop"
              :width="150"
              show-header-overflow
              show-overflow="tooltip"
            />
            <vxe-table-column
              title="项目"
              field="rdTitle"
              :width="120"
              show-header-overflow
              show-overflow="tooltip"
            />
            <template v-if="salaryArr && salaryArr.length > 0">
              <vxe-table-column
                v-for="c in salaryArr"
                :key="c.name"
                :field="c.name"
                :title="c.alias"
                show-header-overflow
                show-overflow="tooltip"
                align="right"
                :width="80"
              ></vxe-table-column>
            </template>
            <template v-else>
              <vxe-table-column
                align="right"
                title="应发工资"
                :width="100"
                field="pay"
                show-header-overflow
                show-overflow="tooltip"
              ></vxe-table-column>
            </template>
            <template v-if="insuranceArr && insuranceArr.length > 0">
              <vxe-table-column
                v-for="c in insuranceArr"
                :key="c.name"
                :field="c.name"
                :title="c.alias"
                show-header-overflow
                show-overflow="tooltip"
                align="right"
                :width="80"
              ></vxe-table-column>
            </template>
            <vxe-table-column
              v-else
              title="五险一金"
              field="insuranceFund"
              key="insuranceFund"
              :width="100"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
            <!-- <vxe-table-column
              title="养老保险"
              field="endowmentOfCom"
              key="endowmentOfCom"
              :width="100"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
            <vxe-table-column
              title="医疗保险"
              field="medicalOfCom"
              key="medicalOfCom"
              :width="100"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
            <vxe-table-column
              title="失业保险"
              field="unemploymentOfCom"
              key="unemploymentOfCom"
              :width="100"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
            <vxe-table-column
              title="生育保险"
              field="maternityOfCom"
              key="maternityOfCom"
              :width="100"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
            <vxe-table-column
              title="工伤保险"
              field="injuryOfCom"
              key="injuryOfCom"
              :width="100"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
            <vxe-table-column
              title="公积金"
              field="houseOfCom"
              key="houseOfCom"
              :width="100"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>-->
            <vxe-table-column
              title="研发工资合计"
              field="rdPay"
              key="rdPay"
              :width="130"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
            <vxe-table-column
              title="研发五险一金合计"
              field="rdInsuranceFund"
              key="rdInsuranceFund"
              :width="150"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
            <vxe-table-column
              title="总计"
              field="total"
              key="total"
              min-width="80"
              show-header-overflow
              show-overflow="tooltip"
              align="right"
            ></vxe-table-column>
          </vxe-grid>
        </div>
      </span>
    </a-card>
  </a-spin>
</template>

<script>
import yearMixin from '@/utils/yearMixin'
import moment from 'moment'
import ystable from '@/components/Table/ystable'
export default {
  mixins: [yearMixin],
  components: {
    ystable
  },
  data () {
    return {
      spining: false,
      actvieKey: '0',
      salaryArr: undefined,
      insuranceArr: undefined,
      tableData: [],
      colMap: {},
      keyMap: {}
    }
  },
  created () {
    this.getData()
  },
  methods: {
    moment,
    customRender (t, r, i) {
      var spanValue
      const obj = {
        children: spanValue,
        attrs: {}
      }
      if (r.first) {
        obj.attrs.rowSpan = this.colMap[r.accountTitleId]
      } else {
        obj.attrs.rowSpan = 0
      }
      spanValue = t
      obj.children = spanValue
      return obj
    },
    getData () {
      if (!this.$auth('project:rdSalaryDetail:search')) {
        this.$message.info('无查询权限，请跟管理员联系！')
        return
      }
      this.spining = true
      this.$getSalaryConfig(this, false).then(res => {
        ({ salary: this.salaryArr, insurance: this.insuranceArr } = res)
      })
      this.$http.get('/summary/getRdSalaryDetail', { params: { month: moment(this.currentYear + '-' + (parseInt(this.actvieKey) + 1) + '-' + '01' + ' 00:00:00') } }).then(res => {
        const tempMap = {}
        if (res.data) {
          res.data.forEach(row => {
            Object.assign(row, row.salaryMap)
            Object.assign(row, row.insuranceMap)
            for (const key in row) {
              if (key !== 'accountName' && key !== 'workshop' && key !== 'rdTitle' && key !== 'num') {
                if (row[key]) {
                  row[key] = Number(row[key]).toFixed(2)
                } else {
                  row[key] = '-'
                }
              }
            }
            if (!tempMap[row.accountTitleId]) {
              tempMap[row.accountTitleId] = 0
              row.first = true
            }
            tempMap[row.accountTitleId] += 1
          })
          this.colMap = tempMap
          this.tableData = res.data
        } else {
          this.tableData = []
        }
        this.spining = false
      })
    },
    search () {
      this.getData()
    },
    exportData () {
      // var headerKeys = ['accountName', 'workshop', 'rdTitle']
      // var headerTitles = ['费用类型', '车间', '项目']
      // if (this.salaryArr) {
      //   this.salaryArr.forEach(c => {
      //     headerKeys.push(c.name)
      //     headerTitles.push(c.alias)
      //   })
      // } else {
      //   headerKeys.push('pay')
      //   headerTitles.push('应发工资')
      // }
      // if (this.insuranceArr) {
      //   this.insuranceArr.forEach(c => {
      //     headerKeys.push(c.name)
      //     headerTitles.push(c.alias)
      //   })
      // } else {
      //   headerKeys.push('insuranceFund')
      //   headerTitles.push('五险一金')
      // }
      // const lastKeys = ['rdPay', 'rdInsuranceFund', 'total']
      // const lastTitles = ['研发工资合计', '研发五险一金合计', '总计']
      // headerKeys = headerKeys.concat([...lastKeys])
      // headerTitles = headerTitles.concat([...lastTitles])
      // var beginRow = 3
      // const merges = [`A1:${this.$getExcelHeadTitle(headerTitles.length - 1)}1`]
      // const dataCopy = []
      // this.tableData.forEach(row => {
      //   const tempRow = { ...row }
      //   if (tempRow.first) {
      //     merges.push(`A${beginRow}:A${beginRow + this.colMap[tempRow.accountTitleId] - 1}`)
      //     beginRow += this.colMap[tempRow.accountTitleId]
      //   }
      //   dataCopy.push(tempRow)
      // })
      // const exportName = `${this.$store.getters.userInfo.companyName}${this.currentYear + '-' + this.actvieKey + 1}研发薪资`
      // const colWidthList = [20, 20]
      // var sheetHeaders = [[exportName]]
      // for (let i = 2; i <= headerTitles.length - 2; i++) {
      //   if (i === headerTitles.length - 3) {
      //     colWidthList.push(16)
      //   } else {
      //     colWidthList.push(12)
      //   }
      // }
      // this.$exportJsonData(exportName, headerTitles, headerKeys, dataCopy, colWidthList, merges, sheetHeaders)
      this.spinning = true
      this.$exportData('/aggregation/exportRdSalaryDetail', { month: moment(this.currentYear + '-' + (parseInt(this.actvieKey) + 1) + '-' + '01' + ' 00:00:00') }, `${this.$store.getters.userInfo.companyName}${this.currentYear + '-' + (parseInt(this.actvieKey) + 1)}研发薪资.xls`, this.$message).then(res => {
        this.spinning = false
      })
    }
  }
}
</script>

<style lang="less" scoped>
//parentHeight
@FH: 100%;
#tabs /deep/ .ant-tabs-content {
  height: @FH;
}
#tabs /deep/ .ant-tabs-tabpane-active {
  height: calc(@FH - 60px);
  overflow: auto;
}
#tabs {
  height: @FH;
}
#spin /deep/ .ant-spin-container {
  height: @FH;
}
</style>
