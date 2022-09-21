<template>
  <a-modal
    :title="title"
    :width="500"
    :visible="isVisible"
    :maskClosable="false"
    @cancel="resetData"
    @ok="handleSubmit">
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="项目阶段" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              placeholder="请选择项目阶段"
              v-decorator="['stageKey' , {rules:[{required: true, message: '请选择项目阶段'}]}]" >
              <a-select-option :value="item.key" v-for="item in stages" :key="item.key">{{ item.value }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="截止日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker style="width: 188px;" v-decorator="['deadline',{rules:[{required: true, message: '请选择项目阶段'}]} ]"></a-date-picker>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
export default {
  name: 'BatchSetSatageEndDateModal',
  data () {
    return {
      isVisible: false,
      selectRowIds: [],
      stages: [],
      form: this.$form.createForm(this),
      labelCol: { sm: { span: 8 } },
      wrapperCol: { sm: { span: 10 } },
      title: ''
    }
  },
  methods: {
    show (selectRowIds, stages) {
      this.form.resetFields()
      this.stages = stages
      this.selectRowIds = selectRowIds
      this.title = '批设置单个阶段的截止日期'
      this.isVisible = true
    },
    resetData () {
      this.$emit('ok', false)
      this.isVisible = false
      this.title = ''
      this.form.resetFields()
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          values['projectIds'] = this.selectRowIds
          this.$http.post('/productTechStage/setDeadlines', values).then((res) => {
            if (res.data && res.success) {
              this.$message.success('操作成功')
              this.isVisible = false
              this.$emit('ok', false)
            } else {
              this.$message.error(res.errorMessage)
            }
          }).catch((error) => {
            if (error.message.includes('403')) {
              this.$message.error('无此权限，请跟管理员联系！')
            } else {
              this.$message.error('操作失败：' + error.message)
            }
          })
        }
      })
    }
  }
}
</script>
<style lang='less' scoped>
</style>
