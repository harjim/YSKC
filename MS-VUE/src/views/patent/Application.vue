<!--
 * @Author: lzh
 * @Date: 2022-02-09 08:35:36
 * @LastEditors: zdf
 * @LastEditTime: 2022-08-05 19:39:06
 * @Description: 需求列表(新)
 * @FilePath: \MS-VUE\src\views\patent\Application.vue
-->
<template>
  <a-card v-if="auth.search">
    <TableSearchbar :colNum="4" @search="query(true)">
      <a-form-item label="年份">
        <year-select placeholder="请选择年份" v-model="queryParams.year" />
      </a-form-item>
      <a-form-item label="客户名称">
        <a-input
          placeholder="请输入客户名称"
          v-model="queryParams.customerName"
        />
      </a-form-item>
      <a-form-item label="所属集团">
        <a-input
          placeholder="请输入所属集团"
          v-model="queryParams.groupName"
        />
      </a-form-item>
      <a-form-item label="业务员">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="false"
          placeholder="请输入业务员"
          v-model="queryOwner"
        />
      </a-form-item>
      <a-form-item label="工程师">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="false"
          placeholder="请输入工程师"
          v-model="queryTech"
        />
      </a-form-item>
      <a-form-item label="类型">
        <a-select :allowClear="true" v-model="queryParams.type" placeholder="请选择类型">
          <a-select-option v-for="(value, key) in typeMap" :key="key">{{ value }}</a-select-option>
        </a-select>
      </a-form-item>
    </TableSearchbar>
    <ystable
      ref="table"
      queryUrl="/patentAppliaction/getList"
      :params="getParams()"
      header-align="center"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      :toolbar="{ custom: true, zoom: true, refresh: true }"
      @checkbox-all="selectCheckBoxChange"
      @checkbox-change="selectCheckBoxChange"
      @completed="completed"
    >
      <template #buttons>
        <a-button
          type="primary"
          style="margin-right: 10px"
          @click="$refs.staffModal.showModal(selectedRows.map((a) => a.id), 0)"
          :disabled="selectedRows.length <= 0"
          v-if="auth.setOwner"
        >
          分配业务员
        </a-button>
        <a-button
          type="primary"
          style="margin-right: 10px"
          @click="$refs.staffModal.showModal(selectedRows.map((a) => a.id), 1)"
          :disabled="selectedRows.length <= 0"
          v-if="auth.setTech"
        >
          分配工程师
        </a-button>
      </template>
      <vxe-table-column type="checkbox" width="40" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column type="expand" title="序号" width="80" align="left" header-align="center">
        <template #default="{rowIndex}">
          {{ pageInfo && (pageInfo.pageNo - 1) * pageInfo.pageSize + (rowIndex + 1) }}
        </template>
        <template #content="{row: pRow}">
          <ystable
            queryUrl="/patentAppliaction/getPlanList"
            :params="{ demandId: pRow.id }"
            header-align="center"
            highlight-hover-row
            show-overflow
            resizable
            auto-resize
            :columns="expandTableColumns"
          >
            <template #patentType="{row}">
              {{ row.patentType | getLabel(patentTypes) }}
            </template>
            <template #planType="{row}">
              {{ row.planType | getLabel(planTypes) }}
            </template>
            <template #nodeNumber="{row}">
              {{ (row.nodeNumber || 0) | getLabel(patentPlanProcessType) }}
            </template>
            <template #submittedDate="{row}">
              {{ row.submittedDate && row.submittedDate.slice(0, 10) }}
            </template>
            <template #acceptNoticeDate="{row}">
              {{ row.acceptNoticeDate && row.acceptNoticeDate.slice(0, 10) }}
            </template>
            <template #acceptFeeDate="{row}">
              {{ row.acceptFeeDate && row.acceptFeeDate.slice(0, 10) }}
            </template>
            <template #authDate="{row}">
              {{ row.authDate && row.authDate.slice(0, 10) }}
            </template>
            <template #authFeeDate="{row}">
              {{ row.authFeeDate && row.authFeeDate.slice(0, 10) }}
            </template>
            <template #issueDate="{row}">
              {{ row.issueDate && row.issueDate.slice(0, 10) }}
            </template>
          </ystable>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="demandNo"
        title="编号"
        width="120"
        align="center"
        header-align="center"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column field="year" title="年份" width="100" align="center" remoteSort></vxe-table-column>
      <vxe-table-column
        field="customerName"
        title="客户名称"
        width="200"
        align="left"
        header-align="center"
        remoteSort>
      </vxe-table-column>
      <vxe-table-column
        field="groupName"
        title="所属集团"
        width="150"
        align="left"
        header-align="center"
        remoteSort>
        <template v-slot="{row}">
          {{ row.groupName || '--' }}
        </template>
      </vxe-table-column>
      <vxe-table-column field="type" title="类型" width="100" align="center" remoteSort>
        <template #default="{ row }">
          {{ typeMap[row.type] }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="total"
        title="需求数量"
        width="100"
        align="right"
        header-align="center"
        remoteSort>
        <template #default="{ row }">
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
      </vxe-table-column>
      <vxe-table-column
        field="total"
        title="申请数"
        width="100"
        align="right"
        header-align="center">
        <template #default="{ row }">
          <div>{{ row.noOfPlan }}</div>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="ownerName"
        title="业务员"
        width="100"
        align="left"
        header-align="center"
      >
        <template #default="{row}">
          {{ row.ownerName || '-' }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="techName"
        title="工程师"
        width="150"
        align="left"
        header-align="center"
      >
        <template #default="{row}">
          {{ row.techName || '-' }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="planSubmitDate"
        title="计划提交国知局日期"
        width="180"
        align="center"
        remoteSort
      >
        <template #default="{row}">
          {{ row.planSubmitDate | DateFormat }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="附件" minWidth="100" align="center" header-align="center">
        <template #default="{ row }">
          <a-popover trigger="click" placement="bottom">
            <template slot="content">
              <table border="1" borderColor="#e8eaec" cellpadding="8px">
                <tr v-for="(item, tIndex) in tableData" :key="tIndex">
                  <td style="width: 130px">{{ item.title }}</td>
                  <td style="min-width: 200px; max-width: 600px;">
                    <div
                      v-for="(file, index) in transFormStrToAry(row[item.type])"
                      :key="index"
                      style="margin-right: 8px; display: inline-block;"
                    >
                      <a v-if="auth.download" @click="onDownloadFile(file.url, file.name)" title="点击下载需求文档">{{
                        file.name
                      }}</a>
                      <span v-else>{{ file.name }}</span>
                      <a-tooltip style="cursor: pointer" placement="top" @click="onPreview(file.url, file.name)">
                        <template slot="title">
                          <span>预览</span>
                        </template>
                        <a-icon type="eye" style="margin-left: 2px" />
                      </a-tooltip>
                      <a-popconfirm
                        title="是否确定删除?"
                        @confirm="onDelete(row.id, file.url, item.type)"
                        style="color: red; margin-left: 2px"
                        placement="bottom"
                        v-if="auth.del"
                      >
                        <a-tooltip placement="top">
                          <template slot="title">
                            <span>删除</span>
                          </template>
                          <a-icon type="close" style="cursor: pointer" />
                        </a-tooltip>
                      </a-popconfirm>
                    </div>
                  </td>
                  <td v-if="auth.upload" style="width: 100px">
                    <a-upload
                      name="file"
                      :multiple="false"
                      :showUploadList="false"
                      :beforeUpload="(file, fileList) => beforeUpload(file, fileList, row.id, item.type)"
                    >
                      <a-button>
                        <a-icon type="upload" />
                        上传文件
                      </a-button>
                    </a-upload>
                  </td>
                </tr>
              </table>
            </template>
            <a> 附件列表
              <a-icon type="down" />
            </a>
          </a-popover>
        </template>
      </vxe-table-column>
      <vxe-table-column field="remark" title="备注" width="150" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column
        field="createTime"
        title="创建日期"
        width="120"
        align="center"
        header-align="center"
        remoteSort
      >
        <template #default="{row}">
          {{ row.createTime | DateFormat }}
        </template>
      </vxe-table-column>
    </ystable>
    <set-staff-modal ref="staffModal" @ok="query(false)" />
    <preview-modal ref="previewModal" />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import SetStaffModal from './modules/SetStaffModal'
import PreviewModal from '@/components/PreviewModal'
import { SearchSelect, YearSelect } from '@/components/Selects'
import TableSearchbar from '@/components/TableSearchbar'
import { getAuth } from '@/utils/util'
import { mapState } from 'vuex'

export default {
  components: {
    ystable,
    SetStaffModal,
    PreviewModal,
    SearchSelect,
    YearSelect,
    TableSearchbar
  },
  data () {
    return {
      typeMap: {
        0: '购买',
        1: '撰写'
      },
      selectedRows: [],
      queryParams: {},
      queryOwner: undefined,
      queryTech: undefined,
      tableData: [
        { title: '1.委托书', type: 'proxyPath' },
        { title: '2.营业执照', type: 'blPath' },
        { title: '3.费用减免', type: 'remissionPath' },
        { title: '4.其他', type: 'otherPath' }
      ],
      labelCol: { span: 5 },
      wrapperCol: { span: 19 },
      auth: {},
      expandTableColumns: [
        { title: '申请名称', field: 'patentName' },
        { title: '专利号', field: 'patentNo' },
        { title: '专利类型', field: 'patentType', remoteSort: true, slots: { default: 'patentType' } },
        { title: '工程师', field: 'engineer' },
        { title: '版本', field: 'planType', remoteSort: true, slots: { default: 'planType' } },
        { title: '节点', field: 'nodeNumber', remoteSort: true, slots: { default: 'nodeNumber' } },
        { title: '当前处理人', field: 'auditUsers' },
        { title: '实际提交国知局日期', field: 'submittedDate', remoteSort: true, slots: { default: 'submittedDate' } },
        { title: '受理通知日期', field: 'acceptNoticeDate', remoteSort: true, slots: { default: 'acceptNoticeDate' } },
        { title: '受理缴费日期', field: 'acceptFeeDate', remoteSort: true, slots: { default: 'acceptFeeDate' } },
        { title: '授权日期', field: 'authDate', remoteSort: true, slots: { default: 'authDate' } },
        { title: '授权缴费日期', field: 'authFeeDate', remoteSort: true, slots: { default: 'authFeeDate' } },
        { title: '下证日期', field: 'issueDate', remoteSort: true, slots: { default: 'issueDate' } }
      ],
      pageInfo: undefined
    }
  },
  computed: mapState({
    patentPlanProcessType: state => state.enums.patentPlanProcessType,
    patentPlanStatus: state => state.enums.patentPlanStatus,
    planTypes: state => state.enums.patentPlanType,
    patentTypes: state => state.enums.patentType
  }),
  mounted () {
    const keys = [`search`, `upload`, `download`, `del`, `setOwner`, `setTech`]
    this.auth = getAuth('patent:application:', keys)
  },
  methods: {
    getParams () {
      const params = { ...this.queryParams }
      params.memberInfo = {}
      if (this.queryOwner && this.queryOwner.id) {
        params.memberInfo.ownerId = this.queryOwner.id
      }
      if (this.queryTech && this.queryTech.id) {
        params.memberInfo.techIds = [this.queryTech.id]
      }
      return params
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRows = records
    },
    query (refresh = false) {
      this.selectedRows = []
      this.$refs.table.refresh(refresh)
    },
    onDownloadFile (path, name) {
      this.$exportData('/patentAppliaction/download', { filePath: path }, name, this.$message)
    },
    onPreview (path, name) {
      if (path === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(path, name)
    },
    onDelete (id, filePath, type) {
      this.$http
        .post('/patentAppliaction/del', {
          id,
          file: filePath,
          type
        })
        .then((res) => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.query()
          } else {
            this.$message.error(res.errorMessage || '操作失败')
          }
        })
    },
    beforeUpload (file, filePath, id, type) {
      const isLt10M = file.size / 1024 / 1024 <= 10
      if (!isLt10M) {
        this.$message.error('文件大小不能超过 10MB!')
        return false
      }
      const param = new FormData()
      param.append('file', file)
      param.append('id', id)
      param.append('type', type)
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http
        .post('/patentAppliaction/upload', param, config)
        .then((res) => {
          if (res.success && res.data) {
            this.$message.success('添加成功')
            this.query()
          } else {
            this.$message.error(res.errorMessage || '操作失败')
          }
        })
        .catch(() => {
          this.$message.error('操作失败')
        })
      return false
    },
    transFormStrToAry (filePaths) {
      if (filePaths == null || filePaths.length === 0) return []
      const files = []
      filePaths.split(',').forEach((path) => {
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
    completed ({ data }) {
      this.pageInfo = data && {
        pageNo: data.pageNo,
        pageSize: data.pageSize
      }
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-col-8 {
  width: 23%;
}
</style>
