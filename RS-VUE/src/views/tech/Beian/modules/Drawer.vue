<!--
 * @Author: ldx
 * @Date: 2021-03-19 08:52:03
 * @LastEditTime: 2021-10-27 09:00:02
 * @LastEditors: lzh
 * @Description: 设备抽屉
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\Drawer.vue
-->
<template>
  <a-drawer
    :title="title"
    placement="right"
    :closable="true"
    :maskClosable="false"
    :visible="visible"
    width="900"
    :bodyStyle="{ padding: '12px 12px', height: 'calc(100% - 55px)', overflowY: 'auto' }"
    destroyOnClose
    :after-visible-change="afterVisibleChange"
    @close="onClose"
  >
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <div class="content">
        <a-form :form="form">
          <a-row :gutter="24">
            <a-col span="12">
              <a-form-item label="序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number
                  placeholder="请输入序号"
                  style="width: 100%;"
                  v-decorator="['order',{ rules: [{ required: true, message: '请输入序号' }] }]"
                  :min="-$store.state.maxOrder"
                  :max="$store.state.maxOrder"
                ></a-input-number>
              </a-form-item>
            </a-col>
            <a-col span="12">
              <a-form-item label="支出类别" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-cascader
                  placeholder="请输入支出类别"
                  v-decorator="['type', { rules: [{ required: true, message: '请输入支出类别' }] }]"
                  :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                  :options="disburseType"
                  changeOnSelect
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col span="12">
              <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <search-select
                  url="/inverstment/getDeviceName"
                  searchField="deviceName"
                  placeholder="请输入设备名称"
                  v-decorator="[
                    'ename',
                    { rules: [{ required: true, message: '请输入设备名称' }], validateTrigger: ['change', 'blur'] }
                  ]"
                />
              </a-form-item>
            </a-col>
            <a-col span="12">
              <a-form-item label="规格型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入规格型号"
                  v-decorator="['emodal', { rules: [{ required: true, message: '请输入规格型号' }] }]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col span="12">
              <a-form-item label="入账时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-date-picker style="width: 100%;" v-decorator="['entryDate', { rules: [{ required: true, message: '请选择入账时间' }] }]"></a-date-picker>
              </a-form-item>
            </a-col>
            <a-col span="12">
              <a-form-item label="功率(kW)" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number
                  v-decorator="['usagePower', { rules: [{ required: true, message: '请输入额定功耗' }] }]"
                  :precision="2"
                  :min="0"
                  :max="$store.state.totalMax"
                  style="width:100%"
                  placeholder="请输入额定功耗"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col span="12">
              <a-form-item label="负荷系数" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number
                  v-decorator="['loadFactor', { rules: [{ required: true, message: '请输入负荷系数' }] }]"
                  :precision="4"
                  :min="0"
                  :max="9.9998"
                  style="width:100%"
                  placeholder="请输入负荷系数"
                />
              </a-form-item>
            </a-col>
            <a-col span="12">
              <a-form-item label="稼动率" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number
                  v-decorator="['runRate', { rules: [{ required: true, message: '请输入稼动率' }] }]"
                  :precision="4"
                  :min="0"
                  :max="9.9998"
                  style="width:100%"
                  placeholder="请输入稼动率"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col span="12">
              <a-form-item label="运行时间(h)" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number
                  v-decorator="['workHour', { rules: [{ required: true, message: '请输入运行时间' }] }]"
                  :precision="2"
                  :min="0"
                  :max="$store.state.totalMax"
                  style="width:100%"
                  placeholder="请输入运行时间"
                />
              </a-form-item>
            </a-col>
          </a-row>

          <ys-divider title="发票" height="18px" />
          <invoices
            ref="invoices"
            v-bind="$attrs"
            :record="record"
            :investRecord="investRecord"
          ></invoices>
          <ys-divider title="合同" height="18px" top="35px" />
          <contracts
            ref="contracts"
            v-bind="$attrs"
            :record="record"
            :investRecord="investRecord"
          ></contracts>
          <ys-divider title="投资清单付款" height="18px" top="35px" />
          <bank-receipts
            ref="bankReceipts"
            v-bind="$attrs"
            :record="record"
            :investRecord="investRecord"
          ></bank-receipts>
          <ys-divider title="铭牌" height="18px" top="35px" />
          <nameplate
            ref="nameplate"
            v-bind="$attrs"
            :record="record"
            :investRecord="investRecord"
          ></nameplate>
        </a-form>
      </div>
      <div
        :style="{
          position: 'absolute',
          right: 0,
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e9e9e9',
          padding: '10px 16px',
          background: '#fff',
          textAlign: 'right',
          zIndex: 1
        }"
      >
        <!-- <a-button :style="{ marginRight: '8px' }" @click="onClose">
          取消
        </a-button> -->
        <a-button type="primary" @click="onSave" :loading="loading">
          保存
        </a-button>
      </div>
    </a-spin>
  </a-drawer>
</template>

<script>
import Invoices from './Invoices'
import Contracts from './Contracts'
import BankReceipts from './BankReceipts'
import Nameplate from './Nameplate'
import YsDivider from '@/components/YsDivider'
import { saveInvestment } from '@/api/tech/BeiAnGuanLi/Invest'
import SearchSelect from './SearchSelect'
export default {
  name: 'Drawer',
  components: {
    Invoices,
    Contracts,
    BankReceipts,
    Nameplate,
    YsDivider,
    SearchSelect
  },
  props: {
    record: {
      type: Object,
      default: () => {
        return {}
      }
    },
    disburseType: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      visible: false,
      form: this.$form.createForm(this),
      title: '',
      spinning: false,
      isAdd: true,
      loading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      investRecord: {},
      updateId: 0,
      files: { filePath: [] }
    }
  },
  methods: {
    show (title) {
      this.updateId = 0
      this.form.resetFields()
      this.visible = true
      this.title = title
      this.isAdd = true
      this.resetTableData()
    },
    resetTableData () {
      if (this.isAdd) {
        this.$nextTick(() => {
          this.$refs.invoices.resetTableData()
          this.$refs.contracts.resetTableData()
          this.$refs.bankReceipts.resetTableData()
          this.$refs.nameplate.resetTableData()
        })
      } else {
        this.$refs.invoices.onloadTableData(this.investRecord.id)
        this.$refs.contracts.onloadTableData(this.investRecord.id)
        this.$refs.bankReceipts.onloadTableData(this.investRecord.id)
        this.$refs.nameplate.onloadTableData(
          this.investRecord && this.investRecord.nameplateModels ? this.investRecord.nameplateModels : []
        )
      }
    },
    edit (title, investRecord) {
      this.updateId = 0
      this.form.resetFields()
      this.visible = true
      this.title = title
      this.isAdd = false
      this.updateId = investRecord.id
      this.investRecord = investRecord
      this.$nextTick(() => {
        this.initFormData(investRecord)
        this.resetTableData()
      })
    },
    onClose () {
      Object.assign(this.$data, this.$options.data.call(this))
    },
    onSave () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = true
          this.loading = true
          const params = {
            invoiceIds: [],
            contractIds: [],
            bankReceiptIds: [],
            nameplateModel: undefined,
            beianId: this.record.id
          }
          if (this.updateId) {
            params['id'] = this.updateId
          }
          this.setParamsValue(params)
          Object.assign(params, values)
          params.type = params.type.join(',')
          params.ename = params.ename[0]

          saveInvestment(params)
            .then(response => {
              if (response.data && response.success) {
                this.$message.success('操作成功!')
                this.$emit('refresh')
                this.visible = false
              } else {
                this.$message.error(response.errorMessage)
                console.log('params onSave:', params)
                console.log(`onSave function error ${response.errorCode}: ${response.errorMessage}`)
              }
            })
            .catch(error => {
              console.log('onSave function error ', error)
            })
            .finally(res => {
              this.spinning = false
              this.loading = false
            })
        }
      })
    },
    setParamsValue (params) {
      if (this.$refs.invoices && this.$refs.invoices.tableDatas) {
        const tableDatas = this.$refs.invoices.tableDatas
        tableDatas.forEach(item => {
          params['invoiceIds'].push(item.id)
        })
      }
      if (this.$refs.contracts && this.$refs.contracts.tableDatas) {
        const tableDatas = this.$refs.contracts.tableDatas
        tableDatas.forEach(item => {
          params['contractIds'].push(item.id)
        })
      }
      if (this.$refs.bankReceipts && this.$refs.bankReceipts.tableDatas) {
        const tableDatas = this.$refs.bankReceipts.tableDatas
        tableDatas.forEach(item => {
          params['bankReceiptIds'].push(item.id)
        })
      }
      if (this.$refs.nameplate && this.$refs.nameplate.tableDatas) {
        const tableDatas = this.$refs.nameplate.tableDatas
        params['nameplateModels'] = tableDatas
      }
    },
    afterVisibleChange () {},
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
    changeSaveBtnStatus () {
      this.onChange()
    },
    initFormData (investObj) {
      this.$nextTick(() => {
        const type = investObj.type.toString()
        investObj.type = type.split(',')
        investObj.ename = [investObj.ename]
        this.$initForm(this.form, investObj, ['entryDate', 'factoryDate'])
      })
    }
  }
}
</script>

<style lang="less" scoped>
.content {
  height: calc(100% - 53px);
  overflow-y: auto;
  overflow-x: hidden;

  & /deep/ .ant-form-item {
    margin-bottom: 8px;
  }
}
.footer {
  position: 'absolute';
  right: 0;
  bottom: 0;
  width: '100%';
  border-top: '1px solid #e9e9e9';
  padding: '10px 16px';
  background: '#fff';
  text-align: 'right';
  z-index: 1;
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
