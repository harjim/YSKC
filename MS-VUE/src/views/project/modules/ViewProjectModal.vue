<template>
  <a-modal
    :width="850"
    :visible="visible"
    :maskClosable="false"
    title="项目信息"
    @cancel="handleCancel"
    :afterClose="afterClose"
    :confirmLoading="confirmLoading"
  >
    <div>
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="客户名称" :labelCol="{span: 4}" :wrapperCol="{span: 20}" style="margin-bottom: 8px">
              <search-select
                url="/customer/customerForSelect"
                searchField="customerName"
                sTitle="companyName"
                placeholder="客户名称"
                :readonly="readonly"
                v-decorator="['customer']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="所属地" :labelCol="{span: 8}" :wrapperCol="{span: 16}" style="margin-bottom: 8px">
              {{ addressStr }}
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="所属部门" :labelCol="{span: 8}" :wrapperCol="{span: 16}" style="margin-bottom: 8px">
              {{ data.deptName }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="项目年份" :labelCol="{span: 8}" :wrapperCol="{span: 16}" style="margin-bottom: 8px">
              <year-select
                placeholder="项目年份"
                :readonly="readonly"
                v-decorator="['year']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="项目类型" :labelCol="{span: 8}" :wrapperCol="{span: 16}" style="margin-bottom: 8px">
              {{ data.productName }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="业务员" :labelCol="{span: 8}" :wrapperCol="{span: 16}" style="margin-bottom: 8px">
              <search-select
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                placeholder="业务员"
                :readonly="readonly"
                v-decorator="['ower']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="谈单经理" :labelCol="{span: 8}" :wrapperCol="{span: 16}" style="margin-bottom: 8px">
              <search-select
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                placeholder="谈单经理"
                :readonly="readonly"
                v-decorator="['business']"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="技术负责人" :labelCol="{span: 8}" :wrapperCol="{span: 16}" style="margin-bottom: 8px">
              <search-select
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="true"
                placeholder="技术负责人员"
                :readonly="readonly"
                v-decorator="['techs']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="财务负责人" :labelCol="{span: 8}" :wrapperCol="{span: 16}" style="margin-bottom: 8px">
              <search-select
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="true"
                placeholder="财务负责人员"
                :readonly="readonly"
                v-decorator="['finances']"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <template slot="footer">
      <a-button type="primary" @click="handleCancel">关闭</a-button>
    </template>
  </a-modal>
</template>

<script>
import { SearchSelect, YearSelect, DeptSelect, ProductSelect } from '@/components/Selects'
import moment from 'moment'
export default {
  components: {
    DeptSelect,
    SearchSelect,
    YearSelect,
    ProductSelect
  },
  data () {
    return {
      projectId: undefined,
      addressCode: {},
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
      products: [],
      data: {},
      readonly: true,
      addressMap: {},
      addressStr: ''
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
                this.data = res.data
                const memberMap = {}
                res.data.members.reduce((mmap, m) => {
                  if (typeof mmap[m.mType] === 'undefined') {
                    mmap[m.mType] = []
                  }
                  mmap[m.mType].push({ 'id': m.memberId, 'realName': m.realName, 'userName': m.userName })
                  return mmap
                }, memberMap)
                if (res.data.addressCode) {
                  res.data.addressCode.split(',').forEach((item) => {
                    this.getAddressStr(this.addressCode, item)
                  })
                }
                this.addressStr = this.addressStr.substring(0, this.addressStr.length - 1)
                const fieldObj = {
                  customer: { id: res.data.customerId, companyName: res.data.companyName },
                  year: res.data.year,
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
    getAddressStr (data, key) {
      for (let index = 0; index < data.length; index++) {
        if (data[index].children && data[index].children.length > 0) {
          this.getAddressStr(data[index].children, key)
        }
        if (data[index].key === key) {
          this.addressStr += data[index].value + '/'
          return
        }
      }
    },
    handleCancel () {
      this.form.resetFields()
      this.visible = false
    },
    afterClose () {
      this.form.resetFields()
      this.projectId = undefined
      this.addressMap = {}
      this.addressStr = ''
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
