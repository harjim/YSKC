<template>
  <a-form v-if="$auth('project:report:outSource:view')">
    <a-card :bordered="false">
      <a-spin tip="请稍后..." :spinning="spin">
        <a-table
          bordered
          :pagination="false"
          :dataSource="tableData"
          size="small"
          rowKey="name"
          :scroll="scroll"
        >
          <a-table-column
            align="left"
            title="费用类型"
            data-index="name"
            key="name"
            :width="120"
            :fixed="fixed"
          />
          <a-table-column
            v-for="n in month"
            :key="n"
            :title="n"
            :data-index="n"
            :width="130"
            align="center"
          >
            <template slot-scope="text,record">
              <span>
                <a-input-number
                  :value="record[n]"
                  :min="1"
                  :precision="2"
                  :key="new Date().toString()"
                  @change="(val)=>onCellChange(val,record,n)"
                />
              </span>
            </template>
          </a-table-column>
        </a-table>
        <br />
        <span>
          <a-button
            v-if="$auth('project:report:outSource:save')"
            type="primary"
            :disabled="noModify"
            @click="handleSubmit()"
          >保存</a-button>
        </span>
      </a-spin>
    </a-card>
  </a-form>
</template>

<script>
import moment from 'moment'
const typeArr = {
  '1': { 'name': '研发工资', 'types': [10000] },
  '2': { 'name': '五险一金', 'types': [10100] },
  '3': { 'name': '设备折旧', 'types': [30000] },
  '4': { 'name': '研发材料', 'types': [20000] },
  '5': { 'name': '中间试制', 'types': [20301] },
  '6': { 'name': '修理材料', 'types': [20601] },
  '7': { 'name': '动力损耗', 'types': [20100] },
  '8': { 'name': '燃料损耗', 'types': [20200] },
  '9': { 'name': '设计费用', 'types': [50000] },
  '10': { 'name': '检测费用', 'types': [20500] },
  '11': { 'name': '修理费用', 'types': [20600] },
  '12': { 'name': '软件摊销费用', 'types': [40000] },
  '13': { 'name': '专利摊销费用', 'types': [40100] },
  '14': { 'name': '其他摊销费用', 'types': [40200] },
  '15': { 'name': '差旅费用', 'types': [60400] },
  '16': { 'name': '其他费用', 'types': [69900] },
  '17': { 'name': '其他试制', 'types': [20300] }
}
const defaultData = [{
  num: '1',
  name: `研发工资`,
  types: '10000',
  dataFiled: 'salary'

}, {
  num: '2',
  name: `五险一金`,
  types: '10101',
  dataFiled: 'housing '
}, {
  num: '3',
  name: `设备折旧`,
  types: '30000',
  dataFiled: 'equipment '
}, {
  num: '4',
  name: `研发材料`,
  types: '20000',
  dataFiled: 'materials '
}, {
  num: '5',
  name: `中间试制`,
  types: '20301',
  dataFiled: 'production '

}, {
  num: '6',
  name: `修理材料`,
  types: '20601',
  dataFiled: 'repairMaterial  '
},
{
  num: '7',
  name: `动力损耗`,
  types: '20100',
  dataFiled: 'powerloss '
},
{
  num: '8',
  name: `燃料损耗`,
  types: '20200',
  dataFiled: 'fuelConsumption '

},
{
  num: '9',
  name: `设计费用`,
  types: '20200',
  dataFiled: 'design '
},
{
  num: '10',
  name: `检测费用`,
  types: '20500',
  dataFiled: 'detection '

},
{
  num: '11',
  name: `修理费用`,
  types: '20600',
  dataFiled: 'repair '
},
{
  num: '12',
  name: `软件摊销费用`,
  types: '40000',
  dataFiled: 'softwareAmortization '

}, {
  num: '13',
  name: `专利摊销费用`,
  types: '40100',
  dataFiled: 'patentAmortization '
}, {
  num: '14',
  name: `其他摊销费用`,
  types: '40200',
  dataFiled: 'otherAmortization '
}, {
  num: '15',
  name: `差旅费用`,
  types: '60400',
  dataFiled: 'travel '
}, {
  num: '16',
  name: `其他费用`,
  types: '69900',
  dataFiled: 'other '
}, {
  num: '17',
  name: `其他试制`,
  types: '20300',
  dataFiled: 'otherManufacture '
}]
export default {
  name: 'OutsourceModal',
  components: {
  },
  props: {
    projectData: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      fixed: true,
      scroll: {},
      spin: false,
      visible: false,
      beginDate: undefined,
      noModify: true,
      endDate: undefined,
      title: undefined,
      typeArr: typeArr,
      projectId: undefined,
      month: [],
      tableData: []

    }
  },
  watch: {
    projectData (item) {
      this.test = true
      this.beginDate = item.beginDate
      this.endDate = item.endDate
      this.projectId = item.id
      this.month = this.months()
      this.loadData()
      this.$nextTick(() => {
        this.setScrollValue()
      })
    }
  },
  created () {
    this.lated()
    this.setScrollValue()
    this.loadData()
  },
  methods: {
    lated () {
      this.title = '设置数据'
      this.confirmLoading = false
      this.visible = true
      this.beginDate = this.projectData.beginDate
      this.endDate = this.projectData.endDate
      this.projectId = this.projectData.id
      this.month = this.months()
    },
    months () {
      var endDate = this.endDate
      var beginDate = this.beginDate
      if (typeof endDate === 'undefined' || typeof beginDate === 'undefined') {
        return []
      }
      var beginArray = beginDate.split('-').map(Number)
      var endArray = endDate.split('-').map(Number)
      var returnArry = []
      var begin = parseInt(beginArray[0]) * 12 + parseInt(beginArray[1])
      var end = parseInt(endArray[0]) * 12 + parseInt(endArray[1])
      var totalMonth = Math.abs(end - begin)
      for (var i = 0; i <= totalMonth; i++) {
        var month = (parseInt(beginArray[1]) + i)
        if (month <= 12) {
          returnArry.push(beginArray[0] + '-' + (month + '').padStart(2, '0'))
        } else {
          returnArry.push(endArray[0] + '-' + (month - 12 + '').padStart(2, '0'))
        }
      }
      return returnArry
    },
    loadData () {
      this.noModify = true
      this.spin = true
      this.$http.get('/project/querySummaryByProjectId', { params: { projectId: this.projectId } })
        .then(res => {
          if (res.success && res.data) {
            var arr = {}
            res.data.forEach(item => {
              if (!arr[item.month]) {
                arr[item.month] = []
              }
              arr[item.month].push(item)
            })
          }
          var copyData = this.$deepClone(defaultData)
          this.month.forEach(m => {
            if (arr[m]) {
              arr[m].forEach(item => {
                copyData.forEach(d => {
                  if (item.type === Number(d.types)) {
                    d[m] = item.value
                  }
                })
              })
            }
          })
          this.tableData = copyData
          this.spin = false
        })
    },
    onCellChange (value, record, name) {
      this.noModify = false
      this.$set(record, name, value)
    },
    moment,
    handleSubmit () {
      this.spin = true
      var value = []
      this.month.forEach(item => {
        // this.tableData = this.$deepClone(defaultData)
        for (var i = 0; i < this.tableData.length; i++) {
          const row = this.tableData[i]
          if (row[item] !== undefined) {
            var child = {}
            child.value = row[item]
            child.month = item + '-' + '01 00:00:00'
            child.projectId = this.projectId
            child.type = row.types
            value.push(child)
          }
        }
      })
      this.$http.post('/project/insertData', value)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('更新成功')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
          }
        })
        .finally(res => {
          this.spin = false
          this.confirmLoading = false
        })
    },
    setScrollValue () {
      const x = 120 + this.month.length * 130
      if (x <= 1000) {
        this.scroll = {}
        this.fixed = false
      } else {
        this.scroll = { x: x }
        this.fixed = true
      }
    }
  }
}
</script>
