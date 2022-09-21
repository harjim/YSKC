<template>
  <a-layout-sider
    style="background: #fff;overflow: auto;"
    :width="width"
    :trigger="null"
  >
    <s-menu id="menu" mode="inline" :menu="menus" theme="light" :style="{ height: '100%'}" />
  </a-layout-sider>
</template>

<script>
import SMenu from '../Menu/'
export default {
  name: 'GlobalLeft',
  components: {
    SMenu
  },
  props: {
    menus: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      width: 240
    }
  },
  methods: {
    setColl (val) {
      if (!val) {
        for (let i = 1; i <= 240; i++) {
          this.width = i
        }
      } else {
        for (let i = 240 - 1; i >= 0; i--) {
          this.width = i
        }
      }
    }
  }
}
</script>

<style lang="less" scoped>
#menu {
   /deep/ .ant-menu-item {
    height: 33px;
    line-height : 33px;
  }
  /deep/ .ant-menu-submenu-title {
    height: 35px;
    line-height : 35px;
  }
}
</style>
