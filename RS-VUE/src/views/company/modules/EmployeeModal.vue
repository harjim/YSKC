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
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="工号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                :disabled="id!==-1"
                placeholder="请输入工号"
                @blur="checkenumber"
                v-decorator="['enumber', {rules:[{required: true, message: '请输入工号'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入姓名"
                v-decorator="['ename', {rules:[{required: true, message: '请输入姓名'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门">
              <a-input
                placeholder="请输入部门"
                v-decorator="['deptName', {rules:[{required: true, message: '请输入部门'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="出生日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker placeholder="请选择出生日期" format="YYYY-MM-DD" v-decorator="['birthday']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="性别">
              <a-select showSearch v-decorator="['gender']" placeholder="请选择性别">
                <a-select-option value="0">无</a-select-option>
                <a-select-option value="1">女</a-select-option>
                <a-select-option value="2">男</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入身份证号" v-decorator="['idNumber']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="入职日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                placeholder="请选择入职日期"
                format="YYYY-MM-DD"
                @change="registerTimeChange"
                v-decorator="['edate']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="职位">
              <a-input v-decorator="['position']" placeholder="请输入职位" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="职称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['title']" placeholder="请输入职称" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="学历">
              <a-select showSearch v-decorator="['eduLevel']" placeholder="请选择学历">
                <a-select-option value="0">无</a-select-option>
                <a-select-option value="7">初中</a-select-option>
                <a-select-option value="1">高中</a-select-option>
                <a-select-option value="2">中专</a-select-option>
                <a-select-option value="3">大专</a-select-option>
                <a-select-option value="4">本科</a-select-option>
                <a-select-option value="5">硕士</a-select-option>
                <a-select-option value="6">博士</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="专业" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入专业" v-decorator="['specialities']" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="离职日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker placeholder="请选择离职日期" format="YYYY-MM-DD" v-decorator="['leaveDate']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12" v-for="obj in employeeArr" :key="obj.name">
            <a-form-item :label="obj.alias" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input :placeholder="`请输入${obj.alias}`" v-decorator="[`${obj.name}`]"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="备注"
              :labelCol=" {xs: { span: 24 },sm: { span: 3 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
              :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 300 ? 300 : r.length}/300)`}"
            >
              <a-textarea v-decorator="['remark']" :rows="3" maxlength="300"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
export default {
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
      id: -1,
      thisEnumber: '',
      checked: true,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  props: {
    employeeArr: {
      type: Array,
      default: () => []
    }
  },
  mounted () {
  },
  methods: {
    add () {
      this.checked = true
      this.title = '添加花名册'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
    },
    edit (record) {
      this.checked = true
      this.title = `编辑花名册[${record.ename}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      record = Object.assign(record, JSON.parse(record.data))
      this.$nextTick(() => {
        if (record.edate && record.birthday && record.leaveDate) {
          this.$initForm(this.form, record, ['edate', 'birthday', 'leaveDate'])
        } else if (record.edate && record.birthday) {
          this.$initForm(this.form, record, ['edate', 'birthday'])
        } else if (record.edate && record.leaveDate) {
          this.$initForm(this.form, record, ['edate', 'leaveDate'])
        } else if (record.edate) {
          this.$initForm(this.form, record, ['edate'])
        } else if (record.birthday && record.leaveDate) {
          this.$initForm(this.form, record, ['birthday', 'leaveDate'])
        } else if (record.birthday) {
          this.$initForm(this.form, record, ['birthday'])
        } else if (record.leaveDate) {
          this.$initForm(this.form, record, ['leaveDate'])
        } else {
          this.$initForm(this.form, record)
        }
        this.thisEnumber = record.enumber
      })
    },
    handleSubmit () {
      if (!this.checked) {
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const data = {}
          this.employeeArr.forEach(elem => { data[elem.name] = values[elem.name] })
          values.data = JSON.stringify(data)
          if (this.id === -1) {
            this.$http.post('/employee/addEmployee', values)
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
            values.oldEnumber = this.thisEnumber
            this.$http.post('/employee/updateEmployee', values)
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
    registerTimeChange (date, dateString) {
    },
    checkenumber () {
      const enumber = this.form.getFieldValue('enumber')
      if (enumber) {
        this.$http.get('/employee/checkEnumber', { params: { enumber: enumber, id: this.id } })
          .then(res => {
            if (!res.data) {
              this.checked = false
              this.form.setFields({ 'enumber': { value: enumber, errors: [new Error('工号已被使用，请输入新工号。')] } })
            } else {
              this.checked = true
              this.form.setFields({ 'enumber': { value: enumber } })
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
