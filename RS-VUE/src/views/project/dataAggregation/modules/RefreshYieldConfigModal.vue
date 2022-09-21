<!--
 * @Author: zdf
 * @Date: 2021-10-18 09:53:37
 * @LastEditTime: 2022-09-16 14:00:26
 * @LastEditors: zdf
 * @Description: 刷新试制计划配置
 * @FilePath: \RS-VUE\src\views\project\dataAggregation\modules\RefreshYieldConfigModal.vue
-->
<template>
  <a-modal
    :title="`刷新${rdTitle}[${month}]研发试制计划`"
    :visible="visible"
    :width="600"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    @cancel="closeModal">
    <a-alert style="margin:4px 0;" message="请注意：刷新研发试制计划会强制清除上一次生成的研发试制计划；手动添加/导入的研发试制计划不受影响。若所选设备工时记录为空，则只清除上一次生成的研发试制计划。" type="warning" banner/>
    <a-form :form="form">

      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="开始试制时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-time-picker
              style="width: 100%"
              format="HH:mm"
              v-decorator="['trialTime',{ rules: [{ required: true, message: '请选择开始试制时间' }] }]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="休息起止时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
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
  props: {
    projectId: {
      type: Number,
      required: true
    },
    month: {
      type: String,
      default: ''
    },
    rdTitle: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      curRecord: undefined
    }
  },
  methods: {
    show (curRecord) {
      this.curRecord = curRecord
      this.visible = true
      this.getTrialConfig()
    },
    getTrialConfig () {
      this.confirmLoading = true
      this.$http.get('/projectYieldConfig/getTrialConfig').then(res => {
        const date = moment().format('YYYY-MM-DD')
        const config = { trialTime: date + ' ' + '08:00',
          startTime: date + ' ' + '12:00',
          endTime: date + ' ' + '13:30' }
        if (res.success) {
          const data = res.data
          if (data) {
            config.trialTime = date + ' ' + data.trialTime
            config.startTime = data.startTime ? date + ' ' + data.startTime : undefined
            config.endTime = data.endTime ? date + ' ' + data.endTime : undefined
          }
        } else {
          this.$message.error(res.errorMessage || '加载研发试制计划生成配置失败')
        }
        this.$initForm(this.form, config, ['trialTime', 'startTime', 'endTime'])
      }).finally(res => {
        this.confirmLoading = false
      })
    },
    closeModal () {
      this.visible = false
      this.curRecord = undefined
    },
    handleSubmit () {
      const self = this
      this.$confirm({
        content: '请注意：刷新研发试制计划会强制清除上一次生成的研发试制计划；手动添加/导入的研发试制计划不受影响。若所选设备工时记录为空，则只清除上一次生成的研发试制计划。',
        onOk () {
          self.executorSubmit()
        },
        onCancel () {
        }
      })
    },
    executorSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        const diff = values.startTime && values.endTime ? values.endTime.diff(values.startTime, 'm') : 0
        console.log(diff)
        const lastTime = moment().endOf('d')
        for (let i = 0; i < this.curRecord.equDataArr.length; i++) {
          const current = this.curRecord.equDataArr[i]
          if (current * 60 + diff > (lastTime.diff(values.trialTime, 'm')) + 1) {
            this.$message.warning(`试制起止时间【${values.trialTime.format('HH:mm')}~${lastTime.format('HH:mm')}】与研发工时【${current}】不匹配，请调整试制开始时间！`)
            this.confirmLoading = false
            return
          }
        }
        if (values.startTime) {
          values.startTime = values.startTime.format('HH:mm')
        }
        if (values.endTime) {
          values.endTime = values.endTime.format('HH:mm')
        }
        values.trialTime = values.trialTime.format('HH:mm')
        values.projectId = this.projectId
        values.month = this.month + '-01 00:00:00'
        values.equDataArr = this.curRecord.equDataArr
        values.ecode = this.curRecord.ecode
        this.$http.post('/projectYieldConfig/refreshYieldConfig', values).then(res => {
          if (res.success && res.data) {
            this.$message.success('刷新成功')
            this.closeModal()
          } else {
            this.$message.error(res.errorMessage || '刷新试制计划失败')
          }
          this.confirmLoading = false
        })
      })
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
