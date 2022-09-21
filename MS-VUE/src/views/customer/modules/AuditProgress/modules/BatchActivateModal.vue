<!--
 * @Description: 批量激活modal
-->
<template>
  <a-modal
    :zIndex="1001"
    :title="title"
    :width="650"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    style="top: 0px;"
    :destroyOnClose="true"
    :getContainer="popupContainer"
    :bodyStyle="{ height: '65vh', maxHeight: '65vh', overflow: 'auto' }"
    @cancel="isVisible = false"
  >
    <template #footer>
      <a-button type="default" @click="isVisible = false">取消</a-button>
      <a-button type="primary" @click="handleSubmit" :disabled="!btnDisabled">确定</a-button>
    </template>
    <div id="tableBox" style="height:calc(100% - 200px);">
      <vxe-grid
        :data="tableData"
        height="auto"
        size="small"
        ref="table"
        highlight-current-row
        highlight-hover-row
        show-overflow="title"
        resizable
        auto-resize
        :columns="columns"
      >
        <template #stageSlot="{row}">
          {{ stageMap[row.currentObj.stage] }}
        </template>
        <template #docSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.currentObj.id}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
        <template #backupDataSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.docFileId}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
        <template #noveltySearchSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.docFileIds}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
        <template #projectSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.id}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
        <template #patentSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.id}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
        <template #proposalSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.id}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
        <template #achievementSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.id}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
        <template #reportSlot="{row}">
          <a-checkbox
            :checked="row.checked"
            :id="`${row.docFileId}`"
            @change="(e) => checkboxChange(e,row)">
          </a-checkbox>
        </template>
        <template #projectName="{row}">
          {{ row.rdTitle }}-{{ row.pname }}
        </template>
      </vxe-grid>
    </div>
    <a-form :form="form">
      <a-form-item label="激活说明" :extra="onComputeTextarea('suggestion',200)" style="margin-bottom: 0px;">
        <a-textarea
          v-decorator="['suggestion', {rules:[{required: true, whitespace:true,min: 5, message: '请输入5个字以上激活说明'}]}]"
          placeholder="请输入激活说明"
          :rows="4"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { isEditStatus, statusMap } from '@/utils/processDoc/auditStatus'
import { popupContainer } from './js/screenFullMountDom'
import { cloneDeep } from 'lodash'
const stageMap = {
  '100': '规划阶段',
  '200': '研究阶段',
  '300': '设计开发',
  '400': '设计验证',
  '500': '实验验证',
  '600': '试验证',
  '700': '试制',
  '800': '项目验收',
  '900': '项目收尾'
}
const columnObj = {
  docTab: [
    { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
    { title: '阶段', headerAlign: 'center', align: 'left', width: '100', slots: { default: 'stageSlot' } },
    { title: '文档名称', field: 'currentObj.docFileName', headerAlign: 'center', align: 'left' },
    { title: '是否激活', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'docSlot' } }
  ],
  noveltySearchTab: [
    { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
    { title: '项目名称', headerAlign: 'center', align: 'left', slots: { default: 'projectName' } },
    { title: '是否激活', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'noveltySearchSlot' } }
  ],
  backupTab: [
    { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
    { title: '文档名称', field: 'docName', headerAlign: 'center', align: 'left' },
    { title: '是否激活', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'backupDataSlot' } }
  ],
  projectTab: [
    { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
    { title: '项目名称', field: 'pname', headerAlign: 'center', align: 'left' },
    { title: '是否激活', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'projectSlot' } }
  ],
  patentTab: [
    { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
    { title: '项目名称', field: 'rdTitle', headerAlign: 'center', align: 'left' },
    { title: '申请名称', field: 'patentName', headerAlign: 'center', align: 'left' },
    { title: '是否激活', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'patentSlot' } }
  ],
  proposalTab: [
    { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
    { title: '项目名称', field: 'title', headerAlign: 'center', align: 'left' },
    { title: '是否激活', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'proposalSlot' } }
  ],
  achievementTab: [
    { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
    { title: '项目名称', field: 'pname', headerAlign: 'center', align: 'left' },
    { title: '是否激活', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'achievementSlot' } }
  ],
  reportTab: [
    { title: '序号', type: 'seq', headerAlign: 'center', align: 'center', width: '60' },
    { title: '项目名称', field: 'pname', headerAlign: 'center', align: 'left' },
    { title: '是否激活', field: 'checked', headerAlign: 'center', align: 'center', width: '80', slots: { default: 'reportSlot' } }
  ]
}
export default {
  name: 'BatchAuditModal',
  data () {
    return {
      columnObj,
      columns: columnObj['docTab'],
      statusMap,
      stageMap,
      confirmLoading: false,
      form: this.$form.createForm(this),
      isVisible: false,
      title: '',
      tableData: [],
      docFileIds: [],
      projectIds: [],
      btnDisabled: false,
      spinning: false,
      projectId: undefined,
      commitType: 1 // 提交类型 1： 文档提交 2：查新报告 3:留存备查资料 4:项目名称 5:专利管理 6:提案管理 7:成果管理 8:立项报告
    }
  },
  methods: {
    isEditStatus,
    popupContainer,
    getPopupContainer () {
      return popupContainer('tableBox')
    },
    docAutidShow (title, checkedFiles, projectId) {
      this.docFileIds = []
      this.projectId = projectId
      this.title = title
      this.columns = columnObj['docTab']
      this.form.resetFields()
      this.commitType = 1
      this.tableData = cloneDeep(checkedFiles)
      this.tableData.forEach((item) => {
        if (item.checked) {
          this.docFileIds.push(item.currentObj.id)
        }
      })
      this.btnDisabled = this.docFileIds.length
      this.isVisible = true
    },
    noveltSearchShow (title, checkedProjects, projectId) {
      this.projectIds = []
      this.projectId = projectId
      this.title = title
      this.columns = columnObj['noveltySearchTab']
      this.form.resetFields()
      this.commitType = 2
      this.tableData = cloneDeep(checkedProjects)
      this.tableData.forEach((item) => {
        if (item.checked) {
          this.projectIds.push(item.projectId)
        }
      })
      this.btnDisabled = this.projectIds.length
      this.isVisible = true
    },
    backupDataShow (title, chooseFiles, projectId) {
      this.docFileIds = []
      this.projectId = projectId
      this.title = title
      this.columns = columnObj['backupTab']
      this.form.resetFields()
      this.commitType = 3
      this.tableData = cloneDeep(chooseFiles)
      this.tableData.forEach((item) => {
        if (item.checked) {
          this.docFileIds.push(item.pdocFileId)
        }
      })
      this.btnDisabled = this.docFileIds.length
      this.isVisible = true
    },
    projectAuditShow (title = '批量激活立项项目', chooseFiles) {
      this.commitType = 4
      this.title = title
      this.columns = columnObj['projectTab']
      this.form.resetFields()
      chooseFiles.forEach(item => {
        this.$set(item, 'checked', true)
        this.projectIds.push(item.id)
      })
      this.tableData = cloneDeep(chooseFiles)
      this.btnDisabled = this.projectIds.length
      this.isVisible = true
    },
    projectPatentShow (title = '批量激活专利', chooseFiles) {
      this.commitType = 5
      this.title = title
      this.columns = columnObj['patentTab']
      this.form.resetFields()
      chooseFiles.forEach(item => {
        this.$set(item, 'checked', true)
        this.projectIds.push(item.id)
      })
      this.tableData = cloneDeep(chooseFiles)
      this.btnDisabled = this.projectIds.length
      this.isVisible = true
    },
    projectProposalShow (title = '批量激活提案', chooseFiles) {
      this.commitType = 6
      this.title = title
      this.columns = columnObj['proposalTab']
      this.form.resetFields()
      chooseFiles.forEach(item => {
        this.$set(item, 'checked', true)
        this.projectIds.push(item.id)
      })
      this.tableData = cloneDeep(chooseFiles)
      this.btnDisabled = this.projectIds.length
      this.isVisible = true
    },
    projectAchievementShow (title = '批量激活成果', chooseFiles) {
      this.commitType = 7
      this.title = title
      this.columns = columnObj['achievementTab']
      this.form.resetFields()
      chooseFiles.forEach(item => {
        this.$set(item, 'checked', true)
        this.projectIds.push(item.id)
      })
      this.tableData = cloneDeep(chooseFiles)
      this.btnDisabled = this.projectIds.length
      this.isVisible = true
    },
    reportShow (title, chooseFiles) {
      this.docFileIds = []
      this.title = title
      this.columns = columnObj['reportTab']
      this.form.resetFields()
      this.commitType = 8
      this.tableData = cloneDeep(chooseFiles)
      this.tableData.forEach((item) => {
        if (item.checked) {
          this.docFileIds.push({
            docFileId: item.docFileId,
            projectId: item.projectId
          })
        }
      })
      this.btnDisabled = this.docFileIds.length
      this.isVisible = true
    },
    afterClose () {
      this.docFileIds = []
      this.tableData = []
      this.projectIds = []
      this.projectId = undefined
      this.btnDisabled = false
    },
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          let paramObj = {}
          if (this.commitType === 1 || this.commitType === 3) {
            paramObj = {
              suggestion: values.suggestion,
              docFileIds: this.docFileIds,
              projectId: this.projectId
            }
          }
          if (this.commitType === 2) {
            paramObj = {
              suggestion: values.suggestion,
              projectIds: this.projectIds
            }
          }
          if (this.commitType === 4) {
            paramObj = {
              suggestion: values.suggestion,
              projectIds: this.projectIds,
              moduleId: 4
            }
          }
          if (this.commitType === 5) {
            paramObj = {
              suggestion: values.suggestion,
              patentIds: this.projectIds
            }
          }
          if (this.commitType === 6) {
            paramObj = {
              suggestion: values.suggestion,
              proposalIds: this.projectIds
            }
          }
          if (this.commitType === 7) {
            paramObj = {
              suggestion: values.suggestion,
              achievementIds: this.projectIds
            }
          }
          if (this.commitType === 8) {
            paramObj = {
              suggestion: values.suggestion,
              projectDocFiles: this.docFileIds
            }
          }
          this.$http.post('/projectProgress/activateFlows', paramObj).then((res) => {
            if (res.data && res.success) {
              this.$message.success('操作成功')
              this.$emit('refresh')
              this.isVisible = false
            } else {
              this.$message.error(res.errorMessage)
            }
          }).catch((error) => {
            this.$message.error(error.message)
          }).finally(() => {
          })
        }
      })
    },
    checkboxChange (e, row) {
      row.checked = e.target.checked
      this.$nextTick(() => {
        this.docFileIds = []
        this.projectIds = []
        this.tableData.forEach((item) => {
          if (item.checked) {
            if (this.commitType === 1) {
              this.docFileIds.push(item.currentObj.id || item.docFileId)
            } else if (this.commitType === 2 || this.commitType === 4) {
              this.projectIds.push(item.projectId || item.id)
            } else if (this.commitType === 3 || this.commitType === 8) {
              this.docFileIds.push(item.docFileId)
            } else if (this.commitType === 5 || this.commitType === 6 || this.commitType === 7) {
              this.projectIds.push(item.id)
            }
          }
        })
        if ([1, 3, 8].includes(this.commitType)) {
          this.btnDisabled = this.docFileIds.length
        }
        if ([2, 4, 5, 6, 7].includes(this.commitType)) {
          this.btnDisabled = this.projectIds.length
        }
      })
    },
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
    }
  }
}
</script>
<style lang='less' scoped>
#tableBox /deep/ .vxe-table .c--title {
  width: 100% !important;
}
.btn-space {
  margin-right: 12px;
}
.hover(@color:#fff) {
  color: #fff;
  box-shadow: #fff;
  border: 1px solid #fff;
  background-color: @color;
}
</style>
