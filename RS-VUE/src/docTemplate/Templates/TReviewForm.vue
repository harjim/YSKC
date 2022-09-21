<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 11:55:39
 * @LastEditors: hm
 * @Description: 阶段评审报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\TReviewForm.vue
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
          label="负责人"
          style="margin-bottom: 0px">
          <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : ''
          }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目阶段"
          style="margin-bottom: 0px"
        >
          {{ fileInfo.stageType }}
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="日期"
          required
          style="margin-bottom: 0px">
          <a-date-picker
            :disabledDate="disabledDate"
            :getCalendarContainer="getCalendarContainer"
            :value="content.reviewTime ? moment(content.reviewTime,'YYYY-MM-DD'): content.reviewTime"
            format="YYYY-MM-DD"
            @change="onDateChange"
            @openChange="(status) => onOpenChange(status,'reviewTime')"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider />
    <div style="padding: 0 12px">
      <a-tabs>
        <a-tab-pane key="1">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="评审组成员"
            />
          </template>
          <a-button type="primary" @click="handleAdd">添加</a-button>
          <a-table
            :dataSource="content.list"
            :pagination="false"
            :scroll="{y:480}"
            bordered
            rowKey="enumber"
            size="small"
            style="padding-top:10px;"
          >
            <a-table-column
              :width="60"
              align="center"
              data-index="ename"
              title="评审组成员"></a-table-column>
            <a-table-column
              :width="100"
              align="center"
              data-index="deptName"
              title="部门">
              <template slot-scope="text,record">
                <span>{{ record.rdDeptName || record.deptName || '-' }}</span>
              </template>
            </a-table-column>
            <a-table-column
              :width="100"
              align="center"
              data-index="position"
              title="职务"></a-table-column>
            <a-table-column
              :width="100"
              align="center"
              title="签字"></a-table-column>
            <a-table-column
              :width="50"
              align="center"
              data-index="index1"
              title="操作">
              <template slot-scope="text,record">
                <a @click="handleDel(record)">移除</a>
              </template>
            </a-table-column>
          </a-table>
        </a-tab-pane>
        <a-tab-pane key="2">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="评审内容"
              title="重点撰写
                1、项目研究阶段资源综合使用情况：人员工时、设备占用工时等；
                2、研究阶段项目费用支出情况；
                3、产品、技术或工艺关键指标完成情况；
                4、产品、技术或工艺可推广性（如产品原材料来源、价格；工艺、技术的可操作性等）。"
            />
          </template>
          <a-textarea
            v-model="content.details"
            :rows="10"
            placeholder="重点撰写
1、项目研究阶段资源综合使用情况：人员工时、设备占用工时等；
2、研究阶段项目费用支出情况；
3、产品、技术或工艺关键指标完成情况；
4、产品、技术或工艺可推广性（如产品原材料来源、价格；工艺、技术的可操作性等）。" />
        </a-tab-pane>
        <a-tab-pane key="3">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="评审结论"
              title="项目研究阶段针对项目的关键技术进行攻克，关键参数进行了优化，研究方向及研发项目的可实施性良好，同意进入下一阶段进行深入研发、细化。"
            />
          </template>
          <a-textarea
            v-model="content.reviewConclusion"
            :rows="10"
            placeholder="项目研究阶段针对项目的关键技术进行攻克，关键参数进行了优化，研究方向及研发项目的可实施性良好，同意进入下一阶段进行深入研发、细化。" />
        </a-tab-pane>
      </a-tabs>
    </div>
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
  </a-card>
</template>
<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import employeeModal from './modules/EmployeeModal'
import yearMixin from '@/utils/yearMixin'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import {
  domName,
  popupContainer
} from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'
import YsTooltip from '@/components/YsTooltip/YsTooltip'

export default {
  name: 'TReviewForm',
  components: {
    employeeModal,
    AuditFooter,
    YsTooltip
  },
  mixins: [yearMixin, dateMixin],
  created () {
    this.content = getTemplateContent('tReviewForm')
    this.BPContent = getTemplateContent('tReviewForm')
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
      checkedList: [],
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
      fileInfo: {},
      computedFields: ['details', 'reviewConclusion'],
      requiredFields: ['list', 'reviewTime', 'details', 'reviewConclusion']
    }
  },
  watch: {
    checkedList (list) {
      const self = this
      self.content.checkedOptionStr = ''
      this.liaisonMattersOptions.map(item => {
        if (list.indexOf(item) > -1) {
          self.content.checkedOptionStr += '  ☑ ' + item
        } else {
          self.content.checkedOptionStr += '  ☒ ' + item
        }
      })
    },
    project (v) {
      this.content._pro = v.id
    },
    docId () {
      this.content = getTemplateContent('tReviewForm')
      this.BPContent = getTemplateContent('tReviewForm')
    }
  },
  methods: {
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
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
      return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
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
    onDateChange (date, dateString) {
      if (!dateString) {
        this.content.reviewTime = date
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
      this.content.reviewTime = dateString
    },
    onChange (checkedList) {
      this.checkedList = checkedList
    },
    handleOk (items) {
      if (this.content.list) {
        this.content.list.push(...items)
      } else {
        this.content.list = items
      }
    },
    handleAdd () {
      const enumberArr = []
      if (this.content.list) {
        this.content.list.forEach(item => {
          enumberArr.push(item.enumber)
        })
      }
      this.$refs.employeeModal.add(this.project.id, this.currentYear, enumberArr, this.fileDate, this.docId)
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    },
    handleTemplateEvent () {
      this.content.stage = this.fileInfo.stageType
    }
  }
}
</script>

<style lang="less" scoped>
.icon-interval {
  margin-left: 8px;
}
</style>
