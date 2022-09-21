<template>
  <a-modal
    :title="title"
    :width="600"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item :label="initName" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="['role',{rules:[{required: true, message: '请输入' + initName}]}]"
              :placeholder="'请输入' + initName"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'SetUsedRoleModal',
  props: {
    initName: {
      type: String,
      default: '项目角色'
    },
    url: {
      type: String,
      required: true
    },
    year: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      ids: []
    }
  },
  mounted () {
  },
  methods: {
    add (ids) {
      this.title = '设置' + this.initName
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.ids = ids
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.ids = this.ids
          values.year = this.year
          this.$http.post(this.url, values)
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
