<!--
 * @Author: your name
 * @Date: 2021-11-18 15:48:09
 * @LastEditors: lzh
 * @LastEditTime: 2021-11-23 14:02:30
 * @Description: 研究人员名单
 * @FilePath: \RS-VUE\src\docTemplate\Templates\projectReportTab\Chapter7Tab.vue
-->
<template>
  <div>
    <vxe-grid
      ref="table"
      id="Chapter7Tab"
      :data="tableData"
      size="small"
      border
      resizable
      auto-resize
      highlight-hover-row
      show-overflow
      show-header-overflow
      highlight-current-row
      header-align="center"
      :toolbar="{ refresh: { query: queryProject }, zoom: true, custom: true }"
    >
      <vxe-table-column v-for="(config, index) in columns" :key="index" v-bind="config"></vxe-table-column>
    </vxe-grid>
  </div>
</template>

<script>
import moment from 'moment'
export default {
  name: 'Chapter7Tab',
  props: {
    content: {
      type: Object,
      required: true
    },
    project: {
      type: Object,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    columns: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      tableData: [],
      activeKey: '1'
    }
  },
  watch: {
    project (item) {
      this.$nextTick(() => {
        this.queryProject()
      })
    }
  },
  created () {
    this.queryProject()
  },
  methods: {
    moment,
    queryProject () {
      let memberList = []
      if (this.content.memberList && this.content.memberList.length) {
        memberList = this.content.memberList
      }
      this.$http.get('/initMember/getStaffList', { params: { projectId: this.project.id, addData: false, memberIds: memberList, pDocFileId: this.docId } })
        .then(res => {
          if (res.data) {
            this.tableData = res.data
            return res.data
          }
        })
    },
    callback (activeKey) {
      // 切换tab调用
      this.activeKey = activeKey
    },
    onDateChange (dateString, record, name) {
      record[name] = moment(dateString)
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-table{
  width: 100%;
}
</style>
