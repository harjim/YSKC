<template>
  <a-tabs class="tabs" animated :activeKey="activeKey" @change="changeTab">
    <a-tab-pane key="1" tab="审核" v-if="$auth('innovation:finaDaily:review') && show && canReview">
      <a-form
        ref="form"
        :form="form"
      >
        <a-form-item >
          <a-textarea
            placeholder="请输入审核意见"
            :maxLength="200"
            :auto-size="{ minRows: 3, maxRows: 5 }"
            v-decorator="[
              'suggestion',
              {rules: [{required: true, message: '请输入审批意见'},
                       { min: 5, message: '审批意见不能少于5个字符'},
                       { max: 200, message: '审批意见不能大于200个字符'}]}
            ]"
          />
        </a-form-item>
        <a-row>
          <a-col :span="2" :offset="10" style="margin-top: 16px;display: flex;justify-content: space-between;">
            <a-popconfirm
              title="是否确认驳回？"
              @confirm="review(false)"
              @cancel="showReject = false"
              :visible="showReject"
            >
              <a-button size="small" class="reject" @click="valid(false)">驳回</a-button>
            </a-popconfirm>
            <a-popconfirm
              title="是否确认通过？"
              @confirm="review(true)"
              @cancel="showPass = false"
              :visible="showPass"
            >
              <a-button size="small" style="margin-left: 16px;" class="success" @click="valid(true)">通过</a-button>
            </a-popconfirm>
          </a-col>
        </a-row>
      </a-form>
    </a-tab-pane>
    <a-tab-pane key="2" tab="日志" v-if="$auth('innovation:finaDaily:audit')">
      <vxe-grid
        highlight-hover-row
        highlight-current-row
        show-overflow="title"
        resizable
        auto-resize
        max-height="100%"
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
import { getStatusName, statusColor, isEditStatus } from '@/utils/processDoc/auditStatus'
import { mapState } from 'vuex'

export default {
  name: 'FinaDailyDetailAudit',
  props: {
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
      form: this.$form.createForm(this),
      show: true,
      showReject: false,
      showPass: false
    }
  },
  computed: {
    ...mapState({
      currentOrder: state => state.service.currentOrder,
      recordOrder: state => state.workRecord.recordOrder
    }),
    canReview () {
      // const flag = this.record.status || this[this.detail].hasPermission
      return !isEditStatus(this.record.status) && this.record.hasPermission && this.$auth('innovation:finaDaily:review')
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
    } else if (this.$auth('innovation:finaDaily:audit')) {
      this.activeKey = '2'
    }
  },
  methods: {
    getStatusName,
    /** 获取日志 */
    getAuditLog () {
      if (!this.record.instanceId) return
      this.loading = true
      this.$http.get('/rdFeeAudit/getAuditLog', { params: { instanceId: this.record.instanceId } }).then(({
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
    /**
     * @description 校验必填项是否已填
     *
     * @param { Boolean } pass 是否通过
     */
    valid (pass) {
      this.form.validateFields((error) => {
        if (!error) {
          if (pass) {
            this.showPass = true
          } else {
            this.showReject = true
          }
        }
      })
    },
    /**
     * 提交审核
     * @param {Boolean} flag 是否通过
     */
    review (flag) {
      const suggestion = this.form.getFieldValue('suggestion')
      const params = { suggestion, pass: flag, instanceId: this.record.instanceId }
      this.$http.post('/finaDaily/reviewFinaDaily', params).then(({ success, errorMessage }) => {
        if (success) {
          this.$message.success('操作成功')
          this.show = false
          this.activeKey = '2'
          this.form.resetFields()
          this.$emit('refresh', true)
        } else {
          this.$message.error(errorMessage || '审核失败')
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

.ant-tabs {
  height: 100%;

}
/deep/ .ant-tabs-content-animated {
    height: calc(100% - 60px);
  }

</style>
