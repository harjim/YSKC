<!--
 * @Author: zdf
 * @Date: 2021-08-20 16:46:59
 * @LastEditTime: 2022-01-19 17:48:51
 * @LastEditors: zdf
 * @Description: 专利信息
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\PatentInfo.vue
-->
<template>
  <a-spin :spinning="spinning" tip="请稍后...">
    <a-card title="专利信息" style="width: 100%;">
      <template slot="extra">
        <a @click="openEdit(true)" v-if="!inputRequired">编辑</a>
        <a @click="openEdit(false)" v-else>取消</a>
      </template>

      <a-form :form="form">
        <a-row style="margin-right:-16px;">
          <a-row>
            <a-col class="colClass" :span="6">
              <a-form-item label="专利号" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.patentNo || '-' }}</div>
                <a-input @blur="checkpatentNo" v-else v-decorator="['patentNo', {rules:[{required: inputRequired, message: '请输入专利号'}]}]" placeholder="请输入专利号" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="专利名称" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.patentName || '-' }}</div>
                <a-input v-else v-decorator="['patentName', {rules:[{required: inputRequired, message: '请输入专利名称'}]}]" placeholder="请输入专利名称" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="专利类型" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.mainType || '-' }}</div>
                <a-select v-else v-decorator="['mainType', {rules:[{required: inputRequired, message: '请选择专利类型'}]}]" placeholder="请选择专利类型">
                  <a-select-option v-for="(v) in patentTypes" :key="String(v)">{{ v }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="申请日期" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.applyDateTime ? moment(row.applyDateTime).format('YYYY-MM-DD'): '-' }}</div>
                <a-date-picker v-else style="width:100%" v-decorator="['applyDateTime', {rules:[{required: false, message: '请选择申请日期'}]}]" placeholder="请选择申请日期" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="授权日期" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.authDate ? moment(row.authDate).format('YYYY-MM-DD'): '-' }}</div>
                <a-date-picker v-else style="width:100%" v-decorator="['authDate', {rules:[{required: false, message: '请选择授权日期'}]}]" placeholder="请选择授权日期" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="权利要求数量" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.claimNum || '-' }}</div>
                <a-input-number
                  v-else
                  :max="$store.state.maxOrder"
                  :min="0"
                  :precision="0"
                  style="width:100%"
                  v-decorator="['claimNum', {rules:[{required: false, message: '请输入权利要求数量'}]}]"
                  placeholder="请输入权利要求数量" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="使用次数" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.usedCnt || '-' }}</div>
                <a-input-number
                  v-else
                  :max="$store.state.maxOrder"
                  :min="0"
                  :precision="0"
                  style="width:100%"
                  v-decorator="['usedCnt', {rules:[{required: false, message: '请输入使用次数'}]}]"
                  placeholder="请输入使用次数" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="12">
              <a-form-item label="发明人" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.inventor || '-' }}</div>
                <a-input v-else v-decorator="['inventor', {rules:[{required: inputRequired, message: '请输入发明人'}]}]" placeholder="请输入发明人" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col class="colClass" :span="24">
              <a-form-item
                class="textarea"
                label="权利要求内容"
                :labelCol="fullCol"
                :wrapperCol="fullCol"
                :help="()=>{if(!inputRequired)return; const r = form.getFieldValue('claimContent'); return `(${!r? 0 : r.length > 3000 ? 3000 : r.length}/3000)`}">
                <div v-if="!inputRequired" class="colDiv">{{ row.claimContent || '-' }}</div>
                <a-textarea
                  v-else
                  v-decorator="['claimContent', {rules:[{required: false, message: '请输入权利要求内容'}]}]"
                  :maxLength="1000"
                  :rows="5"
                  placeholder="请输入权利要求内容">
                </a-textarea>
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="24">
              <a-form-item
                class="textarea"
                label="说明书内容"
                :labelCol="fullCol"
                :wrapperCol="fullCol"
                :help="()=>{if(!inputRequired)return; const r = form.getFieldValue('specification'); return `(${!r? 0 : r.length > 2000 ? 2000 : r.length}/2000)`}">
                <div v-if="!inputRequired" class="colDiv">{{ row.specification || '-' }}</div>
                <a-textarea
                  v-else
                  v-decorator="['specification', {rules:[{required: false, message: '请输入说明书内容'}]}]"
                  :maxLength="1000"
                  :rows="5"
                  placeholder="请输入说明书内容">
                </a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <div style="text-align:right;margin-top:10px;">
              <a-button style="margin-right:10px;" v-if="inputRequired" type="primary" @click="save()">保存</a-button>
            </div>
          </a-row>
        </a-row>
      </a-form>
    </a-card>
  </a-spin>
</template>

<script>
import moment from 'moment'
import SearchSelect from '@/components/Selects/SearchSelect.vue'
export default {
  components: {
    SearchSelect
  },
  props: {
    row: {
      type: Object,
      default: () => {}
    },
    patentPlan: {
      type: Object,
      required: true
    },
    patentTypes: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      fileList: [],
      inputRequired: false,
      otherItems: ['发明+实用新型同时申请', '申请时办理提前公开请求', '是否办理费减'],
      unitTypes: ['工矿企业', '个人', '大专院校', '科研单位', '事业单位'],
      spinning: false,
      form: this.$form.createForm(this),
      fullCol: { span: 24 },
      rds: []
    }
  },
  watch: {
    row () {
      this.inputRequired = false
    }
  },
  methods: {
    moment,
    openEdit (inputRequired) {
      this.inputRequired = inputRequired
      if (this.inputRequired) {
        this.$nextTick(() => {
          if (this.row.applyDateTime) {
            this.row.applyDateTime = this.moment(this.row.applyDateTime, 'YYYY-MM-DD 00:00:00')
          }
          if (this.row.authDate) {
            this.row.authDate = this.moment(this.row.authDate, 'YYYY-MM-DD 00:00:00')
          }
          const formParams = Object.assign({}, this.row)
          if (!this.row.id) {
            formParams.mainType = this.patentTypes[this.patentPlan.patentType]
            formParams.inventor = this.patentPlan.firstInventor
            formParams.patentName = this.patentPlan.patentName
          }
          this.$initForm(this.form, formParams)
        })
      }
    },
    save () {
      this.spinning = true
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.spinning = false
          return
        }
        values.id = this.row.id
        values.patentPlanId = this.row.patentPlanId
        this.$http.post('/patentPlan/savePatent', values).then(res => {
          if (res.success && res.data) {
            this.$message.success('操作成功')
            this.patentPlan.patentNo = this.row.patentNo = values.patentNo
            this.$emit('getInfo')
          } else {
            this.$message.error(res.errorMessage || '操作失败')
          }
          this.spinning = false
        })
      })
    },
    checkpatentNo () {
      const patentNo = this.form.getFieldValue('patentNo')
      if (patentNo) {
        this.$http.get('/patentPlan/checkPatentNo', { params: { patentNo: patentNo, patentId: this.row.id } })
          .then(res => {
            if (!res.data) {
              this.checked = false
              this.form.setFields({ 'patentNo': { value: patentNo, errors: [new Error('该专利号已存在')] } })
            } else {
              this.checked = true
              this.form.setFields({ 'patentNo': { value: patentNo } })
            }
          })
      }
    }
  }
}
</script>

<style>
.colClass{
  margin-bottom:-4px;
  padding-right:8px;
}
  .colClass.ant-form-item-label{
    text-align:left;
    padding-bottom: 8px;
    color:aliceblue;

  }
  .colClass.ant-form-item-label > label::after {
    content: '';
  }
.colClass label{
  color:#99AEC2;
}
.colDiv{
  line-height: 18px;
}
</style>
