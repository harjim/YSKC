<template>
  <a-tree-select
    showSearch
    :dropdownStyle="{ maxHeight: '350px', overflow: 'scroll', width: '150px' }"
    :treeData="deptTree"
    :allowClear="true"
    :value="val"
    treeNodeFilterProp="title"
    :placeholder="placeholder"
    @change="change"
    treeDefaultExpandAll
    :disabled="readonly"
    :multiple="multiple"
  ></a-tree-select>
</template>
<script>
export default {
  name: 'DeptSelect',
  props: {
    // eslint-disable-next-line vue/require-default-prop
    value: [String, Number, Array],
    placeholder: {
      type: String,
      default: '所属部门'
    },
    readonly: {
      type: Boolean,
      default: false
    },
    multiple: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      deptTree: [],
      val: this.value
    }
  },
  watch: {
    value(val) {
      this.val = typeof val === 'number' ? val.toString() : val
    }
  },
  created() {
    this.$getDeptTree().then((res) => {
      this.deptTree = res
    })
  },
  methods: {
    change(v, l, e) {
      const fPath = e.triggerNode && e.triggerNode.eventKey ? e.triggerNode.eventKey : ''
      this.$emit('change', v, fPath)
      this.$emit('input', v, fPath)
    }
  }
}
</script>
