<!--
 * @Author:
 * @Date: 2021-02-02 11:27:51
 * @LastEditTime: 2022-09-14 12:01:39
 * @LastEditors: hm
 * @Description:研发人员参与项目汇总表 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\RdEmploeeSummaryForm.vue
-->
<template>
  <a-card>
    <a-table
      bordered
      rowKey="num"
      :dataSource="list"
      :pagination="false"
      size="small"
      :scroll="{y:480}"
    >
      <a-table-column title="序号" data-index="num" :width="60"></a-table-column>
      <a-table-column title="姓名" data-index="ename" :width="120"></a-table-column>
      <a-table-column title="研发部门" data-index="rdDeptName" :width="120" />
      <a-table-column title="项目角色" data-index="role" :width="120" />
      <a-table-column title="本年参与工作量(小时)" data-index="yearWorkOurs" :width="120" />
      <a-table-column title="签字" :width="120" />
    </a-table>
    <a-divider />
    <audit-footer
      ref="audtiFooter"
      :projectId="projectId"
      :docId="docId"
      :year="year"
      :employeeMap="employeeMap"
    />
  </a-card>
</template>

<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import { STable, Ellipsis, SelectDown, TreeKeyMap } from '@/components'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'RdEmploeeSummaryForm',
  components: {
    AuditFooter,
    STable,
    Ellipsis,
    SelectDown,
    TreeKeyMap
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('rdEmploeeSummaryForm')
    this.BPContent = getTemplateContent('rdEmploeeSummaryForm')
  },
  props: {
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
      width: 1000,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: 0,
      content: {},
      BPContent: {},
      project: {},
      fileInfo: {},
      list: [],
      years: []
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
      this.initYears()
    },
    'fileInfo.currentYear' (newValue) {
      this.content.employeeYear = newValue
    },
    docId () {
      this.content = getTemplateContent('rdEmploeeSummaryForm')
      this.BPContent = getTemplateContent('rdEmploeeSummaryForm')
    }
  },
  methods: {
    moment,
    initYears () {
      if (this.project == null) {
        return []
      }
      var endDate = this.project.endDate
      var beginDate = this.project.beginDate
      if (typeof endDate === 'undefined' || typeof beginDate === 'undefined') {
        return []
      }
      var beginArray = beginDate.split('-').map(Number)
      var endArray = endDate.split('-').map(Number)
      var returnArry = []
      var begin = parseInt(beginArray[0])
      var end = parseInt(endArray[0])

      for (let y = begin; y <= end; y++) {
        returnArry.push(y)
      }
      this.years = returnArry
    },
    handleTemplateEvent () {
      this.querylist()
    },
    querylist () {
      this.$nextTick(() => {
        this.$http.get('/projectRdEmployee/getListByYear', { params: { projectId: this.project.id, pDocFileId: this.docId } })/*, year: this.fileInfo.currentYear */
          .then(res => {
            const data = res.data
            this.list = data.map((item, index) => {
              item['num'] = index + 1
              return item
            })
            this.content.lists = this.list
            return res.data
          })
      })
    }
  }
}
</script>

<style scoped>
</style>
