<!--
 * @Author: ldx
 * @Date: 2021-06-09 09:07:32
 * @LastEditTime: 2021-07-05 11:07:54
 * @LastEditors: ldx
 * @Description: 专利申请资料
 * @FilePath: \RS-VUE\src\views\project\modules\ApplyDataDrawer.vue
-->
<template>
  <a-drawer
    :title="title"
    width="800"
    placement="right"
    :closable="true"
    :visible="isVisible"
    :bodyStyle="{ height: 'calc(100% - 55px)', padding: '12px' }"
    @close="afterClose"
    :zIndex="999"
  >
    <a-spin style="height: 100%" id="spin_wrap" :spinning="spinning" tip="加载中...">
      <!-- <ysDivider title="申请资料意见反馈" bottom="12px"></ysDivider>
      <ysDivider title="文件" :level="1" bottom="10px" :fontSize="15">文件</ysDivider> -->
      <div class="title">文件</div>
      <div class="file_wrap">
        <a-empty v-if="!files.length" />
        <ol v-else>
          <li class="file_item" v-for="(file,index) in files" :key="index">
            <a title="点击下载" @click="onDownload(file.path,2)">{{ file.name }}</a>
            <a-tooltip style="cursor:pointer" placement="top" @click="onPreview(file.path,file.name)">
              <template slot="title">
                <span>预览</span>
              </template>
              <a-icon title="预览" type="eye" style="margin-left:5px" />
            </a-tooltip>
          </li>
        </ol>
      </div>
      <!-- <ysDivider title="建议" :level="1" bottom="10px" :fontSize="15">建议</ysDivider> -->
      <div class="title">建议</div>
      <a-form :form="form">
        <a-form-item :extra="onComputeTextarea('opinion',500)" style="margin-bottom: 0px;">
          <a-textarea
            :disabled="isDisabled"
            :autosize="{ minRows: 4, maxRows: 4 }"
            v-decorator="['opinion', {rules:[{required: true, whitespace:true, min: 5, message: '请输入5个字以上审批建议'}]}]"
            placeholder="请输入建议"
            :rows="4"
          />
        </a-form-item>
      </a-form>
      <div class="btn-wrap">
        <a-button type="primary" @click="saveOpinion" :disabled="isDisabled" v-if="$auth('project:patentPlan:publish')">发表</a-button>
      </div>
      <!-- <ysDivider title="申请资料建议日志" bottom="12px">建议日志</ysDivider> -->
      <div class="title">建议日志</div>
      <div class="table_wrap">
        <vxe-table
          ref="table"
          border="full"
          height="100%"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          :data="tableData"
        >
          <vxe-table-column
            type="seq"
            title="序号"
            width="60"
            align="center"
            header-align="center"
          ></vxe-table-column>
          <!-- <vxe-table-column
            title="发布者"
            field="uploader"
            width="120"
            align="center"
            header-align="center"
          ></vxe-table-column> -->
          <vxe-table-column
            title="上传时间"
            field="createTime"
            width="150"
            align="center"
            header-align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="意见"
            field="opinion"
            minWidth="250"
            align="left"
            header-align="center"
          ></vxe-table-column>
          <!-- <vxe-table-column
            title="申请资料"
            field="filepath"
            align="center"
            header-align="center"
          >
            <template #default="{row}">
              <span style="margin-left: 8px;" v-for="(file,index) in getFiles(row.filepath)" :key="index">
                <a @click="onDownload">{{ file.name }}</a>
                <a-tooltip style="cursor:pointer" placement="top" @click="onPreview(file.path)">
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin-left:5px" />
                </a-tooltip>
              </span>
            </template>
          </vxe-table-column> -->
        </vxe-table>
      </div>
    </a-spin>
  </a-drawer>
</template>
<script>
import { saveOpinion, getPatentOpinions } from '@/api/patent/patent'
import ysDivider from '@/components/YsDivider'
export default {
  name: 'ApplyDataDrawer',
  components: { ysDivider },
  props: {
    openPreviewModal: {
      type: Function,
      required: true
    }
  },
  computed: {
    isDisabled () {
      if (this.files && this.files.length) { return false }
      return true
    }
  },
  data () {
    return {
      isVisible: false,
      title: '',
      form: this.$form.createForm(this),
      record: undefined,
      spinning: false,
      tableData: [],
      files: []
    }
  },
  methods: {
    show (record) {
      this.record = record
      this.isVisible = true
      this.spinning = false
      this.title = `${record.patentName} [${record.rdTitle}] - 申请文件`
      this.handlerGetPatentOpinions({ patentPlanId: record.id })
      // this.files = this.getFiles(record.filepath)
    },
    afterClose () {
      this.isVisible = false
      this.form.resetFields()
      this.title = undefined
      this.record = undefined
      this.spinning = false
      this.tableData = []
      this.files = []
    },
    handleSubmit () { },
    // 计算文本域的个数
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    },
    handlerGetPatentOpinions (params) {
      this.spinning = true
      getPatentOpinions(params).then(data => {
        if (data.length) {
          const firstData = data.splice(0, 1)[0]
          this.tableData = data
          this.files = this.getFiles(firstData.filepath)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.spinning = false
      })
    },
    saveOpinion () {
      this.form.validateFields((error, values) => {
        if (!error) {
          values['patentPlanId'] = this.record.id
          this.$confirm({
            title: '您确定要发布意见？',
            onOk: () => this.handlerSaveOpinion(values)
          })
        }
      })
    },
    handlerSaveOpinion (params) {
      this.spinning = true
      saveOpinion(params).then(data => {
        if (data) {
          this.handlerGetPatentOpinions({ patentPlanId: this.record.id })
          this.form.resetFields()
          this.$message.success('操作成功！')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.spinning = false
      })
    },
    getFiles (filePath) {
      if (!filePath) { return [] }
      const filePaths = filePath.split(',')
      const fileList = []
      filePaths.forEach(filePath => {
        const file = {
          name: filePath.substring(filePath.lastIndexOf('/') + 14),
          path: filePath
        }
        fileList.push(file)
      })
      return fileList
    },
    onDownload (path, type) {
      this.$exportData('/patentPlan/download', { id: this.record.id, path, type }, undefined, this.$message)
    },
    onPreview (path, name) {
      this.openPreviewModal(path, name)
      // if (path === '') {
      //   this.$message.info('请先上传文件')
      //   return
      // }
      // this.$refs.previewModal.show(path, name)
    }
  }
}
</script>
<style lang='less' scoped>
#spin_wrap{
& /deep/ .ant-spin-container {
    height: 100%;
    display: flex;
    flex-direction: column;
  }
}
.btn-wrap {
  margin: 8px 0;
  display: flex;
  // flex-direction: column;
  justify-content: center;
}
.table_wrap {
  flex-grow: 1;
}
.file_wrap {
  min-height: 100px;
  overflow: auto;
  .file_item {
    padding: 4px 3px;
  }
}
.title {
  font-size: 16px;
  font-weight: 650;
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
