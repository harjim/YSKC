<!--
 * @Author: zdf
 * @Date: 2021-07-09 13:59:44
 * @LastEditTime: 2021-07-27 15:54:17
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \MS-VUE\src\views\sys\modules\SolutionTab.vue
-->
<template>
  <div>
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form layout="inline" v-if="control.search">
        <a-form-item label="标题">
          <a-input
            placeholder="请输入标题"
            v-model="queryParams.title"
          />
        </a-form-item>
        <a-form-item
          v-if="!isBase"
          label="客户名称">
          <a-input
            placeholder="请输入客户名称"
            v-model="queryParams.customer"
          />
        </a-form-item>
        <a-form-item label="服务项目">
          <a-input
            placeholder="请输入服务项目"
            v-model="queryParams.serveProject"
          />
        </a-form-item>
        <a-form-item label="行业">
          <a-input
            placeholder="请输入行业"
            v-model="queryParams.industry"
          />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <ystable
        ref="table"
        rowId="id"
        @completed="()=>selectRowIds=[]"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        :params="queryParams"
        :queryUrl="url"
        :toolbar="{
          custom: true,
          zoom:true,
          refresh:true
        }"
      >
        <template v-slot:buttons>
          <template v-if="!isShare">
            <span v-if="control.add" style="margin-right:10px;">
              <a-button type="primary" @click="$refs.solutionModal.add()">添加</a-button>
            </span>
            <span v-if="control.delete" style="margin-right:10px;">
              <a-button @click="batchDelete()" :disabled="selectRowIds.length <=0" type="primary">删除</a-button>
            </span>
            <span v-if="!isBase && control.share" style="margin-right:10px;">
              <a-button @click="$refs.solutionShareModal.open(selectRowIds)" :disabled="selectRowIds.length <=0" type="primary">分享</a-button>
            </span>
          </template>
        </template>
        <vxe-table-column v-if="!isShare" type="checkbox" width="40" fixed="left"/>
        <vxe-table-column type="seq" title="序号" width="50" fixed="left"/>
        <vxe-table-column
          title="标题"
          field="title"
          min-width="260"
          remoteSort
          showHeaderOverflow
          fixed="left"
          showOverflow="tooltip">
          <template #default="{row}">
            <template>
              <a @click="$refs.solutionInfoModal.show(row)">{{ row.title }}</a>
            </template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="客户名称"
          field="customer"
          width="220"
          v-if="!isBase"
          remoteSort
          showHeaderOverflow
          showOverflow="tooltip"/>
        <vxe-table-column
          title="服务项目"
          field="serveProject"
          width="220"
          remoteSort
          showHeaderOverflow
          showOverflow="tooltip"/>
        <vxe-table-column
          title="行业"
          field="industry"
          width="140"
          remoteSort
          showHeaderOverflow
          showOverflow="tooltip"/>
        <vxe-table-column
          v-if="!isBase"
          title="星级"
          field="rate"
          width="130"
          remoteSort
          align="center"
          showHeaderOverflow
          showOverflow="tooltip">
          <template #default="{row}">
            <a-rate style="font-size:14px;" :value="row.rate ? row.rate / 2 : 0" :disabled="true" :allowHalf="true" />
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="附件"
          field="filePath"
          width="160"
          showHeaderOverflow
          showOverflow="tooltip">
          <template v-slot="{row}">
            <span v-for="(file,index) in getPaths(row.filePath)" :key="index" style="margin-right:10px;">
              <a title="点击下载" @click="downloadFile(file)" v-if="control.download">
                {{ file.name }}
              </a>
              <span v-else>
                {{ file.name }}
              </span>
              <a-tooltip style="cursor:pointer" placement="top" @click="preview(file.url,file.name)" v-if="control.preview">
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin-left:5px" />
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="创建人"
          field="creatorName"
          width="120"
          remoteSort
          showHeaderOverflow
          showOverflow="tooltip"/>
        <vxe-table-column
          title="创建时间"
          field="createTime"
          width="160"
          remoteSort
          align="center"
          showHeaderOverflow
          showOverflow="tooltip"/>
        <vxe-table-column
          v-if="!isShare"
          title="操作"
          width="180"
          fixed="right"
          showHeaderOverflow
          showOverflow="tooltip">
          <template v-slot="{row}">
            <template v-if="control.edit">
              <a @click="$refs.solutionModal.edit(row)">编辑</a>
            </template>
            <template v-if="control.delete">
              <a-divider type="vertical" v-if="control.edit"/>
              <a-popconfirm
                title="是否确定删除?"
                @confirm="deleteRow(row.id)"
              >
                <a>删除</a>
              </a-popconfirm>
            </template>
            <template v-if="control.share && !isBase">
              <a-divider type="vertical" v-if="control.edit || control.delete"/>
              <a @click="$refs.solutionShareInfoModal.open(row.id,row.title)">分享列表</a>
            </template>
          </template>
        </vxe-table-column>
      </ystable>
    </a-spin>
    <solution-modal ref="solutionModal" :preview="preview" @ok="search" :isBase="isBase" :type="type"/>
    <solution-share-modal ref="solutionShareModal" @ok="search(false)"/>
    <solution-share-info-modal ref="solutionShareInfoModal"/>
    <solution-info-modal ref="solutionInfoModal" />
    <preview-modal ref="previewModal"/>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import SolutionModal from './SolutionModal.vue'
import SolutionShareModal from './SolutionShareModal.vue'
import SolutionShareInfoModal from './SolutionShareInfoModal'
import SolutionInfoModal from './SolutionInfoModal'
import PreviewModal from '@/components/PreviewModal'
import Template from '@/views/doc/Template.vue'
export default {
  name: 'SolutionTab',
  components: {
    ystable,
    SolutionModal,
    SolutionShareModal,
    SolutionShareInfoModal,
    SolutionInfoModal,
    PreviewModal,
    Template
  },
  props: {
    isShare: {
      type: Boolean,
      default: false
    },
    url: {
      type: String,
      required: true
    },
    isBase: {
      type: Boolean,
      default: false
    },
    type: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      // 查询参数
      queryParams: { type: this.type },
      selectRowIds: [],
      control: {
        search: this.$auth('sys:solution:search'),
        add: this.$auth('sys:solution:add'),
        edit: this.$auth('sys:solution:edit'),
        delete: this.$auth('sys:solution:delete'),
        share: this.$auth('sys:solution:share'),
        preview: this.$auth('sys:solution:preview'),
        download: this.$auth('sys:solution:download')
      },
      spinning: false
    }
  },
  created () {
  },
  methods: {
    search (refresh) {
      this.selectRowIds = []
      this.$refs.table.refresh(refresh)
    },
    selectChange ({ records }) {
      this.selectRowIds = records.map(item => item.id)
    },
    deleteRow (id) {
      this.executorDelete([id])
    },
    batchDelete () {
      const self = this
      this.$confirm({
        title: '您确定要删除选中的解决方案吗?',
        onOk () {
          self.executorDelete(self.selectRowIds)
        },
        onCancel () {
        }
      })
    },
    executorDelete (ids) {
      this.spinning = true
      this.$http.post('/solution/delete', { ids }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    preview (filePath, filename) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(filePath, filename)
    },
    getPaths (filePath) {
      const result = []
      if (!filePath || filePath.trim().length <= 0) {
        return result
      }
      filePath.split(',').forEach(item => {
        const name = item.substring(item.lastIndexOf('/') + 14, item.length)
        result.push({ url: item, name: name })
      })
      return result
    },
    downloadFile (file) {
      this.$exportData('/patentBuying/download', { filePath: file.url }, file.name, this.$message)
    }
  }
}
</script>
