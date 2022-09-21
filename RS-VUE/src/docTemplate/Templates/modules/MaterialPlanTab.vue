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
        <a-table-column title="序号" data-index="num" key="num" :width="60" class="word-wrap">
          <template slot-scope="text,record,index">{{ index+1 }}</template>
        </a-table-column>
        <a-table-column title="材料编号" data-index="mcode" key="mcode" class="word-wrap" :width="80">
        </a-table-column>
        <a-table-column
          title="材料名称"
          data-index="mname"
          key="mname"
          :width="100"
          class="word-wrap"
        >
        </a-table-column>
        <a-table-column
          align="center"
          title="数量"
          data-index="quantity"
          key="quantity"
          :width="80"
          class="word-wrap"
        >
        </a-table-column>
        <a-table-column
          align="center"
          title="单位"
          data-index="unit"
          key="unit"
          :width="70"
          class="word-wrap"
        >
        </a-table-column>
        <a-table-column
          align="center"
          title="日期"
          :width="80"
          data-index="acqDate"
          class="word-wrap"
        >
          <template slot-scope="text">
            {{ text ? moment(text).format('YYYY-MM-DD') : '' }}
          </template>
        </a-table-column>
        <a-table-column
          align="center"
          title="出库单号"
          :width="80"
          data-index="billNo"
          class="word-wrap"
        >
        </a-table-column>
        <a-table-column
          align="center"
          title="备注"
          :width="80"
          data-index="remark"
          class="word-wrap"
        >
        </a-table-column>
        <!-- <a-table-column
          align="center"
          title="领料人"
          :width="80"
          data-index="picker"
          class="word-wrap"
        >
        </a-table-column>
        <a-table-column
          align="center"
          title="制单人"
          :width="80"
          data-index="biller"
          class="word-wrap"
        >
        </a-table-column> -->
        <!-- 2022年5月24日13:58:50 邓总：不显示领料人，制单人 -->
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
  name: 'MaterialPlanTab',
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
    },
    docFileId: {
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
      loadData: parameter => {
        this.spinning = true
        this.selectedRowKeys = []
        this.queryParam.projectId = this.projectId
        this.queryParam.docFileId = this.docFileId
        // if (this.dataMonth) {
        //   this.queryParam.month = moment(this.dataMonth)
        // } else {
        //   this.queryParam.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
        // }
        this.queryParam.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
        if (!this.queryParam.month) {
          this.tableData = []
          this.$listeners.onControlSaveBtn(false)
          this.$emit('change', false)
          return Promise.resolve({ data: [], pageNo: 1, pageSize: 10, totalCount: 0, totalPage: 0 })
        }
        // this.queryParam.month = this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00')
        return this.$http.get('/material/getMaterialPlan', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            this.tableData = res.data.data
            const result = this.tableData.length > 0
            this.$listeners.onControlSaveBtn(result)
            this.$emit('change', result)
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
  computed: {
    hasChange () {
      return this.type && this.trial
    }
  },
  watch: {
    month (m) {
      if (this.$refs.table) {
        this.$refs.table.refresh(true)
      }
    },
    hasChange () {
      this.$refs.table.refresh(true)
    }
  },
  methods: {
    moment
    // tableRefresh () {
    //   this.$nextTick(() => {
    //     this.$refs.table.refresh(true)
    //   })
    // }
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
