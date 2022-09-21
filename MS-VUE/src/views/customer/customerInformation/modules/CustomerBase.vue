<!--
 * @Author: ldx
 * @Date: 2020-12-04 16:20:36
 * @LastEditTime: 2022-07-04 13:47:27
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\CustomerBase.vue
-->
<template>
  <div>
    <custom-title
      title="基本信息"
      @switchEdit="switchEdit"
      ref="title"
    ></custom-title>
    <a-spin
      :spinning="spinning"
      tip="加载中..."
    >
      <a-form :form="form">
        <a-row :gutter="[8]">
          <a-col :span="24">
            <a-form-item
              label="公司名称"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              {{ data.companyName }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="24">
            <a-form-item
              label="发票抬头"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入发票抬头"
                style="width: 100%"
                v-decorator="['invoiceTitle', {rules:[{required: true, message: '请输入发票抬头'}]}]"
              />
              <span v-else>
                {{ data.invoiceTitle ? data.invoiceTitle : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="24">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :style="{ marginBottom: isEdit ? 0 : 24+ 'px' }"
            >
              <template v-slot:label>
                <span :class="isEdit ? 'ant-form-item-required' : ''">单位地址</span>
              </template>
              <div v-if="isEdit">
                <a-form-item :style="{ display: 'inline-block', width: '30%' }">
                  <a-cascader
                    :options="addressCode"
                    placeholder="请选择所在地"
                    style="width: 100%;"
                    v-decorator="['addressCode', {rules:[{required: true, message: '请选择所在地'}]}]"
                    :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                    changeOnSelect
                  />
                </a-form-item>
                <a-form-item :style="{ display: 'inline-block', width: '70%' }">
                  <a-input
                    style="width: 100%;"
                    placeholder="请输入公司地址"
                    v-decorator="['companyAddress']"
                  />
                </a-form-item>
              </div>
              <span
                v-else
                style="width:100%"
              >
                {{ data.addressCode ? getAddres(data.addressCode) : '' }} - {{ data.companyAddress ? data.companyAddress : '' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item
              label="级别"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <!-- <a-select
                v-if="isEdit"
                v-decorator="['industryCode']"
                mode="multiple"
                :maxTagCount="1"
                placeholder="请选择所属行业"
                :allowClear="true"
                :options="industryOptions"
              >
              </a-select> -->
              <a-select
                v-if="isEdit"
                style="width: 230px"
                v-decorator="['companyLevel', {initialValue: data.companyLevel}]"
                placeholder="请选择公司级别"
              >
                <a-select-option value="A+">
                  A+
                </a-select-option>
                <a-select-option value="A">
                  A
                </a-select-option>
                <a-select-option value="B">
                  B
                </a-select-option>
                <a-select-option value="C">
                  C
                </a-select-option>
              </a-select>
              <span v-else>
                {{ data.companyLevel }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="状态"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-select
                v-if="isEdit"
                style="width: 100%"
                v-decorator="['status', { rules: [{ required: true, message: '请选择当前状态' }], initialValue: data.status }]"
              >
                <a-select-option v-for="(label, key) in typeData" :key="key" :value="key">{{ label }}</a-select-option>
              </a-select>
              <span v-else>
                {{ typeData[data.status] }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item
              label="所属行业"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <!-- <a-select
                v-if="isEdit"
                v-decorator="['industryCode']"
                mode="multiple"
                :maxTagCount="1"
                placeholder="请选择所属行业"
                :allowClear="true"
                :options="industryOptions"
              >
              </a-select> -->
              <a-tree-select
                v-if="isEdit"
                :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
                v-decorator="['industryCode']"
                multiple
                :maxTagCount="5"
                placeholder="请选择所属行业"
                :allowClear="true"
                treeDefaultExpandAll
                :treeData="industryOptions"
              >
              </a-tree-select>
              <span v-else>
                {{ getindustryName(data.industryCode) }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="主行业"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <!-- <a-select
                v-if="isEdit"
                v-decorator="['mainIndustry']"
                placeholder="请选择主行业"
                :allowClear="true"
                :options="industryOptions"
              >
              </a-select> -->
              <a-cascader
                v-if="isEdit"
                :fieldNames="{label:'title',value:'value',children:'children'}"
                :options="industryOptions"
                placeholder="请选择主行业"
                v-decorator="['mainIndustry', { rules:[{required: false, message: '请选择主行业'}], normalize: val => (typeof val === 'string'? val.split(',') : val ) }]"
              />
              <span v-else>
                {{ data.mainIndustry ? getindustryName(data.mainIndustry.substr(data.mainIndustry.lastIndexOf(','))) : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item
              label="联系人"
              :labelCol="{
                xs: { span: 24 },
                sm: { span: 10 }
              }"
              :wrapperCol="{
                xs: { span: 24 },
                sm: { span: 14 }
              }"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入联系人"
                v-decorator="['linkMan']"
              />
              <span v-else>
                {{ data.linkMan ? data.linkMan : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="联系电话"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入联系电话"
                v-decorator="['linkTel']"
              />
              <span v-else>
                {{ data.linkTel ? data.linkTel : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item
              label="所属部门"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <dept-select-only
                ref="deptSelect"
                v-if="isEdit"
                v-decorator="['deptId', {rules:[{required: true, message: '请选择所属部门'}]}]"
                @deptSelect="deptId=>this.deptId = deptId"
                @deptFullPath="deptFullPath"
                style="width:100%;"
              />
              <span v-else>
                {{ data.deptName ? data.deptName : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="业务员"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-select
                v-if="isEdit"
                showSearch
                v-decorator="['owerId', {rules:[{required: true, message: '请输入业务员'}]}]"
                placeholder="请输入业务员"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="getOwerList"
                @change="owerChange"
                @select="selOwer"
                :notFoundContent="null"
              >
                <a-select-option
                  v-for="d in owerList"
                  :key="d.id"
                  :value="`${d.id}`"
                >{{ `${d.realName}${d.userName? '('+d.userName+')': ''}` }}</a-select-option>
              </a-select>
              <span v-else>
                {{ data.ownerName ? data.ownerName : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item
              label="纳税识别号"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入纳税识别号"
                v-decorator="['taxpayerId']"
              />
              <span v-else>
                {{ data.taxpayerId ? data.taxpayerId : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="统一社会码"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入统一社会码"
                style="100%"
                v-decorator="['creditCode']"
              />
              <span v-else>
                {{ data.creditCode ? data.creditCode : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item
              label="法人"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入法人"
                style="width: 100%"
                v-decorator="['owner']"
              />
              <span v-else>
                {{ data.owner ? data.owner : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="注册资金"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
              :style="{ marginBottom: isEdit ? 0 : 24+ 'px' }"
            >
              <div v-if="isEdit">
                <a-form-item :style="{ display: 'inline-block', width: '50%' }">
                  <a-input-number
                    placeholder="资金"
                    :min="0.0001"
                    :step="0.0001"
                    style="width: 100%"
                    v-decorator="['capital']"
                  />
                </a-form-item>
                <a-form-item :style="{ display: 'inline-block', width: '50%' }">
                  <a-select
                    style="width:100%"
                    v-decorator="['capitalUnit']"
                  >
                    <a-select-option
                      v-for="item in capitalUnitData"
                      :key="item.key"
                    >
                      <span v-if="item!=null">万{{ item.value }}</span>
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </div>
              <span
                v-else
                style="width:100%"
              >
                {{ data.capital ? data.capital : '-' }} {{ data.capitalUnit ? capitalUnitDataKM[data.capitalUnit] : '' }}
              </span>
            </a-form-item>
            <!-- <a-form-item label="注册资金 " :labelCol="labelColT" :wrapperCol="wrapperColT">
              <div v-if="isEdit">
                <a-input-number
                  placeholder="资金"
                  :min="0.0001"
                  :step="0.0001"
                  style="width: 50%"
                  v-decorator="['capital']"
                />
                <a-select style="width:50%" v-model="capitalUnit" @change="capitalUnitChange">
                  <a-select-option v-for="item in capitalUnitData" :key="item.key">
                    <span v-if="item!=null">万{{ item.value }}</span>
                  </a-select-option>
                </a-select>
              </div>
              <span v-else>
                {{ data.capital ? data.capital : '-' }}
              </span>
            </a-form-item> -->
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item
              label="注册时间"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-date-picker
                v-if="isEdit"
                style="width: 100%"
                format="YYYY-MM-DD"
                v-decorator="['registerTime']"
              />
              <span v-else>
                {{ data.registerTime | DayFormat }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="电子邮箱"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入电子邮箱"
                style="width: 100%"
                v-decorator="['email']"
              />
              <span v-else>
                {{ data.email ? data.email : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item
              label="首次发生研发费用"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-date-picker
                v-if="isEdit"
                style="width: 100%"
                format="YYYY-MM-DD"
                v-decorator="['firstDevFee']"
              />
              <span v-else>
                {{ data.firstDevFee | DayFormat }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="会计制度"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入会计制度"
                style="width: 100%"
                v-decorator="['accountSystem']"
              />
              <span v-else>
                {{ data.accountSystem ? data.accountSystem : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item
              label="所属税务机关"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入所属税务机关"
                v-decorator="['taxAuthorities']"
              />
              <span v-else>
                {{ data.taxAuthorities ? data.taxAuthorities : '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="办理税务机关"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-input
                v-if="isEdit"
                placeholder="请输入办理税务机关"
                v-decorator="['realTaxAuthorities']"
              />
              <span v-else>
                {{ data.realTaxAuthorities ? data.realTaxAuthorities : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item
              label="研发费用专账"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-checkbox
                v-if="isEdit"
                @change="isHasChange"
                v-decorator="['hasDevAccount']"
                :checked="isHasfun"
              />
              <span v-else>
                {{ data.hasDevAccount ? '是' : '否' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="高新"
              :labelCol="labelColT"
              :wrapperCol="wrapperColT"
            >
              <a-checkbox
                v-if="isEdit"
                @change="isHightChange"
                v-decorator="['highTec']"
                :checked="isHightfun"
              />
              <span v-else>
                {{ data.highTec ? '是': '否' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <template v-if="isHightfun">
          <a-row :gutter="8">
            <a-col :span="24">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="高新领域"
              >
                <a-cascader
                  v-if="isEdit"
                  :fieldNames="{label:'value',value:'key',children:'children'}"
                  :options="highTecIndustry"
                  placeholder="请选择高新领域"
                  v-decorator="['highTecIndustry', { rules:[{required: false, message: '请选择高新领域'}], normalize: val => (typeof val === 'string'? val.split(',') : val ) }]"
                />
                <span v-else>
                  {{ getHighTechIndustry(data.highTecIndustry) }}
                </span>
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <a-row :gutter="8">
          <a-col
            :span="24"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-form-item
              label="备注"
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
            >
              <a-textarea
                v-if="isEdit"
                v-decorator="['remark']"
                :maxLength="200"
                :rows="3"
              ></a-textarea>
              <span v-else>
                {{ data.remark ? data.remark : '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <div>
        <div
          v-if="isEdit"
          class="action-buttons"
        >
          <a-button
            class="save-btn"
            type="primary"
            @click="saveBtn"
          >保存</a-button>
          <a-button @click="cancelBtn">取消</a-button>
        </div>
      </div>
    </a-spin>
  </div>
</template>

<script>
import { cloneDeep } from 'lodash'
import CustomTitle from './CustomTitle'
import DeptSelectOnly from '@/components/DeptSelectOnly'
import moment from 'moment'

function loadHighTecIndustryMap (preTitle, source, data) {
  source.forEach(item => {
    data[item.key] = preTitle ? preTitle + '/' + item.value : item.value
    if (item.children && item.children.length) {
      loadHighTecIndustryMap(data[item.key], item.children, data)
    }
  })
  return data
}
export default {
  name: 'CustomerBase',
  components: {
    CustomTitle,
    DeptSelectOnly
  },
  props: {
    companyId: {
      type: Number,
      default: 0
    }
  },
  watch: {
    companyId (newValue) {
      this.cancelBtn()
      this.loadData(newValue)
    }
  },
  created () {
    this.loadData(this.companyId)
    this.$getDictionary(5).then(res => {
      this.capitalUnitData = res
      for (const item of res) {
        this.capitalUnitDataKM[item.key] = item.value
      }
    })
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
    this.$getDictionary(2).then(d => {
      this.highTecIndustry = d
      this.highTecIndustryMap = loadHighTecIndustryMap('', this.highTecIndustry, {})
    })
    this.$getDictionary(15).then(d => {
      this.industryOptions = this.$deepClone(d).map(a => {
        a.selectable = false
        return a
      })
      this.transfromTreeData(this.industryOptions)
    })
  },
  data () {
    return {
      typeData: {
        0: '未签',
        10: '邀约',
        20: '拜访',
        30: '已签'
      },
      industryCodeData: {},
      highTecIndustryMap: {},
      industryOptions: [],
      highTecIndustry: [],
      form: this.$form.createForm(this),
      spinning: false,
      isEdit: false,
      customerId: undefined,
      data: {},
      record: {},
      isHightfun: false,
      isHasfun: false,
      owerList: [],
      deptName: '',
      deptId: undefined,
      capitalUnitData: [],
      capitalUnitDataKM: {},
      capitalUnit: undefined,
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
    getHighTechIndustry (v) {
      if (v) {
        const arr = v.split(',')
        return this.highTecIndustryMap[arr[arr.length - 1]]
      }
      return '-'
    },
    moment,
    loadData (companyId) {
      this.spinning = true
      this.$http.get('/companyInfo/queryBasicInfo', { params: { companyId } }).then((res) => {
        if (res.data && res.success) {
          this.data = res.data
          this.customerId = res.data.customerId
          this.isHightfun = res.data.highTec
          const addressCode = this.data['addressCode']
          if (addressCode) {
            this.data['addressCode'] = addressCode.split(',')
          }
          const industryCode = this.data['industryCode']
          if (industryCode) {
            this.data['industryCode'] = industryCode.split(',')
          }
          this.capitalUnit = res.data.capitalUnit
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
          const addressCode = postParams['addressCode'] // 注册地址
          if (addressCode && Array.isArray(addressCode)) {
            postParams['addressCode'] = addressCode.join(',')
          }
          const industryCode = postParams['industryCode'] // 注册地址
          if (industryCode && Array.isArray(industryCode)) {
            postParams['industryCode'] = industryCode.join(',')
          }
          postParams.deptId = this.deptId
          values.deptId = this.deptId
          values.deptName = this.deptName
          postParams['firstDevFee'] = moment(values.firstDevFee).format('YYYY-MM-DD HH:mm:ss')
          postParams['registerTime'] = moment(values.registerTime).format('YYYY-MM-DD HH:mm:ss')
          postParams['customerId'] = this.customerId
          postParams['companyId'] = this.companyId
          if (this.record['owerId']) {
            postParams['owerId'] = this.record['owerId']
            values['owerId'] = postParams['owerId']
            values['ownerName'] = this.record['ownerName']
          }
          if (Array.isArray(postParams.highTecIndustry)) {
            postParams.highTecIndustry = postParams.highTecIndustry.join(',')
            values['highTecIndustry'] = values.highTecIndustry.join(',')
          }
          if (Array.isArray(postParams.mainIndustry)) {
            postParams.mainIndustry = postParams.mainIndustry.join(',')
            values['mainIndustry'] = values.mainIndustry.join(',')
          }

          this.$http.post('/companyInfo/editBasicInfo', postParams).then((res) => {
            if (res.data && res.success) {
              this.cancelBtn()
              Object.assign(this.data, values)
              this.$message.success('保存成功')
              this.$emit('refresh')
              this.spinning = false
            } else {
              this.spinning = false
              this.$message.error(res.errorMessage)
            }
          }).catch(() => {
            this.spinning = false
          })
        }
      })
    },
    cancelBtn () {
      this.$refs.title.changeEdit(false)
    },
    switchEdit (isEdit) {
      if (isEdit) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.data)
          const object = {
            firstDevFee: this.data.firstDevFee ? moment(this.data.firstDevFee) : undefined,
            registerTime: this.data.registerTime ? moment(this.data.registerTime) : undefined
          }
          this.owerList = [{ realName: this.data.ownerName, id: this.data.owerId }]
          this.form.setFieldsValue(object)
          this.deptId = this.data.deptId
          this.deptName = this.data.deptName
          this.$refs.deptSelect.setDept(this.data.deptId)
        })
      }
      this.isEdit = isEdit
    },
    // 业务人员
    getOwerList (value) {
      if (value === '') {
        this.owerList = []
        return
      }
      return this.$http.get('/user/userForSelect', { params: { realName: value, fullPath: this.fullPath, hasDept: true } })
        .then(res => {
          this.owerList = res.data
          const map = {}
          res.data.forEach(item => {
            map[item.id] = item
          })
          this.owerMap = map
          return this.owerList
        })
    },
    owerChange (id, b) {
      let ownerName = ''
      if (id) {
        ownerName = b.componentOptions.children[0].text
      }
      this.record['owerId'] = id
      this.record['ownerName'] = ownerName
      this.$initForm(this.form, { ownerName: ownerName })
    },
    clearOwerList () {
      this.owerList = []
    },
    selOwer (value) {
      this.owerId = value
      if (this.deptId === undefined) {
        this.clearState = false
        this.$initForm(this.form, { deptId: this.owerMap[value].deptId })
        this.$refs.deptSelect.setDept(this.owerMap[value].deptId)
        // this.deptId = this.owerMap[value].deptId
        this.fullPath = this.owerMap[value].fullPath
      }
    },
    // TODO 这里要改一下
    // capitalUnitChange (value) {
    //   this.capitalUnit = value
    // },
    isHightChange (e) {
      this.isHightfun = e.target.checked
      this.data.highTec = e.target.checked
      this.form.setFieldsValue({ highTec: e.target.checked })
    },
    isHasChange (e) {
      this.isHasfun = e.target.checked
      this.data.hasDevAccount = e.target.checked
      this.form.setFieldsValue({ hasDevAccount: e.target.checked })
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
    getindustryName (value) {
      if (value) {
        const arr = Array.isArray(value) ? value : value.split(',')
        return arr.filter(a => a).map(a => this.industryCodeData[a]).join(',')
      }
      return '-'
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
        this.industryCodeData[item['key']] = item['title']
        if (item['children'] && item['children'].length) {
          this.transfromTreeData(item['children'])
        } else {
          delete item['children']
        }
      }
    },
    deptFullPath (path, name) {
      this.deptName = name
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
</style>
