<template>
  <a-card :bodyStyle="{ height: '100%', overflow: 'auto'}" style="height: 100%;">
    <a-form layout="inline">
      <a-form-item label="申请名称" style="margin-button: 0px;">
        <a-input v-model="queryParams.patentName" placeholder="请输入申请名称"></a-input>
      </a-form-item>
      <a-form-item label="项目名称" style="margin-button: 0px;">
        <a-input v-model="queryParams.pname" placeholder="请输入项目名称"></a-input>
      </a-form-item>
      <a-form-item label="发明人" style="margin-button: 0px;">
        <a-input v-model="queryParams.inventor" placeholder="请输入发明人"></a-input>
      </a-form-item>
      <a-form-item style="margin-button: 0px;">
        <a-button
          type="primary"
          style="margin-right: 10px;"
          v-if="$auth('project:patentPlan:search')"
          @click="search(true)"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <div id="scrollContent">
      <ystable
        ref="table"
        border="full"
        max-height="100%"
        queryUrl="/patentPlan/queryPatentPlan"
        :params="getParams()"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :checkbox-config="{ checkMethod: checCheckboxkMethod ,showHeader: showHeaderChk}"
        @completed="({data:{data}})=>completed(data)"
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="$auth('project:patentPlan:add')"
            @click="addPatent(currentYear)"
          >添加</a-button>
          <a-button
            v-if="isMsUser && $auth('project:patentPlan:submit')"
            type="primary"
            style="margin-right: 10px;"
            @click="onSubmitPatents"
            :loading="btnLoading"
            :disabled="selectedRowKeys.length <= 0"
          >提交</a-button>
        </template>
        <vxe-table-column
          v-if="isMsUser"
          type="checkbox"
          width="40"
          align="center"
          header-align="center"
          fixed="left" >
        </vxe-table-column>
        <vxe-table-column
          type="seq"
          title="序号"
          width="60"
          align="center"
          header-align="center"
          fixed="left"
        >
        </vxe-table-column>
        <vxe-table-column
          title="申请名称"
          field="patentName"
          min-width="200"
          align="left"
          header-align="center"
          fixed="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="发明人"
          field="inventor"
          min-width="120"
          align="left"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="项目"
          field="rdTitle"
          :min-width="260"
          align="left"
          header-align="center"
          remoteSort
        >
          <template v-slot="{row}">

            {{ row.projectId > 0 ? row.rdTitle + '-' + row.pname : '其他' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="状态"
          field="status"
          width="120"
          align="center"
          header-align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            {{ statusMap[row.auditStatus] }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="交底书"
          field="docName"
          width="220"
          align="left"
          header-align="center"
        >
          <template v-slot="{ row }">
            <a
              v-if="$auth('project:patentPlan:download')"
              @click="downloadFile({id:row.id,path:row.disclosureParperPath },1)"
              title="点击下载交底书"
            >{{ row.docName }}</a>
            <span v-else>{{ row.docName }}</span>
            <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({name: row.docName, path: row.disclosureParperPath })" v-if="row.disclosureParperPath" >
              <template slot="title">
                <span>预览</span>
              </template>
              <a-icon type="eye" style="margin-left:5px" />
            </a-tooltip>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="发明人信息"
          width="220"
          align="left"
          header-align="center"
        >
          <template v-slot="{ row }">
            <span v-for="(file,index) in getFiles(row.inventorInfo)" :key="index" style="margin-right: 10px;">
              <a
                v-if="$auth('project:patentPlan:download')"
                @click="downloadFile({id:row.id,path: file.filePath},3)"
                title="点击下载交底书"
              >{{ file.fileName }}</a>
              <span v-else>{{ file.fileName }}</span>
              <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({name: file.fileName, path: file.filePath })" v-if="file.filePath" >
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin-left:5px" />
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="描述"
          field="description"
          width="250"
          align="left"
          header-align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column title="操作" width="210" align="center" header-align="center" fixed="right">
          <template v-slot="{ row }">
            <span v-if="$auth('project:patentPlan:edit')">
              <a @click="editPatent(row,currentYear)" :disabled="!isEditStatus(row.auditStatus)">编辑</a>
            </span>
            <span v-if="$auth('project:patentPlan:view')">
              <a-divider type="vertical" ></a-divider>
              <a @click="onShowApplyData(row)" :disabled="!(row.auditStatus === 1)">申请文件</a>
            </span>
            <span v-if="$auth('project:patentPlan:del')">
              <a-divider type="vertical" />
              <a-popconfirm title="是否确定删除?" @confirm="delPatent(row)">
                <a :disabled="!isEditStatus(row.auditStatus)">删除</a>
              </a-popconfirm>
            </span>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
    <add-patent-plan-modal ref="addModal" @ok="search"></add-patent-plan-modal>
    <preview-modal ref="previewModal"></preview-modal>
    <ApplyDataDrawer ref="applyDataDrawer" :openPreviewModal="openPreviewModal"></ApplyDataDrawer>
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import AddPatentPlanModal from './modules/AddPatentPlanModal'
import { PreviewModal } from '@/components'
import yearMiXin from '@/utils/yearMixin'
import { submitPatents } from '@/api/patent/patent'
import { isEditStatus, statusMap, isMsUser } from '@/utils/processDoc/auditStatus'
import ApplyDataDrawer from './modules/ApplyDataDrawer'
const statusAry = ['待申请', '申请中', '已申请', '已驳回']
export default {
  mixins: [yearMiXin],
  components: {
    ystable,
    AddPatentPlanModal,
    PreviewModal,
    ApplyDataDrawer
  },
  watch: {
    horizontal: function (newHorizontal) {
      if (localStorage) {
        localStorage.setItem('horizontal', newHorizontal)
      }
    }
  },
  data () {
    return {
      statusMap,
      statusAry,
      queryParams: {
        patentName: undefined,
        inventor: undefined,
        pname: undefined
        // statusName: undefined
      },
      selectedRowKeys: [],
      btnLoading: false,
      showHeaderChk: true
    }
  },
  computed: {
    isMsUser () {
      return isMsUser()
    }
  },
  methods: {
    isEditStatus,
    getParams () {
      this.queryParams.year = this.currentYear
      return this.queryParams
    },
    search (refresh) {
      this.queryParams.year = this.$store.state.currentYear
      this.$refs.table.refresh(refresh)
    },
    selectChange (val) {
      this.queryParams.statusName = val
    },
    addPatent (currentYear) {
      this.$refs.addModal.add(currentYear)
    },
    editPatent (record, currentYear) {
      this.$refs.addModal.edit(record, currentYear)
    },
    activePatent (record, currentYear) {
      this.$confirm({
        title: '您确定要提交？',
        onOk: () => { this.handlerSubmitPatents([record.id]) },
        onCancel () {}
      })
    },
    delPatent (record) {
      this.$http.post('/patentPlan/delPatentPlan', { id: record.id }).then(res => {
        if (res.success && res.data) {
          this.search(false)
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
      })
    },
    downloadFile (record, type) {
      this.$exportData('/patentPlan/download', { id: record.id, path: record.path, type }, undefined, this.$message)
    },
    onSubmitPatents () {
      const params = this.selectedRowKeys
      this.$confirm({
        title: '您确定要提交？',
        onOk: () => { this.handlerSubmitPatents(params) },
        onCancel () {}
      })
    },
    handlerSubmitPatents (ids) {
      this.btnLoading = true
      submitPatents(ids).then(data => {
        if (data) {
          this.$message.success('操作成功！')
          this.search(true)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.btnLoading = false
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
    },
    onPreview (record) {
      if (record.disclosureParperPath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(record.path, record.name !== undefined ? record.name : '')
    },
    openPreviewModal (path, name) {
      if (path === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(path, name)
    },
    onShowApplyData (record) {
      this.$refs.applyDataDrawer.show(record)
    },
    checCheckboxkMethod ({ row }) {
      return isEditStatus(row.auditStatus)
    },
    completed (data) {
      this.selectedRowKeys = []
      if (data && data.length) {
        this.showHeaderChk = data.some(item => { return isEditStatus(item.auditStatus) })
      } else {
        this.showHeaderChk = false
      }
    },
    getFiles (filePathStr) {
      if (!filePathStr) { return [] }
      const filePaths = filePathStr.split(',')
      if (!filePaths.length) { return [] }
      const fileAry = []
      filePaths.forEach(filePath => {
        const nameIndex = filePath.lastIndexOf('/')
        const fileName = filePath.substring(nameIndex).substring(14)
        fileAry.push({ fileName, filePath })
      })
      return fileAry
    },
    getPatentFileName (path) {
      if (!path) { return '' }
      const nameIndex = path.lastIndexOf('/')
      const name = path.substring(nameIndex).substring(14)
      return name
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
