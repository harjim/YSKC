/**
 * @Author        : hzp
 * @Date          : 2022-09-23 15:23:46
 * @FilePath      : \YSIS\RS-VUE\src\views\project\RdHourSummary.vue
 * @Description   : 人员设备工时表
 * @LastEditors   : hzp
 * @LastEditTime  : 2022-09-24 11:45:35
 */

<template>
  <a-card>
    <a-tabs v-model="activeKey">
      <a-tab-pane tab="人员工时记录" :key="1">
        <PersonnelHourSummaryTab :projects="projects" :months="months" :year="currentYear" />
      </a-tab-pane>
      <a-tab-pane tab="设备工时记录" :key="2">
        <EquipmentHourSummaryTab :projects="projects" :months="months" :year="currentYear" />
      </a-tab-pane>
      <template slot="tabBarExtraContent">
        <a-button type="primary" style="margin-left: 8px;" @click="$refs.ProjectsExportsModal.show()">按项目导出</a-button>
        <a-button type="primary" style="margin-left: 8px;" @click="$refs.MonthsExportsModal.show()">按月份导出</a-button>
      </template>
    </a-tabs>
    <MonthsExportsModal ref="MonthsExportsModal" />
    <ProjectsExportsModal ref="ProjectsExportsModal" />
  </a-card>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
import { map, padStart } from 'lodash'
import moment from 'moment'
import EquipmentHourSummaryTab from './hourSummary/EquipmentHourSummaryTab.vue'
import MonthsExportsModal from './hourSummary/MonthsExportsModal.vue'
import PersonnelHourSummaryTab from './hourSummary/PersonnelHourSummaryTab.vue'
import ProjectsExportsModal from './hourSummary/ProjectsExportsModal.vue'

export default {
  components: { PersonnelHourSummaryTab, EquipmentHourSummaryTab, MonthsExportsModal, ProjectsExportsModal },
  mixins: [yearMiXin],
  data () {
    return {
      activeKey: 1,
      months: [],
      projects: []
    }
  },
  watch: {
    currentYear: {
      immediate: true,
      handler (val) {
        if (val) {
          this.getMonths()
          this.getSelectList()
        }
      }
    }
  },
  methods: {
    /******
     * @description: 根据年份获取月份天数
     * @return {*}
     */
    getMonths () {
      const months = []
      for (let i = 0; i < 12; i++) {
        months.push({
          value: i + 1,
          label: `${i + 1}月`,
          days: moment(`${this.currentYear}-${padStart(i + 1, 2, '0')}`, 'YYYY-MM').daysInMonth()
        })
      }
      this.months = months
    },
    /******
     * @description: 获取当年项目列表
     * @return {*}
     */
    getSelectList () {
      this.$http.get('/project/getSelectList', { params: { year: this.currentYear, sign: 2 } }).then(({ success, data, errorMessage }) => {
        if (success) {
          this.projects = [{
            value: 0,
            label: `全选${data.length}`,
            children: map(data, item => ({
              ...item,
              value: item.id,
              label: item.pname
            }))
          }]
        } else {
          this.$message.error(errorMessage)
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>
