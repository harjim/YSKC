
<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false"
  >
    <template v-slot:footer>
      <a-button @click="visible = false">关闭</a-button>
    </template>
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="资产代码">
          <a-input style="width:165px" v-model="queryParam.ecode" placeholder="请输入资产代码" />
        </a-form-item>
        <a-form-item label="设备名称">
          <a-input style="width:165px" v-model="queryParam.ename" placeholder="请输入设备名称" />
        </a-form-item>
        <a-form-item label="研发部门">
          <a-input style="width:165px;" v-model="queryParam.deptName" placeholder="请输入研发部门" />
        </a-form-item>
        <a-form-item label="项目作用">
          <a-input style="width:165px" v-model="queryParam.effect" placeholder="请输入项目作用" />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/projectApproval/getProjectEquipmentList"
          :params="queryParam"
          highlight-hover-row
          show-overflow="title"
          resizable
          auto-resize
          @completed="completed"
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
        >
          <template v-slot:toolbar_buttons>
            <a-popconfirm
              title="是否确定清除归集费用?"
              :disabled="selectedRowKeys.length <= 0"
              @confirm="cleanEquipmentFunds">
              <a-button
                :disabled="selectedRowKeys.length <= 0"
                type="primary"
                style="margin-right: 10px;"
              >清除归集费用</a-button>
            </a-popconfirm>
          </template>
          <vxe-table-column type="checkbox" width="40" align="center" fixed="left" />
          <vxe-table-column
            field="year"
            title="年份"
            width="80"
            align="center"
            fixed="left"
            remoteSort />
          <vxe-table-column field="ecode" title="资产代码" width="100" fixed="left" remoteSort />
          <vxe-table-column field="ename" title="设备名称" width="100" fixed="left" remoteSort />
          <vxe-table-column field="emodal" title="设备型号" width="100" remoteSort />
          <vxe-table-column field="etype" title="设备类型" width="100" align="center" remoteSort>
            <template v-slot="{ row }">{{ row.etype ? $getEnums('equipmentEnum').find(item => item.value === Number(row.etype)).label: '' }}</template>
          </vxe-table-column>
          <vxe-table-column field="rdDeptName" title="研发部门" width="100"></vxe-table-column>
          <vxe-table-column field="entryDate" title="加入日期" width="100" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="unitPrice" title="原值" width="100" align="right" remoteSort></vxe-table-column>
          <vxe-table-column field="effect" title="项目作用" width="100" remoteSort></vxe-table-column>
          <vxe-table-column field="deptName" title="部门" width="100" remoteSort></vxe-table-column>
          <vxe-table-column field="workshop" title="车间" width="100" remoteSort></vxe-table-column>
          <vxe-table-column field="productLine" title="产线" width="100" remoteSort></vxe-table-column>
          <vxe-table-column field="processSection" title="工艺段" width="100" remoteSort></vxe-table-column>
        </ystable>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'

export default {
  name: 'MemberTab',
  components: {
    ystable
  },
  props: {
    projectData: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      visible: false,
      title: '',
      spinning: false,
      selectedRowKeys: [],
      selectedRowIds: [],
      queryParam: { year: this.projectData.year, projectId: this.projectData.id }
    }
  },

  watch: {
    projectData (item) {
      this.$nextTick(() => {
        for (const key in this.queryParam) {
          this.queryParam[key] = undefined
        }
        this.queryParam.year = item.year
        this.queryParam.projectId = item.id
        this.title = `[${item.pname}]资产清单`
        if (this.$refs.table) {
          this.$refs.table.refresh(true)
        }
      })
    }
  },
  methods: {
    completed () {
      this.selectedRowKeys = []
      this.selectedRowIds = []
    },
    open () {
      this.visible = true
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.selectedRowIds = []
      this.$refs.table.refresh(refresh)
    },
    selectCheckBoxChange ({ checked, records }) {
      const ids = []
      const ecodes = []
      records.forEach(item => {
        ids.push(item.id)
        ecodes.push(item.ecode)
      })
      this.selectedRowIds = ids
      this.selectedRowKeys = ecodes
    },
    cleanEquipmentFunds () {
      const self = this
      this.$confirm({
        title: `您确定要清除[${self.selectedRowKeys.join(',')}]的归集费用吗?`,
        content: '同资产代码的设备将会一并清除，清除设备归集费用是不可逆转的操作，这意味着您将失去相关设备的折旧费，设备动力费用，资产摊销费用，设备工时表。',
        onOk () {
          self.executorClean()
        },
        onCancel () {
        }
      })
    },
    executorClean () {
      this.$http.post('/projectApproval/cleanEquipmentFunds', { projectId: this.projectData.id, ids: this.selectedRowIds }).then(res => {
        if (res.success && res.data) {
          this.$message.success('清除成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '清除失败')
        }
      })
    }
  }
}
</script>

<style>
</style>
