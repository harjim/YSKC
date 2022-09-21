<template>
  <!-- <a-card :bordered="false"> -->
  <div>
    <div id="table_box">
      <s-table
        :useLoading="false"
        ref="table"
        rowKey="num"
        size="small"
        :data="loadData"
        showPagination="auto"
        bordered
      >
        <a-table-column
          title="序号"
          data-index="num"
          key="num"
          :width="60"
          align="center"
          class="word-wrap">
          <template slot-scope="text,record,index">{{ pageNo + index+1 }}</template>
        </a-table-column>
        <!-- <a-table-column
          title="工号"
          data-index="enumber"
          key="enumber"
          :width="80"
          class="word-wrap"
        /> -->
        <a-table-column
          title="姓名"
          data-index="ename"
          key="ename"
          class="word-wrap"
          :width="80"
          align="center">
          <template slot-scope="text">
            <span>{{ text }}</span>
          </template>
        </a-table-column>
        <a-table-column
          title="部门"
          data-index="rdDeptName"
          key="rdDeptName"
          :width="100"
          class="word-wrap"
          align="center"
        >
        </a-table-column>
        <a-table-column
          align="center"
          title="项目角色"
          data-index="role"
          key="role"
          :width="80"
          class="word-wrap"
        >
        </a-table-column>
        <a-table-column
          align="center"
          title="本月参与工作量(小时）"
          :width="80"
          data-index="monthWorkOurs"
          class="word-wrap"
        >
          <template slot-scope="text">
            <span>{{ text ? text : '-' }}</span>
          </template>
        </a-table-column>
        <a-table-column
          align="center"
          title="备注"
          :width="80"
          data-index="Signature"
          class="word-wrap"
        >
        </a-table-column>
      </s-table>
    </div>
  </div>
</template>

<script>
import { STable, Ellipsis, SelectDown, TreeKeyMap } from '@/components'
import moment from 'moment'
import yearMixin from '@/utils/yearMixin'
export default {
  mixins: [yearMixin],
  name: 'AttendanceAggTab',
  components: {
    STable,
    Ellipsis,
    SelectDown,
    TreeKeyMap
  },
  props: {
    project: {
      type: Object,
      required: true
    },
    month: {
      type: String,
      default: ''
    },
    dataMonth: {
      type: String,
      default: ''
    },
    projectId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      pageNo: 0,
      editChecked: false,
      spinning: false,
      selectedRowKeys: [],
      selectedRowMap: {},
      editRow: {},
      headerData: {},
      queryParam: {},
      tableData: [],
      loadData: parameter => {
        this.spinning = true
        this.selectedRowKeys = []
        this.queryParam.projectId = this.projectId
        // if (this.dataMonth) {
        //   this.queryParam.month = moment(this.dataMonth)
        // } else {
        //   this.queryParam.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
        // }
        this.queryParam.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
        if (!this.queryParam.month) {
          this.$listeners.onControlSaveBtn(false)
          return Promise.resolve({ data: [], pageNo: 1, pageSize: 10, totalCount: 0, totalPage: 0 })
        }
        this.queryParam.year = this.project.beginYear
        return this.$http.get('/projectRdEmployee/getListByMonth', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            this.pageNo = res.data.pageNo ? (res.data.pageNo - 1) * 10 : 0
            this.tableData = res.data.data
            // this.isShow = res.data.footer
            this.$listeners.onControlSaveBtn(this.tableData.length > 0)
            return res.data
          })
          .catch(res => {
            this.headerData = {}
          })
          .finally(res => {
            this.spinning = false
          })
      }
    }
  },
  watch: {
    month () {
      this.$refs.table.refresh(true)
    }
  },
  methods: {
    moment,
    tableRefresh () {
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    }
  }
}
</script>

<style lang="less" scoped>
.word-wrap {
  word-break: break-all;
}
#table_box /deep/ .ant-table {
  width: 100%;
}
</style>
