<template>
  <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
    <a-tree-select
      v-decorator="['deptId',{rules:[{required: required, message: '请选择部门'}]}]"
      showSearch
      :dropdownStyle="{ maxHeight: '350px', overflow: 'scroll', width: '150px' }"
      :treeData="deptTree"
      :allowClear="true"
      treeNodeFilterProp="title"
      placeholder="请选择部门"
      treeDefaultExpandAll
      @select="deptSelect"
    ></a-tree-select>
  </a-form-item>
</template>

<script>
export default {
  name: 'DeptSelect',
  data () {
    return {
      deptTree: [],
      deptId: undefined,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      required: true
    }
  },
  mounted () {
    this.loadDept()
  },
  methods: {
    loadDept () {
      return this.$getTree('dept')
        .then(res => {
          this.deptTree = res.tree
          return this.deptTree
        })
    },
    deptSelect (value) {
      this.$emit('deptSelect', value)
    },
    setDept (id, required) {
      if (id === 0 || id === '0' || id === null || id === undefined) {
        this.deptId = undefined
      } else {
        this.deptId = id.toString()
      }
      if (required === undefined || required === null) {
        this.required = true
      } else {
        this.required = required
      }
    }
  }
}
</script>
