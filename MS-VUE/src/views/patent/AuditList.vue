<!--
 * @Author: ldx
 * @Date: 2021-06-08 15:15:49
 * @LastEditTime: 2021-07-26 11:09:15
 * @LastEditors: hck
 * @Description: 专利审批
 * @FilePath: \MS-VUE\src\views\patent\AuditList.vue
-->
<template>
  <a-card :bordered="false">
    <a-form ref="form" layout="inline">
      <a-form-item label="申请名称">
        <a-input v-model="queryParams.patentName" placeholder="请输入申请名称"/>
      </a-form-item>
      <a-form-item label="客户名称">
        <a-input v-model="queryParams.companyName" placeholder="请输入客户名称"/>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" v-if="$auth('patent:auditList:query')" @click="$refs.table.refresh(true)">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      border="full"
      queryUrl="/projectProgress/getAuditPatents"
      :params="queryParams"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      :checkbox-config="{ checkMethod: checCheckboxkMethod,showHeader: showHeaderChk}"
      @completed="({data:{data}})=>completed(data)"
      @checkbox-all="selectCheckBoxChange"
      @checkbox-change="selectCheckBoxChange"
      :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
    >
      <template v-slot:toolbar_buttons>
        <a-button type="primary" style="margin-right: 10px;" v-if="$auth('patent:auditList:audit')" @click="onBatchAudit" :disabled="selectedRows.length <=0 " >批量审核</a-button>
      </template>
      <vxe-table-column type="checkbox" width="60" align="center" header-align="center" fixed="left"></vxe-table-column>
      <vxe-table-column
        type="req"
        title="序号"
        width="60"
        align="center"
        header-align="center"
        fixed="left">
        <template #default="{row,rowIndex}">
          <a-badge :dot="row.hasPermission" :offset="[0,-7]">
          </a-badge>
          {{ rowIndex + 1 }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="客户名称"
        field="companyName"
        min-width="200"
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
        title="年份"
        field="year"
        min-width="100"
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
      <vxe-table-column
        title="状态"
        field="auditStatus"
        width="85"
        align="center"
        header-align="center"
        remoteSort>
        <template v-slot="{ row }">
          {{ row.auditStatus !==null ? statusMap[row.auditStatus] : '-' }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="交底书" field="docName" width="180" align="left" header-align="center">
        <template v-slot="{ row }">
          <span v-if="$auth('patent:applyList:download') && row.disclosureParperPath">
            <a @click="onDownloadFile(row.disclosureParperPath,1,row.id)" title="点击下载交底书">{{ getPatentFileName(row.disclosureParperPath) }}</a>
          </span>
          <span v-else>
            {{ getPatentFileName(row.disclosureParperPath) || '-' }}
          </span>
          <a-tooltip v-if="row.disclosureParperPath" style="cursor:pointer" placement="top" @click="onPreview(row.disclosureParperPath)">
            <template slot="title">
              <span>预览</span>
            </template>
            <a-icon type="eye" style="margin-left:5px" />
          </a-tooltip>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="发明人"
        field="inventor"
        width="100"
        align="left"
        header-align="center"
      ></vxe-table-column>
      <vxe-table-column title="发明人信息" field="docName" width="200" align="left" header-align="center">
        <template v-slot="{ row }">
          <span v-for="( file ,index) in getFiles(row.inventorInfo)" :key="index" style="margin-left:10px;" >
            <a @click="onDownloadFile(file.path,3,row.id)" title="点击下载" style="margin-left:3px;">{{ file.name }}</a>
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
        remoteSort></vxe-table-column>
    </ystable>
    <preview-modal ref="previewModal"/>
    <BatchAuditPatentModal ref="batchAuditPatentModal" @refresh="refresh"></BatchAuditPatentModal>
  </a-card>
</template>
<script>
import PreviewModal from '@/components/PreviewModal'
import ystable from '@/components/Table/ystable'
import { } from '@/api/patent/patent'
import { statusMap } from '@/utils/processDoc/auditStatus'
import BatchAuditPatentModal from './modules/BatchAuditPatentModal.vue'
export default {
  name: 'AuditList',
  components: {
    ystable,
    PreviewModal,
    BatchAuditPatentModal
  },
  data () {
    return {
      statusMap,
      selectedRows: [],
      queryParams: {
        patentName: undefined,
        companyName: undefined
      },
      showHeaderChk: true
    }
  },
  methods: {
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRows = records
    },
    onPreview (filePath) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      const filename = this.getPatentFileName(filePath)
      this.$refs.previewModal.show(filePath, filename)
    },
    onBatchAudit () {
      this.$refs.batchAuditPatentModal.show(this.selectedRows)
    },
    /**
     * @description: 下载文档
     * @param {*} record 行记录
     * @param {*} type type: 1 交底书 2.申请文件
     * @return {*}
     */
    onDownloadFile (filePath, type, id) {
      this.$exportData('/rsPatentPlan/download', { id, path: filePath, type }, undefined, this.$message)
    },
    getPatentFileName (path) {
      if (!path) { return '' }
      const nameIndex = path.lastIndexOf('/')
      const name = path.substring(nameIndex).substring(14)
      return name
    },
    checCheckboxkMethod ({ row }) {
      return row.hasPermission
    },
    refresh () {
      this.$refs.table.refresh(false)
    },
    completed (data) {
      this.selectedRows = []
      if (data && data.length) {
        this.showHeaderChk = data.some(item => { return item.hasPermission })
      } else {
        this.showHeaderChk = false
      }
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
    }
  }
}
</script>
<style lang="less" scoped>
</style>
