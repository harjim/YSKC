<template>
  <div>
    <a-tabs defaultActiveKey="material" size="small">
      <a-tab-pane tab="材料明细账" key="material">
        <detail :accType="8" typeName="材料" />
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
