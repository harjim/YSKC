<template>
  <div>
    <a-form @submit="handleSubmit" :form="form">
      <a-table :columns="columns" :dataSource="data" bordered :pagination="false" size="small">
        <template slot="action" slot-scope="text,record">
          <a-input
            :value="formData[record.key]"
            @change="(e)=>onCellChange(e.target.value,record)"
          />
        </template>
      </a-table>
    </a-form>
    <div style="width:100%;height:80px;border:1px solid #CCCCCC;border-top:none">
      <span>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;重点从提高生产效率、降低运营成本、降低产品不良品率、提高能源利用率五个方面概述项目实施后实现的主要经济指标、
        技术指标提升等情况；新产品开发情况或质量提升情况，含产能提升情况；人工节约情况；节能减排情况；预计实现年销售收入，利润，年纳税额等概述项目实施效果。
      </span>
    </div>
    <br />
    <div class="table-operator" style="text-align: center">
      <a-button :disabled="noModify" type="primary" @click="handleSubmit">提交</a-button>
    </div>
  </div>
</template>
<script>
const indexArray = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17]
const defaultData = {
  businessIncome: '',
  profit: '',
  taxRevenue: '',
  expect: '',
  drivenFundsInput: '',
  lineOfInfo: '',
  manpowerSavings: '',
  goodOfRate: '',
  consumptionPer: '',
  carbonDioxide: '',
  unitWaterUse: '',
  casualti: '',
  weedOut: '',
  digitalize: '',
  numericalControl: '',
  talentTeam: ''
}
export default {
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    year: {
      type: Number,
      default: 0
    }
  },
  data () {
    const columns = [{
      title: '评价内容',
      dataIndex: 'name',
      width: 130,
      customRender: (value, row, index) => {
        const obj = {
          children: value,
          attrs: {}
        }
        if (index === 0) {
          obj.attrs.rowSpan = 5
        }
        if (index === 5) {
          obj.attrs.rowSpan = 2
        }
        if (index === 8) {
          obj.attrs.rowSpan = 3
        }
        if (index === 12) {
          obj.attrs.rowSpan = 3
        }
        if (indexArray[index] === 1 || indexArray[index] === 2 || indexArray[index] === 3 || indexArray[index] === 4 || indexArray[index] === 6 || indexArray[index] === 9 || indexArray[index] === 10 || indexArray[index] === 13 || indexArray[index] === 14) {
          obj.attrs.rowSpan = 0
        }
        return obj
      }
    }, {
      title: '具体指标',
      dataIndex: 'age',
      width: 350
    }, {
      title: '实施效果',
      dataIndex: 'tel',
      scopedSlots: { customRender: 'action' }
    }]
    return {
      form: this.$form.createForm(this),
      data: [{
        key: 'businessIncome',
        name: '经济效益',
        age: `${this.year}年新增营业收入(万元)`,
        businessIncome: ''
      }, {
        key: 'profit',
        age: `${this.year}年新增利润(万元)`,
        profit: ''

      }, {
        key: 'taxRevenue',
        age: `${this.year}年新增税收(万元)`,
        taxRevenue: ''

      }, {
        key: 'expect',
        age: '预计可实现年销售收入 利润、年纳税额等',
        expect: ''

      }, {
        key: 'drivenFundsInput',
        age: '项目执行期内带动的资金投入',
        drivenFundsInput: ''
      },
      {
        key: 'lineOfInfo',
        name: '效率产能提升',
        age: '生产线产能提升情况',
        lineOfInfo: ''
      },
      {
        key: 'manpowerSavings',
        age: '人工节约情况，人均产量提升情况',
        manpowerSavings: ''
      },
      {
        key: 'goodOfRate',
        name: '质量可靠性',
        age: '良品提升情况',
        goodOfRate: ''
      },
      {
        key: 'consumptionPer',
        name: '生态效益',
        age: '单位产品能耗',
        consumptionPer: ''
      },
      {
        key: 'carbonDioxide',
        age: '单位产品二氧化碳排放量',
        carbonDioxide: ''
      },
      {
        key: 'unitWaterUse',
        age: '单位产品用水量',
        unitWaterUse: ''
      },
      {
        key: 'casualti',
        name: '安全',
        age: '设备保障率和事故伤亡发生情况',
        casualti: ''
      },
      {
        key: 'weedOut',
        name: '设备',
        age: '淘汰落后设备工艺情况',
        weedOut: ''

      }, {
        key: 'digitalize',
        age: '数字化研发设计工具普及率',
        digitalize: ''
      },
      {
        key: 'numericalControl',
        age: '关键工序数控化率',
        numericalControl: ''
      }, {
        key: 'talentTeam',
        name: '人才培养',
        age: '人才队伍建设情况',
        talentTeam: ''
      }],
      columns,
      id: 0,
      noModify: true,
      formData: {}
    }
  },
  watch: {
    projectId () {
      this.initialization()
    }
  },
  created () {
    this.initialization()
  },
  methods: {
    onCellChange (val, record, fld) {
      this.noModify = false
      this.formData[record.key] = val
    },
    initialization () {
      this.noModify = true
      this.id = 0
      this.formData = { ...defaultData }
      this.$nextTick(() => {
        this.$http.get('/projectImplement/queryImplement', { params: { projectId: this.projectId } })
          .then(res => {
            const result = res.data
            if (result !== null) {
              this.formData = result
              this.id = result.id
            }
            self.loading = false
            return result
          })
      })
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
          this.$http.post('/projectImplement/addProjectImplement', values)
            .then(res => {
              if (res.success && res.data) {
                this.noModify = true
                this.$emit('ok', true)
                this.id = res.data
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
    }
  }
}
</script>
