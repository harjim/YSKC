/**
 * @Author        : hzp
 * @Date          : 2022-09-23 15:50:38
 * @FilePath      : \YSIS\RS-VUE\src\views\project\hourSummary\PersonnelHourSummaryTab.vue
 * @Description   : 人员设备工时表/人员工时记录
 * @LastEditors   : hzp
 * @LastEditTime  : 2022-09-26 14:14:42
 */

<template>
  <div>
    <a-form layout="inline">
      <a-form-item label="项目">
        <a-tree-select
          show-search
          style="min-width: 200px; max-width: 1120px;"
          placeholder="请选择项目"
          multiple
          tree-checkable
          treeDefaultExpandAll
          dropdownMatchSelectWidth
          :show-checked-strategy="SHOW_PARENT"
          :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
          :treeData="projects"
          v-model="params.projectIds"
          @change="changeProjectChecked"
        ></a-tree-select>
      </a-form-item>
      <a-form-item label="月份">
        <a-select :options="months" style="width: 200px;" placeholder="请选择月份" v-model="params.months" @change="changeMonths" />
      </a-form-item>
      <a-form-item label="工号">
        <a-input style="width: 200px;" placeholder="请输入工号" v-model="params.enumber" />
      </a-form-item>
      <a-form-item label="姓名">
        <a-input style="width: 200px;" placeholder="请输入姓名" v-model="params.ename" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="refresh(true)">查询</a-button>
      </a-form-item>
    </a-form>
    <YsTable
      ref="xTable"
      border="full"
      queryUrl="/projectAttendance/getRdHourData"
      :params="params"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      :toolbar="{ refresh: true, zoom: true, custom: true }"
    >
      <vxe-table-column type="seq" width="60" fixed="left"></vxe-table-column>
      <vxe-table-column title="RD" field="rdTitle" width="120" fixed="left" remoteSort></vxe-table-column>
      <vxe-table-column title="项目名称" field="pname" width="140" fixed="left" remoteSort></vxe-table-column>
      <vxe-table-column title="工号" field="enumber" width="100" fixed="left" remoteSort></vxe-table-column>
      <vxe-table-column title="姓名" field="ename" width="120" fixed="left" remoteSort></vxe-table-column>
      <vxe-table-column title="部门" field="deptName" width="120" remoteSort></vxe-table-column>
      <vxe-table-column title="人员类型" field="typeDetail" width="120" remoteSort></vxe-table-column>
      <vxe-table-column title="总工时" field="workHours" width="100" align="right"></vxe-table-column>
      <vxe-table-column title="研发工时" field="rdHour" width="100" remoteSort align="right"></vxe-table-column>
      <vxe-table-column
        width="80"
        v-for="n in get(months, `[${moment(params.months).format('MM') - 1}].days`, 0)"
        :key="n"
        :title="`${n}号`"
        :field="`info[${n}]`"
        remoteSort
        align="right"></vxe-table-column>
    </YsTable>
  </div>
</template>

<script>
import YsTable from '@/components/Table/ystable'
import { TreeSelect } from 'ant-design-vue'
import { get, map } from 'lodash'
import moment from 'moment'

const SHOW_PARENT = TreeSelect.SHOW_PARENT

export default {
  components: {
    YsTable
  },
  props: {
    projects: {
      type: Array,
      default: () => ([])
    },
    months: {
      type: Array,
      default: () => ([])
    },
    year: {
      type: Number,
      default: undefined
    }
  },
  data () {
    return {
      SHOW_PARENT,
      params: {
        year: this.year
      }
    }
  },
  watch: {
    /******
     * @description: 监听项目列表的变化并自动选择第一个项目
     * @return {*}
     */
    projects: {
      immediate: true,
      handler (val) {
        if (get(val, '[0].children.length')) {
          this.params.projectIds = [get(val, '[0].children[0].id')]
          this.refresh(true)
        }
      }
    },
    /** ****
     * @description: 监听项目月份变化并自动选择一月份
     * @return {*}
     */
    months: {
      immediate: true,
      handler (val) {
        if (get(val, '[0].value')) {
          this.params.months = get(val, '[0].value')
        }
      }
    }
  },
  methods: {
    get,
    moment,
    /******
     * @description: 表格刷新
     * @param {Boolean} flag 是否刷回第一页，默认否
     * @return {*}
     */
    refresh (flag = false) {
      this.$refs.xTable.refresh(flag)
    },
    /******
     * @description: 项目选择，全选则选中所有 ID
     * @param {Array} value
     * @return {*}
     */
    changeProjectChecked (projectIds) {
      if (projectIds.length === 1 && projectIds[0] === 0) {
        projectIds = map(this.projects[0].children, item => item.value)
      }
      this.params = {
        ...this.params,
        projectIds
      }
    },
    /** ****
     * @description: 月份选择
     * @param {Number} value
     * @return {*}
     */
    changeMonths (months) {
      this.params = {
        ...this.params,
        months
      }
    }
  }
}
</script>
