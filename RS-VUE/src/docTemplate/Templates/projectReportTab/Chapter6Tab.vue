<template>
  <!-- <capital-budget :projectData="project" :isFilter="true" :isEdit="false"></capital-budget> -->
  <budget
    :projectData="project"
    style="height: 136%;"
    ref="budget"
    :isFilter="true"
    :isEdit="false"
    :isShowTitle="false"
    :isTrust="isTrust"></budget>
</template>

<script>
import Budget from '@/components/CapitalBudget/Budget.vue'
export default {
  name: 'CapitalbudgetTab',
  components: {
    Budget
  },
  props: {
    content: {
      type: Object,
      required: true
    },
    project: {
      type: Object,
      required: true
    },
    isTrust: { // 是否委托项目
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
      yearArr: [],
      type3: [],
      type4: [],
      types: [],
      visible: true
    }
  },
  watch: {
    project (item) {
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
      this.projectId = this.project.id
      // this.$nextTick(() => {
      //   this.spin = true
      //   this.$http.get('/budget/queryProjectBudget', { params: { projectId: this.projectId } })
      //     .then(res => {
      //       this.yearArr = []
      //       if (res.data && res.data.length) {
      //         this.yearArr = res.data
      //         this.yearArr.forEach(item => {
      //           this.tableDatas.forEach((r, index) => {
      //             r[`r${item.year}`] = item[`k${r.keyOut}`]
      //             r[`valueOut`] = item[`k${r.keyOut}`]
      //             if (r.key) {
      //               r[`value`] = item[`k${r.key}`]
      //             }
      //           })
      //         })
      //       }
      //       this.content.chapter6List = this.tableDatas
      //     }).then(res => {
      //       this.spin = false
      //     })
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
              const remoseItem = [400, 500, 600]
              if (remoseItem.includes(d['parentKey'] * 1)) {
                continue
              }
              if (t !== d.type) {
                t = d.type
                dIndex = 0
              }
              if (d['parentKey'] !== '0') {
                allKey[d['parentKey']] = allKey[d['parentKey']] ? allKey[d['parentKey']] + 1 : 1
              }
              if (t === 3) {
                dataArr[dIndex] = { type: d['type'], key: d['key'], name: d['value'], value: undefined, nameOut: undefined, valueOut: undefined, parentKey: d['parentKey'], parentKeyOut: undefined }
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
            this.clearDataAndLoad()
          })
      })
    },
    onCellChange (value, record, valueType) {
      this.noModify = false
      if (!value || !Number(value)) {
        value = 0
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
