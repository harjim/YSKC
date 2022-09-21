<!--
 * @Author: lzh
 * @Date: 2021-09-06 14:52:47
 * @LastEditors: lzh
 * @LastEditTime: 2021-09-08 11:04:50
 * @Description: 单个提案管理Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\Proposal.vue
-->
<template>
  <tab-layout>
    <template #up>
      <a-form :form="form" ref="form">
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ proposalInfo.title }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="提案类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ types[proposalInfo.type] }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ proposalInfo.year }}
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <template v-if="proposalInfo.filePath">
                <div v-for="(fpath,index) in proposalInfo.filePath.split(',')" :key="index">
                  <a @click="onDownloadFile(fpath)" title="点击下载">{{ fpath.substring(fpath.lastIndexOf('/') + 14) || '-' }}</a>
                  <a-icon
                    v-if="fpath"
                    style="cursor:pointer;margin-left:5px;margin-right:5px"
                    placement="top"
                    @click="() =>onPreview(fpath)"
                    title="预览"
                    type="eye"
                  />
                </div>
              </template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="6">
          <a-col :span="24">
            <a-form-item class="form-item" label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              {{ proposalInfo.remark }}
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <preview-modal ref="previewModal"/>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" :title="proposalInfo.title"></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import ystable from '@/components/Table/ystable'
import AuditLog from './AuditLog'
import TabLayout from './TabLayout.vue'
import PreviewModal from '@/components/PreviewModal'
const types = ['立项素材', '现场图片', '立项对接表', '体系文件提案', '专利提案', '费用规划', '其他提案']
export default {
  name: 'Proposal',
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
    },
    mode: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    this.logGetParams.proposalId = this.record.dataId
    this.handlerGetPatentInfo(this.record)
  },
  data () {
    return {
      types,
      spinningLog: false,
      form: this.$form.createForm(this),
      proposalInfo: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      logGetParams: {
        proposalId: undefined
      }
    }
  },
  methods: {
    /**
     * @description: 下载文档
     * @param {*} record 行记录
     * @param {*} type type: 1 交底书 2.上传资料
     * @return {*}
     */
    onDownloadFile (url, name) {
      this.$exportData('/patentBuying/download', { filePath: url }, name, this.$message)
    },
    getFileName (path) {
      const arr = path.split('/')
      return arr[arr.length - 1]
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
    handlerGetPatentInfo (record) {
      this.$http.get('/proposalManagement/getProposal', { params: { id: record.dataId } }).then(res => {
        let proposalInfo = {}
        if (res.success) {
          proposalInfo = res.data
        } else {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员！')
        }
        this.proposalInfo = proposalInfo
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
