<!--
 * @Author: ldx
 * @Date: 2021-04-20 13:57:59
 * @LastEditTime: 2021-09-02 16:28:17
 * @LastEditors: zdf
 * @Description: 当个过程文档审核
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\DocAudit.vue
-->
<template>
  <tab-layout>
    <template #up>
      <div style=" height: 100%;">
        <appendix-form
          v-if="record.templateName === 'AppendixForm'"
          :docFile="{id:record.docFileId,projectId:record.rsProjectId,data: record.data}"
        />
        <div v-else ref="previewDoc" class="preview_doc" v-html="htmlData"></div>
      </div>
    </template>
    <template #down>
      <audit-log
        ref="auditLog"
        :params="logGetParams"
        :active="record.moduleId"
        :configParams="{
          rsProjectId: record.rsProjectId,
          year: record.year,
          companyId: record.companyId
        }"
        :currentObj="{projectId: record.rsProjectId, companyId: record.companyId}"
      ></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import TabLayout from './TabLayout'
import AuditLog from './AuditLog'
import AppendixForm from './AppendixForm'

export default {
  name: 'DocAudit',
  components: {
    TabLayout,
    AuditLog,
    AppendixForm
  },
  props: {
    record: {
      type: Object,
      required: true
    }
  },
  mounted () {
    this.getHtmlTemplateStr()
    this.logGetParams['docFileId'] = this.record.docFileId
  },
  data () {
    return {
      logGetParams: { docFileId: undefined },
      htmlData: ''
    }
  },
  methods: {
    /**
     * @description:  获取后台模板信息
     * @param {*} null
     * @return {*} null
     */
    getHtmlTemplateStr () {
      if (this.record.templateName) {
        this.htmlData = ''
        return
      }
      const params = {
        pDocFileId: this.record.docFileId,
        projectId: this.record.rsProjectId,
        currentYear: this.record.year,
        companyId: this.record.companyId
      }
      // this.spinning = true
      this.$http.get('/projectProgress/previewFile', { params: params }).then((res) => {
        if (res.data && res.success) {
          this.htmlData = res.data
        } else {
          this.htmlData = `<div style="font-size: 1.5em; font-weight: bold;">${res.errorMessage}</div>`
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.htmlData = ''
      }).finally((res) => {
        setTimeout(() => {
          // this.spinning = false
          this.renderContent()
        }, 300)
      })
    },
    /**
     * @description: 修改模板图片的端口号
     * @param {*}
     * @return {*}
     */
    renderContent () {
      this.$nextTick(() => {
        const arr = Array.from(this.$refs.previewDoc.getElementsByTagName('img'))
        if (arr.length) {
          arr.forEach(a => {
            a.src = a.src.replace(':9000', ':8000')
          })
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.preview_doc {
  height: 100%;
  overflow: auto;
}
</style>
