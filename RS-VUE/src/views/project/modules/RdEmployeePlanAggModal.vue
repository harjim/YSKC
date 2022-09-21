<!--
 * @Author: zdf
 * @Date: 2022-04-12 08:22:16
 * @LastEditTime: 2022-04-19 18:47:30
 * @LastEditors: zdf
 * @Description: 小程序计划工时归集费用
 * @FilePath: \RS-VUE\src\views\project\modules\RdEmployeePlanAggModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="500"
    v-model="visible"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    @cancel="closeModal">
    <a-form :form="form">
      <a-row :gutter="24">
        <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择月份"
            v-decorator="['month', { rules: [{ required: true, message: '请选择月份' }] }]"
            :options="monthOptions"
          >
          </a-select>
        </a-form-item>
      </a-row>
      <a-row :gutter="24">
        <a-form-item label="人员类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            mode="multiple"
            :maxTagCount="3"
            placeholder="请选择人员类型"
            v-decorator="['etypes', { rules: [{ required: true, message: '请选择人员类型'}],initialValue:[1,2] }]"
          >
            <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-row>
    </a-form>
    <a-alert message="当前操作会清空所选月份相关人员的原归集费用及研发工时记录，请谨慎操作！" type="warning" banner/>
  </a-modal>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
export default {
  mixins: [yearMiXin],
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      title: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    show () {
      this.title = `研发计划归集[${this.currentYear}]人员费用`
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      this.confirmLoading = true
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        values.month = Number(values.month) + 1
        const params = { etypes: values.etypes, month: `${this.currentYear}-${values.month < 10 ? '0' + values.month : values.month}-01 00:00:00` }
        this.$http.post('/projectRdEmployee/aggRdPlanFee', params)
          .then(res => {
            if (res.success && res.data) {
              this.$message.success('归集成功')
              this.closeModal()
            } else {
              this.confirmLoading = false
              this.$message.error(res.errorMessage ? res.errorMessage : '归集失败')
            }
          })
      })
    },
    closeModal () {
      this.visible = false
      this.confirmLoading = false
    }
  }
}
</script>

<style>

</style>
