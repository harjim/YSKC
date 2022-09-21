<!--
    * @Author: hm
    * @Date: 2022-08-30 10:10:19
    * @Description:
-->
<template>
  <!-- <ystable
    :column="[]"></ystable> -->
  <div style="height: calc(100vh - 180px);">
    <!--  -->
    <vxe-grid
      ref="feeTable"
      autoResize
      resizable
      highlight-hover-row
      highlight-current-row
      showOverflow
      showHeaderOverflow
      showFooterOverflow
      maxHeight="94%"
      :data="tableData"
      :loading="tableLoading"
      showFooter
      column-key
      :footer-method="showTotal"
      :toolbar="{ refresh: {query:search}, zoom: true, custom: true, enabled: true }"
      :expand-config="{ loadMethod: loadMethod, trigger: 'default', toggleMethod: expandShow }"
      @cell-click="clickShow"
    >
      <!-- @toggle-row-expand="expandShow"   -->
      <template #buttons>
      </template>
      <vxe-table-column
        field="pname"
        title="项目名称"
        width="200px"
        type="expand"
        fixed="left"
      >
        <template #default="{ row }">
          {{ `${row.rdTitle} - ${row.pname}` }}
        </template>
        <template #content="{ row, rowIndex }">
          <!-- 存在多项(内容) -->
          <div>
            <a-tabs v-if="detailShow[rowIndex].tabs">
              <template #tabBarExtraContent>
                <span class="detailTitle">{{ detailShow[rowIndex].title }}</span>
              </template>
              <a-tab-pane
                v-for="(tabsTitle, tabsType) in detailShow[rowIndex].tabs"
                :key="`${row.id}-${tabsType}`"
                :tab="tabsTitle"
              >
                <feeDetailTable
                  :key="`${detailShow[rowIndex].key}${tabsType}`"
                  :queryParams="detailParams"
                  :tableConfig="getCurConfig(row.id, tabsType)"
                />
              </a-tab-pane>
            </a-tabs>
            <!-- 不存在多项 -->
            <feeDetailTable
              v-else
              :key="detailShow[rowIndex].key"
              :queryParams="detailParams"
              :tableConfig="detailShow[rowIndex].tableConfig"
            >
              <template #detailTitle>
                <span class="detailTitle">{{ detailShow[rowIndex].title }}</span>
              </template>
            </feeDetailTable>
          </div>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="pCycle"
        title="项目周期"
        width="200px"
        align="center"
        fixed="left"
      >
        <template #default="{ row }">
          {{ row.beginDate | DayFormat }} - {{ row.endDate | DayFormat }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        v-for="(column, index) in auditCostTypes"
        :key="`${column.rdType}-${index}`"
        :field="`c${column.rdType}`"
        :title="column.title"
        width="130px"
        align="right"
        className="cursor"
      >
        <!-- :type="!index ? 'expand' : null" -->
        <!-- type="expand" -->
        <template #default="{ row }">
          {{ row[`c${column.rdType}`] | NumberFormatHasNull }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="totalAmount"
        title="合计"
        align="right"
        width="100px"
        fixed="right"
      >
        <template #default="{ row }">
          {{ row.totalAmount | NumberFormatHasNull }}
        </template>
      </vxe-table-column>
    </vxe-grid>
  </div>
</template>

<script>
import { auditCostTypes, auditCostTypesMap, getConfigParams } from './FeeDetailConfig'
import FeeDetailTable from './FeeDetailTable'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import VXETable from 'vxe-table'
VXETable.setup({
  zIndex: 9999 // 层级
})
export default {
  components: {
    ystable,
    feeDetailTable: FeeDetailTable
  },
  data () {
    return {
      auditCostTypes,
      auditCostTypesMap,
      configMap: {},
      tableData: null,
      footerData: null,
      tableLoading: false,
      detail: null,
      detailShow: {},
      show: true
    }
  },
  props: {
    params: {
      type: Object,
      default: () => {}
    },
    month: {
      type: Number,
      default: 0
    }
  },
  computed: {
    isSearch () {
      return this.month + this.params.companyName + this.params.year
    },
    day () {
      return moment({ y: this.params.year, M: this.month, d: 1, h: 0, m: 0, s: 0, ms: 0 })
    },
    detailParams () {
      return { companyId: this.params.companyId, month: this.day }
    }
  },
  watch: {
    isSearch: {
      immediate: true,
      handler (newValue, oldValue) {
        this.search()
      }
    }
  },
  methods: {
    getConfigParams,
    showTotal ({ columns }) {
      let result = ['合计', null]
      const numberFormat = this.$options.filters['NumberFormatHasNull']
      if (this.footerData !== null && this.footerData !== undefined) {
        auditCostTypes.forEach(item => {
          let num = null
          num = this.footerData[`c${item.rdType}`]
          result.push(numberFormat(num))
        })
        result.push(numberFormat(this.footerData.totalAmount))
      } else {
        result = result.concat(Array.from({ length: columns.length - 2 }, () => '--'))
      }
      return [result]
    },
    search () {
      this.tableLoading = true
      this.$http.get('/rdFeeAudit/getRdFundsByMonth', { params: { month: this.day, companyId: this.params.companyId } }).then(res => {
        if (res.success && res.data) {
          this.footerData = res.data.pop()
          this.tableData = res.data
        } else {
          this.$message.error(res.errorMessage || '获取统计失败')
        }
      }).catch(res => {
        this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
      }).finally(() => {
        this.tableLoading = false
      })
    },
    closeAll () {
      this.$refs.feeTable.clearRowExpand()
    },
    loadMethod ({ row }) {
      return new Promise(resolve => {
        resolve()
      })
    },
    getCurConfig (projectId, rdType) {
      let config = this.configMap[`${projectId}-${rdType}`]
      if (!config) {
        config = getConfigParams(projectId, rdType)
        this.configMap[`${projectId}-${rdType}`] = config
      }
      return config
    },
    clickShow ({ column, row, columnIndex, rowIndex }) {
      if (columnIndex < 2 || columnIndex > 14) return
      this.dataChange(+column.property.slice(1), row, rowIndex)
      this.$refs.feeTable.setRowExpansion([this.tableData[rowIndex]], true)
    },
    expandShow ({ row, rowIndex }) {
      if (!this.$refs.feeTable.isExpandByRow(row) && (!this.detailShow[rowIndex] || (this.detailShow[rowIndex] && this.detailShow[rowIndex].isChange === false))) {
        this.dataChange(10000, row, rowIndex)
      }
      this.detailShow[rowIndex].isChange = false
      return true
    },
    dataChange (rdType, row, rowIndex) {
      // const rdType = +column.property.slice(1)
      const projectId = row.id
      const key = `${projectId}${rdType}`
      if (!this.detailShow[rowIndex]) {
        this.detailShow[rowIndex] = {}
      }

      this.detailShow[rowIndex].tableConfig = this.getCurConfig(projectId, rdType)
      this.detailShow[rowIndex].key = key
      this.detailShow[rowIndex].tabs = auditCostTypesMap[rdType].tabs
      this.detailShow[rowIndex].change = true
      this.detailShow[rowIndex].title = auditCostTypesMap[rdType].title
      this.detail = this.detailShow[rowIndex]
      this.detailShow[rowIndex].isChange = true
    }
  }
}
</script>

<style lang='less' scoped>
/deep/ .cursor {
  cursor: pointer;
}

/deep/ .detailTitle {
  font-size: 16px;
  font-weight: bold;
  color: #1a1a1a;
}
/deep/ .ant-tabs-extra-content {
  float: left !important;
  margin-right: 20px;
}

/deep/ .vxe-body--expanded-row {
  width: 77vw;
  display: block;
  position: sticky;
  left: 0;
  z-index: 2;
}
/deep/ .vxe-body--expanded-column {
  width: 100%;
  display: block;
}
/deep/ .vxe-body--expanded-cell {
  padding-top: 6px;
  width: 100%;
}
// 取消固定原有阴影
/deep/ .vxe-table--fixed-left-wrapper, /deep/ .vxe-table--fixed-right-wrapper {
  -webkit-box-shadow:  0 0 0 0 !important;
  box-shadow:  0 0 0 0 !important;
}
// 左固定
/deep/ .vxe-table--fixed-left-wrapper table tr th:nth-child(2), /deep/ .vxe-table--fixed-left-wrapper table tr td:nth-child(2) {
  box-shadow: 2px 0px 2px 0px #e1e1e1;
  -webkit-box-shadow: 2px 0px 2px 0px #e1e1e1;
  margin-right: 4px;
  display: -webkit-box;
  line-height: 48px;
}
// /deep/ .vxe-table--fixed-left-wrapper table tr th:nth-child(1), /deep/ .vxe-table--fixed-left-wrapper table tr td:nth-child(1) {
  // box-shadow: -2px 0px 3px 0 #e1e1e1;
// }
// /deep/ .vxe-table--fixed-left-wrapper table tr:not(.vxe-body--expanded-row) {
//   -webkit-box-shadow: 4px 0px 3px 0 #e1e1e1;
//   box-shadow: 4px 0px 3px 0 #e1e1e1;
//   display: block;
//   width: 197%;
// }

// 右固定
// title
/deep/ .vxe-table--fixed-right-wrapper table:first-child tr:first-child .col--last {
  -webkit-box-shadow: inset 3px 0 1px 0 #e1e1e1;
  box-shadow: inset 3px 0 1px 0 #e1e1e1;
}
// content + footer
/deep/ .vxe-table--fixed-right-wrapper table:not(:first-child) .vxe-body--row {
  -webkit-box-shadow: -2px 0px 5px 0 #e1e1e1;
  box-shadow: -2px 0px 5px 0 #e1e1e1;
  display: block;
  margin-left: 4px;
}
</style>
