<template>
  <div>
    <a-spin :spinning="confirmLoading">
      <a-form
        @submit="handleSubmit"
        :form="form"
      >
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="项目名称"
              :labelCol="{ xs: { span: 24 }, sm: { span: 4 } }"
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }"
            >
              <a-input v-decorator="['pname', { rules:[{ required: true, whitespace: true, message: '请输入项目名称'},{ validator: this.checkPname }], validateTrigger: ['blur'] }]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item
              label="RD"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-input-group compact>
                <span style="margin-top:5px">{{ year }}RD&nbsp;</span>
                <a-input-number
                  :min="1"
                  :precision="0"
                  style="width: 60px;"
                  v-decorator="['rdIndex', { rules:[{required: true, message: '请输入RD编号'}, { validator: this.checkRD } ], validateTrigger: ['blur'] }]"
                />
              </a-input-group>
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
            <a-form-item
              label="研发部门:"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-tree-select
                showSearch
                v-decorator="['rdDeptId']"
                :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                :treeData="rdDeptTree"
                placeholder="请选择研发部门"
                treeNodeFilterProp="title"
                treeDefaultExpandAll
              ></a-tree-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              label="负责人"
            >
              <a-select
                showSearch
                v-decorator="['masterENumber']"
                placeholder="输入名字搜索"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="handleSearch"
                @change="(v,event) => onHandleChange(event)"
                :notFoundContent="null"
              >
                <a-select-option
                  v-for="d in employeeList"
                  :key="d.enumber"
                  :ename="d.ename"
                >{{ d.ename + '(' + d.enumber + ')' }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="产品" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入产品" v-decorator="['involvedProduct', { rules: [{ message: '请输入产品'}] }]" />
            </a-form-item>
          </a-col>
        </a-row>
        <span>
          <a-button
            v-if="$auth('project:report:base:save') && (projectData.beginYear !== currentYear || modify)"
            type="primary"
            @click="handleSubmit()"
          >保存</a-button>
        </span>
      </a-form>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'

export default {
  mixins: [yearMiXin],
  name: 'ParentBaseTab',
  props: {
    projectData: {
      type: Object,
      required: true
    },
    modify: {
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
      limitDate: undefined,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      deptIdsArr: [],
      rdDeptTree: [],
      resultParam: {},
      tecIndustryLevel: [],
      confirmLoading: false,
      form: this.$form.createForm(this),
      employeeList: [],
      year: 2019,
      submitState: false,
      beginDate: null,
      endDate: null,
      endOpen: false,
      tBeginDate: null,
      tEndDate: null,
      isTrial: false,
      ename: undefined
    }
  },
  created () {
    this.year = this.projectData.beginYear
    this.loadRdDept()
    if (this.projectData.masterENumber && this.projectData.ename) {
      this.employeeList = this.projectData.masterENumber ? [{ ename: this.projectData.ename, enumber: this.projectData.masterENumber }] : []
    } else {
      this.projectData.masterENumber = undefined
    }
    this.$nextTick(() => {
      // 初始化表单数据
      const deptId = this.projectData.deptId ? { value: this.projectData.deptId, label: this.projectData.deptName } : null
      this.$initForm(this.form, { ...this.projectData, deptId })
      // 规划部门提示
      // if (!this.projectData.deptTree || !this.projectData.deptTree.length) {
      //   if (!(this.deptTree && this.deptTree.length !== 0)) {
      //     this.form.setFields({ 'deptId': { errors: [new Error('未规划部门,请先规划当前年部门')] } })
      //   }
      // }
    })
  },
  methods: {
    moment,
    tooltipDom () {
      return this.$refs.fullName.$el
    },
    // 验证项目名称
    checkPname (rule, value, callback) {
      if (value && value.trim() !== '') {
        this.$http.get('/project/checkPname', { params: { pname: value, projectId: this.projectData.id } })
          .then(res => {
            if (!res.data) {
              callback(new Error('该年份已存在相同项目名称,请重新输入'))
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
        this.$http.get('/project/checkRD', { params: { rdIndex: value, year: this.currentYear, projectId: this.projectData.id } })
          .then(res => {
            if (!res.data) {
              callback(new Error('该年份已存在相同RD,请重新输入'))
            } else {
              callback()
            }
          })
      } else {
        callback()
      }
    },
    loadRdDept () {
      return this.$getTree('rdDept', false, this.year)
        .then(res => {
          this.rdDeptTree = res.tree
          return this.rdDeptTree
        })
    },
    handleSubmit () {
      if (this.submitState) {
        return
      }
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const saveValue = { currentYear: this.currentYear }
          if (this.ename) {
            values.ename = this.ename
          }
          values.id = this.projectData.id
          values.deptName = values.deptId ? values.deptId.label : null
          values.deptId = values.deptId ? values.deptId.value : 0
          Object.assign(saveValue, this.projectData, values)
          const postData = { ...saveValue }
          this.$http.post('/project/update', saveValue)
            .then(res => {
              if (res.success && res.data) {
                this.$emit('updated', postData)
                this.$message.success('保存成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
            }).finally(res => {
              this.confirmLoading = false
              this.$emit('refresh')
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    // 负责人改变事件
    onHandleChange (event) {
      this.ename = event.data.attrs.ename
    },
    handleSearch (value) {
      this.$http.get('/project/getEmployeeList', { params: { ename: value, year: this.currentYear } })
        .then(res => {
          this.employeeList = res.data
        })
    },
    setDeafultValue () {
      if (this.projectData.projectSource === 0) {
        this.projectData.projectSource = undefined
      }
      if (this.projectData.result === 0) {
        this.projectData.result = undefined
      }
      if (this.projectData.formula === 0) {
        this.projectData.formula = undefined
      }
      if (this.projectData.targets === 0) {
        this.projectData.targets = undefined
      }
    }
    // deptValidator (rule, value, callback) {
    //   console.log('rule', rule)
    //   if (!(this.deptTree && this.deptTree.length !== 0)) {
    //     callback(new Error('未规划部门,请先规划当前年部门'))
    //   }
    //   callback()
    // }
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
