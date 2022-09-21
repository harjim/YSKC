<!--
 * @Author: zdf
 * @Date: 2021-10-27 15:56:13
 * @LastEditTime: 2021-11-12 09:06:30
 * @LastEditors: zdf
 * @Description: 日志
 * @FilePath: \MS-VUE\src\views\sys\SysLog.vue
-->
<template>
  <div>
    <a-card :bordered="false">
      <a-tabs @change="callback">
        <a-tab-pane tab="后台日志" key="1">
          <logTab ref="MsLogTab" :key="1" :pageType="1"></logTab>
        </a-tab-pane>
        <a-tab-pane tab="企业端日志" key="2" forceRender>
          <logTab ref="RsLogTab" :key="2" :pageType="2"></logTab>
        </a-tab-pane>
        <a-tab-pane tab="人才端日志" key="3" forceRender>
          <logTab ref="RsLogTab" :key="3" :pageType="3"></logTab>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>

<script>
import LogTab from './LogTab'
export default {
  components: {
    LogTab
  },
  data () {
    return {
      pageType: 1
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
