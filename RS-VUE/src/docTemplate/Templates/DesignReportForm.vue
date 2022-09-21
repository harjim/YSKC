<!--
 * @Author: ldx
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 11:53:34
 * @LastEditors: hm
 * @Description: 验证报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\DesignReportForm.vue
-->
<template>
  <a-card id="wrap_box">
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目名称"
          style="margin-bottom: 0px">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目编号"
          style="margin-bottom: 0px">
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目阶段"
          style="margin-bottom: 0px">
          {{ fileInfo.stageType }}
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="负责人"
          style="margin-bottom: 0px">
          <span>{{
            publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : ''
          }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider />
    <!-- <a-button type="primary" @click="handleAdd" style="margin-bottom: 8px">添加</a-button> -->
    <a-form layout="inline">
      <a-form-item label="月份" style="margin-bottom: 8px">
        <a-select
          v-model="content.month"
          :getPopupContainer="getPopupContainer"
          placeholder="请选择月份"
          style="width: 120px"
          @change="onChange"
        >
          <a-select-option v-for="n in months" :key="n" :value="n">{{ n }}
          </a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
    <a-table
      :columns="columns"
      :dataSource="tableList"
      :loading="tableLoading"
      :pagination="false"
      :row-selection="rowSelection"
      :scroll="{ x: 800, y: 480 }"
      bordered
      rowKey="id"
      size="small"
      @change="handleChange"
    >
      <template slot="trialDate" slot-scope="text,record">
        {{ record.trialDate ? moment(record.trialDate).format('YYYY-MM-DD') : ''
        }}
      </template>
      <template slot="startTime" slot-scope="text,record">
        {{ record.startTime ? moment(record.startTime).format('HH:mm') : '' }}
      </template>
      <template slot="endTime" slot-scope="text,record">
        {{ record.endTime ? moment(record.endTime).format('HH:mm') : '' }}
      </template>
      <template slot="rdYield" slot-scope="text,record">
        {{ record.rdYield === null || record.rdYield === '' || record.rdYield === 'undefined' ? 0 : record.rdYield
        }}
      </template>
      <template slot="unit" slot-scope="text,record">
        {{ record.unit === null || record.unit === '' || record.unit === 'undefined' ? '-' : record.unit
        }}
      </template>
    </a-table>
    <a-divider />
    <a-row>
      <a-tabs defaultActiveKey="1">
        <a-tab-pane key="1">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getPopupContainer"
              required
              text="试验试制目的"
              title="常见写法通过研究阶段的试验，确定XXX产品的XX参数指标，确定采用XXX方案；
                通过研究阶段的试验，优化XXX工艺的XX技术参数，进而确定采用XXX方案，达到XXX效果；
                通过研究阶段的试验，分析XX技术可实现的XX技术指标，验证是否可达到预期指标XXX。"
            />
          </template>
          <a-textarea
            v-model="content.objectives"
            :rows="10"
            placeholder="常见写法通过研究阶段的试验，确定XXX产品的XX参数指标，确定采用XXX方案；
通过研究阶段的试验，优化XXX工艺的XX技术参数，进而确定采用XXX方案，达到XXX效果；
通过研究阶段的试验，分析XX技术可实现的XX技术指标，验证是否可达到预期指标XXX。"
          />
        </a-tab-pane>
        <a-tab-pane key="2">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getPopupContainer"
              required
              text="试验试制研发资源使用情况"
              title="如下供参考： 1、本次试验试制参与的研发人员及工时情况，详见《研发人员工时记录》；
                2、本次试验试制涉及的研发设备仪器及其使用情况，详见《仪器设备使用记录》；
                3、本次试验试制物料消耗情况和领用明细详见《机物料申领清单》。--若有则此条保留，若无则删除此条。"
            />
          </template>
          <a-textarea
            v-model="content.usageSituation"
            :rows="10"
            placeholder="如下供参考：
1、本次试验试制参与的研发人员及工时情况，详见《研发人员工时记录》；
2、本次试验试制涉及的研发设备仪器及其使用情况，详见《仪器设备使用记录》；
3、本次试验试制物料消耗情况和领用明细详见《机物料申领清单》。--若有则此条保留，若无则删除此条。"
          />
        </a-tab-pane>
        <a-tab-pane key="3">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getPopupContainer"
              required
              text="试验试制情况及存在的问题"
              title="根据项目实际情况，记录、撰写项目在研究阶段试制的情况及遇到的问题。"
            />
          </template>
          <a-textarea
            v-model="content.existingProblems"
            :rows="10"
            placeholder="根据项目实际情况，记录、撰写项目在研究阶段试制的情况及遇到的问题。"
          />
        </a-tab-pane>
        <a-tab-pane key="4">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getPopupContainer"
              required
              text="分析意见和建议"
              title="针对罗列的问题一一分析，撰写解决方案，最后还可以提出问题解决方案之外的建议。"
            />
          </template>
          <a-textarea
            v-model="content.proposal"
            :rows="10"
            placeholder="针对罗列的问题一一分析，撰写解决方案，最后还可以提出问题解决方案之外的建议。"
          />
        </a-tab-pane>
      </a-tabs>
    </a-row>
    <a-divider />
    <audit-footer
      ref="audtiFooter"
      :docId="docId"
      :employeeMap="employeeMap"
      :projectId="projectId"
      :year="year" />
  </a-card>
</template>
<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import TrialProductionModal from './modules/TrialProductionModal'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import {
  domName,
  popupContainer
} from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'
import YsTooltip from '@/components/YsTooltip/YsTooltip'

export default {
  name: 'DesignReportForm',
  components: {
    TrialProductionModal,
    AuditFooter,
    YsTooltip
  },

  mixins: [dateMixin],
  props: {
    dataMonth: {
      type: String,
      default: ''
    },
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      domName,
      width: 960,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      content: {},
      BPContent: {},
      dataList: [],
      project: {},
      fileInfo: {},
      tableList: [],
      hasTrialProduct: false,
      months: [],
      selectedRowKeys: [],
      computedFields: ['objectives', 'usageSituation', 'existingProblems', 'proposal'],
      deptNameFiltersList: [],
      unitFiltersList: [],
      filteredInfo: null,
      tableLoading: false,
      requiredFields: ['objectives', 'usageSituation', 'existingProblems', 'proposal']
    }
  },
  computed: {
    columns () {
      let { filteredInfo } = this
      filteredInfo = filteredInfo || {}
      const columns = [
        {
          title: '试制日期',
          dataIndex: 'trialDate',
          key: 'trialDate',
          width: 150,
          align: 'center',
          scopedSlots: { customRender: 'trialDate' }
        },
        {
          title: '开始时间',
          dataIndex: 'startTime',
          key: 'startTime',
          width: 150,
          align: 'center',
          scopedSlots: { customRender: 'startTime' }
        },
        {
          title: '结束时间',
          dataIndex: 'endTime',
          key: 'endTime',
          width: 150,
          align: 'center',
          scopedSlots: { customRender: 'endTime' }
        },
        {
          title: '试验试制量',
          dataIndex: 'rdYield',
          key: 'rdYield',
          width: 100,
          align: 'center',
          scopedSlots: { customRender: 'rdYield' }
        },
        {
          title: '单位',
          dataIndex: 'unit',
          key: 'unit',
          width: 90,
          align: 'center',
          scopedSlots: { customRender: 'unit' },
          filters: this.unitFiltersList,
          onFilter: (value, record) => record.unit.includes(value),
          filteredValue: filteredInfo.unit || null
        }
      ]
      if (this.hasTrialProduct) {
        columns.push({
          title: '试制品名',
          dataIndex: 'trialProduct',
          key: 'trialProduct',
          width: 120,
          align: 'center'
        })
      }
      columns.push({
        title: '地点',
        dataIndex: 'deptName',
        key: 'deptName',
        width: 150,
        align: 'center',
        filters: this.deptNameFiltersList,
        onFilter: (value, record) => record.deptName.includes(value),
        filteredValue: filteredInfo.deptName || null
      })
      return columns
    },
    rowSelection () {
      return {
        onChange: this.onSelectChange,
        getCheckboxProps: record => ({
          props: {
            defaultChecked: this.selectedRowKeys.includes(record.id)
          }
        })
      }
    }
  },
  created () {
    this.content = getTemplateContent('designReportForm')
    this.BPContent = getTemplateContent('designReportForm')
    this.hsaFileDataNull = false
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    },
    'content.dataList' (newValue, oldValue) {
      this.content.dataList.forEach((item, index) => {
        item['index'] = index + 1
      })
    },
    docId () {
      this.content = getTemplateContent('designReportForm')
      this.BPContent = getTemplateContent('designReportForm')
    }
  },
  methods: {
    moment,
    getPopupContainer (triggerNode) {
      return popupContainer(this.domName)
    },
    handleTemplateEvent () {
      this.initMonths()
      this.content.projectPhase = this.fileInfo.stageType
      if (!this.content.month) {
        if (this.dataMonth) {
          this.content.month = moment(this.dataMonth).format('YYYY-MM')
          this.BPContent.month = moment(this.dataMonth).format('YYYY-MM')
        }
      }
      const month = this.content.month ? this.content.month + '-01 00:00:00' : undefined
      this.queryTableList(this.fileInfo.docId, this.project.id, month)
    },
    initMonths () {
      var endDate = this.project.endDate
      var beginDate = this.project.beginDate
      var beginArray = beginDate.split('-').map(Number)
      var endArray = endDate.split('-').map(Number)
      var returnArry = []
      var begin = parseInt(beginArray[0])
      var end = parseInt(endArray[0])
      for (let y = begin; y <= end; y++) {
        const current = y === begin ? parseInt(beginArray[1]) : 1
        const last = y === end ? parseInt(endArray[1]) : 12
        for (let i = current; i <= last; i++) {
          returnArry.push(`${y}-${(i + '').padStart(2, '0')}`)
        }
      }
      this.months = returnArry
    },

    queryTableList (docFileId, projectId, month) {
      this.filteredInfo = {}
      this.deptNameFiltersList = []
      this.unitFiltersList = []
      this.selectedRowKeys = []
      this.tableLoading = true
      this.$http.get('/trialProd/queryDocFileTrial', {
        params: {
          docFileId,
          projectId,
          month
        }
      }).then(res => {
        this.tableList = res.data.list
        this.content.activeIds = this.selectedRowKeys = this.tableList.filter(item => item.selected === 1).map(item => item.id)
        this.content.inactiveIds = this.tableList.map(item => item.id).filter(id => this.selectedRowKeys.indexOf(id) === -1)
        this.hasTrialProduct = this.tableList ? Boolean(this.tableList.find(a => a.trialProduct && a.trialProduct.length)) : false
        const tempDeptNameFiltersList = this.tableList.map(item => item.deptName)
        this.deptNameFiltersList = [...new Set(tempDeptNameFiltersList)].map(item => ({
          text: item,
          value: item
        }))
        const tempUnitFiltersList = this.tableList.map(item => item.unit)
        this.unitFiltersList = [...new Set(tempUnitFiltersList)].map(item => ({
          text: item,
          value: item
        }))
      }).finally(() => {
        this.tableLoading = false
      })
    },
    // handleRemove (record) {
    //   const docFileId = this.fileInfo.docId
    //   const projectId = this.project.id
    //   this.$http.post('/docFileTrial/delDocFileTrial', { pdocFileId: docFileId, trialId: record.id }).then(res => {
    //     const month = this.content.month ? this.content.month + '-01 00:00:00' : undefined
    //     this.queryTableList(docFileId, projectId, month)
    //   })
    // },
    onSelectChange (selectedRowKeys) {
      // this.content.trialProdIds = selectedRowKeys
      this.content.activeIds = this.selectedRowKeys = selectedRowKeys
      this.content.inactiveIds = this.tableList.map(item => item.id).filter(id => this.selectedRowKeys.indexOf(id) === -1)
    },
    onChange (value) {
      if (value) {
        this.queryTableList(this.fileInfo.docId, this.project.id, value + '-01 00:00:00')
        this.fileDate = moment(value + '-1').startOf('month')
        if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
          this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
        }
        this.hsaFileDataNull = false
      }
    },
    handleChange (pagination, filters, sorter) {
      this.filteredInfo = filters
    }
  }
}
</script>

<style lang="less" scoped>
custom-form-item {
  margin-bottom: 0px;
}

#wrap_box /deep/ .ant-table {
  width: 100%;
}
</style>
