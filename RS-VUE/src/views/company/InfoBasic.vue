<template>
  <div>
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-card class="zoneSpacing" :bordered="true">
          <a-row :gutter="48" style="position:relative;">
            <a-col :md="8" :sm="24">
              <a-form-item label="LOGO" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-upload
                  v-decorator="['logo']"
                  name="avatar"
                  listType="picture-card"
                  class="avatar-uploader"
                  :showUploadList="false"
                  :action="action"
                  :beforeUpload="beforeUpload"
                  @change="uploadChange"
                  :headers="headers"
                >
                  <img
                    v-if="imageUrl"
                    :src="imageUrl"
                    alt="avatar"
                    style="max-width:250px;"
                    height="56"
                  />
                  <div v-else>
                    <a-icon :type="loading ? 'loading' : 'plus'" />
                    <div class="ant-upload-text">上传</div>
                  </div>
                </a-upload>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item
                label="公司名称"
                :labelCol="labelCol"
                :wrapperCol="{
                  xs: { span: 24 },
                  sm: { span: 17 }
                }">
                <div style="display:flex">
                  <a-input
                    :disabled="true"
                    v-decorator="['companyName', {rules:[{required: true, message: '请输入公司名称'}]}]"
                    placeholder="请输入公司名称"
                    @change="(e)=>companyNameChange(e.target.value)"
                  />
                  <a-button v-if="$auth('company:info:base:updateName')" style="margin-left: 8px;" type="primary" @click="showUpdateCompanyNameModal">变更</a-button>
                </div>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="发票抬头" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入发票抬头"
                  v-decorator="['invoiceTitle', {rules:[{required: true, message: '请输入发票抬头'}]}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="所在地" :labelCol="labelCol" :wrapperCol="wrapperCol" :width="300">
                <a-cascader
                  :fieldNames="{label:'value',value:'key',children:'children'}"
                  v-decorator="['addressCode', {rules:[{required: true, message: '请选择所在地'}]}]"
                  :options="addressCode"
                  placeholder="请选择所在地"
                  changeOnSelect
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="公司地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入公司地址"
                  v-decorator="['companyAddress', {rules:[{required: true, message: '请输入公司地址'}]} ]"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="注册资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-group compact>
                  <a-input-number
                    :min="0.0001"
                    :step="0.0001"
                    style="width: 50%"
                    placeholder="请输入注册资金"
                    v-decorator="['capital', {rules:[{required: true, message: '请输入注册资金'}]}]"
                  />
                  <a-select
                    style="width:50%"
                    :dropdownStyle="{width:'100%'}"
                    v-model="capitalUnit"
                    @change="capitalUnitChange"
                  >
                    <a-select-option v-for="item in capitalUnitData" :key="item.key">
                      <span v-if="item!=null">万{{ item.value }}</span>
                    </a-select-option>
                  </a-select>
                </a-input-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="纳税识别号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入纳税识别号"
                  v-decorator="['taxpayerId', {rules:[{required: true, message: '请输入纳税识别号'}]}]"
                  @change="(e)=>taxpayerIdChange(e.target.value)"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label=" 统一社会码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入统一社会码"
                  v-decorator="['creditCode', {rules:[{required: true, message: '请输入统一社会码'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="注册时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-date-picker
                  format="YYYY-MM-DD"
                  @change="registerTimeChange"
                  placeholder="请选择注册时间"
                  v-decorator="['registerTime', {rules:[{required: true, message: '请选择注册时间'}]}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="所属行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-tree-select
                  multiple
                  treeDefaultExpandAll
                  :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
                  :tree-data="treeData"
                  v-decorator="['industryCode', {rules:[{required: true, message: '请选择所属行业'}]}]"
                  placeholder="请选择所属行业"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="主行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <!-- <a-tree-select
                  :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
                  :tree-data="treeData"
                  v-decorator="['mainIndustry', {rules:[{required: true, message: '请选择主行业'}]}]"
                  placeholder="请选择主行业"
                /> -->
                <a-cascader
                  :fieldNames="{label:'title',value:'value',children:'children'}"
                  :options="treeData"
                  placeholder="请选择主行业"
                  v-decorator="['mainIndustry', { rules:[{required: true, message: '请选择主行业'}], normalize: val => (typeof val === 'string'? val.split(',') : val ) }]"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
        <a-card class="zoneSpacing" :bordered="true">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入法人"
                  v-decorator="['owner', {rules:[{required: true, message: '请输入法人'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入联系人"
                  v-decorator="['linkMan', {rules:[{required: false, message: '请输入联系人'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入联系电话"
                  v-decorator="['linkTel', {rules:[{required: false, message: '请输入联系电话'}]}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="电子邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入电子邮箱"
                  v-decorator="['email', {rules:[{required: true, type: 'email', message: '请输入电子邮箱'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="域名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="['domain']" placeholder="请输入域名" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="首次发生研发费用" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-date-picker
                  placeholder="请输入首次发生研发费用"
                  format="YYYY-MM-DD"
                  @change="firstDevFeeChange"
                  v-decorator="['firstDevFee']"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="所属税务机关" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="['taxAuthorities']" placeholder="请输入所属税务机关" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="办理税务机关" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="['realTaxAuthorities']" placeholder="请输入办理税务机关" />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="会计制度" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="['accountSystem']" placeholder="请输入会计制度" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="研发费用专账" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-checkbox
                  @change="isHasChange"
                  v-decorator="['hasDevAccount']"
                  :checked="isHasfun"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="高新" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-checkbox
                  @change="isHightChange"
                  v-decorator="['highTec']"
                  :checked="isHightfun"
                />
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item
                v-show="isHightfun"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="高新领域"
              >
                <!-- <a-tree-select
                  showSearch
                  v-decorator="['highTecIndustry']"
                  style="width: 350px"
                  :treeData="highTecIndustry"
                  :treeDataSimpleMode="true"
                  :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                  placeholder="请选择"
                  allowClear
                >
                  <a-icon slot="suffixIcon" type="smile" />
                </a-tree-select> -->
                <a-cascader
                  :fieldNames="{label:'value',value:'key',children:'children'}"
                  :options="highTecIndustry"
                  placeholder="请选择高新领域"
                  v-decorator="['highTecIndustry', { rules:[{required: false, message: '请选择高新领域'}], normalize: val => (typeof val === 'string'? val.split(',') : val ) }]"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
        <a-card class="zoneSpacing">
          <div>
            <a-row :gutter="48">
              <a-col :md="24" :sm="24">
                <a-form-item
                  label="公司简介"
                  :labelCol="{span: 2}"
                  :wrapperCol="{span:20}"
                  :help="()=>{const r = form.getFieldValue('synopsis'); return `(${!r? 0 : r.length > 800 ? 800 : r.length}/800)`}"
                >
                  <a-textarea
                    rows="10"
                    v-decorator="['synopsis']"
                    placeholder="请输入公司简介"
                    :maxLength="800"
                  ></a-textarea>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row :gutter="48">
              <a-col :md="24" :sm="24">
                <a-form-item label="营业执照" :labelCol="{span: 2}" :wrapperCol="{span:20}">
                  <a-upload
                    v-decorator="['businessLicense']"
                    name="avatar"
                    listType="picture-card"
                    class="avatar-uploader"
                    :showUploadList="false"
                    :action="controller"
                    :beforeUpload="beforeUpload"
                    @change="uploadChangeLicense"
                    :headers="headers"
                  >
                    <img
                      v-if="businessLicenseUrl"
                      :src="businessLicenseUrl"
                      alt="avatar"
                      :width="594"
                      :height="840"
                    />
                    <div v-else>
                      <a-icon :type="loading ? 'loading' : 'plus'" />
                      <div class="ant-upload-text">上传</div>
                    </div>
                  </a-upload>
                </a-form-item>
              </a-col>
            </a-row>
          </div>
        </a-card>
      </a-form>
      <div class="table-operator">
        <a-button type="primary" v-if="$auth('company:info:base:save')" @click="handleSubmit">保存</a-button>
        <!-- <a-dropdown v-action:edit v-if="selectedRowKeys.length > 0"></a-dropdown> -->
      </div>
      <update-company-name-modal ref="updateCompanyNameModal" @updateSuccess="initializeDate" @changeCompanyName="changeCompanyName" />
    </a-spin>
  </div>
</template>

<script>
import { getBaseUrl } from '@/utils/request'
import {
  ACCESS_TOKEN //, ISADMIN
} from '@/store/mutation-types'
import updateCompanyNameModal from './modules/UpdateCompanyNameModal.vue'
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}
export default {
  name: 'InfoBasic',
  components: {
    updateCompanyNameModal
  },
  computed: {
    isHightfun: function () {
      return this.isHight
    },
    isHasfun: function () {
      return this.isHas
    }
  },
  data () {
    return {
      treeData: [],
      addressCode: [],
      highTecIndustry: [],
      action: getBaseUrl() + 'company/import',
      controller: getBaseUrl() + 'company/importBusinessLicense',
      loading: false,
      imageUrl: '',
      logo: '',
      businessLicense: undefined,
      businessLicenseUrl: undefined,
      invoice: '',
      headers: { 'Access-Token': '' },
      formData: {},
      // 查询参数
      queryParam: {},
      isHas: false,
      isHight: false,
      // isRegulatedEnterprises: false,
      // isListedCompany: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      form: this.$form.createForm(this),
      confirmLoading: false,
      capitalUnitMap: {},
      capitalUnitData: [],
      capitalUnit: 'rmb',
      updateLogo: false
    }
  },
  mounted () {
    this.$getDictionary(5).then(res => {
      this.capitalUnitData = res
      for (let index = 0; index < res.length; index++) {
        const element = res[index]
        this.$set(this.capitalUnitMap, element.key, element.value)
      }
    })
    this.$getDictionary(15).then(res => {
      this.treeData = this.$deepClone(res).map(a => {
        a.selectable = false
        return a
      })
      this.transfromTreeData(this.treeData)
    })
    const token = this.$ls.get(ACCESS_TOKEN)
    this.headers['Access-Token'] = token
    // if (this.$ls.get(ISADMIN)) {
    this.headers['CompanyId'] = this.$store.getters.companyId
    // }
    this.$addressCode(this).then(res => {
      this.addressCode = res
    })
    this.$highTecIndustryTree(this).then(res => {
      this.highTecIndustry = res
    })
    this.initializeDate()
  },
  methods: {
    changeCompanyName (companyName) {
      this.form.setFieldsValue({ companyName })
    },
    capitalUnitChange (value) {
      this.capitalUnit = value
    },
    companyNameChange (value) {
      const { form: { setFieldsValue } } = this
      setFieldsValue({ invoiceTitle: value })
    },
    taxpayerIdChange (value) {
      const { form: { setFieldsValue } } = this
      setFieldsValue({ creditCode: value })
    },
    uploadChange (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
      }
      if (info.file.status === 'done') {
        this.logo = info.file.response.data
        const { form: { setFieldsValue } } = this
        setFieldsValue({ logo: this.logo })
        getBase64(info.file.originFileObj, (imageUrl) => {
          this.imageUrl = imageUrl
          this.loading = false
        })
      }
      this.updateLogo = true
    },
    uploadChangeLicense (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
      }
      if (info.file.status === 'done') {
        this.businessLicense = info.file.response.data
        const { form: { setFieldsValue } } = this
        setFieldsValue({ businessLicense: this.businessLicense })
        getBase64(info.file.originFileObj, (imageUrl) => {
          this.businessLicenseUrl = imageUrl
          this.loading = false
        })
      }
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
    registerTimeChange (date, dateString) {
    },
    firstDevFeeChange (date, dateString) {
    },
    industryCodeChange (value) {
    },
    isHasChange (e) {
      this.isHas = e.target.checked
    },
    isHightChange (e) {
      this.isHight = e.target.checked
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (Array.isArray(values.mainIndustry)) {
            values.mainIndustry = values.mainIndustry.join(',')
          }
          if (Array.isArray(values.highTecIndustry)) {
            values.highTecIndustry = values.highTecIndustry.join(',')
          }
          values.id = this.id
          values.capitalUnit = this.capitalUnit
          if (this.logo) {
            values.logo = this.logo
          }
          if (this.businessLicense) {
            values.businessLicense = this.businessLicense
          }
          this.$http.post('/company/save', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', this.capitalUnitMap[this.capitalUnit])
                this.$message.success('保存成功')
                this.initializeDate()
                if (this.updateLogo) {
                  this.updateLogo = false
                  window.location.reload()
                }
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    initializeDate () {
      if (!this.$auth('company:info:base:view')) {
        this.$notification.error({ message: '无此权限，请跟管理员联系！' })
        return
      }
      this.$nextTick(() => {
        this.$http.get('/company/queryAll', { params: this.queryParam })
          .then(res => {
            const result = res.data
            this.formData = result
            this.isHight = this.formData.highTec
            this.isHas = this.formData.hasDevAccount
            // this.isRegulatedEnterprises = this.formData.regulatedEnterprises

            this.loading = false
            this.$initForm(this.form, result, ['firstDevFee', 'registerTime'])
            if (this.formData.logo && this.formData.logo.trim() !== '') {
              this.imageUrl = '/static/images' + this.formData.logo.trim()
            }
            if (this.formData.businessLicense && this.formData.businessLicense.trim() !== '') {
              this.businessLicenseUrl = '/static/images' + this.formData.businessLicense.trim()
            }
            this.form.setFieldsValue({ logo: this.formData.logo, businessLicense: this.formData.businessLicense })
            this.capitalUnit = res.data.capitalUnit
            this.$emit('ok', this.capitalUnitMap[this.capitalUnit])
            return result
          })
      })
    },
    /**
     * @description: 转化组件使用的数据
     * @param {*} data
     * @return {*}
     */
    transfromTreeData (data) {
      if (!data || !data.length) {
        return
      }
      for (const item of data) {
        item['title'] = item['value']
        item['value'] = item['key']
        if (item['children'] && item['children'].length) {
          this.transfromTreeData(item['children'])
        } else {
          delete item['children']
        }
      }
    },
    showUpdateCompanyNameModal () {
      this.$refs.updateCompanyNameModal.show()
    }
  }

}
</script>
<style lang='less' scoped>
.zoneSpacing {
  margin-bottom: 12px;
}
</style>
