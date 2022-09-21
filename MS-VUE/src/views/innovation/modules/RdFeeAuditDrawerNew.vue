<!--
    * @Author: hm
    * @Date: 2022-08-30 09:09:33
    * @Description:
-->
<template>
  <a-drawer
    :title="`财务审批-[${year} - ${companyName}] ${months[month]}份归集费用`"
    placement="right"
    :visible="visible"
    width="80%"
    @close="closeModel"
  >
    <a-tabs
      tab-position="top"
      v-model="month"
    >
      <a-tab-pane v-for="(m, index) in months" :key="index" :tab="m">
      </a-tab-pane>
    </a-tabs>
    <fee-table ref="feeTable" key="table" :month="month" :params="params"></fee-table>
  </a-drawer>
</template>

<script>
// import { auditCostTypes, auditCostTypesMap } from './auditModules/FeeDetailConfig'
import FeeTable from './auditModules/FeeTable'

export default {
  data () {
    return {
      year: null,
      companyName: null,
      month: 0
    }
  },
  props: {
    params: {
      type: Object,
      default: () => {}
    },
    visible: {
      type: Boolean,
      default: false
    },
    months: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    params: {
      immediate: true,
      handler (newValue, oldValue) {
        if (newValue !== null && newValue !== undefined) {
          this.year = newValue.year
          this.companyName = newValue.companyName
          this.month = 0
        }
      }
    }
  },
  components: {
    FeeTable
  },

  methods: {
    closeModel () {
      this.$refs.feeTable.closeAll()
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style lang='css' scoped>

</style>
