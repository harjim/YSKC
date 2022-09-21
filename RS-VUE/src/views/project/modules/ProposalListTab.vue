<!--
 * @Author: your name
 * @Date: 2022-03-23 15:44:29
 * @LastEditors: lzh
 * @LastEditTime: 2022-04-19 15:57:15
 * @Description: 提案管理-列表
 * @FilePath: \RS-VUE\src\views\project\modules\ProposalListTab.vue
-->
<template>
  <div v-if="auth.search">
    <div class="form_wrap">
      <a-form layout="inline">
        <a-form-item label="项目名称">
          <a-input placeholder="请输入项目名称" v-model="queryParams.pname" allowClear></a-input>
        </a-form-item>
        <a-form-item label="研发类型">
          <a-select style="width:230px;" placeholder="请选择研发类型" v-model="queryParams.formula" allowClear>
            <a-select-option v-for="item in rdTypeOptions" :key="item.value" :value="item.value">{{ `${item.value}.${item.label}` }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="项目提出人">
          <a-input placeholder="请输入项目提出人" v-model="queryParams.initiator" allowClear></a-input>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="search(true)">查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <ystable
        ref="table"
        queryUrl="/proposalList/getList"
        :params="queryParams"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :toolbar="{ custom: true, zoom: true, refresh: true }"
        :checkbox-config="{ showHeader: showHeaderChk }"
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        @completed="completed"
      >
        <template #buttons>
          <a-button
            type="primary"
            @click="onAdd"
            style="margin-right:10px;"
            v-if="auth.add"
          >添加</a-button>
          <a-button
            type="primary"
            @click="onBatchDel"
            :disabled="selectedRows.length <= 0"
            v-if="auth.del"
          >删除</a-button>
        </template>
        <vxe-table-column type="checkbox" width="60" fixed="left"></vxe-table-column>
        <vxe-table-column field="pname" title="项目名称" width="200" fixed="left" remoteSort></vxe-table-column>
        <vxe-table-column field="formula" title="研发类型" width="200" remoteSort>
          <template #default="{row}">
            {{ row.formula | getLabel(rdTypeOptions) }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="initiator" title="项目提出人" width="120" remoteSort></vxe-table-column>
        <vxe-table-column field="deptName" title="立项部门" width="120" remoteSort></vxe-table-column>
        <vxe-table-column field="rdTitle" title="RD" width="120" remoteSort></vxe-table-column>
        <vxe-table-column field="involvedProduct" title="项目对应产品" width="180" remoteSort></vxe-table-column>
        <vxe-table-column field="proposalDate" title="提案日期" width="100">
          <template #default="{row}">
            {{ row.proposalDate | DayFormat }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="tBEDate" title="试制日期" width="180">
          <template #default="{row}">
            <span v-if="row.tBeginDate || row.tEndDate">{{ row.tBeginDate | DayFormat }}~{{ row.tEndDate | DayFormat }}</span>
            <span v-else>无</span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="BEDate" title="起止日期" width="180">
          <template #default="{row}">
            {{ row.beginDate | DayFormat }}~{{ row.endDate | DayFormat }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="createTime" title="创建日期" width="150"></vxe-table-column>
        <vxe-table-column title="操作" width="100">
          <template #default="{row}">
            <a @click="onEdit(row.id)" v-if="auth.edit">编辑</a>
            <a-divider type="vertical" v-if="auth.del"></a-divider>
            <a-popconfirm title="您确定要删除？" @confirm="handleDel([row.id])" v-if="auth.del">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
      <a-modal
        :title="title"
        :width="960"
        :visible="isVisibleModal"
        :afterClose="afterCloseModal"
        @cancel="isVisibleModal = false"
        @ok="handleSubmit"
        :confirmLoading="confirmLoading"
        destroyOnClose
        :bodyStyle="{ maxHeight: '70vh', overflow: 'auto' }"
      >
        <a-form :form="form" v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 20 } }">
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="项目名称" v-bind="subFieldCol">
                <a-input placeholder="请输入项目名称" v-decorator="['pname', { rules: [{ required: true, message: '请输入项目名称' }] }]"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="项目提出人" v-bind="subFieldCol">
                <a-input placeholder="请输入项目提出人" v-decorator="['initiator', { rules: [{ required: true, message: '请输入项目提出人' }] }]"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="研发类型" v-bind="subFieldCol">
                <a-select
                  placeholder="请选择研发类型"
                  v-decorator="['formula', { rules: [{ required: true, message: '请选择研发类型' }] }]"
                >
                  <a-select-option v-for="item in rdTypeOptions" :key="item.value" :value="item.value">{{ `${item.value}.${item.label}` }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="立项部门" v-bind="subFieldCol">
                <a-input placeholder="请输入立项部门" v-decorator="['deptName', { rules: [{ required: true, message: '请输入立项部门' }] }]"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <!-- <a-form-item label="起止日期" v-bind="subFieldCol">
                <a-range-picker :placeholder="['开始日期', '结束日期']" v-decorator="['BEDate', { rules: [{ required: true, message: '请选择起止日期' }] }]" @change="onBEDateChange" />
              </a-form-item> -->
              <a-form-item label="起止日期" :required="true" style="margin-bottom:0;" v-bind="subFieldCol">
                <a-form-item :style="{display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-date-picker
                    format="YYYY-MM-DD"
                    placeholder="开始日期"
                    v-decorator="['beginDate', {rules:[{required: true, message: '请选择开始日期'}]}]"
                    @change="onBEDateChange('beginDate')"
                  />
                </a-form-item>
                <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
                  -
                </span>
                <a-form-item :style="{display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-date-picker
                    :disabledDate="disabledEndDate"
                    format="YYYY-MM-DD"
                    placeholder="结束日期"
                    v-decorator="['endDate', { rules: [{required: true,message:'请输入结束日期'}] }]"
                    @change="onBEDateChange"
                  />
                </a-form-item>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <!-- <a-form-item label="试制时间" v-bind="subFieldCol">
                <a-range-picker :placeholder="['开始日期', '结束日期']" v-decorator="['tDate', { rules: [{ required: true, message: '请选择试制时间' }] }]" :disabled-date="setDisabledDate" :disabled="tBEDateDisabled" />
              </a-form-item> -->
              <a-form-item label="试制时间" style="margin-bottom:0;" v-bind="subFieldCol">
                <a-form-item :style="{display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-date-picker
                    format="YYYY-MM-DD"
                    placeholder="开始日期"
                    v-decorator="['tBeginDate', { rules:[{required: tDateRequired, message: '请选择开始日期'}], type: 'date'}]"
                    :disabledDate="disabledTBeginDate"
                    :disabled="tBEDateDisabled"
                    @change="onTDateChange"
                  />
                </a-form-item>
                <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
                  -
                </span>
                <a-form-item :style="{display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-date-picker
                    format="YYYY-MM-DD"
                    placeholder="结束日期"
                    v-decorator="['tEndDate', { rules: [{required: tDateRequired, message:'请输入结束日期'}], type: 'date' }]"
                    :disabledDate="disabledTEndDate"
                    :disabled="tBEDateDisabled"
                    @change="onTDateChange"
                  />
                </a-form-item>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="提案日期" v-bind="subFieldCol">
                <a-date-picker style="width: 100%" placeholder="请选择提案日期" v-decorator="['proposalDate', { rules: [{ required: true, message: '请选择提案日期' }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="项目对应产品" v-bind="subFieldCol">
                <a-input placeholder="请输入项目对应产品" v-decorator="['involvedProduct', { rules: [{ required: false }] }]"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="关联RD">
                <a-select placeholder="请选择关联RD" v-decorator="['projectId', { rules: [{ required: false }] }]" allowClear>
                  <a-select-option v-for="item in rdList" :key="item.id" :value="item.id">{{ `${item.rdTitle}-${item.pname}` }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="项目人员名单" :extra="onComputeTextarea('members', 500)">
                <a-textarea placeholder="请输入项目人员名单" v-decorator="['members', { rules: [{ required: false }] }]" :rows="4"></a-textarea>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="主要设备仪器" :extra="onComputeTextarea('equipments', 500)">
                <a-textarea placeholder="请输入主要设备仪器" v-decorator="['equipments', { rules: [{ required: false }] }]" :rows="4"></a-textarea>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="现状分析" :extra="onComputeTextarea('situation', 2000)">
                <a-textarea placeholder="请输入现状分析" v-decorator="['situation', { rules: [{ required: true, message: '请输入现状分析' }] }]" :rows="4"></a-textarea>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="主要研究内容">
                <a-textarea placeholder="请输入主要研究内容" v-decorator="['research', { rules: [{ required: true, message: '请输入主要研究内容' }] }]" :rows="4"></a-textarea>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="创新点">
                <a-textarea placeholder="请输入创新点" v-decorator="['innovation', { rules: [{ required: true, message: '请输入创新点' }] }]" :rows="4"></a-textarea>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="实施前后对比" :extra="onComputeTextarea('comparison', 800)">
                <a-textarea placeholder="请输入实施前后对比" v-decorator="['comparison', { rules: [{ required: false }] }]" :rows="4"></a-textarea>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="进度计划" :extra="onComputeTextarea('progressPlan', 800)">
                <a-textarea placeholder="请输入进度计划" v-decorator="['progressPlan', { rules: [{ required: false }] }]" :rows="4"></a-textarea>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="技术指标及经济效益" :extra="onComputeTextarea('target', 800)">
                <a-textarea placeholder="请输入技术指标及经济效益" v-decorator="['target', { rules: [{ required: true, message: '请输入技术指标及经济效益' }] }]" :rows="4"></a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-modal>
    </a-spin>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { getAuth } from '@/utils/util'
import { mapState } from 'vuex'
import moment from 'moment'
export default {
  name: 'ProposalManagement',
  components: {
    ystable
  },
  data () {
    return {
      spinning: false,
      queryParams: {},
      auth: {},
      selectedRows: [],
      showHeaderChk: true,
      // 弹窗
      confirmLoading: false,
      updateId: undefined,
      form: this.$form.createForm(this),
      isVisibleModal: false,
      title: undefined,
      subFieldCol: { labelCol: { span: 8 }, wrapperCol: { span: 16 } },
      hasRdList: false,
      rdList: [],
      tBEDateDisabled: true,
      tDateRequired: false
    }
  },
  computed: {
    ...mapState({
      rdTypeOptions: state => state.enums.rdTypeOptions
    })
  },
  created () {
    const keys = ['search', 'add', 'edit', 'del']
    this.auth = getAuth('project:proposalList:', keys)
  },
  methods: {
    onAdd () {
      this.title = '添加提案管理'
      this.isVisibleModal = true
      this.tBEDateDisabled = true
      this.getRDList()
      this.$nextTick(() => {
        this.form.setFieldsValue({
          formula: 10,
          proposalDate: moment()
        })
      })
    },
    onEdit (id) {
      this.updateId = id
      this.tBEDateDisabled = false
      this.getRDList()
      this.$http.get('/proposalList/getInfo', {
        params: {
          id: id
        }
      }).then(res => {
        if (res.success && res.data) {
          this.isVisibleModal = true
          this.$nextTick(() => {
            const record = res.data
            this.title = `编辑【${record.pname}】`
            const formData = {}
            Object.keys(this.form.getFieldsValue()).forEach((key) => {
              formData[key] = record[key]
            })
            formData.beginDate = moment(record.beginDate)
            formData.endDate = moment(record.endDate)
            formData.tBeginDate = record.tBeginDate ? moment(record.tBeginDate) : undefined
            formData.tEndDate = record.tEndDate ? moment(record.tEndDate) : undefined
            formData.proposalDate = moment(record.proposalDate)
            formData.projectId = formData.projectId || undefined
            this.form.setFieldsValue(formData)
          })
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      })
    },
    onBatchDel () {
      this.$confirm({
        title: '您确定要删除选中项？',
        onOk: () => {
          this.handleDel(this.selectedRows.map(item => item.id))
        }
      })
    },
    handleDel (ids) {
      this.confirmLoading = true
      this.$http.post('/proposalList/del', { ids: ids })
        .then(data => {
          if (data) {
            this.search()
            this.$message.success('操作成功')
          }
        })
        .catch(error => {
          this.message.error(error.message || '操作失败')
        })
        .finally(() => {
          this.confirmLoading = false
        })
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    selectCheckBoxChange ({ records }) {
      this.selectedRows = records
    },
    completed ({ data }) {
      if (data.data && data.data.length) {
        this.showHeaderChk = true
      } else {
        this.showHeaderChk = false
      }
    },
    afterCloseModal () {
      this.isVisibleModal = false
      this.updateId = undefined
      this.form.resetFields()
    },
    getRDList () {
      if (this.hasRdList) {
        return
      }
      this.$http.get('/project/getSimpleList', { params: { sign: 0 } }).then(res => {
        if (res.success) {
          this.rdList = res.data || []
          this.hasRdList = true
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      })
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (errors) return
        const params = { ...values }
        params.tBeginDate = values.tBeginDate && values.tBeginDate.startOf('day')
        params.tEndDate = values.tEndDate && values.tEndDate.startOf('day')
        params.id = this.updateId
        params.beginDate = values.beginDate.startOf('day')
        params.endDate = values.endDate.startOf('day')
        params.proposalDate = values.proposalDate.startOf('day')
        delete params.BEDate
        delete params.tDate
        this.confirmLoading = true
        this.$http.post('/proposalList/save', params)
          .then(data => {
            if (data.success) {
              this.$message.success('操作成功')
              this.isVisibleModal = false
              this.search(true)
            } else {
              this.$message.error(data.errorMessage || '操作失败')
            }
          })
          .finally(() => {
            this.confirmLoading = false
          })
      })
    },
    // 计算文本域的个数
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    },
    onBEDateChange (fieldName) {
      if (fieldName === 'beginDate') {
        this.form.setFieldsValue({ endDate: undefined })
      }
      this.form.setFieldsValue({ tBeginDate: undefined, tEndDate: undefined })
      this.$nextTick(() => {
        const beginDate = this.form.getFieldValue('beginDate')
        const endDate = this.form.getFieldValue('endDate')
        if (beginDate && endDate) {
          this.tBEDateDisabled = false
        } else {
          this.tBEDateDisabled = true
        }
      })
    },
    onTDateChange () {
      this.form.setFieldsValue({ tEndDate: undefined })
      this.$nextTick(() => {
        const tBeginDate = this.form.getFieldValue('tBeginDate')
        const tEndDate = this.form.getFieldValue('tEndDate')
        if (Boolean(tBeginDate) ^ Boolean(tEndDate)) {
          this.tDateRequired = true
        } else {
          this.tDateRequired = false
        }
        this.$nextTick(() => {
          this.form.validateFields(['tBeginDate', 'tEndDate'], { force: true })
        })
      })
    },
    setDisabledDate (current) {
      const beginDate = this.form.getFieldValue('beginDate')
      const endDate = this.form.getFieldValue('endDate')
      if (beginDate && endDate) {
        return current && (current < beginDate.startOf('day') || current > endDate.endOf('day'))
      } else {
        return true
      }
    },
    // 结束日期限制
    disabledEndDate (currentDate) {
      const beginDate = this.form.getFieldValue('beginDate')
      return currentDate < (beginDate && beginDate.endOf('day'))
    },
    // 试剂开始日期限制
    disabledTBeginDate (currentDate) {
      const { beginDate, endDate } = this.form.getFieldsValue(['beginDate', 'endDate'])
      return !(currentDate > beginDate.startOf('day') && currentDate < endDate.endOf('day'))
    },
    // 试剂结束日期限制
    disabledTEndDate (currentDate) {
      const { beginDate, endDate, tBeginDate } = this.form.getFieldsValue(['beginDate', 'endDate', 'tBeginDate'])
      return !(currentDate > beginDate.startOf('day') && currentDate < endDate.endOf('day') && currentDate > (tBeginDate && tBeginDate.startOf('day')))
    }
  }
}
</script>
