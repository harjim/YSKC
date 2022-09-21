<!--
 * @Author: zdf
 * @Date: 2021-03-08 08:04:25
 * @LastEditTime: 2022-04-06 09:02:49
 * @LastEditors: zdf
 * @Description: 研发人员计划工时
 * @FilePath: \RS-VUE\src\views\project\RdEmployeePlan.vue
-->
<template>
  <a-card :bordered="false">
    <a-tabs v-model="activeKey" @change="tabChange">
      <a-tab-pane key="1" tab="计划">
        <rd-employee-plan-tab :currentKey="activeKey"/>
      </a-tab-pane>
      <a-tab-pane key="2" tab="汇总">
        <rd-employee-plan-total-tab :currentKey="activeKey"/>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
import RdEmployeePlanTab from './modules/RdEmployeePlanTab'
import RdEmployeePlanTotalTab from './modules/RdEmployeePlanTotalTab'
export default {
  name: 'RdEmployeePlan',
  components: {
    RdEmployeePlanTab,
    RdEmployeePlanTotalTab
  },
  data () {
    return {
      activeKey: '1'
    }
  },
  methods: {
    tabChange (k) {
      this.activeKey = k
    }
  }
}
</script>
<style lang="less" scoped>
</style>
