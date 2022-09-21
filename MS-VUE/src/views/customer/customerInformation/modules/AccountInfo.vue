<!--
 * @Author: ldx
 * @Date: 2020-12-07 08:46:33
 * @LastEditTime: 2020-12-07 10:37:59
 * @LastEditors: ldx
 * @Description:单位银行开户信息
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\AccountInfo.vue
-->
<template>
  <div>
    <!-- <a-divider orientation="left">
      <span class="mode-title">单位银行开户信息</span>
    </a-divider> -->
    <custom-title
      title="单位银行开户信息"
      @switchEdit="switchEdit"
      ref="title"
    ></custom-title>
    <a-spin
      :spinning="spinning"
      top="请稍后..."
    >
      <a-form :form="form">
        <a-row :gutter="[8]">
          <a-col :span="24">
            <a-form-item
              label="银行账户名称  "
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入银行账户名称"
                v-decorator="['accountName', {rules:[{required: false, message: '请输入银行账户名称'}]}]"
              />
              <template v-else>
                {{ formData.accountName ? formData.accountName : '-' }}
              </template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item
              label="基本账户开户行"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入基本账户开户行"
                v-decorator="['bankName', {rules:[{required: false, message: '请输入基本账户开户行'}]}]"
              />
              <template v-else>
                {{ formData.bankName ? formData.bankName : '-' }}
              </template>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="开户账号"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入开户账号"
                v-decorator="['bankAccount', {rules:[{required: false, message: '请输入开户账号'}]}]"
              />
              <template v-else>
                {{ formData.bankAccount ? formData.bankAccount : '-' }}
              </template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item
              label="银行信用等级"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-select
                v-if="isEdit"
                v-decorator="['creditRating', {rules:[{required: false, message: '请选择银行信用等级'}]}]"
                placeholder="请选择银行信用等级"
              >
                <a-select-option
                  v-for="(v,i) in creditArr"
                  :key="i"
                  :value="`${i}`"
                >{{ v }}</a-select-option>
              </a-select>
              <template v-else>
                {{ formData.creditRating || formData.creditRating === 0 ? creditArr[formData.creditRating] : '-' }}
              </template>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="企业细分领域"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入企业细分领域"
                v-decorator="['industry', {rules:[{required: false, message: '请输入企业细分领域'}]}]"
              />
              <template v-else>
                {{ formData.industry ? formData.industry : '-' }}
              </template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="24">
            <a-form-item
              label="附件"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-upload
                v-if="isEdit"
                :fileList="fileList"
                @change="UploadHandleChange"
                :beforeUpload="beforeUpload"
                @preview="downloadfile"
              >
                <a-button>
                  <a-icon type="upload" />上传附件
                </a-button>
              </a-upload>
              <template v-else>
                <template v-if="formData.filePath">
                  <a @click="downloadfile({ name:getFileName() })">
                    {{ getFileName() }}
                  </a>
                </template>
                <template v-else>-</template>
              </template>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <div>
        <div
          class="action-buttons"
          v-if="isEdit"
        >
          <a-button
            class="save-btn"
            type="primary"
            @click="saveBtn"
          >保存</a-button>
          <a-button @click="cancelBtn()">取消</a-button>
        </div>
      </div>
    </a-spin>
  </div>
</template>
<script>
import CustomTitle from './CustomTitle'
const creditArr = ['AAA', 'AA', 'A', 'BBB', 'BB', 'B', 'CCC', 'CC', 'C', 'D']
export default {
  name: 'AccountInfo',
  components: {
    CustomTitle
  },
  props: {
    companyId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      bakData: undefined,
      fileList: [],
      spinning: false,
      creditArr,
      formData: {},
      form: this.$form.createForm(this),
      isEdit: false,
      currentYear: new Date().getFullYear(),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 }
      },
      labelColT: {
        xs: { span: 24 },
        sm: { span: 10 }
      },
      wrapperColT: {
        xs: { span: 24 },
        sm: { span: 14 }
      }
    }
  },
  created () {
    this.loadData()
  },
  watch: {
    companyId (cId) {
      this.cancelBtn()
      this.loadData()
    }
  },
  methods: {
    downloadfile (file) {
      this.$exportData('/companyInfo/download', { companyId: this.companyId }, file.name, this.$message)
    },
    beforeUpload (file, key) {
      const param = new FormData()
      param.append('file', file)
      param.append('companyId', this.companyId)
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/companyInfo/uploadBankInfo', param, config).then(res => {
        if (res.success) {
          this.fileList = [{
            uid: res.data.fileName,
            name: res.data.fileName,
            status: 'done',
            url: res.data.filePath
          }]
        }
      }).catch(res => {
      })
      return false
    },
    UploadHandleChange (file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
      }
    },
    loadData () {
      this.$http.get('/companyInfo/getBankInfo', { params: { companyId: this.companyId } }).then(res => {
        var formData = {}
        if (res.success) {
          if (res.data) {
            formData = res.data
          }
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '获取单位银行开户信息失败')
        }
        this.formData = formData
      })
    },
    saveBtn () {
      this.spinning = true
      const { form: { validateFields } } = this
      validateFields((error, values) => {
        if (error) {
          this.spinning = false
          return
        }
        let check = false
        for (const k in values) {
          if (values[k]) {
            check = true
            break
          }
        }
        if (!check) {
          this.spinning = false
          return
        }
        values.year = this.currentYear
        values.companyId = this.companyId
        if (this.fileList.length) {
          values.filePath = this.fileList[0].url
        } else {
          values.filePath = null
        }
        this.$http.post('/companyInfo/editBankInfo', values).then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
            this.formData = values
            this.bakData = this.$deepClone(this.formData)
            this.cancelBtn()
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
        }).finally(() => {
          this.spinning = false
        })
      })
    },
    cancelBtn () {
      this.isEdit = false
      this.$refs.title.changeEdit(false)
      if (this.bakData) {
        this.formData = this.$deepClone(this.bakData)
        this.bakData = undefined
      }
      this.loadFilePath()
    },
    switchEdit (isEdit) {
      this.isEdit = isEdit
      if (isEdit) {
        this.bakData = this.$deepClone(this.formData)
        this.loadFilePath()
      }
    },
    loadFilePath () {
      this.$nextTick(() => {
        this.$initForm(this.form, this.formData)
        const temp = []
        if (this.formData.filePath) {
          const name = this.getFileName()
          temp.push({
            uid: name,
            name,
            status: 'done',
            url: this.formData.filePath
          })
        }
        this.fileList = temp
      })
    },
    getFileName () {
      if (this.formData.filePath) {
        return this.formData.filePath.substring(this.formData.filePath.lastIndexOf('/') + 14, this.formData.filePath.length)
      }
      return ''
    }
  }
}
</script>
<style lang="less" scoped>
.mode-title {
  font-size: 18px;
  font-weight: 600;
  color: #151515;
}
.action-buttons {
  padding: 12px 0;
  text-align: center;
}
.save-btn {
  margin-right: 40px;
}
</style>
