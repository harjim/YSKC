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
      <a-button type="primary" @click="exported" v-if="$auth('rdsheet:sheet:export')">导出</a-button>
      <a-button type="primary" @click="cancel">关闭</a-button>
    </template>
    <a-spin tip="请稍后..." :spinning="spin">
      <div>
        <a-table
          ref="table"
          size="small"
          rowKey="num"
          :pagination="false"
          :dataSource="tableData"
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
            align="left"
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
            align="right"
            title="费用支出金额"
            data-index="cost"
            key="cost"
            :width="100"
            class="word-wrap"
          ></a-table-column>
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
  name: 'BaseSheetModal',
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      tableData: [],
      spin: false,
      form: this.$form.createForm(this),
      param: undefined,
      title: '',
      visible: false
    }
  },
  methods: {
    moment,
    cancel () {
      this.visible = false
    },
    showModal (param, sheetNum, rdNum) {
      this.param = param
      this.sheetNum = sheetNum
      this.title = `${param.typeName}[${sheetNum}]-[${rdNum}]`
      this.visible = true
      this.search()
    },
    search () {
      if (this.param) {
        this.spin = true
        this.$http.get(this.param.dataUrl, { params: this.param })
          .then(res => {
            if (res.success && res.data) {
              this.tableData = res.data
              this.tableData.forEach((item, index) => {
                if (this.tableData.length - 1 === index) {
                  return
                }
                item.sheetNum = this.sheetNum
              })
            } else {
              this.tableData = []
            }
            return res.data
          }).finally(res => {
            this.spin = false
          })
      }
    },
    exported () {
      const exportData = this.$deepClone(this.tableData)
      const fileName = `${exportData[0].rdNum}-${moment(this.param.workDate).format('YYYYMMDD')}-${this.param.typeName}`
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
