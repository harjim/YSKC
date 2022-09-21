<template>
  <a-modal
    :title="title"
    :visible="visible"
    :width="1300"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    :footer="null"
    @cancel="closeModal"
  >
    <div style="min-height: 500px;">
      <a-spin :spinning="spinning">
        <a-form layout="inline">
          <a-form-item label="姓名">
            <a-input placeholder="请输入姓名" v-model="queryParams.ename" style="width:165px" />
          </a-form-item>
          <a-form-item label="工号">
            <a-input placeholder="请输入工号" v-model="queryParams.enumber" style="width:165px" />
          </a-form-item>
          <a-form-item label="人员类型">
            <a-select
              :allowClear="true"
              v-model="queryParams.etype"
              placeholder="请选择人员类型"
              default-value="-1"
              style="width: 165px"
            >
              <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item>
          <span>
            <a-button @click="search(true)" type="primary">查询</a-button>
          </span>
          <span style="padding-left:10px;" v-if="canModify">
            <a-button @click="$refs.uploadModal.show(tableField)" type="primary">导入</a-button>
          </span>
          <span style="padding-left:10px;">
            <a-button @click="exportData(false)" type="primary">导出</a-button>
          </span>
          <span style="padding-left:10px;" v-if="activeKey !== 'timeModal'">
            <a-button @click="exportData(true)" type="primary">导出所有RD</a-button>
          </span>
        </a-form>
        <!-- <a-tabs
          type="card"
          tabPosition="right"
          :animated="true"
          :activeKey="activeKey"
          @change="tabChange"
        >
        <a-tab-pane tab="工时" key="hourModal">-->
        <div v-show="activeKey === 'hourModal'">
          <attendance-hour-modal
            @modalChange="modalChange"
            :canModify="canModify"
            :queryParams="queryParams"
            ref="hourModal"
          />
        </div>
        <!-- </a-tab-pane>
        <a-tab-pane tab="时间" key="timeModal">-->
        <div v-show="activeKey === 'timeModal'">
          <attendance-time-modal
            @modalChange="modalChange"
            :queryParams="queryParams"
            :month="month"
            :projectId="projectId"
            ref="timeModal"
          />
        </div>
        <!-- </a-tab-pane>
        </a-tabs>-->
      </a-spin>
    </div>
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="paramData"
      paramKey="name"
      :title="`导入${month}人员研发工时记录`"
      ref="uploadModal"
      action="/doc/projectAttendance/importRdAttendance"
      :templateName="`${month}人员研发工时记录模板`"
      @onSuccess="success"
    />
  </a-modal>
</template>

<script>
import { UploadModal } from '@/components'
import { mapGetters } from 'vuex'
import AttendanceHourModal from './AttendanceHourModal'
import AttendanceTimeModal from './AttendanceTimeModal'
import moment from 'moment'
const defaultField = {
  rdTitle: { title: 'RD', defaultTitle: 'RD', importField: true, required: true },
  enumber: { title: '工号', defaultTitle: '工号', importField: true, required: true },
  ename: { title: '姓名', defaultTitle: '姓名', importField: true, required: true }
}
export default {
  components: {
    AttendanceHourModal,
    AttendanceTimeModal,
    UploadModal
  },
  name: 'AttendanceModal',
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    month: {
      type: String,
      default: ''
    },
    rdTitle: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      activeKey: 'hourModal',
      spinning: false,
      title: '设置研发比例',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      queryParams: {},
      canModify: true,
      paramData: {},
      tableField: {
        tableId: `projectAttendanceTable`,
        fieldTitleObject: {}
      },
      sampleData: [ {
        rdTitle: '2022RD01',
        enumber: 'A-01',
        ename: '张三',
        etype: '-1',
        d1: '8',
        d2: '8',
        d3: '4',
        d5: '6'
      }]
    }
  },
  methods: {
    moment,
    showModal (month, canModify) {
      this.canModify = canModify
      this.activeKey = 'hourModal'
      this.queryParams = {
        month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'),
        projectId: this.projectId
      }
      this.paramData = { projectId: this.projectId, month: this.month + '-01 00:00:00' }
      this.title = '[' + month.format('YYYY年MM月') + '】研发考勤'
      this.visible = true
      this.search(true, this.queryParams)
      this.loadTableField(this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'))
    },
    loadTableField (month) {
      const monthDayField = {}
      if (month) {
        const maxDay = month.endOf('month').date()
        let titleV
        for (let i = 1; i <= maxDay; i++) {
          titleV = i + ''
          monthDayField[`d${titleV}`] = { title: titleV, defaultTitle: titleV, importField: true }
        }
      }
      this.tableField.fieldTitleObject = Object.assign({ rdTitle: defaultField.rdTitle, enumber: defaultField.enumber, ename: defaultField.ename, etype: defaultField.etype }, monthDayField)
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {
          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.search(true)
    },
    closeModal () {
      this.visible = false
    },
    ...mapGetters(['userInfo']),
    exportData (all) {
      this.queryParams.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      this.queryParams.projectId = this.projectId
      if (this.activeKey === 'timeModal') {
        this.exportTimeData()
      } else {
        this.exportHourData(all)
      }
    },
    exportTimeData () {
      this.$http.get('/projectAttendance/exportTimeData', { params: this.queryParams })
        .then(res => {
          if (res.data && res.data.length > 0) {
            const lastTitles = ['工号', '姓名', '部门', '人员类型', '出勤日期', '工时', '上班时间1', '下班时间1', '上班时间2', '下班时间2', '上班时间3', '下班时间3']
            const colWidthList = [15, 15, 15, 15, 10, 10, 10, 10, 10, 10, 10]
            const exportData = res.data
            const lastKeys = ['enumber', 'ename', 'deptName', 'etypeName', 'workDate', 'workHour', 'onTime1', 'offTime1', 'onTime2', 'offTime2', 'onTime3', 'offTime3']
            const datas = [{ sheetHeader: lastTitles, sheetFilter: lastKeys, sheetData: exportData, columnWidths: colWidthList, sheetName: `${this.month}研发时间` }]
            this.$exportJson(`${this.month}研发考勤时间`, datas)
          } else {
            this.$message.info('当前无可导出的数据。')
          }
        })
    },
    exportHourData (all) {
      // this.$http.get('/projectAttendance/exportHourData', { params: Object.assign(this.queryParams, { all: all }) })
      //   .then(res => {
      //     const multiHeader = [[this.userInfo().companyName, '', '', '', ''], [`${this.month}研发技术人员工作打卡记录表`, '', '', '', '']]
      //     if (res.data && res.data.length > 0) {
      //       const lastKeys = ['rdTitle', 'enumber', 'ename', 'deptName', 'rdHour']
      //       const lastTitles = ['RD', '工号', '姓名', '部门', '研发工时']
      //       const arr = this.$getMonthDay(this.month)
      //       const colWidthList = [15, 15, 15, 15, 15]
      //       arr.forEach(m => {
      //         colWidthList.push(8)
      //         lastKeys.push(m.index)
      //         lastTitles.push(m.index)
      //         multiHeader.map(merge => {
      //           merge.push('')
      //         })
      //       })

      //       const exportData = []
      //       res.data.forEach(item => {
      //         const d = { rdTitle: item.rdTitle, enumber: item.enumber, ename: item.ename, deptName: item.deptName, rdHour: item.rdHour }
      //         if (item.dayHour) {
      //           for (const k in item.dayHour) {
      //             d[k] = item.dayHour[k]
      //           }
      //         }
      //         exportData.push(d)
      //       })
      //       multiHeader.push(lastTitles)
      //       const merges = [`A1:${this.$getExcelHeadTitle(lastKeys.length - 1)}1`, `A2:${this.$getExcelHeadTitle(lastKeys.length - 1)}2`]
      //       // const datas = [{ sheetHeader: lastTitles, sheetFilter: lastKeys, sheetData: exportData, columnWidths: colWidthList, sheetName: `${this.month}研发工时` }]
      //       // this.$exportJson(`${this.month}研发考勤工时`, datas)
      //       this.$exportJsonData(`${!all ? this.rdTitle + '-' : this.userInfo().companyName}${this.month}研发考勤工时`,
      //         [],
      //         lastKeys,
      //         exportData,
      //         colWidthList,
      //         merges,
      //         [],
      //         multiHeader)
      //     } else {
      //       this.$message.info('当前无可导出的数据。')
      //     }
      //   })
      this.$exportData('/projectAttendance/exportHourData',
        Object.assign(this.queryParams, { all: all }),
        `${!all ? this.rdTitle + '-' : this.userInfo().companyName}${this.month}研发考勤工时.xlsx`, this.$message)
    },
    modalChange (key) {
      this.activeKey = key
      this.search(true)
    },
    search (refresh, queryParams) {
      if (this.$refs[this.activeKey]) {
        this.$refs[this.activeKey].search(refresh, queryParams)
      }
    }
  }
}
</script>

<style>
.time-enter-active,
.time-leave-active,
.hour-enter-active,
.hour-leave-active {
  transition: all 0.9s;
}
.time-enter,
.time-leave-active,
.hour-enter,
.hour-leave-active {
  opacity: 0;
}
.hour-enter {
  transform: translateX(40px);
}
.hour-leave-active {
  transform: translateX(40px);
}

.time-enter {
  transform: translateX(40px);
}
.time-leave-active {
  transform: translateX(40px);
}

#maxBox {
  position: relative;
  overflow: auto;
}
.transtionFloat {
  position: absolute;
  width: 100%;
}
.clearfix {
  *zoom: 1;
}
.clearfix:after {
  display: bolck;
  content: '';
  clear: both;
  visibility: hidden;
  height: 0;
}

#transionConetent {
  position: relative;
}
</style>
