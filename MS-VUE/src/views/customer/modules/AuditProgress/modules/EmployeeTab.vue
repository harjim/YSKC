<!--
 * @Author: ldx
 * @Date: 2021-01-22 14:20:08
 * @LastEditTime: 2022-08-08 14:40:27
 * @LastEditors: zdf
 * @Description: 研发花名册审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\EmployeeTab.vue
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
          @completed="completed"
          queryUrl="/projectProgress/getEmployeeList"
          :params="params"
          :columns="column"
        >
          <template v-slot:top>
            <a-tooltip placement="top">
              <template slot="title">
                <span>导出研发考勤绑定情况</span>
              </template>
              <a-button @click="exportData" :loading="btnLoading" type="primary" size="small" style="margin-right:10px;margin-bottom:4px;">导出</a-button>
            </a-tooltip>
            <span class="span_wrap">研究人员:  <span class="value">{{ headerInfo.researchCnt }}</span></span>
            <span class="span_wrap">技术人员:  <span class="value">{{ headerInfo.techCnt }}</span></span>
            <span class="span_wrap">辅助人员:  <span class="value">{{ headerInfo.assistCnt }}</span></span>
            <span class="span_wrap">研发人员占比:  <span class="value">{{ headerInfo.proportion }}%</span></span>
          </template>
        </ystable>
      </div>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" title="研发花名册"></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import TabLayout from './TabLayout'
import ystable from '@/components/Table/ystable'
import AuditLog from './AuditLog'
// const column =
export default {
  name: 'EmployeeTab',
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
        { field: 'enumber', title: '工号', width: 100, headerAlign: 'center', align: 'left' },
        { field: 'ename', title: '姓名', width: 120, headerAlign: 'center', align: 'left' },
        { field: 'etype',
          title: '人员类型',
          width: 120,
          headerAlign: 'center',
          align: 'left',
          formatter: ({ cellValue }) => {
            return cellValue && cellValue !== -1 ? this.$getEnums('rdEmployeeEnum').find(item => item.value === cellValue).label : '-'
          } },
        { field: 'hasBind', title: '绑定', width: 80, headerAlign: 'center', align: 'center', remoteSort: true },
        { field: 'deptName', title: '研发部门', headerAlign: 'center', align: 'left' },
        { field: 'specialities', title: '专业', headerAlign: 'center', align: 'left' }
      ],
      btnLoading: false,
      headerInfo: {
        researchCnt: '-',
        techCnt: '-',
        assistCnt: '-'
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
    },
    exportData () {
      this.btnLoading = true
      this.$exportData('/projectProgress/exportEmployeeBindInfo', Object.assign(this.params, { companyName: this.record.companyName }),
        `${this.record.companyName}-${this.params.year}研发考勤绑定情况.xlsx`, this.$message).then(res => {
        this.btnLoading = false
      })
    }
  }
}
</script>

<style lang="less" scoped>
* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis>.vxe-body--expanded-cell {
  overflow: auto;
}
</style>
