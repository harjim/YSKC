<template>
  <a-spin :tip="tip" :spinning="spinning">
    <span>
      <a-button type="primary" @click="exportDeclaration">导出技改项目申请报告</a-button>
    </span>
    <a-tabs :defaultActiveKey="key" size="small" @change="tabChange">
      <a-tab-pane
        v-for="tabKey in Object.keys(tabPane)"
        :tab="tabPane[tabKey].name"
        :key="`${tabKey}_${projectId}`"
      >
        <DeclarationPane
          :key="`${tabKey}_${projectId}`"
          :type="`${tabKey}`"
          :projectId="projectId"
          @handleSubmit="handlePaneSubmit"
        ></DeclarationPane>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>
<script>
import moment from 'moment'
import CompanySurveyPane from './panes/CompanySurveyPane'
import ConditionsPane from './panes/ConditionsPane'
import DevelopmentPane from './panes/DevelopmentPane'
import ImpactAnalysisPane from './panes/ImpactAnalysisPane'
import ProjectSurveyPane from './panes/ProjectSurveyPane'
import RiskPane from './panes/RiskPane'
const tabPane = {
  'tab_1': {
    key: '1',
    name: '第一章 申报单位概况',
    component: CompanySurveyPane
  },
  'tab_2': {
    key: '2',
    name: '第二章 项目概况',
    component: ProjectSurveyPane
  },
  'tab_3': {
    key: '3',
    name: '第三章 项目实施条件',
    component: ConditionsPane
  },
  'tab_4': {
    key: '4',
    name: '第四章 经济和社会影响分析',
    component: ImpactAnalysisPane
  },
  'tab_5': {
    key: '5',
    name: '第五章 项目对企业的发展作用',
    component: DevelopmentPane
  },
  'tab_6': {
    key: '6',
    name: '第六章 风险因素',
    component: RiskPane
  }

}
export default {
  name: 'Declaration',
  components: {
    'DeclarationPane': {
      functional: true,
      render: function (createElement, context) {
        const d = context.data
        return createElement(
          tabPane[d.attrs.type].component,
          context.data,
          context.children
        )
      }
    }
  },
  data () {
    return {
      spinning: false,
      tip: '请稍后...',
      tabPane,
      dataMap: {},
      key: 'tab_1'
    }
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    }
  },
  created () {
    this.tabChange(`tab_1_${this.projectId}`)
  },
  computed: {
  },
  methods: {
    moment,
    handlePaneSubmit (self, value) {
      this.$http.post('/techProject/saveDeclarationInfoList', value)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
        }).finally(res => {
          self.saveLoading = false
        })
    },
    tabChange (activeKey) {
      this.key = activeKey
    },
    exportDeclaration () {
      this.spinning = true
      this.tip = '正在导出，请稍后...'
      this.$exportData('/techProject/exportDeclarationInfo', { projectId: this.projectId }, `项目技改申请报告.docx`, this.$message).finally(res => {
        this.spinning = false
      })
    }
  }
}
</script>
