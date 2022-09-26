/**
 * @Author        : hzp
 * @Date          : 2022-09-23 15:23:46
 * @FilePath      : \YSIS\RS-VUE\src\views\project\RdHourSummary.vue
 * @Description   : 人员设备工时表
 * @LastEditors   : hzp
 * @LastEditTime  : 2022-09-26 17:02:57
 */

<template>
  <a-card>
    <template v-if="$auth('project:rdHourSummary:search')">
      <a-tabs v-model="activeKey">
        <a-tab-pane tab="人员工时记录" :key="1">
          <PersonnelHourSummaryTab ref="PersonnelHourSummaryTab" :projects="projects" :months="months" :year="currentYear" />
        </a-tab-pane>
        <a-tab-pane tab="设备工时记录" :key="2">
          <EquipmentHourSummaryTab ref="EquipmentHourSummaryTab" :projects="projects" :months="months" :year="currentYear" />
        </a-tab-pane>
        <template slot="tabBarExtraContent">
          <a-button v-if="$auth('project:rdHourSummary:exportProjects')" type="primary" style="margin-left: 8px;" @click="showExportsModal('project')">按项目导出</a-button>
          <a-button v-if="$auth('project:rdHourSummary:exportMonths')" type="primary" style="margin-left: 8px;" @click="showExportsModal('months')">按月份导出</a-button>
        </template>
      </a-tabs>
      <MonthsExportsModal v-if="$auth('project:rdHourSummary:exportMonths')" ref="MonthsExportsModal" />
      <ProjectsExportsModal v-if="$auth('project:rdHourSummary:exportProjects')" ref="ProjectsExportsModal" />
    </template>
    <a-empty v-else />
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
          value: moment(`${this.currentYear}-${padStart(i + 1, 2, '0')}-01`).format('YYYY-MM-DD HH:mm:ss'),
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
            label: `所有项目${data.length}`,
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
    },
    showExportsModal (flag) {
      const temp = {
        year: this.currentYear
      }
      this.wrapperQueryParams(temp)
      switch (flag) {
        case 'project':
          temp.projects = this.projects
          this.showExportsProjects(temp)
          break
        case 'months':
          temp.months = this.months
          this.showExportsMonths(temp)
          break
      }
    },
    wrapperQueryParams (queryParams) {
      if (this.activeKey === 1) {
        queryParams.enumber = this.$refs.PersonnelHourSummaryTab.params.enumber
        queryParams.ename = this.$refs.PersonnelHourSummaryTab.params.ename
      } else {
        queryParams.ecode = this.$refs.EquipmentHourSummaryTab.params.ecode
        queryParams.ename = this.$refs.EquipmentHourSummaryTab.params.ename
      }
    },
    showExportsMonths (queryParams) {
      this.$refs.MonthsExportsModal.show(queryParams, this.activeKey)
    },
    showExportsProjects (queryParams) {
      this.$refs.ProjectsExportsModal.show(queryParams, this.activeKey)
    }
  }
}
</script>
