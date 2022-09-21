<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="类型">
        <a-select style="width:160px;" v-model="queryParam.type" placeholder="请选择类型" allowClear>
          <a-select-option v-for="(v, k) in types" :key="k" :value="k">{{ v }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="键">
        <a-input v-model="queryParam.key" placeholder="请输入键" style="width:180px" />
      </a-form-item>
      <a-form-item label="值">
        <a-input v-model="queryParam.value" placeholder="请输入值" style="width:180px" />
      </a-form-item>
      <a-form-item label="父节点">
        <a-input v-model="queryParam.parentKeyValue" placeholder="请输入父节点" style="width:180px" />
      </a-form-item>

      <span>
        <a-button v-if="$auth('sys:dictionary:search')" type="primary" @click="search()">查询</a-button>
      </span>
    </a-form>
    <ystable
      queryUrl="/sysDictionary/queryList"
      :params="queryParam"
      highlight-hover-row
      show-overflow
      resizable
      ref="table"
      @completed="completed"
      :toolbar="tableToolbar"
      @checkbox-all="selectAllEvent"
      @checkbox-change="selectChangeEvent"
    >
      <template v-slot:toolbar_buttons>
        <span style="margin-right:10px;">
          <a-button type="primary" v-if="$auth('sys:dictionary:add')" @click="$refs.addDictionary.add()">添加</a-button>
        </span>
        <span style="margin-right:10px;">
          <a-popconfirm title="是否确定删除?" @confirm="delAll">
            <a-button type="primary" v-if="$auth('sys:dictionary:del')">删除</a-button>
          </a-popconfirm>
        </span>
      </template>
      <vxe-table-column type="checkbox" width="50" align="center" fixed="left"></vxe-table-column>
      <vxe-table-column
        field="type"
        title="类型"
        width="150"
        align="center"
        remoteSort
      ><template v-slot="{ row }">{{ types[row.type] }}</template></vxe-table-column
      >
      <vxe-table-column field="key" title="键" width="150"></vxe-table-column>
      <vxe-table-column field="value" title="值" width="150" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="parentKeyValue" title="父节点" width="180" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="order" title="排序" width="180" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="remark" title="备注" width="180" align="center" remoteSort></vxe-table-column>
      <vxe-table-column title="操作" width="250" align="center">
        <template v-slot="{ row }">
          <a v-if="$auth('sys:dictionary:edit')" @click="$refs.addDictionary.edit(row)">编辑</a>
          <template v-if="$auth('sys:dictionary:del')">
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
              <a-divider v-if="$auth('sys:dictionary:edit')" type="vertical" />
              <a v-if="$auth('sys:dictionary:del')">删除</a>
            </a-popconfirm>
          </template>
        </template>
      </vxe-table-column>
    </ystable>
    <role-modal ref="createModal" @ok="handleOk" />
    <add-dictionary-modal ref="addDictionary" @ok="handleOk"></add-dictionary-modal>
  </a-card>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import RoleModal from './modules/RoleModal'
import ystable from '@/components/Table/ystable'
import AddDictionaryModal from './modules/AddDictionaryModal'
import Template from '../doc/Template.vue'
export default {
  name: 'DictionaryManage',
  components: {
    ystable,
    STable,
    RoleModal,
    Ellipsis,
    AddDictionaryModal,
    Template
  },
  data () {
    return {
      types: this.$getDictionaryTypes(),
      // 查询参数
      queryParam: {},
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        }
      },
      selectedRowKeys: [],
      entityList: [],
      options: {}
    }
  },
  filters: {},
  created () {},
  methods: {
    search () {
      this.selectedRowKeys = []
      this.$refs.table.refresh(true)
    },
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    openRoleModal (record) {
      this.$refs.setRoleModal.set(record)
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.entityList = selectedRows
    },
    handleDel (record) {
      this.$http
        .post('/sysDictionary/del', { id: record.id })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.selectedRowKeys = []
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
          }
        })
        .finally(res => {
          this.$refs.table.refresh(false)
        })
    },
    setHandleOk (flag) {
      this.$message.success('设置成功')
    },
    handleOk (flag) {
      this.selectedRowKeys = []
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(flag)
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    },
    selectChangeEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
      this.entityList = records
    },
    selectAllEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
      this.entityList = records
    },
    completed () {
      this.selectedRowKeys = []
      this.selectUser = []
    },
    delAll () {
      this.$http
        .post('/sysDictionary/delAll', { ids: this.selectedRowKeys })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.selectedRowKeys = []
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
          }
        })
        .finally(res => {
          this.$refs.table.refresh(false)
        })
    }
  }
}
</script>
