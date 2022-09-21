<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="合同编号">
        <a-input v-model="queryParam.contractNumber" placeholder="请输入合同编号" style="width:180px" />
      </a-form-item>
      <a-form-item label="客户名称">
        <a-input v-model="queryParam.customerName" placeholder="请输入客户名称" style="width:180px" />
      </a-form-item>
      <span v-if="$auth('sys:contractTraceability:search')" style="padding-left:30px;">
        <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
      </span>
    </a-form>
    <br/>
    <ystable
      ref="table"
      queryUrl="/contractTraceability/queryConTraceabilityList"
      :params="queryParam"
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :toolbar="tableToolbar"
    >
      <template v-slot:toolbar_buttons>
        <span style="margin-right:10px;">
          <a-button
            type="primary"
            v-if="$auth('sys:contractTraceability:add')"
            @click="$refs.createModal.add()"
          >
            添加
          </a-button>
        </span>
      </template>
      <vxe-table-column type="checkbox" width="40"/>
      <vxe-table-column
        title="合同编号"
        field="contractNumber"
        key="contractNumber"
        show-header-overflow
        :width="200"
        remoteSort
        show-overflow="tooltip"
      ></vxe-table-column>
      <vxe-table-column
        title="客户名称"
        field="customerName"
        key="customerName"
        show-header-overflow
        remoteSort
        show-overflow="tooltip"
      ></vxe-table-column>
      <vxe-table-column
        title="二维码"
        field="qrcode"
        key="qrcode"
        show-header-overflow
        :width="200"
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">
          <img :src="'/images'+row.qrcode" :width="38" :height="38" />
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="创建时间"
        field="createTime"
        key="createTime"
        show-header-overflow
        :width="180"
        remoteSort
        show-overflow="tooltip"
      ></vxe-table-column>
      <vxe-table-column
        title="操作"
        field="action"
        key="action"
        show-header-overflow
        :width="150"
        fixed="right"
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">
          <a v-if="$auth('sys:contractTraceability:edit')" @click="handleEdit(row)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            v-if="$auth('sys:contractTraceability:del')"
            title="是否确定删除?"
            @confirm="handleDel(row)"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <contractTraceability-modal ref="createModal" @ok="handleOk"></contractTraceability-modal>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable } from '@/components'
import ContractTraceabilityModal from './modules/ContractTraceabilityModal'

export default {
  name: 'ContractTraceability',
  components: {
    STable,
    ContractTraceabilityModal,
    ystable
  },
  data () {
    return {
      queryParam: {},
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        }
      }
    }
  },
  methods: {
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    handleDel (record) {
      this.$http.post('/contractTraceability/delete', { id: record.id }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.$refs.table.refresh(false)
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
    search (refresh) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>
