<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 11:59:39
 * @LastEditors: hm
 * @Description: 设计开发方案及附件 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\DevForm.vue
-->
<template>
  <a-card>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目名称"
          style="margin-bottom: 0px;">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目编号"
          style="margin-bottom: 0px;">
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="开发周期"
          style="margin-bottom: 0px;">
          {{ handleDate(project.beginDate) }} 至 {{ handleDate(project.endDate)
          }}
          <!-- 20210205 邓总要求取项目起止日期 -->
          <!-- <a-month-picker
            :getCalendarContainer="getCalendarContainer"
            @change="onBeginChange"
            style="width:45%"
            :allowClear="false"
            :value="content.beginDate?moment(content.beginDate,'YYYY-MM'):undefined"
          />~
          <a-month-picker
            :getCalendarContainer="getCalendarContainer"
            @change="onEndChange"
            style="width:45%"
            :allowClear="false"
            :value="content.endDate?moment(content.endDate,'YYYY-MM'):undefined"
          /> -->
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="负责人"
          style="margin-bottom: 0px;">
          <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : ''
          }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider />
    <a-row>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane key="1">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="研发目的"
            />
          </template>
          <a-textarea
            v-model="content.objectives"
            :rows="10"
            placeholder="研发目的" />
        </a-tab-pane>
        <a-tab-pane key="2">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getPopupContainer"
              required
              text="开发方案"
            />
          </template>
          <a-textarea v-model="content.odp" :rows="10" placeholder="开发方案" />
        </a-tab-pane>
        <a-tab-pane key="3">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getPopupContainer"
              required
              text="关键指标"
            />
          </template>
          <a-textarea v-model="content.kPI" :rows="10" placeholder="关键指标" />
        </a-tab-pane>
      </a-tabs>
    </a-row>
    <a-divider />
    <audit-footer
      ref="audtiFooter"
      :docId="docId"
      :employeeMap="employeeMap"
      :projectId="projectId"
      :year="year"
    />
  </a-card>
</template>
<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import {
  domName,
  popupContainer
} from '@/docTemplate/Templates/js/screenFullMountDom'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from '../Templates/js/dateMixin'
import YsTooltip from '@/components/YsTooltip/YsTooltip'

export default {
  name: 'DevForm',
  components: {
    AuditFooter,
    YsTooltip
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('devForm')
    this.BPContent = getTemplateContent('devForm')
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
      content: {},
      BPContent: {},
      project: {},
      computedFields: ['objectives', 'odp', 'kPI'],
      requiredFields: ['objectives', 'odp', 'kPI']
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
    onBeginChange (dates, dateStr) {
      this.content.beginDate = dateStr
    },
    onEndChange (dates, dateStr) {
      this.content.endDate = dateStr
    },
    handleDate (date) {
      if (!date) return
      const dateAry = date.split('-')
      return `${dateAry[0]}年${dateAry[1]}月`
    }
  }
}
</script>

<style>
</style>
