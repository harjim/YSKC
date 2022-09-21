<!--
 * @Author: ldx
 * @Date: 2020-11-06 09:01:53
 * @LastEditTime: 2020-11-06 10:15:06
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\components\TimeLine\TimeLine.vue
-->
<template>
  <div class="timeLineContainer">
    <div class="timeLine timeLine-bottom"></div>
    <template v-if="isMultiple">
      <div
        v-for="( timeLine, index ) in timeLines"
        :key="index"
        class="timeLine timeLine-top"
        :style="{ width: timeLine.topWidth, left: timeLine.topLeft, backgroundColor: timeLine.color ? timeLine.color : color }">
      </div>
    </template>
    <template v-else>
      <div class="timeLine timeLine-top" :style="{ width: topWidth, left: topLeft, backgroundColor:color }">
      </div>
    </template>
  </div>
</template>

<script>
export default {
  name: 'TimeLine',
  props: {
    topWidth: {
      type: String,
      required: true
    },
    color: {
      type: String,
      default: '#1890FF'
    },
    topLeft: {
      type: String,
      required: true
    },
    isMultiple: {
      type: Boolean,
      default: false
    },
    timeLines: {
      type: Array,
      default: () => { return [] }
    }
  },
  data () {
    return {

    }
  }
}
</script>

<style lang="less" scoped>
  .timeLineContainer {
    position: relative;
  }
  .timeLine {
    width: 100%;
    height: 5px;
    border-radius: 10px;
  }
  .timeLine-top,.timeLine-bottom {
    position: absolute;
  }
  .timeLine-bottom {
    background-color: #e9e9e9;
  }
  // .timeLine-top {
  //   background-color: #1890FF;
  // }
</style>
