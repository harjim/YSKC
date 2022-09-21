<!--
 * @Author: ldx
 * @Date: 2021-03-16 17:04:15
 * @LastEditTime: 2021-05-18 14:12:38
 * @LastEditors: ldx
 * @Description:折旧分配说明
 * @FilePath: \RS-VUE\src\views\project\DepreciationCost.vue
-->
<template>
  <a-spin id="spin" :spinning="spinning" tip="加载中...">
    <a-card
      v-if="$auth('rdvoucher:depreciationCost:view')"
      class="contentPage"
      :bodyStyle="{height: '100%', overflow: 'auto'}">
      <a-form layout="inline">
        <select-project ref="selectProject" @projectSelect="projectSelected" :year="currentYear" :sign="2" />
      </a-form>
      <div class="container" v-if="projectId > 0">
        <div class="title-wrap">
          <div class="btn_wrap">
            <template v-if="$auth('rdvoucher:depreciationCost:setTemplate')">
              模板：
              <a-select
                :value="docInfo.templateId ? docInfo.templateId : undefined"
                placeholder="请选择模板"
                style="width: 150px; margin-right: 10px;"
                @change="selectedChange">
                <a-select-option
                  v-for=" template in templates"
                  :key="template.id"
                  :value="template.id"
                  :docFileId="template.docFileId"
                  :template="template">
                  {{ template.templateName }}
                </a-select-option>
              </a-select>
            </template>
            <a-button
              size="small"
              type="primary"
              class="btn_spcoe"
              v-show="!isEdit && $auth('rdvoucher:depreciationCost:edit') && docInfo.templateName === 'PDistributionReportForm'"
              @click="changeMode(true)">编辑</a-button>
            <a-button size="small" type="primary" class="btn_spcoe" v-show="isEdit" @click="onReturn">返回</a-button>
            <a-button
              size="small"
              type="primary"
              class="btn_spcoe"
              v-show="isEdit && $auth('rdvoucher:depreciationCost:edit')"
              @click="changeMode(false)">保存</a-button>
            <a-button
              size="small"
              type="primary"
              class="btn_spcoe"
              @click="exportFile"
              v-if="!isEdit && docInfo.docFileId && $auth('rdvoucher:depreciationCost:export')">导出</a-button>
          </div>
          <a-row type="flex" justify="center" align="middle">
            <a-col> <span class="title">RD人员设备折旧分配说明</span> </a-col>
          </a-row>
          <a-divider />
        </div>
        <div class="content-wrap">
          <div class="preview-wrap" v-show="!isEdit" v-html="docInfo.htmlData"></div>
          <div class="form-wrap" v-if="isEdit">
            <pDistribution-report-form
              ref="comp"
              :projectId="projectId"
              :docId="docInfo.docFileId"
              :isShowAuditFooter="false"></pDistribution-report-form>
          </div>
        </div>
      </div>
    </a-card>
  </a-spin>
</template>

<script>
import { mapGetters } from 'vuex'
import { SelectProject } from '@/components'
import PDistributionReportForm from '@/docTemplate/Templates/PDistributionReportForm'
import { getDocInfo, previewFile } from '@/api/project/FinalExpenseReport'
import { setDocTemplate } from '@/api/ForFutureReferenceData'
import { saveData } from '@/api/doc/index'
import { isEmpty, trim, isString, cloneDeep } from 'lodash'
export default {
  name: 'DepreciationCost',
  components: {
    SelectProject,
    PDistributionReportForm
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
      },
      templates: undefined
    }
  },
  watch: {
    projectId () {
      if (this.$auth('rdvoucher:depreciationCost:view')) {
        this.handleGetDocInfo()
        this.isEdit = false
      }
    }
  },
  mounted () {
    this.getEquipmentTemplate()
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
      getDocInfo({ projectId: this.projectId, docFileId: 22 }).then((response) => {
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
      const compSync = this.$refs.comp
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
      const content = this.$refs.comp.content
      const cStr = JSON.stringify(content)
      // 统计材料文本域字数
      let totalContentLength = 0
      if (this.$refs.comp.computedFields) {
        const computedFields = this.$refs.comp.computedFields
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
          // const compSync = this.$refs.comp
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
    },
    getEquipmentTemplate () {
      this.$http.get('projectDocFileData/getEquipmentTemplate').then(({ data }) => {
        this.templates = data
      })
    },
    selectedChange (value, option) {
      const { data: { attrs: { docFileId } } } = option
      const params = {
        id: this.docInfo.docFileId,
        year: this.currentYear,
        docFileId,
        templateId: value,
        projectId: this.projectId
      }
      if (this.docInfo.data) {
        this.$confirm({
          title: '警告',
          content: '切换模板会删除文档内容，请问是否切换？',
          onOk: () => {
            this.handleSetDocTemplate(params, docFileId)
          }
        })
      } else {
        this.handleSetDocTemplate(params, docFileId)
      }
    },
    handleSetDocTemplate (params, docFileId) {
      setDocTemplate(params).then(data => {
        if (data) {
          this.handleGetDocInfo(docFileId)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
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
