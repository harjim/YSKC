<!--
 * @Author: your name
 * @Date: 2022-02-23 14:47:22
 * @LastEditors: lzh
 * @LastEditTime: 2022-03-16 14:20:23
 * @Description: 专利申请 审批
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\PatentAudit.vue
-->

<template>
  <a-card v-if="[0,1].includes(currentStep) && auditPermission" title="审批" size="small" style="margin-top: 16px;">
    <a-form :form="form">
      <a-form-item
        class="textarea"
        :help="()=>{const r = form.getFieldValue('suggestion'); return `(${!r? 0 : r.length > 100 ? 100 : r.length}/100)`}">
        <a-textarea v-decorator="['suggestion', {rules: [{required: false, message: '请输入审批意见'}]}]" placeholder="请输入审批意见" :rows="4" :maxLength="100">
        </a-textarea>
      </a-form-item>
    </a-form>
    <div style="text-align: center;">
      <a-popconfirm
        title="确定驳回吗?"
        ok-text="确定"
        cancel-text="取消"
        @confirm="submit(false)"
      >
        <a-button style="margin-right: 16px;">驳回</a-button>
      </a-popconfirm>
      <a-popconfirm
        title="确定通过吗?"
        ok-text="确定"
        cancel-text="取消"
        @confirm="submit(true)"
      >
        <a-button>通过</a-button>
      </a-popconfirm>
    </div>
  </a-card>
</template>

<script>
export default {
  props: {
    row: {
      type: Object,
      default: () => {}
    },
    currentStep: {
      type: Number,
      default: 0
    },
    auditPermission: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      form: this.$form.createForm(this)
    }
  },
  methods: {
    submit (pass) {
      this.form.validateFields((errors, values) => {
        if (errors) {
          return
        }
        const parmas = {
          ...values,
          pass: pass,
          instanceId: this.row.instanceId,
          nodeId: this.row.curNodeId
        }
        this.$http.post('/patentPlanNew/patentAudit', parmas).then(res => {
          if (res.success && res.data) {
            this.$message.success('操作成功')
            this.$emit('update')
          } else {
            this.$message.error(res.errorMessage || '操作失败')
          }
        })
      })
    }
  }
}
</script>
