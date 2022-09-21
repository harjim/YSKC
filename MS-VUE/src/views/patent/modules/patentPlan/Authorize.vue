<!--
 * @Author: zdf
 * @Date: 2021-08-20 16:46:59
 * @LastEditTime: 2022-03-14 09:03:50
 * @LastEditors: lzh
 * @Description: 授权&授权缴费
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\Authorize.vue
-->
<template>
  <a-spin v-if="canShow" :spinning="spinning" tip="请稍后...">
    <a-divider orientation="left">授权信息</a-divider>
    <div style="text-align: right;">
      <a-button size="small" @click="openEdit(true)" v-if="!inputRequired">编辑</a-button>
      <a-button size="small" @click="openEdit(false)" v-else>取消</a-button>
    </div>

    <a-form :form="form" :colon="false">
      <div style="padding: 0 12px" :class="{ 'status-not-edit': !inputRequired }">
        <a-row :gutter="8">
          <a-col :span="6">
            <a-form-item label="授权日期">
              <div v-if="!inputRequired">{{ patentPlanInfo.authDate ? moment(patentPlanInfo.authDate).format('YYYY-MM-DD'): '-' }}</div>
              <a-date-picker v-else style="width:100%" v-decorator="['authDate', {rules:[{required: false, message: '请选择授权日期'}]}]" placeholder="请选择授权日期" @change="authDateChange" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="授权缴费截至日期">
              <div v-if="!inputRequired">{{ patentPlanInfo.authFeeDate ? moment(patentPlanInfo.authFeeDate).format('YYYY-MM-DD'): patentPlanInfo.authDate ? moment(patentPlanInfo.authDate).add(60, 'days').format('YYYY-MM-DD'): '-' }}</div>
              <a-date-picker v-else style="width:100%" v-decorator="['authFeeDate', {rules:[{required: false, message: '请选择授权缴费截至日期'}]}]" placeholder="请选择授权缴费截至日期" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="应缴费用(元)">
              <div v-if="!inputRequired">{{ patentPlanInfo.authFee || '-' }}</div>
              <a-input-number
                v-else
                :max="$store.state.maxOrder"
                :min="0"
                :precision="0"
                style="width:100%"
                v-decorator="['authFee', {rules:[{required: false, message: '请输入应缴费用(元)'}]}]"
                placeholder="请输入应缴费用(元)" />
            </a-form-item>
          </a-col>
          <a-col :span="6" v-if="currentStep === 7">
            <a-form-item label="下证日期">
              <div v-if="!inputRequired">{{ patentPlanInfo.issueDate ? moment(patentPlanInfo.issueDate).format('YYYY-MM-DD'): '-' }}</div>
              <a-date-picker v-else style="width:100%" v-decorator="['issueDate', {rules:[{required: false, message: '请选择下证日期'}]}]" placeholder="请选择下证日期" />
            </a-form-item>
          </a-col>
        </a-row>
        <div style="text-align:right;" v-if="inputRequired">
          <a-button v-if="control.edit" @click="onSave()">{{ this.currentStep === 6 ? '暂存' : '保存' }}</a-button>
          <a-button v-if="auditPermission" style="margin-left:10px;" type="primary" @click="onSubmit()">提交</a-button>
        </div>
      </div>
    </a-form>
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
    patentPlanInfo: {
      type: Object,
      required: true
    },
    patentFiles: {
      type: Object,
      required: true
    },
    currentStep: {
      type: Number,
      default: 0
    },
    auditPermission: {
      type: Boolean,
      default: false
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
      rds: [],
      control: {
        edit: this.$auth('patent:patentPlanNew:edit')
      }
    }
  },
  computed: {
    canShow () {
      return this.currentStep >= 6
    }
  },
  created () {
    this.openEdit(this.currentStep === 6 || (this.currentStep === 7 && this.row.status !== 1))
  },
  methods: {
    moment,
    openEdit (inputRequired) {
      this.inputRequired = inputRequired
      if (this.inputRequired) {
        this.$nextTick(() => {
          const formParams = Object.assign({}, this.patentPlanInfo)
          formParams.authDate = formParams.authDate && this.moment(formParams.authDate)
          formParams.authFeeDate = formParams.authFeeDate && this.moment(formParams.authFeeDate)
          if (this.currentStep === 7) {
            formParams.issueDate = formParams.issueDate && this.moment(formParams.issueDate)
          } else {
            delete formParams.issueDate
          }
          this.$initForm(this.form, formParams)
        })
      }
    },
    onSave (isSubmit) {
      return new Promise((resolve, reject) => {
        this.form.validateFields((errors, values) => {
          if (errors) {
            return resolve(false)
          }
          const params = {
            ...values,
            patentPlanId: this.row.id,
            patentNo: this.row.patentNo,
            instanceId: this.row.instanceId,
            id: this.patentPlan.id,
            patentPlanInfoId: this.patentPlanInfo.id
          }
          this.spinning = true
          this.$http.post('/patentPlanNew/savePatent', params).then(res => {
            if (res.success && res.data) {
              if (!isSubmit) {
                this.$message.success('操作成功')
                this.$emit('update')
              }
              resolve(true)
            } else {
              this.$message.error(res.errorMessage || '操作失败')
              resolve(false)
            }
            this.spinning = false
          }).catch(
            // this.$message.error('catch异常')
          ).finally(() => {
            this.spinning = false
          })
        })
      })
    },
    async onSubmit () {
      try {
        switch (this.currentStep) {
          case 6:
            if (!(this.patentFiles[5] && this.patentFiles[5].length)) {
              this.$message.error('需上传授权通知书, 请上传后重新操作!')
              return
            }
            break
          case 7:
            if (!(this.patentFiles[11] && this.patentFiles[11].length)) {
              this.$message.error('需上传办登缴费, 请上传后重新操作!')
              return
            }
            break
          default:
            break
        }
        const result = await this.onSave(true)
        if (result) {
          const params = {
            pass: true,
            instanceId: this.row.instanceId,
            nodeId: this.row.curNodeId
          }
          this.$http.post('/patentPlanNew/patentAudit', params).then(res => {
            if (res.success && res.data) {
              this.$message.success('提交成功')
              this.$emit('update')
            } else {
              this.$message.error(res.errorMessage || '操作失败')
            }
          })
        }
      } catch (err) {}
    },
    authDateChange (_, dateString) {
      if (!this.form.getFieldValue('authFeeDate')) {
        this.form.setFieldsValue({ 'authFeeDate': moment(dateString).add(60, 'days') })
      }
    }
  }
}
</script>
