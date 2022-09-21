<template>
  <a-card>
    <a-form @submit="handleSubmit" :form="form">
      <a-table :columns="columns" :dataSource="data" bordered :pagination="false">
        <!-- <template slot="name" slot-scope="text">
          <a href="javascript:;">{{text}}</a>
        </template>-->
        <template slot="reportAction" slot-scope="text,record">
          <a-input-number
            v-if="record.key==='gd'"
            :min="0"
            :precision="2"
            :disabled="record.disabled"
            :value="formData['equipmentCostOfReport']+formData['installationCostOfReport']+formData['constructionCostOfReport']"
            @change="(val)=>onCellChange(parseInt(val,10),record,'Report')"
          />
          <a-input-number
            v-else-if="record.key==='qt'"
            :min="0"
            :precision="2"
            :disabled="record.disabled"
            :value="formData['softwareKitOfReport']+formData['inspectionOfReport']+formData['digitalIntegrationOfReport']"
            @change="(val)=>onCellChange(parseInt(val,10),record,'Report')"
          />
          <a-input-number
            v-else-if="record.key==='hj'"
            :min="0"
            :precision="2"
            :disabled="record.disabled"
            :value="formData['equipmentCostOfReport']+formData['installationCostOfReport']+formData['constructionCostOfReport']+formData['softwareKitOfReport']+formData['inspectionOfReport']+formData['digitalIntegrationOfReport']+formData['rdOutsourcingOfReport']"
            @change="(val)=>onCellChange(parseInt(val,10),record,'Report')"
          />
          <a-input-number
            v-else
            :min="0"
            :precision="2"
            :disabled="record.disabled"
            :value="formData[record.key+'OfReport']"
            @change="(val)=>onCellChange(val,record,'Report')"
          />
        </template>
        <template slot="paidAction" slot-scope="text,record">
          <a-input-number
            v-if="record.key==='gd'"
            :min="0"
            :precision="2"
            :disabled="record.disabled"
            :value="formData['equipmentCostOfPaid']+formData['installationCostOfPaid']+formData['constructionCostOfPaid']"
            @change="(val)=>onCellChange(parseInt(val,10),record,'Paid')"
          />
          <a-input-number
            v-else-if="record.key==='qt'"
            :min="0"
            :precision="2"
            :disabled="record.disabled"
            :value="formData['softwareKitOfPaid']+formData['inspectionOfPaid']+formData['digitalIntegrationOfPaid']"
            @change="(val)=>onCellChange(parseInt(val,10),record,'Paid')"
          />
          <a-input-number
            v-else-if="record.key==='hj'"
            :min="0"
            :precision="2"
            :disabled="record.disabled"
            :value="formData['equipmentCostOfPaid']+formData['installationCostOfPaid']+formData['constructionCostOfPaid']+formData['softwareKitOfPaid']+formData['inspectionOfPaid']+formData['digitalIntegrationOfPaid']+formData['rdOutsourcingOfPaid']"
            @change="(val)=>onCellChange(parseInt(val,10),record,'Paid')"
          />
          <a-input-number
            v-else
            :min="0"
            :precision="2"
            :disabled="record.disabled"
            :value="formData[record.key+'OfPaid']"
            @change="(val)=>onCellChange(val,record,'Paid')"
          />
        </template>
      </a-table>
    </a-form>
    <div class="table-operator">
      <a-button type="primary" @click="handleSubmit">提交</a-button>
    </div>
  </a-card>
</template>
<script>
const data = [{
  key: 'gd',
  index: '一',
  name: '固定资产投资（万元）',
  report: 'tetete',
  paid: '',
  disabled: true
}, {
  key: 'equipmentCost',
  index: '1-1',
  name: '设备购置费',
  report: '',
  paid: '',
  disabled: false
}, {
  key: 'installationCost',
  index: '1-2',
  name: '安装工程费',
  report: '',
  paid: '',
  disabled: false
}, {
  key: 'constructionCost',
  index: '1-3',
  name: '建筑工程费',
  report: '',
  paid: '',
  disabled: false
}, {
  key: 'qt',
  index: '二',
  name: '其他投入',
  report: '',
  paid: '',
  disabled: true
}, {
  key: 'softwareKit',
  index: '2-1',
  name: '配套软件',
  report: '',
  paid: '',
  disabled: false
}, {
  key: 'inspection',
  index: '2-2',
  name: '检测维护',
  report: '',
  paid: '',
  disabled: false
}, {
  key: 'digitalIntegration',
  index: '2-3',
  name: '数字化集成',
  report: '',
  paid: '',
  disabled: false
}, {
  key: 'rdOutsourcing',
  index: '2-4',
  name: '研发外包服务',
  report: '',
  paid: '',
  disabled: false
}, {
  key: 'hj',
  index: '金额合计',
  report: '',
  paid: '',
  disabled: true
}, {
  key: 'water',
  index: '其中：',
  name: '节水改造投入',
  report: '',
  paid: '',
  disabled: false
}
]
export default {
  name: 'Investment',
  props: {
    projectId: {
      type: Number,
      default: 0
    }
  },
  data () {
    const columns = [{
      title: '序号',
      dataIndex: 'index',
      customRender: (value, row, index) => {
        const obj = {
          children: value,
          attrs: {}
        }
        if (index === 9) {
          obj.attrs.colSpan = 2
        }
        return obj
      }
    }, {
      title: '名称',
      dataIndex: 'name',
      customRender: (value, row, index) => {
        const obj = {
          children: value,
          attrs: {}
        }
        if (index === 9) {
          obj.attrs.colSpan = 0
        }
        return obj
      }
    }, {
      title: '申报金额（万元）',
      dataIndex: 'report',
      scopedSlots: { customRender: 'reportAction' }
    }, {
      title: '已付金额（万元）',
      dataIndex: 'paid',
      scopedSlots: { customRender: 'paidAction' }
    }]
    return {
      form: this.$form.createForm(this),
      data,
      columns,
      id: 0,
      formData: {
        equipmentCostOfReport: 0,
        equipmentCostOfPaid: 0,
        installationCostOfReport: 0,
        installationCostOfPaid: 0,
        constructionCostOfReport: 0,
        constructionCostOfPaid: 0,
        softwareKitOfReport: 0,
        softwareKitOfPaid: 0,
        inspectionOfReport: 0,
        inspectionOfPaid: 0,
        digitalIntegrationOfReport: 0,
        digitalIntegrationOfPaid: 0,
        rdOutsourcingOfReport: 0,
        rdOutsourcingOfPaid: 0,
        waterOfReport: 0,
        waterOfPaid: 0
      }
    }
  },
  watch: {
    projectId (newId) {
      this.initialize()
    }
  },
  created () {
    this.initialize()
  },
  methods: {
    onCellChange (val, record, fld) {
      if (typeof val === 'string') {
        val = 0
      }
      if (Number.isNaN(val)) {
        val = 0
      }
      this.formData[record.key + 'Of' + fld] = val
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values = this.formData
          values.projectId = this.projectId
          values.id = this.id
          this.$http.post('/projectInvestMent/saveInvestMent', values)
            .then(res => {
              if (res.success && res.data) {
                this.id = res.data
                this.visible = false
                this.$emit('ok', true)
                this.$message.success('保存成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    initialize () {
      const self = this
      this.$nextTick(() => {
        this.$http.get('/projectInvestMent/queryAll', { params: { projectId: self.projectId } })
          .then(res => {
            self.formData = {
              equipmentCostOfReport: 0,
              equipmentCostOfPaid: 0,
              installationCostOfReport: 0,
              installationCostOfPaid: 0,
              constructionCostOfReport: 0,
              constructionCostOfPaid: 0,
              softwareKitOfReport: 0,
              softwareKitOfPaid: 0,
              inspectionOfReport: 0,
              inspectionOfPaid: 0,
              digitalIntegrationOfReport: 0,
              digitalIntegrationOfPaid: 0,
              rdOutsourcingOfReport: 0,
              rdOutsourcingOfPaid: 0,
              waterOfReport: 0,
              waterOfPaid: 0
            }
            const result = res.data
            if (result !== null) {
              self.formData = result
              this.id = result.id
            }
            self.loading = false
            return result
          })
      })
    }
  }
}
</script>
