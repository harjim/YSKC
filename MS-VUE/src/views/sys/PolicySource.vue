<template>
  <a-card :bordered="false">
    <a-spin :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="来源">
          <a-input
            v-model="queryParams.sname"
            placeholder="请输入来源"
            style="width: 180px"
          />
        </a-form-item>
        <a-form-item label="所在地">
          <a-cascader
            :changeOnSelect="true"
            @change="
              (v) => {
                if (v && v.length) queryParams.addressCode = v.join(',')
                else queryParams.addressCode = ''
              }
            "
            placeholder="请输入所在地"
            style="width: 180px"
            :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
            :options="addressCodes"
          />
        </a-form-item>
        <a-form-item label="订阅">
          <a-select
            allowClear
            v-model="queryParams.subscription"
            style="width:180px;"
            placeholder="是否订阅">
            <a-select-option value="false">未订阅</a-select-option>
            <a-select-option value="true">已订阅</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button
            v-if="$auth('sys:policySource:search')"
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <ystable
        ref="table"
        queryUrl="/policySource/getList"
        :params="queryParams"
        @completed="()=>selectedRowKeys=[]"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        :toolbar="tableToolbar"
      >
        <template v-slot:buttons>
          <a-button
            type="primary"
            style="margin-right: 10px"
            v-if="$auth('sys:policySource:add')"
            @click="$refs.sourceModal.add()"
          >
            添加
          </a-button>
          <a-button
            type="primary"
            style="margin-right: 10px"
            v-if="$auth('sys:policySource:del')"
            @click="deleteSources"
          >
            删除
          </a-button>
        </template>
        <vxe-table-column
          type="checkbox"
          width="40"
          fixed="left"
        />
        <vxe-table-column
          title="来源"
          field="sname"
          remoteSort
          show-header-overflow
          show-overflow="tooltip"
        >
          <template v-slot="{ row }">
            <a
              :href="row.domain"
              target="_blank"
            >{{ row.sname }}</a>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="栏目"
          field="stype"
          remoteSort
          show-header-overflow
          :width="150"
          show-overflow="tooltip"
          align="center"
        >
          <template v-slot="{ row }">
            <a
              :href="row.typeUrl"
              target="_blank"
            >{{ row.stype }}</a>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="订阅"
          field="subscription"
          show-header-overflow
          :width="80"
          v-if="$auth('sys:policySource:subscription')"
          show-overflow="tooltip"
          align="center"
        >
          <template v-slot="{ row }">
            <a
              v-if="row.subscription"
              @click="unsubscription(row.id)"
            >取消订阅</a>
            <a
              v-else
              @click="subscription(row.id)"
            >订阅</a>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="操作"
          field="action"
          show-header-overflow
          :width="250"
          fixed="right"
          show-overflow="tooltip"
          align="center"
        >
          <template v-slot="{ row }">
            <a
              v-if="$auth('sys:policySource:edit')"
              @click="$refs.sourceModal.edit(row)"
            >编辑</a>
            <a-divider type="vertical" />
            <a
              v-if="$auth('sys:policySource:edit')"
              @click="showList(row.id,row.sname)"
            >订阅列表</a>
            <a-popconfirm
              v-if="$auth('sys:policySource:del')"
              title="是否确定删除?"
              @confirm="deleteSource(row.id)"
            >
              <a-divider
                type="vertical"
                v-if="$auth('sys:policySource:sublist') || $auth('sys:policySource:edit')"
              />
              <a>删除</a>
            </a-popconfirm>
            <template v-if="$auth('sys:policySource:run')">
              <a-divider type="vertical" />
              <a @click="runPolicy(row)">运行</a>
            </template>
          </template>
        </vxe-table-column>
      </ystable>
    </a-spin>
    <policy-source-modal
      ref="sourceModal"
      @ok="(refresh) => search(refresh)"
    />
    <source-user-list-modal
      :sourceId="currentId"
      ref="userListModal"
    />
    <PolicyTermimalModal ref="runPolicy"/>
  </a-card>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import SourceUserListModal from './modules/SourceUserListModal'
import PolicySourceModal from './modules/PolicySourceModal'
import ystable from '@/components/Table/ystable'
import PolicyTermimalModal from './modules/PolicyTerminalModal'
export default {
  name: 'PolicySource',
  components: {
    STable,
    Ellipsis,
    SourceUserListModal,
    PolicySourceModal,
    PolicyTermimalModal,
    ystable
  },
  data () {
    return {
      selectedRowKeys: [],
      queryParams: { subscription: undefined },
      loading: false,
      spinning: false,
      currentId: 0,
      addressCodes: [],
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true
      }
    }
  },
  created () {
    this.$getDictionary(1).then(addressCodes => {
      this.addressCodes = addressCodes
    })
  },
  methods: {
    query () {
      this.search(true)
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
    },
    showList (id, sname) {
      this.currentId = id
      this.$nextTick(() => {
        this.$refs.userListModal.showModal(sname)
      })
    },
    unsubscription (id) {
      this.spinning = true
      this.$http.post('/policySource/unsubscription', { id: id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('取消订阅')
          this.search(false)
        } else {
          this.$message.error('操作失败，请稍后再试。')
        }
        this.spinning = false
      })
    },
    subscription (id) {
      this.spinning = true
      this.$http.post('/policySource/subscription', { id: id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('订阅成功')
          this.search(false)
        } else {
          this.$message.error('操作失败，请稍后再试。')
        }
        this.spinning = false
      })
    },
    deleteSource (id) {
      const ids = [id]
      this.handleDelete(ids)
    },
    deleteSources () {
      this.handleDelete(this.selectedRowKeys)
    },
    handleDelete (ids) {
      this.spinning = true
      const values = { ids: ids }
      this.$http.post('/policySource/del', values).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败，请稍后再试。')
        }
        this.spinning = false
      })
    },
    onSelectChange (rowIds) {
      this.selectedRowKeys = rowIds
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    },
    runPolicy (row) {
      this.$refs.runPolicy.show(row)
    }
  }
}
</script>
