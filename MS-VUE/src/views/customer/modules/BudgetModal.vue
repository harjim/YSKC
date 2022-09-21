<template>
  <a-modal
    :title="title"
    :width="1000"
    style="top:0;"
    :maskClosable="false"
    v-model="visible"
    @cancel="close"
    :destroyOnClose="true"
    :confirmLoading="confirmLoading"
    :bodyStyle="{maxHeight: '85vh', height: '85vh', overflow: 'auto'}"
  >
    <capital-budget v-if="project.id" :isFilter="true" :projectData="project" @getBudgetSum="getBudgetSum"></capital-budget>
    <template slot="footer">
      <a-button type="primary" @click="close">关闭</a-button>
      <span
        style="padding-left:10px;"
        v-if="$auth('customer:finalization:final') && project.status !== 3"
      >
        <a-button :disabled="!budgetSum" type="primary" @click="final()">审核</a-button>
        <!-- <a-button type="primary" @click="final()">审核</a-button> -->
      </span>
      <span
        style="padding-left:10px;"
        v-if="$auth('customer:finalization:recall') && project.status === 3"
      >
        <!-- <a-button type="primary" @click="recall()">撤回</a-button> -->
        <a-button :disabled="!budgetSum" type="primary" @click="recall()">撤回</a-button>
      </span>
    </template>
  </a-modal>
</template>

<script>
import { CapitalBudget } from '@/components/'

export default {
  name: 'BudgetModal',
  components: {
    CapitalBudget
  },
  data () {
    return {
      title: '',
      confirmLoading: false,
      visible: false,
      project: {},
      year: undefined,
      budgetSum: 0
      // selectKey: 'capitalbudgetTab'
    }
  },
  methods: {
    close () {
      this.visible = false
      // this.selectKey = 'capitalbudgetTab'
    },
    show (row, year) {
      this.title = '项目资金预算[' + row.pname + ']'
      this.confirmLoading = false
      this.visible = true
      this.project = row
      this.year = year
    },
    final () {
      this.$http.post('/rsProject/budgetFinal', { projectId: this.project.id, companyId: this.project.companyId })
        .then(res => {
          if (res.success && res.data) {
            this.close()
            this.project.status = 3
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '审核失败')
          }
        })
    },
    recall () {
      this.$http.post('/rsProject/budgetRecall', { projectId: this.project.id, companyId: this.project.companyId })
        .then(res => {
          if (res.success && res.data) {
            this.close()
            this.project.status = 4
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '撤回失败')
          }
        })
    },
    getBudgetSum (data) {
      this.budgetSum = data
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
