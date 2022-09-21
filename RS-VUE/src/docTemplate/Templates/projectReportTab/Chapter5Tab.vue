<template>
  <div>
    <a-button type="primary" style="margin-bottom: 10px;margin-right:10px;" @click="addRow">添加</a-button>
    <a-button type="primary" style="margin-bottom: 10px;margin-right:10px;" @click="addStage">引入项目阶段</a-button>
    <vxe-grid
      ref="table"
      :data="content.chapter5List"
      border
      resizable
      auto-resize
      highlight-hover-row
      show-overflow
      show-header-overflow
      highlight-current-row
      header-align="center"
    >
      <vxe-table-column title="序号" type="seq" width="55" align="center"></vxe-table-column>
      <vxe-table-column title="开始日期" field="beginDate" width="150" align="left" header-align="center">
        <template #default="{row}">
          <a-date-picker
            style="width:100%"
            :getCalendarContainer="getCalendarContainer"
            @change="(dates,dateString)=>onDateChange(dateString,row,'beginDate')"
            :value="row.beginDate ? moment(row.beginDate,'YYYY-MM-DD') : row.beginDate"
            format="YYYY-MM-DD"
            :allowClear="false"
            :disabledDate="(val)=>disabledBDate(val,row)"
            @openChange="(status) => onOpenChange(status,'beginDate',row)"
          />
        </template>
      </vxe-table-column>
      <vxe-table-column title="结束日期" field="endDate" width="150" align="left" header-align="center">
        <template #default="{row}">
          <a-date-picker
            style="width:100%"
            :getCalendarContainer="getCalendarContainer"
            @change="(dates,dateString)=>onDateChange(dateString,row,'endDate')"
            :value="row.endDate ? moment(row.endDate,'YYYY-MM-DD') : row.endDate"
            format="YYYY-MM-DD"
            :allowClear="false"
            :disabledDate="(val)=>disabledEDate(val,row)"
            @openChange="(status) => onOpenChange(status,'endDate',row)"
          />
        </template>
      </vxe-table-column>
      <vxe-table-column title="阶段" field="stageGoal" width="150" align="left" header-align="center">
        <template #default="{row}">
          <a-input v-model="row.stageGoal" placeholder="阶段" :rows="1"></a-input>
        </template>
      </vxe-table-column>
      <vxe-table-column title="主要工作内容" field="mainWork" min-width="300" align="left" header-align="center">
        <template #default="{row}">
          <a-textarea v-model="row.mainWork" placeholder="主要工作内容" :rows="2"></a-textarea>
        </template>
      </vxe-table-column>
      <vxe-table-column title="操作" width="90" align="center" header-align="center">
        <template #default="{row}">
          <a type="primary" @click="removeRow(row)">移除</a>
        </template>
      </vxe-table-column>
    </vxe-grid>
  </div>
</template>

<script>
import moment from 'moment'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
export default {
  name: 'Chapter5Tab',
  components: {},
  props: {
    content: {
      type: Object,
      required: true
    },
    project: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      domName,
      tableDate: [],
      activeKey: '1',
      row: { beginDate: undefined, endDate: undefined, stageGoal: '', mainWork: '' }
    }
  },
  methods: {
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    disabledBDate (current, record) {
      if (record.endDate !== undefined) {
        return moment(this.project.beginDate) > current || current >= moment(record.endDate).add(1, 'days')
      }
      return moment(this.project.beginDate) > current || current >= moment(this.project.endDate).add(1, 'days')
    },
    disabledEDate (current, record) {
      if (record.beginDate !== undefined) {
        return current >= moment(this.project.endDate).add(1, 'days') || current < moment(record.beginDate)
      }
      return moment(this.project.beginDate) > current || current >= moment(this.project.endDate).add(1, 'days')
    },
    callback (activeKey) {
      // 切换tab调用
      this.activeKey = activeKey
    },
    onDateChange (dateString, record, name) {
      this.$set(record, name, dateString)
    },
    addRow () {
      const tempRow = { ...this.row }
      tempRow.num = this.content.chapter5List.length + 1
      this.content.chapter5List.push(tempRow)
    },
    addStage () {
      this.$http.get('/projectDocFile/getReportStage', { params: { projectId: this.project.id } })
        .then(res => {
          if (res.data) {
            this.tableDate = res.data
            let num = this.content.chapter5List.length
            this.content.chapter5List.push(...res.data.map(item => {
              return { num: ++num, beginDate: item.beginDate, endDate: item.endDate, stageGoal: item.stageType, mainWork: item.workDesc }
            }))
          }
        })
    },
    removeRow (record) {
      const tempRows = []
      var num = 0
      for (var i = 0; i < this.content.chapter5List.length; i++) {
        const row = this.content.chapter5List[i]
        if (row.num !== record.num) {
          row.num = ++num
          tempRows.push(row)
        }
      }
      this.content.chapter5List = tempRows
    },
    onOpenChange (status, field, record) {
      if (!record[field] && status) {
        record[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    }
  }
}
</script>

<style scoped>
</style>
