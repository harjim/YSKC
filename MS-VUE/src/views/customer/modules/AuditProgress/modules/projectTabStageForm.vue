<!--
 * @Author: lzh
 * @Date: 2021-09-15 15:48:54
 * @LastEditors: lzh
 * @LastEditTime: 2021-09-23 11:30:15
 * @Description: 项目名称审核Tab的嵌套子表[项目成员 资产清单 阶段]
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\projectTabStageForm.vue
-->
<template>
  <div>
    <!-- 阶段 -->
    <a-table
      rowKey="id"
      size="small"
      :columns="stageColumns"
      :dataSource="tableData"
      :pagination="false"
      :loading="loading"
    >
      <template slot="index" slot-scope="text,record,index">{{ index + 1 }}</template>
    </a-table>
  </div>
</template>

<script>
const stageColumns = [
  { title: '序号', dataIndex: 'index', key: 'index', fixed: 'left', width: 50, scopedSlots: { customRender: 'index' } },
  { title: '规划阶段', dataIndex: 'stageType', key: 'stageType', fixed: 'left', width: 100 },
  { title: '开始日期', dataIndex: 'beginDate', key: 'beginDate' },
  { title: '结束日期', dataIndex: 'endDate', key: 'endDate' },
  { title: '工作内容', dataIndex: 'workDesc', key: 'workDesc', width: 300, ellipsis: true },
  { title: '备注', dataIndex: 'remark', key: 'remark', ellipsis: true }
]
export default {
  props: {
    queryParam: {
      type: Object,
      default: () => {}
    }
  },
  data () {
    return {
      stageColumns,
      tableData: [],
      loading: false
    }
  },
  mounted () {
    this.getDatas()
  },
  methods: {
    getDatas () {
      this.loading = true
      this.$http.get('/RsStage/queryStage', { params: this.queryParam })
        .then(res => {
          this.tableData = []
          if (res.data && res.data.length > 0) {
            this.tableData = res.data
          }
        })
        .finally(() => {
          this.loading = false
        })
    }
  }
}
</script>
