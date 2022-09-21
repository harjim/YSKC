<template>
  <a-modal
    :visible="visible"
    title="设置部门职位"
    @ok="handleSubmit"
    @cancel="cancel"
    :maskClosable="false"
    destroyOnClose
  >
    <a-form :form="form" :label-col="{ span: 3 }" :wrapper-col="{ span: 16 }">
      <a-form-item label="部门">
        <a-input
          v-decorator="['deptName', { rules: [{ required: true, message: '请输入部门' }] }]"
          placeholder="请输入职位"
        />
      </a-form-item>
      <a-form-item label="职位">
        <a-input
          v-decorator="['position', { rules: [{ required: true, message: '请输入职位' }] }]"
          placeholder="请输入职位"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'SelectDeptPosiModal',
  data () {
    return {
      visible: false,
      ids: []
    }
  },
  beforeCreate () {
    this.form = this.$form.createForm(this)
  },
  methods: {
    open (ids) {
      this.ids = ids
      this.visible = true
    },
    cancel () {
      Object.assign(this.$data, this.$options.data())
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          const params = {
            ...values,
            ids: this.ids
          }
          this.$http.post('reviewCommittee/updateDeptPosition', params).then(({ success, errorMessage }) => {
            if (success) {
              this.cancel()
              this.$emit('ok', true)
            } else {
              this.$message.error(errorMessage)
            }
          })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped></style>
