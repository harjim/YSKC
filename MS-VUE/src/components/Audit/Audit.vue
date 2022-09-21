<template>
  <a-spin :spinning="loading" :delay="150">
    <a-tabs :activeKey="active" @change="(v) => (active = v)">
      <a-tab-pane key="1" tab="审核" v-if="hasPermission">
        <a-form :form="form">
          <a-form-item>
            <a-textarea
              v-decorator="[
                'suggestion',
                {
                  rules: [
                    { required: true, min: 5, message: '请输入5个字以上审批意见' },
                    { max: 200, message: '请输入200个字以内审批意见' }
                  ]
                }
              ]"
              placeholder="请输入审批意见"
              :rows="4"
            />
          </a-form-item>
          <div style="text-align: center">
            <a-popconfirm title="确定驳回吗?" ok-text="确定" cancel-text="取消" @confirm="audit(false)">
              <a-button style="margin-right: 16px" class="reject">驳回</a-button>
            </a-popconfirm>
            <a-popconfirm title="确定通过吗?" ok-text="确定" cancel-text="取消" @confirm="audit(true)">
              <a-button class="success">通过</a-button>
            </a-popconfirm>
          </div>
        </a-form>
      </a-tab-pane>

      <a-tab-pane key="2" tab="日志">
        <vxe-grid
          highlight-hover-row
          highlight-current-row
          show-overflow="title"
          resizable
          auto-resize
          :max-height="160"
          size="small"
          :data="list"
          :columns="columns"
        >
          <slot></slot>
        </vxe-grid>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>
export default {
  props: {
    hasPermission: {
      type: Boolean,
      default: false
    },
    columns: {
      type: Array,
      default: () => []
    },
    instanceId: {
      type: Number,
      default: undefined
    },
    callback: {
      type: Function,
      default: () => () => {}
    },
    url: {
      type: String,
      default: '/contract/audit'
    }
  },
  data() {
    return {
      loading: false,
      active: '1',
      form: this.$form.createForm(this),
      list: []
    }
  },
  mounted() {
    this.active = this.hasPermission ? '1' : '2'
  },
  watch: {
    hasPermission(v) {
      this.active = v ? '1' : '2'
    },
    active(v) {
      if (v === '2' && this.instanceId) this.getAuditLog()
    }
  },
  methods: {
    audit(pass) {
      this.form.validateFields((err, values) => {
        if (!err) {
          const v = { ...values, instanceId: this.instanceId, pass }
          this.$emit('check', v)
        }
      })
    },
    auditRequest(params) {
      this.loading = true
      this.$http
        .post(this.url, params)
        .then((res) => {
          if (res.success) {
            this.$message.success('操作成功')
            this.$emit('refresh', params.id)
          } else {
            this.$message.error(res.errorMessage || '操作失败')
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
    getAuditLog() {
      this.loading = true
      this.$http
        .get('/rdFeeAudit/getAuditLog', { params: { instanceId: this.instanceId } })
        .then(({ success, data, errorMessage }) => {
          if (success) {
            this.list = data
          } else {
            this.$message.error(errorMessage || '获取数据失败')
          }
        })
        .finally(() => {
          this.loading = false
        })
    }
  }
}
</script>

<style lang="less" scoped>
& /deep/ .ant-tabs-nav-scroll {
    text-align: right;
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
