<template>
  <a-modal
    :title="title"
    style="top:20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="48">
        <a-col :span="48">
          <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              :disabled="this.id > 0"
              v-decorator="['contractNumber', {rules:[{required: true, message: '请输入合同编号'}]}]"
              @blur="checkContractNo"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :span="48">
          <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <!-- <search-select
              ref="customerSearch"
              v-decorator="['customerId', {rules:[{required: true, message: '请输入客户名称'}]}]"
              placeholder="请输入客户名称"
              url="/customer/getCustomerSelect"
              searchField="customerName"
              :selected="selectCustomer"
              @change="handleChange"
            />-->
            <a-input
              placeholder="请输入客户名称"
              v-decorator="['customerName', {rules:[{required: true, message: '请输入客户名称'}]}]"
            ></a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :span="48">
          <a-form-item label="封面" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-upload
              v-decorator="['thecover', {rules:[{required: false, message: '请上传封面'}]}]"
              name="avatar"
              listType="picture-card"
              class="avatar-uploader"
              :showUploadList="false"
              :action="action"
              :beforeUpload="beforeUpload"
              @change="uploadThecover"
              :headers="headers"
            >
              <img v-if="thecoverUrl" :src="thecoverUrl" alt="avatar" :width="130" :height="60" />
              <div v-else>
                <a-icon :type="loading ? 'loading' : 'plus'" />
                <div class="ant-upload-text">Upload</div>
              </div>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row v-if="this.id > 0">
        <a-col :span="48">
          <a-form-item label="二维码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <img :src="currentQrPath" :width="120" :height="120" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { getBaseUrl } from '@/utils/request'
import { ACCESS_TOKEN } from '@/store/mutation-types'
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}
export default {
  name: 'ContractTraceability',
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      title: '',
      visible: false,
      confirmLoading: false,
      id: 0,
      form: this.$form.createForm(this),
      thecoverUrl: '',
      thecover: '',
      currentQrPath: '',
      loading: false,
      headers: { 'Access-Token': '' },
      action: getBaseUrl() + '/contractTraceability/uploadThecoverUrl',
      checked: true
      // selectCustomer: {}
    }
  },
  created () {
    const token = this.$ls.get(ACCESS_TOKEN)
    this.headers['Access-Token'] = token
  },
  methods: {
    add () {
      // this.selectCustomer = {}
      this.checked = true
      this.currentQrPath = ''
      this.title = '新建合同追溯'
      this.confirmLoading = false
      this.thecoverUrl = null
      this.form.resetFields()
      this.visible = true
      this.id = -1
    },
    edit (record) {
      this.checked = true
      this.currentQrPath = '/images' + record.qrcode
      this.title = '修改合同追溯'
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record)
        // this.selectCustomer = { id: record.customerId, title: record.customerName }
        if (record.thecover) {
          this.thecoverUrl = '/images' + record.thecover
        }
        this.form.setFieldsValue({ thecover: record.thecover })
      })
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/jpeg' || 'image/png'
      if (!isJPG) {
        this.$message.error('请上传jpg或png格式的图片!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    uploadThecover (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
      }
      if (info.file.status === 'done') {
        this.thecover = info.file.response.data
        const { form: { setFieldsValue } } = this
        setFieldsValue({ thecover: this.thecover })
        getBase64(info.file.originFileObj, (imageUrl) => {
          this.thecoverUrl = imageUrl
          this.loading = false
        })
      }
    },
    handleSubmit () {
      if (!this.checked) {
        return
      }
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          if (this.thecover) {
            values.thecover = this.thecover
          }
          if (this.id === -1) {
            this.$http.post('/contractTraceability/addConTraceability', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage)
                }
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/contractTraceability/updateConTraceability', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage)
                }
                this.confirmLoading = false
              })
          }
        }
      })
    },
    // handleChange (v) {
    //   this.form.setFieldsValue({ customerId: v })
    // },
    checkContractNo () {
      const contractNo = this.form.getFieldValue('contractNumber')
      if (contractNo && contractNo.trim() === '') {
        return
      }
      this.$http.get('/contractTraceability/checkContractNo', { params: { contractNo: contractNo } })
        .then(res => {
          if (!res.data) {
            this.checked = false
            this.form.setFields({ 'contractNumber': { value: contractNo, errors: [new Error('合同编号已存在，请输入新合同编号。')] } })
          } else {
            this.checked = true
            this.form.setFields({ 'contractNumber': { value: contractNo } })
          }
        })
    }
  }
}
</script>

<style scoped>
</style>
