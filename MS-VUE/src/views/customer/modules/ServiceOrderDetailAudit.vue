<template>
  <a-tabs class="tabs" animated :activeKey="activeKey" @change="changeTab">
    <a-tab-pane key="1" tab="审核" v-if="show && canReview">
      <a-form-model
        ref="form"
        :model="form"
        :rules="{suggestion: [
          {required: true, message: '请输入审批意见'},
          { min: 5, message: '审批意见不能少于5个字符'},
          { max: 200, message: '审批意见不能大于200个字符'},
        ]}">
        <a-form-model-item prop="suggestion">
          <a-textarea
            v-model="form.suggestion"
            placeholder="请输入审核意见"
            :maxLength="200"
            :auto-size="{ minRows: 3, maxRows: 5 }"
          />
        </a-form-model-item>
        <a-row>
          <a-col :span="2" :offset="10" style="margin-top: 16px;display: flex;justify-content: space-between;">
            <a-popconfirm
              title="是否确认驳回？"
              @confirm="review(false)"
            >
              <a-button size="small" class="reject">驳回</a-button>
            </a-popconfirm>
            <a-popconfirm
              title="是否确认通过？"
              @confirm="review(true)"
            >
              <a-button size="small" style="margin-left: 16px;" class="success">通过</a-button>
            </a-popconfirm>
          </a-col>
        </a-row>
      </a-form-model>
    </a-tab-pane>
    <a-tab-pane key="2" tab="日志" v-if="$auth('customer:serviceApply:audit')">
      <vxe-grid
        highlight-hover-row
        highlight-current-row
        show-overflow="title"
        resizable
        auto-resize
        :max-height="200"
        size="small"
        :loading="loading"
        :data="logList"
      >
        <vxe-table-column type="seq" title="序号" width="60"></vxe-table-column>
        <vxe-table-column
          title="节点名称"
          field="nodeName"
          width="140"
          align="left"
        >
        </vxe-table-column>
        <vxe-table-column
          title="审批状态"
          field="pass"
          width="100"
          align="left"
        >
          <template v-slot="{row}">
            <a-badge :color="statusColor[row.status]" :text="getStatusName(row.status)"/>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="参与者"
          field="auditor"
          align="left"
        >
          <template v-slot="{row}">
            {{ row.auditor ? row.auditor : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="审批意见"
          field="suggestion"
          align="left"
        >
        </vxe-table-column>
        <vxe-table-column
          title="审批时间"
          field="createTime"
          width="160"
          align="center"
        >
        </vxe-table-column>
      </vxe-grid>
    </a-tab-pane>
  </a-tabs>
</template>

<script>
import { getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
import { mapState } from 'vuex'

export default {
  name: 'ServiceOrderDetailAudit',
  props: {
    storeName: {
      type: String,
      default: 'service'
    },
    detail: {
      type: String,
      default: 'currentOrder'
    },
    record: {
      type: Object,
      default: () => ({})
    }
  },
  data () {
    return {
      statusColor,
      defaultTab: undefined,
      loading: false,
      activeKey: undefined,
      logList: [],
      form: {},
      show: true
    }
  },
  computed: {
    ...mapState({
      currentOrder: state => state.service.currentOrder,
      recordOrder: state => state.workRecord.recordOrder
    }),
    canReview () {
      const flag = this.record.hasPermission || this[this.detail].hasPermission
      return flag && this.$auth('customer:serviceApply:review')
    }
  },
  watch: {
    activeKey (val) {
      if (val === '2') {
        this.getAuditLog()
      }
    },
    'record.instanceId' (val) {
      if (val && this.activeKey === '2') {
        this.getAuditLog()
      }
    }
  },
  mounted () {
    if (this.canReview) {
      this.activeKey = '1'
    } else if (this.$auth('customer:serviceApply:audit')) {
      this.activeKey = '2'
    }
  },
  methods: {
    getStatusName,
    // 获取日志
    getAuditLog () {
      if (!this[this.detail].instanceId && !this.record.instanceId) return
      this.loading = true
      this.$http.get('/rdFeeAudit/getAuditLog', { params: { instanceId: this[this.detail].instanceId || this.record.instanceId } }).then(({
        success,
        data,
        errorMessage
      }) => {
        if (success) {
          this.logList = data
        } else {
          this.$message.error(errorMessage || '获取数据失败')
        }
      }).finally(() => {
        this.loading = false
      })
    },
    changeTab (v) {
      this.activeKey = v
    },
    review (flag) {
      this.$refs.form.validate(valid => {
        if (valid) {
          const params = {
            model: {
              ...this.form,
              pass: flag,
              instanceId: this[this.detail].instanceId || this.record.instanceId
            },
            applyModel: {}
          }
          if (this.detail === 'currentOrder') {
            params.applyModel.finaDirectorId = this[this.detail].finaDirectorId
            params.applyModel.finaList = this[this.detail].finaList
            params.applyModel.finaManagerId = this[this.detail].finaManagerId
            params.applyModel.otherList = this[this.detail].otherList
            params.applyModel.techList = this[this.detail].techList
            params.applyModel.techDirectorId = this[this.detail].techDirectorId
            params.applyModel.techManagerId = this[this.detail].techManagerId
            params.applyModel.id = this[this.detail].id
          }
          this.$http.post('/serviceApply/audit', params).then(({ success, errorMessage }) => {
            if (success) {
              this.$message.success('操作成功')
              this.$store.commit(`${this.storeName}/CHANGE_PERMISSION`, false)
              this.show = false
              this.activeKey = '2'
              this.$refs.form.resetFields()
              this.$emit('refresh', false)
            } else {
              this.$message.error(errorMessage || '审核失败')
            }
          })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.tabs /deep/ .ant-tabs-nav-scroll {
  display: flex;
  flex-direction: row-reverse;
}

.reject {
  &:hover {
    .hover(red);
  }
}

.success {
  &:hover {
    .hover(#1890ff);
  }
}

.hover(@color:#fff) {
  color: #fff;
  box-shadow: #fff;
  border: 1px solid #fff;
  background: @color;
}
</style>
