<template>
  <div v-if="auth.search">
    <div class="form_wrap">
      <a-form layout="inline">
        <a-form-item label="标题">
          <a-input placeholder="请输入标题" v-model="queryParams.title"></a-input>
        </a-form-item>
        <a-form-item label="提案类型">
          <a-select style="width:165px;" placeholder="请选择提案类型" v-model="queryParams.type" allowClear>
            <a-select-option v-for="(name, index) in types" :key="index" :value="index">{{ name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="备注">
          <a-input placeholder="请输入备注" v-model="queryParams.remark"></a-input>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="search(true)">查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <ystable
        ref="table"
        queryUrl="/proposalManagement/getList"
        :params="queryParams"
        border="full"
        header-align="center"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        hieght="100%"
        :toolbar="{ custom: true, zoom: true, refresh: true }"
        :checkbox-config="{ checkMethod: checCheckboxkMethod, showHeader: showHeaderChk }"
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        @completed="({ data: { data } }) => completed(data)"
      >
        <template #buttons>
          <a-button
            type="primary"
            @click="onAdd"
            style="margin-right:10px;"
            :disabled="btnStatus"
            v-if="auth.save"
          >添加</a-button
          >
          <a-button
            type="primary"
            @click="onBatchDel"
            style="margin-right:10px;"
            :disabled="btnStatus || selectedRows.length <= 0"
            v-if="auth.del"
          >删除</a-button
          >
          <a-button
            type="primary"
            @click="onBatchAudit"
            style="margin-right:10px;"
            :disabled="btnStatus || selectedRows.length <= 0"
            v-if="auth.submit && isMsUser"
          >提交</a-button
          >
        </template>
        <vxe-table-column type="checkbox" width="60" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column type="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column field="title" title="标题" width="120" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column field="type" title="提案类型" width="120" align="center" header-align="center">
          <template #default="{row}">
            {{ types[row.type] }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          v-if="isMsUser"
          field="status"
          title="状态"
          width="90"
          align="center"
          header-align="center">
          <template v-slot="{ row }">
            {{ statusMap[row.status] }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="year" title="年份" width="90" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column field="rdTitle" title="RD" width="120" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column title="附件" min-width="300" align="left" header-align="center">
          <template #default="{row}">
            <span v-for="(file, index) in transFormStrToAry(row.filePath)" :key="index">
              <a
                v-if="auth.download"
                @click="onDownloadFile({ path: file.url, name: file.name })"
                title="点击下载需求文档"
              >{{ file.name }}</a>
              <span v-else>{{ file.name }}</span>
              <a-tooltip
                style="cursor:pointer"
                placement="top"
                @click="onPreview({ path: file.url, name: file.name })"
                v-if="file.url && auth.preview"
              >
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin:0 5px" /><br/>
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="remark" title="备注" width="120" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column title="操作" width="100" align="center" header-align="center">
          <template #default="{row}">
            <a @click="onEdit(row)" :disabled="btnStatus || !isEditStatus(row.status)" v-if="auth.save">编辑</a>
            <a-divider type="vertical" v-if="auth.del"></a-divider>
            <a-popconfirm title="您确定要删除？" @confirm="onDel(row)" :disabled="btnStatus" v-if="auth.del">
              <a :disabled="btnStatus || !isEditStatus(row.status)">删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
      <a-modal
        :title="titleModal"
        :width="500"
        :visible="isVisibleModal"
        :afterClose="afterCloseModal"
        :maskClosable="false"
        @cancel="isVisibleModal = false"
        @ok="handleSubmit"
        :confirmLoading="btnStatus"
        :bodyStyle="{ padding: '12px', maxHeight: '70vh', overflow: 'auto' }"
      >
        <a-form :form="form">
          <a-row :gutter="24">
            <a-col :span="24">
              <a-form-item label="标题" :labelCol="{ sm: { span: 4 } }" :wrapperCol="{ sm: { span: 19 } }">
                <a-input placeholder="请输入标题" v-decorator="['title', { rules: [{ required: true, message: '请输入标题' }] }]"></a-input>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="24">
              <a-form-item label="提案类型" :labelCol="{ sm: { span: 4 } }" :wrapperCol="{ sm: { span: 19 } }">
                <a-select
                  placeholder="请选择提案类型"
                  v-decorator="['type', { rules: [{ required: true, message: '请选择提案类型' }] }]"
                >
                  <a-select-option v-for="(name, index) in types" :key="index" :value="index">{{
                    name
                  }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="24">
              <a-form-item label="关联RD" :labelCol="{ sm: { span: 4 } }" :wrapperCol="{ sm: { span: 19 } }">
                <a-select placeholder="请选择关联RD" v-decorator="['projectId', { rules: [{ required: false }] }]" allowClear>
                  <a-select-option
                    v-for="item in rdList"
                    :key="item.id"
                    :value="item.id"
                  >{{ item.rdTitle }}-{{ item.pname }}</a-select-option
                  >
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="24">
              <a-form-item label="附件" :labelCol="{ sm: { span: 4 } }" :wrapperCol="{ sm: { span: 19 } }">
                <ys-upload
                  :multiple="true"
                  :lists="fileLists"
                  url="proposalManagement/upload"
                  :params="{ dir: '/roposalManagement/' }"
                  @path="getPath"
                  @change="onFileListsChange"
                  :previwFun="onPreview"
                  :downloadFun="onDownloadFile"
                  v-decorator="['files', { initialValue: [], rules: [{ required: true, message: '请上传附件' }] }]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="24">
              <a-form-item
                :extra="onComputeTextarea('remark', 200)"
                label="备注"
                :labelCol="{ sm: { span: 4 } }"
                :wrapperCol="{ sm: { span: 19 } }"
              >
                <a-textarea
                  :rows="3"
                  v-decorator="['remark', { rules: [{ required: false, message: '请输入备注' }] }]"
                ></a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-modal>
      <preview-modal ref="previewModal"></preview-modal>
    </a-spin>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import YsUpload from '@/components/YsUpload'
import { PreviewModal } from '@/components'
import { getAuth } from '@/utils/util'
import { saveProposal, delProposal, auditProposal } from '@/api/proposalManagement'
import yearMixin from '@/utils/yearMixin'
import { isEditStatus, statusMap, isMsUser } from '@/utils/processDoc/auditStatus'
import { isEmpty } from 'lodash'
const types = ['立项素材', '现场图片', '立项对接表', '体系文件提案', '专利提案', '费用规划', '其他提案']
export default {
  name: 'ProposalManagement',
  components: {
    ystable,
    YsUpload,
    PreviewModal
  },
  computed: {
    isMsUser () {
      return isMsUser()
    }
  },
  mixins: [yearMixin],
  data () {
    return {
      statusMap,
      types,
      spinning: false,
      queryParams: {},
      auth: {},
      selectedRows: [],
      form: this.$form.createForm(this),
      titleModal: '添加提案附件',
      isVisibleModal: false,
      fileLists: [],
      path: '',
      btnStatus: false,
      updateId: undefined,
      showHeaderChk: true,
      hasRdList: false,
      rdList: []
    }
  },
  watch: {
    currentYear: {
      handler () {
        this.hasRdList = false
      }
    }
  },
  mounted () {
    this.queryParams.year = this.currentYear
    const keys = ['search', 'save', 'del', 'upload', 'download', 'preview', 'submit']
    this.auth = getAuth('project:proposalManagement:', keys)
  },
  methods: {
    isEditStatus,
    onAdd () {
      this.isVisibleModal = true
      this.fileLists = []
      this.getRDList()
    },
    onEdit (record) {
      this.updateId = record.id
      this.getRDList()
      const formData = {
        title: record.title,
        remark: record.remark,
        type: record.type,
        projectId: record.projectId || undefined,
        files: this.transFormStrToAry(record.filePath)
      }
      this.isVisibleModal = true
      this.$nextTick(() => {
        this.form.setFieldsValue(formData)
      })
    },
    onDel (record) {
      this.handleDel([record])
    },
    onBatchDel () {
      this.$confirm({
        title: '您确定要删除选中的记录？',
        onOk: () => {
          this.handleDel(this.selectedRows)
        }
      })
    },
    handleDel (params) {
      this.btnStatus = true
      delProposal(params)
        .then(data => {
          if (data) {
            this.search()
            this.$message.success('操作成功！')
          }
        })
        .catch(error => {
          this.message.error(error.message || '系统异常，请联系管理员！')
        })
        .finally(() => {
          this.btnStatus = false
        })
    },
    onBatchAudit () {
      this.$confirm({
        title: '是否确定提交?',
        onOk: () => {
          this.handleAudit(this.selectedRows)
        }
      })
    },
    handleAudit (params) {
      this.btnStatus = true
      const requestParams = {
        moduleId: 11,
        proposalIds: params.map(value => value.id)
      }
      auditProposal(requestParams)
        .then(data => {
          if (data) {
            this.search()
            this.$message.success('操作成功！')
          }
        })
        .catch(error => {
          this.message.error(error.message || '系统异常，请联系管理员！')
        })
        .finally(() => {
          this.btnStatus = false
        })
    },
    search (refresh) {
      this.queryParams.year = this.currentYear
      if (this.$refs.table) {
        this.$refs.table.refresh(refresh)
      }
    },
    checCheckboxkMethod ({ row }) {
      return isEditStatus(row.status)
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRows = records
    },
    completed (data) {
      this.selectedRows = []
      if (data && data.length) {
        this.showHeaderChk = data.some(item => {
          return isEditStatus(item.status)
        })
      } else {
        this.showHeaderChk = false
      }
    },
    afterCloseModal () {
      this.isVisibleModal = false
      this.path = ''
      this.updateId = undefined
      this.form.resetFields()
    },
    onDownloadFile ({ name, path }) {
      this.$exportData('/beian/download', { filePath: path }, name, this.$message)
    },
    onPreview ({ name, path }) {
      if (path === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(path, name)
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (errors) return
        const params = { ...values }
        params.filePath = this.path
        params.year = this.currentYear
        this.btnStatus = true
        if (this.updateId) {
          params.id = this.updateId
        }
        saveProposal(params)
          .then(data => {
            if (data) {
              this.isVisibleModal = false
              this.search(true)
              this.$message.success('操作成功！')
            }
          })
          .catch(error => {
            this.message.error(error.message || '系统异常，请联系管理员！')
          })
          .finally(() => {
            this.btnStatus = false
          })
      })
    },
    transFormStrToAry (filePaths) {
      const files = []
      filePaths.split(',').forEach(path => {
        const name = path.substring(path.lastIndexOf('/') + 14)
        files.push({
          uid: path,
          name,
          status: 'done',
          url: path
        })
      })
      return files
    },
    // 计算文本域的个数
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    },
    getPath (path) {
      this.path = path
    },
    isEmpty,
    onFileListsChange (value) {
      if (!isEmpty(this.form.getFieldValue('title'))) {
        return
      }
      if (value.length > 0) {
        const title = value[0].name.slice(0, value[0].name.lastIndexOf('.'))
        this.form.setFieldsValue({
          title: title
        })
      }
    },
    getRDList () {
      if (this.hasRdList) {
        return
      }
      this.$http.get('/project/getSimpleList', { params: { sign: 0, year: this.currentYear } }).then(res => {
        if (res.success) {
          this.rdList = res.data || []
          this.hasRdList = true
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      })
    }
  }
}
</script>
