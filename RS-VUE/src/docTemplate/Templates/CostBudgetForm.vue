<!-- 费用预算表 —— 默认模板 -->
<template>
  <a-card>
    <!-- <span>总预算: {{ totalBudget }} 万元</span> -->
    <!-- <cost-budget-tab ref="costBudgetTab" :projectData="project" @getAllBudget="queryAllBudget" @getTablDatas="getTablDatas" :isFilterData="true" /> -->
    <capital-budget :projectData="project" :isFilter="true" :isEdit="false"></capital-budget>
    <div v-if="isShow">
      <a-divider/>
      <audit-footer
        :projectId="projectId"
        :docId="docId"
        :year="project.beginYear"
        :currentYear="currentYear"
        :content.sync="content" />
    </div>
  </a-card>
</template>

<script>
// import moment from 'moment'
import { CapitalBudget } from '@/components'
import AuditFooter from './modules/AuditFooter'
import CostBudgetTab from './modules/CostBudgetTab'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
export default {
  name: 'CostBudgetForm',
  components: {
    CostBudgetTab,
    AuditFooter,
    CapitalBudget
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    isShow: {
      type: Boolean,
      default: true
    }
  },
  created () {
    this.content = getTemplateContent('costBudgetForm')
    this.BPContent = getTemplateContent('costBudgetForm')
  },
  data () {
    return {
      width: 1000,
      title: '',
      url: '/reviewCommittee/getReviewsSelect',
      currentYear: this.$store.state.currentYear,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 10 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: 0,
      confirmLoading: false,
      form: this.$form.createForm(this),
      visible: false,
      content: {},
      BPContent: {},
      project: {},
      selectKey: '10',
      tableDatas: [],
      totalBudget: 0
    }
  },
  // mounted () {
  //   this.queryAllBudget()
  // },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
      // this.queryAllBudget()
    },
    docId () {
      this.content = getTemplateContent('costBudgetForm')
      this.BPContent = getTemplateContent('costBudgetForm')
    }
  },
  methods: {
    refreshProject () {
      this.$emit('ok')
    },
    handleTemplateEvent () {
      this.queryAllBudget()
    },
    queryAllBudget () {
      if (this.projectId) {
        this.project.id = this.projectId
      }
      if (!this.project.id) { return }
      this.$http.get('/budget/queryTotalBudget', { params: Object.assign({ projectId: this.project.id }) })
        .then(res => {
          this.totalBudget = res.data
          this.content.totalBudget = res.data
          return res.data
        })
      if (this.$refs.costBudgetTab) {
        this.$refs.costBudgetTab.clearDataAndLoad()
      }
    },
    getTablDatas (tablDatas) {
      this.content.tablDatas = tablDatas
    }
  }
}
</script>

<style scoped>
</style>
