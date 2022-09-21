<!--
 * @Author: ldx
 * @Date: 2020-12-07 14:41:44
 * @LastEditTime: 2021-03-02 12:00:37
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\CustomerExtra.vue
-->
<template>
  <div>
    <custom-title title="扩展信息" @switchEdit="switchEdit" ref="title"></custom-title>
    <a-spin :spinning="spinning" tip="加载中...">
      <a-form :form="form">
        <a-row :gutter="[8]">
          <a-col :span="24">
            <a-form-item label="经营地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-if="isEdit"
                placeholder="请输入经营地址"
                v-decorator="['manageAddress', {rules:[{required: false, message: '请输入经营地址'}]}]"
              />
              <span v-else>
                {{ data.manageAddress ? data.manageAddress : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item label="注册所在区" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-cascader
                v-if="isEdit"
                :options="addressCode"
                placeholder="请输入注册所在区"
                style="width: 100%;"
                v-decorator="['registerAddrCode', {rules:[{required: false, message: '请输入注册所在区'}]}]"
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                changeOnSelect
              />
              <span v-else>
                {{ data.registerAddrCode ? getAddres(data.registerAddrCode) : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="注册所在街道" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                placeholder="请输入注册所在街道"
                v-decorator="['registerAddress', {rules:[{required: false, message: '请输入注册所在街道'}]}]"
              />
              <span v-else>
                {{ data.registerAddress ? data.registerAddress : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item label="登记注册类型" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                placeholder="请输入登记注册类型"
                v-decorator="['registerType', {rules:[{required: false, message: '请输入登记注册类型'}]}]"
              />
              <span v-else>
                {{ data.registerType ? data.registerType : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="主营产品" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                placeholder="请输入主营产品"
                v-decorator="['mainProducts', {rules:[{required: false, message: '请输入主营产品'}]}]"
              />
              <span v-else>
                {{ data.mainProducts ? data.mainProducts : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item :labelCol="labelColT" :wrapperCol="wrapperColT">
              <span slot="label">
                合作组织数量&nbsp;
                <a-tooltip title="合作组织是指与企业开展实质性合作的产学研机构(企业、高等学校和科研院所)">
                  <a-icon type="question-circle-o" />
                </a-tooltip>
              </span>
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                placeholder="请输入合作组织数量"
                style="width:209px;"
                v-decorator="['cooperateOrg', {rules:[{required: false, message: '请输入合作组织数量'}]}]"
              />
              <span v-else>
                {{ data.cooperateOrg ? data.cooperateOrg : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="内设研发机构数 " :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                placeholder="内设研发机构数 "
                style="width:209px;"
                v-decorator="['insideRdOrg', {rules:[{required: false, message: '请输入内设研发机构数 '}]}]"
              />
              <span v-else>
                {{ data.insideRdOrg ? data.insideRdOrg : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item label="生产所在区" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-cascader
                v-if="isEdit"
                :options="addressCode"
                placeholder="请输入生产所在区"
                style="width: 100%;"
                v-decorator="['productAddrCode', {rules:[{required: false, message: '请输入生产所在区'}]}]"
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                changeOnSelect
              />
              <span v-else>
                {{ data.productAddrCode ? getAddres(data.productAddrCode) : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="办公所在区" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-cascader
                v-if="isEdit"
                :options="addressCode"
                placeholder="请输入办公所在区"
                style="width: 100%;"
                v-decorator="['officeAddrCode', {rules:[{required: false, message: '请输入办公所在区'}]}]"
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                changeOnSelect
              />
              <span v-else>
                {{ data.officeAddrCode ? getAddres(data.officeAddrCode) : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item label="生产用房面积" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="2"
                :step="1"
                placeholder="生产用房面积"
                style="width:209px;"
                v-decorator="['productArea', {rules:[{required: false, message: '请输入生产用房面积'}]}]"
              />
              <span v-else>
                {{ data.productArea ? data.productArea : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="办公用房面积" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="2"
                :step="1"
                placeholder="办公用房面积"
                style="width:209px;"
                v-decorator="['officeArea', {rules:[{required: false, message: '请输入办公用房面积'}]}]"
              />
              <span v-else>
                {{ data.officeArea ? data.officeArea : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="海外营销机构数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                placeholder="海外营销机构数"
                style="width:209px;"
                v-decorator="['overSeaMarketings', {rules:[{required: false, message: '请输入海外营销机构数'}]}]"
              />
              <span v-else>
                {{ data.overSeaMarketings ? data.overSeaMarketings : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="海外研发机构数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                placeholder="海外研发机构数"
                style="width:209px;"
                v-decorator="['overSeaRdOrg', {rules:[{required: false, message: '请输入海外研发机构数'}]}]"
              />
              <span v-else>
                {{ data.overSeaRdOrg ? data.overSeaRdOrg : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="全年用水量" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="2"
                :step="1"
                placeholder="全年用水量"
                style="width:209px;"
                v-decorator="['yearMwo', {rules:[{required: false, message: '请输入全年用水量'}]}]"
              />
              <span v-else>
                {{ data.yearMwo ? data.yearMwo : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="全年用电量" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="2"
                :step="1"
                placeholder="全年用电量"
                style="width:209px;"
                v-decorator="['yearKWh', {rules:[{required: false, message: '请输入全年用电量'}]}]"
              />
              <span v-else>
                {{ data.yearKWh ? data.yearKWh : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="24">
            <a-form-item
              label="单位资质"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol">
              <a-checkbox-group
                v-if="isEdit"
                v-decorator="['qualification']"
                :options="qualifications">
              </a-checkbox-group>
              <span v-else>
                {{ data.qualification ? data.qualification.join(' , ') : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="24">
            <a-form-item
              label="是否统计联网直报企业"
              :labelCol="{
                xs: { span: 24 },
                sm: { span: 7 }
              }"
              :wrapperCol="{
                xs: { span: 24 },
                sm: { span: 17 }
              }">
              <a-checkbox
                v-if="isEdit"
                v-decorator="['onlineReport']"
                @change="onlineReportChange"
                :checked="data.onlineReport">
              </a-checkbox>
              <span v-else>
                {{ data.onlineReport ? '是' : '否' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="24">
            <a-form-item
              label="拥有自营电子商务交易平台"
              :labelCol="{
                xs: { span: 24 },
                sm: { span: 7 }
              }"
              :wrapperCol="{
                xs: { span: 24 },
                sm: { span: 17 }
              }">
              <!-- <a-checkbox
                v-if="isEdit"
                @change="onChange"
                v-decorator="['ownerECP', {rules:[{required: true, message: '请输入拥有自营电子商务交易平台 '}]}]">
              </a-checkbox> -->
              <a-checkbox
                v-if="isEdit"
                @change="onChange"
                v-decorator="['ownerECP']"
                :checked="data.ownerECP">
              </a-checkbox>
              <span v-else>
                {{ data.ownerECP ? '是' : '否' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <template v-if="isOwnerECP">
          <a-row :gutter="8">
            <a-col :span="24">
              <a-form-item
                label="电子商务交易平台名称"
                :labelCol="{
                  xs: { span: 24 },
                  sm: { span: 7 }
                }"
                :wrapperCol="{
                  xs: { span: 24 },
                  sm: { span: 17 }
                }">
                <a-input
                  v-if="isEdit"
                  placeholder="电子商务交易平台名称"
                  v-decorator="['nameECP', {rules:[{required: true, message: '请输入电子商务交易平台名称'}]}]"
                />
                <span v-else>
                  {{ data.nameECP ? data.nameECP : '-' }}
                </span>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="8">
            <a-col :span="24">
              <a-form-item
                label="电子商务交易平台网址"
                :labelCol="{
                  xs: { span: 24 },
                  sm: { span: 7 }
                }"
                :wrapperCol="{
                  xs: { span: 24 },
                  sm: { span: 17 }
                }">
                <a-input
                  v-if="isEdit"
                  placeholder="电子商务交易平台网址"
                  v-decorator="['urlECP', {rules:[{required: true, message: '请输入电子商务交易平台网址'}]}]"
                />
                <span v-else>
                  {{ data.urlECP ? data.urlECP : '-' }}
                </span>
              </a-form-item>
            </a-col>
          </a-row>
        </template>
      </a-form>
      <div>
        <div v-if="isEdit" class="action-buttons">
          <a-button class="save-btn" type="primary" @click="saveBtn">保存</a-button>
          <a-button @click="cancelBtn">取消</a-button>
        </div>
      </div>
    </a-spin>
  </div>
</template>

<script>
import CustomTitle from './CustomTitle'
import { cloneDeep } from 'lodash'
export default {
  name: 'CustomerBase',
  components: {
    CustomTitle
  },
  props: {
    companyId: {
      type: Number,
      default: 0
    }
  },
  watch: {
    companyId (newValue) {
      if (newValue) {
        this.loadData(newValue)
        this.cancelBtn()
      }
    }
  },
  created () {
    this.loadData(this.companyId)
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
  },
  data () {
    return {
      form: this.$form.createForm(this),
      id: undefined,
      data: {},
      spinning: false,
      isEdit: false,
      isOwnerECP: false,
      addressCode: [],
      qualifications: [
        { value: '国家高新技术企业', label: '国家高新技术企业' },
        { value: '深圳市高新技术企业', label: '深圳市高新技术企业' },
        { value: '深圳市软件企业', label: '深圳市软件企业' },
        { value: '服务外包企业', label: '服务外包企业' },
        { value: '先进服务业', label: '先进服务业' }
      ],
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
  methods: {
    loadData (companyId) {
      this.spinning = true
      this.$http.get('/companyInfo/queryExtra', { params: { companyId } }).then((res) => {
        if (res.data && res.success) {
          this.data = res.data
          if (this.data.id) {
            this.id = this.data.id
          }
          const registerAddrCode = this.data['registerAddrCode'] // 注册地址
          if (registerAddrCode) {
            this.data['registerAddrCode'] = registerAddrCode.split('/')
          }
          const productAddrCode = this.data['productAddrCode'] // 生产地址
          if (productAddrCode) {
            this.data['productAddrCode'] = productAddrCode.split('/')
          }
          const officeAddrCode = this.data['officeAddrCode'] // 办公地址
          if (officeAddrCode) {
            this.data['officeAddrCode'] = officeAddrCode.split('/')
          }
          const qualification = this.data['qualification'] // 资质
          if (qualification) {
            this.data['qualification'] = qualification.split('/')
          }
          this.isOwnerECP = this.data['ownerECP']
          this.spinning = false
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch(() => {
        this.spinning = false
      })
    },
    saveBtn () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.spinning = true
          const postParams = cloneDeep(values)
          // console.log('values', postParams)
          if (this.id) {
            postParams['id'] = this.id
          }
          postParams['companyId'] = this.companyId
          // 转换地址数据格式
          const registerAddrCode = postParams['registerAddrCode'] // 注册地址
          if (registerAddrCode && Array.isArray(registerAddrCode)) {
            postParams['registerAddrCode'] = registerAddrCode.join('/')
          }
          const productAddrCode = postParams['productAddrCode'] // 生产地址
          if (productAddrCode && Array.isArray(productAddrCode)) {
            postParams['productAddrCode'] = productAddrCode.join('/')
          }
          const officeAddrCode = postParams['officeAddrCode'] // 办公地址
          if (officeAddrCode && Array.isArray(officeAddrCode)) {
            postParams['officeAddrCode'] = officeAddrCode.join('/')
          }
          const qualification = postParams['qualification'] // 资质
          if (qualification && Array.isArray(qualification)) {
            postParams['qualification'] = qualification.join('/')
          }
          this.$http.post('/companyInfo/editExtra', postParams).then((res) => {
            if (res.data && res.success) {
              this.id = res.data
              this.cancelBtn()
              Object.assign(this.data, values)
              this.$message.success('保存成功')
              this.spinning = false
            } else {
              this.$message.error(res.errorMessage)
              this.spinning = false
            }
          }).catch(() => {
            this.spinning = false
          })
        }
      })
    },
    cancelBtn () {
      this.$refs.title.switchEdit()
    },
    switchEdit (isEdit) {
      if (isEdit) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.data)
        })
      }
      this.isEdit = isEdit
    },
    onChange (e) {
      this.data.ownerECP = e.target.checked
      this.form.setFieldsValue({ ownerECP: e.target.checked })
      this.isOwnerECP = e.target.checked
    },
    getAddres (code) {
      if (!code) {
        return ''
      }
      const arr = code
      var currentIndex = 0
      var result = ''
      var tempChildren = this.addressCode
      while (currentIndex < arr.length) {
        for (let i = 0; i < tempChildren.length; i++) {
          if (arr[currentIndex] === tempChildren[i].key) {
            result += tempChildren[i].value + '/'
            if (tempChildren[i].children && tempChildren[i].children.length > 0) {
              tempChildren = tempChildren[i].children
            } else {
              tempChildren = []
            }
            break
          }
        }
        currentIndex++
      }
      return result ? result.substr(0, result.length - 1) : result
    },
    onlineReportChange (e) {
      this.data.onlineReport = e.target.checked
      this.form.setFieldsValue({ onlineReport: e.target.checked })
    }
  }
}
</script>

<style lang="less" scoped>
.action-buttons {
  padding: 12px 0;
  text-align: center;
}
.save-btn {
  margin-right: 40px;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
