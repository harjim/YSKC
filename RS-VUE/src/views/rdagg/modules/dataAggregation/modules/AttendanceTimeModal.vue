<template>
  <div>
    <ystable
      ref="table"
      queryUrl="/projectAttendance/getTimeList"
      :params="queryParams"
      rowId="id"
      :toolbar="{zoom: true, refresh: true}"
    >
      <template v-slot:buttons>
        <span style="float:right;padding-right:10px;">
          <a-button type="primary" size="small" @click="modalChange('hourModal')">切换工时</a-button>
        </span>
      </template>
      <vxe-table-column
        align="center"
        title="工号"
        field="enumber"
        :width="130"
        remoteSort
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="姓名"
        field="ename"
        :width="130"
        remoteSort
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="部门"
        field="deptName"
        :width="130"
        remoteSort
        show-header-overflow
        show-overflow="title"
      ></vxe-table-column>
      <vxe-table-column
        align="center"
        title="人员类型"
        field="etype"
        :width="100"
        remoteSort
      >
        <template slot-scope="{ row }">
          {{
            row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : ''
          }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        align="center"
        title="出勤日期"
        field="workDate"
        :width="120"
        remoteSort
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="工时"
        field="workHour"
        :width="100"
        remoteSort
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="上班时间1"
        field="onTime1"
        :width="100"
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="下班时间1"
        field="offTime1"
        :width="100"
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="上班时间2"
        field="onTime2"
        :width="100"
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="下班时间2"
        field="offTime2"
        :width="100"
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="上班时间3"
        field="onTime3"
        :width="100"
        show-header-overflow
        show-overflow="title"
      />
      <vxe-table-column
        align="center"
        title="下班时间3"
        field="offTime3"
        :width="100"
        show-header-overflow
        show-overflow="title"
      />
    </ystable>
  </div>
</template>
<script>
import moment from 'moment'
import ystable from '@/components/Table/ystable'
export default {
  components: {
    ystable
  },
  props: {
    queryParams: {
      type: Object,
      default: () => { return {} }
    }
  },
  data () {
    return {
    }
  },
  methods: {
    moment,
    search (refresh, param) {
      if (param) {
        this.queryParams = param
      }
      this.$refs.table.refresh(refresh)
    },
    modalChange (key) {
      this.$emit('modalChange', key)
    }
  }
}
</script>

<style>
</style>
