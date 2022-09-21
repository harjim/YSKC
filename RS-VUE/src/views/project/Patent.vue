<template>
  <a-card :bodyStyle="{ height: '100%', overflow: 'auto'}" style="height: 100%;">
    <a-form layout="inline">
      <a-form-item label="专利号">
        <a-input v-model="queryParams.patentNo" placeholder="请输入专利号" />
      </a-form-item>
      <a-form-item label="专利名称">
        <a-input v-model="queryParams.patentName" placeholder="请输入专利名称" />
      </a-form-item>
      <a-form-item label="申请人">
        <a-input v-model="queryParams.applyName" placeholder="请输入申请人" />
      </a-form-item>
      <a-form-item label="专利类型">
        <a-select
          style="width:174px"
          v-model="queryParams.mainType"
          placeholder="请选择专利类型"
          :allowClear="true"
        >
          <a-select-option value="发明专利">发明专利</a-select-option>
          <a-select-option value="实用新型">实用新型</a-select-option>
          <a-select-option value="外观设计">外观设计</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          v-if="$auth('project:patent:list:search')"
          @click="$refs.table.refresh(true)"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <div v-if="showTable" id="scrollContent">
      <ystable
        ref="table"
        border="full"
        queryUrl="/patentDetail/getPatentList"
        :params="queryParams"
        max-height="100%"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="$auth('project:patent:list:add')"
            @click="$refs.addPatentModal.add(currentYear,projectId)"
          >添加</a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="$auth('project:patent:list:relevance')"
            :disabled="selectedRowKeys.length <= 0"
            @click="$refs.relatedPatentModal.show(selectedPatentNos,currentYear)"
          >关联项目</a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="$auth('project:patent:list:import')"
            @click="openUploadModal"
          >导入</a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            @click="searchPatent"
            v-if="isMsUser"
          >查询专利</a-button>
        </template>
        <vxe-table-column
          type="checkbox"
          width="60"
          align="center"
          header-align="center"
          fixed="left"
        ></vxe-table-column>
        <vxe-table-column
          title="专利号/申请号"
          field="patentNo"
          width="150"
          align="left"
          header-align="center"
          fixed="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="专利名称"
          field="patentName"
          min-width="150"
          align="left"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="专利类型"
          field="mainType"
          width="120"
          align="center"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="项目"
          field="pname"
          :min-width="200"
          align="left"
          header-align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <template
              v-if="row.pname"
            >{{ row.rdTitle }}-{{ row.pname }}</template>
            <template v-else-if="$auth('project:patent:list:relevance')">
              <a @click="$refs.relatedPatentModal.show(row, currentYear)">关联项目</a>
            </template>
            <template v-else>--</template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="发明人"
          field="inventor"
          width="100"
          align="left"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="申请人"
          field="applyName"
          width="100"
          align="left"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="申请日期"
          field="applyDateTime"
          width="150"
          align="center"
          header-align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <template v-if="row.applyDateTime">{{ row.applyDateTime | DayFormat }}</template>
            <template v-else></template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="权利要求数量"
          field="claimNum"
          width="126"
          align="right"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="使用次数"
          field="usedCnt"
          width="100"
          align="right"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="来源"
          field="source"
          width="120"
          align="center"
          header-align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            {{ row.source ===1 ? '购买' : '自主' }}
          </template></vxe-table-column>
        <vxe-table-column
          title="授权日期"
          field="authDate"
          width="150"
          align="center"
          header-align="center"
          remoteSort
        >
        </vxe-table-column>
        <vxe-table-column
          title="法律状态"
          field="caseStatus"
          width="120"
          align="left"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="截止缴费日期"
          field="expiryDate"
          width="150"
          align="center"
          header-align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <template v-if="row.expiryDate">{{ row.expiryDate | DayFormat }}</template>
            <template v-else></template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="缴费金额"
          field="expiryAmount"
          width="120"
          align="right"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column title="操作" width="170" align="center" header-align="center" fixed="right">
          <template v-slot="{ row }">
            <span v-if="$auth('project:patent:doc:view')">
              <!-- <a @click="$refs.patentFileModal.showModal(row,projectName)">资料</a> -->
              <a @click="onShowAttchment(row)">附件列表</a>
            </span>
            <span v-if="$auth('project:patent:list:edit')">
              <a-divider type="vertical" v-if="$auth('project:patent:doc:view')" />
              <a @click="$refs.addPatentModal.edit(row,currentYear)">编辑</a>
            </span>
            <span v-if="$auth('project:patent:list:del')">
              <a-divider
                v-if="$auth('project:patent:list:edit') || $auth('project:patent:list:datum')"
                type="vertical"
              />
              <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
                <a>删除</a>
              </a-popconfirm>
            </span>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
    <patent-files-modal ref="attachmentsModal"/>
    <patent-search-modal ref="searchModal" @ok="handleOk" />
    <add-patent-modal ref="addPatentModal" @ok="handleOk"></add-patent-modal>
    <related-patent-modal ref="relatedPatentModal" @ok="handleSave"></related-patent-modal>
    <upload-modal
      :showProgress="true"
      :sampleData="sampleData"
      paramKey="tableField"
      title="导入专利"
      templateName="专利列表模板"
      ref="uploadModal"
      action="/doc/patentDetail/importPatent"
      @onSuccess="success"
      @error="error"
    />
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import { Ellipsis, SelectProject, UploadModal } from '@/components'
import yearMiXin from '@/utils/yearMixin'
import AddPatentModal from './modules/AddPatentModal'
import RelatedPatentModal from './modules/RelatedPatentModal'
import PatentFilesModal from './modules/PatentFilesModal'
import PatentSearchModal from './modules/PatentSearchModal'
import { isMsUser } from '@/utils/processDoc/auditStatus'
export default {
  mixins: [yearMiXin],
  name: 'IntellectualProperty',
  components: {
    ystable,
    Ellipsis,
    SelectProject,
    UploadModal,
    AddPatentModal,
    RelatedPatentModal,
    PatentFilesModal,
    PatentSearchModal
  },
  data () {
    return {
      queryParams: {},
      paramData: { projectId: this.projectId },
      tableField: {
        tableId: 'patentTable',
        fieldTitleObject: {
          patentNo: { title: '专利号', required: true, defaultTitle: '专利号', importField: true },
          patentName: { title: '发明名称', required: true, defaultTitle: '发明名称', importField: true },
          mainType: { title: '类型', required: true, defaultTitle: '类型', importField: true },
          inventor: { title: '发明人', defaultTitle: '发明人', importField: true },
          applyDateTime: { title: '申请日期', required: true, defaultTitle: '申请日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          authDate: { title: '授权日期', required: true, defaultTitle: '授权日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          claimNum: { title: '权利要求数量', required: true, defaultTitle: '权利要求数量', importField: true },
          claimContent: { title: '权利要求内容', required: true, defaultTitle: '权利要求内容', importField: true },
          specification: { title: '说明书内容', defaultTitle: '说明书内容', importField: true },
          usedCnt: { title: '使用次数', required: true, defaultTitle: '使用次数', importField: true }
        }
      },
      sampleData: [{
        applyDateTime: '格式："年-月-日"，例如：2019-10-11',
        authDate: '格式："年-月-日"，例如：2019-10-11',
        mainType: '发明专利/实用新型/外观设计',
        claimNum: '数字格式，例如：4',
        usedCnt: '数字格式，例如：4'
      }],
      showTable: true,
      projectId: 0,
      projectName: '',
      selectedRowKeys: [],
      selectedPatentNos: [],
      patentData: parameter => {
        if (this.projectId === 0) {
          return
        }
        return this.$http.get('/patent/queryPatent', { params: Object.assign(parameter, { year: this.currentYear, projectId: this.projectId, patentNo: this.patentNo, patentName: this.patentName }) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  updated () {
    this.selectedRowKeys = []
    this.selectedPatentNos = []
  },
  computed: {
    isMsUser () {
      return isMsUser()
    }
  },
  methods: {
    searchPatent () {
      this.$refs.searchModal.show()
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    search () {
      if (this.$refs.table && this.$refs.table.refresh) { this.$refs.table.refresh(true) }
    },
    projectSelected (value, project) {
      this.projectId = value
      if (value === 0) {
        this.showTable = false
      } else {
        this.showTable = true
        this.projectName = project.pname
        this.paramData.projectId = this.projectId
        this.$refs.table.refresh(true)
      }
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
        this.$refs.table.refresh(true)
      } else {
        this.$message.success('更新成功')
        this.$refs.table.refresh(false)
      }
    },
    handleSave () {
      this.$message.success('操作成功')
      this.$refs.table.refresh(false)
      this.selectedRowKeys = []
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
    handleDel (record) {
      if (record.pname) {
        this.$message.warning('该专利已关联项目，不能删除')
        return
      }
      this.$http.post('/patentDetail/delPatent', { id: record.id, patentNo: record.patentNo }).then(res => {
        if (res.data) {
          this.$message.success('删除成功')
          this.$refs.table.refresh(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
      this.selectedPatentNos = records.map((item) => { return item.patentNo })
    },
    onShowAttchment (record) {
      this.$refs.attachmentsModal.show(record.patentNo, record.patentName)
    }
  }
}
</script>
<style lang="less" scoped>
#scrollContent {
  // margin-top: 8px;
  height: calc(100% - 40px);
  overflow: auto;
  & /deep/ .ant-spin-container {
    height: 100%;
  }
}
</style>
