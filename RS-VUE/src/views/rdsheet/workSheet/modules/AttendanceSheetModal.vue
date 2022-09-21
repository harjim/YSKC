<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="1200"
    :maskClosable="false"
    :visible="visible"
    @cancel="cancel"
  >
    <template slot="footer">
      <a-button type="primary" @click="exportData" v-if="$auth('rdsheet:sheet:export')">导出</a-button>
      <a-button type="primary" @click="handleOk">关闭</a-button>
    </template>
    <a-spin tip="请稍后..." :spinning="spinning">
      <div>
        <a-table
          ref="table"
          size="small"
          rowKey="num"
          :pagination="false"
          :dataSource="datas"
          :scroll="{x:1024, y: 620}"
          bordered
        >
          <a-table-column
            align="center"
            title="序号"
            data-index="num"
            key="num"
            :width="50"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="成本要素"
            data-index="costElement"
            key="costElement"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="参考凭证编号（工单号）"
            data-index="sheetNum"
            key="sheetNum"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="期间"
            data-index="period"
            key="period"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="工作事件"
            data-index="workEvent"
            key="workEvent"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="费用支出金额"
            data-index="cost"
            key="cost"
            :width="100"
            class="word-wrap"
          >
            <template slot-scope="text">
              <span>{{ text | MoneyFormat }}</span>
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="CO对象名称/工件"
            data-index="coObj"
            key="coObj"
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
        </a-table>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
export default {
  mixins: [yearMiXin],
  name: 'AttendanceSheetModal',
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      sheetNum: undefined,
      disabled: true,
      datas: [],
      spinning: false,
      data: [],
      mdl: {},
      optionWidth: 150,
      title: '',
      queryParam: { queryDate: null, defaultPickerValue: null },
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
  },
  methods: {
    cancel () {
      this.visible = false
    },
    handleOk () {
      this.visible = false
    },
    showModal (record, projectId) {
      this.sheetNum = record.sheetNum
      this.title = `人员工单[${record.sheetNum}]-[${record.rdNum}]`
      this.projectId = projectId
      this.visible = true
      this.queryParam.queryDate = moment(record.workDate)
      this.workNo = record.workNo
      this.search()
    },
    dateChange () {
      this.search()
    },
    moment,
    exportData () {
      const exportData = this.$deepClone(this.datas)// JSON.parse(JSON.stringify(this.datas))
      const fileName = `${exportData[0].rdIndexStr}-${moment(this.queryParam.queryDate).format('YYYYMMDD')}-人员工单`
      exportData[exportData.length - 1].id = null
      this.$exportJsonData(fileName,
        ['序号', '成本要素', '参考凭证编号（工单号）', '期间', '工作事件', '费用支出金额', 'CO对象名称/工件', '项目编号'],
        ['num', 'costElement', 'sheetNum', 'period', 'workEvent', 'cost', 'coObj', 'rdNum'],
        exportData,
        [10, 20, 22, 10, 20, 12, 20, 10])
    },
    search () {
      this.spinning = true
      this.$http.get('/workSheet/getStaffWorkSheetList', { params: { workDate: this.queryParam.queryDate, projectId: this.projectId, year: this.currentYear } })
        .then(res => {
          this.datas = res.data
          if (this.datas && this.datas.length > 0) {
            this.datas.forEach((item) => {
              item.sheetNum = this.sheetNum
            })
            this.disabled = false
          } else {
            this.disabled = true
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
