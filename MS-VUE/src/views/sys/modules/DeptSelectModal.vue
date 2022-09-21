<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="400"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="服务部门">
        <dept-select
          :multiple="true"
          v-decorator="['deptIds', {rules:[{required: true, message: '请输入所属部门'}]}]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { DeptSelect } from '@/components/Selects'
export default {
  name: 'DeptSelectModal',
  components: {
    DeptSelect
  },
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
      form: this.$form.createForm(this),
      title: '设置服务部门'
    }
  },
  methods: {
    open () {
      this.visible = true
      this.form.resetFields()
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleCancel () {
      this.close()
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          // console.log(values)
          this.$emit('ok', values.deptIds)
          this.visible = false
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
