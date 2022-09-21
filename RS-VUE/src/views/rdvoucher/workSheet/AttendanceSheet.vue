<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-button
          :disabled="datas.length <= 0"
          style="margin-left:10px;"
          type="primary"
          @click="exportData"
        >导出</a-button>
      </a-form>
      <br />
      <div>
        <a-table
          ref="table"
          size="small"
          rowKey="id"
          :pagination="false"
          :dataSource="datas"
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
            data-index="workNo"
            key="workNo"
            :width="120"
            class="word-wrap"
          ></a-table-column>
          <a-table-column
            align="center"
            title="摘要"
            data-index="remark"
            key="remark"
            :width="100"
            class="word-wrap"
          >
            <template slot-scope="text,record">
              <span v-if="!record.sum">{{ text }}</span>
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="科目"
            data-index="accountName"
            key="accountName"
            :width="100"
            validateStatus="success"
            class="word-wrap"
          >
            <template slot-scope="text,record">
              <a-tree-select
                v-if="!record.sum"
                style="width:250px"
                v-model="record.accountName"
                :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                :treeData="accountTree"
                placeholder="选择科目"
                treeNodeFilterProp="title"
                treeDefaultExpandAll
                @change="(value)=>onTreeChange(value,record)"
              ></a-tree-select>
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="借方金额"
            data-index="debitAmount"
            key="debitAmount"
            :width="100"
            class="word-wrap"
          >
            <template slot-scope="text">
              <span>{{ text | MoneyFormat }}</span>
            </template>
          </a-table-column>
          <a-table-column
            align="center"
            title="贷方金额"
            data-index="creditAmount"
            key="creditAmount"
            :width="100"
            class="word-wrap"
          >
            <template slot-scope="text">
              <span>{{ text | MoneyFormat }}</span>
            </template>
          </a-table-column>
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
  name: 'AttendanceSheet',
  components: {
    STable,
    Ellipsis
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    month: {
      type: String,
      default: ''
    },
    types: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      accountTree: [],
      wrapClassName: 'wrapClassName',
      drawerVisible: false,
      disabled: true,
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
  mounted () {
    this.loadTree()
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
    moment,
    onTreeChange (value, record) {
      this.$http.post('/workSheet/saveVoucherAccount', {
        projectId: record.projectId,
        rdType: record.rdType,
        month: record.month,
        accountId: value
      })
        .finally(res => {
          this.$emit('ok', true)
        })
    },
    loadTree () {
      this.$getTree('account')
        .then(res => {
          this.accountTree = res.tree
        })
    },
    exportData () {
      const exportData = this.$deepClone(this.datas)
      const fileName = `${exportData[0].rdIndexStr}-${this.queryParam.queryDate.format('YYYY')}-费用凭证`
      exportData[exportData.length - 1].yearStr = null
      exportData[exportData.length - 1].monthStr = null
      exportData[exportData.length - 1].dayStr = null
      this.$exportJsonData(fileName,
        ['年', '月', '日', '凭证号', '摘要', '科目', '借方金额', '贷方金额'],
        ['yearStr', 'monthStr', 'dayStr', 'workNo', 'remark', 'accountName', 'debitAmount', 'creditAmount'],
        exportData,
        [5, 5, 5, 20, 20, 20, 12, 12])
    },
    search () {
      if (this.projectId === 0) {
        return
      }
      if (moment(this.queryParam.queryDate).get('year') !== this.$store.state.currentYear) {
        this.$set(this.queryParam, 'queryDate', moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]))
        this.$set(this.queryParam, 'defaultPickerValue', moment([this.$store.state.currentYear, 0, 1, 0, 0, 0, 0]))
      }
      this.spinning = true
      this.$http.get('/workSheet/getWorkVoucherList', { params: { workDate: this.queryParam.queryDate, projectId: this.projectId, types: this.types } })
        .then(res => {
          if (res.success) {
            this.datas = res.data
            if (this.datas && this.datas.length > 0) {
              this.disabled = false
            } else {
              this.disabled = true
            }
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
