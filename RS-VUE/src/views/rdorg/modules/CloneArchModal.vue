<template>
  <a-modal
    :title="title"
    :visible="visible"
    width="1000px"
    :maskClosable="false"
    :footer="null"
    @cancel="closeModal"
  >
    <div style="text-align: center;overflow:auto;padding:10px;">
      <vue2-org-tree
        :data="treeNodes"
        :horizontal="horizontal"
        :collapsable="true"
        @on-expand="onExpand"
        :render-content="renderContent"
      ></vue2-org-tree>
    </div>
  </a-modal>
</template>

<script>
import Vue2OrgTree from 'vue2-org-tree'
export default {
  components: {
    Vue2OrgTree
  },
  data () {
    return {
      treeNodes: {},
      title: '',
      visible: false,
      currentNode: {},
      horizontal: false,
      year: undefined
    }
  },
  methods: {
    renderContent (h, data) {
      return (<a-button onclick={() => this.cloneNode(data)}>{data.title}</a-button>)
    },
    showModal (treeNodes, currentNode, year) {
      this.year = year
      this.horizontal = localStorage.getItem('horizontal') === 'true'
      this.treeNodes = this.$deepClone(treeNodes)
      this.toggleExpand(this.treeNodes, true)
      this.title = `克隆到[${currentNode.title}]部门下`
      this.currentNode = currentNode
      this.visible = true
    },
    cloneNode (node) {
      if (Number(node.parentId) === Number(this.currentNode.value)) {
        this.$message.warning(`该部门[${node.title}]已存在[${this.currentNode.title}]部门下,请选择其他部门。`)
        return
      }
      if (this.currentNode.children !== null && this.currentNode.children !== undefined && this.currentNode.children.length > 0) {
        for (let i = 0; i < this.currentNode.children.length; i++) {
          const temp = this.currentNode.children[i]
          if (temp.title === node.title) {
            this.$message.warning(`当前部门[${this.currentNode.title}]已存在相同的子部门[${node.title}]`)
            return
          }
        }
      }
      const self = this
      this.$confirm({
        title: `您确定要克隆[${node.title}]部门吗?`,
        content: <b>该操作会克隆选中的部门及其所有子部门。</b>,
        onOk () {
          var values = {
            parentId: self.currentNode.value,
            parentIdentity: self.currentNode.key,
            cloneId: node.value,
            cloneIdentity: node.key,
            parentLevel: self.currentNode.level,
            cloneLevel: node.level,
            currentYear: self.year
          }
          self.$http.post('/rdDept/cloneNode', values).then(res => {
            if (res.success && res.data) {
              self.$emit('ok', true)
              self.closeModal()
            } else {
              self.$message.error(res.errorMessage ? res.errorMessage : '克隆节点失败')
            }
          })
        },
        onCancel () {
        }
      })
    },
    toggleExpand (data, val) {
      var _this = this
      if (Array.isArray(data)) {
        data.forEach(function (item) {
          _this.$set(item, 'expand', val)
          if (item.children) {
            _this.toggleExpand(item.children, val)
          }
        })
      } else {
        this.$set(data, 'expand', val)
        if (data.children) {
          _this.toggleExpand(data.children, val)
        }
      }
    },
    onExpand (e, data) {
      if ('expand' in data) {
        data.expand = !data.expand
        // if (!data.expand && data.children) {
        //   this.collapse(data.children)
        // }
      } else {
        this.$set(data, 'expand', true)
      }
    },
    closeModal () {
      this.visible = false
      this.currentNode = {}
    }
  }
}
</script>

<style>
.org-tree-node-label .org-tree-node-label-inner {
  padding: 0;
}
</style>
