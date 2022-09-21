<template>
  <a-form :form="form">
    <a-row :gutter="24">
      <a-col :span="12">
        <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            disabled
            v-decorator="['pname', {rules:[{required: true, whitespace: true, message: '请输入项目名称'}]}]"
            placeholder="请输入项目名称"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            v-decorator="['pyear', {rules:[{required: true, message: '请选择年份'}]}]"
            :options="years"
          ></a-select>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="24">
      <a-col :span="12">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="申报方向">
          <a-input
            disabled
            v-decorator="['reportType', {rules:[{required: false, whitespace: true, message: '请输入申报方向'}]}]"
            placeholder="请输入申报方向"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="扶持方式">
          <a-input
            v-decorator="['aidType', {rules:[{required: false, whitespace: true, message: '请输入扶持方式'}]}]"
            placeholder="请输入扶持方式"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="24">
      <a-col :span="12">
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['masterName', {rules:[{required: true,whitespace: true, message: '请输入负责人'}]}]"
            placeholder="请输入负责人"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="负责人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['masterTel', {rules:[{required: true,whitespace: true, message: '请输入负责人电话'}]}]"
            placeholder="请输入负责人电话"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="24">
      <a-col :span="12">
        <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['linkName', {rules:[{required: true, whitespace: true, message: '请输入联系人'}]}]"
            placeholder="请输入联系人"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="联系人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['linkTel', {rules:[{required: true,whitespace: true, message: '请输入联系人电话'}]}]"
            placeholder="请输入联系人电话"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row :gutter="24">
      <a-col :span="12">
        <a-form-item :required="true" :labelCol="labelCol" :wrapperCol="wrapperCol" label="项目起止日期" style="margin-bottom: 0;">
          <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
            <a-date-picker
              :disabledDate="disabledBeginDate"
              format="YYYY-MM-DD"
              placeholder="开始日期"
              v-decorator="['beginDate', {rules:[{required: true, message: '请选择开始日期'}]}]"
            />
          </a-form-item>
          <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
            -
          </span>
          <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
            <a-date-picker
              :disabledDate="disabledEndDate"
              format="YYYY-MM-DD"
              placeholder="结束日期"
              v-decorator="['endDate', {rules:[{required: true, message: '请选择结束日期'}]}]"
            />
          </a-form-item>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="申请日期">
          <a-date-picker
            format="YYYY-MM-DD"
            v-decorator="['applyDate',{rules:[{required: true, message: '请选择申请日期'}]}]"
            placeholder="请选择申请日期"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="24">
        <a-form-item
          label="备注"
          :labelCol=" {xs: { span: 24 },sm: { span: 3 } }"
          :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
          :extra="onComputeTextarea('remark',200)"
        >
          <a-textarea v-decorator="['remark']" :rows="3"></a-textarea>
        </a-form-item>
      </a-col>
    </a-row>
  </a-form>
</template>

<script>
export default {
  name: 'BaseTab',
  props: {
    record: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      form: this.$form.createForm(this),
      years: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      }
    }
  },
  mounted () {
    const year = new Date().getFullYear() + 3
    this.years = []
    for (var i = 6; i > 0; i--) {
      var node = { key: (year - i), label: (year - i) }
      this.years.push(node)
    }
    this.form.resetFields()
    this.$nextTick(() => {
      if (this.record) {
        this.$initForm(this.form, this.record, ['applyDate', 'beginDate', 'endDate'])
      }
    })
  },
  methods: {
    baseSubmit () {
      if (!this.$auth('tech:pro:edit')) {
        return
      }
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.record.id
          if (!values.id) {
            this.$http.post('/techProject/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.$message.success('添加成功')
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/techProject/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.$message.success('更新成功')
                  Object.assign(this.record, values)
                  this.$emit('ok', false, values)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    disabledBeginDate (currentValue) {
      const endDate = this.form.getFieldValue('endDate')
      return endDate ? currentValue > endDate : false
    },
    disabledEndDate (currentValue) {
      const beginDate = this.form.getFieldValue('beginDate')
      return beginDate ? currentValue < beginDate : false
    },
    /**
     * @description: 计算与控制文本域的个数
     * @param fieldName { type: String }
     * @param limitNumber {type:String }
     * @return String { type: String }
     */
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    }
  }
}
</script>

<style lang="less" spaced>

</style>
