<template>
  <a-modal
    :title="title"
    style="top: 20px"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="研发工时:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入研发工时"
              v-decorator="['hours', { rules: [{ required: true, message: '请输入研发工时' }] }]"
              :min="-getMax()"
              :precision="hourBit"
              :max="getMax()"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
function toBit (v, bit) {
  var b = 2
  if (bit || bit > 0) {
    b = bit
  }
  var div = 1
  for (let i = 0; i < b; i++) {
    div = div * 10
  }

  // 保留双倍小数位，确保四舍五入不会丢失精度
  v = Number(v).toFixed(b * 2)
  return (Math.round(v * div) / div).toFixed(b)
}
const typeMap = {
  equipment: { url: '/projectRdEquipment/saveList' },
  employee: { url: '/projectRdEmployee/saveList' }
}
export default {
  components: {
  },
  props: {
    rType: {
      type: String, // equipment, employee
      required: true
    },
    hourBit: {
      type: Number,
      default: 1
    }
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
      params: {},
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      insuranceArr: [],
      salaryArr: []
    }
  },
  methods: {
    set (data, salaryArr, insuranceArr) {
      this.salaryArr = salaryArr
      this.insuranceArr = insuranceArr
      this.params = { ...data }
      this.title = '设置研发工时'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
    },
    buildRow (rdHour, item) {
      const e = { ...item }
      if (e.remainHour > 0) {
        rdHour = rdHour > Number(e.remainHour) ? Number(e.remainHour) : rdHour
      } else if (e.remainHour < 0) {
        rdHour = rdHour < Number(e.remainHour) ? Number(e.remainHour) : rdHour
      }
      e.rdHour = rdHour
      const rdRatio = rdHour / e.workHours
      if (this.rType === 'equipment') {
        e.rdDepreciation = toBit(rdRatio * e.depreciation)
      } else if (this.rType === 'employee') {
        var rdPay
        var rdInsuranceFund
        if (this.salaryArr && this.salaryArr.length && e.payDetail) {
          rdPay = this.getRdFee(this.salaryArr, JSON.parse(e.payDetail), rdRatio)
        } else {
          rdPay = toBit(rdRatio * e.pay)
        }
        if (this.insuranceArr && this.insuranceArr.length && e.insuranceDetail) {
          rdInsuranceFund = this.getRdFee(this.insuranceArr, JSON.parse(e.insuranceDetail), rdRatio)
        } else {
          rdInsuranceFund = toBit(rdRatio * e.insuranceFund)
        }
        e.rdPay = rdPay
        e.rdInsuranceFund = rdInsuranceFund
      }
      return e
    },
    getMax () {
      var month = this.params.month
      if (month) {
        month = month.format('YYYY-MM')
        return new Date(month.substr(0, 4), month.substr(5), 0).getDate() * 24
      }
      return 744
    },
    getRdFee (keyArr, fees, ratio) {
      var result = 0
      keyArr.forEach(item => {
        const f = Number(fees[item.name])
        if (f) {
          result += Number(toBit(f * ratio))
        }
      })
      return toBit(result)
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          var addArr = []
          this.params.list.forEach(item => {
            if (item.workHours && Number(item.workHours)) {
              addArr.push(this.buildRow(values.hours, item))
            }
          })
          if (addArr.length <= 0) {
            this.$message.info('没有可保存的数据！')
            this.confirmLoading = false
            return
          }
          const params = {}
          params.list = addArr
          params.projectId = this.params.projectId
          params.month = this.params.month
          this.$http.post(typeMap[this.rType].url, params)
            .then(res => {
              if (res.success) {
                this.$message.success('保存成功')
                this.$emit('ok')
                this.visible = false
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
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
