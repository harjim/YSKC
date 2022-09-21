<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="标题">
        <a-input v-model="queryParams.title" placeholder="请输入标题" style="width: 180px" />
      </a-form-item>
      <a-form-item label="来源">
        <a-input v-model="queryParams.sname" placeholder="请输入来源" style="width: 180px" />
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
        <a-button v-if="$auth('sys:policyContent:search')" type="primary" @click="$refs.table.refresh(true)">
          查询
        </a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/policyContent/getList"
      :params="queryParams"
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :seq-config="{ startIndex: 1 }"
      :toolbar="tableToolbar"
    >
      <vxe-table-column type="seq" title="序号" width="50" align="left"/>
      <vxe-table-column
        title="标题"
        field="title"
        key="title"
        show-header-overflow
        show-overflow="tooltip"
        align="left"
        :min-width="200"
      >
        <template v-slot="{ row }">
          <a :href="row.contentUrl" target="_blank">{{ row.title }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="来源"
        field="sname"
        key="sname"
        show-header-overflow
        :width="250"
        show-overflow="tooltip"
        align="left"
        remoteSort
      >
        <template v-slot="{ row }">
          <a :href="row.domain" target="_blank">{{ row.sname }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="栏目"
        field="stype"
        key="stype"
        show-header-overflow
        :width="220"
        show-overflow="tooltip"
        align="left"
        remoteSort
      >
        <template v-slot="{ row }">
          <a :href="row.typeUrl" target="_blank">{{ row.stype }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="发布日期"
        field="issueDate"
        key="issueDate"
        show-header-overflow
        :width="120"
        remoteSort
        show-overflow="tooltip"
        align="center"
      >
      </vxe-table-column>
      <vxe-table-column
        title="创建时间"
        field="createTime"
        key="createTime"
        align="center"
        show-header-overflow
        :width="150"
        remoteSort
        show-overflow="tooltip"
      >
      </vxe-table-column>
    </ystable>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
export default {
  name: 'PolicyManage',
  components: {
    ystable,
    STable,
    Ellipsis
  },
  data () {
    return {
      queryParams: {},
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true
      },
      loading: false,
      addressCodes: []
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
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>
