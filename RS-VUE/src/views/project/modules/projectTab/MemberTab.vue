<template>
  <div>
    <a-spin tip="请稍后..." :spinning="spinning">
      <div>
        <a-form layout="inline">
          <a-form-item label="姓名">
            <a-input style="width:165px;" v-model="queryParam.ename" placeholder="请输入姓名" />
          </a-form-item>
          <a-form-item label="工号">
            <a-input style="width:165px;" v-model="queryParam.enumber" placeholder="请输入工号" />
          </a-form-item>
          <a-form-item label="研发部门">
            <select-down
              ref="rdDeptSelet"
              treeType="rdDept"
              style="width:165px;"
              @fullPath="deptSelected"
              placeholder="请选择研发部门"
            />
          </a-form-item>
          <a-form-item label="项目角色">
            <a-input style="width:165px;" v-model="queryParam.role" placeholder="请输入项目角色" />
          </a-form-item>
          <a-form-item label="专业">
            <a-input style="width:165px;" v-model="queryParam.specialities" placeholder="请输入专业" />
          </a-form-item>
          <a-form-item>
            <span v-if="$auth('project:report:member:search')">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
            </span>
          </a-form-item>
        </a-form>
      </div>
      <ystable
        ref="table"
        queryUrl="/initMember/getList"
        :params="queryParam"
        highlight-hover-row
        show-overflow="title"
        resizable
        auto-resize
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        :toolbar="{ refresh: true, zoom: true, custom: true, slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button
            type="primary"
            style="margin-right: 10px"
            v-if="$auth('project:report:member:add')"
            @click="$refs.memberListModal.showModal(projectData.id,projectData.pname,projectData.rdTitle,currentYear)"
          >添加</a-button>
          <a-button
            type="primary"
            style="margin-right: 10px"
            v-if="$auth('project:report:member:import')"
            @click="openUploadModal"
          >导入</a-button>
          <a-button
            type="primary"
            style="margin-right: 10px"
            v-if="$auth('project:report:member:del')"
            :disabled="selectedRowKeys.length <= 0"
            @click="delInitMembers"
          >删除</a-button>
          <template v-if="$auth('project:report:member:edit')">
            <a-button
              type="primary"
              style="margin-right: 10px"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setRoleModal.add(selectedRowKeys)"
            >设置项目角色</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px"
              :disabled="selectedRowKeys.length != 1"
              @click="setMaster"
            >设置项目负责人</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setEntryDateModal.add(selectedRowKeys,projectData.beginDate,projectData.endDate,projectData.id,currentYear)"
            >设置加入日期</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px"
              v-if="projectData.beginYear !== projectData.endYear"
              @click="$refs.yearImport.open(currentYear,projectData.id)"
            >引入</a-button>
          </template>
        </template>
        <vxe-table-column type="checkbox" width="40" align="center" fixed="left"></vxe-table-column>
        <vxe-table-column field="enumber" title="工号" width="100" fixed="left" remoteSort></vxe-table-column>
        <vxe-table-column field="ename" title="姓名" width="100" fixed="left" remoteSort />
        <vxe-table-column field="position" title="职位" width="100" remoteSort />
        <vxe-table-column field="etype" title="人员类型" width="110" align="center" remoteSort>
          <template v-slot="{ row }">
            {{
              row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : ''
            }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="rdDeptName" title="研发部门" width="130">
          <template v-slot="{ row }">{{ keyMap[row.rdDeptId] }}</template>
        </vxe-table-column>
        <vxe-table-column field="role" title="项目角色" width="100" remoteSort></vxe-table-column>
        <vxe-table-column field="master" title="项目负责人" width="120" align="center">
          <template v-slot="{ row }">
            <span v-if="row.master">是</span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="entryDate" title="加入日期" width="130" align="center" remoteSort>
          <template v-slot="{ row }">{{ row.entryDate | formatDate }}</template>
        </vxe-table-column>
        <vxe-table-column field="birthday" title="出生日期" width="130" align="center" remoteSort>
          <template v-slot="{ row }">{{ row.birthday | formatDate }}</template>
        </vxe-table-column>
        <vxe-table-column field="title" title="职称" width="100" remoteSort></vxe-table-column>
        <vxe-table-column field="specialities" title="专业" width="100" remoteSort></vxe-table-column>
        <vxe-table-column field="edate" title="入职日期" width="130" align="center" remoteSort>
          <template v-slot="{ row }">{{ row.edate | formatDate }}</template>
        </vxe-table-column>
        <vxe-table-column title="操作" width="80" align="center" fixed="right">
          <template v-slot="{ row }">
            <a-popconfirm
              title="是否确定删除?"
              v-if="$auth('project:report:member:del')"
              @confirm="del(row)"
            >
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
      <member-list-modal ref="memberListModal" @ok="handleOk(true)" @error="handleError"></member-list-modal>
      <set-used-role-modal
        ref="setRoleModal"
        @ok="handleOk(false)"
        url="/initMember/updateInitMemberRole"
        :year="currentYear"
        initName="项目角色"
      />
      <set-entry-date-modal
        ref="setEntryDateModal"
        @ok="handleOk(false)"
        :checkRdUsed="checkRdUsed"
        :year="currentYear"
        url="/initMember/setEntryDate"
      />
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        paramKey="tableField"
        :paramData="{year: currentYear,projectId: projectData.id}"
        title="导入项目成员"
        ref="uploadModal"
        action="/doc/initMember/importMember"
        templateName="项目成员模板"
        @onSuccess="success"
        @error="error"
      />
      <year-import-modal
        ref="yearImport"
        initName="项目成员"
        url="initMember/importMembers"
        queryUrl="initMember/getMembersYear"
        @ok="search"
        @done="emit"
      />
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment'
import ystable from '@/components/Table/ystable'
import { SelectDown, Ellipsis, UploadModal } from '@/components'
import MemberListModal from '../../init/MemberListModal'
import SetUsedRoleModal from '../../init/SetUsedRoleModal'
import SetEntryDateModal from '../../init/SetEntryDateModal'
import YearImportModal from '../../init/YearImportModal'
import yearMixin from '@/utils/yearMixin'

export default {
  mixins: [yearMixin],
  name: 'MemberTab',
  components: {
    ystable,
    MemberListModal,
    SelectDown,
    SetUsedRoleModal,
    Ellipsis,
    UploadModal,
    SetEntryDateModal,
    YearImportModal
  },
  props: {
    projectData: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      keyMap: {},
      visible: false,
      title: '',
      spinning: false,
      options: [],
      selectedRowKeys: [],
      selectedRows: [],
      form: this.$form.createForm(this),
      queryParam: { year: null },
      getData: parameter => {
        this.queryParam.year = this.currentYear
        this.queryParam.projectId = this.projectData.id
        return this.$http.get('/initMember/getList', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            return res.data
          })
      },
      tableField: {
        tableId: 'initMember',
        fieldTitleObject: {
          enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
          ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
          typeName: { title: '人员类型', required: false, defaultTitle: '人员类型', importField: false },
          role: { title: '项目角色', defaultTitle: '项目角色', importField: true },
          birthday: { title: '出生日期', defaultTitle: '出生日期', importField: false },
          position: { title: '职位', defaultTitle: '职位', importField: false },
          title: { title: '职称', defaultTitle: '职称', importField: false },
          specialities: { title: '专业', defaultTitle: '专业', importField: false },
          edate: { title: '入职日期', defaultTitle: '入职日期', importField: false },
          entryDate: { title: '加入日期', defaultTitle: '加入日期', importField: true }
          // master: { title: '项目负责人', defaultTitle: '项目负责人', importField: true }
        }
      },
      sampleData: [{
        enumber: 'A-01',
        ename: '张三',
        role: '总工程师',
        entryDate: '格式："年-月-日"，例如：2019-10-11'
        // master: '项目负责人： 是/否'
      }]
    }
  },
  watch: {
    projectData (item) {
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    }
  },
  mounted () {
    this.queryParam.year = this.currentYear
    this.queryParam.projectId = this.projectData.id
    this.$getTree('rdDept', false, this.currentYear).then(res => {
      this.keyMap = res.keyMap
    })
  },
  filters: {
    formatDate (value) {
      if (!value) return ''
      return moment(value).format('YYYY-MM-DD')
    }
  },
  methods: {
    moment,
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
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    deptSelected (rdDeptPath) {
      this.queryParam.rdDeptPath = rdDeptPath
    },
    handleSubmit () {
      this.visible = false
    },
    handleOk (ok) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(ok)
    },
    handleError (errorMassege) {
      this.$message.error(errorMassege)
    },
    del (record) {
      this.spinning = true
      const ids = [record.id]
      this.checkRdUsed({ ids: ids, projectId: this.projectData.id, year: this.currentYear }).then(res => {
        if (res) {
          this.$http.post('/initMember/del', { id: record.id, projectId: this.projectData.id, year: this.currentYear })
            .then(res => {
              this.spinning = false
              if (res.success && res.data) {
                this.$message.success('删除成功')
                this.selectedRowKeys = []
                this.$refs.table.refresh(false)
                this.$emit('update')
              } else {
                this.$message.error('删除失败')
              }
            })
        } else {
          this.spinning = false
        }
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    search (refresh) {
      this.selectedRow = []
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
    },
    delInitMembers () {
      this.spinning = true
      var content = ''
      var hasMaster = false
      var values = {}
      for (let i = 0; i < this.selectedRows.length; i++) {
        if (this.selectedRows[i].master) {
          content = `即将删除的人员里存在项目负责人[${this.selectedRows[i].ename}]`
          hasMaster = true
          this.$emit('update')
          break
        }
      }
      values.ids = this.selectedRowKeys
      values.hasMaster = hasMaster
      values.projectId = this.projectData.id
      values.year = this.currentYear
      this.checkRdUsed(values).then(res => {
        if (res) {
          const self = this
          this.$confirm({
            title: '您确定要删除选中的人员吗?',
            content: content,
            onOk () {
              return self.$http.post('/initMember/delInitMembers', values)
                .then(res => {
                  if (res.success) {
                    self.$message.success('删除成功')
                    self.search(false)
                    self.$emit('update')
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
        } else {
          this.spinning = false
        }
      })
    },
    setMaster () {
      const master = this.selectedRows[0]
      master.projectId = this.projectData.id
      master.year = this.currentYear
      this.$http.post('initMember/setMaster', master).then(res => {
        if (res.success) {
          this.$message.success('设置项目负责人成功.')
          this.$emit('update')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '设置项目负责人失败，请稍后重试!')
        }
      }).finally(res => {
        this.search(false)
      })
    },
    emit () {
      this.$emit('update')
    },
    checkRdUsed (values) {
      return new Promise((resolve, reject) => {
        this.$http.get('/initMember/checkRdUsed', { params: values })
          .then(res => {
            if (res.success) {
              if (res.data) {
                this.$confirm({
                  title: '您确定要继续当前操作吗?',
                  content: () => { return res.data },
                  onOk () {
                    resolve(true)
                  },
                  onCancel () {
                    resolve(false)
                  }
                })
              } else {
                resolve(true)
              }
            } else {
              reject(res)
            }
          })
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
      this.selectedRows = records
    }
  }
}
</script>

<style>
</style>
