<template>
  <a-card :bordered="false" v-if="$auth('doc:docFileStage:search')">
    <vxe-table
      ref="vt"
      :resizable="true"
      auto-resize
      highlight-hover-row
      show-overflow
      show-header-overflow
      :data="stages"
      :expand-config="{ labelField: 'value', lazy: true, loadMethod: loadMethod }"
    >
      <vxe-table-column type="expand" title="阶段" align="left">
        <template #content="{row}">
          <vxe-table
            ref="lf"
            :resizable="true"
            auto-resize
            highlight-hover-row
            show-overflow
            size="small"
            :loading="loadMap[row.key]"
            show-header-overflow
            :data="row.children"
          >
            <vxe-table-column title="序号" align="left" :width="50" type="seq" />
            <vxe-table-column title="文件名称" align="left" field="docName" />
            <vxe-table-column title="自动添加" align="left">
              <template v-slot="{ row }">
                {{ row.autoAdd ? '是' : '否' }}
              </template>
            </vxe-table-column>
            <vxe-table-column title="按月添加" align="left">
              <template v-slot="{ row }">
                {{ row.monthly ? '是' : '否' }}
              </template>
            </vxe-table-column>
            <vxe-table-column title="月份前缀" align="left">
              <template v-slot="{ row }">
                {{ row.mPrefix ? '是' : '否' }}
              </template>
            </vxe-table-column>
            <vxe-table-column title="需要编辑" align="left">
              <template v-slot="{ row }">
                {{ row.needEdit ? '是' : '否' }}
              </template>
            </vxe-table-column>
            <vxe-table-column v-if="$auth('doc:docFileStage:order')" title="排序" align="center" :width="100">
              <template v-slot="{ row, rowIndex }">
                <span style="padding-right:12px;">
                  <a-icon
                    v-if="rowIndex > 0 && rowIndex < (stageMaxSeq[row.stageKey] || -1)"
                    @click.stop="handleSort(rowIndex - 1, row.stageKey)"
                    title="向上移"
                    type="arrow-up"
                  />
                  <template v-else>
                    &nbsp;&nbsp;
                  </template>
                </span>
                <span style="padding-right:12px;">
                  <a-icon
                    v-if="rowIndex < (stageMaxSeq[row.stageKey] - 1 || -1)"
                    @click.stop="handleSort(rowIndex, row.stageKey)"
                    title="向下移"
                    type="arrow-down"
                  />
                  <template v-else>
                    &nbsp;&nbsp;
                  </template>
                </span>
              </template>
            </vxe-table-column>
            <vxe-table-column title="操作" align="center" :width="120">
              <template v-slot="{ row, rowIndex }">
                <a v-if="$auth('doc:docFileStage:setting')" @click="$refs.docFileSetting.edit(row, rowIndex)">设置</a>
                <template v-if="$auth('doc:docFileStage:del')">
                  <a-divider type="vertical" v-if="$auth('doc:docFileStage:setting')" />
                  <a-popconfirm title="是否确定删除?" @confirm="del(row.id, row.stageKey, rowIndex)">
                    <a>删除</a>
                  </a-popconfirm>
                </template>
              </template>
            </vxe-table-column>
          </vxe-table>
        </template>
      </vxe-table-column>
      <vxe-table-column title="操作" :width="130" align="center">
        <template v-if="$auth('doc:docFileStage:save')" v-slot="{ row }">
          <a @click="$refs.docFileList.add(row.key)">添加</a>
        </template>
      </vxe-table-column>
    </vxe-table>
    <doc-file-list-modal ref="docFileList" @ok="handlerOk" />
    <doc-file-setting-modal ref="docFileSetting" @editOk="editOk" />
  </a-card>
</template>

<script>
import DocFileListModal from './modules/DocFileListModal'
import DocFileSettingModal from './modules/DocFileSettingModal'
export default {
  name: 'DocFileStage',
  components: {
    DocFileSettingModal,
    DocFileListModal
  },
  data () {
    return {
      loadMap: [],
      stageMaxSeq: {},
      stages: []
    }
  },
  mounted () {
    this.$getDictionary(6).then(res => {
      if (res) {
        this.stages = res
      }
    })
  },
  methods: {
    loadMethod ({ row }) {
      this.loadMap[row.key] = true
      return this.$http
        .get('/docFileStage/queryDocFileStageList', { params: { stageKey: row.key } })
        .then(res => {
          this.stageMaxSeq[row.key] = 0
          if (res.data && res.success) {
            row.children = res.data
            this.stageMaxSeq[row.key] = res.data.length
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '获取阶段文档失败，请联系管理员。')
          }
          this.loadMap[row.key] = false
          return Promise.resolve(res.data)
        })
        .catch(error => {
          console.log(error.message)
        })
    },
    getStage (stageKey) {
      let current = null
      for (const row of this.stages) {
        if (row.key === stageKey) {
          current = row
          break
        }
      }
      return current
    },
    handleSort (index, stageKey) {
      this.loadMap[stageKey] = true
      if (index < 0) {
        this.$message.error('移动失败。')
        this.loadMap[stageKey] = false
        return
      }
      const current = this.getStage(stageKey)
      if (!current) {
        this.$message.error('移动失败。')
        this.loadMap[stageKey] = false
      }
      const currentRow = current.children[index]
      const nextRow = current.children[index + 1]
      if (!currentRow || !nextRow) {
        this.$message.error('移动失败。')
        this.loadMap[stageKey] = false
        return
      }
      this.$http.post('/docFileStage/updateSeq', [currentRow.id, nextRow.id]).then(res => {
        if (res.success && res.data) {
          const temp = nextRow.seq
          nextRow.seq = currentRow.seq
          currentRow.seq = temp
          this.$set(current.children, index, nextRow)
          this.$set(current.children, index + 1, currentRow)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '移动失败')
        }
        this.loadMap[stageKey] = false
      })
    },
    del (id, stageKey, index) {
      this.loadMap[stageKey] = true
      this.$http.post('/docFileStage/del', { id: id }).then(res => {
        if (res.success && res.data) {
          const current = this.getStage(stageKey)
          if (current && current.children) {
            this.stageMaxSeq[stageKey] = current.children.length - 1
            current.children.splice(index, 1)
          }
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
        this.loadMap[stageKey] = false
      })
    },
    editOk (stageKey, index, newRow) {
      this.loadMap[stageKey] = true
      const current = this.getStage(stageKey)
      if (current && current.children && current.children[index]) {
        for (const key in newRow) {
          current.children[index][key] = newRow[key]
        }
      }
      this.loadMap[stageKey] = false
    },
    handlerOk (stageKey) {
      if (!stageKey) {
        return
      }
      const current = this.getStage(stageKey)
      if (current) {
        this.loadMethod({ row: current })
      }
    }
  }
}
</script>
