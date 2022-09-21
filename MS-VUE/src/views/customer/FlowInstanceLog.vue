<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="标题">
        <a-input placeholder="请输入标题" v-model="queryParams.content" />
      </a-form-item>
      <a-form-item label="审批人">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="false"
          placeholder="请选择审批人"
          style="width: 165px"
          v-model="queryParams.userId"
        />
      </a-form-item>
      <a-form-item> </a-form-item>

      <a-form-item label="状态">
        <a-select allowClear v-model="queryParams.status" style="width: 180px" placeholder="请选择状态">
          <a-select-option v-for="(n, key) in statusMap" :key="key">{{ n }}</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="审批意见">
        <a-input placeholder="请输入审批意见" v-model="queryParams.suggestion" />
      </a-form-item>

      <a-form-item label="审批日期">
        <a-date-picker
          v-model="queryParams.startTime"
          placeholder="请输入开始日期"
          format="YYYY-MM-DD"
          :disabledDate="disabledStartDate"
          @openChange="handleStartOpenChange"
        />-
        <a-date-picker
          v-model="queryParams.endTime"
          placeholder="请输入结束日期"
          format="YYYY-MM-DD"
          :disabledDate="disabledEndDate"
          :open="endOpen"
          @openChange="handleEndOpenChange"
        />
      </a-form-item>

      <a-form-item>
        <a-button type="primary" @click="search(true)" v-if="$auth('customer:all:search')">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      rowId="id"
      :params="getParams()"
      :columns="columns"
      queryUrl="/myAudit/getLogList"
      :toolbar="{
        custom: true,
        zoom: true,
        refresh: true
      }"
    >
      <template slot="status" slot-scope="{ row }">
        {{ getStatusName(row.status) }}
      </template>
    </ystable>
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import { getStatusName, statusMap } from '@/utils/processDoc/auditStatus'
import { SearchSelect } from '@/components/Selects'
export default {
  name: 'ComapnyRole',
  components: {
    ystable,
    SearchSelect
  },
  data () {
    return {
      endOpen: false,
      statusMap,
      // 查询参数
      queryParams: {},
      columns: [
        {
          type: 'seq',
          width: 50,
          title: '序号'
        }, {
          title: '标题',
          field: 'content',
          align: 'left',
          width: 260,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '模块',
          field: 'modeName',
          align: 'center',
          width: 140,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '客户名称',
          field: 'companyName',
          align: 'left',
          width: 260,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '审批人',
          field: 'realName',
          align: 'left',
          width: 130,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '状态',
          field: 'status',
          align: 'center',
          width: 120,
          remoteSort: true,
          slots: { default: 'status' },
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '审批意见',
          field: 'suggestion',
          align: 'left',
          width: 300,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '审批时间',
          field: 'createTime',
          align: 'center',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }
      ]
    }
  },
  created () {
    delete statusMap['9999']
  },
  methods: {
    getParams () {
      const result = {}
      result.content = this.queryParams.content
      result.suggestion = this.queryParams.suggestion
      result.status = this.queryParams.status
      if (this.queryParams.startTime) {
        result.startTime = this.queryParams.startTime.format('YYYY-MM-DD') + ' 00:00:00'
      }
      if (this.queryParams.endTime) {
        result.endTime = this.queryParams.endTime.format('YYYY-MM-DD') + ' 23:59:59'
      }
      if (this.queryParams.userId) {
        result.userId = this.queryParams.userId.id
      }
      return result
    },
    disabledStartDate (startTime) {
      const endValue = this.queryParams.endTime
      if (!startTime || !endValue) {
        return false
      }
      return startTime.valueOf() > endValue.valueOf()
    },
    disabledEndDate (endTime) {
      const startValue = this.queryParams.startTime
      if (!endTime || !startValue) {
        return false
      }
      return startValue.valueOf() >= endTime.valueOf()
    },
    handleStartOpenChange (open) {
      if (!open) {
        this.endOpen = true
      }
    },
    handleEndOpenChange (open) {
      this.endOpen = open
    },
    getStatusName,
    search (refresh) {
      this.$refs.table.refresh(refresh)
    }
  }
}
</script>
