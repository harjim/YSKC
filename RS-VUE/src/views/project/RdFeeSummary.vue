<template>
  <a-card :border="false">
    <a-spin :spinning="spinning" tip="加载中...">
      <div
        style="width:100%;scroll-x:auto;scroll-y:auto;"
        v-if="control.search && ledgerList.length > 0"
      >
        <div v-if="control.export" style="margin-bottom:20px;">
          <a-button type="primary" @click="exportData">导出</a-button>
        </div>
        <a-tabs :defaultActiveKey="0">
          <template v-if="ledgerList && ledgerList.length">
            <a-tab-pane
              v-for="(_, idx) in ledgerList.length"
              :key="idx"
              :tab="ledgerList[idx].title"
            >
              <fee-summary-tab :dataMap="ledgerList[idx]" :year="currentYear" :isType="false" />
            </a-tab-pane>
          </template>
        </a-tabs>
      </div>
      <div v-else style="width:100%;text-align:center;">
        <a-empty />
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import FeeSummaryTab from './modules/rdGneralLedgerTab/FeeSummaryTab.vue'
import yearMixin from '@/utils/yearMixin'

export default {
  mixins: [yearMixin],
  components: {
    FeeSummaryTab
  },
  data () {
    return {
      spinning: false,
      ledgerList: [],
      activeKey: '0',
      control: {
        search: this.$auth('project:RdFeeSummary:search'),
        export: this.$auth('project:subsidiaryLedger:projectExport')
      }
    }
  },
  created () {
    this.search()
  },
  methods: {
    search () {
      this.ledgerList = []
      this.spinning = true
      this.$nextTick(() => {
        this.$http.get('/aggregation/getDataDetail', {
          params: {
            year: this.currentYear,
            exportType: 1
          }
        }).then(res => {
          if (res.data) {
            this.ledgerList = res.data
          }
          this.spinning = false
          return res.data
        })
      })
    },
    exportData () {
      this.spinning = true
      this.$exportData('/aggregation/exportDataDetail', {
        year: this.currentYear,
        exportType: 1
      }, `${this.currentYear}项目归集明细表.xls`, this.$message).then(res => {
        this.spinning = false
      })
    }
  }
}
</script>

<style>
</style>
