<!--
 * @Author: zdf
 * @Date: 2022-04-26 09:17:17
 * @LastEditTime: 2022-05-10 15:08:22
 * @LastEditors: zdf
 * @Description: 月份累计费用表
 * @FilePath: \MS-VUE\src\views\innovation\modules\auditModules\MonthFeeTable.vue
-->

<template>
  <div style="margin-bottom:12px;">
    <a-switch checked-children="当期汇总" un-checked-children="当期汇总" v-model="monthFeeswitch" />
    <transition name="fade">
      <div v-drag style="width: 460px;position:fixed;left:0;bottom:0;z-index:101;">
        <a-card v-show="monthFeeswitch" :bodyStyle="{ padding: '0' }" title="当期汇总">
          <div slot="extra">
            <a-button style="margin-right:10px;" @click="queryData" shape="circle">
              <i class="vxe-icon--refresh"></i>
            </a-button>
            <a-button @click="changeFlag" shape="circle">
              <a-icon :type="flag ? 'down' : 'up'" />
            </a-button>
          </div>
          <vxe-grid
            v-show="flag"
            :id="`${queryParams.rdType}_${queryParams.companyId}`"
            :data="tableData"
            size="small"
            highlight-hover-row
            highlight-current-row
            show-overflow="title"
            :loading="loading"
            empty-text="..."
          >
            <vxe-table-column
              title="类型"
              field="title"
              align="center"
              width="74"
              showHeaderOverflow
            />
            <vxe-table-column
              title="月合计"
              field="monthFee"
              align="right"
              width="115"
              showHeaderOverflow
            >
              <template #default="{row}">{{ toBit(row.monthFee) }}</template>
            </vxe-table-column>
            <vxe-table-column title="占比" align="right" width="70" showHeaderOverflow>
              <template #default="{row}">{{ getRatio(row,0) }}</template>
            </vxe-table-column>
            <vxe-table-column
              title="当期累计"
              field="addFee"
              align="right"
              width="115"
              showHeaderOverflow
            >
              <template #default="{row}">{{ toBit(row.addFee) }}</template>
            </vxe-table-column>
            <vxe-table-column title="累计占比" align="right" width="80" showHeaderOverflow>
              <template #default="{row}">{{ getRatio(row,1) }}</template>
            </vxe-table-column>
          </vxe-grid>
        </a-card>
      </div>
    </transition>
  </div>
</template>

<script>
import { toBit } from './FeeDetailConfig'
export default {
  props: {
    auditCostTypes: {
      type: Array,
      required: true
    },
    queryParams: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      first: true,
      loading: false,
      monthFeeswitch: false,
      flag: true,
      tableData: [],
      emptyData: [],
      typeMap: { 0: 'monthFee', 1: 'addFee' },
      totalRow: {}
    }
  },

  created () {
    this.loadTableData([])
  },
  watch: {
    monthFeeswitch (n) {
      if (n && this.first) {
        this.queryData()
      }
      this.first = false
    }
  },
  directives: {
    drag: {
      bind: function (el) {
        const odiv = el
        el.onmousedown = e => {
          const disX = e.clientX - odiv.offsetLeft
          const disY = e.clientY - odiv.offsetTop
          let left = ''
          let top = ''
          document.onmousemove = e => {
            left = e.clientX - disX
            top = e.clientY - disY
            odiv.style.left = left + 'px'
            odiv.style.top = top + 'px'
          }
          document.onmouseup = () => {
            document.onmousemove = null
            document.onmouseup = null
          }
        }
      }
    }
  },
  methods: {
    toBit,
    queryData () {
      this.loading = true
      this.$http
        .get('/rdFeeAudit/getMonthFee', { params: this.queryParams })
        .then(res => {
          if (res.success && res.data) {
            this.loadTableData(res.data)
          } else {
            this.loadTableData([])
            this.$message.error(res.errorMessage || '加载当期累计失败')
          }
        })
        .finally(() => {
          this.loading = false
        })
    },
    loadTableData (data) {
      const dataMap = {}
      const total = { title: '合计' }
      data.forEach(item => {
        const col = this.typeMap[item.type]
        for (const key in item) {
          if (key === 'type') {
            continue
          }
          const k = key.replace('k', '')
          if (!dataMap[k]) {
            dataMap[k] = {}
          }
          const fee = item[key]
          dataMap[k][col] = fee
          total[col] = (total[col] || 0) + (fee || 0)
        }
      })
      const tableData = []
      this.auditCostTypes.forEach(item => {
        tableData.push(Object.assign({ rdType: item.rdType, title: item.title }, dataMap[item.rdType] || {}))
      })
      this.totalRow = total
      tableData.push(total)
      this.tableData = tableData
    },
    getRatio (row, type) {
      if (row.title === '合计') {
        return ''
      }
      const key = this.typeMap[type]
      if (this.totalRow[key] === 0 || row[key] === 0) {
        return '--'
      }
      return toBit(row[key] / this.totalRow[key] * 100) + '%'
    },
    changeFlag () {
      this.flag = !this.flag
    }
  }
}
</script>

<style scoped>
.fade-enter {
  opacity: 0;
  transform: translateX(-100%);
}
.fade-enter-active {
  transition: all 0.7s;
}
.fade-leave-active {
  opacity: 0;
  transform: translateX(-100%);
}
.ant-card-head-wrapper {
  height: 32px;
}
</style>
