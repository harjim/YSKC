<!--
 * @Author: ldx
 * @Date: 2021-05-12 15:37:01
 * @LastEditTime: 2021-06-28 15:43:10
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\components\Org\committeeNode.vue
-->
<template>
  <div style="display: flex;">
    <div v-if="!lineDirection" style="height:50%;width: 20px; border-bottom: 1px solid;"/>
    <div style="align-self:center;text-align: left;">
      <div class="textContainer" style="display: inline-block;" @mouseenter="onMouse(true)" @mouseleave="onMouse(false)">
        <span class="htext" style="margin-left: 0px; margin-right: 0px;">{{ data.title }}</span>
        <div v-if="modify" class="menu" >
          <ul :class="{'item_wrap':showMenu}">
            <li :class="{'item':showMenu}" v-if="this.$auth('rdorg:arch:rd:edit')"><a @click="onClick({key:'edit'},data,nodeType)">编辑</a></li>
            <li :class="{'item':showMenu}" v-if="this.$auth('rdorg:arch:rd:del')"><a @click="onClick({key:'del'},data,nodeType)">删除</a></li>
          </ul>
        </div>
      </div>
    </div>
    <div v-if="lineDirection" style="height:50%;width: 20px; border-bottom: 1px solid;" />
  </div>
</template>
<script>
export default {
  name: 'CommitteeNode',
  props: {
    data: {
      type: Object,
      required: true
    },
    lineDirection: { // 默认右边
      type: Boolean,
      default: false
    },
    onClick: {
      type: Function,
      required: true
    },
    modify: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
      showMenu: false,
      nodeType: 1
    }
  },
  methods: {
    onMouse (boolean) {
      if (!this.modify) return
      this.showMenu = boolean
    }
  }
}
</script>
<style lang="less" scoped>
.textContainer {
  display: inline-block;
  position: relative;
}
.htext {
  display:inline-block;
  border: 1px solid ;
  border-radius: 4px;
  padding-left: 0.6em;
  padding-right: 0.6em;
  padding-top: 0.3em;
  padding-bottom: 0.3em;
  white-space: nowrap;
  margin-left: 0.3em;
  margin-right: 0.3em;
}
.htext:hover,.vtext:hover {
  cursor: pointer;
  color:#40a9ff;
  border-color: #40a9ff;
}
.menu {
  position:absolute;
  display:flex;
  left: 0.3em;
  width: 140px;
  flex-direction: column;
  padding: 3px 0;
  .item_wrap {
    padding: 3px 0;
    border: 1px solid ;
    list-style: none;
    margin: 0;
    overflow: hidden;
    background-color: white;
    border-radius: 4px;
    .item {
      height: 30px;
    }
  }
  ul {
    list-style: none;
    margin: 0;
    overflow: hidden;
    padding: 0;
    border: 0 solid ;
    background-color: white;
    border-radius: 4px;
    z-index: 1;
    -webkit-transition: border 200ms ease;
    -moz-transition: border 000ms ease;
    -o-transition: border 200ms ease;
    transition: border 200ms ease;
    li {
      font-size: 14px;
      overflow: hidden;
      height: 0;
      -webkit-transition: height 200ms ease;
      -moz-transition: height 200ms ease;
      -o-transition: height 200ms ease;
      transition: height 200ms ease;
      :hover  {
        background-color: #e6f7ff;
      }
      a {
        color: #000000a6;
        display: block;
        text-align: left;
        padding: 5px 12px;
        line-height: 22px;
      }
    }
  }
}
</style>
