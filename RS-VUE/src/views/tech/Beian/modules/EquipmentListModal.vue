<template>
  <a-modal
    :title="title"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    @cancel="handleCancel"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入设备名称"
                v-decorator="['ename', { rules: [{ required: true, message: '请输入设备名称' }] }]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="规格型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入规格型号"
                v-decorator="['emodal', { rules: [{ required: true, message: '请输入规格型号' }] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0.01"
                :precision="2"
                :max="$store.state.totalMax"
                placeholder="请输入数量"
                style="width:100%"
                v-decorator="['quantity', { rules: [{ required: true, message: '请输入数量' }] }]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="单位">
              <a-input
                placeholder="请输入单位"
                v-decorator="['unit', { rules: [{ required: true, message: '请输单位' }] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="单价(元)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                placeholder="请输入单价"
                style="width:100%"
                v-decorator="['unitPrice', { rules: [{ required: true, message: '请输入单价' }] }]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="金额(元)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                placeholder="请输入金额"
                style="width:100%"
                v-decorator="['amount', { rules: [{ required: true, message: '请输入金额' }] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="额定功耗(kW)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['usagePower', { rules: [{ required: true, message: '请输入额定功耗' }] }]"
                :precision="2"
                :min="0"
                :max="$store.state.totalMax"
                style="width:100%"
                placeholder="请输入额定功耗"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="负荷系数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['loadFactor', { rules: [{ required: true, message: '请输入负荷系数' }] }]"
                :precision="4"
                :min="0"
                :max="9.9998"
                style="width:100%"
                placeholder="请输入负荷系数"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="稼动率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['runRate', { rules: [{ required: true, message: '请输入稼动率' }] }]"
                :precision="4"
                :min="0"
                :max="9.9998"
                style="width:100%"
                placeholder="请输入稼动率"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="运转时间(h)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['workHour', { rules: [{ required: true, message: '请输入运转时间' }] }]"
                :precision="2"
                :min="0"
                :max="$store.state.totalMax"
                style="width:100%"
                placeholder="请输入运转时间"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="用电(kW·h)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ powerUsed }}
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
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      form: this.$form.createForm(this, { onValuesChange: (props, values) => {
        if (values.usagePower || values.loadFactor || values.runRate || values.workHour) {
          this.$nextTick(() => {
            const { usagePower = 0, loadFactor = 0, runRate = 0, workHour = 0 } = this.form.getFieldsValue(['usagePower', 'loadFactor', 'runRate', 'workHour'])
            this.powerUsed = (usagePower * loadFactor * runRate * workHour).toFixed(2)
          })
        }
      } }),
      title: '',
      visible: false,
      confirmLoading: false,
      id: undefined,
      beianId: undefined,
      powerUsed: 0
    }
  },
  methods: {
    add (beianId) {
      this.title = '添加'
      this.confirmLoading = false
      this.beianId = beianId
      this.id = undefined
      this.visible = true
      this.form.resetFields()
    },
    edit (row) {
      this.title = `编辑[${row.ename}]`
      this.confirmLoading = false
      this.id = row.id
      this.powerUsed = row.powerUsed
      this.visible = true
      this.$nextTick(() => {
        this.$initForm(this.form, row)
      })
    },
    handleCancel () {
      Object.assign(this.$data, this.$options.data.call(this))
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.visible = true
      validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          if (this.id) {
            values.id = this.id
            this.$http
              .post('/techEquipment/edit', values)
              .then(res => {
                if (res.success && res.data) {
                  this.$message.success('更新成功')
                  this.$emit('ok', false)
                  this.handleCancel()
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
                }
              })
              .finally(() => {
                this.confirmLoading = false
              })
          } else {
            values.beianId = this.beianId
            this.$http
              .post('/techEquipment/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.$message.success('添加成功')
                  this.$emit('ok', true)
                  this.handleCancel()
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              })
              .finally(() => {
                this.confirmLoading = false
              })
          }
        }
      })
    }
  }
}
</script>
