<!--
 * @Author: ldx
 * @Date: 2020-11-23 11:35:03
 * @LastEditTime: 2021-03-30 10:28:35
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\AuditPreviewModal.vue
-->
<template>
  <a-modal
    style="top: 0px;"
    :title="title"
    :width="800"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :bodyStyle="{ maxHeight:'84vh',height: '84vh',overflow:'auto'}"
    :destroyOnClose="true"
    @cancel="afterClose"
    :footer="null"
  >

    <a-spin id="spin" :spinning="spinning" style="height: 100%">
      <template v-if=" mode!==5">
        <ystable
          ref="table"
          height="calc(100% - 31px)"
          highlight-hover-row
          highlight-current-row
          show-overflow="title"
          resizable
          auto-resize
          :queryUrl="url"
          :params="params"
          :columns="column"
        ></ystable>
      </template>
      <template v-else>
        <div class="content" v-show="isDirectory">
          <a-list size="small" bordered :data-source="treeDirectorys">
            <a-list-item slot="renderItem" slot-scope="item">
              {{ item.index+1 }}、
              <a title="点击预览文件" @click="previewFile(item)">
                {{ `${item.rdTitle} 【${item.pname}】   ${item.docFileName}` }}
              </a>
            </a-list-item>
          </a-list>
        </div>
        <div class="previewWrap" v-show="!isDirectory">
          <div class="header">
            <a-row type="flex" justify="center" align="top">
              <a-button class="customSpace" type="primary" @click="switchDirectory"> 返回目录 </a-button>
              <a-button class="customSpace" type="primary" :disabled="isDisabled(false)" @click="switchDoc(false)"> 上一个 </a-button>
              <a-button class="customSpace" type="primary" :disabled="isDisabled(true)" @click="switchDoc(true)"> 下一个 </a-button>
            </a-row>
            <a-divider dashed/>
          </div>
          <div id="printContentWrap" class="contentWrap">
            <component id="content" ref="comp" class="content" :is="componentName"></component>
          </div>
        </div>
      </template>
    </a-spin>
  </a-modal>
</template>
<script>
import moment from 'moment'
import ystable from '@/components/Table/ystable'
const urls = {
  2: '/projectProgress/getEmployeeList',
  3: '/projectProgress/getEquipmentList',
  5: '/projectProgress/getProjectReport',
  7: '/projectProgress/getPatentList'
}
const columns = {
  2: [
    { type: 'seq', title: '序号', width: 60, headerAlign: 'center', align: 'center' },
    { field: 'enumber', title: '工号', width: 80, headerAlign: 'center', align: 'left' },
    { field: 'ename', title: '姓名', width: 100, headerAlign: 'center', align: 'left' },
    { field: 'etype',
      title: '人员类型',
      width: 100,
      headerAlign: 'center',
      align: 'left',
      formatter: ({ cellValue }) => {
        return cellValue && cellValue !== -1 ? this.$getEnums('rdEmployeeEnum').find(item => item.value === cellValue).label : '-'
      } },
    { field: 'deptName', title: '研发部门', width: 150, headerAlign: 'center', align: 'left' },
    { field: 'specialities', title: '专业', headerAlign: 'center', align: 'left' }
  ],
  3: [
    { type: 'seq', title: '序号', width: 60, headerAlign: 'center', align: 'center' },
    { field: 'ecode', title: '资产代码', width: 120, headerAlign: 'center', align: 'left' },
    { field: 'ename', title: '设备名称', width: 120, headerAlign: 'center', align: 'left' },
    { field: 'emodal', title: '设备型号', width: 120, headerAlign: 'center', align: 'left' },
    { field: 'etype',
      title: '设备类型',
      width: 120,
      headerAlign: 'center',
      align: 'left',
      formatter: ({ cellValue }) => {
        return cellValue ? this.$getEnums('equipmentEnum').find(item => item.value === Number(cellValue)).label : ''
      } },
    { field: 'deptName', title: '研发部门', width: 120, headerAlign: 'center', align: 'left' },
    { field: 'workshop', title: '车间', width: 120, headerAlign: 'center', align: 'left' },
    { field: 'productLine', title: '产线', width: 120, headerAlign: 'center', align: 'left' },
    { field: 'processSection', title: '工艺段', width: 120, headerAlign: 'center', align: 'left' }
  ],
  7: [
    { type: 'seq', title: '序号', width: 60, headerAlign: 'center', align: 'center' },
    { field: 'rdTitle', title: 'RD', headerAlign: 'center', align: 'left' },
    { field: 'patentNo', title: '专利号', headerAlign: 'center', align: 'left' },
    { field: 'patentName', title: '专利名称', headerAlign: 'center', align: 'left' },
    { field: 'mainType', title: '专利类型', headerAlign: 'center', align: 'left' },
    { field: 'applyDateTime',
      title: '申请时间',
      width: 120,
      headerAlign: 'center',
      align: 'center',
      formatter: ({ cellValue }) => {
        if (!cellValue) { return '' }
        const formatDate = moment(cellValue).format('YYYY-MM-DD')
        return formatDate === 'Invalid date' ? cellValue : formatDate
      } },
    { field: 'inventor', title: '发明人', headerAlign: 'center', align: 'left' }

  ]
}
export default {
  name: 'AuditPreviewModal',
  components: {
    // ...allComps,
    ystable
  },
  data () {
    return {
      componentName: 'empty',
      isVisible: false,
      title: '',
      urls,
      record: undefined,
      mode: undefined,
      columns,
      column: [],
      tableData: [],
      spinning: false,
      treeDirectorys: [],
      isDirectory: true,
      ishandle: false,
      currentPage: 1,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      },
      fileIndex: {
        min: 0,
        max: undefined,
        currentIndex: undefined,
        docs: {}
      },
      params: {
        year: undefined,
        companyId: undefined
      },
      url: undefined
    }
  },
  methods: {
    moment,
    show (mode, title, record) {
      this.title = title
      this.record = record
      this.mode = mode
      this.column = this.columns[mode]
      if (mode * 1 === 5) {
        this.getData(mode)
      }
      this.isVisible = true
      this.url = urls[mode]
      this.params.year = this.record.year
      this.params.companyId = this.record.companyId
      if (this.$refs.table) {
        this.$refs.table.refresh()
      }
    },
    afterClose () {
      this.isVisible = false
      this.title = ''
      this.mode = undefined
      this.record = undefined
      this.column = []
      this.tableData = []
      this.spinning = false
      this.treeDirectorys = []
      this.componentName = 'empty'
      this.isDirectory = true
      this.ishandle = false
      this.fileIndex = {
        min: 0,
        max: undefined,
        currentIndex: undefined,
        docs: {}
      }
      this.tablePage = {
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      }
      this.params = {
        year: undefined,
        companyId: undefined
      }
    },
    // 获取相应模块的数据
    getData (mode) {
      if (mode * 1 !== 5) {
        return
      }
      this.spinning = true
      this.$http.get(urls[mode], { params: { year: this.record.year, companyId: this.record.companyId, pageNo: this.tablePage.currentPage, pageSize: this.tablePage.pageSize } }).then((res) => {
        if (res.data && res.success) {
          this.tableData = res.data
          this.treeDirectorys = res.data.map((item, index) => {
            item['index'] = index
            this.fileIndex.docs[index] = item
            return item
          })
          this.fileIndex.max = this.treeDirectorys.length
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
        this.spinning = false
      })
    },
    // 预览
    previewFile (item) {
      if (item.docFileId) {
        if (!item.templateName) {
          item.templateName = 'empty'
        }
        this.getDocContent(item, true)
      }
    },
    /**
     * @description: 获取文档信息
     * @param docId { Number }
     * @param componentName { String }
     */
    getDocContent (item, isSwitchDirectory) { // 被目录组件调用
      const fileData = {}
      this.spinning = true
      if (!item.docFileId) {
        this.$refs.comp.initData({ content: undefined, docFileName: item.docFileName, docId: undefined })
        return
      }
      this.$http.post('/projectProgress/exportWord', { pDocFileId: item.docFileId, projectId: item.projectId, currentYear: item.year, companyId: item.companyId }).then(res => {
        if (res.data) {
          // 文件内容
          fileData['content'] = res.data
          fileData['docFileName'] = item.docFileName
          fileData['docId'] = item.docFileId
        } else {
          this.fileData = {}
        }
        item.templateName = item.templateName.replace('Form', '').toLocaleLowerCase()
        this.componentName = item.templateName
        // 处理目录索引
        this.fileIndex.currentIndex = item.index
        Promise.resolve()
      }).then(() => {
        this.$refs.comp.initData(fileData) // 更新预览组件的内容
        this.spinning = false
        if (isSwitchDirectory) {
          this.switchDirectory()
        }
      })
    },
    /**
     * @description: 切换目录与预览
     */
    switchDirectory () {
      this.isDirectory = !this.isDirectory
    },
    /**
     * @description: 切换预览文件的上一个或下一个
     * @param type {boolean} false: 上一个文件 true:下一个文件
     */
    switchDoc (type) {
      if (this.ishandle) { return }
      this.ishandle = !this.ishandl
      const { fileIndex } = this
      let index = this.fileIndex.currentIndex
      type ? index++ : index--
      if (index >= this.fileIndex.min && index <= this.fileIndex.max) {
        const doc = fileIndex.docs[index]
        this.getDocContent(doc, false)
        setTimeout(() => {
          this.ishandle = !this.ishandle
        }, 300)
      }
    },
    // 禁止上一个或下一个
    isDisabled (type) {
      const { fileIndex } = this
      if (!type) {
        return (fileIndex.currentIndex * 1) === 0
      } else {
        return (fileIndex.currentIndex * 1) === fileIndex.max * 1 - 1
      }
    },
    // 分页栏改变事件
    handlePageChange ({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getData(this.mode)
    }
  }
}
</script>
<style lang='less' scoped>
#spin /deep/ .ant-spin-container {
  height: 100%;
}
.previewWrap {
  height: 100%;
}
.previewWrap .contentWrap {
  height: calc(100% - 72px);
  overflow: auto;
}
.p_header,.p_footer{
  display: none;
}
.customSpace {
  margin: 0 5px;
}
.ant-spin-nested-loading {
  height: 100%;
}
</style>
