<!--
 * @Author: ldx
 * @Date: 2021-07-02 08:57:17
 * @LastEditTime: 2021-07-05 14:46:43
 * @LastEditors: ldx
 * @Description: 附件列表
 * @FilePath: \RS-VUE\src\views\project\modules\AttachmentsModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    :bodyStyle="{height: '60vh'}"
  >
    <div id="modal" style="height: 100%;">
      <a-spin id="spin" tip="加载中..." :spinning="spinning" style="height: 100%;">
        <vxe-table
          :data="patentData"
          border="full"
          size="small"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
        >
          <vxe-table-column
            field="fileName"
            title="名称"
            width="120"
            align="left"
            header-align="center"
            fixed="left" >
            <template #default="{row}">
              <template v-if="row.fileType === 8">
                <a-popover
                  id="popover"
                  title="日志"
                  trigger="click"
                  :getPopupContainer="getPopupContainer"
                  :arrowPointAtCenter="true"
                  placement="right"
                  :overlayStyle="{width:'600px',height: '450px'}"
                >
                  <div slot="content" style="height:400px;">
                    <a-spin id="contentSpin" tip="加载中..." :spinning="contentSpinning" style="height: 100%;">
                      <vxe-table
                        size="small"
                        border="full"
                        height="100%"
                        highlight-hover-row
                        show-overflow
                        resizable
                        auto-resize
                        :data="opinionsData">
                        <vxe-table-column type="seq" title="序号" width="60" align="left" header-align="center" ></vxe-table-column>
                        <vxe-table-column field="opinion" title="建议" minWidth="200" align="left" header-align="center" ></vxe-table-column>
                        <vxe-table-column field="createTime" title="日期" minWidth="150" align="center" header-align="center" ></vxe-table-column>
                      </vxe-table>
                    </a-spin>
                  </div>
                  <div
                    style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- <span>{{ row.fileName }}</span><a-icon title="点击查看日志" type="profile" /> -->
                    <span>{{ row.fileName }}</span><a-icon title="点击查看日志" type="profile" @click="getOpinionsData"/>
                  </div>
                </a-popover>
              </template>
              <template v-else>
                {{ row.fileName }}
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column field="files" title="文件" minWidth="250" align="left" header-align="center" >
            <template #default="{row}">
              <span v-for="( file ,index) in row.files" :key="index" style="margin-left:10px;" >
                <a @click="downloadFile(file.filePath,row,file)" title="点击下载" style="margin-left:3px;">{{ file.fileName }}</a>
                <a-icon title="预览" type="eye" style="margin-left:5px" @click="onPreview(file.filePath,file.fileName)" />
              </span>
            </template>
          </vxe-table-column>
        </vxe-table>
      </a-spin>
    </div>
    <template slot="footer">
      <a-button @click="onClose">
        关闭
      </a-button>
    </template>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'
import UploadFile from '@/components/UploadFile'
import { getPatentFiles, getPatentOpinions } from '@/api/patent/patent'

export default {
  name: 'AttachmentsModal',
  components: {
    ystable,
    UploadFile
  },
  data () {
    return {
      isVisible: false,
      title: '',
      queryParams: {},
      datas: [
        { name: '1' },
        { name: '2' },
        { name: '3' },
        { name: '4' }
      ],
      patentData: [],
      record: undefined,
      isPatentNo: false,
      opinionsData: [],
      spinning: false,
      contentSpinning: false,
      params: {}
    }
  },
  methods: {
    show (record, isPatentNo = false, title = '附件列表') {
      this.isVisible = true
      this.title = title
      this.record = record
      this.isPatentNo = isPatentNo
      this.params = {}
      if (isPatentNo) {
        this.params.patentNo = record.patentNo
      } else {
        this.params.patentId = record.id
      }
      this.handlerGetPatentFiles(this.params)
      // this.getOpinionsData()
    },
    afterClose () {
      this.record = undefined
      this.patentData = []
      this.isPatentNo = false
      this.opinionsData = []
      this.spinning = false
      this.contentSpinning = false
      this.params = {}
    },
    getPopupContainer () {
      return document.querySelector('#modal')
    },
    async handlerGetPatentFiles (params) {
      // 这代码我不想怎么写的，要怪就要怪后端给的数据（沟通无果） 2021-07-03 by lidx
      // 1:受理通知书 2:实质审查资料 3:授权通知书 4:知识产权证书 5:缴费收据 6:其他 7:交底书 8:申请文件
      this.spinning = true
      const fileNames = { 1: '受理通知书', 2: '实质审查资料', 3: '授权通知书', 4: '知识产权证书', 5: '缴费收据', 6: '其他', 7: '交底书', 8: '申请文件', 9: '发明人信息' }
      const fileLists = [
        { fileType: 7, fileName: undefined, files: [], downloadType: 1, isDisabled: true },
        { fileType: 9, fileName: undefined, files: [], downloadType: 3, isDisabled: true },
        { fileType: 8, fileName: undefined, files: [], downloadType: 2 },
        { fileType: 1, fileName: undefined, files: [] },
        { fileType: 2, fileName: undefined, files: [] },
        { fileType: 3, fileName: undefined, files: [] },
        { fileType: 4, fileName: undefined, files: [] },
        { fileType: 5, fileName: undefined, files: [] },
        { fileType: 6, fileName: undefined, files: [] }
      ]
      const lists = fileLists.map(row => {
        row.fileName = fileNames[row.fileType]
        return row
      })
      const result = await getPatentFiles(params).then(data => {
        return Promise.resolve(data)
      })
      if (Object.keys(result).length) {
        lists.forEach((row) => {
          if (this.isSpecial(row.fileType)) {
            row['files'] = this.getFiles(result[row.fileType])
          } else {
            row['files'] = result[row.fileType] || []
          }
        })
      }
      this.patentData = fileLists
      this.spinning = false
    },
    success () {
      this.handlerGetPatentFiles(this.params)
    },
    getOpinionsData () {
      this.contentSpinning = true
      getPatentOpinions(this.params).then(data => {
        if (data && data.length) {
          data.splice(0, 1)
          this.opinionsData = data
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.contentSpinning = false
      })
    },
    getFiles (filePathStr) {
      if (!filePathStr) { return [] }
      const filePaths = filePathStr.split(',')
      if (!filePaths.length) { return [] }
      const fileAry = []
      filePaths.forEach(filePath => {
        const nameIndex = filePath.lastIndexOf('/')
        const fileName = filePath.substring(nameIndex).substring(14)
        fileAry.push({ fileName, filePath })
      })
      return fileAry
    },
    getPatentFileName (path) {
      if (!path) { return '' }
      const nameIndex = path.lastIndexOf('/')
      const name = path.substring(nameIndex).substring(14)
      return name
    },
    onPreview (filePath) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      const filename = this.getPatentFileName(filePath)
      this.$preview({
        filePath: filePath,
        docName: filename || '',
        visible: true
      })
    },
    downloadFile (path, record, file) {
      if (this.isSpecial(record.fileType)) {
        const params = { type: record.downloadType, path }
        Object.assign(params, this.params)
        this.$exportData('/patentPlan/download', params, file.fileName, this.$message)
      } else {
        this.$exportData('/patentPlan/downloadPatentFile', { id: file.id }, file.fileName, this.$message)
      }
    },
    onClose () {
      this.isVisible = false
    },
    isSpecial (fileType) {
      const specialFileType = [7, 8, 9]
      return specialFileType.includes(fileType)
    }
  }
}
</script>
<style lang='less' scoped>
#spin /deep/ .ant-spin-container {
  height: 100%;
}
#contentSpin /deep/ .ant-spin-container {
  height: 100%;
}
</style>
