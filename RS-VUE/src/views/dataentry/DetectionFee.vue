<template>
  <inspection
    :type="[20500]"
    typeName="检测"
    :view="$auth('dataentry:detectionFee:search')"
    :addRecord="$auth('dataentry:detectionFee:add')"
    :editRecord="$auth('dataentry:detectionFee:edit')"
    :delRecord="$auth('dataentry:detectionFee:del')"
    :importRecord="$auth('dataentry:detectionFee:import')"
    :exportRecord="$auth('dataentry:detectionFee:export')"
  ></inspection>
</template>
<script>
import Inspection from './Inspection'
export default {
  components: {
    Inspection
  }
}
</script>

<style>
</style>
