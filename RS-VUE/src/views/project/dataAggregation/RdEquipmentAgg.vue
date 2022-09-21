<template>
  <div class="customCard">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <a-form layout="inline">
        <a-form-item label="资产代码">
          <a-input
            v-model="queryParam.ecode"
            placeholder="请输入资产代码"
            style="width: 165px"
          />
        </a-form-item>
        <a-form-item label="设备名称">
          <a-input
            v-model="queryParam.ename"
            placeholder="请输入设备名称"
            style="width: 165px"
          />
        </a-form-item>
        <a-form-item
          label="设备类型"
          v-if="rdTypeInfo.types.length > 1"
        >
          <a-select
            v-model="queryParam.etype"
            style="width: 165px"
          >
            <a-select-option value="-1">请选择设备类型</a-select-option>
            <a-select-option v-for="item in $getEnums('equipmentEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
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
          rowId="ecode"
          queryUrl="/projectRdEquipment/getList"
          :params="queryParam"
          highlight-hover-row
          highlight-current-row
          resizable
          auto-resize
          @checkbox-change="onSelectChange"
          @checkbox-all="onSelectChange"
          :toolbar="{ zoom: true, custom: true, refresh: true }"
          show-overflow="title"
          @completed="({ data }) => completed(data)"
          :pagerConfig="{ pageSize: 50, pageSizes: [50, 100, 200, 500] }"
        >
          <template slot="buttons">
            <template v-if="canModify && $auth('project:data:agg')">
              <a-button
                style="margin-right: 10px"
                type="primary"
                :disabled="!this.selectedRowKeys || this.selectedRowKeys.length <= 0"
                @click="setRatio"
              >
                设置研发比例</a-button>
              <a-button
                style="margin-right: 10px"
                type="primary"
                :disabled="!this.selectedRowKeys || this.selectedRowKeys.length <= 0"
                @click="setHours"
              >设置研发工时</a-button>
              <a-button
                style="margin-right: 10px"
                type="primary"
                @click="importRdHour"
              >导入研发工时</a-button>
              <rd-fee-submit style="margin-right:10px;" :projectId="projectId" :month="month" :rdType="rdFeeType" @getSummary="getSummary"/>
            </template>
            <a-button
              style="margin-right: 10px"
              type="primary"
              @click="$refs.equipmentModal.showModal(moment(month), canModify)"
            >研发工时表</a-button>
            <template v-if="canModify && $auth('project:data:agg')">
              <a-button
                type="primary"
                :disabled="!this.selectedRowKeys || this.selectedRowKeys.length <= 0"
                @click="adjustAmount"
              >调整费用</a-button>
            </template>
            <span style="padding-left: 10px">
              总计：
              <a style="font-weight: 600">{{ headerData.total || '-' }}</a>
            </span>
            <template v-if="rdTypeInfo.types.length === 2">
              <span>
                设备：
                <a style="font-weight: 600">{{ headerData.prod || '-' }}</a>
              </span>
              <span style="padding-left: 10px">
                仪器：
                <a style="font-weight: 600">{{ headerData.lab || '-' }}</a>
              </span>
            </template>
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
                style="margin-left: 10px"
                type="primary"
                size="small"
                v-if="editChecked"
                @click="saveData"
              >保存</a-button>
            </span>
          </template>
          <vxe-table-column
            type="checkbox"
            :width="40"
            fixed="left"
          />
          <vxe-table-column
            align="left"
            title="资产代码"
            field="ecode"
            :min-width="120"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            align="left"
            title="设备名称"
            field="ename"
            :min-width="120"
            remoteSort
            show-header-overflow
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
            align="left"
            title="设备类型"
            field="etype"
            :width="80"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{ row }">
              <span>{{ row.etype ? $getEnums('equipmentEnum').find(elem => elem.value === row.etype).label : '' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="left"
            title="部门"
            field="deptName"
            :width="150"
            remoteSort
            show-header-overflow
          ></vxe-table-column>
          <vxe-table-column
            align="right"
            title="运行工时"
            :width="100"
            field="workHours"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{ row }">
              <span>{{ row.workHours ? Number(row.workHours).toFixed(hourBit) : '-' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="运行折旧"
            :width="110"
            field="depreciation"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{ row }">
              <span>{{ row.depreciation ? row.depreciation : '-' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发工时"
            :width="120"
            field="rdHour"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{ row }">
              <span v-if="!editChecked || !row.workHours">{{
                row.rdHour ? toBit(Number(row.rdHour), hourBit) : '-'
              }}</span>
              <span v-else>
                <a-input-number
                  v-model="row.rdHour"
                  style="width: 100%"
                  :min="row.remainHour > 0 ? 0 : Number(row.remainHour)"
                  :max="row.remainHour > 0 ? Number(row.remainHour) : 0"
                  :precision="hourBit"
                  size="small"
                  @change="(v) => handleChange(v, row)"
                />
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发折旧"
            :width="120"
            field="rdDepreciation"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{ row }">
              <span v-if="!editChecked || !row.depreciation">
                <a-tooltip
                  placement="top"
                  v-if="warningField(row, 'depreciation', 'rdAmount')"
                >
                  <template slot="title"> 差额: {{ warningField(row, 'depreciation', 'rdAmount') }} </template>
                  <span style="color: red; font-weight: bolder">{{
                    row.rdDepreciation ? Number(row.rdDepreciation) : '-'
                  }}</span>
                </a-tooltip>
                <span v-else>{{ row.rdDepreciation ? Number(row.rdDepreciation) : '-' }}</span>
              </span>
              <span v-else>
                <a-input-number
                  v-model="row.rdDepreciation"
                  style="width: 100%"
                  :min="loadV(row.depreciation * (row.remainHour / row.workHours), false)"
                  :max="loadV(row.depreciation * (row.remainHour / row.workHours), true)"
                  :precision="2"
                  size="small"
                  @change="(v) => rdDepreciationChange(v, row)"
                />
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发占比"
            :width="100"
            field="rdRatio"
            show-header-overflow
          >
            <template slot-scope="{ row }">
              <span v-if="row.rdHour && !isNaN(row.rdHour) && row.workHours && Number(row.workHours) !== 0">
                {{ toBit(row.rdHour / row.workHours, 6) }}
              </span>
              <span v-else>-</span>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <adjustAmount-modal
        ref="adjustAmountModal"
        @ok="search(true, true)"
      />
      <set-rd-hour-modal
        rType="equipment"
        ref="setRdHourModal"
        :hourBit="hourBit"
        @ok="search(true, true)"
      />
      <rd-ratio-modal
        @ok="search(true, true)"
        ref="ratioMoal"
        :hourBit="hourBit"
        rType="equipment"
      />
      <equipment-modal
        :projectId="projectId"
        :month="month"
        :rdTitle="selectProject.rdTitle"
        :types="rdTypeInfo.types"
        ref="equipmentModal"
      />
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        :paramData="paramData"
        paramKey="name"
        title="导入设备研发工时"
        ref="uploadModal"
        action="/doc/projectRdEquipment/importRdHour"
        templateName="设备研发工时模板"
        @onSuccess="search(true,true)"
      />
    </a-spin>
  </div>
</template>

<script>
import RdFeeSubmit from './modules/RdFeeSubmit.vue'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import yearMixin from '@/utils/yearMixin'
import RdRatioModal from './ratioModal/RdRatioModal'
import SetRdHourModal from './modules/SetRdHourModal'
import EquipmentModal from './modules/EquipmentModal'
import AdjustAmountModal from './modules/AdjustAmountModal'
import UploadModal from '@/components/UploadModal/UploadModal'
import { mapState } from 'vuex'
function toBit (v, bit) {
  var b = 2
  if (bit || bit >= 0) {
    b = bit
  }
  var div = 1
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
  tableId: 'rdEquipmentTable',
  fieldTitleObject: {
    month: { title: '月份', required: true, defaultTitle: '月份', importField: true },
    ecode: { title: '资产代码', required: true, defaultTitle: '资产代码', importField: true },
    ename: { title: '设备名称', required: true, defaultTitle: '设备名称', importField: true },
    rdHour: { title: '研发工时', required: true, defaultTitle: '研发工时', importField: true }
  },
  hasMonth: true
}
export default {
  mixins: [yearMixin],
  name: 'RdEquipmentAgg',
  components: {
    ystable,
    SetRdHourModal,
    RdRatioModal,
    EquipmentModal,
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
      sampleData: [{
        month: '格式："年-月-日"，一般为当月第一天，例如：2019-10-01',
        ecode: 'ZC-JQ-00081',
        ename: '上煤皮带机',
        rdHour: '176'
      }],
      paramData: {},
      editChecked: false,
      spinning: false,
      selectedRowKeys: [],
      selectedRowMap: {},
      editRow: {},
      headerData: {},
      hourBit: 1,
      queryParam: {
        projectId: this.projectId,
        month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'),
        etype: this.rdTypeInfo.types.length > 1 ? undefined : this.rdTypeInfo.types[0]
      },
      tableData: []
    }
  },
  computed: {
    ...mapState({
    })
  },
  watch: {
    projectId (newId) {
      this.queryParam.projectId = newId
      this.queryParam.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      this.queryParam.year = this.currentYear
      this.queryParam.etype = this.rdTypeInfo.types.length > 1 ? undefined : this.rdTypeInfo.types[0]
      this.selectedRowKeys = []
      this.headerData = {}
      this.search(true, true)
    }
  },
  mounted () {
    this.queryParam.year = this.currentYear
  },
  methods: {
    toBit,
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
    loadV (v, max) {
      v = Number(toBit(v, 2))
      if (max) {
        return v > 0 ? v : 0
      }
      return v < 0 ? v : 0
    },
    completed (data) {
      this.hourBit = 1
      this.headerData = {}
      if (!data) {
        this.tableData = []
        return
      }
      this.tableData = data.data
      if (data.header) {
        if (this.rdTypeInfo.types.length > 1) {
          this.headerData.lab = data.header.lab
          this.headerData.prod = data.header.prod
          if (this.headerData.lab && this.headerData.prod) {
            this.headerData.total = toBit(Number(this.headerData.lab) + Number(this.headerData.prod))
          }
        } else {
          this.headerData.total = data.header.assets
        }
      }
      if (!isNaN(data.footer)) {
        this.hourBit = data.footer
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
        this.setRDRecord(record, toBit(v, this.hourBit), toBit(ratio * record.depreciation))
      }
    },
    rdDepreciationChange (v, record) {
      if (!isNaN(v) && record.depreciation && Number(record.depreciation) !== 0) {
        const ratio = v / record.depreciation
        const rdHour = toBit(ratio * record.workHours, this.hourBit)
        if (rdHour) {
          this.setRDRecord(record, rdHour, v)
        } else {
          this.setRDRecord(record, 0, 0)
        }
      }
    },
    setRDRecord (record, rdHour, rdDepreciation) {
      record.rdHour = rdHour
      record.rdDepreciation = rdDepreciation
      this.editRow[record.ecode] = record
    },
    setHours () {
      this.editCheckedChange(false)
      this.$refs.setRdHourModal.set({ month: moment(this.month), projectId: this.projectId, list: this.getPostItem() })
    },
    onSelectChange ({ records }) {
      const keys = []
      records.forEach(r => {
        this.selectedRowMap[r.ecode] = r
        keys.push(r.ecode)
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
    setRatio () {
      this.editCheckedChange(false)
      this.$refs.ratioMoal.showModal({ month: moment(this.month), projectId: this.projectId, list: this.getPostItem() })
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
      this.$http.post('/projectRdEquipment/saveList', values)
        .then(res => {
          if (res.success) {
            this.spinning = false
            this.$message.success('保存成功')
            this.editRow = {}
            this.search(true, true)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
            this.spinning = false
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
        this.$message.info('所选设备未进行归集，不能调整费用。')
      } else {
        this.$refs.adjustAmountModal.show(params)
      }
    },
    importRdHour () {
      this.paramData = { projectId: this.projectId, year: this.currentYear }
      this.$refs.uploadModal.show(tableField)
    }
  }
}
</script>

<style  lang="css" >
.word-wrap {
  word-break: break-all;
}
.customCard {
  padding: 0 12px;
}
</style>
