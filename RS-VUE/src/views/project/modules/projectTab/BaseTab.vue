<template>
  <div>
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="项目名称"
              :labelCol="{ xs: { span: 24 }, sm: { span: 4 } }"
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }"
            >
              <a-input
                placeholder="请输入项目名称"
                v-decorator="[
                  'pname',
                  {
                    rules: [
                      { required: true, message: '请输入项目名称', whitespace: true },
                      { validator: this.checkPname }
                    ],
                    validateTrigger: ['blur', 'submit']
                  }
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12" v-if="!projectData.parentId > 0">
            <a-form-item label="RD" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-group compact>
                <span style="margin-top:5px">{{ year }}RD&nbsp;</span>
                <a-input-number
                  :min="1"
                  :precision="0"
                  style="width: 60px;"
                  v-decorator="[
                    'rdIndex',
                    {
                      rules: [{ required: true, message: '请输入RD编号' }, { validator: this.checkRD }],
                      validateTrigger: ['blur', 'submit']
                    }
                  ]"
                />
              </a-input-group>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="内部编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入内部编号"
                v-decorator="['rdNumber', { rules: [{ whitespace: true, message: '不能只输入空格' }] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="部门"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <!-- v-decorator="['deptId', {rules:[{required: true, message: '请选择部门'}]}]" -->
              <a-tree-select
                treeDefaultExpandAll
                showSearch
                treeNodeFilterProp="title"
                :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
                :tree-data="deptTree"
                :labelInValue="true"
                v-decorator="['deptId']"
                placeholder="请选择部门"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item
              label="原部门："
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              ref="fullName"
              v-if="!projectData.deptId && (projectData.deptName || projectData.workshop || projectData.productLine || projectData.processSection)"
            >
              <a-tooltip
                :getPopupContainer="tooltipDom"
              >
                <template slot="title">
                  {{ projectData.deptName ? `${projectData.deptName}` : '' }}{{ projectData.workshop && projectData.deptName ? '/' : '' }}{{
                    projectData.workshop ? `${projectData.workshop}` : '' }}{{ projectData.productLine && (projectData.deptName || projectData.workshop) ? '/' : '' }}{{
                    projectData.productLine ? `${projectData.productLine}` : '' }}{{ projectData.processSection && (projectData.deptName || projectData.workshop || projectData.productLine) ? '/' : '' }}{{
                    projectData.processSection ? `${projectData.processSection}` : '' }}
                </template>
                <span style="overflow: hidden; display: inline-block; text-overflow: ellipsis; white-space: nowrap; cursor: pointer; line-height: 20px; margin-top: 10px;max-width: 100%;">
                  {{ projectData.deptName ? `${projectData.deptName}` : '' }}{{ projectData.workshop && projectData.deptName ? '/' : '' }}{{
                    projectData.workshop ? `${projectData.workshop}` : '' }}{{ projectData.productLine && (projectData.deptName || projectData.workshop) ? '/' : '' }}{{
                    projectData.productLine ? `${projectData.productLine}` : '' }}{{ projectData.processSection && (projectData.deptName || projectData.workshop || projectData.productLine) ? '/' : '' }}{{
                    projectData.processSection ? `${projectData.processSection}` : '' }}
                </span>
              </a-tooltip>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="研发部门:" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-tree-select
                showSearch
                v-decorator="['rdDeptId', { rules: [{ required: true, whitespace: true, message: '请选择研发部门' }] }]"
                :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                :treeData="rdDeptTree"
                placeholder="请选择研发部门"
                treeNodeFilterProp="title"
                treeDefaultExpandAll
              ></a-tree-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="负责人">
              <a-select
                showSearch
                v-decorator="['masterENumber']"
                placeholder="输入名字搜索"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="handleSearch"
                @change="(v, event) => onHandleChange(event)"
                :notFoundContent="null"
              >
                <a-select-option v-for="d in employeeList" :key="d.enumber" :ename="d.ename">{{
                  d.ename + '(' + d.enumber + ')'
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :required="true" :wrapperCol="wrapperCol" style="margin-bottom:0;">
              <template slot="label">
                <span>起止日期</span>
              </template>
              <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabled="!isChangeYear"
                  @change="changeProjectYear"
                  :disabledDate="disabledBeginDate"
                  format="YYYY-MM-DD"
                  placeholder="开始日期"
                  v-decorator="['beginDate', { rules: [{ required: true, message: '请选择开始日期' }] }]"
                />
              </a-form-item>
              <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
                -
              </span>
              <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabled="!isChangeYear"
                  :disabledDate="disabledEndDate"
                  format="YYYY-MM-DD"
                  placeholder="结束日期"
                  v-decorator="['endDate', { rules: [{ required: true, message: '请选择结束日期' }] }]"
                />
              </a-form-item>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="产品" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入产品"
                v-decorator="['involvedProduct', { rules: [{ message: '请输入产品' }] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="试制">
              <a-radio-group
                @change="trialChange"
                v-decorator="['trialProd', { rules: [{ required: true, message: '请选择有无试制' }] }]"
              >
                <a-radio :value="true">
                  有
                </a-radio>
                <a-radio :value="false">
                  无
                </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :required="isTrial" style="margin-bottom:0;">
              <template slot="label">
                <span>试制日期</span>
              </template>
              <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabledDate="disabledTStartDate"
                  format="YYYY-MM-DD"
                  valueFormat="YYYY-MM-DD"
                  placeholder="开始日期"
                  v-decorator="[
                    'tBeginDate',
                    { rules: [{ required: isTrial, message: '请选择开始日期' }, { validator: this.checkTrialdate }] }
                  ]"
                  :disabled="!isTrial"
                /> </a-form-item
              ><span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
                -
              </span>
              <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabledDate="disabledTEndDate"
                  format="YYYY-MM-DD"
                  placeholder="结束日期"
                  v-decorator="[
                    'tEndDate',
                    { rules: [{ required: isTrial, message: '请选择结束日期' }, { validator: this.checkTrialdate }] }
                  ]"
                  :disabled="!isTrial"
                />
              </a-form-item>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="项目开展形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="[
                  'formula',
                  { normalize: normalizeZero, rules: [{ required: true, message: '请选择项目开展形式' }] }
                ]"
                placeholder="请选择项目开展形式"
              >
                <a-select-option value="10">10.自主完成</a-select-option>
                <a-select-option value="21">21.与境内研究机构合作</a-select-option>
                <a-select-option value="22">22.与境内高等学校合作</a-select-option>
                <a-select-option value="23">23.与境内其他企业或单位合作</a-select-option>
                <a-select-option value="24">24.与境外机构合作</a-select-option>
                <a-select-option value="30">30.委托其他企业或单位</a-select-option>
                <a-select-option value="40">40.其他形式</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="项目当年成果形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="[
                  'result',
                  { normalize: normalizeZero, rules: [{ required: true, message: '请选择项目当年成果形式' }] }
                ]"
                :dropdownStyle="{ width: '550px' }"
                placeholder="请选择项目当年成果形式"
              >
                <a-select-option value="01">01.论文或专著</a-select-option>
                <a-select-option value="02">02.新产品、新工艺等推广与示范活动</a-select-option>
                <a-select-option value="03">03.对已有产品、工艺等进行一般性改进</a-select-option>
                <a-select-option value="04">04.对已有产品、工艺等实现突破性变革</a-select-option>
                <a-select-option value="05">05.软件著作权</a-select-option>
                <a-select-option value="06">06.应用软件</a-select-option>
                <a-select-option value="07">07.中间件或新算法</a-select-option>
                <a-select-option value="08">08.基础软件</a-select-option>
                <a-select-option value="09">09.发明专利</a-select-option>
                <a-select-option value="10">10.实用新型专利或外观设计专利</a-select-option>
                <a-select-option
                  value="11"
                >11.带有技术、工艺参数的图纸、技术标准、操作规范、技术论证、研究报告、咨询评价</a-select-option
                >
                <a-select-option value="12">12.自主研制的新产品原型或样机、样件、样品、配方、新装置</a-select-option>
                <a-select-option value="13">13.自主开发的新技术或新工艺、新工法、新服务</a-select-option>
                <a-select-option value="14">14.其他</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="项目技术经济目标" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="[
                  'targets',
                  { normalize: normalizeZero, rules: [{ required: true, message: '请选择项目技术经济目标' }] }
                ]"
                placeholder="请选择项目技术经济目标"
              >
                <a-select-option value="1">1.科学原理的探索、发现</a-select-option>
                <a-select-option value="2">2.技术原理的研究</a-select-option>
                <a-select-option value="3">3.开发全新产品</a-select-option>
                <a-select-option value="4">4.增加产品功能或提高性能</a-select-option>
                <a-select-option value="5">5.提高劳动生产率</a-select-option>
                <a-select-option value="6">6.减少能源消耗或提高能源使用效率</a-select-option>
                <a-select-option value="7">7.节约原材料</a-select-option>
                <a-select-option value="8">8.减少环境污染</a-select-option>
                <a-select-option value="9">9.其他</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="项目来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="[
                  'projectSource',
                  { normalize: normalizeZero, rules: [{ required: true, message: '请选择项目来源' }] }
                ]"
                placeholder="请选择项目来源"
              >
                <a-select-option value="1">1.本企业自选项目</a-select-option>
                <a-select-option value="2">2.政府部门科技项目</a-select-option>
                <a-select-option value="3">3.其他企业（单位）委托项目</a-select-option>
                <a-select-option value="4">4.境外项目</a-select-option>
                <a-select-option value="5">5.其他项目</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              :labelCol="{ xs: { span: 24 }, sm: { span: 4 } }"
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }"
              label="高新领域"
            >
              <a-cascader
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                :options="tecIndustryLevel"
                placeholder="请选择高新领域"
                v-decorator="[
                  'tecIndustry',
                  {
                    rules: [{ required: true, message: '请选择高新领域' }],
                    normalize: val => (typeof val === 'string' ? val.split(',') : val)
                  }
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <span v-if="modifyStatus">
          <a-button
            v-if="$auth('project:report:base:save') && (projectData.beginYear !== currentYear || modify)"
            type="primary"
            @click="handleSubmit()"
            style="margin-right:15px"
          >保存</a-button
          >
          <a-checkbox
            @change="chekcBoxChange"
            v-if="!(!$auth('project:report:base:updateYear') && !projectData.hasChild && projectData.parentId === 0)"
          >调整项目周期</a-checkbox
          >
        </span>
      </a-form>
    </a-spin>
  </div>
</template>

<script>
import { DeptSelect } from '@/components'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import { zeroFormat } from '@/utils/util'
export default {
  mixins: [yearMiXin],
  name: 'BaseTab',
  components: {
    DeptSelect
  },
  props: {
    projectData: {
      type: Object,
      required: true
    },
    modify: {
      type: Boolean,
      required: true
    },
    modifyStatus: {
      type: Boolean,
      required: true
    },
    deptTree: {
      type: Array,
      required: true
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
        sm: { span: 14 }
      },
      id: 0,
      rdDeptTree: [],
      tecIndustryLevel: [], // 高新领域
      confirmLoading: false,
      form: this.$form.createForm(this),
      employeeList: [], // 负责人
      year: 2019,
      isTrial: false, // 是否试制
      firstDay: undefined,
      lastDay: undefined,
      ename: undefined,
      isChangeYear: false
    }
  },
  created () {
    this.ename = undefined
    this.year = this.projectData.beginYear
    this.firstDay = moment(`${this.year}-01-01`)
    this.lastDay = moment(`${this.year}-12-31`)
    this.isTrial = this.projectData.trialProd
    this.$highTecIndustryTree(this).then(res => {
      this.tecIndustryLevel = res
    })
    this.loadRdDept()
    if (this.projectData.masterENumber && this.projectData.ename) {
      this.employeeList = this.projectData.masterENumber
        ? [{ ename: this.projectData.ename, enumber: this.projectData.masterENumber }]
        : []
    } else {
      this.projectData.masterENumber = undefined
    }
    this.form.resetFields()
    this.$nextTick(() => {
      // 项目预算
      const estimateExpense = this.projectData.estimateExpense
        ? this.projectData.estimateExpense / 10000
        : this.projectData.estimateExpense
      const deptId = this.projectData.deptId ? { value: this.projectData.deptId, label: this.projectData.deptName } : null
      // 初始化表单数据
      this.$initForm(this.form, { ...this.projectData, estimateExpense, deptId }, [
        'beginDate',
        'endDate',
        'tBeginDate',
        'tEndDate'
      ])
      // 规划部门提示
      // if (!(this.deptTree && this.deptTree.length !== 0)) {
      //   this.form.setFields({ 'deptId': { errors: [new Error('未规划部门,请先规划当前年部门')] } })
      // }
    })
  },
  methods: {
    moment,
    zeroFormat,
    chekcBoxChange (e) {
      this.isChangeYear = e.target.checked
    },
    trialChange (e) {
      this.isTrial = e.target.value
    },
    disabledBeginDate (v) {
      const endDate = this.form.getFieldValue('endDate')
      const end = endDate && endDate < this.lastDay ? endDate : this.lastDay
      if (this.isChangeYear && !this.projectData.hasChild && this.projectData.parentId === 0) {
        return v > endDate
      }
      return v < this.firstDay || v > end
    },
    changeProjectYear (e) {
      const changeYear = moment(e).year()
      if (changeYear !== this.year) {
        this.year = changeYear
      }
    },
    disabledEndDate (v) {
      const beginDate = this.form.getFieldValue('beginDate')
      const start = beginDate || this.firstDay
      return v < start
    },
    disabledTStartDate (v) {
      const startDay = this.form.getFieldValue('beginDate')
      const endDay = this.form.getFieldValue('endDate')
      if (startDay && endDay) {
        const tEndDate = this.form.getFieldValue('tEndDate')
        const end = tEndDate && tEndDate < endDay ? tEndDate : endDay
        return v < startDay || v > end
      } else {
        return true
      }
    },
    disabledTEndDate (v) {
      const startDay = this.form.getFieldValue('beginDate')
      const endDay = this.form.getFieldValue('endDate')
      if (startDay && endDay) {
        const tBeginDate = this.form.getFieldValue('tBeginDate')
        const start = tBeginDate || startDay
        return v < start || v > endDay
      } else {
        return true
      }
    },
    checkPname (rule, value, callback) {
      if (value.trim() !== '') {
        this.$http
          .get('/project/checkPname', { params: { pname: value, projectId: this.projectData.id } })
          .then(res => {
            if (!res.data) {
              callback(new Error('已存在相同项目名称，请重新输入'))
            } else {
              callback()
            }
            return res.data
          })
      } else {
        callback()
      }
    },
    checkTrialdate (rule, value, callback) {
      const startDay = this.form.getFieldValue('beginDate')
      const endDay = this.form.getFieldValue('endDate')
      if (value && value > startDay && value > endDay) {
        callback(new Error('不在项目期间之内'))
      } else {
        callback()
      }
    },
    checkRD (rule, value, callback) {
      if (value) {
        this.$http
          .get('/project/checkRD', { params: { rdIndex: value, year: this.year, projectId: this.projectData.id } })
          .then(res => {
            if (!res.data) {
              callback(new Error('该年份已存在相同RD编号,请重新输入'))
            } else {
              callback()
            }
            return res.data
          })
      } else {
        callback()
      }
    },
    loadRdDept () {
      return this.$getTree('rdDept', false, this.year).then(res => {
        this.rdDeptTree = res.tree
        return this.rdDeptTree
      })
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const estimateExpense = values.estimateExpense ? values.estimateExpense * 10000 : values.estimateExpense
          const tecIndustry = values.tecIndustry ? values.tecIndustry.join(',') : values.tecIndustry
          values.deptName = values.deptId ? values.deptId.label : null
          values.deptId = values.deptId ? values.deptId.value : 0
          const postData = {
            ...values,
            estimateExpense,
            tecIndustry,
            id: this.projectData.id,
            currentYear: this.currentYear
          }
          if (this.ename) {
            postData.ename = this.ename
          }
          this.$http
            .post('/project/update', postData)
            .then(res => {
              if (res.success && res.data) {
                if (this.isChangeYear) {
                  const year = this.year
                  const rdIndex = this.zeroFormat(postData.rdIndex)
                  const newRdTitle = `${year}RD${rdIndex}`
                  postData['rdTitle'] = newRdTitle
                  postData['beginYear'] = this.year
                }
                this.$emit('updated', postData)
                this.$message.success('保存成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
            })
            .finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleSearch (value) {
      this.$http.get('/project/getEmployeeList', { params: { ename: value, year: this.currentYear } }).then(res => {
        this.employeeList = res.data
      })
    },
    normalizeZero (v) {
      return v === '0' || v === 0 ? undefined : v
    },
    // 负责人改变事件
    onHandleChange (event) {
      this.ename = event.data.attrs.ename
    },
    refresh () {
      this.$http
        .get('/project/getMaster', { params: { projectId: this.projectData.id, year: this.currentYear } })
        .then(res => {
          if (res.success) {
            if (res.data) {
              const { ename, enumber } = res.data
              this.employeeList = [{ ename: ename, enumber: enumber }]
              this.form.setFieldsValue({ masterENumber: enumber })
            } else {
              this.form.setFieldsValue({ masterENumber: null })
              this.employeeList = []
            }
          }
        })
    },
    tooltipDom () {
      return this.$refs.fullName.$el
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}

</style>
<style>

</style>
