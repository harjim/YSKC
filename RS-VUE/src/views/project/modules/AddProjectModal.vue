<template>
  <a-modal
    :title="title"
    :width="1000"
    :maskClosable="false"
    v-model="visible"
    @ok="handleSubmit"
    centered
  >
    <a-form :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="项目名称"
              :labelCol="{ xs: { span: 24 }, sm: { span: 4 } }"
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 20 } }"
            >
              <a-input
                placeholder="请输入项目名称"
                v-decorator="['pname', { rules:[{required: true, whitespace: true, message: '请输入项目名称'}, { validator: this.checkPname }], validateTrigger: ['blur'] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="RD" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-group compact>
                <span style="margin-top:5px">{{ year }}RD&nbsp;</span>
                <a-input-number
                  :min="1"
                  style="width: calc(100% - 52px);"
                  placeholder="请输入RD编号"
                  v-decorator="['rdIndex', { rules:[{required: true, message: '请输入RD编号'}, { validator: this.checkRD }], validateTrigger:['blur'] }]"
                />
              </a-input-group>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="内部编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入内部编号" v-decorator="['rdNumber', { rules: [{whitespace: true, message: '不能只输入空格'}] }]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!-- v-decorator="['deptId', {rules:[{required: true, message: '请选择部门'}]}]" -->
              <a-tree-select
                treeDefaultExpandAll
                showSearch
                treeNodeFilterProp="title"
                :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
                :tree-data="deptTree"
                v-decorator="['deptId']"
                :labelInValue="true"
                placeholder="请选择部门"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="研发部门:" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-tree-select
                showSearch
                v-decorator="['rdDeptId', { rules: [{ required: true, whitespace: true, message: '请选择研发部门'}] }]"
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
                v-decorator="['ename']"
                placeholder="输入名字搜索"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="handleSearch"
                @change="handleChange"
                :notFoundContent="null"
              >
                <a-select-option
                  v-for="d in employeeList"
                  :key="d.enumber"
                >{{ d.ename + '(' + d.enumber + ')' }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="起止日期" :required="true" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom:0;">
              <a-form-item :style="{display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabledDate="disabledStartDate"
                  format="YYYY-MM-DD"
                  placeholder="开始日期"
                  v-decorator="['beginDate', {rules:[{required: true, message: '请选择开始日期'}]}]"
                  :defaultPickerValue="limitFirstDate"
                />
              </a-form-item>
              <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
                -
              </span>
              <a-form-item :style="{display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabledDate="disabledEndDate"
                  format="YYYY-MM-DD"
                  placeholder="结束日期"
                  v-decorator="['endDate', { rules: [{required: true,message:'请输入结束日期'}] }]"/>
              </a-form-item>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="产品" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入产品" v-decorator="['involvedProduct', { rules: [{ message: '请输入产品'}] }]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="试制">
              <a-radio-group @change="trialChange" v-decorator="['trialProd', {rules:[{required: true, message: '请选择有无试制'}]}]">
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
            <a-form-item label="试制日期" :required="isTrial" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0;">
              <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabled="!isTrial"
                  :disabledDate="disabledTStartDate"
                  format="YYYY-MM-DD"
                  placeholder="开始日期"
                  v-decorator="['tBeginDate', {rules: [{required: isTrial, message: '请选择开始日期'},{ validator: this.checkTrialdate }] }]"
                />
              </a-form-item>
              <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
                -
              </span>
              <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabled="!isTrial"
                  :disabledDate="disabledTEndDate"
                  format="YYYY-MM-DD"
                  placeholder="结束日期"
                  v-decorator="['tEndDate', { rules: [{required: isTrial, message:'请选择结束日期'},{ validator: this.checkTrialdate }] }]"/>
              </a-form-item>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="项目开展形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                placeholder="请选择项目开展形式"
                v-decorator="['formula', {rules:[{required: true, message: '请选择项目开展形式'}]}]"
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
                v-decorator="['result',{ rules:[{required: true, message: '请选择项目当年成果形式'}] }]"
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
                <a-select-option value="11">11.带有技术、工艺参数的图纸、技术标准、操作规范、技术论证、研究报告、咨询评价</a-select-option>
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
              <a-select v-decorator="['targets',{rules:[{required: true, message: '请选择项目技术经济目标'}]}]" placeholder="请选择项目技术经济目标">
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
              <a-select v-decorator="['projectSource',{rules:[{required: true, message: '请选择项目来源'}]}]" placeholder="请输入项目来源">
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
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 20 } }"
              label="高新领域"
            >
              <a-cascader
                :fieldNames="{label:'value',value:'key',children:'children'}"
                :options="tecIndustryLevel"
                placeholder="请选择"
                v-decorator="['tecIndustry',{rules:[{required: true, message: '请选择高新领域'}]} ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import { DeptSelect } from '@/components'
import moment from 'moment'
import { mapGetters } from 'vuex'
export default {
  name: 'AddProjectModal',
  components: {
    DeptSelect
  },
  data () {
    return {
      year: 0,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      id: 0,
      rdDeptTree: [],
      enumber: '',
      tecIndustryLevel: [],
      form: this.$form.createForm(this),
      employeeList: [],
      visible: false,
      isTrial: false,
      limitFirstDate: undefined,
      limitLastDate: undefined,
      deptTree: []
    }
  },
  props: {
  },
  watch: {
    year () {
      this.loadRdDept()
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  methods: {
    moment,
    trialChange (e) {
      this.isTrial = e.target.value
    },
    // 项目起始日期限制
    disabledStartDate (currentDate) {
      const endDate = this.form.getFieldValue('endDate')
      const end = endDate && endDate < this.limitLastDate ? endDate : this.limitLastDate
      return currentDate < this.limitFirstDate || currentDate > end
    },
    // 项目结束日期限制
    disabledEndDate (currentDate) {
      const beginDate = this.form.getFieldValue('beginDate')
      const begin = beginDate || this.limitFirstDate
      return currentDate < begin
    },
    // 试制起始日期限制
    disabledTStartDate (currentDate) {
      const startDay = this.form.getFieldValue('beginDate')
      const endDay = this.form.getFieldValue('endDate')
      if (startDay && endDay) {
        const tEndDate = this.form.getFieldValue('tEndDate')
        const end = tEndDate && tEndDate < endDay ? tEndDate : endDay
        return currentDate < startDay || currentDate > end
      } else {
        return true
      }
    },
    // 试制结束日期限制
    disabledTEndDate (currentDate) {
      const startDay = this.form.getFieldValue('beginDate')
      const endDay = this.form.getFieldValue('endDate')
      if (startDay && endDay) {
        const tBeginDate = this.form.getFieldValue('tBeginDate')
        const start = tBeginDate || startDay
        return currentDate < start || currentDate > endDay
      } else {
        return true
      }
    },
    // 验证项目名称
    checkPname (rule, value, callback) {
      if (value && value.trim() !== '') {
        this.$http.get('/project/checkPname', { params: { pname: value } }).then(res => {
          if (!res.data) {
            callback(new Error('已存在相同项目名称，请重新输入'))
          } else {
            callback()
          }
        })
      } else {
        callback()
      }
    },
    // 验证项目RD
    checkRD (rule, value, callback) {
      if (value) {
        this.$http.get('/project/checkRD', { params: { rdIndex: value, year: this.year } }).then(res => {
          if (!res.data) {
            callback(new Error('该年份存在相同RD,请重新输入'))
          } else {
            callback()
          }
        })
      } else {
        callback()
      }
    },
    loadRdDept () {
      return this.$getTree('rdDept')
        .then(res => {
          this.rdDeptTree = res.tree
          return this.rdDeptTree
        })
    },
    add (year, deptTree) {
      this.form.resetFields()
      this.deptTree = deptTree
      this.year = year
      this.title = year + '年添加项目'
      this.visible = true
      this.id = 0
      this.enumber = ''
      this.endDate = null
      this.tBeginDate = null
      this.tEndDate = null
      this.isTrial = false
      this.limitFirstDate = this.moment(year + '-01-01')
      this.limitLastDate = this.moment(year + '-12-31')
      // this.loadTree()
      this.$highTecIndustryTree(this).then(res => {
        this.tecIndustryLevel = res // 高新领域
      })
      this.$http.get('/project/selectMaxRd', { params: { year: this.year } }).then(res => {
        const fieldObj = {
          formula: '10', // 默认项目开展形式
          rdIndex: 1
        }
        if (res.data) {
          fieldObj.rdIndex = res.data
        }
        this.$nextTick(() => {
          this.$initForm(this.form, fieldObj)
          // 规划部门提示
          // if (!(this.deptTree && this.deptTree.length !== 0)) {
          //   this.form.setFields({ 'deptId': { errors: [new Error('未规划部门,请先规划当前年部门')] } })
          // }
        })
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          values.year = this.year
          if (values.tecIndustry !== null && values.tecIndustry !== undefined) {
            values.tecIndustry = values.tecIndustry.toString()
          }
          values.id = this.id
          values.masterENumber = this.enumber
          if (values.estimateExpense !== null) {
            values.estimateExpense = values.estimateExpense * 10000
          }
          values.deptName = values.deptId ? values.deptId.label : null
          values.deptId = values.deptId ? values.deptId.value : 0
          values.currentYear = this.currentYear
          this.$http.post('/project/addProject', values)
            .then(res => {
              if (res.success && res.data) {
                this.$message.success('保存成功')
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
            })
        }
      })
    },
    handleSearch (value) {
      this.$http.get('/project/getEmployeeList', { params: { ename: value, year: this.currentYear } })
        .then(res => {
          this.employeeList = res.data
        })
    },
    handleChange (value) {
      this.enumber = value
    },
    // 验证试制日期
    checkTrialdate (rule, value, callback) {
      const startDay = this.form.getFieldValue('beginDate')
      const endDay = this.form.getFieldValue('endDate')
      if (value && value > startDay && value > endDay) {
        callback(new Error('不在项目期间之内'))
      } else {
        callback()
      }
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
