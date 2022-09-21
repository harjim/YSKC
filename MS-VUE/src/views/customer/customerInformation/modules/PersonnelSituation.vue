<!--
 * @Author: ldx
 * @Date: 2020-12-05 11:11:35
 * @LastEditTime: 2020-12-08 11:01:59
 * @LastEditors: ldx
 * @Description: 上年末公司从业人员情况
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\personnelSituation.vue
-->
<template>
  <div>
    <custom-title title="上年末公司从业人员情况" @switchEdit="switchEdit" ref="title"></custom-title>
    <a-spin :spinning="spinning" tip="加载中...">
      <a-form :form="form">
        <a-divider orientation="left">
          <span class="mode-title-h2">法定代表人</span>
        </a-divider>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="姓名" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                style="width:209px;"
                placeholder="请输入姓名"
                v-decorator="['corporationName', {rules:[{required: false, message: '请输入姓名'}]}]"
              />
              <span v-else>
                {{ data.corporationName ? data.corporationName: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="学历" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-select
                v-if="isEdit"
                v-decorator="['corporationDegree', {rules:[{required: false, message: '请选择学历'}]}]"
                placeholder="请选择学历"
              >
                <a-select-option
                  v-for="(v,i) in eduStrs"
                  :key="i"
                  :value="`${i}`"
                >{{ v }}</a-select-option>
              </a-select>
              <template v-else>
                {{ data.corporationDegree ? eduStrs[data.corporationDegree] : '-' }}
              </template>
              <!-- <a-input
                v-if="isEdit"
                placeholder="请输入学历"
                v-decorator="['corporationDegree', {rules:[{required: true, message: '请输入学历'}]}]"
              />
              <span v-else>
                {{ data.corporationDegree ? data.corporationDegree: '-' }}
              </span> -->
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="移动电话" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                placeholder="请输入移动电话"
                v-decorator="['corporationPhone', {rules:[{required: false, message: '请输入移动电话'}]}]"
              />
              <span v-else>
                {{ data.corporationPhone ? data.corporationPhone: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="身份证号码" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                placeholder="请输入身份证号码"
                v-decorator="['corporationIdNum', {rules:[{required: false, message: '请输入身份证号码'}]}]"
              />
              <span v-else>
                {{ data.corporationIdNum ? data.corporationIdNum: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-divider orientation="left">
          <span class="mode-title-h2">单位联系人</span>
        </a-divider>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="姓名" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                placeholder="请输入姓名"
                v-decorator="['linkName', {rules:[{required: false, message: '请输入姓名'}]}]"
              />
              <span v-else>
                {{ data.linkName ? data.linkName: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="学历" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-select
                v-if="isEdit"
                v-decorator="['linkDegree', {rules:[{required: false, message: '请选择学历'}]}]"
                placeholder="请选择学历"
              >
                <a-select-option
                  v-for="(v,i) in eduStrs"
                  :key="i"
                  :value="`${i}`"
                >{{ v }}</a-select-option>
              </a-select>
              <template v-else>
                {{ data.linkDegree ? eduStrs[data.linkDegree] : '-' }}
              </template>
              <!-- <a-input
                v-if="isEdit"
                placeholder="请输入学历"
                v-decorator="['linkDegree', {rules:[{required: true, message: '请输入学历'}]}]"
              />
              <span v-else>
                {{ data.linkDegree ? data.linkDegree: '-' }}
              </span> -->
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="移动电话" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                placeholder="请输入移动电话"
                v-decorator="['linkPhone', {rules:[{required: false, message: '请输入移动电话'}]}]"
              />
              <span v-else>
                {{ data.linkPhone ? data.linkPhone: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="身份证号码" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input
                v-if="isEdit"
                placeholder="请输入身份证号码"
                v-decorator="['linkIdNum', {rules:[{required: false, message: '请输入身份证号码'}]}]"
              />
              <span v-else>
                {{ data.linkIdNum ? data.linkIdNum: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-divider orientation="left">
          <span class="mode-title-h2">从业人员</span>
        </a-divider>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="从业人员总数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入从业人员总数"
                v-decorator="['employees', {rules:[{required: false, message: '请输入从业人员总数'}]}]"
              />
              <span v-else>
                {{ data.employees ? data.employees: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="其中女职工数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入其中女职工数"
                v-decorator="['females', {rules:[{required: false, message: '请输入其中女职工数'}]}]"
              />
              <span v-else>
                {{ data.females ? data.females: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="留学归国人员数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入留学归国人员数"
                v-decorator="['returnees', {rules:[{required: false, message: '请输入留学归国人员数'}]}]"
              />
              <span v-else>
                {{ data.returnees ? data.returnees: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="参加社保人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入参加社保人数"
                v-decorator="['insurances', {rules:[{required: false, message: '请输入参加社保人数'}]}]"
              />
              <span v-else>
                {{ data.insurances ? data.insurances: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="外籍专家人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入外籍专家人数"
                v-decorator="['foreignExperts', {rules:[{required: false, message: '请输入外籍专家人数'}]}]"
              />
              <span v-else>
                {{ data.foreignExperts ? data.foreignExperts: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="新增高校毕业生" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入新增高校毕业生"
                v-decorator="['freshGraduates', {rules:[{required: false, message: '请输入新增高校毕业生'}]}]"
              />
              <span v-else>
                {{ data.freshGraduates ? data.freshGraduates: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-divider orientation="left">
          <span class="mode-title-h2">各岗位从业人员</span>
        </a-divider>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="行政管理人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入行政管理人数"
                v-decorator="['administrations', {rules:[{required: false, message: '请输入行政管理人数'}]}]"
              />
              <span v-else>
                {{ data.administrations ? data.administrations: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="市场营销人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入市场营销人数"
                v-decorator="['marketings', {rules:[{required: false, message: '请输入市场营销人数'}]}]"
              />
              <span v-else>
                {{ data.marketings ? data.marketings: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="研发设计人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入研发设计人数"
                v-decorator="['rdDesigners', {rules:[{required: false, message: '请输入研发设计人数'}]}]"
              />
              <span v-else>
                {{ data.rdDesigners ? data.rdDesigners: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="加工制造人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入加工制造人数"
                v-decorator="['processes', {rules:[{required: false, message: '请输入加工制造人数'}]}]"
              />
              <span v-else>
                {{ data.processes ? data.processes: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="其他人数 " :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入其他人数"
                v-decorator="['otherWorkers', {rules:[{required: false, message: '请输入其他人数'}]}]"
              />
              <span v-else>
                {{ data.otherWorkers ? data.otherWorkers: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-divider orientation="left">
          <span class="mode-title-h2">各学历从业人员</span>
        </a-divider>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="博士毕业人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入博士毕业人数"
                v-decorator="['doctors', {rules:[{required: false, message: '请输入博士毕业人数'}]}]"
              />
              <span v-else>
                {{ data.doctors ? data.doctors: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="硕士毕业人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入硕士毕业人数"
                v-decorator="['masters', {rules:[{required: false, message: '请输入硕士毕业人数'}]}]"
              />
              <span v-else>
                {{ data.masters ? data.masters: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="本科毕业人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入本科毕业人数"
                v-decorator="['undergraduates', {rules:[{required: false, message: '请输入本科毕业人数'}]}]"
              />
              <span v-else>
                {{ data.undergraduates ? data.undergraduates: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="大专毕业人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入大专毕业人数"
                v-decorator="['juniorColleges', {rules:[{required: false, message: '请输入大专毕业人数'}]}]"
              />
              <span v-else>
                {{ data.juniorColleges ? data.juniorColleges: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="其他人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入其他人数"
                v-decorator="['otherDegrees', {rules:[{required: false, message: '请输入其他人数'}]}]"
              />
              <span v-else>
                {{ data.otherDegrees ? data.otherDegrees: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-divider orientation="left">
          <span class="mode-title-h2">各职称从业人员</span>
        </a-divider>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="高级职称人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入高级职称人数"
                v-decorator="['highTitles', {rules:[{required: false, message: '请输入高级职称人数'}]}]"
              />
              <span v-else>
                {{ data.highTitles ? data.highTitles: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="中级职称人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入中级职称人数"
                v-decorator="['middleTitles', {rules:[{required: false, message: '请输入中级职称人数'}]}]"
              />
              <span v-else>
                {{ data.middleTitles ? data.middleTitles: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="[8]">
          <a-col :span="12">
            <a-form-item label="初级职称人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入初级职称人数"
                v-decorator="['primaryTitles', {rules:[{required: false, message: '请输入初级职称人数'}]}]"
              />
              <span v-else>
                {{ data.primaryTitles ? data.primaryTitles: '-' }}
              </span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="其他人数" :labelCol="labelColT" :wrapperCol="wrapperColT">
              <a-input-number
                v-if="isEdit"
                :min="0"
                :precision="0"
                :step="1"
                style="width:209px;"
                placeholder="请输入其他人数"
                v-decorator="['otherTitles', {rules:[{required: false, message: '请输入其他人数'}]}]"
              />
              <span v-else>
                {{ data.otherTitles ? data.otherTitles: '-' }}
              </span>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <div>
        <div v-if="isEdit" class="action-buttons">
          <a-button class="save-btn" type="primary" @click="saveBtn">保存</a-button>
          <a-button @click="cancelBtn">取消</a-button>
        </div>
      </div>
    </a-spin>
  </div>
</template>

<script>
import CustomTitle from './CustomTitle'
const eduStrs = ['无', '高中及以下', '中专', '大专', '本科', '硕士', '博士']
export default {
  name: 'PersonnelSituation',
  components: {
    CustomTitle
  },
  props: {
    companyId: {
      type: Number,
      default: 0
    }
  },
  created () {
    this.loadData(this.companyId)
  },
  watch: {
    companyId (newValue) {
      if (newValue) {
        this.cancelBtn()
        this.loadData(newValue)
      }
    }
  },
  data () {
    return {
      form: this.$form.createForm(this),
      isEdit: false,
      spinning: false,
      eduStrs,
      id: undefined,
      data: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 }
      },
      labelColT: {
        xs: { span: 24 },
        sm: { span: 10 }
      },
      wrapperColT: {
        xs: { span: 24 },
        sm: { span: 14 }
      }
    }
  },
  methods: {
    loadData (companyId) {
      this.spinning = true
      const year = new Date().getFullYear() - 1
      this.$http.get('/companyInfo/queryEmploymentInfo', { params: { companyId, year } }).then((res) => {
        if (res.data && res.success) {
          if (res.data) {
            this.id = res.data.id
          }
          this.data = res.data
          this.$initForm(this.form, res.data)
          this.spinning = false
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch(() => {
        this.spinning = false
      })
    },
    saveBtn () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.spinning = true
          if (this.id) {
            values['id'] = this.id
          }
          values['companyId'] = this.companyId
          const year = new Date().getFullYear()
          values['year'] = year - 1
          this.$http.post('/companyInfo/editEmployment', values).then((res) => {
            if (res.data && res.success) {
              this.id = res.data
              this.cancelBtn()
              Object.assign(this.data, values)
              this.$message.success('保存成功')
              this.spinning = false
            } else {
              this.$message.error(res.errorMessage)
              this.spinning = false
            }
          }).catch(() => {
            this.spinning = false
          })
        }
      })
    },
    cancelBtn () {
      this.$refs.title.switchEdit()
    },
    switchEdit (isEdit) {
      if (isEdit) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.data)
        })
      }
      this.isEdit = isEdit
    }
  }
}
</script>

<style lang="less" scoped>
.mode-title-h2 {
  font-size: 14px;
  font-weight: 500;
  color: #151515;
}
.action-buttons {
  padding: 12px 0;
  text-align: center;
}
.save-btn {
  margin-right: 40px;
}
</style>
