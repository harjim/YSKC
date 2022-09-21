<template>
  <div>
    <TwoColumnsDrawer
      :visible="visible"
      :title="title"
      :spinning="spinning"
      :showBtn="canEdit"
      @close="close"
      @add="handleSubmit(editing ? 'checkPayment/editCheckPayment' : 'checkPayment/addCheckPayment')"
      @submit="handleSubmit('checkPayment/submit')"
    >
      <template #node>
        <a-steps :current="currentNode" direction="vertical" size="small">
          <a-step v-for="(item, index) in stepList" :key="item.value">
            <template #title>
              <span :style="{ fontWeight: item.value === currentNode ? '700' : 'normal' }">
                <div>{{ item.label }}</div>
                <a-badge
                  v-if="item.value === currentNode"
                  :color="statusColor[row.status === undefined ? 5 : row.status]"
                  :text="getStatusName(row.status)"
                />
              </span>
            </template>
            <template #description v-if="item.createTime">
              <ellipsis
                v-if="item.remark && index !== 0"
                style="padding-top: 8px; font-size: 14px"
                :length="50"
                :tooltip="true"
              >
                {{ item.remark }}
              </ellipsis>
              <div class="create-time">{{ item.userName }} {{ item.createTime }}</div>
            </template>
          </a-step>
        </a-steps>
      </template>

      <template #content>
        <a-form :form="form" class="form-box" :hideRequiredMark="!canEdit">
          <a-row>
            <a-col :span="12">
              <a-form-item label="客户名称">
                <a-select
                  v-if="canEdit"
                  show-search
                  allowClear
                  v-decorator="[
                    'customerId',
                    { rules: [{ required: true, message: '请输入客户名称' }], initialValue: row.customerId }
                  ]"
                  placeholder="请输入客户名称"
                  style="width: 330px"
                  :default-active-first-option="false"
                  :show-arrow="false"
                  :filter-option="false"
                  :not-found-content="fetching ? undefined : null"
                  @search="delayedCompanyNameChange"
                >
                  <a-spin v-if="fetching" slot="notFoundContent" size="small" />
                  <a-select-option v-for="customer in customerList" :key="customer.id">
                    {{ customer.title }}
                  </a-select-option>
                </a-select>
                <span v-else>{{ row.companyName }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="年份">
                <YearSelect
                  v-if="canEdit"
                  v-decorator="['year', { rules: [{ required: true, message: '请选择年份' }], initialValue: row.year }]"
                  style="width: 330px"
                  placeholder="请选择年份"
                />
                <span v-else>{{ row.year }}</span>
              </a-form-item>
            </a-col>
          </a-row>

          <a-row>
            <a-col :span="12">
              <a-form-item label="业务员">
                <SearchSelect
                  v-if="canEdit"
                  v-decorator="[
                    'ownerId',
                    {
                      rules: [{ required: true, message: '请输入业务员' }],
                      initialValue: row.ownerId ? { id: row.ownerId, realName: row.ownerName } : undefined
                    }
                  ]"
                  style="width: 330px"
                  url="/user/userForSelect"
                  searchField="realName"
                  sTitle="realName"
                  :multiple="false"
                  placeholder="请输入业务员"
                />
                <span v-else>{{ row.ownerName }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="所属部门">
                <DeptOnlySelect
                  v-if="canEdit"
                  v-decorator="[
                    'deptId',
                    { rules: [{ required: true, message: '请选择所属部门' }], initialValue: row.deptId }
                  ]"
                  style="width: 330px"
                  placeholder="请选择所属部门"
                />
                <span v-else>{{ row.deptName }}</span>
              </a-form-item>
            </a-col>
          </a-row>

          <a-row>
            <a-col :span="12">
              <a-form-item label="技术人员">
                <SearchSelect
                  v-if="canEdit"
                  v-decorator="[
                    'techId',
                    {
                      rules: [{ required: true, message: '请输入技术人员' }],
                      initialValue: row.techId ? { id: row.techId, realName: row.techName } : undefined
                    }
                  ]"
                  style="width: 330px"
                  url="/user/userForSelect"
                  searchField="realName"
                  sTitle="realName"
                  :multiple="false"
                  placeholder="请输入技术人员"
                />
                <span v-else>{{ row.techName }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="财务经理">
                <SearchSelect
                  v-if="canEdit"
                  v-decorator="[
                    'finaManagerId',
                    {
                      rules: [{ required: true, message: '请输入财务经理' }],
                      initialValue: row.finaManagerId ? { id: row.finaManagerId, realName: row.finaManagerName } : undefined
                    }
                  ]"
                  style="width: 330px"
                  url="/user/userForSelect"
                  searchField="realName"
                  sTitle="realName"
                  :multiple="false"
                  placeholder="请输入财务经理"
                />
                <span v-else>{{ row.finaManagerName }}</span>
              </a-form-item>
            </a-col>
          </a-row>

          <a-row>
            <a-col :span="12">
              <a-form-item label="分公司总经理">
                <SearchSelect
                  v-if="canEdit"
                  v-decorator="[
                    'ownerMangerId',
                    {
                      rules: [{ required: true, message: '请输入分公司总经理' }],
                      initialValue: row.ownerMangerId ? { id: row.ownerMangerId, realName: row.ownerMangerName } : undefined
                    }
                  ]"
                  style="width: 330px"
                  url="/user/userForSelect"
                  searchField="realName"
                  sTitle="realName"
                  :multiple="false"
                  placeholder="请输入分公司总经理"
                />
                <span v-else>{{ row.ownerMangerName }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="财务总监">
                <SearchSelect
                  v-if="canEdit"
                  v-decorator="[
                    'finaDirectorId',
                    {
                      rules: [{ required: true, message: '请输入财务总监' }],
                      initialValue: row.finaDirectorId ? { id: row.finaDirectorId, realName: row.finaDirectorName } : undefined
                    }
                  ]"
                  style="width: 330px"
                  url="/user/userForSelect"
                  searchField="realName"
                  sTitle="realName"
                  :multiple="false"
                  placeholder="请输入财务总监"
                />
                <span v-else>{{ row.finaDirectorName }}</span>
              </a-form-item>
            </a-col>
          </a-row>

          <a-row>
            <a-col :span="12">
              <a-form-item label="查新日期">
                <a-date-picker
                  v-if="canEdit"
                  v-decorator="[
                    'checkDate',
                    {
                      rules: [{ required: true, message: '请输入查新日期' }],
                      initialValue: row.checkDate ? moment(row.checkDate) : null
                    }
                  ]"
                  style="width: 330px"
                  placeholder="年-月-日"
                />
                <span v-else>{{ row.checkDate }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="查新机构">
                <a-select
                  v-if="canEdit"
                  show-search
                  allowClear
                  v-decorator="[
                    'checkInstId',
                    { rules: [{ required: true, message: '请选择查新机构' }], initialValue: row.checkInstId }
                  ]"
                  placeholder="请输入查新机构"
                  style="width: 330px"
                  :default-active-first-option="false"
                  :show-arrow="false"
                  :filter-option="false"
                  :not-found-content="fetching ? undefined : null"
                  @search="delayedCheckInstChange"
                >
                  <a-spin v-if="fetching" slot="notFoundContent" size="small" />
                  <a-select-option v-for="checkInst in checkInstList" :key="checkInst.id">
                    {{ checkInst.checkInstName }}
                  </a-select-option>
                </a-select>
                <span v-else>{{ row.checkInstName }}</span>
              </a-form-item>
            </a-col>
          </a-row>

          <div>
            <a-form-item label="查新项目">
              <vxe-table
                ref="list"
                auto-resize
                resizable
                stripe
                keep-source
                row-key
                :max-height="244"
                :edit-rules="canEdit ? validRules : {}"
                :edit-config="{ trigger: 'manual', mode: 'row', autoClear: false, showIcon: false }"
                show-overflow="tooltip"
                :data="list"
              >
                <vxe-table-column type="seq" width="60" fixed="left" title="序号" align="center" />
                <vxe-table-column title="项目编号" field="rdTitle" width="260" :edit-render="{ immediate: true }">
                  <template v-slot="{ row, $rowIndex }">
                    <a-form-item v-if="canEdit">
                      <a-input
                        v-decorator="[
                          `list[${$rowIndex}].rdTitle`,
                          { rules: [{ required: true, message: '请输入项目编号' }] }
                        ]"
                        style="width: 240px"
                        placeholder="请输入项目编号"
                      />
                    </a-form-item>
                    <span v-else>{{ row.rdTitle }}</span>
                  </template>
                </vxe-table-column>
                <vxe-table-column title="项目名称" field="pname" width="430" :edit-render="{ immediate: true }">
                  <template v-slot="{ row, $rowIndex }">
                    <a-form-item v-if="canEdit">
                      <a-input
                        v-decorator="[
                          `list[${$rowIndex}].pname`,
                          { rules: [{ required: true, message: '请输入项目名称' }] }
                        ]"
                        style="width: 410px"
                        placeholder="请输入项目名称"
                      />
                    </a-form-item>
                    <span v-else>{{ row.pname }}</span>
                  </template>
                </vxe-table-column>
                <vxe-table-column v-if="canEdit" title="操作" width="60" align="center">
                  <template v-slot="{ row, $rowIndex }">
                    <template>
                      <a-popconfirm
                        title="是否删除该项目记录?"
                        :autoAdjustOverflow="false"
                        placement="topRight"
                        @confirm="delListItem($rowIndex)"
                      >
                        <a>移除</a>
                      </a-popconfirm>
                    </template>
                  </template>
                </vxe-table-column>
              </vxe-table>
            </a-form-item>
            <div class="form-box__table-btn" v-if="canEdit">
              <a-button block type="dashed" icon="plus" @click="addListItem">添加</a-button>
              <a-button
                block
                :disabled="!form.getFieldValue('customerId') || !form.getFieldValue('year')"
                type="dashed"
                @click="$refs.QuickAddCheckDrawer.open(form.getFieldsValue(['customerId', 'year']))"
              >
                引入项目
              </a-button>
            </div>
          </div>

          <a-row>
            <a-col :span="12">
              <a-form-item label="查新单价">
                <a-input-number
                  v-if="canEdit"
                  v-decorator="[
                    'unitPrice',
                    { rules: [{ required: true, message: '请输入查新单价' }], initialValue: row.unitPrice }
                  ]"
                  style="width: 330px"
                  placeholder="请输入查新单价"
                />
                <span v-else>{{ row.unitPrice }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="总价">
                <a-input-number
                  v-if="canEdit"
                  v-decorator="['totalAmount', { initialValue: row.totalAmount }]"
                  disabled
                  style="width: 330px"
                  placeholder="0"
                />
                <span v-else>{{ row.totalAmount }}</span>
              </a-form-item>
            </a-col>
          </a-row>

          <a-row>
            <a-col :span="12">
              <a-form-item label="查新账号">
                <a-input
                  v-if="canEdit"
                  v-decorator="['checkUsername', { initialValue: row.checkUsername }]"
                  style="width: 330px"
                  placeholder="请输入查新账号"
                />
                <span v-else>{{ row.checkUsername }}</span>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="查新密码">
                <a-input
                  v-if="canEdit"
                  v-decorator="['checkPassword', { initialValue: row.checkPassword }]"
                  style="width: 330px"
                  placeholder="请输入查新密码"
                />
                <span v-else>{{ row.checkPassword }}</span>
              </a-form-item>
            </a-col>
          </a-row>

          <a-form-item label="备注">
            <a-textarea
              v-if="canEdit"
              v-decorator="['remark', { initialValue: row.remark }]"
              style="width: 810px"
              :max-length="200"
              :rows="3"
              placeholder="请输入"
            />
            <p v-else v-html="row.remark || '-'" style="max-width: 810px"></p>
          </a-form-item>
        </a-form>

        <template v-if="row.status === 1 || (currentNode === 4 && row.hasPermission)">
          <a-divider orientation="left">付款信息</a-divider>
          <a-form :form="auditForm" class="audit-form-box" :hideRequiredMark="!row.hasPermission">
            <a-row>
              <a-col :span="12">
                <a-form-item label="付款方式">
                  <span v-if="row.status === 1">{{ getPayTypeTitle(row.payType) }}</span>
                  <a-select
                    v-else
                    v-decorator="[
                      'payType',
                      { rules: [{ required: true, message: '请选择付款方式' }], initialValue: row.payType }
                    ]"
                    style="width: 330px"
                    placeholder="请选择付款方式"
                  >
                    <a-select-option v-for="item in dictionary" :key="item.key">{{ item.value }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="附件">
                  <div v-if="row.status === 1" style="display: flex; column-gap: 12px; align-items: center">
                    <span v-for="item in fileList" :key="item.uid">
                      <a title="点击下载" @click="downloadFile(item)">{{ item.name }}</a>
                      <a-tooltip style="cursor: pointer" placement="top" @click="onPreview(item)">
                        <template slot="title">
                          <span>预览</span>
                        </template>
                        <a-icon type="eye" style="margin-left: 4px" />
                      </a-tooltip>
                    </span>
                  </div>
                  <a-upload
                    v-else
                    v-decorator="['paymentFile', { rules: [{ required: true, message: '请选择上传附件' }] }]"
                    :fileList="fileList"
                    accept=".docx"
                    @change="UploadHandleChange"
                    :before-upload="beforeUpload"
                    @preview="onPreview"
                    @download="downloadFile"
                  >
                    <a-button icon="upload">上传附件</a-button>
                  </a-upload>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </template>
      </template>

      <template v-if="showAuditCard" #audit>
        <Audit
          ref="audit"
          :hasPermission="row.hasPermission"
          :instanceId="row.instanceId"
          :columns="logCol"
          url="/checkPayment/audit"
          @check="audit"
          @refresh="edit"
        />
      </template>
    </TwoColumnsDrawer>
    <QuickAddCheckDrawer ref="QuickAddCheckDrawer" @select="quickSelect" />
  </div>
</template>

<script>
import TwoColumnsDrawer from '@/components/Layout/TwoColumnsDrawer.vue'
import { getStatusName, statusColor, isEditStatus } from '@/utils/processDoc/auditStatus'
import _ from 'lodash'
import YearSelect from '@/components/Selects/YearSelect.vue'
import SearchSelect from '@/components/Selects/SearchSelect.vue'
import QuickAddCheckDrawer from './QuickAddCheckDrawer.vue'
import moment from 'moment'
import Audit from '@/components/Audit/Audit.vue'
import Ellipsis from '@/components/Ellipsis/Ellipsis'
import DeptOnlySelect from '@/components/Selects/DeptOnlySelect.vue'

const processType = [
  { value: 0, label: '业务员' },
  { value: 1, label: '分公司总经理' },
  { value: 2, label: '财务经理' },
  { value: 3, label: '财务总监' },
  { value: 4, label: '出纳支付' }
]

export default {
  name: 'CheckDrawer',
  components: { TwoColumnsDrawer, YearSelect, SearchSelect, QuickAddCheckDrawer, Audit, Ellipsis, DeptOnlySelect },
  props: {
    preview: {
      type: Function,
      required: true
    }
  },
  data() {
    const logCol = [
      { type: 'seq', width: '60', align: 'center' },
      { title: '节点名称', field: 'nodeName', width: '140' },
      { title: '审批状态', field: 'status', width: '80', formatter: ({ cellValue }) => getStatusName(cellValue) },
      { title: '参与者', field: 'auditor', width: '100' },
      { title: '审批意见', field: 'suggestion', minWidth: '100' },
      { title: '审批时间', field: 'createTime', width: '160' }
    ]
    return {
      logCol,
      statusColor,
      visible: false,
      title: '',
      currentNode: 0,
      row: {},
      stepList: [],
      list: [],
      spinning: false,
      editing: false,
      customerList: [],
      checkInstList: [],
      fetching: false,
      dictionary: [],
      fileList: [],
      validRules: {
        rdTitle: [{ required: true }],
        pname: [{ required: true }]
      }
    }
  },
  computed: {
    canEdit() {
      return this.$auth('innovation:checkPayment:edit') && this.isEditStatus(this.row.status)
    },
    showAuditCard() {
      return (
        this.row.instanceId &&
        (this.$auth('innovation:checkPayment:audit') || this.$auth('innovation:checkPayment:log'))
      )
    }
  },
  beforeCreate() {
    const defaultFn = () => () => ({})
    this.delayedCompanyNameChange = _.debounce(this.delayedCompanyNameChange || defaultFn, 500)
    this.delayedCheckInstChange = _.debounce(this.delayedCheckInstChange || defaultFn, 500)
    this.form = this.$form.createForm(this, {
      onValuesChange: (props, values) => {
        console.log(values)
        if (values.ownerId) {
          this.form.setFieldsValue({
            deptId: values.ownerId.deptId
          })
        }
        if (values.list || values.unitPrice) {
          let a, b
          if (values.list) {
            a = this.list.length
            b = props.form.getFieldValue('unitPrice')
          }
          if (values.unitPrice) {
            a = values.unitPrice
            b = props.form.getFieldValue('list') ? this.list.length : 0
          }
          this.form.setFieldsValue({
            totalAmount: a * b
          })
        }
      }
    })
    this.auditForm = this.$form.createForm(this)
  },
  methods: {
    moment,
    getStatusName,
    isEditStatus,
    add() {
      this.title = '添加'
      this.stepList = [...processType]
      this.visible = true
    },
    edit(id) {
      this.title = '编辑'
      this.editing = true
      this.spinning = true
      this.visible = true
      this.$http
        .get('checkPayment/getInfo', { params: { id } })
        .then(({ success, data, errorMessage }) => {
          if (success) {
            this.row = data
            this.initial(data)
          } else {
            this.$message.error(errorMessage)
          }
        })
        .finally(() => {
          this.spinning = false
        })
    },
    initial(row) {
      this.currentNode = row.nodeNumber
      this.stepList = [...processType].map((item) => {
        const i = { ...item }
        if (row.nodeList && row.nodeList.length) {
          if (row.nodeList[item.value]) {
            i.createTime = row.nodeList[i.value].createTime
            i.remark = row.nodeList[i.value].suggestion
            i.userName = row.nodeList[i.value].userName
          }
        }
        return i
      })
      this.customerList = [{ title: row.companyName, id: row.customerId }]
      this.checkInstList = [{ checkInstName: row.checkInstName, id: row.checkInstId }]
      this.fileList = row.paymentFile
        ? row.paymentFile.split(',').map((item) => {
            const pos = item.lastIndexOf('/')
            const fileName = item.substring(pos + 14)
            return {
              uid: fileName,
              name: fileName,
              status: 'done',
              url: item
            }
          })
        : []
      this.list = row.list
      for (let i = 0; i < this.list.length; i++) {
        const element = this.list[i]
        this.form.getFieldDecorator(`list[${i}].rdTitle`, {
          initialValue: element.rdTitle
        })
        this.form.getFieldDecorator(`list[${i}].pname`, {
          initialValue: element.pname
        })
      }
      this.$getDictionary(18).then((res) => {
        this.dictionary = res
      })
    },
    close() {
      this.form.resetFields()
      Object.assign(this.$data, this.$options.data())
      this.$emit('refresh')
    },
    delayedCompanyNameChange(customerName) {
      if (!customerName) return
      this.fetching = true
      this.$http
        .get('customer/getCustomerSelect', { params: { customerName } })
        .then(({ success, data, errorMessage }) => {
          if (success) {
            this.customerList = data
          } else {
            this.$message.error(errorMessage)
          }
        })
        .finally(() => {
          this.fetching = false
        })
    },
    delayedCheckInstChange(checkInstName) {
      if (!checkInstName) return
      this.fetching = true
      this.$http
        .get('checkPayment/getCheckInstList', { params: { checkInstName } })
        .then(({ success, data, errorMessage }) => {
          if (success) {
            this.checkInstList = data
          } else {
            this.$message.error(errorMessage)
          }
        })
        .finally(() => {
          this.fetching = false
        })
    },
    addListItem() {
      this.list = [...this.list, {}]
    },
    delListItem(i) {
      const list = this.form.getFieldValue('list')
      list.splice(i, 1)
      this.list = list
      const unitPrice = this.form.getFieldValue('unitPrice') || 0
      this.form.setFieldsValue({
        totalAmount: list.length * unitPrice
      })
      for (let i = 0; i < this.list.length; i++) {
        const element = this.list[i]
        this.form.getFieldDecorator(`list[${i}].rdTitle`, {
          initialValue: element.rdTitle
        })
        this.form.getFieldDecorator(`list[${i}].pname`, {
          initialValue: element.pname
        })
      }
    },
    quickSelect(arr) {
      const temp = _.map(arr, (item) => ({
        ..._.pick(item, ['pname', 'rdTitle'])
      }))
      this.list = _.uniqBy(_.concat(this.list, temp), 'rdTitle')
      this.$nextTick(() => {
        this.form.setFieldsValue({
          list: this.list
        })
      })
    },
    handleSubmit(url) {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.spinning = true
          const params = {
            ..._.pick(this.row, ['id']),
            ..._.pick(values, [
              'customerId',
              'deptId',
              'remark',
              'unitPrice',
              'year',
              'checkInstId',
              'checkUsername',
              'checkPassword'
            ]),
            checkDate: values.checkDate.format('YYYY-MM-DD'),
            finaDirectorId: values.finaDirectorId.id,
            finaManagerId: values.finaManagerId.id,
            techId: values.techId.id,
            ownerId: values.ownerId.id,
            ownerMangerId: values.ownerMangerId.id,
            list: _.map(values.list, (item) => ({ ..._.pick(item, ['rdTitle', 'pname', 'id']) }))
          }
          this.$http
            .post(url, params)
            .then(({ success, errorMessage }) => {
              if (success) {
                this.$message.success('操作成功')
                this.close()
              } else {
                this.$message.error(errorMessage)
              }
            })
            .catch((e) => {
              console.log(e)
            })
            .finally(() => {
              this.spinning = false
            })
        }
      })
    },
    audit(val) {
      if (this.currentNode === 4) {
        this.auditForm.validateFieldsAndScroll((e, v) => {
          if (!e) {
            this.$refs.audit.auditRequest({
              ...val,
              ...v,
              id: this.row.id,
              nodeNumber: this.currentNode,
              paymentFile: _.map(this.fileList, (item) => item.url).join(',')
            })
          }
        })
      } else {
        this.$refs.audit.auditRequest({ ...val, id: this.row.id, nodeNumber: this.currentNode })
      }
    },
    getPayTypeTitle(key) {
      for (let i = 0; i < this.dictionary.length; i++) {
        const el = this.dictionary[i]
        if (el.key === key) return el.value
      }
      return '-'
    },
    // 更新
    UploadHandleChange(file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
      }
    },
    // 上传
    beforeUpload(file) {
      // 是否超过限制大小
      if (!this.$checkFileSize(file, this.$message)) {
        return false
      }
      // 是否重复上传
      const files = this.fileList.map((item) => item.name)
      if (files.includes(file.name)) {
        this.$message.error('文件名重复')
        return false
      }

      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/checkPayment/')
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/document/upload', param, config).then(({ data, success, errorMessage }) => {
        if (success) {
          this.fileList = [
            {
              uid: data[0].fileName,
              name: data[0].fileName,
              status: 'done',
              url: data[0].filePath
            }
          ]
        } else {
          this.$message.error(errorMessage)
        }
      })
      return false
    },
    // 预览
    onPreview(file) {
      this.preview(file.url, file.name)
    },
    // 下载
    downloadFile(file) {
      this.$exportData(
        '/document/downloadFile',
        {
          fileName: file.name,
          filePath: file.url
        },
        file.name,
        this.$message
      )
    }
  }
}
</script>

<style lang="less" scoped>
.form-box {
  & .ant-form-item {
    display: flex;
    margin-bottom: 8px;
    & /deep/ .ant-form-item-label {
      width: 110px;
    }
  }

  & .ant-form-item-control-wrapper {
    flex: 1;
  }

  &__table-btn {
    width: 820px;
    margin: 12px 0;
    margin-left: auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
    column-gap: 16px;

    & button:first-child {
      flex: 7;
    }
    & button:last-child {
      flex: 3;
    }
  }
}

.audit-form-box {
  & .ant-form-item {
    display: flex;
    margin-bottom: 8px;
    & /deep/ .ant-form-item-label {
      width: 110px;
    }
  }

  & .ant-form-item-control-wrapper {
    flex: 1;
  }
}
</style>
