<template>
  <a-modal
    :title="title"
    :width="800"
    :maskClosable="false"
    v-model="visible"
    @ok="handleSubmit"
    @cancel="visible = false"
    :afterClose="afterClose"
    centered
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="20" :offset="1">
          <a-form-item
            :labelCol="{ xs: { span: 24 }, sm: { span: 4 } }"
            :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }">
            <a-radio-group default-value="new" button-style="solid" @change="radio_change" v-model="radioValue">
              <a-radio value="new">
                合并成新项目
              </a-radio>
              <a-radio value="old">
                合并至现有项目
              </a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <span v-if="radioValue === 'new'">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="项目名称"
              :labelCol="{ xs: { span: 24 }, sm: { span: 4 } }"
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }"
            >
              <a-input
                placeholder="请输入项目名称"
                v-decorator="['pname', {rules: [{ required: true, whitespace: true, message: '请输入项目名称'}, { validator: this.checkPname } ]}, { validateTrigger: ['blur'] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="RD" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-group compact>
                <span style="margin-top:5px">{{ minYear }}RD&nbsp;</span>
                <a-input-number
                  :min="1"
                  style="width: 120;"
                  placeholder="请输入RD"
                  v-decorator="['rdIndex', {rules: [{ required: true, message: '请输入RD编号'}, { validator: this.checkRD } ]} ,{ validateTrigger: ['blur'] }]"
                />
              </a-input-group>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="内部编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入内部编号" v-decorator="['rdNumber', { rules: [{ whitespace: true, message:'不能只输入空格' }] }]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <!-- <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入所属部门" v-decorator="['deptName', { rules: [{ whitespace: true, message:'不能只输入空格' }] }]" />
            </a-form-item> -->
            <a-form-item
              label="部门:"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol">
              <!-- v-decorator="['deptId', {rules:[{required: true, message: '请选择部门'}]}]" -->
              <a-tree-select
                treeDefaultExpandAll
                showSearch
                treeNodeFilterProp="title"
                :labelInValue="true"
                :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
                :tree-data="deptTree"
                v-decorator="['deptId']"
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
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="负责人">
              <a-select
                showSearch
                placeholder="输入名字搜索"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="handleSearch"
                v-model="masterENumber"
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
            <a-form-item label="产品" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入产品" v-decorator="['involvedProduct', { rules: [{ message: '请输入产品'}] }]" />
            </a-form-item>
          </a-col>
        </a-row>
      </span>
      <span v-else>
        <a-form-item
          label="合并至"
          style="margin-bottom: 0px;"
          :labelCol="{ xs: { span: 24 }, sm: { span: 3 } }"
          :wrapperCol="{ xs: { span: 24 }, sm: { span: 8 } }">
          <a-select placeholder="请选择项目" v-decorator="['parentId', {rules:[{required: true, message: '请选择项目'}]}]">
            <a-select-option :value="item.id" v-for="item in parentProject" :key="item.id">{{ item.rdTitle }} - {{ item.pname }}</a-select-option>
          </a-select>
        </a-form-item >
      </span>
    </a-form>
  </a-modal>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      visible: false,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      year: -1,
      minYear: -1,
      employeeList: [],
      rdDeptTree: [],
      selectedRowKeys: [],
      selectedMinRows: null,
      form: this.$form.createForm(this),
      enumber: undefined,
      masterENumber: undefined,
      radioValue: 'new',
      parentProject: [],
      deptTree: []
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  watch: {
    year () {
      this.loadRdDept()
    }
  },
  methods: {
    show (selectedRowKeys, selectedMinRows, year, deptTree) {
      this.form.resetFields()
      this.title = '合并项目'
      this.visible = true
      this.radioValue = 'new'
      this.selectedMinRows = selectedMinRows
      this.selectedRowKeys = selectedRowKeys
      this.year = year
      this.minYear = selectedMinRows.beginYear
      this.deptTree = deptTree
      // this.loadTree()
      this.$nextTick(() => {
        this.form.setFields({ 'rdIndex': { value: selectedMinRows.rdIndex } })
        // 规划部门提示
        // if (!(this.deptTree && this.deptTree.length !== 0)) {
        //   this.form.setFields({ 'deptId': { errors: [new Error('未规划部门,请先规划当前年部门')] } })
        // }
      })
    },
    handleSearch (value) {
      this.$http.get('/project/getEmployeeList', { params: { ename: value, year: this.currentYear } })
        .then(res => {
          this.employeeList = res.data
        })
    },
    loadRdDept () {
      return this.$getTree('rdDept')
        .then(res => {
          this.rdDeptTree = res.tree
          return this.rdDeptTree
        })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          if (this.radioValue === 'new') {
            values.masterENumber = this.masterENumber
            values.sign = 1
          } else if (this.radioValue === 'old') {
            values.sign = 0
          }
          values.deptName = values.deptId ? values.deptId.label : null
          values.deptId = values.deptId ? values.deptId.value : 0
          values.childIds = this.selectedRowKeys
          this.$http.post('/project/mergeProject', values).then((res) => {
            if (res.data && res.success) {
              this.$message.success('操作成功')
              this.$emit('ok', true)
              this.visible = false
            } else {
              this.$message.error(res.errorMessage)
            }
          }).catch(error => {
            this.$message.error(error)
          })
        }
      })
    },
    afterClose () {
      this.form.resetFields()
      this.visible = false
      this.title = ''
      this.employeeList = []
      this.id = 0
      this.enumber = undefined
      this.selectedMinRows = null
      this.selectedRowKeys = []
      this.parentProject = []
      this.$emit('clearSelectKeys')
    },
    setMaxRdIndex (year) {
      this.$http.get('/project/selectMaxRd', { params: { year } }).then((res) => {
        if (res.success && res.data) {
          this.$nextTick(() => {
            this.form.setFieldsValue({
              rdIndex: res.data
            })
          })
        }
      })
    },
    checkRD (rule, value, callback) {
      if (value) {
        this.$http.get('/project/checkParentNo', { params: { rdIndex: value, projectIds: this.selectedRowKeys } })
          .then(res => {
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
    checkPname (rule, value, callback) {
      if (value) {
        this.$http.get('/project/checkPname', { params: { pname: value } })
          .then(res => {
            if (!res.data) {
              callback(new Error('该年份存在相同项目名称,请重新输入'))
            } else {
              callback()
            }
          })
      } else {
        callback()
      }
    },
    // 单选按钮改变事件
    radio_change (event) {
      if (this.radioValue === 'new') {
        this.$nextTick(() => {
          this.form.setFields({ 'rdIndex': { value: this.selectedMinRows.rdIndex } })
        })
      } else {
        if (this.parentProject.length > 0) return
        this.$http.get('/project/queryParentList', { params: { projectIds: this.selectedRowKeys, currentYear: this.year } })
          .then((res) => {
            if (res.success && res.data) {
              this.parentProject = res.data
            } else {
              this.$message.error('获取父项目失败，请联系管理员')
            }
          })
      }
    }
  }
}
</script>

<style>

</style>
