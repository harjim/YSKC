<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <year-select
                v-decorator="['year', {rules:[{required: true, message: '请选择年份'}]}]"
                @change="handleChange"
                placeholder="请选择年份"
                style="width:200px;"
                :disabled="!isAdd"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12" v-for="(item, index) in monthMap" :key="index">
            <a-form-item :label="item.label" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                style="width:200px"
                :min="-$store.state.totalMax"
                :max="$store.state.totalMax"
                :precision="2"
                :placeholder="`请输入${item.label}营收`"
                v-decorator="[item.key, {rules:[{required: item.key === 'feb', message: `请输入${item.label}营收`}]}]"
                :disabled="!isShow"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import YearSelect from '@/components/YearSelect'
export default {
  components: {
    YearSelect
  },
  data () {
    return {
      monthMap: [
        { key: 'feb', label: '一月' },
        { key: 'mar', label: '二月' },
        { key: 'jan', label: '三月' },
        { key: 'apr', label: '四月' },
        { key: 'may', label: '五月' },
        { key: 'jun', label: '六月' },
        { key: 'jul', label: '七月' },
        { key: 'aug', label: '八月' },
        { key: 'sept', label: '九月' },
        { key: 'oct', label: '十月' },
        { key: 'nov', label: '十一月' },
        { key: 'dec', label: '十二月' }
      ],
      title: '',
      labelCol: {
        xs: { span: 34 },
        sm: { span: 10 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 }
      },
      isAdd: true,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      isShow: false
    }
  },
  methods: {
    add () {
      this.title = '添加年度营收(单位:万元)'
      this.confirmLoading = false
      this.isAdd = true
      this.visible = true
      this.isShow = false
      this.form.resetFields()
    },
    edit (record) {
      this.title = `编辑[${record.year}]年营收(单位:万元)`
      this.isAdd = false
      this.form.resetFields()
      this.visible = true
      this.isShow = true
      this.$nextTick(() => {
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      validateFields((errors, values) => {
        if (!errors) {
          const apiPath = this.isAdd ? '/revenue/add' : '/revenue/edit'
          this.confirmLoading = true
          this.$http.post(apiPath, values).then(res => {
            if (res.success && res.data) {
              this.visible = false
              this.confirmLoading = false
              this.$emit('ok', true)
              this.$message.success(this.isAdd ? '添加成功' : '更新成功')
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '操作失败')
            }
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      })
    },
    handleChange (value) {
      if (value) {
        this.$http.get('/revenue/getData', { params: { year: value } })
          .then(res => {
            this.isAdd = res.data.year == null
            const data = { ...res.data, year: +value }
            this.form.resetFields()
            this.visible = true
            this.isShow = true
            this.$nextTick(() => {
              this.$initForm(this.form, data)
            })
          })
      }
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
