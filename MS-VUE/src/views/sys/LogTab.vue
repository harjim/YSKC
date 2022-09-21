<template>
  <div>
    <a-form layout="inline">
      <a-form-item label="操作人">
        <a-select
          showSearch
          :allowClear="true"
          v-model="queryParam.userName"
          placeholder="请输入姓名(用户名)搜索"
          style="width:200px"
          :defaultActiveFirstOption="false"
          :showArrow="false"
          :filterOption="false"
          @search="userSearch"
          @change="userChange"
          :notFoundContent="null"
        >
          <a-select-option v-for="d in userNameList" :key="d.userName">{{ d.userName }}</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="功能">
        <a-input v-model="queryParam.description" placeholder="请输入功能" style="width:180px" />
      </a-form-item>

      <a-form-item label="类型">
        <a-select
          showSearch
          v-model="queryParam.logType"
          :allowClear="true"
          placeholder="请选择类型"
          style="width:180px;"
        >
          <a-select-option value="-1">请选择</a-select-option>
          <a-select-option value="0" v-if="pageType === 1">管理端</a-select-option>
          <template v-if="pageType ===2">
            <a-select-option v-for=" (item,index) in logTypeArr" :key="index" :value="index">{{ item }}</a-select-option>
          </template>
          <template v-if="pageType ===3">
            <a-select-option v-for=" (item,index) in esLogTypeArr" :key="index" :value="index">{{ item }}</a-select-option>
          </template>
        </a-select>
      </a-form-item>

      <a-form-item label="公司名称" v-if="pageType==2">
        <a-input v-model="queryParam.companyName" placeholder="请输入公司名称" style="width:185px" />
      </a-form-item>
      <a-form-item>
        <span class="table-page-search-submitButtons" v-if="$auth('sys:product:search')">
          <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
        </span>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/sysLog/querySysLogList"
      :params="getParams()"
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :seq-config="{startIndex: 1}"
      :toolbar="{ zoom: true, custom:true, refresh:true }"
    >
      <vxe-table-column type="seq" title="序号" width="60" align="center"></vxe-table-column>
      <vxe-table-column
        title="功能"
        field="description"
        key="description"
        show-header-overflow
        show-overflow="tooltip"
        remoteSort
      >
      </vxe-table-column>
      <vxe-table-column
        title="账户类型"
        field="logType"
        key="logType"
        show-header-overflow
        :width="100"
        show-overflow="tooltip"
        align="center"
        remoteSort
      >
        <template v-slot="{ row }">
          <span>{{ getype(row.logType) }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="公司名称"
        field="companyName"
        key="companyName"
        show-header-overflow
        :width="300"
        v-if="pageType==2"
        show-overflow="tooltip"
        align="center"
        remoteSort
      >
      </vxe-table-column>
      <vxe-table-column
        title="操作人"
        field="username"
        key="username"
        show-header-overflow
        :width="250"
        v-if="$auth('sys:policySource:subscription')"
        show-overflow="tooltip"
        remoteSort
        align="center"
      >
      </vxe-table-column>
      <vxe-table-column
        title="操作时间"
        field="logTime"
        key="logTime"
        remoteSort
        show-header-overflow
        show-overflow="tooltip"
        align="center"
        :width="300"
      >
      </vxe-table-column>
    </ystable>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import Template from '../doc/Template.vue'
const esLogTypeArr = ['普通']
const logTypeArr = ['管理端', '企业端', '小程序']
export default {
  name: 'LogTab',
  components: {
    ystable,
    Template
  },
  props: {
    pageType: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      logTypeArr,
      esLogTypeArr,
      queryParam: {},
      options: {},
      userNameList: []
    }
  },
  methods: {
    getParams () {
      this.queryParam.pageType = this.pageType
      return this.queryParam
    },
    userSearch (value) {
      this.$http.get('/sysLog/getDataByUserName', { params: { userName: value, pageType: this.pageType } })
        .then(res => {
          this.userNameList = res.data
          return this.userNameList
        })
    },
    userChange (value) {
      this.queryParam.userName = value
    },
    getype (text) {
      return this.pageType === 3 ? esLogTypeArr[text] : logTypeArr[text]
    },
    onSelectChange (rowIds) {
      this.selectedRowKeys = rowIds
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>
