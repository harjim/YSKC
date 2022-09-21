<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
    <a-spin :spinning="spin" tip="请稍后..." v-if="$auth('science:situation:view')">
      <vxe-grid
        id="science:situation"
        :data="tableData"
        border
        rowId="num"
        highlight-hover-row
        show-overflow
        show-header-overflow
        resizable
        :toolbar="{zoom:true,custom:true}"
        :customConfig="{storage: { visible: true, resizable: true } }"
      >
        <template v-slot:buttons>
          <span>
            <a-button
              type="primary"
              v-if="$auth('science:situation:export')"
              @click="exportReport"
              :disabled="disabled"
            >导出报告</a-button>
          </span>
        </template>
        <vxe-table-column title="序号" type="seq" :width="50"></vxe-table-column>
        <vxe-table-column title="名称" field="pname" :min-width="200"></vxe-table-column>
        <vxe-table-column title="来源" :width="80" field="projectSource" align="center"></vxe-table-column>
        <vxe-table-column title="开展形式" :width="80" field="formula" align="center"></vxe-table-column>
        <vxe-table-column title="当年成果形式" :width="100" field="result" align="center"></vxe-table-column>
        <vxe-table-column title="技术经济目标" :width="100" field="targets" align="center"></vxe-table-column>
        <vxe-table-column title="起始日期" :width="80" field="beginDate" align="center"></vxe-table-column>
        <vxe-table-column title="完成日期" :width="80" field="endDate" align="center"></vxe-table-column>
        <vxe-table-column title="跨年项目当年所处主要进展阶段" :width="130" field="stage" align="center"></vxe-table-column>
        <vxe-table-column title="研究 开发人员(人)" :width="120" field="employee" align="right"></vxe-table-column>
        <vxe-table-column title="人员实际工作时间(人月)" :width="120" field="avgWork" align="right">
          <template slot-scope="{row}">{{ toBit(row.avgWork,true) }}</template>
        </vxe-table-column>
        <vxe-table-column title="经费支出(千元)" :width="120" field="cost" align="right" fixed="right">
          <template slot-scope="{row}">{{ toBit(row.cost,true) }}</template>
        </vxe-table-column>
        <vxe-table-column title="政府资金" :width="130" field="govCost" align="right" fixed="right">
          <template slot-scope="{row}">
            <span v-if="row.isTotal">{{ toBit(row.govCost,true) }}</span>
            <span v-else>
              <span v-if="$auth('science:situation:edit')">
                <a-input-number
                  style="width: 100%"
                  size="small"
                  :value="row.govCost"
                  @change="(v)=>valueChange(v,row)"
                  :precision="2"
                  :min="0"
                  @blur="saveValue(row)"
                />
              </span>
              <span v-else>{{ toBit(row.govCost,true) }}</span>
            </span>
          </template>
        </vxe-table-column>
      </vxe-grid>
    </a-spin>
  </a-card>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
export default {
  name: 'RdSituationReport',
  mixins: [yearMiXin],
  components: {
  },
  data () {
    return {
      spin: false,
      tableData: [],
      disabled: false,
      hasModify: false
    }
  },
  created () {
    this.search()
  },
  methods: {
    saveValue (row) {
      if (!this.hasModify) {
        return
      }
      var value = {}
      value.govCost = row.govCost
      value.id = row.id
      this.$http.post('/summary/saveProjectGovCost', value)
        .then(res => {
          if (!res.success && !res.data) {
            this.$message.error(`[${row.pname}]-[政府资金]保存失败`)
            this.search()
          }
        }).finally(res => {
          this.hasModify = false
        })
    },
    valueChange (v, row) {
      if (Number(v) || v === 0) {
        this.$set(row, 'govCost', v)
        var govTotal = 0
        var totalRow = this.tableData[this.tableData.length - 1]
        this.tableData.forEach(item => {
          if (!item.isTotal) {
            govTotal += parseFloat(item.govCost)
          }
        })
        govTotal = this.toBit(govTotal, true)
        if (Number(totalRow.govCost) !== Number(govTotal)) {
          totalRow.govCost = govTotal
          this.hasModify = true
        }
      }
    },
    search () {
      if (!this.$auth('science:situation:view')) {
        this.$notification.error({ message: '无此权限，请跟管理员联系！' })
        return
      }
      this.spin = true
      this.$http.get('/summary/rdSituation', { params: { year: this.currentYear } })
        .then(res => {
          if (res.success && res.data && res.data.length > 0) {
            this.disabled = false
            var node = { stage: '本表合计', employee: 0.0, avgWork: 0.0, govCost: 0.0, cost: 0.0, isTotal: true, num: '' }
            const tempKeys = ['employee', 'avgWork', 'govCost', 'cost']
            res.data.forEach(e => {
              for (const temp of tempKeys) {
                node[temp] += parseFloat(e[temp]) ? parseFloat(e[temp]) : 0
              }
            })
            res.data.push(node)
            this.tableData = res.data
          } else {
            this.tableData = []
            this.disabled = true
          }
        }).finally(r => {
          this.spin = false
        })
    },
    toBit (v, toFixed) {
      const numV = Number(v)
      if (numV && numV !== 0) {
        v = (Math.round(parseFloat(v) * 100) / 100)
        if (toFixed) {
          v = v.toFixed(2)
        }
      }
      return v
    },
    exportReport () {
      this.$exportData('/summary/exportSituation', { year: this.currentYear }, `${this.currentYear}企业研究开发项目情况.xls`, this.$message)
    }
  }
}
</script>

<style>
</style>
