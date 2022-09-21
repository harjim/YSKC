<!--
 * @Author: ldx
 * @Date: 2021-02-24 11:36:53
 * @LastEditTime: 2022-04-11 16:04:45
 * @LastEditors: lzh
 * @Description: 调整项目里面的文件阶段
 * @FilePath: \RS-VUE\src\views\project\modules\ProcessDoc\modules\AdjustFileStageModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="400"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :getContainer="getContainer"
    destroyOnClose>
    <a-form :form="form" >
      <p><b>将选中的文档调整至:</b></p>
      <a-row :gutter="[8,8]">
        <a-col :span="21">
          <a-form-item label="阶段" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              style="width:100%;"
              :options="stages"
              placeholder="请选择调整到的阶段"
              v-decorator="['stage',{ rules:[{ required: true, message: '请选择调整到的阶段' }] } ]"
              @change="getDocumentListByStage"
            ></a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="[8,8]">
        <a-col :span="21">
          <a-form-item label="文档" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              style="width:100%;"
              :options="documentList"
              placeholder="请选择文档"
              v-decorator="['targetPDocFileId',{ rules:[{ required: false, message: '请选择文档' }] } ]"
            ></a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="[8,8]">
        <a-col :span="21">
          <a-form-item label="位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group v-decorator="['after',{ initialValue: true, rules:[{ required: true, message: '请选择位置' }] } ]">
              <a-radio :value="false">之前</a-radio>
              <a-radio :value="true">之后</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
import { adjustFileStage } from '@/api/doc/index'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
export default {
  name: 'AdjustfileStageModal',
  props: {
    stageList: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      domName,
      isVisible: false,
      title: '',
      stages: [],
      labelCol: {
        sm: { span: 4 }
      },
      wrapperCol: {
        sm: { span: 20 }
      },
      form: this.$form.createForm(this),
      pdocFileIds: [],
      projectId: undefined,
      documentList: [],
      confirmLoading: false
    }
  },
  methods: {
    show (title, projectId, pdocFileIds, stages) {
      this.isVisible = !this.isVisible
      this.title = title
      this.stages = stages
      this.projectId = projectId
      this.form.resetFields()
      this.pdocFileIds = pdocFileIds
    },
    afterClose () {
      this.pdocFileIds = []
      this.documentList = []
      this.stages = []
      this.projectId = undefined
      // this.$emit('OnModalClose')
    },
    handleSubmit () {
      this.confirmLoading = true
      this.form.validateFields((error, values) => {
        if (!error) {
          values['pdocFileIds'] = this.pdocFileIds
          values['projectId'] = this.projectId
          adjustFileStage(values).then(response => {
            if (response.success) {
              this.$message.success('操作成功！')
              this.$emit('refresh')
              this.isVisible = !this.isVisible
            } else {
              this.$message.error(response.errorMessage || '操作失败')
            }
          }).catch((error) => {
            this.$message.error('操作失败')
            console.log('handleSubmit function error' + error.message)
            console.log('接口：/projectDocFileData/changeDocStage:' + error.message)
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      })
    },
    getDocumentListByStage (value) {
      this.form.setFieldsValue({ targetPDocFileId: undefined })
      const result = this.stageList.find(item => {
        return item.stageKey === value
      })
      // 文档列表筛选已选中的文档
      this.documentList = result.projectDocList.filter(item => {
        return !this.pdocFileIds.includes(item.id)
      }).map(item => {
        return {
          value: item.id,
          label: item.docFileName
        }
      })
      // 文档默认选中最后一项
      if (this.documentList.length > 0) {
        this.form.setFieldsValue({ targetPDocFileId: this.documentList[this.documentList.length - 1].value })
      }
    },
    getContainer () {
      return popupContainer(this.domName)
    }
  }
}
</script>
<style lang='less' scoped>
</style>
