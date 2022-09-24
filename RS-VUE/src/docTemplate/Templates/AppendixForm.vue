<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-09-14 11:55:10
 * @LastEditors: hm
 * @Description:记录及附件 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\AppendixForm.vue
-->
<template>
  <div>
    <div style="margin-bottom: 10px;">
      月份：
      <a-select
        v-model="content.month"
        :disabled="!isEdit"
        placeholder="请选择月份"
        style="width: 120px"
        @change="change">
        <a-select-option v-for="m in months" :key="m" :value="m">{{ m }}
        </a-select-option>
      </a-select>
    </div>
    <vxe-table
      id="AppendixForm"
      ref="table"
      :data="tableData"
      auto-resize
      highlight-current-row
      highlight-hover-row
      resizable
      row-id="id"
      show-overflow
      size="small"
    >
      <vxe-table-column
        align="center"
        header-align="center"
        title="序号"
        type="seq"
        width="60"></vxe-table-column>
      <vxe-table-column
        align="left"
        field="fileName"
        header-align="center"
        min-width="200"
        title="文件名称">
        <template #default="{row}">
          <a title="点击下载" @click="downloadFile(row)">{{ row.fileName
          }}{{ getExtension(row.filePath) }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column
        align="center"
        field="fileType"
        header-align="center"
        title="附件类型"
        width="150">
        <template #default="{row}">
          {{ fileTypeList[row.fileType] }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        align="center"
        field="uploadTime"
        header-align="center"
        title="记录日期"
        width="150">
        <template #default="{row}">
          {{ row.uploadTime | DayFormat }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        align="center"
        header-align="center"
        title="操作"
        width="150">
        <template #default="{row}">
          <a type="primary" @click="onPreview(row)">预览</a>
          <a-divider type="vertical"></a-divider>
          <a type="primary" @click="onEdit(row)">编辑</a>
          <a-divider type="vertical"></a-divider>
          <a type="primary" @click="removeRow(row)">移除</a>
        </template>
      </vxe-table-column>
    </vxe-table>
    <a-button
      :disabled="!content.month || !isEdit"
      style="width: 100%;font-weight: bolder"
      title="上传附件"
      type="dashed"
      @click="onUpload">上传文件
    </a-button>
    <upload
      ref="upload"
      :month="moment(content.month + '-1').startOf('month')"
      :paramData="{docFileId:docId,projectId}"
      accept="image/png,image/jpg,image/jpeg,image/gif,application/pdf"
      action="/docFileAttachment/upload"
      @success="success"></upload>
  </div>
</template>
<script>
import yearMiXin from '@/utils/yearMixin'
import { cloneDeep } from 'lodash'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import upload from '@/components/UploadModal/Upload.vue'
import { getAttachments } from '@/api/doc/appendixForm'
import moment from 'moment'
import dateMixin from '../Templates/js/dateMixin'

const fileTypeMap = {
  '300': 8006,
  '400': 8001,
  '500': 8004,
  '600': 8002,
  '700': 8003,
  '800': 8005
}
export default {
  name: 'AppendixForm',
  components: {
    upload
  },
  mixins: [yearMiXin, dateMixin],
  props: {
    docId: {
      type: Number,
      required: true
    },
    docFileId: {
      type: Number,
      required: true
    },
    projectId: {
      type: Number,
      required: true
    },
    stage: {
      type: Object,
      required: true
    },
    isEdit: {
      type: Boolean,
      required: true
    },
    dataMonth: {
      type: String,
      default: ''
    }
  },
  created () {
    this.content = getTemplateContent('appendixForm')
    this.BPContent = getTemplateContent('appendixForm')
    this.hsaFileDataNull = false
  },
  computed: {
    months () {
      var endDate = this.stage.endDate
      var beginDate = this.stage.beginDate
      var beginArray = beginDate.split('-').map(Number)
      var endArray = endDate.split('-').map(Number)
      var returnArry = []
      var begin = parseInt(beginArray[0])
      var end = parseInt(endArray[0])
      for (let y = begin; y <= end; y++) {
        const current = y === begin ? parseInt(beginArray[1]) : 1
        const last = y === end ? parseInt(endArray[1]) : 12
        for (let i = current; i <= last; i++) {
          returnArry.push(`${y}-${(i + '').padStart(2, '0')}`)
        }
      }
      return returnArry
    }
  },
  data () {
    return {
      uploadTitle: '上传附件',
      width: 960,
      content: {},
      BPContent: {},
      project: {},
      fileInfo: {},
      fileTypeMap,
      tableData: [],
      fileTypeList: {
        1: '会议纪要',
        2: '实验记录',
        3: '试制排期',
        4: '试制报表',
        5: '试制报告',
        6: '技术培训',
        7: '物料清单（BOM）',
        8: '作业指导书（SOP）',
        9: '发明专利',
        10: '实用新型专利',
        11: '外观设计专利',
        12: '计算机软件著作权',
        13: '集成电路布图设计',
        14: '科技查新报告',
        15: '论文期刊',
        16: '国家标准',
        17: '行业标准',
        18: '地方标准',
        19: '团体标准',
        20: '企业标准',
        21: '技术原理',
        22: '技术图纸',
        23: '技术方案',
        24: '技术规范',
        25: '工艺规程',
        26: '检测报告',
        27: '样品样机',
        99: '其他'
      }
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    },
    docId () {
      this.content = getTemplateContent('appendixForm')
      this.BPContent = getTemplateContent('appendixForm')
    }
  },
  methods: {
    handleTemplateEvent () {
      if (this.content.month) {
        this.handlerGetAttachments()
        return
      }
      if (this.dataMonth) {
        this.content.month = moment(this.dataMonth).format('YYYY-MM')
        this.BPContent.month = moment(this.dataMonth).format('YYYY-MM')
      }
      this.handlerGetAttachments()
    },
    onUpload () {
      this.$refs.upload.show(this.stage)
    },
    change (date) {
      this.fileDate = moment(date + '-1').startOf('month')
      // this.handlerGetAttachments()
      this.hsaFileDataNull = false
    },
    handlerGetAttachments () {
      const { projectId } = this
      getAttachments({ projectId, docFileId: this.docId }).then(data => {
        this.$set(this, 'tableData', data)
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
    },
    success (data) {
      this.handlerGetAttachments()
    },
    error (fileName, errorMessage) {
      this.$message.error(errorMessage)
    },
    getExtension (path) {
      if (!path) return
      return path.substring(path.lastIndexOf('.'))
    },
    downloadFile (record) {
      const txt = this.getExtension(record.filePath)
      this.$exportData('/sysDocument/downloadAttachment', {
        path: record.filePath,
        fileName: record.fileName + txt
      }, record.fileName + txt, this.$message)
    },
    onPreview (record) {
      const txt = this.getExtension(record.filePath)
      this.$preview({
        filePath: record.filePath,
        docName: record.fileName + txt,
        visible: true,
        url: '/document/appendixPreview'
      })
    },
    removeRow (record) {
      const tempRows = []
      const bkList = cloneDeep(this.content.list)
      var num = 0
      for (var i = 0; i < this.content.list.length; i++) {
        const row = this.content.list[i]
        if (row.num !== record.num) {
          row.num = ++num
          tempRows.push(row)
        }
      }
      // this.content.list = tempRows
      const slef = this
      this.$confirm({
        title: '您确定删除吗?',
        onOk () {
          //  slef.handleSaveData(record.fileName + '删除', bkList, tempRows)
          slef.handleSaveData(record.id)
        },
        onCancel () {
          slef.content.list = bkList
        }
      })
    },
    onEdit (record) {
      this.$refs.upload.edit(this.stage, record)
    },
    handleSaveData (id) {
      this.$http.post('/docFileAttachment/delUploadFile', { id }).then(res => {
        if (res.success && res.data) {
          this.handlerGetAttachments()
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
      })
    }
  }
}
</script>

<style>
</style>
