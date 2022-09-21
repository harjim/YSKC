<template>
  <a-card :bordered="false">
    <a-form ref="form" layout="inline">
      <a-form-item label="专利号">
        <a-input v-model="queryParams.patentNo" placeholder="请输入专利号"/>
      </a-form-item>
      <a-form-item label="申请名称">
        <a-input v-model="queryParams.patentName" placeholder="请输入申请名称"/>
      </a-form-item>
      <a-form-item label="客户名称">
        <a-input v-model="queryParams.companyName" placeholder="请输入客户名称"/>
      </a-form-item>
      <a-form-item label="代理人">
        <a-select
          style="width:150px"
          :allowClear="true"
          :value="queryParams.masterId"
          placeholder="请选择代理人"
          @change="masterSelectChange">
          <a-select-option v-for="master in masterList" :key="master.id" :value="master.id">
            {{ master.masterName }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" v-if="$auth('patent:applyList:search')" @click="$refs.table.refresh(true)">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      border="full"
      queryUrl="/rsPatentPlan/queryPatentPlan"
      :params="queryParams"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      @completed="()=>selectedRowKeys=[]"
      @checkbox-all="selectCheckBoxChange"
      @checkbox-change="selectCheckBoxChange"
      :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
    >
      <template v-slot:toolbar_buttons>
        <a-button type="primary" style="margin-right: 10px;" v-if="$auth('patent:applyList:allocation')" @click="masterClick()" :disabled="selectedRowKeys.length <= 0">
          分配代理人
        </a-button>
      </template>
      <vxe-table-column type="checkbox" width="60" align="center" header-align="center" fixed="left"></vxe-table-column>
      <vxe-table-column
        title="客户名称"
        field="companyName"
        min-width="200"
        align="left"
        header-align="center"
        remoteSort></vxe-table-column>
      <vxe-table-column
        title="所属部门"
        field="deptName"
        min-width="150"
        align="left"
        header-align="center"
        remoteSort></vxe-table-column>
      <vxe-table-column
        title="工程师"
        field="creator"
        min-width="120"
        align="left"
        header-align="center"
        remoteSort></vxe-table-column>
      <vxe-table-column
        title="业务员"
        field="owner"
        min-width="120"
        align="left"
        header-align="center"
        remoteSort></vxe-table-column>
      <vxe-table-column
        title="申请名称"
        field="patentName"
        min-width="200"
        align="left"
        header-align="center"
        remoteSort></vxe-table-column>
      <vxe-table-column
        title="定稿名称"
        field="finalizedName"
        width="150"
        align="left"
        header-align="center"
      >
        <template #default="{row}">
          <template v-if="$auth('patent:applyList:setFinalizedName')">
            <a v-if="row.finalizedName" title="点击修改定稿名称" @click="setFinalizedName(true,row)">{{ row.finalizedName }}</a>
            <a v-else title="设置定稿名称" @click="setFinalizedName(false,row)">--</a>
          </template>
          <template v-else>
            {{ row.finalizedName ? row.finalizedName: '-' }}
          </template>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="专利号"
        field="patentNo"
        width="200"
        align="left"
        header-align="center"
        remoteSort>
        <template v-slot="{ row }">
          <span v-if="$auth('patent:applyList:actiovePatent')">
            <a v-if="!row.patentNo" style="color:#FF0000" title="点击关联专利" @click="$refs.addPatentModal.addAndchengeStatus(row)">关联专利</a>
            <a v-else @click="$refs.addPatentModal.addAndchengeStatus(row)" > {{ row.patentNo }} </a>
          </span>
          <span v-else>
            <span title="点击修改专利">{{ row.patentNo || '--' }}</span>
          </span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="代理人"
        field="masterName"
        width="200"
        align="left"
        header-align="center">
        <template v-slot="{ row }">
          <span v-if="$auth('patent:applyList:allocation')">
            <a @click="showMasterModal(row)" v-if="row.masterName" title="点击分配代理人">{{ row.masterName }}</a>
            <a @click="showMasterModal(row)" v-else style="color:#FF0000" title="点击分配代理人">分配代理人</a>
          </span>
          <span v-else>
            <span style="color:#000">{{ row.masterName || '--' }}</span>
          </span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="年份"
        field="year"
        width="100"
        align="center"
        header-align="center"
        remoteSort></vxe-table-column>
      <vxe-table-column
        title="项目名称"
        field="pname"
        width="200"
        align="left"
        header-align="center"
        remoteSort>
        <template v-slot="{row}">
          {{ row.projectId>0 ? row.rdTitle +'-'+ row.pname :'其他' }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="交底书" field="docName" width="230" align="left" header-align="center">
        <template v-slot="{ row }">
          <span v-if="$auth('patent:applyList:download')">
            <a @click="downloadFile(row.path,1,row.id)" title="点击下载交底书">{{ row.docName }}</a>
          </span>
          <span v-else>
            {{ row.docName }}
          </span>
          <a-tooltip style="cursor:pointer" placement="top" @click="onPreview(row.path,row.docName)">
            <template slot="title">
              <span>预览</span>
            </template>
            <a-icon type="eye" style="margin-left:5px" />
          </a-tooltip>
        </template>
      </vxe-table-column>
      <vxe-table-column title="申请文件" field="docName" width="250" align="left" header-align="center">
        <template v-slot="{ row }">
          <span v-for="( file ,index) in getFiles(row.filepath)" :key="index" style="margin-left:10px;" >
            <a @click="downloadFile(file.path,2,row.id)" title="点击下载" style="margin-left:3px;">{{ file.name }}</a>
            <a-icon title="预览" type="eye" style="margin-left:5px" @click="onPreview(file.path,file.name)" />
          </span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="描述"
        field="description"
        width="200"
        align="left"
        header-align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="建议"
        field="opinion"
        width="250"
        align="left"
        header-align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="操作"
        width="100"
        align="center"
        header-align="center"
        fixed="right"
      >
        <template #default="{row}">
          <a @click="onShowAttachments(row)">附件列表</a>
        </template>
      </vxe-table-column>
    </ystable>
    <addPatent-modal ref="addPatentModal" @ok="handleOk"></addPatent-modal>
    <allocation-master-modal ref="MasterModal" @ok="masterHandleOk"></allocation-master-modal>
    <preview-modal ref="previewModal"/>
    <upload-file ref="uploadFile" action="/rsPatentPlan/upload" @success="success" :isShowFileName="false"></upload-file>
    <attachments-modal ref="attachmentsModal" @success="success" ></attachments-modal>
    <set-finalized-name-modal ref="setFinalizedNameModal" @success="success"></set-finalized-name-modal>
  </a-card>
</template>

<script>
import PreviewModal from '@/components/PreviewModal'
import UploadFile from '@/components/UploadFile'
import ystable from '@/components/Table/ystable'
import AddPatentModal from './modules/AddPatentModal'
import { delUploadFile } from '@/api/patent/patent'
import AllocationMasterModal from './modules/AllocationMasterModal'
import AttachmentsModal from './modules/AttachmentsModal'
import SetFinalizedNameModal from './modules/SetFinalizedNameModal'
const statusAry = ['待申请', '申请中', '已申请', '已驳回']
export default {
  name: 'ApplyList',
  components: {
    ystable,
    PreviewModal,
    AddPatentModal,
    AllocationMasterModal,
    UploadFile,
    AttachmentsModal,
    SetFinalizedNameModal
  },
  data () {
    return {
      statusAry,
      queryParams: {
        masterId: undefined
      },
      selectedRowKeys: [],
      masterList: []
    }
  },
  mounted () {
    this.queryMasters()
  },
  methods: {
    masterSelectChange (val) {
      this.queryParams.masterId = val
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
    },
    downloadFile (path, type = 1, id) {
      this.$exportData('/rsPatentPlan/download', { id, path, type }, undefined, this.$message)
    },
    handleOk (result) {
      if (result) {
        this.$message.success('操作成功')
        this.$refs.table.refresh(false)
      } else {
        this.$message.error('操作失败')
      }
    },
    masterHandleOk () {
      this.$refs.table.refresh(false)
      this.selectedRowKeys = []
    },
    isDisabled (status) {
      if (status === 1) {
        return false
      } else {
        return true
      }
    },
    queryMasters () {
      this.$http.get('/patentMaster/getMasterList').then(res => {
        if (res.success) {
          this.masterList = res.data
        }
      })
    },
    isMasterClick (row) {
      if (row.status <= 1) {
        return true
      } else {
        return false
      }
    },
    showMasterModal (obj) {
      let ids = []
      ids = [obj.id]
      this.$refs.MasterModal.show(ids)
    },
    masterClick () {
      this.$refs.MasterModal.show(this.selectedRowKeys)
    },
    onPreview (filePath, filename) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(filePath, filename)
    },
    onUploadData (record) {
      this.$refs.uploadFile.show({ patentId: record.id })
    },
    getFiles (filePathStr) {
      if (!filePathStr) { return }
      const filePaths = filePathStr.split(',')
      if (!filePaths.length) { return [] }
      const fileAry = []
      filePaths.forEach(path => {
        const nameIndex = path.lastIndexOf('/')
        const name = path.substring(nameIndex).substring(14)
        fileAry.push({ name, path })
      })
      return fileAry
    },
    delFile (id, delPath, row) {
      const filePaths = row.filepath.split(',')
      const temp = []
      filePaths.forEach(path => {
        if (path !== delPath) {
          temp.push(path)
        }
      })
      const filepath = temp.join(',')
      const param = { id, filepath }
      delUploadFile(param).then(data => {
        if (data) {
          row.filepath = filepath
          this.$message.success('操作成功！')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
    },
    success () {
      this.$refs.table.refresh(false)
    },
    onShowAttachments (row) {
      const { attachmentsModal: modal } = this.$refs
      modal.show(row)
    },
    setFinalizedName (isEdit, record) {
      const { setFinalizedNameModal: modal } = this.$refs
      isEdit ? modal.edit(record) : modal.show(record)
    }
  }
}
</script>

<style>

</style>
