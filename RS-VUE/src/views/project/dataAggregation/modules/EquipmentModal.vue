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
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="资产代码">
          <a-input v-model="queryParams.ecode" placeholder="请输入资产代码" style="width:165px" />
        </a-form-item>
        <a-form-item label="设备名称">
          <a-input v-model="queryParams.ename" placeholder="请输入设备名称" style="width:165px" />
        </a-form-item>
        <a-form-item label="部门">
          <a-input v-model="queryParams.deptName" placeholder="请输入部门名称" style="width:165px" />
        </a-form-item>
        <a-form-item>
          <span style="padding-left:10px;">
            <a-button type="primary" @click="search(true)">查询</a-button>
          </span>
          <span style="padding-left:10px;">
            <a-button type="primary" @click="exportData(false)">导出</a-button>
          </span>
          <span style="padding-left:10px;">
            <a-button type="primary" @click="exportData(true)">导出所有RD</a-button>
          </span>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          rowId="id"
          queryUrl="/projectEquipment/getList"
          :params="queryParams"
          :toolbar="{zoom: true, refresh: true}"
          @checkbox-change="onSelectChange"
          @checkbox-all="onSelectChange"
          @completed="({data})=>completed(data)"
          show-overflow="title"
        >
          <template slot="buttons">
            <span v-if="canModify && $auth('project:data:agg')">
              <span style="padding-left:10px;">
                <a-checkbox :checked="all" @change="all = !all">所有设备</a-checkbox>
              </span>
              <a-button
                type="primary"
                style="margin-left:10px;"
                size="small"
                :disabled="!all && selectedRowKeys.length <= 0"
                @click="refreshEquipment"
              >生成研发工时</a-button>
              <a-tooltip placement="top" >
                <template slot="title">
                  <span>{{ `刷新${rdTitle}【${month}】研发试制计划` }}</span>
                </template>
                <a-button
                  type="primary"
                  style="margin-left:10px;"
                  size="small"
                  :disabled="selectedRowKeys.length != 1"
                  @click="refreshYieldConfig"
                >刷新试制计划</a-button>
              </a-tooltip>

              <a-switch
                checkedChildren="编辑"
                unCheckedChildren="编辑"
                style="margin-left:10px;"
                type="primary"
                :checked="openEditMode"
                @change="onOpenEditMode"
              />
              <a-button
                style="margin-left: 10px;"
                type="primary"
                size="small"
                v-if="openEditMode"
                @click="onSaveList"
              >保存</a-button>
            </span>
          </template>
          <vxe-table-column type="checkbox" :width="40" fixed="left" />
          <vxe-table-column
            align="left"
            title="资产代码"
            field="ecode"
            :min-width="120"
            remoteSort
            show-header-overflow
            fixed="left"
          />
          <vxe-table-column
            align="left"
            title="设备名称"
            field="ename"
            :min-width="120"
            remoteSort
            show-header-overflow
            fixed="left"
          />
          <vxe-table-column
            align="left"
            title="部门"
            field="deptName"
            :width="150"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            align="right"
            title="研发工时"
            :width="100"
            field="rdHour"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{ row }">
              <a-tooltip
                placement="top"
                v-if="!row.workHours || row.rdHour - row.workHours !== 0"
              >
                <template slot="title">研发工时差额: {{ row.workHours ? row.rdHour - row.workHours : row.rdHour }} </template>
                <span style="color: red; font-weight: bolder">{{ row.rdHour }}</span>
              </a-tooltip>
              <span v-else>{{ row.rdHour }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            v-for="item in $getMonthDay(month)"
            field="equDataArr"
            :key="`att_${item.index}`"
            :width="workHourWidth"
          >
            <template slot-scope="{row}">
              <span v-if="row.equDataArr.length >= item.index">
                <span v-if="existDate(item.index,row)">
                  <template v-if="!openEditMode">
                    <span
                      v-if="Number(row.equDataArr[item.index - 1])"
                    >{{ row.equDataArr[item.index - 1] }}</span>
                    <span v-else>-</span>
                  </template>
                  <template v-else>
                    <vxe-input
                      :value="getHoursByDay(row.equDataArr,item.index)"
                      :class="row.remainEquDataArr[item.index - 1] < 0 ? 'redInput' : 'baseInput'"
                      :min="0"
                      :max="24"
                      type="number"
                      size="mini"
                      @change="v => handleChange(v, item.index, row)"
                    />
                  </template>
                </span>
                <span v-else>-</span>
              </span>
            </template>
            <span slot="header" :style="{color: item.isWeekDay?'#d9d9d9':'#3D3D3D'}">
              <a-tooltip placement="top">
                <template slot="title">{{ item.weekDayStr }}</template>
                <span v-if="attData!== null">
                  <span v-if="attData[item.index]">{{ item.index }}</span>
                  <del v-else style="color: red;">{{ item.index }}</del>
                </span>
                <del v-else style="color: red;">{{ item.index }}</del>
              </a-tooltip>
            </span>
          </vxe-table-column>
        </ystable>
      </div>
      <setting-day-hour-modal url="/projectEquipment/refresh" ref="settingDayHour" freshType="equipment" @ok="settingOk"/>
      <refresh-yield-config-modal :rdTitle="rdTitle" :month="month" :projectId="projectId" ref="refreshYield"/>
    </a-spin>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import yearMixin from '@/utils/yearMixin'
import { mapGetters } from 'vuex'
import SettingDayHourModal from './SettingDayHourModal'
import RefreshYieldConfigModal from './RefreshYieldConfigModal'
export default {
  mixins: [yearMixin],
  name: 'EquipmentModal',
  components: {
    ystable,
    SettingDayHourModal,
    RefreshYieldConfigModal
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    month: {
      type: String,
      default: ''
    },
    types: {
      type: Array,
      required: true
    },
    rdTitle: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      curRecord: undefined,
      title: '',
      workHourWidth: 80,
      visible: false,
      confirmLoading: false,
      spinning: false,
      openEditMode: false,
      attData: null,
      tableData: [],
      selectedRowKeys: [],
      queryParams: {
        projectId: this.projectId,
        month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'),
        types: this.types
      },
      all: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      editMap: {},
      firstLoad: true,
      canModify: true
    }
  },
  created () {
    this.queryParams.year = this.currentYear
    this.getAttData()
  },
  methods: {
    completed (data) {
      this.editMap = {}
      this.tableData = data.data
      if (this.openEditMode) {
        this.tableData.forEach(record => {
          this.copyBak(record)
        })
      }
    },
    showModal (month, canModify) {
      this.canModify = canModify
      this.openEditMode = false
      this.all = false
      this.selectedRowKeys = []
      this.title = '[' + month.format('YYYY年MM月') + '】研发工时表'
      this.queryParams.projectId = this.projectId
      this.queryParams.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      this.queryParams.types = this.types
      this.queryParams.year = this.currentYear
      this.visible = true
      if (!this.firstLoad) {
        this.search(true)
      }
      this.firstLoad = false
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    onSaveList () {
      this.spinning = true
      const list = []
      for (const k in this.editMap) {
        const temp = this.editMap[k]
        if (temp.error) {
          this.$message.error(this.editMap[k].error)
          this.spinning = false
          return
        }
        const record = temp.record
        if (record && record.peId) {
          var totalHours = 0
          record.equDataArr.forEach(v => { totalHours += Number(v) })
          if (totalHours < record.rdHour) {
            this.$message.warning(`资产代码：${record.ecode}，输入的总工时：${totalHours}，小于研发工时：${record.rdHour}`)
            this.spinning = false
            return
          }
          list.push({ peId: record.peId, equData: record.equData, ecode: record.ecode })
        }
      }
      if (list.length <= 0) {
        this.$message.info('未进行任何编辑')
        this.spinning = false
        return
      }
      const params = { list: list, month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'), projectId: this.projectId }
      this.$http.post('/projectEquipment/saveList', params)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
            this.editMap = {}
            this.search(false)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
        }).finally(res => {
          this.spinning = false
        })
    },
    copyBak (record) {
      record.cacheData = { ...record }
      record.cacheData.equDataArr = [...record.equDataArr]
      record.cacheData.remainEquDataArr = [...record.remainEquDataArr]
    },
    onOpenEditMode (ck) {
      this.openEditMode = ck
      this.editMap = {}
      for (var i = 0; i < this.tableData.length; i++) {
        var record = this.tableData[i]
        if (ck) {
          this.copyBak(record)
        } else {
          const cacheRecord = record.cacheData
          Object.assign(record, cacheRecord)
          delete record.cacheRecord
        }
      }
    },
    existDate (n, record) {
      var now = new Date(this.month)
      var hasHours = (parseFloat(record.equDataArr[n - 1]) + parseFloat(record.remainEquDataArr[n - 1])) > 0
      var isIMonth = n <= new Date(now.getFullYear(), now.getMonth() + 1, 0).getDate()
      return hasHours && isIMonth
    },
    getHoursByDay (text, day) {
      return parseFloat(text[day - 1])
    },
    moment,
    getAttData () {
      this.$http.get('/projectEquipment/getAttData', { params: { projectId: this.projectId, month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00') } })
        .then(res => {
          this.attData = res.data
        })
    },
    handleChange (val, n, record) {
      val = Number(val.value)
      var oldValue = Number(this.getHoursByDay(record.equDataArr, n))
      if (val < 0 || isNaN(val) || val === oldValue) {
        return
      }
      record.remainHours += oldValue
      var total = oldValue + this.getHoursByDay(record.remainEquDataArr, n)
      this.$nextTick(() => {
        var totalHours = 0
        record.equDataArr.forEach(v => { totalHours += Number(v) })
        totalHours = totalHours - oldValue + val
        if (record.rdHour < totalHours) {
          val = val - (totalHours - record.rdHour)
        }
        this.$set(record.equDataArr, n - 1, val.toFixed(2))
        this.$set(record.remainEquDataArr, n - 1, (total - val).toFixed(2))
        record.equData = record.equDataArr.join(',')
        record.remainEquData = record.remainEquDataArr.join(',')
        if (record.remainEquDataArr[n - 1] < 0) {
          const error = `资产代码：${record.ecode}，天数：${n}，输入工时超出可分配工时：${-record.remainEquDataArr[n - 1]}`
          this.$message.warning(error)
          record.remainHours -= val
          this.editMap[record.peId] = { record: record, error: error }
          return
        }
        if (val > record.remainHours) {
          record.remainHours -= val
          const error = `资产代码：${record.ecode}，天数：${n}，输入工时超出可分配工时：${-record.remainHours}`
          this.$message.warning(error)
          this.editMap[record.peId] = { record: record, error: error }
          return
        }
        record.remainHours -= val
        this.editMap[record.peId] = { record: record, error: false }
      })
    },
    onSelectChange ({ records }) {
      this.selectedRowKeys = records.map(item => item.id)
      this.curRecord = this.selectedRowKeys.length === 1 ? records[0] : undefined
    },
    refreshEquipment () {
      const values = {}
      values.projectId = this.projectId
      values.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      if (!this.all) {
        values.ids = this.selectedRowKeys
      }
      this.$refs.settingDayHour.set(values)
    },
    settingOk () {
      this.selectedRowKeys = []
      this.all = false
      this.search()
    },
    ...mapGetters(['userInfo']),
    exportData (all) {
      this.spinning = true
      this.queryParams.projectId = this.projectId
      this.queryParams.types = this.types
      this.queryParams.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
      this.queryParams.year = this.currentYear
      this.$exportData('/projectEquipment/exportData',
        Object.assign(this.queryParams, { all: all }),
        `${!all ? this.rdTitle + '-' : this.userInfo().companyName}${this.month}研发设备工作记录.xlsx`, this.$message).then(res => {
        this.spinning = false
      })
    },
    refreshYieldConfig () {
      this.$refs.refreshYield.show(this.curRecord)
    },
    closeModal () {
      this.queryParams = {}
      this.confirmLoading = false
      this.visible = false
      this.editChecked = false
    }
  }
}
</script>
<style>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
.baseInput {
  width: 80px;
}
.redInput {
  width: 80px;
  border: red 1px solid;
}
</style>
