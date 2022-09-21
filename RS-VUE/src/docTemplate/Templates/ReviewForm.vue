<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-17 09:09:21
 * @LastEditors: hm
 * @Description: 立项评审报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ReviewForm.vue
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
            style="margin-bottom:0px;">
            <span>{{ project.pname }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="项目编号"
            style="margin-bottom:0px;">
            <span>{{ project.beginYear }}RD{{ project.rdIndex | ZeroFormat
            }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="项目性质"
            style="margin-bottom:0px;"
          >
            <a-checkbox-group
              v-model="content.checkedList"
              :options="projectOptions"
              @change="onChange"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="主持人"
            required
            style="margin-bottom:0px;">
            <select-employee
              :mountDom="domName"
              :selected="content.host"
              :year="year"
              @select="host=>content.host = host"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="评审时间"
            required
            style="margin-bottom:0px;">
            <!-- :disabledDate="disabledDate" -->
            <a-date-picker
              :getCalendarContainer="getCalendarContainer"
              :value="content.reviewTime ? moment(content.reviewTime,'YYYY-MM-DD'): content.reviewTime"
              format="YYYY-MM-DD"
              @change="onDateChange"
              @openChange="(status) => onOpenChange(status,'reviewTime')"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="评审地点"
            required
            style="margin-bottom:0px;"
          >
            <a-input
              v-model="content.reviewAddress"
              placeholder="请输入评审地点"
            />
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
                text="评审人员"
              />
            </template>
            <a-button type="primary" @click="handleAdd">添加</a-button>
            <a-button
              :disabled="!selectedRowKeys || !selectedRowKeys.length"
              style="margin-left:12px;"
              type="primary"
              @click="handleDels">移除
            </a-button>
            <a-table
              :dataSource="content.list"
              :pagination="false"
              :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange,columnWidth:30 }"
              :scroll="{y:480}"
              bordered
              rowKey="enumber"
              size="small"
              style="padding-top:10px;"
            >
              <a-table-column
                :width="60"
                data-index="ename"
                title="评审人员"></a-table-column>
              <a-table-column :width="40" align="center" title="排序">
                <template slot-scope="text,record,index">
                  <span style="width: 20px; display: inline-block">
                    <a-icon
                      v-if="index !== 0"
                      title="向上"
                      type="arrow-up"
                      @click="handleSort(index, true)" />
                  </span>
                  <span style="width: 20px; display: inline-block">
                    <a-icon
                      v-if="index !== content.list.length - 1"
                      title="向下"
                      type="arrow-down"
                      @click="handleSort(index, false)" />
                  </span>
                </template>
              </a-table-column>
              <a-table-column
                key="deptName"
                :width="100"
                align="center"
                data-index="deptName"
                title="部门"
              >
              </a-table-column>
              <a-table-column
                :width="100"
                data-index="position"
                title="职务"></a-table-column>
            </a-table>
          </a-tab-pane>
          <a-tab-pane key="2">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="评审内容"
                title="1、项目关键技术和实施方法；
                  2、项目费用预算情况；
                  3、项目组成员设置；
                  4、项目硬件投入情况；
                  5、项目试制预计占用生产资源情况。"
              />
            </template>
            <a-textarea
              v-model="content.liaisonMatters"
              :rows="10"
              placeholder=" 1、项目关键技术和实施方法； 2、项目费用预算情况； 3、项目组成员设置； 4、项目硬件投入情况；  5、项目试制预计占用生产资源情况。"
            />
          </a-tab-pane>
          <a-tab-pane key="3">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="评审意见"
                title="第一种：评审通过！
                  第二种：同意本项目立项实施！
                  第三种：请项目组成员积极配合，按计划实施项目！"
              />
            </template>
            <a-textarea
              v-model="content.opinion"
              :rows="10"
              placeholder=" 第一种：评审通过！ 第二种：同意本项目立项实施！ 第三种：请项目组成员积极配合，按计划实施项目！" />
          </a-tab-pane>
        </a-tabs>
      </a-row>
      <a-card
        :bodyStyle="{ padding: 0, marginTop: '12px' }"
        :bordered="false"
        :headStyle="{padding: 0}"
        title="评审结论">
        <a-row>
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="同意立项的人数"
              required
              style="margin-bottom:0px;">
              <a-input-number
                v-model="content.agreeCount"
                :max="$store.state.totalMax"
                :min="0"
                placeholder="请输入同意立项的人数"
                style="width:200px"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="反对立项的人数"
              required
              style="margin-bottom:0px;">
              <a-input-number
                v-model="content.opposeCount"
                :max="$store.state.totalMax"
                :min="0"
                placeholder="请输入反对立项的人数"
                style="width:200px"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              :labelCol="{xs: { span: 12 },sm: { span: 3 }}"
              :wrapperCol="{xs: { span: 12 },sm: { span: 6 }}"
              label="是否同意立项"
              required
              style="margin-bottom:0px;"
            >
              <a-radio-group
                v-model="content.checkedAgree"
                :defaultValue="content.checkedAgree"
                :options="plainOptions"
                @change="onPlainOptionsChange"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
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
        :isShowEType="false"
        @addMemberList="handleOk"></employee-Modal>
    </a-form>
  </a-card>
</template>
<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import SelectEmployee from '@/components/SelectEmployee'
import employeeModal from './modules/EmployeeModal'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import {
  domName,
  popupContainer
} from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'
import YsTooltip from '@/components/YsTooltip/YsTooltip'
import yearMixin from '@/utils/yearMixin'

const projectOptions = ['研发类', '实施类']
const plainOptions = ['同意立项', '不同意立项']
export default {
  name: 'ReviewForm',
  components: {
    employeeModal,
    AuditFooter,
    SelectEmployee,
    YsTooltip
  },
  mixins: [yearMixin, dateMixin],
  created () {
    this.content = getTemplateContent('reviewForm')
    this.BPContent = getTemplateContent('reviewForm')
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
      rdDeptNameKeyMap: undefined,
      projectOptions,
      plainOptions,
      checkedAgree: undefined,
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
      computedFields: ['liaisonMatters', 'opinion'],
      selectedRowKeys: [],
      requiredFields: ['host.enumber', 'reviewTime', 'reviewAddress', 'list', 'liaisonMatters', 'opinion', 'agreeCount', 'opposeCount', 'checkedAgree']
    }
  },
  watch: {
    docId () {
      this.content = getTemplateContent('reviewForm')
      this.BPContent = getTemplateContent('reviewForm')
    },
    'content.checkedAgree' (value) {
      const self = this
      self.content.checkedAgreeOptionStr = ''
      this.plainOptions.map(item => {
        if (value === item) {
          self.content.checkedAgreeOptionStr += '   [ √ ]' + item
        } else {
          self.content.checkedAgreeOptionStr += '   [    ]' + item
        }
      })
    },
    checkedList (list) {
      const self = this
      self.content.checkedOptionArr = []
      this.projectOptions.map(item => {
        if (list.indexOf(item) > -1) {
          self.content.checkedOptionArr.push(true)
        } else {
          self.content.checkedOptionArr.push(false)
        }
      })
    },
    project (v) {
      this.content._pro = v.id
    }
  },
  methods: {
    handleTemplateEvent () {
      this.selectedRowKeys = []
    },
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    handleDels () {
      const tempList = []
      this.content.list.forEach(item => {
        if (!this.selectedRowKeys.includes(item.enumber)) {
          tempList.push(item)
        }
      })
      this.selectedRowKeys = []
      this.content.list = tempList
    },
    // disabledDate (current) {
    //   return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    // },
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
    onPlainOptionsChange (e) {

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
      this.$refs.employeeModal.add(this.project.id, this.project.beginYear, enumberArr, this.fileDate, this.docId)
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    },
    // 排序 向上true 向下false
    handleSort (index, isUp = true) {
      const targetIndex = isUp ? index - 1 : index + 1
      this.content.list.splice(index, 1, ...this.content.list.splice(targetIndex, 1, this.content.list[index]))
    }
  }
}
</script>

<style>
</style>
