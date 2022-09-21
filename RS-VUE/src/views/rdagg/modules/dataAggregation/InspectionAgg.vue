<template>
  <div>
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <a-form
        layout="inline"
        :form="form"
      >
        <a-form-item label="记账日期">
          <a-date-picker
            format="YYYY-MM-DD"
            v-model="selectAccDate"
            placeholder="请选择记账日期"
            style="width:165px"
          />
        </a-form-item>
        <a-form-item label="部门">
          <a-input
            v-model="deptName"
            style="width:165px"
            placeholder="请输入部门"
          />
        </a-form-item>
        <a-form-item label="摘要">
          <a-input
            v-model="summary"
            style="width:165px"
            placeholder="请输入摘要"
          />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div style="height: calc(82% - 50px); min-height: 213px; width: 100%;">
        <ystable
          ref="table"
          rowId="id"
          max-height="100%"
          highlight-hover-row
          highlight-current-row
          show-overflow
          resizable
          auto-resize
          :toolbar="{zoom:true,refresh:true,custom:true}"
          queryUrl="/projectInspection/queryProjectInspectionList"
          @completed="({data})=>completed(data)"
          :params="{ projectId: this.projectId, types: this.rdTypeInfo.types.toString(), accDate: this.selectAccDate, projectMonth: this.monthDate, summary: this.summary, deptName: this.deptName }"
          @checkbox-change="onSelectChange"
          @checkbox-all="onSelectChange"
          :pagerConfig="{ pageSize: 50, pageSizes: [50, 100, 200, 500] }"
        >
          <template v-slot:buttons>
            <template v-if="canModify && $auth('project:data:agg')">
              <span>
                <a-button
                  type="primary"
                  @click="$refs.modal.add(projectId,month,rdTypeInfo.types)"
                >添加</a-button>
              </span>
              <span style="padding-left:10px;">
                <a-button
                  type="primary"
                  @click="delInspection"
                  :disabled="inspectionList.length <= 0"
                >删除</a-button>
              </span>
              <span style="padding-left:10px;">
                <a-button
                  type="primary"
                  @click="showSetRdAmount"
                  :disabled="inspectionList.length <= 0"
                >设置研发费用</a-button>
              </span>
              <span style="padding-left:10px;">
                <rd-fee-submit :projectId="projectId" :month="month" :rdType="rdFeeType" @getSummary="getSummary"/>
              </span>
            </template>
            <span>
              &nbsp;&nbsp;总计：
              <a style="font-weight: 600">{{ totals.total ? totals.total.toFixed(2) : '-' }}</a>
            </span>
            <span v-if="rdTypeInfo.types.length==3">
              &nbsp;&nbsp;软件摊销：
              <a style="font-weight: 600">{{ totals.software ? totals.software.toFixed(2) : '-' }}</a>
              &nbsp;&nbsp; 专利摊销：
              <a style="font-weight: 600">{{ totals.patent ? totals.patent.toFixed(2) : '-' }}</a>
              &nbsp;&nbsp;其他摊销：
              <a style="font-weight: 600">{{ totals.otherAmortization ? totals.otherAmortization.toFixed(2) : '-' }}</a>
            </span>
            <span v-if="rdTypeInfo.types.length==5">
              &nbsp;&nbsp;资料：
              <a style="font-weight: 600">{{ totals.book ? totals.book.toFixed(2) : '-' }}</a>
              &nbsp;&nbsp; 研发成果：
              <a style="font-weight: 600">{{ totals.rdProduction ? totals.rdProduction.toFixed(2) : '-' }}</a>
              &nbsp;&nbsp;知识产权：
              <a style="font-weight: 600">{{ totals.copyright ? totals.copyright.toFixed(2) : '-' }}</a>
              &nbsp;&nbsp;福利：
              <a style="font-weight: 600">{{ totals.benefits ? totals.benefits.toFixed(2) : '-' }}</a>
              &nbsp;&nbsp;其他：
              <a style="font-weight: 600">{{ totals.other ? totals.other.toFixed(2) : '-' }}</a>
            </span>
            <span v-if="canModify && $auth('project:data:agg')">
              <a-switch
                checkedChildren="编辑"
                unCheckedChildren="编辑"
                style="margin-left:10px;"
                type="primary"
                :checked="editChecked"
                @change="editCheckedChange"
              />
              <a-button
                style="margin-left: 10px;"
                type="primary"
                size="small"
                v-if="editChecked"
                @click="saveData"
              >保存</a-button>
            </span>
          </template>
          <vxe-table-column
            type="checkbox"
            :width="40"
            fixed="left"
          />
          <vxe-table-column
            title="摘要"
            :min-width="240"
            field="summary"
            show-header-overflow
            show-overflow="title"
            fixed="left"
          >
            <template slot-scope="{row}">
              <a-badge
                v-if="row.usedModels"
                :dot="row.usedModels.length > 0 "
              >
                <a-tooltip placement="top">
                  <template slot="title">
                    <div
                      v-for="(item, index) in row.usedModels"
                      :key="index"
                    >{{ item.rdTitle }} 归集金额：{{ item.rdAmount }}</div>
                  </template>
                  <span>{{ row.summary }}</span>
                </a-tooltip>
              </a-badge>
              <span v-else>{{ row.summary }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="center"
            title="记账日期"
            :width="120"
            field="accDate"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            title="凭证号"
            :width="180"
            field="accNumber"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            title="费用类型"
            :width="110"
            field="type"
            remoteSort
            align="center"
            show-header-overflow
            show-overflow="title"
          >
            <template slot-scope="{row}">{{ typeArr[row.type] }}</template>
          </vxe-table-column>
          <vxe-table-column
            title="部门"
            :width="160"
            field="deptName"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            align="center"
            title="费用"
            :width="120"
            field="expense"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            title="研发费用"
            field="rdAmount"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{row}">
              <span v-if="!editChecked">{{ row.rdAmount ? Number(row.rdAmount) : '-' }}</span>
              <span v-else>
                <a-input-number
                  v-model="row.rdAmount"
                  style="width:100%"
                  :min="(row.Amount + row.remainExpense) > 0 ? 0 : (row.Amount + row.remainExpense)"
                  :max="(row.Amount + row.remainExpense) > 0 ? (row.Amount + row.remainExpense) : 0"
                  :precision="2"
                  size="small"
                  @change="v=>handleChange(v,row)"
                />
              </span>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <projectInspection-modal
        ref="modal"
        @ok="search(true)"
      ></projectInspection-modal>
      <set-rdAmount-modal
        url="/projectInspection/setInspectAmounts"
        ref="setRdAmountModal"
        @ok="search(true)"
      />
    </a-spin>
  </div>
</template>

<script>
import RdFeeSubmit from './modules/RdFeeSubmit.vue'
import ystable from '@/components/Table/ystable'
import ProjectInspectionModal from './bindingDataModules/ProjectInspectionModal'
import SetRdAmountModal from './modules/SetRdAmountModal'
import moment from 'moment'
export default {
  name: 'InspectionAgg',
  components: {
    ystable,
    ProjectInspectionModal,
    SetRdAmountModal,
    RdFeeSubmit
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    month: {
      type: String,
      required: true
    },
    monthDate: {
      type: String,
      required: true
    },
    rdTypeInfo: {
      type: Object,
      required: true
    },
    rdFeeType: {
      type: String,
      required: true
    },
    canModify: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      typeArr: {
        '20500': '检测',
        '20600': '修理',
        '40000': '软件摊销',
        '40100': '专利摊销',
        '40200': '其他摊销',
        '20300': '其他试制',
        '69900': '其他',
        '60400': '差旅费',
        '60000': '资料',
        '60100': '研发成果',
        '60200': '知识产权',
        '60300': '福利'
      },
      spinning: false,
      form: this.$form.createForm(this),
      selectAccDate: undefined,
      summary: '',
      deptName: undefined,
      totals: {
        total: 0,
        software: 0,
        patent: 0,
        otherAmortization: 0,
        book: 0,
        rdProduction: 0,
        copyright: 0,
        benefits: 0,
        other: 0
      },
      inspectionList: [],
      tableData: [],
      editRow: {},
      editChecked: false
    }
  },
  watch: {
    projectId (newId) {
      this.search(true)
    }
  },
  methods: {
    moment,
    search (refresh) {
      this.getSummary()
      this.$refs.table.refresh(refresh)
    },
    getSummary () {
      this.$emit('getSummary')
    },
    completed (data) {
      data.data.forEach(item => {
        item.Amount = item.rdAmount
      })
      this.tableData = data.data
      this.inspectionList = []
      for (const key in this.totals) {
        this.totals[key] = 0
      }
      if (!data) {
        return
      }
      if (this.rdTypeInfo.types.length === 1) {
        this.totals.total = data.header
      } else {
        if (data.header) {
          if (this.rdTypeInfo.types.length === 3) {
            this.totals.software = data.header.softwareSum
            this.totals.patent = data.header.patentSum
            this.totals.otherAmortization = data.header.otherSum
            this.totals.total = data.header.softwareSum + data.header.patentSum + data.header.otherSum
          } else {
            this.totals.book = data.header.book
            this.totals.rdProduction = data.header.rdProduction
            this.totals.copyright = data.header.copyright
            this.totals.benefits = data.header.benefits
            this.totals.other = data.header.other
            this.totals.total = data.header.book + data.header.rdProduction + data.header.copyright + data.header.benefits + data.header.other
          }
        }
      }
      this.editCheckedChange(this.editChecked)
    },
    onSelectChange ({ records }) {
      this.inspectionList = records
    },
    delInspection () {
      if (this.inspectionList.length === undefined || this.inspectionList.length < 1) {
        this.$message.info('请选择要删除的费用')
        return
      }
      const self = this
      this.$confirm({
        title: '您确定要删除选中的费用吗?',
        onOk () {
          const value = {}
          const arr = []
          self.inspectionList.forEach(item => {
            arr.push({ 'type': item.type, 'id': item.id })
          })
          value.inspectionEntities = arr
          value.projectMonth = self.monthDate
          value.projectId = self.projectId
          return self.$http.post('/projectInspection/delInspectionBatch', value)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
              } else {
                self.$message.error(res.errorMessage || '删除失败')
              }
              self.search(false)
            })
        },
        onCancel () {
        }
      })
    },
    editCheckedChange (ck) {
      this.editChecked = ck
      if (this.tableData) {
        if (ck) {
          this.tableData.forEach(record => {
            record.cacheData = { ...record }
          })
        } else {
          this.tableData.forEach(record => {
            Object.assign(record, record.cacheData)
            delete record.cacheData
          })
        }
      }
    },
    saveData () {
      if (!this.editRow) {
        return
      }
      this.spinning = true
      const arr = []
      for (const k in this.editRow) {
        arr.push(this.editRow[k])
      }
      if (arr.length === 0) {
        this.spinning = false
        return
      }
      const params = {
        projectId: this.projectId,
        projectMonth: this.monthDate,
        models: arr
      }
      this.$http.post('/projectInspection/setRdAmounts', params).then((res) => {
        if (res.data && res.success) {
          this.spinning = false
          this.$message.success('保存成功')
          this.editRow = {}
          this.search(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    showSetRdAmount () {
      const params = {
        projectId: this.projectId,
        projectMonth: this.monthDate,
        models: this.inspectionList
      }
      this.$refs.setRdAmountModal.show(params)
    },
    handleChange (v, record) {
      if (v !== '-' && record.rdAmount && Number(record.rdAmount) !== 0) {
        record.rdAmount = v
        this.editRow[record.id] = record
      }
    }
  }
}
</script>

<style lang="less">
.ant-drawer-header {
  height: 50px;
}
.ant-drawer-body {
  height: calc(100% - 50px);
  overflow: hidden;
}
.ant-spin-container .ant-spin-nested-loading, .customCard, .ant-tabs-content, .ant-tabs, .ant-layout, .ant-spin-container, .ant-spin-nested-loading {
  height: 100%;
}
#tabs {
  width: 100%;
}
</style>
<style lang="less" scoped>
 div {
  height: 100%;
 }
</style>
