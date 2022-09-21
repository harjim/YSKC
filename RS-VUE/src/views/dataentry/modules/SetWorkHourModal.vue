<template>
  <div>
    <a-modal
      title="设置运行工时"
      :visible="visible"
      @ok="handleOk"
      width="350px"
      :confirmLoading="confirmLoading"
      :maskClosable="false"
      @cancel="closeModal"
    >
      <a-form :form="form">
        <a-form-item :label-col="{ span: 8 }" :wrapper-col="{ span: 14 }" label="运行工时">
          <a-input-number
            :min="0"
            :max="744"
            :precision="2"
            v-decorator="['workHours',{rules: [{ required: true, message: '请输入运行工时' }]}]"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
export default {
  name: 'SetWorkHourModal',
  data () {
    return {
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      rows: []
    }
  },
  methods: {
    showModal (rows) {
      this.form.resetFields()
      this.rows = rows
      this.visible = true
    },
    handleOk () {
      this.confirmLoading = true
      this.form.validateFields((error, values) => {
        if (!error) {
          var params = []
          this.rows.forEach(row => {
            var node = {}
            node.id = row.id
            if (values.workHours > 0) {
              const maxHour = (new Date(row.month.substr(0, 4), row.month.substr(5), 0)).getDate() * 24
              node.workHours = maxHour > values.workHours ? values.workHours : maxHour
            } else {
              const minHour = -(new Date(row.month.substr(0, 4), row.month.substr(5), 0)).getDate() * 24
              node.workHours = minHour > values.workHours ? minHour : values.workHours
            }
            params.push(node)
          })
          this.$http.post('/dEquipment/setWorkHour', params)
            .then(res => {
              if (res.success && res.data) {
                this.$message.success('设置运行工时成功')
                this.$emit('ok')
                this.closeModal()
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
                this.confirmLoading = false
              }
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    closeModal () {
      this.confirmLoading = false
      this.visible = false
      this.selectRows = []
    }
  }
}
</script>

<style>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
