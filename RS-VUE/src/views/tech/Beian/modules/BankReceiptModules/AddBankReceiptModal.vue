<!--
 * @Author: ldx
 * @Date: 2021-03-19 14:14:54
 * @LastEditTime: 2021-10-15 09:17:39
 * @LastEditors: lzh
 * @Description: 添加合同信息详细
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\BankReceiptModules\AddBankReceiptModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="900"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    style="top:0px;"
    @cancel="isVisible = false"
    @ok="handleSubmit"
  >
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入序号"
                style="width: 100%;"
                v-decorator="['seq', { rules: [{ required: true, message: '请输入序号' }] }]"
                :min="-$store.state.maxOrder"
                :max="$store.state.maxOrder"
              ></a-input-number>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="付款记账凭证字号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="付款记账凭证字号"
                v-decorator="['voucherNo', { rules: [{ required: true, message: '请输入付款记账凭证字号', whitespace: true }] }]"
              ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="记账日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width:100%;"
                v-decorator="['voucherDate', { rules: [{ required: true, message: '请选择记账日期' }] }]"
              ></a-date-picker>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="金额（含税）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0.1"
                :precision="2"
                style="width:100%;"
                placeholder="金额（含税）"
                v-decorator="['amountTax', { rules: [{ required: true, message: '请输入金额（含税）' }] }]"
              ></a-input-number>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="税率" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0.1"
                :max="1"
                :precision="2"
                style="width:100%;"
                placeholder="税率"
                v-decorator="['taxRate', { rules: [{ required: true, message: '请输入税率' }] }]"
              ></a-input-number>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="金额（不含税）" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ amount }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="收款单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <search-select
                url="/inverstment/getCompanyName"
                searchField="companyName"
                placeholder="请输入收款单位"
                v-decorator="[
                  'payee',
                  { rules: [{ required: true, message: '请输入收款单位' }], validateTrigger: ['change', 'blur'] }
                ]"
              />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="付款日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker style="width:100%;" v-decorator="['payDate', { rules: [{ required: true, message: '请选择付款日期' }] }]"></a-date-picker>
            </a-form-item>
          </a-col>
        </a-row>
        <!-- TODO: 字段更新 -->
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="付款阶段" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="请选择付款阶段" :options="getPayStageArr" v-decorator="['payStage', { rules: [{ required: true, message: '请选择付款阶段' }] }]"></a-select>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="付款比例" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入付款比例"
                style="width: 100%;"
                v-decorator="['payRate', { rules: [{ required: true, message: '请输入付款比例' }] }]"
                :min="0"
                :max="1"
                :precision="2"
              ></a-input-number>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="付款方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="请选择付款方式" :options="getPayTypeArr" v-decorator="['payType', { rules: [{ required: true, message: '请选择付款方式' }] }]"></a-select>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="承兑汇票到期时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker style="width:100%;" :disabled="form.getFieldValue('payType') !== '1'" v-decorator="['acceptDate', { rules: [{ required: form.getFieldValue('payType') === '1', message: '请选择承兑汇票到期时间' }] }]"></a-date-picker>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="付款凭证附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-upload
                :fileList="files['voucherPath']"
                :multiple="false"
                @preview="preview"
                @download="download('voucherPath')"
                @change="file => handleChange(file, 'voucherPath')"
                :beforeUpload="file => beforeUpload(file, 'voucherPath')"
                :showUploadList="{ showPreviewIcon: true, showRemoveIcon: true, showDownloadIcon: true }"
                v-decorator="[
                  'voucherPathUpload',
                  { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                ]"
              >
                <a-button> <a-icon type="upload" />上传</a-button>
              </a-upload>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="银行水单附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-upload
                :fileList="files['bankReceiptPath']"
                :multiple="false"
                @preview="preview"
                @download="download('bankReceiptPath')"
                @change="file => handleChange(file, 'bankReceiptPath')"
                :beforeUpload="file => beforeUpload(file, 'bankReceiptPath')"
                :showUploadList="{ showPreviewIcon: true, showRemoveIcon: true, showDownloadIcon: true }"
                v-decorator="[
                  'bankReceiptPathUpload',
                  { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                ]"
              >
                <a-button> <a-icon type="upload" />上传</a-button>
              </a-upload>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
import { savePayment } from '@/api/tech/BeiAnGuanLi/Invest'
import SearchSelect from '../SearchSelect'
import { mapGetters } from 'vuex'
import { get } from 'lodash'

export default {
  name: 'AddBankReceiptModal',
  components: {
    SearchSelect
  },
  props: {
    record: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      options: [{ label: '是', value: true }, { label: '否', value: false }],
      isVisible: false,
      title: '',
      tableDatas: [],
      files: { filePath: [], voucherPath: [], bankReceiptPath: [] },
      spinning: false,
      editBankReceiptObje: {},
      updateId: 0,
      confirmLoading: false,
      amount: 0
    }
  },
  created () {
    this.form = this.$form.createForm(this, {
      onValuesChange: (props, values) => {
        if (values.payType && values.payType !== '1') {
          if (props.form.getFieldValue('acceptDate')) {
            this.$nextTick(() => {
              this.form.setFieldsValue({
                acceptDate: null
              })
            })
          }
        }
        if (values.amountTax || values.taxRate) {
          this.$nextTick(() => {
            const { amountTax = 0, taxRate = 0 } = this.form.getFieldsValue(['amountTax', 'taxRate'])
            this.amount = (amountTax / (1 + taxRate)).toFixed(2)
          })
        }
      }
    })
  },
  computed: {
    ...mapGetters('common', ['getPayStageArr', 'getPayTypeArr'])
  },
  methods: {
    show (title) {
      this.form.resetFields()
      this.title = title
      this.isVisible = true
    },
    edit (title, bankReceiptRecord) {
      this.form.resetFields()
      this.title = title
      this.isVisible = true
      this.loaderBankReceiptData(bankReceiptRecord)
    },
    afterClose () {
      this.isVisible = false
      this.title = ''
      this.tableDatas = []
      this.editBankReceiptObje = {}
      this.updateId = 0
      this.files = { filePath: [], voucherPath: [], bankReceiptPath: [] }
    },
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = true
          this.confirmLoading = true
          const params = { ...values }
          params.voucherDate = params.voucherDate.format('YYYY-MM-DD')
          params.payDate = params.payDate.format('YYYY-MM-DD')
          if (params.payType === '1') {
            params.acceptDate = params.acceptDate.format('YYYY-MM-DD')
          }
          params.payee = params.payee[0]
          for (const key in this.files) {
            if (this.files[key].length) {
              params[key] = this.files[key][0].url
            }
          }
          if (this.updateId) {
            params['id'] = this.updateId
          }
          savePayment(params)
            .then(response => {
              if (response.success && response.data) {
                this.$message.success('操作成功！')
                this.$emit('refresh')
                this.isVisible = false
              } else {
                this.$message.error(response.errorMessage)
                console.log(`handleSubmit functon erro ${response.errorCode} :${response.errorMessage}`)
                console.log(`handleSubmit functon params ${params}`)
              }
            })
            .catch(error => {
              console.log('handleSubmit functon erro ', error)
            })
            .finally(() => {
              this.spinning = false
              this.confirmLoading = false
            })
        }
      })
    },
    loaderBankReceiptData (bankReceiptRecord) {
      this.updateId = bankReceiptRecord.id
      bankReceiptRecord.payee = [bankReceiptRecord.payee]
      this.initFormData(bankReceiptRecord)
    },
    initFormData (bankReceiptRecord) {
      this.$nextTick(() => {
        for (const key in this.files) {
          if (bankReceiptRecord[key]) {
            const filePath = bankReceiptRecord[key]
            const arr = filePath.split('/')
            const fileName = arr[arr.length - 1].substr(13)
            this.files[key] = [
              {
                uid: fileName,
                name: fileName,
                status: 'done',
                url: filePath
              }
            ]
          }
        }
        this.$initForm(this.form, bankReceiptRecord, ['voucherDate', 'payDate', 'acceptDate'])
        this.form.setFieldsValue({
          bankReceiptPathUpload: { fileList: this.files['bankReceiptPath'] },
          voucherPathUpload: { fileList: this.files['voucherPath'] }
        })
      })
    },
    beforeUpload (file, key) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http
        .post('/beian/upload', param, config)
        .then(res => {
          if (res.success) {
            this.files[key] = [
              {
                uid: res.data.fileName,
                name: res.data.fileName,
                status: 'done',
                url: res.data.filePath
              }
            ]
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '上传失败')
          }
        })
        .catch(res => {})
      return false
    },
    handleChange (file, key) {
      if (file.file.status === 'removed') {
        this.files[key] = file.fileList
      }
    },
    preview (file) {
      if (file.url) {
        this.$preview({
          filePath: file.url,
          docName: get(file, 'name', ''),
          visible: true
        })
      }
    },
    download (key) {
      const file = this.files[key][0]
      if (file) {
        const filePath = file.url
        const arr = filePath.split('/')
        const fileName = arr[arr.length - 1]
        this.$exportData(
          '/beian/download',
          { filePath },
          fileName.length > 13 ? fileName.substr(13) : fileName,
          this.$message
        )
      }
    },
    /**
     * @description: 转化上传的验证值的回调函数
     * @param {*} 当前的值
     * @return {*} 验证的内容
     */
    transformUpload (value) {
      if (value && value.fileList && value.fileList.length) {
        return value.fileList
      }
      return null
    }
  }
}
</script>
<style lang="less" scoped>
#spin {
  height: 100%;
  width: 100%;
  & /deep/ .ant-spin-nested-loading {
    height: 100%;
  }
  & /deep/ .ant-spin-container {
    height: 100%;
  }
}
</style>
