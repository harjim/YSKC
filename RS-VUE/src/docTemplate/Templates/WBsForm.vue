<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2021-05-11 08:59:32
 * @LastEditors: ldx
 * @Description:工作分解结构
 * @FilePath: \RS-VUE\src\docTemplate\Templates\WBsForm.vue
-->
<template>
  <a-card>
    <a-form>
      <a-row>
        <a-button type="primary" style="margin-bottom: 10px;" @click="addRow">添加</a-button>
        <a-table
          bordered
          rowKey="num"
          :dataSource="content.list"
          :pagination="false"
          size="small"
          :scroll="{x:1600, y:480}"
          :customCell="{valign:'top'}"
        >
          <a-table-column valign="top" title="序号" data-index="num" :width="60">
            <template slot-scope="text,record,index">{{ index + 1 }}</template>
          </a-table-column>
          <a-table-column title="阶段" data-index="stage" :width="160">
            <template slot-scope="text,record">
              <a-input v-model="record.stage" placeholder="阶段" />
            </template>
          </a-table-column>
          <a-table-column title="开始日期" data-index="beginDate" :width="160">
            <template slot-scope="text,record">
              <a-date-picker
                style="width:100%"
                @change="(dates,dateString)=>onDateChange(dateString,record,'beginDate')"
                :value="record.beginDate ? moment(record.beginDate) : undefined"
                format="YYYY/MM/DD"
                :allowClear="false"
                :disabledDate="(val)=>disabledBDate(val,record)"
              />
            </template>
          </a-table-column>
          <a-table-column title="结束日期" data-index="endDate" :width="160">
            <template slot-scope="text,record">
              <a-date-picker
                style="width:100%"
                @change="(dates,dateString)=>onDateChange(dateString,record,'endDate')"
                :value="record.endDate ? moment(record.endDate) : undefined"
                format="YYYY/MM/DD"
                :allowClear="false"
                :disabledDate="(val)=>disabledEDate(val,record)"
              />
            </template>
          </a-table-column>
          <a-table-column title="负责人" data-index="member" :width="200">
            <template slot-scope="text,record">
              <!-- <a-input v-model="record.ename" placeholder="反馈人" /> -->
              <select-employee
                :selected="text"
                :year="project.beginYear"
                @select="member=>record.member = member"
              />
            </template>
          </a-table-column>
          <a-table-column title="活动内容" data-index="solution" :width="300">
            <template slot-scope="text,record">
              <a-textarea v-model="record.solution" placeholder="活动内容" :rows="1"></a-textarea>
            </template>
          </a-table-column>
          <a-table-column title="输出内容" data-index="solutionState" :width="300">
            <template slot-scope="text,record">
              <a-textarea v-model="record.solutionState" placeholder="输出内容" :rows="1"></a-textarea>
            </template>
          </a-table-column>
          <a-table-column title="备注" data-index="remark" :width="140">
            <template slot-scope="text,record">
              <a-textarea v-model="record.remark" placeholder="备注" :rows="1"></a-textarea>
            </template>
          </a-table-column>
          <a-table-column title="操作" data-index="action" align="center">
            <template slot-scope="text,record">
              <a type="primary" @click="removeRow(record)">移除</a>
            </template>
          </a-table-column>
        </a-table>
      </a-row>
      <a-divider/>
      <a-row >
        <a-col :span="8">
          <a-form-item label="编制" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
            <select-employee
              :selected="content.toCompile"
              :year="project.beginYear"
              @select="toCompile=>content.toCompile = toCompile"
            />
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label="审核" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
            <select-employee
              :selected="content.audit"
              :year="project.beginYear"
              @select="audit=>content.audit = audit"
            />
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label="批准" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
            <select-employee
              :selected="content.approval"
              :year="project.beginYear"
              @select="approval=>content.approval = approval"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-card>
</template>
<script>
import moment from 'moment'
import { SelectEmployee } from '@/components/'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
export default {
  name: 'WBsForm',
  components: {
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
    this.content = getTemplateContent('wBsForm')
    this.BPContent = getTemplateContent('wBsForm')
  },
  data () {
    return {
      row: { stage: '', beginDate: undefined, endDate: undefined, member: {}, solution: '', solutionState: '', remark: '' },
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
      project: {}
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
    disabledBDate (current, record) {
      if (record.endDate !== undefined) {
        return moment(this.project.beginDate) > current || current >= moment(record.endDate).add(1, 'days')
      }
      return moment(this.project.beginDate) > current || current >= moment(this.project.endDate).add(1, 'days')
    },
    disabledEDate (current, record) {
      if (record.beginDate !== undefined) {
        return current >= moment(this.project.endDate).add(1, 'days') || current < record.beginDate
      }
      return moment(this.project.beginDate) > current || current >= moment(this.project.endDate).add(1, 'days')
    },
    onDateChange (dateString, record, name) {
      record[name] = moment(dateString)
    },
    addRow () {
      const tempRow = { ...this.row }
      tempRow.num = this.content.list.length + 1
      this.content.list.push(tempRow)
    },
    removeRow (record) {
      const tempRows = []
      var num = 0
      for (var i = 0; i < this.content.list.length; i++) {
        const row = this.content.list[i]
        if (row.num !== record.num) {
          row.num = ++num
          tempRows.push(row)
        }
      }
      this.content.list = tempRows
    }
  }
}
</script>

<style>
</style>
