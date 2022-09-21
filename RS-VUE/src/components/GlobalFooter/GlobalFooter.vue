<!--
 * @Author: ldx
 * @Date: 2021-07-24 11:00:40
 * @LastEditTime: 2021-07-27 18:39:26
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\components\GlobalFooter\GlobalFooter.vue
-->
<template>
  <div class="footer">
    <div class="copyright">
      Copyright
      <a-icon type="copyright" /> {{ year }} <span>优赛科创</span> <span style="padding:0 10px">V 1.1.0</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'GlobalFooter',
  data () {
    return {
      year: new Date().getFullYear()
    }
  }
}
</script>

<style lang="less" scoped>
.footer {
  padding: 0 16px;
  margin-top: 8px;
  margin-bottom: 8px;
  text-align: center;
  .links {
    margin-bottom: 8px;

    a {
      color: rgba(0, 0, 0, 0.45);

      &:hover {
        color: rgba(0, 0, 0, 0.65);
      }

      &:not(:last-child) {
        margin-right: 40px;
      }
    }
  }
  .copyright {
    color: rgba(0, 0, 0, 0.45);
    font-size: 14px;
  }
}
</style>
