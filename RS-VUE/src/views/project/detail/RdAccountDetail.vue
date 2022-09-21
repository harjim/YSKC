<template>
  <div>
    <a-tabs defaultActiveKey="rd" size="small">
      <a-tab-pane tab="研发费用明细账" key="rd">
        <detail :accType="1" ref="detail" typeName="研发费用" />
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
