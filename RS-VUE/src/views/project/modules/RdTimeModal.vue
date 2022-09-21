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
            <a-form-item label="上班时间1" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                @change="(v) => timeChange(v, 1,'on')"
                :disabled="!row.onTime1"
                :disabledHours="() => disabledHours(1,'on')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 1,'on')"
                style="width:100%"
                v-decorator="['onTime1', {rules:[{required: true, message: '请输入上班时间1'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="下班时间1" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                :disabled="!row.offTime1"
                @change="(v) => timeChange(v, 1,'off')"
                :disabledHours="() => disabledHours(1,'off')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 1,'off')"
                style="width:100%"
                v-decorator="['offTime1', {rules:[{required: false, message: '请输入下班时间1'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="上班时间2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                @change="(v) => timeChange(v, 2,'on')"
                :disabledHours="() => disabledHours(2,'on')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 2,'on')"
                :disabled="!row.onTime2"
                style="width:100%"
                v-decorator="['onTime2', {rules:[{required: false, message: '请输入上班时间2'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="下班时间2" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                @change="(v) => timeChange(v, 2,'off')"
                :disabledHours="() => disabledHours(2,'off')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 2,'off')"
                :disabled="!row.offTime2"
                style="width:100%"
                v-decorator="['offTime2', {rules:[{required: false, message: '请输入下班时间2'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="上班时间3" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                @change="(v) => timeChange(v, 3,'on')"
                :disabledHours="() => disabledHours(3,'on')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 3,'on')"
                :disabled="!row.onTime3"
                style="width:100%"
                v-decorator="['onTime3', {rules:[{required: false, message: '请输入上班时间3'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="下班时间3" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                @change="(v) => timeChange(v, 3,'off')"
                :disabledHours="() => disabledHours(3,'off')"
                :disabledMinutes="(selectedHour) => disabledMinutes(selectedHour, 3,'off')"
                :disabled="!row.offTime3"
                v-decorator="['offTime3', {rules:[{required: false, message: '请输入下班时间3'}]}]"/>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
  data () {
    return {
      visible: false,
      confirmLoading: false,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      form: this.$form.createForm(this),
      row: {}
    }
  },
  methods: {
    moment,
    edit (row) {
      this.row = row
      this.title = `编辑[${row.rdTitle}-${row.ename}-${row.workDate}]工时`
      this.visible = true
      this.$nextTick(() => {
        const formData = {
          onTime1: row.onTime1 ? moment(row.onTime1, 'HH:mm') : undefined,
          onTime2: row.onTime2 ? moment(row.onTime2, 'HH:mm') : undefined,
          onTime3: row.onTime3 ? moment(row.onTime3, 'HH:mm') : undefined,
          offTime1: row.offTime1 ? moment(row.offTime1, 'HH:mm') : undefined,
          offTime2: row.offTime2 ? moment(row.offTime2, 'HH:mm') : undefined,
          offTime3: row.offTime3 ? moment(row.offTime3, 'HH:mm') : undefined
        }
        this.$initForm(this.form, formData)
      })
      this.form.resetFields()
    },
    disabledHours (index, mode) {
      const offKey = `offTime${index}`
      const onKey = `onTime${index}`
      let onTime = this.row[onKey]
      let offTime = this.row[offKey]
      if (mode !== 'on') {
        const filedTime = this.form.getFieldValue(onKey)
        if (filedTime) {
          onTime = filedTime.format('HH:mm')
        }
      }
      if (mode !== 'off') {
        const filedTime = this.form.getFieldValue(offKey)
        if (filedTime) {
          offTime = filedTime.format('HH:mm')
        }
      }
      if (onTime && offTime) {
        const begin = Number(onTime.split(':')[0])
        const end = Number(offTime.split(':')[0])
        const hours = []
        for (let i = 0; i < begin; i++) {
          hours.push(i)
        }
        for (let i = end + 1; i <= 23; i++) {
          hours.push(i)
        }
        return hours
      }
    },
    disabledMinutes (hour, index, mode) {
      const offKey = `offTime${index}`
      const onKey = `onTime${index}`
      let onTime = this.row[onKey]
      let offTime = this.row[offKey]
      if (mode !== 'on') {
        const filedTime = this.form.getFieldValue(onKey)
        if (filedTime) {
          onTime = filedTime.format('HH:mm')
        }
      }
      if (mode !== 'off') {
        const filedTime = this.form.getFieldValue(offKey)
        if (filedTime) {
          offTime = filedTime.format('HH:mm')
        }
      }
      if (onTime && offTime) {
        const onTimeArr = onTime.split(':')
        const offTimeArr = offTime.split(':')
        const begin = Number(onTimeArr[0])
        const end = Number(offTimeArr[0])
        const minutes = []
        hour = Number(hour)
        if (hour === begin) {
          for (let i = 0; i < Number(onTimeArr[1]); i++) {
            minutes.push(i)
          }
        }
        if (hour === end) {
          for (let i = Number(offTimeArr[1]) + 1; i < 60; i++) {
            minutes.push(i)
          }
        }
        return minutes
      }
    },
    timeChange (v, index, mode) {
      if (!v) {
        return
      }
      let key
      if (mode === 'on') {
        key = 'onTime' + index
      } else {
        key = 'offTime' + index
      }
      const onKey = 'onTime' + index
      const offKey = 'offTime' + index
      let minTime
      let maxTime
      if (this.row[onKey]) {
        minTime = moment(this.row[onKey], 'HH:mm:ss')
      }
      if (this.row[offKey]) {
        maxTime = moment(this.row[offKey], 'HH:mm:ss')
      }
      if (!minTime) {
        minTime = maxTime
      }
      if (!maxTime) {
        maxTime = minTime
      }

      if (v < minTime) {
        v = minTime
      }
      if (v > maxTime) {
        v = maxTime
      }
      const obj = {}
      obj[key] = v
      this.$nextTick(() => {
        this.$initForm(this.form, obj)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      validateFields((errors, values) => {
        if (errors) {
          return
        }
        this.confirmLoading = true
        var prevNotNull = true
        for (const key in values) {
          if (!prevNotNull && values[key]) {
            this.$message.error('打卡时间段之间不能出现空缺。')
            this.confirmLoading = false
            return
          }
          if (!values[key]) {
            prevNotNull = false
          }
        }
        values.id = this.row.id
        this.$http.post('/projectAttendance/editAttendance', values).then(res => {
          if (res.success && res.data) {
            this.$message.success('更新成功')
            this.visible = false
            this.$emit('ok')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    }
  }
}
</script>
