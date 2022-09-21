<!--
 * @Author: your name
 * @Date: 2022-02-23 14:47:22
 * @LastEditors: lzh
 * @LastEditTime: 2022-03-16 14:30:42
 * @Description: 定稿步骤
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\Finalization.vue
-->

<template>
  <a-card v-show="currentStep === 3 && auditPermission" title="定稿说明" style="margin-top: 16px;">
    <!-- <a-card v-if="currentStep === 3" title="定稿说明" style="margin-top: 16px;"> -->
    <a-form :form="form">
      <a-row :gutter="16">
        <a-col span="12">
          <a-form-item label="专利名称" :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }">
            <a-input
              placeholder="请输入专利名称"
              v-decorator="['confirmName', { rules: [{ required: true, message: '请输入专利名称' }] }]"
              :maxLength="200"
            />
          </a-form-item>
        </a-col>
        <a-col span="12">
          <a-form-item label="申请类型" :label-col="{ span: 4 }" :wrapper-col="{ span: 20 }">
            <a-select
              v-decorator="['confirmType', { rules: [{ required: true, message: '请选择申请类型' }] }]"
              placeholder="请选择申请类型"
            >
              <a-select-option v-for="v in patentType" :key="v.value">{{ v.label }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-form-item class="textarea" :help="()=>{const r = form.getFieldValue('suggestion'); return `(${!r? 0 : r.length > 100 ? 100 : r.length}/100)`}">
          <a-textarea v-decorator="['suggestion', {rules: [{required: true, message: '请输入定稿说明'}]}]" placeholder="请输入定稿说明" :rows="4" :maxLength="100">
          </a-textarea>
        </a-form-item>
      </a-row>
    </a-form>
    <div style="text-align: center;">
      <a-popconfirm
        title="确定提交吗?"
        @confirm="submit()"
      >
        <a-button>提交</a-button>
      </a-popconfirm>
    </div>
  </a-card>
</template>

<script>
import { mapState } from 'vuex'
export default {
  props: {
    row: {
      type: Object,
      default: () => {}
    },
    currentStep: {
      type: Number,
      default: 0
    },
    auditPermission: {
      type: Boolean,
      default: false
    },
    files: {
      type: Object,
      default: () => {}
    }
  },
  computed: {
    ...mapState({
      patentType: state => state.enums.patentType
    })
  },
  data () {
    return {
      form: this.$form.createForm(this)
    }
  },
  methods: {
    submit (pass) {
      this.form.validateFields((errors, values) => {
        if (errors) {
          return
        }
        if (!(this.files[2] && this.files[2].length)) {
          this.$message.error('需上传定稿, 请上传文件后重新操作!')
          return
        }
        const params = {
          ...values,
          pass: true,
          instanceId: this.row.instanceId,
          nodeId: this.row.curNodeId
        }
        this.$http.post('/patentPlanNew/patentAudit', params).then(res => {
          if (res.success && res.data) {
            this.$message.success('操作成功')
            this.$emit('update')
          } else {
            this.$message.error(res.errorMessage || '操作失败')
          }
        })
      })
    }
  }
}
</script>
