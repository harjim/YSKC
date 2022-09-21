<!--
 * @Author: ldx
 * @Date: 2021-07-14 08:56:43
 * @LastEditTime: 2021-09-14 14:09:49
 * @LastEditors: zdf
 * @Description: 购买详情抽屉
 * @FilePath: \MS-VUE\src\views\patent\modules\PurchaseListDrawer.vue
-->
<template>
  <div>
    <a-drawer
      :title="title"
      width="1184"
      placement="right"
      :closable="true"
      :visible="isVisible"
      :bodyStyle="{padding: '12px 12px',height: 'calc(100% - 108px)', overflowY: 'auto'}"
      @close="onClose"
    >
      <div class="title" style="marign-top: 0px;">可购专利列表</div>
      <a-spin :spinning="canBuySpin" tip="请稍后...">
        <ystable
          ref="selectTable"
          queryUrl="/patentBuying/getBuyingList"
          :params="selectPatent"
          border="full"
          size="small"
          header-align="center"
          highlight-hover-row
          show-overflow="title"
          resizable
          auto-resize
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          @completed="({data:{data}})=>completed(data)"
          :toolbar="{ custom: true, refresh:true }"
        >
          <template #buttons>
            <template v-if="isCompleted">
              <a-button type="primary" size="small" style="margin-right:8px;" @click="onAdd" v-if="auth.selected">添加</a-button>
              <a-popconfirm
                title="您确定要移除？"
                @confirm="handlerBuyBack(true)"
                v-if="auth.buyBack"
                :disabled="selectedRows.length <= 0"
              >
                <a-button
                  type="primary"
                  size="small"
                  style="margin-right:8px;"
                  :disabled="selectedRows.length <= 0"
                >移除</a-button>
              </a-popconfirm>
              <a-button
                type="primary"
                size="small"
                :disabled="hasData"
                style="margin-right:8px;"
                @click="exportData"
                v-if="auth.exportCanBuy">导出</a-button>
            </template>
          </template>
          <vxe-table-column type="checkbox" width="50" align="center" header-align="center"></vxe-table-column>
          <vxe-table-column type="seq" title="序号" width="50" align="center" header-align="center"></vxe-table-column>
          <vxe-table-column field="patentNo" title="专利号" width="100" align="left" header-align="center"></vxe-table-column>
          <vxe-table-column field="patentName" title="专利名称" width="100" align="left" header-align="center"></vxe-table-column>
          <vxe-table-column title="专利类型" width="100" align="center" header-align="center">
            <template #default="{row}">
              {{ mainTypeMap[row.mainType] }}
            </template>
          </vxe-table-column>
          <vxe-table-column field="inventor" title="发明人" width="100" align="left" header-align="center"></vxe-table-column>
          <vxe-table-column field="inventor" title="状态" width="80" align="center" header-align="center">
            <template #default="{row}">
              {{ statusMap[row.status] }}
            </template>
          </vxe-table-column>
          <vxe-table-column field="" title="专利资料" minWidth="250" align="left" header-align="center">
            <template #default="{row}">
              <span v-for="(file,index) in transFormStrToAry(row.patentSeaFile)" :key="index">
                <a
                  v-if="auth.download"
                  @click="onDownloadFile({path: file.url,name: file.name })"
                  title="点击下载专利资料"
                >{{ file.name }}</a>
                <span v-else>{{ file.name }}</span>
                <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.url,name: file.name })" v-if="file.url && auth.preview" >
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin:0 5px" />
                </a-tooltip>
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column field="applyDateTime" title="申请日期" width="100" align="center" header-align="center">
            <template #default="{row}">
              {{ row.applyDateTime | DateFormat }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="操作"
            width="80"
            align="center"
            header-align="center"
            fixed="right"
            v-if="isCompleted "
          >
            <template #default="{row}">
              <a-popconfirm
                title="您确定要选购？"
                @confirm="onComfirm(row)"
                v-if="auth.chooseBuy"
              >
                <a>选购</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </a-spin>
      <div class="title">选购专利列表</div>
      <ystable
        ref="selectedTable"
        queryUrl="/patentBuying/getBuyingList"
        :params="selectedPatent"
        border="full"
        size="small"
        header-align="center"
        highlight-hover-row
        show-overflow="title"
        resizable
        auto-resize
        :toolbar="{ custom: true, refresh:true }"
      >
        <vxe-table-column type="seq" title="序号" width="50" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column field="patentNo" title="专利号" width="100" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column field="patentName" title="专利名称" width="100" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column title="专利类型" width="100" align="center" header-align="center">
          <template #default="{row}">
            {{ mainTypeMap[row.mainType] }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="inventor" title="发明人" width="100" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column field="inventor" title="状态" width="80" align="center" header-align="center">
          <template #default="{row}">
            {{ statusMap[row.status] }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="" title="专利资料" minWidth="250" align="left" header-align="center">
          <template #default="{row}">
            <span v-for="(file,index) in transFormStrToAry(row.patentSeaFile)" :key="index">
              <a
                v-if="auth.download"
                @click="onDownloadFile({path: file.url,name: file.name })"
                title="点击下载专利资料"
              >{{ file.name }}</a>
              <span v-else>{{ file.name }}</span>
              <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.url,name: file.name })" v-if="file.url && auth.preview" >
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin:0 5px" />
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="" title="申请日期" width="100" align="center" header-align="center">
          <template #default="{row}">
            {{ row.applyDateTime | DateFormat }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="操作"
          width="100"
          align="center"
          header-align="center"
          fixed="right"
          v-if="isCompleted"
        >
          <template #default="{row}">
            <a-popconfirm
              title="您确定要购买？"
              @confirm="onBuy(row)"
              v-if="row.status ===1 && auth.buy"
            >
              <a>购买</a>
            </a-popconfirm>
            <a-divider type="vertical" v-if="auth.buyBack"></a-divider>
            <a-popconfirm
              title="您确定要移除？"
              v-if="auth.buyBack"
              @confirm="handlerBuyBack(false,row)"
            >
              <a>移除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
      <div class="title">已购专利列表</div>
      <a-spin :spinning="boughtSpin" tip="请稍后...">
        <ystable
          ref="completedTable"
          queryUrl="/patentBuying/getBuyingList"
          :params="completedPatent"
          border="full"
          size="small"
          header-align="center"
          highlight-hover-row
          show-overflow="title"
          resizable
          auto-resize
          :toolbar="{ custom: true, refresh:true }"
        >
          <vxe-table-column type="seq" title="序号" width="50" align="center" header-align="center"></vxe-table-column>
          <vxe-table-column field="patentNo" title="专利号" width="100" align="left" header-align="center"></vxe-table-column>
          <vxe-table-column field="patentName" title="专利名称" width="100" align="left" header-align="center"></vxe-table-column>
          <vxe-table-column title="专利类型" width="100" align="center" header-align="center">
            <template #default="{row}">
              {{ mainTypeMap[row.mainType] }}
            </template>
          </vxe-table-column>
          <vxe-table-column field="inventor" title="发明人" width="100" align="left" header-align="center"></vxe-table-column>
          <vxe-table-column field="inventor" title="状态" width="80" align="center" header-align="center">
            <template #default="{row}">
              {{ statusMap[row.status] }}
            </template>
          </vxe-table-column>
          <vxe-table-column field="" title="专利资料" minWidth="250" align="left" header-align="center">
            <template #default="{row}">
              <span v-for="(file,index) in transFormStrToAry(row.patentSeaFile)" :key="index">
                <a
                  v-if="auth.download"
                  @click="onDownloadFile({path: file.url,name: file.name })"
                  title="点击下载专利资料"
                >{{ file.name }}</a>
                <span v-else>{{ file.name }}</span>
                <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.url,name: file.name })" v-if="file.url && auth.preview" >
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin:0 5px" />
                </a-tooltip>
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column field="" title="采购凭证" minWidth="200" align="left" header-align="center">
            <template #default="{row}">
              <span v-for="(file,index) in transFormStrToAry(row.filePath)" :key="index">
                <a
                  v-if="auth.download"
                  @click="onDownloadFile({path: file.url,name: file.name })"
                  title="点击下载采购凭证"
                >{{ file.name }}</a>
                <span v-else>{{ file.name }}</span>
                <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.url,name: file.name })" v-if="file.url && auth.preview" >
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin:0 5px" />
                </a-tooltip>
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column field="" title="转让资料" minWidth="200" align="left" header-align="center">
            <template #default="{row}">
              <span v-for="(file,index) in transFormStrToAry(row.sellFile)" :key="index" style="margin-right:5px">
                <a
                  v-if="auth.download"
                  @click="onDownloadFile({path: file.url,name: file.name })"
                  title="点击下载转让资料"
                >{{ file.name }}</a>
                <span v-else>{{ file.name }}</span>
                <a-tooltip style="cursor:pointer" placement="top" @click="onPreview({path: file.url,name: file.name })" v-if="file.url && auth.preview" >
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin-left:5px" />
                </a-tooltip>
                <a-popconfirm
                  :title="`是否确定[${file.name}]删除?`"
                  @confirm="delFile(row,file.url)"
                  style="color:red;margin-left:5px"
                  v-if="isCompleted && auth.buy"
                >
                  <a-tooltip placement="top">
                    <template slot="title">
                      <span>删除</span>
                    </template>
                    <a-icon type="close" />
                  </a-tooltip>
                </a-popconfirm>
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column field="" title="申请日期" width="100" align="center" header-align="center">
            <template #default="{row}">
              {{ row.applyDateTime | DateFormat }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="操作"
            width="110"
            align="center"
            header-align="center"
            fixed="right"
            v-if="isCompleted && auth.buy"
          >
            <template #default="{row}">
              <a @click="uploadSellFile(row)">上传转让资料</a>
            </template>
          </vxe-table-column>
        </ystable>
      </a-spin>
      <patent-sea-modal ref="patentSeaModal" :previewCB="previewCB" @success="success_cb" :auth="auth"></patent-sea-modal>
      <file-upload-modal ref="fileUploadModal" @submitStatus="submitStatus" @updateSellFile="updateSellFile" :previewCB="previewCB"></file-upload-modal>
      <div
        :style="{
          position: 'absolute',
          right: 0,
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e9e9e9',
          padding: '10px 16px',
          background: '#fff',
          textAlign: 'right',
          zIndex: 1,
        }"
      >
        <a-button type="primary" @click="onCompleted" v-if="isCompleted">
          确定购买完成
        </a-button>
        <a-button v-else @click="() => isVisible = false">关闭</a-button>
      </div>
    </a-drawer>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import PatentSeaModal from './PatentSeaModal'
import { buyPatent, completePurchase, buyBack } from '@/api/patent/demandList'
import FileUploadModal from './FileUploadModal.vue'
import Template from '@/views/doc/Template.vue'
export default {
  name: 'PurchaseListDrawer',
  components: {
    ystable,
    PatentSeaModal,
    FileUploadModal,
    Template
  },
  props: {
    previewCB: {
      type: Function,
      required: true
    },
    auth: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      hasData: false,
      hasUpdate: false,
      boughtSpin: false,
      canBuySpin: false,
      isVisible: false,
      selectPatent: {
        status: 0
      },
      selectedPatent: {
        status: 1
      },
      completedPatent: {
        status: 2
      },
      selectedRows: [],
      demandRecord: undefined,
      title: {},
      mainTypeMap: {
        1: '发明专利',
        2: '实用新型',
        3: '外观设计'
      },
      statusList: [
        { value: 0, label: '未购' },
        { value: 1, label: '选购' },
        { value: 2, label: '已购' }
      ],
      statusMap: {
        0: '未购',
        1: '选购',
        2: '购买'
      },
      isCompleted: false
    }
  },
  methods: {
    show (demandRecord, title = '购买详情') {
      this.hasUpdate = false
      this.isVisible = true
      this.title = `${title}【${demandRecord.companyName}】`
      this.isCompleted = demandRecord.status < 3
      this.demandRecord = demandRecord
      this.selectPatent.demandId = demandRecord.id
      this.selectedPatent.demandId = demandRecord.id
      this.completedPatent.demandId = demandRecord.id
      this.success_cb()
    },
    onClose () {
      this.isVisible = false
      this.isCompleted = false
      if (this.hasUpdate) {
        this.$emit('ok')
      }
    },
    onAdd () {
      this.$refs.patentSeaModal.show(this.demandRecord)
    },
    success_cb (ary = [1, 2, 3]) {
      const objAry = {
        1: 'selectTable',
        2: 'selectedTable',
        3: 'completedTable'
      }
      ary.forEach(key => {
        const field = objAry[key]
        if (field && this.$refs[field]) {
          this.$refs[field].refresh()
        }
      })
      // if (this.$refs.selectTable) {
      //   this.$refs.selectTable.refresh()
      // }
      // if (this.$refs.selectedTable) {
      //   this.$refs.selectedTable.refresh()
      // }
      // if (this.$refs.completedTable) {
      //   this.$refs.completedTable.refresh()
      // }
    },
    transFormStrToAry (filePaths) {
      if (!filePaths) return []
      const files = []
      filePaths.split(',').forEach(path => {
        const name = path.substring(path.lastIndexOf('/') + 14)
        files.push({
          uid: path,
          name,
          status: 'done',
          url: path
        })
      })
      return files
    },
    onPreview ({ path, name }) {
      this.previewCB({ path, name })
    },
    onDownloadFile (file) {
      this.$exportData('/patentBuying/download', { filePath: file.path }, file.name, this.$message)
    },
    onComfirm (record) {
      this.submitStatus(record, { id: record.id, status: 1 }, [1, 2])
    },
    onBuy (record) {
      this.hasUpdate = true
      this.$refs.fileUploadModal.show(record, this.demandRecord)
    },
    uploadSellFile (record) {
      this.$refs.fileUploadModal.show(record, this.demandRecord, '上传转让资料', true)
    },
    delFile (row, url) {
      const arr = this.transFormStrToAry(row.sellFile)
      this.updateSellFile(row, arr.filter(a => a.url !== url).map(a => a.url).join(','), true)
    },
    updateSellFile (row, files, del) {
      let sellFile
      if (del) {
        sellFile = files
      } else {
        sellFile = row.sellFile
        if (sellFile && files.length) {
          sellFile += (',' + files)
        } else {
          sellFile = files
        }
      }
      this.boughtSpin = true
      this.$http.post('/patentBuying/updateSellFile', { id: row.id, sellFile }).then(res => {
        if (res.success && res.data) {
          row.sellFile = sellFile
        } else {
          this.$message.error(res.errorMessage || '保存转让资料失败')
        }
      }).finally(() => {
        this.boughtSpin = false
      })
    },
    submitStatus (record, param, ary) {
      buyPatent(param).then(data => {
        if (data) {
          record.status = param.status
          if (param.filePath) {
            record.filePath = param.filePath
          }
          this.$message.success('操作成功！')
          this.success_cb(ary)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
    },
    onCompleted () {
      this.$confirm({
        title: '您确定要改为【完成】状态？',
        onOk: () => {
          completePurchase({ demandId: this.demandRecord.id }).then(data => {
            if (data) {
              this.demandRecord.status = 3
              this.onClose()
              this.$message.success('操作成功！')
            }
          }).catch(error => {
            this.$message.error(error.message || '系统异常，请联系管理员！')
          })
        },
        onCancel: () => {}
      })
    },
    onRemove () {},
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRows = records
    },
    handlerBuyBack (isBatchRemove, record) {
      let params = []
      if (isBatchRemove) {
        params = this.selectedRows.map(record => record.id)
      } else {
        params.push(record.id)
      }
      buyBack(params).then(data => {
        if (data) {
          this.$message.success('操作成功！')
          if (isBatchRemove) {
            this.success_cb([1])
          } else {
            this.success_cb([1, 2])
          }
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
    },
    completed (data) {
      this.selectedRows = []
      this.hasData = !(data && data.length)
    },
    exportData () {
      this.canBuySpin = true
      this.$exportData('/patentBuying/exportCanBuy', { status: 0, demandId: this.demandRecord.id }, undefined, this.$message).then(res => {
        this.canBuySpin = false
      })
    }
  }
}
</script>

<style lang="less" scoped>
.title {
  line-height: 40px;
  font-weight: 650;
  font-size: 16px;
  margin-top: 8px;
}
</style>
