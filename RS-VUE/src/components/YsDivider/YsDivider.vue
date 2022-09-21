<!--
 * @Author: zdf
 * @Date: 2021-03-17 15:49:08
 * @LastEditTime: 2021-06-09 14:58:35
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\components\YsDivider\YsDivider.vue
-->
<template>
  <div class="center-form-title" :style="{ height, marginTop: top, marginBottom: bottom, fontSize: fontSize + 'px' }">
    <div class="title" :style="{ left: (level * 2)+'em' }">{{ title }}</div>
    <div class="line"></div>
  </div>
</template>

<script>
export default {
  name: 'CustomTitle',
  props: {
    title: {
      type: String,
      default: ''
    },
    height: {
      type: String,
      default: '38px'
    },
    top: {
      type: String,
      default: '0px'
    },
    bottom: {
      type: String,
      default: '20px'
    },
    level: {
      type: Number,
      default: 0
    },
    fontSize: {
      type: Number,
      default: 16
    }
  },
  data () {
    return {
      isEdit: false
    }
  },
  methods: {
  }
}
</script>

<style lang="less" scoped>
.center-form-title {
  width: 100%;
  height: 38px;
  position:relative;
  line-height: 38px;
  font-weight: 700;
  vertical-align: middle;
}
.title {
  position:absolute;
  padding: 0 10px 0 5px;
  background-color: #fff;
  color: #333;
}
.line {
  padding-top: 18px;
  border-bottom: 1px solid #ebebeb;
}
</style>
