<!--
 * @Author: ldx
 * @Date: 2021-06-15 11:51:07
 * @LastEditTime: 2021-06-22 15:06:11
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\components\YsIcon\YsIcon.vue
-->
<template>
  <a-icon :component="icons[icon]" />
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
