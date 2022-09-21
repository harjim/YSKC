<!--
 * @Author: ldx
 * @Date: 2021-07-15 11:07:14
 * @LastEditTime: 2022-03-25 14:20:37
 * @LastEditors: lzh
 * @Description: 提案管理
 * @FilePath: \RS-VUE\src\views\project\ProposalManagement.vue
-->
<template>
  <a-card class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto', padding: '12px 12px'}">
    <a-tabs @change="onChange">
      <a-tab-pane key="1" tab="列表"></a-tab-pane>
      <a-tab-pane key="2" tab="附件"></a-tab-pane>
    </a-tabs>
    <component :is="component" />
  </a-card>
</template>

<script>
import ProposalListTab from './modules/ProposalListTab.vue'
import ProposalManagementTab from './modules/ProposalManagementTab.vue'
export default {
  components: {
    ProposalListTab,
    ProposalManagementTab
  },
  data () {
    return {
      component: 'ProposalListTab'
    }
  },
  methods: {
    onChange (activeKey) {
      if (activeKey === '1') {
        this.component = 'ProposalListTab'
      } else {
        this.component = 'ProposalManagementTab'
      }
    }
  }
}
</script>

<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
}
#spin {
  height: 100%;
  width: 100%;
  & /deep/ .ant-spin-nested-loading {
    height: 100%;
  }
  & /deep/ .ant-spin-container {
    height:  100%;
  }
}
</style>
