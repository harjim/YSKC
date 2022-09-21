<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 11:54:03
 * @LastEditors: hm
 * @Description:阶段人员工作安排 研发人员工时记录 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\AttendanceAggForm.vue
-->
<template>
  <a-card>
    <a-row >
      <a-col :span="12">
        <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row >
      <a-col :span="12">
        <a-form-item label="确定日期" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px" required>
          <a-date-picker
            :getCalendarContainer="getCalendarContainer"
            :value="content.confirmTime ? moment(content.confirmTime,'YYYY-MM-DD'): content.confirmTime"
            format="YYYY-MM-DD"
            :defaultPickerValue="moment(fileDate)"
            :disabledDate="disabledDate"
            @change="onDateChange"
          />
          <!-- @openChange="(status) => onOpenChange(status,'confirmTime')" -->
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
          <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : '' }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row >
      <a-col :span="12">
      </a-col>
      <a-col :span="12"></a-col>
    </a-row>
    <a-divider/>
    <a-form layout="inline">
      <a-form-item label="月份" style="margin-bottom: 8px">
        <a-select
          :getPopupContainer="getPopupContainer"
          style="width: 120px"
          v-model="content.employeeMonth"
          placeholder="请选择月份"
          @change="onMonthSelectChange"
        >
          <a-select-option v-for="n in months" :key="n" :value="n">{{ n }}</a-select-option>
        </a-select>

      </a-form-item>
    </a-form>
    <attendance-tab
      ref="attendanceTab"
      :project="project"
      :dataMonth="dataMonth"
      :month="content.employeeMonth"
      v-on="$listeners"
      :projectId="projectId"
      v-bind="$attrs" />
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
import AttendanceTab from './modules/AttendanceTab'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'AttendanceAggForm',

  components: {
    AttendanceTab,
    AuditFooter
  },
  mixins: [dateMixin],
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
    }
  },
  created () {
    this.content = getTemplateContent('attendanceAggForm')
    this.BPContent = getTemplateContent('attendanceAggForm')
    this.hsaFileDataNull = false
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
      confirmLoading: false,
      form: this.$form.createForm(this),
      visible: false,
      m: '',
      months: [],
      content: {},
      BPContent: {},
      project: {},
      // selectKey: '10',
      requiredFields: ['confirmTime']
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
      this.initMonths()
    },
    docId () {
      this.content = getTemplateContent('attendanceAggForm')
      this.BPContent = getTemplateContent('attendanceAggForm')
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
      if (this.content.employeeMonth) { return }
      if (this.dataMonth) {
        this.content.employeeMonth = moment(this.dataMonth).format('YYYY-MM')
        this.BPContent.employeeMonth = moment(this.dataMonth).format('YYYY-MM')
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
    onDateChange (date, dateString) {
      if (!dateString) {
        dateString = undefined
      }
      this.content.confirmTime = dateString
    },
    onMonthSelectChange (value, option) {
      this.content._pro = this.project.id
      this.$refs.attendanceTab.tableRefresh()
      this.fileDate = moment(value + '-1').startOf('month')
      if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
        this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
      }
      this.hsaFileDataNull = false
    },
    // onOpenChange (status, field) {
    //   if (!this.content[field] && status) {
    //     this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
    //   }
    // },
    saveData (data) {
      this.content.list = data
    }
  }
}
</script>

<style scoped>
</style>
