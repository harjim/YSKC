<template>
  <a-spin tip="请稍后..." :spinning="spinning">
    <a-card v-if="$auth('project:subsidiaryLedger:view')">
      <div style="margin-right:10px; margin-bottom:10px;">
        <a-button
          style="margin-right:10px;"
          type="primary"
          :disabled="ledgerList.length <= 0"
          v-if="$auth('project:subsidiaryLedger:export')"
          @click="exportSubsidiaryLedger()"
        >导出</a-button>
        <a-button
          style="margin-right:10px;"
          type="primary"
          :disabled="ledgerList.length <= 0"
          v-if="$auth('project:subsidiaryLedger:projectExport')"
          @click="exportByProject(1)"
        >按项目导出</a-button>
        <a-button
          type="primary"
          style="margin-right:10px;"
          :disabled="ledgerList.length <= 0"
          v-if="$auth('project:subsidiaryLedger:typeExport')"
          @click="exportByProject(2)"
        >按费用类型导出</a-button>
        <a-button
          type="primary"
          v-if="$auth('project:subsidiaryLedger:view')"
          @click="changeSearch"
        >切换</a-button>
      </div>
      <div>
        <a-collapse :bordered="false" v-if="ledgerList.length > 0">
          <a-collapse-panel :key="index" :style="customStyle" v-for="(item,index) in ledgerList">
            <template slot="header">
              <span>{{ index+1+'、'+item.title+' (单位/元) 合计:' }}{{ getTotal(item.data[item.data.length - 1]) }}</span>
            </template>
            <vxe-grid
              ref="table"
              highlight-hover-row
              show-overflow
              resizable
              auto-resize
              border="full"
              :data="item.data"
            >
              <vxe-table-column
                title="项目名"
                field="rdPname"
                align="left"
                min-width="150"
                fixed="left"
              ></vxe-table-column>
              <vxe-table-column title="工艺线/车间" field="workshop" min-width="120" align="left"></vxe-table-column>
              <vxe-table-column
                :title="month"
                :field="month"
                width="100"
                align="right"
                v-for="(month, i) in months"
                :key="i"
              >
                <template v-slot="{ row }">{{ row[month] !== null ? row[month] : '--' }}</template>
              </vxe-table-column>
              <vxe-table-column title="合计" field="rdFunds" align="right" width="100">
                <template v-slot="{ row }">{{ getTotal(row) }}</template>
              </vxe-table-column>
            </vxe-grid>
          </a-collapse-panel>
        </a-collapse>
      </div>
    </a-card>
  </a-spin>
</template>
<script>
import { mapGetters } from 'vuex'
import { TreeKeyMap } from '@/components'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
export default {
  mixins: [yearMiXin],
  components: {
    TreeKeyMap
  },
  data () {
    return {
      spinning: false,
      customStyle: 'border-radius: 8px;margin-bottom: 12px;border:1px solid #E1E5ED;overflow: hidden',
      ledgerList: [],
      child: false
    }
  },
  created () {
    this.search()
  },
  computed: {
    months () {
      const monthArr = []
      for (let i = 1; i <= 12; i++) {
        monthArr.push(`${this.currentYear}-${i < 10 ? '0' + i : i}`)
      }
      return monthArr
    }
  },
  methods: {
    changeSearch () {
      this.child = !this.child
      this.search()
    },
    getTotal (row, allTotal) {
      var total = 0
      this.months.forEach(item => {
        total += row[item]
        if (allTotal) {
          if (!allTotal[item] || allTotal <= 0) {
            allTotal[item] = 0
          }
          allTotal[item] += row[item]
        }
      })
      return total.toFixed(2)
    },
    ...mapGetters(['userInfo']),
    exportSubsidiaryLedger () {
      if (!this.ledgerList || this.ledgerList.length <= 0) {
        this.$message.warinning('当前无可导出的数据')
        return
      }
      this.spinning = true
      this.$exportData('/aggregation/exportSubsidiaryLedger', { year: this.currentYear }, `${this.userInfo().companyName}${this.currentYear}归集明细表.xls`, this.$message).then(res => {
        this.spinning = false
      })
    },
    exportByProject (exportType) {
      this.spinning = true
      this.$exportData('/aggregation/exportDataDetail', { year: this.currentYear, exportType: exportType }, `${this.userInfo().companyName}${this.currentYear}${exportType === 1 ? '项目' : '费用类型'}归集明细表.xls`, this.$message).then(res => {
        this.spinning = false
      })
    },
    search () {
      this.ledgerList = []
      this.spinning = true
      this.$nextTick(() => {
        this.$http.get('/project/querySubsidiaryLedger', { params: { year: this.currentYear, child: this.child } })
          .then(res => {
            if (res.data) {
              this.ledgerList = res.data
            }
            this.spinning = false
            return res.data
          })
      })
    },
    moment
  }
}
</script>
