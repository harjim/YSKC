<!--
 * @Author: zdf
 * @Date: 2021-07-09 09:27:02
 * @LastEditTime: 2021-07-21 15:21:47
 * @LastEditors: zdf
 * @Description: 解决方案 知识库
 * @FilePath: \MS-VUE\src\views\sys\Solution.vue
-->
<template>
  <div>
    <a-card :bordered="false">
      <a-tabs v-model="activeKey" @change="tabChange">
        <a-tab-pane key="solution1" tab="方案库">
          <SolutionTab ref="solution1" :key="1" :type="0" url="/solution/getList"/>
        </a-tab-pane>
        <a-tab-pane key="solution2" tab="分享给我的">
          <SolutionTab ref="solution2" :key="2" :isShare="true" :type="0" url="/solution/getShareList"/>
        </a-tab-pane>
        <a-tab-pane key="solution3" tab="基础知识">
          <SolutionTab ref="solution3" :key="3" :isBase="true" :type="1" url="/solution/getList"/>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>

<script>
import SolutionTab from './modules/SolutionTab'
export default {
  components: {
    SolutionTab
  },
  data () {
    return {
      activeKey: 'solution1'
    }
  },
  methods: {
    tabChange (key) {
      if (this.$refs[key]) {
        this.$refs[key].search(true)
      }
    }
  }
}
</script>

<style>

</style>
