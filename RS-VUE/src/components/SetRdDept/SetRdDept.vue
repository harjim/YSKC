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
          <a-form-item label="研发部门:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <select-down
              ref="rdDept"
              treeType="rdDept"
              placeholder="请选择研发部门"
              @select="rdDeptSelected"
              v-decorator="['rdDeptId', {rules:[{required: true, message: '请选择研发部门'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import SelectDown from '@/components/SelectDown'
export default {
  components: {
    SelectDown
  },
  name: 'SetRdDept',
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
      materialList: {}
    }
  },
  methods: {
    add () {
      this.title = '设置研发部门'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = 0
      this.$nextTick(() => {
        this.$refs.rdDept.setValue(undefined)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          this.$emit('ok', values.rdDeptId)
          this.confirmLoading = false
          this.visible = false
        } else {
          this.confirmLoading = false
        }
      })
    },
    rdDeptSelected (rdDeptId) {
      this.form.setFieldsValue({ rdDeptId: rdDeptId })
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
