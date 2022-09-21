<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="closeModal"
  >
    <div>
      <a-form layout="inline" :form="form">
        <a-form-item label="姓名">
          <a-input
            v-model="queryParam.ename"
            @pressEnter="search"
            style="width:130px;"
            placeholder="请输入姓名"
          />
        </a-form-item>
        <a-form-item label="工号">
          <a-input
            v-model="queryParam.enumber"
            style="width:130px;"
            @pressEnter="search"
            placeholder="请输入工号"
          />
        </a-form-item>
        <a-form-item label="职位">
          <a-input
            v-model="queryParam.position"
            style="width:130px;"
            @pressEnter="search"
            placeholder="请输入职位"
          />
        </a-form-item>
        <a-form-item label="人员类型">
          <a-select v-model="queryParam.etype" style="width:130px;" placeholder="请选择人员类型">
            <a-select-option value="-1">请选择人员类型</a-select-option>
            <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="备注">
          <a-input style="width:130px;" v-model="queryParam.remark" placeholder="请输入备注" />
        </a-form-item>
        <a-form-item v-if="$auth('project:report:member:add')">
          <a-button type="primary" @click="search">查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <ystable
      ref="table"
      queryUrl="/initMember/getEmployeeList"
      :params="queryParam"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      @checkbox-all="selectCheckBoxChange"
      @checkbox-change="selectCheckBoxChange"
      :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
    >
      <template v-slot:toolbar_buttons>
        <span>
          当前选中个数：
          <a>{{ selectedRowKeys.length }}</a>
        </span>
      </template>
      <vxe-table-column type="checkbox" width="40" align="center" fixed="left" />
      <vxe-table-column field="enumber" title="工号" width="110" fixed="left" remoteSort />
      <vxe-table-column field="ename" title="姓名" width="110" fixed="left" remoteSort />
      <vxe-table-column field="position" title="职位" width="110" remoteSort />
      <vxe-table-column field="etype" title="人员类型" width="110" align="center" remoteSort>
        <template v-slot="{row}">{{ row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : '' }}</template>
      </vxe-table-column>
      <vxe-table-column field="deptName" title="部门" width="110" remoteSort></vxe-table-column>
      <vxe-table-column field="rdDeptId" title="研发部门" width="110" remoteSort>
        <template v-slot="{ row }">{{ keyMap[row.rdDeptId] }}</template>
      </vxe-table-column>
      <vxe-table-column field="birthday" title="出生日期" width="120" align="center" remoteSort>
        <template v-slot="{ row }">{{ row.birthday | formatDate }}</template>
      </vxe-table-column>
      <vxe-table-column field="title" title="职称" width="110" remoteSort />
      <vxe-table-column field="specialities" title="专业" width="110" remoteSort />
      <vxe-table-column field="edate" title="入职日期" width="120" align="center" remoteSort>
        <template v-slot="{ row }">{{ row.edate | formatDate }}</template>
      </vxe-table-column>
      <vxe-table-column field="remark" title="备注" min-width="180" align="left" remoteSort></vxe-table-column>
    </ystable>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'

export default {
  name: 'MemberModal',
  components: {
    ystable
  },
  data () {
    return {
      keyMap: {},
      selectedRowKeys: [],
      rowKeyObj: {},
      title: '',
      year: undefined,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      queryParam: {},
      visible: false,
      confirmLoading: false,
      firstLoad: false,
      form: this.$form.createForm(this)
    }
  },
  filters: {
    formatDate (value) {
      if (!value) return ''
      return moment(value).format('YYYY-MM-DD')
    }
  },
  methods: {
    moment,
    closeModal () {
      this.visible = false
      this.selectedRowKeys = []
      this.rowKeyObj = {}
      this.resetDept()
    },
    resetDept () {
      this.queryParam.deptId = -1
      if (this.$refs.deptSelet) {
        this.$refs.deptSelet.setValue(0)
      }
    },
    onSelectChange (selectedRowKeys, rows) {
      this.selectedRowKeys = selectedRowKeys
      rows.forEach(r => {
        this.rowKeyObj[r.id] = r
      })
    },
    getPostItems () {
      var itemRows = []
      for (var i = 0; i < this.selectedRowKeys.length; i++) {
        const id = this.selectedRowKeys[i]
        const item = this.rowKeyObj[id]
        if (item) {
          itemRows.push(item.enumber)
        }
      }
      return itemRows
    },
    showModal (projectId, pname, rdTitle, year) {
      this.queryParam = {}
      this.queryParam.projectId = projectId
      this.queryParam.year = year
      this.title = `添加项目成员[${pname}-${rdTitle}]`
      this.visible = true
      this.confirmLoading = false
      this.$nextTick(() => {
        this.$getTree('rdDept', false, year).then(res => {
          this.keyMap = res.keyMap
        })
        if (this.firstLoad) {
          this.search()
        }
        this.firstLoad = true
      })
    },
    search () {
      this.$refs.table.refresh(true)
    },
    handleSubmit () {
      this.confirmLoading = true
      var value = {}
      value.enumbers = this.getPostItems()
      if (value.enumbers === null || value.enumbers.length <= 0) {
        this.$emit('error', '未选择任何人员')
        this.confirmLoading = false
        return
      }
      value.projectId = this.queryParam.projectId
      value.year = this.queryParam.year
      this.$http.post('/initMember/addList', value).then(res => {
        if (res.success && res.data) {
          this.closeModal()
          this.$emit('ok')
          this.confirmLoading = false
        } else {
          this.$emit('error', res.errorMessage ? res.errorMessage : '添加失败')
          this.confirmLoading = false
        }
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
      records.forEach(r => {
        this.rowKeyObj[r.id] = r
      })
    }
  }
}
</script>

<style>
.word-wrap {
  word-break: break-all;
}
</style>
