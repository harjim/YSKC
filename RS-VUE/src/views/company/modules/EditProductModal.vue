<!-- 企业产品管理--编辑产品信息 -->
<template>
  <a-modal
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    :visible="visible"
    :width="800"
    destroyOnClose
    :title="`编辑【${product.pname}】`"
    @cancel="visible = false"
    @ok="handleSubmit"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form" @submit="handleSubmit">
        <a-card :bordered="false">
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品名称">
                <span v-if="$auth('company:product:finaEdit')">
                  <!-- 添加财务可修改 -->
                  <a-input v-decorator="['pname', {rules:[{required: true, message: '请输入产品名称'}], initialValue: product.pname}]" type="text"></a-input>
                </span>
                <span v-else>{{ product.pname }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品编码">
                <span v-if="$auth('company:product:finaEdit')">
                  <a-input v-decorator="['pcode', {rules:[{required: true, message: '请输入产品编码'}, { validator: pcodeExist}], initialValue: product.pcode, validateTrigger: ['blur', 'submit'] }]" type="text"></a-input>
                </span>
                <span v-else>{{ product.pcode }}</span>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品创建时间">
                <span v-if="$auth('company:product:finaEdit')">
                  <a-date-picker
                    v-decorator="['creationDate', {rules:[{required: true, message: '请输入产品创建时间'}], initialValue: moment(product.creationDate)}]"
                    placeholder="请输入产品创建时间"
                    :disabled-date="(current) => current > moment().endOf('day')"
                  />
                </span>
                <span v-else>{{ product.creationDate }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品型号">
                <span v-if="$auth('company:product:finaEdit')">
                  <a-input v-decorator="['model', {rules:[{required: true, message: '请输入产品型号'}], initialValue: product.model}]" type="text"></a-input>
                </span>
                <span v-else>{{ product.model }}</span>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="单位">
                <span v-if="$auth('company:product:finaEdit')">
                  <a-input v-decorator="['unit', {rules:[{required: true, message: '请输入单位'}], initialValue: product.unit}]" type="text"></a-input>
                </span>
                <span v-else>{{ product.unit }}</span>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="24">
              <a-form-item
                :labelCol="labelCol1"
                :wrapperCol="wrapperCol1"
                label="技术性能参数">
                <span v-if="$auth('company:product:edit')">
                  <a-textarea
                    v-decorator="['parameter', {rules:[{required: true, message: '请输入技术性能参数'}], initialValue: product.parameter}]"
                    :auto-size="{ minRows: 4 }"
                    placeholder="请输入技术性能参数"
                  />
                </span>
                <span v-else>{{ product.parameter }}</span>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="24">
              <a-form-item
                :labelCol="labelCol1"
                :wrapperCol="wrapperCol1"
                label="特性及用途">
                <span v-if="$auth('company:product:edit')">
                  <a-textarea
                    v-decorator="['features', {rules:[{required: true, message: '请输入特性及用途'}], initialValue: product.features}]"
                    :auto-size="{ minRows: 4 }"
                    placeholder="请输入特性及用途"
                  />
                </span>
                <span v-else>{{ product.features }}</span>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="24">
              <a-form-item
                :labelCol="labelCol1"
                :wrapperCol="wrapperCol1"
                label="主要材料">
                <span v-if="$auth('company:product:edit')">
                  <a-textarea
                    v-decorator="['mainRaw', {rules:[{required: true, message: '请输入主要材料'}], initialValue: product.mainRaw}]"
                    :auto-size="{ minRows: 4 }"
                    placeholder="请输入主要材料"
                  />
                </span>
                <span v-else>{{ product.mainRaw }}</span>

              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="24">
              <a-form-item
                :labelCol="labelCol1"
                :wrapperCol="wrapperCol1"
                label="同行对比优势劣势">
                <span v-if="$auth('company:product:edit')">
                  <a-textarea
                    v-decorator="['comparison', { rules:[{required: true, message: '请输入同行对比优势劣势'}], initialValue: product.comparison }]"
                    :auto-size="{ minRows: 4 }"
                    placeholder="请输入同行对比优势劣势"
                  />
                </span>
                <span v-else>{{ product.comparison }}</span>

              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 23 },
        sm: { span: 10 }
      },
      labelCol1: {
        xs: { span: 5 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 2 },
        sm: { span: 13 }
      },
      wrapperCol1: {
        xs: { span: 21 },
        sm: { span: 18 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      product: {}
    }
  },
  methods: {
    moment,
    showModal (product) {
      this.product = product
      this.visible = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values = Object.assign(this.product, values)
          this.$http.post('/product/edit', values)
            .then(res => {
              if (res.success && res.data) {
                this.confirmLoading = false
                this.visible = false
                this.$emit('tableRefresh', true)
              } else {
                this.$message.error(res.errorMessage)
              }
            }).finally(() => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    pcodeExist (rule, value, callback) {
      if (value) {
        this.$http.get('/product/checkPcode', { params: { pcode: value, productId: this.product.id } }).then(res => {
          if (res.success) {
            if (res.data) {
              callback()
            } else {
              callback(new Error('产品编码已存在,请重新输入'))
            }
          } else {
            callback(new Error(res.errorMessage))
          }
        })
      } else {
        callback()
      }
    }
  }
}
</script>

<style lang="less" scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}

/deep/ .ant-col-sm-6 {
  width: 32%;
}
</style>
