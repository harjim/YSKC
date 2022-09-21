<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24" v-show="isAdd">
          <a-col :span="12" v-if="isAdd">
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                v-decorator="['ecodeName', {rules:[{required: true, message: '请输入设备名称'}]}]"
                placeholder="请输入设备名称"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="handleSearch"
                @change="handleChange"
                :notFoundContent="null"
              >
                <a-select-option
                  v-for="d in equipmentList"
                  :key="d.ename + '^' + d.ecode"
                >{{ `${d.ename}(${d.ecode})` }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12" v-show="isAdd">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="月份">
              <a-month-picker
                format="YYYY-MM"
                :disabledDate="disabledDate"
                v-decorator="['month',{rules:[{required: true, message: '请选择月份'}]}]"
                placeholder="请选择月份"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="月折旧额">
              <a-input-number
                :max="$store.state.totalMax"
                placeholder="请输入月折旧额"
                :precision="2"
                v-decorator="['depreciation', {rules:[{required: true, message: '请输入月折旧额'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="运行工时">
              <a-input-number
                :min="-getMax()"
                :max="getMax()"
                :precision="2"
                placeholder="请输入运行工时"
                v-decorator="['workHours', {rules:[{required: false, message: '请输入运行工时'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <!-- <a-col :span="12">
            <a-form-item label="设备类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                v-decorator="['etype', {rules:[{required: false, message: '请选择设备类型'}]}]"
                placeholder="请选择设备类型"
              >
                <a-select-option value="0">普通</a-select-option>
                <a-select-option value="30000">设备</a-select-option>
                <a-select-option value="30100">仪器</a-select-option>
                <a-select-option value="40001">软件摊销</a-select-option>
              </a-select>
            </a-form-item>
          </a-col> -->
          <a-col :span="12">
            <a-form-item label="科目" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <select-down
                ref="selectDown"
                treeType="account"
                v-decorator="['accountTitleId', {rules:[{required: false, message: '请选择科目'}]}]"
                placeholder="请选择科目"
                @select="getSelectVal"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['deptName', {rules:[{required: false, message: '请输入部门'}]}]"
                placeholder="请输入部门"
              />
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
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      title: '',
      isAdd: false,
      visible: false,
      ename: '',
      ecode: '',
      id: -1,
      confirmLoading: false,
      equipmentList: [],
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.title = '添加设备使用'
      this.id = -1
      this.isAdd = true
      this.equipmentList = []
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.ename = ''
      this.ecode = ''
      this.$nextTick(() => {
        this.$refs.selectDown.setValue(0)
      })
    },
    edit (record) {
      this.title = `编辑设备使用[${record.ename}]`
      this.id = record.id
      this.ecode = record.ecode
      this.isAdd = false
      this.confirmLoading = false
      this.form.resetFields()
      this.visible = true
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['month'])
        this.$refs.selectDown.setValue(record.accountTitleId)
      })
    },
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (!values.workHours) {
            values.workHours = this.getMax()
          }
          if (!values.depreciation) {
            values.depreciation = 0
          }
          if (!values.accountTitleId) {
            values.accountTitleId = -1
          }
          if (!this.isAdd) {
            values.id = this.id
            this.$http.post('/dEquipment/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$message.success('更新成功')
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.ecode = this.ecode
            values.ename = this.ename
            this.$http.post('/dEquipment/addEquipment', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$message.success('添加成功')
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
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
    handleSearch (value) {
      if (value === '') {
        this.equipmentList = []
        return
      }
      this.$http.get('/dEquipment/getEquipment', { params: { value: value } })
        .then(res => {
          this.equipmentList = res.data
          return this.equipmentList
        })
    },
    handleChange (value) {
      const arr = value.split('^')
      this.ename = arr[0]
      this.ecode = arr[1]
    },
    getMax () {
      var month = this.form.getFieldValue('month')
      if (month) {
        month = month.format('YYYY-MM')
        return new Date(month.substr(0, 4), month.substr(5), 0).getDate() * 24
      }
      return 744
    },
    getSelectVal (val) {
      this.form.setFieldsValue({ accountTitleId: val })
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
