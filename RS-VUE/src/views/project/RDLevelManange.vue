<!--
 * @Author: ldx
 * @Date: 2021-06-19 11:36:10
 * @LastEditTime: 2022-05-26 17:31:50
 * @LastEditors: zdf
 * @Description: 多层级研发管理
 * @FilePath: \RS-VUE\src\views\project\RDLevelManange.vue
-->
<template>
  <a-spin id="spin" :spinning="spinning" tip="加载中...">
    <a-card class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto'}">
      <div id="fullBox" class="container" v-if="$auth('project:rdLevelManage:search')">
        <div class="wrap_box" id="wrap_box" >
          <div class="menu_tree" id="article_left" >
            <div
              v-for="(dir,index) in directorys"
              :key="index"
            >
              <div class="item">{{ index +1 }}.<span class="item_content" :title="dir.menu" >{{ dir.menu }}</span></div>
              <div v-for="(model,y) in dir.models" :key="y">
                <div
                  class="item-sub"
                  :class="{'item-active':model.index === active.index}"
                  @click="onActiveItem(dir,model)"
                >
                  {{ (index + 1) + '.' + (y + 1) }}.<span class="sub_content" :title="model.menuInfo">{{ model.menuInfo }}</span>
                </div>
              </div>
            </div>
          </div>
          <div class="middle" id="resize"><div class="icon">⋮</div></div>
          <div class="right_content" id="article_right">
            <div class="content_box" >
              <div class="content" >
                <div class="query-wrap">
                  <a-form layout="inline">
                    <a-form-item label="月份">
                      <a-select style="width:120px;" placeholder="请选择月份" v-model="queryParams.month">
                        <a-select-option v-for="m in 12" :value="m" :key="m">{{ monthMap[m] }}</a-select-option>
                      </a-select>
                    </a-form-item>
                    <a-form-item label="项目名称">
                      <project-select
                        :sign="2"
                        style="width: 250px;"
                        :year="currentYear"
                        :isJoinPrjectName="true"
                        :isFilter="true"
                        :filterField="{deptName: active.deptName,
                                       processSection:active.processSection }"
                        :allowClear="true"
                        v-model="queryParams.projectId"
                      />
                      <!-- <a-input placeholder="请输入项目名称" v-model="queryParams.pname"></a-input> -->
                    </a-form-item>
                    <a-form-item>
                      <a-button type="primary" @click="refresh">查询</a-button>
                    </a-form-item>
                  </a-form>
                </div>
                <div class="table-wrap">
                  <ystable
                    ref="table"
                    queryUrl="/docFileAttachment/getList"
                    :params="queryParams"
                    rowId="id"
                    size="small"
                    heigth="100%"
                    border
                    :autoLoad="false"
                    resizable
                    auto-resize
                    highlight-hover-row
                    show-overflow="title"
                    show-header-overflow
                    highlight-current-row
                    header-align="center"
                  >
                    <!-- <template #top>
                      <a-button v-if="$auth('project:rdLevelManage:upload')" type="primary" @click="onUploadFile" style="margin-bottom: 5px; margin-right: 10px;">上传附件</a-button>
                    </template> -->
                    <vxe-table-column type="seq" title="序号" width="60" headerAlign="center" align="center"/>
                    <vxe-table-column field="uploadTime" title="记录日期" width="110" headerAlign="center" align="center">
                      <template #default="{row}">
                        {{ row.uploadTime | DayFormat }}
                      </template>
                    </vxe-table-column>
                    <vxe-table-column field="pname" title="项目名称" min-width="150" headerAlign="center" align="left">
                      <template #default="{row}">
                        {{ row.rdTitle+'-'+ row.pname }}
                      </template>
                    </vxe-table-column>
                    <vxe-table-column field="place" title="地点" width="150" headerAlign="center" align="center"></vxe-table-column>
                    <vxe-table-column field="fileName" title="附件" min-width="150" headerAlign="center" align="left">
                      <template #default="{row}">
                        <a style="margin-left:5px" @click="onDownloadFile(row)" v-if="$auth('project:rdLevelManage:download')">{{ row.fileName + getExtension(row.filePath) }}</a>
                        <span v-else>{{ row.fileName + getExtension(row.filePath) }}</span>
                        <a-icon type="eye" @click="onPreview(row)" style="margin-left:5px" v-if="$auth('project:rdLevelManage:preview')" />
                      </template>
                    </vxe-table-column>
                    <!-- <vxe-table-column title="操作" width="70" align="center" header-align="center">
                      <template #default="{row}">
                        <a-popconfirm
                          title="您确定要删除？"
                          @confirm="onDel(row)"
                        >
                          <a >删除</a>
                        </a-popconfirm>
                      </template>
                    </vxe-table-column> -->
                  </ystable>
                  <preview-modal url="/document/appendixPreview" ref="previewModal"></preview-modal>
                  <!-- <upload
                    action="/docFileAttachment/upload"
                    ref="upload"
                    :month="active.month"
                    :active="active"
                    :isSelectProject="true"
                    @success="success"></upload> -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </a-card>
  </a-spin>
</template>

<script>
import ProjectSelect from '@/components/ProjectSelect/index.vue'
import { getRdManagerMenu, getList } from '@/api/project/RDLevelManange' // delUploadFile
import PreviewModal from '@/components/PreviewModal'
// import Upload from '@/components/UploadModal/Upload.vue'
import { mapGetters } from 'vuex'
import ystable from '@/components/Table/ystable'
import moment from 'moment'

export default {
  name: 'RDLevelManange',
  components: {
    ystable,
    PreviewModal,
    // Upload,
    ProjectSelect
  },
  data () {
    return {
      spinning: false,
      directorys: [],
      monthMap: {
        1: '一月',
        2: '二月',
        3: '三月',
        4: '四月',
        5: '五月',
        6: '六月',
        7: '七月',
        8: '八月',
        9: '九月',
        10: '十月',
        11: '十一月',
        12: '十二月'
      },
      queryParams: {
        month: undefined,
        deptName: undefined,
        workshop: undefined,
        productLine: undefined,
        processSection: undefined,
        projectId: undefined
      },
      active: {
        index: undefined,
        month: undefined,
        deptName: undefined,
        workshop: undefined,
        productLine: undefined,
        processSection: undefined,
        projectId: undefined
      }
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  watch: {
    currentYear: {
      handler (val) {
        this.handlerGetRdManagerMenu(val)
      }
    }
  },
  mounted () {
    if (this.$auth('project:rdLevelManage:search')) {
      this.reSize()
      this.handlerGetRdManagerMenu(this.currentYear)
    }
  },
  methods: {
    moment,
    refresh () {
      this.active.month = moment().year(this.currentYear).month(this.queryParams.month - 1).startOf('month')
      this.active.projectId = this.queryParams.projectId
      Object.assign(this.queryParams, this.active)
      this.$refs.table.refresh(true)
      this.queryParams.month = this.active.month.month() + 1
    },
    onActiveItem (dir, model) {
      this.active.deptName = dir.deptName
      this.active.workshop = dir.workshop
      this.active.processSection = model.processSection
      this.active.productLine = model.productLine
      if (this.queryParams.month) {
        this.active.month = moment().year(this.currentYear).month(this.queryParams.month - 1).startOf('month')
        this.active.projectId = this.queryParams.projectId
        Object.assign(this.queryParams, this.active)
        this.$refs.table.refresh(true)
        this.queryParams.month = this.active.month.month() + 1
        this.active.index = model.index
      } else {
        this.active.month = moment().year(this.currentYear).startOf('month')
        this.active.projectId = this.queryParams.projectId
        Object.assign(this.queryParams, this.active)
        this.$refs.table.refresh(true)
        this.queryParams.month = this.active.month.month() + 1
        this.active.index = model.index
      }
    },
    handlerGetRdManagerMenu (year) {
      this.spinning = true
      getRdManagerMenu({ year }).then(data => {
        // console.log('data', data)
        this.directorys = this.transformMenu(data)
        // console.log('this.directorys', this.directorys)
        if (this.directorys && this.directorys.length) {
          const firstData = data[0]
          this.active.deptName = firstData.deptName
          this.active.workshop = firstData.workshop
          if (firstData.models) {
            const childFirstData = firstData.models[0]
            this.active.processSection = childFirstData.processSection
            this.active.productLine = childFirstData.productLine
            this.active.index = childFirstData.index
          }
          this.active.month = moment().year(this.currentYear).startOf('month')
          Object.assign(this.queryParams, this.active)
          this.$refs.table.refresh(true)
          this.queryParams.month = this.active.month.month() + 1
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.spinning = false
      })
    },
    transformMenu (data) {
      if (!data && data.length) { return [] }
      data.forEach((item, i) => {
        if (item.models && item.models.length) {
          this.$set(item, 'index', i + 1)
          item.models.forEach((subItem, y) => {
            this.$set(subItem, 'index', (i + 1) + '' + (y + 1))
          })
        }
      })
      return data
    },
    handlerGetList () {
      getList().then(data => {

      })
    },
    onPreview (record) {
      const txt = this.getExtension(record.filePath)
      this.$refs.previewModal.show(record.filePath, record.fileName + txt)
    },
    getExtension (path) {
      if (!path) return
      return path.substring(path.lastIndexOf('.'))
    },
    onDownloadFile (record) {
      const txt = this.getExtension(record.filePath)
      this.$exportData('/sysDocument/downloadAttachment', { path: record.filePath, fileName: record.fileName + txt }, record.fileName + txt, this.$message)
    },
    /**
     * @description:  实现拖拽改变宽度
     * @param {*} null
     * @return {*} null
     */
    reSize () {
      const resize = document.getElementById('resize')
      const left = document.getElementById('article_left')
      const right = document.getElementById('article_right')
      const box = document.getElementById('wrap_box')
      resize.onmousedown = function (e) {
        const startX = e.clientX
        resize.left = left.offsetWidth
        document.onmousemove = function (e) {
          const endX = e.clientX
          let moveLen = resize.left + (endX - startX)
          const maxT = box.clientWidth - resize.offsetWidth
          if (moveLen < 160) moveLen = 160
          if (moveLen > maxT - 850) moveLen = maxT - 850
          resize.style.left = moveLen
          left.style.width = moveLen + 'px'
          right.style.width = (box.clientWidth - moveLen - 5) + 'px'
        }
        document.onmouseup = function (evt) {
          evt.stopPropagation()
          document.onmousemove = null
          document.onmouseup = null
          resize.releaseCapture && resize.releaseCapture()
        }
        resize.setCapture && resize.setCapture()
        return false
      }
    }
    // onDel (record) {
    //   delUploadFile({ id: record.id }).then(data => {
    //     if (data) {
    //       this.$message.success('操作成功！')
    //       this.refresh()
    //     }
    //   }).catch(error => {
    //     this.$message.error(error.message || '系统异常，请联系管理员！')
    //   })
    // }
  }
}
</script>

<style lang="less" scoped>
@import './less/RDLevelManange.less';
</style>
