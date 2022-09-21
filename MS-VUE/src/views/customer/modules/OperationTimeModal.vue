<!--
 * @Author: zdf
 * @Date: 2021-07-23 15:21:58
 * @LastEditTime: 2021-07-23 15:27:36
 * @LastEditors: zdf
 * @Description: 操作时间抽屉
 * @FilePath: \MS-VUE\src\views\customer\modules\OperationTimeModal.vue
-->
<template>
  <a-modal
    :title="drawerTitle"
    :width="500"
    :visible="drawerVisible"
    :footer="null"
    @cancel="drawerVisible = false"
  >
    <vxe-table
      bordered
      ref="table2"
      :data="DrawerTableData"
    >
      <vxe-table-column
        title="阶段"
        field="stageName"
        :width="150"
        align="center"
        :formatter="formatterCell"
      />
      <vxe-table-column
        title="操作人"
        field="realName"
        :width="150"
        align="center"
        show-header-overflow
        :formatter="formatterCell"
      />
      <vxe-table-column
        title="操作时间"
        field="operationTime"
        :width="150"
        align="center"
        show-header-overflow
        :formatter="formatterCell"
      />
    </vxe-table>
  </a-modal>
</template>

<script>
const row = [
  // { stageId: 10000, stageName: '基本信息' },
  { stageId: 10001, stageName: '花名册' },
  { stageId: 10002, stageName: '设备列表' },
  { stageId: 20000, stageName: '规划项目' },
  // { stageId: 30000, stageName: '立项准备' },
  { stageId: 30001, stageName: '研发架构' },
  { stageId: 30002, stageName: '研发设备' },
  { stageId: 30003, stageName: '研发人员' },
  // { stageId: 40000, stageName: '技术立项' },
  { stageId: 40001, stageName: '项目管理' },
  { stageId: 40002, stageName: '项目成员' },
  { stageId: 40003, stageName: '项目设备' },
  { stageId: 50000, stageName: '预算' },
  { stageId: 60000, stageName: '项目归集' },
  { stageId: 70000, stageName: '过程文档' }
]
export default {
  data () {
    return {
      drawerVisible: false,
      drawerTitle: '',
      DrawerTableData: []
    }
  },
  methods: {
    open (queryParam, title) {
      this.$http.get('/projectProgress/getProgressDetail', { params: queryParam }).then(
        res => {
          const data = res.data
          const stageObj = {}
          data.forEach(item => {
            stageObj[item.stage] = item
          })
          const newRow = row.map(rowItem => {
            const key = rowItem.stageId
            if (stageObj[key]) {
              rowItem.realName = stageObj[key].realName
              rowItem.operationTime = stageObj[key].operationTime
            } else {
              rowItem.realName = undefined
              rowItem.operationTime = undefined
            }
            return rowItem
          })
          this.DrawerTableData = newRow
          this.drawerVisible = true
          this.drawerTitle = title
        })
    },
    formatterCell ({ cellValue }) {
      if (cellValue) {
        return cellValue
      }
      return '-'
    }
  }
}
</script>

<style>

</style>
