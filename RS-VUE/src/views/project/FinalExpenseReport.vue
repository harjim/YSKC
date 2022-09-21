<!--
 * @Author: ldx
 * @Date: 2021-03-11 08:29:19
 * @LastEditTime: 2022-03-29 18:15:06
 * @LastEditors: zdf
 * @Description: 费用决算报告
 * @FilePath: \RS-VUE\src\views\project\FinalExpenseReport.vue
-->
<template>
  <a-spin id="spin" :spinning="spinning" tip="加载中...">
    <a-card v-if="$auth('project:finalExpenseReport:view')" class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto'}">
      <a-form layout="inline">
        <select-project ref="selectProject" @projectSelect="projectSelected" :year="currentYear" :sign="2"/>
      </a-form>
      <div class="container" v-if="projectId > 0">
        <div class="title-wrap">
          <div class="btn_wrap">
            <a-button size="small" type="primary" class="btn_spcoe" v-show="!isEdit && $auth('project:finalExpenseReport:edit')" @click="changeMode(true)">编辑</a-button>
            <a-button size="small" type="primary" class="btn_spcoe" v-show="isEdit" @click="onReturn">返回</a-button>
            <a-button size="small" type="primary" class="btn_spcoe" v-show="isEdit && $auth('project:finalExpenseReport:edit')" @click="changeMode(false)">保存</a-button>
            <a-button
              size="small"
              type="primary"
              class="btn_spcoe"
              @click="exportFile"
              v-if="!isEdit && docInfo.docFileId && $auth('project:finalExpenseReport:export')">导出</a-button>
          </div>
          <a-row type="flex" justify="center" align="middle">
            <a-col> <span class="title">费用决算报告</span> </a-col>
          </a-row>
          <a-divider/>
        </div>
        <div class="content-wrap">
          <div class="preview-wrap" v-show="!isEdit" v-html="docInfo.htmlData"></div>
          <div class="form-wrap" v-if="isEdit">
            <final-project-cost-form :projectId="projectId" :docId="docInfo.docFileId" ref="finalProjectCost" :isShowAuditFooter="false"></final-project-cost-form>
          </div>
        </div>
      </div>
    </a-card>
  </a-spin>
</template>

<script>
import { mapGetters } from 'vuex'
import { SelectProject } from '@/components'
import FinalProjectCostForm from '@/docTemplate/Templates/FinalProjectCostForm'
import { getDocInfo, previewFile } from '@/api/project/FinalExpenseReport'
import { saveData } from '@/api/doc/index'
import { isEmpty, trim, isString, cloneDeep } from 'lodash'
export default {
  name: 'FinalExpenseReport',
  components: {
    SelectProject,
    FinalProjectCostForm
  },
  data () {
    return {
      spinning: false,
      projectId: undefined,
      projectInfo: undefined,
      isEdit: false,
      docInfo: {
        data: undefined,
        docFileId: undefined, // 文档id
        pdocFileId: undefined, // 文档内容id
        docFileName: undefined,
        templateName: undefined,
        htmlData: undefined,
        commonMap: undefined
      }
    }
  },
  watch: {
    projectId () {
      if (this.$auth('project:finalExpenseReport:view')) {
        this.handleGetDocInfo()
        this.isEdit = false
      }
    }
  },
  mounted () {
  },
  computed: {
    ...mapGetters(['currentYear', 'companyId'])
  },
  methods: {
    projectSelected (projectId, projectInfo) {
      this.projectId = projectId
      this.projectInfo = projectInfo
    },
    handleGetHtmlData () {
      const { currentYear, companyId, projectId } = this
      const params = { currentYear, companyId, projectId, pDocFileId: this.docInfo.docFileId }
      previewFile(params).then((response) => {
        if (response.data && response.success) {
          this.docInfo.htmlData = response.data
        }
      })
    },
    handleGetDocInfo () {
      this.spinning = true
      for (const key in this.docInfo) {
        if (Object.hasOwnProperty.call(this.docInfo, key)) {
          this.docInfo[key] = undefined
        }
      }
      const temp = cloneDeep(this.docInfo)
      getDocInfo({ projectId: this.projectId, docFileId: 34 }).then((response) => {
        if (response.data && response.success) {
          Object.assign(this.docInfo, response.data)
          return Promise.resolve(true)
        } else {
          Object.assign(this.docInfo, temp)
          return Promise.resolve(false)
        }
      }).then((response) => {
        if (response) {
          this.handleGetHtmlData()
        } else {
          this.docInfo.htmlData = ''
        }
      }).catch((error) => {
        console.log('error function handleGetDocInfo', error)
      }).finally(() => {
        this.spinning = false
      })
    },
    /**
     * @description: 给组件赋值
     * @param {*} null
     * @return {*} null
     */
    initComponentData () {
      this.spinning = true
      const compSync = this.$refs.finalProjectCost
      const fileInfo = {
        docName: this.docInfo.docFileName,
        docId: this.docInfo.docFileId,
        formName: this.docInfo.templateName,
        currentYear: this.currentYear,
        saveDocId: this.docInfo.pdocFileId,
        docFileId: this.docInfo.docFileId
      }
      if (compSync) {
        compSync.fileInfo = fileInfo // 给组件赋文件信息数据
        Object.assign(compSync.project, this.projectInfo) // 给组件赋项目对象数据
        compSync.content = cloneDeep(compSync.BPContent) // 重置组件content内容
      }
      if (this.docInfo.data) { // 给组件赋表单内容数据
        const tempContent = JSON.parse(this.docInfo.data)
        Object.assign(compSync.content, tempContent)
      }
      this.spinning = false
    },
    /**
     * @description: 保存费用决算报告文档
     * @param {*} null
     * @return {*} null
     */
    handleSaveData () {
      this.spinning = true
      const content = this.$refs.finalProjectCost.content
      const cStr = JSON.stringify(content)
      // 统计材料文本域字数
      let totalContentLength = 0
      if (this.$refs.finalProjectCost.computedFields) {
        const computedFields = this.$refs.finalProjectCost.computedFields
        totalContentLength = this.handleComputedFields(computedFields, content)
      }
      const postData = {
        id: this.docInfo.pdocFileId ? this.docInfo.pdocFileId : undefined,
        pDocFileId: this.docInfo.docFileId,
        projectId: this.projectId,
        data: cStr,
        wordLength: totalContentLength,
        filledItems: 0,
        totalItems: 0
      }
      saveData(postData).then(res => {
        if (res.data) {
          return Promise.resolve(true)
        } else {
          this.docInfo.htmlData = ''
          this.$message.error('操作失败')
          // const compSync = this.$refs.finalProjectCost
          // if (compSync.BPContent) { // 重置组件content内容
          //   compSync.content = cloneDeep(compSync.BPContent)
          // }
          return Promise.resolve(false)
        }
      }).then((response) => {
        if (response) {
          this.handleGetDocInfo()
        }
      }).finally(() => {
        this.spinning = false
        // this.isEdit = true
      })
    },
    changeMode (isEdit) {
      this.isEdit = isEdit
      if (isEdit) {
        this.$nextTick(() => {
          this.initComponentData()
        })
      } else {
        this.handleSaveData()
      }
    },
    onReturn () {
      this.isEdit = false
    },
    /**
     * @description: 统计字数
     * @param {Array} computedFields
     * @param {Object} content
     * @return {number} totalContentLength
     */
    handleComputedFields (computedFields, content) {
      let totalContentLength = 0
      if (!Array.isArray(computedFields) || isEmpty(content)) {
        return
      }
      computedFields.forEach((item, index) => {
        const textContent = trim(content[item])
        if (isEmpty(textContent)) { return false }
        if (isString(textContent)) {
          totalContentLength += textContent.length
        }
      })
      return totalContentLength
    },
    /**
     * @description:导出过程材料
     * @param {*} null
      *@return {*} null
     */
    exportFile () {
      this.spinning = true
      this.$exportData(
        '/projectDocFileData/exportPdf',
        { pDocFileId: this.docInfo.docFileId, docName: this.docInfo.docFileName, projectId: this.projectId, currentYear: this.currentYear },
        `${this.docInfo.docFileName}.pdf`,
        this.$message).then(() => {
        this.spinning = false
      })
    }
  }
}
</script>

<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
  position: relative;
   .container {
      height: calc(100% - 39px);
     .title-wrap {
        position: relative;
        .title {
          font-size: 18px;
          font-weight: bold;
        }
      }
     .content-wrap {
        height: calc(100% - 38px);
        overflow: auto
     }
   }
   .ant-divider-horizontal {
     margin: 10px 0;
   }
   .btn_wrap {
     position: absolute;
     top: 1px;
     right: 26px;
     .btn_spcoe {
       margin: 0 3px;
     }
   }
  }
#spin {
  height: 100%;
  width: 100%;
  & /deep/ .ant-spin-nested-loading {
    height: 100%;
  }
  & /deep/ .ant-spin-container {
    height:  100%;
  }
}
</style>
