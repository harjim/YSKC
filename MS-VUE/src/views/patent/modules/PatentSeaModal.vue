<!--
 * @Author: ldx
 * @Date: 2021-07-10 08:46:48
 * @LastEditTime: 2021-07-14 18:33:57
 * @LastEditors: ldx
 * @Description: 专利公海
 * @FilePath: \MS-VUE\src\views\patent\modules\PatentSeaModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="900"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    okText="添加"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-form layout="inline">
      <a-form-item label="专利号/申请号">
        <a-input v-model="queryParams.patentNo" style="width:180px;" placeholder="请输入专利号/申请号"></a-input>
      </a-form-item>
      <a-form-item label="专利名称">
        <a-input v-model="queryParams.patentName" style="width:180px;" placeholder="请输入专利名称"></a-input>
      </a-form-item>
      <a-form-item label="发明人">
        <a-input v-model="queryParams.inventor" style="width:180px;" placeholder="请输入发明人"></a-input>
      </a-form-item>
      <a-form-item label="专利类型">
        <a-select v-model="queryParams.mainType" :allowClear="true" style="width:180px;" placeholder="请选择专利类型">
          <a-select-option value="1">发明专利</a-select-option>
          <a-select-option value="2">实用新型</a-select-option>
          <a-select-option value="3">外观设计</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="success_cb">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/patentBuying/getPatentSea"
      :params="queryParams"
      border="full"
      size="small"
      header-align="center"
      highlight-hover-row
      show-overflow="title"
      resizable
      auto-resize
      @checkbox-change="checkboxChange"
      @checkbox-all="checkboxAll"
      @completed="completed"
      :toolbar="{ custom: true, zoom:true, refresh:true }"
    >
      <template #buttons>
        <a-button type="primary" size="small" @click="onAdd" v-if="auth.addPatent">新增专利</a-button>
      </template>
      <vxe-table-column type="checkbox" width="40" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column type="seq" title="序号" width="50" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column field="patentNo" title="专利号" width="100" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column field="patentName" title="专利名称" width="100" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="专利类型" width="100" align="center" header-align="center">
        <template #default="{row}">
          {{ mainTypeMap[row.mainType] }}
        </template>
      </vxe-table-column>
      <vxe-table-column field="inventor" title="发明人" width="100" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column field="" title="专利资料" minWidth="250" align="left" header-align="center">
        <template #default="{row}">
          <span v-for="(file,index) in transFormStrToAry(row.filePath)" :key="index">
            <a
              v-if="auth.download"
              @click="onDownloadFile({path: file.url,name: file.name })"
              title="点击下载专利资料"
            >{{ file.name }}</a>
            <span v-else>{{ file.name }}</span>
            <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.url,name: file.name })" v-if="file.url && auth.preview" >
              <template slot="title">
                <span>预览</span>
              </template>
              <a-icon type="eye" style="margin:0 5px" />
            </a-tooltip>
          </span>
        </template>
      </vxe-table-column>
      <vxe-table-column field="" title="申请日期" width="100" align="center" header-align="center">
        <template #default="{row}">
          {{ row.applyDateTime | DateFormat }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="操作" width="100" align="center" header-align="center" fixed="right">
        <template #default="{row}">
          <a @click="onEdit(row)" v-if="auth.editPatent">编辑</a>
          <a-divider type="vertical" v-if="auth.delPatent"></a-divider>
          <a-popconfirm title="您确定要删除吗？" @confirm="onDel(row)" v-if="auth.delPatent">
            <a >删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <patent-form-modal ref="patentFormModal" @success="success_cb" :previewCB="previewCB" ></patent-form-modal>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'
import PatentFormModal from './PatentFormModal'
import { relatedDemand, delPatentSea } from '@/api/patent/demandList'
export default {
  name: 'PatentSeaModal',
  components: {
    ystable,
    PatentFormModal
  },
  props: {
    previewCB: {
      type: Function,
      required: true
    },
    auth: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      isVisible: false,
      title: '',
      queryParams: {},
      mainTypeMap: {
        1: '发明专利',
        2: '实用新型',
        3: '外观设计'
      },
      selectedRecords: [],
      demandRecord: undefined
    }
  },
  methods: {
    show (demandRecord, title = '专利列表') {
      this.isVisible = true
      this.title = `${title}【${demandRecord.companyName}】`
      this.demandRecord = demandRecord
      this.success_cb()
    },
    afterClose () {
      this.demandRecord = undefined
      this.queryParams = {}
      this.selectedRecords = []
    },
    handleSubmit () {
      const postParams = []
      this.selectedRecords.forEach(item => {
        postParams.push({ demandId: this.demandRecord.id, id: item.id })
      })
      relatedDemand(postParams).then(data => {
        this.$message.success('操作成功！')
        this.$emit('success', [1])
        this.isVisible = false
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
    },
    onAdd () {
      this.$refs.patentFormModal.show()
    },
    onEdit (record) {
      this.$refs.patentFormModal.edit(record)
    },
    onDel (record) {
      delPatentSea([record.id]).then(data => {
        this.success_cb()
        this.$message.success('操作成功！')
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
    },
    success_cb () {
      if (!this.$refs.table) { return }
      this.$refs.table.refresh()
    },
    transFormStrToAry (filePaths) {
      if (!filePaths) return []
      const files = []
      filePaths.split(',').forEach(path => {
        const name = path.substring(path.lastIndexOf('/') + 14)
        files.push({
          uid: path,
          name,
          status: 'done',
          url: path
        })
      })
      return files
    },
    onPreview ({ path, name }) {
      this.previewCB({ path, name })
    },
    onDownloadFile (file) {
      this.$exportData('/patentBuying/download', { filePath: file.path }, file.name, this.$message)
    },
    completed ({ data: { data } }) {
      this.selectedRecords = []
    },
    checkboxChange ({ records }) {
      this.selectedRecords = records
    },
    checkboxAll ({ records, checked }) {
      this.selectedRecords = records
    }
  }
}
</script>
<style lang='less' scoped>
</style>
