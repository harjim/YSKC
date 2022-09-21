<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline" class="dept_user_search">
        <a-form-item label="日期">
          <a-date-picker
            format="YYYY-MM-DD"
            v-model="queryParam.queryDate"
            :defaultPickerValue="queryParam.defaultPickerValue"
            placeholder="请选择日期"
          />
        </a-form-item>
        <a-button type="primary" @click="getData">查询</a-button>
        <a-button :disabled="disabled" style="left:20px;" type="primary" @click="exportData">导出</a-button>
      </a-form>
      <br />
      <div>
        <a-table
          ref="table"
          size="small"
          rowKey="id"
          :dataSource="datas"
          :pagination="false"
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
            data-index="dname"
            key="dname"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="name"
            title="参考凭证编号（工单号）"
            data-index="name"
            key="name"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="期间"
            data-index="designDate"
            key="designDate"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="工作事件"
            data-index="设计费用"
            key="设计费用"
            :width="100"
            class="word-wrap"
          >
          </a-table-column>
          <a-table-column
            align="center"
            title="费用支出金额"
            data-index="dFee"
            key="dFee"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="CO对象名称/工件"
            data-index="rdDeptName"
            key="rdDeptName"
            :width="100"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="项目编号"
            data-index="rdIndexStr"
            key="rdIndexStr"
            :width="100"
            class="word-wrap"
          ></a-table-column>
        </a-table>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import moment from 'moment'
export default {
  name: 'DesignSheet',
  components: {
    STable
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    month: {
      type: String,
      default: ''
    },
    rdTypeInfo: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      datas: [],
      disabled: true,
      scroll: { y: 600 },
      spinning: false,
      queryParam: { queryDate: null, defaultPickerValue: null },
      pagination: [],
      getData: parameter => {
        return this.$http.get('/designProject/queryuOutData', { params: Object.assign(parameter, { projectId: this.projectId, date: this.queryParam.queryDate }) })
          .then(res => {
            this.datas = res.data
            if (this.datas && this.datas.length > 0) {
              var num = 0
              this.datas.forEach(e => {
                e.num = ++num
              })
              this.disabled = false
            } else {
              this.disabled = true
            }

            return res.data
          })
      },
      options: {}
    }
  },
  watch: {
    projectId (newId) {
      this.getData()
    },
    allSelectedRows (newRows) {
      var rows = this.getPostItems()
      if (rows.length > 0) {
        this.deleteListDisabled = false
      } else {
        this.deleteListDisabled = true
      }
    }
  },
  created () {
    if (document.body.clientWidth < 1700) {
      this.scroll = { x: 1240, y: 600 }
    }
    const nowDate = new Date()
    const date = nowDate.getFullYear()
    this.$set(this.queryParam, 'defaultPickerValue', moment([date, 0, 1, 0, 0, 0, 0]))
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1700) {
          this.scroll = { x: 1240, y: 600 }
        }
      })()
    }
  },
  methods: {
    moment,
    exportData () {
      const fileName = `${this.queryParam.queryDate.format('YYYYMMDD')}工单`
      this.$exportJsonData(fileName,
        ['序号', '成本要素', '参考凭证编号（工单号）', '期间', '工作事件', '费用支出金额', 'CO对象名称/工件', '项目编号'],
        ['num', 'dname', '', 'designDate', 'type', 'dFee', 'rdDeptName', 'id'],
        this.datas)
    }
  }
}
</script>

<style>
</style>
