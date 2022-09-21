<!--
 * @Description: 机构建设事项编辑/保存页
 * @FilePath: \RS-VUE\src\components\BuildConfig\SaveBuildConfigModal.vue
-->
<template>
  <div>
    <a-modal :width="1184" :visible="visible" title="机构建设事项配置" :maskClosable="false" @cancel="visible = false">
      <a-form :form="form1" layout="horizontal" labelAlign="left">
        <a-divider orientation="left">技术配置</a-divider>
        <a-row>
          <a-col :span="12">
            <a-form-item label="研发部门负责人" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <search-select
                url="/project/getBaseEmployeeSelect"
                @change="handleChange"
                placeholder="请输入研发部门负责人"
                v-decorator="['rdDeptMaster', { rules: [{ required: true, message: '请输入研发部门负责人' }] }]"
                style="width:80%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="制定部门" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <select-down
                style="width:80%"
                ref="rdDept"
                treeType="rdDept"
                :value="content.madeDept"
                @select="
                  (v, n) => {
                    content.madeDept = n
                  }
                "
                placeholder="请选择制定部门"
              >
              </select-down>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="制定人" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <search-select
                url="/project/getBaseEmployeeSelect"
                placeholder="请输入制定人"
                v-decorator="['maker', { rules: [{ required: true, message: '请输入制定人' }] }]"
                style="width:80%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="发布日期" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <a-date-picker
                @change="handleChange2"
                style="width:80%"
                v-decorator="[
                  'issueDate',
                  {
                    rules: [{ required: true, message: '请选择日期' }]
                  }
                ]"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item label="版本" :labelCol="{ span: 3 }" :wrapperCol="{ span: 21 }">
              <a-input
                style="width:92%"
                :maxLength="6"
                v-decorator="[
                  'version',
                  {
                    rules: [{ required: true, message: '请输入版本' }],
                    initialValue: 'V/0'
                  }
                ]"
                placeholder="请输入版本"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="变更内容简述" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <a-input
                style="width:80%"
                :maxLength="32"
                v-decorator="[
                  'description',
                  {
                    rules: [{ required: true, message: '请输入变更内容简述' }],
                    initialValue: '首次发行'
                  }
                ]"
                placeholder="请输入变更内容简述"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="生效日期" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <a-date-picker
                style="width:80%"
                v-decorator="[
                  'validDate',
                  {
                    rules: [{ required: true, message: '请选择日期' }]
                  }
                ]"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="核准" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <search-select
                url="/project/getBaseEmployeeSelect"
                placeholder="请输入核准"
                v-decorator="['approval', { rules: [{ required: true, message: '请输入核准' }] }]"
                style="width:80%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="审核" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <search-select
                url="/projectDocFile/getList"
                :year="this.year"
                placeholder="请输入审核"
                v-decorator="['auditor', { rules: [{ required: true, message: '请输入审核' }] }]"
                style="width:80%"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <a-form :form="form2" layout="horizontal" labelAlign="left">
        <a-divider orientation="left">财务配置</a-divider>
        <a-row>
          <a-col :span="12">
            <a-form-item label="财务负责人" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <search-select
                url="/project/getEmployeeNames"
                placeholder="请输入财务负责人"
                v-decorator="['rdDeptMaster', { rules: [{ required: true, message: '请输入财务负责人' }] }]"
                @change="handleChange1"
                style="width:80%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="制定部门" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <a-input
                style="width:80%"
                :maxLength="16"
                v-decorator="[
                  'madeDept',
                  {
                    rules: [{ required: true, message: '请输入制定部门' }],
                    initialValue: '财务部'
                  }
                ]"
                placeholder="请输入制定部门"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="制定人" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <search-select
                url="/project/getEmployeeNames"
                placeholder="请输入制定人"
                v-decorator="['maker', { rules: [{ required: true, message: '请输入制定人' }] }]"
                style="width:80%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="发布日期" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <a-date-picker
                @change="handleChange3"
                style="width:80%"
                v-decorator="[
                  'issueDate',
                  {
                    rules: [{ required: true, message: '请选择日期' }]
                  }
                ]"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item label="版本" :labelCol="{ span: 3 }" :wrapperCol="{ span: 21 }">
              <a-input
                style="width:92%"
                :maxLength="6"
                v-decorator="[
                  'version',
                  {
                    rules: [{ required: true, message: '请输入版本' }],
                    initialValue: 'V/0'
                  }
                ]"
                placeholder="请输入版本"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="变更内容简述" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <a-input
                style="width:80%"
                :maxLength="32"
                v-decorator="[
                  'description',
                  {
                    rules: [{ required: true, message: '请输入变更内容简述' }],
                    initialValue: '首次发行'
                  }
                ]"
                placeholder="请输入变更内容简述"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="生效日期" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <a-date-picker
                style="width:80%"
                v-decorator="[
                  'validDate',
                  {
                    rules: [{ required: true, message: '请选择日期' }]
                  }
                ]"
                format="YYYY-MM-DD"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="12">
            <a-form-item label="核准" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <search-select
                url="/project/getEmployeeNames"
                placeholder="请输入核准"
                v-decorator="['approval', { rules: [{ required: true, message: '请输入核准' }] }]"
                style="width:80%"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="审核" :labelCol="{ span: 6 }" :wrapperCol="{ span: 18 }">
              <search-select
                url="/project/getEmployeeNames"
                placeholder="请输入审核"
                v-decorator="['auditor', { rules: [{ required: true, message: '请输入审核' }] }]"
                style="width:80%"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <div
        :style="{
          position: 'absolute',
          right: 0,
          bottom: 0,
          width: '100%',
          padding: '5px',
          background: '#fff',
          textAlign: 'right',
          zIndex: 1
        }"
      >
        <a-button :style="{ marginRight: '5px' }" @click="onClose()">
          取消
        </a-button>
        <a-button type="primary" @click="onSubmit()">
          保存
        </a-button>
      </div>
    </a-modal>
  </div>
</template>
<script>
import moment from 'moment'
import SearchSelect from './SearchSelect'
import SelectDown from '@/components/SelectDown'
export default {
  components: {
    SelectDown,
    SearchSelect
  },
  data () {
    return {
      form1: this.$form.createForm(this, { name: 'form1' }),
      form2: this.$form.createForm(this, { name: 'form2' }),
      visible: false,
      negotiation: false,
      dataInfo: {},
      update: false,
      reviewList: {},
      employeeList: {},
      deptTree: {},
      year: '',
      content: {}
    }
  },
  methods: {
    moment,
    open (year) {
      this.year = year
      this.form1.resetFields()
      this.form2.resetFields()
      this.loadInfo(year)
      this.visible = true
    },
    onClose () {
      this.form1.resetFields()
      this.form2.resetFields()
      this.visible = false
    },
    loadInfo (year) {
      this.spinning = true
      this.$http
        .get('/buildConfig/getList', { params: { year: year } })
        .then(res => {
          if (res.success) {
            if (res.data && res.data.length !== 0) {
              this.dataInfo = res.data
              if (this.dataInfo[0] && this.dataInfo[0] != null) {
                this.content = this.dataInfo[0]
                this.dataInfo[0].issueDate = moment(this.dataInfo[0].issueDate)
                this.dataInfo[0].validDate = moment(this.dataInfo[0].validDate)
                this.$initForm(this.form1, this.dataInfo[0])
              }
              if (this.dataInfo[1] && this.dataInfo[1] != null) {
                this.dataInfo[1].issueDate = moment(this.dataInfo[1].issueDate)
                this.dataInfo[1].validDate = moment(this.dataInfo[1].validDate)
                this.$initForm(this.form2, this.dataInfo[1])
              }
            }
          } else {
            this.$message.error(res.errorMessage || '获取事项配置失败，请联系管理员')
          }
        })
        .finally(() => {
          this.spinning = false
        })
    },
    handleChange (ename) {
      if (this.form1.getFieldValue('approval') == null) {
        this.form1.setFieldsValue({ approval: ename })
      }
    },
    handleChange1 (ename) {
      if (this.form2.getFieldValue('approval') == null) {
        this.form2.setFieldsValue({ approval: ename })
      }
    },
    handleChange2 (date) {
      this.form1.setFieldsValue({ validDate: date })
    },
    handleChange3 (date) {
      this.form2.setFieldsValue({ validDate: date })
    },
    onSubmit () {
      const models = []
      const p1 = new Promise((resolve, reject) => {
        this.form1.validateFields((errors, values) => {
          if (!errors) {
            if (this.dataInfo[0] != null && this.dataInfo[0]) {
              values.createTime = this.dataInfo[0].createTime
              values.creatorId = this.dataInfo[0].creatorId
              values.msCreatorId = this.dataInfo[0].msCreatorId
              values.companyId = this.dataInfo[0].companyId
              values.id = this.dataInfo[0].id
            }
            values.year = this.year
            values.type = 0
            values.madeDept = this.content.madeDept
            models.push(values)
            resolve()
          }
          reject(new Error())
        })
      })
      const p2 = new Promise((resolve, reject) => {
        this.form2.validateFields((errors, values) => {
          if (!errors) {
            if (this.dataInfo != null && this.dataInfo[1]) {
              values.createTime = this.dataInfo[1].createTime
              values.creatorId = this.dataInfo[1].creatorId
              values.msCreatorId = this.dataInfo[1].msCreatorId
              values.companyId = this.dataInfo[1].companyId
              values.id = this.dataInfo[1].id
            }
            values.year = this.year
            values.type = 1
            models.push(values)
            resolve()
          }
          reject(new Error())
        })
      })

      Promise.all([p1, p2])
        .then(() => {
          this.$http.post('/buildConfig/save', models).then(res => {
            if (res.success) {
              this.$message.success('保存成功')
              this.onClose()
            } else {
              this.$message.error(res.errorMessage || '保存失败，请联系管理员')
            }
          })
        })
        .catch()
    }
  }
}
</script>
