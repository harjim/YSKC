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
        <a-button
          :disabled="tableData.length <= 0"
          style="margin-left:10px;"
          type="primary"
          @click="exported"
        >导出</a-button>
      </a-form>
      <div>
        <a-table
          ref="table"
          size="small"
          rowKey="voucherNum"
          :pagination="false"
          :dataSource="tableData"
          bordered
        >
          <a-table-column
            align="center"
            title="年"
            data-index="yearStr"
            key="yearStr"
            :width="20"
            class="word-wrap"
          >
            <template slot-scope="text,record">
              <span v-if="!record.sum">{{ text }}</span>
              <span v-else></span>
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="月"
            data-index="monthStr"
            key="monthStr"
            :width="20"
            class="word-wrap"
          >
            <template slot-scope="text,record">
              <span v-if="!record.sum">{{ text }}</span>
              <span v-else></span>
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="日"
            data-index="dayStr"
            key="dayStr"
            :width="20"
            class="word-wrap"
          >
            <template slot-scope="text,record">
              <span v-if="!record.sum">{{ text }}</span>
              <span v-else></span>
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="凭证号"
            data-index="voucherNum"
            key="voucherNum"
            :width="80"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="摘要"
            data-index="remark"
            key="remark"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="金额"
            data-index="expenseAmount"
            key="expenseAmount"
            :width="100"
            class="word-wrap"
          ></a-table-column>
        </a-table>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
export default {
  mixins: [yearMiXin],
  name: 'EquipmentSheet',
  components: {
    STable,
    Ellipsis
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
            this.tableData.forEach((item, index) => {
              if (index === this.tableData.length - 1) {
                return
              }
              item.remark = this.typeName
            })
          } else {
            this.tableData = []
          }
          return res.data
        }).finally(res => {
          this.spin = false
        })
    },
    exported () {
      const exportData = JSON.parse(JSON.stringify(this.tableData))
      const fileName = `${exportData[0].rdNum}-${this.queryParam.workMonth.format('YYYYMMDD')}-${this.typeName}`
      exportData[exportData.length - 1].id = null
      this.$exportJsonData(fileName,
        ['年', '月', '日', '凭证号', '摘要', '金额'],
        ['yearStr', 'monthStr', 'dayStr', 'voucherNum', 'remark', 'expenseAmount'],
        exportData,
        [5, 5, 5, 20, 20, 12])
    }
  }
}
</script>
<style>
.word-wrap {
  word-break: break-all;
}
</style>
