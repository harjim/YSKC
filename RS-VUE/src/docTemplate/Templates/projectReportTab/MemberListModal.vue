<!--
 * @Author: ldx
 * @Date: 2021-03-15 17:17:07
 * @LastEditTime: 2021-05-25 18:02:48
 * @LastEditors: ldx
 * @Description:  获取成员列表
 * @FilePath: \RS-VUE\src\docTemplate\Templates\projectReportTab\MemberListModal.vue
-->
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
        <a-form-item label="项目角色">
          <a-input
            v-model="queryParam.role"
            style="width:130px;"
            @pressEnter="search"
            placeholder="请输入项目角色"
          />
        </a-form-item>

        <a-form-item >
          <a-button type="primary" @click="search">查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <ystable
      ref="table"
      queryUrl="/initMember/getAllStaff"
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
      <vxe-table-column field="role" title="项目角色" width="110" remoteSort />
      <vxe-table-column field="etype" title="人员类型" width="110" align="center" remoteSort>
        <template v-slot="{ row }">{{ row.etype != null && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : '' }}</template>
      </vxe-table-column>
      <vxe-table-column field="deptName" title="部门" width="110" remoteSort></vxe-table-column>
      <vxe-table-column field="rdDeptName" title="研发部门" width="110" remoteSort />
      <vxe-table-column field="birthday" title="出生日期" width="120" align="center" remoteSort>
        <template v-slot="{ row }">{{ row.birthday | formatDate }}</template>
      </vxe-table-column>
      <vxe-table-column field="title" title="职称" width="110" remoteSort />
      <vxe-table-column field="specialities" title="专业" width="110" remoteSort />
      <vxe-table-column field="edate" title="入职日期" width="120" align="center" remoteSort>
        <template v-slot="{ row }">{{ row.edate | formatDate }}</template>
      </vxe-table-column>
    </ystable>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import { TreeKeyMap } from '@/components'

export default {
  name: 'MemberModal',
  components: {
    ystable,
    TreeKeyMap
  },
  props: {
    memberList: {
      type: Array,
      default: () => { return [] }
    }
  },
  data () {
    return {
      selectedRowKeys: [],
      rowKeyObj: {},
      // etypes,
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
      form: this.$form.createForm(this),
      memberIds: []
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
      this.memberIds = []
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
    showModal (project, memberIds, pDocFileId) {
      this.queryParam = {}
      this.queryParam.projectId = project.id
      this.queryParam.existIds = memberIds
      this.queryParam.pDocFileId = pDocFileId
      this.memberIds = memberIds
      this.title = `添加项目成员[${project.pname}] [RD${project.rdIndex}]`
      this.visible = true
      this.confirmLoading = false
      this.$nextTick(() => {
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
      this.selectedRowKeys.forEach((item) => {
        this.memberIds.push(item)
      })
      this.confirmLoading = false
      this.$emit('refresh', this.memberIds)
      this.closeModal()
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
      console.log('this.selectedRowKeys', this.selectedRowKeys)
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
