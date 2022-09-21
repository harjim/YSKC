<template>
  <inspection
    :type="[20600]"
    typeName="修理"
    :view="$auth('dataentry:repairFee:search')"
    :addRecord="$auth('dataentry:repairFee:add')"
    :editRecord="$auth('dataentry:repairFee:edit')"
    :delRecord="$auth('dataentry:repairFee:del')"
    :importRecord="$auth('dataentry:repairFee:import')"
    :exportRecord="$auth('dataentry:repairFee:export')"
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
