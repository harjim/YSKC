<!--
 * @Author: zdf
 * @Date: 2022-04-06 08:38:22
 * @LastEditTime: 2022-04-24 15:27:29
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\RdEmployeePlanTotalTab.vue
-->
<template>
  <a-spin :spinning="spinning" tip="请稍后...">

    <a-form layout="inline">
      <a-form-item label="月份" >
        <a-select
          v-model="paramData.monthValue"
          placeholder="请选择月份"
          :allowClear="true"
          :options="monthOptions"
          style="width: 165px;"
        ></a-select>
      </a-form-item>
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
    <div style="height: 65vh;">
      <ystable
        rowId="id"
        ref="table"
        highlight-hover-row
        show-overflow
        resizable
        height="120%"
        auto-resize
        show-header-overflow
        :toolbar="{zoom:true,custom:true,refresh:true}"
        :params="getParams()"
        queryUrl="/rdEmployeePlan/getTotalList"
      >
        <vxe-table-column
          title="月份"
          field="month"
          key="month"
          :width="120"
          remoteSort
          align="center" />
        <vxe-table-column title="姓名" field="ename" key="ename" :width="120" remoteSort>
          <template slot-scope="{ row }">
            <a-badge
              v-if="row.usedList"
              :dot="row.usedList.length > 1"
            >
              <a-tooltip placement="top">
                <template slot="title">
                  <div>总数：{{ row.usedList.length }}</div>
                  <div
                    v-for="(item,index) in row.usedList"
                    :key="index"
                  >
                    {{ item.rdTitle }}分配工时：{{ item.planTime ? item.planTime : '-' }}
                  </div>
                </template>
                <span>{{ row.ename }}</span>
              </a-tooltip>
            </a-badge>
            <span v-else>{{ row.ename }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="工号" field="enumber" key="enumber" :width="120" remoteSort />
        <vxe-table-column
          title="人员类型"
          field="etype"
          key="etype"
          align="center"
          :width="120"
          remoteSort>
          <template #default="{row}">
            {{
              row.etype && Number(row.etype) !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === Number(row.etype)).label : ''
            }}</template>
        </vxe-table-column>
        <vxe-table-column title="总工时" field="total" align="right" :width="120" remoteSort>
        </vxe-table-column>
      </ystable>
    </div>
  </a-spin>
</template>

<script>
import ystable from '@/components/Table/ystable'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'

export default {
  mixins: [yearMiXin],
  name: 'RdEmployeePlanTotalTab',
  components: {
    ystable
  },
  props: {
    currentKey: {
      type: String,
      default: '1'
    }
  },
  data () {
    return {
      tabKey: '2',
      month: '',
      spinning: false,
      paramData: {}
    }
  },
  watch: {
    currentKey (key) {
      if (key === this.tabKey) {
        this.search(true)
      }
    }
  },
  methods: {
    moment,
    getParams () {
      const params = { year: this.currentYear, enumber: this.paramData.enumber, ename: this.paramData.ename }
      const monthValue = this.paramData.monthValue
      if (monthValue !== undefined && monthValue >= 0) {
        params.month = moment([this.currentYear, monthValue, 1, 0, 0, 0, 0])
      }
      return params
    },
    search (refresh) {
      this.$nextTick(() => {
        if (this.$refs.table && this.currentKey === this.tabKey) {
          this.$refs.table.refresh(refresh)
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
</style>
