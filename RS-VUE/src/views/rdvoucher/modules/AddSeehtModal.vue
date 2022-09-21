<template>
  <a-modal
    :width="500"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @cancel="closeModal"
    @ok="handleSubmit"
    :afterClose="afterClose">
    <a-form layout="horizontal" :form="form" ref="form">
      <a-form-item
        label="凭证号"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <a-input
          placeholder="请输入凭证号"
          @blur="checkVoucherNo"
          v-decorator="['voucherNo',{ rules: [{ required: true, message: '请输入凭证号' }] }]" ></a-input>
      </a-form-item>
      <a-form-item
        label="日期"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <a-date-picker
          style="width: 100%"
          placeholder="请选择日期"
          v-decorator="['voucherDate',{ rules: [{ required: true, message: '请选择日期' }] }]" />
      </a-form-item>
      <a-form-item
        label="金额"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <a-input-number
          :min="0"
          :max="999999999999999999"
          placehloder="请输入金额"
          style="width: 100%"
          v-decorator="['amount',{ rules: [{ required: true, message: '请输入金额' }] }]" ></a-input-number>
        <!-- <a-input placeholder="请输入金额"></a-input> -->
      </a-form-item>
      <a-form-item
        label="项目"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <project-select
          :selected="projectIds"
          :sign="2"
          :isMul="true"
          :year="year"
          width="100%"
          @getPrjectIds="getPrjectIds"
          v-decorator="['verifyProjects',{ rules: [{ required: isVerifyProjectsRequired, message: '请选择项目' }] }]"></project-select>
      </a-form-item>
      <a-form-item
        label="费用类型"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <a-select
          @change="Backfill"
          :allowClear="true"
          :filter-option="filterOption"
          placeholder="请选择费用类型"
          v-decorator="['rdType',{ rules: [{ required: isRequired, message: '请选择费用类型' }] }]">
          <a-select-option v-for="item in costTypes" :key="item.type" :title="item.title" :text="item.title">{{ item.title }}</a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item
        label="摘要"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <a-input placeholder="请输入摘要" v-decorator="['summary', { rules: [{ required: true, message: '请输入摘要' }] }]"></a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import ProjectSelect from '@/components/ProjectSelect'
import moment from 'moment'
// const currentYear = new Date().getFullYear()
export default {
  name: 'AddSeehtModal',
  components: {
    ProjectSelect
  },
  props: {
    year: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      visible: false,
      title: '',
      projectIds: undefined, // 项目id
      id: 0, // 凭证Id
      form: this.$form.createForm(this),
      costTypes: this.$getCostTypes(),
      costtype: 0,
      isRequired: false,
      // isDisable: false,
      isVerifyProjectsRequired: false,
      isAdd: true,
      labelCol: {
        xs: { span: 2 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 20 },
        sm: { span: 18 }
      }
    }
  },
  methods: {
    moment,
    add () {
      this.title = '添加凭证'
      this.visible = true
    },
    edit (record) {
      this.title = '修改凭证'
      this.visible = true
      if (record.projectIds) {
        this.projectIds = record.projectIds
      } else {
        this.projectIds = undefined
      }
      this.id = record.id
      this.isAdd = false
      if (record.projectIds || record.rdType) {
        this.isRequired = true
        this.isVerifyProjectsRequired = true
      }
      this.$nextTick(() => {
        this.form.setFieldsValue({
          voucherNo: record.voucherNo,
          rdType: record.rdType !== null ? record.rdType : undefined,
          amount: record.amount !== null ? record.amount : undefined,
          voucherDate: this.moment(record.voucherDate),
          summary: record.summary !== null ? record.summary : undefined,
          verifyProjects: record.projectIds != null ? record.projectIds : undefined
        })
      })
    },
    closeModal () {
      this.visible = false
    },
    // onDateChange () {

    // },
    getPrjectIds (values) {
      if (values.length > 0) {
        this.isRequired = true
        this.isVerifyProjectsRequired = true
      } else {
        const rdType = this.form.getFieldValue('rdType')
        if (rdType) {
          this.isRequired = true
          this.isVerifyProjectsRequired = true
        } else {
          this.isRequired = false
          this.form.setFields({ 'verifyProjects': { value: undefined } })
          this.form.setFields({ 'rdType': { value: undefined } })
          this.isVerifyProjectsRequired = false
        }
      }
      this.projectIds = values
      let obj = {}
      if (values.length !== 0) {
        obj = { verifyProjects: values }
      } else {
        obj = { verifyProjects: undefined }
      }
      this.form.setFieldsValue(obj)
    },
    afterClose () {
      this.form.resetFields()
      this.title = ''
      this.projectIds = undefined
      this.isRequired = false
      this.isVerifyProjectsRequired = false
      // this.isDisable = false
      this.isAdd = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          if (this.isAdd) {
            values.projectIds = this.projectIds
            this.$http.post('/voucher/addVoucher', values).then((res) => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true, true) // param1 成功 param2 是否当前刷新
              } else {
                this.$message.error(res.errorMessage)
              }
            })
          } else {
            values.projectIds = this.projectIds
            values.id = this.id
            this.$http.post('/voucher/updateVoucher', values).then((res) => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true, false) // param1 成功 param2 是否当前刷新
              } else {
                this.$message.error(res.errorMessage)
              }
            })
          }
        }
      })
    },
    checkVoucherNo () {
      const voucherNo = this.form.getFieldValue('voucherNo')
      if (voucherNo) {
        this.$http.get('/voucher/checkVoucherNo', { params: { voucherNo: voucherNo } })
          .then(res => {
            if (!res.data) {
              this.checked = false
              this.form.setFields({ 'voucherNo': { value: voucherNo, errors: [new Error('该凭证号已存在')] } })
            } else {
              this.checked = true
              this.form.setFields({ 'voucherNo': { value: voucherNo } })
            }
          })
      }
    },
    filterOption (input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    },
    findCostName (rdType) {
      const list = this.$getCostTypes()
      const index = list.findIndex((item) => {
        return item.type === rdType * 1
      })
      if (index !== -1) {
        return list[index].title
      }
      return ''
    },
    Backfill (value, option) {
      if (!option) {
        this.$nextTick(() => {
          if (this.projectIds) {
            this.isRequired = false
            this.isVerifyProjectsRequired = false
            this.form.setFields({ 'rdType': { value: undefined } })
            this.form.setFields({ 'verifyProjects': { value: undefined } })
          }
          this.form.setFieldsValue({ summary: undefined })
        })
        return
      }
      const costName = option.data.attrs.text
      if (costName) {
        this.isRequired = true
        this.isVerifyProjectsRequired = true
      } else {
        this.isRequired = false
        this.form.setFields({ 'rdType': { value: undefined } })
        this.form.setFields({ 'verifyProjects': { value: undefined } })
        this.isVerifyProjectsRequired = false
      }
      this.$nextTick(() => {
        this.form.setFieldsValue({ summary: costName })
      })
    }
  }
}
</script>

<style>

</style>
