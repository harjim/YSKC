<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="月份">
          <a-month-picker
            format="YYYY-MM"
            v-model="queryParam.queryDate"
            :defaultPickerValue="queryParam.defaultPickerValue"
            placeholder="请选择月份"
            @change="dateChange"
          />
        </a-form-item>
      </a-form>
      <br />
      <div>
        <a-table
          ref="table"
          size="small"
          rowKey="sheetNum"
          :pagination="false"
          :dataSource="datas"
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
          >
            <template slot-scope="text">
              <span>{{ text | MoneyFormat }}</span>
            </template>
          </a-table-column>
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
              <template v-if="datas.length - 1 !== index ">
                <a @click="view(record)">查看</a>
              </template>
              <template v-else>{{ text }}</template>
            </template>
          </a-table-column>
        </a-table>
      </div>
    </a-spin>
    <attendance-sheet-modal ref="attendanceSheetModal"></attendance-sheet-modal>
  </a-card>
</template>
<script>
import { STable, Ellipsis } from '@/components'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import AttendanceSheetModal from './modules/AttendanceSheetModal'
export default {
  mixins: [yearMiXin],
  name: 'AttendanceSheet',
  components: {
    STable,
    Ellipsis,
    AttendanceSheetModal
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    month: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      wrapClassName: 'wrapClassName',
      drawerVisible: false,
      datas: [],
      spinning: false,
      data: [],
      mdl: {},
      optionWidth: 150,
      title: '',
      queryParam: { queryDate: moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]), defaultPickerValue: moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]) },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      visible: false,
      form: this.$form.createForm(this)
    }
  },
  created () {
    this.$set(this.queryParam, 'defaultPickerValue', moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]))
    this.search()
  },
  watch: {
    projectId (newId) {
      this.search()
    }
  },
  methods: {
    view (record) {
      this.$refs.attendanceSheetModal.showModal(record, this.projectId)
    },
    dateChange () {
      this.search()
    },
    moment,
    search () {
      if (this.projectId === 0) {
        return
      }
      if (moment(this.queryParam.queryDate).get('year') !== this.$store.state.currentYear) {
        this.$set(this.queryParam, 'queryDate', moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]))
        this.$set(this.queryParam, 'defaultPickerValue', moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]))
      }
      this.spinning = true
      this.$http.get('/workSheet/getMonthWorkSheetList', { params: { month: this.queryParam.queryDate, projectId: this.projectId, year: this.currentYear } })
        .then(res => {
          if (res.success && res.data) {
            this.datas = res.data
          } else {
            this.datas = []
          }
          return res
        }).finally(res => {
          this.spinning = false
        })
    }
  }
}
</script>

<style  lang="css" >
.word-wrap {
  word-break: break-all;
}
</style>
