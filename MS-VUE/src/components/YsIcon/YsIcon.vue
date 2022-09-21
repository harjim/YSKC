<!--
 * @Author: ldx
 * @Date: 2021-06-15 11:51:07
 * @LastEditTime: 2021-06-22 14:59:25
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\components\YsIcon\YsIcon.vue
-->
<template>
  <a-icon style="color: #03A9F3;" :component="icons[icon]" />
</template>

<script>
import icons from '@/core/icons'
export default {
  props: {
    icon: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      icons
    }
  }

}
</script>

<style>

</style>
