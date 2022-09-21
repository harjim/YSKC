<template>
  <a-modal
    :title="title"
    style="top: 120px;"
    :width="1000"
    v-model="visible"
    :maskClosable="false"
    @cancel="visible = false"
  >
    <ystable
      ref="table"
      queryUrl="/trialProd/queryTrial"
      :params="queryParams"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      border
      :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
    >
      <template v-slot:toolbar_buttons>
        <a-button
          type="primary"
          v-if="$auth('project:report:trial:add')"
          @click="$refs.trialProdModal.add(projectData)"
        >添加</a-button>
      </template>
      <vxe-table-column field="planPO" title="计划试制量" width="120" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="actualPO" title="实际试制量" width="120" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="unit" title="单位" width="70" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="place" title="地点" min-width="100" align="left" remoteSort></vxe-table-column>
      <vxe-table-column field="trialDate" title="试制日期" width="120" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="startTime" title="开始时间" width="110" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="endTime" title="结束时间" width="110" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="pos" title="试制机台号" width="130" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="trialGroup" title="试制班组" width="100" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="mainMaterial" title="主材消耗" width="100" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="auxMaterial" title="辅材消耗" width="100" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="fuel" title="燃料消耗" width="100" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="power" title="动力消耗" width="100" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="gas" title="气体" width="100" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="spare" title="预计备品备件(万元)" width="165" align="center" remoteSort></vxe-table-column>
      <vxe-table-column title="操作" width="120" align="center" fixed="right">
        <template v-slot="{ row }">
          <a
            type="primary"
            v-if="$auth('project:report:trial:edit')"
            @click="$refs.trialProdModal.edit(row,projectData)"
          >编辑</a>
          <template v-if="$auth('project:report:trial:del')">
            <a-divider type="vertical" v-if="$auth('project:report:trial:del')" />
            <a-popconfirm title="是否确定删除?" @confirm="del(row)">
              <a type="primary">删除</a>
            </a-popconfirm>
          </template>
        </template>
      </vxe-table-column>
    </ystable>
    <trial-prod-modal ref="trialProdModal" @ok="handleOk" @error="handleError" />
    <template slot="footer">
      <a-button @click="visible = false">关闭</a-button>
    </template>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
import TrialProdModal from './projectTab/tabModules/TrialProdModal'
export default {
  components: {
    ystable,
    STable,
    TrialProdModal,
    Ellipsis
  },
  name: 'TrialProdData',
  data () {
    return {
      scroll: {},
      projectData: null,
      projectId: undefined,
      visible: false,
      title: '试制',
      options: [],
      form: this.$form.createForm(this),
      queryParams: {},
      getData: parameter => {
        return this.$http.get('/trialProd/queryTrial', { params: Object.assign(parameter, { projectId: this.projectId }) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  methods: {
    showModal (projectData) {
      const sourceData = this.projectData
      this.projectId = projectData.id
      this.projectData = projectData
      this.title = `[${projectData.pname}]试制`
      this.queryParams.projectId = projectData.id
      if (sourceData) {
        this.$refs.table.refresh(true)
      }

      this.visible = true
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    handleError (errorMassege) {
      this.$message.error(errorMassege)
    },
    del (record) {
      this.$http.post('/trialProd/delTrial', { id: record.id }).then(res => {
        if (!res.success) {
          this.$message.warning(res.errorMessage)
          return
        }
        this.$message.success('删除成功')
      }).finally(res => {
        this.$emit('change')
        this.$refs.table.refresh(true)
      })
    }
  }
}
</script>

<style lang="less" scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
#zoneSpacing {
  margin-top: 12px;
}
</style>
