<template>
  <a-spin :spinning="confirmLoading">
    <a-card :bordered="false">
      <div>
        <a-form layout="inline">
          <a-form-item label="角色名称">
            <a-input v-model="queryParam.roleName" placeholder="请输入角色名称" />
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="search()">查询</a-button>
          </a-form-item>
        </a-form>
      </div>
      <ystable
        ref="table"
        size="small"
        rowId="id"
        queryUrl="/rsRole/getList"
        :checkbox-config="{checkMethod:defaultCheck}"
        @checkbox-change="onSelectChange"
        @checkbox-all="onSelectChange"
        :params="queryParam"
        :columns="columns"
        @completed="(res)=>setSelect(res)"
      />
      <br />
      <span>
        <a-button type="primary" @click="handleSubmit()">保存</a-button>
      </span>
    </a-card>
  </a-spin>
</template>
<script>

import ystable from '@/components/Table/ystable'
import { Ellipsis } from '@/components'
export default {
  name: 'RoleSelectModal',
  components: {
    Ellipsis,
    ystable
  },
  props: {
    companyId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      deptId: 1,
      selectedRowKeys: [],
      title: '',
      queryParam: { roleName: '', companyId: this.companyId },
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
      confirmLoading: false,
      columns: [
        {
          type: 'checkbox',
          width: 40
        }, {
          title: '角色名称',
          field: 'roleName',
          key: 'roleName',
          showOverflow: 'tooltip',
          width: 200
        }, {
          title: '所属公司',
          field: 'companyName',
          key: 'companyName',
          showOverflow: 'tooltip',
          width: 300
        }, {
          title: '描述',
          field: 'remark',
          key: 'remark',
          showOverflow: 'tooltip',
          width: 300
        }, {
          title: '创建时间',
          field: 'createTime',
          key: 'createTime',
          showOverflow: 'tooltip',
          width: 180
        }
      ],
      form: this.$form.createForm(this),
      pageRoleIds: []
    }
  },
  methods: {
    defaultCheck ({ row }) {
      return !row.isvisible
    },
    setSelect (res) {
      const rs = res.data.data
      const selectRowsKeys = []
      const tempPageIds = []
      const selectRows = []
      for (let index = 0; index < rs.length; index++) {
        const element = rs[index]
        if (element.selectedRoleId) {
          selectRows.push(element)
          selectRowsKeys.push(element.selectedRoleId)
        }
        tempPageIds.push(element.id)
      }
      this.$refs.table.setCheckboxRow(selectRows, true)
      this.selectedRowKeys = selectRowsKeys
      this.pageRoleIds = tempPageIds
    },
    search () {
      this.$refs.table.refresh(true)
    },
    onSelectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    },
    assign (record) {
      const oldCompanyId = this.queryParam.companyId
      this.queryParam.roleName = ''
      this.queryParam.companyId = this.companyId
      this.title = '分配角色[' + record.companyName + ']'
      this.visible = true
      if (oldCompanyId > 0) {
        this.search()
      }
    },
    handleSubmit () {
      this.confirmLoading = true
      if (this.selectedRowKeys.length > 0) {
        this.$http.post('/rsRole/setCompanyRole', { companyId: this.queryParam.companyId, roleIds: this.selectedRowKeys, pageRoleIds: this.pageRoleIds })
          .then(res => {
            if (res.success && res.data) {
              this.$message.success('设置角色成功')
              this.$emit('success')
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : this.id === -1 ? '设置角色失败' : '更新失败')
            }
            this.visible = false
          }).finally(res => {
            this.confirmLoading = false
          })
      } else {
        this.$message.error('请选择要设置的角色')
        this.confirmLoading = false
      }
    },
    handleTableChange (pagination, filters, sorter) {
      this.pagination.pageSize = pagination.pageSize
      this.pagination.current = pagination.pageNo
      this.search()
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
