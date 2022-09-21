<template>
  <a-card :bordered="false">
    <div>
      <a-form layout="inline">
        <a-form-item label="项目">
          <project-select
            style="width:400px"
            ref="creatProject"
            :year="currentYear"
            :sign="2"
            allowClear
            :isJoinPrjectName="true"
            :selected="queryParam.projectId"
            v-model="queryParam.projectId"
          ></project-select>
        </a-form-item>
        <a-form-item label="试制月份" >
          <a-select
            v-model="queryParam.monthValue"
            placeholder="请选择试制月份"
            :allowClear="true"
            :options="monthOptions"
            style="width: 165px;"
          ></a-select>
        </a-form-item>
        <a-form-item label="试制日期">
          <a-date-picker
            format="YYYY-MM-DD"
            v-model="queryParam.trialDate"
            placeholder="请选择试制日期"
            style="width: 165px;"
          />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            v-if="$auth('project:trialProdPlan:search')"
            @click="query(true)"
          >查询</a-button
          >
        </a-form-item>
      </a-form>
    </div>
    <template>
      <ystable
        ref="table"
        :params="getParam()"
        queryUrl="/projectYieldConfig/queryPYieldConfigList"
        :toolbar="{ custom: true, zoom: true, refresh: true }"
        :checkbox-config="{checkMethod:checkMethod,showHeader: showHeaderChk}"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        @completed="({data:{data}})=>completed(data)"
      >
        <template v-slot:buttons>
          <span style="margin-right:10px" v-if="$auth('project:trialProdPlan:add')">
            <a-button type="primary" @click="$refs.modifyYield.add()">添加</a-button>
          </span>
          <span style="margin-right:10px" v-if="$auth('project:trialProdPlan:import')">
            <a-button type="primary" @click="$refs.uploadModal.show(tableField)">导入</a-button>
          </span>
          <span style="margin-right:10px">
            <a-button type="primary" v-if="$auth('project:trialProdPlan:export')" @click="exportTrialPlan">导出</a-button>
          </span>
          <span style="margin-right:10px">
            <a-button v-if="$auth('project:trialProdPlan:delete')" type="primary" @click="onBatchDelete()" :disabled="!selectRowKeys.length">删除</a-button>
          </span>
          <a-button
            type="primary"
            style="margin-right:10px;"
            v-if="$auth('project:trialProdPlan:agg')"
            @click="$refs.rdTrialAgg.show()"
          >费用归集</a-button>
        </template>
        <vxe-table-column type="checkbox" fixed="left" :width="40"/>
        <vxe-table-column
          title="RD"
          field="rdTitle"
          key="rdTitle"
          :width="120"
          show-header-overflow
          show-overflow="tooltip"
          remoteSort
          fixed="left"
        >
        </vxe-table-column>
        <vxe-table-column
          title="项目名称"
          field="pname"
          key="pname"
          :width="180"
          show-header-overflow
          show-overflow="tooltip"
          align="left"
          remoteSort
        >
        </vxe-table-column>
        <vxe-table-column
          remoteSort
          title="部门"
          field="deptName"
          key="deptName"
          min-width="100"
          show-header-overflow
          show-overflow="tooltip"
        >
        </vxe-table-column>
        <vxe-table-column
          remoteSort
          title="单位"
          field="unit"
          key="unit"
          :width="120"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          remoteSort
          title="试制品名"
          field="trialProduct"
          key="trialProduct"
          :width="140"
          show-header-overflow
          show-overflow="tooltip"
        />
        <vxe-table-column
          title="月份"
          field="month"
          key="month"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          title="总量"
          field="totalYield"
          key="totalYield"
          :width="150"
          remoteSort
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        >
        </vxe-table-column>
        <vxe-table-column
          title="计划量"
          field="planYield"
          key="planYield"
          :width="150"
          remoteSort
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          title="试制日期"
          field="trialDate"
          key="trialDate"
          remoteSort
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          title="试制量"
          field="rdYield"
          key="rdYield"
          :width="120"
          remoteSort
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          title="运行工时"
          field="totalHour"
          key="totalHour"
          remoteSort
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          title="研发工时"
          field="rdHour"
          key="rdHour"
          remoteSort
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          title="试制工时"
          field="trialHour"
          key="trialHour"
          remoteSort
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          title="开始时间"
          field="startTime"
          key="startTime"
          :width="150"
          remoteSort
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        >
        </vxe-table-column>
        <vxe-table-column
          title="结束时间"
          field="endTime"
          key="endTime"
          :width="120"
          remoteSort
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        >
        </vxe-table-column>
        <vxe-table-column
          title="检验工时"
          field="testHour"
          key="testHour"
          remoteSort
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          title="检验开始时间"
          field="testStartTime"
          key="testStartTime"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        >
        </vxe-table-column>
        <vxe-table-column
          title="检验结束时间"
          field="testEndTime"
          key="testEndTime"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        >
        </vxe-table-column>
        <vxe-table-column title="操作" align="center" :width="160" fixed="right">
          <template v-slot="{ row }">
            <span>
              <a @click="$refs.modifyYield.edit(row)" v-if="$auth('project:trialProdPlan:edit')">编辑</a>
              <template v-if="$auth('project:trialProdPlan:delete')">
                <a-divider type="vertical" v-if="$auth('project:trialProdPlan:edit')" />
                <a-popconfirm title="是否确定删除?" @confirm="del(row)">
                  <a :disabled="!row.del">删除</a>
                </a-popconfirm>
              </template>
            </span>
          </template>
        </vxe-table-column>
      </ystable>
    </template>
    <modify-yield-modal ref="modifyYield" @ok="query"/>
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="{}"
      paramKey="tableField"
      title="导入研发试制计划"
      ref="uploadModal"
      action="/doc/projectYieldConfig/importYield"
      templateName="研发试制计划模板"
      @onSuccess="query(true)"
    />
    <rdTrial-agg-modal ref="rdTrialAgg"/>
  </a-card>
</template>
<script>
import { mapGetters } from 'vuex'
import ystable from '@/components/Table/ystable'
import { UploadModal } from '@/components'
import ProjectSelect from '@/components/ProjectSelect'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
import ModifyYieldModal from './modules/ModifyYieldModal'
import RdTrialAggModal from './modules/RdTrialAggModal.vue'
export default {
  mixins: [yearMiXin],
  name: 'TrialProdPlan',
  components: {
    ystable,
    ModifyYieldModal,
    UploadModal,
    ProjectSelect,
    RdTrialAggModal
  },
  data () {
    return {
      tableField: {
        tableId: 'yieldTable',
        fieldTitleObject: {
          rdTitle: { title: 'RD', defaultTitle: 'RD', importField: true, required: true },
          deptName: { title: '部门', defaultTitle: '部门', importField: true, required: true },
          unit: { title: '单位', defaultTitle: '单位', importField: true, required: true },
          trialProduct: { title: '试制品名', defaultTitle: '试制品名', importField: true, required: false },
          month: { title: '月份', defaultTitle: '月份', importField: true, required: true },
          totalYield: { title: '总量', defaultTitle: '总量', importField: true, required: true },
          planYield: { title: '计划量', defaultTitle: '计划量', importField: true, required: true },
          trialDate: { title: '试制日期', defaultTitle: '试制日期', importField: true, required: true },
          rdYield: { title: '试制量', defaultTitle: '试制量', importField: true, required: true },
          totalHour: { title: '运行工时', defaultTitle: '运行工时', importField: true, required: true },
          trialHour: { title: '试制工时', defaultTitle: '试制工时', importField: true, required: true },
          startTime: { title: '开始时间', defaultTitle: '开始时间', importField: true, required: true },
          endTime: { title: '结束时间', defaultTitle: '结束时间', importField: true, required: true },
          testHour: { title: '检验工时', defaultTitle: '检验工时', importField: true },
          testStartTime: { title: '检验开始时间', defaultTitle: '检验开始时间', importField: true },
          testEndTime: { title: '检验结束时间', defaultTitle: '检验结束时间', importField: true }
        },
        hasMonth: true
      },
      sampleData: [
        {
          rdTitle: '2021RD01',
          deptName: 'xxx部门',
          unit: '个',
          trialProduct: 'A产品',
          month: '格式："年-月-日"，一般为当月第一天，例如：2019-10-01',
          totalYield: '数字格式，例如：300000',
          planYield: '数字格式，例如：300000',
          trialDate: '格式："年-月-日"，例如：2019-10-11',
          rdYield: '数字格式，例如：300000',
          totalHour: '数字格式，例如：300000',
          trialHour: '数字格式，例如：300000',
          startTime: '格式："时:分"，例如：14:50',
          endTime: '格式："时:分"，例如：14:50',
          testHour: '数字格式，例如：300000',
          testStartTime: '格式："时:分"，例如：14:50',
          testEndTime: '格式："时:分"，例如：14:50'
        }
      ],
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: { projectId: undefined },
      selectedRowKeys: [],
      selectedRows: [],
      options: {
        alert: {
          show: true,
          clear: () => {
            this.selectedRowKeys = []
          }
        },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false,
      selectRowKeys: [],
      showHeaderChk: true
    }
  },
  filters: {},
  methods: {
    initParam () {
      this.queryParam.year = this.$store.state.currentYear ? this.$store.state.currentYear : null
      if (this.queryParam.monthValue === Number.undefined || this.queryParam.monthValue === '-1') {
        this.queryParam.month = null
      } else {
        this.queryParam.month = moment([this.currentYear, this.queryParam.monthValue, 1, 0, 0, 0, 0])
      }
      this.queryParam.year = this.currentYear
    },
    getParam () {
      this.initParam()
      return this.queryParam
    },
    ...mapGetters(['userInfo']),
    exportTrialPlan () {
      this.initParam()
      this.$exportData(
        '/projectYieldConfig/exportYield',
        this.queryParam,
        `${this.userInfo().companyName}-研发试制计划表.xlsx`,
        this.$message
      )
    },
    moment,
    search () {
      this.$nextTick(() => {
        this.queryParam.year = this.$store.state.currentYear
        this.queryParam.projectId = undefined
        this.query(true)
      })
    },
    query (refresh) {
      if (this.$refs.table) {
        this.$refs.table.refresh(refresh)
      }
    },
    // handleDel (record) {
    //   this.$http.post('/rsRole/del', record).then(res => {
    //     if (res.success && res.data) {
    //       this.$message.success('删除成功')
    //     } else {
    //       this.$message.error(res.errorMessage)
    //     }
    //   }).finally(res => {
    //     this.search(false)
    //   })
    // },
    setHandleOk (flag) {
      this.query(false)
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.query(flag)
    },
    del (record) {
      this.$http
        .post('/projectYieldConfig/delete', { ids: [record.id] })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
          } else {
            this.$message.error('删除失败')
          }
        })
        .finally(res => {
          this.query(false)
        })
    },
    onBatchDelete () {
      const self = this
      this.$confirm({
        title: '您确定要删除选中项吗?',
        onOk () {
          return self.$http.post('/projectYieldConfig/delete', { ids: self.selectRowKeys })
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.query(false)
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
            })
        },
        onCancel () {
        }
      })
    },
    selectChange ({ records }) {
      this.selectRowKeys = records.map(a => a.id)
    },
    checkMethod ({ row }) {
      return row.del
    },
    completed (data) {
      if (data && data.length) {
        this.showHeaderChk = data.some(item => item.del)
      } else {
        this.showHeaderChk = false
      }
    }
  }
}
</script>
