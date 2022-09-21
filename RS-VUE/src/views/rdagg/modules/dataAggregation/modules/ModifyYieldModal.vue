<!-- <template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="900"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    @cancel="closeModal"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="部门:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入部门"
              v-decorator="['deptName', {rules:[{required: true, message: '请输入部门'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="试制品名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入试制品名"
              v-decorator="['trialProduct', {rules:[{required: false, message: '请输入试制品名'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="单位:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入单位"
              v-decorator="['unit', {rules:[{required: true, message: '请输入单位'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="总量:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入总量"
              v-decorator="['totalYield', {rules:[{required: true, message: '请输入总量'}]}]"
              :precision="2"
              :max="$store.state.totalMax"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="计划试制量" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入计划试制量"
              v-decorator="['planYield', {rules:[{required: true, message: '请输入计划试制量'}]}]"
              :precision="2"
              :max="$store.state.totalMax"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="试制量:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入试制量"
              v-decorator="['rdYield', {rules:[{required: true, message: '请输入试制量'}]}]"
              :max="getMax()"
              :precision="2"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="试制日期:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker style="width: 100%" v-decorator="['trialDate',{rules:[{required: true, message: '请输入试制日期'}]}]" :disabledDate="disabledDate" :defaultPickerValue="moment(this.params.month).startOf('month')" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="试制起止时间:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }" >
              <a-time-picker
                style="width: 100%"
                format="HH:mm"
                v-decorator="['startTime']"
                :disabledHours="()=>disabledHours('endTime',true)"
                :disabledMinutes="(h) => disabledMinutes('endTime',true,h)" />
            </a-form-item>
            <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
              -
            </span>
            <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
              <a-time-picker
                style="width: 100%"
                format="HH:mm"
                v-decorator="['endTime']"
                :disabledHours="()=>disabledHours('startTime',false)"
                :disabledMinutes="(h) => disabledMinutes('startTime',false,h)" />
            </a-form-item>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
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
      form: this.$form.createForm(this),
      visible: false,
      confirmLoading: false,
      id: undefined,
      params: {},
      title: ''
    }
  },
  methods: {
    moment,
    add (params) {
      this.title = `添加[${params.rdTitle}]总量/试制量`
      this.id = undefined
      this.params = params
      this.visible = true
      this.form.resetFields()
    },
    edit (row, rdTitle) {
      this.title = `编辑[${rdTitle}]总量/试制量`
      this.id = row.id
      this.form.resetFields()
      this.visible = true
      this.$nextTick(() => {
        const startTime = row.startTime ? moment().format('YYYY-MM-DD') + ' ' + row.startTime : undefined
        const endTime = row.endTime ? moment().format('YYYY-MM-DD') + ' ' + row.endTime : undefined
        this.form.setFieldsValue({
          deptName: row.deptName,
          unit: row.unit,
          totalYield: row.totalYield,
          planYield: row.planYield,
          rdYield: row.rdYield,
          trialDate: moment(row.trialDate),
          startTime: startTime ? moment(startTime) : undefined,
          endTime: endTime ? moment(endTime) : undefined,
          trialProduct: row.trialProduct
        })
      })
    },
    getMax () {
      const max = Number(this.form.getFieldValue('totalYield'))
      if (max) {
        return max
      }
      return 0
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (values.startTime) {
            values.startTime = values.startTime.format('HH:mm')
          }
          if (values.endTime) {
            values.endTime = values.endTime.format('HH:mm')
          }
          Object.assign(this.params, values)
          if (!this.id) {
            this.$http.post('/projectYieldConfig/add', this.params).then(res => {
              if (res.success && res.data) {
                this.$message.success('添加成功')
                this.closeModal()
                this.$emit('ok')
              } else {
                this.confirmLoading = false
                this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
              }
            })
          } else {
            this.params.id = this.id
            this.$http.post('/projectYieldConfig/edit', this.params).then(res => {
              if (res.success && res.data) {
                this.$message.success('更新成功')
                this.$emit('ok')
                this.closeModal()
              } else {
                this.confirmLoading = false
                this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
              }
            })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    closeModal () {
      this.id = undefined
      this.visible = false
      this.confirmLoading = false
    },
    disabledDate (currentDate) {
      const startDay = moment(this.params.month).startOf('month')
      const endDay = moment(this.params.month).endOf('month')
      return currentDate < startDay || currentDate > endDay
    },
    // 禁用时间选择框 -- 时
    disabledHours (key, le) {
      const hours = []
      const time = this.form.getFieldValue(key)
      if (time) {
        const hour = time.toDate().getHours()
        if (le) {
          for (let i = hour + 1; i < 24; i++) {
            hours.push(i)
          }
        } else {
          for (let i = hour - 1; i >= 0; i--) {
            hours.push(i)
          }
        }
      }
      return hours
    },
    disabledMinutes (key, le, h) {
      const minutes = []
      const time = this.form.getFieldValue(key)
      if (time) {
        const minute = time.toDate().getMinutes()
        if (h === time.toDate().getHours()) {
          if (le) {
            for (let i = minute + 1; i < 60; i++) {
              minutes.push(i)
            }
          } else {
            for (let i = minute - 1; i >= 0; i--) {
              minutes.push(i)
            }
          }
        }
      }
      return minutes
    }
  }
}
</script>

<style>
</style>
-->
