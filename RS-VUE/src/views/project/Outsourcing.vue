<template>
  <a-card style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}">
    <a-tabs v-model="actvieKey" @change="callback" id="tabs">
      <a-tab-pane tab="国内委托费用" key="0">
        <outsourcing-tab ref="insideTab" :type="0"/>
      </a-tab-pane>
      <a-tab-pane tab="国外委托费用" key="1">
        <outsourcing-tab ref="outsideTab" :type="1"/>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
import OutsourcingTab from './modules/OutsourcingTab'

export default {
  components: {
    OutsourcingTab
  },
  data () {
    return {
      actvieKey: '0'
    }
  },
  methods: {
    callback (activeKey) { // 切换tab调用
      this.actvieKey = activeKey
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
</style>
