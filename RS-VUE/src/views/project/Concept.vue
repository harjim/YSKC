<!--
 * @Author: lzh
 * @Date: 2021-10-25 08:08:25
 * @LastEditors: lzh
 * @LastEditTime: 2022-03-01 08:24:31
 * @Description: 研发意识管理
 * @FilePath: \RS-VUE\src\views\project\Concept.vue
-->
<template>
  <a-card v-if="control.search">
    <vxe-grid
      :columns="annualColumns"
      :data="annualData"
      :loading="loading"
      :showHeader="false"
      :expand-config="{lazy: true, loadMethod: getMeetingFromList}"
      ref="grid"
      :cellStyle="() => 'background: #f8f8f8; border-bottom: 1px solid #e8eaec;' "
      :reserve="false"
    >
      <template v-slot:expand="{ row }">
        <a-table
          v-if="control.searchData"
          :columns="monthColumns"
          :dataSource="row.detailList"
          :pagination="false"
          :loading="cLoading"
          :rowKey="(record, index) => index">
          <template slot="files" slot-scope="text, record">
            <span v-for="(file,index) in record.files" :key="index" style="margin-right: 8px;">
              <a
                v-if="control.download"
                @click="onDownloadFile({path: file.filePath, name: file.fileName })"
                title="点击下载需求文档"
              >{{ file.fileName }}</a>
              <span v-else>{{ file.fileName }}</span>
              <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.filePath,name: file.fileName })" v-if="control.preview" >
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin:0 5px" />
              </a-tooltip>
              <a-popconfirm
                title="是否确定删除?"
                @confirm="delFile(file.id, row)"
                style="color:red;"
                v-if="control.delFile"
              >
                <a-tooltip placement="top">
                  <template slot="title">
                    <span>删除</span>
                  </template>
                  <a-icon type="close" />
                </a-tooltip>
              </a-popconfirm>
            </span>
          </template>
          <template slot="actions" slot-scope="text, record">
            <template v-if="control.upload">
              <a @click="$refs.conceptFilesModal.show(record, row)">上传</a>
              <a-divider type="vertical" />
            </template>
            <a @click="$refs.conceptDetailModal.show(record, row)">详情</a>
          </template>
        </a-table>
      </template>
      <template v-slot:total="{ row }">
        <span>{{ row.docFileIdCount || 0 }}/{{ row.idCount || 0 }}</span>
      </template>
    </vxe-grid>
    <concept-detail-modal ref="conceptDetailModal" />
    <concept-files-modal ref="conceptFilesModal" @success="updateMonthList" />
  </a-card>
</template>
<script>
import yearMixin from '@/utils/yearMixin'
import conceptDetailModal from './modules/ConceptDetailModal.vue'
import conceptFilesModal from './modules/ConceptFilesModal.vue'

export default {
  components: {
    conceptDetailModal,
    conceptFilesModal
  },
  data () {
    return {
      annualColumns: [
        { type: 'expand', width: 30, slots: { content: 'expand' } },
        { title: '月份', field: 'label' },
        { title: '完成度', width: 100, slots: { default: 'total' } }
      ],
      monthColumns: [
        { title: 'RD', dataIndex: 'rd', width: 100 },
        { title: '会议主题', dataIndex: 'theme', width: 200 },
        { title: '会议照片', scopedSlots: { customRender: 'files' } },
        { title: '操作', width: 150, scopedSlots: { customRender: 'actions' } }
      ],
      annualData: [],
      loading: false,
      cLoading: false,
      control: {
        search: this.$auth('project:meeting:search'),
        searchData: this.$auth('project:meeting:searchData'),
        delFile: this.$auth('project:meeting:delFile'),
        upload: this.$auth('project:meeting:upload'),
        download: this.$auth('project:meeting:download'),
        preview: this.$auth('project:meeting:preview')
      }
    }
  },
  mixins: [yearMixin],
  created () {
    this.annualData = [...this.monthOptions]
    this.getList()
  },
  methods: {
    search () {
      this.annualData = [...this.monthOptions]
      this.$refs.grid.clearRowExpand()
      this.getList()
    },
    getList (monthDate) {
      this.loading = true
      this.$http.get('/project/getAnnualData', { params: { year: this.currentYear, month: monthDate } }).then(res => {
        if (res.success && res.data) {
          const { data } = res
          const tempParam = {}
          for (const key in data) {
            const { month, docFileIdCount, idCount } = data[key]
            const m = +month.slice(5, 7) - 1
            tempParam[m] = {
              docFileIdCount,
              idCount
            }
          }
          for (let i = 0; i < this.annualData.length; i++) {
            if (tempParam[i]) {
              this.annualData[i].docFileIdCount = tempParam[i].docFileIdCount
              this.annualData[i].idCount = tempParam[i].idCount
            } else if (!monthDate) {
              this.annualData[i].docFileIdCount = 0
              this.annualData[i].idCount = 0
            }
          }
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      }).finally(() => {
        this.loading = false
      })
    },
    getMeetingFromList ({ row }) {
      let month = +row.value + 1
      month = month < 10 ? '0' + month : month
      row.date = `${this.currentYear}-${month}-01 00:00:00`
      this.cLoading = true
      return new Promise((resolve, reject) => {
        this.$http.get('/project/getMeetingFromList', { params: { month: row.date } })
          .then(res => {
            if (res.success && res.data) {
              row.detailList = res.data
              resolve()
            } else {
              reject(new Error())
            }
          }).catch(() => {
            reject(new Error())
          }).finally(() => {
            this.cLoading = false
          })
      })
    },
    onDownloadFile ({ name, path }) {
      this.$exportData('/project/downloadFile', { fileName: name, path: path }, name, this.$message)
    },
    onPreview ({ name, path }) {
      if (path === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$preview({
        filePath: path,
        docName: name,
        visible: true,
        url: '/project/preview'
      })
    },
    delFile (id, row) {
      this.$http.post('/project/delMeetingFile', { ids: [id] })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
            this.updateMonthList(row)
          } else {
            this.$message.error('删除失败')
          }
        }).finally(res => {
          this.getList()
        })
    },
    updateMonthList (row) {
      this.getList(row.date)
      this.$refs.grid.expandConfig.loadMethod({ row })
    }
  }
}
</script>
