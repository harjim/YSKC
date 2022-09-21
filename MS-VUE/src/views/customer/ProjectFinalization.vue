<template>
  <div>
    <a-spin
      tip="请稍后..."
      :spinning="spin"
    >
      <a-card
        :bordered="false"
        v-show="!cardTableIsShow && !showBudgetTable"
      >
        <a-form layout="inline">
          <a-form-item label="客户名称">
            <a-input
              placeholder="请输入客户名称"
              v-model="queryParams.companyName"
            />
          </a-form-item>
          <a-form-item label="所属部门">
            <dept-select
              style="width: 165px"
              v-model="queryParams.deptId"
            />
          </a-form-item>
          <a-form-item label="项目年份">
            <year-select
              style="width: 120px"
              v-model="queryParams.year"
              placeholder="项目年份"
            />
          </a-form-item>
          <a-form-item label="技术人员">
            <search-select
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              :multiple="true"
              placeholder="技术人员"
              style="width: 165px"
              v-model="queryParams.tIds"
            />
          </a-form-item>
          <a-form-item label="财务人员">
            <search-select
              style="width: 165px"
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              :multiple="true"
              placeholder="财务人员"
              v-model="queryParams.fIds"
            />
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="$refs.table.refresh(true)"
            >查询</a-button>
          </a-form-item>
        </a-form>
        <ystable
          rowId="id"
          ref="table"
          queryUrl="/projectProgress/getFinalList"
          :params="getParams()"
          :toolbar="{
            custom: true,
            zoom: true,
            refresh: true,
          }"
        >
          <template v-slot:buttons>
            <span>
              <a-button
                type="primary"
                v-if="$auth('customer:finalization:export')"
                @click="exportDataList()"
              >
                导出
              </a-button>
            </span>
          </template>

          <vxe-table-column
            type="seq"
            title="序号"
            width="50"
            fixed="left"
          ></vxe-table-column>
          <vxe-table-column
            title="客户名称"
            field="companyName"
            :min-width="220"
            align="left"
            fixed="left"
            show-header-overflow
            show-overflow="tooltip"
          />
          <vxe-table-column
            title="所属部门"
            field="deptName"
            :width="200"
            align="left"
            show-header-overflow
            show-overflow="tooltip"
          />
          <vxe-table-column
            title="项目年份"
            show-header-overflow
            field="year"
            :width="120"
            remoteSort
            align="center"
          />
          <vxe-table-column
            title="预算(万元)"
            show-header-overflow
            field="budget"
            :width="140"
            align="right"
            remoteSort
          >
            <template v-slot="{ row }">
              <template v-if="$auth('customer:finalization:rdDetail')">
                <a-tooltip title="点击进入预算详情">
                  <a @click="budgetShow(row)">{{ row.budget ? (row.budget / 10000).toFixed(2) : '-' }}</a>
                </a-tooltip>
              </template>
              <template v-else>{{ row.budget ? (row.budget / 10000).toFixed(2) : '-' }}</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="归集(万元)"
            show-header-overflow
            field="rdFunds"
            :width="150"
            align="right"
            remoteSort
          >
            <template v-slot="{ row }">
              <template v-if="$auth('customer:finalization:rdDetail')">
                <a-tooltip title="点击进入归集详情">
                  <a @click="checkoutTabel(row, true)">{{ row.rdFunds ? (row.rdFunds / 10000).toFixed(2) : '-' }}</a>
                </a-tooltip>
              </template>
              <template v-else>{{ row.rdFunds ? (row.rdFunds / 10000).toFixed(2) : '-' }}</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="规划立项数"
            show-header-overflow
            field="cnt"
            :width="140"
            align="right"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="技术立项"
            show-header-overflow
            field="rdCount"
            :width="120"
            align="right"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="技术人员"
            show-header-overflow
            field="techRealName"
            :width="120"
            align="left"
          />
          <vxe-table-column
            title="财务人员"
            show-header-overflow
            field="financeRealName"
            :width="120"
            align="left"
          />
        </ystable>
      </a-card>
      <a-card v-show="cardTableIsShow">
        <div class="interval">
          <span class="tabelTitle">{{ drawerTitle }}</span>
          <a-button
            style="margin-left:18px;"
            type="primary"
            @click="checkoutTabel(null, false)"
          >返回</a-button>
        </div>
        <div class="interval">
          <a-button
            :disabled="noSelect"
            type="primary"
            v-if="$auth('customer:finalization:final')"
            @click="auditBatch()"
          >审核</a-button>
        </div>
        <a-table
          id="cardTableIs"
          size="small"
          rowKey="id"
          ref="tables"
          :dataSource="DrawerTableData"
          :expandIcon="expandIcon"
          :pagination="false"
          :scroll="{ x: 1930, y: 600 }"
        >
          <a-table-column
            title="RD"
            data-index="pname"
            align="left"
            :width="200"
          >
            <template slot-scope="text, record">
              <span v-if="record.pname">{{ `${record.rdTitle}--${record.pname}` }}</span>
              <span v-else>{{ record.rdTitle }}</span>
            </template>
          </a-table-column>
          <a-table-column
            title="起止时间"
            data-index="beginAndEnd"
            align="center"
            :width="190"
          >
            <template slot-scope="text, record">
              <span v-if="record.beginDate">{{ record.beginDate + '~' + record.endDate }}</span>
            </template>
          </a-table-column>
          <a-table-column
            title="工资"
            data-index="salary"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="五险一金"
            data-index="insurance"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="设备折旧"
            data-index="prod"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="仪器折旧"
            data-index="lab"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="材料"
            data-index="material"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="动力"
            data-index="stimulus"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="燃料"
            data-index="fuel"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="试制"
            data-index="trial"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="修理"
            data-index="repair"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="检测"
            data-index="inspection"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="样机"
            data-index="sampleMachine"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="设计"
            data-index="design"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="摊销"
            data-index="amortization"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="差旅费"
            data-index="travel"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="其他"
            data-index="other"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="合计"
            data-index="rdTotal"
            align="right"
            :width="90"
            :customRender="numRender"
          />
          <a-table-column
            title="状态"
            data-index="status"
            align="center"
            :width="90"
          />
          <a-table-column
            title="操作"
            data-index="operate"
            align="center"
            :width="150"
          />
          <a-table
            slot="expandedRowRender"
            slot-scope="record"
            size="small"
            rowKey="month"
            :dataSource="record.monthFundsStatus"
            :pagination="false"
            :showHeader="false"
            :row-selection="{selectedRowKeys:selectKeys[record.id],getCheckboxProps:checkProps,onChange: (keys,rows)=>onSelectChange(keys,rows,record.id)}"
          >
            <a-table-column
              title="月份"
              data-index="month"
              align="center"
              :width="370"
            />
            <a-table-column
              title="工资"
              data-index="salary"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="五险一金"
              data-index="insurance"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="设备折旧"
              data-index="prod"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="仪器折旧"
              data-index="lab"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="材料"
              data-index="material"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="动力"
              data-index="stimulus"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="燃料"
              data-index="fuel"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="试制"
              data-index="trial"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="检测"
              data-index="inspection"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="修理"
              data-index="repair"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="样机"
              data-index="sampleMachine"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="设计"
              data-index="design"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="摊销"
              data-index="amortization"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="差旅费"
              data-index="travel"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="其他"
              data-index="other"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="合计"
              data-index="rdTotal"
              align="right"
              :width="90"
              :customRender="numRender"
            />
            <a-table-column
              title="状态"
              data-index="status"
              align="center"
              :width="90"
            >
              <template slot-scope="text">
                <template v-if="text === 0">未审核</template>
                <template v-if="text === 3">已审核</template>
                <template v-else-if="text === 4">已撤回</template>
              </template>
            </a-table-column>
            <a-table-column
              title="操作"
              data-index="operate"
              align="center"
              :width="150"
            >
              <template slot-scope="text, row">
                <template v-if="row.status !== 3">
                  <span v-if="$auth('customer:finalization:final')">
                    <a :disabled="row.nothing">
                      <a-popconfirm
                        title="是否确定审核?"
                        @confirm="operateHandle(row, record, 3)"
                      >审核</a-popconfirm>
                    </a>
                  </span>
                </template>
                <template v-else>
                  <span v-if="$auth('customer:finalization:recall')">
                    <a-popconfirm
                      title="是否确定撤回?"
                      @confirm="operateHandle(row, record, 4)"
                    >
                      <a>撤回</a>
                    </a-popconfirm>
                  </span>
                </template>
              </template>
            </a-table-column>
          </a-table>
        </a-table>
      </a-card>
      <a-card v-if="showBudgetTable">
        <div class="interval">
          <a-button
            type="primary"
            @click="showBudgetTable = false"
          >返回</a-button>
          <span class="tabelTitle">{{ budgetTitle }}</span>
        </div>
        <vxe-grid
          id="/rsProject/getProjectList"
          ref="budgetTable"
          highlight-hover-row
          show-overflow
          show-header-overflow
          resizable
          auto-resize
          :data="budgetTableData"
          :customConfig="{ storage: { visible: true, resizable: true } }"
        >
          <vxe-table-column
            type="seq"
            title="序号"
            width="60"
            fixed="left"
          />
          <vxe-table-column
            field="RD"
            title="RD"
            width="120"
            fixed="left"
          >
            <template v-slot="{ row }">{{ `${row.rdTitle}--${row.pname}` }}</template>
          </vxe-table-column>
          <vxe-table-column
            field="pname"
            title="项目名称"
            :width="300"
            fixed="left"
          />
          <vxe-table-column
            field="perid"
            title="项目周期"
            width="200"
            align="center"
          >
            <template v-slot="{ row }">{{ row.beginDate + '~' + row.endDate }}</template>
          </vxe-table-column>
          <vxe-table-column
            field="estimateExpense"
            title="预算(万元)"
            width="140"
            align="right"
          >
            <template v-slot="{ row }">
              <span v-if="$auth('customer:finalization:final') || $auth('customer:finalization:recall')">
                <a @click="$refs.budgetModal.show(row, currentProject.year)">{{
                  row.estimateExpense ? (row.estimateExpense / 10000).toFixed(2) : '-'
                }}</a>
              </span>
              <span v-else>{{ row.estimateExpense ? (row.estimateExpense / 10000).toFixed(2) : '-' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            field="status"
            title="状态"
            width="140"
            align="center"
          >
            <template v-slot="{ row }">
              <template v-if="!row.status">未审核</template>
              <template v-if="row.status === 3">已审核</template>
              <template v-else-if="row.status === 4">已撤回</template>
            </template>
          </vxe-table-column>
        </vxe-grid>
      </a-card>
      <budget-modal
        ref="budgetModal"
        @ok="budgetShow(currentProject)"
      />
    </a-spin>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import BudgetModal from './modules/BudgetModal'
import { SearchSelect, YearSelect, DeptSelect } from '@/components/Selects'
import moment from 'moment'
export default {
  components: {
    ystable,
    SearchSelect,
    YearSelect,
    DeptSelect,
    BudgetModal
  },
  data () {
    return {
      noSelect: true,
      budgetTableData: [],
      budgetTitle: '',
      showBudgetTable: false,
      spin: false,
      queryParams: {},
      currentCompanyId: undefined,
      drawerVisible: false,
      drawerTitle: '',
      DrawerTableData: [],
      selectKeys: {},
      cardTableIsShow: false,
      selectRows: {},
      currentProject: undefined
    }
  },
  methods: {
    checkProps (row) {
      return { props: { disabled: row.nothing || row.status === 3 } }
    },
    onSelectChange (keys, rows, id) {
      this.$set(this.selectKeys, id, keys)
      this.$set(this.selectRows, id, rows)
      if (keys && keys.length) {
        this.noSelect = false
      } else {
        let temp = true
        for (const k in this.selectKeys) {
          if (this.selectKeys[k] && this.selectKeys[k].length) {
            temp = false
            break
          }
        }
        this.noSelect = temp
      }
    },
    getParams () {
      const params = { companyName: this.queryParams.companyName, deptId: this.queryParams.deptId, year: this.queryParams.year, sign: 2 }
      if (this.queryParams.tIds) { params.tIds = this.queryParams.tIds.map(a => a.id) }
      if (this.queryParams.fIds) { params.fIds = this.queryParams.fIds.map(a => a.id) }
      return params
    },
    moment,
    numRender (text, record) {
      if (!text) {
        return 0
      }
      return text
    },
    checkoutTabel (record, isShow) {
      this.currentCompanyId = undefined
      if (record) {
        this.currentCompanyId = record.companyId
      }

      this.cardTableIsShow = isShow
      if (record) {
        this.getDrawerData(record)
        this.drawerTitle = '[' + record.companyName + ']' + '-' + '[' + record.year + '年] - 归集数据'
      }
      if (!isShow) {
        this.DrawerTableData = []
      }
    },
    getDrawerData (record) {
      this.spin = true
      this.selectKeys = {}
      this.selectRows = {}
      this.noSelect = true
      this.$http.get('/rsProject/getRdFunds', { params: { companyId: record.companyId, year: record.year } })
        .then(res => {
          this.DrawerTableData = res.data
          if (this.DrawerTableData && this.DrawerTableData.length) {
            const row = {
              salary: 0, insurance: 0, prod: 0, lab: 0, material: 0, stimulus: 0, fuel: 0, trial: 0, repair: 0, inspection: 0, design: 0, amortization: 0, travel: 0, other: 0, rdTotal: 0, sampleMachine: 0
            }
            this.DrawerTableData.forEach(item => {
              this.$set(this.selectKeys, item.id, [])
              this.$set(this.selectRows, item.id, [])
              for (const key in row) {
                if (item[key]) {
                  row[key] = Math.round((row[key] + item[key]) * 100) / 100
                }
              }
            })
            row['rdTitle'] = '合计'
            this.DrawerTableData.push(row)
          }
          this.spin = false
          return this.DrawerTableData
        })
    },
    expandIcon (props) {
      if (props.record.rdTitle === '合计') {
        return ''
      }
      if (props.expanded) {
        return (<a-icon type="down" onClick={e => { props.onExpand(props.record, e) }} />)
      } else {
        return (<a-icon type="right" onClick={e => { props.onExpand(props.record, e) }} />)
      }
    },
    operateHandle (row, record, val) {
      this.spin = true
      var month = row.month
      month = month.replace('年', '-').replace('月', '')
      this.audit({ audits: [{ projectId: row.projectId, months: [month + '-' + '01' + ' 00:00:00'] }], companyId: this.currentCompanyId, status: val }, [row])
    },
    auditBatch () {
      const audits = []
      const rows = []
      for (const pId in this.selectKeys) {
        if (this.selectKeys[pId] && this.selectKeys[pId].length) {
          audits.push({ projectId: pId, months: this.selectKeys[pId].map(m => m.replace('年', '-').replace('月', '') + '-' + '01' + ' 00:00:00') })
          rows.push(...this.selectRows[pId])
        }
      }
      this.audit({ audits, status: 3, companyId: this.currentCompanyId }, rows)
    },
    audit (values, rows) {
      this.spin = true
      this.$http.post('/rsProject/setStatus', values).then(res => {
        if (res.success && res.data) {
          const projectIds = {}
          rows.forEach(item => {
            projectIds[item.projectId] = true
            this.$set(item, 'status', values.status)
          })
          this.DrawerTableData.forEach(item => {
            if (projectIds[item.id]) {
              this.$set(item, 'bakMonthFundsStatus', item.monthFundsStatus)
              this.$set(item, 'monthFundsStatus', [])
              this.$nextTick(() => {
                this.$set(item, 'monthFundsStatus', item.bakMonthFundsStatus)
                delete item.bakMonthFundsStatus
              })
            }
          })
          for (const pId in this.selectKeys) {
            if (this.selectKeys[pId]) {
              this.$set(this.selectKeys, pId, [])
              this.$set(this.selectRows, pId, [])
            }
          }
          this.noSelect = true
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '操作失败，请稍后再试。')
        }
        this.spin = false
      })
    },
    exportDataList () {
      this.spin = true
      this.$exportData('/projectProgress/export', this.getParams(), `归集审核列表.xls`, this.$message).then(res => {
        this.spin = false
      })
    },
    budgetShow (row) {
      this.currentProject = row
      this.spin = true
      this.budgetTitle = '[' + row.companyName + ']' + '-' + '[' + row.year + '年] - 预算数据'
      this.budgetTableData = []
      this.showBudgetTable = true
      this.$http.get('/rsProject/getProjectList', { params: { companyId: row.companyId, year: row.year } })
        .then(res => {
          if (res.success && res.data) {
            this.budgetTableData = res.data
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '获取数据失败')
          }
          this.spin = false
        })
    }
  }
}
</script>

<style lang="less" scoped>
.interval {
  margin-bottom: 8px;
}
.tabelTitle {
  margin-left: 12px;
  font-size: 20px;
  font-weight: 600;
}
.coustomDrawer /deep/ .ant-drawer-content-wrapper {
  width: 60%;
}
#scrollContent {
  height: calc(100% - 40px);
  overflow: auto;
}
#cardTableIs {
  /deep/ .ant-table-small {
    border: none;
    border-radius: none;
  }
}
</style>
