<template>
  <a-modal
    :width="500"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
  >
    <a-form
      @submit="handleSubmit"
      :form="form"
    >
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="项目类型"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input
                placeholder="请输入项目类型"
                v-decorator="['productName', {rules:[{required: true, message: '请输入项目类型'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="年份"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <year-select v-decorator="['year']"></year-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="所属地"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-cascader
                placeholder="请选择所属地"
                v-decorator="['addressCodeArr']"
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                :options="addressCode"
                changeOnSelect
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="类型"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-select v-decorator="['productType',{initialValue:'0'}]">
                <a-select-option value="0">投资项目</a-select-option>
                <a-select-option value="1">创新项目</a-select-option>
                <a-select-option value="2">高新项目</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="申报项目类型"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-select
                :options="declareTypes"
                placeholder="请选择申报项目类型"
                v-decorator="['declareType',{rules: [{ required: true, message: '请选择申报项目类型' }] }]"
              ></a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="是否备案"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-checkbox
                @change="trialChange"
                v-decorator="['shouldRegister',{valuePropName:'checked',initialValue:false}]"
              />
            </a-form-item>
          </a-col>
        </a-row>

      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import { YearSelect } from '@/components/Selects'
export default {
  components: {
    YearSelect
  },
  data () {
    return {
      addressCode: {},
      title: '',
      declareTypes: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      confirmLoading: false,
      visible: false,
      form: this.$form.createForm(this)
    }
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
    this.$getDictionary(10).then(res => {
      for (const type of res) {
        this.declareTypes.push({ value: type.key, label: type.value })
      }
    })
  },
  methods: {
    trialChange (chk) {
      this.shouldRegister = chk.target.checked
    },
    add () {
      this.form.resetFields()
      this.id = 0
      this.visible = true
      this.confirmLoading = false
    },
    edit (record) {
      this.form.resetFields()
      this.visible = true
      this.confirmLoading = false
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.productName = values.productName.trim()
          if (values.productName.length === 0) {
            this.$message.info('项目类型不能是空格')
            this.confirmLoading = false
            return
          }
          values.id = this.id
          // values.shouldRegister = this.shouldRegister
          // values.type = this.type
          if (values.id === 0) {
            this.$http.post('/product/addProduct', values)
              .then(res => {
                if (res.data === '') {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.warning(res.data)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/product/editProduct', values)
              .then(res => {
                if (res.data === '') {
                  this.visible = false
                  this.$emit('ok', false)
                } else {
                  this.$message.warning(res.data)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
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
</style>
