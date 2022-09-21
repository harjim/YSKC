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
          <a-form-item label="研发工时:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入研发工时"
              v-decorator="['rdHour', {rules:[{required: true, message: '请输入研发工时'}]}]"
              :min="-getMax()"
              :precision="1"
              :max="getMax()"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
const typeMap = {
  energy: '/projectEnergy/setRdHour'
}
export default {
  components: {
  },
  props: {
    rType: {
      type: String, // energy
      required: true
    }
  },
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
      params: {}
    }
  },
  methods: {
    set (params) {
      this.params = { ...params }
      this.title = '设置研发工时'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
    },
    getMax () {
      if (this.params.month) {
        const month = this.params.month.format('YYYY-MM')
        return new Date(month.substr(0, 4), month.substr(5), 0).getDate() * 24
      }
      return 744
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          this.params.rdHour = values.rdHour
          this.$http.post(typeMap[this.rType], this.params).then(res => {
            if (res.success && res.data) {
              this.$message.success('保存成功')
              this.visible = false
              this.$emit('ok')
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              this.confirmLoading = false
            }
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
