<template>
  <a-spin
    :spinning="spinning"
    tip="请稍后..."
    style="height: 100%; overflow:auto"
  >
    <a-card
      v-if="$auth('project:rdHour:search')"
      :bordered="false"
      style="height: 100%; overflow:auto"
      :bodyStyle="{ height: '100%', overflow: 'auto'}"
    >
      <a-form layout="inline" >
        <a-form-item label="项目">
          <a-tree-select
            v-model="projectIds"
            style="width: 350px;"
            :dropdownStyle="{ maxHeight: '420px', maxWidth: '350px' }"
            tree-default-expand-all
            tree-checkable
            :show-checked-strategy="SHOW_PARENT"
            :maxTagCount="3"
            placeholder="请选择项目"
          >
            <a-tree-select-node
              title="所有项目"
              :value="-1"
              key="-1"
              v-if="projects && projects.length"
            >
              <a-tree-select-node
                v-for="p in projects"
                :key="p.id"
                :value="p.id"
                :title="`${p.rdTitle}-${p.pname}`"
              />
            </a-tree-select-node>
          </a-tree-select>
        </a-form-item>
        <a-form-item label="姓名">
          <a-input placeholder="请输入姓名" v-model="queryParams.ename"></a-input>
        </a-form-item>
        <a-form-item label="月份">
          <a-select
            style="width:120px;"
            v-model="monthValue"
            placeholder="请选择月份"
            :options="monthOptions"
          ></a-select>
        </a-form-item>
        <a-form-item><a-button type="primary" @click="query(true)">查询</a-button></a-form-item>
      </a-form>
      <div v-if="projects && projects.length" style="height: calc(100% - 39px); overflow: auto;">
        <div v-show="activeKey === 'hourModal'">
          <ystable
            border
            highlight-hover-row
            show-overflow
            show-header-overflow
            ref="hourModal"
            queryUrl="/projectAttendance/getAllRdHour"
            :params="queryParams"
            :toolbar="{zoom: true,custom:true,refresh:true}"
          >
            <template v-slot:buttons>
              <span>
                <a-button
                  type="primary"
                  @click="change('timeModal')"
                >切换时间</a-button>
              </span>
              <span style="padding-left:10px;">
                <a-button
                  type="primary"
                  @click="exportHour"
                  v-if="$auth('project:rdHour:export')"
                >导出</a-button>
              </span>
            </template>
            <vxe-table-column
              type="seq"
              :width="50"
              fixed="left"
              title="序号"
            />
            <vxe-table-column
              title="RD"
              field="rdTitle"
              fixed="left"
              :width="86"
            />
            <vxe-table-column
              title="项目名称"
              field="pname"
              fixed="left"
              :min-width="180"
            />
            <vxe-table-column
              align="center"
              title="月份"
              field="month"
              :width="110"
            />
            <vxe-table-column
              title="工号"
              field="enumber"
              :width="120"
            />
            <vxe-table-column
              title="姓名"
              field="ename"
              :width="120"
            />
            <vxe-table-column
              align="right"
              title="计划工时"
              field="planTime"
              :width="100"
            />
            <vxe-table-column
              align="right"
              title="合计"
              field="totalHour"
              :width="100"
            >
              <template v-slot="{row}">
                <span :style="{color: getCompareColor(row)}">{{ getTotalHour(row.info) }}</span>
              </template>
            </vxe-table-column>
            <template v-for="item in monthMaxDay">
              <vxe-table-column
                :key="item"
                align="center"
                :width="60"
                :title="`${item}`"
              >
                <template v-slot="{row}">
                  <span v-if="row.info[item]">{{ row.info[item].workHour }}</span>
                  <span v-else>-</span>
                </template>
              </vxe-table-column>
              <template v-if="hadContent(item)">
                <vxe-table-column
                  :key="'content'+item"
                  width="100"
                  title="工作内容"
                >
                  <template v-slot="{row}">
                    {{ getContent(row.info,item) }}
                  </template>
                </vxe-table-column>
              </template>
            </template>
          </ystable>
        </div>
        <div v-show="activeKey === 'timeModal'">
          <ystable
            border
            highlight-hover-row
            show-overflow
            show-header-overflow
            ref="timeModal"
            queryUrl="/projectAttendance/getAllRdTime"
            @checkbox-all="selectAllEvent"
            @checkbox-change="selectChangeEvent"
            :params="queryParams"
            :toolbar="{zoom: true,custom:true,refresh:true}"
          >
            <template v-slot:buttons>
              <span>
                <a-button
                  type="primary"
                  @click="change('hourModal')"
                >切换工时</a-button>
              </span>
              <span style="padding-left:10px;">
                <a-button
                  type="primary"
                  @click="exportTime"
                  v-if="$auth('project:rdHour:export')"
                >导出</a-button>
              </span>
              <a-button
                type="primary"
                v-if="$auth('project:rdHour:del')"
                style="margin-left: 10px;"
                @click="delManHour"
                :disabled="selectDelRecords.length <= 0"
              >删除</a-button>
            </template>
            <vxe-table-column type="checkbox" width="50" align="center" headAlign="center" fixed="left"></vxe-table-column>
            <vxe-table-column
              type="seq"
              :width="50"
              fixed="left"
              title="序号"
            />
            <vxe-table-column
              title="RD"
              field="rdTitle"
              fixed="left"
              :width="86"
            />
            <vxe-table-column
              title="项目名称"
              field="pname"
              fixed="left"
              :min-width="180"
            />
            <vxe-table-column
              align="center"
              title="出勤日期"
              field="workDate"
              :width="110"
            />
            <vxe-table-column
              title="工号"
              field="enumber"
              :width="120"
            />
            <vxe-table-column
              title="姓名"
              field="ename"
              :width="120"
            />
            <vxe-table-column
              align="right"
              title="工时"
              field="workHour"
              :width="100"
            />
            <vxe-table-column
              align="center"
              title="上班时间1"
              field="onTime1"
              :width="100"
            />
            <vxe-table-column
              align="center"
              title="下班时间1"
              field="offTime1"
              :width="100"
            />
            <vxe-table-column
              align="center"
              title="上班时间2"
              field="onTime2"
              :width="100"
            />
            <vxe-table-column
              align="center"
              title="下班时间2"
              field="offTime2"
              :width="100"
            />
            <vxe-table-column
              align="center"
              title="上班时间3"
              field="onTime3"
              :width="100"
            />
            <vxe-table-column
              align="center"
              title="下班时间3"
              field="offTime3"
              :width="100"
            />
            <vxe-table-column
              title="工作内容"
              field="content"
              :width="140"
            />
            <vxe-table-column
              title="操作"
              header-align="center"
              align="center"
              :width="80"
              fixed="right"
            >
              <template v-slot="{row}">
                <a v-if="$auth('project:rdHour:edit')" @click="$refs.rdTimeModal.edit(row)">编辑</a>
              </template>
            </vxe-table-column>
          </ystable>
        </div>
      </div>
      <rd-time-modal ref="rdTimeModal" @ok="query(false)"/>
    </a-card>
  </a-spin>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'
import { TreeSelect } from 'ant-design-vue'
import moment from 'moment'
import RdTimeModal from './modules/RdTimeModal'
const SHOW_PARENT = TreeSelect.SHOW_PARENT
const monthDay = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
const timeKeys = ['rdTitle', 'pname', 'workDate', 'enumber', 'ename', 'workHour', 'onTime1', 'offTime1', 'onTime2', 'offTime2', 'onTime3', 'offTime3', 'content']
const timeTitles = ['RD', '项目名称', '出勤日期', '工号', '姓名', '工时', '上班时间1', '下班时间1', '上班时间2', '下班时间2', '上班时间3', '下班时间3', '工作内容']
const timeColWidths = [10, 25, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 20]
export default {
  components: {
    ystable,
    RdTimeModal
  },
  mixins: [yearMiXin],
  data () {
    return {
      SHOW_PARENT,
      monthDay,
      timeKeys,
      timeTitles,
      timeColWidths,
      first: true,
      projectIds: [],
      spinning: false,
      monthValue: '0',
      queryParams: {},
      activeKey: 'hourModal',
      monthMaxDay: 31,
      projects: [],
      selectDelRecords: []
    }
  },
  created () {
    this.search()
    this.queryParams.year = this.currentYear
    this.queryParams.month = moment([this.currentYear, parseInt(this.monthValue), 1, 0, 0, 0, 0])
  },
  methods: {
    search () {
      if (!this.currentYear) {
        return
      }
      this.$http.get('/project/getSelectList', { params: { year: this.currentYear, sign: 2 } })
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.projects = res.data
            this.projectIds = [this.projects[0].id]
            this.query(true)
          } else {
            this.projects = []
            this.projectIds = []
          }
        })
    },
    moment,
    loadQueryParam () {
      this.queryParams.month = moment([this.currentYear, parseInt(this.monthValue), 1, 0, 0, 0, 0])
      var monthMaxDay = monthDay[Number(this.monthValue)]
      if (!monthMaxDay) {
        monthMaxDay = !(this.currentYear % 4) || (!this.currentYear % 100 && !this.currentYear % 400) ? 29 : 28
      }
      this.monthMaxDay = monthMaxDay
      this.queryParams.year = this.currentYear
      let projectIds = []
      if (this.projectIds.indexOf(-1) >= 0) {
        projectIds = this.projects.map(a => a.id)
      } else {
        projectIds = this.projectIds
      }
      this.queryParams.projectIds = projectIds
    },
    query (refresh) {
      this.$nextTick(() => {
        this.loadQueryParam()
        if (this.$refs[this.activeKey]) {
          this.$refs[this.activeKey].refresh(refresh)
        }
      })
    },
    change (key) {
      this.activeKey = key
      this.query(true)
    },
    exportHour () {
      this.spinning = true
      const hourKeys = ['rdTitle', 'pname', 'month', 'enumber', 'ename', 'planTime', 'totalHour']
      const hourTitles = ['RD', '项目名称', '月份', '工号', '姓名', '计划工时', '合计']
      const hourColWidths = [10, 25, 15, 15, 15, 10, 10]
      for (let i = 1; i <= this.monthMaxDay; i++) {
        hourKeys.push(i)
        hourTitles.push(i)
        hourColWidths.push(8)
        if (this.hadContent(i)) {
          hourKeys.push('content' + i)
          hourTitles.push('工作内容')
          hourColWidths.push(15)
        }
      }
      this.loadQueryParam()
      this.$http.get('/projectAttendance/exportAllRdHour', { params: this.queryParams })
        .then(res => {
          if (res.success) {
            if (res.data && res.data.length > 0) {
              var monthItem = {}
              res.data.forEach(item => {
                let total = 0
                for (let i = 1; i <= this.monthMaxDay; i++) {
                  if (!item.info[i]) {
                    item[i] = '-'
                  } else {
                    item[i] = item.info[i].workHour
                    total += Number(item[i])
                  }
                  if (this.hadContent(i)) {
                    item['content' + i] = this.getContent(item.info, i)
                  }
                }
                item['totalHour'] = total
                if (!monthItem[item.month]) {
                  monthItem[item.month] = []
                }
                monthItem[item.month].push(item)
              })
              const keyArr = Object.keys(monthItem)
              var datas = []
              for (let i = 0; i < keyArr.length; i++) {
                datas.push({ sheetHeader: hourTitles, sheetFilter: hourKeys, sheetData: monthItem[keyArr[i]], columnWidths: hourColWidths, sheetName: keyArr[i] })
              }
              this.$exportJson(`研发工时表`, datas)
            } else {
              this.$message.info('当前无可导出的数据。')
            }
          } else {
            this.$message.warning('导出失败，请联系管理员。')
          }
          this.spinning = false
        })
    },
    exportTime () {
      this.loadQueryParam()
      this.spinning = true
      this.$http.get('/projectAttendance/exportAllRdTime', { params: this.queryParams })
        .then(res => {
          if (res.success) {
            if (res.data && res.data.length > 0) {
              var monthItem = {}
              res.data.forEach(item => {
                const month = item.workDate.substr(0, 7)
                if (!monthItem[month]) {
                  monthItem[month] = []
                }
                monthItem[month].push(item)
              })
            } else {
              this.$message.info('当前无可导出的数据。')
            }
            const keyArr = Object.keys(monthItem)
            var datas = []
            for (let i = 0; i < keyArr.length; i++) {
              datas.push({ sheetHeader: this.timeTitles, sheetFilter: this.timeKeys, sheetData: monthItem[keyArr[i]], columnWidths: this.timeColWidths, sheetName: keyArr[i] })
            }
            this.$exportJson(`研发时间表`, datas)
          } else {
            this.$message.warning('导出失败，请联系管理员。')
          }
          this.spinning = false
        })
    },
    getTotalHour (infos) {
      var total = 0
      for (const k in infos) {
        if (infos[k]) {
          total += Number(infos[k].workHour)
        }
      }
      return total ? (Math.round(total * 100) / 100).toFixed(2) : '0'
    },
    getCompareColor (row) {
      const total = this.getTotalHour(row.info)
      if (total === '-') {
        if (row.planTime) {
          return 'red'
        }
      }
      return row.planTime > total ? 'red' : ''
    },
    hadContent (day) {
      const currentDay = moment([this.currentYear, parseInt(this.monthValue), day, 0, 0, 0, 0])
      if (!currentDay.day()) { // 星期天
        return true
      }
      return day === this.monthMaxDay
    },
    getContent (infos, day) {
      let weekDay = moment([this.currentYear, parseInt(this.monthValue), day, 0, 0, 0, 0]).day()
      if (!weekDay) {
        weekDay = 7
      }
      var content = []
      let i = (day - weekDay) + 1
      i = i > 0 ? i : 1
      for (; i <= day; i++) {
        if (infos[i] && infos[i].content) {
          content.push(infos[i].content)
        }
      }
      return content.join('  ')
    },
    selectAllEvent ({ checked, records }) {
      this.selectDelRecords = records
    },
    selectChangeEvent ({ checked, records }) {
      this.selectDelRecords = records
    },
    delManHour () {
      const postData = []
      let totalTime = 0
      this.selectDelRecords.forEach((item, index) => {
        totalTime = (totalTime * 1000 + item.workHour * 1000) / 1000
        postData.push({ projectId: item.projectId, date: moment(new Date(item.workDate)), enumber: item.enumber })
      })
      const slef = this
      this.$confirm({
        title: `所选总工时：${totalTime}小时，您确定要删除？`,
        onOk () {
          slef.handleDelManHour(postData)
        }
      })
    },
    handleDelManHour (postData) {
      this.spinning = true
      this.$http.post('/projectAttendance/delData', postData).then((res) => {
        if (res.data && res.success) {
          this.selectDelRecords = []
          this.$message.success('操作成功')
          this.query(true)
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
        this.spinning = false
      })
    }
  }
}
</script>

<style>
</style>
