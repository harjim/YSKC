<!--
 * @Author:
 * @Date: 2020-11-25 16:36:10
 * @LastEditTime: 2022-05-21 09:55:16
 * @LastEditors: zdf
 * @Description: 研发成果管理
 * @FilePath: \RS-VUE\src\views\project\Result.vue
-->
<template>
  <a-card>
    <div>
      <a-spin :spinning="spinning" tip="请稍后...">
        <a-alert
          closable
          style="margin-bottom:32px;"
          description="研发成果是组织开展研发活动获得的有价值的产品、技术、方法等结论性的文件、图纸、实物等，主要类型有：专利受理通知书、专利证书、科技鉴定报告、新产品检测报告、新技术鉴定报告、研发会议纪要、图片、签到表、样品单、化验单、实施前后对比图及分析、带有技术参数的图纸、高新技术产品、当地政府新产品鉴定等。"
          type="info"
          show-icon/>
        <a-form layout="inline" style="padding-left: 1em;">
          <a-form-item label="成果名称">
            <a-input v-model="paramData.achievementName" placeholder="请输入成果名称"></a-input>
          </a-form-item>
          <a-form-item label="项目">
            <a-select
              style="width:400px;"
              showSearch
              :allowClear="true"
              placeholder="请选择项目"
              v-model="paramData.projectId"
            >
              <a-select-option
                v-for="project in projectList"
                :key="project.id"
                :value="project.id"
              >{{ project.rdTitle }} - {{ project.pname }}</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" v-if="control.view" @click="search(true)">查询</a-button>
          </a-form-item>
        </a-form>
        <ystable
          rowId="id"
          ref="table"
          :expand-config="{lazy: true, loadMethod: loadMethod,visibleMethod: fileVisible}"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          show-header-overflow
          header-align="center"
          :toolbar="{zoom:true,custom:true,refresh:true}"
          :params="paramData"
          queryUrl="/achievement/getList"
          :checkbox-config="{ checkMethod: checCheckboxkMethod, showHeader: showHeaderChk}"
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          @completed="({data:{data}})=>completed(data)"
        >
          <template v-slot:buttons>
            <span>
              <a-button
                type="primary"
                v-if="control.add"
                @click="$refs.resultModal.add()"
                style="margin-right:10px;"
              >添加</a-button>
              <a-button
                type="primary"
                v-if="control.submit && isMsUser"
                @click="onBatchAudit()"
                :disabled="selectedRows.length <= 0"
                style="margin-right:10px;"
              >提交</a-button>
              <a-tooltip :title="isMsUser ? '只能删除没有成果文件或未提交审核的成果。' : '只能删除没有成果文件的成果'">
                <a-button
                  type="primary"
                  v-if="control.del"
                  @click="deleteAchievement"
                  :disabled="deleteIds.length <= 0"
                  style="margin-right:10px;"
                >删除</a-button>
              </a-tooltip>
            </span>
          </template>
          <template>
            <vxe-table-column type="checkbox" width="60" align="center" header-align="center"></vxe-table-column>
            <vxe-table-column
              title="成果名称"
              field="achievementName"
              remoteSort
              align="left"
              :width="180"
              header-align="center" />
            <vxe-table-column
              title="RD"
              field="rdTitle"
              remoteSort
              align="left"
              :width="130"
              header-align="center" />
            <vxe-table-column
              title="项目名称"
              field="pname"
              remoteSort
              align="left"
              :width="240"
              header-align="center"/>
            <vxe-table-column
              title="文件数"
              field="fileCnt"
              remoteSort
              align="right"
              type="expand"
              :width="120"
              header-align="center">
              <template v-slot="{ row }">
                <span>{{ row.fileCnt || '-' }}</span>
              </template>
              <template v-slot:content="{ row }">
                <a-table
                  rowKey="id"
                  size="small"
                  :dataSource="row.files"
                  :pagination="false"
                  style="background: white;"
                  :loading="loading"
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
                  <a-table-column title="排序" :width="100">
                    <template slot-scope="text,record,index">
                      <a-icon v-if="canEditOrder('up', row.files.length, index)" @click="handleSort('up', row.files, index, row)" title="向上移" type="arrow-up"/>
                      <span v-else>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                      <a-icon style="margin-left: 8px;" v-if="canEditOrder('down', row.files.length, index)" @click="handleSort('down', row.files, index, row)" title="向下移" type="arrow-down"/>
                      <span v-else>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    </template>
                  </a-table-column>
                  <a-table-column title="文件名" data-index="fileName" align="left" :width="300">
                    <template slot-scope="text,record">
                      <span v-if="control.download">
                        <a @click="downloadFile(record)">{{ text }}</a>
                      </span>
                      <span v-else>
                        {{ text }}
                      </span>
                      <span v-if="control.preview">
                        <a-tooltip style="cursor:pointer" placement="top" @click="preview(record)">
                          <template slot="title">
                            <span>预览</span>
                          </template>
                          <a-icon type="eye" style="margin-left:5px" />
                        </a-tooltip>
                      </span>
                    </template>
                  </a-table-column>
                  <a-table-column title="上传时间" data-index="lastUpdateTime" align="center" :width="130"/>
                  <a-table-column title="操作" align="center" :width="80" v-if="control.del && ((isMsUser && isEditStatus(row.status)) || !isMsUser)">
                    <template slot-scope="text,record,index">
                      <a @click="$refs.fileModal.edit(row, record)">编辑</a>
                      <a-divider type="vertical" />
                      <a-popconfirm title="是否确定删除?" @confirm="deleteFile(record,index,row)">
                        <a>删除</a>
                      </a-popconfirm>
                    </template>
                  </a-table-column>
                </a-table>
              </template>
            </vxe-table-column>
            <vxe-table-column
              v-if="isMsUser"
              title="状态"
              field="status"
              align="center"
              header-align="center"
              :width="150">
              <template v-slot="{ row }">
                {{ statusMap[row.status] }}
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="成果类型"
              field="type"
              remoteSort
              align="left"
              :width="100">
              <template v-slot="{row}">{{ types[row.type] }}</template>
            </vxe-table-column>
            <vxe-table-column
              title="成果来源"
              field="source"
              remoteSort
              align="left"
              :width="100">
              <template v-slot="{row}">{{ sources[row.source] }}</template>
            </vxe-table-column>
            <vxe-table-column
              title="转化形式"
              field="converType"
              remoteSort
              align="left"
              :width="120">
              <template v-slot="{row}">{{ converTypes[row.converType] }}</template>
            </vxe-table-column>
            <vxe-table-column
              title="最后上传时间"
              field="lastUploadTime"
              remoteSort
              align="center"
              :width="180"
              header-align="center">
            </vxe-table-column>
            <vxe-table-column title="成果说明" field="description" :width="230"/>
            <vxe-table-column title="操作" align="left" :width="120">
              <template slot-scope="{row}">
                <!-- <span v-if="$auth('project:result:del')">
                  <a-popconfirm title="是否确定删除?" @confirm="delResultFile(row)">
                    <a :disabled="!isEditStatus(row.status)">删除</a>
                  </a-popconfirm>
                </span>
                <span v-else>
                  <a disabled>删除</a>
                </span> -->
                <a @click="$refs.resultModal.edit(row)" v-if="control.edit">编辑</a>
                <template v-if="control.upload && ((isMsUser && isEditStatus(row.status)) || !isMsUser)">
                  <a-divider type="vertical" v-if="control.edit" />
                  <a @click="$refs.fileModal.show(row)">上传</a>
                </template>
              </template>
            </vxe-table-column>
          </template>
        </ystable>
      </a-spin>
    </div>
    <achievement-file-modal ref="fileModal" :converResults="converResults" @update="(row) => { $refs.table.expandConfig.loadMethod({ row }) }"/>
    <result-modal ref="resultModal" @ok="search" :types="types" :sources="sources" :converTypes="converTypes" />
  </a-card>
</template>

<script>
import { SelectProject } from '@/components'
import moment from 'moment'
import ystable from '@/components/Table/ystable'
import yearMiXin from '@/utils/yearMixin'
import ResultModal from './modules/ResultModal'
import AchievementFileModal from './modules/AchievementFileModal.vue'
import { isEditStatus, statusMap, isMsUser } from '@/utils/processDoc/auditStatus'
export default {
  mixins: [yearMiXin],
  name: 'PurchaseOrder',
  components: {
    ystable,
    SelectProject,
    ResultModal,
    AchievementFileModal
  },
  data () {
    return {
      control: {
        add: this.$auth('project:result:add'),
        submit: this.$auth('project:result:submit'),
        view: this.$auth('project:result:view'),
        edit: this.$auth('project:result:edit'),
        del: this.$auth('project:result:del'),
        upload: this.$auth('project:result:upload'),
        download: this.$auth('project:result:download'),
        preview: this.$auth('project:result:preview')
      },
      types: { 0: '专利', 1: '版权', 2: '集成电路布图设计', 3: '其他' },
      sources: { 0: '自主研发', 1: '受让、受赠、并购', 2: '其他' },
      converTypes: { 0: '自行投资转化', 1: '向他人转让该技术成果', 2: '许可他人使用该科技成果', 3: '以该科技成果作为合作条件，与他人共同实施转化', 4: '以该科技成果作价投资，折算股份或者出资比例' },
      converResults: { 0: '新产品', 1: '新设备', 2: '新技术服务', 3: '样品/样机', 4: '新服务' },
      deleteIds: [],
      month: '',
      spinning: false,
      projectId: 0,
      paramData: {},
      beginDate: '',
      endDate: '',
      monthDay: {},
      minNum: 0,
      step: 1,
      precision: 0,
      typeList: [],
      stageMap: {
      },
      stageLists: [],
      projectList: [],
      selectedRows: [],
      statusMap,
      showHeaderChk: true,
      loading: false
    }
  },
  created () {
    this.getTypes()
    this.paramData.year = this.currentYear
    this.loadProject()
    this.$getDictionary(6).then(res => {
      this.stageLists = res
      for (const stage of res) {
        this.stageMap[stage.key] = stage.value
      }
    })
  },
  computed: {
    isMsUser () {
      return isMsUser()
    }
  },
  watch: {
    currentYear () {
      this.loadProject()
    }
  },
  methods: {
    moment,
    isEditStatus,
    fileVisible ({ row }) {
      return Boolean(row.fileCnt)
    },
    loadMethod ({ row }) {
      row.files = []
      return new Promise(resolve => {
        this.loading = true
        this.$http.get('/achievement/getFiles', { params: { achievementId: row.id } }).then(res => {
          if (res.success) {
            if (res.data) {
              row.files = res.data
            } else {
              this.$message.error('获取成果文件失败')
            }
          }
          resolve()
        }).finally(() => {
          this.loading = false
        })
      })
    },
    loadProject () {
      if (!this.paramData.year) {
        return
      }
      return this.$http.get('/project/getSelectList', { params: { year: this.paramData.year, sign: 0 } })
        .then(res => {
          if (res.data && res.success) {
            this.projectList = res.data
          }
        })
    },
    search (refresh) {
      this.paramData.year = this.currentYear
      if (this.$refs.table) {
        this.$refs.table.refresh(refresh)
      }
    },
    downloadFile (row) {
      this.$exportData('/beian/download', { filePath: row.filepath }, row.fileName, this.$message)
    },
    preview (row) {
      this.$preview({
        filePath: row.filepath,
        docName: row.fileName || '',
        visible: true
      })
    },
    getTypes () {
      return this.$http.get('/sysDocument/getFileType')
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.typeList = res.data
            return this.typeList
          }
        })
    },
    deleteAchievement () {
      const self = this
      this.$confirm({
        title: '您确定删除选中的成果吗?',
        content: this.isMsUser ? '只能删除没有成果文件或未提交审核的成果。' : '只能删除没有成果文件的成果。',
        onOk () {
          self.exeDeleteAchievement()
        },
        onCancel () {
        }
      })
    },
    exeDeleteAchievement () {
      this.spinning = true
      this.$http.post('/achievement/del', { ids: this.deleteIds }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
        this.spinning = false
      })
    },
    deleteFile (record, index, row) {
      this.spinning = true
      this.$http.post('/achievement/deleteFile', { id: record.id, achievementId: record.achievementId }).then(res => {
        if (res.success && res.data) {
          row.files.splice(index, 1)
          if (row.files.length) {
            row.lastUploadTime = row.files[row.files.length - 1].lastUpdateTime
            row.fileCnt = row.files.length
          } else {
            this.search(false)
          }
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
        this.spinning = false
      })
    },
    checCheckboxkMethod ({ row }) {
      return (this.isMsUser && isEditStatus(row.status)) || (!this.isMsUser && !row.fileCnt)
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRows = records
      this.deleteIds = records.filter(a => !a.fileCnt).map(a => a.id)
    },
    completed (data) {
      this.deleteIds = []
      this.selectedRows = []
      if (data && data.length) {
        this.showHeaderChk = data.some(item => { return this.checCheckboxkMethod({ row: item }) })
      } else {
        this.showHeaderChk = false
      }
    },
    onBatchAudit () {
      const self = this
      this.$confirm({
        title: '是否确定提交?',
        onOk: () => {
          self.handleAudit(self.selectedRows)
        }
      })
    },
    handleAudit (params) {
      this.spinning = true
      const requestParams = {
        moduleId: 12,
        achievementId: params.map(value => value.id)
      }
      this.$http.post('/projectAudit/submitAchievement', requestParams).then(data => {
        if (data) {
          this.search()
          this.$message.success('操作成功！')
        }
      }).catch(error => {
        this.message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.spinning = false
      })
    },
    canEditOrder (type, length, index) {
      if (type === 'up') {
        if (index <= 0) return false
      } else if (type === 'down') {
        if (index >= length - 1) return false
      }
      return true
    },
    handleSort (type, list, index, row) {
      const formIndex = index
      let toIndex
      if (type === 'up') {
        toIndex = index - 1
      } else if (type === 'down') {
        toIndex = index + 1
      }
      const param = {
        id1: list[formIndex].id,
        id2: list[toIndex].id
      }
      this.loading = true
      this.$http.post('/achievement/updateSeq', param).then(res => {
        if (res.success && res.data) {
          this.$message.success('操作成功')
          this.$refs.table.expandConfig.loadMethod({ row })
        } else {
          this.$message.error(res.errorMessage || '操作失败')
        }
      }).catch()
    }
  }

}
</script>
<style lang="less" scoped>
</style>
