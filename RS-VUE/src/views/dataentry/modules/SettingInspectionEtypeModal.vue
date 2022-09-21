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
          <a-form-item label="费用类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              showSearch
              v-decorator="['type', {rules:[{required: true, message: '选择费用类型'}]}]"
            >
              <a-select-option value="-1">请选择费用类型</a-select-option>
              <a-select-option value="20500">检测</a-select-option>
              <a-select-option value="20600">修理</a-select-option>
              <a-select-option value="40000">软件摊销</a-select-option>
              <a-select-option value="40100">专利摊销</a-select-option>
              <a-select-option value="40200">其他摊销</a-select-option>
              <a-select-option value="20300">其他试制</a-select-option>
              <a-select-option value="69900">其他</a-select-option>
              <a-select-option value="60400">差旅费</a-select-option>
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
      entityList: {}
    }
  },
  mounted () {
  },
  methods: {
    add (record) {
      this.title = '设置费用类型'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = 0
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
          values.entityList = this.entityList
          this.$http.post('/inspection/updateInspetioneType', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$message.success('设置成功')
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
