
<template>
  <FormTableLayout>
    <template #form>
      <a-form :form="form" class="form" id="searchForm">
        <a-form-item label="客户名称"><a-input v-model="params.companyName" placeholder="请输入客户名称" allow-clear /></a-form-item>
        <a-form-item label="事项">
          <a-select placeholder="请选择事项" v-model="params.itemType" allow-clear>
            <a-select-option v-for="(value, key) in reasons" :key="key" :value="key">{{ value }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="创建人">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="请输入创建人"
            style="width: 240px;"
            v-model="query.creator"
            @change="v => {
              if (v) {
                params.creatorId = v.id;
              } else {
                params.creatorId = null;
              }
            }"
          />
        </a-form-item>
        <a-form-item label="当前处理人">
          <a-input v-model="params.auditUsers" placeholder="请选择当前处理人" allow-clear />
        </a-form-item>
        <a-form-item label="流程状态">
          <a-select v-model="params.status" placeholder="请选择流程状态" allow-clear>
            <a-select-option v-for="(value, key) in selectStatusMap" :key="key" :value="key">{{ value }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="业务员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="请输入业务员"
            style="width: 240px;"
            v-model="query.owner"
            @change="v => {
              if (v) {
                params.ownerId = v.id;
              } else {
                params.ownerId = null;
              }
            }"
          />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select style="width: 240px;" v-model="params.deptId" placeholder="请输入所属部门"/>
        </a-form-item>
        <a-button type="primary" @click="search(true)">查 询</a-button>
      </a-form>
    </template>

    <template #table>
      <ystable
        ref="table"
        :params="params"
        :columns="columns"
        max-height="100%"
        queryUrl="/finaDaily/getList"
        :toolbar="{ zoom: true, custom:true, refresh:true }"
        @checkbox-all="selectAllEvent"
        @checkbox-change="selectChangeEvent"
        :loading="tableLoading"
      >
        <template #buttons>
          <a-button v-if="$auth('innovation:finaDaily:add')" type="primary" style="margin-right: 20px;" @click="showDrawer()">添加</a-button>
          <a-button v-if="$auth('innovation:finaDaily:del')" :disabled="delDisabled" type="primary" @click="delDaily">删除</a-button>
        </template>
        <template #companyName="{ row }">
          <a v-if="$auth('innovation:finaDaily:check')" @click="showDrawer(row)">{{ row.companyName }}</a>
          <span v-else>{{ row.companyName }}</span>
        </template>
        <template #filepath="{ row }">
          <template v-if="row.filepath">
            <span v-for="(file,index) in row.filepath.split(',')" :key="index" style="margin-right:10px;">
              <a title="点击下载" @click="downloadFile(row, file)">
                {{ getFileName(file) }}
              </a>
              <a-tooltip style="cursor:pointer" placement="top" @click="preview(file)">
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin-left:5px" />
              </a-tooltip>
            </span>
          </template>
        </template>
        <template #status="{ row }">
          <a-badge :color="statusColor[row.status === null ? 5 : row.status]" :text="getStatusName(row.status)"/>
        </template>
      </ystable>
    </template>

    <template #other>
      <FinaDailyDrawer ref="finaDailyDrawer" @submit="handleChange(true)" :reasons="reasons" />
      <preview-modal ref="previewModal" />
    </template>
  </FormTableLayout>
</template>

<script>
import FormTableLayout from '@/components/LayoutAll/FormTableLayout'
import ystable from '@/components/Table/ystable'
import { statusMap, statusColor, getStatusName, selectStatusMap, isEditStatus } from '@/utils/processDoc/auditStatus'
import SearchSelect from '@/components/Selects/SearchSelect'
import DeptSelect from '@/components/Selects/DeptSelect'
import FinaDailyDrawer from './modules/FinaDailyDrawer'
import PreviewModal from '@/components/PreviewModal'
export default {
  data () {
    return {
      columns: [
        {
          type: 'checkbox',
          width: 50,
          fixed: 'left'
        },
        {
          title: '客户名称',
          field: 'companyName',
          align: 'left',
          width: 200,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          fixed: 'left',
          slots: { default: 'companyName' }
        },
        {
          title: '业务员',
          field: 'owner',
          align: 'left',
          width: 100,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          fixed: 'left'
        },
        {
          title: '所属部门',
          field: 'deptName',
          align: 'left',
          width: 100,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '流程状态',
          field: 'status',
          align: 'left',
          width: 100,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          formatter: ({ cellValue }) => this.statusMap[cellValue],
          slots: { default: 'status' }
        },
        {
          title: '日期',
          field: 'workDate',
          align: 'center',
          width: 150,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '事项',
          field: 'itemType',
          align: 'left',
          width: 80,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          formatter: ({ cellValue }) => {
            if (this.reasons) {
              return this.reasons[cellValue]
            }
            return ''
          }
        },
        {
          title: '附件',
          field: 'filepath',
          align: 'left',
          width: 150,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          slots: { default: 'filepath' }
        },
        {
          title: '创建人',
          field: 'creator',
          align: 'left',
          width: 100,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '内容',
          field: 'content',
          align: 'left',
          width: 200,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '当前处理人',
          field: 'auditUsers',
          align: 'left',
          width: 130,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '创建时间',
          field: 'createTime',
          align: 'center',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '最后修改时间',
          field: 'lastUpdateTime',
          align: 'center',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }
      ],
      reasons: null,
      params: {},
      form: this.$form.createForm(this),
      selectedRowKeys: [],
      selectStatusMap,
      statusColor,
      statusMap,
      tableLoading: false,
      query: { creator: null, owner: null },
      delDisabled: true
    }
  },
  components: {
    ystable,
    DeptSelect,
    FinaDailyDrawer,
    FormTableLayout,
    SearchSelect,
    PreviewModal
  },
  created () {
    this.$getDictionary(19, false).then(res => {
      this.reasons = {}
      res.forEach(item => {
        this.reasons[item.key] = item.value
      })
    })
  },
  methods: {
    getStatusName,
    /** 下载文件 */
    downloadFile (row, file) {
      this.$exportData('/document/downloadFile', { id: row.id, fileName: this.getFileName(file), filePath: file }, file.name, this.$message)
    },
    getFileName (filePath) {
      return filePath.substring(filePath.lastIndexOf('/') + 1).replace(/\d+/, '')
    },
    /** 预览文件 */
    preview (filePath) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      const fileName = this.getFileName(filePath)
      this.$refs.previewModal.show(filePath, fileName)
    },
    /** 表格刷新 */
    refresh (flag) {
      this.$refs.table.refresh(flag)
    },
    /** 查询，清除条件两端空格
     * @param {Boolean} flag 是否回首页
     * @param {Boolean} clear 是否清空查询条件
     */
    search (flag, clear = false) {
      this.selectedRowKeys = []
      if (clear) {
        this.params = {}
      } else {
        Object.keys(this.params).forEach(key => {
          if (this.params[key] && typeof this.params[key] === 'string') {
            this.params[key] = this.params[key].trim()
          }
        })
      }
      this.refresh(flag)
    },
    /** 单选 */
    selectChangeEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
      this.delDisabled = !this.selectedRowKeys.length || records.some(item => !isEditStatus(item.status))
    },
    /** 全选 */
    selectAllEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
      this.delDisabled = !this.selectedRowKeys.length || records.some(item => !isEditStatus(item.status))
    },
    /** 添加、编辑 抽屉 */
    showDrawer (row = null) {
      this.$refs.finaDailyDrawer.open(row)
    },
    /** 删除日报 */
    delDaily () {
      const self = this
      this.$confirm({
        title: '您确定要删除选择的财务日报吗？',
        onOk () {
          self.tableLoading = true
          self.$http.post('/finaDaily/delFinaDaily', { ids: self.selectedRowKeys }).then(res => {
            if (res.success) {
              self.$message.success('删除成功')
              self.search(true, true)
            } else {
              self.$message.error(res.errorMessage || '删除失败')
            }
          }).catch(res => {
            self.$message.error(res.errorMessage || '系统异常，请联系管理员')
          }).finally(() => {
            self.tableLoading = false
          })
        },
        onCancel () {}
      })
    },
    /** 添加 重置查询条件刷新至首页，
     *  编辑 不重置查询条件并刷新当前页
     * @param { Boolean } flag 是否是添加
    */
    handleChange (flag) {
      if (flag) {
        this.params = {}
      }
      this.search(flag, flag)
    }
  }
}
</script>

<style lang="less" scoped>

  // 查询行样式
  /deep/ #searchForm {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    margin-bottom: 20px;
    .ant-form-item {
      min-width: 260px;
      // width: 270px;
      display: flex;
      margin: 4px 0;
    }
    .ant-form-item-control-wrapper {
      flex-grow: 1;
      margin-right: 20px;
    }
    .ant-form-item-control {
      width: 240px;
    }

  }

  // 抽屉title加粗
  /deep/ .ant-drawer-title {
    font-weight: bold;
  }
</style>
