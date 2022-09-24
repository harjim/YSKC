<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input v-model="queryParams.ename" placeholder="请输入姓名" style="width:200px" />
        </a-form-item>
        <a-form-item label="工号">
          <a-input v-model="queryParams.enumber" placeholder="请输入工号" style="width:200px" />
        </a-form-item>
        <a-form-item label="职位">
          <a-input v-model="queryParams.position" placeholder="请输入职位" style="width:200px" />
        </a-form-item>
        <a-form-item label="部门">
          <a-input v-model="queryParams.deptName" placeholder="请输入部门" style="width:200px" />
        </a-form-item>
        <a-form-item label="职称">
          <a-input
            v-model="queryParams.title"
            placeholder="请输入职称"
            autosize="true"
            style="width:200px"
          />
        </a-form-item>
        <span>
          <a-form-item label="月份">
            <a-month-picker
              :disabledDate="disabledDate"
              v-model="queryParams.edate"
              format="YYYY-MM-DD"
              placeholder="请选择月份"
              style="width:200px"
            />
          </a-form-item>
          <a-form-item label="学历">
            <a-select
              v-model="queryParams.eduLevel"
              style="width:200px"
              :allowClear="true"
              placeholder="请选择学历"
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
          <a-form-item label="专业">
            <a-input v-model="queryParams.specialities" placeholder="请输入专业" style="width:200px" />
          </a-form-item>
          <a-form-item label="身份证号">
            <a-input v-model="queryParams.idNumber" placeholder="请输入身份证号" style="width:200px" />
          </a-form-item>
        </span>
        <a-form-item>
          <a-button
            style="margin-right: 10px;"
            type="primary"
            @click="search(true)"
            v-if="$auth('company:employee:search')"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/employee/queryEmployeeList"
          :params="queryParams"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :seq-config="{startIndex: 1}"
          :toolbar="tableToolbar"
        >
          <template v-slot:buttons>
            <a-button
              type="primary"
              @click="$refs.createModal.add()"
              style="margin-right:10px"
              v-if="$auth('company:employee:add')"
            >添加</a-button>
            <a-button
              type="primary"
              @click="openUploadModal"
              style="margin-right:10px"
              v-if="$auth('company:employee:improt')"
            >导入</a-button>
            <a-button
              type="primary"
              @click="exportEmployee"
              style="margin-right:10px"
              v-if="$auth('company:employee:export')"
            >导出</a-button>
            <a-button
              type="primary"
              @click="delEmployee"
              style="margin-right:10px"
              v-if="$auth('company:employee:del')"
              :disabled="selectedRowKeys.length <= 0"
            >删除</a-button>
            <a-button
              type="primary"
              @click="unbindAtt"
              style="margin-right:10px"
              v-if="$auth('company:employee:unbindAtt')"
              :disabled="bindRowKeys.length <= 0"
            >解绑研发考勤</a-button>
            <a-button
              type="primary"
              @click="triggerAtt(true)"
              style="margin-right:10px"
              v-if="$auth('company:employee:disabledAtt')"
              :disabled="selectedRowKeys.length <= 0"
            >禁用研发考勤</a-button>
            <a-button
              type="primary"
              @click="triggerAtt(false)"
              style="margin-right:10px"
              v-if="$auth('company:employee:enabledAtt')"
              :disabled="selectedRowKeys.length <= 0"
            >启用研发考勤</a-button>
            <a-button
              type="primary"
              @click="$refs.setRoleType.show(selectedRowKeys)"
              style="margin-right:10px"
              v-if="$auth('company:employee:setRoleType')"
              :disabled="selectedRowKeys.length <= 0"
            >设置角色</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              @click="$refs.configColumn.showModal(2)"
              v-if="$auth('company:employee:config')"
            >自定义列</a-button>
          </template>
          <vxe-table-column type="checkbox" width="40" fixed="left" />
          <vxe-table-column
            title="工号"
            field="enumber"
            width="90"
            remoteSort
            align="center"
            fixed="left"
          ></vxe-table-column>
          <vxe-table-column
            title="姓名"
            field="ename"
            width="100"
            align="left"
            fixed="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column title="职位" field="position" width="110" remoteSort align="center"></vxe-table-column>
          <vxe-table-column title="部门" field="deptName" width="100" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="研发考勤" field="disabledAtt" width="100" remoteSort align="center">
            <template v-slot="{ row }">{{ row.bindAtt ? '已绑定' : row.disabledAtt ? '禁用' : '启用' }}</template>
          </vxe-table-column>
          <vxe-table-column title="角色类型" field="roleType" width="100" remoteSort align="center">
            <template v-slot="{ row }">{{ row.roleType ? '项目统筹' : '普通' }}</template>
          </vxe-table-column>
          <vxe-table-column title="签名" field="autographUrl" width="130" align="center">
            <template v-slot="{ row }">
              <div v-if="row.autographUrl">
                <a @click="preview(row.autographUrl, `[${row.ename}]签名${row.autographUrl.substring(row.autographUrl.lastIndexOf('.'))}`)"><img :src="row.autographUrl" style="width:100px;height:45px;"></a>&nbsp;
                <a-popconfirm
                  v-if="$auth('company:employee:uploadAutograph')"
                  title="是否确定删除签名?"
                  @confirm="deleteAutograph(row.id)"
                >
                  <a style="color:red;vertical-align: top;">x</a>
                </a-popconfirm>
              </div>
            </template>
          </vxe-table-column>
          <vxe-table-column title="性别" field="gender" width="80" remoteSort align="center">
            <template v-slot="{ row }">
              <span>{{ getGender(row.gender) }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="职称" field="title" width="100" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="身份证号" field="idNumber" width="160" align="center" remoteSort></vxe-table-column>
          <vxe-table-column title="出生日期" field="birthday" width="120" remoteSort align="center">
            <template v-slot="{ row }">{{ row.birthday | formatDate }}</template>
          </vxe-table-column>
          <vxe-table-column title="入职日期" field="edate" width="120" remoteSort align="center">
            <template v-slot="{ row }">{{ row.edate | formatDate }}</template>
          </vxe-table-column>
          <vxe-table-column title="离职日期" field="leaveDate" width="120" remoteSort align="center">
            <template v-slot="{ row }">{{ row.leaveDate | formatDate }}</template>
          </vxe-table-column>
          <vxe-table-column title="学历" field="eduLevel" width="80" remoteSort align="center">
            <template v-slot="{ row }">
              <span>{{ getEduLevel(row.eduLevel) }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="专业" field="specialities" width="140" remoteSort align="center"></vxe-table-column>
          <vxe-table-column
            v-for="config in employeeArr"
            :key="config.name"
            align="center"
            :title="config.alias"
            width="80"
          >
            <template v-slot="{ row }">{{ row && row.data ? JSON.parse(row.data)[config.name] : '' }}</template>
          </vxe-table-column>
          <vxe-table-column title="备注" field="remark" min-width="140" align="left"></vxe-table-column>
          <vxe-table-column title="操作" field="action" width="180" align="center" fixed="right">
            <template v-slot="{ row }">
              <a v-if="$auth('company:employee:edit')" @click="$refs.createModal.edit(row)">编辑</a>
              <template v-if="$auth('company:employee:uploadAutograph')">
                <a-divider type="vertical" v-if="$auth('company:employee:edit')" />
                <a @click="$refs.uploadAutograph.show({id:row.id,enumber:row.enumber,ename:row.ename})">上传签名</a>
              </template>
              <template v-if="$auth('company:employee:del')">
                <a-divider type="vertical" v-if="$auth('company:employee:uploadAutograph') || $auth('company:employee:edit')" />
                <a-popconfirm
                  title="是否确定删除?"
                  @confirm="handleDel(row)"
                >
                  <a>删除</a>
                </a-popconfirm>
              </template>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <employee-modal ref="createModal" @ok="handleOk" :employeeArr="employeeArr"></employee-modal>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        paramKey="tableField"
        title="导入花名册"
        :templateName="templateName"
        ref="uploadModal"
        action="/doc/employee/importEmployee"
        @onSuccess="success"
        @error="error"
      />
      <set-role-type-modal ref="setRoleType" @ok="search"/>
      <upload-autograph-modal ref="uploadAutograph" @ok="search"/>
      <config-column-modal ref="configColumn" @ok="reloadTable" />
    </a-spin>
  </a-card>
</template>

<script>
import SetRoleTypeModal from './modules/SetRoleTypeModal'
import UploadAutographModal from './modules/UploadAutographModal'
import ConfigColumnModal from './modules/ConfigColumnModal'
import { STable, UploadModal, Ellipsis, EditTableTitle } from '@/components'
import EmployeeModal from './modules/EmployeeModal'
import moment from 'moment'
import { mixinLoadTitleObject } from '@/utils/mixin.js'
import ystable from '@/components/Table/ystable'

/// 学历， 0:无，1：高中，2：中专，3：大专，4：本科 5：硕士，6：博士
const eduLevelArr = ['无', '高中', '中专', '大专', '本科', '硕士', '博士', '初中']
const defaultTitleField = {
  tableId: 'employeeTable',
  fieldTitleObject: {
    enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
    ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
    deptName: { title: '部门', required: true, defaultTitle: '部门', importField: true },
    gender: { title: '性别', defaultTitle: '性别', importField: true },
    birthday: { title: '出生日期', defaultTitle: '出生日期', importField: true },
    position: { title: '职位', defaultTitle: '职位', importField: true },
    title: { title: '职称', defaultTitle: '职称', importField: true },
    idNumber: { title: '身份证号', defaultTitle: '身份证号', importField: true },
    edate: { title: '入职日期', defaultTitle: '入职日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
    leaveDate: { title: '离职日期', defaultTitle: '离职日期', importField: true },
    eduLevel: { title: '学历', defaultTitle: '学历', importField: true },
    specialities: { title: '专业', defaultTitle: '专业', importField: true },
    remark: { title: '备注', defaultTitle: '备注', importField: true }
  }
}
const defaultTitleConfig = [{
  enumber: 'A-01',
  ename: '张三',
  birthday: '格式："年-月-日"，例如：2019-10-11',
  deptName: 'xxx部门',
  position: '出纳',
  title: '职称',
  idNumber: '440000000000000000',
  edate: '格式："年-月-日"，例如：2019-10-11',
  eduLevel: '请从中选择填写：无/初中/高中/中专/大专/本科/硕士/博士，例如：本科',
  rdDeptName: '研发部门',
  specialities: '机电技术应用、电气技术应用',
  remark: '备注'
}]
export default {
  name: 'Employee',
  mixins: [mixinLoadTitleObject],
  components: {
    EditTableTitle,
    STable,
    EmployeeModal,
    Ellipsis,
    UploadModal,
    ystable,
    SetRoleTypeModal,
    UploadAutographModal,
    ConfigColumnModal
  },
  data () {
    return {
      scroll: {},
      tableToolbar: {
        refresh: true,
        zoom: true,
        custom: true
      },
      tableField: {},
      spinning: false,
      templateName: '花名册模板',
      form: this.$form.createForm(this),
      queryParams: {},
      employeeList: [],
      selectedRowKeys: [],
      bindRowKeys: [],
      sampleData: [],
      options: {},
      employeeArr: []
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1890) {
          this.scroll = { x: 1600 }
        }
      })()
    }
  },
  created () {
    this.getEmployeeConfig()
    this.getTableField()
    if (document.body.clientWidth < 1890) {
      this.scroll = { x: 1600 }
    }
  },
  filters: {
    formatDate (value) {
      if (!value) { return '' }
      return moment(value).format('YYYY-MM-DD')
    }
  },
  methods: {
    moment,
    preview (filePath, fileName) {
      this.$preview({
        filePath: filePath,
        docName: fileName,
        visible: true,
        url: '/document/appendixPreview'
      })
    },
    getTableField () {
      this.$http.get('/sysDictionary/getTableField', { params: { tableId: this.tableField.tableId } })
        .then(res => {
          if (res.success && res.data != null) {
            this.tableField = res.data
          }
        })
    },
    // 获取员工表列名
    getEmployeeConfig () {
      this.$http.get('/fieldConfig/getTypeConfig', { params: { type: 2 } }).then(res => {
        if (res.success && res.data) {
          this.employeeArr = JSON.parse(res.data.config)
        }
      })
    },
    reloadTable (edit) {
      if (edit) {
        this.getEmployeeConfig()
      }
      this.$refs.table.refresh(true)
    },
    onSaveTitle (value, name) {
      this.tableField.fieldTitleObject[name].title = value
      this.tableField.fieldTitle = JSON.stringify(this.tableField.fieldTitleObject)
      this.$http.post('/sysDictionary/saveTableField', this.tableField).then(res => {
        this.$message.success('保存成功')
        this.getTableField()
      })
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
      this.$message.success(flag ? '添加成功' : '更新成功')
      this.search(flag)
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.bindRowKeys = []
      this.employeeList = []
      this.$refs.table.refresh(refresh)
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
        this.$message.success(`${fileName}导入成功`)
      }
      this.search(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    handleDel (record) {
      this.$http.post('/employee/delEmployee', { id: record.id }).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.search()
        } else {
          this.$message.error(`[${record.ename}]${res.errorMessage ? res.errorMessage : '删除失败'}`)
        }
      })
    },
    loadTitle () {
      ({ sampleData: this.sampleData, tableField: this.tableField } = this.loadTitleObject('remark', this.employeeArr, defaultTitleField, defaultTitleConfig))
    },
    openUploadModal () {
      this.loadTitle()
      this.$refs.uploadModal.show(this.tableField)
    },
    exportEmployee () {
      this.getTableField()
      this.loadTitle()
      this.$http.get('/employee/employeEexport', { params: this.queryParams })
        .then(res => {
          if (res.success) {
            var keys = Object.keys(this.tableField.fieldTitleObject).map(a => this.tableField.fieldTitleObject[a].title)
            var sheetData = res.data
            sheetData.forEach(r => {
              if (r.data && typeof (r.data) === 'string') {
                r = Object.assign(r, JSON.parse(r.data))
              }
            })
            var sheetFilter = Object.keys(this.tableField.fieldTitleObject)
            this.$exportJsonData('花名册列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
        })
    },
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    delEmployee () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的人员吗?',
        content: <b>已被加计扣除使用的人员不会删除。</b>,
        onOk () {
          return self.$http.post('/employee/delEmployeeBatch', self.employeeList)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.search()
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
    unbindAtt () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要解除选中的人员的研发考勤绑定吗?',
        content: <b>解除绑定后，可再次研发考勤。</b>,
        onOk () {
          return self.$http.post('/employee/unbindAtt', { ids: self.bindRowKeys })
            .then(res => {
              if (res.success) {
                self.$message.success('解除绑定成功')
                self.search()
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '解除绑定失败')
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    deleteAutograph (id, enumber) {
      this.spinning = true
      this.$http.post('/employee/deleteAutograph', { id, enumber }).then((res) => {
        if (res.success && res.data) {
          this.$message.success('删除签名成功')
          this.search()
        } else {
          this.$message.error(res.errorMessage || '删除签名失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    triggerAtt (disabled) {
      this.spinning = true
      const self = this
      let text = '禁用'
      let url = '/employee/disabledAtt'
      if (!disabled) {
        text = '启用'
        url = '/employee/enabledAtt'
      }

      this.$confirm({
        title: `您确定要${text}选中的人员的研发考勤吗?`,
        content: disabled ? <b>禁用研发考勤后，不可绑定和使用研发考勤。</b> : <b>启用研发考勤后，可正常绑定和使用研发考勤。</b>,
        onOk () {
          return self.$http.post(url, { ids: self.selectedRowKeys })
            .then(res => {
              if (res.success) {
                self.$message.success(`${text}成功`)
                self.search()
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : `${text}失败`)
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    selectChange ({ records }) {
      this.employeeList = records
      this.bindRowKeys = []
      const bindRowKeys = []
      const selectedRowKeys = []
      records.forEach(item => {
        selectedRowKeys.push(item.id)
        if (item.bindAtt) {
          bindRowKeys.push(item.id)
        }
      })
      this.selectedRowKeys = selectedRowKeys
      this.bindRowKeys = bindRowKeys
    }
  }
}
</script>

<style scoped>
</style>
