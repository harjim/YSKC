<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="姓名" >
          <a-input v-model="queryParam.ename" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item label="工号">
          <a-input v-model="queryParam.enumber" placeholder="请输入工号" />
        </a-form-item>
        <a-form-item label="考勤日期" >
          <a-date-picker
            :disabledDate="disabledDate"
            v-model="queryParam.workDate"
            placeholder="请选择考勤日期"
            style="width:100%"
          />
        </a-form-item>
        <a-form-item label="部门" >
          <a-input v-model="queryParam.deptName" placeholder="请输入部门" />
        </a-form-item>
        <a-form-item>
          <a-button
            style="margin-right: 10px"
            :size="operationSize"
            type="primary"
            @click="search(true)"
            v-if="$auth('dataentry:customerAttendance:search')"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          border
          ref="table"
          queryUrl="/customerAttendance/getList"
          :params="getParams()"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
        >
          <template v-slot:toolbar_buttons>
            <a-button
              type="primary"
              style="margin-right:10px;"
              @click="$refs.modifyModal.showModal()"
              v-if="$auth('dataentry:customerAttendance:add')"
            >添加</a-button>
            <a-button
              type="primary"
              style="margin-right:10px;"
              @click="$refs.uploadModal.show(tableField)"
              v-if="$auth('dataentry:customerAttendance:import')"
            >导入</a-button>
            <a-button
              type="primary"
              style="margin-right:10px;"
              @click="exportData"
              v-if="$auth('dataentry:customerAttendance:export')"
            >导出</a-button>
            <a-button
              type="primary"
              style="margin-right:10px;"
              @click="delList"
              :disabled="selectedRowKeys.length <= 0"
              v-if="$auth('dataentry:customerAttendance:del')"
            >删除</a-button>
          </template>
          <vxe-table-column type="checkbox" width="40" align="center" fixed="left"></vxe-table-column>
          <vxe-table-column field="enumber" title="工号" width="100" align="left" remoteSort></vxe-table-column>
          <vxe-table-column field="ename" title="姓名" width="100" align="left" remoteSort></vxe-table-column>
          <vxe-table-column field="deptName" title="部门" width="150" align="left" remoteSort></vxe-table-column>
          <vxe-table-column field="workDate" title="出勤日期" width="140" align="center" remoteSort></vxe-table-column>
          <vxe-table-column title="出勤时间段" align="center">
            <vxe-table-column field="onTime1" title="上班时间1" width="120" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="offTime1" title="下班时间1" width="120" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="onTime2" title="上班时间2" width="120" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="offTime2" title="下班时间2" width="120" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="onTime3" title="上班时间3" width="120" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="offTime3" title="下班时间3" width="120" align="center" remoteSort></vxe-table-column>
          </vxe-table-column>
          <vxe-table-column field="workHour" title="工时" width="100" align="right" remoteSort></vxe-table-column>
          <vxe-table-column field="remark" title="备注" min-width="150" align="left" remoteSort></vxe-table-column>
          <vxe-table-column title="操作" width="120" align="center" fixed="right">
            <template v-slot="{ row }">
              <span v-if="$auth('dataentry:customerAttendance:edit')">
                <a @click="$refs.modifyModal.edit(row)">编辑</a>
                <a-divider v-if="$auth('dataentry:customerAttendance:del')" type="vertical" />
              </span>
              <a-popconfirm
                v-if="$auth('dataentry:customerAttendance:del')"
                title="是否确定删除?"
                @confirm="del(row)"
              >
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
    </a-spin>
    <customer-attendance-modal @ok="(ok)=>search(ok)" ref="modifyModal" />
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      paramKey="name"
      title="导入员工考勤"
      ref="uploadModal"
      action="/doc/customerAttendance/importAttendance"
      templateName="员工考勤模板"
      @onSuccess="success"
    />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { UploadModal, Ellipsis } from '@/components'
import CustomerAttendanceModal from './modules/CustomerAttendanceModal'
import yearMixin from '@/utils/yearMixin'
import moment from 'moment'
export default {
  name: 'CustomerAttendance',
  mixins: [yearMixin],
  components: {
    ystable,
    UploadModal,
    CustomerAttendanceModal,
    Ellipsis
  },
  data () {
    return {
      labelCol: {
        md: { span: 8 }
      },
      wrapperCol: {
        md: { span: 16 }
      },
      spinning: false,
      selectedRowKeys: [],
      scroll: {},
      queryParam: {},
      // loadData: paramter => {
      //   this.initParam()
      //   return this.$http.get('/cAttendance/getList', { params: Object.assign(paramter, this.queryParam) })
      //     .then(res => {
      //       return res.data
      //     })
      // },
      tableField: {
        tableId: 'dataEquipmentTable',
        fieldTitleObject: {
          enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
          ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          workDate: { title: '出勤日期', required: true, defaultTitle: '出勤日期', importField: true },
          workHour: { title: '工时', required: true, defaultTitle: '工时', importField: true },
          onTime1: { title: '上班时间1', required: true, defaultTitle: '上班时间1', importField: true },
          offTime1: { title: '下班时间1', required: true, defaultTitle: '下班时间1', importField: true },
          onTime2: { title: '上班时间2', required: false, defaultTitle: '上班时间2', importField: true },
          offTime2: { title: '下班时间2', required: false, defaultTitle: '下班时间2', importField: true },
          onTime3: { title: '上班时间3', required: false, defaultTitle: '上班时间3', importField: true },
          offTime3: { title: '下班时间3', required: false, defaultTitle: '下班时间3', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      sampleData: [
        {
          enumber: 'A-01',
          ename: '张三',
          workHour: '数字格式，范围：0-24，例如：1.21',
          deptName: 'xxx部门',
          workDate: '格式："年-月-日"，例如：2019-10-01',
          onTime1: '格式："时:分"，例如：08:00',
          offTime1: '格式："时:分"，例如：12:00',
          onTime2: '格式："时:分"，例如：14:00',
          offTime2: '格式："时:分"，例如：18:00',
          onTime3: '格式："时:分"，例如：18:00',
          offTime3: '格式："时:分"，例如：23:00',
          remark: '备注'
        }
      ],
      // 设置操作按键的尺寸: large,default,small add by lee  20200408
      operationSize: 'default'
    }
  },
  created () {
    this.queryParam.year = this.$store.state.currentYear ? this.$store.state.currentYear : null
    if (document.body.clientWidth < 1780) {
      this.scroll = { x: 1800 }
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1780) {
          this.scroll = { x: 1800 }
        }
      })()
    }
    this.initParam()
  },
  methods: {
    getParams () {
      this.initParam()
      return this.queryParam
    },
    moment,
    getWorkhour (onTime, offTime) {
      const time = Math.abs(moment(onTime, 'HH:mm').format('x') - moment(offTime, 'HH:mm').format('x'))
      return (moment.duration(time).hours() + moment.duration(time).minutes() / 60).toFixed(2)
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
    search (refresh) {
      this.selectedRowKeys = []
      this.initParam()
      this.$refs.table.refresh(refresh)
    },
    disabledDate (current) {
      var end = moment().endOf('day')
      var start = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
      const nowYear = new Date().getFullYear()
      if (nowYear !== this.currentYear) {
        if (nowYear < this.currentYear) {
          start = moment([nowYear, 11, 31, 0, 0, 0, 0])
          end = moment([nowYear, 0, 1, 0, 0, 0, 0])
        } else {
          end = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        }
      }
      return (!current || current > end) || current < start
    },
    initParam () {
      this.queryParam.year = this.$store.state.currentYear ? this.$store.state.currentYear : null
      if (this.queryParam.monthValue === Number.undefined || this.queryParam.monthValue === '-1') {
        var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        this.queryParam.startMonth = startMonth
        this.queryParam.endMonth = endMonth
      } else {
        this.queryParam.startMonth = moment([this.currentYear, this.queryParam.monthValue, 1, 0, 0, 0, 0])
        var value = parseInt(this.queryParam.monthValue)
        if (value <= 12) {
          this.queryParam.endMonth = moment([this.currentYear, parseInt(this.queryParam.monthValue), 1, 0, 0, 0, 0])
        } else {
          this.queryParam.endMonth = moment([this.currentYear + 1, this.queryParam.monthValue, 1, 0, 0, 0, 0])
        }
      }
    },
    del (record) {
      this.$http.post('/customerAttendance/delete', { id: record.id })
        .then(res => {
          if (res.success) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.error(`[${res.errorMessage ? res.errorMessage : '删除失败'}`)
          }
          this.spinning = false
        })
    },
    delList () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的考勤吗?',
        onOk () {
          var values = []
          self.selectedRowKeys.forEach(id => {
            values.push({ id: id })
          })
          return self.$http.post('/customerAttendance/deleteList', values)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.search(false)
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    onSelectChange (rows) {
      this.selectedRowKeys = rows
    },
    exportData () {
      this.initParam()
      this.spinning = true
      this.$http.get('/customerAttendance/exportAttendance', { params: this.queryParam })
        .then(res => {
          if (res.success) {
            var keys = Object.keys(this.tableField.fieldTitleObject).map(a => this.tableField.fieldTitleObject[a].title)
            var sheetData = res.data
            var sheetFilter = Object.keys(this.tableField.fieldTitleObject)
            this.$exportJsonData('员工考勤列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
        }).finally(res => {
          this.spinning = false
        })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
    }
  }
}
</script>

<style lang="less" scoped>
.table-page-search-wrapper .ant-form-inline .ant-form-item > .ant-form-item-label {
  width: 33%;
}
#operation {
  float: right;
  margin-bottom: 10px;
  span {
    padding-left: 5px;
  }
}

.clearfix:before,
.clearfix::after {
  content: '';
  display: table;
  clear: both;
}
.clearfix {
  *zoom: 1;
}
</style>
