<template>
  <a-modal
    title="设置加入日期"
    :width="500"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="加入日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              :disabledDate="disabledDate"
              format="YYYY-MM-DD"
              v-decorator="['entryDate',{rules:[{required: true, message: '请选择加入日期'}]}]"
              placeholder="请选择加入日期"
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
  name: 'SetEntryDateModal',
  props: {
    url: {
      type: String,
      required: true
    },
    checkRdUsed: {
      type: Function,
      default: () => { }
    },
    year: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      begin: undefined,
      end: undefined,
      params: {}
    }
  },
  methods: {
    moment,
    add (ids, begin, end, projectId, currentYear) {
      let beginDate = moment(begin)
      let endDate = moment(end) // .add(1, 'days')
      if (beginDate.year() < currentYear) {
        beginDate = moment(currentYear + '-01-01') // 1月份
      }
      if (endDate.year() > currentYear) {
        endDate = moment(currentYear + '-12-31') // 12月份
      }
      this.begin = beginDate
      this.end = endDate.add(1, 'days')
      // this.begin = moment(begin)

      // this.end = moment(end).add(1, 'days')
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.params = { ids: ids, projectId: projectId, year: this.year }
    },
    handleSubmit () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          Object.assign(values, this.params)
          this.checkRdUsed(values).then(res => {
            if (res) {
              this.$http.post(this.url, values)
                .then(res => {
                  if (res.success && res.data) {
                    this.visible = false
                    this.$message.success('设置成功')
                    this.$emit('ok', true)
                  } else {
                    this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
                  }
                }).finally(res => {
                  this.confirmLoading = false
                })
            } else {
              this.confirmLoading = false
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    disabledDate (current) {
      return (!current || current > this.end) || current < this.begin
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
