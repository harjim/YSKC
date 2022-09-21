<!--
 * @Author: ldx
 * @Date: 2021-01-22 14:19:26
 * @LastEditTime: 2021-07-27 14:09:32
 * @LastEditors: ldx
 * @Description: 研发设备仪器审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\EquipmentTab.vue
-->
<template>
  <tab-layout>
    <template #up>
      <div class="table_wrap">
        <ystable
          ref="table"
          height="100%"
          highlight-hover-row
          highlight-current-row
          show-overflow="title"
          resizable
          auto-resize
          size="small"
          queryUrl="/projectProgress/getEquipmentList"
          :params="params"
          :columns="column"
          @completed="completed"
        >
          <template #top>
            <span class="span_wrap">设备: <span class="value">{{ headerInfo.prodCnt }}</span></span>
            <span class="span_wrap">仪器: <span class="value">{{ headerInfo.labCnt }}</span></span>
            <span class="span_wrap">软件摊销: <span class="value">{{ headerInfo.softCnt }}</span></span>
            <span class="span_wrap">研发仪器设备占比: <span class="value">{{ headerInfo.proportion }}%</span></span>
          </template>
        </ystable>
      </div>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" title="研发设备"></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import TabLayout from './TabLayout'
import ystable from '@/components/Table/ystable'
import AuditLog from './AuditLog'
export default {
  name: 'EquipmentTab',
  components: {
    ystable,
    AuditLog,
    TabLayout
  },
  props: {
    record: {
      type: Object,
      default: () => { return {} }
    },
    mode: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    this.params.year = this.record.year
    this.params.companyId = this.record.companyId
    this.logGetParams.companyId = this.record.companyId
    this.logGetParams.year = this.record.year
    this.logGetParams.moduleId = this.mode.moduleId
    // this.callBack()
  },
  data () {
    return {
      column: [
        { type: 'seq', title: '序号', width: 60, headerAlign: 'center', align: 'center' },
        { field: 'ecode', title: '资产代码', width: 120, headerAlign: 'center', align: 'left' },
        { field: 'ename', title: '设备名称', width: 120, headerAlign: 'center', align: 'left' },
        { field: 'emodal', title: '设备型号', width: 120, headerAlign: 'center', align: 'left' },
        { field: 'etype',
          title: '设备类型',
          width: 120,
          headerAlign: 'center',
          align: 'left',
          formatter: ({ cellValue }) => {
            return cellValue ? this.$getEnums('equipmentEnum').find(item => item.value === Number(cellValue)).label : ''
          } },
        { field: 'deptName', title: '研发部门', width: 120, headerAlign: 'center', align: 'left' },
        { field: 'workshop', title: '车间', width: 120, headerAlign: 'center', align: 'left' },
        { field: 'productLine', title: '产线', width: 120, headerAlign: 'center', align: 'left' },
        { field: 'processSection', title: '工艺段', width: 120, headerAlign: 'center', align: 'left' }
      ],
      spinningLog: false,
      headerInfo: {
        prodCnt: '-',
        labCnt: '-',
        softCnt: '-'
      },
      params: {
        year: undefined,
        companyId: undefined
      },
      logGetParams: {
        companyId: undefined,
        year: undefined,
        moduleId: undefined
      }
    }
  },
  methods: {
    completed (data) {
      Object.assign(this.headerInfo, data.data.header)
    },
    callBack () {
      if (this.$refs.auditLog && this.$refs.auditLog.handleGetAuditLog) {
        this.$refs.auditLog.handleGetAuditLog()
      }
    },
    updataStatus () {
      this.$emit('updataStatus')
    }
  }
}
</script>

<style lang="less" scoped>
* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis>.vxe-body--expanded-cell {
  overflow: auto;
}
</style>
