<!--
 * @Author: ldx
 * @Date: 2021-04-20 10:46:05
 * @LastEditTime: 2021-11-09 17:50:00
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\MyAudit.vue
-->
<template>
  <a-card :bordered="false">
    <a-tabs v-model="activeKey" @change="tabChange">
      <a-tab-pane key="audit1" tab="待处理" v-if="control.handle">
        <MyAuditTab ref="audit1" :key="1" :sign="1" :params="{ ongoing: true }" :isAudit="true" />
      </a-tab-pane>
      <a-tab-pane key="audit2" tab="我发起的" v-if="control.send">
        <MyAuditTab ref="audit2" :key="2" :sign="2" :params="{ mySubmit: true }" :isRecall="true" />
      </a-tab-pane>
      <a-tab-pane key="audit3" tab="我收到的" v-if="control.receive">
        <MyAuditTab ref="audit3" :key="3" :sign="3" />
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>
<script>
import MyAuditTab from './modules/MyAuditTab'

export default {
  name: 'MyAudit',
  components: {
    MyAuditTab
  },
  data() {
    return {
      activeKey: 'audit1',
      control: {
        handle: this.$auth('customer:myAudit:handle'),
        send: this.$auth('customer:myAudit:send'),
        receive: this.$auth('customer:myAudit:receive')
      }
    }
  },
  created() {
    this.activeKey = this.control.handle ? 'audit1' : this.control.send ? 'audit2' : 'audit3'
  },
  methods: {
    tabChange(key) {
      if (this.$refs[key]) {
        this.$refs[key].search(true)
      }
    }
  }
}
</script>
