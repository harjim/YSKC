<!--
 * @Author: zdf
 * @Date: 2021-08-20 16:46:59
 * @LastEditTime: 2022-03-14 09:03:52
 * @LastEditors: lzh
 * @Description: 专利信息
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\PatentInfoNew.vue
-->
<template>
  <a-spin v-if="canShow" :spinning="spinning" tip="请稍后...">
    <a-divider orientation="left">专利信息</a-divider>
    <div style="text-align: right;">
      <a-button size="small" @click="openEdit(true)" v-if="!inputRequired">编辑</a-button>
      <a-button size="small" @click="openEdit(false)" v-else>取消</a-button>
    </div>

    <a-form :form="form" :colon="false">
      <div style="padding: 0 12px" :class="{ 'status-not-edit': !inputRequired }">
        <a-row :gutter="8">
          <a-col :span="6">
            <a-form-item label="专利号">
              <div v-if="!inputRequired">{{ patentPlan.patentNo || '-' }}</div>
              <a-input @blur="checkpatentNo" v-else v-decorator="['patentNo', {rules:[{required: inputRequired, message: '请输入专利号'}]}]" placeholder="请输入专利号" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="专利名称">
              <div v-if="!inputRequired">{{ patentPlan.patentName || '-' }}</div>
              <a-input v-else v-decorator="['patentName', {rules:[{required: inputRequired, message: '请输入专利名称'}]}]" placeholder="请输入专利名称" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="专利类型">
              <div v-if="!inputRequired">{{ patentPlan.mainType || '-' }}</div>
              <a-select v-else v-decorator="['mainType', {rules:[{required: inputRequired, message: '请选择专利类型'}]}]" placeholder="请选择专利类型">
                <a-select-option v-for="item in patentType" :key="item.label">{{ item.label }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="申请日期">
              <div v-if="!inputRequired">{{ patentPlan.applyDateTime ? moment(patentPlan.applyDateTime).format('YYYY-MM-DD'): '-' }}</div>
              <a-date-picker v-else style="width:100%" v-decorator="['applyDateTime', {rules:[{required: false, message: '请选择申请日期'}]}]" placeholder="请选择申请日期" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="发明人">
              <div v-if="!inputRequired">{{ patentPlan.inventor || '-' }}</div>
              <a-input v-else v-decorator="['inventor', {rules:[{required: inputRequired, message: '请输入发明人'}]}]" placeholder="请输入发明人" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="权利要求数量">
              <div v-if="!inputRequired">{{ patentPlan.claimNum || '-' }}</div>
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
          <a-col :span="6">
            <a-form-item label="使用次数">
              <div v-if="!inputRequired">{{ patentPlan.usedCnt || '-' }}</div>
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
          <a-col :span="24">
            <a-form-item
              class="textarea"
              label="权利要求内容"
              :help="()=>{if(!inputRequired)return; const r = form.getFieldValue('claimContent'); return `(${!r? 0 : r.length > 3000 ? 3000 : r.length}/3000)`}">
              <div v-if="!inputRequired">{{ patentPlan.claimContent || '-' }}</div>
              <a-textarea
                v-else
                v-decorator="['claimContent', {rules:[{required: false, message: '请输入权利要求内容'}]}]"
                :maxLength="1000"
                :rows="5"
                placeholder="请输入权利要求内容">
              </a-textarea>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item
              class="textarea"
              label="说明书内容"
              :help="()=>{if(!inputRequired)return; const r = form.getFieldValue('specification'); return `(${!r? 0 : r.length > 2000 ? 2000 : r.length}/2000)`}">
              <div v-if="!inputRequired">{{ patentPlan.specification || '-' }}</div>
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
      </div>
      <a-divider orientation="left">受理信息</a-divider>
      <div style="padding: 0 12px">
        <a-row :gutter="8">
          <a-col :span="6">
            <a-form-item label="提交国知局日期">
              <div v-if="!inputRequired">{{ patentPlanInfo.submittedDate ? moment(patentPlanInfo.submittedDate).format('YYYY-MM-DD'): '-' }}</div>
              <a-date-picker v-else style="width:100%" v-decorator="['submittedDate', {rules:[{required: true, message: '请选择提交国知局日期'}]}]" placeholder="请选择提交国知局日期" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="受理通知日期">
              <div v-if="!inputRequired">{{ patentPlanInfo.acceptNoticeDate ? moment(patentPlanInfo.acceptNoticeDate).format('YYYY-MM-DD'): '-' }}</div>
              <a-date-picker v-else style="width:100%" v-decorator="['acceptNoticeDate', {rules:[{required: true, message: '请选择受理通知日期'}]}]" placeholder="请选择受理通知日期" @change="acceptFeeDateChange" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <!-- 修改时间 -->
            <a-form-item label="受理缴费截至日期">
              <div v-if="!inputRequired">{{ patentPlanInfo.acceptFeeDate ? moment(patentPlanInfo.acceptFeeDate).format('YYYY-MM-DD'): patentPlanInfo.acceptNoticeDate ? moment(patentPlanInfo.acceptNoticeDate).add(60, 'days').format('YYYY-MM-DD'): '-' }}</div>
              <a-date-picker v-else style="width:100%" v-decorator="['acceptFeeDate', {rules:[{required: true, message: '请选择受理缴费截至日期'}]}]" placeholder="请选择受理缴费截至日期" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="应缴费用(元)">
              <div v-if="!inputRequired">{{ patentPlanInfo.acceptFee || '-' }}</div>
              <a-input-number
                v-else
                :max="$store.state.maxOrder"
                :min="0"
                :precision="0"
                style="width:100%"
                v-decorator="['acceptFee', {rules:[{required: true, message: '请输入应缴费用(元)'}]}]"
                placeholder="请输入应缴费用(元)" />
            </a-form-item>
          </a-col>
        </a-row>
        <div style="text-align:right;">
          <template v-if="inputRequired">
            <a-button v-if="control.edit" @click="onSave()">{{ this.currentStep === 4 ? '暂存' : '保存' }}</a-button>
            <a-button v-if="auditPermission" style="margin-left:10px;" type="primary" @click="onSubmit()">提交</a-button>
          </template>
          <a-popconfirm title="是否受理缴费完成?" @confirm="onComplete()" v-if="currentStep === 5 && auditPermission">
            <a-button style="margin-left:10px;" type="primary">受理缴费完成</a-button>
          </a-popconfirm>
        </div>
      </div>
    </a-form>
  </a-spin>
</template>

<script>
import moment from 'moment'
import SearchSelect from '@/components/Selects/SearchSelect.vue'
import { mapState } from 'vuex'
export default {
  components: {
    SearchSelect
  },
  props: {
    currentStep: {
      type: Number,
      default: 0
    },
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
    ...mapState({
      patentType: state => state.enums.patentType
    }),
    canShow () {
      return this.currentStep >= 4
    }
  },
  created () {
    this.openEdit(this.currentStep === 4)
  },
  methods: {
    moment,
    openEdit (inputRequired) {
      this.inputRequired = inputRequired
      if (this.inputRequired) {
        this.$nextTick(() => {
          const { applyDateTime, mainType, patentName } = this.patentPlan
          // const mainTypes = this.patentType.filter(elem => elem.value === confirmType)[0]
          // let type = mainType
          // if (!mainType && mainTypes && mainTypes.label) {
          //   type = mainTypes.label
          // }
          const { confirmType, confirmName } = this.row
          const { submittedDate, acceptNoticeDate, acceptFeeDate, acceptFee } = this.patentPlanInfo
          const formParams = Object.assign(this.patentPlan, {
            applyDateTime: applyDateTime && this.moment(applyDateTime),
            submittedDate: submittedDate && this.moment(submittedDate),
            acceptNoticeDate: acceptNoticeDate && this.moment(acceptNoticeDate),
            acceptFeeDate: acceptFeeDate && this.moment(acceptFeeDate),
            acceptFee: acceptFee,
            // mainType: [confirmType],
            mainType: mainType || this.patentType.filter(elem => elem.value === confirmType)[0].label,
            patentName: patentName || confirmName
            // mainType = this.patentTypes[this.patentPlan.patentType] || this.patentTypes[this.patentPlan.confirmType]
            // ,patentName = this.patentPlan.patentName || this.patentPlan.confirmName
          })
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
          }).catch().finally(() => {
            this.spinning = false
          })
        })
      })
    },
    async onSubmit () {
      try {
        if (!(this.patentFiles[3] && this.patentFiles[3].length)) {
          this.$message.error('需上传受理通知书, 请上传后重新操作!')
          return
        }
        const result = await this.onSave(true)
        if (result) {
          const parmas = {
            pass: true,
            instanceId: this.row.instanceId,
            nodeId: this.row.curNodeId
          }
          this.$http.post('/patentPlanNew/patentAudit', parmas).then(res => {
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
    onComplete () {
      const parmas = {
        pass: true,
        instanceId: this.row.instanceId,
        nodeId: this.row.curNodeId
      }
      if (!(this.patentFiles[10] && this.patentFiles[10].length)) {
        this.$message.error('需上传受理缴费, 请上传后重新操作!')
        return
      }
      this.$http.post('/patentPlanNew/patentAudit', parmas).then(res => {
        if (res.success && res.data) {
          this.$message.success('提交成功')
          this.$emit('update')
        } else {
          this.$message.error(res.errorMessage || '操作失败')
        }
      })
    },
    checkpatentNo () {
      const patentNo = this.form.getFieldValue('patentNo')
      if (patentNo) {
        this.$http.get('/patentPlanNew/checkPatentNo', { params: { patentNo: patentNo, patentId: this.patentPlan.id } })
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
    },
    acceptFeeDateChange (_, dateString) {
      if (!this.form.getFieldValue('acceptFeeDate')) {
        this.form.setFieldsValue({ 'acceptFeeDate': moment(dateString).add(60, 'days') })
      }
    }
  }
}
</script>
<style lang="less" scoped>

</style>
