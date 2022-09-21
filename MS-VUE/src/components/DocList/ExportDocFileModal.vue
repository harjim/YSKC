<!--
 * @Author: zdf
 * @Date: 2021-10-19 09:24:52
 * @LastEditTime: 2022-05-12 10:17:11
 * @LastEditors: zdf
 * @Description: 导出过程文档modal
 * @FilePath: \MS-VUE\src\components\DocList\ExportDocFileModal.vue
-->
<template>
  <a-modal
    :width="500"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="closeModal"
    :confirmLoading="confirmLoading">
    <a-form>
      <a-form-item label="生成页眉" :labelCol="{span: 6}" :wrapperCol="{span:16}">
        <a-radio-group v-model="exportParams.header">
          <a-radio :value="true">
            是
          </a-radio>
          <a-radio :value="false">
            否
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="引入签名" :labelCol="{span: 6}" :wrapperCol="{span:16}">
        <a-radio-group v-model="exportParams.importFooterName">
          <a-radio :value="true">
            是
          </a-radio>
          <a-radio :value="false">
            否
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="生成页码" :labelCol="{span: 6}" :wrapperCol="{span:16}">
        <a-radio-group v-model="exportParams.pageNum">
          <a-radio :value="true">
            是
          </a-radio>
          <a-radio :value="false">
            否
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item v-if="hasCatalogue" label="生成目录" :labelCol="{span: 6}" :wrapperCol="{span:16}">
        <a-radio-group v-model="exportParams.catalogue">
          <a-radio :value="true">
            是
          </a-radio>
          <a-radio :value="false">
            否
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item v-if="hasCover" label="导出封面" :labelCol="{span: 6}" :wrapperCol="{span:16}">
        <a-radio-group v-model="exportParams.cover">
          <a-radio :value="true">
            是
          </a-radio>
          <a-radio :value="false">
            否
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item v-if="hasBudget" label="预算详情" :labelCol="{span: 6}" :wrapperCol="{span:16}">
        <a-radio-group v-model="exportParams.budgetDetail">
          <a-radio :value="true">
            有
          </a-radio>
          <a-radio :value="false">
            无
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item v-if="hasUtility" label="导出效用报告" :labelCol="{span: 6}" :wrapperCol="{span:16}">
        <a-radio-group v-model="exportParams.utility">
          <a-radio :value="true">
            是
          </a-radio>
          <a-radio :value="false">
            否
          </a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item v-if="hasMerge" label="合并导出" :labelCol="{span: 6}" :wrapperCol="{span:16}">
        <a-radio-group v-model="exportParams.merge">
          <a-radio :value="true">
            是
          </a-radio>
          <a-radio :value="false">
            否
          </a-radio>
        </a-radio-group>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { getStore, setStore } from '@/utils/storage'

export default {
  data () {
    return {
      docExportParamsKey: 'docExportParams',
      datas: {},
      visible: false,
      title: '',
      confirmLoading: false,
      exportParams: {
        importFooterName: false,
        pageNum: false,
        catalogue: false,
        cover: true,
        budgetDetail: true,
        utility: false,
        header: true,
        merge: false
      },
      hasUtility: false,
      hasCatalogue: true,
      hasCover: true,
      hasBudget: true,
      hasMerge: false
    }
  },
  methods: {
    /**
     * 参数：url, params, filename, rdTitle, docName,hasCatalogue
     */
    show (datas) {
      const localDocExportParams = getStore(this.docExportParamsKey)
      if (localDocExportParams) {
        this.exportParams = Object.assign(this.exportParams, localDocExportParams)
      }
      this.datas = datas
      this.hasCover = datas.hasCover
      this.hasBudget = datas.hasBudget
      this.hasMerge = datas.hasMerge
      this.hasUtility = datas.hasUtility
      this.title = `导出${datas.rdTitle}${datas.docName ? '-' + datas.docName : '研发过程文档'}`
      this.visible = true
    },
    handleSubmit () {
      this.confirmLoading = true
      const params = this.datas.params
      params.importFooterName = this.exportParams.importFooterName
      params.pageNum = this.exportParams.pageNum
      params.header = this.exportParams.header
      if (this.hasCatalogue) {
        params.catalogue = this.exportParams.catalogue
      }
      if (this.hasCover) {
        params.cover = this.exportParams.cover
      }
      if (this.hasBudget) {
        params.budgetDetail = this.exportParams.budgetDetail
      }
      if (this.hasUtility) {
        params.utility = this.exportParams.utility
      }
      if (this.hasMerge) {
        params.merge = this.exportParams.merge
      }
      setStore(this.docExportParamsKey, this.exportParams)
      this.$exportData(
        this.datas.url, params, this.datas.filename, this.$message).then((res) => {
        this.confirmLoading = false
        this.closeModal()
      })
    },
    closeModal () {
      this.visible = false
      this.$emit('ok')
    }
  }
}
</script>

<style>

</style>
