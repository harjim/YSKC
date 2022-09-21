<template>
  <a-modal
    :width="600"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading">
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form :form="form">
        <a-card :bordered="false">
          <a-row :gutter="24">
            <a-form-item label="高新技术产品" :labelCol="{span: 5}" :wrapperCol="{span: 19}">
              <a-select v-decorator="['highTechId', {rules:[{required: true, message: '请选择高新技术产品'}]}]">
                <a-select-option v-for="item in highTechList" :key="item.id" :value="item.id">{{ `${item.hcode}-${item.hname}` }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-row>
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      title: '',
      confirmLoading: false,
      ids: [],
      currentYear: undefined,
      highTechList: [],
      spinning: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    bind (ids, currentYear) {
      this.ids = ids
      this.form.resetFields()
      if (this.currentYear !== currentYear) {
        this.currentYear = currentYear
        this.title = `绑定[${currentYear}]年高新技术产品`
        this.loadHighTech()
      }
      this.visible = true
    },
    loadHighTech () {
      this.spinning = true
      this.$http.get('/highTech/getHighTechSelect', { params: { year: this.currentYear } }).then(res => {
        if (res.success && res.data) {
          this.highTechList = res.data
        } else {
          this.highTechList = []
          this.$message.error(res.errorMessage ? res.errorMessage : '获取高品下拉列表失败')
        }
        this.spinning = false
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (errors) {
          return
        }
        this.$http.post('/highTechIncome/bindHighTech', { ids: this.ids, highTechId: values.highTechId }).then(res => {
          if (res.success && res.data) {
            this.$message.success('绑定成功')
            this.$emit('ok')
            this.visible = false
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '绑定失败')
          }
          this.confirmLoading = false
        })
      })
    }
  }
}
</script>

<style>

</style>
