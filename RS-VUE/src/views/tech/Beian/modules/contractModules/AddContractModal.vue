<!--
 * @Author: ldx
 * @Date: 2021-03-19 14:14:54
 * @LastEditTime: 2021-10-27 09:54:22
 * @LastEditors: lzh
 * @Description: 添加合同信息详细
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\contractModules\AddContractModal.vue
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
            <a-form-item label="设备供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <search-select
                url="/inverstment/getCompanyName"
                searchField="companyName"
                :paramData="{ beianId: record.id }"
                placeholder="请输入设备供应商"
                v-decorator="[
                  'signTarget',
                  { rules: [{ required: true, message: '请输入设备供应商' }], validateTrigger: ['change', 'blur'] }
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="合同编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="合同编号"
                @change="changeContractNo"
                v-decorator="[
                  'contractNo',
                  {
                    rules: [
                      { required: true, message: '请输入合同编号', whitespace: true },
                      { validator: this.verifyContractNo }
                    ],
                    validateTrigger: ['blur']
                  }
                ]"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="合同日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width:100%;"
                v-decorator="['contractDate', { rules: [{ required: true, message: '请选择合同日期' }] }]"
              ></a-date-picker>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="24">
            <a-form-item
              label="合同附件"
              :labelCol="{
                xs: { span: 24 },
                sm: { span: 4 }
              }"
              :wrapperCol="{
                xs: { span: 24 },
                sm: { span: 16 }
              }"
            >
              <a-upload
                :fileList="files['filePath']"
                :multiple="false"
                @preview="download('filePath')"
                :beforeUpload="file => beforeUpload(file, 'filePath')"
                @change="file => handleChange(file, 'filePath')"
                v-decorator="[
                  'filePathUpload',
                  { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                ]"
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
          <vxe-table-column title="设备名称" field="ename" min-width="120" align="left" header-align="center">
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
          <vxe-table-column title="规格型号" field="emodal" min-width="120" align="left" header-align="center">
            <template #header>
              <span class="required">*</span>规格型号
            </template>
            <template #default="{row}">
              <a-input v-if="row.add" placeholder="规格型号" v-model="row.emodal"></a-input>
              <span v-else>{{ row.emodal }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="数量" field="quantity" min-width="120" align="left" header-align="center">
            <template #header>
              <span class="required">*</span>数量
            </template>
            <template #default="{row}">
              <a-input v-if="row.add" placeholder="数量" v-model="row.quantity"></a-input>
              <span v-else>{{ row.quantity }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="金额" field="amount" min-width="120" align="left" header-align="center">
            <template #header>
              <span class="required">*</span>金额
            </template>
            <template #default="{row}">
              <a-input-number
                v-if="row.add"
                placeholder="金额"
                v-model="row.amount"
                :max="$store.state.maxOrder"
                :min="0"
                :precision="2"
              ></a-input-number>
              <span v-else>{{ row.amount }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="是否关联方购置" field="partPurchase" min-width="120" align="center" header-align="center">
            <template #header>
              <span class="required">*</span>是否关联方购置
            </template>
            <template #default="{row}">
              <a-checkbox v-model="row.partPurchase" v-if="row.add" />
              <span v-else>{{ row.partPurchase ? '是' : '否' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="是否二手" field="secondHand" min-width="120" align="center" header-align="center">
            <template #header>
              <span class="required">*</span>是否二手
            </template>
            <template #default="{row}">
              <a-checkbox v-model="row.secondHand" v-if="row.add" />
              <span v-else>{{ row.secondHand ? '是' : '否' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="是否经销商购置" field="secondHand" min-width="120" align="center" header-align="center">
            <template #header>
              <span class="required">*</span>是否经销商购置
            </template>
            <template #default="{row}">
              <a-checkbox v-model="row.traderPurchase" v-if="row.add" />
              <span v-else>{{ row.traderPurchase ? '是' : '否' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="操作" width="100" align="center" header-align="center">
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
import { getContractInfo, saveContract, verifyContract } from '@/api/tech/BeiAnGuanLi/Invest'
import { trim } from 'lodash'
import SearchSelect from '../SearchSelect'
export default {
  name: 'AddContractModal',
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
  data() {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      isVisible: false,
      title: '',
      form: this.$form.createForm(this),
      confirmLoading: false,
      tableDatas: [],
      files: { filePath: [] },
      spinning: false,
      editContractObje: {},
      updateId: 0,
      isVerify: true
    }
  },
  methods: {
    vChange(v, row) {
      row.ename = v
    },
    show(title) {
      this.form.resetFields()
      this.title = title
      this.isVisible = true
    },
    edit(title, contractRecord) {
      this.form.resetFields()
      this.title = title
      this.isVisible = true
      this.isVerify = false
      this.loaderContractData(contractRecord.contractId)
    },
    onAdd() {
      // if (!this.verifyTableRow()) {
      //   return
      // }
      const newRow = { add: true, ename: undefined, emodal: undefined }
      this.tableDatas.push(newRow)
    },
    afterClose() {
      this.isVisible = false
      this.title = ''
      this.isVerify = true
      this.tableDatas = []
      this.editContractObje = {}
      this.updateId = 0
      this.files = { filePath: [] }
    },
    onConfirm(record, index) {
      this.spinning = true
      this.tableDatas.splice(index, 1)
      this.spinning = false
      // if (record.id) {
      //   const params = [{ invoiceId: record.invoiceId, id: record.id }]
      //   delContractInfo(params).then(response => {
      //     if (response.success && response.data) {
      //       this.tableDatas.splice(index, 1)
      //       this.$emit('refresh')
      //     } else {
      //       console.log(`response errorCode ${response.errorCode} : ${response.errorMessage}`)
      //       this.$message.error('删除失败！')
      //     }
      //   }).catch(error => {
      //     console.log('onDel function error', error)
      //   }).finally(() => {
      //     this.spinning = false
      //   })
      // } else {
      //   this.tableDatas.splice(index, 1)
      //   this.spinning = false
      // }
    },
    handleSubmit() {
      if (!(this.tableDatas && this.tableDatas.length)) {
        this.$message.warning('合同必须有明细项！')
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
          const tempTableDatas = this.$deepClone(this.tableDatas)
          params.contractDate = params.contractDate.format('YYYY-MM-DD')
          params['detailModels'] = tempTableDatas.map(value => {
            value.ename = value.ename[0]
            value.partPurchase = value.partPurchase || false
            value.secondHand = value.secondHand || false
            value.traderPurchase = value.traderPurchase || false
            return value
          })
          params['signTarget'] = params['signTarget'][0]
          for (const key in this.files) {
            if (this.files[key].length) {
              params[key] = this.files[key][0].url
            }
          }
          if (this.updateId) {
            params['id'] = this.updateId
          }
          saveContract(params)
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
    /**
     * @description: 转化上传的验证值的回调函数
     * @param {*} 当前的值
     * @return {*} 验证的内容
     */
    transformUpload(value) {
      if (value && value.fileList && value.fileList.length) {
        return value.fileList
      }
      return null
    },
    changeContractNo(e) {
      if (this.editContractObje && this.editContractObje.contractNo === e.target.value) {
        this.isVerify = false
      } else {
        this.isVerify = true
      }
    },
    verifyContractNo(rule, value, callback) {
      if (value && this.isVerify) {
        verifyContract({ contractNo: value }).then(response => {
          if (!response.data) {
            return callback(new Error('该合同号已存在，请重新输入！'))
          } else {
            return callback()
          }
        })
      } else {
        return callback()
      }
    },
    verifyTableRow() {
      if (this.tableDatas && this.tableDatas.length) {
        const verifyField = ['ename', 'emodal']
        const KV = { ename: '设备名称', emodal: '规格型号' }
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
    loaderContractData(contractId) {
      this.spinning = true
      getContractInfo({ contractId })
        .then(response => {
          if (response.success && response.data) {
            this.updateId = contractId
            this.editContractObje = response.data
            this.editContractObje['signTarget'] = [this.editContractObje['signTarget']]
            this.editContractObje.detailModels = this.editContractObje.detailModels.map(value => {
              value.ename = [value.ename]
              return value
            })
            this.initFormData(this.editContractObje)
          }
        })
        .catch(error => {
          console.log('loaderContractData function error', error)
        })
        .finally(() => {
          this.spinning = false
        })
    },
    initFormData(contractObje) {
      this.$nextTick(() => {
        for (const key in this.files) {
          if (contractObje[key]) {
            const filePath = contractObje[key]
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
        this.$initForm(this.form, contractObje, ['contractDate'])
        contractObje.detailModels.forEach(item => {
          item['add'] = true
        })
        this.tableDatas = contractObje.detailModels
        this.form.setFieldsValue({ filePathUpload: { fileList: this.files['filePath'] } })
      })
    },
    beforeUpload(file, key) {
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
    handleChange(file, key) {
      if (file.file.status === 'removed') {
        this.files[key] = file.fileList
      }
    },
    download(key) {
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
