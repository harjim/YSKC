<template>
  <div>
    <a-tabs defaultActiveKey="fuel" size="small">
      <a-tab-pane tab="燃料费明细账" key="fuel">
        <detail :accType="2" key="2" typeName="燃料费" />
      </a-tab-pane>
      <a-tab-pane tab="动力费明细账" key="stimulus">
        <detail :accType="3" key="3" typeName="动力费" />
      </a-tab-pane>
      <a-tab-pane tab="折旧费明细账" key="deprecation">
        <detail :accType="4" key="4" typeName="折旧费" />
      </a-tab-pane>
      <a-tab-pane tab="备品备件耗用明细账" key="material">
        <detail :accType="5" key="5" typeName="备品备件耗用" />
      </a-tab-pane>
      <a-tab-pane tab="其他明细账" key="other">
        <detail :accType="6" key="6" typeName="其他" />
      </a-tab-pane>
    </a-tabs>
  </div>
</template>

<script>
import Detail from './Detail'
export default {
  components: {
    Detail
  }
}
</script>

<style>
</style>
