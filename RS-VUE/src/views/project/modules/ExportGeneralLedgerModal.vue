<!--
 * @Author: zdf
 * @Date: 2022-02-21 09:56:30
 * @LastEditTime: 2022-06-01 09:55:52
 * @LastEditors: zdf
 * @Description: 导出辅助帐/可选新版旧版
 * @FilePath: \RS-VUE\src\views\project\modules\ExportGeneralLedgerModal.vue
-->
<template>
  <a-modal
    :title="title"
    v-model="visible"
    :width="350"
    @cancel="visible=false"
    @ok="exportData"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-row style="text-align:center;">
      <a-radio-group v-model="old">
        <a-col :span="12">
          <a-radio :value="true">
            2015版
          </a-radio>
        </a-col>
        <a-col :span="12">
          <a-radio :value="false">
            2021版
          </a-radio>
        </a-col>
      </a-radio-group>
    </a-row>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      title: '',
      confirmLoading: false,
      old: true
    }
  },
  methods: {
    show (year) {
      this.old = true
      this.title = `导出[${year}]辅助帐`
      this.visible = true
    },
    exportData () {
      this.$emit('ok', this.old)
      this.visible = false
    }
  }
}
</script>

<style>

</style>
