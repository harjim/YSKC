/**
 * @Author        : hzp
 * @Date          : 2022-09-24 11:40:21
 * @FilePath      : \YSIS\RS-VUE\src\views\project\hourSummary\ProjectsExportsModal.vue
 * @Description   :
 * @LastEditors   : hzp
 * @LastEditTime  : 2022-09-24 11:51:23
 */

<template>
  <a-modal v-model="visible" title="按RD导出">
    <a-form :form="form" layout="inline">
      <a-form-item label="项目">
        <a-tree-select
          show-search
          style="min-width: 200px; max-width: 1120px;"
          placeholder="请选择导出项目"
          allow-clear
          multiple
          tree-checkable
          treeDefaultExpandAll
          dropdownMatchSelectWidth
          :show-checked-strategy="SHOW_PARENT"
          :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
          :treeData="projects"
          v-decorator="[
            'projects',
            { rules: [{ required: true, message: '请选择导出项目' }] }
          ]"
        ></a-tree-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { TreeSelect } from 'ant-design-vue'

const SHOW_PARENT = TreeSelect.SHOW_PARENT

export default {
  props: {
    projects: {
      type: Array,
      default: () => ([])
    }
  },
  data () {
    return {
      SHOW_PARENT,
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
