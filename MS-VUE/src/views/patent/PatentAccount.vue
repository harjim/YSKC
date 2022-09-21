<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="用户名">
        <a-input v-model="username" placeholder="请输入用户名" style="width: 180px" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="$refs.table.refresh(true)" v-if="$auth('patent:patentAccount:search')">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      highlight-hover-row
      show-overflow
      ref="table"
      queryUrl="/patentAccount/queryPatentAccountList"
      :params="getParam()"
      @completed="completed"
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :toolbar="{ zoom: true, custom:true, refresh:true }"
    >
      <template v-slot:buttons>
        <a-button
          type="primary"
          @click="$refs.addModal.add()"
          style="margin-right:10px"
          v-if="$auth('patent:patentAccount:add')"
        >添加</a-button>
        <a-button
          :disabled="selectedRowKeys.length <= 0"
          type="primary"
          @click="delBatch"
          style="margin-right:10px"
          v-if="$auth('patent:patentAccount:del')"
        >删除</a-button>
      </template>
      <vxe-table-column type="checkbox" width="40" fixed="left" />
      <vxe-table-column
        title="用户名"
        field="username"
        width="150"
        remoteSort
        align="center"
        fixed="left"
      ></vxe-table-column>
      <vxe-table-column
        title="密码"
        field="password"
        width="150"
        align="center"
        fixed="left"
      ></vxe-table-column>
      <!-- <vxe-table-column
        title="状态"
        field="status"
        width="100"
        remoteSort
        align="center"
      >
        <template v-slot="{ row }">
          <span v-if=" row.status ===1">未使用</span>
          <span v-else-if="row.status===2">可使用</span>
          <span v-else>不可用</span>
        </template>
      </vxe-table-column> -->
      <vxe-table-column
        title="备注"
        field="remark"
        min-width="150"
      ></vxe-table-column>
      <vxe-table-column
        title="操作"
        field="action"
        width="150"
        fixed="right"
        align="center"
      >
        <template v-slot="{ row }">
          <a v-if="$auth('patent:PatentAccount:edit')" @click="$refs.addModal.edit(row)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            v-if="$auth('patent:patentAccount:del')"
            title="是否确定删除?"
            @confirm="handleDel(row)"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <!-- <s-table
      rowKey="id"
      ref="table"
      :loading="loading"
      size="default"
      :data="getData"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
    >
      <a-table-column title="用户名" data-index="username" key="username" align="left">
        <template slot-scope="text">
          <span>{{ text }}</span>
        </template>
      </a-table-column>
      <a-table-column title="密码" data-index="password" key="password" align="left">
        <template slot-scope="text">
          <span>{{ text }}</span>
        </template>
      </a-table-column>
      <a-table-column title="状态" data-index="status" key="status" align="left">
        <template slot-scope="text,record">
          <span v-if=" record.status ===1">未使用</span>
          <span v-else-if="record.status===2">可使用</span>
          <span v-else>不可用</span>
        </template>
      </a-table-column>
      <a-table-column title="备注" data-index="remark" key="remark" align="left">
        <template slot-scope="text">
          <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
        </template>
      </a-table-column>
      <a-table-column align="center" title="操作" data-index="action" key="action" :width="250">
        <template slot-scope="text,record">
          <a
            href="javascript:;"
            @click="$refs.addModal.edit(record)"
            v-if="$auth('patent:patentList:edit')"
          >编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            title="是否确定删除?"
            @confirm="handleDel(record)"
            v-if="$auth('patent:patentAccount:del')"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </a-table-column>
    </s-table> -->
    <addPatentAccount-modal ref="addModal" @ok="handleOk"></addPatentAccount-modal>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
import AddPatentAccountModal from './modules/AddPatentAccountModal'
export default {
  name: 'PatentAccount',
  components: {
    STable,
    Ellipsis,
    AddPatentAccountModal,
    ystable
  },
  data () {
    return {
      pagination: [],
      username: undefined,
      selectedRowKeys: [],
      // getData: parameter => {
      //   return this.$http.get('/patentAccount/queryPatentAccountList', { params: Object.assign(parameter, { username: this.username }) })
      //     .then(res => {
      //       return res.data
      //     })
      // },
      loading: false,
      queryParam: {}
    }
  },
  methods: {
    getParam () {
      this.queryParam.username = this.username
      return this.queryParam
    },
    // onSelectChange (selectedRowKeys, selectedRows) {
    //   this.selectedRowKeys = selectedRowKeys
    //   this.entityList = selectedRows
    // },
    handleDel (record) {
      this.$http.post('/patentAccount/delPatentAccount', { id: record.id })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.$refs.table.refresh()
          } else {
            this.$message.info(res.errorMessage)
          }
        })
    },

    delBatch () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的账号吗?',
        onOk () {
          return self.$http.post('/patentAccount/delBatch', self.entityList)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys = []
                self.entityList = {}
                self.$refs.table.refresh()
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
                self.selectedRowKeys = []
                self.entityList = {}
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
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
    },
    completed () {
      this.entityList = []
      this.selectedRowKeys = []
    }
  }
}
</script>
