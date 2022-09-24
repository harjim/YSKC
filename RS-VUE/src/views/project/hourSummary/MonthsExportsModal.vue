/**
 * @Author        : hzp
 * @Date          : 2022-09-24 11:40:04
 * @FilePath      : \YSIS\RS-VUE\src\views\project\hourSummary\MonthsExportsModal.vue
 * @Description   :
 * @LastEditors   : hzp
 * @LastEditTime  : 2022-09-24 11:50:45
 */

<template>
  <a-modal v-model="visible" title="按月份导出">
    <a-form :form="form" layout="inline">
      <a-form-item label="月份">
        <a-select
          :options="months"
          style="width: 200px;"
          placeholder="请选择导出月份"
          v-decorator="[
            'months',
            { rules: [{ required: true, message: '请选择导出月份' }] }
          ]"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  props: {
    months: {
      type: Array,
      default: () => ([])
    }
  },
  data () {
    return {
      visible: false
    }
  },
  created () {
    this.form = this.$form.createForm(this)
  },
  methods: {
    show () {
      this.visible = true
    }
  }
}
</script>

<style lang="less" scoped>

</style>
