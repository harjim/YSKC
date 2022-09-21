<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <year-select
                v-decorator="['year', {rules:[{required: true, message: '请选择年份'}]}]"
                @change="handleChange"
                placeholder="请选择年份"
                style="width:200px;"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="营业收入" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                style="width:200px"
                :min="0"
                :precision="2"
                placeholder="请输入营业收入"
                v-decorator="['businessIncome', {rules:[{required: true, message: '请输入营业收入'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="主营业务收入" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0"
                placeholder="请输入主营业务收入"
                style="width:200px"
                :precision="2"
                v-decorator="['mainBusinessIncome', {rules:[{required: true, message: '请输入主营业务收入'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="净利润">
              <a-input-number
                :min="0"
                placeholder="请输入净利润"
                style="width:200px"
                :precision="2"
                v-decorator="['netProfit', {rules:[{required: true, message: '请输入净利润'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="总资产">
              <a-input-number
                :min="0"
                placeholder="请输入总资产"
                :precision="2"
                style="width:200px"
                v-decorator="['totalAssets', {rules:[{required: true, message: '请输入总资产'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="净资产">
              <a-input-number
                :min="0"
                :precision="2"
                placeholder="请输入净资产'"
                style="width:200px"
                v-decorator="['netAssets', {rules:[{required: true, message: '请输入净资产'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>

        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label=" 企业所得税">
              <a-input-number
                :min="0"
                style="width:200px"
                placeholder="请输入企业所得税"
                :precision="2"
                v-decorator="['corporateIncomeTax', {rules:[{required: true, message: '请输入企业所得税'}]}]"
              />
            </a-form-item>
          </a-col>

          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="工业增加值">
              <a-input-number
                :min="0"
                :precision="2"
                placeholder="请输入工业增加值"
                style="width:200px"
                v-decorator="['addedOfIndustrial']"
              />
            </a-form-item>
          </a-col>
        </a-row>

        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="固定资产总额">
              <a-input-number
                :min="0"
                :precision="2"
                style="width:200px"
                placeholder="请输入固定资产总额"
                v-decorator="['totalFixedAssets']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="固定资产投资额">
              <a-input-number
                :min="0"
                :precision="2"
                style="width:200px"
                placeholder="请输入固定资产投资额"
                v-decorator="['fixedAssetsOfInvestment']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="总现金流量净额">
              <a-input-number
                :precision="2"
                :min="0"
                style="width:200px"
                placeholder="请输入总现金流量净额"
                v-decorator="['netTotalCashFlow']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经营活动现金流量净额">
              <a-input-number
                :min="0"
                placeholder="请输入经营活动现金流量净额"
                :precision="2"
                style="width:200px"
                v-decorator="['netCashFlowOfOperating']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="资产负债率（%）">
              <a-input-number
                :min="0"
                :max="100"
                placeholder="请输入资产负债率"
                :precision="2"
                style="width:200px"
                v-decorator="['assetLiabilityRatio']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label=" R&D支出总额">
              <a-input-number
                :min="0"
                :precision="2"
                placeholder="请输入R&D支出总额"
                style="width:200px"
                v-decorator="['totalExpenditureOfRD']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="政府借款金额">
              <a-input-number
                :min="0"
                :precision="2"
                placeholder="请输入政府借款金额"
                style="width:200px"
                v-decorator="['loanAmountOfGovernment']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="到期未还的政府借款额">
              <a-input-number
                :precision="2"
                placeholder="请输入到期未还的政府借款额"
                :min="0"
                style="width:200px"
                v-decorator="['dueLoanOfGovernment']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="纳税总额">
              <a-input-number
                :precision="2"
                placeholder="请输入纳税总额"
                :min="0"
                style="width:200px"
                v-decorator="['totalTax']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="工业总产值">
              <a-input-number
                :precision="2"
                placeholder="请输入工业总产值"
                :min="0"
                style="width:200px"
                v-decorator="['grossOfIndustrial']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="利润总额">
              <a-input-number
                :precision="2"
                placeholder="请输入利润总额"
                :min="0"
                style="width:200px"
                v-decorator="['totalProfit']"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import YearSelect from '@/components/YearSelect'
export default {
  components: {
    YearSelect
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 34 },
        sm: { span: 10 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.title = '添加财务状况(单位:万元)'
      this.isCreat = true
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
    },
    edit (record) {
      this.isCreat = false
      this.title = `编辑[${record.year}]年财务状况`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            this.$http.post('/financialCondition/addFinancialCond', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/financialCondition/updateFinancialCond', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
                }
              })
              .finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleChange (value) {
      if (value) {
        this.$http.get('/financialCondition/getDataByTerm', { params: { year: value } })
          .then(res => {
            if (res.data.id !== null) {
              this.form.resetFields()
              this.visible = true
              this.id = res.data.id
              this.$nextTick(() => {
                this.$initForm(this.form, res.data)
              })
            } else {
              this.id = -1
              this.$nextTick(() => {
                this.$initForm(this.form, res.data)
                this.form.setFields({ 'year': { value: value } })
              })
            }
          })
      }
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
