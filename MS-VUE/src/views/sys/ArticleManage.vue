<template>
  <div>
    <a-card :bordered="false">
      <a-spin :spinning="spinning" tip="请稍后...">
        <a-form layout="inline" v-if="control.search">
          <a-form-item label="标题">
            <a-input placeholder="请输入标题" v-model="queryParams.title" />
          </a-form-item>
          <a-form-item label="作者">
            <a-input placeholder="请输入作者" v-model="queryParams.author" />
          </a-form-item>
          <a-form-item label="类型">
            <a-select allowClear v-model="queryParams.type" style="width: 180px" placeholder="选择类型">
              <a-select-option value="0">公司新闻</a-select-option>
              <a-select-option value="1">行业动态</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="来源">
            <a-input placeholder="请输入来源" v-model="queryParams.source" />
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="search(true)">查询</a-button>
          </a-form-item>
        </a-form>
        <ystable
          ref="table"
          rowId="id"
          @completed="() => (selectArtIds = [])"
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :params="queryParams"
          queryUrl="article/getArtList"
          :toolbar="{
            custom: true,
            zoom: true,
            refresh: true,
          }"
        >
          <template v-slot:buttons>
            <template>
              <span v-if="control.add" style="margin-right: 10px">
                <a-button type="primary" @click="$refs.articleModal.add()">添加</a-button>
              </span>
              <span v-if="control.delete" style="margin-right: 10px">
                <a-button @click="batchDelete()" :disabled="selectArtIds.length <= 0" type="primary">删除</a-button>
              </span>
            </template>
          </template>
          <vxe-table-column type="checkbox" width="40" fixed="left" />
          <vxe-table-column type="seq" title="序号" width="50" fixed="left" />
          <vxe-table-column
            title="标题"
            field="title"
            min-width="200"
            remoteSort
            showHeaderOverflow
            fixed="left"
            align="center"
            showOverflow="tooltip"
          >
            <template #default="{ row }">
              <template>
                <a @click="$refs.articleInfoModal.show(row)">{{ row.title }}</a>
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="作者"
            field="author"
            width="180"
            align="center"
            showHeaderOverflow
            showOverflow="tooltip"
          />
          <vxe-table-column
            title="置顶权重"
            field="top"
            width="100"
            align="center"
            showHeaderOverflow
            showOverflow="tooltip"
          />
          <vxe-table-column
            title="类型"
            field="type"
            width="150"
            align="center"
            showHeaderOverflow
            showOverflow="tooltip"
          >
            <template #default="{ row }">
              {{ typeArr[row.type] }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="来源"
            field="source"
            width="180"
            align="center"
            showHeaderOverflow
            showOverflow="tooltip"
          >
          </vxe-table-column>
          <vxe-table-column
            title="发行日期"
            field="issueDate"
            width="180"
            remoteSort
            align="center"
            showHeaderOverflow
            showOverflow="tooltip"
          >
          </vxe-table-column>
          <vxe-table-column
            title="创建人"
            field="realName"
            width="140"
            remoteSort
            align="center"
            showHeaderOverflow
            showOverflow="tooltip"
          />
          <vxe-table-column
            title="创建时间"
            field="createTime"
            width="180"
            remoteSort
            align="center"
            showHeaderOverflow
            showOverflow="tooltip"
          />
          <vxe-table-column
            title="操作"
            width="150"
            fixed="right"
            align="center"
            showHeaderOverflow
            showOverflow="tooltip"
          >
            <template v-slot="{ row }">
              <template v-if="control.edit">
                <a @click="$refs.articleModal.edit(row)">编辑</a>
              </template>
              <template v-if="control.delete">
                <a-divider type="vertical" v-if="control.edit" />
                <a-popconfirm title="是否确定删除?" @confirm="deleteRow(row.id)">
                  <a>删除</a>
                </a-popconfirm>
              </template>
            </template>
          </vxe-table-column>
        </ystable>
      </a-spin>
      <article-modal ref="articleModal" @ok="search"/>
      <preview-modal ref="previewModal" />
      <article-info-modal ref="articleInfoModal" />
    </a-card>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import ArticleModal from './modules/ArticleModal.vue'
import ArticleInfoModal from './modules/ArticleInfoModal'
import PreviewModal from '@/components/PreviewModal'
import Template from '@/views/doc/Template.vue'
export default {
  name: 'SolutionTab',
  components: {
    ystable,
    ArticleModal,
    ArticleInfoModal,
    PreviewModal,
    Template
  },
  data () {
    return {
      typeArr: ['公司新闻', '行业动态'],
      // 查询参数
      queryParams: { type: this.type, author: this.author, source: this.source, title: this.title },
      selectArtIds: [],
      control: {
        search: this.$auth('sys:article:search'),
        delete: this.$auth('sys:article:del'),
        add: this.$auth('sys:article:add'),
        edit: this.$auth('sys:article:edit')
      },
      spinning: false
    }
  },
  created () {},
  methods: {
    search (refresh) {
      this.selectArtIds = []
      this.$refs.table.refresh(refresh)
    },
    selectChange ({ records }) {
      this.selectArtIds = records.map((item) => item.id)
    },
    deleteRow (id) {
      this.executorDelete([id])
    },
    batchDelete () {
      const self = this
      this.$confirm({
        title: '您确定要删除选中的文章吗?',
        onOk () {
          self.executorDelete(self.selectArtIds)
        },
        onCancel () {}
      })
    },
    executorDelete (ids) {
      this.spinning = true
      this.$http
        .post('/article/delArt', { ids })
        .then((res) => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.error(res.errorMessage || '删除失败')
          }
        })
        .finally(() => {
          this.spinning = false
        })
    }
  }
}
</script>
