<template>
  <!-- <a-card :bordered="false"> -->
  <div>
    <div id="table_box">
      <s-table
        :useLoading="false"
        ref="table"
        rowKey="ecode"
        size="small"
        :data="loadData"
        showPagination="auto"
        bordered
        style="width: 100%;"
      >
        <a-table-column
          align="center"
          title="序号"
          data-index="index"
          key="index"
          :width="60"
          class="word-wrap"
        >
          <template slot-scope="text,record,index">{{ pageNo + index+1 }}</template>
        </a-table-column>
        <a-table-column
          align="center"
          title="资产代码"
          data-index="ecode"
          key="ecode"
          :width="80"
          class="word-wrap"
        />
        <a-table-column
          align="center"
          title="设备名称"
          data-index="ename"
          key="ename"
          :width="80"
          class="word-wrap"
        >
          <template slot-scope="text,record">
            <a-badge v-if="record.usedList" :dot="record.usedList.length > 0 ">
              <a-tooltip placement="top">
                <template slot="title">
                  <div
                    v-for="(item, index) in record.usedList"
                    :key="index"
                  >{{ item.beginYear }}RD{{ item.rdIndex | ZeroFormat }}分配工时：{{ item.rdHour }}</div>
                </template>
                <span>{{ text }}</span>
              </a-tooltip>
            </a-badge>
            <span v-else>{{ text }}</span>
          </template>
        </a-table-column>
        <a-table-column
          align="center"
          title="运行工时"
          :width="60"
          data-index="monthHour"
          class="word-wrap"
        >
          <template slot-scope="text">
            <span>{{ text ? text : '-' }}</span>
          </template>
        </a-table-column>
        <a-table-column
          align="center"
          title="型号"
          data-index="emodal"
          key="emodal"
          :width="80"
          class="word-wrap"
        />
        <a-table-column
          align="center"
          title="项目中的作用"
          data-index="effect"
          key="effect"
          :width="80"
          class="word-wrap"
        />
        /</s-table>
    </div>
  </div>
</template>

<script>
import { STable, Ellipsis, SelectDown, TreeKeyMap } from '@/components'
import moment from 'moment'
import yearMixin from '@/utils/yearMixin'
function toBit (v, bit) {
  var b = 2
  if (bit || bit >= 0) {
    b = bit
  }

  // 保留双倍小数位，确保四舍五入不会丢失精度
  v = Number(v).toFixed(b * 2)
  return (Math.round(v * 100) / 100).toFixed(b)
}
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
      editChecked: false,
      spinning: false,
      selectedRowKeys: [],
      selectedRowMap: {},
      editRow: {},
      headerData: {},
      queryParam: {},
      tableData: [],
      pageNo: 0,
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
        // this.queryParam.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
        this.queryParam.year = this.project.beginYear
        return this.$http.get('/projectRdEquipment/queryList', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            this.pageNo = res.data.pageNo ? (res.data.pageNo - 1) * 10 : 0
            this.tableData = res.data.data
            this.$listeners.onControlSaveBtn(this.tableData.length > 0)
            if (res.data != null && res.data.header != null) {
              this.headerData.lab = res.data.header.lab
              this.headerData.prod = res.data.header.prod
              if (this.headerData.lab && this.headerData.prod) {
                this.headerData.total = toBit(Number(this.headerData.lab) + Number(this.headerData.prod))
              }
            } else {
              this.headerData = {}
            }
            return res.data
          }).catch(res => {
            this.headerData = {}
          }).finally(res => {
            this.spinning = false
          })
      }
    }
  },
  created () {

  },
  watch: {
    // project (project) {
    //   this.queryParam.projectId = project.id
    //   this.queryParam.month = null
    //   this.$nextTick(() => {
    //     this.$refs.table.refresh(true)
    //   })
    // },
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
