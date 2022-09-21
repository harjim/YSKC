<!--
 * @Author: zdf
 * @Date: 2021-10-27 15:56:13
 * @LastEditTime: 2021-11-12 09:06:24
 * @LastEditors: zdf
 * @Description: 菜单管理
 * @FilePath: \MS-VUE\src\views\sys\MenuManage.vue
-->
<template>
  <div>
    <a-card :bordered="false">
      <a-tabs @change="callback">
        <a-tab-pane tab="后台菜单" key="1">
          <msMenuManageTab ref="MsMenuManageTab" :key="1" :msType="1"></msMenuManageTab>
        </a-tab-pane>
        <a-tab-pane tab="企业端菜单" key="2" forceRender>
          <msMenuManageTab ref="RsMenuManageTab" :key="2" :msType="2"></msMenuManageTab>
        </a-tab-pane>
        <a-tab-pane tab="人才端菜单" key="3" forceRender>
          <msMenuManageTab ref="EsMenuManageTab" :key="3" :msType="3"></msMenuManageTab>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>

<script>
import MsMenuManageTab from './MsMenuManageTab'
export default {
  components: {
    MsMenuManageTab
  },
  data () {
    return {
      msType: 1
    }
  },
  methods: {
    callback (key) {
    },
    change (key) {
      this.key = key
    }
  }
}
</script>
