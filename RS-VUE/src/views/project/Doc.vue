<template>
  <a-card id="card" style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}">
    <div style="height: 100%" v-if="$auth('project:doc:preview')">
      <a-spin :spinning="spinning" tip="加载中..." style="height: 100%;">
        <a-form layout="inline">
          <a-form-item label="项目">
            <project-select
              :year="currentYear"
              :isDefault="true"
              :isJoinPrjectName="true"
              :sign="0"
              v-model="projectId"
              @getPrjectIds="OnProjectChange"
              width="600px"
            />
          </a-form-item>
          <template v-if="isNohaveProject">
            <a-form-item>
              <a-button
                type="primary"
                v-if="$auth('project:doc:export')"
                @click="exportFile"
                :disabled="selectedCount <= 0"
                style="margin-right: 10px;"
              >导出</a-button>
              <a-button
                type="primary"
                v-if="$auth('project:doc:preview')"
                @click="openPreviewModal"
                :disabled="docIds.length <= 0"
                style="margin-right: 10px;"
              >预览</a-button>
              <a-button type="primary" @click="refresh" :disabled="docIds.length <= 0">刷新</a-button>
            </a-form-item>
          </template>
          <a-alert
            v-if="isShowAlter"
            :message="msg"
            type="warning"
            show-icon
            style="width:600px;margin-left:40px"
          />
        </a-form>
        <div v-if="isNohaveProject" style="height: calc(100% - 39px); overflow: auto;">
          <process-doc
            :project="currentProject"
            @getDocIds="getDocIds"
            @OnSpinLoad="OnSpinLoad"
            @getSelectedCount="getSelectedCount"
            ref="processDoc"
            @onChangeRefresh="refresh"
          ></process-doc>
          <preview-modal ref="previewModal"></preview-modal>
        </div>
      </a-spin>
    </div>
  </a-card>
</template>
<script>
import { ProjectSelect } from '@/components'
import ProcessDoc from './modules/ProcessDoc/Index'
import PreviewModal from './modules/ProcessDoc/modules/PreviewModal'
import moment from 'moment'
import { mapActions, mapGetters } from 'vuex'
export default {
  components: {
    ProcessDoc,
    PreviewModal,
    ProjectSelect
  },
  computed: {
    ...mapGetters(['userInfo', 'currentYear'])
  },
  watch: {
    projectId: {
      // immediate: true,
      handler: function (value) {
        this.isNohaveProject = !!(value && value > 0)
        if (!this.isNohaveProject) {
          this.msg = `${this.currentYear}年：没有项目，请重新选择年份`
        }
      }
    }
  },
  data () {
    return {
      projectId: 0,
      isNohaveProject: false,
      isShowAlter: false,
      currentProject: {},
      msg: '',
      docIds: [],
      spinning: false,
      isShowFileLayout: true,
      selectedCount: undefined
    }
  },
  methods: {
    ...mapActions(['setProjectId', 'setSelectedProject']),
    getDocIds (docIds) {
      this.docIds = docIds
    },
    moment,
    OnProjectChange (value, project) {
      this.docIds = []
      this.currentProject = project
      this.isShowAlter = !(value && value > 0)
    },
    /**
     * @description: 导出过程文件
     */
    exportFile () {
      this.spinning = true
      if (this.$refs.processDoc && this.$refs.processDoc.exportAllFile) {
        this.$refs.processDoc.exportAllFile()
      }
    },
    /**
     * @description: 打开预览modal
     * @param {*}
     * @return {*}
     */
    openPreviewModal () {
      const ids = this.docIds
      const paramObj = {
        projectId: this.projectId,
        pDocFileIds: ids,
        currentYear: this.currentYear
      }
      this.$refs.previewModal.show(this.fileName, paramObj)
    },
    /**
     * @description: 子组件调用加载动画
     * @param {*} isLoad 是否加载
     */
    OnSpinLoad (isLoad) {
      this.spinning = isLoad
    },
    /**
     * @description: 刷新
     * @param {*}
     * @return {*}
     */
    refresh () {
      const { processDoc } = this.$refs
      if (processDoc && processDoc.showDoc) {
        processDoc.showDoc(undefined, false)
      }
    },
    getSelectedCount (count) {
      this.selectedCount = count
    }
  }
}
</script>
<style lang="less" scoped>
#card /deep/ .ant-card-body {
  padding-top: 12px;
  padding-left: 12px;
  padding-right: 12px;
  padding-bottom: 5px;
}

#card /deep/ .ant-spin-nested-loading {
  height: 100%;
}
#card /deep/ .ant-spin-container {
  height: 100%;
}
#triangle-up {
  width: 0;
  height: 0;
  border-left: 10px solid transparent;
  border-right: 10px solid transparent;
  border-bottom: 10px solid red;
}
#triangle-down {
  width: 0;
  height: 0;
  border-left: 10px solid transparent;
  border-right: 10px solid transparent;
  border-top: 10px solid red;
}
</style>
