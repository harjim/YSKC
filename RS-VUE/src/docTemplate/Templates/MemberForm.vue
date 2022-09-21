<template>
  <a-form>
    <a-card>
      <a-row :gutter="12">
        <a-col :span="12">
          <a-form-item
            label="项目名称"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <span>{{ project.pname }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="项目编号"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <span>{{ project.rdNumber }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="12">
        <a-col :span="12">
          <a-form-item
            label="确定日期"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-date-picker
              :getCalendarContainer=" trigger => trigger.parentNode"
              :value="moment(content.d,'YYYY-MM-DD')"
              format="YYYY-MM-DD"
              @change="onDateChange"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="负责人"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <span>{{ project.ename }}</span>
          </a-form-item>
        </a-col>
      </a-row>
    </a-card>
    <a-card>
      <a-row :gutter="12">
        <a-form-item
          :labelCol="{xs: { span: 24 },sm: { span: 24 }}"
          :wrapperCol="{xs: { span: 24 },sm: { span: 24 }}"
        >
          <a-button
            type="primary"
            @click="handleAdd"
          >添加</a-button>
          <a-table
            bordered
            :dataSource="content.employeeData"
            :pagination="false"
            size="small"
            rowKey="enumber"
            :scroll="{y:480}"
          >
            <a-table-column
              title="序号"
              data-index="index"
              :width="60"
            >
            </a-table-column>
            <a-table-column
              title="姓名"
              data-index="ename"
              :width="60"
            >
            </a-table-column>
            <a-table-column
              title="部门"
              data-index="deptName"
              :width="200"
            >
            </a-table-column>
            <a-table-column
              title="职务"
              data-index="position"
              :width="200"
            >
            </a-table-column>
            <a-table-column
              title="项目角色"
              data-index="typeName"
              :width="110"
            >
            </a-table-column>
            <a-table-column
              title="备注"
              data-index="remark"
              :width="50"
            >
            </a-table-column>
            <a-table-column
              title="操作"
              data-index="index1"
              :width="50"
            >
              <template slot-scope="text,record">
                <a @click="handleDel(record)">移除</a>
              </template>
            </a-table-column>
          </a-table>
        </a-form-item>
      </a-row>
    </a-card>
    <a-card>
      <a-row :gutter="12">
        <a-col :span="8">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="编制"
          >
            <a-select
              showSearch
              :value="content.compilingName"
              placeholder="姓名"
              style="width:200px"
              :defaultActiveFirstOption="false"
              :showArrow="false"
              :filterOption="false"
              @search="handleSearch"
              @change="handleCompilingChange"
              :notFoundContent="null"
            >
              <a-select-option
                v-for="d in employeeList"
                :key="d.enumber"
              >{{ d.ename }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="审核"
          >
            <a-select
              showSearch
              :value="content.verifyName"
              placeholder="姓名"
              style="width:200px"
              :defaultActiveFirstOption="false"
              :showArrow="false"
              :filterOption="false"
              @search="handleSearch"
              @change="handleVerifyChange"
              :notFoundContent="null"
            >
              <a-select-option
                v-for="d in employeeList"
                :key="d.enumber"
              >{{ d.ename }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="批准"
          >
            <a-select
              showSearch
              :value="content.approveName"
              placeholder="姓名"
              style="width:200px"
              :defaultActiveFirstOption="false"
              :showArrow="false"
              :filterOption="false"
              @search="handleSearch"
              @change="handleApproveChange"
              :notFoundContent="null"
            >
              <a-select-option
                v-for="d in employeeList"
                :key="d.enumber"
              >{{ d.ename }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-card>
    <employee-Modal
      ref="employeeModal"
      @addMemberList="handleOk"
    ></employee-Modal>
  </a-form>
</template>
<script>
import moment from 'moment'
import employeeModal from './modules/EmployeeModal'
export default {
  name: 'MemberForm',
  components: { employeeModal },
  data () {
    return {
      employeeList: [],
      employeeMap: {},
      width: 1060,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      content: {
        approveName: undefined,
        verifyName: undefined,
        compilingName: undefined,
        d: moment().format('YYYY-MM-DD'),
        _pro: 0,
        _dept: undefined,
        _emp: undefined,
        employeeData: []
      },
      project: {}
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
    }
  },
  methods: {
    handleSearch (value) {
      this.$http.get('/project/getEmployeeList', { params: { ename: value } })
        .then(res => {
          const self = this
          this.employeeList = res.data
          this.employeeList.map(item => {
            var key = item.enumber
            self.employeeMap[key] = item
          })
        })
    },
    handleApproveChange (value) {
      if (this.employeeMap[value]) {
        this.content.approveName = this.employeeMap[value].ename
      }
    },
    handleVerifyChange (value) {
      if (this.employeeMap[value]) {
        this.content.verifyName = this.employeeMap[value].ename
      }
    },
    handleCompilingChange (value) {
      if (this.employeeMap[value]) {
        this.content.compilingName = this.employeeMap[value].ename
      }
    },
    moment,
    onDateChange (date, dateString) {
      this.content.d = dateString
    },
    handleAdd () {
      this.$refs.employeeModal.add(this.content._pro)
    },
    handleOk (items) {
      const self = this
      let list = [...this.content.employeeData]
      list.push(...items)
      const hash = {}
      let index = 0
      list = list.reduce(function (item, next) {
        if (!hash[next.enumber]) {
          hash[next.enumber] = true
          item.push(next)
        }
        index++
        next.index = index
        if (self.project.masterENumber === next.enumber) {
          next.type = 1
        }
        next.typeName = self.showEtype(next.type)
        return item
      }, [])
      this.content.employeeData = list
    },
    showEtype (text) {
      switch (text) {
        case 1:
          return '负责人'
        default:
          return '普通人员'
      }
    },
    handleDel (record) {
      const hash = {}
      let index = 0
      let list = [...this.content.employeeData]
      list = list.filter(item => { return item.enumber !== record.enumber })
      list = list.reduce(function (item, next) {
        if (!hash[next.enumber]) {
          hash[next.enumber] = true
          item.push(next)
        }
        index++
        next.index = index
        return item
      }, [])
      this.content.employeeData = list
    }
  }
}
</script>

<style>
</style>
