<!--
 * @Author: ldx
 * @Date: 2021-04-20 13:57:59
 * @LastEditTime: 2021-12-07 15:57:34
 * @LastEditors: zdf
 * @Description: 当个过程文档审核
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\NoveltySearchAudit.vue
-->
<template>
  <tab-layout>
    <template #up>
      <div style="height:100%;">
        <ystable
          ref="table"
          height="100%"
          highlight-hover-row
          highlight-current-row
          show-overflow="title"
          resizable
          auto-resize
          size="small"
          queryUrl="/docList/getDocList"
          :params="params"
        >
          <template v-slot:top>
          </template>
          <vxe-table-column
            :width="60"
            align="center"
            headerAlign="center"
            title="序号"
            type="seq"></vxe-table-column>
          <vxe-table-column
            title="RD"
            field="rdTitle"
            :width="150"
            align="left"
            headerAlign="center"></vxe-table-column>
          <vxe-table-column
            title="项目名称"
            field="pname"
            :width="150"
            align="left"
            headerAlign="center"></vxe-table-column>
          <vxe-table-column
            :width="120"
            align="left"
            field="stageKey"
            headerAlign="center"
            title="项目阶段">
            <template #default="{ row }">
              {{ stagesKV[row.stageKey] }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="left"
            field="fileName"
            headerAlign="center"
            title="文档名">
            <template #default="{ row }">
              <a
                v-if="$auth('customer:projectProgress:download')"
                title="点击下载"
                @click="download(row)">{{ row.fileName
                }}</a>
              <span v-else> {{ row.fileName }}</span>
              <span v-if="$auth('customer:projectProgress:preview')">
                <a-tooltip
                  placement="top"
                  style="cursor:pointer"
                  @click="preview(row)">
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin-left:5px" />
                </a-tooltip>
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            :width="120"
            align="center"
            field="createTime"
            headerAlign="center"
            title="上传时间">
            <template #default="{ row }">
              {{ row.createTime | DayFormat }}
            </template>
          </vxe-table-column>
        </ystable>
        <preview-modal ref="previewModal" />
      </div>
    </template>
    <template #down>
      <audit-log
        ref="auditLog"
        :active="record.moduleId"
        :params="logGetParams"
        :currentObj="record"
        :configParams="{
          rsProjectId: record.rsProjectId,
          year: record.year,
          companyId: record.companyId
        }"
      />
    </template>
  </tab-layout>
</template>

<script>
import TabLayout from './TabLayout'
import AuditLog from './AuditLog'
import ystable from '@/components/Table/ystable'
import PreviewModal from '@/components/PreviewModal'

export default {
  name: 'DocAudit',
  components: {
    TabLayout,
    AuditLog,
    ystable,
    PreviewModal
  },
  props: {
    record: {
      type: Object,
      required: true
    }
  },
  mounted () {
    this.params.year = this.record.year
    this.params.companyId = this.record.companyId
    this.params.projectId = this.record.rsProjectId
    // this.logGetParams['rsProjectId'] = this.record.rsProjectId
    this.logGetParams['docFileId'] = this.record.docFileId
    // 获取项目阶段字典
    this.$getDictionary(6).then(res => {
      if (res) {
        res.forEach(item => {
          this.stagesKV[item.key] = item.value
        })
      }
    })
  },
  data () {
    return {
      logGetParams: { docFileId: undefined },
      htmlData: '',
      params: {
        year: undefined,
        companyId: undefined,
        projectId: undefined
      },
      headerInfo: {},
      stagesKV: {}
    }
  },
  methods: {
    preview (row) {
      if (row.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(row.filePath, row.fileName ? row.fileName : '')
    }
  }
}
</script>

<style lang="less" scoped>
.preview_doc {
  height: 100%;
  overflow: auto;
}
</style>
