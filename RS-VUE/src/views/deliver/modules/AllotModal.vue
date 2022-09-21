<template>
  <a-modal
    v-model="isShow"
    title="分配人员"
    destroy-on-close
    :confirmLoading="loading"
    @cancel="onCancelModal"
    @ok="handleAllotSubmit">
    <a-form :label-col="{ span: 8 }" :wrapper-col="{ span: 12 }" :form="form">
      <a-form-item label="工厂技术">
        <a-input
          v-decorator="['ftyTech', { rules: [{
            required: true, message: '请输入工厂技术人员'
          }]}]"
          type="txt"
          placeholder="请输入工厂技术人员"
          clearable
        />
      </a-form-item>
      <a-form-item label="工厂财务">
        <a-input
          v-decorator="['ftyFina', { rules: [{
            required: true, message: '请输入工厂财务人员'
          }]}]"
          placeholder="请输入工厂财务人员"
          clearable
        />
      </a-form-item>
      <a-form-item label="运行总监">
        <a-input
          v-decorator="['areaTech', { rules: [{
            required: true, message: '请输入区域运行总监'
          }]}]"
          type="txt"
          placeholder="请输入区域运行总监"
          clearable
        />
      </a-form-item>
      <a-form-item label="财务经理">
        <a-input
          v-decorator="['areaFina', { rules: [{
            required: true, message: '请输入区域财务经理'
          }]}]"
          type="txt"
          placeholder="请输入区域财务经理"
          clearable
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'AllotModal',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    currentYear: {
      type: Number,
      default: new Date().getFullYear()
    },
    selectedRow: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    visible (val) {
      this.isShow = val
    }
  },
  data () {
    return {
      loading: false,
      isShow: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    handleAllotSubmit () { // 分配表单提交
      this.loading = true
      this.form.validateFields((err, val) => {
        if (!err) {
          this.$http.post('highTechProgress/allot', {
            projectIds: this.selectedRow,
            year: this.currentYear,
            ...val
          }).then(({ success, errorMessage }) => {
            this.loading = false
            if (success) {
              this.$emit('close')
              this.$emit('handleGetList')
            } else {
              this.$message.error(errorMessage)
            }
          })
        }
      })
    },
    onCancelModal () { // 关闭分配模态框
      this.form.resetFields()
      this.$emit('close')
    }
  }
}
</script>

<style>
</style>
