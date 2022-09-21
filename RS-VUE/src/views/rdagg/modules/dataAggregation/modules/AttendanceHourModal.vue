<template>
  <a-spin :spinning="spinning">
    <ystable
      ref="table"
      queryUrl="/projectAttendance/getHourList"
      :params="queryParams"
      rowId="id"
      @checkbox-change="onSelectChange"
      @checkbox-all="onSelectChange"
      @completed="({data})=>completed(data)"
      :toolbar="{zoom: true, refresh: true}"
      show-overflow="title"
    >
      <template slot="buttons">
        <span style="float:right;padding-right:10px;">
          <a-button type="primary" size="small" @click="modalChange('timeModal')">切换时间</a-button>
        </span>
        <span v-if="canModify && $auth('project:data:agg')" style="padding-left:10px;">
          <span>
            <a-checkbox :checked="allMan" @change="allMan = !allMan">所有人员</a-checkbox>
          </span>
          <a-button
            type="primary"
            size="small"
            style="padding-left:10px;"
            :disabled="!allMan && selectedRowKeys.length <= 0"
            @click="refreshAttendance"
          >生成考勤工时</a-button>
          <a-switch
            checkedChildren="编辑"
            unCheckedChildren="编辑"
            style="margin-left:10px;"
            type="primary"
            :checked="editChecked"
            @change="editCheckedChange"
          />
          <a-button
            v-if="editChecked"
            style="margin-left:10px;"
            type="primary"
            size="small"
            @click="saveData"
          >保存</a-button>
        </span>
      </template>
      <vxe-table-column type="checkbox" :width="40" fixed="left" />
      <vxe-table-column
        fixed="left"
        align="center"
        title="工号"
        field="enumber"
        :width="120"
        remoteSort
        show-header-overflow
      />
      <vxe-table-column
        align="center"
        title="姓名"
        field="ename"
        :width="120"
        remoteSort
        fixed="left"
        show-header-overflow
      />
      <vxe-table-column align="center" title="部门" field="deptName" :width="120"/>
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
      <vxe-table-column align="right" title="总工时" field="workHours" :width="100"/>
      <vxe-table-column
        align="center"
        title="研发工时"
        field="rdHour"
        :width="100"
        remoteSort
      >
        <template slot-scope="{ row }">
          <a-tooltip
            placement="top"
            v-if="row.rdHour - row.attendanceHour !== 0"
          >
            <template slot="title">研发考勤工时差额: {{ row.rdHour - row.attendanceHour }} </template>
            <span style="color: red; font-weight: bolder">{{ row.rdHour }}</span>
          </a-tooltip>
          <span v-else>{{ row.rdHour }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        v-for="monthItem in $getMonthDay(queryParams.month.format('YYYY-MM'))"
        field="attDataList"
        :key="`att_${monthItem.index}`"
        align="center"
        :width="workHourWidth"
      >
        <!-- 列的title -->
        <span slot="header" :style="{color: monthItem.isWeekDay?'#d9d9d9':'#3D3D3D'}">
          <a-tooltip placement="top">
            <template slot="title">{{ monthItem.weekDayStr }}</template>
            <span>{{ monthItem.index }}</span>
          </a-tooltip>
        </span>
        <!-- 列的content -->
        <template slot-scope="{row}">
          <span
            v-if="row.info[monthItem.index] && getMax(row.info[monthItem.index],row,monthItem.index)"
          >
            <span v-if="editChecked">
              <vxe-input
                type="number"
                :title="getCount(row.info[monthItem.index]) > 0? '输入工时超出可分配工时:' +getCount(row.info[monthItem.index]) : ''"
                :value="getVal(row.info[monthItem.index])"
                :class="getCount(row.info[monthItem.index]) > 0 ? 'redInput' : 'baseInput'"
                :min="0"
                :max="24"
                size="mini"
                @change="v=>handleChange(v,row,monthItem.index)"
              />
            </span>
            <span
              v-else-if="row.info[monthItem.index].workHour"
            >{{ row.info[monthItem.index].workHour }}</span>
            <span v-else>-</span>
          </span>
          <span v-else>-</span>
        </template>
      </vxe-table-column>
    </ystable>
    <setting-day-hour-modal url="/projectAttendance/refresh" ref="settingDayHour" freshType="attendance" @ok="settingOk"/>
  </a-spin>
</template>
<script>
import moment from 'moment'
import ystable from '@/components/Table/ystable'
import SettingDayHourModal from './SettingDayHourModal'
export default {
  components: {
    ystable,
    SettingDayHourModal
  },
  props: {
    queryParams: {
      type: Object,
      default: () => { return {} }
    },
    canModify: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      tableData: [],
      selectedRowKeys: [],
      allMan: false,
      editChecked: false,
      firstLoad: true,
      spinning: false,
      editMap: {},
      hasAttendance: false,
      workHourWidth: 80
    }
  },
  created () {
    this.$http.get('/projectAttendance/hasAttendance').then(res => {
      if (res.success) {
        this.hasAttendance = res.data
      } else {
        this.$message.error(res.errorMessage)
      }
    })
  },
  methods: {
    modalChange (key) {
      this.$emit('modalChange', key)
    },
    completed (data) {
      this.editMap = {}
      this.tableData = data.data
      this.editCheckedChange(this.editChecked)
    },
    getVal (d) {
      return d.workHour
    },
    getCount (d) {
      return d.remainHours < 0 ? -d.remainHours : 0
    },
    getMax (d, r, n) {
      var t = d
      if (r && r.cacheInfo) {
        t = r.cacheInfo[n]
      }
      if (t.remainHours !== undefined && t.remainHours !== null && t.remainHours >= 0) {
        return t.remainHours
      }
      return t.baseHours ? t.baseHours : 0
    },
    getTotal (info) {
      var total = 0
      for (const key in info) {
        if (info[key].workHour) {
          total += Number(info[key].workHour)
        }
      }
      return total
    },
    moment,
    editCheckedChange (ck) {
      this.editChecked = ck
      if (this.tableData) {
        if (ck) {
          this.tableData.forEach(record => {
            record.cacheInfo = this.$deepClone(record.info)
          })
        } else {
          this.tableData.forEach(record => {
            Object.assign(record.info, record.cacheInfo)
            delete record.cacheInfo
          })
        }
      }
    },
    search (refresh, closeEdit) {
      if (closeEdit) {
        this.editChecked = false
      }
      this.$nextTick(() => {
        this.$refs.table.refresh(refresh)
      })
    },
    refreshAttendance () {
      const values = { month: this.queryParams.month, projectId: this.queryParams.projectId }
      // values.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      // values.projectId = this.projectId
      values.allMan = this.allMan
      if (!this.allMan) {
        values.ids = this.selectedRowKeys
      }
      this.$refs.settingDayHour.set(values, this.hasAttendance)
    },
    onSelectChange ({ records }) {
      this.selectedRowKeys = records.map(item => item.id)
      if (this.selectedRowKeys.length) {
        this.allMan = false
      }
    },
    handleChange (v, record, index) {
      v = Number(v.value)
      if (v < 0 || isNaN(v)) {
        if (this.editMap[record.enumber]) {
          const t = this.editMap[record.enumber]
          if (t[index]) {
            t[index] = undefined
          }
        }
        return
      }
      if (v === record.info[index].workHour) {
        return
      }
      const total = this.getTotal(record.info) - Number(record.info[index].workHour) + v
      if (record.rdHour < total) {
        v = v - (total - Number(record.rdHour))
      }
      const max = this.getMax(record.cacheInfo[index])
      record.info[index].workHour = v
      record.info[index].remainHours = Math.round((max - v) * 100) / 100
      if (record.info[index].remainHours < 0) {
        this.$message.warning(`工号：${record.enumber}，天数：${index}，输入工时超出可分配工时: ${-record.info[index].remainHours}`)
      }
      if (!this.editMap[record.enumber]) {
        this.editMap[record.enumber] = {}
      }
      this.editMap[record.enumber][index] = record.info[index]
    },
    saveData () {
      this.spinning = true
      const list = []
      for (const key in this.editMap) {
        const temp = this.editMap[key]
        for (const k in temp) {
          if (temp[k]) {
            if (temp[k].remainHours < 0) {
              this.spinning = false
              this.$message.error(`工号：${key}，天数：${k}，输入工时超出可分配工时: ${-temp[k].remainHours}`)
              return
            }
            temp[k].enumber = key
            list.push(temp[k])
          }
        }
      }
      if (list.length <= 0) {
        this.spinning = false
        this.$message.info('未进行任何编辑')
        return
      }
      const values = { list: list, projectId: this.queryParams.projectId, month: this.queryParams.month }
      this.$http.post('/projectAttendance/saveData', values).then(res => {
        if (res.success) {
          this.editMap = {}
          this.$message.success('保存成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          this.visible = false
        }
        this.spinning = false
      })
    },
    settingOk () {
      this.selectedRowKeys = []
      this.allMan = false
      this.search()
    }
  }
}
</script>

<style>
.baseInput {
  width: 80px;
}
.redInput {
  width: 80px;
  border: red 1px solid;
}
</style>
