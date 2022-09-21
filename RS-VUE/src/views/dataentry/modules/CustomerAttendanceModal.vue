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
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                v-decorator="['numberName', {rules:[{required: true, message: '请输入姓名'}]}]"
                placeholder="请输入姓名"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="handleSearch"
                @change="handleChange"
                :notFoundContent="null"
              >
                <a-select-option
                  v-for="d in employeeList"
                  :key="`${d.enumber}@${d.ename}`"
                >{{ d.ename + '(' + d.enumber + ')' }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="出勤日期">
              <a-date-picker
                format="YYYY-MM-DD"
                :disabledDate="disabledDate"
                v-decorator="['workDate',{rules:[{required: true, message: '请选择出勤日期'}]}]"
                placeholder="请选择出勤日期"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="上班时间1" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                v-decorator="['onTime1',{rules:[{required: true, message: '请选择上班时间1'}]}]"
                placeholder="请选择上班时间1"
                :disabled="onTimeDisabled.onTime1"
                @change="(val,dateStrings)=>onTimeChange(val,1)"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="下班时间1">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                v-decorator="['offTime1',{rules:[{required: true, message: '请选择下班时间1'}]}]"
                @change="(val) => offTimeChange(val,1)"
                placeholder="请选择下班时间1"
                :disabledHours="() => disabledHours(1, 'offTime')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 1, 'offTime')"
                :disabled="offTimeDisabled.offTime1"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="上班时间2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                v-decorator="['onTime2', {rules:[{required: timeQequireds.time2, message: '请选择上班时间2'}]}]"
                placeholder="请选择上班时间2"
                :disabled="onTimeDisabled.onTime2"
                :disabledHours="() => disabledHours(2, 'onTime')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 2, 'onTime')"
                @change="(val,dateStrings)=>onTimeChange(val,2)"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="下班时间2">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                v-decorator="['offTime2', {rules:[{required: timeQequireds.time2, message: '请选择下班时间2'}]}]"
                @change="(val) => offTimeChange(val,2)"
                placeholder="请选择下班时间2"
                :disabledHours="() => disabledHours(2, 'offTime')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 2, 'offTime')"
                :disabled="offTimeDisabled.offTime2"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="上班时间3" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                v-decorator="['onTime3', {rules:[{required: timeQequireds.time3, message: '请选择上班时间3'}]}]"
                placeholder="请选择上班时间3"
                :disabled="onTimeDisabled.onTime3"
                :disabledHours="() => disabledHours(3, 'onTime')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 3, 'onTime')"
                @change="(val,dateStrings)=>onTimeChange(val, 3)"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="下班时间3">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                v-decorator="['offTime3', {rules:[{required: timeQequireds.time3, message: '请选择下班时间3'}]}]"
                @change="(val) => offTimeChange(val, 3)"
                placeholder="请选择下班时间3"
                :disabledHours="() => disabledHours(3, 'offTime')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 3, 'offTime')"
                :disabled="offTimeDisabled.offTime3"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0"
                :max="24"
                placeholder="请输入工时"
                :precision="2"
                v-decorator="['workHour', {rules:[{required: false, message: '请输入工时'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入部门"
                v-decorator="['deptName', {rules:[{required: false, message: '请输入部门'}]}]"
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
              :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
            >
              <a-textarea v-decorator="['remark']" :rows="3" maxlength="200"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>
<script>
import yearMixin from '@/utils/yearMixin'
import moment from 'moment'
export default {
  mixins: [yearMixin],
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
      visible: false,
      title: '',
      confirmLoading: false,
      id: -1,
      form: this.$form.createForm(this),
      employeeList: [],
      enumber: '',
      ename: '',
      // startTime: '00:00:00',
      // endTime: '23:59:59',
      timePickDisabled: false,
      totalTimes: { 1: 0, 2: 0, 3: 0 },
      offTimeDisabled: { offTime1: true, offTime2: true, offTime3: true },
      onTimeDisabled: { onTime1: false, onTime2: true, onTime3: true },
      timeQequireds: { time1: true, time2: false, time3: false }
    }
  },
  methods: {
    // 禁用时间选择框 -- 时
    disabledHours (index, type) {
      const hours = []
      let time = null
      let start = 0
      // const time = this.startTime
      if (type === 'onTime' && index > 1) {
        const previousOffTime = this.form.getFieldValue(`offTime${index - 1}`)
        if (previousOffTime) {
          time = previousOffTime.format('HH:mm:ss')
        } else {
          for (let i = 0; i < 24; i++) {
            hours.push(i)
          }
          return hours
        }
      }
      if (type === 'offTime') {
        const currentOnTime = this.form.getFieldValue(`onTime${index}`)
        const nextOnTime = this.form.getFieldValue(`onTime${index + 1}`)
        if (currentOnTime) {
          time = currentOnTime.format('HH:mm:ss')
          if (nextOnTime) {
            start = nextOnTime.format('HH:mm:ss').split(':')[0] * 1
          }
        } else {
          for (let i = 0; i < 24; i++) {
            hours.push(i)
          }
          return hours
        }
      }
      const timeArr = time.split(':')
      for (let i = 0; i < parseInt(timeArr[0]); i++) {
        hours.push(i)
      }
      if (start > 0) {
        for (let i = start + 1; i < 24; i++) {
          hours.push(i)
        }
      }
      // console.log(start)
      // console.log(hours)
      return hours
    },
    // 禁用时间选择框 -- 分
    disabledMinutes (selectedHour, index, type) {
      const minutes = []
      let start = []
      let time = null
      // const time = this.startTime
      if (type === 'onTime' && index > 1) {
        const previousOffTime = this.form.getFieldValue(`offTime${index - 1}`)
        if (previousOffTime) {
          time = previousOffTime.format('HH:mm:ss')
        } else {
          for (let i = 0; i < 60; i++) {
            minutes.push(i)
          }
          return minutes
        }
      }
      if (type === 'offTime') {
        const currentOnTime = this.form.getFieldValue(`onTime${index}`)
        const nextOnTime = this.form.getFieldValue(`onTime${index + 1}`)
        if (currentOnTime) {
          // time = currentOnTime.format('HH:mm:ss')
          time = currentOnTime.format('HH:mm:ss')
          if (nextOnTime) {
            // start = nextOnTime.format('HH:mm:ss').split(':')[0] * 1
            start = nextOnTime.format('HH:mm:ss').split(':')
          }
        } else {
          for (let i = 0; i < 60; i++) {
            minutes.push(i)
          }
          return minutes
        }
      }

      const timeArr = time.split(':')
      if (selectedHour === parseInt(timeArr[0])) {
        for (let i = 0; i < parseInt(timeArr[1]); i++) {
          minutes.push(i)
        }
      }
      if (start.length > 0) {
        if (selectedHour === start[0] * 1) {
          for (let i = parseInt(start[1]) + 1; i < 60; i++) {
            minutes.push(i)
          }
        }
      }
      return minutes
    },
    onTimeChange (onTime, index) {
      this.offTimeDisabled[`offTime${index}`] = false
      this.getWorkhour(onTime, index, 'onTime')
      const offTime = this.form.getFieldValue(`offTime${index}`)
      let totalTime = 0
      for (const key in this.totalTimes) {
        totalTime += this.totalTimes[key]
      }
      this.form.setFields({ workHour: { value: totalTime } })
      if (onTime > offTime) {
        this.form.setFields({ [`offTime${index}`]: null })
      }
      if (index > 1) {
        const temp = {}
        temp[`offTime${index}`] = null
        this.form.setFields(temp)
        // const offTime = this.form.getFieldValue(`offTime${index}`)
        if (onTime != null || offTime != null) {
          this.timeQequireds[`time${index}`] = true
        }
        if (onTime == null && offTime == null) {
          this.timeQequireds[`time${index}`] = false
          this.$nextTick(() => {
            this.form.validateFields([`onTime${index}`], { force: true })
            this.form.validateFields([`offTime${index}`], { force: true })
          })
        }
      }
    },
    offTimeChange (offTime, index) {
      const onTime = this.form.getFieldValue(`onTime${index}`)
      // if (!onTime || !offTime) {
      //   return
      // }
      let totalTime = 0
      this.getWorkhour(offTime, index, 'offTime')
      for (const key in this.totalTimes) {
        totalTime += this.totalTimes[key]
      }
      this.form.setFields({ workHour: { value: totalTime } })
      this.onTimeDisabled[`onTime${index + 1}`] = false
      if (index > 1) {
        if (onTime != null || offTime != null) {
          this.timeQequireds[`time${index}`] = true
        }
        if (onTime == null && offTime == null) {
          this.timeQequireds[`time${index}`] = false
          this.$nextTick(() => {
            this.form.validateFields([`onTime${index}`], { force: true })
            this.form.validateFields([`offTime${index}`], { force: true })
          })
        }
      }
    },
    getWorkhour (cuurentTime, index, type) {
      let onTime = this.form.getFieldValue(`onTime${index}`)
      let offTime = this.form.getFieldValue(`offTime${index}`)

      if (type === 'onTime') {
        onTime = cuurentTime
      }

      if (type === 'offTime') {
        offTime = cuurentTime
      }
      if (!offTime || !onTime) {
        this.totalTimes[index] = 0
      } else {
        const time = Math.abs(moment(onTime, 'HH:mm').format('x') - moment(offTime, 'HH:mm').format('x'))
        this.totalTimes[index] = (moment.duration(time).hours() + moment.duration(time).minutes() / 60).toFixed(2) * 1
      }
    },
    moment,
    disabledDate (current) {
      var end = moment().endOf('day')
      var start = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
      const nowYear = new Date().getFullYear()
      if (nowYear !== this.currentYear) {
        if (nowYear < this.currentYear) {
          start = moment([nowYear, 11, 31, 0, 0, 0, 0])
          end = moment([nowYear, 0, 1, 0, 0, 0, 0])
        } else {
          end = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        }
      }
      return !current || current > end || current < start
    },
    showModal () {
      this.id = -1
      this.employeeList = []
      this.title = '添加员工考勤'
      this.form.resetFields()
      this.visible = true
      this.offTimeDisabled = { offTime1: true, offTime2: true, offTime3: true }
      this.onTimeDisabled = { onTime1: false, onTime2: true, onTime3: true }
      this.timeQequireds = { time1: true, time2: false, time3: false }
      this.totalTimes = { 1: 0, 2: 0, 3: 0 }
    },
    edit (record) {
      const tempRow = this.$deepClone(record)
      this.timeQequireds = { time1: false, time2: false, time3: false }
      for (let i = 1; i <= 3; i++) {
        const attrOnTime = `onTime${i}`
        const attrOffTime = `offTime${i}`
        tempRow[attrOnTime] = !tempRow[attrOnTime] ? null : moment(tempRow[attrOnTime], 'HH:mm:ss')
        tempRow[attrOffTime] = !tempRow[attrOffTime] ? null : moment(tempRow[attrOffTime], 'HH:mm:ss')
        if (tempRow[attrOnTime] && tempRow[attrOffTime]) {
          const time = Math.abs(moment(tempRow[attrOnTime], 'HH:mm').format('x') - moment(tempRow[attrOffTime], 'HH:mm').format('x'))
          this.totalTimes[i] = (moment.duration(time).hours() + moment.duration(time).minutes() / 60).toFixed(2) * 1
        }
        if (tempRow[attrOnTime] != null || tempRow[attrOffTime] != null) {
          this.timeQequireds[`time${i}`] = true
        }
      }
      this.id = tempRow.id
      this.employeeList = []
      this.ename = tempRow.ename
      this.enumber = tempRow.enumber
      this.employeeList.push({ enumber: this.enumber, ename: this.ename })
      tempRow.numberName = `${this.enumber}@${this.ename}`
      this.title = `编辑员工考勤[${tempRow.ename} (${tempRow.enumber})][${tempRow.workDate}]`
      this.visible = true
      this.offTimeDisabled = { offTime1: false, offTime2: false, offTime3: false }
      this.onTimeDisabled = { onTime1: false, onTime2: false, onTime3: false }
      this.$nextTick(() => {
        this.$initForm(this.form, tempRow, ['workDate'])
      })
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.enumber = this.enumber
          values.ename = this.ename
          if (this.id === -1) {
            this.$http
              .post('/customerAttendance/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                  this.totalTimes = { 1: 0, 2: 0, 3: 0 }
                  this.timeQequireds = { time1: true, time2: false, time3: false }
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              })
              .finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            // console.log(values)
            this.$http
              .post('/customerAttendance/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', false)
                  this.totalTimes = { 1: 0, 2: 0, 3: 0 }
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
    handleSearch (value) {
      this.$http.get('/project/getBaseEmployeeSelect', { params: { ename: value } }).then(res => {
        this.employeeList = res.data
      })
    },
    handleChange (value) {
      if (value && value.indexOf('@') > 0) {
        const arr = value.split('@')
        this.enumber = arr[0]
        this.ename = arr[1]
      } else {
        this.enumber = ''
        this.ename = ''
      }
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
