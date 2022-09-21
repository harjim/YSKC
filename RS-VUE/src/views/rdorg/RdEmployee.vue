<template>
  <a-card :bordered="false">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input
            v-model="queryParams.ename"
            placeholder="请输入姓名"
          />
        </a-form-item>
        <a-form-item label="工号">
          <a-input
            v-model="queryParams.enumber"
            placeholder="请输入工号"
          />
        </a-form-item>
        <a-form-item label="职称">
          <a-input
            v-model="queryParams.title"
            placeholder="请输入职称"
            autosize="true"
          />
        </a-form-item>
        <a-form-item label="身份证号">
          <a-input
            v-model="queryParams.idNumber"
            placeholder="请输入身份证号"
          />
        </a-form-item>
        <a-form-item label="研发部门">
          <select-down
            style="width:174px;"
            ref="rdDeptSelet"
            treeType="rdDept"
            @fullPath="rdDeptSelected"
            placeholder="请选择研发部门"
          />
        </a-form-item>
        <a-form-item label="原职位">
          <a-input
            v-model="queryParams.position"
            placeholder="请输入原职位"
          />
        </a-form-item>
        <a-form-item label="月份">
          <a-month-picker
            style="width:100%"
            :disabledDate="disabledDate"
            v-model="queryParams.edate"
            placeholder="请选择月份"
          />
        </a-form-item>
        <a-form-item label="专业">
          <a-input
            v-model="queryParams.specialities"
            placeholder="请输入专业"
          />
        </a-form-item>
        <a-form-item label="人员类型">
          <a-select
            style="width: 174px;"
            v-model="queryParams.etypes"
            mode="multiple"
            :maxTagCount="3"
            placeholder="请选择人员类型"
          >
            <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="学历">
          <a-select
            v-model="queryParams.eduLevel"
            :allowClear="true"
            placeholder="请选择学历"
            style="width: 174px;"
          >
            <a-select-option value="-1">请选择</a-select-option>
            <a-select-option value="0">无</a-select-option>
            <a-select-option value="7">初中</a-select-option>
            <a-select-option value="1">高中</a-select-option>
            <a-select-option value="2">中专</a-select-option>
            <a-select-option value="3">大专</a-select-option>
            <a-select-option value="4">本科</a-select-option>
            <a-select-option value="5">硕士</a-select-option>
            <a-select-option value="6">博士</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="部门">
          <a-input
            v-model="queryParams.deptName"
            placeholder="请输入部门"
          />
        </a-form-item>
        <span class="table-page-search-submitButtons">
          <a-button
            v-if="$auth('rdorg:rdEmployee:search')"
            type="primary"
            @click="search()"
          >查询</a-button>
        </span>
      </a-form>
      <div class="table">
        <ystable
          ref="table"
          queryUrl="/rdEmployee/getRdEmployeeData"
          :params="queryParams"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :seq-config="{startIndex: 1}"
          :toolbar="tableToolbar"
          @completed="({data:{footer}})=>completed(footer)"
          max-height="100%"
        >
          <template v-slot:buttons>
            <a-button
              type="primary"
              @click="$refs.createModal.add(currentYear)"
              style="margin-right:10px"
              v-if="$auth('rdorg:rdEmployee:add') && modify"
            >添加</a-button>
            <a-button
              type="primary"
              @click="openUploadModal"
              style="margin-right:10px"
              v-if="$auth('rdorg:rdEmployee:import') && modify"
            >导入</a-button>
            <a-button
              type="primary"
              @click="exportRdEmp"
              style="margin-right:10px"
              v-if="$auth('rdorg:rdEmployee:export')"
            >导出</a-button>
            <a-button
              type="primary"
              @click="delRdEmployee"
              style="margin-right:10px"
              :disabled="selectedRowKeys.length <= 0"
              v-if="$auth('rdorg:rdEmployee:del') && modify"
            >删除</a-button>
            <a-button
              type="primary"
              v-if="$auth('rdorg:rdEmployee:setDept') && modify"
              style="margin-right:10px"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.rdDept.add(currentYear)"
            >设置研发部门</a-button>
            <a-button
              type="primary"
              style="margin-right:10px"
              v-if="$auth('rdorg:rdEmployee:setType') && modify"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.modal.add(selectedRowKeys,currentYear)"
            >设置类型</a-button>
            <a-button
              type="primary"
              style="margin-right:10px"
              v-if="$auth('rdorg:rdEmployee:setPosition') && modify"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setPosition.add(selectedRowKeys,currentYear)"
            >设置职位</a-button>
            <a-button
              type="primary"
              style="margin-right:10px"
              v-if="$auth('rdorg:rdEmployee:setProject') && modify"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setProject.open(employeeList.map(a=>a.enumber),currentYear)"
            > 分配项目</a-button>
            <a-button
              type="primary"
              style="margin-right:10px"
              v-if="$auth('rdorg:rdEmployee:setAmount') && modify"
              @click="$refs.setAmount.open(currentYear)"
            > 设置人员总数</a-button>
            人员总数：<a style="font-weight: 600">{{ employeeAmount || '-' }}</a>
          </template>
          <vxe-table-column
            type="checkbox"
            width="40"
            fixed="left"
          />
          <vxe-table-column
            title="工号"
            field="enumber"
            width="120"
            remoteSort
            align="left"
            fixed="left"
          ></vxe-table-column>
          <vxe-table-column
            title="姓名"
            field="ename"
            width="80"
            remoteSort
            align="left"
            fixed="left"
          ></vxe-table-column>
          <vxe-table-column
            title="人员类型"
            field="etype"
            width="160"
            align="center"
          >
            <template v-slot="{ row }">
              <a-select
                v-if="$auth('rdorg:rdEmployee:setType') && modify"
                :value="row.etype > 0 ? String(row.etype): undefined"
                style="width:120px;"
                placeholder="请选择人员类型"
                @change="v=>saveEtype(v,row)"
              >
                <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="`${item.value}`">{{ item.label }}</a-select-option>
              </a-select>
              <template v-else>{{
                row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : ''
              }}</template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="部门"
            field="deptName"
            width="100"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="研发部门"
            field="rdDeptName"
            width="160"
            align="left"
          >
            <template v-slot="{ row }">
              <template v-if="$auth('rdorg:rdEmployee:setDept') && modify">
                <select-down
                  :allowClear="false"
                  ref="rdDeptSelet"
                  treeType="rdDept"
                  :value="row.rdDeptId"
                  @select="v=>saveOneRdDept(v,row)"
                  placeholder="请选择研发部门"
                  style="width:120px;"
                />
              </template>
              <template v-else>
                {{ row.fullName }}
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="项目编号"
            field="rds"
            width="140"
            align="left"
          />  <vxe-table-column
            title="缺失月份"
            field="lackMonth"
            width="130"
            align="center"
          />
          <vxe-table-column
            title="职位"
            field="position"
            width="130"
            align="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="原职位"
            field="basePosition"
            width="130"
            align="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="出生日期"
            field="birthday"
            width="100"
            align="center"
          >
            <template v-slot="{ row }">{{ row.birthday | formatData }}</template>
          </vxe-table-column>
          <vxe-table-column
            title="性别"
            field="gender"
            width="80"
            align="center"
            remoteSort
          >
            <template v-slot="{ row }">
              <span>{{ getGender(row.gender) }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="职称"
            field="title"
            width="80"
            align="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="身份证号"
            field="idNumber"
            width="160"
            align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="学历"
            field="eduLevel"
            width="80"
            remoteSort
            align="left"
          >
            <template v-slot="{ row }">
              <span>{{ getEduLevel(row.eduLevel) }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="专业"
            field="specialities"
            width="100"
            align="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="入职日期"
            field="edate"
            width="100"
            remoteSort
            align="center"
          >
            <template v-slot="{ row }">{{ row.edate | formatData }}</template>
          </vxe-table-column>
          <vxe-table-column
            title="离职日期"
            field="leaveDate"
            width="100"
            align="center"
            remoteSort
          >
            <template v-slot="{ row }">{{ row.leaveDate | formatData }}</template>
          </vxe-table-column>
          <vxe-table-column
            title="备注"
            field="remark"
            min-width="140"
            align="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="操作"
            field="action"
            width="80"
            align="center"
            fixed="right"
          >
            <template v-slot="{ row }">
              <a-popconfirm
                v-if="$auth('rdorg:rdEmployee:del') && modify"
                title="是否确定删除?"
                @confirm="handleDel(row)"
              >
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <selectEmployee-modal
        ref="createModal"
        @ok="handleOk"
      ></selectEmployee-modal>
      <set-rd-dept
        ref="rdDept"
        @ok="setRdDeptId"
      ></set-rd-dept>
      <settingEtype-modal
        ref="modal"
        @ok="handleOk"
      ></settingEtype-modal>
      <settingPosition-modal
        ref="setPosition"
        @ok="handleOk"
      ></settingPosition-modal>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        :paramData="paramData"
        paramKey="tableField"
        title="导入研发花名册"
        :templateName="templateName"
        ref="uploadModal"
        action="/doc/rdEmployee/importEmployee"
        @onSuccess="success"
        @error="error"
      />
      <setting-project-modal
        title="分配项目-研发花名册"
        url="/rdEmployee/setProjectMember"
        ref="setProject"
        @ok="handleOk"
      />
      <setting-amount-modal
        ref="setAmount"
        @ok="handleOk"/>
    </a-spin>
  </a-card>
</template>

<script>
import { Ellipsis, SetRdDept, SelectDown, UploadModal } from '@/components'
import { isEditStatus, isExportStatus } from '@/utils/processDoc/auditStatus'
import { mapGetters } from 'vuex'
import moment from 'moment'
import selectEmployeeModal from './modules/selectEmployeeModal'
import yearMiXin from '@/utils/yearMixin'
import SettingEtypeModal from './modules/SettingEtypeModal'
import SettingPositionModal from './modules/SettingPositionModal'
import ystable from '@/components/Table/ystable'
import SettingProjectModal from './modules/settingProjectModal'
import SettingAmountModal from './modules/SettingAmountModal'

/// 学历， 0:无，1：高中，2：中专，3：大专，4：本科 5：硕士，6：博士, 7 : 初中
const eduLevelArr = ['无', '高中', '中专', '大专', '本科', '硕士', '博士', '初中']
/// 员工类型，0：普通员工， 1： 研发人员， 2：专员
/// 普通员工，研究人员，技术人员，辅助人员

export default {
  mixins: [yearMiXin],
  name: 'RdEmployee',
  components: {
    Ellipsis,
    SelectDown,
    SetRdDept,
    selectEmployeeModal,
    SettingEtypeModal,
    SettingPositionModal,
    UploadModal,
    ystable,
    SettingProjectModal,
    SettingAmountModal
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 10 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      spinning: false,
      form: this.$form.createForm(this),
      queryParams: {},
      scroll: {},
      employeeList: [],
      selectedRowKeys: [],
      pagination: [],
      templateName: '研发花名册模板',
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true
      },
      paramData: { year: this.currentYear },
      tableFieldEx: {
        tableId: 'rdEmployeeTable',
        fieldTitleObject: {
          enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
          ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
          typeValue: { title: '人员类型', defaultTitle: '人员类型', importField: true },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          rdDeptName: { title: '研发部门', defaultTitle: '研发部门', importField: true },
          position: { title: '职位', defaultTitle: '职位', importField: true },
          basePosition: { title: '原职位' },
          rds: { title: '项目编号' },
          lackMonth: { title: '缺失月份' },
          birthday: { title: '出生日期', defaultTitle: '出生日期', importField: true },
          title: { title: '职称', defaultTitle: '职称', importField: true },
          idNumber: { title: '身份证号', defaultTitle: '身份证号', importField: true },
          eduLevel: { title: '学历', defaultTitle: '学历', importField: true },
          specialities: { title: '专业', defaultTitle: '专业', importField: true },
          edate: { title: '入职日期', defaultTitle: '入职日期', importField: true },
          leaveDate: { title: '离职日期', defaultTitle: '离职日期', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }

        }
      },
      tableField: {
        tableId: 'importRdEmployeeTable',
        fieldTitleObject: {
          enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
          ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
          rdDeptName: { title: '研发部门', required: true, defaultTitle: '研发部门', importField: true },
          typeValue: { title: '人员类型', required: true, defaultTitle: '人员类型', importField: true },
          position: { title: '职位', required: false, defaultTitle: '职位', importField: true }
        }
      },
      sampleData: [{
        enumber: '格式：2020或AC20191128',
        ename: '张三',
        rdDeptName: '/xxxx研发中心/xxxx组/',
        typeValue: ' 研究人员或技术人员或辅助人员',
        position: '研发人员职位'
      }],
      modify: false,
      employeeAmount: 0
    }
  },
  mounted () {
    this.modify = this.isEditStatus(this.auditStatus) || !this.userInfo.userSource
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1890) {
          this.scroll = { x: 1600 }
        }
      })()
    }
  },
  computed: {
    ...mapGetters(['auditStatus', 'userInfo'])
  },
  watch: {
    auditStatus: {
      immediate: true,
      handler (val) {
        this.modify = this.isEditStatus(val) || !this.userInfo.userSource
        this.search()
      }
    }
  },
  created () {
    if (document.body.clientWidth < 1890) {
      this.scroll = { x: 1600 }
    }
    this.paramData.year = this.currentYear
    this.queryParams.year = this.currentYear
  },
  filters: {
    formatData (value) {
      if (!value) { return '' }
      return moment(value).format('YYYY-MM-DD')
    }
  },
  methods: {
    completed (footer) {
      this.employeeAmount = footer
    },
    isEditStatus,
    isExportStatus,
    search () {
      this.selectedRowKeys = []
      this.queryParams.year = this.currentYear
      this.paramData.year = this.currentYear
      if (this.$refs.table && this.$refs.table.refresh) {
        this.$refs.table.refresh(true)
      }
    },
    getEduLevel (index) {
      return eduLevelArr[index]
    },
    getGender (index) {
      if (index === 1) {
        return '女'
      }
      if (index === 2) {
        return '男'
      }
    },
    handleOk (flag) {
      this.selectedRowKeys = []
      this.employeeList = []
      if (flag) {
        this.$refs.table.refresh(true)
      } else {
        this.$refs.table.refresh()
      }
    },
    setRdDeptId (rdDeptId) {
      const values = { rdDeptId: rdDeptId, ids: this.selectedRowKeys }
      this.saveRdDept(values, true)
    },
    saveRdDept (values, showMsg) {
      values.year = this.currentYear
      this.$http.post('/rdEmployee/updateEmployeeRdDept', values)
        .then(res => {
          if (res.success && res.data) {
            if (showMsg) {
              this.$message.success('设置成功')
              this.selectedRowKeys = []
              this.$refs.table.refresh()
            }
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
          }
        })
    },
    saveOneRdDept (v, row) {
      const values = { rdDeptId: v, ids: [row.id] }
      row.rdDeptId = v
      this.saveRdDept(values)
    },
    saveEtype (v, row) {
      const values = { etype: v, ids: [row.id], year: this.currentYear }
      this.$http.post('/rdEmployee/updateRdEmployeeEtype', values)
        .then(res => {
          if (!res.success || !res.data) {
            this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
          } else {
            row.etype = v
          }
        })
    },
    handleDel (record) {
      this.$http.post('/rdEmployee/delRdEmployee', { id: record.id, year: this.currentYear }).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.$refs.table.refresh()
        } else {
          this.$message.error(`[${record.ename}]${res.errorMessage ? res.errorMessage : '删除失败'}`)
        }
      })
    },
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    rdDeptSelected (rdDeptPath) {
      this.queryParams.rdDeptPath = rdDeptPath
    },
    delRdEmployee () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的研发花名册吗?',
        content: <b>已被加计扣除使用的人员不会删除。</b>,
        onOk () {
          return self.$http.post('/rdEmployee/delRdEmployeeBatch', { ids: self.selectedRowKeys, year: self.currentYear })
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys.length = 0
                self.employeeList = []
                self.$refs.table.refresh()
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    exportRdEmp () {
      this.spinning = true
      this.$exportData('/rdEmployee/getTableExport', this.queryParams,
        `研发花名册列表数据.xls`, this.$message).then(res => {
        this.spinning = false
      })
    },
    openUploadModal () {
      this.paramData.year = this.currentYear
      this.$refs.uploadModal.show(this.tableField)
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {
          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    selectChange ({ records }) {
      this.employeeList = records
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>

<style lang='less' scoped>

/deep/ .ant-spin-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 220px);

}
/deep/ .ant-form-inline {
  height: auto;
  flex-shrink: 0;
}

/deep/ .table {
  flex: 1;
  overflow-y: hidden;
}

</style>
