<template>
  <a-modal
    :width="850"
    :visible="visible"
    :maskClosable="false"
    title="项目信息"
    @cancel="handleCancel"
    @ok="handleSubmit"
    :afterClose="afterClose"
    :confirmLoading="confirmLoading"
  >
    <div>
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="客户名称" :labelCol="{span: 4}" :wrapperCol="{span: 20}">
              <search-select
                url="/customer/customerForSelect"
                searchField="customerName"
                sTitle="companyName"
                placeholder="客户名称"
                @change="customerChange"
                :readonly="!isNew"
                v-decorator="['customer', {rules:[{required: true,message: '请输入客户名称'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="所属地" :labelCol="{span: 8}" :wrapperCol="{span: 16}">
              <a-cascader
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                :options="addressCode"
                placeholder="所属地"
                :disabled="true"
                changeOnSelect
                @change="addressChange"
                v-decorator="['addressCode', {normalize: val => (typeof val === 'string'? val.split(',') : val)}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="所属部门" :labelCol="{span: 8}" :wrapperCol="{span: 16}">
              <dept-select
                :readonly="!isNew"
                v-decorator="['deptId', {rules:[{required: true, message: '请输入所属部门'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="项目年份" :labelCol="{span: 8}" :wrapperCol="{span: 16}">
              <year-select
                placeholder="项目年份"
                :readonly="!isNew"
                @change="yearChange"
                v-decorator="['year', {rules:[{required: true, message: '请选择项目年份'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="项目类型" :labelCol="{span: 8}" :wrapperCol="{span: 16}">
              <product-select
                :filterOption="productOption"
                :readonly="!isNew"
                v-decorator="['productId', {rules:[{required: true, message: '请选择项目类型'}]}]"
              ></product-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="业务员" :labelCol="{span: 8}" :wrapperCol="{span: 16}">
              <search-select
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                placeholder="业务员"
                :readonly="!isNew && !$auth('project:projectList:edit')"
                v-decorator="['ower',{rules:[{required: isNew || $auth('project:projectList:edit'), message: '请输入业务员'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="谈单经理" :labelCol="{span: 8}" :wrapperCol="{span: 16}">
              <search-select
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                placeholder="谈单经理"
                :readonly="!isNew && !$auth('project:projectList:edit')"
                v-decorator="['business']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="技术负责人" :labelCol="{span: 8}" :wrapperCol="{span: 16}">
              <search-select
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="true"
                placeholder="技术负责人员"
                :readonly="!isNew && !$auth('project:projectList:tech') && !$auth('project:projectList:edit')"
                v-decorator="['techs', {rules:[{required: isNew || $auth('project:projectList:tech') || $auth('project:projectList:edit'), message: '请输入技术负责人'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="财务负责人" :labelCol="{span: 8}" :wrapperCol="{span: 16}">
              <search-select
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="true"
                placeholder="财务负责人员"
                :readonly="!isNew && !$auth('project:projectList:fina') && !$auth('project:projectList:edit')"
                v-decorator="['finances', {rules:[{required: isNew || $auth('project:projectList:fina') || $auth('project:projectList:edit'), message: '请输入财务负责人'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <template slot="footer">
      <a-button type="primary" @click="handleCancel">取消</a-button>
      <a-button type="primary" @click="handleSubmit" v-if="isNew || $auth('project:projectList:edit') || $auth('project:projectList:fina') || $auth('project:projectList:tech')">确定</a-button>
    </template>
  </a-modal>
</template>

<script>
import { SearchSelect, YearSelect, DeptSelect, ProductSelect } from '@/components/Selects'
import DateRange from '@/components/DateRange'
import moment from 'moment'
export default {
  components: {
    DeptSelect,
    DateRange,
    SearchSelect,
    YearSelect,
    ProductSelect
  },
  data () {
    return {
      projectId: undefined,
      addressCode: {},
      productOption: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,
      visible: false,
      form: this.$form.createForm(this),
      productType: -1,
      products: []
    }
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
    this.$getProductList()
      .then(res => {
        this.products = res
      })
  },
  computed: {
    isNew () {
      return typeof this.projectId === 'undefined'
    }
  },
  methods: {
    moment,
    open (projectId) {
      this.visible = true
      if (projectId) {
        this.projectId = projectId
        this.$nextTick(() => {
          this.$http.get('/project/detail', { params: { projectId: projectId } })
            .then(res => {
              if (res.success && res.data) {
                const memberMap = {}
                res.data.members.reduce((mmap, m) => {
                  if (typeof mmap[m.mType] === 'undefined') {
                    mmap[m.mType] = []
                  }
                  mmap[m.mType].push({ 'id': m.memberId, 'realName': m.realName, 'userName': m.userName })
                  return mmap
                }, memberMap)
                this.productType = res.data.productType
                const fieldObj = {
                  customer: { id: res.data.customerId, companyName: res.data.companyName },
                  addressCode: res.data.addressCode,
                  deptId: res.data.deptId,
                  year: res.data.year,
                  productId: res.data.productId,
                  techs: memberMap['1'],
                  finances: memberMap['2'],
                  ower: memberMap['3'] ? memberMap['3'][0] : undefined,
                  business: memberMap['4'] ? memberMap['4'][0] : undefined
                }
                this.$nextTick(() => {
                  this.form.setFieldsValue(fieldObj)
                })
              }
            })
        })
      }
    },
    customerChange (value) {
      if (value) {
        if (value.addressCode) {
          this.form.setFieldsValue({ addressCode: value.addressCode })
        }
        if (value.owerId) {
          this.form.setFieldsValue({ ower: { realName: value.owerName, id: value.owerId, userName: value.userName } })
        }
        if (value.deptId !== null) {
          this.form.setFieldsValue({ deptId: value.deptId })
        }
      }
    },
    yearChange (value) {
      this.form.resetFields(['productId'])
      this.productOption = { ...this.productOption, year: value }
      this.productType = -1
    },
    addressChange (val) {
      this.form.resetFields(['productId'])
      this.productOption = { ...this.productOption, address: val.join(',') }
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          const pDtail = {
            id: this.projectId,
            customerId: values.customer.id,
            members: [],
            deptId: values.deptId,
            productId: values.productId,
            year: values.year
          }
          if (this.isNew || this.$auth('project:projectList:edit') || this.$auth('project:projectList:tech') && values.techs && values.techs.length > 0) {
            values.techs.forEach(a => {
              pDtail.members.push({ projectId: this.projectId, mType: 1, memberId: a.id })
            })
          }
          if (this.isNew || this.$auth('project:projectList:edit') || this.$auth('project:projectList:fina') && values.finances && values.finances.length > 0) {
            values.finances.forEach(a => {
              pDtail.members.push({ projectId: this.projectId, mType: 2, memberId: a.id })
            })
          }
          if (this.isNew || this.$auth('project:projectList:edit')) {
            if (values.ower) {
              pDtail.members.push({ projectId: this.projectId, mType: 3, memberId: values.ower.id })
            } else {
              pDtail.members.push({ projectId: this.projectId, mType: 3 })
            }
            if (values.business) {
              pDtail.members.push({ projectId: this.projectId, mType: 4, memberId: values.business.id })
            } else {
              pDtail.members.push({ projectId: this.projectId, mType: 4 })
            }
          }
          this.$http.post(this.isNew ? '/project/add' : '/project/update', pDtail).then(res => {
            if (res.success) {
              this.form.resetFields()
              this.visible = false
              this.$message.success('操作成功')
              this.$emit('ok')
            } else {
              this.$message.error(res.errorMessage)
            }
          })
        }
      })
    },
    handleCancel () {
      this.form.resetFields()
      this.visible = false
    },
    afterClose () {
      this.form.resetFields()
      this.projectId = undefined
      this.productType = -1
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-card-head-title {
  position: absolute;
  margin-top: -40px;
}
</style>
