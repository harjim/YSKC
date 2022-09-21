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
    <a-form
      @submit="handleSubmit"
      :form="form"
    >
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="人员类型"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-select
              showSearch
              v-decorator="['etype', {rules:[{required: true, message: '请选择人员类型'}]}]"
            >
              <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'SettingEtype',
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
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      ids: {},
      year: undefined
    }
  },
  mounted () {
  },
  methods: {
    add (record, year) {
      this.year = year
      this.title = '设置人员类型'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.ids = record
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      // if (this.employeeList.length === 0) {
      //   this.$message.info('请选择需要设置的人员')
      //   this.confirmLoading = false
      //   return
      // }
      validateFields((errors, values) => {
        if (!errors) {
          values.ids = this.ids
          values.year = this.year
          this.$http.post('/rdEmployee/updateRdEmployeeEtype', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$message.success('设置成功')
                this.$emit('ok', false)
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
