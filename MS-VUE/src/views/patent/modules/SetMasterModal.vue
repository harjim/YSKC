<!--
 * @Author: zdf
 * @Date: 2021-12-20 11:28:44
 * @LastEditTime: 2021-12-20 14:12:17
 * @LastEditors: zdf
 * @Description: 设置专利代理人
 * @FilePath: \MS-VUE\src\views\patent\modules\SetMasterModal.vue
-->
<template>
  <a-modal
    :width="400"
    :visible="visible"
    @cancel="closeModal"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    title="分配代理人"
    @ok="handleSubmit"
  >
    <a-form :form="form">
      <a-form-item label="代理人" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-select
          style="width: 250px"
          placeholder="请选择代理人"
          v-decorator="['masterId', { rules: [{ required: true, message: `请选择代理人` }] }]">
          <a-select-option v-for="item in masters" :key="item.id" :value="item.id">{{ item.title }}</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      id: undefined,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 6 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 16 },
        sm: { span: 16 }
      },
      masters: [],
      loadMaster: false
    }
  },
  methods: {
    showModal (id, masterId) {
      this.form.resetFields()
      this.id = id
      if (!this.loadMaster) {
        this.loadMaster = true
        this.$http.get('/patentMaster/getSelect').then(res => {
          if (res.success && res.data) {
            this.masters = res.data
          } else {
            this.$message.error(res.errorMessage || '获取代理人失败')
          }
          this.show(masterId)
        })
      } else {
        this.show(masterId)
      }
    },
    show (masterId) {
      if (masterId) {
        this.$nextTick(() => {
          this.form.setFieldsValue({ masterId })
        })
      }
      this.visible = true
    },
    handleSubmit () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post('/patentPlan/setMaster', { id: this.id, masterId: values.masterId }).then(res => {
            if (res.success && res.data) {
              this.$message.success('设置成功')
              this.$emit('ok')
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage || '设置失败')
              this.confirmLoading = false
            }
          })
        }
      })
    },
    closeModal () {
      this.visible = false
      this.confirmLoading = false
      this.id = undefined
    }
  }
}
</script>

<style>
</style>
