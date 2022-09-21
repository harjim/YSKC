<!--
 * @Author: ldx
 * @Date: 2021-03-04 08:56:04
 * @LastEditTime: 2021-03-10 17:51:50
 * @LastEditors: ldx
 * @Description: 预算表格
 * @FilePath: \MS-VUE\src\components\CapitalBudget\CapitalBudget.vue
-->
<template>
  <a-spin class="spin-wrap" :spinning="spinning" :tip="spinTip">
    <div class="table-wrap">
      <vxe-grid
        border
        size="small"
        :data="tableDatas"
        highlight-hover-row
        show-overflow
        show-header-overflow
      >
        <template #top>
          <span>{{ isFinal ? '项目的经费支出' : '总预算' }}: {{ budgetSum }} 万元</span>
        </template>
        <vxe-table-column title="经费来源预算(单位:万元)" header-align="center">
          <vxe-table-column field="name" title="科目" align="left" header-align="center"></vxe-table-column>
          <vxe-table-column field="value" title="预算数" align="right" header-align="center">
            <template #default="{ row, rowIndex}">
              <a-input-number
                v-if="row.name && rowIndex !== 0 && isEdit"
                :value="row.value"
                :precision="2"
                :step="1"
                :key="`value${row.key}`"
                @change="(val)=>onCellChange(val,row,'value')"
              />
              <span v-else> {{ row.value }}</span>
            </template>
          </vxe-table-column>
        </vxe-table-column>
        <vxe-table-column :title=" isFinal ? '经费支出': '经费支出预算(单位:万元)'" header-align="center">
          <vxe-table-column field="nameOut" title="科目" align="left" header-align="center"></vxe-table-column>
          <vxe-table-column field="valueOut" :title="isFinal ? '费用（万元）': '预算数'" align="right" header-align="center">
            <template #default="{ row }">
              {{ row.valueOut ? row.valueOut: 0 }}
            </template>
          </vxe-table-column>
        </vxe-table-column>
      </vxe-grid>
    </div>
    <div v-if="$auth('project:report:budget:save') && isShowBtn" class="btn-wrap">
      <a-button type="primary" :loading="confirmLoading" :disabled="noModify" @click="handleSubmit()">保存</a-button>
    </div>
  </a-spin>
</template>

<script>
export default {
  name: 'CapitalBudget',
  props: {
    projectData: {
      type: Object,
      required: true
    },
    isFilter: { // 是否过滤
      type: Boolean,
      defaultL: false
    },
    isEdit: {
      type: Boolean,
      default: false
    },
    isShowBtn: {
      type: Boolean,
      default: false
    },
    isFinal: {
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
      maxMap: {}
    }
  },
  watch: {
    projectData (obj) {
      this.$nextTick(() => {
        this.clearDataAndLoad()
      })
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
    getTitle () {
      return this.isFinal ? '经费支出' : '经费支出预算(单位:万元)'
    },
    loadData () {
      this.noModify = true
      this.projectId = this.projectData.id
      this.$nextTick(() => {
        this.spinning = true
        this.$http.get('/budget/queryBudgetList', { params: { projectId: this.projectId } })
          .then(res => {
            const data = res.data
            const tempAry = []
            this.tableDatas.forEach((item) => {
              if (item.type === 3) {
                item.value = 0
                if (data[item.key]) {
                  item.value = data[item.key]
                }
              }
              item.valueOut = 0
              if (data[item.keyOut] && item.keyOut === 'spending01') {
                item.valueOut = data[item.keyOut]
              }
              if (data['t' + item.keyOut] && item.keyOut !== 'spending01') {
                item.valueOut = data['t' + item.keyOut]
              }
              tempAry.push(item)
            })
            this.tableDatas = tempAry
            this.budgetSum = this.getBudget()
            this.$emit('getBudgetSum', this.budgetSum)
          }).then(res => {
            this.spinning = false
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
                  parentKeyOut: undefined }
              } else {
                if (dataArr[dIndex]) {
                  dataArr[dIndex]['typeOut'] = d['type']
                  dataArr[dIndex]['keyOut'] = d['key']
                  dataArr[dIndex]['nameOut'] = d['value']
                  dataArr[dIndex]['valueOut'] = 0
                  dataArr[dIndex]['parentKeyOut'] = d['parentKey']
                } else {
                  dataArr[dIndex] = { typeOut: d['type'], keyOut: d['key'], nameOut: d['value'], valueOut: 0, parentKeyOut: d['parentKey'] }
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
    getBudget () {
      if (this.tableDatas && this.tableDatas.length) {
        if (this.tableDatas[0]['key'] === 'source01') {
          return this.tableDatas[0].valueOut
        }
      }
    },
    onCellChange (value, record, valueType) {
      if (!value) {
        value = 0
      }
      record.value = value
      this.noModify = false
      let total = 0
      const root = this.tableDatas[0]
      for (let index = 0; index < this.tableDatas.length; index++) {
        const item = this.tableDatas[index]
        if (index && item.type === 3 && item.value) {
          total += item.value * 1000
        }
      }
      root.value = total / 1000
    },
    handleSubmit () {
      this.spinning = true
      this.confirmLoading = true
      const value = {}
      const dataMap = {}
      // let sumData = 0
      for (let i = 0; i < this.tableDatas.length; i++) {
        const d = this.tableDatas[i]
        if (d.type === 3) {
          // if (d.key === 'source01') {
          //   sumData = d.value * 1000
          // }
          if (d.value >= 0) {
            dataMap[d.key] = d.value * 10000
          }
        }
      }
      value.projectId = this.projectId
      value.dataMap = dataMap
      this.$http.post('/budget/save', value).then(res => {
        if (res.success && res.data) {
          this.noModify = true
          this.$message.success('操作成功')
          this.$emit('getAllBudget')
          // 更新表单数据
          // if (sumData) {
          //   this.projectData.estimateExpense = sumData
          // }
          this.clearDataAndLoad()
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '操作失败')
        }
      }).catch((error) => {
        this.$message.error(error)
      }).finally(() => {
        this.confirmLoading = false
        this.openEditMode = false
        this.spinning = false
      })
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
  }
  .table-wrap {
    width: 100%;
  }
  .btn-wrap {
    padding: 8px 0 ;
  }
}
</style>
