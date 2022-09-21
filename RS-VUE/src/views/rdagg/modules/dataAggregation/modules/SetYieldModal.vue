<!-- <template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="900"
    v-model="visible"
    :maskClosable="false"
    @cancel="visible = false"
  >
    <a-spin tip="请稍后..." :spinning="loading">
      <vxe-grid
        id="/projectYieldConfig/getList"
        rowId="id"
        ref="table"
        size="default"
        highlight-hover-row
        highlight-current-row
        show-overflow="title"
        resizable
        auto-resize
        :data="tableData"
        :toolbar="{custom: true}"
      >
        <template v-slot:buttons>
          <a-button
            type="primary"
            style="margin-right:10px;"
            @click="$refs.modify.add( {month: params.month,projectId: params.projectId, rdTitle: params.rdTitle } )"
          >添加</a-button>
          <a-button
            type="primary"
            @click="importYield"
          >导入</a-button>
        </template>
        <vxe-table-column
          title="部门"
          field="deptName"
          align="left"
          :width="120"
          remoteSort
          show-overflow="title"
          headerAlign="center"
        /><vxe-table-column
          title="单位"
          field="unit"
          align="left"
          remoteSort
          :width="90"
          show-overflow="title"
          headerAlign="center"
        />
        <vxe-table-column
          remoteSort
          title="试制品名"
          field="trialProduct"
          key="trialProduct"
          :width="140"
          show-header-overflow
          show-overflow="tooltip"
        />
        <vxe-table-column
          title="总量"
          field="totalYield"
          align="right"
          :width="100"
          remoteSort
          show-overflow="title"
          headerAlign="center"
        />
        <vxe-table-column
          title="计划量"
          field="planYield"
          align="right"
          :width="100"
          remoteSort
          show-overflow="title"
          headerAlign="center"
        />
        <vxe-table-column
          title="试制量"
          field="rdYield"
          align="right"
          :width="100"
          remoteSort
          show-overflow="title"
          headerAlign="center"
        />
        <vxe-table-column
          title="试制日期"
          field="trialDate"
          align="center"
          :width="110"
          remoteSort
          show-overflow="title"
          headerAlign="center"
        />
        <vxe-table-column
          title="开始时间"
          field="startTime"
          align="center"
          :width="100"
          remoteSort
          show-overflow="title"
          headerAlign="center">
          <template slot-scope="{row}">
            {{ handleTime(row.startTime) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="结束时间"
          field="endTime"
          align="center"
          :width="100"
          remoteSort
          show-overflow="title"
          headerAlign="center"
        >
          <template slot-scope="{row}">
            {{ handleTime(row.endTime) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="操作"
          align="left"
          :width="100"
          fixed="right"
          remoteSort
          show-overflow="title"
          headerAlign="center"
        >
          <template slot-scope="{row}">
            <a type="primary" @click="$refs.modify.edit(row,params.rdTitle)">编辑</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否确定删除?" @confirm="del(row)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </vxe-grid>
      <ModifyYieldModal ref="modify" @ok="fresh()" />
    </a-spin>
    <template slot="footer">
      <a-button @click="visible = false">关闭</a-button>
      <a-button @click="handle" :loading="handleState" type="primary" title="应用总量/试制量到归集数据">应用</a-button>
    </template>
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="paramData"
      paramKey="tableField"
      title="导入总量/试制量"
      ref="uploadModal"
      action="/doc/projectYieldConfig/importYield"
      templateName="总量-试制量模板"
      @onSuccess="fresh"
    />
  </a-modal>
</template>

<script>
// import ModifyYieldModal from './ModifyYieldModal'
// import ystable from '@/components/Table/ystable'
// import UploadModal from '@/components/UploadModal'
// import moment from 'moment'
// export default {
//   components: {
//     ModifyYieldModal,
//     ystable,
//     UploadModal
//   },
//   data () {
//     return {
//       labelCol: {
//         xs: { span: 24 },
//         sm: { span: 6 }
//       },
//       wrapperCol: {
//         xs: { span: 24 },
//         sm: { span: 14 }
//       },
//       visible: false,
//       params: undefined,
//       loading: false,
//       tableData: [],
//       handleState: false,
//       title: '',
//       tableField: {
//         tableId: 'yieldTable',
//         fieldTitleObject: {
//           rdTitle: { title: 'RD', defaultTitle: 'RD', importField: true, required: true },
//           month: { title: '月份', defaultTitle: '月份', importField: true, required: true },
//           deptName: { title: '部门', defaultTitle: '部门', importField: true, required: true },
//           unit: { title: '单位', defaultTitle: '单位', importField: true, required: true },
//           trialProduct: { title: '试制品名', defaultTitle: '试制品名', importField: true, required: false },
//           totalYield: { title: '总量', defaultTitle: '总量', importField: true, required: true },
//           planYield: { title: '计划量', defaultTitle: '计划量', importField: true, required: true },
//           rdYield: { title: '试制量', defaultTitle: '试制量', importField: true, required: true },
//           trialDate: { title: '试制日期', defaultTitle: '试制日期', importField: true, required: true },
//           startTime: { title: '开始时间', defaultTitle: '开始时间', importField: true },
//           endTime: { title: '结束时间', defaultTitle: '结束时间', importField: true }
//         }
//       },
//       sampleData: [
//         {
//           rdTitle: '2021RD01',
//           month: '格式："年-月-日"，一般为当月第一天，例如：2019-10-01',
//           deptName: 'xxx部门',
//           unit: '个',
//           trialProduct: 'A产品',
//           totalYield: '数字格式，例如：300000',
//           planYield: '数字格式，例如：300000',
//           rdYield: '数字格式，例如：300000',
//           trialDate: '格式："年-月-日"，例如：2019-10-11',
//           startTime: '格式："时:分"，例如：14:50',
//           endTime: '格式："时:分"，例如：14:50'
//         }
//       ],
//       paramData: {}
//     }
//   },
//   methods: {
//     moment,
//     set (params) {
//       this.title = `设置[${params.rdTitle}]总量/试制量`
//       this.params = params
//       this.visible = true
//       this.fresh()
//     },
//     fresh () {
//       this.loading = true
//       this.$http.get('/projectYieldConfig/getList', { params: { month: this.params.month, projectId: this.params.projectId } }).then(res => {
//         if (res.success && res.data) {
//           this.tableData = res.data
//         } else {
//           this.tableData = []
//           this.$message.error(res.errorMessage ? res.errorMessage : '获取配置失败。')
//         }
//         this.loading = false
//       })
//     },
//     del (record) {
//       this.loading = true
//       this.$http.post('/projectYieldConfig/delete', { ids: [record.id] }).then(res => {
//         if (res.success && res.data) {
//           this.$message.success('删除成功')
//           this.loading = false
//           this.fresh()
//         } else {
//           this.loading = false
//           this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
//         }
//       })
//     },
//     handle () {
//       this.handleState = true
//       this.$http.post('/projectYieldConfig/handleRd', this.params).then(res => {
//         if (res.success && res.data) {
//           this.$message.success('应用成功')
//           this.$emit('ok')
//           this.visible = false
//         } else {
//           this.$message.error(res.errorMessage ? res.errorMessage : '应用失败')
//         }
//         this.handleState = false
//       })
//     },
//     handleTime (strTime) {
//       if (!strTime) return
//       const index = strTime.lastIndexOf(':')
//       return strTime.substring(0, index)
//     },
//     importYield () {
//       this.paramData = { projectId: this.params.projectId }
//       this.$refs.uploadModal.show(this.tableField)
//     }
//   }
// }
</script>

<style>
</style>
-->
