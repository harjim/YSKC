<template>
  <a-spin tip="请稍后..." :spinning="spin">
    <vxe-grid
      v-if="control.view"
      border
      resizable
      :toolbar="{slots: {buttons: 'toolbar_buttons'}}"
      :data="tableData">
      <template v-slot:toolbar_buttons>
        <template v-if="control.save">
          <a-button type="primary" @click="save()" style="margin-right:10px;" :disabled="!modifyLen">保存</a-button>
          <a-button type="primary" @click="search()" style="margin-right:10px;" :disabled="!modifyLen">取消</a-button>
        </template>
        <a-button type="primary" @click="importData()" v-if="control.import" style="margin-right:10px;">导入</a-button>
        <a-button type="primary" @click="exportData()" v-if="control.export" style="margin-right:10px;">导出</a-button>
      </template>
      <vxe-table-column
        field="rdTitle"
        title="RD"
        width="120"
        fixed="left"
        headerAlign="center"
      />
      <vxe-table-column
        field="pname"
        title="项目名称"
        width="260"
        fixed="left"
        headerAlign="center"
      />
      <template v-for="num in 12">
        <vxe-table-column
          :key="num"
          :field="`${num}`"
          :title="`${currentYear}-${num < 10 ? '0'+num : num}`"
          width="110"
          headerAlign="center"
          align="right"
        >
          <template v-slot="{row}">
            <template v-if="control.save && canModify(row,num)">
              <a-input-number
                :value="row.fundMap[num] ? row.fundMap[num].rdFunds : undefined"
                @change="v=>handleChange(v,row,num)"
                :max="$store.state.totalMax"
                :min="-$store.state.totalMax"
                :precision="2"
                :style="{borderColor: modifyMap[`${row.projectId}_${num}`] ? 'rgb(24,144,255)': 'rgb(200,212,221)'}"
                size="small"
                title="委外费用"
                style="width:96px;"/>
            </template>
            <template v-else>{{ row.fundMap[num] ? row.fundMap[num].rdFunds : '-' }}</template>
          </template>
        </vxe-table-column>
      </template>
      <vxe-table-column
        field="total"
        title="合计"
        width="140"
        headerAlign="center"
        align="right"
      />
    </vxe-grid>
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="paramData"
      paramKey="name"
      :title="`导入${Number(type) === 0 ?'国内' : '国外'}委托费用`"
      ref="uploadModal"
      action="/doc/projectOutsourcing/importOutsourcing"
      :templateName="`${Number(type) === 0 ?'国内' : '国外'}委托费用模板`"
      @onSuccess="success"
    />
  </a-spin>
</template>

<script>

import UploadModal from '@/components/UploadModal'
import yearMixin from '@/utils/yearMixin'
import moment from 'moment'
export default {
  components: {
    UploadModal
  },
  mixins: [yearMixin],
  props: {
    type: {
      type: [String, Number],
      default: 0
    }
  },
  data () {
    return {
      spin: false,
      tableData: [],
      modifyMap: {},
      modifyLen: 0,
      paramData: {},
      control: {
        view: this.$auth('project:outsourcing:view'),
        save: this.$auth('project:outsourcing:save'),
        import: this.$auth('project:outsourcing:import'),
        export: this.$auth('project:outsourcing:export')
      },
      tableField: {
        tableId: 'OutSourcingTable',
        fieldTitleObject: {
          rdTitle: { title: 'RD', required: true, defaultTitle: 'RD', importField: true },
          pname: { title: '项目名称', defaultTitle: '项目名称', importField: true },
          month: { title: '月份', required: true, defaultTitle: '月份', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-01' },
          rdFunds: { title: '分配金额', required: true, defaultTitle: '分配金额', importField: true }
        },
        hasMonth: true
      },
      sampleData: [
        {
          rdTitle: '2019RD01',
          pname: 'xxxx研发项目',
          month: '格式："年-月-日"，例如：2019-10-01',
          rdFunds: '数字格式，例如：300000'
        }
      ]
    }
  },
  computed: {
  },
  created () {
    this.search()
  },
  methods: {
    success () {
      this.$message.success('导入成功')
      this.search()
    },
    canModify (row, num) {
      if (row.fundMap[num]) {
        return true
      }
      const begin = moment(row.beginDate)
      if (begin.year() === this.currentYear) {
        if (num < begin.month() + 1) {
          return false
        }
      }
      const end = moment(row.endDate)
      if (end.year() === this.currentYear) {
        if (num > end.month() + 1) {
          return false
        }
      }
      return true
    },
    handleChange (v, row, num) {
      if (isNaN(v) || !v) {
        v = 0
      }
      if (!row.fundMap[num]) {
        this.$set(row.fundMap, num, { projectId: row.projectId, month: moment(`${this.currentYear}-${num < 10 ? '0' + num : num}-01`) })
      }
      this.$set(row.fundMap[num], 'rdFunds', v)
      this.modifyMap[`${row.projectId}_${num}`] = row.fundMap[num]
      this.modifyLen = Object.keys(this.modifyMap).length
    },
    moment,
    search () {
      this.modifyMap = {}
      this.modifyLen = 0
      this.loadData()
    },
    save () {
      const list = Object.values(this.modifyMap)
      if (!list.length) {
        this.$message.warning('未获取到修改的费用。')
        return
      }
      list.forEach(item => {
        if (item.id) {
          return
        }
        item.month = item.month.format('YYYY-MM-DD HH:mm:ss')
      })
      this.spin = true
      this.$http.post('/projectOutsourcing/save', { list, type: this.type, year: this.currentYear }).then(res => {
        if (res.success && res.data) {
          this.search()
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          this.spin = false
        }
      })
    },
    importData () {
      this.paramData.type = this.type
      this.paramData.year = this.currentYear
      this.$refs.uploadModal.show(this.tableField)
    },
    exportData () {
      this.spin = true
      this.$exportData('/projectOutsourcing/exportOutsourcing', { type: this.type, year: this.currentYear }, undefined, this.$message).then(res => {
        this.spin = false
      })
    },
    loadData () {
      this.spin = true
      this.$http.get('/projectOutsourcing/getOutsourcingList', { params: { type: this.type, year: this.currentYear } }).then(res => {
        let tempData
        if (res.success) {
          if (res.data) {
            tempData = res.data
          } else {
            tempData = []
          }
        } else {
          tempData = []
          this.$message.error(res.errorMessage ? res.errorMessage : '获取委外费用失败')
        }
        this.tableData = tempData
      }).finally(res => {
        this.spin = false
      })
    }
  }
}
</script>

<style>

</style>
