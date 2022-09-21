<template>
  <inspection
    :type="[40000,40100,40200]"
    typeName="摊销费用"
    :view="$auth('dataentry:amortizeFee:search')"
    :addRecord="$auth('dataentry:amortizeFee:add')"
    :editRecord="$auth('dataentry:amortizeFee:edit')"
    :delRecord="$auth('dataentry:amortizeFee:del')"
    :importRecord="$auth('dataentry:amortizeFee:import')"
    :exportRecord="$auth('dataentry:amortizeFee:export')"
    :setFeeType="$auth('dataentry:amortizeFee:setFeeType')"
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
