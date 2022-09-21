<!--
 * @Author: ldx
 * @Date: 2021-03-04 08:56:04
 * @LastEditTime: 2022-05-26 14:14:48
 * @LastEditors: zdf
 * @Description: 预算表格
 * @FilePath: \RS-VUE\src\components\CapitalBudget\Budget.vue
-->
<template>
  <a-spin class="spin-wrap" :spinning="spinning" :tip="spinTip">
    <template v-if="isShowTitle">
      <div style="display: grid;  grid-template-columns: 33.3% 33.3% 33.3%; grid-template-rows: 40px;">
        <span v-for="(year,index) in budgetYear" :key="index" style="margin-right:10px;">
          {{ year }}年预算： <a-input-number
            :precision="2"
            :min="0"
            :max="100000"
            style="width:165px;"
            placeholder="请输入预算数"
            v-model="inputObj[year]"></a-input-number>
        </span>
        <a-button
          v-if="$auth('project:report:budget:save') && isShowBtn"
          type="primary"
          :disabled="isSave"
          style="width:80px;"
          @click="onSaveBudget">保存</a-button>
      </div>
      <hr style="width: 100%">
    </template>
    <div class="table-wrap">
      <div class="grid-wrap">
        <vxe-grid
          border
          size="small"
          :data="tableDatas"
          auto-resize
          highlight-hover-row
          show-overflow="title"
          show-header-overflow="title"
          max-height="100%"
        >
          <template #top>
            <div style="display: flex; justify-content: space-between;">
              <span>{{ isFinal ? '项目的经费支出' : '总预算' }}：{{ budgetSum }}</span><span>单位：万元</span>
            </div>
          </template>
          <template v-if="!isTrust">
            <vxe-table-column :title="isFinal ? '经费来源' : '经费来源预算'" header-align="center">
              <vxe-table-column field="name" title="科目" align="left" header-align="center" width="160"></vxe-table-column>
              <vxe-table-column field="value" :title="isFinal ? '决算' : '预算数'" align="right" width="100" header-align="center">
              </vxe-table-column>
            </vxe-table-column>
            <vxe-table-column :title=" isFinal ? '经费支出': '经费支出预算'" header-align="center">
              <vxe-table-column field="nameOut" title="科目" align="left" header-align="center" width="160"></vxe-table-column>
              <vxe-table-column
                v-for="(year,index) in years"
                :key="index"
                :field="year + ''"
                :title="isFinal ? year + '费用（万元）': year+'年'"
                align="right"
                min-width="100"
                header-align="center">
                <template v-slot="{ row }">
                  <template v-if="$auth('project:report:budget:save') && isEdit">
                    <span v-if="row.keyOut === 'spending01'">
                      {{ computedSpending01(row, year) }}
                    </span>
                    <span v-else-if="['100', '200', '300'].includes(row.keyOut)">
                      {{ computedParentSubject(row, year) }}
                    </span>
                    <a-input-number v-else style="width: 100%" v-model="row[year]" :precision="2" :min="0"></a-input-number>
                  </template>
                  <span v-else>{{ row[year] }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                v-if="years.length > 1"
                field="totalBudget"
                title="合计"
                align="right"
                minWidth="100"
                header-align="center">
                <template v-slot="{ row }">
                  {{ computedTotalBudget(row, years) }}
                </template>
              </vxe-table-column>
            </vxe-table-column>
          </template>
        </vxe-grid>
      </div>
      <div class="btn">
        <a-tooltip title="根据归集数据实时计算预算">
          <a-button v-if="$auth('project:report:budget:save') && isEdit" style="margin:8px 8px;width:80px;" type="primary" @click="onGetBudgetList">同步</a-button>
        </a-tooltip>
        <a-button v-if="$auth('project:report:budget:save') && isEdit" style="margin:8px 8px;width:80px;" type="primary" @click="onSaveTable">保存</a-button>
      </div>
    </div>
  </a-spin>
</template>

<script>
import { mapGetters } from 'vuex'
import { setBudget } from '@/api/project/budget'
const mathjs = require('mathjs')
export default {
  name: 'Budget',
  props: {
    projectData: {
      type: Object,
      required: true
    },
    isFilter: { // 是否过滤
      type: Boolean,
      default: false
    },
    isEdit: {
      type: Boolean,
      default: false
    },
    isShowBtn: {
      type: Boolean,
      default: false
    },
    url: {
      type: String,
      default: '/budget/queryBudgetList'
    },
    isFinal: {
      type: Boolean,
      default: false
    },
    isShowTitle: {
      type: Boolean,
      default: true
    },
    isTrust: { // 是否委托项目，委托项目只展示预算总额
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      spinning: false,
      spinTip: '加载中...',
      budgetSum: 0, // 总预算
      noModify: true,
      projectId: undefined,
      confirmLoading: false,
      tableDatas: [],
      type3: [],
      type4: [],
      types: [],
      maxMap: {},
      inputObj: {},
      isSave: false,
      URL: this.url
    }
  },
  computed: {
    ...mapGetters(['currentYear']),
    years () {
      const result = []
      for (let index = this.projectData.beginYear; index <= this.projectData.endYear; index++) {
        result.push(index)
      }
      return result
    },
    budgetYear () {
      const result = []
      for (let index = this.projectData.beginYear; index <= this.projectData.endYear; index++) {
        this.$set(this.inputObj, index, undefined)
        result.push(index)
      }
      return result
    },
    // 合计列
    computedTotalBudget: function () {
      return function (row, years) {
        let result = 0
        years.forEach(year => {
          result += row[year] || 0
        })
        return Math.round(result * 100) / 100
      }
    },
    // 支出预算合计行
    computedSpending01: function () {
      return function (row, year) {
        let result = 0
        this.tableDatas.forEach(item => {
          if (['100', '200', '300', '400', '500', '600'].includes(item.keyOut)) {
            result += item[year] || 0
          }
        })
        row[year] = result = Math.round(result * 100) / 100
        return result
      }
    },
    // 子科目
    computedParentSubject: function () {
      return function (row, year) {
        let result = 0
        this.tableDatas.forEach(item => {
          if (item.keyOut.length >= 5 && item.keyOut.startsWith(row.keyOut.slice(0, 2))) {
            result += item[year] || 0
          }
        })
        row[year] = result = Math.round(result * 100) / 100
        return result
      }
    }
  },
  watch: {
    'projectData.id' (obj) {
      this.$nextTick(() => {
        this.clearDataAndLoad()
      })
    },
    inputObj: {
      deep: true,
      handler (v) {
        let total = 0
        this.isSave = true
        for (const key in v) {
          if (Object.hasOwnProperty.call(v, key)) {
            const data = v[key]
            if (data) {
              try {
                total = mathjs.add(mathjs.bignumber(total), mathjs.bignumber(data))
              } catch (error) {}
            }
          }
        }
        this.isSave = !Object.values(v).every(item => item === 0 || item)
        this.budgetSum = parseInt(total)
      }
    },
    budgetSum (val) {
      this.tableDatas[3].value = val
      this.tableDatas[0].value = val
    }
  },
  created () {
    this.loadSysDictionData()
  },
  methods: {
    clearDataAndLoad () {
      this.tableDatas.forEach(item => {
        item.value = 0
        item.id = undefined
        item.valueOut = 0
        item.outId = undefined
      })
      this.loadData()
    },
    // 核心拼数据代码
    loadData () {
      this.noModify = true
      this.projectId = this.projectData.id
      this.spinning = true
      this.$nextTick(() => {
        this.$http.get(this.URL, { params: { projectId: this.projectId, year: this.currentYear } })
          .then(res => {
            this.handleTable(res.data)
          }).finally(res => {
            if (this.isFinal) {
              this.budgetSum = this.tableDatas[0].valueOut
            }
            this.spinning = false
            this.URL = '/budget/queryBudgetList'
          })
      })
    },
    loadSysDictionData () {
      this.$nextTick(() => {
        this.$http.get('/budget/getSysDictionaryModelist')
          .then(res => {
            var dataArr = []
            var allKey = {}
            let t = 0
            let dIndex = 0
            for (let i = 0; i < res.data.length; i++) {
              const d = res.data[i]
              if (this.isFilter) {
                const remoseItem = [400, 500, 600]
                if (remoseItem.includes(d['parentKey'] * 1)) {
                  continue
                }
              }
              if (this.isFinal && d.key === 'spending01') {
                d.value = '支出费用合计'
              }
              if (t !== d.type) {
                t = d.type
                dIndex = 0
              }
              if (d['parentKey'] !== '0') {
                allKey[d['parentKey']] = allKey[d['parentKey']] ? allKey[d['parentKey']] + 1 : 1
              }
              if (t === 3) {
                dataArr[dIndex] = {
                  type: d['type'],
                  key: d['key'],
                  name: d['value'],
                  value: 0,
                  nameOut: undefined,
                  valueOut: 0,
                  parentKey: d['parentKey'],
                  parentKeyOut: undefined
                }
              } else {
                if (dataArr[dIndex]) {
                  dataArr[dIndex]['typeOut'] = d['type']
                  dataArr[dIndex]['keyOut'] = d['key']
                  dataArr[dIndex]['nameOut'] = d['value']
                  dataArr[dIndex]['valueOut'] = 0
                  dataArr[dIndex]['parentKeyOut'] = d['parentKey']
                  this.years.forEach((year, index) => {
                    dataArr[dIndex][year] = 0
                  })
                } else {
                  dataArr[dIndex] = {
                    typeOut: d['type'], keyOut: d['key'], nameOut: d['value'], valueOut: 0, parentKeyOut: d['parentKey']
                  }
                  this.years.forEach((year, index) => {
                    dataArr[dIndex][year] = 0
                  })
                }
              }
              dIndex++
            }
            dataArr.forEach(item => {
              if (allKey[item.keyOut]) {
                item.hasChild = true
              }
            })
            this.tableDatas = dataArr
            this.loadData()
          })
      })
    },
    onSaveBudget () {
      this.spinning = true
      this.confirmLoading = true
      const param = []
      for (const key in this.inputObj) {
        if (Object.hasOwnProperty.call(this.inputObj, key)) {
          const budget = this.inputObj[key]
          param.push({
            projectId: this.projectData.id,
            year: key,
            budget
          })
        }
      }
      setBudget(param).then(data => {
        this.$emit('success', this.inputObj[this.currentYear] || 0)
        this.$message.success('操作成功！')
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.spinning = false
        this.confirmLoading = false
      })
    },
    onSaveTable () {
      const result = []
      this.tableDatas.forEach(item => {
        const params = []
        this.years.forEach(year => {
          const keyValue = item.keyOut === 'spending01' ? item.keyOut : 't' + item.keyOut
          params.push({
            projectId: this.projectId,
            year: year,
            key: keyValue,
            value: item[year],
            type: 4
          })
        })
        result.push(...params)
      })
      this.spinning = true
      this.$http.post('/project/setBudgetTable', result).then(res => {
        if (res.success) {
          this.$message.success('操作成功')
        } else {
          this.$message.error(res.errorMessage || '操作失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    onGetBudgetList () {
      this.URL = '/budget/getBudgetList'
      this.loadSysDictionData()
      // this.noModify = true
      // this.projectId = this.projectData.id
      // this.spinning = true
      // this.$nextTick(() => {
      //   this.$http.get('/budget/getBudgetList', { params: { projectId: this.projectId, year: this.currentYear } }).then(({ data }) => {
      //     this.handleTable(data)
      //   }).finally(() => {
      //     this.spinning = false
      //   })
      // })
    },
    handleTable (yearDatas) {
      for (const year in yearDatas) {
        if (year !== 'total') {
          if (this.inputObj[year]) {
            this.inputObj[year] = yearDatas[year]['budget']
          } else {
            this.$set(this.inputObj, year * 1, yearDatas[year]['budget'] ? yearDatas[year]['budget'] : undefined)
          }
          if (Object.hasOwnProperty.call(yearDatas, year)) {
            const yearData = yearDatas[year]
            this.tableDatas.forEach(row => {
              if (Object.hasOwnProperty.call(row, year)) {
                if (yearData[row.keyOut] && row.keyOut === 'spending01') {
                  row[year] = yearData[row.keyOut]
                }
                if (yearData['t' + row.keyOut] && row.keyOut !== 'spending01') {
                  row[year] = yearData['t' + row.keyOut]
                }
              }
            })
          }
        } else {
          if (Object.hasOwnProperty.call(yearDatas, year)) {
            const yearData = yearDatas[year]
            this.tableDatas.forEach(row => {
              row['totalBudget'] = 0
              if (yearData[row.keyOut] && row.keyOut === 'spending01') {
                row['totalBudget'] = yearData[row.keyOut]
              }
              if (yearData['t' + row.keyOut] && row.keyOut !== 'spending01') {
                row['totalBudget'] = yearData['t' + row.keyOut]
              }
            })
          }
        }
      }
    }
  }
}
</script>
<style lang="less" scoped>
.spin-wrap {
  height: 100%;
  width: 100%;
  & /deep/ .ant-spin-container {
    height: 100%;
    display: flex;
    flex-direction: column;
  }
  .table-wrap {
    width: 100%;
    flex: 1;
    height: 0;
    .grid-wrap {
      height: calc(100% - 40px);
      overflow: auto;
    }
  }
  .btn-wrap {
    padding: 8px 0 ;
  }
}
.btn {
  display: flex;
  justify-content: flex-end;
}
</style>
