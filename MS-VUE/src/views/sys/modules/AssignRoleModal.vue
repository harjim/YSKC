<template>
  <a-modal
    :width="840"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="spinning"
    @cancel="visible = false"
    :zIndex="99"
  >
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-card :bordered="false">
        <div class="table-page-search-wrapper">
          <a-form layout="inline">
            <a-row :gutter="24">
              <a-col :md="12" :sm="24">
                <a-form-item label="角色名称">
                  <a-input v-model="queryParam.roleName" placeholder />
                </a-form-item>
              </a-col>
              <span>
                <a-button v-if="$auth('sys:role:search')" type="primary" @click="search()">查询</a-button>
              </span>
            </a-row>
          </a-form>
        </div>
        <a-form @submit="handleSubmit" :form="form">
          <ystable
            queryUrl="/role/getAppRoleList"
            :params="queryParam"
            highlight-hover-row
            show-overflow
            resizable
            ref="table"
            :checkbox-config="{ range: true }"
            @checkbox-all="selectChangeEvent"
            @checkbox-change="selectChangeEvent"
            row-id="id"
            @completed="({data:{data}})=>{ tableData=data;setSelected(); }"
          >
            <vxe-table-column type="checkbox" width="50" align="center"></vxe-table-column>
            <vxe-table-column field="roleName" title="角色名称" width="150" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="remark" title="描述" width="150" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="creatorName" title="创建人" width="150" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="createTime" title="创建时间" align="center" remoteSort></vxe-table-column>
          </ystable>
        </a-form>
      </a-card>
    </a-spin>
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import ystable from '@/components/Table/ystable'
export default {
  name: 'AssignRoleModal',
  components: {
    STable,
    Ellipsis,
    ystable
  },

  data () {
    return {
      pageRoleIds: [],
      defaultRoleIds: [],
      userIds: undefined,
      deptId: 1,
      selectedRowKeys: [],
      selectedRows: [],
      advanced: false,
      data: {},
      title: '',
      queryParam: { roleName: '' },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      id: 0,
      visible: false,
      spinning: false,
      loading: false,
      tableToolbar: {
        perfect: true,
        refresh: true,
        zoom: true,
        custom: true
      },
      form: this.$form.createForm(this),
      tableData: []
    }
  },
  computed: {
    userInfo () {
      return this.$store.getters.userInfo
    }
  },
  created () {
  },
  methods: {
    setSelected () {
      this.selectedRowKeys = this.defaultRoleIds
      const selectRows = []
      const pageIds = []
      this.$refs.table.setAllCheckboxRow(false)
      this.tableData.forEach((item, index) => {
        pageIds.push(item.id)
        if (this.defaultRoleIds.includes(item.id)) {
          selectRows.push(item)
        }
      })
      this.pageRoleIds = pageIds
      this.$refs.table.setCheckboxRow(selectRows, true)
    },
    search () {
      this.$refs.table.refresh(true)
    },
    assign (record) {
      this.userIds = undefined
      this.data = record
      this.queryParam.roleName = ''
      this.title = '分配用户[' + record.userName + ']角色'
      this.visible = true
      this.$nextTick(() => {
        this.$http.get('/role/getUserRoleIds', { params: { userId: this.data.id } })
          .then(res => {
            if (res.success) {
              this.defaultRoleIds = res.data.roleIds
              this.setSelected()
            }
          })
      })
    },
    setRoles (ids) {
      this.defaultRoleIds = []
      this.userIds = ids
      this.queryParam.roleName = ''
      this.title = '分配用户角色'
      this.selectedRowKeys = []
      this.visible = true
    },
    handleSubmit () {
      this.spinning = true
      if (this.userIds) {
        this.$http.post('/role/setUserRoles', { userIds: this.userIds, roleIds: this.selectedRowKeys })
          .then(res => {
            if (res.data && res.success) {
              this.visible = false
              this.$emit('ok', true)
              this.selectedRowKeys = []
            } else {
              this.$message.error('设置角色失败')
            }
          }).finally(res => {
            this.spinning = false
          })
      } else {
        this.$http.post('/role/setUserRole', { userId: this.data.id, roleIds: this.selectedRowKeys })
          .then(res => {
            if (res.data && res.success) {
              this.visible = false
              this.$emit('ok', true)
              this.selectedRowKeys = []
            } else {
              this.$message.error('设置角色失败')
            }
          }).finally(res => {
            this.spinning = false
          })
      }
    },
    selectChangeEvent ({ checked, records }) {
      const tempDefaultRoleId = this.defaultRoleIds.filter(id => !this.pageRoleIds.includes(id)).map(id => id)
      this.selectedRowKeys = [...tempDefaultRoleId, ...records.map(item => { return item.id })]
    }
  }
}
</script>
</script>
<style lang="less" scoped>
.setrole {
  width: 100%;
}
</style>
