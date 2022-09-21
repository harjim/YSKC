<template>
  <a-row :gutter="[0, 16]">
    <a-col :span="24">
      <a-tabs
        id="audit-tabs"
        :activeKey="tabKey"
        :animated="false"
        :tabBarStyle="{ marginBottom: '10px' }"
        size="small"
        style="overflow: auto;"
        @change="onChangeTabKey"
      >
        <a-tab-pane v-if="isAudit" key="audit" tab="审核">
          <a-form :form="form">
            <a-form-item>
              <a-textarea
                v-decorator="['suggestion', {
                  rules: [{ required: true, whitespace: true, min: 5, message: '请输入5个字以上审批意见'}],
                  initialValue: ''
                }]"
                placeholder="请输入审批意见"
              />
            </a-form-item>
            <a-form-item
              :label-col="{ span: 24 }"
              :wrapper-col="{ span: 8, offset: 11 }">
              <a-popconfirm
                cancel-text="取消"
                ok-text="确定"
                title="您确定要驳回审核吗？"
                @confirm="handleSubmit(false)"
              >
                <a-button size="small" style="margin-right: 20px">驳回</a-button>
              </a-popconfirm>
              <a-popconfirm
                cancel-text="取消"
                ok-text="确定"
                title="您确定要通过审核吗？"
                @confirm="handleSubmit(true)"
              >
                <a-button size="small">通过</a-button>
              </a-popconfirm>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        <a-tab-pane key="log" tab="日志">
          <a-table
            :columns="columns"
            :data-source="logList"
            :pagination="false"
            :rowKey="record => record.logTime"
            :scroll="{ y: 140 }"
            size="small"
          />
        </a-tab-pane>
      </a-tabs>
    </a-col>
  </a-row>
</template>

<script>
export default {
  name: 'ReviewDrawer',
  props: {
    isAudit: {
      type: Boolean,
      default: false
    },
    getLogParams: {
      type: Object,
      default: () => {
      }
    },
    submitParams: {
      type: Object,
      default: () => {
      }
    }
  },
  data () {
    return {
      columns: [
        { title: '公司名称', dataIndex: 'companyName', align: 'center' },
        {
          title: '节点',
          dataIndex: 'node',
          align: 'center',
          customRender: txt => txt === 0 ? '优赛准备中' : (txt === 1 ? '已完成' : (txt === 10 ? '工厂审核中' : '区域审核中'))
        },
        {
          title: '状态',
          dataIndex: 'status',
          align: 'center',
          customRender: txt => txt === 1 ? '通过' : '驳回'
        },
        { title: '参与人', dataIndex: 'auditUser', align: 'center' },
        { title: '审核意见', dataIndex: 'suggestion', align: 'center' },
        { title: '审核时间', dataIndex: 'logTime', align: 'center' }
      ],
      form: this.$form.createForm(this, { name: 'audit_form' }),
      tabKey: 'audit',
      logList: []
    }
  },
  methods: {
    onChangeTabKey (key) {
      this.tabKey = key
      if (key === 'log') this.getLogList()
    },
    handleSubmit (isPass) {
      this.form.validateFields((err, val) => {
        if (!err) {
          this.$emit('handleSubmit', isPass, val.suggestion)
          const params = {
            ...this.submitParams,
            suggestion: val.suggestion,
            status: isPass ? 1 : 2
          }
          this.$http.post('/highTechProgress/submitAudit', params).then(({
            success,
            errorMessage
          }) => {
            if (success) {
              this.$message.success('操作成功！')
              this.$emit('close')
            } else {
              this.$message.error(errorMessage)
            }
          }).catch(e => this.$message.error(e.message))
        }
      })
    },
    getLogList () { // 获取日志列表
      this.$http.get('highTechProgress/getLogList', {
        params: this.getLogParams
      }).then(({
        data,
        success,
        errorMessage
      }) => {
        if (success) {
          this.logList = data
        } else {
          this.$message.error(errorMessage)
        }
      }).catch(e => this.$message.error(e.message))
    }
  }
}
</script>
