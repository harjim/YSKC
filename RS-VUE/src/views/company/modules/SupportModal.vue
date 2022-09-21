<template>
  <a-modal
    :width="900"
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
                v-decorator="['syear', {rules:[{required: true, message: '请选择年份'}]}]"
                @change="handleChange"
                placeholder="请选择年份"
                style="width:200px;"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="项目名称"
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
            >
              <a-input
                placeholder="请输入项目名称"
                v-decorator="['projectName', {rules:[{required: true, message: '请输入项目名称'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="项目建设期间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-range-picker
                @change="onChange"
                v-decorator="['beginAndEnd',{rules:[{required: true, message: '请选择项目建设期间'}],initialValue: beginAndEnd}]"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="扶持时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入扶持时间"
                v-decorator="['supportTime',{rules:[{required: true, message: '请输入扶持时间'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="扶持部门">
              <a-input
                placeholder="请输入扶持部门"
                v-decorator="['supportDeptName', {rules:[{required: true, message: '请输入扶持部门'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="扶持金额(万元)">
              <a-input-number
                :min="1"
                placeholder="请输入扶持金额(万元)"
                :precision="2"
                style="width:225px"
                v-decorator="['supportAmount', {rules:[{required: true, message: '请输入扶持金额(万元)'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="验收时间">
              <a-date-picker
                placeholder="请输入验收时间"
                format="YYYY-MM-DD"
                v-decorator="['checkTime',{rules:[{required: true, message: '请输入验收时间'}]}]"
                @change="registerTimeChange"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
              label="验收结果"
            >
              <a-input
                placeholder="请输入验收结果"
                v-decorator="['checkResult', {rules:[{required: true, message: '请输入验收结果'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="备注"
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
              :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
            >
              <a-textarea placeholder="请输入备注" v-decorator="['remark']" :rows="3" maxlength="200"></a-textarea>
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
    moment,
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
        sm: { span: 14 }
      },
      id: 0,
      deptId: undefined,
      rdDeptTree: [],
      thisEnumber: '',
      visible: false,
      isCreat: false,
      confirmLoading: false,
      beginAndEnd: [moment(), moment()],
      form: this.$form.createForm(this)
    }
  },
  mounted () {

  },
  methods: {
    add () {
      this.title = '添加扶持状况'
      this.isCreat = true
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
    },
    edit (record) {
      this.isCreat = false
      this.title = `编辑[${record.projectName}]扶持状况`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.beginAndEnd = []
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['checkTime'])
        this.beginAndEnd.push(moment(record.startTime), moment(record.endTime))
      })
    },
    moment,
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            values.startTime = this.beginAndEnd[0].format('YYYY-MM-DD')
            values.endTime = this.beginAndEnd[1].format('YYYY-MM-DD')
            this.$http.post('/support/addSupport', values)
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
            values.startTime = this.beginAndEnd[0].format('YYYY-MM-DD')
            values.endTime = this.beginAndEnd[1].format('YYYY-MM-DD')
            this.$http.post('/support/updateSupport', values)
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
    deptSelected (deptId) {
      this.deptId = deptId
    },
    registerTimeChange (date, dateString) {
    },
    handlePhoneCheck (rule, value, callback) {
      callback()
    },
    onChange (dates, dateStr) {
      this.beginAndEnd = dates
    },
    handleChange (value) {
      if (value) {
        this.beginAndEnd = []
        this.beginAndEnd.push(moment(value + '-01-01'), moment(value + '-12-31'))
        this.form.setFields({ 'beginAndEnd': { value: this.beginAndEnd }, syear: { value: value } })
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
