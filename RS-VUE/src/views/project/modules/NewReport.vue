<!--
 * @Author: ldx
 * @Date: 2021-03-15 09:48:33
 * @LastEditTime: 2021-04-15 16:00:16
 * @LastEditors: ldx
 * @Description: 查新报告
 * @FilePath: \RS-VUE\src\views\project\modules\NewReport.vue
-->
<template>
  <div style="height: 100%;">
    <ystable
      rowId="id"
      ref="table"
      border
      resizable
      auto-resize
      highlight-hover-row
      show-overflow
      show-header-overflow
      highlight-current-row
      header-align="center"
      @loading="loading"
      @completed="({data}) => completed(data)"
      :toolbar="{zoom:true,custom:true,refresh:true}"
      :params="paramData"
      queryUrl="/projectDocFileData/getNewReports"
      height="100%"
    >
      <template>
        <vxe-table-column
          title="RD"
          field="rdTitle"
          key="rdTitle"
          remoteSort
          align="left"
          :width="120"
          header-align="center" />
        <vxe-table-column
          title="项目名称"
          field="pname"
          key="pname"
          remoteSort
          align="left"
          :width="150"
          header-align="center"/>
        <vxe-table-column
          title="项目阶段"
          field="stageKey"
          key="stageKey"
          align="center"
          header-align="center"
          :width="100">
          <template v-slot="{ row }">
            {{ stageMap[row.stageKey] }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="文件类型"
          field="docName"
          key="docName"
          align="center"
          header-align="center"
          :width="100">
        </vxe-table-column>
        <vxe-table-column title="文件名" field="fileName" key="fileName" align="left" header-align="center">
          <template v-slot="{ row }">
            <span v-if="$auth('project:result:download')">
              <a @click="downloadFile(row)">{{ row.fileName }}</a>
            </span>
            <span v-else>
              {{ row.fileName }}
            </span>
            <span v-if="$auth('project:result:preview')">
              <a-tooltip style="cursor:pointer" placement="top" @click="preview(row)">
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin-left:5px" />
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="上传时间"
          field="createTime"
          key="createTime"
          align="center"
          :width="170"
          header-align="center">
          <template v-slot="{row}">
            {{ row.createTime }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          v-if="$auth('project:result:del') && isEditStatus(doc.auidtStatus)"
          title="操作"
          align="center"
          width="90"
          header-align="center">
          <template v-slot="{row}">
            <a-popconfirm title="是否确定删除?" @confirm="onDel(row)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </template>
    </ystable>
    <preview-modal ref="previewModal"></preview-modal>
  </div>
</template>

<script>
import { PreviewModal } from '@/components'
import { isEditStatus } from '@/utils/processDoc/auditStatus'
import ystable from '@/components/Table/ystable'
export default {
  name: 'NewReport',
  components: {
    PreviewModal,
    ystable
  },
  props: {
    projectId: {
      type: Number,
      default: undefined
    },
    doc: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    this.$getDictionary(6).then(res => {
      this.stageLists = res
      for (const stage of res) {
        this.stageMap[stage.key] = stage.value
      }
    })
    this.paramData.projectId = this.projectId
  },
  watch: {
    projectId (val) {
      this.paramData.projectId = val
      this.$refs.table.refresh(true)
    }
  },
  data () {
    return {
      stageMap: {},
      paramData: {
        projectId: undefined
      },
      auditStatus: undefined
    }
  },
  methods: {
    isEditStatus,
    preview (row) {
      if (row.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(row.filePath, row.fileName !== undefined ? row.fileName : '')
    },
    downloadFile (row) {
      this.$exportData('/sysDocument/downloadFile', { id: row.id }, row.fileName, this.$message)
    },
    refresh () {
      this.$refs.table.refresh(true)
    },
    loading (spinning) {
      this.$emit('loading', spinning)
    },
    completed (response) {
      const { footer, data } = response
      this.$emit('completed', footer, data)
    },
    onDel (record) {
      this.$http.post('/sysDocument/del', { id: record.id })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
          } else {
            this.$message.error('删除失败')
          }
        }).finally(res => {
          this.$refs.table.refresh(true)
        })
    }
  }
}
</script>

<style>

</style>
