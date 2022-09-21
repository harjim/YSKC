<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 12:12:53
 * @LastEditors: hm
 * @Description: 研发月度总结报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\MonthSummaryReportForm.vue
-->
<template>
  <a-card>
    <a-row >
      <a-col :span="12">
        <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row >
      <a-col :span="12">
        <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;" required>
          <a-select v-model="content.dateMonth" @change="monthChange" style="width: 160px;" placeholder="请选择月份">
            <a-select-option :value="n" v-for="n in 12" :key="n">{{ n }}月</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :span="12">
      </a-col>
    </a-row>
    <a-divider/>
    <a-row >
      <a-form-item label="本月研发总结" :labelCol="contentLabelCol" :wrapperCol="contentWrapperCol" required>
        <a-textarea v-model="content.summary" placeholder="本月研发总结" :rows="10" />
      </a-form-item>
      <a-form-item label="下月研发计划" :labelCol="contentLabelCol" :wrapperCol="contentWrapperCol" required>
        <a-textarea v-model="content.inCase" placeholder="下月研发计划" :rows="10" />
      </a-form-item>
      <a-form-item label="研发意见和建议" :labelCol="contentLabelCol" :wrapperCol="contentWrapperCol" required>
        <a-textarea v-model="content.plan" placeholder="研发意见和建议" :rows="10" />
      </a-form-item>
    </a-row>
    <a-divider/>
    <audit-footer
      ref="audtiFooter"
      :projectId="projectId"
      :docId="docId"
      :year="year"
      :employeeMap="employeeMap"/>
  </a-card>
</template>
<script>
import moment from 'moment'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import AuditFooter from './modules/AuditFooter'
import dateMixin from './js/dateMixin'
export default {
  name: 'MonthSummaryReportForm',
  components: {
    AuditFooter
  },
  mixins: [dateMixin],
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
  created () {
    this.content = getTemplateContent('monthSummaryReportForm')
    this.BPContent = getTemplateContent('monthSummaryReportForm')
  },
  data () {
    return {
      domName,
      currentYear: this.$store.state.currentYear,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      contentLabelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      contentWrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      content: {},
      BPContent: {},
      projectName: '',
      project: {},
      requiredFields: ['dateMonth', 'inCase', 'plan', 'summary']
    }
  },
  methods: {
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    monthChange (value) {
      if (value) {
        this.fileDate = this.fileDate.month(value - 1)
        this.hsaFileDataNull = false
      } else {
        this.fileDate = moment(this.BUfileDate)
        this.hsaFileDataNull = true
      }
    }
  }
}
</script>
