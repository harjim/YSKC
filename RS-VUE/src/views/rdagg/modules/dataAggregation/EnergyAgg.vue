<template>
  <div class="customCard">
    <a-spin
      :spinning="spin"
      tip="请稍后..."
    >
      <a-form layout="inline">
        <a-form-item label="能源名称">
          <a-input
            v-model="queryParam.ename"
            style="width:165px"
            placeholder="请输入能源名称"
          />
        </a-form-item>
        <a-form-item label="部门">
          <a-input
            v-model="queryParam.deptName"
            style="width:165px"
            placeholder="请输入部门"
          />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div style="height: calc(82% - 50px); min-height: 213px; width: 100%;">
        <ystable
          rowId="id"
          ref="table"
          queryUrl="/projectEnergy/queryProjectEnergy"
          :params="queryParam"
          @checkbox-change="onSelectChange"
          @checkbox-all="onSelectChange"
          :toolbar="{zoom:true,custom:true,refresh:true}"
          @completed="({data})=>completed(data)"
          show-overflow="title"
          max-height="100%"
          highlight-hover-row
          highlight-current-row
          resizable
          auto-resize
          :pagerConfig="{ pageSize: 50, pageSizes: [50, 100, 200, 500] }"
        >
          <template v-slot:buttons>
            <template v-if="canModify && $auth('project:data:agg')">
              <span>
                <a-button
                  type="primary"
                  @click="$refs.bindingEnergyModal.add(projectId,queryParam.month,queryParam.type,rdType)"
                >添加</a-button>
              </span>
              <span style="padding-left:10px;">
                <a-button
                  type="primary"
                  :disabled="selectRows.length <= 0"
                  @click="delList"
                >删除</a-button>
              </span>
              <template v-if="rdType !== 20201 && rdType !== 20102">
                <span style="padding-left:10px;">
                  <a-button
                    type="primary"
                    @click="syncDepreciation"
                  >同步折旧工时</a-button>
                </span>
                <a-button
                  style="margin-left:10px;"
                  type="primary"
                  :disabled="selectRows.length <= 0"
                  @click="setRdHour"
                >设置研发工时</a-button>
              </template>
              <span>
                <a-button
                  style="margin-left: 10px;"
                  type="primary"
                  :disabled="selectRows.length <= 0"
                  @click="setAdjustAmount"
                >调整费用</a-button>
              </span>
              <rd-fee-submit :projectId="projectId" :month="projectMonth" :rdType="rdFeeType" style="margin-left: 10px;" @getSummary="getSummary"/>
            </template>
            <span :style="{paddingLeft:canModify ? '10px': 0 }">
              总计：
              <a style="font-weight: 600">{{ totalAmount ? totalAmount.toFixed(2) : '-' }}</a>
            </span>
            <span v-if="canModify && $auth('project:data:agg')">
              <a-switch
                checkedChildren="编辑"
                unCheckedChildren="编辑"
                style="margin-left:10px;"
                type="primary"
                :checked="openEditMode"
                @change="onOpenEditMode"
              />
              <a-checkbox
                style="margin-left: 10px;font-size:13px;"
                :checked="synEditMode"
                v-show="openEditMode"
                @change="onSynEditChange"
              >同步</a-checkbox>
              <a-button
                style="margin-left: 10px;"
                type="primary"
                size="small"
                v-if="openEditMode"
                @click="onSaveList"
              >保存</a-button>
            </span>
          </template>
          <vxe-table-column
            type="checkbox"
            :width="40"
            fixed="left"
          />
          <vxe-table-column
            title="能源名称"
            field="ename"
            align="left"
            :min-width="220"
            remoteSort
            show-header-overflow
            fixed="left"
          >
            <template v-slot="{row}">
              <a-badge
                v-if="row.usedList"
                :dot="row.usedList.length > 0 "
              >
                <a-tooltip placement="top">
                  <template slot="title">
                    <div
                      v-for="(item, index) in row.usedList"
                      :key="index"
                    >{{ item.rdTitle }}{{ typeMap[item.etype] }}：{{ item.rdAmount }}</div>
                  </template>
                  <span>{{ row.ename }}</span>
                </a-tooltip>
              </a-badge>
              <span v-else>{{ row.ename }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="凭证号"
            field="accNumber"
            align="left"
            :width="160"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="部门"
            field="deptName"
            align="left"
            :width="160"
            remoteSort
            show-header-overflow
          ></vxe-table-column>
          <vxe-table-column
            title="发生日期"
            field="occDate"
            align="center"
            :width="120"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="单价"
            field="unitPrice"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="数量"
            field="quantity"
            align="right"
            :width="100"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="单位"
            field="unit"
            align="right"
            :width="90"
            show-header-overflow
          />
          <vxe-table-column
            title="金额"
            field="totalAmount"
            align="right"
            :width="120"
            remoteSort
            show-header-overflow
          ></vxe-table-column>
          <vxe-table-column
            title="分配金额"
            field="amount"
            align="right"
            :width="120"
            remoteSort
            show-header-overflow
          ></vxe-table-column>
          <!-- 钢铁动力燃料，不按工时计算 -->
          <template v-if="rdType !== 20201 && rdType !== 20102">
            <vxe-table-column
              title="总工时"
              field="totalHour"
              align="right"
              :width="100"
              remoteSort
              show-header-overflow
            >
              <template slot-scope="{row}">
                <span v-if="openEditMode">
                  <a-input-number
                    :min="0"
                    :precision="2"
                    :max="$store.state.totalMax"
                    style="width:100%"
                    size="small"
                    v-model="row.totalHour"
                    @change="v=>handleChange(v,row,'totalHour','hour')"
                  />
                </span>
                <span v-else>{{ row.totalHour }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="研发工时"
              field="rdHour"
              align="right"
              :width="100"
              remoteSort
              show-header-overflow
            >
              <template slot-scope="{row}">
                <span v-if="openEditMode">
                  <a-input-number
                    :min="0"
                    :precision="2"
                    :max="Number(row.totalHour)"
                    style="width:100%"
                    size="small"
                    v-model="row.rdHour"
                    @change="v=>handleChange(v,row,'rdHour','hour')"
                  />
                </span>
                <span v-else>{{ row.rdHour }}</span>
              </template>
            </vxe-table-column>
          </template>
          <template v-else>
            <vxe-table-column
              title="总产量"
              field="totalYield"
              align="right"
              :width="100"
              remoteSort
              show-header-overflow
            >
              <template slot-scope="{row}">
                <span v-if="openEditMode">
                  <a-input-number
                    :min="0"
                    :precision="6"
                    :max="$store.state.totalMax"
                    style="width:100%"
                    size="small"
                    v-model="row.totalYield"
                    @change="v=>handleChange(v,row,'totalYield','yield')"
                  />
                </span>
                <span v-else>{{ row.totalYield }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="试制量"
              field="rdYield"
              align="right"
              :width="100"
              remoteSort
              show-header-overflow
            >
              <template slot-scope="{row}">
                <span v-if="openEditMode">
                  <a-input-number
                    :min="0"
                    :precision="6"
                    :max="Number(row.totalYield)"
                    style="width:100%"
                    size="small"
                    v-model="row.rdYield"
                    @change="v=>handleChange(v,row,'rdYield','yield')"
                  />
                </span>
                <span v-else>{{ row.rdYield }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="比例"
              field="yieldRatio"
              align="right"
              :width="100"
              remoteSort
              show-header-overflow
            >
              <template v-slot="{row}">
                <span v-if="Number(row.rdYield) && Number(row.totalYield)">{{ (row.rdYield / row.totalYield).toFixed(6) }}</span>
                <span v-else>-</span>
              </template>
            </vxe-table-column>
          </template>
          <vxe-table-column
            title="研发数量"
            field="rdQuantity"
            align="right"
            :width="100"
            remoteSort
            show-header-overflow
          >
            <template v-slot="{row}">
              <span v-if="Number(row.rdAmount)&& Number(row.unitPrice)">{{ (row.rdAmount / row.unitPrice).toFixed(2) }}</span>
              <span v-else>-</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="研发费用"
            field="rdAmount"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{row}">
              <span>{{ row.rdAmount }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="研发占比"
            field="usedRatio"
            align="right"
            :width="90"
          >
            <template slot-scope="{row}">
              <span v-if="Number(row.rdAmount) && Number(row.totalAmount)">{{ (row.rdAmount / row.totalAmount).toFixed(2) }}</span>
              <span v-else>-</span>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <bindingEnergy-modal
        ref="bindingEnergyModal"
        @ok="search(true)"
      />
      <set-energy-rd-hour-modal
        rType="energy"
        ref="setRdHour"
        @ok="search(true)"
      />
      <adjustAmount-modal
        ref="adjustAmountModal"
        @ok="search(true)"
      />
    </a-spin>
  </div>
</template>
<script>
import RdFeeSubmit from './modules/RdFeeSubmit.vue'
import bindingEnergyModal from './bindingDataModules/BindingEnergyModal'
import SetEnergyRdHourModal from './modules/SetEnergyRdHourModal'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import AdjustAmountModal from './modules/AdjustAmountModal'
export default {
  name: 'EnergyAgg',
  components: {
    ystable,
    bindingEnergyModal,
    SetEnergyRdHourModal,
    AdjustAmountModal,
    RdFeeSubmit
  },
  props: {
    selectProject: {
      type: Object,
      default: () => { return {} }
    },
    projectId: {
      type: Number,
      required: true
    },
    projectMonth: {
      type: String,
      default: ''
    },
    rdType: {
      type: Number,
      default: 20100
    },
    rdFeeType: {
      type: String,
      required: true
    },
    rdTypeInfo: {
      type: Object,
      required: true
    },
    canModify: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      typeMap: {
        20302: '试制动力',
        20100: '动力损耗',
        20200: '燃料损耗',
        20102: '钢铁动力',
        20201: '钢铁燃料'
      },
      openEditMode: false,
      synEditMode: false,
      selectKeys: [],
      selectRows: [],
      totalAmount: 0,
      paramData: {},
      queryParam: {
        month: this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00'),
        type: this.rdTypeInfo.type,
        etype: this.rdType,
        projectId: this.projectId
      },
      tableData: [],
      spin: false,
      form: this.$form.createForm(this)
    }
  },
  watch: {
    projectId (newId) {
      this.queryParam.month = this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00')
      this.queryParam.ename = undefined
      this.queryParam.deptName = undefined
      this.queryParam.type = this.rdTypeInfo.type
      this.queryParam.etype = this.rdType
      this.queryParam.projectId = newId
      this.search(true)
    }
  },
  methods: {
    setRdHour () {
      const params = { etype: this.queryParam.etype, type: this.queryParam.type, month: this.queryParam.month, projectId: this.queryParam.projectId }
      params.modelList = this.selectKeys.map(a => { return { id: a } })
      this.$refs.setRdHour.set(params)
    },
    syncDepreciation () {
      this.spin = true
      this.$http.post('/projectEnergy/syncDepreciation',
        { etype: this.queryParam.etype, type: this.queryParam.type, month: this.queryParam.month, projectId: this.queryParam.projectId })
        .then(res => {
          if (res.success && res.data) {
            this.search(true)
            this.$message.success('同步成功')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '同步失败')
          }
          this.spin = false
        })
    },
    completed (data) {
      this.selectKeys = []
      this.selectRows = []
      if (!data) {
        this.stimulus = 0
        this.fuel = 0
        return
      }
      this.getTotal(data.header)
      this.tableData = data.data
      this.onOpenEditMode(this.openEditMode)
    },
    onSaveList () {
      this.spin = true
      const values = []
      for (var i = 0; i < this.tableData.length; i++) {
        var record = this.tableData[i]
        if (Number(record.rdAmount) !== Number(record.cacheData.rdAmount) ||
          record.totalHour !== record.cacheData.totalHour ||
          record.rdHour !== record.cacheData.rdHour ||
          record.totalYield !== record.cacheData.totalYield ||
          record.rdYield !== record.cacheData.rdYield) {
          if (record.rdYield > record.totalYield) {
            record.rdYield = record.totalYield
          }
          if (record.rdHour > record.totalHour) {
            record.rdHour = record.totalHour
          }
          values.push(record)
        }
      }
      if (values.length <= 0) {
        this.$message.info('未进行任何操作')
        this.spin = false
        return
      }
      const result = {}
      result.projectId = this.projectId
      result.month = moment(this.projectMonth)
      result.modelList = values.map(a => {
        delete a.cacheData
        return a
      })
      result.etype = this.rdType
      result.type = this.rdTypeInfo.type
      this.$http.post('/projectEnergy/updateList', result)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
            this.search(false)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
          this.spin = false
        })
    },
    onOpenEditMode (ck) {
      this.openEditMode = ck
      for (var i = 0; i < this.tableData.length; i++) {
        var record = this.tableData[i]
        if (ck) {
          record.cacheData = { ...record }
          record.remainAmount = (Number(record.remainAmount) + Number(record.rdAmount)).toFixed(2)
        } else {
          const cacheRecord = record.cacheData
          Object.assign(record, cacheRecord)
          delete record.cacheRecord
        }
      }
      if (!ck) {
        this.synEditMode = false
      }
    },
    onSynEditChange (e) {
      this.synEditMode = e.target.checked
    },
    handleChange (v, record, key, accountKey) {
      var row = {}
      if (this.synEditMode && this.selectKeys.length > 0) {
        for (var i = 0; i < this.tableData.length; i++) {
          row = this.tableData[i]
          if (this.selectKeys.indexOf(row.id) >= 0) {
            this.saveRecord(row, v, key, accountKey)
          }
        }
      } else {
        this.saveRecord(record, v, key, accountKey)
      }
    },
    saveRecord (record, v, key, accountKey) {
      record[key] = v
      var amount, totalKey, rdKey
      if (accountKey === 'hour') {
        totalKey = 'totalHour'
        rdKey = 'rdHour'
      } else {
        totalKey = 'totalYield'
        rdKey = 'rdYield'
      }
      if (!Number(record[totalKey]) || !Number(record[rdKey])) {
        amount = '0.00'
      } else {
        amount = Math.round((record[rdKey] / record[totalKey] * record.amount) * 100) / 100
      }
      if ((amount > record.remainAmount && record.remainAmount > 0) || (amount < record.remainAmount && record.remainAmount < 0)) {
        amount = record.remainAmount
      }
      record.rdAmount = amount
    },
    onSelectChange ({ records }) {
      this.selectKeys = records.map(item => item.id)
      this.selectRows = records
    },
    getTotal (header) {
      if (header) {
        this.totalAmount = header
      } else {
        this.totalAmount = 0
      }
    },
    moment,
    delList () {
      const self = this
      this.$confirm({
        title: '您确定要删除选中的能源吗?',
        onOk () {
          const value = {}
          value.ids = self.selectKeys
          value.month = self.queryParam.month
          value.projectId = self.projectId
          value.etype = self.rdType
          value.type = self.rdTypeInfo.type
          return self.$http.post('/projectEnergy/delEnergys', value)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
              } else {
                self.$message.error(res.errorMessage || '删除失败')
              }
              self.search(false)
            })
        },
        onCancel () {
        }
      })
    },
    setAdjustAmount () {
      const params = {
        projectId: this.projectId,
        month: this.queryParam.month,
        pRdIds: this.selectKeys,
        rdTypes: this.rdTypeInfo.types,
        type: this.rdTypeInfo.type
      }
      this.$refs.adjustAmountModal.show(params)
    },
    search (refresh) {
      this.selectRows = []
      this.selectKeys = []
      this.openEditMode = false
      this.synEditMode = false
      this.getSummary()
      this.$refs.table.refresh(refresh)
    },
    getSummary () {
      this.$emit('getSummary')
    }
  }
}
</script>

<style  lang="less" scoped >
.customCard {
  padding: 0 12px;
}
</style>

<style lang="less">
.ant-drawer-header {
  height: 50px;
}
.ant-drawer-body {
  height: calc(100% - 50px);
  overflow: hidden;
}
.ant-spin-container .ant-spin-nested-loading, .customCard, .ant-tabs-content, .ant-tabs, .ant-layout, .ant-spin-container, .ant-spin-nested-loading {
  height: 100%;
}
</style>

<style lang="less" scoped>
 div {
  height: 100%;
 }
</style>
