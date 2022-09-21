<!--
    * @Author:
    * @Date: 2022-06-24 09:48:52
    * @Description: 会议签到表 —— 默认
-->
<template>
  <a-card id="container" style="height: 98%;">
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form style="width: 100%;" type="flex" justify="right" layout="horizontal">
        <a-row>
          <a-col :span="12">
            <a-form-item
              label="项目名称"
              :labelCol="{xs: { span: 24 },sm: { span: 8}}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}"
              style="margin-bottom: 8px; height: 100%;">
              <span>{{ project.pname }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="时间"
              :labelCol="{xs: { span: 24 },sm: { span: 4}}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}"
              style="margin-bottom: 8px;"
              required
            >
              <a-date-picker
                style="width: 100%;"
                :value="content.hostTime ? moment(content.hostTime) : null"
                @change="onDateChange"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item
              label="会议主题"
              :labelCol="{xs: { span: 24 },sm: { span: 8}}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}"
              style="margin-bottom: 8px;"
              required>
              <a-input type="text" v-model="content.theme" @change="arriveChange = true"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-row>
              <a-col :span="9">
                <a-form-item
                  label="应到人员"
                  :labelCol="{xs: { span: 24 },sm: { span: 11}}"
                  :wrapperCol="{xs: { span: 20},sm: { span: 13 }}"
                  span="12"
                  style="margin-bottom: 8px;padding-right: 5px;"
                  required>
                  <a-input type="number" style="width: 100%;" v-model="content.shouldArrive" @change="arriveChange = true" />
                </a-form-item>
              </a-col>
              <a-col :span="9">
                <a-form-item
                  label="实到人员"
                  :labelCol="{xs: { span: 24 },sm: { span: 12}}"
                  :wrapperCol="{xs: { span: 20},sm: { span: 12 }}"
                  span="12"
                  style="margin-bottom: 8px;padding-right: 0px;"
                  required>
                  <a-input type="number" style="width: 100%;" v-model="content.actuallyArrive" @change="arriveChange = true" />
                </a-form-item>
              </a-col>
            </a-row>
          </a-col>
        </a-row>
        <a-divider/>
        <div style="height: calc(100% - 145px);">
          <vxe-grid
            ref="table"
            :columns="columns"
            :data="content.list"
            height="110%"
            style="min-height: 100px"
            border
            show-overflow
            highlight-hover-row
            auto-resize
            resizable
            :column-config="{resizable: true}"
          >
            <template #toolbar>
              <span class="bottons">
                <a-button type="primary" @click="showAddModal()">
                  添加
                </a-button>
              </span>
              <span class="bottons">
                <a-button type="primary" @click="importEmployeeModal()">
                  引入
                </a-button>
              </span>
            </template>
            <template #operate="{ row }">
              <a-popconfirm title="确认移除？" ok-text="确认" cancel-text="取消" @confirm="delEmployee(row)">
                <a>移除</a>
              </a-popconfirm>
            </template>
          </vxe-grid>
        </div>
      </a-form>
      <add-employee-modal ref="addEmployee" @addEmployeeList="addEmployeeList"/>
      <import-meeting-modal ref="importMeeting" @importMeetingSummary="importMeetingSummary" />
    </a-spin>
  </a-card>
</template>

<script>
import VXETable from 'vxe-table'
import dateMixin from '../Templates/js/dateMixin'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
import addEmployeeModal from './modules/AddEmployeeModal'
import importMeetingModal from './modules/importMeetingModal'
// import AuditFooter from './modules/AuditFooter'
export default {
  name: 'MeetingSignInForm',
  components: {
    addEmployeeModal,
    // AuditFooter,
    VXETable,
    importMeetingModal
  },
  mixins: [dateMixin, yearMiXin],
  props: {
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    docFile: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      content: {}, // 存储的数据
      BPContent: {}, // copy组件content内容
      spinning: false,
      project: { pname: null },
      fileInfo: {},
      columns: [
        {
          title: 'ID',
          type: 'seq',
          width: 90,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        },
        {
          field: 'deptName',
          title: '部门',
          width: 250,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center',
          sortable: true
        },
        {
          field: 'ename',
          title: '姓名',
          width: 250,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center',
          sortable: true
        },
        {
          field: 'enumber',
          title: '工号',
          width: 250,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center',
          sortable: true
        },
        {
          field: '',
          title: '签名',
          width: 250,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center'
        },
        {
          title: '操作',
          slots: { default: 'operate' },
          width: 200,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center',
          fixed: 'right'
        }
      ],
      enumberArr: [],
      arriveChange: true
    }
  },
  created () {
    this.content = getTemplateContent('MeetingSignInForm')
    this.BPContent = getTemplateContent('MeetingSignInForm')
    // this.fileDate = moment(this.content.hostTime).startOf('month')
  },
  watch: {
    content: {
      handler (newValue, oldValue) {
        if (this.arriveChange) {
          this.arriveChange = false
          return
        }
        if (newValue.shouldArrive !== null && oldValue.shouldArrive !== null && oldValue.shouldArrive === newValue.shouldArrive) {
          this.content.shouldArrive = this.content.list ? this.content.list.length : 0
        }
        if (newValue.hostTime) {
          this.hsaFileDataNull = false
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    moment,
    showAddModal () {
      this.getEnumberArr()
      this.$refs.addEmployee.open(this.fileDate, this.docId, this.enumberArr)
    },
    getEnumberArr () {
      if (this.content.list) {
        this.content.list.forEach(elem => {
          this.enumberArr.push(elem.enumber)
        })
      }
    },
    delEmployee (row) {
      this.content.list = this.content.list.filter((elem) => {
        return elem.enumber !== row.enumber
      })
      this.$message.success('删除成功')
    },
    addEmployeeList (items) {
      if (this.content.list) {
        this.arriveChange = false
        items.forEach(elem => {
          this.content.list.push(elem)
        })
      } else {
        this.content.list = items
      }
      this.$message.success('添加成功')
    },
    importEmployeeModal () {
      this.$refs.importMeeting.open(this.fileDate, this.currentYear, this.project.id)
    },
    importMeetingSummary (data) {
      this.content.hostTime = data.hostTime
      this.content.theme = data.theme
      this.getEnumberArr()
      const self = this
      data.list.forEach(elem => {
        if (!self.enumberArr.includes(elem.enumber)) {
          self.content.list.push(elem)
        }
      })
    },
    onDateChange (_, value) {
      this.content.hostTime = value
      this.fileDate = moment(this.value).startOf('month')
      this.hsaFileDataNull = false
      this.arriveChange = true
    }
  }
}
</script>

<style lang='css' scoped>
html, body {
  overflow: hidden;
}
.ant-card-bordered .ant-card-body {
  height: 100%;
}
</style>
<style lang="less" scoped>
#container /deep/ .ant-tooltip-inner {
  width: 650px;
}
.bottons{
    padding-left: 10px;
    margin-bottom: 10px;
    display: inline-block;
}
/deep/ .ant-card-body, /deep/ .ant-form {
  height: 100%;
}
html {
  overflow: hidden;
}
</style>
