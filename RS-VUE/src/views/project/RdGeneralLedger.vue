<!--
 * @Author: zdf
 * @Date: 2021-07-14 17:03:59
 * @LastEditTime: 2022-03-02 11:30:34
 * @LastEditors: zdf
 * @Description: 研发辅助帐
 * @FilePath: \RS-VUE\src\views\project\RdGeneralLedger.vue
-->
<template>
  <a-card :bordered="false">
    <a-spin :spinning="spinning" tip="请稍后...">
      <div style="width:100%;scroll-x:auto;scroll-y:auto;" v-if="control.view">
        <div v-if="control.export" style="margin-bottom:20px;">
          <a-button type="primary" @click="exportData">导出</a-button>
        </div>
        <a-tabs v-model="activeKey">
          <a-tab-pane key="0" :tab="dataMap.tabs[0]">
            <component :is="dataMap.isShangHai ? rdComs.shangHai.leder : rdComs.normal.leder" :dataMap="dataMap.firstMap" />
          </a-tab-pane>
          <template v-if="dataMap.list && dataMap.list.length">
            <a-tab-pane v-for="(rd,index) in dataMap.list.length" :key="index+1" :tab="dataMap.tabs[index + 1]">
              <component :is="dataMap.isShangHai ? rdComs.shangHai.rd : rdComs.normal.rd" :dataMap="dataMap.list[index]" />
            </a-tab-pane>
          </template>
        </a-tabs>
      </div>
    </a-spin>
    <ExportGeneralLedgerModal ref="exportGeneralLedgerModal" @ok="exportGeneralLedger"/>
  </a-card>
</template>
<script>
import ShangHaiLederTab from './modules/rdGneralLedgerTab/ShangHaiLederTab.vue'
import ShangHaiRdTab from './modules/rdGneralLedgerTab/ShangHaiRdTab.vue'
import NormalLederTab from './modules/rdGneralLedgerTab/NormalLederTab.vue'
import NormalRdTab from './modules/rdGneralLedgerTab/NormalRdTab.vue'
import yearMixin from '@/utils/yearMixin'
import { mapGetters } from 'vuex'
import ExportGeneralLedgerModal from './modules/ExportGeneralLedgerModal'
const rdComs = { shangHai: { leder: ShangHaiLederTab, rd: ShangHaiRdTab }, normal: { leder: NormalLederTab, rd: NormalRdTab } }
export default {
  mixins: [yearMixin],
  components: {
    ExportGeneralLedgerModal
  },
  data () {
    return {
      rdComs,
      tableData: [],
      dataMap: { tabs: ['辅助帐总表'] },
      spinning: false,
      activeKey: '0',
      control: {
        view: this.$auth('project:rdGeneralLedger:view'),
        export: this.$auth('project:rdGeneralLedger:export')
      }
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    search () {
      this.loadData()
    },
    loadData () {
      this.activeKey = '0'
      this.spinning = true
      this.$http.get('/project/getGeneralLedgerData', { params: { year: this.currentYear } }).then(res => {
        if (res.success && res.data) {
          this.dataMap = res.data
        } else {
          this.dataMap = { tabs: ['辅助帐总表'] }
          this.$message.error(res.errorMessage || '加载失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    ...mapGetters(['userInfo']),
    exportData () {
      if (this.$store.state.generalLedgerYear <= this.currentYear) {
        this.$refs.exportGeneralLedgerModal.show(this.currentYear)
      } else {
        this.exportGeneralLedger(true)
      }
    },
    exportGeneralLedger (old) {
      this.spinning = true
      this.$exportData('/project/exportGeneralLedger', { year: this.currentYear, old }, `${this.userInfo().companyName}${this.currentYear}年辅助账总表.xls`, this.$message).then(res => {
        this.spinning = false
      })
    }
  }
}
</script>

<style>
</style>
