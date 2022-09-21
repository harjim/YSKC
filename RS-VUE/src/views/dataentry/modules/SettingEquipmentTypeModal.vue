<template>
  <a-modal
    title="设置设备类型"
    style="top: 20px;"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="设备类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              showSearch
              v-decorator="['etype', {rules:[{required: true, message: '请选择设备类型'}]}]"
            >
              <a-select-option v-for="item in $getEnums('equipmentEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'SettingEtype',
  deptTree: [],
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      ids: [],
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
    add (selectedRowKeys) {
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.ids = selectedRowKeys
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.ids = this.ids
          this.$http.post('/dEquipment/updateEtype', values)
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
</style>
