<template>
  <TwoColumnsDrawer
    :title="title"
    :visible="visible"
    :showBtn="canEdit"
    :edit="editing"
    :spinning="spinning"
    @close="close"
    @add="handleSubmit(editing ? 'contract/editContract' : 'contract/addContract')"
    @submit="handleSubmit('contract/submit')"
  >
    <template #node>
      <a-steps :current="currentNode" direction="vertical" size="small">
        <a-step v-for="(item, index) in stepList" :key="item.value">
          <template #title>
            <span :style="{ fontWeight: item.value === currentNode ? '700' : 'normal' }">
              <div>{{ item.label }}</div>
              <a-badge
                v-if="item.value === currentNode"
                :color="statusColor[status === undefined ? 5 : status]"
                :text="getStatusName(status)"
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
      <a-form :form="form" :label-col="{ span: 7 }" :wrapper-col="{ span: 16 }" :hideRequiredMark="!canEdit">
        <a-form-item label="客户名称" :label-col="{ span: 3 }" style="margin-left: -29px">
          <a-select
            v-if="canEdit"
            show-search
            allowClear
            v-decorator="[
              'customerId',
              { rules: [{ required: true, message: '请选择客户' }], initialValue: row.customerId }
            ]"
            placeholder="请输入客户名称"
            style="width: 830px"
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
          <span v-else>{{ customerList.length && customerList[0].title }}</span>
        </a-form-item>
        <a-row>
          <a-col :span="8">
            <a-form-item label="业务员">
              <search-select
                v-if="canEdit"
                v-decorator="[
                  'ownerId',
                  {
                    rules: [{ required: true, message: '请输入业务员' }],
                    initialValue: row.ownerId
                      ? { id: row.ownerId, realName: row.ownerName }
                      : { id: userInfo.id, realName: userInfo.realName }
                  }
                ]"
                style="width: 216px"
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="false"
                placeholder="请输入业务员"
              />
              <span v-else>{{ row.ownerName }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="谈单经理">
              <search-select
                v-if="canEdit"
                v-decorator="[
                  'businessId',
                  {
                    rules: [{ required: true, message: '请输入谈单经理' }],
                    initialValue: row.businessId ? { id: row.businessId, realName: row.businessName } : undefined
                  }
                ]"
                style="width: 216px"
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="false"
                placeholder="请输入谈单经理"
              />
              <span v-else>{{ row.businessName }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="所属部门">
              <DeptOnlySelect
                v-if="canEdit"
                v-decorator="[
                  'deptId',
                  {
                    rules: [{ required: true, message: '请选择所属部门' }],
                    initialValue: row.deptId || userInfo.deptIds[0]
                  }
                ]"
                style="width: 216px"
                placeholder="请选择所属部门"
              />
              <span v-else>{{ row.deptName }}</span>
            </a-form-item>
          </a-col>
        </a-row>

        <a-divider orientation="left">项目列表</a-divider>
        <div class="table-box">
          <vxe-grid
            ref="projectListTable"
            auto-resize
            resizable
            stripe
            row-key
            :data="projectList"
            :max-height="244"
            show-overflow="tooltip"
            :edit-rules="canEdit ? projectListValidRules : {}"
            :edit-config="{ trigger: 'manual', mode: 'row', autoClear: false, showIcon: false }"
          >
            <vxe-table-column type="seq" width="60" fixed="left" title="序号" align="center" />
            <vxe-table-column field="productId" title="项目类型" width="170" :edit-render="{ immediate: true }">
              <template v-slot="{ row, $rowIndex }">
                <a-form-item v-if="canEdit">
                  <a-select
                    v-decorator="[
                      `projectList[${$rowIndex}].productId`,
                      { rules: [{ required: true, message: '请输入项目类型' }] }
                    ]"
                    :disabled="!form.getFieldValue('customerId')"
                    allowClear
                    show-search
                    placeholder="请输入项目类型"
                    option-filter-prop="children"
                    style="width: 150px"
                    :filter-option="filterProductOption"
                    @select="(v) => changeProductId(v, $rowIndex)"
                  >
                    <a-select-option v-for="item in product" :key="item.id">
                      {{ item.productName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
                <span v-else>{{ row.productName }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="年份"
              field="years"
              :min-width="canEdit ? 260 : 120"
              align="center"
              :edit-render="{ immediate: true }"
            >
              <template v-slot="{ row, $rowIndex }">
                <span
                  v-if="canEdit"
                  style="display: flex; align-item: center; justify-content: space-around; width: 100%"
                >
                  <a-form-item>
                    <year-picker
                      v-decorator="[
                        `projectList[${$rowIndex}].beginYear`,
                        { rules: [{ required: true, message: '请选择开始年份' }] }
                      ]"
                      placeholder="开始年份"
                      style="width: 120px"
                      @change="(v) => changeBeginYear(v, $rowIndex)"
                    />
                  </a-form-item>
                  <span>-</span>
                  <a-form-item>
                    <year-picker
                      v-decorator="[
                        `projectList[${$rowIndex}].endYear`,
                        { rules: [{ required: true, message: '请选择结束年份' }] }
                      ]"
                      placeholder="结束年份"
                      style="width: 120px"
                      @change="(v) => changeEndYear(v, $rowIndex)"
                    />
                  </a-form-item>
                </span>
                <span v-else>{{ row.beginYear }}-{{ row.endYear }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="ratio" title="收费比例" width="100" :edit-render="{ immediate: true }">
              <template v-slot="{ row, $rowIndex }">
                <a-form-item v-if="canEdit">
                  <a-input-number
                    v-decorator="[
                      `projectList[${$rowIndex}].ratio`,
                      { rules: [{ required: true, message: '请输入收费比例' }] }
                    ]"
                    placeholder="收费比例"
                    :min="0.01"
                    :max="99.99"
                    :step="0.01"
                    style="width: 80px"
                    :formatter="(value) => (value ? `${value}%` : '')"
                    :parser="(value) => value.replace('%', '')"
                  />
                </a-form-item>
                <span v-else>{{ row.ratio }}%</span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="signCnt" width="100" title="签约次数" :edit-render="{ immediate: true }">
              <template v-slot="{ row, $rowIndex }" :edit-render="{ immediate: true }">
                <a-form-item v-if="canEdit">
                  <a-input-number
                    v-decorator="[
                      `projectList[${$rowIndex}].signCnt`,
                      { rules: [{ required: true, message: '请输入签约次数' }] }
                    ]"
                    :disabled="disabledSignCnt($rowIndex)"
                    placeholder="签约次数"
                    style="width: 80px"
                  />
                </a-form-item>
                <span v-else>{{ row.signCnt }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="remark" title="备注" min-width="160" :edit-render="{ immediate: true }">
              <template v-slot="{ row, $rowIndex }">
                <a-form-item v-if="canEdit">
                  <a-textarea
                    v-decorator="[`projectList[${$rowIndex}].remark`]"
                    placeholder="请输入"
                    :auto-size="{ minRows: 1, maxRows: 5 }"
                    style="width: 150px"
                  />
                </a-form-item>
                <span v-else v-html="row.remark || '-'"></span>
              </template>
            </vxe-table-column>
            <vxe-table-column v-if="canEdit" title="操作" width="60" align="center">
              <template v-slot="{ row, $rowIndex }">
                <a-popconfirm
                  title="是否删除该项目记录?"
                  :autoAdjustOverflow="false"
                  placement="topRight"
                  @confirm="delProjectListItem($rowIndex)"
                >
                  <a>删除</a>
                </a-popconfirm>
              </template>
            </vxe-table-column>
          </vxe-grid>
          <a-button v-if="canEdit" style="margin-top: 12px" block type="dashed" icon="plus" @click="addProjectListItem">
            添加
          </a-button>
        </div>

        <a-divider orientation="left">合作人员</a-divider>
        <div class="table-box">
          <vxe-grid
            ref="memberListTable"
            auto-resize
            resizable
            stripe
            keep-source
            row-key
            :data="memberList"
            :max-height="244"
            :edit-config="{ trigger: 'manual', mode: 'row', autoClear: false, showIcon: false }"
            show-overflow="tooltip"
          >
            <vxe-table-column type="seq" width="60" fixed="left" title="序号" align="center" />
            <vxe-table-column field="userId" title="姓名" width="160">
              <template v-slot="{ row, $rowIndex }">
                <a-form-item v-if="canEdit">
                  <search-select
                    v-decorator="[
                      `memberList[${$rowIndex}].userId`,
                      { rules: [{ required: true, message: '请输入合作人员' }] }
                    ]"
                    style="width: 130px"
                    url="/user/userForSelect"
                    searchField="realName"
                    sTitle="realName"
                    :multiple="false"
                    placeholder="请输入合作人员"
                    @change="(v) => changeUserId(v, $rowIndex)"
                  />
                </a-form-item>
                <span v-else>{{ row.userName }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="deptId" title="所属部门" width="160">
              <template v-slot="{ row, $rowIndex }">
                <a-form-item v-if="canEdit">
                  <DeptOnlySelect
                    v-decorator="[
                      `memberList[${$rowIndex}].deptId`,
                      { rules: [{ required: true, message: '请输入所属部门' }] }
                    ]"
                    style="width: 130px"
                    placeholder="请选择所属部门"
                  />
                </a-form-item>
                <span v-else>{{ row.deptName }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="ratio" title="贡献比" width="150">
              <template v-slot="{ row, $rowIndex }">
                <a-form-item v-if="canEdit">
                  <a-input-number
                    v-decorator="[
                      `memberList[${$rowIndex}].ratio`,
                      { rules: [{ required: true, message: '请输入贡献比' }] }
                    ]"
                    placeholder="贡献比"
                    :min="0.01"
                    :max="99.99"
                    :step="0.01"
                    style="width: 120px"
                    :formatter="(value) => (value ? `${value}%` : '')"
                    :parser="(value) => value.replace('%', '')"
                  />
                </a-form-item>
                <span v-else>{{ row.ratio }}%</span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="remark" title="备注" min-width="160">
              <template v-slot="{ row, $rowIndex }">
                <a-form-item v-if="canEdit">
                  <a-textarea
                    v-decorator="[`memberList[${$rowIndex}].remark`]"
                    placeholder="请输入"
                    :auto-size="{ minRows: 1, maxRows: 5 }"
                  />
                </a-form-item>
                <span v-else v-html="row.remark || '-'"></span>
              </template>
            </vxe-table-column>
            <vxe-table-column v-if="canEdit" title="操作" width="60" align="center">
              <template v-slot="{ row }">
                <template>
                  <a-popconfirm
                    title="是否删除该合作人员记录?"
                    :autoAdjustOverflow="false"
                    placement="topRight"
                    @confirm="delMemberListItem($rowIndex)"
                  >
                    <a>删除</a>
                  </a-popconfirm>
                </template>
              </template>
            </vxe-table-column>
          </vxe-grid>
          <a-button v-if="canEdit" style="margin-top: 12px" block type="dashed" icon="plus" @click="addMemberListItem">
            添加
          </a-button>
        </div>

        <a-divider orientation="left">合同信息</a-divider>
        <a-row>
          <a-col :span="8">
            <a-form-item label="签署日期">
              <a-date-picker
                v-if="canEdit"
                v-decorator="[
                  'signDate',
                  {
                    rules: [{ required: true, message: '请选择合同签署日期' }],
                    initialValue: row.signDate ? moment(row.signDate) : null
                  }
                ]"
                style="width: 216px"
                placeholder="年-月-日"
              />
              <span v-else>{{ row.signDate }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="生效日期">
              <a-date-picker
                v-if="canEdit"
                v-decorator="[
                  'effectDate',
                  {
                    rules: [{ required: true, message: '请选择合同生效日期' }],
                    initialValue: row.effectDate ? moment(row.effectDate) : null
                  }
                ]"
                style="width: 216px"
                placeholder="年-月-日"
                :disabled-date="disabledEffectDate"
              />
              <span v-else>{{ row.effectDate }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="终止日期">
              <a-date-picker
                v-if="canEdit"
                v-decorator="[
                  'closeDate',
                  {
                    rules: [{ required: true, message: '请选择合同终止日期' }],
                    initialValue: row.closeDate ? moment(row.closeDate) : null
                  }
                ]"
                style="width: 216px"
                placeholder="年-月-日"
                :disabled-date="disabledCloseDate"
              />
              <span v-else>{{ row.closeDate }}</span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row style="margin-left: -14px">
          <a-col :span="8">
            <a-form-item label="技术负责人" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <search-select
                v-if="canEdit"
                v-decorator="[
                  'techId',
                  {
                    rules: [{ required: true, message: '请输入技术负责人' }],
                    initialValue: row.techId ? { id: row.techId, realName: row.techName } : undefined
                  }
                ]"
                style="width: 216px"
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="false"
                placeholder="请输入技术负责人"
              />
              <span v-else>{{ row.techName }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="8">
            <a-form-item label="财务负责人" :label-col="{ span: 8 }" :wrapper-col="{ span: 16 }">
              <search-select
                v-if="canEdit"
                v-decorator="[
                  'finaId',
                  {
                    rules: [{ required: true, message: '请输入财务负责人' }],
                    initialValue: row.finaId ? { id: row.finaId, realName: row.finaName } : undefined
                  }
                ]"
                style="width: 216px"
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="false"
                placeholder="请输入财务负责人"
              />
              <span v-else>{{ row.finaName }}</span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-model-item label="公章类型" :label-col="{ span: 2 }" style="margin-left: 9px">
          <a-checkbox-group
            v-if="canEdit"
            :options="sealType"
            v-decorator="[
              'sealType',
              {
                rules: [{ required: true, message: '请选择公章类型' }],
                initialValue: row.sealType ? row.sealType.split(',').map((i) => +i) : []
              }
            ]"
          />
          <span v-else>{{
            row.sealType && row.sealType
              .split(',')
              .map((i) => sealType[i].label)
              .join(', ')
          }}</span>
        </a-form-model-item>
        <a-form-item label="附件" :label-col="{ span: 2 }" style="margin-left: 12px">
          <a-upload
            v-if="canEdit"
            v-decorator="[
              'filepath',
              { rules: [{ required: true, message: '请选择上传附件' }], initialValue: fileList }
            ]"
            :fileList="fileList"
            accept=".docx"
            @change="UploadHandleChange"
            :before-upload="beforeUpload"
            @preview="onPreview"
            @download="downloadFile"
          >
            <a-button icon="upload">上传附件</a-button>
          </a-upload>
          <div v-else style="display: flex; column-gap: 12px; align-items: center">
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
        </a-form-item>
      </a-form>

      <template v-if="status === 1 || (currentNode === 3 && logAudit.hasPermission)">
        <a-divider orientation="left">快递信息</a-divider>
        <a-form
          :form="auditForm"
          :hideRequiredMark="!logAudit.hasPermission"
          :label-col="{ span: 7 }"
          :wrapper-col="{ span: 16 }"
        >
          <a-row>
            <a-col :span="8">
              <a-form-item :label-col="{ span: 6 }" label="快递单号">
                <span v-if="status === 1">{{ row.expressNo }}</span>
                <a-input
                  v-else
                  v-decorator="[
                    'expressNo',
                    { rules: [{ required: true, message: '请输入快递单号' }], initialValue: row.expressNo }
                  ]"
                  style="width: 216px"
                  placeholder="请输入快递单号"
                />
              </a-form-item>
            </a-col>
            <a-col :span="16">
              <a-form-item :label-col="{ span: 3 }" label="寄送地址">
                <span v-if="status === 1">{{ row.expressAddress }}</span>
                <a-input
                  v-else
                  v-decorator="[
                    'expressAddress',
                    { rules: [{ required: true, message: '请输入寄送地址' }], initialValue: row.expressAddress }
                  ]"
                  style="width: 538px"
                  placeholder="请输入寄送地址"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </template>
    </template>

    <template v-if="showAuditCard" #audit>
      <Audit
        ref="audit"
        :hasPermission="logAudit.hasPermission"
        :instanceId="logAudit.instanceId"
        :columns="logAudit.logCol"
        @check="audit"
        @refresh="edit"
      />
    </template>
  </TwoColumnsDrawer>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import { getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
import Ellipsis from '@/components/Ellipsis/Ellipsis'
import SearchSelect from '@/components/Selects/SearchSelect'
import _ from 'lodash'
import YearSelect from '@/components/Selects/YearSelect'
import YearPicker from '@/components/Picker/YearPicker'
import RangeYearPicker from '@/components/Picker/RangeYearPicker'
import moment from 'moment'
import TwoColumnsDrawer from '@/components/Layout/TwoColumnsDrawer.vue'
import Audit from '@/components/Audit/Audit.vue'
import DeptOnlySelect from '@/components/Selects/DeptOnlySelect.vue'

export default {
  name: 'ContractDrawer',
  components: {
    RangeYearPicker,
    YearPicker,
    YearSelect,
    SearchSelect,
    Ellipsis,
    TwoColumnsDrawer,
    Audit,
    DeptOnlySelect
  },
  props: {
    preview: {
      type: Function,
      required: true
    }
  },
  data() {
    const defaultFn = () => () => ({})
    this.delayedCompanyNameChange = _.debounce(this.delayedCompanyNameChange || defaultFn, 500)
    const logCol = [
      { type: 'seq', width: '60', align: 'center' },
      { title: '节点名称', field: 'nodeName', width: '140' },
      { title: '审批状态', field: 'status', width: '80', formatter: ({ cellValue }) => getStatusName(cellValue) },
      { title: '参与者', field: 'auditor', width: '100' },
      { title: '审批意见', field: 'suggestion', minWidth: '100' },
      { title: '审批时间', field: 'createTime', width: '160' }
    ]
    return {
      statusColor,
      row: {},
      fetching: false,
      customerList: [],
      projectList: [],
      memberList: [],
      fileList: [],
      status: null,
      title: '',
      stepList: [],
      currentNode: 0,
      spinning: false,
      visible: false,
      editing: false,
      logAudit: {
        logCol,
        instanceId: undefined
      }
    }
  },
  created() {
    this.form = this.$form.createForm(this, {
      name: 'contract',
      onValuesChange: this.formValuesChange
    })
    this.auditForm = this.$form.createForm(this, { name: 'audit' })
  },
  computed: {
    ...mapState({
      contractProcessType: (state) => state.contract.contractProcessType,
      sealType: (state) => state.contract.sealType,
      product: (state) => state.contract.product,
      projectListValidRules: (state) => state.contract.projectListValidRules
    }),
    ...mapGetters(['userInfo']),
    ...mapGetters('contract', ['getIsEditStatus']),
    showAuditCard() {
      return this.logAudit.instanceId && (this.$auth('customer:contract:audit') || this.$auth('customer:contract:log'))
    },
    canEdit() {
      return this.$auth('customer:contract:edit') && this.getIsEditStatus(this.status)
    }
  },
  methods: {
    moment,
    getStatusName,
    add() {
      this.title = '添加合同'
      this.stepList = [...this.contractProcessType]
      this.visible = true
    },
    edit(id) {
      this.editing = true
      this.visible = true
      this.init(id)
    },
    init(id) {
      this.spinning = true
      this.$http
        .get('/contract/getInfo', { params: { id } })
        .then(({ success, data, errorMessage }) => {
          if (success) {
            this.row = data
            this.initDetailData(data)
          } else {
            this.$message.error(errorMessage || '获取数据失败')
          }
        })
        .finally(() => {
          this.spinning = false
        })
    },
    initDetailData(row) {
      this.status = row.status
      this.logAudit = {
        ...this.logAudit,
        instanceId: row.instanceId,
        hasPermission: row.hasPermission && !!this.$auth('customer:contract:audit')
      }
      this.title = `${this.getIsEditStatus(row.status) ? '编辑' : this.logAudit.hasPermission ? '审核' : '查看'}-${
        row.contractNo
      }`
      this.stepList = [...this.contractProcessType].map((item) => {
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
      this.currentNode = row.nodeNumber || 0

      this.memberList = _.map(row.memberList, (item) => ({
        ...item,
        userId: { id: item.userId, realName: item.userName, deptId: item.deptId }
      }))
      this.projectList = _.map(row.projectList, (item) => ({
        ...item,
        beginYear: item.beginYear + '',
        endYear: item.endYear + ''
      }))
      this.customerList = [{ id: row.customerId, title: row.companyName }]
      this.fileList = row.filepath.split(',').map((item) => {
        const pos = item.lastIndexOf('/')
        const fileName = item.substring(pos + 14)
        return {
          uid: fileName,
          name: fileName,
          status: 'done',
          url: item
        }
      })
      this.$nextTick(() => {
        for (let i = 0; i < this.projectList.length; i++) {
          const element = this.projectList[i]
          this.form.getFieldDecorator(`projectList[${i}].productId`, {
            initialValue: element.productId
          })
          this.form.getFieldDecorator(`projectList[${i}].beginYear`, {
            initialValue: element.beginYear
          })
          this.form.getFieldDecorator(`projectList[${i}].endYear`, {
            initialValue: element.endYear
          })
          this.form.getFieldDecorator(`projectList[${i}].ratio`, {
            initialValue: element.ratio
          })
          this.form.getFieldDecorator(`projectList[${i}].signCnt`, {
            initialValue: element.signCnt
          })
          this.form.getFieldDecorator(`projectList[${i}].remark`, {
            initialValue: element.remark
          })
        }
        for (let i = 0; i < this.memberList.length; i++) {
          const element = this.memberList[i]
          this.form.getFieldDecorator(`memberList[${i}].userId`, {
            initialValue: element.userId
          })
          this.form.getFieldDecorator(`memberList[${i}].deptId`, {
            initialValue: element.deptId
          })
          this.form.getFieldDecorator(`memberList[${i}].ratio`, {
            initialValue: element.ratio
          })
          this.form.getFieldDecorator(`memberList[${i}].remark`, {
            initialValue: element.remark
          })
        }
      })
    },
    close() {
      this.form.resetFields()
      this.$store.commit('contract/RESET_SIGN_CNT')
      Object.assign(this.$data, this.$options.data())
      this.$emit('refresh', false)
    },
    formValuesChange(props, values) {
      if (values.ownerId) {
        this.form.setFieldsValue({ deptId: values.ownerId.deptId })
      }
    },
    delayedCompanyNameChange(customerName) {
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
    filterProductOption(input, option) {
      return option.componentOptions.children[0].text.indexOf(input.toLowerCase()) >= 0
    },
    // 更新
    UploadHandleChange(file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
      }
    },
    // 上传
    beforeUpload(file, key) {
      // 是否超过限制大小
      if (!this.$checkFileSize(file, this.$message)) {
        return false
      }
      // 是否是指定类型
      const fileSuffix = file.name.substring(file.name.lastIndexOf('.') + 1)
      const whiteList = ['wps', 'doc', 'docx', 'wpt', 'dot']
      if (!whiteList.includes(fileSuffix)) {
        this.$message.error('请上传word文档')
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
      param.append('dir', '/contract/')
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
    },
    // 添加 & 提交
    handleSubmit(url) {
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          this.spinning = true
          const params = {
            ..._.pick(values, ['customerId', 'deptId', 'sealType', 'remark']),
            ..._.pick(this.row, ['id']),
            businessId: values.businessId.id,
            finaId: values.finaId.id,
            ownerId: values.ownerId.id,
            techId: values.techId.id,
            signDate: values.signDate.format('YYYY-MM-DD'),
            effectDate: values.effectDate.format('YYYY-MM-DD'),
            closeDate: values.closeDate.format('YYYY-MM-DD'),
            projectList: _.map(values.projectList, (item, idx) => ({
              ..._.pick(item, ['productId', 'beginYear', 'endYear', 'ratio', 'signCnt', 'remark', 'id']),
              id: _.get(this.row.projectList, `[${idx}].id`)
            })),
            memberList: _.map(values.memberList, (item, idx) => ({
              ..._.pick(item, ['deptId', 'ratio', 'remark', 'id']),
              userId: item.userId.id,
              id: _.get(this.row.memberList, `[${idx}].id`)
            })),
            filepath: this.fileList[0].url
          }
          this.checkProduct({
            projectList: params.projectList,
            customerId: params.customerId
          })
            .then((data) => {
              if (data) {
                this.$confirm({
                  title: '警告',
                  content: data,
                  onOk: () => {
                    this.submit(url, params)
                  }
                })
              } else {
                this.submit(url, params)
              }
            })
            .finally(() => {
              this.spinning = false
            })
        }
      })
    },
    checkProduct(params) {
      return this.$http.post('/contract/checkProduct', params).then(({ success, data, errorMessage }) => {
        if (success) {
          return data
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    submit(url, params) {
      this.$http.post(url, params).then(({ success, errorMessage }) => {
        if (success) {
          this.$message.success('操作成功')
          this.close()
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    // 审核
    audit(val) {
      if (this.currentNode === 3) {
        this.auditForm.validateFieldsAndScroll((e, v) => {
          if (!e) {
            this.$refs.audit.auditRequest({ ...val, ...v, id: this.row.id, nodeNumber: this.currentNode })
          }
        })
      } else {
        this.$refs.audit.auditRequest({ ...val, id: this.row.id, nodeNumber: this.currentNode })
      }
    },
    changeUserId(v, i) {
      this.$nextTick(() => {
        const memberList = this.form.getFieldValue('memberList')
        memberList[i].deptId = v.deptId
        this.form.setFieldsValue({
          memberList: memberList
        })
      })
    },
    // 添加删除人员列表
    addMemberListItem() {
      this.memberList = [...this.memberList, {}]
    },
    delMemberListItem(i) {
      const memberList = this.form.getFieldValue('memberList')
      memberList.splice(i, 1)
      this.memberList = memberList
      this.form.setFieldsValue({
        memberList: memberList
      })
    },
    // 添加删除项目列表
    addProjectListItem() {
      this.projectList = [...this.projectList, {}]
    },
    delProjectListItem(i) {
      const projectList = this.form.getFieldValue('projectList')
      projectList.splice(i, 1)
      this.projectList = projectList
      this.form.setFieldsValue({
        projectList: projectList
      })
    },
    // 项目类型输入
    changeProductId(v, i) {
      const customerId = this.form.getFieldValue('customerId')
      this.$http
        .get('contract/getProduct', { params: { customerId, productId: v } })
        .then(({ success, data, errorMessage }) => {
          if (success) {
            const projectList = this.form.getFieldValue('projectList')
            projectList[i].signCnt = data
            this.form.setFieldsValue({
              projectList: projectList
            })
          } else {
            this.$message.error(errorMessage)
          }
        })
    },
    // 年份改变
    changeBeginYear(v, i) {
      this.$nextTick(() => {
        const list = this.form.getFieldValue('projectList')
        if (!list[i].endYear) {
          list[i].endYear = v
        } else if (moment(list[i].endYear) < moment(v)) {
          list[i].beginYear = null
        }
        this.form.setFieldsValue({
          projectList: list
        })
      })
    },
    changeEndYear(v, i) {
      this.$nextTick(() => {
        const list = this.form.getFieldValue('projectList')
        if (!list[i].beginYear) {
          list[i].beginYear = v
        } else if (moment(list[i].beginYear) > moment(v)) {
          list[i].endYear = null
        }
        this.form.setFieldsValue({
          projectList: list
        })
      })
    },
    // 签约次数规则
    disabledSignCnt(i) {
      const customerId = this.form.getFieldValue('customerId')
      const projectList = this.form.getFieldValue('projectList')
      return !customerId || !(projectList && projectList[i] && projectList[i].productId)
    },
    // 生效时间与终止时间校验
    disabledEffectDate(c) {
      const closeDate = this.form.getFieldValue('closeDate')
      if (!closeDate) {
        return false
      }
      return c && c > closeDate
    },
    disabledCloseDate(c) {
      const effectDate = this.form.getFieldValue('effectDate')
      if (!effectDate) {
        return false
      }
      return c && c < effectDate
    }
  }
}
</script>

<style lang="less" scoped>
& /deep/ .ant-badge-status-text {
  font-size: 12px;
}

& /deep/ .ant-divider-inner-text {
  font-size: 14px;
}

& /deep/ .ant-divider-horizontal {
  margin: 8px 0;
}

& /deep/ .ant-form-item {
  margin-bottom: 8px;
}

& /deep/ .ant-upload-list-item {
  width: 820px;
  margin: 8px auto 0;
}

/deep/ .container-box__right {
  .ant-form-item-label {
    label {
      color: #99aec2;
    }
  }

  .ant-form-item-control-wrapper {
    color: #333;
  }

  // 不是编辑状态
  .status-not-edit {
    // .ant-form-item {
    //   margin-bottom: 12px;
    // }
    .ant-form-item-label {
      line-height: 24px;
    }
  }
}
</style>
