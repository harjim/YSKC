<!--
 * @Author: ldx
 * @Date: 2021-02-23 11:42:18
 * @LastEditTime: 2022-09-14 11:56:24
 * @LastEditors: hm
 * @Description: 项目阶段总结报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\StageSummaryForm.vue
-->
<template>
  <a-card>
    <a-row :gutter="[8,8]" >
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
    <a-row :gutter="[8,8]">
      <a-col :span="12">
        <a-form-item label="项目现阶段" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
          <span> {{ fileInfo.stageType }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="总结日期" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;" required>
          <a-date-picker
            :getCalendarContainer="getCalendarContainer"
            style="width:100%"
            :value="content.summaryDate? moment(content.summaryDate,'YYYY-MM-DD') : content.summaryDate"
            format="YYYY-MM-DD"
            @openChange="(status) => onOpenChange(status,'summaryDate')"
            @change="onDateChange"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="12">
        <a-form-item label="现阶段试制量" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;" required>
          <a-input v-model="content.trialManufactureQuantity" placeholder="试制量"></a-input>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="样品处置方式" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;" required>
          <a-select style="width:100%" :options="options" v-model="content.way" placeholder="样品处置方式"></a-select>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="24">
        <a-form-item label="项目成员" :labelCol="labelCol24" :wrapperCol="wrapperCol24" style="margin-bottom: 0px;" required>
          <a-input placeholder="请输入项目成员" v-model="content.member"></a-input>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="24">
        <a-form-item :labelCol="labelCol24" :wrapperCol="wrapperCol24" style="margin-bottom: 0px;" required>
          <template slot="label">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              style="display: inline-block;"
              text="项目背景及目的"
              title="可参考项目立项书中的项目背景及目的，结合本阶段研发后的调整情况进行撰写。"
            />
          </template>
          <a-textarea placeholder="可参考项目立项书中的项目背景及目的，结合本阶段研发后的调整情况进行撰写。" :rows="5" v-model="content.purpose"></a-textarea>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="24">
        <a-form-item :labelCol="labelCol24" :wrapperCol="wrapperCol24" style="margin-bottom: 0px;" required>
          <template slot="label">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              style="display: inline-block;"
              text="现阶段研究内容"
              title="写本阶段研究的具体内容，重点研究、设计、验证那些关键功能和改进了那些重点指标。"
            />
          </template>
          <a-textarea placeholder="写本阶段研究的具体内容，重点研究、设计、验证那些关键功能和改进了那些重点指标。" :rows="5" v-model="content.researchContents"></a-textarea>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="24">
        <a-form-item :labelCol="labelCol24" :wrapperCol="wrapperCol24" style="margin-bottom: 0px;" required>
          <template slot="label">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              style="display: inline-block;"
              text="项目主要创新点"
              title="参考项目立项书中的创新点，重点写本阶段实施了那些创新活动，实现了那些创新点的工作开展。"
            />
          </template>
          <a-textarea placeholder="参考项目立项书中的创新点，重点写本阶段实施了那些创新活动，实现了那些创新点的工作开展。" :rows="5" v-model="content.innovation"></a-textarea>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="24">
        <a-form-item :labelCol="labelCol24" :wrapperCol="wrapperCol24" style="margin-bottom: 0px;" required>
          <template slot="label">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              style="display: inline-block;"
              text="项目现阶段实测参数"
              title="写目前项目实施达到的技术、工艺、产品的具体参数。（建议此阶段的指标可以略逊于最终指标，后面才有更进一步研究的意义和价值）"
            />
          </template>
          <a-textarea placeholder="写目前项目实施达到的技术、工艺、产品的具体参数。（建议此阶段的指标可以略逊于最终指标，后面才有更进一步研究的意义和价值）" :rows="5" v-model="content.measuredParameters"></a-textarea>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="24">
        <vxe-grid
          ref="table"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          :data="content.list"
        >
          <vxe-table-column title="序号" header-align="center" type="seq" width="60"></vxe-table-column>
          <vxe-table-column
            header-align="center"
            field="a"
            align="left">
            <template v-slot:header>
              <i class="required-field"/>项目当前阶段遇到的问题
            </template>
            <template #default="{row}">
              <a-textarea placeholder="项目当前阶段遇到的问题" :rows="2" v-model="row.problem"></a-textarea>
            </template>
          </vxe-table-column>
          <vxe-table-column
            header-align="center"
            field="b"
            align="left">
            <template v-slot:header>
              <i class="required-field"/>项目当前阶段对应的解决方法
            </template>
            <template v-slot="{row}">
              <a-textarea placeholder="项目当前阶段对应的解决方法" :rows="2" v-model="row.solution"></a-textarea>
            </template>
          </vxe-table-column>
        </vxe-grid>
        <div class="operate">
          <a-button
            type="dashed"
            style="width: 100%"
            icon="plus"
            @click="addRow"
          >添加问题记录</a-button>
        </div>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="24">
        <a-form-item :labelCol="labelCol24" :wrapperCol="wrapperCol24" style="margin-bottom: 0px;" required>
          <template slot="label">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              style="display: inline-block;"
              text="项目后续可能存在的问题"
              title="后续试制需要重点关注的参数，及小批量试制，存在的量之间差异化引起的问题需要重点关注，结合项目实际研究的对象，具体预测后续的问题，即后续试制关注的重点。"
            />
          </template>
          <a-textarea placeholder="后续试制需要重点关注的参数，及小批量试制，存在的量之间差异化引起的问题需要重点关注，结合项目实际研究的对象，具体预测后续的问题，即后续试制关注的重点。" :rows="5" v-model="content.problem"></a-textarea>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="[8,8]">
      <a-col :span="24">
        <a-form-item :labelCol="labelCol24" :wrapperCol="wrapperCol24" style="margin-bottom: 0px;" required>
          <template slot="label">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              style="display: inline-block;"
              text="开发工作评价"
              title="总的来讲，项目设计验证阶段完成了相关的设计验证工作，项目的关键指标通过了理论验证和测试验证，建议进入下一步的验证工作。请领导批示！"
            />
          </template>
          <a-textarea placeholder="总的来讲，项目设计验证阶段完成了相关的设计验证工作，项目的关键指标通过了理论验证和测试验证，建议进入下一步的验证工作。请领导批示！" :rows="5" v-model="content.evaluate"></a-textarea>
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider/>
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
import YsTooltip from '@/components/YsTooltip/YsTooltip'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import AuditFooter from './modules/AuditFooter'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'StageSummaryForm',
  components: {
    AuditFooter,
    YsTooltip
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
    this.content = getTemplateContent('stageSummaryForm')
    this.BPContent = getTemplateContent('stageSummaryForm')
  },
  data () {
    return {
      domName,
      width: 960,
      currentYear: this.$store.state.currentYear,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 }
      },
      labelCol24: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol24: {
        xs: { span: 24 },
        sm: { span: 19 }
      },
      content: {},
      BPContent: {},
      projectName: '',
      project: {},
      fileInfo: {},
      options: [
        { value: '报废', label: '报废' },
        { value: '送客户', label: '送客户' },
        { value: '卖出', label: '卖出' }
      ],
      computedFields: [
        'evaluate', // 开发工作评价
        'problem', // 项目后续可能存在的问题
        'measuredParameters', // 项目现阶段实测参数
        'innovation', // 项目主要创新点
        'researchContents', // 现阶段研究内容
        'purpose' // 项目背景及目的
      ],
      requiredFields: ['summaryDate', 'trialManufactureQuantity', 'way', 'member', 'purpose', 'researchContents', 'innovation', 'measuredParameters', 'list', 'problem', 'evaluate']
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
    },
    docId () {
      this.content = getTemplateContent('stageSummaryForm')
      this.BPContent = getTemplateContent('stageSummaryForm')
    }
  },
  methods: {
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    onDateChange (date, dateString) {
      if (dateString) {
        this.hsaFileDataNull = true
        this.content.summaryDate = dateString
        this.fileDate = moment(dateString).startOf('month')
        if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
          this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
        }
      } else {
        this.hsaFileDataNull = false
        this.content.summaryDate = date
        if (this.stage && this.stage.beginDate) {
          this.fileDate = moment(this.stage.beginDate).startOf('month')
        } else {
          this.fileDate = moment(this.BUfileDate).startOf('month')
        }
      }
    },
    addRow () {
      const addRow = { problem: undefined, solution: undefined }
      this.content.list.push(addRow)
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).subtract(1, 'days').format('YYYY-MM-DD')
      }
    },
    handleTemplateEvent () {
      this.content.stageName = this.fileInfo.stageType
    }
  }
}
</script>
