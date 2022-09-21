<template>
  <a-modal
    title="操作"
    style="top: 20px;"
    :width="600"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="48">
        <a-col :span="48">
          <a-form-item label="审核意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-decorator="['remark']" :rows="5"></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      id: 0,
      mdl: {},
      form: this.$form.createForm(this),
      permissions: [],
      autoExpandParent: true,
      status: 0,
      deptTree: []
    }
  },
  methods: {
    add (record) {
      this.title = '审核信息'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = record.id
      this.status = 2
    },
    edit (record) {
      this.title = '审核信息'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = record.id
      this.status = 1
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleCancel () {
      this.close()
    },
    onChange (value) {
      this.value = value
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          values.status = this.status
          this.$http.post('/expert/updateExpertStatus', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', true)
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
