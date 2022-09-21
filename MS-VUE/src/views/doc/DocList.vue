<template>
  <a-card>
    <a-form layout="inline">
      <a-form-item label="清单类型">
        <a-select default-value="-1" v-model="type" style="width:140px;">
          <a-select-option value="-1">请选择</a-select-option>
          <a-select-option value="6001">研发技术中心</a-select-option>
          <a-select-option value="7001">研发项目</a-select-option>
          <a-select-option value="1001">研发成果</a-select-option>
          <a-select-option value="2001">知识产权</a-select-option>
          <a-select-option value="3001">研发档案</a-select-option>
          <!-- <a-select-option value="3002">附件清单</a-select-option> -->
          <a-select-option value="4001">研发意识</a-select-option>
          <a-select-option value="5001">项目其他财务信息</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="文档名">
        <a-input v-model="docName" placeholder="请输入文档名" />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          @click="$refs.table.refresh(true)"
          style="margin-left:10px;"
          v-if="$auth('doc:docList:search')"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/docList/queryDocList"
      :params="getParam()"
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :toolbar="{ zoom: true, custom:true, refresh:true }"
    >
      <template v-slot:buttons>
        <a-button
          type="primary"
          @click="$refs.model.add()"
          style="margin-right:10px"
          v-if="$auth('doc:docList:add')"
        >添加</a-button>
      </template>
      <vxe-table-column type="checkbox" width="50" align="center"/>
      <vxe-table-column
        title="序号"
        field="seq"
        width="60"
        align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="清单类型"
        field="listType"
        width="200"
        align="center"
        remoteSort
      >
        <template v-slot="{ row }">
          <span>{{ listType[row.listType] }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="分类"
        field="classify"
        width="200"
        remoteSort
        align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="子分类"
        field="subClassify"
        width="200"
        align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="研发活动"
        field="rdActivities"
        width="250"
        remoteSort
        align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="文档名"
        field="docName"
        width="250"
        align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="文档说明"
        field="desciption"
        width="200"
        align="center"
      ></vxe-table-column>
      <vxe-table-column
        title="操作"
        field="action"
        width="150"
        fixed="right"
        align="center"
      >
        <template v-slot="{ row }">
          <a v-if="$auth('doc:docList:edit')" @click="$refs.model.edit(row)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm
            v-if="$auth('doc:docList:del')"
            title="是否确定删除?"
            @confirm="del(row.id)"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <doc-list-modal ref="model" @ok="handleOk"></doc-list-modal>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import DocListModal from './modules/DocListModal'
export default {
  name: 'DocList',
  components: {
    ystable,
    DocListModal
  },
  data () {
    return {
      getData: [],
      type: '',
      docName: '',
      listType: {
        '6001': '研发技术中心',
        '7001': '研发项目',
        '1001': '研发成果',
        '2001': '知识产权',
        '3001': '研发档案',
        // '3002': '附件清单',
        '4001': '研发意识',
        '5001': '项目其他财务信息'
      },
      queryParam: {}
    }
  },
  methods: {
    getParam () {
      this.queryParam.listType = this.type
      this.queryParam.docName = this.docName
      return this.queryParam
    },
    del (id) {
      this.$http.post('/docList/del', { id: id })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
            this.$refs.table.refresh(true)
            // this.queryProject()
          } else {
            this.$message.info('所选文档项已上传文件，不能删除!')
          }
        })
    },
    handleOk (flag) {
      if (flag === 1) {
        this.$message.success('添加成功')
      } else if (flag === 2) {
        this.$message.success('更新成功')
      } else {
        this.$message.error('上传模板失败')
      }
      this.$refs.table.refresh(true)
    //  this.queryProject()
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}

</script>
