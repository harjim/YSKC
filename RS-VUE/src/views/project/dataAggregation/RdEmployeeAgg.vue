<template>
  <div class="customCard">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input
            placeholder="请输入姓名"
            v-model="queryParam.ename"
            style="width: 165px"
          />
        </a-form-item>
        <a-form-item label="工号">
          <a-input
            placeholder="请输入工号"
            v-model="queryParam.enumber"
            style="width: 165px"
          />
        </a-form-item>
        <a-form-item label="人员类型">
          <a-select
            :allowClear="true"
            v-model="queryParam.etype"
            placeholder="请选择人员类型"
            default-value="-1"
            style="width: 165px"
          >
            <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="部门">
          <a-input
            placeholder="请输入部门"
            v-model="queryParam.deptName"
            style="width: 165px"
          />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          rowId="enumber"
          queryUrl="/projectRdEmployee/getList"
          :params="queryParam"
          highlight-hover-row
          highlight-current-row
          show-header-overflow
          resizable
          auto-resize
          @checkbox-change="onSelectChange"
          @checkbox-all="onSelectChange"
          :toolbar="{ zoom: true, custom: true, refresh: true }"
          @completed="({ data }) => completed(data)"
          show-overflow="title"
          :pagerConfig="{ pageSize: 50, pageSizes: [50, 100, 200, 500] }"
        >
          <template slot="buttons">
            <template v-if="canModify && $auth('project:data:agg')">
              <a-button
                :disabled="!this.selectedRowKeys || this.selectedRowKeys.length <= 0"
                type="primary"
                @click="setRatio"
              >
                设置研发比例
              </a-button>
              <a-button
                :disabled="!this.selectedRowKeys || this.selectedRowKeys.length <= 0"
                style="margin-left: 10px"
                type="primary"
                @click="setHours"
              >
                设置研发工时
              </a-button>
              <a-button
                style="margin-left: 10px"
                type="primary"
                @click="importRdHour"
              >导入研发工时</a-button>
              <a-button
                style="margin-left: 10px"
                type="primary"
                :disabled="!this.selectedRowKeys || this.selectedRowKeys.length <= 0"
                @click="adjustAmount"
              >
                调整费用
              </a-button>
              <a-button
                style="margin-left: 10px"
                type="primary"
                @click="refreshAgg"
              >
                刷新费用
              </a-button>
              <rd-fee-submit style="margin-left:10px;" :projectId="projectId" :month="month" :rdType="rdFeeType" @getSummary="getSummary"/>
            </template>
            <a-button
              :style="{ marginLeft: canModify ? '10px' : '0' }"
              type="primary"
              @click="showAttendance"
            >
              研发考勤
            </a-button>
            <span style="padding-left: 10px">
              总计：
              <a style="font-weight: 600">{{ headerData.total ? headerData.total : '-' }}</a>
            </span>
            <span style="padding-left: 10px">
              工资：
              <a style="font-weight: 600">{{ headerData.pay ? headerData.pay : '-' }}</a>
            </span>
            <span style="padding-left: 10px">
              五险一金：
              <a style="font-weight: 600">{{ headerData.insuranceFund ? headerData.insuranceFund : '-' }}</a>
            </span>
            <span v-if="canModify && $auth('project:data:agg')">
              <a-switch
                checkedChildren="编辑"
                unCheckedChildren="编辑"
                style="margin-left: 10px"
                type="primary"
                :checked="editChecked"
                @change="(ck, e) => editCheckedChange(ck)"
              />
              <a-button
                v-if="editChecked"
                style="margin-left: 10px"
                type="primary"
                size="small"
                @click="saveData"
              >
                保存
              </a-button>
            </span>
          </template>
          <vxe-table-column
            type="checkbox"
            :width="40"
            fixed="left"
          />
          <vxe-table-column
            title="工号"
            field="enumber"
            :min-width="120"
            fixed="left"
            remoteSort
          />
          <vxe-table-column
            title="姓名"
            field="ename"
            :min-width="120"
            remoteSort
            fixed="left"
          >
            <template slot-scope="{ row }">
              <a-badge
                v-if="row.usedList"
                :dot="row.usedList.length > 1"
              >
                <a-tooltip placement="top">
                  <template slot="title">
                    <div>总数：{{ row.usedList.length }}</div>
                    <div
                      v-for="(item, index) in row.usedList"
                      :key="index"
                    >
                      {{ item.rdTitle }}分配工时：{{ item.rdHour ? item.rdHour : '-' }}
                    </div>
                  </template>
                  <span>{{ row.ename }}</span>
                </a-tooltip>
              </a-badge>
              <span v-else>{{ row.ename }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="部门"
            field="deptName"
            :width="100"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            align="center"
            title="人员类型"
            field="etype"
            :width="100"
            remoteSort
          >
            <template slot-scope="{ row }">
              {{ row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : '' }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="总工时"
            :width="100"
            field="workHours"
            remoteSort
          >
            <template slot-scope="{ row }">
              {{ row.workHours ? Number(row.workHours) : '-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="应发工资"
            :width="120"
            field="pay"
            remoteSort
          >
            <template slot-scope="{ row }">
              {{ row.pay ? row.pay : '-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="五险一金"
            :width="120"
            field="insuranceFund"
            remoteSort
          >
            <template slot-scope="{ row }">
              {{ row.insuranceFund ? row.insuranceFund : '-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发工时"
            :width="110"
            field="rdHour"
            remoteSort
          >
            <template slot-scope="{ row }">
              <span v-if="!editChecked || !row.workHours">{{
                row.rdHour ? toBit(Number(row.rdHour), hourBit) : '-'
              }}</span>
              <span v-else>
                <a-input-number
                  v-model="row.rdHour"
                  style="width: 100%"
                  :min="Number(row.remainHour < 0 ? row.remainHour : 0)"
                  :max="Number(row.remainHour > 0 ? row.remainHour : 0)"
                  :precision="hourBit"
                  size="small"
                  @change="(v) => handleChange(v, row)"
                />
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发工资"
            :width="120"
            field="rdPay"
            remoteSort
          >
            <template slot-scope="{ row }">
              <span v-if="!editChecked || !row.pay">
                <a-tooltip
                  placement="top"
                  v-if="warningField(row, 'pay', 'rdAmount')"
                >
                  <template slot="title"> 差额: {{ warningField(row, 'pay', 'rdAmount') }} </template>
                  <span style="color: red; font-weight: bolder">{{ row.rdPay ? Number(row.rdPay) : '-' }}</span>
                </a-tooltip>
                <span v-else>{{ row.rdPay ? Number(row.rdPay) : '-' }}</span>
              </span>
              <span v-else>
                <a-input-number
                  v-model="row.rdPay"
                  style="width: 100%"
                  :min="loadV(row.pay * (row.remainHour / row.workHours), false)"
                  :max="loadV(row.pay * (row.remainHour / row.workHours), true)"
                  :precision="2"
                  size="small"
                  @change="(v) => rdPayChange(v, row)"
                />
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发五险一金"
            :width="130"
            field="rdInsuranceFund"
            remoteSort
          >
            <template slot-scope="{ row }">
              <a-tooltip
                placement="top"
                v-if="warningField(row, 'insuranceFund', 'rdInsuranceFund')"
              >
                <template slot="title"> 差额: {{ warningField(row, 'insuranceFund', 'rdInsuranceFund') }} </template>
                <span style="color: red; font-weight: bolder">{{
                  row.rdInsuranceFund ? row.rdInsuranceFund : '-'
                }}</span>
              </a-tooltip>
              <span v-else>{{ row.rdInsuranceFund ? row.rdInsuranceFund : '-' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发占比"
            :width="100"
            field="rdRatio"
            remoteSort
          >
            <template slot-scope="{ row }">
              <span v-if="row.rdHour && !isNaN(row.rdHour) && row.workHours && Number(row.workHours) !== 0">
                {{ toBit(row.rdHour / row.workHours, 6) }}
              </span>
              <span v-else>-</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发合计"
            :width="100"
            field="total"
          >
            <template slot-scope="{ row }">
              <span>{{ loadTotal(row) }}</span>
            </template>
          </vxe-table-column>
        </ystable>
        <set-rd-hour-modal
          rType="employee"
          :hourBit="hourBit"
          ref="setRdHourModal"
          @ok="search(true, true)"
        />
      </div>
    </a-spin>
    <AdjustAmountModal
      ref="adjustAmountModal"
      :isEmployee="true"
      @ok="search(true, true)"
    />
    <rd-ratio-modal
      @ok="search(true, true)"
      ref="ratioMoal"
      :hourBit="hourBit"
      rType="employee"
    />
    <attendance-modal
      ref="attendance"
      :projectId="projectId"
      :month="month"
      :rdTitle="selectProject.rdTitle"
    />
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="paramData"
      paramKey="name"
      title="导入人员研发工时"
      ref="uploadModal"
      action="/doc/projectRdEmployee/importRdHour"
      templateName="人员研发工时模板"
      @onSuccess="search(true,true)"
    />
  </div>
</template>

<script>
import RdFeeSubmit from './modules/RdFeeSubmit.vue'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import yearMixin from '@/utils/yearMixin'
import RdRatioModal from './ratioModal/RdRatioModal'
import AttendanceModal from './modules/AttendanceModal'
import SetRdHourModal from './modules/SetRdHourModal'
import AdjustAmountModal from './modules/AdjustAmountModal'
import UploadModal from '@/components/UploadModal/UploadModal'
function toBit (v, bit) {
  let b = 2
  if (bit || bit >= 0) {
    b = bit
  }
  let div = 1
  for (let i = 0; i < b; i++) {
    div = div * 10
  }
  // 保留双倍小数位，确保四舍五入不会丢失精度
  v = Number(v).toFixed(b * 2)
  // if (sub) {
  //   return (parseInt(v * div) / div).toFixed(b)
  // }
  return (Math.round(v * div) / div).toFixed(b)
}
const tableField = {
  tableId: 'rdEmployeeTable',
  fieldTitleObject: {
    month: { title: '月份', required: true, defaultTitle: '月份', importField: true },
    enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
    ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
    rdHour: { title: '研发工时', required: true, defaultTitle: '研发工时', importField: true }
  },
  hasMonth: true
}
export default {
  mixins: [yearMixin],
  name: 'RdEmployeeAgg',
  components: {
    ystable,
    RdRatioModal,
    AttendanceModal,
    SetRdHourModal,
    AdjustAmountModal,
    UploadModal,
    RdFeeSubmit
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    month: {
      type: String,
      default: ''
    },
    canModify: {
      type: Boolean,
      default: true
    },
    rdFeeType: {
      type: String,
      required: true
    },
    rdTypeInfo: {
      type: Object,
      required: true
    },
    selectProject: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      hourBit: 1,
      sampleData: [{
        month: '格式："年-月-日"，一般为当月第一天，例如：2019-10-01',
        enumber: 'A-01',
        ename: '张三',
        rdHour: '176'
      }],
      paramData: {},
      editChecked: false,
      spinning: false,
      selectedRowKeys: [],
      selectedRowMap: {},
      editRow: {},
      headerData: { pay: 0, insuranceFund: 0, total: 0 },
      queryParam: {
        projectId: this.projectId,
        month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      },
      tableData: [],
      salaryArr: [],
      insuranceArr: []
    }
  },
  watch: {
    projectId (newId) {
      this.queryParam.projectId = newId
      this.queryParam.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      this.queryParam.year = this.currentYear
      this.selectedRowKeys = []
      this.headerData = {}
      this.search(true, true)
    }
  },
  created () {
    this.queryParam.year = this.currentYear
    this.getSalaryConfig()
  },
  methods: {
    warningField (row, field, rdField) {
      if (row.usedList && row.usedList.length) {
        let totalHour = 0
        let totalField = 0
        row.usedList.forEach(u => {
          totalHour += u.rdHour ? Number(u.rdHour) : 0
          totalField += u[rdField] ? Number(u[rdField]) : 0
        })
        if (totalHour === Number(row.workHours) && Number(row[field]) !== totalField) {
          const sub = Math.round((totalField - Number(row[field])) * 100) / 100
          return sub !== 0 ? sub : undefined
        }
      }
    },
    getSalaryConfig () {
      this.$getSalaryConfig(this).then(res => {
        ({ salary: this.salaryArr, insurance: this.insuranceArr } = res)
      })
    },
    toBit,
    loadV (v, max) {
      v = Number(toBit(v, 2))
      if (max) {
        return v > 0 ? v : 0
      }
      return v < 0 ? v : 0
    },
    completed (data) {
      this.selectedRowKeys = []
      this.headerData.pay = 0
      this.headerData.insuranceFund = 0
      this.headerData.total = 0
      this.hourBit = 1
      if (!data) {
        this.tableData = []
        return
      }
      this.tableData = data.data
      if (data) {
        if (data.header) {
          this.headerData.pay = data.header.pay
          this.headerData.insuranceFund = data.header.insuranceFund
          if (this.headerData.pay && this.headerData.insuranceFund) {
            this.headerData.total = toBit(Number(this.headerData.pay) + Number(this.headerData.insuranceFund))
          }
        }
        if (!isNaN(data.footer)) {
          this.hourBit = data.footer
        }
      }

      this.editCheckedChange(this.editChecked, true)
    },
    editCheckedChange (ck, search) {
      this.editChecked = ck
      if (this.tableData) {
        if (ck) {
          this.tableData.forEach(record => {
            if (record.rdHour) {
              record.rdHour = toBit(Number(record.rdHour), this.hourBit)
            }
            record.cacheData = { ...record }
          })
        } else {
          if (search) {
            return
          }
          this.tableData.forEach(record => {
            Object.assign(record, record.cacheData)
            delete record.cacheData
          })
        }
      }
    },
    handleChange (v, record) {
      if (!isNaN(v) && record.workHours && Number(record.workHours) !== 0) {
        const ratio = v / record.workHours
        this.setRDRecord(record, ratio, toBit(v, this.hourBit))
      }
    },

    rdPayChange (v, record) {
      if (!isNaN(v) && record.pay && Number(record.pay) !== 0) {
        const ratio = v / record.pay
        const rdHour = toBit(record.workHours * ratio, this.hourBit)
        if (rdHour) {
          this.setRDRecord(record, ratio, toBit(record.workHours * ratio, this.hourBit))
        } else {
          this.setRDRecord(record, 0, 0)
        }
      }
    },
    setRDRecord (record, rdRatio, rdHour) {
      let rdPay
      let rdInsuranceFund
      if (this.salaryArr && this.salaryArr.length && record.payDetail) {
        rdPay = this.getRdFee(this.salaryArr, JSON.parse(record.payDetail), rdRatio)
      } else {
        rdPay = toBit(record.pay * rdRatio)
      }
      if (this.insuranceArr && this.insuranceArr.length && record.insuranceDetail) {
        rdInsuranceFund = this.getRdFee(this.insuranceArr, JSON.parse(record.insuranceDetail), rdRatio)
      } else {
        rdInsuranceFund = toBit(record.insuranceFund * rdRatio)
      }
      record.rdPay = rdPay
      record.rdHour = rdHour
      record.rdInsuranceFund = rdInsuranceFund
      this.editRow[record.enumber] = record
    },
    getRdFee (keyArr, fees, ratio) {
      let result = 0
      keyArr.forEach(item => {
        const f = Number(fees[item.name])
        if (f) {
          result += Number(toBit(f * ratio))
        }
      })
      return toBit(result)
    },
    loadTotal (row) {
      if (!row.rdPay || !row.rdInsuranceFund) {
        return '-'
      }
      return toBit(Number(row.rdPay) + Number(row.rdInsuranceFund))
    },
    onSelectChange ({ records }) {
      const keys = []
      records.forEach(r => {
        this.selectedRowMap[r.enumber] = r
        keys.push(r.enumber)
      })
      this.selectedRowKeys = keys
    },
    moment,
    search (refresh, getEmit) {
      this.$refs.table.refresh(refresh)
      this.selectedRowMap = {}
      this.selectedRowKeys = []
      if (getEmit) {
        this.$emit('change')
        this.getSummary()
      }
    },
    getSummary () {
      this.$emit('getSummary')
    },
    setHours () {
      this.editCheckedChange(false)
      this.$refs.setRdHourModal.set({ month: moment(this.month), projectId: this.projectId, list: this.getPostItem() }, this.salaryArr, this.insuranceArr)
    },
    setRatio () {
      this.editCheckedChange(false)
      this.$refs.ratioMoal.showModal({ month: moment(this.month), projectId: this.projectId, list: this.getPostItem() }, this.salaryArr, this.insuranceArr)
    },
    showAttendance () {
      this.$refs.attendance.showModal(moment(this.month), this.canModify)
    },
    getPostItem () {
      const arr = []
      this.selectedRowKeys.forEach(k => {
        if (this.selectedRowMap[k]) {
          arr.push(this.selectedRowMap[k])
        }
      })
      return arr
    },
    saveData () {
      this.spinning = true
      const arr = []
      for (const k in this.editRow) {
        arr.push(this.editRow[k])
      }
      if (arr.length === 0) {
        this.spinning = false
        return
      }
      const values = {}
      values.list = arr
      values.projectId = this.projectId
      values.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      this.$http.post('/projectRdEmployee/saveList', values).then(res => {
        if (res.success) {
          this.spinning = false
          this.$message.success('保存成功')
          this.editRow = {}
          this.search(true, true)
        } else {
          this.spinning = false
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
        }
      })
    },
    adjustAmount () {
      const params = {
        projectId: this.projectId,
        month: moment(this.month),
        pRdIds: this.getPostItem().filter(a => a.id).map(a => a.id),
        rdTypes: this.rdTypeInfo.types
      }
      if (!params.pRdIds || params.pRdIds.length <= 0) {
        this.$message.info('所选人员未进行归集，不能调整费用。')
      } else {
        this.$refs.adjustAmountModal.show(params)
      }
    },
    importRdHour () {
      this.paramData = { projectId: this.projectId, year: this.currentYear }
      this.$refs.uploadModal.show(tableField)
    },
    refreshAgg () {
      const self = this
      this.$confirm({
        title: `您确定要刷新所有RD[${self.month}]的人员归集费用吗?`,
        onOk () {
          return self.$http.post('/projectRdEmployee/refreshFee', { month: self.queryParam.month })
            .then(res => {
              if (res.success) {
                self.$message.success('刷新成功')
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '刷新失败')
              }
              self.search(false)
            })
        },
        onCancel () {
        }
      })
    }
  }
}
</script>

<style>
.customCard {
  padding: 0 12px;
}
.vxe-table .vxe-body--row.row-red {
  background-color: red;
  color: #000;
}
</style>
