<!--
 * @Author: zdf
 * @Date: 2022-04-25 15:55:09
 * @LastEditTime: 2022-05-11 15:39:21
 * @LastEditors: zdf
 * @Description: 归集审批抽屉
 * @FilePath: \MS-VUE\src\views\innovation\modules\RdFeeAuditDrawer.vue
-->
<template>
  <a-drawer
    id="fullBox"
    :width="width"
    placement="right"
    :closable="true"
    :destroyOnClose="true"
    :headerStyle="{paddingBottom:'0',marginBottom: '0'}"
    :visible="visible"
    :bodyStyle="{padding: '12px 24px'}"
    @close="onClose()"
  >
    <template #title>
      <div style="display:flex;flex-direction: row;">
        <span>{{ title }}</span>
        <month-fee-table style="padding-left:10px;" ref="monthFeeTable" :queryParams="queryParams" :auditCostTypes="auditCostTypes"/>
        <div style="padding-left:10px;flex:1;text-align:right;padding-right:36px;">
          <a-button :title="isFullscreen ? '还原' : '全屏'" size="small" icon="fullscreen" @click="handleFull()"></a-button>
        </div>
      </div>
    </template>
    <div style="height: 100%; background-color: white; padding:5px 5px;" >
      <a-spin :spinning="spin" tip="请稍后...">
        <a-tabs
          id="feeTabs"
          v-model="activeKey"
          @change="tabChange"
          size="small">
          <a-tab-pane v-for="item in auditCostTypes" :key="item.rdType">
            <template #tab>
              <a-badge
                v-if="auditCnts[item.rdType]"
                :offset="[10,-2]"
                :count="auditCnts[item.rdType]"
                :overflow-count="99">{{ item.title }}</a-badge>
              <span v-else>{{ item.title }}</span>
            </template>
            <rd-fee-table
              :key="item.rdType"
              :queryParams="queryParams"
              :rdType="item.rdType"
              :costType="auditCostTypesMap[item.rdType]"
              :m="params.m"
              @freshCnt="loadAuditCnt"/>
          </a-tab-pane>
        </a-tabs>
      </a-spin>
    </div>
  </a-drawer>
</template>

<script>
import { auditCostTypes, auditCostTypesMap } from './auditModules/FeeDetailConfig'
import MonthFeeTable from './auditModules/MonthFeeTable.vue'
import RdFeeTable from './auditModules/RdFeeTable.vue'
import screenfull from 'screenfull'
export default {
  components: {
    RdFeeTable,
    MonthFeeTable,
    screenfull
  },
  data () {
    return {
      width: 1184,
      title: '',
      visible: false,
      queryParams: {},
      auditCostTypes,
      auditCostTypesMap,
      feeAmounts: {},
      companyId: 0,
      activeKey: '',
      params: {},
      spin: false,
      isFullscreen: false,
      isEnabledFullscreen: false,
      auditCnts: {},
      closeFresh: false
    }
  },
  watch: {
    isFullscreen (n) {
      this.full()
    }
  },
  mounted () {
    this.isEnabledFullscreen = screenfull.isEnabled
    const self = this
    window.onresize = () => {
      var isFull = screenfull.isFullscreen
      if (isFull === undefined) {
        isFull = false
      }
      if (!isFull) {
        self.$nextTick(() => {
          self.width = 1184
          self.isFullscreen = false
        })
      }
    }
  },
  methods: {
    show (row) {
      this.tabChange(this.auditCostTypes[0].rdType)
      this.params = row
      this.title = `财务审批-【${row.year}-${row.companyName}】${row.m}份归集费用`
      this.queryParams = { companyId: row.companyId, month: row.month }
      this.visible = true
      this.loadAuditCnt()
    },
    loadAuditCnt (refresh) {
      if (refresh) {
        this.closeFresh = refresh
      }
      this.spin = true
      this.$http.get('/rdFeeAudit/getAuditCnt', { params: this.queryParams }).then(res => {
        let auditCnts = {}
        if (res.success && res.data) {
          auditCnts = res.data
        } else {
          this.$message.error(res.errorMessage || '获取审核统计失败')
        }
        this.auditCnts = auditCnts
      }).finally(() => {
        this.spin = false
      })
    },
    tabChange (k) {
      this.activeKey = k
    },
    onClose () {
      if (this.closeFresh) {
        this.$emit('ok')
      }
      this.closeFresh = false
      this.width = 1184
      this.visible = false
      this.isFullscreen = false
    },
    handleFull () {
      this.isFullscreen = !this.isFullscreen
    },
    // 全屏
    full () {
      if (!this.isEnabledFullscreen) {
        this.$message.warning('您现在使用的浏览器不支持全屏显示！')
        return
      }

      const element = document.getElementById('fullBox')
      if ((this.isFullscreen && !screenfull.isFullscreen) || (!this.isFullscreen && screenfull.isFullscreen)) {
        screenfull.toggle(element)
      }
      if (!this.isFullscreen) {
        this.width = 1184
      } else {
        this.width = '100%'
      }
    }
  }
}
</script>
<style scoped lang="less">
/deep/ #feeTabs{
.ant-tabs-nav .ant-tabs-tab{
  margin:0 8px 0 0;
}
}
</style>
