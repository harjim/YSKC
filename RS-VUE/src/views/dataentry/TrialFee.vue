<template>
  <inspection
    :type="[20300]"
    typeName="其他试制"
    :view="$auth('dataentry:trialFee:search')"
    :addRecord="$auth('dataentry:trialFee:add')"
    :editRecord="$auth('dataentry:trialFee:edit')"
    :delRecord="$auth('dataentry:trialFee:del')"
    :importRecord="$auth('dataentry:trialFee:import')"
    :exportRecord="$auth('dataentry:trialFee:export')"
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
