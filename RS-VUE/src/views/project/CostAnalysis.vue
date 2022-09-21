<template>
  <a-card :bordered="false">
    <a-tabs defaultActiveKey="1">
      <a-tab-pane
        tab="资金预算对比分析表"
        key="1"
      >
        <cost-ratio ref="costRatio" />
      </a-tab-pane>
      <a-tab-pane
        tab="研发费占成本总额占比分析表"
        key="2"
      >
        <rd-cost-ratio ref="rDCostRatio" />
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
import CostRatio from './analysis/CostRatio'
import RdCostRatio from './analysis/RdCostRatio'
export default {
  components: {
    CostRatio,
    RdCostRatio
  },
  name: 'CostAnalysis',
  data () {
    return {
    }
  }
}
</script>

<style>
</style>
