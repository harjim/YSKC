<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
  >
    <a-form :form="form">
      <a-tabs @change="k=>selectKey = k" :activeKey="selectKey">
        <a-tab-pane tab="基本信息" key="1">
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="公司名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
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
                  :options="addressCode"
                  :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
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
                <a-select v-decorator="['mainIndustry']" style="width:200px;float:left">
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
                <a-input style="width: 230px" v-decorator="['linkMan']" />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input style="width: 200px" v-decorator="['linkTel']" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-tab-pane>
        <a-tab-pane tab="其他信息" key="2">
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="纳税识别号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  style="width: 200px"
                  v-decorator="['taxpayerId']"
                  @change="(e)=>taxpayerIdChange(e.target.value)"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label=" 统一社会码" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input style="width: 200px" v-decorator="['creditCode']" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input style="width: 200px" v-decorator="['owner']" />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="注册资金" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <div>
                  <a-input-number
                    :min="0.0001"
                    :step="0.0001"
                    style="width: 100px"
                    v-decorator="['capital']"
                  />
                  <a-select style="width:100px" v-model="capitalUnit" @change="v=>capitalUnit = v">
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
                <a-input style="width: 200px" v-decorator="['email']" />
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
                <a-input style="width: 200px" v-decorator="['accountSystem']" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="所属税务机关" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input style="width: 200px" v-decorator="['taxAuthorities']" />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="办理税务机关" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input style="width: 200px" v-decorator="['realTaxAuthorities']" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="10" :sm="24">
              <a-form-item label="研发费用专账" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-checkbox
                  @change="e=>isHas = e.target.checked"
                  v-decorator="['hasDevAccount']"
                  :checked="isHasfun"
                />
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24">
              <a-form-item label="高新" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-checkbox
                  @change="e=>isHight = e.target.checked"
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
                <a-textarea v-decorator="['remark']" maxlength="200" :rows="3"></a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
        </a-tab-pane>
      </a-tabs>
    </a-form>
  </a-modal>
</template>

<script>

import highTecIndustry from '@/assets/tecIndustry'
export default {
  data () {
    return {
      highTecIndustry,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 10 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      selectKey: '1',
      capitalUnitData: [],
      capitalUnit: undefined,
      visible: false,
      title: '',
      confirmLoading: false,
      form: this.$form.createForm(this),
      id: -1,
      isHas: false,
      isHight: false,
      addressCode: {}
    }
  },
  computed: {
    isHightfun: function () {
      return this.isHight
    },
    isHasfun: function () {
      return this.isHas
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
  methods: {
    add () {
      this.form.resetFields()
      this.title = '添加客户'
      this.visible = true
      this.id = -1
    },
    handleSubmit () {
    }
  }
}
</script>

<style>
</style>
