<!--
 * @Author: your name
 * @Date: 2022-02-21 16:30:59
 * @LastEditors: zdf
 * @LastEditTime: 2022-04-02 08:09:58
 * @Description: In User Settings Edit
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\BaseInfoNew.vue
-->
<template>
  <a-spin :spinning="spinning" tip="请稍后...">
    <a-form :form="form" :colon="false">
      <a-divider orientation="left">需求</a-divider>
      <div style="padding: 0 12px" :class="{ 'status-not-edit': !canEdit || !editBase }">
        <a-row :gutter="16">
          <a-col span="24">
            <span style="float: right; z-index: 1;" v-if="$auth('patent:patentPlanNew:editBase') && !canEdit">
              <a-button size="small" v-if="!editBase" type="default" style="z-index: 1;" @click="editBaseMessage">编辑</a-button>
              <a-button size="small" v-else type="default" style="z-index: 1;" @click="editBase = false">取消</a-button>
            </span>
            <a-form-item label="专利需求" :required="canEdit">
              <vxe-pulldown v-if="canEdit" ref="pulldown" style="width: 100%">
                <template #default>
                  <a-input
                    v-model="customerName"
                    placeholder="请选择专利需求（可输入客户名称查询）"
                    @keyup="onPulldownKeyup"
                    @focus="onPulldownFocus"
                    @blur="onPulldownBlur"
                  >
                    <a-icon slot="suffix" type="search" />
                  </a-input>
                </template>
                <template #dropdown>
                  <div class="my-dropdown">
                    <ystable
                      ref="table"
                      queryUrl="/patentAppliaction/getList"
                      :params="queryParams"
                      header-align="center"
                      highlight-hover-row
                      show-overflow
                      :columns="pulldownTableColumn"
                      @cell-click="cellClickEvent"
                      resizable
                      auto-resize
                      row-id="id"
                    >
                      <template #ownerName="{ row }">
                        {{ row.ownerName }}
                      </template>
                      <template #techName="{ row }">
                        {{ row.techName }}
                      </template>
                      <template #total="{ row }">
                        <a-tooltip placement="top">
                          <template slot="title">
                            <div>发明专利：{{ row.inventionNum || '-' }}</div>
                            <div>实用新型：{{ row.utilityNum || '-' }}</div>
                            <div>外观设计：{{ row.appearanceDesignNum || '-' }}</div>
                            <div>软件著作：{{ row.softRightNum || '-' }}</div>
                          </template>
                          <div>{{ row.total }}</div>
                        </a-tooltip>
                      </template>
                    </ystable>
                  </div>
                </template>
              </vxe-pulldown>
              <div v-else>{{ `【${record.demandNo}】${record.companyName}-${record.year}` }}</div>
            </a-form-item>
          </a-col>
          <a-col span="6">
            <a-form-item label="预计提交国知局日期">
              <a-date-picker
                v-if="canEdit || editBase"
                style="width: 100%"
                v-decorator="['submitDate', { rules: [{ required: false, message: '请选择预计提交国知局日期' }] }]"
                placeholder="请选择预计提交国知局日期"
                :disabled="true"
              />
              <div v-else>{{ record.submitDate }}</div>
            </a-form-item>
          </a-col>
          <a-col span="6">
            <a-form-item label="RD">
              <a-select
                v-if="canEdit || editBase"
                v-decorator="['projectId', { rules: [{ required: true, message: '请选择RD' }] }]"
                placeholder="请选择RD"
              >
                <a-select-option v-for="rd in rds" :key="rd.id">{{ rd.rdTitle }}</a-select-option>
              </a-select>
              <div v-else>{{ record.rdTitle || '其他' }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="业务员">
              <search-select
                v-if="canEdit || editBase"
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="false"
                placeholder="请输入业务员"
                v-decorator="['owner', { rules: [{ required: true, message: '请输入业务员' }] }]"
              />
              <div v-else>{{ record.ownerName }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="工程师">
              <search-select
                :disabled="true"
                v-if="canEdit || editBase"
                url="/user/userForSelect"
                searchField="realName"
                sTitle="realName"
                :multiple="false"
                placeholder="请输入工程师"
                v-decorator="['engineer', { rules: [{ required: true, message: '请输入工程师' }] }]"
              />
              <div v-else>{{ record.engineer }}</div>
            </a-form-item>
          </a-col>
          <a-divider orientation="left">基本信息</a-divider>
          <a-col :span="6">
            <a-form-item label="申请名称">
              <a-input
                v-if="canEdit || editBase"
                v-decorator="['patentName', { rules: [{ required: true, message: '请输入申请名称' }] }]"
                placeholder="请输入申请名称"
              />
              <div v-else>{{ record.patentName }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="申请类型">
              <a-select
                v-if="canEdit || editBase"
                v-decorator="['patentType', { rules: [{ required: true, message: '请选择申请类型' }] }]"
                placeholder="请选择申请类型"
              >
                <a-select-option v-for="v in patentType" :key="v.value">{{ v.label }}</a-select-option>
              </a-select>
              <div v-else>{{ record.patentType | getLabel(patentType) }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="联系人">
              <a-input
                v-if="canEdit || editBase"
                v-decorator="['linkMan', { rules: [{ required: true, message: '请输入联系人' }] }]"
                placeholder="请输入联系人"
              />
              <div v-else>{{ record.linkMan }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="联系方式">
              <a-input
                v-if="canEdit || editBase"
                v-decorator="['linkTel', { rules: [{ required: true, message: '请输入联系方式' }] }]"
                placeholder="请输入联系方式"
              />
              <div v-else>{{ record.linkTel }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="申请（专利权）类型">
              <a-select
                v-if="canEdit || editBase"
                v-decorator="['unitType', { rules: [{ required: true, message: '请选择申请人类型' }] }]"
                placeholder="请选择申请人类型"
              >
                <a-select-option v-for="(item, index) in unitTypes" :key="index">{{ item }}</a-select-option>
              </a-select>
              <div v-else>{{ unitTypes[record.unitType] }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="申请（专利权）人">
              <a-input
                v-if="canEdit || editBase"
                v-decorator="['applicant', { rules: [{ required: true, message: '请输入申请人' }] }]"
                placeholder="请输入申请人"
              />
              <div v-else>{{ record.applicant }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="身份证号码或统一信息代码">
              <a-input
                v-if="canEdit || editBase"
                v-decorator="[
                  'code',
                  {
                    rules: [
                      { required: true, pattern: new RegExp(numPattern), message: '请输入身份证号码或统一信息代码' }
                    ]
                  }
                ]"
                placeholder="请输入身份证号码或统一信息代码"
              />
              <div v-else>{{ record.code }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="版本">
              <a-select
                v-if="canEdit || editBase"
                v-decorator="['planType', { rules: [{ required: true, message: '请选择版本' }] }]"
                placeholder="请选择版本"
              >
                <a-select-option v-for="(item, index) in patentPlanType" :key="index">{{ item.label }}</a-select-option>
              </a-select>
              <div v-else>{{ record.planType | getLabel(patentPlanType) }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="第一发明人">
              <a-input
                v-if="canEdit || editBase"
                v-decorator="[
                  'firstInventor',
                  { rules: [{ required: true, message: '请输入第一发明人姓名及身份证号' }] }
                ]"
                placeholder="请输入第一发明人姓名及身份证号"
              />
              <div v-else>{{ record.firstInventor }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="其他发明人">
              <a-input v-if="canEdit || editBase" v-decorator="['otherInventor']" placeholder="请输入其他发明人" />
              <div v-else>{{ record.otherInventor || '-' }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="详细地址">
              <a-input v-if="canEdit || editBase" v-decorator="['address']" placeholder="请输入详细地址" />
              <div v-else>{{ record.address || '-' }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="邮政编码">
              <a-input
                v-if="canEdit || editBase"
                :maxLength="6"
                v-decorator="[
                  'postalCode',
                  {
                    rules: [
                      {
                        required: false,
                        whitespace: true,
                        min: 6,
                        max: 6,
                        pattern: new RegExp('\\d{6}'),
                        message: '请输入6位邮政编码'
                      }
                    ]
                  }
                ]"
                placeholder="请输入邮政编码"
              />
              <div v-else>{{ record.postalCode || '-' }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="初稿需求日期">
              <a-date-picker
                v-if="canEdit || editBase"
                style="width: 100%"
                v-decorator="['demandDate', { rules: [{ required: true, message: '请选择初稿需求日期' }] }]"
                placeholder="请选择初稿需求日期"
              />
              <div v-else>{{ record.demandDate }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="18">
            <a-form-item label="其他事项">
              <a-checkbox-group v-if="canEdit || editBase" v-decorator="['otherItem']">
                <a-checkbox
                  v-for="(item, index) in otherItems"
                  :key="index"
                  :value="index"
                >{{ item }}</a-checkbox>
              </a-checkbox-group>
              <a-checkbox-group v-else :value="record.otherItem ? record.otherItem.split(',').map(item => +item) : []">
                <a-checkbox
                  v-for="(item, index) in otherItems"
                  :key="index"
                  :value="index"
                >{{ item }}</a-checkbox>
              </a-checkbox-group>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="交底书">
              <template v-if="canEdit || editBase">
                <input
                  v-if="canEdit || editBase"
                  style="display:none;"
                  v-decorator="['fileList', { rules: [{ required: true, message: '请上传附件' }] }]"
                />
                <a-upload
                  :fileList="fileList"
                  :multiple="false"
                  @change="changeUpload"
                  :beforeUpload="beforeUpload"
                  :showUploadList="{ showPreviewIcon: true, showDownloadIcon: true, showRemoveIcon: true }"
                  @preview="file => preview(file.url, file.name)"
                  @download="file => onDownloadFile(file.url, file.name)"
                >
                  <a-button> <a-icon type="upload" />上传附件 </a-button>
                </a-upload>
              </template>
              <template v-else>
                <span v-for="file in baseFiles" :key="file.id" style="margin-right:12px">
                  <a @click="onDownloadFile(file.filePath, file.fileName)">{{ file.fileName }}</a>
                  <a-tooltip @click="preview(file.filePath, file.fileName)">
                    <template slot="title">
                      <span>预览</span>
                    </template>
                    <a-icon type="eye" style="margin-left:5px" />
                  </a-tooltip>
                </span>
              </template>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item class="textarea" label="备注" :help="()=>{if(!canEdit && !editBase)return; const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 100 ? 100 : r.length}/100)`}">
              <a-textarea v-if="canEdit || editBase" v-decorator="['remark']" placeholder="请输入备注" :maxLength="100" :rows="3" />
              <div v-else>{{ record.remark || '-' }}</div>
            </a-form-item>
          </a-col>
          <a-col :span="24" style="text-align:right;">
            <a-form-item>
              <a-button v-if="canEdit || editBase" style="margin-right:8px;" type="primary" @click="save()">暂存</a-button>
              <a-button v-if="canEdit" type="primary" @click="onSubmit()">提交</a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </div>
    </a-form>
  </a-spin>
</template>

<script>
import moment from 'moment'
import SearchSelect from '@/components/Selects/SearchSelect.vue'
import ystable from '@/components/Table/ystable'
import _ from 'lodash'
import { mapState, mapGetters } from 'vuex'
import Template from '@/views/doc/Template.vue'
import { isEditStatus } from '@/utils/processDoc/auditStatus'

export default {
  components: {
    SearchSelect,
    ystable,
    Template
  },
  props: {
    record: {
      type: Object,
      default: () => {}
    },
    currentStep: {
      type: Number,
      default: 0
    },
    baseFiles: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      unitTypes: ['工矿企业', '个人', '大专院校', '科研单位', '事业单位'],
      otherItems: ['发明+实用新型同时申请', '申请时办理提前公开请求', '是否办理费减'],
      form: this.$form.createForm(this),
      spinning: false,
      isEdit: true,
      patentPlan: {},
      rds: [],
      fileList: [],
      queryParams: {},
      customerName: '', // 专利需求框显示的内容
      tableData: [],
      pulldownTableColumn: [
        { field: 'demandNo', title: '编号', width: 150 },
        { field: 'year', title: '年份', width: 80 },
        { field: 'customerName', title: '客户名称', width: 180 },
        { field: 'total', title: '需求数量', align: 'center', width: 80, slots: { default: 'total' } },
        { field: 'ownerName', title: '业务员', width: 120, slots: { default: 'ownerName' } },
        { field: 'techName', title: '工程师', width: 150, slots: { default: 'techName' } },
        { field: 'planSubmitDate', title: '计划提交国知局日期', width: 150 }
      ],
      dropdownParams: {},
      canEdit: false,
      editBase: false,
      control: {
        add: this.$auth('patent:patentPlanNew:add'),
        edit: this.$auth('patent:patentPlanNew:edit'),
        patent: this.$auth('patent:patentPlanNew:patent')
      }
    }
  },
  computed: {
    ...mapState({
      patentPlanProcessType: state => state.enums.patentPlanProcessType,
      patentPlanStatus: state => state.enums.patentPlanStatus,
      patentType: state => state.enums.patentType,
      patentPlanType: state => state.enums.patentPlanType
    }),
    ...mapGetters(['userInfo']),
    numPattern () {
      const year = (new Date().getFullYear() - 2000 + '').split('')
      return `^([1-9]\\d{5}(([1][9]\\d{2})|([2][0]([0-${Number(year[0]) - 1}]\\d)|([${Number(year[0])}][0${
        Number(year[1]) !== 0 ? '-' + Number(year[1]) : ''
      }])))\\d{7}[\\d|x|X]$)|(^[^_IOZSVa-z\\W]{2}\\d{6}[^_IOZSVa-z\\W]{10}$)`
    }
  },
  mounted () {
    if (Object.keys(this.record).length === 0) {
      this.initInfo()
    } else {
      this.updateInfo()
    }
  },
  methods: {
    moment,
    initInfo () {
      this.canEdit = true
      this.$nextTick(() => {
        this.form.setFieldsValue({ engineer: { id: this.userInfo.id, realName: this.userInfo.realName } })
      })
    },
    updateInfo () {
      // 不管在哪个节点驳回，需要回到第一个节点重新提交基本信息
      this.canEdit = Boolean(this.control.add) && (this.record.status === 2 || (this.record.nodeNumber === 0 || this.record.nodeNumber === null) && isEditStatus(this.record.status))
      if (this.canEdit) {
        this.initForm()
      }
    },
    // 通过客户公司获取rd列表
    getRds (d) {
      if (d) {
        this.$http
          .get('/patentPlanNew/getProjectSelect', { params: { companyId: d.companyId, year: d.year } })
          .then(res => {
            if (res.success) {
              this.rds = res.data || []
              this.rds.push({ id: -1, rdTitle: '其他' })
            } else {
              this.rds = []
            }
          })
      } else {
        this.rds = [{ id: -1, rdTitle: '其他' }]
      }
    },
    sumbitInfo (url) {
      this.form.validateFields((errors, values) => {
        if (this.dropdownParams.customerId == null) {
          this.$message.warning('请输入专利需求')
          return
        }
        if (errors) {
          return
        }
        values.id = this.record.id
        values.demandId = this.dropdownParams.id
        values.customerId = this.dropdownParams.customerId
        values.year = this.dropdownParams.year
        values.ownerId = values.owner.id
        values.engineerId = values.engineer.id
        delete values.owner
        delete values.engineer
        values.nodeNumber = this.record.nodeNumber == null ? null : 0
        if (values.otherItem) {
          values.otherItem = values.otherItem.join(',')
        }
        values.fileModels = values.fileList.map(a => {
          return { fileName: a.name, filePath: a.url, id: a.id, fileType: 0 }
        })
        delete values.fileList
        this.spinning = true
        this.$http
          .post(url, values)
          .then(res => {
            if (res.success && res.data) {
              this.$message.success('操作成功')
              this.$emit('update')
            } else {
              this.$message.error(res.errorMessage || '操作失败')
            }
          })
          .catch(err => {
            console.log('err ==>', err)
          })
          .finally(() => {
            this.spinning = false
          })
      })
    },
    save () {
      if (this.editBase) {
        this.sumbitInfo('/patentPlanNew/edit')
      } else {
        this.sumbitInfo('/patentPlanNew/save')
      }
    },
    onSubmit () {
      this.sumbitInfo('/patentPlanNew/submitPatent')
    },
    changeUpload (file) {
      if (file.file.status === 'removed') {
        const index = this.fileList.findIndex(item => item.url === file.file.url)
        this.fileList.splice(index, 1)
        this.form.setFieldsValue({ fileList: this.fileList.length ? this.fileList : undefined })
      }
    },
    preview (url, name) {
      this.$emit('preview', { url, name })
    },
    onDownloadFile (url, name) {
      this.$exportData('/patentBuying/download', { filePath: url }, name, this.$message)
    },
    beforeUpload (file) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/patent/')
      this.$http
        .post('/serviceLog/upload', param)
        .then(res => {
          if (res.success && res.data) {
            const data = res.data
            this.fileList.push({
              uid: data.filePath,
              name: data.fileName,
              status: 'done',
              url: data.filePath
            })
            this.form.setFieldsValue({ fileList: this.fileList })
          } else {
            this.$message.error(res.errorMessage || '系统异常，请联系管理员！')
          }
        })
        .catch(error => {
          this.$message.error(error.message || '系统异常，请联系管理员！')
        })
      return false
    },
    // 当需求框获得焦点时显示下拉表格
    onPulldownFocus () {
      this.$refs.pulldown.showPanel()
      if (this.dropdownParams.customerName) {
        this.customerName = ''
        // 有确定过需求且有前一次的查询的时候,清除掉查询关键字
        if (this.queryParams.customerName) {
          this.queryParams.customerName = ''
          this.$refs.table.refresh(true)
        }
      } else {
        // 没有确定过需求保留前一次的查询关键字
        this.customerName = this.queryParams.customerName
      }
    },
    onPulldownKeyup: _.debounce(function () {
      if (this.queryParams.customerName !== this.customerName) {
        this.queryParams.customerName = this.customerName
        this.$refs.table.refresh(true)
      }
    }, 500),
    onPulldownBlur () {
      if (this.dropdownParams.customerName) {
        this.customerName = this.dropdownParams.customerName
      } else {
        this.customerName = this.queryParams.customerName
      }
    },
    cellClickEvent ({ row }) {
      this.dropdownParams = {
        id: row.id,
        name: row.customerName,
        year: row.year,
        demandNo: row.demandNo,
        customerId: row.customerId,
        companyId: row.companyId,
        customerName: `【${row.demandNo}】${row.customerName}-${row.year}`
      }
      this.customerName = this.dropdownParams.customerName
      this.$refs.pulldown.hidePanel()
      this.form.setFieldsValue({
        submitDate: row.planSubmitDate ? moment(row.planSubmitDate) : undefined,
        owner: row.ownerId ? { realName: row.ownerName, id: row.ownerId } : undefined
      })
      this.form.setFieldsValue({ projectId: undefined })
      this.getRds({ companyId: row.companyId, year: row.year })
    },
    editBaseMessage () {
      this.editBase = true
      this.initForm()
    },
    initForm () {
      this.$nextTick(() => {
        let params = {}
        Object.keys(this.form.getFieldsValue()).forEach(key => {
          params[key] = this.record[key]
        })
        this.dropdownParams = {
          id: this.record.demandId,
          name: this.record.companyName,
          year: this.record.year,
          demandNo: this.record.demandNo,
          customerId: this.record.customerId,
          customerName: `【${this.record.demandNo || '-'}】${this.record.companyName}-${this.record.year}`
        }
        params.fileList = []
        this.baseFiles.forEach(item => {
          params.fileList.push({ id: item.id, name: item.fileName, url: item.filePath, uid: item.filePath })
        })
        this.fileList = params.fileList
        params = {
          ...params,
          owner: { id: this.record.ownerId, realName: this.record.ownerName, userName: this.record.ownerName },
          engineer: { id: this.record.engineerId, realName: this.record.engineer, userName: this.record.engineer },
          submitDate: this.record.submitDate ? moment(this.record.submitDate) : undefined,
          demandDate: moment(this.record.demandDate),
          otherItem: this.record.otherItem ? this.record.otherItem.split(',').map(item => +item) : []
        }
        this.getRds({ companyId: this.record.companyId, year: this.record.year })
        this.form.setFieldsValue(params)
        this.customerName = this.dropdownParams.customerName
        // 打开下拉容器才能修改值，todo优化
        /*
        if (!this.editBase) {
          this.$refs.pulldown.showPanel().then(() => {
            this.queryParams.customerName = this.dropdownParams.customerName
            this.$refs.pulldown.hidePanel()
          })
        }
        */
      })
    }
  }
}
</script>

<style lang="less" scoped>
.my-dropdown {
  background-color: #fff;
  box-shadow: 0 0 6px 2px rgba(0, 0, 0, 0.1);
}
// /deep/ .ant-col:nth-last-child(2) {
//   // margin-top: 20px;
//   .ant-form-item-control-wrapper {
//   height: auto !important;
// }
// }
</style>
