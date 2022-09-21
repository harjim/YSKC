<!--
 * @Author: ldx
 * @Date: 2021-05-12 16:10:49
 * @LastEditTime: 2021-06-18 16:21:58
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\components\Org\Node.vue
-->
<template>
  <div
    class="textContainer"
    @mouseenter="onMouse(true)"
    @mouseleave="onMouse(false)">
    <span
      v-if="menuDirection"
      :class="{'active':showMenu}"
      :style="{textAlign: getAlign()}"
      class="vtext"
      v-html="transformData(data.title)"></span>
    <span
      v-else
      :class="{'active':showMenu}"
      :style="{textAlign: getAlign()}"
      class="htext"
      v-html="transformData(data.title)"></span>
    <div
      v-if="modify && this.$auth(['add','clone','edit'],false,'rdorg:arch:rd') "
      :class="{'hMenu':!menuDirection ,'vMenu': menuDirection}">
      <ul :class="{'item-wrap':showMenu }">
        <li
          v-if="this.$auth('rdorg:arch:rd:edit') && (orderLeft || orderRight)"
          :class="{'item':showMenu}">
          <a
            v-if="orderLeft"
            :style="{textAlign:'left',width: orderRight ? '50%' : '100%'}"
            @click="sortNode(data.value,true)">
            <a-icon
              title="向左移"
              type="arrow-left"
            />
          </a>
          <a
            v-if="orderRight"
            :style="{textAlign:'right',width: orderLeft ? '50%' : '100%'}"
            @click="sortNode(data.value,false)">
            <a-icon
              title="向右移"
              type="arrow-right" />
          </a>
        </li>
        <li v-if="this.$auth('rdorg:arch:rd:add')" :class="{'item':showMenu}"><a
          @click="onClick({key:'addChild'},data)">添加下级部门</a></li>
        <li
          v-if="!isRoot && this.$auth('rdorg:arch:rd:add')"
          :class="{'item':showMenu}"><a
            @click="onClick({key:'add'},data)">添加同级部门</a></li>
        <li
          v-if="isRoot && this.$auth('rdorg:arch:rd:add') "
          :class="{'item':showMenu}"><a
            @click="onClick({key:'addCommittee'},data,1)">添加委员会</a></li>
        <li v-if="this.$auth('rdorg:arch:rd:clone')" :class="{'item':showMenu}">
          <a @click="onClick({key:'cloneNode'},data)">克隆其他部门</a></li>
        <li v-if="this.$auth('rdorg:arch:rd:edit')" :class="{'item':showMenu}">
          <a @click="onClick({key:'edit'},data,data.nodeType)">编辑</a></li>
        <li v-if="this.$auth('rdorg:arch:rd:del')" :class="{'item':showMenu}"><a
          @click="onClick({key:'del'},data)">删除</a></li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Node',
  props: {
    data: {
      type: Object,
      required: true
    },
    onClick: {
      type: Function,
      required: true
    },
    isRoot: {
      type: Boolean,
      default: false
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
    },
    sortNode: {
      type: Function,
      required: true
    }
  },
  computed: {
    menuDirection () {
      // 0竖向 1横向
      return this.data.level > 0 && Number(this.data.textDirection) === 0
    }
  },
  data () {
    return {
      showMenu: false,
      isShow: false
    }
  },
  methods: {
    onMouse (boolean) {
      if (!this.modify) return
      this.showMenu = boolean
    },
    transformData (str) {
      if (!str) {
        return ''
      }
      return str.replace(/\n/gm, '<br/>')
    },
    getAlign () {
      // 对齐方式，0 左 1中 2右
      if (this.data.align === 0) {
        return 'left'
      } else if (this.data.align === 1) {
        return 'center'
      } else if (this.data.align === 2) {
        return 'right'
      }
    }
  }
}
</script>
<style lang="less" scoped>
// .textAlign{

// }
.textContainer {
  // display: flex;
  // flex-direction: ;
  position: relative;
  display: inline-block;
}

.htext {
  display: inline-block;
  // display:block;
  border: 1px solid;
  border-radius: 4px;
  padding-left: 0.6em;
  padding-right: 0.6em;
  padding-top: 0.3em;
  padding-bottom: 0.3em;
  white-space: nowrap;
  margin-left: 0.3em;
  margin-right: 0.3em;
}

.vtext {
  display: inline-block;
  // display:block;
  border: 1px solid;
  border-radius: 4px;
  text-orientation: upright;
  writing-mode: vertical-lr;
  height: 10em;
  vertical-align: top;
  text-align: left;
  padding-top: 0.6em;
  padding-left: 3px;
  padding-right: 3px;
  margin-left: 0.6em;
  margin-right: 0.6em;
  word-break: break-all;
  white-space: pre-wrap;
}

.htext:hover, .vtext:hover {
  cursor: pointer;
  color: #40a9ff;
  border-color: #40a9ff;
}

.hMenu {
  position: absolute;
  display: flex;
  left: 0.3em;
  width: 140px;
  flex-direction: column;
  padding: 3px 0;

  .item-wrap {
    padding: 3px 0;
    border: 1px solid;
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
    border: 0 solid;
    background-color: white;
    border-radius: 4px;
    z-index: 1;
    -webkit-transition: border 300ms ease;
    -moz-transition: border 300ms ease;
    -o-transition: border 300ms ease;
    transition: border 300ms ease;

    li {
      display: flex;
      font-size: 14px;
      overflow: hidden;
      height: 0;
      -webkit-transition: height 300ms ease;
      -moz-transition: height 300ms ease;
      -o-transition: height 300ms ease;
      transition: height 300ms ease;

      :hover {
        background-color: #e6f7ff;
      }

      a {
        color: #000000a6;
        display: block;
        width: 100%;
        text-align: left;
        padding: 5px 12px;
        line-height: 22px;
      }
    }
  }
}

.vMenu {
  position: absolute;
  display: flex;
  left: 100%;
  top: 0;
  overflow: hidden;
  flex-direction: column;
  margin-left: -0.6em;

  .item-wrap {
    width: 140px;
    padding: 3px 0;
    border: 1px solid;
    list-style: none;
    margin: 0;
    overflow: hidden;
    background-color: white;
    border-radius: 4px;
  }

  ul {
    width: 0px;
    list-style: none;
    margin: 0;
    overflow: hidden;
    padding: 3px 0;
    border: 0px solid;
    background-color: white;
    border-radius: 4px;
    -webkit-transition: width 300ms ease;
    -moz-transition: width 300ms ease;
    -o-transition: width 300ms ease;
    transition: width 300ms ease;
    z-index: 1;

    li {
      display: flex;
      font-size: 14px;
      overflow: hidden;
      height: 30px;

      :hover {
        background-color: #e6f7ff;
      }

      a {
        overflow: hidden;
        color: #000000a6;
        display: block;
        width: 100%;
        text-align: left;
        padding: 5px 12px;
        line-height: 22px;
      }
    }
  }
}
</style>
