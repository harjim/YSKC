<!--
 * @Author: zdf
 * @Date: 2021-11-20 08:45:06
 * @LastEditors: zdf
 * @LastEditTime: 2022-02-25 10:23:23
 * @Description: 单个成果审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\Achievement.vue
-->
<template>
  <tab-layout>
    <template #up>
      <a-form :form="form" ref="form">
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="成果名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ resultInfo.info.achievementName }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="RD" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ resultInfo.info.rdTitle }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ resultInfo.info.pname }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="文件数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ resultInfo.info.fileCnt }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="最后上传时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ resultInfo.info.lastUploadTime }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-table
            rowKey="id"
            :dataSource="resultInfo.files"
            :pagination="false"
            style="background: white;"
          >
            <a-table-column title="阶段" data-index="stageKey" align="center" :width="100">
              <template slot-scope="text">
                {{ stageMap[text] }}
              </template>
            </a-table-column>
            <a-table-column title="转化结果" data-index="converResult" align="left" :width="100">
              <template slot-scope="text">
                {{ converResults[text] }}
              </template>
            </a-table-column>
            <a-table-column title="证明材料" data-index="docName" align="left" :width="100"/>
            <a-table-column title="文件名" data-index="fileName" align="left" :width="300">
              <template slot-scope="t,child">
                <span>
                  <a @click="onDownloadFile(child.filepath,child.fileName)">{{ t }}</a>
                </span>
                <span>
                  <a-tooltip style="cursor:pointer" placement="top" @click="onPreview(child.filepath,child.fileName)">
                    <template slot="title">
                      <span>预览</span>
                    </template>
                    <a-icon type="eye" style="margin-left:5px" />
                  </a-tooltip>
                </span>
              </template>
            </a-table-column>
            <a-table-column title="上传时间" data-index="lastUpdateTime" align="center" :width="130"/>
          </a-table>
        </a-row>
      </a-form>
      <preview-modal ref="previewModal"/>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" :title="resultInfo.info.achievementName"></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import AuditLog from './AuditLog'
import TabLayout from './TabLayout.vue'
import PreviewModal from '@/components/PreviewModal'
export default {
  name: 'Achievement',
  components: {
    ystable,
    AuditLog,
    PreviewModal,
    TabLayout
  },
  props: {
    record: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    this.$getDictionary(6).then(res => {
      this.stageLists = res
      for (const stage of res) {
        this.stageMap[stage.key] = stage.value
      }
    })
    this.logGetParams.documentId = this.record.dataId
    this.handlerGetPatentInfo(this.record.dataId)
  },
  data () {
    return {
      converResults: { 0: '新产品', 1: '新设备', 2: '新技术服务', 3: '样品/样机', 4: '新服务' },
      stageMap: {},
      spinningLog: false,
      form: this.$form.createForm(this),
      resultInfo: { info: {}, files: [] },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      logGetParams: {
        documentId: undefined
      }
    }
  },
  methods: {
    moment,
    getPatentFileName (path) {
      if (!path) { return '' }
      const nameIndex = path.lastIndexOf('/')
      const name = path.substring(nameIndex).substring(14)
      return name
    },
    /**
     * @description: 下载文档
     * @param {*} record 行记录
     * @param {*} type type: 1 交底书 2.上传资料
     * @return {*}
     */
    onDownloadFile (url, name) {
      this.$exportData('/patentBuying/download', { filePath: url }, name, this.$message)
    },
    onPreview (filePath, filename) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$nextTick(() => {
        this.$refs.previewModal.show(filePath, filename)
      })
    },
    updataStatus () {

    },
    handlerGetPatentInfo (dataId) {
      this.$http.get('/rsAchievement/getInfo', { params: { id: dataId } }).then(res => {
        let resultInfo = { info: {}, files: {} }
        if (res.success) {
          resultInfo = res.data
        } else {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员！')
        }
        this.resultInfo = resultInfo
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
    }
  }
}
</script>

<style lang="less" scoped>
@import '../css/AuditDrawerTab.less';
* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis>.vxe-body--expanded-cell {
  overflow: auto;
}
.form-item {
  margin-bottom: 8px;
}
</style>
