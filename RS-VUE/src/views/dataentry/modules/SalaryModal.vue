<template>
  <a-modal
    :width="950"
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
            <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                v-decorator="['ename', {rules:[{required: true, message: '请输入姓名'}]}]"
                placeholder="请输入姓名"
                style="width:200px"
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
          </a-col>
          <a-col :span="12">
            <a-form-item label="工资月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-month-picker
                :disabledDate="disabledDate"
                placeholder="请选择工资月份"
                style="width:200px"
                v-decorator="['month', {rules:[{required: true, message: '请选择工资月份'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="总工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入总工时"
                style="width:200px"
                :min="-getMax(true)"
                :max="getMax(true)"
                :precision="2"
                v-decorator="['workHours', {rules:[{ required: true, message: '请输入总工时'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="工作天数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="-getMax(false)"
                :max="getMax(false)"
                :precision="2"
                style="width:200px"
                placeholder="请输入工作天数"
                v-decorator="['workDays', {rules:[{ required: true, message: '请输入正确工作天数'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="应发工资" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                style="width:200px"
                :precision="2"
                :max="$store.state.totalMax"
                :disabled="configArr && configArr.length > 0"
                placeholder="请输入应发工资"
                v-decorator="['pay', {rules:[{required: !(configArr && configArr.length > 0), message: '请输入应发工资'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col v-for="config in configArr" :key="config.name" :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="config.alias">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                style="width:200px"
                v-decorator="[config.name,{rules: [{ required: true, message: `请输入${config.alias}` }]}]"
                :placeholder="`请输入${config.alias}`"
                @blur="()=>handleBlur('pay',configArr)"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="五险一金" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入五险一金"
                style="width:200px"
                :disabled="insuranceArr && insuranceArr.length > 0"
                :max="$store.state.totalMax"
                :precision="2"
                v-decorator="['insuranceFund',{rules:[{required: !(insuranceArr && insuranceArr.length > 0), message: '请输入五险一金'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col v-for="config in insuranceArr" :key="config.name" :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="config.alias">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                style="width:200px"
                v-decorator="[config.name,{rules: [{ required: true, message: `请输入${config.alias}` }]}]"
                :placeholder="`请输入${config.alias}`"
                @blur="()=>handleBlur('insuranceFund',insuranceArr)"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="科目"
              validateStatus="success"
            >
              <select-down
                treeType="account"
                ref="accountSelect"
                @select="accountSelected"
                placeholder="请选择科目"
                style="width:200px"
                v-decorator="['accountTitleId']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门">
              <a-input placeholder="请输入部门" style="width:200px" v-decorator="['deptName']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="备注"
              :labelCol=" {xs: { span: 24 },sm: { span: 3 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
              :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
            >
              <a-textarea v-decorator="['remark']" :rows="3" :maxLength="200"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
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
  name: 'SalaryModal',
  deptTree: [],
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
      confirmLoading: false,
      isCreat: false,
      employeeList: [],
      configArr: [],
      insuranceArr: [],
      form: this.$form.createForm(this)
    }
  },
  methods: {
    handleBlur (key, arr) {
      var pay = 0
      arr.forEach(c => {
        const temp = this.form.getFieldValue(c.name)
        if (temp) {
          pay += Number(temp)
        }
      })
      const obj = {}
      obj[key] = pay
      this.form.setFieldsValue(obj)
    },
    add (configArr, insuranceArr) {
      this.configArr = configArr
      this.insuranceArr = insuranceArr
      this.title = '添加薪资'
      this.confirmLoading = false
      this.visible = true
      this.isCreat = true
      this.employeeList = []
      this.form.resetFields()
      this.id = -1
      this.enumber = null
      this.$nextTick(() => {
        this.$refs.accountSelect.setValue(0)
      })
    },
    edit (record, configArr, insuranceArr) {
      this.configArr = configArr
      this.insuranceArr = insuranceArr
      this.title = `编辑薪资[${record.ename}]`
      this.form.resetFields()
      this.visible = true
      this.isCreat = false
      this.employeeList = []
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['month'])
        if (record.ename != null) {
          this.enumber = record.enumber
          this.$initForm(this.form, { ename: record.ename + '(' + record.enumber + ')' })
          this.$refs.accountSelect.setValue(record.accountTitleId)
        }
        if (record.payDetail) {
          const customField = JSON.parse(record.payDetail)
          this.$initForm(this.form, customField)
        }
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          var params = { enumber: this.enumber }
          for (var col in values) {
            if (col.indexOf('col') < 0) {
              params[col] = values[col]
            }
          }
          var payDetail = {}
          this.configArr.forEach(c => {
            if (!values[c.name]) {
              payDetail[c.name] = 0
            } else {
              payDetail[c.name] = values[c.name]
            }
          })
          var insuranceDetail = {}
          this.insuranceArr.forEach(c => {
            if (!values[c.name]) {
              insuranceDetail[c.name] = 0
            } else {
              insuranceDetail[c.name] = values[c.name]
            }
          })
          params.payDetail = JSON.stringify(payDetail)
          params.insuranceDetail = JSON.stringify(insuranceDetail)
          if (this.id === -1) {
            this.$http.post('/salary/addSalary', params)
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
            params.id = this.id
            this.$http.post('/salary/updateSalary', params)
              .then(res => {
                if (res.success && res.data) {
                  this.confirmLoading = false
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
    },
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    handleSearch (value) {
      this.$http.get('/project/getBaseEmployeeSelect', { params: { ename: value } })
        .then(res => {
          this.employeeList = res.data
        })
    },
    handleChange (value) {
      this.enumber = value
    },
    getMax (flag) {
      var month = this.form.getFieldValue('month')
      var maxDay
      if (month) {
        month = month.format('YYYY-MM')
        maxDay = new Date(month.substr(0, 4), month.substr(5), 0).getDate()
      } else {
        maxDay = 31
      }
      if (flag) {
        return maxDay * 24
      }
      return maxDay
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
