<template>
  <a-modal
    :width="500"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            showSearch
            v-decorator="['ename', {rules:[{required: true, message: '请输入姓名'}]}]"
            placeholder="请输入姓名"
            style="width: 250px"
            :defaultActiveFirstOption="false"
            :showArrow="false"
            :filterOption="false"
            @search="handleSearch"
            @change="handleChange"
            :notFoundContent="null"
          >
            <a-select-option
              v-for="d in employeeList"
              :key="d.enumber"
            >{{ d.ename + '(' + d.enumber + ')' }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="请输入金额"
            style="width:250px"
            :max="$store.state.totalMax"
            v-decorator="['bonus', {rules:[{required: true, message: '请输入金额'}]}]"
          />
        </a-form-item>
        <a-form-item label="起止日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-range-picker
            style="width:250px"
            :disabledTime="disabledTime"
            :defaultPickerValue="date.defaultPickerValue"
            v-decorator="['rangeTime', {rules:[{required: true, message: '请输入发放奖金的起止日期'}]}]"
            @change="(dates,dateStrings)=>onDateChange(dateStrings)"
          />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="科目"
          validateStatus="success"
        >
          <select-down
            treeType="account"
            v-decorator="['accountTitleId']"
            ref="accountSelect"
            @select="accountSelected"
            placeholder="请选择科目"
            style="width:250px"
          />
        </a-form-item>
        <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="请输入部门"
            style="width:250px"
            v-decorator="['deptName', {rules:[{required: false, message: '请输入部门'}]}]"
          />
        </a-form-item>
        <a-form-item
          label="备注"
          :labelCol=" {xs: { span: 24 },sm: { span: 6 } }"
          :wrapperCol="{xs: { span: 24 },sm: { span: 16 } }"
          :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
        >
          <a-textarea v-decorator="['remark']" style="width:250px" maxlength="200" :row="3"></a-textarea>
        </a-form-item>
      </a-card>
    </a-form>
  </a-modal>
</template>
<script>
import moment from 'moment'
import SelectDown from '@/components/SelectDown'
export default {
  components: {
    SelectDown
  },
  name: 'DataBonusModal',
  deptTree: [],
  data () {
    return {
      date: { defaultPickerValue: [] },
      year: moment().year,
      enumber: {},
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      isCreat: false,
      insuranceFund: 0,
      employeeList: [],
      form: this.$form.createForm(this)
    }
  },
  destroyed () {
    this.$set(this.date, 'defaultPickerValue', [])
  },
  methods: {
    disabledTime (_, type) {
      if (type === 'start') {
        return {
          disabledYears: () => [this.year]
        }
      }
      return {
        disabledYears: () => [this.year]
      }
    },
    add (year) {
      this.year = year
      var startMonth = moment([this.year, 0, 1, 0, 0, 0, 0])
      var endMonth = moment([this.year, 11, 31, 0, 0, 0, 0])
      var days = [startMonth, endMonth]
      this.title = '添加奖金'
      this.confirmLoading = false
      this.visible = true
      this.isCreat = true
      this.employeeList = []
      this.form.resetFields()
      this.$set(this.date, 'defaultPickerValue', days)
      this.id = -1
      this.$nextTick(() => {
        this.$refs.accountSelect.setValue(0)
      })
    },
    edit (record) {
      this.title = `编辑薪资[${record.ename}]`
      this.form.resetFields()
      this.visible = true
      this.isCreat = false
      this.employeeList = []
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['month'])
        if (record.ename != null) {
          this.$initForm(this.form, { ename: record.ename + '(' + record.enumber + ')' })
          this.$refs.accountSelect.setValue(record.accountTitleId)
        }
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          var item = {
            enumber: this.enumber,
            bonus: values.bonus,
            remark: values.remark,
            accountTitleId: values.accountTitleId,
            beginDay: values.rangeTime[0],
            endDay: values.rangeTime[1],
            deptName: values.deptName
          }
          this.$http.post('/dataBonus/add', item)
            .then(res => {
              this.confirmLoading = false
              if (res.success && res.data) {
                this.visible = false
                this.$message.success('添加成功')
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
              }
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    handleSearch (value) {
      if (!value || !value.trim()) {
        this.employeeList = []
        return
      }
      this.$http.get('/project/getBaseEmployeeSelect', { params: { ename: value } })
        .then(res => {
          this.employeeList = res.data
        })
    },
    handleChange (value) {
      this.enumber = value
    },
    onDateChange (dateString) {
    },
    accountSelected (accountTitleId) {
      this.form.setFieldsValue({ accountTitleId: accountTitleId })
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
