<!--
 * @Author: ldx
 * @Date: 2020-10-16 08:59:27
 * @LastEditTime: 2021-03-16 18:08:04
 * @LastEditors: hck
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\modules\RelevanceDeclareModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="950"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-spin :spinning="spinning">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="地区"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-cascader
                :changeOnSelect="true"
                placeholder="请选择地区"
                @change="cascaderChange"
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                :options="addressCode"
                :allowClear="false"
                v-decorator="['addressCode',{rules:[{required: true, message: '请选择地区'}]}]"
                :disabled="hasUsed"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="申报年份" :labelCol="labelCol" :wrapperCol="wrapperCol" >
              <a-select
                placeholder="请选择申报年份"
                :allowClear="false"
                @change="declareYearChange"
                v-decorator="['declareYear',{rules:[{required: true, message: '请选择申报年份'}]}]"
                :disabled="hasUsed"
              >
                <a-select-option v-for="y in years" :key="y">{{ y }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="申报项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['pname', {rules:[{required: true, message: '请选择申报项目名称'}]}]"
                :options="declareOptions"
                @change="declareSelectChange"
                placeholder="申报项目名称"
                :disabled="hasUsed"
              ></a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                placeholder="请选择年份"
                :allowClear="true"
                v-decorator="['pyear', {rules:[{required: true, message: '请选择年份'}]}]"
              >
                <a-select-option v-for="y in years" :key="y">{{ y }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="!row.beian">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="申报方向">
              <a-select
                v-decorator="['reportType', {rules:[{required: true, message: '请输入申报方向'}]}]"
                :options="directionOptions"
                placeholder="请输入申报方向"
                :disabled="hasUsed"
              ></a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="扶持方式">
              <a-input
                v-decorator="['aidType', {rules:[{required: false, whitespace: true, message: '不允许填写空格'}]}]"
                placeholder="请输入扶持方式"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="!row.beian">
          <a-col :span="12">
            <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['masterName', {rules:[{required: false,whitespace: true, message: '不允许填写空格'}]}]"
                placeholder="请输入负责人"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="负责人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['masterTel', {rules:[{required: false,whitespace: true, message: '不允许填写空格'}]}]"
                placeholder="请输入负责人电话"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="!row.beian">
          <a-col :span="12">
            <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['linkName', {rules:[{required: false, whitespace: true, message: '不允许填写空格'}]}]"
                placeholder="请输入联系人"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="联系人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['linkTel', {rules:[{required: false,whitespace: true, message: '不允许填写空格'}]}]"
                placeholder="请输入联系人电话"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="!row.beian">
          <a-col :span="12">
            <a-form-item :required="true" :labelCol="labelCol" :wrapperCol="wrapperCol" label="项目起止日期" style="margin-bottom: 0;">
              <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabledDate="disabledBeginDate"
                  format="YYYY-MM-DD"
                  placeholder="开始日期"
                  v-decorator="['beginDate', {rules:[{required: true, message: '请选择开始日期'}]}]"
                />
              </a-form-item>
              <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
                -
              </span>
              <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                <a-date-picker
                  :disabledDate="disabledEndDate"
                  format="YYYY-MM-DD"
                  placeholder="结束日期"
                  v-decorator="['endDate', {rules:[{required: true, message: '请选择结束日期'}]}]"
                />
              </a-form-item>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="申请日期">
              <a-date-picker
                format="YYYY-MM-DD"
                v-decorator="['applyDate',{rules:[{required: true, message: '请选择申请日期'}]}]"
                placeholder="请选择申请日期"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="备注"
              :labelCol=" {xs: { span: 24 },sm: { span: 3 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
              :extra="onComputeTextarea('remark',200)"
            >
              <a-textarea v-decorator="['remark']" :rows="3"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
import { YearSelect, SearchSelect } from '@/components/Selects'
import moment from 'moment'
const startYear = (new Date()).getFullYear() - 4
const endYear = (new Date()).getFullYear() + 6
const years = []
for (let y = startYear; y < endYear; y++) {
  years.unshift(y)
}
export default {
  name: 'RelevanceDeclareModal',
  components: {
    YearSelect,
    SearchSelect
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
    this.$http.get('/tProject/getProductList').then((res) => {
      if (res.data && res.success) {
        for (const declare of res.data) {
          this.declareMap[declare.id] = declare
        }
      } else {
        this.$message.error(res.errorMessage)
      }
    }).catch((error) => {
      this.$message.error(error.message)
    })
  },
  data () {
    return {
      isVisible: false,
      form: this.$form.createForm(this),
      years,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      declareMap: {},
      declareOptions: [], // 申报项目选项
      directionOptions: [], // 方向选项
      directionMap: {},
      currentDirection: undefined, // 当前选中的方向
      spinning: false,
      row: {},
      hasUsed: false,
      title: '',
      addressCode: []
    }
  },
  methods: {
    moment,
    show (row) {
      this.title = '关联申报项目[' + row.companyName + ']'
      this.hasUsed = false
      this.isVisible = true
      this.row = row
      this.declareOptions = []
      this.form.resetFields()
      if (row.tProjectId) {
        this.getProject(row.tProjectId)
      }
    },
    afterClose () {},
    /**
     * @description: 获取关联数据,并且初始化表单数据
     * @param {*} tProjectId
     * @return {*} null
     */
    getProject (tProjectId) {
      this.$http.get('/tProject/getProject', { params: { projectId: tProjectId, beian: this.row.beian } }).then((res) => {
        if (res.data && res.success) {
          const obj = res.data
          this.declareOptions.push({ value: obj.productId, label: obj.productName })
          if (this.row.beian) {
            this.$nextTick(() => {
              const fieldObj = {
                pname: obj.productId,
                pyear: obj.pyear,
                remark: obj.remark,
                addressCode: obj.addressCode.split('/'),
                declareYear: obj.reportYear
              }
              this.form.setFieldsValue(fieldObj)
            })
            return
          }
          this.hasUsed = obj.hasUsed
          this.getDirections(obj.productId)
          let addressCode
          if (obj.addressCode.includes('/')) {
            addressCode = obj.addressCode.split('/')
          } else {
            addressCode = []
            addressCode.push(obj.addressCode)
          }
          const fieldObj = {
            pname: obj.productId,
            pyear: obj.pyear,
            reportType: obj.directionId, // 方向
            aidType: obj.aidType, // 扶持方向
            masterName: obj.masterName,
            masterTel: obj.masterTel,
            linkName: obj.linkName,
            linkTel: obj.linkTel,
            beginDate: moment(obj.beginDate),
            endDate: moment(obj.endDate),
            applyDate: moment(obj.applyDate),
            remark: obj.remark,
            addressCode,
            declareYear: obj.reportYear
          }
          this.$nextTick(() => {
            this.form.setFieldsValue(fieldObj)
          })
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '获取项目失败，请稍后重试。')
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    /**
     * @description: 提交表单
     * @param {*} null
     * @return {*} null
     */
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = true
          delete values.addressCode
          if (this.row.beian) {
            this.saveBeianProject(values)
          } else {
            this.saveTechProject(values)
          }
        }
      })
    },
    saveBeianProject (values) {
      const paramsData = {
        sourceProjectId: this.row.id,
        companyId: this.row.companyId,
        productId: values.pname,
        projectId: this.row.tProjectId,
        pyear: values.pyear,
        remark: values.remark
      }
      Object.assign(paramsData, values)
      this.$http.post('/tProject/saveBeian', paramsData).then((res) => {
        if (res.data && res.success) {
          this.$message.success('保存成功')
          this.isVisible = false
          this.$emit('ok', false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
        }
        this.spinning = false
      }).catch((error) => {
        this.spinning = false
        this.$message.error(error.message)
      })
    },
    saveTechProject (values) {
      const paramsData = {
        sourceProjectId: undefined,
        productId: undefined,
        directionId: undefined,
        productName: undefined,
        direction: undefined,
        companyId: undefined
      }
      delete values.declareYear
      Object.assign(paramsData, values)
      paramsData.sourceProjectId = this.row.id
      paramsData.companyId = this.row.companyId
      paramsData.productId = values.pname
      if (!this.directionMap[values.reportType]) {
        this.$message.error('此处表单不支持自定义方向！')
        this.spinning = false
        return
      }
      paramsData.directionId = values.reportType
      paramsData.direction = this.directionMap[values.reportType].direction
      this.$http.post('/tProject/relatedProject', paramsData).then((res) => {
        if (res.data && res.success) {
          this.spinning = false
          this.$message.success('保存成功')
          this.isVisible = false
          this.$emit('ok', false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          this.spinning = false
        }
      }).catch((error) => {
        this.spinning = false
        this.$message.error(error.message)
      })
    },
    disabledBeginDate (currentValue) {
      const endDate = this.form.getFieldValue('endDate')
      return endDate ? currentValue > endDate : false
    },
    disabledEndDate (currentValue) {
      const beginDate = this.form.getFieldValue('beginDate')
      return beginDate ? currentValue < beginDate : false
    },
    /**
     * @description: 申报项目名称改变事件回调函数
     * @param {*} value
     * @param {*} option
     * @return {*} null
     */
    declareSelectChange (value, option) {
      if (!this.row.beian && (!this.currentDirection || this.currentDirection !== value.id)) {
        this.spinning = true
        this.getDirections(value)
        this.currentDirection = value
      }
      this.form.setFieldsValue({ reportType: undefined })
    },
    /**
     * @description: 获取方向列表
     * @param {*} productId
     * @return {*} null
     */
    getDirections (productId) {
      this.$http.get('/rsProduct/getDirection', { params: { productId } }).then((res) => {
        if (res.data && res.success) {
          this.directionOptions = []
          if (res.data.length === 0) {
            this.$message.warning('该申报项目，没填写方向，请添加方向。')
            this.spinning = false
            return
          }
          for (const direction of res.data) {
            this.directionOptions.push({ label: direction.direction, value: direction.id })
            this.directionMap[direction.id] = direction
          }
          this.spinning = false
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinning = false
      })
    },
    /**
     * @description: 获取申报项目列表
     * @param {*} addressCode 地址
     * @param {*} declareYear 申报年份
     * @return {*}
     */
    getProductList (addressCode, declareYear) {
      this.$http.get('/tProject/getProductList', { params: { addressCode, year: declareYear } }).then((res) => {
        if (res.data && res.success) {
          this.declareOptions = []
          for (const item of res.data) {
            this.declareOptions.push({ label: item.productName, value: item.id })
          }
          this.form.setFieldsValue({ pname: undefined, reportType: undefined })
          this.spinning = false
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinning = false
      })
    },
    /**
     * @description: 计算与控制文本域的个数
     * @param fieldName { type: String }
     * @param limitNumber {type:String }
     * @return String { type: String }
     */
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    },
    /**
     * @description: 地区改变时触发的回调函数
     * @param {*} value
     * @param {*} selectedOption
     * @return {*}
     */
    cascaderChange (value, selectedOption) {
      const declareYear = this.form.getFieldValue('declareYear')
      if (declareYear) {
        this.getProductList(value.join('/'), declareYear)
      }
    },
    /**
     * @description: 申报年份改变时触发的回调函数
     * @param {*} value
     * @return {*}
     */
    declareYearChange (value) {
      const addressCode = this.form.getFieldValue('addressCode')
      if (addressCode) {
        this.getProductList(addressCode.join('/'), value)
      }
    }
  }
}
</script>
<style lang='less' scoped>
</style>
