<!--
 * @Author:
 * @Date: 2021-05-06 14:16:22
 * @LastEditTime: 2022-03-29 13:39:41
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\components\Org\OrgNode.vue
-->
<template>
  <div :class="{ 'tree': isRoot}">
    <div style="display: flex; height: 20px;" v-if="!isRoot">
      <div style="width: 50%; border-right: 1px solid ;" v-if="type===1 || type===0"></div>
      <div style="width: 50%; border-right: 1px solid ; border-top: 1px solid ;" v-if="type===2 || type===3"></div>
      <div style="width: 50%; border-top: 1px solid ;" v-if="type===1 || type===3"></div>
    </div>
    <Node
      :data="data"
      :onClick="onClick"
      :isRoot="isRoot"
      :modify="modify"
      :orderLeft="orderLeft"
      :orderRight="orderRight"
      :sortNode="sortNode"></Node>
    <div class="vline" v-if="(data.committee && data.committee.length <= 0 || !data.committee) && data.children && data.children.length > 0">
      <div style="height: 100%;width: 50%;border-right: 1px solid ; justify-content:flex-end" class="root-line-box" ></div>
      <div style="height: 100%;width: 50%;justify-content:flex-start" class="root-line-box"></div>
    </div>
    <template v-if="data.committee">
      <div class="vRootline" v-for="(committees,index) in data.committee" :key="index" style="width: 100%; display: flex; flex-direction: row-reverse;">
        <div
          v-for="(committee,i) in committees"
          :key="i"
          style="height:100%; width: 50%;"
          :class="{ 'committee-left': i===1 ,'committee-right':i===0}"
          class="root-line-box"
        >
          <CommitteeNode v-if="i===0" :data="committee" :onClick="onClick" :modify="modify"/>
          <CommitteeNode v-if="i===1" :lineDirection="true" :data="committee" :onClick="onClick" :modify="modify" />
        </div>
      </div>
    </template>
    <template v-if="data.children && data.children.length>0">
      <div class="children">
        <template v-if="data.children.length===1">
          <Org-node :data="data.children[0]" :currentYear="currentYear" :onClick="onClick" :modify="modify" :sortNode="sortNode"></Org-node>
        </template>
        <template v-else>
          <Org-node
            v-for="(d,i) in data.children"
            :data="d"
            :key="d.key"
            :currentYear="currentYear"
            :modify="modify"
            :onClick="onClick"
            :sortNode="sortNode"
            :orderLeft="i<data.children.length && i > 0"
            :orderRight="i < data.children.length - 1"
            :type="i===0 ? 1 : ( i===(data.children.length-1) ? 2 : 3 )"/>
        </template>
      </div>
    </template>
  </div>
</template>
<script>
import CommitteeNode from './committeeNode'
import Node from './Node'
export default {
  name: 'OrgNode',
  components: { CommitteeNode, Node },
  props: {
    data: {
      type: Object,
      required: true
    },
    isRoot: {
      type: Boolean,
      default: false
    },
    type: {
      type: Number,
      default: 0
    },
    currentYear: {
      type: Number,
      required: true
    },
    onClick: {
      type: Function,
      required: true
    },
    sortNode: {
      type: Function,
      required: true
    },
    modify: {
      type: Boolean,
      required: true
    },
    orderLeft: {
      type: Boolean,
      default: false
    },
    orderRight: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      showMenu: false,
      showCommitteeMenu: false
    }
  },
  methods: {
    onHtext (boolean) {
      this.showMenu = boolean
    },
    onVtext (boolean) {
      this.showMenu = boolean
    },
    onCommittee (boolean) {
      this.showCommitteeMenu = boolean
    }
  }
}
</script>
<style lang="less">
.tree {
  // display: inline-block;
  display: block;
  text-align: center;
  box-sizing: border-box;
  letter-spacing: 0.1em;
}
.vRootline {
  display: flex;
  height: 4em !important;
  .root-line-box {
    display: flex;
    flex-direction: row;
  }
}
.vline {
  height: 1.2em ;
}
.children {
  // html2canvas不支持display: -webkit-box;
  // display: flex;
  display: -webkit-box;
  -webkit-box-pack: center;
  justify-content: center;
}
.committee-left {
justify-content:flex-end;
}
.committee-right {
  justify-content:flex-start;
  border-left: 1px solid
}
</style>
