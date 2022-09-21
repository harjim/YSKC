<template>
  <a-modal
    title="导出研发人员计划"
    v-model="visible"
    :width="500"
    @cancel="closeModal"
    @ok="exportData"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-row>
        <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-tree-select
            :dropdownStyle="{ maxHeight: '420px', maxWidth: '100%' }"
            v-decorator="['months', {rules:[{required: true, message: '请选择月份'}]}]"
            placeholder="请选择月份"
            style="width: 100%"
            tree-default-expand-all
            tree-checkable
            :show-checked-strategy="SHOW_PARENT"
          >
            <a-tree-select-node title="所有月份" :value="-1" key="-1">
              <a-tree-select-node
                v-for="m in monthOptions"
                :key="m.value"
                :value="m.value"
                :title="m.label"
              />
            </a-tree-select-node>
          </a-tree-select>
        </a-form-item>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import { TreeSelect } from 'ant-design-vue'
const SHOW_PARENT = TreeSelect.SHOW_PARENT
export default {
  mixins: [yearMiXin],
  data () {
    return {
      SHOW_PARENT,
      visible: false,
      confirmLoading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      form: this.$form.createForm(this)
    }
  },
  methods: {
    open () {
      this.visible = true
    },
    closeModal () {
      this.visible = false
      this.confirmLoading = false
      this.form.resetFields()
    },
    moment,
    exportData () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          var months
          if (values.months[0] === -1) {
            months = this.monthOptions.map(m => moment([this.currentYear, m.value, 1, 0, 0, 0, 0]).format('YYYY-MM-DD HH:mm:ss'))
          } else {
            months = values.months.map(m => moment([this.currentYear, m, 1, 0, 0, 0, 0]).format('YYYY-MM-DD HH:mm:ss'))
          }
          this.$exportData('/rdEmployeePlan/exportPlan', { year: this.currentYear, months: months }, `${this.$store.getters.userInfo.companyName}-${this.currentYear}年研发人员计划表.xls`, this.$message).then(res => {
            this.closeModal()
          })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>

<style>

</style>
