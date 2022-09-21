<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="名称">
        <a-input v-model="queryParam.masterName" placeholder="请输入名称" style="width: 180px" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="$refs.table.refresh(true)" v-if="$auth('patent:patentMaster:search')">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      highlight-hover-row
      show-overflow
      auto-resize
      ref="table"
      queryUrl="/patentMaster/queryMasterList"
      :params="queryParam"
      resizable
      :toolbar="{ zoom: true, custom:true, refresh:true }"
    >
      <template v-slot:buttons>
        <a-button
          type="primary"
          @click="$refs.addModal.add()"
          style="margin-right:10px"
          v-if="$auth('patent:patentMaster:add')"
        >添加</a-button>
      </template>
      <vxe-table-column
        type="seq"
        title="序号"
        width="60"
        align="center"
        header-align="center"
        fixed="left"></vxe-table-column>
      <vxe-table-column
        title="名称"
        field="masterName"
        min-width="200"
        remoteSort
        align="left"
        header-align="center"
        fixed="left"
      ></vxe-table-column>
      <vxe-table-column
        title="电话"
        field="linkTel"
        width="200"
        align="left"
        header-align="center"
        fixed="left"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="邮箱"
        field="linkEmail"
        width="200"
        align="left"
        header-align="center"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="地址"
        field="address"
        min-width="250"
        align="left"
        header-align="center"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="流程负责人"
        field="userName"
        min-width="250"
        align="left"
        header-align="center"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="备注"
        field="remark"
        align="left"
        header-align="center"
        width="200"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="创建时间"
        field="createTime"
        align="center"
        header-align="center"
        width="200"
        remoteSort
      >
        <!-- <template v-slot="{ row }">
          {{ row.createTime | DayFormat }}
        </template> -->
      </vxe-table-column>
      <vxe-table-column
        title="操作"
        field="action"
        width="150"
        fixed="right"
        align="center"
      >
        <template v-slot="{ row }">
          <a v-if="$auth('patent:patentMaster:edit')" @click="$refs.addModal.edit(row)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            v-if="$auth('patent:patentMaster:del')"
            title="是否确定删除?"
            @confirm="handleDel(row)"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <add-patent-master-model ref="addModal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { Ellipsis } from '@/components'
import AddPatentMasterModel from './modules/AddPatentMasterModel'
export default {
  name: 'PatentMaster',
  components: {
    Ellipsis,
    AddPatentMasterModel,
    ystable
  },
  data () {
    return {
      pagination: [],
      selectedRowKeys: [],
      loading: false,
      queryParam: {}
    }
  },
  methods: {
    handleDel (record) {
      this.$http.post('/patentMaster/del', { id: record.id })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除操作成功')
            this.$refs.table.refresh()
          } else {
            this.$message.warning(res.errorMessage)
          }
        })
    },
    // delBatch () {
    //   this.spinning = true
    //   const self = this
    //   this.$confirm({
    //     title: '您确定要删除选中的账号吗?',
    //     onOk () {
    //       return self.$http.post('/patentAccount/delBatch', self.entityList)
    //         .then(res => {
    //           if (res.success) {
    //             self.$message.success('删除成功')
    //             self.selectedRowKeys = []
    //             self.entityList = {}
    //             self.$refs.table.refresh()
    //           } else {
    //             self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
    //             self.selectedRowKeys = []
    //             self.entityList = {}
    //           }
    //           self.spinning = false
    //         })
    //     },
    //     onCancel () {
    //       self.spinning = false
    //     }
    //   })
    // },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(flag)
    },
    selectChange ({ records }) {
      this.entityList = records
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>
