<template>
  <div class="customCard">
    <a-form layout="inline">
      <a-form-item label="物料编码">
        <a-input
          v-model="mcode"
          placeholder="请输入物料编码"
        />
      </a-form-item>
      <a-form-item label="物料名称">
        <a-input
          v-model="mname"
          placeholder="请输入物料名称"
        />
      </a-form-item>
      <a-form-item label="部门">
        <a-input
          v-model="deptName"
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
        size="default"
        max-height="100%"
        highlight-hover-row
        highlight-current-row
        show-overflow="title"
        resizable
        auto-resize
        :params="{ selectDate: this.monthDate, mname: this.mname, mcode: this.mcode, projectId: this.projectId, rdType: this.rdTypeInfo.types[0], deptName: this.deptName }"
        queryUrl="/projectMaterial/queryProjectMaterial"
        :toolbar="{zoom: true, custom: true, refresh: true}"
        @completed="({data})=>completed(data)"
        @checkbox-change="onSelectChange"
        @checkbox-all="onSelectChange"
        :pagerConfig="{ pageSize: 50, pageSizes: [50, 100, 200, 500] }"
      >
        <template v-slot:buttons>
          <template v-if="canModify && $auth('project:data:agg')">
            <span style="padding-left:10px;">
              <a-button
                type="primary"
                @click="$refs.bindingMaterialModal.add(projectId,monthDate,rdTypeInfo)"
              >添加</a-button>
            </span>
            <span style="padding-left:10px;">
              <a-button
                type="primary"
                :disabled="materialList.length <= 0"
                @click="delSelect"
              >删除</a-button>
            </span>
            <template v-if="rdTypeInfo.types[0] === 20002 || rdTypeInfo.types[0] === 20304">
              <span>
                <a-button
                  style="margin-left: 10px;"
                  type="primary"
                  :disabled="materialList.length <= 0"
                  @click="setDepreciationRatio"
                >设置损耗率</a-button>
              </span>
            </template>
            <span>
              <a-button
                style="margin-left: 10px;"
                type="primary"
                :disabled="materialList.length <= 0"
                @click="setAdjustAmount"
              >调整费用</a-button>
            </span>
            <rd-fee-submit style="margin-left:10px;" :projectId="projectId" :month="month" :rdType="rdFeeType" @getSummary="getSummary"/>
          </template>
          <span :style="{paddingLeft: canModify ? '10px' : '0'}">
            总计：
            <a style="font-weight: 600">{{ countTotal ? countTotal.toFixed(2): '-' }}</a>
          </span>
          <span
            style="padding-left: 10px;"
            v-if="canModify && $auth('project:data:agg')"
          >
            <a-switch
              checkedChildren="编辑"
              unCheckedChildren="编辑"
              @change="onOpenEditMode"
              v-model="openEditMode"
            />
            <a-button
              style="margin-left: 10px;"
              type="primary"
              v-if="openEditMode && (rdTypeInfo.types[0] === 20001 || rdTypeInfo.types[0] === 20303)"
              :disabled="materialList.length <= 0"
              @click="$refs.setPaper.set(materialList)"
            >设置比例/废品单价</a-button>
            <a-button
              style="margin-left: 10px;"
              type="primary"
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
          title="物料名称"
          field="mname"
          align="left"
          :width="180"
          remoteSort
          show-header-overflow
          show-overflow="title"
          headerAlign="center"
          fixed="left"
        />
        <vxe-table-column
          title="物料编码"
          field="mcode"
          align="left"
          headerAlign="center"
          :min-width="160"
          remoteSort
          show-header-overflow
          show-overflow="title"
        />
        <vxe-table-column
          title="领用日期"
          field="acqDate"
          align="center"
          headerAlign="center"
          :width="130"
          remoteSort
          show-header-overflow
          show-overflow="title"
        />
        <vxe-table-column
          title="单价"
          field="unitPrice"
          align="right"
          headerAlign="center"
          :width="100"
          remoteSort
          show-header-overflow
          show-overflow="title"
        />
        <vxe-table-column
          title="数量"
          field="quantity"
          align="right"
          headerAlign="center"
          :width="100"
          remoteSort
          show-header-overflow
          show-overflow="title"
        />
        <vxe-table-column
          title="单位"
          field="unit"
          align="left"
          :width="60"
        />
        <vxe-table-column
          title="金额"
          field="totalAmount"
          align="right"
          headerAlign="center"
          :width="100"
          show-header-overflow
          show-overflow="title"
        >
          <template slot-scope="{row}">{{ row.totalAmount.toFixed(6) }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="研发数量"
          field="used"
          align="right"
          headerAlign="center"
          :width="120"
          remoteSort
          show-header-overflow
          show-overflow="title"
        >
          <template slot-scope="{row}">
            <a-input-number
              v-if="openEditMode && !(rdTypeInfo.types[0] === 20002 || rdTypeInfo.types[0] === 20304)"
              :value="row.used"
              :precision="6"
              :min="row.maxQuantity > 0 ? 0 : Number(row.maxQuantity)"
              :max="row.maxQuantity > 0 ? Number(row.maxQuantity) : 0"
              :key="`used${row.key}`"
              @change="(val)=>onCellChange(Number(val),row,'used')"
            />
            <span v-else>{{ row.used }}</span>
          </template>
        </vxe-table-column>
        <template v-if="rdTypeInfo.types[0] === 20001 || rdTypeInfo.types[0] === 20303">
          <vxe-table-column
            title="成品单价"
            field="finishUnitPrice"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <a-input-number
                v-if="openEditMode"
                :value="row.finishUnitPrice"
                :precision="6"
                :key="`finishUnitPrice${row.key}`"
                @change="(val)=>onCellChange(Number(val),row,'finishUnitPrice','finish')"
              />
              <span v-else>{{ row.finishUnitPrice }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="成品数量"
            field="finishQuantity"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <a-input-number
                v-if="openEditMode"
                :value="row.finishQuantity"
                :min="row.maxQuantity > 0 ? 0 : Number(row.maxQuantity)"
                :max="row.maxQuantity > 0 ? Number(row.maxQuantity) : 0"
                :precision="6"
                :key="`finishQuantity${row.key}`"
                @change="(val)=>onCellChange(Number(val),row,'finishQuantity','finish')"
              />
              <span v-else>{{ row.finishQuantity }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="成品金额"
            field="finishAmount"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <a-input-number
                v-if="openEditMode"
                :value="row.finishAmount"
                :precision="6"
                :key="`finishAmount${row.key}`"
                @change="(val)=>onCellChange(Number(val),row,'finishAmount')"
              />
              <span v-else>{{ row.finishAmount }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="废品单价"
            field="wasteUnitPrice"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <a-input-number
                v-if="openEditMode"
                :value="row.wasteUnitPrice"
                :precision="6"
                :key="`wasteUnitPrice${row.key}`"
                @change="(val)=>onCellChange(Number(val),row,'wasteUnitPrice','waste')"
              />
              <span v-else>{{ row.wasteUnitPrice }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="废品数量"
            field="wasteQuantity"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <a-input-number
                v-if="openEditMode"
                :value="row.wasteQuantity"
                :min="row.maxQuantity > 0 ? 0 : Number(row.maxQuantity)"
                :max="row.maxQuantity > 0 ? Number(row.maxQuantity) : 0"
                :precision="6"
                :key="`wasteQuantity${row.key}`"
                @change="(val)=>onCellChange(Number(val),row,'wasteQuantity','waste')"
              />
              <span v-else>{{ row.wasteQuantity }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="废品金额"
            field="wasteAmount"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <a-input-number
                v-if="openEditMode"
                :value="row.wasteAmount"
                :precision="6"
                :key="`wasteAmount${row.key}`"
                @change="(val)=>onCellChange(Number(val),row,'wasteAmount')"
              />
              <span v-else>{{ row.wasteAmount }}</span>
            </template>
          </vxe-table-column>
        </template>
        <template v-if="rdTypeInfo.types[0] === 20002 || rdTypeInfo.types[0] === 20304">
          <vxe-table-column
            title="总产量"
            field="totalYield"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <a-input-number
                v-if="openEditMode"
                :max="$store.state.totalMax"
                :value="row.totalYield"
                :precision="6"
                @change="(val)=>onYieldChange(Number(val),row,'totalYield','yield')"
              />
              <span v-else>{{ row.totalYield }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="试制量"
            field="rdYield"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <a-input-number
                v-if="openEditMode"
                :max="Number(row.totalYield)"
                :value="row.rdYield"
                :precision="6"
                @change="(val)=>onYieldChange(Number(val),row,'rdYield','yield')"
              />
              <span v-else>{{ row.rdYield }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="试制比"
            headerAlign="center"
            :width="110"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">
              <span v-if="Number(row.rdYield) && Number(row.totalYield)">{{ (Number(row.rdYield) / Number(row.totalYield)).toFixed(6) }}</span>
              <span v-else>-</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="损耗率"
            field="depreciationRatio"
            align="right"
            headerAlign="center"
            :width="120"
            show-header-overflow
            show-overflow="title"
          >
            <template v-slot="{row}">
              <a-input-number
                v-if="openEditMode"
                :value="row.depreciationRatio"
                :precision="6"
                :min="0"
                :max="1"
                @change="(val)=>onYieldChange(Number(val),row,'depreciationRatio')"
              />
              <span v-else>{{ row.depreciationRatio }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发投入金额"
            headerAlign="center"
            :width="110"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">{{ (Number(row.used) * Number(row.unitPrice)).toFixed(6) }}</template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            title="研发损耗量"
            headerAlign="center"
            :width="110"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">{{ (Number(row.used) * Number(row.depreciationRatio)).toFixed(6) }}</template>
          </vxe-table-column>
        </template>
        <vxe-table-column
          title="研发费用"
          field="rdAmount"
          align="right"
          headerAlign="center"
          :width="110"
          show-header-overflow
          show-overflow="title"
        >
          <template slot-scope="{row}">{{ row.rdAmount.toFixed(2) }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="部门"
          field="deptName"
          align="left"
          headerAlign="center"
          :width="120"
          remoteSort
          show-header-overflow
          show-overflow="title"
        ></vxe-table-column>
        <vxe-table-column
          title="出库单号"
          field="billNo"
          align="right"
          headerAlign="center"
          :width="120"
          remoteSort
          show-header-overflow
          show-overflow="title"
        />
        <vxe-table-column
          title="凭证号"
          field="accNumber"
          align="right"
          headerAlign="center"
          :width="120"
          remoteSort
          show-header-overflow
          show-overflow="title"
        />
      </ystable>
    </div>
    <bindingMaterial-modal
      ref="bindingMaterialModal"
      @ok="search(true,true)"
    />
    <adjustAmount-modal
      ref="adjustAmountModal"
      @ok="search(true,true)"
    />
    <SetPaPerModal
      ref="setPaper"
      @ok="setOk"
    />
    <SetMaterialRatioModal
      ref="setRatio"
      @ok="search(true,true)"
    />
  </div>
</template>

<script>
import RdFeeSubmit from './modules/RdFeeSubmit.vue'
import SetPaPerModal from './modules/SetPaperModal'
import bindingMaterialModal from './bindingDataModules/BindingMaterialModal'
import ystable from '@/components/Table/ystable'
import AdjustAmountModal from './modules/AdjustAmountModal'
import SetMaterialRatioModal from './modules/SetMaterialRatioModal'

export default {
  name: 'MaterialAgg',
  components: {
    bindingMaterialModal,
    ystable,
    AdjustAmountModal,
    SetPaPerModal,
    SetMaterialRatioModal,
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
    month: {
      type: String,
      required: true
    },
    monthDate: {
      type: String,
      required: true
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
      openEditMode: false,
      mname: '',
      mcode: '',
      deptName: '',
      countTotal: 0,
      tableData: [],
      visible: false,
      title: '',
      form: this.$form.createForm(this),
      materialList: [],
      editRow: {}
    }
  },
  watch: {
    projectId (newId) {
      this.search(true)
    }
  },
  methods: {
    search (refresh, getSummary) {
      if (getSummary) {
        this.getSummary()
      }
      this.$refs.table.refresh(refresh)
    },
    getSummary () {
      this.$emit('getSummary')
    },
    completed (data) {
      this.materialList = []
      if (data) {
        this.countTotal = data.footer
        this.tableData = data.data
      }
      this.onOpenEditMode(this.openEditMode)
    },
    onSaveList () {
      const values = []
      for (const k in this.editRow) {
        values.push(this.editRow[k])
      }
      if (values.length <= 0) {
        this.$message.info('未进行任何编辑')
        return
      }
      this.$http.post('/projectMaterial/edit', values)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
            this.editRow = {}
            this.search(false, true)
            this.openEditMode = false
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
        }).finally(res => {
          this.confirmLoading = false
        })
    },
    onOpenEditMode (checked) {
      this.openEditMode = checked
      for (var i = 0; i < this.tableData.length; i++) {
        var record = this.tableData[i]
        if (checked) {
          record.cacheData = { ...record }
        } else {
          const cacheRecord = record.cacheData
          Object.assign(record, cacheRecord)
          delete record.cacheRecord
        }
      }
    },
    onCellChange (value, record, name, key) {
      record[name] = value
      if (key === 'finish') {
        record['finishAmount'] = record['finishUnitPrice'] * record['finishQuantity']
      }
      if (key === 'waste') {
        record['wasteAmount'] = record['wasteUnitPrice'] * record['wasteQuantity']
      }
      record['rdAmount'] = (record['unitPrice'] * record['used']) - record['finishAmount'] - record['wasteAmount']
      this.editRow[record.id] = record
    },
    onYieldChange (value, record, name, key) {
      record[name] = value
      if (key === 'yield') {
        record.used = record.quantity * (record.rdYield / record.totalYield)
      }
      record.rdAmount = Number(record.depreciationRatio) * Number(record.used) * Number(record.unitPrice)
      this.editRow[record.id] = record
    },

    delSelect () {
      const self = this
      this.$confirm({
        title: '您确定要删除选中的物料吗?',
        onOk () {
          return self.$http.post('/projectMaterial/delSelect', { modelList: self.materialList, rdType: self.rdTypeInfo.types[0], acqMonth: self.monthDate, projectId: self.projectId })
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
              } else {
                self.$message.error(res.errorMessage || '删除失败')
              }
              self.search(false, true)
            })
        },
        onCancel () {
        }
      })
    },
    setAdjustAmount () {
      const params = {
        projectId: this.projectId,
        month: this.monthDate,
        pRdIds: this.materialList.map(item => { return item.id }),
        rdTypes: this.rdTypeInfo.types
      }
      this.$refs.adjustAmountModal.show(params)
    },
    onSelectChange ({ records }) {
      this.materialList = records
    },
    setOk (list) {
      if (list && list.length > 0) {
        list.forEach(item => {
          this.editRow[item.id] = item
        })
      }
    },
    setDepreciationRatio () {
      const params = {
        projectId: this.projectId,
        month: this.monthDate,
        ids: this.materialList.map(item => { return item.id }),
        rdType: this.rdTypeInfo.types[0]
      }
      this.$refs.setRatio.set(params)
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
