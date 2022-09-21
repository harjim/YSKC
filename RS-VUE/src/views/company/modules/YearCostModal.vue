<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="true"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!-- <year-select
                v-decorator="['year', { rules: [{ required: true, message: '请选择年份' }] }]"
                placeholder="请选择年份"
                style="width:200px;"
                :disabled="this.update"
                @change="handleChange"
              /> -->
              <a-month-picker
                placeholder="请选择月份"
                v-decorator="['month', { rules: [{ required: true, message: '请选择月份' }] }]"
                inputReadOnly
                :disabled="this.update"
                @change="handleChange"
                style="width:190px;"
              ></a-month-picker>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="工资">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入工资"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="[
                  'wages',
                  {
                    rules: [{ required: true, message: '请输入工资' }]
                  }
                ]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="五险一金">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入五险一金"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['insuranceAndFund']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="原材料成本">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入原材料成本"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['material']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="折旧费">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入折旧费"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['depreciation']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="动力费">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入动力费"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['power']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="燃料费">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入燃料费"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['fuel']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备品件">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入备品件"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['trial']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="检测费对应成本">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入检测费对应成本"
                :step="1"
                style="width:190px"
                v-decorator="['test']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="修理费">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入修理费"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['repair']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="样机费">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入样机费"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['machine']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="设计费">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入设计费"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['design']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="软件费">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入软件费"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['software']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其他摊销费">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入其他摊销费"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['otherAmortization']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="其他费用">
              <a-input-number
                :min="0"
                :max="$store.state.totalMax"
                placeholder="请输入其他费用"
                :step="1"
                :precision="2"
                style="width:190px"
                v-decorator="['other']"
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
import moment from 'moment'
export default {
  components: {
    YearSelect
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      update: false
    }
  },
  mounted () {},
  methods: {
    open (update, row) {
      this.update = update
      this.confirmLoading = false
      if (update) {
        this.title = '编辑月度成本(单位：万元)'
        const month = moment().year(row.year).month(row.month - 1)
        this.$nextTick(() => {
          this.$initForm(this.form, { ...row, month }, ['month'])
        })
      } else {
        this.title = '添加月度成本(单位：万元)'
        this.form.resetFields()
      }
      this.visible = true
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          const year = values.month.year()
          const month = values.month.month() + 1
          this.$http.post('/yearCost/save', { ...values, year, month }).then(res => {
            if (res.success) {
              this.$emit('ok', true)
              this.$message.success('保存成功')
              this.onClose()
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
            }
          })
        }
      })
    },
    handleChange (value) {
      const year = value.year()
      const month = value.month() + 1
      this.$http.get('/yearCost/getMonthCost', { params: { year, month } }).then(res => {
        if (res.data && res.data != null) {
          const m = moment().year(res.data.year).month(res.data.month - 1)
          this.$initForm(this.form, { ...res.data, month: m }, ['month'])
        }
      })
    },
    onClose () {
      this.form.resetFields()
      this.visible = false
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
