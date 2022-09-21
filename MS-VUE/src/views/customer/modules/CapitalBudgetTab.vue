<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spin">
      <div class="table-page-search-wrapper">
        <a-table bordered :pagination="false" size="small" rowKey="keyOut" :dataSource="tableDatas">
          <a-table-column-group>
            <span slot="title" style="text-align:center">经费来源预算(单位:万元)</span>
            <a-table-column title="科目" data-index="name" :width="380" align="center">
              <template slot-scope="text">
                <span style="text-align:left;float:left;">{{ text }}</span>
              </template>
            </a-table-column>
            <a-table-column title="预算数" data-index="value" :width="380" align="right">
              <template slot-scope="text,record">{{ record.key ? text.toFixed(2) : '' }}</template>
            </a-table-column>
          </a-table-column-group>
          <a-table-column-group>
            <span slot="title" style="text-align:center;">经费支出预算(单位:万元)</span>
            <a-table-column title="科目" align="center" data-index="nameOut" :width="380">
              <template slot-scope="text">
                <span style="text-align:left;float:left;">{{ text }}</span>
              </template>
            </a-table-column>
            <a-table-column title="预算数" align="right" data-index="valueOut" :width="380">
              <template slot-scope="text">{{ text.toFixed(2) }}</template>
            </a-table-column>
          </a-table-column-group>
        </a-table>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import moment from 'moment'
export default {
  name: 'CapitalbudgetTab',
  components: {
  },
  props: {
    projectData: {
      type: Object,
      required: true
    },
    yMonth: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      spin: false,
      noModify: true,
      projectId: undefined,
      confirmLoading: false,
      tableDatas: [],
      type3: [],
      type4: [],
      types: [],
      visible: true,
      month: undefined,
      maxMap: {}
    }
  },
  watch: {
    projectData (item) {
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
    loadData () {
      this.noModify = true
      this.projectId = this.projectData.id
      this.month = this.yMonth ? moment(this.yMonth + '-01') : undefined
      this.$nextTick(() => {
        this.spin = true
        this.$http.get('/budget/queryBudgetList', { params: { projectId: this.projectId, month: this.month } })
          .then(res => {
            var arr = []
            this.maxMap = res.data.maxMap
            res.data.data.forEach(item => {
              arr[item.sole] = item
            })
            for (let i = 0; i < this.tableDatas.length; i++) {
              const item = this.tableDatas[i]
              if (arr[item.key]) {
                item.value = arr[item.key]['value']
                item.id = arr[item.key]['id']
              } else {
                item.value = 0
                item.id = undefined
              }
              if (arr[item.keyOut]) {
                item.valueOut = arr[item.keyOut]['value']
                item.outId = arr[item.keyOut]['id']
              } else {
                item.valueOut = 0
                item.outId = undefined
              }
            }
          }).then(res => {
            this.spin = false
          })
      })
    },
    loadSysDictionData () {
      this.$nextTick(() => {
        this.$getBudgetDictionary()
          .then(data => {
            if (!Array.isArray(data)) {
              this.$message.error(data.errorMessage ? data.errorMessage : '获取数据失败')
              return
            }
            var dataArr = []
            var allKey = {}
            let t = 0
            let dIndex = 0
            for (let i = 0; i < data.length; i++) {
              const d = data[i]
              if (t !== d.type) {
                t = d.type
                dIndex = 0
              }
              if (d['parentKey'] !== '0') {
                allKey[d['parentKey']] = allKey[d['parentKey']] ? allKey[d['parentKey']] + 1 : 1
              }
              if (t === 3) {
                dataArr[dIndex] = { type: d['type'], key: d['key'], name: d['value'], value: 0, nameOut: undefined, valueOut: 0, parentKey: d['parentKey'], parentKeyOut: undefined }
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
          })
      })
    },
    onCellChange (value, record, valueType) {
      this.noModify = false
      if (!value || !Number(value)) {
        value = 0
      }
      var max
      if (valueType === 'value') {
        max = this.maxMap[record.key] ? this.maxMap[record.key] : 0
      } else {
        max = this.maxMap[record.keyOut] ? this.maxMap[record.keyOut] : 0
      }
      if (this.month) {
        if (value > max) {
          value = max
        }
      } else {
        if (value < max) {
          value = max
        }
      }

      this.$set(record, valueType, parseFloat(value))
      var total = 0
      var rootParent = this.tableDatas[0]
      if (record.type === 3 && valueType === 'value') {
        for (let i = 1; i < this.tableDatas.length; i++) {
          const item = this.tableDatas[i]
          if (item.type !== 3) {
            break
          }
          total += parseFloat(item.value)
        }
        rootParent.value = total
        return
      }
      var parent
      var allTotal = 0 - rootParent.valueOut
      const parentKey = record.parentKeyOut
      this.tableDatas.forEach(item => {
        if (!item.hasChild) {
          allTotal += parseFloat(item.valueOut)
        }
        if (item.keyOut === parentKey) {
          parent = item
          return
        }
        if (item.parentKeyOut === parentKey && item.valueOut) {
          total += parseFloat(item.valueOut)
        }
      })
      if (parent) {
        parent.valueOut = total
      }
      rootParent.valueOut = allTotal
    }
  }
}
</script>

<style>
</style>
