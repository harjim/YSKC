<template>
  <div>
    <a-form layout="inline">
      <a-form-item label="标题">
        <a-input
          placeholder="请输入标题"
          v-model="queryParams.content"
        />
      </a-form-item>
      <a-form-item label="模块">
        <a-input
          placeholder="请输入模块"
          v-model="queryParams.modeName"
        />
      </a-form-item>
      <a-form-item label="客户名称">
        <a-input
          placeholder="请输入客户名称"
          v-model="queryParams.companyName"
        />
      </a-form-item>
      <a-form-item label="状态" v-if="!isAudit">
        <a-select
          v-model="queryParams.status"
          style="width:165px;"
          placeholder="请选择审核状态">
          <a-select-option v-for="(v,k) in statusMap" :key="k" :value="k">{{ v }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="处理人" v-if="sign === 2">
        <a-input
          placeholder="请输入处理人"
          v-model="queryParams.auditUsers"
        />
      </a-form-item>
      <a-form-item label="发起人" v-if="sign !== 2">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="false"
          placeholder="请输入发起人"
          style="width:165px;"
          v-model="queryParams.lastSubmiter"
        />
      </a-form-item>
      <a-form-item label="更新时间">
        <a-date-picker
          style="width:146px;"
          v-model="queryParams.beginDate"
          placeholder="开始日期"
          format="YYYY-MM-DD"
          :disabledDate="disabledBeginDate"
          @openChange="handleStartOpenChange"
        />
        -
        <a-date-picker
          style="width:146px;"
          v-model="queryParams.endDate"
          placeholder="结束日期"
          format="YYYY-MM-DD"
          :disabledDate="disabledEndDate"
          :open="endOpen"
          @openChange="handleEndOpenChange"
        />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          @click="search(true)"
        >查询
        </a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      @completed="()=>selectRowIds=[]"
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :checkbox-config="{ checkMethod: checkBoxMethod, showHeader: sign!==2 }"
      :params="getParams()"
      :columns="columns"
      queryUrl="/myAudit/getList"
      :toolbar="{
        custom: true,
        zoom:true,
        refresh:true
      }"
    >
      <template v-slot:buttons>
        <template v-if="isAudit">
          <a-button :disabled="selectRowIds.length <=0" @click="$refs.auditConfirm.open(selectRowIds)" type="primary">
            审核
          </a-button>
        </template>
        <template v-if="isRecall && $auth('customer:myAudit:recall')">
          <a-button :disabled="selectRowIds.length <=0" @click="$refs.RecallModal.open(selectRowIds)" type="primary">
            撤回
          </a-button>
        </template>
      </template>
      <template slot="nodeStatus" slot-scope="{row}">
        {{ getStatusName(row.nodeStatus) }}
      </template>
      <template slot="content" slot-scope="{row}">
        <template v-if="sign != 2">
          <a @click="onShowMyAuditDrawer(row.content ? row.content : row.modeName,row)">
            {{ row.content ? row.content : row.modeName }}</a>
        </template>
        <template v-else>
          {{ row.content ? row.content : row.modeName }}
        </template>
      </template>
    </ystable>
    <RecallModal ref="RecallModal" @ok="search(false)"/>
    <AuditConfirmModal ref="auditConfirm" @ok="search(true)"/>
    <my-audit-drawer ref="myAuditDrawer" @refresh="search(false)"></my-audit-drawer>
  </div>
</template>

<script>
import { SearchSelect } from '@/components/Selects'
import MyAuditDrawer from './MyAuditDrawer'
import AuditConfirmModal from './AuditConfirmModal'
import ystable from '@/components/Table/ystable'
import { getStatusName, statusMap } from '@/utils/processDoc/auditStatus'
import RecallModal from '@/views/customer/RecallModal'

export default {
  name: 'MyAuditTab',
  components: {
    RecallModal,
    ystable,
    AuditConfirmModal,
    MyAuditDrawer,
    SearchSelect
  },
  props: {
    params: {
      type: Object,
      required: false,
      default: () => {
        return {}
      }
    },
    isAudit: {
      type: Boolean,
      default: false
    },
    isRecall: {
      type: Boolean,
      default: false
    },
    sign: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      statusMap,
      // 查询参数
      queryParams: {},
      // 表头
      columns: [
        !this.isAudit && !this.isRecall ? {
          type: 'seq',
          width: 50,
          title: '序号'
        } : {
          type: 'checkbox',
          width: 50
        }, {
          title: '标题',
          field: 'content',
          align: 'left',
          slots: { default: 'content' },
          width: 300,
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
          title: '发起人',
          field: 'submiter',
          align: 'left',
          remoteSort: true,
          width: 160,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '当前处理人',
          field: 'realName',
          align: 'left',
          width: 220,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '状态',
          field: 'nodeStatus',
          align: 'center',
          width: 120,
          remoteSort: true,
          slots: { default: 'nodeStatus' },
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '更新时间',
          field: 'lastUpdateTime',
          align: 'center',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }],
      selectRowIds: [],
      endOpen: false
    }
  },
  created () {
    for (const key in this.params) {
      this.queryParams[key] = this.params[key]
    }
  },
  methods: {
    getStatusName,
    search (refresh) {
      this.selectRowIds = []
      this.$refs.table.refresh(refresh)
    },
    selectChange ({ records }) {
      this.selectRowIds = records.map(item => item.id)
    },
    onShowMyAuditDrawer (title, record) {
      this.$refs.myAuditDrawer.onShow(title, record)
    },
    getParams () {
      const params = Object.assign({}, this.queryParams)
      params.lastSubmiter = params.lastSubmiter ? params.lastSubmiter.id : undefined
      return params
    },
    disabledBeginDate (beginDate) {
      const endValue = this.queryParams.endDate
      if (!beginDate || !endValue) {
        return false
      }
      return beginDate.valueOf() > endValue.valueOf()
    },
    disabledEndDate (endDate) {
      const beginDate = this.queryParams.beginDate
      if (!endDate || !beginDate) {
        return false
      }
      return beginDate.valueOf() >= endDate.valueOf()
    },
    handleStartOpenChange (open) {
      if (!open) {
        this.endOpen = true
      }
    },
    handleEndOpenChange (open) {
      this.endOpen = open
    },
    checkBoxMethod ({ row }) {
      if (this.sign === 2) {
        return row.revokable === true && row.nodeStatus === 0
      }
      return true
    }
  }
}
</script>
