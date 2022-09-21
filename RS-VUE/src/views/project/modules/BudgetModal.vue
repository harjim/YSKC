<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-04-06 10:05:35
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\BudgetModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="900"
    style="top:0;"
    :maskClosable="false"
    v-model="visible"
    @cancel="close"
    :destroyOnClose="true"
    @ok="close"
    :bodyStyle="{padding: '18px', maxHeight: '85vh', height: '85vh', overflow: 'auto'}"
  >
    <!-- <capital-budget :projectData="record" :isFilter="true" :isEdit="true" :isShowBtn="true" :isAssignHeight="true"></capital-budget> -->
    <budget
      ref="budget"
      :projectData="record"
      :isFilter="true"
      :isEdit="true"
      :isShowBtn="true"
      :isAssignHeight="true"
      @success="success"
      @close="close"></budget>
    <template slot="footer">
      <a-button @click="close">关闭</a-button>
    </template>
  </a-modal>
</template>

<script>
// import { CapitalBudget } from '@/components'
import Budget from '@/components/CapitalBudget/Budget.vue'
export default {
  name: 'BudgetModal',
  components: {
    Budget
    // CapitalBudget
  },
  data () {
    return {
      title: '',
      visible: false,
      record: {}
    }
  },
  methods: {
    close () {
      this.visible = false
    },
    add (record) {
      this.record = record
      this.title = '项目资金预算[' + record.pname + ']'
      this.visible = true
    },
    success (budget) {
      this.$emit('refreshBudget', this.record, { estimateExpense: budget })
    }
  }
}
</script>

<style lang="less" scoped>
#tabs /deep/ .ant-tabs-content {
  height: 91%;
  .ant-tabs-tabpane-active {
    height: 100%;
    overflow: auto;
  }
  .ant-tabs-tabpane-inactive {
    height: 100%;
    overflow: auto;
  }
}
</style>
