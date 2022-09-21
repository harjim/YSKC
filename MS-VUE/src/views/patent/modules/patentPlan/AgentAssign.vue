<!--
 * @Author: your name
 * @Date: 2022-02-23 14:47:22
 * @LastEditors: lzh
 * @LastEditTime: 2022-03-08 22:26:49
 * @Description: 代理人分配步骤
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\AgentAssign.vue
-->

<template>
  <div v-if="canShow">
    <a-divider orientation="left">代理人信息</a-divider>
    <div style="padding: 0 12px">
      <a-form :form="form" :colon="false">
        <a-row :gutter="8">
          <a-col :span="12">
            <a-form-item label="代理人">
              <a-select v-if="isEdit" v-decorator="['masterId', {rules: [{required: true, message: '请选择代理人'}]}]" placeholder="请选择代理人" @change="selectChange" :disabled="!auditPermission">
                <a-select-option v-for="item in selectList" :key="item.id">{{ item.title }}</a-select-option>
              </a-select>
              <div v-else>{{ row.masterName }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="联系方式">
              <a-input v-if="isEdit" v-decorator="['linkTel']" placeholder="请输入联系方式" disabled/>
              <div v-else>{{ row.masterLinkTel }}</div>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <div style="text-align: right;" v-if="isEdit">
        <a-button v-if="auditPermission" style="margin-left: 8px" type="primary" @click="onSumbit()">提交</a-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    currentStep: {
      type: Number,
      default: 0
    },
    row: {
      type: Object,
      default: () => {}
    },
    patentInfo: {
      type: Object,
      default: () => {}
    },
    auditPermission: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      form: this.$form.createForm(this),
      selectList: []
    }
  },
  created () {
    if (this.isEdit) {
      this.getSelectList()
      this.$nextTick(() => {
        this.form.setFieldsValue({ masterId: this.row.masterId || undefined, linkTel: this.row.masterLinkTel })
      })
    }
  },
  computed: {
    canShow () {
      return this.currentStep >= 2
    },
    isEdit () {
      return this.currentStep === 2
    }
  },
  methods: {
    getSelectList () {
      this.$http.get('patentMaster/getSelect').then(res => {
        if (res.success && res.data) {
          this.selectList = res.data
        } else {
          this.$message.error('获取数据失败')
        }
      })
    },
    selectChange (key) {
      const linkTel = this.selectList.find(item => {
        return item.id === key
      }).linkTel
      this.form.setFieldsValue({ linkTel: linkTel })
    },
    onSumbit () {
      this.form.validateFields((errors, values) => {
        if (errors) {
          return
        }
        const parmas = {
          pass: true,
          instanceId: this.row.instanceId,
          nodeId: this.row.curNodeId,
          masterId: values.masterId
        }
        this.$http.post('/patentPlanNew/patentAudit', parmas).then(res => {
          if (res.success && res.data) {
            this.$message.success('提交成功')
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
