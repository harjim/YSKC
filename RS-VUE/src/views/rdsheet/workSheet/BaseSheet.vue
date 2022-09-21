<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spin">
      <a-form layout="inline">
        <a-form-item label="月份">
          <a-month-picker
            format="YYYY-MM"
            v-model="queryParam.workMonth"
            :defaultPickerValue="queryParam.defaultPickerValue"
            placeholder="请选择月份"
            @change="search"
          />
        </a-form-item>
      </a-form>
      <div>
        <a-table
          ref="table"
          size="small"
          rowKey="sheetNum"
          :pagination="false"
          :dataSource="tableData"
          :scroll="{x:1024, y: 620}"
          bordered
        >
          <a-table-column
            align="center"
            title="工单号"
            data-index="sheetNum"
            key="sheetNum"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="期间"
            data-index="workDate"
            key="workDate"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="支出金额"
            data-index="expenseAmount"
            key="expenseAmount"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="项目编号"
            data-index="rdNum"
            key="rdNum"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="详情"
            data-index="rdIndexStr"
            key="id"
            :width="100"
            class="word-wrap"
          >
            <template slot-scope="text,record, index">
              <template v-if="tableData.length - 1 !== index ">
                <a @click="view(record)">查看</a>
              </template>
              <template v-else>{{ text }}</template>
            </template>
          </a-table-column>
        </a-table>
      </div>
    </a-spin>
    <base-sheet-modal ref="sheetModal" />
  </a-card>
</template>

<script>
import BaseSheetModal from './modules/BaseSheetModal'
import { STable, Ellipsis } from '@/components'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
export default {
  mixins: [yearMiXin],
  name: 'BaseSheet',
  components: {
    STable,
    Ellipsis,
    BaseSheetModal
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    typeName: {
      type: String,
      default: '工单'
    },
    dataUrl: {
      type: String,
      required: true
    },
    nodeUrl: {
      type: String,
      required: true
    },
    types: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      queryParam: {
        workMonth: moment([this.currentYear, 0, 1, 0, 0, 0, 0]), defaultPickerValue: moment([this.currentYear, 0, 1, 0, 0, 0, 0])
      },
      tableData: [],
      spin: false,
      form: this.$form.createForm(this)
    }
  },
  watch: {
    projectId (newId) {
      this.search()
    }
  },
  created () {
    this.$set(this.queryParam, 'defaultPickerValue', moment([this.currentYear, 0, 1, 0, 0, 0, 0]))
    this.search()
  },
  methods: {
    moment,
    search () {
      if (!this.projectId || this.projectId <= 0) {
        this.tableData = []
        return
      }
      if (moment(this.queryParam.workMonth).get('year') !== this.currentYear) {
        this.$set(this.queryParam, 'workMonth', moment([this.currentYear, 0, 1, 0, 0, 0, 0]))
        this.$set(this.queryParam, 'defaultPickerValue', moment([this.currentYear, 0, 1, 0, 0, 0, 0]))
      }
      if (!this.queryParam.workMonth) {
        this.tableData = []
        return
      }
      this.spin = true
      this.$http.get(this.dataUrl, { params: { month: this.queryParam.workMonth, projectId: this.projectId, types: this.types } })
        .then(res => {
          if (res.success && res.data) {
            this.tableData = res.data
          } else {
            this.tableData = []
          }
          return res.data
        }).finally(res => {
          this.spin = false
        })
    },
    view (record) {
      const param = { projectId: this.projectId, typeName: this.typeName, dataUrl: this.nodeUrl, types: this.types, workDate: moment(record.workDate) }
      this.$refs.sheetModal.showModal(param, record.sheetNum, record.rdNum)
    },
    exported () {
      const exportData = JSON.parse(JSON.stringify(this.tableData))
      const fileName = `${exportData[0].rdNum}-${this.queryParam.workMonth.format('YYYYMMDD')}-${this.typeName}`
      exportData[exportData.length - 1].id = null
      this.$exportJsonData(fileName,
        ['序号', '成本要素', '参考凭证编号（工单号）', '期间', '工作事件', '费用支出金额', 'CO对象名称/工件', '项目编号'],
        ['num', 'costElement', 'sheetNum', 'period', 'workEvent', 'cost', 'coObj', 'rdNum'],
        exportData,
        [10, 20, 22, 10, 20, 12, 20, 10])
    }
  }
}
</script>
<style>
.word-wrap {
  word-break: break-all;
}
</style>
