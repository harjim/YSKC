<template>
  <a-spin
    :spinning="spinning"
    tip="请稍后..."
    style="height: 100%; overflow:auto"
  >
    <a-card
      :bordered="false"
      style="height: 100%; overflow:auto"
      :bodyStyle="{ height: '100%', overflow: 'auto'}"
    >
      <a-form layout="inline" >
        <a-form-item label="姓名">
          <a-input placeholder="请输入姓名" v-model="queryParams.ename"></a-input>
        </a-form-item>
        <a-form-item label="工号">
          <a-input placeholder="请输入工号" v-model="queryParams.enumber"></a-input>
        </a-form-item>
        <a-form-item><a-button type="primary" @click="search(true)">查询</a-button></a-form-item>
      </a-form>
      <div style="height: calc(100% - 39px); overflow: auto;">
        <ystable
          border
          highlight-hover-row
          show-overflow
          show-header-overflow
          ref="table"
          :checkbox-config="{checkMethod:canChecked,showHeader: showHeaderChk}"
          @completed="({data:{data}})=>completed(data)"
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          queryUrl="/projectRdEmployee/getRdEmployeeList"
          :params="queryParams"
          :toolbar="{zoom: true,custom:true,refresh:true}"
        >
          <template v-slot:buttons>
            <span style="padding-right:10px;">
              <a-button :disabled="!selectRows.length" type="primary" @click="aggFee()">归集费用</a-button>
            </span>
          </template>
          <vxe-table-column
            type="checkbox"
            :width="50"
            fixed="left"
          />
          <vxe-table-column
            title="工号"
            remoteSort
            field="enumber"
            :width="120"
          />
          <vxe-table-column
            title="姓名"
            remoteSort
            field="ename"
            :width="120"
          />
          <vxe-table-column
            title="研发部门"
            remoteSort
            field="rdDeptName"
            :width="120"
          >
            <template v-slot="{row}">{{ row.rdDeptName }}</template>
          </vxe-table-column>
          <vxe-table-column
            title="人员类型"
            field="etype"
            remoteSort
            :width="120"
          >
            <template v-slot="{row}">{{ row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : '' }}</template>
          </vxe-table-column>
          <vxe-table-column
            title="RD"
            field="rds"
          />

        </ystable>
      </div>
    </a-card>
    <batch-agg-modal configType="0" ref="batchAgg" @ok="handleOk" />
  </a-spin>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import BatchAggModal from './modules/BatchAggModal'
export default {
  components: {
    ystable,
    BatchAggModal
  },
  mixins: [yearMiXin],
  data () {
    return {
      spinning: false,
      queryParams: {},
      selectRows: [],
      showHeaderChk: true
    }
  },
  created () {
    this.queryParams.year = this.currentYear
  },
  methods: {
    handleOk () {
      this.selectRows = []
      this.$refs.table.clearCheckboxRow()
    },
    canChecked ({ row }) {
      return row.etype && row.etype > 0 && row.rds && row.rds.length > 0
    },
    completed (data) {
      this.selectRows = []
      if (data && data.length) {
        let count = 0
        for (const item of data) {
          if (item.etype && item.etype > 0 && item.rds && item.rds.length > 0) {
            count++
          }
        }
        if (count > 0) {
          this.showHeaderChk = true
        } else {
          this.showHeaderChk = false
        }
      }
    },
    moment,
    search (refresh) {
      this.queryParams.year = this.currentYear
      this.$nextTick(() => {
        if (this.$refs.table) {
          this.$refs.table.refresh(refresh)
        }
      })
    },
    selectChange ({ records }) {
      this.selectRows = records
    },
    aggFee () {
      const etypeMax = { 1: 0, 2: 0, 3: 0 }
      const enumbers = []
      this.selectRows.forEach(item => {
        etypeMax[item.etype] = Math.max(etypeMax[item.etype], item.rds.split(',').length)
        enumbers.push(item.enumber)
      })
      if (!enumbers.length) {
        this.$message.warning('选中的人员未关联项目')
        return
      }
      this.$refs.batchAgg.showModal(enumbers, etypeMax, this.currentYear)
    }
  }
}
</script>
