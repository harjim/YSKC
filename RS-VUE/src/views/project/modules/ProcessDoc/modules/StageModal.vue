<!--
 * @Author: ldx
 * @Date: 2021-01-26 09:35:31
 * @LastEditTime: 2021-04-22 10:00:22
 * @LastEditors: ldx
 * @Description: 项目阶段-阶段设置-自定义名称
 * @FilePath: \RS-VUE\src\views\project\modules\ProcessDoc\modules\StageModal.vue
-->
<template>
  <div>
    <a-modal
      :title="title"
      :width="1000"
      :visible="isVisible"
      :afterClose="afterClose"
      :maskClosable="false"
      :getContainer="getContainer"
      :destroyOnClose="true"
      @cancel="isVisible = false"
      @ok="handleSubmit"
    >
      <div style="height:100%;">
        <stage-tab
          ref="stageTab"
          :loading="loading"
          :projectData="projectObj"
          @ok="onOk"
          @refresh="refresh"
          @showEdit="onShowEdit"
        />
      </div>
      <template #footer></template>
    </a-modal>
    <a-modal
      title="自定义名称"
      cancelText="应用"
      destroyOnClose
      :maskClosable="false"
      :visible="showEdit"
      @cancel="() => this.showEdit = false"
    >
      <template slot="footer">
        <a-button type="primary" @click="$refs.editStage.onEditStage(false)">应用</a-button>
        <a-button type="primary" :loading="loading" @click="$refs.editStage.onEditStage(true)">确认</a-button>
      </template>
      <edit-stage
        ref="editStage"
        :defaultStageList="defaultStageList"
        :companyStageList="companyStageList"
        :loading="loading"
        :projectObjId="projectObj.id"
        @loadingStatus="changeLoadingStatus"
        @showEditClose="showEditClose"
        @changeRefresh="changeRefresh"
      ></edit-stage>
    </a-modal>
  </div>
</template>
<script>
import StageTab from '../../projectTab/StageTab'
import EditStage from './EditStageModal.vue'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
export default {
  name: 'StageModal',
  components: {
    StageTab,
    EditStage
  },
  data () {
    return {
      domName,
      isVisible: false,
      showEdit: false,
      loading: false,
      isChange: false,
      title: '',
      projectObj: {},
      isRefresh: false,
      defaultStageList: [],
      companyStageList: []
    }
  },
  methods: {
    show (title, projectObj) {
      this.isRefresh = false
      this.isVisible = true
      this.title = title
      this.projectObj = projectObj
      if (this.$refs.StageTab && this.$refs.StageTab.resetData) {
        this.$refs.StageTab.resetData()
      }
    },
    getContainer () {
      return popupContainer(this.domName)
    },
    afterClose () {
      // this.$emit('refresh', this.isRefresh)
    },
    handleSubmit () {
    },
    closeModal () {
      this.isVisible = false
      this.title = ''
      this.projectObj = {}
    },
    refresh (isVisible, isRefresh) {
      this.isVisible = isVisible
      this.isRefresh = isRefresh
      this.$emit('refresh', isRefresh)
    },
    onOk (isVisible, isRefresh) {
      this.isVisible = false
      this.$emit('refresh')
    },
    onShowEdit () {
      this.$http.get('stage/getStage').then(({ data }) => {
        this.defaultStageList = [...data.defaultStageList]
        this.companyStageList = data.companyStageList.length ? [...data.companyStageList] : [...data.defaultStageList]
      })
      this.showEdit = true
    },
    showEditClose () {
      this.showEdit = false
    },
    changeLoadingStatus (status) {
      this.loading = status
    },
    changeRefresh () {
      this.$emit('onChangeRefresh')
      this.$refs.stageTab.change2Refresh()
    }
  }
}
</script>
<style lang='less' scoped>
</style>
