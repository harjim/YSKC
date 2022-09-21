<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="姓名">
        <a-input v-model="queryParam.realName" placeholder="请输入姓名" style="width:180px"/>
      </a-form-item>
      <a-form-item label="所属行业">
        <a-input v-model="queryParam.industryCode" placeholder="请输入所属行业" style="width:180px"/>
      </a-form-item>
      <a-form-item label="有效期">
        <a-date-picker
          format="YYYY-MM-DD"
          v-model="queryParam.beginDate"
          :disabledDate="disabledStartDate"
          @openChange="handleStartOpenChange"
        />
        -
        <a-date-picker
          format="YYYY-MM-DD"
          v-model="queryParam.endDate"
          :disabledDate="disabledEndDate"
          :open="endOpen"
          @openChange="handleEndOpenChange"
        />
      </a-form-item>
      <a-form-item>
        <span v-if="$auth('sys:Expert:search')">
          <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
        </span>
      </a-form-item>
    </a-form>
    <ystable
      queryUrl="/expert/queryExpertList"
      :params="queryParam"
      highlight-hover-row
      show-overflow
      resizable
      ref="table"
      :toolbar="tableToolbar"
    >
      <template v-slot:toolbar_buttons>
        <span style="margin-right:10px;" v-if="$auth('sys:Expert:add')">
          <a-button type="primary" @click="$refs.createModal.add()">添加</a-button>
        </span>
      </template>
      <vxe-table-column
        field="expertNumber"
        title="编号"
        align="left"
        remoteSort
        width="150"
        fixed="left"/>
      <vxe-table-column
        field="realName"
        title="姓名"
        align="center"
        remoteSort
        width="120"
        fixed="left"/>
      <vxe-table-column field="policitalStatus" title="政治面貌" align="center" remoteSort width="100">
        <template v-slot="{ row }">
          {{ policitalStatusArr[row.policitalStatus] }}
        </template>
      </vxe-table-column>
      <vxe-table-column field="phone" title="电话" align="center" remoteSort width="120"/>
      <vxe-table-column field="email" title="邮箱" align="center" remoteSort width="180"/>
      <vxe-table-column field="level" title="专家级别" align="center" remoteSort width="100">
        <template v-slot="{ row }">
          {{ levelArr[row.level] }}
        </template>
      </vxe-table-column>
      <vxe-table-column field="eduLevel" title="学历" align="center" remoteSort width="100">
        <template v-slot="{ row }">
          {{ eduLevelArr[row.eduLevel] }}
        </template>
      </vxe-table-column>
      <vxe-table-column field="industryCode" title="所属行业" align="center" remoteSort width="120"/>
      <vxe-table-column field="validDate" title="有效日期" align="center" remoteSort width="150"/>
      <vxe-table-column field="issueDate" title="发证日期" align="center" remoteSort width="150"/>
      <vxe-table-column field="viewUrl" title="查看专家信息" align="center" width="150">
        <template v-slot="{ row }">
          <a :href="row.viewUrl" target="_blank">查看信息</a>
        </template>
      </vxe-table-column>
      <vxe-table-column title="操作" align="center" fixed="right" width="250">
        <template v-slot="{ row }">
          <a @click="approved(row)" v-if="row.status===1">审核通过</a>
          <a @click="notApproved(row)" v-if="row.status===2">审核不通过</a>
          <a v-if="row.status===0">
            <a @click="approved(row)">审核通过</a>
            <a-divider type="vertical"/>
            <a @click="notApproved(row)">审核不通过</a>
          </a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多
              <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item v-if="$auth('sys:Expert:edit')">
                <a @click="handleEdit(row)">编辑</a>
              </a-menu-item>
              <a-menu-item v-if="$auth('sys:Expert:del')">
                <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </template>
      </vxe-table-column>
    </ystable>
    <expert-modal ref="createModal" @ok="handleOk"/>
    <expertStatus-modal ref="statusModal" @ok="refresh"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import ExpertModal from './modules/ExpertModal'
import ExpertStatusModal from './modules/ExpertStatusModal'
import ystable from '@/components/Table/ystable'

const policitalStatusArr = ['群众', '共青团员', '党员', '其他党派']

const eduLevelArr = ['无', '高中及以下', '中专', '大专', '本科', '硕士', '博士']

const levelArr = ['特聘专家', '高级研究员']
export default {
  name: 'RoleManage',
  components: {
    STable,
    ExpertModal,
    ExpertStatusModal,
    ystable
  },
  data () {
    return {
      policitalStatusArr,
      eduLevelArr,
      levelArr,
      mdl: {},
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        }
      },
      scroll: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: { industryCode: null },
      selectedRowKeys: [],
      selectedRows: [],
      paramStatus: 0,
      endOpen: false,
      // custom table alert & rowSelection
      options: {
        alert: {
          show: true,
          clear: () => {
            this.selectedRowKeys = []
          }
        },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false
    }
  },
  filters: {},
  mounted () {
  },
  created () {
  },
  methods: {
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    notApproved (record) {
      this.$refs.statusModal.add(record)
    },
    approved (record) {
      this.$refs.statusModal.edit(record)
    },
    handleDel (record) {
      this.$http.post('/expert/delExpert', { id: record.id }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.$refs.table.refresh(false)
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(flag)
    },
    refresh (flag) {
      if (flag) {
        this.$message.success('操作成功')
      }
      this.$refs.table.refresh(false)
    },
    disabledStartDate (beginDate) {
      const endValue = this.queryParam.endDate
      if (!beginDate || !endValue) {
        return false
      }
      return beginDate.valueOf() > endValue.valueOf()
    },
    disabledEndDate (endDate) {
      const startValue = this.queryParam.beginDate
      if (!endDate || !startValue) {
        return false
      }
      return startValue.valueOf() >= endDate.valueOf()
    },
    handleStartOpenChange (open) {
      if (!open) {
        this.endOpen = true
      }
    },
    handleEndOpenChange (open) {
      this.endOpen = open
    }
  }
}
</script>
