<template>
  <inspection
    :type="[20700, 60000, 60100, 60200, 60300, 69900]"
    typeName="其他费用"
    :view="$auth('dataentry:otherFee:search')"
    :addRecord="$auth('dataentry:otherFee:add')"
    :editRecord="$auth('dataentry:otherFee:edit')"
    :delRecord="$auth('dataentry:otherFee:del')"
    :importRecord="$auth('dataentry:otherFee:import')"
    :exportRecord="$auth('dataentry:otherFee:export')"
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
