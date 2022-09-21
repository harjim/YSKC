<!--
 * @Author: ldx
 * @Date: 2021-03-19 14:14:54
 * @LastEditTime: 2021-10-27 10:08:50
 * @LastEditors: lzh
 * @Description: 添加发票信息详细
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\invoiceModules\AddInvoiceModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    style="top:0px;"
    :confirmLoading="confirmLoading"
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
            <a-form-item label="设备供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <search-select
                url="/inverstment/getCompanyName"
                searchField="companyName"
                :paramData="{ beianId: record.id }"
                placeholder="请输入设备供应商"
                v-decorator="[
                  'invoiceFrom',
                  { rules: [{ required: true, message: '请输入设备供应商' }], validateTrigger: ['change', 'blur'] }
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="发票号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="发票号码"
                @change="changeInvoiceNo"
                v-decorator="[
                  'invoiceNo',
                  {
                    rules: [
                      { required: true, message: '请输入发票号码', whitespace: true },
                      { validator: this.verifyInvoiceNo }
                    ],
                    validateTrigger: ['blur']
                  }
                ]"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="发票日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width: 100%;"
                v-decorator="['invoiceDate', { rules: [{ required: true, message: '请选择发票日期' }] }]"
              ></a-date-picker>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="发票记账凭证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="发票记账凭证号"
                v-decorator="[
                  'voucherNo',
                  { rules: [{ required: true, message: '请输入发票记账凭证号', whitespace: true }] }
                ]"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="转固日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width: 100%;"
                v-decorator="['voucherDate', { rules: [{ required: true, message: '请选择转固日期' }] }]"
              ></a-date-picker>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="发票附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-upload
                :fileList="files['invoicePath']"
                :multiple="false"
                @preview="download('invoicePath')"
                :beforeUpload="file => beforeUpload(file, 'invoicePath')"
                @change="file => handleChange(file, 'invoicePath')"
                v-decorator="[
                  'invoicePathUpload',
                  { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                ]"
              >
                <a-button> <a-icon type="upload" />上传</a-button>
              </a-upload>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="转固凭证附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-upload
                :fileList="files['voucherPath']"
                :multiple="false"
                @preview="download('voucherPath')"
                :beforeUpload="file => beforeUpload(file, 'voucherPath')"
                @change="file => handleChange(file, 'voucherPath')"
                v-decorator="['voucherPathUpload']"
              >
                <a-button> <a-icon type="upload" />上传</a-button>
              </a-upload>
            </a-form-item>
          </a-col>
        </a-row>
        <vxe-grid
          ref="table"
          :data="tableDatas"
          border
          size="small"
          resizable
          auto-resize
          highlight-hover-row
          show-overflow
          show-header-overflow
          highlight-current-row
          header-align="center"
        >
          <vxe-table-column type="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
          <vxe-table-column title="设备名称" field="ename" width="150" align="left" header-align="center">
            <template #header>
              <span class="required">*</span>设备名称
            </template>
            <template #default="{row}">
              <search-select
                v-if="row.add"
                url="/inverstment/getDeviceName"
                searchField="deviceName"
                placeholder="请输入设备名称"
                :value="row.ename"
                @change="v => vChange(v, row)"
              />
              <span v-else>{{ row.ename }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="规格型号" field="emodal" width="120" align="left" header-align="center">
            <template #header>
              <span class="required">*</span>规格型号
            </template>
            <template #default="{row}">
              <a-input v-if="row.add" placeholder="规格型号" v-model="row.emodal"></a-input>
              <span v-else>{{ row.emodal }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="数量" field="quantity" width="90" align="right" header-align="center">
            <template #header>
              <span class="required">*</span>数量
            </template>
            <template #default="{row}">
              <a-input-number
                :min="0"
                style="width: 60px;"
                v-if="row.add"
                placeholder="数量"
                v-model="row.quantity"
              ></a-input-number>
              <span v-else>{{ row.quantity }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="税率" field="taxRate" width="100" align="right" header-align="center">
            <template #header>
              <span class="required">*</span>税率
            </template>
            <template #default="{row}">
              <a-input-number
                :min="0"
                :max="100"
                :step="0.5"
                :precision="2"
                style="width: 65px;"
                v-if="row.add"
                placeholder="税率"
                v-model="row.taxRate"
              ></a-input-number>
              <span v-else>{{ row.taxRate }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="单价(元/含税)" field="taxPrice" width="140" align="right" header-align="center">
            <template #header>
              <span class="required">*</span>单价（元/含税）
            </template>
            <template #default="{row}">
              <a-input-number
                :min="0.1"
                :precision="2"
                style="width: 110px;"
                v-if="row.add"
                placeholder="单价（元/含税）"
                v-model="row.taxPrice"
              ></a-input-number>
              <span v-else>{{ row.taxPrice }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="单价（元）" field="excludeTaxPrice" width="110" align="right" header-align="center">
            <template #default="{row}">
              {{ setValue(row, 'excludeTaxPrice', ((row.taxPrice || 0) / (1 + (row.taxRate || 0) / 100))).toFixed(2) }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="金额(元/含税)" field="taxAmount" width="110" align="right" header-align="center">
            <template #default="{row}">
              {{
                setValue(
                  row,
                  'taxAmount',
                  Math.round(((row.quantity ? row.quantity : 0) * (row.taxPrice ? row.taxPrice : 0)).toFixed(2) * 100) /
                    100
                )
              }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="金额（元）" field="excludeTaxAmount" width="110" align="right" header-align="center">
            <template #default="{row}">
              {{
                setValue(
                  row,
                  'excludeTaxAmount',
                  Math.round(
                    ((row.excludeTaxPrice ? row.excludeTaxPrice : 0) * (row.quantity ? row.quantity : 0)).toFixed(2) *
                      100
                  ) / 100
                )
              }}
            </template>
          </vxe-table-column>
          <vxe-table-column title="操作" width="100" align="center" header-align="center" fixed="right">
            <template #default="{row,rowIndex}">
              <a-popconfirm title="您确定要删除？" placement="topLeft" @confirm="onConfirm(row, rowIndex)">
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
          <template #bottom>
            <a-button title="添加" type="dashed" style="width: 100%;font-weight: bolder" @click="onAdd">+</a-button>
          </template>
        </vxe-grid>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
import { saveInvoice, getInvoiceInfo, verifyInvoice } from '@/api/tech/BeiAnGuanLi/Invest'
import SearchSelect from '../SearchSelect'
import moment from 'moment'
import { trim } from 'lodash'
export default {
  name: 'AddInvoiceModal',
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
      confirmLoading: false,
      isVisible: false,
      title: '',
      form: this.$form.createForm(this),
      tableDatas: [],
      files: { filePath: [], voucherPath: [], invoicePath: [] },
      spinning: false,
      editInvoiceObje: {},
      updateId: 0,
      isVerify: true,
      delIds: []
    }
  },
  methods: {
    moment,
    vChange (v, row) {
      row.ename = v
    },
    show (title) {
      this.form.resetFields()
      this.title = title
      this.isVisible = true
    },
    edit (title, invoiceRecord) {
      this.form.resetFields()
      this.title = title
      this.isVisible = true
      this.isVerify = false
      this.loaderInvoiceData(invoiceRecord.invoiceId)
    },
    onAdd () {
      // if (!this.verifyTableRow()) {
      //   return
      // }
      const newRow = {
        add: true,
        ename: undefined,
        emodal: undefined,
        quantity: undefined,
        excludeTaxPrice: undefined,
        taxPrice: undefined,
        excludeTaxAmount: undefined,
        taxAmount: undefined,
        taxRate: undefined
      }
      this.tableDatas.push(newRow)
    },
    changeInvoiceNo (e) {
      if (this.editInvoiceObje && this.editInvoiceObje.invoiceNo === e.target.value) {
        this.isVerify = false
      } else {
        this.isVerify = true
      }
    },
    verifyInvoiceNo (rule, value, callback) {
      if (value && this.isVerify) {
        verifyInvoice({ invoiceNo: value }).then(response => {
          if (!response.data) {
            callback(new Error('该发票号已存在，请重新输入！'))
          } else {
            callback()
          }
        })
      } else {
        callback()
      }
    },
    onConfirm (record, index) {
      this.spinning = true
      this.tableDatas.splice(index, 1)
      if (record.id) {
        this.delIds.push(record.id)
      }
      this.spinning = false
    },
    afterClose () {
      this.isVisible = false
      this.title = ''
      this.tableDatas = []
      this.editInvoiceObje = {}
      this.updateId = 0
      this.isVerify = true
      this.delIds = []
      this.files = { filePath: [], voucherPath: [], invoicePath: [] }
    },
    handleSubmit () {
      if (!(this.tableDatas && this.tableDatas.length)) {
        this.$message.warning('发票必须有明细项！')
        return
      }
      if (!this.verifyTableRow()) {
        return
      }
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = true
          this.confirmLoading = true
          const params = { ...values }
          params.invoiceDate = params.invoiceDate.format('YYYY-MM-DD')
          params.voucherDate = params.voucherDate.format('YYYY-MM-DD')
          const tempTableDatas = this.$deepClone(this.tableDatas)
          params['models'] = tempTableDatas.map(value => {
            value.ename = value.ename[0]
            return value
          })
          for (const key in this.files) {
            if (this.files[key].length) {
              params[key] = this.files[key][0].url
            }
          }
          let taxAmount = 0
          this.tableDatas.forEach(item => {
            taxAmount = (taxAmount * 1000 + item.taxAmount * 1000) / 1000
          })
          params['taxAmount'] = taxAmount
          if (this.updateId) {
            params['id'] = this.updateId
          }
          params['taxAmount'] = taxAmount
          params['invoiceFrom'] = params['invoiceFrom'][0]
          saveInvoice(params)
            .then(response => {
              if (response.success && response.data) {
                this.$message.success('操作成功！')
                this.$emit('refresh')
                this.$emit('setDelIds', this.delIds)
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
    beforeUpload (file, key) {
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
    setValue (row, field, value) {
      row[field] = value
      return value
    },
    verifyTableRow () {
      if (this.tableDatas && this.tableDatas.length) {
        const verifyField = ['ename', 'emodal', 'quantity', 'taxRate', 'excludeTaxPrice']
        const KV = { ename: '设备名称', emodal: '规格型号', quantity: '数量', taxRate: '税率', excludeTaxPrice: '单价' }
        let result = true
        for (let i = 0; i < this.tableDatas.length; i++) {
          for (let y = 0; y < verifyField.length; y++) {
            const row = this.tableDatas[i]
            const field = verifyField[y]
            if (!row[field] || !trim(row[field]).length) {
              this.$message.warning(`第${i + 1}行,【${KV[field]}】列为必填字段`)
              result = false
              return result
            }
          }
        }
        return result
      } else {
        return true
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
    },
    loaderInvoiceData (invoiceId) {
      this.spinning = true
      getInvoiceInfo({ invoiceId })
        .then(response => {
          if (response.success && response.data) {
            this.updateId = invoiceId
            this.editInvoiceObje = response.data
            this.editInvoiceObje['invoiceFrom'] = [this.editInvoiceObje['invoiceFrom']]
            this.editInvoiceObje.models = this.editInvoiceObje.models.map(value => {
              value.ename = [value.ename]
              return value
            })
            this.initFormData(this.editInvoiceObje)
          }
        })
        .catch(error => {
          console.log('loaderInvoiceData function error', error)
        })
        .finally(() => {
          this.spinning = false
        })
    },
    initFormData (editInvoiceObje) {
      this.$nextTick(() => {
        for (const key in this.files) {
          if (editInvoiceObje[key]) {
            const filePath = editInvoiceObje[key]
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
        this.$initForm(this.form, editInvoiceObje, ['invoiceDate', 'voucherDate'])
        editInvoiceObje.models.forEach(item => {
          item['add'] = true
        })
        this.tableDatas = editInvoiceObje.models
        this.form.setFieldsValue({
          voucherPathUpload: { fileList: this.files['voucherPath'] },
          invoicePathUpload: { fileList: this.files['invoicePath'] }
        })
      })
    }
  }
}
</script>
<style lang="less" scoped>
.required {
  color: red;
}
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
