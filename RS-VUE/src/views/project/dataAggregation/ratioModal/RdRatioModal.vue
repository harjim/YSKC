<template>
  <a-modal
    title="设置研发比例"
    :visible="visible"
    @ok="handleOk"
    width="350px"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    @cancel="closeModal"
  >
    <a-form :form="form">
      <template v-if="rType === 'employee'">
        <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" label="研究人员">
          <a-input-number
            :min="0"
            :max="1"
            style="width: 100%"
            :precision="2"
            placeholder="请输入研发人员比例"
            v-decorator="['research', { rules: [{ required: false, message: '请输入研发人员比例' }] }]"
          />
        </a-form-item>
        <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" label="技术人员">
          <a-input-number
            :min="0"
            :max="1"
            style="width: 100%"
            :precision="2"
            placeholder="请输入技术人员比例"
            v-decorator="['technical', { rules: [{ required: false, message: '请输入技术人员比例' }] }]"
          />
        </a-form-item>
        <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" label="辅助人员">
          <a-input-number
            :min="0"
            :max="1"
            style="width: 100%"
            :precision="2"
            placeholder="请输入辅助人员比例"
            v-decorator="['auxiliary', { rules: [{ required: false, message: '请输入辅助人员比例' }] }]"
          />
        </a-form-item>
      </template>
      <template v-else-if="rType === 'equipment'">
        <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" label="设备">
          <a-input-number
            :min="0"
            :max="1"
            style="width: 100%"
            :precision="2"
            placeholder="请输入设备研发比例"
            v-decorator="['prod', { rules: [{ required: false, message: '请输入设备研发比例' }] }]"
          />
        </a-form-item>
        <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }" label="仪器">
          <a-input-number
            :min="0"
            :max="1"
            style="width: 100%"
            :precision="2"
            placeholder="请输入仪器研发比例"
            v-decorator="['lab', { rules: [{ required: false, message: '请输入仪器研发比例' }] }]"
          />
        </a-form-item>
      </template>
    </a-form>
  </a-modal>
</template>
<script>
function toBit (v, bit) {
  var b = 2
  if (bit || bit >= 0) {
    b = bit
  }
  var div = 1
  for (let i = 0; i < b; i++) {
    div = div * 10
  }
  // if (sub) {
  //   return (parseInt(v * div) / div).toFixed(b)
  // }
  // 保留双倍小数位，确保四舍五入不会丢失精度
  v = Number(v).toFixed(b * 2)
  return (Math.round(v * div) / div).toFixed(b)
}
const typeMap = {
  equipment: { 30100: 'lab', 30000: 'prod', url: '/projectRdEquipment/saveList' },
  employee: { 1: 'research', 2: 'technical', 3: 'auxiliary', url: '/projectRdEmployee/saveList' }
}
export default {
  props: {
    rType: {
      type: String, // equipment, employee
      required: true
    },
    typeAlias: {
      type: String,
      default: 'etype'
    },
    hourBit: {
      type: Number,
      default: 1
    }
  },
  data () {
    return {
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      params: {},
      salaryArr: [],
      insuranceArr: []
    }
  },
  methods: {
    showModal (data, salaryArr, insuranceArr) {
      this.salaryArr = salaryArr
      this.insuranceArr = insuranceArr
      this.params = { ...data }
      this.form.resetFields()
      this.confirmLoading = false
      this.visible = true
    },
    handleOk () {
      const currentType = typeMap[this.rType]
      if (!currentType) {
        this.closeModal()
        return
      }

      this.confirmLoading = true
      this.form.validateFields((error, values) => {
        if (!error) {
          for (const key in values) {
            if (!Number.isFinite(values[key])) {
              values[key] = undefined
            }
          }
          const newList = []
          this.params.list.forEach(r => {
            if (r[this.typeAlias]) {
              const temp = r[this.typeAlias]
              if (currentType[temp]) {
                const tempKey = currentType[temp]
                if (values[tempKey] !== undefined) {
                  const temp = this.buildRow(values[tempKey], r)
                  if (temp) {
                    newList.push(temp)
                  }
                }
              }
            }
          })
          if (newList.length === 0) {
            this.confirmLoading = false
            this.$message.info('没有可保存的数据！')
            this.closeModal()
            return
          }
          this.params.list = newList
          this.$http.post(currentType.url, this.params)
            .then(res => {
              if (res.success) {
                this.confirmLoading = false
                this.$emit('ok')
                this.closeModal()
                this.$message.success('设置研发比例成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '设置研发比例失败')
                this.confirmLoading = false
              }
            })
        }
      })
    },
    buildRow (ratio, r) {
      const record = { ...r }
      var rdRatio
      if ((ratio !== '-' && ratio >= 0) && record.workHours && Number(record.workHours)) {
        const hour = Number(toBit(ratio * record.workHours, this.hourBit))
        if ((hour > 0 && hour > record.remainHour) || (hour < 0 && hour < record.remainHour)) {
          rdRatio = record.remainHour / record.workHours
          record.rdHour = record.remainHour
        } else {
          rdRatio = ratio
          record.rdHour = hour
        }
        if (this.rType === 'equipment') {
          record.rdDepreciation = toBit(rdRatio * record.depreciation)
        } else if (this.rType === 'employee') {
          var rdPay
          var rdInsuranceFund
          if (this.salaryArr && this.salaryArr.length && record.payDetail) {
            rdPay = this.getRdFee(this.salaryArr, JSON.parse(record.payDetail), rdRatio)
          } else {
            rdPay = toBit(rdRatio * record.pay)
          }
          if (this.insuranceArr && this.insuranceArr.length && record.insuranceDetail) {
            rdInsuranceFund = this.getRdFee(this.insuranceArr, JSON.parse(record.insuranceDetail), rdRatio)
          } else {
            rdInsuranceFund = toBit(rdRatio * record.insuranceFund)
          }
          record.rdPay = rdPay
          record.rdInsuranceFund = rdInsuranceFund
        }
        return record
      } else {
        return undefined
      }
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

    closeModal () {
      this.visible = false
      this.params = {}
    }
  }
}
</script>

<style>
</style>
