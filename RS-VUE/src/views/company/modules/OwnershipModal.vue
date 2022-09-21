<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="主要股东名称:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入主要股东名称"
              v-decorator="['shareholder', {rules:[{required: true, message: '请输入主要股东名称'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            :label="capitalContributionTitle"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-input-number
              placeholder="请输入出资额"
              v-decorator="['capitalContribution', {rules:[{required: true, message: '请输入出资额'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="出资方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入出资方式"
              v-decorator="['contributionType', {rules:[{required: true, message: '请输入出资方式'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="所占比例(%):" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :min="0.01"
              :max="100"
              :step="0.01"
              :precision="2"
              placeholder="请输入所占比例"
              v-decorator="['proportion', {rules:[{required: true, message: '请输入所占比例'}]}]"
              @blur="o=>checkProportion(o)"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'OwnershipModal',
  components: {
  },
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
      oldProportion: 0,
      confirmLoading: false,
      form: this.$form.createForm(this),
      submitState: false,
      capitalContributionTitle: '出资额(万人民币)'
    }
  },
  mounted () {
  },
  methods: {
    checkProportion (o) {
      var val = parseFloat(o.target.value)
      if (isNaN(val)) {
        val = 0
      }
      return this.$http.get('/ownership/checkProportion', { params: { proportion: val, oldProportion: this.oldProportion } })
        .then(res => {
          if (res.data !== -1) {
            this.form.setFields({ 'proportion': { value: o.target.value, errors: [new Error('所占比例剩余' + res.data + '%,请重新输入。')] } })
            this.submitState = true
          } else {
            this.form.setFields({ 'proportion': { value: o.target.value } })
            this.submitState = false
          }
          return res.data
        })
    },
    add (capitalContributionTitle) {
      this.oldProportion = 0
      this.title = '添加股东'
      this.capitalContributionTitle = capitalContributionTitle
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = 0
    },
    edit (record, capitalContributionTitle) {
      this.oldProportion = record.proportion
      this.title = `编辑股东[${record.shareholder}]`
      this.capitalContributionTitle = capitalContributionTitle
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      if (this.submitState) {
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          if (values.id === 0) {
            this.$http.post('/ownership/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/ownership/edit', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', false)
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
