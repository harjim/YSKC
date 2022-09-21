<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="600"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea
              v-decorator="['remark',{rules:[{required: true, message: '请输入备注'}]}]"
              :rows="5"
            ></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'EditRemark',
  deptTree: [],
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 3 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      list: {}
    }
  },
  mounted () {
  },
  methods: {
    add (record) {
      this.title = '设置备注'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.list = record
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      if (this.list.length === 0) {
        this.$message.info('请选择需要设置的数据')
        this.confirmLoading = false
        return
      }
      validateFields((errors, values) => {
        if (!errors) {
          values.rsEntityList = this.list
          this.$http.post('/rsPatentCost/updateRemark', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.info(res.errorMessage)
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
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
