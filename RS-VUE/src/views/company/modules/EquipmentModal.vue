<template>
  <a-modal
    :width="850"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    centered
  >
    <a-form :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="资产代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                :disabled="id!==-1"
                v-decorator="['ecode', {rules:[{required: true, message: '请输入资产代码'}]}]"
                @blur="checkEcode"
                placeholder="请输入资产代码"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['ename', {rules:[{required: true, message: '请输入设备名称'}]}]"
                placeholder="请输入设备名称"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门">
              <!-- <a-input
                v-decorator="['deptName', {rules:[{required: true, message: '请输入部门'}]}]"
                placeholder="请输入部门"
              /> -->
              <a-tree-select
                treeDefaultExpandAll
                showSearch
                treeNodeFilterProp="title"
                :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
                :tree-data="deptTree"
                v-decorator="['deptId', {rules:[{required: true, message: '请选择部门'}]}]"
                :labelInValue="true"
                placeholder="请选择部门"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <!-- <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="车间">
              <a-input
                v-decorator="['workshop', {rules:[{required: false, message: '请输入车间'}]}]"
                placeholder="请输入车间"
              />
            </a-form-item> -->
            <!-- <span v-if="equipment && !equipment.deptId" style="line-height: 40px; margin-left: 36px;">
              原部门：{{ equipment.deptName ? `${equipment.deptName}` : '' }}{{ equipment.workshop && equipment.deptName ? '/' : '' }}{{
                equipment.workshop ? `${equipment.workshop}` : '' }}{{ equipment.productLine && (equipment.deptName || equipment.workshop) ? '/' : '' }}{{
                equipment.productLine ? `${equipment.productLine}` : '' }}{{ equipment.processSection && (equipment.deptName || equipment.workshop || equipment.productLine) ? '/' : '' }}{{
                equipment.processSection ? `${equipment.processSection}` : '' }}
            </span> -->
            <a-form-item
              label="原部门："
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              v-if="equipment && !equipment.deptId && (equipment.deptName || equipment.workshop || equipment.productLine || equipment.processSection)"
            >
              <a-tooltip
                :align="{offset: [0, 10]}"
              >
                <template slot="title">
                  {{ equipment.deptName ? `${equipment.deptName}` : '' }}{{ equipment.workshop && equipment.deptName ? '/' : '' }}{{
                    equipment.workshop ? `${equipment.workshop}` : '' }}{{ equipment.productLine && (equipment.deptName || equipment.workshop) ? '/' : '' }}{{
                    equipment.productLine ? `${equipment.productLine}` : '' }}{{ equipment.processSection && (equipment.deptName || equipment.workshop || equipment.productLine) ? '/' : '' }}{{
                    equipment.processSection ? `${equipment.processSection}` : '' }}
                </template>
                <span style="overflow: hidden; display: inline-block; text-overflow: ellipsis; max-width: 86%;vertical-align: bottom; cursor: pointer;">
                  {{ equipment.deptName ? `${equipment.deptName}` : '' }}{{ equipment.workshop && equipment.deptName ? '/' : '' }}{{
                    equipment.workshop ? `${equipment.workshop}` : '' }}{{ equipment.productLine && (equipment.deptName || equipment.workshop) ? '/' : '' }}{{
                    equipment.productLine ? `${equipment.productLine}` : '' }}{{ equipment.processSection && (equipment.deptName || equipment.workshop || equipment.productLine) ? '/' : '' }}{{
                    equipment.processSection ? `${equipment.processSection}` : '' }}
                </span>
              </a-tooltip>
            </a-form-item>
          </a-col>
        </a-row>
        <!-- <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="产线">
              <a-input
                v-decorator="['productLine', {rules:[{required: false, message: '请输入产线'}]}]"
                placeholder="请输入产线"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="工艺段">
              <a-input
                v-decorator="['processSection', {rules:[{required: false, message: '请输入工艺段'}]}]"
                placeholder="请输入工艺段"
              />
            </a-form-item>
          </a-col>
        </a-row> -->
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="设备型号">
              <a-input
                v-decorator="['emodal', {rules:[{required: false, message: '请输入设备型号'}]}]"
                placeholder="请输入设备型号"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="设备类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                v-decorator="['etype', {rules:[{required: false, message: '请选择设备类型'}],initialValue: '30000'}]"
                placeholder="请选择设备类型"
              >
                <a-select-option v-for="item in $getEnums('equipmentEnum')" :key="item.value" :value="`${item.value}`">
                  {{ item.label }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="原值">
              <a-input-number
                :min="1"
                :precision="2"
                v-decorator="['unitPrice', {rules:[{required: true, message: '请输入原值'}]}]"
                placeholder="请输入原值"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="数量">
              <a-input-number
                :min="1"
                :precision="2"
                v-decorator="['quantity', {rules:[{required: false, message: '请输入数量'}]}]"
                placeholder="请输入数量"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="功率(kWh)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="0"
                :precision="2"
                v-decorator="['usagePower', {rules:[{required: false, message: '请输入功率'}]}]"
                placeholder="请输入功率"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['unit', {rules:[{required: false, message: '请输入单位'}]}]"
                placeholder="请输入单位"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="使用年限" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                :min="1"
                :precision="0"
                v-decorator="['usefullife', {rules:[{required: false, message: '请输入使用年限'}]}]"
                placeholder="请输入使用年限"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="开始使用日期">
              <a-date-picker
                format="YYYY-MM-DD"
                @change="changePurchaseDate"
                v-decorator="['purchaseDate',{rules:[{required: false, message: '请选择开始使用日期'}, { validator: handleDateCheck }]}]"
                placeholder="请选择开始使用日期"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="计提折旧日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                format="YYYY-MM-DD"
                :disabledDate="disabledDate"
                @change="changeDepreciationDate"
                v-decorator="['depreciationDate',{rules:[{required: false, message: '请选择计提折旧日期'}]}]"
                placeholder="请选择计提折旧日期"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="月折旧率">
              <a-input-number
                :min="0"
                :max="1"
                :precision="4"
                v-decorator="['depreciationRate', {rules:[{required: false}]}]"
                placeholder="请输入月折旧率"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="停用报废日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                format="YYYY-MM-DD"
                :disabledDate="disabledDate"
                v-decorator="['invalidated',{rules:[{required: false, message: '请选择停用报废日期'}]}]"
                placeholder="请选择停用报废日期"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="类别">
              <a-input v-decorator="['kinds', {rules:[{required: false}]}]" placeholder="请输入类别" />
            </a-form-item>
          </a-col>
          <a-col :span="12" v-for="obj in equipmentArr" :key="obj.name">
            <a-form-item :label="obj.alias" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[`${obj.name}`]" :placeholder="`请输入${obj.alias}`"/>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="备注"
              :labelCol=" {xs: { span: 24 },sm: { span: 3 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 } }"
              :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 300 ? 300 : r.length}/300)`}"
            >
              <a-textarea v-decorator="['remark']" :max-length="300" :rows="3"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import { SelectDown } from '@/components'
import moment from 'moment'
import { mapState } from 'vuex'

export default {
  components: {
    SelectDown
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: -1,
      checked: true,
      deptTree: [],
      purchaseDate: undefined,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      equipment: null
    }
  },
  props: {
    equipmentArr: {
      type: Array,
      default: () => []
    }
  },
  mounted () {
    this.loadTree()
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
    checkEcode () {
      const ecode = this.form.getFieldValue('ecode')
      if (ecode) {
        this.$http.get('/equipment/checkEcode', { params: { ecode: ecode, id: this.id } })
          .then(res => {
            if (!res.data) {
              this.checked = false
              this.form.setFields({ 'ecode': { value: ecode, errors: [new Error('资产代码已被使用，请输入新资产代码。')] } })
            } else {
              this.checked = true
              this.form.setFields({ 'ecode': { value: ecode } })
            }
          })
      }
    },
    moment,
    disabledDate (current) {
      return !current || current < moment(this.purchaseDate)
    },
    add () {
      this.checked = true
      this.purchaseDate = undefined
      this.title = '添加设备'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
      this.equipment = null
    },
    edit (record) {
      this.checked = true
      this.purchaseDate = record.purchaseDate
      this.title = `编辑设备[${record.ename}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.deptName = record.deptName
      this.equipment = record
      // record.etype = +record.etype
      console.log(1)
      const deptId = record.deptId ? typeof record.deptId !== 'object' ? { value: record.deptId, label: record.deptName } : record.deptId : null
      Object.assign(record, JSON.parse(record.data), { deptId })
      this.$nextTick(() => {
        const dateArr = []
        if (record.purchaseDate) {
          dateArr.push('purchaseDate')
        }
        if (record.depreciationDate) {
          dateArr.push('depreciationDate')
        }
        if (record.invalidated) {
          dateArr.push('invalidated')
        }
        if (dateArr.length) {
          this.$initForm(this.form, record, dateArr)
        } else {
          this.$initForm(this.form, record)
        }
      })
    },
    handleSubmit () {
      if (!this.checked) {
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          const data = {}
          this.equipmentArr.forEach(elem => { data[elem.name] = values[elem.name] })
          values.deptName = values.deptId.label
          values.deptId = values.deptId.value
          values.data = JSON.stringify(data)
          if (values.id === -1) {
            this.$http.post('/equipment/addEquipment', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$message.success('添加成功')
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/equipment/updateEquipment', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$message.success('更新成功')
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    changePurchaseDate (date, dateStr) {
      this.purchaseDate = dateStr
    },
    changeDepreciationDate (value) {
      if (this.purchaseDate) {
        this.form.setFields({ 'purchaseDate': { value: moment(this.purchaseDate) } })
      }
    },
    handleDateCheck (rule, value, callback) {
      const depreciationDate = this.form.getFieldValue('depreciationDate')
      if (depreciationDate === null) {
        callback()
      }
      if (moment(value) > moment(depreciationDate)) {
        callback(new Error('开始使用日期应当小于计提折旧日期'))
      }
      callback()
    },
    loadTree () {
      this.$getTree('dept')
        .then(res => {
          this.deptTree = res.tree
        })
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
