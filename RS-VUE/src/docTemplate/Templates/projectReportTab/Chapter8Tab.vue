<!--
 * @Author:
 * @Date: 2021-11-19 09:22:38
 * @LastEditTime: 2021-11-23 16:50:46
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\projectReportTab\Chapter8Tab.vue
-->

<template>
  <div>
    <vxe-grid
      ref="table"
      id="Chapter8Tab"
      :data="tableDate"
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
      <vxe-table-column title="名称" field="ename"></vxe-table-column>
      <vxe-table-column title="单价" field="unitPrice"></vxe-table-column>
      <vxe-table-column title="数量" field="quantity"></vxe-table-column>
      <vxe-table-column title="总价" field="totalPrice"></vxe-table-column>
      <vxe-table-column title="折旧方法"></vxe-table-column>
      <vxe-table-column title="折旧年限" field="usefullife"></vxe-table-column>
    </vxe-grid>
  </div>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'
export default {
  name: 'Chapter8Tab',
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
    }
  },
  data () {
    return {
      tableDate: [],
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
  computed: {
    ...mapGetters(['currentYear'])
  },
  created () {
    this.queryProject()
  },
  methods: {
    moment,
    queryProject () {
      this.$http.get('/initEquipment/getEquList', { params: { projectId: this.project.id, year: this.currentYear } })
        .then(res => {
          if (res.data) {
            this.tableDate = res.data
            // this.content.equList = res.data
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
    },
    refresh (ids) {
      this.content.equList = ids
      this.queryProject()
    },
    handleError () {}
  }
}
</script>

<style scoped>
</style>
