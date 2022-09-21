<template>
  <inspection
    :type="[60400]"
    typeName="差旅费"
    :view="$auth('dataentry:travelExpense:search')"
    :addRecord="$auth('dataentry:travelExpense:add')"
    :editRecord="$auth('dataentry:travelExpense:edit')"
    :delRecord="$auth('dataentry:travelExpense:del')"
    :importRecord="$auth('dataentry:travelExpense:import')"
    :exportRecord="$auth('dataentry:travelExpense:export')"
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
