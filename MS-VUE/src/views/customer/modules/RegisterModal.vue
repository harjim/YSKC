<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-tabs @change="callback" :activeKey="selectKey">
        <a-tab-pane tab="基本信息" key="1">
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="公司名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入公司名称"
                  style="width: 560px"
                  :disabled="true"
                  v-decorator="['companyName', {rules:[{required: true, message: '请输入公司名称'}]}]"
                  @change="(e)=>companyNameChange(e.target.value)"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="发票抬头" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入发票抬头"
                  style="width: 560px"
                  v-decorator="['invoiceTitle', {rules:[{required: true, message: '请输入发票抬头'}]}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="所在地" :labelCol="labelCol" :wrapperCol="wrapperCol" :width="300">
                <a-cascader
                  style="width: 230px"
                  v-decorator="['addressCodeArr', {rules:[{required: true, message: '请选择所在地'}]}]"
                  :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                  :options="addressCode"
                  placeholder="请选择所在地"
                  changeOnSelect
                />
              </a-form-item>
            </a-col>
            <a-col :md="10" :sm="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入公司地址"
                  style="width: 315px;margin-left:30px"
                  v-decorator="['companyAddress']"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="所属行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  v-decorator="['industryCodeArr']"
                  style="width:230px;float:left"
                  mode="multiple"
                  :maxTagCount="1"
                  placeholder="请选择所属行业"
                  :allowClear="true"
                >
                  <a-select-option value="A">农、林、牧、渔业</a-select-option>
                  <a-select-option value="B">采矿业</a-select-option>
                  <a-select-option value="C">制造业</a-select-option>
                  <a-select-option value="D">电力、热力、燃气及水的生产和供应业</a-select-option>
                  <a-select-option value="E">建筑业</a-select-option>
                  <a-select-option value="F">批发和零售业</a-select-option>
                  <a-select-option value="G">交通运输、仓储和邮政业</a-select-option>
                  <a-select-option value="H">住宿和餐饮业</a-select-option>
                  <a-select-option value="I">信息传输、软件和信息技术服务业</a-select-option>
                  <a-select-option value="J">金融业</a-select-option>
                  <a-select-option value="K">房地产业</a-select-option>
                  <a-select-option value="L">租赁和商务服务业</a-select-option>
                  <a-select-option value="M">科学研究和技术服务业</a-select-option>
                  <a-select-option value="N">水利、环境和公共设施管理业</a-select-option>
                  <a-select-option value="O">居民服务、修理和其他服务业</a-select-option>
                  <a-select-option value="P">教育</a-select-option>
                  <a-select-option value="Q">卫生和社会工作</a-select-option>
                  <a-select-option value="R">文化、体育和娱乐业</a-select-option>
                  <a-select-option value="S">公共管理、社会保障和社会组织</a-select-option>
                  <a-select-option value="T">国际组织</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="主行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  v-decorator="['mainIndustry']"
                  style="width:200px;float:left"
                  placeholder="请选择主行业"
                  :allowClear="true"
                >
                  <a-select-option value="A">农、林、牧、渔业</a-select-option>
                  <a-select-option value="B">采矿业</a-select-option>
                  <a-select-option value="C">制造业</a-select-option>
                  <a-select-option value="D">电力、热力、燃气及水的生产和供应业</a-select-option>
                  <a-select-option value="E">建筑业</a-select-option>
                  <a-select-option value="F">批发和零售业</a-select-option>
                  <a-select-option value="G">交通运输、仓储和邮政业</a-select-option>
                  <a-select-option value="H">住宿和餐饮业</a-select-option>
                  <a-select-option value="I">信息传输、软件和信息技术服务业</a-select-option>
                  <a-select-option value="J">金融业</a-select-option>
                  <a-select-option value="K">房地产业</a-select-option>
                  <a-select-option value="L">租赁和商务服务业</a-select-option>
                  <a-select-option value="M">科学研究和技术服务业</a-select-option>
                  <a-select-option value="N">水利、环境和公共设施管理业</a-select-option>
                  <a-select-option value="O">居民服务、修理和其他服务业</a-select-option>
                  <a-select-option value="P">教育</a-select-option>
                  <a-select-option value="Q">卫生和社会工作</a-select-option>
                  <a-select-option value="R">文化、体育和娱乐业</a-select-option>
                  <a-select-option value="S">公共管理、社会保障和社会组织</a-select-option>
                  <a-select-option value="T">国际组织</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入联系人"
                  style="width: 230px"
                  v-decorator="['linkMan']"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入联系电话"
                  style="width: 200px"
                  v-decorator="['linkTel']"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="审核" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-radio-group
                  v-decorator="['status', {rules:[{required: true, message: '请选择审核结果'}]}]"
                  @change="(v,row)=>status = v.target.value"
                >
                  <a-radio value="1">通过</a-radio>
                  <a-radio value="2">不通过</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48" v-if="this.status==='1'">
            <a-col :md="10" :sm="24">
              <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <dept-select-only
                  ref="deptSelect"
                  v-decorator="['deptId', {rules:[{required: true, message: '请选择所属部门'}]}]"
                  @deptSelect="deptId=>this.deptId = deptId"
                  @deptFullPath="path=>this.fullPath = path"
                  style="width:230px;"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="业务员" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  showSearch
                  v-decorator="['owerName', {rules:[{required: true, message: '请输入业务员'}]}]"
                  placeholder="请输入业务员"
                  style="width: 100%"
                  :defaultActiveFirstOption="false"
                  :showArrow="false"
                  :filterOption="false"
                  @search="getOwerList"
                  @change="selOwer"
                  @blur="clearOwerList"
                  :notFoundContent="null"
                >
                  <a-select-option
                    v-for="d in owerList"
                    :key="d.id"
                  >{{ d.realName + '('+d.userName+')' }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
        </a-tab-pane>
        <a-tab-pane tab="其他信息" key="2">
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="纳税识别号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入纳税识别号"
                  style="width: 200px"
                  v-decorator="['taxpayerId']"
                  @change="(e)=>taxpayerIdChange(e.target.value)"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label=" 统一社会码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input placeholder="请输入统一社会码" style="width: 200px" v-decorator="['creditCode']" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input placeholder="请输入法人" style="width: 200px" v-decorator="['owner']" />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="注册资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <div>
                  <a-input-number
                    placeholder="资金"
                    :min="0.0001"
                    :step="0.0001"
                    style="width: 100px"
                    v-decorator="['capital']"
                  />
                  <a-select style="width:100px" v-model="capitalUnit" @change="capitalUnitChange">
                    <a-select-option v-for="item in capitalUnitData" :key="item.key">
                      <span v-if="item!=null">万{{ item.value }}</span>
                    </a-select-option>
                  </a-select>
                </div>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="注册时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-date-picker
                  style="width: 200px"
                  format="YYYY-MM-DD"
                  v-decorator="['registerTime']"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="电子邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input placeholder="请输入电子邮箱" style="width: 200px" v-decorator="['email']" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="首次发生研发费用" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-date-picker
                  style="width: 200px"
                  format="YYYY-MM-DD"
                  v-decorator="['firstDevFee']"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="会计制度" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入会计制度"
                  style="width: 200px"
                  v-decorator="['accountSystem']"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="所属税务机关" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入所属税务机关"
                  style="width: 200px"
                  v-decorator="['taxAuthorities']"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="办理税务机关" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  placeholder="请输入办理税务机关"
                  style="width: 200px"
                  v-decorator="['realTaxAuthorities']"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="研发费用专账" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-checkbox
                  @change="isHasChange"
                  v-decorator="['hasDevAccount']"
                  :checked="isHasfun"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="高新" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-checkbox
                  @change="isHightChange"
                  v-decorator="['highTec']"
                  :checked="isHightfun"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item
                v-show="isHightfun"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="高新领域"
              >
                <a-tree-select
                  showSearch
                  v-decorator="['highTecIndustry']"
                  style="width: 200px"
                  :treeData="highTecIndustry"
                  :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                  placeholder="请选择高新领域"
                  allowClear
                >
                  <a-icon slot="suffixIcon" type="smile" />
                </a-tree-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="24">
              <a-form-item
                label="备注"
                :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
                :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
                :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
              >
                <a-textarea v-decorator="['remark']" max-length="200" :rows="3"></a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
        </a-tab-pane>
      </a-tabs>
    </a-form>
  </a-modal>
</template>

<script>
import moment from 'moment'
import highTecIndustry from '@/assets/tecIndustry'
import MiniSelect from '@/components/MiniSelect'
import DeptSelectOnly from '@/components/DeptSelectOnly'

export default {
  name: 'RegisterModal',
  components: {
    MiniSelect,
    DeptSelectOnly
  },
  deptTree: [],
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
      capitalUnitData: [],
      capitalUnit: undefined,
      selectKey: '1',
      highTecIndustry,
      addressCode: {},
      isHas: false,
      isHight: false,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 10 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      projectData: [],
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      count: 1,
      owerId: 0,
      owerList: [],
      owerMap: {},
      deptId: undefined,
      from: -1,
      status: 0,
      clearState: false,
      submitState: false
    }
  },
  created () {
    this.$getDictionary(5).then(res => {
      this.capitalUnitData = res
    })
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
  },
  watch: {
    deptId (v) {
      this.$initForm(this.form, { deptId: v })
      if (this.clearState) {
        this.$initForm(this.form, { owerName: '' })
      } else {
        this.clearState = true
      }
    }
  },
  methods: {
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
    getOwerList (value) {
      if (value === '') {
        this.owerList = []
        return
      }
      return this.$http.get('/user/userForSelect', { params: { realName: value, fullPath: this.fullPath } })
        .then(res => {
          if (res.data.length === 0) {
            this.$initForm(this.form, { owerName: '' })
          }
          this.owerList = res.data
          for (let index = 0; index < res.data.length; index++) {
            const element = res.data[index]
            this.$set(this.owerMap, element.id, element)
          }
          return this.owerList
        })
    },
    capitalUnitChange (value) {
      this.capitalUnit = value
    },
    callback (key) {
      this.selectKey = key
    },
    moment,
    companyNameChange (value) {
      const { form: { setFieldsValue } } = this
      setFieldsValue({ invoiceTitle: value })
    },
    taxpayerIdChange (value) {
      const { form: { setFieldsValue } } = this
      setFieldsValue({ creditCode: value })
    },
    onDateChange (dateString, record) {
      record.beginDate = dateString[0]
      record.endDate = dateString[1]
    },
    onCellChange (value, record, name) {
      record[name] = value
    },
    handleDel (index) {
      this.projectData.splice(index, 1)
    },
    handleAdd () {
      const { count } = this
      const newData = {
        key: count,
        rdIndex: count,
        pname: '',
        beginDate: moment(),
        endDate: moment()
      }
      this.$set(this.projectData, this.projectData.length, newData)

      this.count = count + 1
    },
    isHasChange (e) {
      this.isHas = e.target.checked
    },
    isHightChange (e) {
      this.isHight = e.target.checked
    },
    edit (record) {
      this.title = '客户审核'
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.formData = record
      this.isHight = this.formData.highTec
      this.isHas = this.formData.hasDevAccount
      this.projectData = record.projectData
      this.from = record.from
      this.status = 0
      this.capitalUnit = 'rmb'
      this.clearState = false
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['registerTime', 'firstDevFee'])
        if (record.status === 0) {
          this.$initForm(this.form, { status: null })
        }
        if (record.capitalUnit !== null && record.capitalUnit !== undefined) {
          this.capitalUnit = record.capitalUnit
        }
      })
      this.checkCustomerName(record.companyName, record.id)
    },
    checkCustomerName (value, companyId) {
      return this.$http.get('/customer/checkCompanyName', { params: { companyName: value } })
        .then(res => {
          if (!res.data) {
            this.form.setFields({ 'companyName': { value: value, errors: [new Error('客户名称已存在。')] } })
            this.submitState = true
            this.$http.post('/company/updateCompany', { id: companyId })
              .then(res => {
                if (res.success && res.data) {
                  this.customerId = res.data
                  this.$emit('ok', 3)
                } else {
                  this.$message.info(res.errorMessage)
                }
              })
          } else {
            this.form.setFields({ 'companyName': { value: value } })
            this.submitState = false
          }
        })
    },
    handleSubmit () {
      if (this.submitState) {
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        this.selectKey = '1'
        if (!errors) {
          values.id = this.id
          values.capitalUnit = this.capitalUnit
          values.from = this.from
          values.owerId = this.owerId
          this.$http.post('/company/save', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', res.data)
              } else {
                this.$message.info(res.errorMessage)
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>
