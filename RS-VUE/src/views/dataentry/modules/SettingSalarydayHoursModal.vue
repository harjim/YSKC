<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="日工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :min="0"
              :max="24"
              :precision="0"
              v-decorator="['dayHours', {rules:[{required: true, message: '请输入日工时'}],initialValue:8}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'SettingdayHours',
  deptTree: [],
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      entityList: {}
    }
  },
  mounted () {
  },
  methods: {
    add (record) {
      this.title = '设置日工时'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.entityList = record
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      if (this.entityList.length === 0) {
        this.$message.info('请选择需要设置的数据')
        this.confirmLoading = false
        return
      }
      validateFields((errors, values) => {
        if (!errors) {
          values.salarylist = this.entityList
          this.$http.post('/salary/updateSalaryDayHours', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
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
