<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    :footer="null"
    @cancel="closeModal"
  >
    <a-form
      layout="inline"
      class="dept_user_search"
      :form="form"
    >
      <a-form-item label="姓名">
        <a-input
          v-model="queryParams.realName"
          placeholder="请输入姓名"
        />
      </a-form-item>
      <a-form-item label="所属部门">
        <dept-select-only
          ref="deptSelect"
          @deptFullPath="path=>queryParams.fullPath = path"
          style="width:180px;"
        />
      </a-form-item>
      <span>
        <a-button
          type="primary"
          @click="search(true)"
        >查询</a-button>
      </span>
    </a-form>
    <div>
      <ystable
        ref="table"
        queryUrl="/policySource/getSourceUserList"
        :params="getParam()"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        :toolbar="{ zoom: true, custom:true, refresh:true}"
      >
        <template v-slot:buttons>
          <a-button
            v-if="$auth('sys:policySource:addSubs')"
            type="primary"
            @click="$refs.sourceUser.show(sourceId,sname)"
          >添加</a-button>
        </template>
        <vxe-table-column
          title="用户名"
          remoteSort
          align="center"
          field="userName"
          key="userName"
          show-header-overflow
          show-overflow="tooltip"
          :width="140"
        />
        <vxe-table-column
          title="姓名"
          align="center"
          field="realName"
          key="realName"
          show-header-overflow
          show-overflow="tooltip"
          :width="100"
        />
        <vxe-table-column
          title="所属部门"
          remoteSort
          align="center"
          field="deptName"
          key="deptName"
          show-header-overflow
          show-overflow="tooltip"
          :width="220"
        />
        <vxe-table-column
          title="电话"
          align="center"
          field="tel"
          key="tel"
          show-header-overflow
          show-overflow="tooltip"
          :width="140"
        />
        <vxe-table-column
          title="职位"
          remoteSort
          align="center"
          field="postion"
          key="postion"
          show-header-overflow
          show-overflow="tooltip"
          :width="140"
        />
        <vxe-table-column
          title="备注"
          align="center"
          field="remark"
          key="remark"
          show-header-overflow
          show-overflow="tooltip"
          :width="140"
          remoteSort
        />
      </ystable>
      <!-- <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="columns"
        :data="getData"
        showPagination="auto"
      >
        <span slot="deptName" slot-scope="text">
          <ellipsis :length="36" tooltip>{{ text }}</ellipsis>
        </span>
        <span slot="remark" slot-scope="text">
          <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
        </span>
      </s-table> -->
    </div>
    <AddSourceUserModal ref="sourceUser" @ok="search"/>
  </a-modal>
</template>

<script>
import AddSourceUserModal from './AddSourceUserModal'
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
import DeptSelectOnly from '@/components/DeptSelectOnly'
// 用户表格
// const columns = [{
//   dataIndex: 'userName',
//   key: 'userName',
//   title: '用户名',
//   align: 'left',
//   width: 100
// }, {
//   dataIndex: 'realName',
//   key: 'realName',
//   title: '姓名',
//   align: 'left',
//   width: 80
// }, {
//   dataIndex: 'deptName',
//   key: 'deptName',
//   title: '所属部门',
//   align: 'left',
//   scopedSlots: { customRender: 'deptName' },
//   width: 280
// }, {
//   title: '电话',
//   dataIndex: 'tel',
//   key: 'tel',
//   align: 'left',
//   width: 100
// }, {
//   title: '职位',
//   dataIndex: 'postion',
//   key: 'postion',
//   align: 'left',
//   width: 100
// }, {
//   title: '备注',
//   key: 'remark',
//   dataIndex: 'remark',
//   align: 'left',
//   scopedSlots: { customRender: 'remark' },
//   width: 100
// }]
export default {
  name: 'TabulationByRoleId',
  components: {
    STable,
    Ellipsis,
    DeptSelectOnly,
    ystable,
    AddSourceUserModal
  },
  props: {
    sourceId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      queryParams: {},
      title: '订阅列表',
      visible: false,
      sname: '',
      // columns,
      first: true,
      form: this.$form.createForm(this)
      // getData: parameter => {
      //   this.queryParams.sourceId = this.sourceId
      //   return this.$http.get('/policySource/getSourceUserList', { params: Object.assign(parameter, this.queryParams) })
      //     .then(res => {
      //       return res.data
      //     })
      // }
    }
  },
  methods: {
    getParam () {
      this.queryParams.sourceId = this.sourceId
      return this.queryParams
    },
    search () {
      this.$refs.table.refresh(true)
    },
    showModal (sname) {
      this.queryParams = {}
      if (!this.first) {
        this.search()
      }
      this.title = `[${sname}]订阅列表`
      this.sname = sname
      this.first = false
      this.visible = true
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    },
    closeModal () {
      this.visible = false
    }
  }
}
</script>
