<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 11:57:23
 * @LastEditors: hm
 * @Description:用料计划表 (研发领用原材料用料记录表、研发中间试验试制及工艺装备开发用料记录表、研发耗用辅助材料记录表、研发中间试验试制用原材料登记表、研发中间试验试制用辅助材料登记表、研发修理用原材料登记表、研发修理用备品备件登记表) —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\MaterialPlanForm.vue
-->
<template>
  <a-card>
    <a-row :gutter="12">
      <a-col :span="12">
        <a-form-item
          label="项目名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          style="margin-bottom: 8px;">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          label="项目编号"
          style="margin-bottom: 8px;"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="12">
      <a-col :span="12">
        <a-form-item
          label="承担部门"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          style="margin-bottom: 8px;"
        >
          {{ publicInfo.commonMap.parentRdDept }}
        </a-form-item>
      </a-col>
      <!-- <a-col :span="12">
        <a-form-item
          label="车间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          style="margin-bottom: 8px;"
        >
          {{ project.workshop }}
        </a-form-item>
      </a-col> -->
    </a-row>
    <!-- <a-row :gutter="12">
      <a-col :span="12">
        <a-form-item
          label="产线"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          style="margin-bottom: 8px;"
        >
          {{ project.productLine }}
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          label="工艺段"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          style="margin-bottom: 8px;"
        >
          {{ project.processSection }}
        </a-form-item>
      </a-col>
    </a-row> -->
    <a-row :gutter="12">
      <a-col :span="12">
      </a-col>
      <a-col :span="12"></a-col>
    </a-row>
    <a-divider/>
    <a-form layout="inline">
      <a-form-item label="月份" style="margin-bottom: 8px">
        <a-select
          style="width: 120px"
          :getPopupContainer="getPopupContainer"
          v-model="content.month"
          @change="onDateChange"
          placeholder="请选择月份"
        >
          <a-select-option v-for="n in months" :key="n" :value="n">{{ n }}</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
    <materialPlan-tab
      @change="a=>hasChange=a"
      ref="materialPlanTab"
      :project="project"
      :projectId="projectId"
      :dataMonth="dataMonth"
      :month="content.month"
      :docFileId="docFileId"
      v-on="$listeners"
      v-bind="$attrs"
    />
    <template>
      <a-divider/>
      <a-row>
        <a-col>
          <a-form-item required :colon="false">
            <template #label>
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                text="使用情况"
                title="使用主要材料哪些，总量有多少，研发使用原因，用途，作用，效果进行描述"
                style="display: inline-block;"
              />
            </template>
            <a-textarea
              :disabled="!hasChange"
              v-model="content.situation"
              placeholder="使用主要材料哪些，总量有多少，研发使用原因，用途，作用，效果进行描述"
              :rows="10" />
          </a-form-item>
        </a-col>
      </a-row>
    </template>
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
import AuditFooter from './modules/AuditFooter'
import MaterialPlanTab from './modules/MaterialPlanTab'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'
import YsTooltip from '@/components/YsTooltip/YsTooltip'

export default {
  name: 'MaterialPlanForm',
  components: {
    AuditFooter,
    MaterialPlanTab,
    YsTooltip
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('materialPlanForm')
    this.BPContent = getTemplateContent('materialPlanForm')
    this.hsaFileDataNull = false
  },
  props: {
    dataMonth: {
      type: String,
      default: ''
    },
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    docFileId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      domName,
      width: 1000,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: 0,
      hasChange: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      visible: false,
      m: '',
      months: [],
      content: {},
      BPContent: {},
      project: {},
      // selectKey: '10',
      requiredFields: ['situation']
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
      this.initMonths()
    },
    docId () {
      this.content = getTemplateContent('materialPlanForm')
      this.BPContent = getTemplateContent('materialPlanForm')
    }
  },
  methods: {
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    getPopupContainer (triggerNode) {
      return popupContainer(this.domName)
    },
    handleTemplateEvent () {
      this.initMonths()
      if (this.content.month) { return }
      if (this.dataMonth) {
        this.content.month = moment(this.dataMonth).format('YYYY-MM')
        this.BPContent.month = moment(this.dataMonth).format('YYYY-MM')
      }
    },
    initMonths () {
      var endDate = this.project.endDate
      var beginDate = this.project.beginDate
      var beginArray = beginDate.split('-').map(Number)
      var endArray = endDate.split('-').map(Number)
      var returnArry = []
      var begin = parseInt(beginArray[0])
      var end = parseInt(endArray[0])
      for (let y = begin; y <= end; y++) {
        const current = y === begin ? parseInt(beginArray[1]) : 1
        const last = y === end ? parseInt(endArray[1]) : 12
        for (let i = current; i <= last; i++) {
          returnArry.push(`${y}-${(i + '').padStart(2, '0')}`)
        }
      }
      this.months = returnArry
    },
    moment,
    disabledDate (current) {
      return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    },
    onDateChange (date) {
      if (date) {
        this.fileDate = moment(date + '-1').startOf('month')
        this.hsaFileDataNull = false
        if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
          this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
        }
      } else {
        this.hsaFileDataNull = true
      }
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    },
    saveData (data) {
      this.content.list = data
    }
  }
}
</script>

<style scoped>
</style>
