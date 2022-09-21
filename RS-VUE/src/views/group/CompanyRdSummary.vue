<template>
  <a-card class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto'}">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <div>
        <a-form
          layout="inline"
        >
          <a-form-item label="公司名称">
            <a-input
              style="width:165px;"
              v-model="queryParams.companyName"
              placeholder="请输入公司名称"
            />
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              v-if="control.search"
              @click="search(true)"
            >查询</a-button>
          </a-form-item>
        </a-form>
      </div>
      <div>
        <ystable
          ref="table"
          show-footer
          :footer-method="footerMethod"
          queryUrl="/companyRdSummary/getList"
          :params="queryParams"
          @completed="completed"
          :toolbar="{ refresh: true, zoom: true, custom: true }"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
        >
          <!-- <template v-slot:buttons>
            <a-button
              type="primary"
              v-if="control.export"
              @click="exportData"
              style="margin-right:10px"
            >导出</a-button>
          </template> -->
          <vxe-table-column title="序号" type="seq" fixed="left" width="50"/>
          <vxe-table-column
            title="公司名称"
            field="companyName"
            width="200"
            fixed="left"
            remoteSort
          >
            <template v-slot="{row}">
              <span v-if="control.jump && row.id !== userInfo.companyId"><a @click="jumpChild(row.id)">{{ row.companyName }}</a></span>
              <span v-else>{{ row.companyName }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="规划立项数"
            field="rdPlanCount"
            width="130"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="规划研发费(万元)"
            field="budget"
            width="160"
            remoteSort
            align="right"
          >
            <template v-slot="{row}">
              <template>{{ divAndToBit(row.budget) }}</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="归集费用(万元)"
            field="rdFunds"
            width="150"
            remoteSort
            align="right"
          >
            <template v-slot:footer="{items,columnIndex}">
              <template v-if="items[columnIndex] && items[columnIndex] !== '-'">
                <a @click="openDrawer('rdFunds')">{{ divAndToBit(items[columnIndex]) }}</a>
              </template>
              <template v-else>-</template>
            </template>
            <template v-slot="{row}">
              <template v-if="row.rdFunds"><a @click="openDrawer('rdFunds', row.companyName, row.id)">{{ divAndToBit(row.rdFunds) }}</a></template>
              <template v-else>-</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="实际立项数"
            field="rdCount"
            width="130"
            remoteSort
            align="right"
          >
            <template v-slot="{row}">
              <template v-if="row.rdCount"><a @click="openDrawer('rdList', row.companyName, row.id)">{{ row.rdCount }}</a></template>
              <template v-else>-</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="研发人数"
            field="rdEmployeeCount"
            width="130"
            remoteSort
            align="right"
          >
            <template v-slot="{row}">
              <template v-if="row.rdEmployeeCount"><a @click="openDrawer('rdEmployeeList', row.companyName, row.id)">{{ row.rdEmployeeCount }}</a></template>
              <template v-else>-</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="研发设备数"
            field="rdEquipmentCount"
            width="130"
            remoteSort
            align="right"
          >
            <template v-slot="{row}">
              <template v-if="row.rdEquipmentCount"><a @click="openDrawer('rdEquipmentList', row.companyName, row.id)">{{ row.rdEquipmentCount }}</a></template>
              <template v-else>-</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="过程材料"
            field="docFileCount"
            width="130"
            remoteSort
            align="right"
          >
            <template v-slot="{row}">
              <template>{{ row.docFileCount || '-' }}</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="机构建设数"
            field="buildCount"
            width="130"
            remoteSort
            align="right"
          >
            <template v-slot="{row}">
              <template v-if="row.buildCount"><a @click="openDrawer('build', row.companyName, row.id)">{{ row.buildCount }}</a></template>
              <template v-else>-</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="高品个数"
            field="highTechCount"
            width="130"
            remoteSort
            align="right"
          >
            <template v-slot="{row}">
              <template v-if="row.highTechCount"><a @click="openDrawer('highTech', row.companyName, row.id)">{{ row.highTechCount }}</a></template>
              <template v-else>-</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="高品占比"
            width="130"
            field="highRatio"
            align="right"
          >
            <template v-slot="{row}">
              {{ row.highTechIncome && row.totalIncome ? (row.highTechIncome / row.totalIncome).toFixed(2) : '-' }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="主营业务收入(万元)"
            field="totalIncome"
            width="170"
            remoteSort
            align="right"
          >
            <template v-slot="{row}">
              <template>{{ divAndToBit(row.totalIncome) }}</template>
            </template>
            <template v-slot:footer="{items,columnIndex}">{{ divAndToBit(items[columnIndex]) }}</template>
          </vxe-table-column>
          <vxe-table-column
            title="研发/主营占比"
            width="130"
            field="rdFundsIncomeRatio"
            align="right"
          >
            <template v-slot="{row}">
              {{ row.rdFunds && row.totalIncome ? (row.rdFunds / row.totalIncome).toFixed(2) : '-' }}
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <summary-detail-drawer ref="detailDrawer" :year="currentYear"/>
    </a-spin>
  </a-card>
</template>
<script>
import SummaryDetailDrawer from './modules/SummaryDetailDrawer'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import { mapGetters } from 'vuex'
export default {
  name: 'CompanyRdSummary',
  mixins: [yearMiXin],
  components: {
    ystable,
    SummaryDetailDrawer
  },
  data () {
    return {
      control: {
        search: this.$auth('group:companyRdSummary:search'),
        // export: this.$auth('group:companyRdSummary:export'),
        jump: this.$auth('group:companyRdSummary:jump')
      },
      queryParams: {
      },
      footer: {},
      spinning: false
    }
  },
  created () {
    this.queryParams.year = this.currentYear
  },

  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    divAndToBit (v) {
      if (!v || v === '-') {
        return '-'
      }
      return (Math.round(v / 10000 * 100) / 100).toFixed(2)
    },
    completed (data) {
      if (data.data.footer) {
        this.footer = data.data.footer
      } else {
        this.footer = {}
      }
    },
    footerMethod ({ columns, data }) {
      return [
        columns.map((a, index) => {
          if (index === 0) {
            return ''
          }
          if (index === 1) {
            return '集团合计'
          }
          return this.footer[a.property] || '-'
        })
      ]
    },
    search (refresh) {
      this.queryParams.year = this.currentYear
      this.$refs.table.refresh(refresh)
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {

          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.search(true)
    },
    moment,
    jumpChild (id) {
      this.spinning = true
      const tempwindow = window.open('about:blank')
      this.$http.post('/user/jumpChild', { companyId: id }).then(res => {
        if (res.success && res.data) {
          tempwindow.location.href = res.data
        } else {
          this.$message.error(res.errorMessage)
          tempwindow.location.href = `javascript:alert('${res.errorMessage}');`
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    openDrawer (type, companyName, companyId) {
      this.$refs.detailDrawer.open(type, companyName, companyId)
    }
    // exportData () {
    //   this.spinning = true
    //   this.$exportData('/highTechIncome/exportIncome', this.initParam(), '高品收入列表数据.xlsx', this.$message).then(res => {
    //     this.spinning = false
    //   })
    // }
  }
}
</script>
<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
  position: relative;
}
</style>
