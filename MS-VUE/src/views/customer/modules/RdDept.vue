<template>
  <div style="position: fixed;z-index:999">
    <div v-if="treeData">
      <div>
        <a-radio-group v-model="horizontal">
          <a-radio :value="false">横排</a-radio>
          <a-radio :value="true">竖排</a-radio>
        </a-radio-group>
      </div>
      <div>
        <vue2-org-tree
          :data="treeData"
          :horizontal="horizontal"
          label-class-name="bg-white"
          @on-expand="onExpand"
          :collapsable="true"
          :render-content="renderContent"
        ></vue2-org-tree>
      </div>
    </div>
    <div v-else>无研发架构</div>
  </div>
</template>

<script>
import Vue2OrgTree from 'vue2-org-tree'
export default {
  components: {
    Vue2OrgTree
  },
  props: {
    rdDepts: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      horizontal: false,
      treeData: undefined
    }
  },
  created () {
    if (this.rdDepts && this.rdDepts.length > 0) {
      this.treeData = this.rdDepts[0]
      this.toggleExpand(this.treeData, true)
    }
  },
  methods: {
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
    renderContent (h, data) {
      return (<span> <a-button>{data.title}</a-button></span >)
    },
    onExpand (e, data) {
      if ('expand' in data) {
        data.expand = !data.expand
      } else {
        this.$set(data, 'expand', true)
      }
    }
  }
}
</script>

<style>
.bg-white {
  background-color: white;
}
.org-tree-node-label .org-tree-node-label-inner {
  padding: 0;
}
</style>
