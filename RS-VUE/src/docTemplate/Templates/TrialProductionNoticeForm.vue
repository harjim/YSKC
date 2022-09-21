<!--
 * @Author:
 * @Date: 2020-12-14 11:35:31
 * @LastEditTime: 2022-09-14 11:53:19
 * @LastEditors: hm
 * @Description:试验试制通知单 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\TrialProductionNoticeForm.vue
-->
<template>
  <a-card>
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
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="试验试制阶段"
          required
          style="margin-bottom: 0px"
        >
          <a-input
            v-model="content.pilotStage"
            placeholder="试验试制阶段"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="负责人"
          style="margin-bottom: 0px">
          <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : ''
          }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <!-- <a-form-item v-if="content.pilotTime && content.endPilotTime" label="试验试制时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-form-item style="margin-bottom: 0px; display:inline-block; width: calc(50% - 12px);">
            <a-date-picker
              :getCalendarContainer="getCalendarContainer"
              :value="content.pilotTime ? moment(content.pilotTime,'YYYY-MM-DD'): content.pilotTime"
              format="YYYY-MM-DD"
              :disabledDate="disabledDate"
              @change="(date, dateString) => onDatePilotTimeChange(dateString,'pilotTime')"
              @openChange="(status) => onOpenChange(status,'pilotTime')"
            />
          </a-form-item>
          <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
            -
          </span>
          <a-form-item style="margin-bottom: 0px; display:inline-block; width: calc(50% - 12px);">
            <a-date-picker
              :getCalendarContainer="getCalendarContainer"
              :value="content.endPilotTime ? moment(content.endPilotTime,'YYYY-MM-DD'): content.endPilotTime"
              format="YYYY-MM-DD"
              :disabledDate="disabledDate"
              @change="(date, dateString) => onDatePilotTimeChange(dateString,'endPilotTime')"
              @openChange="(status) => onOpenChange(status,'endPilotTime',true)"
            />
          </a-form-item>
        </a-form-item> -->
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="月份"
          required
          style="margin-bottom: 0px">
          <a-select
            v-model="content.month"
            :getPopupContainer="getCalendarContainer"
            placeholder="请选择月份"
            style="width: 100%"
            @change="onMonthChange"
          >
            <a-select-option v-for="n in months" :key="n" :value="n">{{ n }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="阶段"
          style="margin-bottom: 0px">
          {{ fileInfo.stageType }}
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider />
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发出部门"
          required
          style="margin-bottom: 0px">
          <select-down
            ref="rdDept"
            :value="content.emitDep.id"
            :year="year"
            placeholder="发出部门"
            style="width: 100%"
            treeType="rdDept"
            @select="(v,n)=>{content.emitDep = {id: v,name: n}}"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发出人"
          required
          style="margin-bottom: 0px">
          <select-employee
            :mountDom="domName"
            :selected="content.emitPerson"
            :year="year"
            @select="emitPerson=>content.emitPerson= emitPerson"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="发出时间"
          required
          style="margin-bottom: 0px">
          <a-date-picker
            :disabledDate="emitTimeDisabledDate"
            :getCalendarContainer="getCalendarContainer"
            :value="content.emitTime ? moment(content.emitTime) : null"
            style="width:100%"
            valueFormat="YYYY-MM-DD"
            @change="onDateEmitTimeChange"
            @openChange="(status) => onOpenChange(status,'emitTime')"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="试验试制时间"
          required>
          <div style="display: flex; position: relative;">
            <div style="display: flex; position: relative; flex: 1;">
              <a-select
                v-model="content.trialDates"
                :getPopupContainer="getCalendarContainer"
                class="group-select"
                mode="tags"
                placeholder="请选择日期"
                style="width: 100%;"
                @change="handleChange">
              </a-select>
              <a-date-picker
                :defaultPickerValue="moment(fileDate)"
                :disabledDate="disabledDate"
                :getCalendarContainer="getCalendarContainer"
                :open="isOpen"
                class="group-btn"
                mode="date"
                @change="onChange"
                @openChange="changePickerStatus"
              >
                <a-icon
                  style="width: 100%; height: 100%; line-height: 32px;"
                  type="calendar" />
              </a-date-picker>
            </div>
            <a-button
              style="margin-left: 8px;"
              type="primary"
              @click="importDatesVisible = true">引入时间
            </a-button>
          </div>
        </a-form-item>
      </a-col>
    </a-row>
    <!-- <a-button type="primary" style="margin: 10px 0;" @click="addRow">添加接收部门</a-button> -->
    <a-tooltip :getPopupContainer="getCalendarContainer" placement="top">
      <template slot="title">
        <span>从该项目其他试验试制单中引入数据</span>
      </template>
      <i class="required-field" />
      <a-button
        style="margin-right:10px;margin-bottom: 6px;"
        type="primary"
        @click="importReception">引入数据
      </a-button>
    </a-tooltip>
    <a-table
      :customCell="{valign:'top'}"
      :dataSource="content.receiveList"
      :pagination="false"
      :scroll="{y:480}"
      bordered
      rowKey="num"
      size="small"
    >
      <a-table-column
        :width="60"
        align="center"
        data-index="num"
        title="序号"></a-table-column>
      <a-table-column :width="250" data-index="department" title="接收部门">
        <template slot-scope="text, record">
          <select-down
            ref="rdDept"
            :mountDom="domName"
            :value="record.department.id"
            :year="year"
            placeholder="请选择部门"
            style="width: 100%"
            treeType="rdDept"
            @select="(v,n)=>{record.department = {id: v,name: n}}"
          />
        </template>
      </a-table-column>
      <a-table-column :width="200" data-index="recipient" title="接收人">
        <template slot-scope="text, record">
          <select-employee
            :mountDom="domName"
            :selected="record.emitPerson"
            :year="year"
            @select="emitPerson=>record.emitPerson= emitPerson"
          />
        </template>
      </a-table-column>
      <a-table-column data-index="reception" title="接收情况">
        <template slot-scope="text, record">
          <a-input v-model="record.reception" placeholder="接收情况" />
        </template>
      </a-table-column>
      <a-table-column
        :width="120"
        align="center"
        data-index="action"
        title="操作">
        <template slot-scope="text,record">
          <a type="primary" @click="removeRow(record)">移除</a>
        </template>
      </a-table-column>
    </a-table>
    <div class="operate">
      <a-button
        icon="plus"
        style="width: 100%"
        type="dashed"
        @click="addRow"
      >添加接收部门
      </a-button>
    </div>
    <a-divider />
    <a-row>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane key="1">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              style="display: inline-block;"
              required
              placement="right"
              text="试验试制要求"
              title="主要表述清楚对各部门或各岗位之间配合的要求，各部门或各岗位分别负责什么事情，达到什么效果。例如：
                项目研究阶段的实施需要各岗位相互配合，共同完成项目方案的理论设计及模拟验证，为确保验证过程有序进行，具体要求如下：
                1、项目负责人负责：试制、试验过程的策划、组织、协调、控制等项目整体掌控，确保研究阶段验证工作顺利开展。
                2、项目评审人员负责：试制、试验阶段各类评审的组织及评审报告的编制。
                3、项目成员负责：产品样品试制、试验阶段技术文件的归档管理，有序完成本职工作。
                4、项目成员负责：样机试制、试验过程中技术服务、设计及零部件制作质量问题的确认、试制及试验过程发现问题的分析、整改、关闭。
                5、实验与检测中心：重点进行样品检测，配合研究人员进行数据验证，并协助查找问题点。"
            />
          </template>
          <a-textarea
            v-model="content.require"
            :rows="10"
            placeholder="主要表述清楚对各部门或各岗位之间配合的要求，各部门或各岗位分别负责什么事情，达到什么效果。例如：
    项目研究阶段的实施需要各岗位相互配合，共同完成项目方案的理论设计及模拟验证，为确保验证过程有序进行，具体要求如下：
        1、项目负责人负责：试制、试验过程的策划、组织、协调、控制等项目整体掌控，确保研究阶段验证工作顺利开展。
        2、项目评审人员负责：试制、试验阶段各类评审的组织及评审报告的编制。
        3、项目成员负责：产品样品试制、试验阶段技术文件的归档管理，有序完成本职工作。
        4、项目成员负责：样机试制、试验过程中技术服务、设计及零部件制作质量问题的确认、试制及试验过程发现问题的分析、整改、关闭。
        5、实验与检测中心：重点进行样品检测，配合研究人员进行数据验证，并协助查找问题点。" />
        </a-tab-pane>
        <a-tab-pane key="2">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              style="display: inline-block;"
              text="试验试制目的"
              title="常见写法通过研究阶段的试验，确定XXX产品的XX参数指标，确定采用XXX方案；
                通过研究阶段的试验，优化XXX工艺的XX技术参数，进而确定采用XXX方案，达到XXX效果；
                通过研究阶段的试验，分析XX技术可实现的XX技术指标，验证是否可达到预期指标XXX。"
            />
          </template>
          <a-textarea
            v-model="content.purpose"
            :rows="10"
            placeholder="常见写法通过研究阶段的试验，确定XXX产品的XX参数指标，确定采用XXX方案；
通过研究阶段的试验，优化XXX工艺的XX技术参数，进而确定采用XXX方案，达到XXX效果；
通过研究阶段的试验，分析XX技术可实现的XX技术指标，验证是否可达到预期指标XXX。" />
        </a-tab-pane>
        <a-tab-pane key="3">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              style="display: inline-block;"
              text="变更内容"
              title="变更内容"
            />
          </template>
          <a-textarea
            v-model="content.changeConetnt"
            :rows="10"
            placeholder="变更内容" />
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
    <employee-Modal
      ref="employeeModal"
      @addMemberList="handleOk"></employee-Modal>
    <import-reception-modal ref="importReception" @ok="importOk" />
    <a-modal
      :confirmLoading="confirmLoading"
      :getContainer="getCalendarContainer"
      :visible="importDatesVisible"
      :zIndex="101"
      title="引入时间"
      @cancel="importDatesVisible = false"
      @ok="submitImportDates">
      <a-form :form="importDatesForm">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="月份">
          <a-month-picker
            v-decorator="['month', { rules: [{ required: true, message: '请选择月份' }] }]"
            :disabledDate="disabledImportDates"
            :getCalendarContainer="getCalendarContainer"
            placeholder="请选择月份" />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-card>
</template>
<script>
import moment from 'moment'
import YsTooltip from '@/components/YsTooltip/YsTooltip'
import ImportReceptionModal from './modules/ImportReceptionModal'
import AuditFooter from './modules/AuditFooter'
import { SelectDown, SelectEmployee } from '@/components/'
import employeeModal from './modules/EmployeeModal'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import {
  domName,
  popupContainer
} from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'TrialProductionNoticeForm',
  components: {
    employeeModal,
    SelectEmployee,
    SelectDown,
    AuditFooter,
    ImportReceptionModal,
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
    this.content = getTemplateContent('trialProductionNoticeForm')
    this.BPContent = getTemplateContent('trialProductionNoticeForm')
  },
  data () {
    return {
      currentId: undefined,
      domName,
      width: 960,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      row: {
        num: undefined,
        department: { id: undefined, name: undefined },
        recipient: undefined,
        reception: ''
      },
      content: {},
      BPContent: {},
      project: {},
      fileInfo: {},
      computedFields: ['require', 'purpose', 'changeConetnt'],
      importDatesVisible: false,
      confirmLoading: false,
      importDatesForm: this.$form.createForm(this),
      requiredFields: ['pilotStage', 'trialDates', 'emitDep.id', 'emitPerson.enumber', 'emitTime', 'receiveList', 'require', 'purpose', 'changeConetnt', 'month'],
      months: [],
      isOpen: false
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    },
    docId () {
      this.content = getTemplateContent('trialProductionNoticeForm')
      this.BPContent = getTemplateContent('trialProductionNoticeForm')
    }
  },
  methods: {
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },

    importOk (arr) {
      if (arr && arr.length) {
        let index = this.content.receiveList.length
        arr.forEach(item => {
          index++
          item.num = index
          this.content.receiveList.push(item)
        })
      }
    },
    addRow () {
      const tempRow = { ...this.row }
      tempRow.num = this.content.receiveList.length + 1
      this.content.receiveList.push(tempRow)
    },
    importReception () {
      this.$refs.importReception.open(this.currentId, this.project.id)
    },
    handleDel (record) {
      const tempList = []
      this.content.list.forEach(item => {
        if (item.enumber !== record.enumber) {
          tempList.push(item)
        }
      })
      this.content.list = tempList
    },
    disabledDate (current) {
      const start = this.project.beginDate
      const end = this.project.endDate
      return current < moment(start) || current > moment(end)
    },
    disabledImportDates (current) {
      const {
        stageBeginMonth,
        stageEndMonth,
        beginAndEnd
      } = this.publicInfo.commonMap
      let start
      let end
      if (stageBeginMonth && stageEndMonth) {
        start = moment().set({
          year: stageBeginMonth.slice(0, 4),
          month: parseInt(stageBeginMonth.slice(-3, -1)) - 2
        })
        end = moment().set({
          year: stageEndMonth.slice(0, 4),
          month: parseInt(stageEndMonth.slice(-3, -1)) - 1
        })
      } else {
        const dates = beginAndEnd.split('至')
        start = moment(dates[0])
        end = moment(dates[1])
      }
      return current < start || current > end
    },
    emitTimeDisabledDate (current) {
      const start = this.project.beginDate
      let end = this.project.endDate
      if (this.content.pilotTime) {
        end = this.content.pilotTime
      }
      return current < moment(start) || current > moment(end)
    },
    handleApproveChange (value) {
      if (this.employeeMap[value]) {
        this.content.approveName = this.employeeMap[value].ename
      }
    },
    onReceiveTimeChange (dateString, record) {
      record.receiveTime = moment(dateString).format('YYYY-MM-DD')
    },
    moment,
    onDateEmitTimeChange (date, dateString) {
      if (!dateString) {
        this.content.emitTime = date
        // if (this.stage && this.stage.beginDate) {
        //   this.fileDate = moment(this.stage.beginDate).startOf('month')
        // } else {
        //   this.fileDate = moment(this.BUfileDate).startOf('month')
        // }
        this.hsaFileDataNull = true
        return
      }
      this.hsaFileDataNull = false
      // this.fileDate = moment(dateString).startOf('month')
      // if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
      //   this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
      // }
      this.content.emitTime = dateString
    },
    // onDatePilotTimeChange (dateString, field) {
    //   if (!dateString) {
    //     dateString = undefined
    //   }
    //   this.content[field] = dateString
    // },
    handleOk (items) {
      this.$getTree('rdDept', false, this.project.beginYear).then(res => {
        const keyMap = res.keyMap
        const arr = []
        items.forEach(t => {
          arr.push({
            enumber: t.enumber,
            ename: t.ename,
            position: t.position,
            rdDeptName: keyMap[t.rdDeptId]
          })
        })
        if (this.content.list) {
          this.content.list.push(...arr)
        } else {
          this.content.list = arr
        }
      })
    },
    // handleAdd () {
    //   const enumberArr = []
    //   if (this.content.list) {
    //     this.content.list.forEach(item => {
    //       enumberArr.push(item.enumber)
    //     })
    //   }
    //   this.$refs.employeeModal.add(this.project.id, this.project.beginYear, enumberArr, this.fileDate, this.docId)
    // },
    removeRow (record) {
      const tempRows = []
      var num = 0
      for (var i = 0; i < this.content.receiveList.length; i++) {
        const row = this.content.receiveList[i]
        if (row.num !== record.num) {
          row.num = ++num
          tempRows.push(row)
        }
      }
      this.content.receiveList = tempRows
    },
    onOpenChange (status, field, isEnd) {
      if (!this.content[field] && status) {
        if (isEnd) {
          if (this.content.endPilotTime) {
            this.content[field] = moment(this.content.endPilotTime).format('YYYY-MM-DD')
          } else {
            this.content[field] = moment(this.project.endDate).format('YYYY-MM-DD')
          }
        } else {
          this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
        }
      }
    },
    handleTemplateEvent () {
      this.content.stage = this.fileInfo.stageType
      this.currentId = this.fileInfo.docId
      this.initMonths()
      this.content.month = this.publicInfo.month ? this.publicInfo.month.slice(0, 7) : undefined
    },
    onChange (value, dateString) {
      if (this.content.trialDates.indexOf(dateString) === -1) {
        this.content.trialDates.push(dateString)
      }
      this.isOpen = true
    },
    handleChange (value) {
      // console.log(`selected ${value}`)
      // console.log(' this.content.trialDates', this.content.trialDates)
    },
    submitImportDates () {
      this.importDatesForm.validateFields((errors, values) => {
        if (errors) return
        const params = {
          projectId: this.projectId,
          month: moment(values.month).format('YYYY-MM-01 00:00:00')
        }
        this.confirmLoading = true
        this.$http.get('/projectYieldConfig/getDate', {
          params: params
        }).then(({ data, success, errorMessage }) => {
          if (success && data) {
            this.$message.success('操作成功')
            this.content.trialDates = data.trialDates
            this.content.emitTime = data.emitTime ? data.emitTime[0] : ''
            this.importDatesVisible = false
            this.importDatesForm.resetFields()
          } else {
            this.$message.error(errorMessage || '操作失败！')
          }
        }).catch(error => {
          this.$message.error(error.message || '系统异常，请联系管理员！')
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    onMonthChange (value) {
      if (value) {
        this.fileDate = moment(value + '-1').startOf('month')
        if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
          this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
        }
        this.hsaFileDataNull = false
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
    changePickerStatus (status) {
      this.isOpen = status
    },
    addSelectedDate (date) {
      console.log(date)
    }
  }
}
</script>

<style lang="less" scoped>
.icon-interval {
  margin-left: 8px;
}

.group-btn {
  position: absolute;
  right: 0;
  padding: 0 0;
  width: 37px;
  height: 32px;
  padding: 0 11px;
  color: rgba(0, 0, 0, .65);
  font-weight: 400;
  font-size: 14px;
  text-align: center;
  background-color: #fafafa;
  border: 1px solid #d9d9d9;
  border-left: 0px;
  border-radius: 4px;
  transition: all .3s;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fafafa;
}

.group-select {
  /deep/ .ant-select-selection {
    width: calc(100% - 37px);
    border-top-right-radius: 0;
    border-bottom-right-radius: 0;
  }
}
</style>
