<template>
  <div class="customCard">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="设备名称">
          <a-input v-model="queryParam.ename" placeholder="请输入设备名称" style="width:165px" />
        </a-form-item>
        <a-form-item label="设备类型">
          <a-select v-model="queryParam.etype" style="width:165px">
            <a-select-option value="-1">请选择设备类型</a-select-option>
            <a-select-option v-for="item in $getEnums('equipmentEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          size="small"
          rowId="ecode"
          @checkbox-change="onSelectChange"
          @checkbox-all="onSelectChange"
          :checkbox-config="{checkMethod:canChecked,showHeader: showHeaderChk}"
          :toolbar="{zoom:true,custom: true,refresh:true}"
          queryUrl="/projectRdEquipment/getEquipmentPowerList"
          :params="queryParam"
          @completed="({ data }) => completed(data)"
          :pagerConfig="{ pageSize: 50, pageSizes: [50, 100, 200, 500] }"
        >
          <template v-slot:buttons>
            <template v-if="canModify && $auth('project:data:agg')">
              <span>
                <a-button
                  type="primary"
                  style="margin-right:10px;"
                  @click="$refs.setPowerUnitPrice.show(projectId,monthDate)"
                >设置电费</a-button>
              </span>
              <a-button style="margin-right: 10px;" type="primary" :disabled="selectRows.length <= 0" @click="adjustAmount">调整费用</a-button>
              <rd-fee-submit :projectId="projectId" :month="month" :rdType="rdFeeType" @getSummary="getSummary"/>
            </template>
          </template>
          <vxe-table-column type="checkbox" :width="50" fixed="left" />
          <vxe-table-column
            align="left"
            title="资产代码"
            field="ecode"
            :width="160"
            remoteSort
            show-header-overflow
            show-overflow="title"
            fixed="left"
          />
          <vxe-table-column
            align="left"
            title="设备名称"
            field="ename"
            :min-width="180"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{row}">
              <a-badge v-if="row.usedList" :dot="row.usedList.length > 0 ">
                <a-tooltip placement="top">
                  <template slot="title">
                    <div
                      v-for="(item, index) in row.usedList"
                      :key="index"
                    >{{ item.beginYear }}RD{{ item.rdIndex | ZeroFormat }}分配工时：{{ item.rdHour }}</div>
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
            key="etype"
            :width="80"
            remoteSort
          >
            <template slot-scope="{ row }">
              <span>{{ row.etype ? $getEnums('equipmentEnum').find(elem => elem.value === row.etype).label : '' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="left"
            title="部门"
            field="deptName"
            key="deptName"
            :width="180"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            align="right"
            title="研发工时"
            :width="110"
            field="rdHour"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            align="right"
            title="功率(kWh)"
            :width="110"
            field="usagePower"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            align="right"
            title="电费单价"
            :width="110"
            field="powerUnitPrice"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            align="right"
            title="研发电费"
            :width="120"
            field="powerRate"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
        </ystable>
      </div>
    </a-spin>
    <adjustAmount-modal ref="adjustAmountModal" @ok="handleOk" />
    <set-power-unit-price-modal ref="setPowerUnitPrice" @ok="handleOk" />
  </div>
</template>

<script>
import RdFeeSubmit from './modules/RdFeeSubmit.vue'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import yearMixin from '@/utils/yearMixin'
import SetPowerUnitPriceModal from './bindingDataModules/SetPowerUnitPriceModal'
import AdjustAmountModal from './modules/AdjustAmountModal'
import { mapState } from 'vuex'
export default {
  mixins: [yearMixin],
  name: 'EquipmentPowerAgg',
  components: {
    SetPowerUnitPriceModal,
    ystable,
    AdjustAmountModal,
    RdFeeSubmit
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    projectYear: {
      type: Number,
      required: true
    },
    month: {
      type: String,
      default: ''
    },
    monthDate: {
      type: String,
      required: true
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
    }
  },
  data () {
    return {
      showHeaderChk: true,
      selectRows: [],
      spinning: false,
      queryParam: {
        projectId: this.projectId,
        month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'),
        year: this.currentYear
      }
    }
  },
  computed: {
    ...mapState({
    })
  },
  watch: {
    projectId (newId) {
      this.$refs.table.refresh(true)
      this.queryParam = {
        projectId: newId,
        month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'),
        year: this.currentYear
      }
    }
  },
  mounted () {
    this.queryParam = {
      projectId: this.projectId,
      month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'),
      year: this.currentYear
    }
  },
  methods: {
    completed (data) {
      if (data && data.length) {
        let temp = false
        for (let i = 0; i < data.data.length; i++) {
          if (data[i].powerUnitPrice && data[i].powerRate) {
            temp = true
            break
          }
        }
        this.showHeaderChk = temp
      } else {
        this.showHeaderChk = false
      }
    },
    canChecked ({ row }) {
      return row.powerUnitPrice && row.powerRate
    },
    handleOk () {
      this.$refs.table.refresh(true)
      this.selectRows = []
      this.getSummary()
    },
    getSummary () {
      this.$emit('getSummary')
    },
    onSelectChange ({ records }) {
      this.selectRows = records
    },
    adjustAmount () {
      const params = {
        projectId: this.projectId,
        month: moment(this.month),
        rdTypes: this.rdTypeInfo.types,
        pRdIds: this.selectRows.map(a => a.id)
      }
      if (!params.pRdIds || params.pRdIds.length <= 0) {
        this.$message.info('所选设备未进行归集，不能调整费用。')
      } else {
        this.$refs.adjustAmountModal.show(params)
      }
    }
  }
}
</script>

<style  lang="less" scoped >
.customCard {
  padding: 0 12px;
}
</style>
