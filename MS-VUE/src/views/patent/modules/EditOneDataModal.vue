<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="48">
        <a-col :span="48">
          <a-form-item label="提醒日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              v-decorator="['remindDateTime',{rules:[{required: true, message: '请选择提醒日期'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
  components: {
  },
  data () {
    return {
      title: '',
      value: 0,
      rsEntityList: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      visible: false,
      isCreat: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    moment,
    edit (record) {
      this.isCreat = false
      this.title = `设置提醒日期`
      this.form.resetFields()
      this.visible = true
      this.rsEntityList = record
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          // values.remindDateTime = this.updateDate
          values.rsEntityList = this.rsEntityList
          this.$http.post('/rsPatentCost/updateRemindDateTime', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', true)
              } else {
                this.$message.info(res.errorMessage)
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    onChange (e) {
      this.updateDate = this.addDate(this.payDate, -15)
      if (e.target.value === 1) {
        this.updateDate = this.addDate(this.payDate, -15)
      }
      if (e.target.value === 2) {
        const newDate = this.addDate(this.payDate, -30)
        this.updateDate = this.addDate(newDate, -16)
        // this.getBetweenDate(16, 31)
      }
      if (e.target.value === 3) {
        const date = this.addDate(this.payDate, -60)
        this.updateDate = this.addDate(date, -31)
      }
    },
    addDate (date, days) {
      var d = new Date(date)
      d.setDate(d.getDate() + days)
      var m = d.getMonth() + 1
      return d.getFullYear() + '-' + m + '-' + d.getDate()
    }
    // getBetweenDate (startDay, endDay) {
    //   var dat = this.payDate
    //   console.log(dat)
    //   var d = new Date()
    //   var startDate = this.getDate(d, startDay)
    //   startDate.toLocaleDateString()
    //   console.log(startDate.toLocaleDateString())
    //   var endDate = this.getDate(startDate, endDay - startDay)
    //   endDate.toLocaleDateString()
    // },
    // getDate (d, days) {
    //   var t = d.getTime()
    //   var d2 = new Date()
    //   d2.setTime(t - 86400000 * days)
    //   return d2
    // }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
