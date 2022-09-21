<!--
    * @Author: hm
    * @Date: 2022-09-03 08:55:29
    * @Description: 专利供应商
-->
<template>
  <a-card :bordered="false">
    <a-form id="searchForm" layout="inline" class="form">
      <a-form-item label="供应商" ><a-input v-model="params.supplier" allowClear placeholder="请输入供应商" /></a-form-item>
      <a-form-item label="户名" ><a-input v-model="params.accountName" allowClear placeholder="请输入户名" /></a-form-item>
      <a-form-item label="账号" ><a-input v-model="params.accountNumber" allowClear placeholder="请输入账号" /></a-form-item>
      <a-form-item label="联系人" ><a-input v-model="params.linkman" allowClear placeholder="请输入联系人" /></a-form-item>
      <a-form-item label="联系电话" ><a-input v-model="params.linkTel" allowClear placeholder="请输入联系电话" /></a-form-item>
      <a-button v-if="$auth('patent:patentSupplier:search')" @click="search(true)" type="primary">查询</a-button>
    </a-form>
    <div class="table">
      <ystable
        highlight-hover-row
        show-overflow
        auto-resize
        resizable
        max-height="100%"
        queryUrl="/patentSupplier/getList"
        :params="params"
        :toolbar="{ zoom: true, custom:true, refresh:true }"
        :columns="columns"
        ref="table"
      >
        <template #buttons>
          <a-button type="primary" v-if="$auth('patent:patentSupplier:add')" @click="showSave()">添加</a-button>
        </template>
        <template #operate="{ row }">
          <a v-if="$auth('patent:patentSupplier:edit')" @click="showSave(row)">编辑</a>
          <a-divider v-if="$auth('patent:patentSupplier:edit') && $auth('patent:patentSupplier:del')" type="vertical" />
          <a-popconfirm
            title="确定删除当前记录?"
            ok-text="确定"
            cancel-text="取消"
            @confirm="delSupplier(row.id)"
          >
            <a v-if="$auth('patent:patentSupplier:del')">删除</a>
          </a-popconfirm>
        </template>
      </ystable>
    </div>
    <AddPatentSupplierModal ref="addSupplier" @refresh="search" />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import AddPatentSupplierModal from './modules/AddPatentSupplierModal'
export default {
  data () {
    return {
      columns: [
        {
          type: 'seq',
          width: 50,
          title: '序号',
          fixed: 'left'
        },
        {
          title: '供应商',
          field: 'supplier',
          align: 'left',
          width: 200,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          fixed: 'left'
        },
        {
          title: '户名',
          field: 'accountName',
          align: 'left',
          width: 200,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          fixed: 'left'
        },
        {
          title: '账号',
          field: 'accountNumber',
          align: 'left',
          width: 200,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '联系人',
          field: 'linkman',
          align: 'left',
          width: 150,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '联系电话',
          field: 'linkTel',
          align: 'left',
          width: 150,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '备注',
          field: 'remark',
          align: 'left',
          width: 230,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '创建时间',
          field: 'createTime',
          align: 'center',
          remoteSort: true,
          width: 150,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '操作',
          align: 'center',
          width: 100,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          fixed: 'right',
          slots: { default: 'operate' }
        }
      ],
      params: {},
      addVisible: false,
      isAdd: true,
      supplierInfo: {}
    }
  },

  components: {
    ystable,
    AddPatentSupplierModal
  },

  methods: {
    showSave (info = null) {
      this.$refs.addSupplier.open(info)
    },
    search (refresh = true) {
      Object.keys(this.params).forEach(key => {
        this.params[key] = this.params[key].trim()
      })
      this.$refs.table.refresh(refresh)
    },
    delSupplier (supplierId) {
      this.$http.post('/patentSupplier/delSupplier', { id: supplierId }).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
      }).catch(res => {
        this.$message.error(res.errorMessage || '删除失败')
      })
    }
  }
}
</script>

<style lang='less' scoped>
  /deep/ #searchForm {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
  }
  /deep/ #searchForm .ant-form-item {
    min-width: 260px;
    display: flex;
  }

  /deep/ #searchForm .ant-form-item-control-wrapper {
    flex-grow: 1;
  }

  /deep/ .ant-card-body {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 189px);
    min-height: 340px;
  }
  .form {
    height: auto;
    flex-shrink: 0;
  }
  .table {
    flex: 1;
    overflow: hidden;
  }
</style>
