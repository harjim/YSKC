<template>
  <!-- <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol"> -->
  <!-- v-decorator="['deptId',{rules:[{required: required, message: '请选择部门'}]}]" -->
  <a-tree-select
    v-model="deptId"
    showSearch
    :dropdownStyle="{ maxHeight: '350px', overflow: 'scroll', width: '150px' }"
    :treeData="deptTree"
    :allowClear="true"
    treeNodeFilterProp="title"
    :placeholder="placeholder"
    @change="onChange"
    treeDefaultExpandAll
    :disabled="disabled"
  ></a-tree-select>
  <!-- </a-form-item> -->
</template>

<script>
export default {
  name: 'DeptSelectOnly',
  props: {
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      deptTree: [],
      deptId: undefined,
      required: true,
      placeholder: ''
    }
  },
  mounted () {
    this.loadDept()
  },
  watch: {
    deptId (nowId) {
      this.$emit('deptSelect', nowId)
    }
  },
  methods: {
    search () {
      if (this.isRd) {
        this.deptId = undefined
        this.loadDept()
      }
    },
    loadDept () {
      const url = '/dept/tree'
      this.placeholder = '请选择所属部门'
      return this.$http.get(url)
        .then(res => {
          this.deptTree = res.data
          return this.deptTree
        })
    },
    // getExpand (tree, arr) {
    //   tree.forEach(node => {
    //     if (node.children && node.children.length > 0) {
    //       arr.push(node.key)
    //       this.getExpand(node.children, arr)
    //     }
    //   })
    //   return arr
    // },
    onChange (v, l, e) {
      let fullPath = ''
      let deptName = ''
      if (e.triggerNode) {
        fullPath = e.triggerNode.eventKey
        deptName = e.triggerNode.title
      }
      this.$emit('deptFullPath', fullPath, deptName)
    },
    setDept (id) {
      if (id <= 0 || id === '0' || id === null || id === undefined) {
        this.deptId = undefined
      } else {
        this.deptId = id.toString()
      }
    }
  }
}
</script>
