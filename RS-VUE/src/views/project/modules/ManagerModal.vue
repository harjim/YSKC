<template>
  <a-modal
    :width="840"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['pname', {rules:[{required: true, message: '请输入项目名称'}]}]" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <dept-select ref="creatDept" @deptSelect="deptSelected"></dept-select>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="RD">
              <a-input
                :disabled="true"
                v-decorator="['rdIndex', {rules:[{required: true, message: '请输入RD'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="项目预算">
              <a-input-number
                :min="1"
                v-decorator="['estimateExpense', {rules:[{required: true, message: '请输入项目预算'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="负责人">
              <a-select
                showSearch
                v-decorator="['ename', {rules:[{required: true, message: '请输入名字搜索'}]}]"
                placeholder="输入名字搜索"
                style="width: 100%"
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
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="起止日期">
              <a-range-picker v-model="beginAndEnd" format="YYYY-MM-DD" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="高新领域">
              <a-cascader
                :fieldNames="{label:'value',value:'key',children:'children'}"
                :options="tecIndustryLevel"
                placeholder="请选择"
                style="width: 550px"
                v-decorator="['tecIndustry', {rules:[{required: true, message: '请选择高新技术领域'}]}]"
              />
              <br />
              <br />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import { DeptSelect } from '@/components'
import moment from 'moment'
import { mapGetters } from 'vuex'

export default {
  components: {
    DeptSelect
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
      deptId: undefined,
      deptTree: [],
      enumber: '',
      visible: false,
      resultParam: {},
      tecIndustryLevel: [],
      isCreat: false,
      confirmLoading: false,
      beginAndEnd: [moment(), moment()],
      form: this.$form.createForm(this),
      employeeList: []
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  created () {
    this.$highTecIndustryTree(this).then(res => {
      this.tecIndustryLevel = res
    })
  },
  methods: {
    moment,
    edit (record) {
      this.isCreat = false
      this.title = '编辑项目[' + record.pname + ']'
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.employeeList = []
      this.enumber = record.enumber
      record.tecIndustry = record.tecIndustry === null ? null : record.tecIndustry instanceof Array ? record.tecIndustry : record.tecIndustry.split(',')
      this.$nextTick(() => {
        this.$initForm(this.form, record)
        if (record.ename != null) {
          this.$initForm(this.form, { ename: record.ename + '(' + record.masterENumber + ')' })
        }

        this.$refs.creatDept.setDept(record.deptId)
        this.beginAndEnd = []
        this.beginAndEnd.push(moment(record.beginDate))
        this.beginAndEnd.push(moment(record.endDate))
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.tecIndustry = values.tecIndustry.toString()
          values.id = this.id
          values.deptId = this.deptId
          values.masterENumber = this.enumber
          values.beginDate = this.beginAndEnd[0].format('YYYY-MM-DD')
          values.endDate = this.beginAndEnd[1].format('YYYY-MM-DD')
          values.currentYear = this.currentYear
          this.$http.post('/project/update', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    deptSelected (deptId) {
      this.deptId = deptId
    },

    handleSearch (value) {
      this.$http.get('/project/getEmployeeList', { params: { ename: value, year: this.currentYear } })
        .then(res => {
          this.employeeList = res.data
        })
    },
    handleChange (value) {
      this.enumber = value
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
