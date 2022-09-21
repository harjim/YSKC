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
            <a-table-column title="预算数" data-index="value" :width="380" align="center">
              <template slot-scope="text,record">
                <span >{{ record.name ? text: '' }}</span>
              </template>
            </a-table-column>
          </a-table-column-group>
          <a-table-column-group>
            <span slot="title" style="text-align:center;">经费支出预算(单位:万元)</span>
            <a-table-column title="科目" align="center" data-index="nameOut" :width="380">
              <template slot-scope="text">
                <span style="text-align:left;float:left;">{{ text }}</span>
              </template>
            </a-table-column>
            <a-table-column title="预算数" align="center" data-index="valueOut" :width="380" />
          </a-table-column-group>
        </a-table>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import moment from 'moment'
export default {
  name: 'CostBudgetTab',
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
    },
    isFilterData: {
      type: Boolean,
      default: false
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
      month: undefined
    }
  },
  watch: {
    // projectData (item) {
    //   // this.$nextTick(() => {
    //   this.clearDataAndLoad()
    //   // })
    // }
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
      if (!this.projectId) { return }
      // this.$nextTick(() => {
      this.spin = true
      this.$http.get('/budget/queryBudgetList', { params: { projectId: this.projectId, month: this.month } })
        .then(res => {
          var arr = []
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
          this.$emit('getTablDatas', this.tableDatas)
        }).then(res => {
          this.spin = false
        })
      // })
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
              if (this.isFilterData) {
                const remoseItem = [400, 500, 600]
                if (remoseItem.includes(d['parentKey'] * 1)) {
                  continue
                }
              }
              if (t !== d.type) {
                t = d.type
                dIndex = 0
              }
              if (d['parentKey'] !== '0') {
                allKey[d['parentKey']] = allKey[d['parentKey']] ? allKey[d['parentKey']] + 1 : 1
              }
              if (t === 3) { // type 3： 是经费来源预算 4：经费支出预算
                dataArr[dIndex] = {
                  type: d['type'],
                  key: d['key'],
                  name: d['value'],
                  value: undefined,
                  nameOut: undefined,
                  valueOut: undefined,
                  parentKey: d['parentKey'],
                  parentKeyOut: undefined }
              } else {
                if (dataArr[dIndex]) {
                  dataArr[dIndex]['typeOut'] = d['type']
                  dataArr[dIndex]['keyOut'] = d['key']
                  dataArr[dIndex]['nameOut'] = d['value']
                  dataArr[dIndex]['valueOut'] = undefined
                  dataArr[dIndex]['parentKeyOut'] = d['parentKey']
                } else {
                  dataArr[dIndex] = { typeOut: d['type'], keyOut: d['key'], nameOut: d['value'], valueOut: undefined, parentKeyOut: d['parentKey'] }
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
            // this.clearDataAndLoad()
          })
      })
    }
  }
}
</script>

<style>
</style>
