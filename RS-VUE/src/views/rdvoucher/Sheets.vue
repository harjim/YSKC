<template>
  <a-card :bodyStyle="{ height: '100%', overflow: 'auto'}" style="height: 100%;">

    <a-form layout="inline">
      <a-form-item label="项目" >
        <a-select style="width:400px" :value="projectId" @change="OnProjectChange">
          <a-select-option v-for="item in projectList" :key="item.id">
            <span
              v-if="item!=null"
            >{{ item.rdTitle }} - {{ item.pname }}</span>
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="月份" v-if="selectProject!=null">
        <a-month-picker
          format="YYYY-MM"
          v-model="queryParam.month"
          placeholder="请选择月份"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" v-if="$auth('rdvoucher:sheets:search')" @click="searchData">查询</a-button>
      </a-form-item>
      <a-alert
        v-if="errorMsg!==''"
        :message="errorMsg"
        type="warning"
        style="width:400px;margin-left:60px"
      />
    </a-form>
    <div v-if="selectProject!=null" id="scrollContent">
      <a-spin tip="请稍后..." :spinning="spinning" style="height: 100%">
        <vxe-grid
          ref="table"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          :data="datas"
          :toolbar="{
            custom: true,
            zoom:true,
            refresh:{query:searchData}
          }"
        >
          <template v-slot:buttons>
            <span>
              <a-button
                v-if="selectProject!=null&&$auth('rdvoucher:sheets:export')"
                type="primary"
                @click="exportData"
              >导出</a-button>
            </span>
          </template>
          <vxe-table-column
            title="年"
            field="yearStr"
            key="yearStr"
            :width="100"
            show-header-overflow
            show-overflow="tooltip"
            align="left"
          >
            <template v-slot="{ row }">
              <span v-if="!row.sum">{{ row.yearStr }}</span>
              <span v-else></span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="月"
            field="monthStr"
            key="monthStr"
            :width="100"
            show-header-overflow
            show-overflow="tooltip"
            align="left"
          >
            <template v-slot="{ row }">
              <span v-if="!row.sum">{{ row.monthStr }}</span>
              <span v-else></span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="日"
            field="dayStr"
            key="dayStr"
            :width="100"
            show-header-overflow
            show-overflow="tooltip"
            align="left"
          >
            <template v-slot="{ row }">
              <span v-if="!row.sum">{{ row.dayStr }}</span>
              <span v-else></span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="凭证号"
            field="workNo"
            key="workNo"
            :width="200"
            show-header-overflow
            show-overflow="tooltip"
            align="left"
          >
            <template v-slot="{ row }">
              <span v-if="!row.sum&&row.workNo&&$auth('rdvoucher:sheets:edit')">
                <a @click="(e)=>openModal(e,row)">{{ row.workNo }}</a>
              </span>
              <span v-else>{{ row.workNo }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="摘要"
            field="remark"
            key="remark"
            :width="150"
            show-header-overflow
            show-overflow="tooltip"
            align="left"
          >
            <template v-slot="{ row }">
              <span v-if="!row.sum">{{ remarkMap[row.rdType] }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="科目"
            field="accountName"
            key="accountName"
            min-width="250"
            show-header-overflow
            show-overflow="tooltip"
            align="center"
          >
            <template v-slot="{ row }">
              <span v-if="$auth('rdvoucher:sheets:edit')">
                <a-tree-select
                  v-if="!row.sum"
                  style="width:220px"
                  v-model="row.accountName"
                  :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                  :treeData="accountTree"
                  placeholder="选择科目"
                  treeNodeFilterProp="title"
                  treeDefaultExpandAll
                  @change="(value)=>onTreeChange(value,row)"
                ></a-tree-select>
              </span>
              <span v-else>{{ row.accountName }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="借方金额"
            field="debitAmount"
            key="debitAmount"
            :width="200"
            show-header-overflow
            show-overflow="tooltip"
            align="right"
          >
            <template v-slot="{ row }">
              <span>{{ row.debitAmount | MoneyFormat }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="贷方金额"
            field="creditAmount"
            key="creditAmount"
            :width="200"
            show-header-overflow
            show-overflow="tooltip"
            align="right"
          >
            <template v-slot="{ row }">
              <span>{{ row.creditAmount | MoneyFormat }}</span>
            </template>
          </vxe-table-column>
        </vxe-grid>
      </a-spin>
    </div>
    <sheets-modal ref="sheetsModal" @ok="searchData"></sheets-modal>
  </a-card>
</template>

<script>
import { PageView } from '@/layouts'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import SheetsModal from './modules/SheetsModal'
import ystable from '@/components/Table/ystable'
export default {
  mixins: [yearMiXin],
  name: 'Sheets',
  components: {
    PageView,
    SheetsModal,
    ystable
  },
  watch: {
    projectId (newId) {
      this.searchData()
    }
  },
  mounted () {
    this.loadTree()
  },
  data () {
    return {
      accountTree: [],
      types: [10000, 10001, 10101, 10102, 10103, 10104, 10105, 10106, 30000, 30100, 20000, 20301, 20601, 20100, 20200, 50000, 20500, 20600, 60400, 40000, 40100, 40200, 20300, 69900],
      remarkMap: {
        10000: '工资费用',
        10001: '奖金费用',
        10100: '五险一金费用',
        30000: '设备费用',
        20000: '研发材料费用',
        20301: '中间试制费用',
        20601: '修理材料费用',
        20100: '动力费用',
        20200: '燃料费用',
        50000: '设计费用',
        20500: '检测费用',
        20600: '修理费用',
        60400: '差旅费用',
        40000: '摊销费用',
        20300: '其他试制',
        69900: '其他费用'
      },
      datas: [],
      spinning: false,
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
      queryParam: { month: undefined, queryDate: moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]), defaultPickerValue: moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]) },
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
    getParam () {
      this.queryParam.projectId = this.projectId
      this.queryParam.types = this.types
      return this.queryParam
    },
    openModal (e, record) {
      var myDatas = this.datas.filter(a => a.baseWorkNo === record.baseWorkNo)
      this.$refs.sheetsModal.show(myDatas, record)
    },
    onTreeChange (value, record) {
      this.$http.post('/workSheet/saveVoucherAccount', {
        projectId: record.projectId,
        rdType: record.rdType,
        month: record.month,
        accountId: value
      })
        .finally(res => {
          this.$emit('ok', true)
        })
    },
    loadTree () {
      this.$getTree('account')
        .then(res => {
          this.accountTree = res.tree
        })
    },
    exportData () {
      const exportData = this.$deepClone(this.datas)
      const fileName = `${exportData[0].rdIndexStr}-${this.queryParam.queryDate.format('YYYY')}-费用凭证`
      exportData[exportData.length - 1].yearStr = null
      exportData[exportData.length - 1].monthStr = null
      exportData[exportData.length - 1].dayStr = null
      this.$exportJsonData(fileName,
        ['年', '月', '日', '凭证号', '摘要', '科目', '借方金额', '贷方金额'],
        ['yearStr', 'monthStr', 'dayStr', 'workNo', 'remark', 'accountName', 'debitAmount', 'creditAmount'],
        exportData,
        [5, 5, 5, 20, 20, 20, 12, 12])
    },
    searchData () {
      if (this.projectId === 0) {
        return
      }
      this.spinning = true
      this.$http.get('/workSheet/getWorkVoucherList', { params: { month: this.queryParam.month, workDate: this.queryParam.month, projectId: this.projectId, types: this.types } })
        .then(res => {
          if (res.success) {
            this.datas = res.data
            if (this.datas && this.datas.length > 0) {
              this.disabled = false
            } else {
              this.disabled = true
            }
          } else {
            this.disabled = true
          }
          return res
        }).finally(res => {
          this.spinning = false
        })
    },
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
      this.$http.get('/project/getSelectList', { params: { year: value, sign: 1 } })
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
#scrollContent {
  // border: 1px solid #e3e3e3;
  // border-radius: 8px;
  margin-top: 12px;
  height: calc(100% - 51px);
  overflow: auto;
}
</style>
