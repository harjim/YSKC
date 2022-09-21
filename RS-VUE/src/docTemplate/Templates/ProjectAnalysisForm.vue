<!--
 * @Author: zdf
 * @Date: 2021-10-13 08:16:32
 * @LastEditTime: 2022-09-14 12:13:06
 * @LastEditors: hm
 * @Description: 项目进度情况分析表 项目完成情况分析表 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ProjectAnalysisForm.vue
-->
<template>
  <a-card>
    <a-spin :spinning="loading" tip="请稍后..." style="width:100%;">
      <div style="width:100%">
        <a-tooltip style="cursor:pointer" placement="top">
          <template slot="title">
            <span>刷新项目完成情况分析表最新数据</span>
          </template>
          <a-button type="primary" @click="refreshData()" :loading="loading">刷新</a-button>
        </a-tooltip>
      </div>

      <div style="width:100%">
        <a-col :span="6">
          <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom:0px;">
            <span>{{ project.rdTitle }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom:0px;">
            <span>{{ project.pname }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="填写时间" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom:0px;" required>
            <a-date-picker
              :getCalendarContainer="getCalendarContainer"
              :disabledDate="disabledDate"
              :value="content.fillDate ? moment(content.fillDate,'YYYY-MM-DD') : content.fillDate"
              format="YYYY-MM-DD"
              style="width:100%"
              @change="onDateChange"
              @openChange="(status) => onOpenChange(status,'fillDate')"
            />
          </a-form-item>
        </a-col>
      </div>
      <a-table
        :useLoading="false"
        bordered
        style="padding-top:10px;width:100%;"
        :dataSource="list"
        :pagination="false"
        size="small"
        rowKey="index"
        :scroll="{y:500}">
        <a-table-column title="序号" data-index="index" :width="60"></a-table-column>
        <a-table-column title="指标项" data-index="label" :width="100"></a-table-column>
        <a-table-column data-index="expectation" :width="280">
          <template #title>
            <i class="required-field"/>预计目标值
          </template>
          <template slot-scope="text,record">
            <a-input-number
              v-if="record.rewrite"
              :value="content[`expectation${record.index}`]"
              :min="0"
              :placeholder="`请输入${record.label}预计目标值`"
              style="width:100%"

              :precision="0"
              @change="(v)=>onCellChange(v,'expectation',record.index)"
            />
            <template v-else><div v-html="content[`expectation${record.index}`]|| '-'"></div></template>
          </template>
        </a-table-column>
        <a-table-column data-index="actuality" :width="280">
          <template #title>
            <i class="required-field"/>实际达成值
          </template>
          <template slot-scope="text,record">
            <div v-html="content[`actuality${record.index}`] || '-'"></div>
          </template>
        </a-table-column>
        <a-table-column title="差异情况分析" data-index="analysis" :width="240">
          <template slot-scope="text,record">
            <a-textarea
              :value="content[`analysis${record.index}`]"
              :rows="3"
              :placeholder="`请输入${record.label}差异情况分析`"
              @change="(e)=>onCellChange(e.target.value,'analysis',record.index)"
            />
          </template>
        </a-table-column>
      </a-table>
      <div style="width: 100%">
        <audit-footer
          ref="audtiFooter"
          :projectId="projectId"
          :docId="docId"
          :year="year"
          :employeeMap="employeeMap"
        />
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import moment from 'moment'

import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import AuditFooter from './modules/AuditFooter'
import dateMixin from './js/dateMixin'
export default {

  name: 'ProjectAnalysisForm',
  components: {
    AuditFooter
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
    project: {
      type: Object,
      required: true
    }
  },
  mixins: [dateMixin],

  data () {
    return {
      list: [
        { index: 1, label: '项目周期', analysis: undefined },
        { index: 2, label: '费用使用情况', analysis: undefined },
        { index: 3, label: '试验试制次数', analysis: undefined, rewrite: true },
        { index: 4, label: '会议次数', analysis: undefined, rewrite: true },
        { index: 5, label: '试制量', analysis: undefined, rewrite: true },
        { index: 6, label: '参与人数', analysis: undefined },
        { index: 7, label: '使用仪器设备数', analysis: undefined },
        { index: 8, label: '项目主要内容', analysis: undefined },
        { index: 9, label: '项目创新点', analysis: undefined },
        { index: 10, label: '项目指标', analysis: undefined },
        { index: 11, label: '多层级研发管理', analysis: undefined, rewrite: true },
        { index: 12, label: '研发成果管理', analysis: undefined, rewrite: true },
        { index: 13, label: '知识产权管理', analysis: undefined, rewrite: true },
        { index: 14, label: '查新', analysis: '-', rewrite: true }
      ],
      loading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      content: {},
      BPContent: {},
      currentYear: 2020,
      row: { beginDate: undefined, endDate: undefined, mainWork: '' },
      requiredFields: ['fillDate', 'expectation3', 'expectation4', 'expectation5', 'expectation11', 'expectation12', 'expectation13', 'expectation14']
    }
  },
  created () {
    this.content = getTemplateContent('ProjectAnalysisForm')
    this.BPContent = getTemplateContent('ProjectAnalysisForm')
  },
  methods: {
    getCalendarContainer (trigger) {
      return popupContainer(domName)
    },
    disabledDate (current) {
      return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    },
    onDateChange (date, dateString) {
      if (!dateString) {
        this.content.fillDate = date
        if (this.stage && this.stage.beginDate) {
          this.fileDate = moment(this.stage.beginDate).startOf('month')
        } else {
          this.fileDate = moment(this.BUfileDate).startOf('month')
        }
        this.hsaFileDataNull = true
        return
      }
      this.hsaFileDataNull = false
      this.fileDate = moment(dateString).startOf('month')
      if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
        this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
      }
      this.content.fillDate = dateString
    },
    moment,
    onCellChange (v, key, index) {
      this.$set(this.content, `${key}${index}`, v)
    },
    refreshData () {
      this.loading = true
      this.$http.get('/projectDocFileData/getProjectAnalysis', { params: { projectId: this.projectId } }).then(res => {
        if (res.success && res.data) {
          for (const key in res.data) {
            this.$set(this.content, key, res.data[key])
          }
        } else {
          this.$message.error(res.errorMessage || '刷新失败，请联系管理员。')
        }
        this.loading = false
      })
    }
  }
}
</script>

<style>

</style>
