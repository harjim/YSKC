<!--
 * @Author: ldx
 * @Date: 2021-03-04 08:56:04
 * @LastEditTime: 2022-05-26 14:14:48
 * @LastEditors: zdf
 * @Description: 预算表格
 * @FilePath: \RS-VUE\src\components\CapitalBudget\Budget.vue
-->
<template>
  <a-drawer
    :title="title"
    placement="right"
    :closable="true"
    :visible="visible"
    @close="onClose"
    width="80vw"
    style="height: 100vh; overflow: hidden;"
  >
    <a-spin :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="项目">
          <a-select
            style="width: 600px; float: left"
            :value="projectId"
            @change="OnProjectChange"
          >
            <a-select-option
              v-for="item in projectList"
              :key="item.id"
            >
              <span v-if="item != null">{{ item.rdTitle }} - {{ item.pname }}</span>
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-button
          type="primary"
          v-if="$auth('project:data:export')"
          @click="$refs.exportDataModal.show()"
        >
          导出
        </a-button>
        <a-alert
          v-if="errorMsg !== ''"
          :message="errorMsg"
          type="warning"
          style="width: 600px; margin-left: 40px"
        />
      </a-form>
      <a-layout
        style="padding: 0; height: 100%; overflow: auto;width: 100%"
        v-if="selectProject != null"
      >
        <a-layout>
          <!-- 归集方式右上按钮 -->
          <div
            class="customBtn"
            @click="showSecondaryMenu"
            ref="btn"
          >
            <a-icon
              :style="{ fontSize: '22px' }"
              type="bars"
              v-if="keyTypeMap[selectKey].node"
              title="点击切换归集方式"
            />
          </div>
          <!-- 隐藏的归集方式 -->
          <a-layout-sider
            v-if="keyTypeMap[selectKey].node"
            id="secondaryMenu"
            :trigger="null"
            style="max-height: 70%; overflow: auto"
            :class="{ hidSider: !isShowSecondaryMenu, showSider: isShowSecondaryMenu }"
            collapsible
            v-model="collapsed"
          >
            <a-menu
              class="customBackground"
              mode="inline"
              @click="(e) => menuClick(e, 'node')"
              v-model="nodeCheck[selectKey]"
            >
              <a-menu-item
                v-for="(v, k) in keyTypeMap[selectKey].node"
                :key="k"
              >
                <span>{{ v.name }} [{{ getRdTypeFunds(k) }}]</span>
              </a-menu-item>
            </a-menu>
          </a-layout-sider>
          <a-tabs
            id="tabs"
            v-if="selectProject != null"
            :activeKey="handleActiveKey(this.nodeCheck[this.selectKey], this.selectKey, this.month, this.months)"
            @change="tabChange"
            :key="`tabs${projectId}`"
          >
            <a-tab-pane
              v-for="n in months"
              :key="`${nodeCheck[selectKey] ? nodeCheck[selectKey] : selectKey}${n}`"
            >
              <template #tab>
                <a-badge
                  :offset="[4,3]"
                  v-if="isMsUser && getStatusInfo(keyTypeMap[selectKey].rdType,n).rdType"
                  :dot="true"
                  :color="statusColor[getStatusInfo(keyTypeMap[selectKey].rdType,n).status]"
                  :title="getStatusInfo(keyTypeMap[selectKey].rdType,n).suggestion">

                  {{ getTabTitle(n, nodeCheck[selectKey] ? nodeCheck[selectKey] : selectKey) }}</a-badge>
                <span v-else>{{ getTabTitle(n, nodeCheck[selectKey] ? nodeCheck[selectKey] : selectKey) }}</span>
              </template>
              <AggTab
                :key="`${nodeCheck[selectKey] ? nodeCheck[selectKey] : selectKey}${n}`"
                :type="nodeCheck[selectKey] ? nodeCheck[selectKey] : selectKey"
                :projectMonth="n"
                :month="n"
                :monthDate="getMonthDate(n)"
                :projectId="projectId"
                :projectYear="project[projectId].beginYear"
                :rdType="selectNode.types[0]"
                :rdTypeInfo="selectNode"
                :rdFeeType="keyTypeMap[selectKey].rdType"
                :selectProject="selectProject"
                @getSummary="refreshSummary"
                :canModify="canModify(getStatusInfo(keyTypeMap[selectKey].rdType,n).status)"
              ></AggTab>
            </a-tab-pane>
          </a-tabs>
        </a-layout>
      </a-layout>
      <export-data-modal ref="exportDataModal" />
      <export-data-modal ref="exportDataModal" />
    </a-spin>
  </a-drawer>
</template>

<script>
// import DataAggregationVue from '@/views/project/DataAggregation'
import { PageView } from '@/layouts'
import { canModify, isMsUser, statusColor } from '@/utils/processDoc/auditStatus'
import InspectionAgg from './dataAggregation/InspectionAgg'
import RdEmployeeAgg from './dataAggregation/RdEmployeeAgg'
import RdEquipmentAgg from './dataAggregation/RdEquipmentAgg'
import MaterialAgg from './dataAggregation/MaterialAgg'
import EnergyAgg from './dataAggregation/EnergyAgg'
import EquipmentPowerAgg from './dataAggregation/EquipmentPowerAgg'
import DesignAgg from './dataAggregation/DesignAgg'
import ExportDataModal from './ExportDataModal'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
const keyTypeMap = { // rdType属性为大类，审核用
  '10': {
    'name': '人员费用',
    'rdType': '10000',
    'component': RdEmployeeAgg,
    'types': [10000, 10100], // 10001, 10101, 10102, 10103, 10104, 10105, 10106, 10107
    'columns': [{ 'name': '工资', 'types': [10000] }, { 'name': '奖金', 'types': [10001] }, { 'name': '五险一金', 'types': [10101, 10102, 10103, 10104, 10105, 10106, 10107] }]
  },
  '20': {
    'name': '设备折旧',
    'rdType': '30000',
    'component': RdEquipmentAgg,
    'types': [30000, 30100],
    'columns': [{ 'name': '设备', 'types': [30000] }, { 'name': '仪器', 'types': [30100] }]
  },
  '31': {
    'name': '研发材料',
    'rdType': '20000',
    node: {
      '31': { 'name': '研发材料', 'component': MaterialAgg, 'types': [20000] },
      '33': { 'name': '造纸材料', 'component': MaterialAgg, 'types': [20001] },
      '35': { 'name': '流程型', 'component': MaterialAgg, 'types': [20002] }
    }
  },
  '32': {
    'name': '中间试制',
    'rdType': '20300',
    node: {
      '32': { 'name': '试制材料', 'component': MaterialAgg, 'types': [20301] },
      '34': { 'name': '造纸试制', 'component': MaterialAgg, 'types': [20303] },
      '36': { 'name': '流程型', 'component': MaterialAgg, 'types': [20304] },
      '37': { 'name': '试制动力', 'component': EnergyAgg, 'types': [20302], 'type': 20100 },
      '65': { 'name': '其他试制', 'component': InspectionAgg, 'types': [20300] }
    }
  },
  '38': {
    'name': '修理费用',
    'rdType': '20600',
    node: {
      '38': { 'name': '修理材料', 'component': MaterialAgg, 'types': [20601] },
      '61': { 'name': '凭证费用', 'component': InspectionAgg, 'types': [20600] }
    }
  },
  '39': { 'name': '样机费用', 'rdType': '20700', 'component': InspectionAgg, 'types': [20700] },
  '40': {
    'name': '动力损耗',
    'rdType': '20100',
    node: {
      '40': { 'name': '研发动力', 'component': EnergyAgg, 'types': [20100], 'type': 20100 },
      '43': { 'name': '流程型', 'component': EnergyAgg, 'types': [20102], 'type': 20100 },
      '41': { 'name': '设备动力', 'component': EquipmentPowerAgg, 'types': [20101] }
    }
  },
  '42': {
    'name': '燃料损耗',
    'rdType': '20200',
    node: {
      '42': { 'name': '研发燃料', 'component': EnergyAgg, 'types': [20200], 'type': 20200 },
      '44': { 'name': '流程型', 'component': EnergyAgg, 'types': [20201], 'type': 20200 }
    }
  },
  '50': { 'name': '设计费用', 'rdType': '50000', 'component': DesignAgg, 'types': [50000] },
  '59': { 'name': '检测费用', 'rdType': '20500', 'component': InspectionAgg, 'types': [20500] },
  '62': { 'name': '差旅费用', 'rdType': '60400', 'component': InspectionAgg, 'types': [60400] },
  '63': {
    'name': '摊销费用',
    'rdType': '40000',
    'types': [40000, 40100, 40200],
    node: {
      '63': {
        'name': '凭证费用',
        'component': InspectionAgg,
        'types': [40000, 40100, 40200],
        'columns': [
          { 'name': '软件摊销', types: [40000] },
          { 'name': '专利摊销', types: [40100] },
          { 'name': '其他摊销', types: [40200] }
        ]
      },
      '64': { 'name': '资产摊销', 'component': RdEquipmentAgg, 'types': [40001] }
    }
  },
  '66': {
    'name': '其他费用',
    'rdType': '69900',
    'component': InspectionAgg,
    'types': [69900, 60000, 60100, 60200, 60300],
    'columns': [
      { 'name': '资料', types: [60000] },
      { 'name': '研发成果', types: [60100] },
      { 'name': '知识产权', types: [60200] },
      { 'name': '福利', types: [60300] },
      { 'name': '其他', types: [69900] }
    ]
  }
  // 最大值 ：67，下一个值，68
}
function getNode (k) {
  if (keyTypeMap[k] && keyTypeMap[k].component) {
    return keyTypeMap[k]
  }
  for (const key in defaultNodeCheck) {
    if (keyTypeMap[key].node[k]) {
      return keyTypeMap[key].node[k]
    }
  }
  return keyTypeMap['10']
}
const defaultNodeCheck = {
  '31': ['31'],
  '32': ['32'],
  '40': ['40'],
  '42': ['42'],
  '38': ['38'],
  '63': ['63']
}
export default {
  mixins: [yearMiXin],
  name: 'DataAggregation',
  components: {
    ExportDataModal,
    PageView,
    'AggTab': {
      functional: true,
      render: function (createElement, context) {
        const d = context.data
        return createElement(
          getNode(d.attrs.type).component,
          context.data,
          context.children
        )
      }
    }
  },
  data () {
    return {
      isMsUser: false,
      keyTypeMap: keyTypeMap,
      rdSwitch: false,
      ryear: this.$store.state.currentYear,
      collapsed: false,
      selectKey: ['10'],
      month: '',
      projectList: [],
      projectId: undefined,
      selectProjectId: '0',
      selectProject: {},
      project: {},
      rdFundsMap: {},
      allRdDataMap: {},
      allRdColumns: [],
      yearSelectOption: [],
      projectMap: {},
      errorMsg: '',
      statusColor,
      statusMap: {},
      newProject: false,
      isShowSecondaryMenu: false,
      nodeCheck: {},
      nodekey: 'selectNode',
      title: null,
      visible: false,
      spinning: false
    }
  },
  mounted () {
    document.addEventListener('click', e => {
      const btn = this.$refs.btn
      if (!btn) return
      if (!btn.contains(e.target)) {
        this.isShowSecondaryMenu = false // 点击其他区域关闭
      }
    })
  },
  computed: {
    rdTotalProjectList: function () {
      return [...this.projectList, { 'rdTitle': -1 }]
    },
    rdColumns: function () {
      if (this.selectNode.columns) {
        return this.selectNode.columns
      } else {
        return [this.selectNode]
      }
    },
    months: function () {
      if (this.selectProject == null) {
        return []
      }
      var endDate = this.selectProject.endDate
      var beginDate = this.selectProject.beginDate
      const cm = this.selectNode

      if (typeof endDate === 'undefined' || typeof beginDate === 'undefined') {
        return []
      }
      const beginYear = Number(beginDate.substr(0, 4))
      if (this.currentYear < beginYear) {
        return []
      }
      const endYear = Number(endDate.substr(0, 4))
      var beginMonth = 1
      if (this.currentYear === beginYear) {
        beginMonth = Number(beginDate.substr(5, 2))
      }
      var maxMonth = 12
      if (this.currentYear === endYear) {
        maxMonth = Number(endDate.substr(5, 2))
      }
      const returnArry = []
      const isTrail = this.selectProject.trialProd && (cm.name === '中间试制' || cm.name === '试制动力' || cm.name === '其他试制' || cm.name === '造纸试制' || (cm.types && cm.types[0] === 20304) || cm.name === '试制材料')
      var trialBegin = beginMonth
      var trialEnd = maxMonth
      if (isTrail) {
        const trialYear = Number(this.selectProject.tBeginDate.substr(0, 4))
        if (this.currentYear < trialYear) {
          trialBegin = 0
          trialEnd = 0
        } else {
          if (this.currentYear === trialYear) {
            trialBegin = Number(this.selectProject.tBeginDate.substr(5, 2))
          }
          if (this.currentYear === Number(this.selectProject.tEndDate.substr(0, 4))) {
            trialEnd = Number(this.selectProject.tEndDate.substr(5, 2))
          }
        }
      }
      for (; beginMonth <= maxMonth; beginMonth++) {
        const n = `${this.currentYear}-${(beginMonth + '').padStart(2, '0')}`
        if (isTrail) {
          if (beginMonth >= trialBegin && beginMonth <= trialEnd) {
            returnArry.push(n)
          } else if (Number(this.getRdTypeFunds(this.selectKey, n)) !== 0) {
            returnArry.push(n)
          }
        } else {
          returnArry.push(n)
        }
      }
      return returnArry
    },
    monthDate: function () {
      return this.getMonthDate(this.month)
    },
    selectNode () {
      if (this.nodeCheck[this.selectKey]) {
        return getNode(this.nodeCheck[this.selectKey])
      }
      return getNode(this.selectKey)
    }
  },
  methods: {
    canModify,
    show (projectData, selectKey) {
      this.spinning = true
      this.visible = true
      this.selectKey = Object.keys(keyTypeMap).filter(elem => (keyTypeMap[elem].types && keyTypeMap[elem].types.includes(selectKey)) || (String(keyTypeMap[elem].rdType).slice(0, 3) === String(selectKey).slice(0, 3)))
      const name = keyTypeMap[this.selectKey].name
      this.title = `${name}`
      this.nodekey = 'selectNode'
      this.isMsUser = isMsUser()
      const companyId = this.$store.getters.companyId
      if (companyId) {
        this.nodekey += ':' + companyId
      }
      if (localStorage && localStorage.getItem(this.nodekey)) {
        this.nodeCheck = JSON.parse(localStorage.getItem(this.nodekey))
      } else {
        this.nodeCheck = defaultNodeCheck
      }
      this.onYearChange()
      this.projectId = Number(`${projectData.id}`.split('-')[0])
      // this.handleActiveKey()
      if (!projectData.hasChild) {
        this.month = `${this.currentYear}-${projectData.month.slice(0, -2).length === 2 ? projectData.month.slice(0, -2) : '0' + projectData.month.slice(0, -2)}`
      } else {
        this.month = this.months[0]
      }
      this.spinning = false
    },
    onClose () {
      this.visible = false
    },
    showSecondaryMenu () {
      this.isShowSecondaryMenu = !this.isShowSecondaryMenu
    },
    isTrial (v, opposite) {
      const p = this.selectProject
      if ((v.name === '中间试制' || v.name === '试制动力' || v.name === '其他试制' || v.name === '造纸试制' || (v.types && v.types[0] === 20304) || v.name === '试制材料')) {
        if (p.trialProd) {
          const beginYear = Number(p.tBeginDate.substr(0, 4))
          const endYear = Number(p.tEndDate.substr(0, 4))
          return opposite ? !(this.currentYear <= endYear && this.currentYear >= beginYear) : (this.currentYear <= endYear && this.currentYear >= beginYear)
        }
        return opposite
      }
      return false
    },
    search () {
      this.selectedRowKeys = []
      this.selectedRows = []
      this.onYearChange()
    },
    getMonthDate (m) {
      return `${m}-01 00:00:00`
    },
    tabChange (activeKey) {
      this.month = activeKey.replace(this.nodeCheck[this.selectKey] ? this.nodeCheck[this.selectKey] : this.selectKey, '')
      if (this.rdSwitch) {
        this.getRdSummaryByMonth()
      }
    },
    menuClick (e, node) {
      if (node) {
        this.nodeCheck[this.selectKey] = [e.key]
        if (localStorage) {
          localStorage.setItem(this.nodekey, JSON.stringify(this.nodeCheck))
        }
      }
      this.isShowSecondaryMenu = false
      if (this.rdSwitch) {
        this.getRdSummaryByMonth()
      }
      if (this.isTrial(this.selectNode, false)) {
        const m = Number(this.month.substr(5, 2))
        const beginM = Number(this.selectProject.tBeginDate.substr(5, 2))
        const endM = Number(this.selectProject.tEndDate.substr(5, 2))
        if (m > endM || m < beginM) {
          this.month = this.selectProject.tBeginDate.substr(0, 7)
        }
      } else {
        if (Number(this.month.substr(0, 4)) !== this.currentYear) {
          this.month = this.months[0]
        }
      }
    },
    getAllRdFunds (rdTitle, rdTypes) {
      let r = 0
      if (rdTitle === -1) {
        for (let j = 0; j < this.projectList.length; j++) {
          const e = this.projectList[j]
          if (this.allRdDataMap[e.rdTitle]) {
            for (let i = 0; i < rdTypes.length; i++) {
              const t = rdTypes[i]
              if (this.allRdDataMap[e.rdTitle][t]) {
                r += this.allRdDataMap[e.rdTitle][t]
              }
            }
          }
        }
      } else {
        if (this.allRdDataMap[rdTitle]) {
          for (let i = 0; i < rdTypes.length; i++) {
            const t = rdTypes[i]
            if (this.allRdDataMap[rdTitle][t]) {
              r += this.allRdDataMap[rdTitle][t]
            }
          }
        }
      }
      return r.toFixed(2)
    },
    getRdSummaryByMonth () {
      this.$http.get('/aggregation/getRdSummaryByMonth', {
        params: { rdYear: this.currentYear, rdMonth: this.monthDate, rdTypes: this.selectNode.types }
      })
        .then(res => {
          const retData = res.data
          this.allRdDataMap = {}
          for (let index = 0; index < retData.length; index++) {
            const element = retData[index]
            if (typeof this.allRdDataMap[element.rdTitle] === 'undefined') {
              this.allRdDataMap[element.rdTitle] = {}
            }
            this.allRdDataMap[element.rdTitle][element.rdType] = element.rdFunds
          }
        })
    },
    rdSwitchChange (checked) {
      this.rdSwitch = checked
      if (this.rdSwitch) {
        this.getRdSummaryByMonth()
      }
    },
    refreshSummary () {
      this.getRdFunds()
      if (this.rdSwitch) {
        this.getRdSummaryByMonth()
      }
      this.$emit('refresh', false)
    },
    moment,
    getRdTypeFunds (key, month, root) {
      var arrType = []
      if (root && keyTypeMap[key].node) {
        for (const k in keyTypeMap[key].node) {
          arrType.push(...keyTypeMap[key].node[k].types)
        }
      } else {
        arrType = getNode(key).types
      }
      let retValue = 0
      for (let index = 0; index < arrType.length; index++) {
        const t = arrType[index]
        if (this.rdFundsMap[t]) {
          if (month) {
            retValue += this.rdFundsMap[t][month] ? this.rdFundsMap[t][month] : 0
          } else {
            for (const item in this.rdFundsMap[t]) {
              retValue += this.rdFundsMap[t][item]
            }
          }
        }
      }
      return retValue.toFixed(2)
    },
    getTabTitle (n, selectKey) {
      return `${n}[${this.getRdTypeFunds(selectKey, n).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')}]`
    },
    getRdFunds () {
      this.$http.get('/aggregation/getRdFunds', { params: { projectId: this.projectId, year: this.currentYear } })
        .then(res => {
          const rdData = res.data.rdFunds
          this.rdFundsMap = {}
          this.statusMap = {}
          for (let i = 0; i < rdData.length; i++) {
            const rdItem = rdData[i]
            if (typeof this.rdFundsMap[rdItem.rdType] === 'undefined') {
              this.rdFundsMap[rdItem.rdType] = {}
            }
            this.rdFundsMap[rdItem.rdType][rdItem.month.substr(0, 7)] = rdItem.rdFunds
          }
          const statusData = res.data.status
          const temp = {}
          for (const k in statusData) {
            const key = k.replace(',', '')
            temp[key] = statusData[k]
          }
          this.statusMap = temp
        })
    },
    getStatusInfo (rdType, m) {
      return this.statusMap[`${m}_${rdType}`] || {}
    },
    OnProjectChange (value) {
      this.projectId = value
      this.selectProject = this.project[value]
      if (this.projectId === undefined) {
        return
      }
      if (this.months.length > 0 && this.months.indexOf(this.month) === -1) {
        this.month = this.months[0]
      }
      this.newProject = true
      this.getRdFunds()
      const v = this.keyTypeMap[this.selectKey]
      if (this.isTrial(v, false)) {
        this.month = this.months[0]
        // this.$message.error('该项目当前年无试制')
      } else if (v.name === '中间试制' || v.name === '试制动力' || v.name === '其他试制' || v.name === '造纸试制' || v.name === '试制材料' || (v.types && v.types[0] === 20304)) {
        this.selectKey = ['10']
      }
    },
    onYearChange () {
      if (!this.currentYear) {
        return
      }
      this.$http.get('/project/getSelectList', { params: { year: this.currentYear, sign: 2 } })
        .then(res => {
          if (res.success && res.data !== null && res.data.length > 0) {
            this.projectList = res.data
            if (this.projectList.length > 0) {
              this.projectList.map(item => {
                this.project[item.id] = item
              })
              this.errorMsg = ''
            } else {
              this.projectId = undefined
              this.project = {}
              this.errorMsg = '当前年份：' + this.currentYear + '没有项目，请重新选择年份'
            }
          } else {
            this.projectList = []
            this.projectId = undefined
            this.project = {}
            // this.$message.warning('当前年份：' + this.currentYear + '没有项目，请重新选择年份')
            this.errorMsg = '当前年份：' + this.currentYear + '没有项目，请重新选择年份'
          }
          this.OnProjectChange(this.projectId)
          return res.data
        })
    },
    /**
     * @description: 生成Tab的激活Key
     * @param {Object} nodeCheck
     * @param {Array} selectKey
     * @param {String} month
     * @param {Array} months
     * @return {String}
     */
    handleActiveKey (nodeCheck, selectKey, month, months) {
      if (!nodeCheck) { nodeCheck = selectKey }
      month = months.includes(month) ? month : months[0]
      return nodeCheck + month
    }
  }
}
</script>

<style lang="less" scoped>
@FH: 100%;
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

.ant-layout {
  background-color: rgba(255, 255, 255, 0) !important;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
.customBtn {
  border-radius: 5px;
  position: absolute;
  top: -22px;
  left: -22px;
  z-index: 10;
  color: #000;
  background-color: #fff;
}
.customBtn :hover {
  border-radius: 5px;
  color: #fff;
  background-color: #40a9ff;
  cursor: pointer;
}
.showSider {
  position: absolute;
  top: 0;
  left: -21px;
  z-index: 10;
}
.hidSider {
  position: absolute;
  top: 0;
  left: -222px;
  z-index: 10;
}
#coreContent {
  height: calc(100% - 39px);
  overflow: auto;
  /deep/ .ant-collapse-header {
    line-height: 18px;
    padding: 6px 0 6px 40px;
    /deep/ .arrow {
      line-height: 34px;
    }
  }
  .cost {
    padding-top: 6px;
    padding-bottom: 6px;
    display: flex;
    justify-content: center;
    b {
      margin-right: 8px;
    }
  }
  #tabs {
    height: @FH;
    width: 100%;
    /deep/ .ant-tabs-content {
      height: @FH;
    }
    /deep/ .ant-tabs-tabpane-active {
      height: calc(@FH - 60px);
      overflow: auto;
    }
  }
  #secondaryMenu {
    border-radius: 5px;
    border-width: 1px;
    border-style: solid;
    border-color: #d9dddf;
    /deep/ .customBackground {
      background-color: #f8f8f9;
    }
  }
}
</style>
