<template>
  <a-card :bordered="false">
    <a-spin :spinning="spin" tip="请稍后...">
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input placeholder="请输入客户名称" v-model="queryParam.customerName" />
        </a-form-item>
        <a-form-item label="服务人员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="true"
            placeholder="请输入服务人员搜索"
            style="width:165px;"
            v-model="queryParam.userIds"
          />
        </a-form-item>
        <a-form-item label="服务方式">
          <a-select
            :allowClear="true"
            style="width: 120px"
            v-model="queryParam.serviceType"
            placeholder="请选择服务方式"
          >
            <a-select-option
              v-for="(item,index) in serviceTypes"
              :key="index"
              :value="index"
            >{{ item }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button v-if="$auth('customer:customerServices:search')" type="primary" @click="search(true)">查询</a-button>
        </a-form-item>
      </a-form>
      <ystable
        rowId="id"
        ref="table"
        size="default"
        queryUrl="/serviceLog/queryServiceLog"
        :params="getParams()"
        @completed="completed"
        :toolbar="{
          custom: true,
          zoom:true,
          refresh:true
        }"
      >
        <template v-slot:buttons>
          <span>
            <a-button type="primary" v-if="$auth('customer:customerServices:draft')" @click="$refs.customerServiceModal.add()">添加</a-button>
          </span>
          <span style="margin-left:10px;">
            <a-button type="primary" v-if="$auth('customer:customerServices:export')" @click="exportServiceLog">导出</a-button>
          </span>
          <span style="margin-right: 10px;">
            服务天数:
            <a style="font-weight: 600">{{ stats.daySum != null ? stats.daySum : '--' }}</a>
          </span>
          <span style="margin-right: 10px;">
            服务次数:
            <a style="font-weight: 600">{{ stats.times != null ? stats.times : '--' }}</a>
          </span>
        </template>
        <vxe-table-column type="seq" title="序号" width="50" fixed="left"></vxe-table-column>
        <vxe-table-column
          title="客户名称"
          field="customerName"
          align="left"
          :width="260"
          show-header-overflow
          remoteSort
          show-overflow="tooltip"
          fixed="left"
        />
        <vxe-table-column title="主服务人员" field="mainStaffName" align="left" :width="120" />

        <vxe-table-column
          title="其他服务人员"
          field="otherStaffName"
          align="left"
          :width="200"
          show-header-overflow
          show-overflow="tooltip"
        />
        <vxe-table-column title="服务方式" field="serviceType" align="left" :width="160" remoteSort>
          <template v-slot="{ row }">
            <span>{{ serviceTypes[row.serviceType] }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="总人数" field="totalStaff" remoteSort align="center" :width="110" />
        <vxe-table-column title="总工时" field="totalTime" remoteSort align="center" :width="110" />
        <vxe-table-column
          title="开始时间"
          field="startDate"
          align="center"
          :width="150"
          remoteSort
          show-header-overflow
          show-overflow="tooltip"
        />
        <vxe-table-column
          title="结束时间"
          field="endDate"
          align="center"
          :width="150"
          remoteSort
          show-header-overflow
          show-overflow="tooltip"
        />
        <vxe-table-column
          title="服务天数"
          field="days"
          align="center"
          :width="80"
          show-header-overflow
          show-overflow="tooltip"
        />
        <vxe-table-column
          title="附件"
          field="filePaths"
          align="left"
          :min-width="200"
          show-header-overflow
          show-overflow="tooltip"
        >
          <template v-slot="{row}">
            <template v-if="row.filePaths && row.filePaths.length">
              <span v-for="(file,index) in row.filePaths" :key="index" style="margin-right:10px;">
                <a title="点击下载" @click="downloadFile(row,file)">
                  {{ file.substring(file.lastIndexOf('/') + 14, file.length) }}
                </a>
                <a-tooltip style="cursor:pointer" placement="top" @click="preview(file,file.substring(file.lastIndexOf('/') + 14, file.length))">
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin-left:5px" />
                </a-tooltip>
              </span>
            </template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="状态" field="status" align="center" remoteSort :width="120">
          <template v-slot="{ row }">
            <span>{{ status[row.status] }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="提交时间" field="submitDate" align="center" remoteSort :width="150"/>
        <vxe-table-column title="操作" field="action" :width="150" fixed="right">
          <template v-slot="{ row }">
            <a @click="$refs.customerServiceModal.edit(row)">详情</a>
            <a-popconfirm
              title="是否确定删除?"
              @confirm="del(row.id)"
              v-if="row.status === 0 && $auth('customer:customerServices:delete')"
            >
              <a-divider type="vertical" />
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
    </a-spin>
    <preview-modal ref="previewModal"/>
    <customer-service-modal ref="customerServiceModal" @ok="handleOk" @error="handleError" :preview="preview"></customer-service-modal>
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import { SearchSelect } from '@/components/Selects'
import CustomerServiceModal from './modules/CustomerServiceModal'
import PreviewModal from '@/components/PreviewModal'
import moment from 'moment'
export default {
  name: 'CustomerServices',
  components: {
    ystable,
    CustomerServiceModal,
    SearchSelect,
    PreviewModal
  },
  data () {
    return {
      pagination: [],
      companyList: [],
      userNameList: [],
      // 查询参数
      queryParam: {
        userIds: []
      },
      spin: false,
      serviceTypes: ['现场对接', '电话沟通', '其他'],
      status: ['草稿', '已提交', '不通过', '已通过'],
      stats: {}
    }
  },
  methods: {
    getParams () {
      const params = { customerName: this.queryParam.customerName, serviceType: this.queryParam.serviceType }
      if (this.queryParam.userIds) { params.userIds = this.queryParam.userIds.map(a => a.id) }
      return params
    },
    moment,
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    handleSearch (value) {
      if (!value || !value.trim()) {
        this.companyList = []
        return
      }
      this.$http.get('/rsRole/getCompany', { params: { companyName: value } })
        .then(res => {
          this.companyList = res.data
          return this.companyList
        })
    },
    handleSubmit () {
      this.visible = false
    },
    del (id) {
      this.$http.post('/serviceLog/delServiceLog', { id: id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          this.$refs.table.refresh(true)
        } else {
          this.$message.error(`${res.errorMessage ? res.errorMessage : '删除失败'}`)
        }
      })
    },
    editState (id) {
      this.$http.post('/serviceLog/editLogState', { id: id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('提交成功')
          this.$refs.table.refresh(true)
        } else {
          this.$message.error(`${res.errorMessage ? res.errorMessage : '提交失败'}`)
        }
      })
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    handleChange (value) {
    },
    handleError (errorMassege) {
      this.$message.error(errorMassege)
    },
    userChange (value) {
      this.userName = value
    },
    userSearch (value) {
      this.$http.get('/user/userForSelect', { params: { realName: value } })
        .then(res => {
          this.userNameList = res.data
          return this.userNameList
        })
    },
    downloadFile (row, file) {
      this.$exportData('/serviceLog/download', { id: row.id, fileName: file.substring(file.lastIndexOf('/') + 14, file.length), filePath: file }, file.name, this.$message)
    },
    preview (filePath, filename) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(filePath, filename)
    },
    completed (res) {
      this.stats = res.data.footer
    },
    exportServiceLog () {
      this.spin = true
      this.$exportData('/serviceLog/exportServiceLog', this.getParams(), null, this.$message).finally(() => { this.spin = false })
    }
  }
}
</script>
