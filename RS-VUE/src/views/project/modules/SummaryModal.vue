<template>
  <a-modal :width="1200" v-model="visible" :footer="null" @cancel="closeModal">
    <a-card :bordered="false" :title="title">
      <div>
        <a-table
          ref="table"
          size="middle"
          rowKey="key"
          bordered
          :columns="columns"
          :pagination="false"
          :dataSource="initializeData"
          :alert="options.alert"
        ></a-table>
      </div>
    </a-card>
    <div style="padding-top: 10px;padding-left: 92%">
      <a-button @click="visible = false">关闭</a-button>
    </div>
  </a-modal>
</template>
<script>
const getData = data => {
  if (data.length <= 0) {
    return []
  }
  var row = { salary: 0, prod: 0, lab: 0, material: 0, design: 0, stimulus: 0, fuel: 0, inspection: 0, repair: 0, total: 0, house: 0, injury: 0, endowment: 0, maternity: 0, medical: 0, unemployment: 0 }
  for (let i = 0; i < data.length; i++) {
    row.salary += parseFloat(data[i].salary)
    row.endowment += parseFloat(data[i].endowment)
    row.house += parseFloat(data[i].house)
    row.injury += parseFloat(data[i].injury)
    row.maternity += parseFloat(data[i].maternity)
    row.prod += parseFloat(data[i].prod)
    row.lab += parseFloat(data[i].lab)
    row.medical += parseFloat(data[i].medical)
    row.unemployment += parseFloat(data[i].unemployment)
    row.material += parseFloat(data[i].material)
    row.design += parseFloat(data[i].design)
    row.stimulus += parseFloat(data[i].stimulus)
    row.fuel += parseFloat(data[i].fuel)
    row.inspection += parseFloat(data[i].inspection)
    row.repair += parseFloat(data[i].repair)
    row.total = parseFloat(data[i].total)
  }
  row = {
    salary: round2Bit(row.salary),
    endowment: round2Bit(row.endowment),
    house: round2Bit(row.house),
    injury: round2Bit(row.injury),
    maternity: round2Bit(row.maternity),
    unemployment: round2Bit(row.unemployment),
    medical: round2Bit(row.medical),
    prod: round2Bit(row.prod),
    lab: round2Bit(row.lab),
    material: round2Bit(row.material),
    design: round2Bit(row.design),
    stimulus: round2Bit(row.stimulus),
    fuel: round2Bit(row.fuel),
    inspection: round2Bit(row.inspection),
    repair: round2Bit(row.repair),
    total: round2Bit(row.total)
  }
  row.month = '汇总'
  row.beginToEnd = ''
  data.push(row)
  return data
}

const round2Bit = (value) => {
  return Math.round(value * 100) / 100
}
const renderTotal = (text, record, index) => {
  return round2Bit(parseFloat(record.salary) + parseFloat(record.endowment) + parseFloat(record.medical) + parseFloat(record.injury) +
    parseFloat(record.unemployment) + parseFloat(record.maternity) + parseFloat(record.house) +
    parseFloat(record.prod) + parseFloat(record.lab) + parseFloat(record.material) + parseFloat(record.design) + parseFloat(record.stimulus) +
    parseFloat(record.fuel) + parseFloat(record.inspection) + parseFloat(record.repair))
}
export default {
  name: 'SummaryModal',
  data () {
    return {
      form: this.$form.createForm(this),
      columns: [],
      title: '',
      options: {},
      visible: false,
      projectId: '',
      initializeData: []
    }
  },
  mounted () {
    this.columns = [{
      dataIndex: 'month',
      key: 'month',
      title: '月份',
      align: 'center'
    }, {
      title: '人员费用',
      align: 'center',
      children: [{
        dataIndex: 'salary',
        key: 'salary',
        title: '应发工资',
        align: 'center'
      }, {
        dataIndex: 'endowment',
        key: 'endowment',
        title: '养老金',
        align: 'center'
      }, {
        dataIndex: 'medical',
        key: 'medical',
        title: '医疗',
        align: 'center'
      }, {
        dataIndex: 'injury',
        key: 'injury',
        title: '工伤',
        align: 'center'
      }, {
        dataIndex: 'unemployment',
        key: 'unemployment',
        title: '失业',
        align: 'center'
      }, {
        dataIndex: 'maternity',
        key: 'maternity',
        title: '生育',
        align: 'center'
      }, {
        dataIndex: 'house',
        key: 'house',
        title: '公积金',
        align: 'center'
      }]
    }, {
      title: '折旧',
      align: 'center',
      children: [{
        dataIndex: 'prod',
        key: 'prod',
        title: '设备',
        align: 'center'
      }, {
        dataIndex: 'lab',
        key: 'lab',
        title: '仪器',
        align: 'center'
      }]
    }, {
      dataIndex: 'material',
      key: 'material',
      title: '物料费',
      align: 'center'
    }, {
      dataIndex: 'design',
      key: 'design',
      title: '设计费',
      align: 'center'
    }, {
      title: '能源',
      align: 'center',
      children: [{
        dataIndex: 'stimulus',
        key: 'stimulus',
        title: '动力',
        align: 'center'
      }, {
        dataIndex: 'fuel',
        key: 'fuel',
        title: '燃料',
        align: 'center'
      }]
    }, {
      title: '检测修理',
      align: 'center',
      children: [{
        dataIndex: 'inspection',
        key: 'inspection',
        title: '检测',
        align: 'center'
      }, {
        dataIndex: 'repair',
        key: 'repair',
        title: '修理',
        align: 'center'
      }]
    }, {
      dataIndex: 'total',
      key: 'total',
      title: '合计',
      align: 'center',
      customRender: renderTotal
    }]
  },
  methods: {
    showModal (record) {
      this.projectId = record.id
      this.title = `费用汇总[${record.pname}] [RD${record.rdIndex}]`
      this.visible = true
      this.initData()
    },
    initData () {
      return this.$http.get('/aggregation/getSummary', { params: Object.assign({ projectId: this.projectId }) })
        .then(res => {
          this.initializeData = getData(res.data)
          return this.initializeData
        })
    },
    closeModal () {
      this.visible = false
      this.initializeData = []
    }

  }
}
</script>
<style scoped>
</style>
