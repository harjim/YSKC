<template>
  <a-modal
    :footer="null"
    :visible.sync="visible"
    centered
    destroy-on-close
    title="批量审核"
    @cancel="onCancel"
  >
    <a-form :form="form">
      <a-form-item field="suggestion" span="24" title="意见">
        <a-textarea
          v-decorator="['suggestion', {
            rules: [{ required: true, whitespace: true, min: 5, message: '请输入5个字以上审批意见'}],
            initialValue: ''
          }]"
          placeholder="请输入审批意见"
        />
      </a-form-item>
      <a-form-item align="right" span="24">
        <a-button
          size="small"
          style="margin-right: 10px;"
          @click="onAuditsSubmit(false)"
        >
          驳回
        </a-button>
        <a-button size="small" @click="onAuditsSubmit(true)">提交</a-button>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    ids: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      auditsForm: {
        suggestion: ''
      },
      form: this.$form.createForm(this, { name: 'audits_form' })
    }
  },
  methods: {
    onCancel () {
      this.form.resetFields()
      Object.assign(this.$data, this.$options.data.call(this))
      this.$emit('close')
    },
    onAuditsSubmit (status) {
      this.form.validateFields((err, val) => {
        if (!err) {
          this.$http.post('/highTechProgress/submitsAudit', {
            status: status ? 1 : 2,
            suggestion: val.suggestion,
            ids: this.ids
          }).then(({ success, errorMessage }) => {
            if (success) {
              this.$message.success('操作成功！')
              if (status) this.$emit('refresh')
              this.onCancel()
            } else {
              this.$message.error(errorMessage)
            }
          }).catch(e => this.$message.error(e.message)).finally(() => {
            this.onCancel()
          })
        }
      })
    }
  }
}
</script>

<style>
</style>
