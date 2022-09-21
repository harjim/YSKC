<!--
 * @Author: ldx
 * @Date: 2021-03-05 16:40:30
 * @LastEditTime: 2022-04-01 09:45:38
 * @LastEditors: lzh
 * @Description: 项目信息汇总明细数据展示modal
 * @FilePath: \RS-VUE\src\views\project\modules\ProjectDataModal.vue
-->
<template>
  <a-modal
    :afterClose="afterClose"
    :bodyStyle="{maxHeight: '85vh', height: '85vh', overflow: 'auto'}"
    :maskClosable="false"
    :title="title"
    :visible="isVisible"
    :width="1000"
    style="top:0;"
    @cancel="isVisible = false">
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <budget
        v-if="isAction && !isFinal"
        ref="budget"
        :isFilter="isFilter"
        :isFinal="isFinal"
        :isShowTitle="false"
        :projectData="projectData"
        :url="url"></budget>
      <capital-budget
        v-if="isAction && isFinal"
        ref="capitalBudget"
        :isFinal="isFinal"
        :projectData="projectData"
        :url="url"></capital-budget>
      <div v-if="!isAction" class="table-wrap">
        <ystable
          v-if="url"
          ref="table"
          :columns="tableColumns"
          :params="params"
          :queryUrl="url"
          border
          highlight-hover-row
          show-header-overflow
          show-overflow
          size="small"
        >
          <template slot="etype" slot-scope="{ row: data }">
            {{ data.etype && data.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === data.etype).label : '' }}
          </template>
        </ystable>
      </div>
    </a-spin>
    <template #footer>
      <a-button @click="afterClose">关闭</a-button>
    </template>
  </a-modal>
</template>
<script>

import Budget from '@/components/CapitalBudget/Budget.vue'
import { CapitalBudget } from '@/components'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import { mapGetters } from 'vuex'
import { DETAIL_URLS } from '@/api/project/ProjectInfoGather'

const staffRdHourColumn = [
  {
    type: 'seq',
    title: '序号',
    width: 60,
    headerAlign: 'center',
    align: 'center'
  },
  {
    field: 'ename',
    title: '姓名',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'rdDeptName',
    title: '研发部门',
    minWidth: 150,
    headerAlign: 'center',
    align: 'left'
  },
  // { field: 'etype', formatter: `formatterEType(etype)`, title: '人员类型', minWidth: 120, headerAlign: 'center', align: 'center' },
  {
    field: 'etype',
    slots: { default: 'etype' },
    title: '人员类型',
    minWidth: 120,
    headerAlign: 'center',
    align: 'center'
  },
  {
    field: 'role',
    title: '项目角色',
    minWidth: 150,
    headerAlign: 'center',
    align: 'center'
  },
  {
    field: 'monthWorkOurs',
    title: '本月参与工作量',
    width: 120,
    headerAlign: 'center',
    align: 'right'
  }
]
const totalStaffRdHourColumn = [
  {
    type: 'seq',
    title: '序号',
    width: 60,
    headerAlign: 'center',
    align: 'center'
  },
  {
    field: 'ename',
    title: '姓名',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'rdDeptName',
    title: '研发部门',
    minWidth: 150,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'monthWorkOurs',
    title: '本月参与工作量',
    width: 120,
    headerAlign: 'center',
    align: 'right'
  }
]
const prodRdHourColumn = [
  {
    type: 'seq',
    title: '序号',
    width: 60,
    headerAlign: 'center',
    align: 'center'
  },
  {
    field: 'ecode',
    title: '资产代码',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'ename',
    title: '设备名称',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'monthHour',
    title: '运行工时',
    minWidth: 120,
    headerAlign: 'center',
    align: 'right'
  },
  {
    field: 'emodal',
    title: '型号',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'effect',
    title: '项目中的作用',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  }
]
const totalProdRdHourColumn = [
  {
    type: 'seq',
    title: '序号',
    width: 60,
    headerAlign: 'center',
    align: 'center'
  },
  {
    field: 'ecode',
    title: '资产代码',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'ename',
    title: '设备名称',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'monthHour',
    title: '运行工时',
    minWidth: 120,
    headerAlign: 'center',
    align: 'right'
  }
]
const materialRawColumn = [
  {
    type: 'seq',
    title: '序号',
    width: 60,
    headerAlign: 'center',
    align: 'center'
  },
  {
    field: 'mcode',
    title: '材料编码',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'mname',
    title: '材料名称',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'quantity',
    title: '数量',
    minWidth: 120,
    headerAlign: 'center',
    align: 'right'
  },
  {
    field: 'acqDate',
    title: '日期',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'billNo',
    title: '出库单号',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'remark',
    title: '备注',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'picker',
    title: '领料人',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  },
  {
    field: 'biller',
    title: '制单人',
    minWidth: 120,
    headerAlign: 'center',
    align: 'left'
  }
]

const columns = {
  staffRdHour: staffRdHourColumn,
  prodRdHour: prodRdHourColumn,
  materials: materialRawColumn,
  totalStaff: totalStaffRdHourColumn,
  totalProd: totalProdRdHourColumn
}
export default {
  name: 'ProjectDataModal',
  components: {
    CapitalBudget,
    Budget,
    ystable
  },
  mounted () {
    this.columns['yieldAmount'] = this.yieldAmountColumn
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  data () {
    return {
      isVisible: false,
      spinning: true,
      title: '',
      columns,
      DETAIL_URLS,
      tableColumns: [],
      tableDatas: [],
      isFinal: false,
      isFilter: true,
      yieldAmountColumn: [
        {
          type: 'seq',
          title: '序号',
          width: 60,
          headerAlign: 'center',
          align: 'center'
        },
        {
          field: 'trialDate',
          title: '试制日期',
          minWidth: 120,
          headerAlign: 'center',
          align: 'center',
          formatter: this.formatDate
        },
        {
          field: 'startTime',
          title: '开始时间',
          minWidth: 120,
          headerAlign: 'center',
          align: 'center',
          formatter: this.formatTime
        },
        {
          field: 'endTime',
          title: '结束时间',
          minWidth: 120,
          headerAlign: 'center',
          align: 'center',
          formatter: this.formatTime
        },
        {
          field: 'rdYield',
          title: '试验试制量',
          minWidth: 120,
          headerAlign: 'center',
          align: 'right'
        },
        {
          field: 'deptName',
          title: '地点',
          minWidth: 150,
          headerAlign: 'center',
          align: 'left'
        }
      ],
      params: {
        month: undefined,
        projectId: undefined,
        type: undefined
      },
      url: '',
      isAction: false,
      row: undefined,
      projectId: undefined,
      projectData: { id: undefined }
    }
  },
  methods: {
    moment,
    // formatterEType (etype) {
    //   return this.etypeList[etype]
    // },
    show (title, row, projectId, projectMap, year) {
      this.isVisible = true
      this.title = title === '费用决算(万元)' ? '费用决算(元)' : title
      this.spinning = true
      this.row = row
      this.projectId = projectId
      if (row.rdTitle === 'budget' || row.rdTitle === 'budgetCost') {
        this.url = row.url
        this.isAction = true
        this.projectData.id = projectId
        this.projectData.beginYear = projectMap[projectId].beginYear
        this.projectData.endYear = projectMap[projectId].endYear
        if (row.rdTitle === 'budgetCost') {
          this.isFinal = true
          this.isFilter = false
        } else {
          this.isFinal = false
          this.isFilter = true
        }
      } else {
        // console.log('row', row)
        this.isAction = false
        if (row.materials) {
          this.url = DETAIL_URLS[row.materials]
          this.params.type = row.type
          this.params.rdType = row.rdType
          this.params.projectId = projectId
          this.tableColumns = columns[row.materials]
        } else {
          let parent = row.parent
          if (!projectId) {
            this.params.year = year
            if (parent === 'staffRdHour') {
              parent = 'totalStaff'
            } else if (parent === 'prodRdHour') {
              parent = 'totalProd'
            }
          } else {
            this.params.projectId = projectId
          }
          this.url = DETAIL_URLS[parent]
          this.tableColumns = columns[parent]
        }
        if (row.month) {
          this.params.month = row.month
        }
        // console.log('this.params', this.params)
      }
      this.spinning = false
    },
    afterClose () {
      this.isAction = false
      this.isVisible = false
      this.title = ''
      this.url = ''
      this.isFinal = false
      this.params = {
        month: undefined,
        projectId: undefined,
        type: undefined
      }
      this.spinning = false
      this.row = undefined
      this.projectId = undefined
    },
    formatDate ({ cellValue }) {
      if (cellValue) {
        return this.moment(cellValue).format('YYYY-MM-DD')
      } else {
        return '-'
      }
    },
    formatTime ({ cellValue }) {
      if (cellValue) {
        return this.moment(cellValue).format('HH:mm:ss')
      } else {
        return '-'
      }
    }
  }
}
</script>
<style lang="less" scoped>
#spin {
  height: 100%;

  & /deep/ .ant-spin-nested-loading {
    height: 100%;
  }

  & /deep/ .ant-spin-container {
    height: 100%;
  }

  .table-wrap {
    height: 100%;
  }
}
</style>
