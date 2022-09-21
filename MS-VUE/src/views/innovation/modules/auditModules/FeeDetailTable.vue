<!--
 * @Author: zdf
 * @Date: 2022-04-26 16:57:17
 * @LastEditTime: 2022-05-05 09:47:15
 * @LastEditors: zdf
 * @Description: 费用明细表
 * @FilePath: \MS-VUE\src\views\innovation\modules\auditModules\FeeDetailTable.vue
-->
<template>
  <ystable
    :ref="`table${tableConfig.rdType}${tableConfig.projectId}`"
    :columns="tableConfig.columns"
    :params="curQueryParams"
    showOverflow="title"
    :id="`${curQueryParams.rdType}_${curQueryParams.companyId}`"
    height="300px"
    size="small"
    :queryUrl="tableConfig.url"
    :toolbar="{ refresh: {query: search}, zoom: true, custom: true }">
    <template #buttons>
      <slot name="detailTitle"></slot>
    </template>
  </ystable>
</template>

<script>
import ystable from '@/components/Table/ystable'
export default {
  components: {
    ystable
  },
  props: {
    queryParams: {
      type: Object,
      required: true
    },
    tableConfig: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      curQueryParams: {}
    }
  },
  mounted () {
    for (const k in this.queryParams) {
      this.curQueryParams[k] = this.queryParams[k]
    }
    this.loadParams()
  },
  methods: {
    search (refresh) {
      this.loadParams()
      this.$nextTick(() => {
        const table = this.$refs[`table${this.tableConfig.rdType}${this.tableConfig.projectId}`]
        if (table) {
          table.refresh(refresh)
        }
      })
    },
    loadParams () {
      this.curQueryParams.projectId = this.tableConfig.projectId
      this.curQueryParams.rdTypes = this.tableConfig.rdTypes
    }
  }
}
</script>

<style lang="less" scoped>
</style>
