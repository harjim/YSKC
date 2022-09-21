<!--
 * @Author: zdf
 * @Date: 2022-04-06 08:38:22
 * @LastEditTime: 2022-04-15 17:14:30
 * @LastEditors: zdf
 * @Description: 小程序计划工时-计划
 * @FilePath: \RS-VUE\src\views\project\modules\RdEmployeePlanTab.vue
-->
<template>
  <a-spin :spinning="spinning" tip="请稍后..." style="height: 100%;">
    <a-form layout="inline">
      <select-project ref="creatProject" @projectSelect="projectSelected" :year="currentYear" :sign="2"></select-project>
    </a-form>
    <div v-if="showTable && projectId" style="height: 65vh">
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input v-model="paramData.ename" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item label="工号">
          <a-input v-model="paramData.enumber" placeholder="请输入工号" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" v-if="$auth('project:rdEmployeePlan:search')" @click="search(true)">查询</a-button>
        </a-form-item>
      </a-form>
      <!-- @completed="({data:{data}})=>{data = Object.}" -->
      <ystable
        rowId="id"
        ref="table"
        highlight-hover-row
        show-overflow
        height="110%"
        resizable
        auto-resize
        show-header-overflow
        :toolbar="{zoom:true,custom:true,refresh:true}"
        :params="paramData"
        queryUrl="/rdEmployeePlan/getList"
      >
        <template v-slot:buttons>
          <a-button
            type="primary"
            style="margin-right:10px;"
            v-if="$auth('project:rdEmployeePlan:save')"
            @click="saveData"
          >保存</a-button>
          <a-button
            type="primary"
            style="margin-right:10px;"
            v-if="$auth('project:rdEmployeePlan:import')"
            @click="()=>{$refs.uploadModal.show(tableField)}"
          >导入</a-button>
          <a-button
            type="primary"
            style="margin-right:10px;"
            v-if="$auth('project:rdEmployeePlan:export')"
            @click="$refs.exportPlan.open()"
          >导出</a-button>
          <a-button
            type="primary"
            style="margin-right:10px;"
            v-if="$auth('project:rdEmployeePlan:importAllocation')"
            @click="()=>{$refs.importAllocationModal.show(allocationTableField)}"
          >导入分配</a-button>
          <a-button
            type="primary"
            style="margin-right:10px;"
            v-if="$auth('project:rdEmployeePlan:agg')"
            @click="$refs.rdPlanAgg.show()"
          >费用归集</a-button>
        </template>
        <template v-if="$auth('project:rdEmployeePlan:save')">
          <vxe-table-column
            title="姓名"
            field="ename"
            remoteSort
            align="left"
            :width="100"/>
          <vxe-table-column
            title="工号"
            field="enumber"
            remoteSort
            align="left"
            min-width="100" />
          <vxe-table-column
            title="人员类型"
            field="etype"
            remoteSort
            align="left"
            min-width="100">
            <template v-slot="{row}">
              {{
                row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : ''
              }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="一月" field="dataMap.m1" key="m1" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[1]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[1]"
                  :step="step"
                  :precision="precision"
                  :value="getV(row)"
                  @change="v=>onChange(v,row,1)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="二月" key="m2" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[2]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[2]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m2?row.dataMap.m2.planTime: 0.00"
                  @change="v=>onChange(v,row,2)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="三月" key="m3" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[3]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[3]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m3?row.dataMap.m3.planTime: 0.00"
                  @change="v=>onChange(v,row,3)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="四月" key="m4" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[4]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[4]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m4?row.dataMap.m4.planTime: 0.00"
                  @change="v=>onChange(v,row,4)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="五月" key="m5" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[5]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[5]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m5?row.dataMap.m5.planTime: 0.00"
                  @change="v=>onChange(v,row,5)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="六月" key="m6" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[6]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[6]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m6?row.dataMap.m6.planTime: 0.00"
                  @change="v=>onChange(v,row,6)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="七月" key="m7" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[7]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[7]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m7?row.dataMap.m7.planTime: 0.00"
                  @change="v=>onChange(v,row,7)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="八月" key="m8" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[8]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[8]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m8?row.dataMap.m8.planTime: 0.00"
                  @change="v=>onChange(v,row,8)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="九月" key="m9" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[9]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[9]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m9?row.dataMap.m9.planTime: 0.00"
                  @change="v=>onChange(v,row,9)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="十月" key="m10" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[10]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[10]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m10?row.dataMap.m10.planTime: 0.00"
                  @change="v=>onChange(v,row,10)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="十一月" key="m11" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[11]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[11]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m11?row.dataMap.m11.planTime: 0.00"
                  @change="v=>onChange(v,row,11)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="十二月" key="m12" align="center" :width="120">
            <template v-slot="{ row }">
              <template v-if="monthDay[12]">
                <a-input-number
                  :min="minNum"
                  :max="monthDay[12]"
                  :step="step"
                  :precision="precision"
                  :value="row.dataMap.m12?row.dataMap.m12.planTime: 0.00"
                  @change="v=>onChange(v,row,12)" />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="合计" field="totalTime" key="totalTime" align="center" :width="80">
          </vxe-table-column>
        </template>
        <template v-else>
          <vxe-table-column title="姓名" field="ename" key="ename" remoteSort align="center"/>
          <vxe-table-column title="工号" field="enumber" key="enumber" remoteSort align="center" />
          <vxe-table-column title="一月" key="m1" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m1?row.dataMap.m1.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="二月" key="m2" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m2?row.dataMap.m2.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="三月" key="m3" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m3?row.dataMap.m3.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="四月" key="m4" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m4?row.dataMap.m4.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="五月" key="m5" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m5?row.dataMap.m5.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="六月" key="m6" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m6?row.dataMap.m6.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="七月" key="m7" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m7?row.dataMap.m7.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="八月" key="m8" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m8?row.dataMap.m8.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="九月" key="m9" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m9?row.dataMap.m9.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="十月" key="m10" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m10?row.dataMap.m10.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="十一月" key="m11" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m11?row.dataMap.m11.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="十二月" key="m12" align="center">
            <template v-slot="{ row }">
              {{ row.dataMap.m12?row.dataMap.m12.planTime:'-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="合计" field="totalTime" align="center" :width="80">
          </vxe-table-column>
        </template>
      </ystable>
    </div>
    <upload-modal
      :showProgress="true"
      paramKey="name"
      title="导入研发人员计划"
      ref="uploadModal"
      action="/doc/rdEmployeePlan/importRdPlan"
      templateName="研发人员计划模板"
      @onSuccess="success"
      :sampleData="sampleData"
    />
    <export-rd-employee-plan-modal ref="exportPlan" />
    <rd-employee-plan-agg-modal ref="rdPlanAgg" />
    <ImportAllocationModal
      :width="1400"
      :showProgress="true"
      paramKey="name"
      title="导入分配"
      ref="importAllocationModal"
      action="/doc/rdEmployeePlan/importAllocation"
      templateName="研发人员计划分配模板"
      @onSuccess="success"
      :sampleData="allocationSampleData"
    />
  </a-spin>
</template>

<script>
import { SelectProject, UploadModal } from '@/components'
import moment from 'moment'
import ystable from '@/components/Table/ystable'
import yearMiXin from '@/utils/yearMixin'
import ExportRdEmployeePlanModal from './ExportRdEmployeePlanModal'
import RdEmployeePlanAggModal from './RdEmployeePlanAggModal'
import ImportAllocationModal from './ImportAllocationModal.vue'
export default {
  mixins: [yearMiXin],
  name: 'RdEmployeePlanTab',
  components: {
    ystable,
    SelectProject,
    UploadModal,
    ExportRdEmployeePlanModal,
    RdEmployeePlanAggModal,
    ImportAllocationModal
  },
  props: {
    currentKey: {
      type: String,
      default: '1'
    }
  },
  data () {
    return {
      tabKey: '1',
      month: '',
      spinning: false,
      projectId: 0,
      pname: '',
      paramData: {},
      showTable: true,
      filterObj: {},
      beginDate: '',
      endDate: '',
      monthDay: {},
      minNum: 0,
      step: 1,
      precision: 1,
      tableField: {
        tableId: 'dataEquipmentTable',
        fieldTitleObject: {
          month: { title: '月份', required: true, defaultTitle: '月份', importField: true },
          rdTitle: { title: 'RD', required: true, defaultTitle: 'RD', importField: true },
          ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
          enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
          planTime: { title: '计划工时', required: true, defaultTitle: '计划工时', importField: true }
          // m1: { title: '一月', defaultTitle: '一月', importField: true },
          // m2: { title: '二月', defaultTitle: '二月', importField: true },
          // m3: { title: '三月', defaultTitle: '三月', importField: true },
          // m4: { title: '四月', defaultTitle: '四月', importField: true },
          // m5: { title: '五月', defaultTitle: '五月', importField: true },
          // m6: { title: '六月', defaultTitle: '六月', importField: true },
          // m7: { title: '七月', defaultTitle: '七月', importField: true },
          // m8: { title: '八月', defaultTitle: '八月', importField: true },
          // m9: { title: '九月', defaultTitle: '九月', importField: true },
          // m10: { title: '十月', defaultTitle: '十月', importField: true },
          // m11: { title: '十一月', defaultTitle: '十一月', importField: true },
          // m12: { title: '十二月', defaultTitle: '十二月', importField: true }
        },
        hasMonth: true
      },
      allocationTableField: {
        tableId: 'dataEquipmentAllocationTable',
        fieldTitleObject: {
          month: { title: '月份', required: true, defaultTitle: '月份', importField: true },
          ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
          enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
          planTime: { title: '计划工时', required: true, defaultTitle: '计划工时', importField: true }
        },
        hasMonth: true
      },
      sampleData: [{
        month: '格式："年-月-日"，一般为当月第一天，例如：2022-01-01',
        rdTitle: '2022RD01',
        ename: '张三',
        enumber: '格式：2022或AC20221128',
        planTime: '数字格式，例如：300000'
      }],
      allocationSampleData: [{
        month: '格式："年-月-日"，一般为当月第一天，例如：2022-01-01',
        ename: '张三',
        enumber: '格式：2022或AC20221128',
        planTime: '数字格式，例如：300000'
      }]
    }
  },
  watch: {
    currentKey (key) {
      if (key === this.tabKey) {
        this.search(true)
      }
    }
  },
  created () {
    this.paramData.year = this.currentYear
    this.loadHourBit()
  },

  methods: {
    moment,
    saveData () {
      if (this.filterObj) {
        this.spinning = true
        const objAry = []
        for (const obj in this.filterObj) {
          if (this.filterObj[obj]) {
            objAry.push(this.filterObj[obj])
          }
        }
        if (!objAry.length) {
          this.spinning = false
          this.$message.warning('数据未更改，不进行保存操作')
          return
        }
        this.$http.post('/rdEmployeePlan/add', objAry).then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
            this.search(false)
          } else {
            this.$message.error(`${res.errorMessage ? res.errorMessage : '保存失败'}`)
          }
          this.spinning = false
        })
      }
      return true
    },
    getV (row) {
      return row.dataMap.m1 ? row.dataMap.m1.planTime : 0.00
    },
    getMaxMonthHour () {
      var begin = new Date(this.beginDate)
      var end = new Date(this.endDate)
      var beginYear = begin.getFullYear()
      var endYear = end.getFullYear()
      var beginMonth = begin.getMonth()
      var endMonth = end.getMonth()
      const maxObj = {}
      const hours = [744, 0, 744, 720, 744, 720, 744, 744, 720, 744, 720, 744]
      for (let index = 1; index <= 12; index++) {
        maxObj[index] = hours[index - 1]
        if (index === 2) {
          maxObj[2] = (this.currentYear % 4 === 0 && this.currentYear % 100 !== 0) ? 696 : 672
        }
        if (beginYear === this.currentYear) {
          if (index - 1 < beginMonth) {
            maxObj[index] = 0
            continue
          }
        }
        if (endYear === this.currentYear) {
          if (index - 1 > endMonth) {
            maxObj[index] = 0
          }
        }
      }
      this.monthDay = maxObj
    },
    onChange (v, row, mKey) {
      const key = 'm' + mKey
      var data = row.dataMap[key]
      if (!row.dataMap[key]) {
        data = { month: `${this.currentYear}-${mKey >= 10 ? mKey : '0' + mKey}-01`, enumber: row.enumber, projectId: row.projectId }
      }
      var col
      data.planTime = v
      this.$set(row.dataMap, key, data)
      if (v || row.dataMap[key].id) {
        col = row.dataMap[key]
      } else {
        col = null
      }
      this.filterObj[`${row.enumber}_${key}`] = col
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {
          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.search(true)
    },
    search (refresh) {
      this.filterObj = {}
      this.paramData.year = this.currentYear
      if (this.$refs.table && this.currentKey === this.tabKey) {
        this.$refs.table.refresh(refresh)
      }
    },
    projectSelected (value, project) {
      this.filterObj = {}
      this.projectId = value
      this.paramData.projectId = value
      this.beginDate = project.beginDate
      this.endDate = project.endDate
      this.pname = project.pname
      //   this.paramData.projectId = value
      this.getMaxMonthHour()
      if (value === 0) {
        this.showTable = false
      } else {
        this.showTable = true
        this.search(true)
      }
    },
    loadHourBit () {
      this.$http.get('/companySetting/getEmployeeHourBit').then(res => {
        if (res.success) {
          this.precision = Number(res.data)
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
</style>
