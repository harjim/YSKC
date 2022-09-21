<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="项目名称">
        <a-input v-model="queryParam.pname" style="width:180px;" placeholder="请输入项目名称" />
      </a-form-item>
      <a-form-item label="客户名称">
        <a-input placeholder="请输入客户名称" v-model="queryParam.companyName" />
      </a-form-item>
      <a-form-item label="项目年份">
        <year-select style="width:120px;" v-model="queryParam.year" placeholder="项目年份" />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          v-if="$auth('customer:projectApproval:search')"
          @click="search(true)"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      :params="queryParam"
      queryUrl="/projectApproval/getProjectApproval"
      :toolbar="{
        custom: true,
        zoom:true,
        refresh:true
      }"
    >
      <vxe-table-column type="seq" title="序号" :width="50" />
      <vxe-table-column
        title="项目名称"
        field="pname"
        show-header-overflow
        show-overflow="tooltip"
        remoteSort
      >
      </vxe-table-column>
      <vxe-table-column
        remoteSort
        title="客户名称"
        field="companyName"
        :width="280"
        show-header-overflow
        show-overflow="tooltip"
      >
      </vxe-table-column>
      <vxe-table-column
        remoteSort
        title="RD"
        field="rdTitle"
        :width="200"
        show-header-overflow
        show-overflow="tooltip"
        align="center"
      >
      </vxe-table-column>
      <vxe-table-column
        title="项目起止日期"
        field="beginAndEndDate"
        :width="220"
        show-header-overflow
        show-overflow="tooltip"
        align="center"
      >
        <template v-slot="{row}">
          {{ `${row.beginDate}~${row.endDate}` }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="项目成员"
        field="memberCnt"
        :width="100"
        show-header-overflow
        show-overflow="tooltip"
        align="right">
        <template v-slot="{row}">
          <template v-if="$auth('customer:projectApproval:cleanEmployee')">
            <template v-if="row.memberCnt">
              <a @click="openMember(row)">{{ row.memberCnt }}</a>
            </template>
            <template v-else>-</template>
          </template>
          <template v-else>
            <template>{{ row.memberCnt || '-' }}</template>
          </template>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="资产清单"
        field="equipmentCnt"
        :width="100"
        show-header-overflow
        show-overflow="tooltip"
        align="right">
        <template v-slot="{row}">
          <template v-if="$auth('customer:projectApproval:cleanEquipment')">
            <template v-if="row.equipmentCnt">
              <a @click="openEquipment(row)">{{ row.equipmentCnt }}</a>
            </template>
            <template v-else>-</template>
          </template>
          <template v-else>
            <template>{{ row.equipmentCnt || '-' }}</template>
          </template>
        </template>
      </vxe-table-column>
      <!-- <vxe-table-column
        title="年份"
        field="year"
        key="year"
        :width="220"
        remoteSort
        show-header-overflow
        show-overflow="tooltip"
        align="center"
      >
      </vxe-table-column> -->
      <vxe-table-column
        title="创建时间"
        field="createTime"
        :width="220"
        remoteSort
        show-header-overflow
        show-overflow="tooltip"
        align="center"
      >
      </vxe-table-column>
      <vxe-table-column title="操作" :width="140" v-if="$auth('customer:projectApproval:cleanAll')">
        <template v-slot="{row}">
          <a-popconfirm title="是否确定清除归集费用?" @confirm="clean(row)">
            <a>清除归集费用</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <member-modal ref="memberModal" :projectData="cuurentProject"/>
    <equipment-modal ref="equipmentModal" :projectData="cuurentProject"/>
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import { YearSelect } from '@/components/Selects'
import MemberModal from './modules/MemberModal'
import EquipmentModal from './modules/EquipmentModal'
export default {
  name: 'ProjectApproval',
  components: {
    ystable,
    YearSelect,
    MemberModal,
    EquipmentModal
  },
  data () {
    return {
      // 查询参数
      queryParam: {},
      cuurentProject: {}
    }
  },
  filters: {
  },
  methods: {
    openMember (row) {
      this.cuurentProject = row
      this.$refs.memberModal.open()
    },
    openEquipment (row) {
      this.cuurentProject = row
      this.$refs.equipmentModal.open()
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    clean (row) {
      const self = this
      this.$confirm({
        title: `您确定要清除[${row.companyName}-${row.rdTitle}-${row.pname}]的归集费用吗?`,
        content: '清除归集费用是不可逆转的操作，这意味着您将失去该项目所关联的所有归集费用。',
        onOk () {
          self.handleclean(row)
        },
        onCancel () {
        }
      })
    },
    handleclean (row) {
      this.$http.post('/projectApproval/cleanRdFunds', { id: row.id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('清除归集费用成功')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '清除归集费用失败')
        }
      })
    }
  }
}
</script>
