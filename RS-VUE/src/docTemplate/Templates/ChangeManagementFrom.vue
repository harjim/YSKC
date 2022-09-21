<!--
 * @Description: 变更管理报表 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ChangeManagementFrom.vue
-->
<template>
  <a-card>
    <a-button style="text-align: left" type="primary" @click="addRecord">添加</a-button>
    <a-table
      style="margin-top: 8px"
      bordered
      size="middle"
      :dataSource="content.tableData"
      :pagination="false"
      :rowKey="(record, index) => index"
      :scroll="{x: 1200}"
    >
      <a-table-column title="序号" data-index="index" :width="50">
        <template slot-scope="text, record, index">
          {{ index + 1 }}
        </template>
      </a-table-column>
      <a-table-column title="事项" data-index="matter" :width="200">
        <template slot-scope="text, record">
          <a-input v-model="record.matter" placeholder="请输入事项" />
        </template>
      </a-table-column>
      <a-table-column title="计划" data-index="planDate" :width="200">
        <template slot-scope="text, record">
          <a-textarea v-model="record.planDate" :rows="3" placeholder="请输入计划" />
        </template>
      </a-table-column>
      <a-table-column title="实际" data-index="actualDate" :width="200">
        <template slot-scope="text, record">
          <a-textarea v-model="record.actualDate" :rows="3" placeholder="请输入实际" />
        </template>
      </a-table-column>
      <a-table-column title="变更原由" data-index="reason" :width="200">
        <template slot-scope="text, record">
          <a-textarea v-model="record.reason" :rows="3" placeholder="请输入变更原由" />
        </template>
      </a-table-column>
      <a-table-column title="变更日期" data-index="reasonDate" :width="180">
        <template slot-scope="text, record">
          <a-date-picker
            @change="(date, dateString) => onDateChange(dateString, record, 'reasonDate')"
            :value="record.reasonDate ? moment(record.reasonDate, 'YYYY-MM-DD') : undefined"
            placeholder="请输入变更日期"
          />
        </template>
      </a-table-column>
      <a-table-column title="申请人" data-index="applicant" :width="200">
        <template slot-scope="text, record">
          <a-input v-model="record.applicant" placeholder="请输入申请人" />
        </template>
      </a-table-column>
      <a-table-column title="佐证材料" data-index="materials" :width="200">
        <template slot-scope="text, record">
          <a-input v-model="record.materials" placeholder="请输入佐证材料" />
        </template>
      </a-table-column>
      <a-table-column title="操作" :width="60">
        <template slot-scope="text, record, index">
          <a @click="delRecord(index)">删除</a>
        </template>
      </a-table-column>
    </a-table>
    <a-divider />
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
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import AuditFooter from './modules/AuditFooter'
import dateMixin from './js/dateMixin'
import moment from 'moment'

export default {
  name: 'ChangeManagementFrom',
  components: {
    AuditFooter
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    project: {
      type: Object,
      required: true
    }
  },
  mixins: [dateMixin],
  data () {
    return {
      content: {},
      BPContent: {}
    }
  },
  created () {
    this.content = getTemplateContent('ChangeManagementFrom')
    this.BPContent = getTemplateContent('ChangeManagementFrom')
  },
  methods: {
    moment,
    addRecord () {
      this.content.tableData.push({
        matter: undefined,
        planDate: undefined,
        actualDate: undefined,
        reason: undefined,
        reasonDate: undefined,
        applicant: undefined,
        materials: undefined
      })
    },
    delRecord (index) {
      this.content.tableData.splice(index, 1)
    },
    onDateChange (dateString, record, name) {
      if (dateString) {
        record[name] = moment(dateString).format('YYYY-MM-DD')
      } else {
        record[name] = undefined
      }
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-table {
  width: 100%;
}
</style>
