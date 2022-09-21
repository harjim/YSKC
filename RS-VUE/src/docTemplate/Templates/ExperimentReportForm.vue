<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2021-05-22 09:20:37
 * @LastEditors: ldx
 * @Description:实验验证报告 (20210522 停用)
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ExperimentReportForm.vue
-->
<template>
  <a-card>
    <a-form>
      <a-row >
        <a-col :span="12">
          <a-form-item :label="titleData.pname" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
            <span>{{ project.pname }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :label="titleData.rdIndex" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
            <span>{{ project.beginYear }}RD{{ project.rdIndex | ZeroFormat }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row >
        <a-col :span="12">
          <a-form-item :label="titleData.ename" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
            <span>{{ project.ename }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="titleData.disabledDate" style="margin-bottom: 0px;">
            <a-date-picker
              :getCalendarContainer="getCalendarContainer"
              @change="onDateChange"
              :allowClear="false"
              :value="content.beginDate ? moment(content.beginDate) : undefined"
              style="width:200px"
              :disabledDate="(val)=>disabledDate(val)"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row >
        <a-col :span="12">
          <a-form-item :label="titleData.actualPO" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
            <a-input-number
              :min="0"
              v-model="content.actualPO"
              :placeholder="titleData.actualPO"
              style="width:200px"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="titleData.address" style="margin-bottom: 0px;">
            <a-input v-model="content.address" :placeholder="titleData.address" style="width:200px" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-divider/>
      <a-row >
        <a-tabs defaultActiveKey="1">
          <a-tab-pane :tab="titleData.objectives" key="1">
            <a-textarea v-model="content.objectives" :placeholder="titleData.objectives" :rows="10" />
          </a-tab-pane>
          <a-tab-pane :tab="titleData.usageSituation" key="2">
            <a-textarea
              v-model="content.usageSituation"
              :placeholder="titleData.usageSituation"
              :rows="10"
            />
          </a-tab-pane>
          <a-tab-pane :tab="titleData.existingProblems" key="3">
            <a-textarea
              v-model="content.existingProblems"
              :placeholder="titleData.existingProblems"
              :rows="10"
            />
          </a-tab-pane>
          <a-tab-pane :tab="titleData.proposal" key="4">
            <a-textarea
              v-model="content.proposal"
              :placeholder="titleData.existingProblems"
              :rows="10"
            />
          </a-tab-pane>
        </a-tabs>
      </a-row>
      <a-divider/>
      <audit-footer
        :projectId="projectId"
        :docId="docId"
        :year="project.beginYear"
        :content.sync="content"
        :titleData="titleData"/>
    </a-form>
  </a-card>
</template>
<script>
import moment from 'moment'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import AuditFooter from './modules/AuditFooter'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
export default {
  components: {
    AuditFooter
  },
  name: 'ExperimentReportForm',
  created () {
    this.content = getTemplateContent('experimentReportForm')
    this.BPContent = getTemplateContent('experimentReportForm')
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
      domName,
      width: 960,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      titleData: {
        objectives: '',
        usageSituation: '',
        existingProblems: '',
        proposal: ''
      },
      content: {},
      BPContent: {},
      project: {}
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    }
  },
  methods: {
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    disabledDate (current) {
      return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    },
    onDateChange (dates, dateStr) {
      this.content.beginDate = dateStr
    }
  }
}
</script>

<style>
</style>
