<template>
  <a-modal
    :title="`归集[${currentYear}]费用`"
    :width="500"
    :visible="isVisible"
    :maskClose="false"
    @cancel="isVisible = false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="月份" :labelCol="{span:4}" :wrapperCol="{span:20}">
            <a-select
              placeholder="请选择月份"
              v-decorator="['month', { rules: [{ required: true, message: '请选择月份' }] }]"
              :options="monthOptions"
            >
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="归集类型:" :labelCol="{span:4}" :wrapperCol="{span:20}">
            <a-checkbox-group v-decorator="['types', { rules: [{ required: true, message: '请选择归集类型' }] }]">
              <a-checkbox value="0">折旧费用</a-checkbox>
              <a-checkbox value="1">动力费用</a-checkbox>
              <a-checkbox value="2">燃料费用</a-checkbox>
            </a-checkbox-group>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
import yearMixin from '@/utils/yearMixin'
import moment from 'moment'
export default {
  name: 'ProjectFinaScheduleAggModal',
  mixins: [yearMixin],
  data () {
    return {
      isVisible: false,
      form: this.$form.createForm(this),
      confirmLoading: false
    }
  },
  methods: {
    show () {
      this.form.resetFields()
      this.isVisible = true
    },
    moment,
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.month = moment([this.currentYear, values.month, 1, 0, 0, 0, 0])
          this.$http.post('/projectFinaSchedule/aggFee', values)
            .then(res => {
              if (res.success) {
                this.isVisible = false
                this.$message.success('归集成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '归集失败')
              }
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
<style lang='less' scoped>
</style>
