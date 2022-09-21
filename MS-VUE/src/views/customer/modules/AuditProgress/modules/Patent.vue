<!--
 * @Author: ldx
 * @Date: 2021-01-22 14:22:01
 * @LastEditTime: 2022-05-21 10:57:49
 * @LastEditors: zdf
 * @Description: 专利审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\Patent.vue
-->
<template>
  <tab-layout>
    <template #up>
      <a-form :form="form" ref="form">
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ patentPlanInfo.rdTitle }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="申请名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ patentPlanInfo.patentName }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="交底书" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <span v-for="(file,index) in patentPlanInfo.fileModels" :key="index">
                <a @click="onDownloadFile(file.filePath,file.fileName)" title="点击下载交底书">{{ file.fileName || '-' }}</a>
                <a-icon
                  v-if="file.filePath"
                  style="cursor:pointer;margin-left:5px"
                  placement="top"
                  @click="() =>onPreview(file.filePath,file.fileName)"
                  title="预览"
                  type="eye"
                />
              </span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="发明人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ patentPlanInfo.firstInventor }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="身份证或信息代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ patentPlanInfo.code }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="其他发明人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ patentPlanInfo.otherInventor }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ patentPlanInfo.linkMan }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="联系方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ patentPlanInfo.linkTel }}
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <preview-modal ref="previewModal"/>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" :title="patentPlanInfo.patentName"></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import AuditLog from './AuditLog'
import TabLayout from './TabLayout.vue'
import { statusMap } from '@/utils/processDoc/auditStatus'
import PreviewModal from '@/components/PreviewModal'
import BatchAuditPatentModal from '@/views/patent/modules/BatchAuditPatentModal'
import { getPatentInfo } from '@/api/patent/patent'
export default {
  name: 'Patent',
  components: {
    ystable,
    AuditLog,
    PreviewModal,
    TabLayout,
    BatchAuditPatentModal
  },
  props: {
    record: {
      type: Object,
      default: () => { return {} }
    },
    mode: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    this.logGetParams.patentPlanId = this.record.patentPlanId
    this.handlerGetPatentInfo(this.record.patentPlanId)
  },
  data () {
    return {
      statusMap,
      spinningLog: false,
      form: this.$form.createForm(this),
      patentPlanInfo: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      logGetParams: {
        patentPlanId: undefined
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
    handlerGetPatentInfo (patentPlanId) {
      getPatentInfo({ patentPlanId }).then(data => {
        this.patentPlanInfo = data
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
