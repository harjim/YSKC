<!--
 * @Author:
 * @Date: 2020-09-23 10:01:15
 * @LastEditTime: 2022-09-14 12:12:38
 * @LastEditors: hm
 * @Description: 项目年度技术总结 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\PAnnualReportForm.vue
-->

<template>
  <a-card>
    <a-form>
      <a-row>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="项目名称"
            style="margin-bottom: 0px">
            <span>{{ project.pname }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="项目编号"
            style="margin-bottom: 0px">
            <span>{{ project.rdTitle }}</span>
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
                text="项目总结背景及目的"
                title="可参考项目立项书中的项目背景及目的，结合实施后的调整情况进行撰写。"
              />
            </template>
            <a-textarea
              v-model="content.objectives"
              :rows="10"
              placeholder="可参考项目立项书中的项目背景及目的，结合实施后的调整情况进行撰写。" />
          </a-tab-pane>
          <a-tab-pane key="2">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="实际开发结果"
                title="结合项目实施要达到的目的，对应的写出是否已达到了对应的研发结果。"
              />
            </template>
            <a-textarea
              v-model="content.developmentResults"
              :rows="10"
              placeholder="结合项目实施要达到的目的，对应的写出是否已达到了对应的研发结果。"
            />
          </a-tab-pane>
          <a-tab-pane key="3">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="开发工作评价"
                title="第一种：若年底项目已验收通过，建议评价内容：
                  项目自提出至完工，历时XX个月，经过项目组成员的通力合作，实现的项目目的，达成了项目技术目标，预计经济效益可观，经过评审委员会一直验收通过。
                  接着第二段详细写，项目完成后，在产品功能、性能、技术水平、技术难度、工艺先进性、节能、减排、降耗、提效等方面的具体效果并作出评价。表示对公司在某产品/工艺/技术方面具有重要的提升效果，对公司长远发展具有重要的意义。
                  第二种：若年底项目还未完工，还在项目实施的某个阶段，则重点表述项目在当前阶段已经具备的意义，达到的效果，产生的效用，具有重要的研究开发价值。"
              />
            </template>
            <a-textarea
              v-model="content.evaluate"
              :rows="10"
              placeholder="第一种：若年底项目已验收通过，建议评价内容：
      项目自提出至完工，历时XX个月，经过项目组成员的通力合作，实现的项目目的，达成了项目技术目标，预计经济效益可观，经过评审委员会一直验收通过。
       接着第二段详细写，项目完成后，在产品功能、性能、技术水平、技术难度、工艺先进性、节能、减排、降耗、提效等方面的具体效果并作出评价。表示对公司在某产品/工艺/技术方面具有重要的提升效果，对公司长远发展具有重要的意义。
第二种：若年底项目还未完工，还在项目实施的某个阶段，则重点表述项目在当前阶段已经具备的意义，达到的效果，产生的效用，具有重要的研究开发价值。" />
          </a-tab-pane>
          <a-tab-pane key="4">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="项目经验及教训"
                title="撰写项目研发过程中有什么宝贵的经验，包括产品/技术/工艺的研发方法、研发路线、研发人员具备的某项技能、研发进度推进方法、原材料筛选方法、人员配合沟通技巧、试制过程部门配合方式等等，可以作为后续研发其他项目的重要提示、技术、方法、技巧的分享都可以。"
              />
            </template>
            <a-textarea
              v-model="content.experience"
              :rows="10"
              placeholder="撰写项目研发过程中有什么宝贵的经验，包括产品/技术/工艺的研发方法、研发路线、研发人员具备的某项技能、研发进度推进方法、原材料筛选方法、人员配合沟通技巧、试制过程部门配合方式等等，可以作为后续研发其他项目的重要提示、技术、方法、技巧的分享都可以。" />
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
    </a-form>
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
  name: 'PAnnualReportForm',
  components: {
    AuditFooter,
    YsTooltip
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('pAnnualReportForm')
    this.BPContent = getTemplateContent('pAnnualReportForm')
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
      computedFields: ['objectives', 'developmentResults', 'evaluate', 'experience'],
      requiredFields: ['objectives', 'developmentResults', 'evaluate', 'experience']
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
    onDateChange (dates, dateStr) {
      this.content.beginDate = dateStr
    }
  }
}
</script>

<style>
</style>
