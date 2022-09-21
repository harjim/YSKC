<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 11:49:19
 * @LastEditors: hm
 * @Description: 项目建议书 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\AdviceForm.vue
-->
<template>
  <a-card>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="提出部门"
          required
          style="margin-bottom: 0px;">
          <select-down
            ref="rdDept"
            :mountDom="domName"
            :value="content.rdDept.id"
            :year="year"
            placeholder="请选择部门"
            style="width: 50%"
            treeType="rdDept"
            @select="(v,n)=>{content.rdDept = {id: v,name: n}}"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="建议人"
          required
          style="margin-bottom: 0px;">
          <select-employee
            :mountDom="domName"
            :selected="content.emp"
            :year="year"
            @select="emp=>content.emp = emp"
          />
        </a-form-item>
      </a-col>
    </a-row>
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
          label="建议日期"
          required
          style="margin-bottom: 0px;">
          <a-date-picker
            :defaultPickerValue="moment(this.project.beginDate)"
            :getCalendarContainer="getCalendarContainer"
            :value="content.d? moment(content.d,'YYYY-MM-DD') : content.d"
            format="YYYY-MM-DD"
            style="width:100%"
            @change="onDateChange"
            @openChange="(status) => onOpenChange(status,'d')"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider />
    <a-row>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane key="1">
          <template #tab>
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="基本现况"
              title="结合项目名称研究的主题，写明主题目前的情况优缺点分析，技术瓶颈等。"
            />
          </template>
          <a-textarea
            v-model="content.basic"
            :rows="10"
            placeholder="结合项目名称研究的主题，写明主题目前的情况优缺点分析，技术瓶颈等。" />
        </a-tab-pane>
        <a-tab-pane key="2">
          <template #tab>
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="创新性建议"
              title="结合“基本现状”中，研究主体的情况、瓶颈，明确表述项目要采用什么手段、方式、方法、技术、材料、配方、工艺等进行改进。"
            />
          </template>
          <a-textarea
            v-model="content.advice"
            :rows="10"
            placeholder="结合“基本现状”中，研究主体的情况、瓶颈，明确表述项目要采用什么手段、方式、方法、技术、材料、配方、工艺等进行改进。" />
        </a-tab-pane>
        <a-tab-pane key="3">
          <template #tab>
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="预期达到目标"
              title="结合“创新性建议”，重点描述此建议实施后达到的效果、技术水平、技术参数、产品的特性、参数等。"
            />
          </template>
          <a-textarea
            v-model="content.target"
            :rows="10"
            placeholder="结合“创新性建议”，重点描述此建议实施后达到的效果、技术水平、技术参数、产品的特性、参数等。" />
        </a-tab-pane>
        <a-tab-pane key="5">
          <template #tab>
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              text="审核意见"
            />
          </template>
          <a-textarea
            v-model="content.suggestion"
            :rows="10"
            placeholder="审核意见" />
        </a-tab-pane>
      </a-tabs>
    </a-row>
    <a-divider />
    <audit-footer
      ref="audtiFooter"
      :docId="docId"
      :employeeMap="employeeMap"
      :projectId="projectId"
      :year="year" />
  </a-card>
</template>
<script>
import moment from 'moment'
import { SelectDown, SelectEmployee } from '@/components/'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import {
  domName,
  popupContainer
} from '@/docTemplate/Templates/js/screenFullMountDom'
import AuditFooter from './modules/AuditFooter'
import dateMixin from '../Templates/js/dateMixin'
import YsTooltip from '@/components/YsTooltip/YsTooltip'

export default {
  name: 'AdviceForm',
  components: {
    YsTooltip,
    SelectDown,
    SelectEmployee,
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
    this.content = getTemplateContent('adviceForm')
    this.BPContent = getTemplateContent('adviceForm')
  },
  data () {
    return {
      domName,
      width: 960,
      currentYear: this.$store.state.currentYear,
      url: '/reviewCommittee/getReviewsSelect',
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
      projectName: '',
      project: {},
      computedFields: ['basic', 'advice', 'target', 'suggestion'],
      // 必填项
      requiredFields: ['rdDept.id', 'emp', 'd', 'basic', 'advice', 'target']
    }
  },
  methods: {
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    onDateChange (date, dateString) {
      if (dateString) {
        this.content.d = dateString
      } else {
        this.content.d = undefined
      }
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).subtract(1, 'days').format('YYYY-MM-DD')
      }
    }
  }
}
</script>
