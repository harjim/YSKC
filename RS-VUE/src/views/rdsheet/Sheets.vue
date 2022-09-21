<template>
  <a-card
    style="height: 100%"
    :bodyStyle="{ height: '100%', overflow: 'auto'}"
    v-if="$auth('rdsheet:sheet:view')"
  >
    <a-form layout="inline">
      <a-form-item label="项目">
        <a-select style="width:600px;float:left" :value="projectId" @change="OnProjectChange">
          <a-select-option v-for="item in projectList" :key="item.id">
            <span
              v-if="item!=null"
            >{{ item.rdTitle }} - {{ item.pname }}</span>
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-alert
        v-if="errorMsg!==''"
        :message="errorMsg"
        type="warning"
        style="width:600px;margin-left:40px"
      />
    </a-form>
    <a-layout
      style="background: #fff; padding: 0; height: calc(100% - 39px); overflow: auto;"
      v-if="selectProject!=null"
    >
      <a-layout-sider
        :trigger="null"
        style="max-width: 175px; min-width: 10px; width: 175px;background: rgb(255, 255, 255);"
        collapsible
        v-model="collapsed"
      >
        <a-menu mode="inline" class="menu-style" :defaultSelectedKeys="['10']" @click="menuClick">
          <a-menu-item v-for="(v,k) in keyTypeMap" :key="k">
            <span>{{ v.name }}</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-content
          :style="{ margin: '0px 10px', padding: '0px 24px 24px 24px ', background: '#fff', minHeight: '280px' }"
        >
          <AggTab
            :key="`${selectKey}`"
            :type="selectKey"
            :projectId="projectId"
            :rdType="keyTypeMap[selectKey].types[0]"
            :rdTypeInfo="keyTypeMap[selectKey]"
            :types="keyTypeMap[selectKey].types"
            :dataUrl="keyTypeMap[selectKey].dataUrl"
            :nodeUrl="keyTypeMap[selectKey].nodeUrl"
            :typeName="keyTypeMap[selectKey].name"
          ></AggTab>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-card>
</template>

<script>
import { PageView } from '@/layouts'
import BaseSheet from './workSheet/BaseSheet'
import AttendanceSheet from './workSheet/AttendanceSheet'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
const keyTypeMap = {
  '10': {
    'name': '人员工单',
    'component': AttendanceSheet,
    'types': [10000, 10001, 10101, 10102, 10103, 10104, 10105, 10106, 10107]
  },
  '20': {
    'name': '设备工单',
    'component': BaseSheet,
    'types': [30000, 30100],
    'dataUrl': '/workSheet/getEquipmentWorkSheetMonthList',
    nodeUrl: '/workSheet/getEquipmentWorkSheetList'
  },
  '31': { 'name': '研发材料工单', 'component': BaseSheet, 'types': [20000], 'dataUrl': '/workSheet/getMaterialWorkSheetMonthList', nodeUrl: '/workSheet/getMaterialWorkSheetList' },
  '32': { 'name': '中间试制工单', 'component': BaseSheet, 'types': [20301], 'dataUrl': '/workSheet/getMaterialWorkSheetMonthList', nodeUrl: '/workSheet/getMaterialWorkSheetList' },
  '33': { 'name': '修理材料工单', 'component': BaseSheet, 'types': [20601], 'dataUrl': '/workSheet/getMaterialWorkSheetMonthList', nodeUrl: '/workSheet/getMaterialWorkSheetList' },
  '40': { 'name': '动力工单', 'component': BaseSheet, 'types': [20100], 'dataUrl': '/workSheet/getEnergyWorkSheetMonthList', nodeUrl: '/workSheet/getEnergyWorkSheetList' },
  '41': { 'name': '燃料工单', 'component': BaseSheet, 'types': [20200], 'dataUrl': '/workSheet/getEnergyWorkSheetMonthList', nodeUrl: '/workSheet/getEnergyWorkSheetList' },
  '50': { 'name': '设计费用工单', 'component': BaseSheet, 'types': [50000], 'dataUrl': '/workSheet/getDesignListByMonth', nodeUrl: '/workSheet/getDesignList' },

  '59': { 'name': '检测费用工单', 'component': BaseSheet, 'types': [20500], 'dataUrl': '/workSheet/getInspectionListByMonth', nodeUrl: '/workSheet/getInspectionList' },
  '61': { 'name': '修理费用工单', 'component': BaseSheet, 'types': [20600], 'dataUrl': '/workSheet/getInspectionListByMonth', nodeUrl: '/workSheet/getInspectionList' },
  '62': { 'name': '差旅费用工单', 'component': BaseSheet, 'types': [60400], 'dataUrl': '/workSheet/getInspectionListByMonth', nodeUrl: '/workSheet/getInspectionList' },
  '63': { 'name': '摊销费用工单', 'component': BaseSheet, 'types': [40000, 40100, 40200], 'dataUrl': '/workSheet/getInspectionListByMonth', nodeUrl: '/workSheet/getInspectionList' },
  '64': { 'name': '其他试制工单', 'component': BaseSheet, 'types': [20300], 'dataUrl': '/workSheet/getInspectionListByMonth', nodeUrl: '/workSheet/getInspectionList' },
  '65': {
    'name': '其他费用工单',
    'component': BaseSheet,
    'types': [69900],
    'dataUrl': '/workSheet/getInspectionListByMonth',
    nodeUrl: '/workSheet/getInspectionList'
  }
}
export default {
  mixins: [yearMiXin],
  name: 'Sheets',
  components: {
    PageView,
    'AggTab': {
      functional: true,
      render: function (createElement, context) {
        const d = context.data
        return createElement(
          keyTypeMap[d.attrs.type].component,
          context.data,
          context.children
        )
      }
    }
  },
  // props: {
  //   tprojectId: {
  //     type: Number,
  //     default: 0
  //   }
  // },
  data () {
    return {
      keyTypeMap: keyTypeMap,
      ryear: this.$store.state.currentYear,
      collapsed: false,
      selectKey: '10',
      month: '',
      projectList: [],
      projectId: undefined,
      selectProject: {},
      project: {},
      rdFundsMap: {},
      allRdDataMap: {},
      allRdColumns: [],
      yearSelectOption: [],
      projectMap: {},
      errorMsg: ''
    }
  },
  created () {
    this.rdYear = this.$store.state.currentYear
    this.onYearChange(this.$store.state.currentYear)
    this.OnProjectChange(this.projectId)
  },
  computed: {
    months: function () {
      if (this.selectProject == null) {
        return []
      }
      var endDate = this.selectProject.endDate
      var beginDate = this.selectProject.beginDate
      if (typeof endDate === 'undefined' || typeof beginDate === 'undefined') {
        return []
      }
      var beginArray = beginDate.split('-').map(Number)
      var endArray = endDate.split('-').map(Number)
      var returnArry = []
      var begin = parseInt(beginArray[0]) * 12 + parseInt(beginArray[1])
      var end = parseInt(endArray[0]) * 12 + parseInt(endArray[1])
      var totalMonth = Math.abs(end - begin)
      for (var i = 0; i <= totalMonth; i++) {
        var month = (parseInt(beginArray[1]) + i)
        if (month <= 12) {
          returnArry.push(beginArray[0] + '-' + (month + '').padStart(2, '0'))
        } else {
          returnArry.push(endArray[0] + '-' + (month - 12 + '').padStart(2, '0'))
        }
      }
      return returnArry
    },
    monthDate: function () {
      return this.getMonthDate(this.month)
    }
  },
  methods: {
    search () {
      this.rdYear = this.$store.state.currentYear
      this.selectedRowKeys = []
      this.selectedRows = []
      this.onYearChange(this.$store.state.currentYear)
    },
    getMonthDate (m) {
      return `${m}-01 00:00:00`
    },
    tabChange (activeKey) {
      this.month = activeKey.replace(this.selectKey, '')
    },
    menuClick (e) {
      if (this.selectKey !== e.key) {
        this.selectKey = e.key
      }
    },
    moment,
    OnProjectChange (value) {
      this.projectId = value
      this.selectProject = this.project[value]
    },
    onYearChange (value, option) {
      if (this.tprojectId === 0 || !value) {
        return
      }
      this.rdYear = value
      this.$http.get('/project/getSelectList', { params: { year: value, sign: 2 } })
        .then(res => {
          if (res.success && res.data !== null && res.data.length > 0) {
            this.projectList = res.data
            if (this.projectList.length > 0) {
              this.projectList.map(item => {
                this.project[item.id] = item
              })
              this.projectId = this.projectList[0].id
              this.errorMsg = ''
            } else {
              this.projectId = undefined
              this.project = {}
              this.errorMsg = '当前年份：' + value + '没有项目，请重新选择年份'
            }
          } else {
            this.projectList = []
            this.projectId = undefined
            this.project = {}
            // this.$message.warning('当前年份：' + value + '没有项目，请重新选择年份')
            this.errorMsg = '当前年份：' + value + '没有项目，请重新选择年份'
          }
          this.OnProjectChange(this.projectId)
          return res.data
        })
    }
  }
}
</script>
<style lang="less" scoped>
.menu-style {
  border: '0';
  width: 'auto';
  padding-top: 12px;
}
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>
