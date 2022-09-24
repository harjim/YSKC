<template>
  <a-card :bodyStyle="{ height: '100%', overflow: 'auto'}" style="height: 100%;">
    <a-form layout="inline">
      <a-form-item label="登记号">
        <a-input v-model="queryParams.registerNo" placeholder="请输入登记号" allowClear></a-input>
      </a-form-item>
      <a-form-item label="软件名称">
        <a-input v-model="queryParams.softName" placeholder="请输入软件名称" allowClear></a-input>
      </a-form-item>
      <a-form-item label="软件著作人">
        <a-input v-model="queryParams.ownerName" placeholder="请输入软件著作人" allowClear></a-input>
      </a-form-item>
      <a-form-item label="证书号">
        <a-input v-model="queryParams.certificateNo" placeholder="请输入证书号" allowClear></a-input>
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          style="margin-right: 10px;"
          v-if="$auth('project:softRegistration:search')"
          @click="search(true)"
        >查询</a-button>
      </a-form-item>
    </a-form>

    <div id="scrollContent">
      <ystable
        ref="table"
        border="full"
        max-height="100%"
        queryUrl="/softRegistration/getList"
        :params="queryParams"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        @completed="({data:{data}})=>completed(data)"
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
      >
        <!-- 操作 -->
        <template v-slot:toolbar_buttons>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="$auth('project:softRegistration:add')"
            @click="handleAdd()"
          >添加</a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="$auth('project:softRegistration:import')"
            @click="handleImport()"
          >导入</a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="$auth('project:softRegistration:del')"
            @click="handleBatchDelete()"
            :disabled="selectedRowKeys.length <= 0"
          >删除</a-button>
        </template>
        <!-- 列 -->
        <vxe-table-column
          type="checkbox"
          width="40"
          align="center"
          header-align="center"
          fixed="left" >
        </vxe-table-column>
        <!-- <vxe-table-column
          type="seq"
          title="序号"
          width="60"
          align="center"
          header-align="center"
        >
        </vxe-table-column> -->
        <vxe-table-column field="registerNo" title="登记号" width="150" header-align="center" fixed="left"></vxe-table-column>
        <vxe-table-column field="softName" title="软件名称" width="150" header-align="center"></vxe-table-column>
        <vxe-table-column field="rdTitle" title="关联项目" width="150" header-align="center"></vxe-table-column>
        <vxe-table-column field="ownerName" title="软件著作人" width="150" header-align="center"></vxe-table-column>
        <vxe-table-column field="issueDate" title="发布日期" width="150" header-align="center"></vxe-table-column>
        <vxe-table-column field="certificateNo" title="证书号" width="150" header-align="center"></vxe-table-column>
        <vxe-table-column field="source" title="来源" width="150" header-align="center">
          <template v-slot="{ row }">
            {{ sourceMap[row.source] }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="fileList" title="附件" width="250" header-align="center">
          <template v-slot="{ row }">
            <span style="margin-right: 8px" v-for="(file,index) in transFormStrToAry(row.fileList)" :key="index">
              <a
                v-if="$auth('project:softRegistration:download')"
                @click="handleDownload({path: file.url,name: file.name })"
                title="点击下载"
              >{{ file.name }}</a>
              <span v-else>{{ file.name }}</span>
              <a-tooltip style="cursor:pointer" placement="top" @click="handlePreview({path: file.url,name: file.name })" v-if="file.url && $auth('project:softRegistration:preview')">
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin:0 5px" />
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="action" title="操作" width="100" header-align="center" fixed="right">
          <template v-slot="{ row }">
            <template v-if="$auth('project:softRegistration:edit')">
              <a @click="handleEdit(row)">编辑</a>
            </template>
            <template v-if="$auth('project:softRegistration:del')">
              <a-divider type="vertical"></a-divider>
              <a-popconfirm title="是否确定删除?" @confirm="handleDelete([row.id])">
                <a>删除</a>
              </a-popconfirm>
            </template>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
    <!-- 添加编辑 -->
    <addModal ref="addModal" :rdProjectList="rdProjectList" @ok="search"></addModal>
    <!-- 导入 -->
    <upload-modal
      :showProgress="true"
      :sampleData="sampleData"
      paramKey="tableField"
      title="导入软件著作登记"
      templateName="软件著作登记列表模板"
      ref="uploadModal"
      action="/doc/softRegistration/import"
      @onSuccess="importSuccess"
      @error="importError"
    />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import addModal from './modules/AddSoftRegistrationModal.vue'
import { UploadModal } from '@/components'

export default {
  components: {
    ystable,
    addModal,
    UploadModal
  },
  data () {
    return {
      queryParams: {},
      tableField: {
        tableId: 'softRegistrationTable',
        fieldTitleObject: {
          registerNo: { title: '登记号', required: true, defaultTitle: '登记号', importField: true },
          softName: { title: '软件名称', required: true, defaultTitle: '软件名称', importField: true },
          ownerName: { title: '软件著作人', defaultTitle: '软件著作人', importField: true },
          issueDate: { title: '发布日期', required: true, defaultTitle: '发布日期', importField: true },
          certificateNo: { title: '证书号', defaultTitle: '证书号', importField: true },
          source: { title: '来源', required: true, defaultTitle: '来源', importField: true }
        }
      },
      sampleData: [{
        issueDate: '格式："年-月-日"，例如：2019-10-11',
        source: '自主/购买'
      }],
      selectedRowKeys: [],
      rdProjectList: [],
      // 来源类型
      sourceMap: { 0: '自主', 1: '购买' }
    }
  },
  created () {
    this.getRdProjectList()
  },
  methods: {
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    // 添加
    handleAdd () {
      this.$refs.addModal.add()
    },
    // 编辑
    handleEdit (row) {
      this.$refs.addModal.edit(row)
    },
    // 删除
    handleDelete (ids) {
      this.$http.post('/softRegistration/del', ids).then(res => {
        if (res.success && res.data) {
          this.search(false)
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
      })
    },
    handleBatchDelete () {
      const _this = this
      this.$confirm({
        title: '您确定要删除选中的数据吗?',
        onOk () {
          _this.handleDelete(_this.selectedRowKeys)
        }
      })
    },
    // 导入
    handleImport () {
      this.$refs.uploadModal.show(this.tableField)
    },
    // 下载
    handleDownload ({ name, path }) {
      this.$exportData('/beian/download', { filePath: path }, name, this.$message)
    },
    // 预览
    handlePreview ({ name, path }) {
      if (path === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$preview({
        filePath: path,
        docName: name || '',
        visible: true
      })
    },
    transFormStrToAry (filePaths) {
      const files = []
      if (filePaths != null) {
        filePaths.forEach(path => {
          const name = path.substring(path.lastIndexOf('/') + 14)
          files.push({
            uid: path,
            name,
            status: 'done',
            url: path
          })
        })
      }
      return files
    },
    importSuccess (fileName, resData) {
      if (resData) {
        this.$message.success(fileName + '导入成功')
      }
      this.$refs.table.refresh(true)
    },
    importError (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
    },
    // 获取rd项目列表
    getRdProjectList () {
      this.$http.get('/project/getSelectList', {
        params: {
          sign: 1
        }
      }).then(res => {
        if (res.success && res.data) {
          this.rdProjectList = res.data
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      })
    },
    completed () {
      this.selectedRowKeys = []
    }
  }
}
</script>

<style lang="less" scoped>
#scrollContent {
  height: calc(100% - 40px);
  overflow: auto;
}
</style>
