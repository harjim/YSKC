<!--
 * @Author:
 * @Date: 2020-11-25 16:36:10
 * @LastEditTime: 2021-02-03 17:25:31
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\modules\TRLListModal.vue
-->
<template>
  <a-modal
    :width="600"
    :visible="visible"
    title="选择TRL"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
    :getContainer="getContainer"
  >
    <a-row :gutter="24" v-for="(item,key) in stageTRL" :key="key">
      <a-col :span="1">
        <a-checkbox
          :disabled="disabledKeys[key]"
          :checked="allChecks[key]"
          @change="onChange(key)"
          style="padding:5px;"
        ></a-checkbox>
      </a-col>
      <a-col :span="22">
        <div style="padding-top:5px;">{{ item.TRL }}</div>
      </a-col>
    </a-row>
  </a-modal>
</template>
<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
const stageTRL = {
  TRL1: { TRL: 'TRL1:明确该技术有关的基本原理,形成报告' },
  TRL2: { TRL: 'TRL2：基于科学原理提出实际应用设想，形成技术方案' },
  TRL3: { TRL: 'TRL3：关健功能和特性在实验室条件下通过试验或仿真完成了原理性验证' },
  TRL4: { TRL: 'TRL4：关键功能试样/模块在实验室通过了试验或仿真验证' },
  TRL5: { TRL: 'TRL5：形成产品初样（部件级），在模拟使用环境中进行了试验或仿真验证' },
  TRL6: { TRL: 'TRL6：形成产品正样（系统级），通过高遇真度的模拟使用环境中进行验证' },
  TRL7: { TRL: 'TRL7：形成整机产品工程样机、在真实使用环境下通过试验验证' },
  TRL8: { TRL: 'TRL8：实际产品设计定型,通过功能、性能测试：可进行产品小批量生产' },
  TRL9: { TRL: 'TRL9：系统产品批量生产，功能、性能、质量等特性在实际任务中得到充分验证' }
}
export default {
  data () {
    return {
      stageTRL,
      domName,
      visible: false,
      confirmLoading: false,
      trls: {},
      allChecks: {},
      disabledKeys: {}
    }
  },
  methods: {
    getContainer () {
      return popupContainer(this.domName)
    },
    onChange (key) {
      this.$set(this.allChecks, key, !this.allChecks[key])
    },
    handleSubmit () {
      this.confirmLoading = true
      const keyObj = {}
      for (const key in this.allChecks) {
        if (!this.disabledKeys[key] && this.allChecks[key]) {
          keyObj[key] = true
        }
      }
      this.$emit('ok', keyObj)
      this.confirmLoading = false
      this.visible = false
    },
    showModal (trls) {
      this.allChecks = {}
      this.disabledKeys = {}
      if (trls) {
        trls.forEach(item => {
          this.$set(this.allChecks, item.key, true)
          this.$set(this.disabledKeys, item.key, true)
        })
      }
      this.visible = true
      this.trls = trls
    }
  }
}
</script>
