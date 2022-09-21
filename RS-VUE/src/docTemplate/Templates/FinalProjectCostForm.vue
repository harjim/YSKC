<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-09-14 12:13:30
 * @LastEditors: hm
 * @Description:项目费用决算报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\FinalProjectCostForm.vue
-->
<template>
  <a-card>
    <a-row >
      <a-col :span="12">
        <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider/>
    <a-tabs defaultActiveKey="1">
      <a-tab-pane tab="一、投资预计与实际支出情况" key="1">
        <!-- <cost-budget-form ref="costBudgetForm" :projectId="project.id" :isShow="false" ></cost-budget-form> -->
        <capital-budget v-if="project.id" :projectData="project" :isFilter="false" :isEdit="false" :isFinal="true"></capital-budget>
      </a-tab-pane>
      <a-tab-pane tab="二、核算总体情况" key="2">
        <a-textarea v-model="content.case" placeholder="核算总体情况" :rows="10" />
      </a-tab-pane>
      <a-tab-pane tab="三、项目费用总体完成情况分析" key="3">
        <a-textarea v-model="content.analyze" placeholder="项目费用总体完成情况分析" :rows="10" />
      </a-tab-pane>
      <a-tab-pane tab="四、项目经费投入及核算存在的问题" key="4">
        <a-textarea v-model="content.issue" placeholder="项目经费投入及核算存在的问题" :rows="10" />
      </a-tab-pane>
      <a-tab-pane tab="五、建议" key="5">
        <a-textarea v-model="content.suggest" placeholder="建议" :rows="10" />
      </a-tab-pane>
    </a-tabs>
    <!-- <template v-if="isShowAuditFooter">
      <a-divider/>
      <audit-footer
        ref="audtiFooter"
        :projectId="projectId"
        :docId="docId"
        :year="year"
        :employeeMap="employeeMap"
      />
    </template> -->
  </a-card>
</template>

<script>
import moment from 'moment'
import { CapitalBudget } from '@/components'
import AuditFooter from './modules/AuditFooter'
import CostBudgetForm from './CostBudgetForm'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'FinalProjectCostForm',
  components: {
    CostBudgetForm,
    AuditFooter,
    CapitalBudget
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('finalProjectCostForm')
    this.BPContent = getTemplateContent('finalProjectCostForm')
  },
  props: {
    isShowAuditFooter: {
      type: Boolean,
      default: true
    },
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      width: 960,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      content: {},
      BPContent: {},
      project: {},
      computedFields: ['case', 'analyze', 'issue', 'suggest']
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    },
    docId () {
      this.content = getTemplateContent('finalProjectCostForm')
      this.BPContent = getTemplateContent('finalProjectCostForm')
    }
  },
  methods: {
    moment,
    handleTemplateEvent () {
      // this.$refs.costBudgetForm.queryAllBudget()
    }
  }
}
</script>

<style lang="less" scoped>
.icon-interval {
  margin-left: 8px;
}
</style>
