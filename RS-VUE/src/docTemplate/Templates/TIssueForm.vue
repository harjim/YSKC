<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-09-14 12:12:00
 * @LastEditors: hm
 * @Description:问题记录日志 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\TIssueForm.vue
-->
<template>
  <a-card>
    <a-form id="wrap_form">
      <a-row>
        <a-col :span="10">
          <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span>{{ project.pname }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="7">
          <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span>{{ project.beginYear }}RD{{ project.rdIndex | ZeroFormat }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="7">
          <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : '' }}</a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <i class="required-field"/>
        <a-button type="primary" style="margin-bottom: 10px; margin-right: 10px" @click="addRow">添加</a-button>
        <a-tooltip placement="top" :getPopupContainer="getCalendarContainer">
          <template slot="title">
            <span>从该项目验证报告中引入数据</span>
          </template>
          <a-button type="primary" style="margin-bottom: 10px; margin-right: 10px;" @click="importData">引入数据</a-button>
        </a-tooltip>
        <a-popconfirm title="是否确定移除?" @confirm="removeRows()" :disabled="!selectRowKeys || !selectRowKeys.length">
          <a-button type="primary" style="margin-bottom: 10px; margin-right: 10px;" :disabled="!selectRowKeys || !selectRowKeys.length">移除</a-button>
        </a-popconfirm>
        <vxe-grid
          bordered
          :data="content.list"
          border
          resizable
          auto-resize
          highlight-hover-row
          show-overflow
          show-header-overflow
          highlight-current-row
          header-align="center"
          @checkbox-change="selectChange"
          @checkbox-all="selectChange">
          <vxe-table-column type="checkbox" :width="40"/>
          <vxe-table-column title="序号" field="num" :width="50" />
          <vxe-table-column title="产出数量" field="actualPO" :width="130">
            <template v-slot="{row}">
              <a-input v-model="row.actualPO" placeholder="产出数量"/>
            </template>
          </vxe-table-column>
          <vxe-table-column title="地点" field="addr" :width="180">
            <template v-slot="{row}">
              <a-input v-model="row.addr" placeholder="试产地点" />
            </template>
          </vxe-table-column>
          <vxe-table-column title="试制时间" field="interval" :width="160">
            <template v-slot="{row}">
              <!-- <a-date-picker
                :getCalendarContainer="getCalendarContainer"
                style="width: 100%"
                @change="(dates, dateString) => onDateChange(dateString, row, 'beginDate')"
                :value="row.beginDate ? moment(row.beginDate, 'YYYY-MM-DD') : undefined"
                format="YYYY-MM-DD"
                :disabledDate="(val) => disabledDate(val)"
              /> -->
              <a-input v-model="row.beginDate" placeholder="试制时间" />
            </template>
          </vxe-table-column>
          <vxe-table-column title="试制问题" field="problem" :width="200">
            <template v-slot="{row}">
              <a-textarea v-model="row.problem" placeholder="试制问题" :rows="1"></a-textarea>
            </template>
          </vxe-table-column>
          <vxe-table-column title="解决方案" field="solution" :width="200">
            <template v-slot="{row}">
              <a-textarea v-model="row.solution" placeholder="解决方案" :rows="1"></a-textarea>
            </template>
          </vxe-table-column>
          <vxe-table-column title="实施情况" field="solutionState" :width="140">
            <template v-slot="{row}">
              <a-textarea v-model="row.solutionState" placeholder="实施情况" :rows="1"></a-textarea>
            </template>
          </vxe-table-column>
          <vxe-table-column title="反馈人" field="member" :width="150">
            <template v-slot="{row}">
              <select-employee
                :mountDom="domName"
                :selected="{ ...row.member }"
                :year="year"
                @select="(v) => select(row.member, v, row)"
              />
            </template>
          </vxe-table-column>
          <vxe-table-column title="备注" field="remark" :width="140">
            <template v-slot="{row}">
              <a-textarea v-model="row.remark" placeholder="备注" :rows="1"></a-textarea>
            </template>
          </vxe-table-column>
        </vxe-grid>
      </a-row>
      <a-divider></a-divider>
      <audit-footer
        :projectId="projectId"
        :docId="docId"
        :year="project.beginYear"
        :content.sync="content"/>
    </a-form>
  </a-card>
</template>
<script>
// import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import { SelectEmployee } from '@/components/'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'
export default {
  name: 'TIssueForm',
  mixins: [dateMixin],
  components: {
    AuditFooter,
    SelectEmployee
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
  created () {
    this.content = getTemplateContent('tIssueForm')
    this.BPContent = getTemplateContent('tIssueForm')
  },
  data () {
    return {
      domName,
      row: {
        num: 0,
        actualPO: '',
        addr: '',
        interval: '',
        beginDate: undefined,
        problem: '',
        solution: '',
        solutionState: '',
        member: {},
        remark: ''
      },
      width: 1600,
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
      selectRowKeys: [],
      requiredFields: ['list']
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    }
  },
  methods: {
    selectChange ({ records }) {
      this.selectRowKeys = records.map(a => a.num)
    },
    // moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    // disabledDate (current) {
    //   return (
    //     current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    //   )
    // },
    // onDateChange (dateString, record, name) {
    //   if (dateString) {
    //     record[name] = moment(dateString).format('YYYY-MM-DD')
    //   } else {
    //     record[name] = undefined
    //   }
    // },
    addRow () {
      const tempRow = { ...this.row }
      tempRow.num = this.content.list.length + 1
      this.content.list.push(tempRow)
    },
    removeRows () {
      const tempRows = []
      var num = 0
      for (var i = 0; i < this.content.list.length; i++) {
        const row = this.content.list[i]
        if (!this.selectRowKeys.includes(row.num)) {
          row.num = ++num
          tempRows.push(row)
        }
      }
      this.selectRowKeys = []
      this.content.list = tempRows
    },
    // setHourStr (record) {
    //   record.hourStr = record.hour + ' h'
    // }
    select (text, v, record) {
      record['member'] = v
    },
    importData () {
      this.$http.get('/projectDocFileData/importReportData', { params: { projectId: this.project.id } })
        .then(res => {
          if (res.success) {
            if (res.data && res.data.length) {
              let num = 0
              if (this.content.list && this.content.list.length) {
                num = this.content.list.length
              } else {
                this.content.list = []
              }
              res.data.forEach(a => {
                num += 1
                a.num = num
                a['member'] = { enumber: a.enumber, ename: a.ename }
                delete a.enumber
                delete a.ename
                this.content.list.push(a)
              })
              this.$message.success('引入成功')
            } else {
              this.$message.warning('该项目还未录入验证报告文档，没有数据可以引入')
            }
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '获取数据失败，请联系管理员')
          }
        })
    }
  }
}
</script>

<style lang="less" scoped>
#wrap_form /deep/ .ant-table {
  width: 100%;
}
</style>
