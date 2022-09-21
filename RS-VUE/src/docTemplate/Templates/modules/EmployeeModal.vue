<!-- 立项评审报告 -->
<template>
  <a-modal
    :width="900"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :footer="null"
    :confirmLoading="confirmLoading"
    :getContainer="getContainer"
  >
    <a-spin tip="请稍后..." :spinning="spinning">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="6" :sm="24">
              <a-form-item label="姓名" >
                <a-input v-model="queryParam.ename" placeholder="请输入姓名"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="工号">
                <a-input v-model="queryParam.enumber" placeholder="请输入工号"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="人员类型">
                <a-select placeholder="请选择" default-value="-1" @select="onSelect">
                  <a-select-option value="-1">请选择</a-select-option>
                  <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-button type="primary" @click="search">查询</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <div>
        <s-table
          :useLoading="false"
          ref="table"
          bordered
          rowKey="enumber"
          :data="initializeData"
          :scroll="{x: 1000 ,y: 500}"
          :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange,getCheckboxProps:getCheckboxProps }"
        >
          <a-table-column align="center" title="姓名" data-index="ename" key="ename" :width="100"></a-table-column>
          <a-table-column
            align="center"
            title="工号"
            data-index="enumber"
            key="enumber"
            :width="100"
          />
          <a-table-column
            v-if="isShowEType"
            align="center"
            title="人员类型"
            data-index="etype"
            key="etype"
            :width="100">
            <template slot-scope="text">
              <span>{{ text && text !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === text).label : '' }}</span>
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="部门"
            data-index="deptName"
            key="deptName"
            :width="100"
          >
            <template slot-scope="text,record">
              {{ record.rdDeptName || record.deptName }}
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="职位"
            data-index="position"
            key="position"
            :width="100"
          />
          <a-table-column
            align="center"
            title="专业"
            data-index="specialities"
            key="specialities"
            :width="120"
          />
        </s-table>
        <div style="text-align: right;margin-top:10px">
          <a-button type="primary" @click="addSubmit">添加</a-button>
        </div>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import { STable, Ellipsis, SelectDown, TreeKeyMap } from '@/components'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import moment from 'moment'
export default {
  name: 'EmployeeModal',
  components: {
    STable,
    Ellipsis,
    SelectDown,
    TreeKeyMap
  },
  watch: {
    allSelectedRows (newRows) {
    }
  },
  data () {
    return {
      domName,
      spinning: false,
      loadFirst: false,
      submitDisabled: true,
      mdl: {},
      mySelectedKeys: [],
      options: {},
      selectItem: {},
      selectedRowKeys: [],
      selectedRows: [],
      allSelectedRows: [],
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      queryParam: { deptId: undefined, isMember: '-1', month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'), projectId: 0, ename: null, enumber: null, rdDeptId: null },
      parameter: {},
      visible: false,
      confirmLoading: false,
      initializeData: this.loadData,
      form: this.$form.createForm(this),
      rdYear: undefined,
      enumbers: [],
      pDocFileId: undefined,
      docDate: undefined
    }
  },
  created () {
  },
  props: {
    month: {
      type: String,
      default: ''
    },
    isShowEType: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    getContainer () {
      return popupContainer(this.domName)
    },
    loadData (parameter) {
      this.spinning = true
      this.parameter = parameter
      this.parameter.pDocFileId = this.pDocFileId
      this.parameter.docDate = this.docDate
      if (this.docDate) { this.parameter.docDate = this.docDate }
      return this.$http.get('/reviewCommittee/getReviewList', { params: Object.assign(parameter, this.queryParam) })
        .then(res => {
          if (res.success) {
            if (res.data != null && res.data.footer != null) {
              this.item = res.data.footer
            }
            return res.data
          } else {
            return {
              pageNo: 1,
              pageSize: 10,
              totalCount: 0,
              totalPage: 0,
              data: []
            }
          }
        }).finally(res => {
          this.spinning = false
        })
    },
    addSubmit () {
      this.$emit('addMemberList', this.getPostItems())
      this.visible = false
    },
    onSelect (value) {
      this.queryParam.etype = value
    },
    rdDeptSelected (rdDeptPath) {
      this.queryParam.rdDeptPath = rdDeptPath
    },
    search () {
      this.selectedRowKeys = []
      this.selectedRows = []
      this.selectItem = {}
      this.allSelectedRows = []
      this.mySelectedKeys = []
      this.$refs.table.refresh(true)
    },
    moment,
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
      this.concatSelectRows(selectedRows)
    },
    concatSelectRows (selectedRows) {
      this.allSelectedRows.push(...selectedRows)
      this.allSelectedRows = Array.from(new Set(this.allSelectedRows))
    },
    getPostItems () {
      var itemRows = []
      for (var i = 0; i < this.allSelectedRows.length; i++) {
        var item = this.allSelectedRows[i]
        if (this.selectedRowKeys.indexOf(item.enumber) !== -1) {
          itemRows.push(item)
        }
      }
      return itemRows
    },
    add (projectId, year, enumbers, docDate, pDocFileId) {
      this.enumbers = enumbers
      this.rdYear = year
      this.confirmLoading = false
      this.docDate = docDate
      this.pDocFileId = pDocFileId
      this.queryParam.deptId = undefined
      this.queryParam.rdDeptId = undefined
      this.queryParam.etype = -1
      this.queryParam.minPay = 0
      this.queryParam.maxPay = 0
      this.queryParam.projectId = projectId
      this.selectedRowKeys = []
      this.selectedRows = []
      this.allSelectedRows = []
      this.title = '选择项目人员'
      this.visible = true
      if (this.$refs.deptSelet) {
        this.$refs.deptSelet.setValue(0)
      }
      if (this.$refs.rdDeptSelet) {
        this.$refs.rdDeptSelet.setValue(0)
      }
      this.$nextTick(() => {
        this.queryParam.ename = null
        this.queryParam.enumber = null
        this.queryParam.rdDeptId = undefined
        this.queryParam.deptId = undefined
        this.queryParam.rdDeptPath = undefined
        if (this.loadFirst) {
          this.$refs.table.refresh(true)
        }
        this.loadFirst = true
      })
    },
    handleSubmit () {
      this.spinning = true
      this.confirmLoading = true
      var value = {}
      value.projectEmployeeModelList = this.getPostItems()
      value.projectId = this.queryParam.projectId
      this.$http.post('/projectAttendance/addDataList', value).then(res => {
        if (res.success && res.data) {
          this.visible = false
          this.$emit('ok')
          this.confirmLoading = false
        } else {
          this.$emit('error', res.errorMessage)
          this.confirmLoading = false
        }
      }).finally(res => {
        this.confirmLoading = false
        this.spinning = false
      })
    },
    getCheckboxProps (record) {
      if (this.enumbers.indexOf(record.enumber) >= 0) {
        return {
          props: {
            disabled: true,
            multiple: record.multiple
          }

        }
      }
      return {
        props: {
          disabled: false,
          multiple: record.multiple
        }
      }
    }
  }
}
</script>

<style scoped>
</style>
