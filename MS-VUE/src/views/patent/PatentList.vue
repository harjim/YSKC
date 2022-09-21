<template>
  <a-spin tip="请稍后..." :spinning="spin">
    <a-card :bordered="false">
      <a-form layout="inline">
        <a-form-item label="专利号">
          <a-input v-model="queryParams.patentNo" placeholder="请输入专利号" style="width: 180px" />
        </a-form-item>
        <a-form-item label="缴费截止日">
          <a-date-picker
            v-model="queryParams.startExpiryDate"
            placeholder="请输入缴费截止日"
            :disabledDate="disabledstartExpiryDate"
            @openChange="handleStartChange"
          />-
          <a-date-picker
            v-model="queryParams.endExpiryDate"
            placeholder="请输入缴费截止日"
            :disabledDate="disabledEndExpiryDate"
            :open="expiryDateOpen"
            @openChange="handleEndChange"
          />
        </a-form-item>
        <a-form-item label="专利类型">
          <a-input v-model="queryParams.mainType" placeholder="请输入发明名称" style="width: 180px" />
        </a-form-item>
        <a-form-item label="专利名称">
          <a-input v-model="queryParams.patentName" placeholder="请输入专利名称" style="width: 180px" />
        </a-form-item>
        <a-form-item label="申请日期">
          <a-date-picker
            v-model="queryParams.applyDateStartTime"
            placeholder="请输入开始日期"
            format="YYYY-MM-DD"
            :disabledDate="disabledStartDate"
            @openChange="handleStartOpenChange"
          />-
          <a-date-picker
            v-model="queryParams.applyDateEndTime"
            placeholder="请输入结束日期"
            format="YYYY-MM-DD"
            :disabledDate="disabledEndDate"
            :open="endOpen"
            @openChange="handleEndOpenChange"
          />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="search(true)" v-if="$auth('Patent:PatentList:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/rsPatent/queryPatentList"
          :params="queryParams"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          @completed="completed"
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
        >
          <template v-slot:toolbar_buttons>
            <b v-if="hasHeart">
              <template v-if="syncObj.done && !syncObj.fail">
                已连接：-/-
                <span style="margin-left:20px; margin-right: 10px;">
                  <a-button
                    :disabled="selectedRowKeys.length <= 0"
                    type="primary"
                    @click="beginSync(selectedRowKeys)"
                  >同步</a-button>
                </span>
              </template>
              <template v-else-if="!syncObj.done && !syncObj.fail">
                同步中：{{ syncObj.syncCount }}/{{ syncObj.total }}{{ syncObj.currentPatentNo ? '，当前同步：' + syncObj.currentPatentNo : '' }}
                <span
                  style="margin-right:10px;"
                >
                  <a-button type="primary" v-if="syncObj.suspend" @click="beginSync([])">继续</a-button>
                  <a-button type="primary" v-else @click="suspendPatent()">暂停</a-button>
                </span>
                <span
                  style="margin-right:10px;"
                  v-if="!syncObj.done && !syncObj.suspend && !syncObj.fail"
                >
                  <a-button type="primary" :disabled="addToIds.length<= 0" @click="addTo()">追加</a-button>
                </span>
                <span style="margin-right:10px;" v-if="!syncObj.suspend">
                  <a-button type="primary" @click="stopPatent()">终止</a-button>
                </span>
              </template>
              <span style="margin-right:10px;">
                <a-button type="primary" @click="exitPatent()">断开</a-button>
              </span>
            </b>
            <b v-else>
              未连接： -/-
              <span style="margin-left:20px;">
                <a-button
                  type="primary"
                  style="margin-right: 10px;"
                  @click="$refs.syncPatent.openSync(selectedRowKeys)"
                >登录</a-button>
              </span>
            </b>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              @click="$refs.addModal.add()"
              v-if="$auth('Patent:PatentList:add')"
            >添加</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              @click="openUploadModal"
              v-if="$auth('Patent:PatentList:import')"
            >导入</a-button>
          </template>
          <vxe-table-column type="checkbox" width="60" align="center" fixed="left"></vxe-table-column>
          <vxe-table-column
            title="专利号"
            field="patentNo"
            width="150"
            align
            header-align="center"
            fixed="left"
            remoteSort
          >
            <template v-slot="{ row }">
              <a
                @click="$refs.payableModal.showModal(row)"
                style="color:99CC99;text-decoration:underline"
              >{{ row.patentNo }}</a>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="专利名称"
            field="patentName"
            width="120"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="	申请人"
            field="applyName"
            width="120"
            align="left"
            header-align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="专利类型"
            field="mainType"
            width="120"
            align="center"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="法律状态"
            field="caseStatus"
            width="120"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="申请日期"
            field="applyDateTime"
            width="120"
            align="center"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="授权日期"
            field="authDate"
            width="120"
            align="center"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="权利要求数量"
            field="claimNum"
            width="126"
            align="right"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="使用次数"
            field="usedCnt"
            width="100"
            align="right"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="来源"
            field="source"
            width="120"
            align="center"
            header-align="center"
            remoteSort
          >
            <template v-slot="{ row }">
              {{ row.source ===0 ? '自主' : '购买' }}
            </template></vxe-table-column>
          <vxe-table-column
            title="截止日期"
            field="expiryDate"
            width="120"
            align="center"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="应缴费用"
            field="expiryAmount"
            width="120"
            align="right"
            header-align="center"
            remoteSort
          >
            <template v-slot="{ row }">
              <span v-if=" row.expiryAmount===0">暂无应缴费用</span>
              <a
                v-else
                @click="$refs.payableModal.showModal(row)"
                style="color:red;text-decoration:underline"
              >{{ row.expiryAmount }}</a>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="客户名称"
            field="companyName"
            width="150"
            align="left"
            header-align="center"
            remoteSort
          >
            <template v-if="$auth('Patent:PatentList:edit')" v-slot="{ row }">
              <a v-if="row.companyName" @click="$refs.createModal.edit(row)">{{ row.companyName }}</a>
              <a v-else @click="$refs.createModal.edit(row)" style="color:red; ">未关联客户</a>
            </template>
            <template v-else v-slot="{ row }">
              <a v-if="row.companyName">{{ row.companyName }}</a>
              <a v-else style="color:red; ">未关联客户</a>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="更新时间"
            field="lastUpdateTime"
            min-width="120"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="备注"
            field="remark"
            min-width="120"
            align="left"
            header-align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="操作"
            width="170"
            align="center"
            header-align="center"
            fixed="right"
          >
            <template v-slot="{ row }">
              <a @click="onShowAttachments(row)">附件列表</a>
              <a-divider type="vertical" />
              <a v-if="$auth('Patent:PatentList:edit')" @click="$refs.addModal.edit(row)">编辑</a>
              <a-divider type="vertical" />
              <a-popconfirm
                title="是否确定删除?"
                @confirm="handleDel(row)"
                v-if="$auth('Patent:PatentList:del')"
              >
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </div>

      <upload-modal
        :showProgress="true"
        paramKey="name"
        title="导入专利号"
        ref="uploadModal"
        action="/rsPatent/importPatent"
        templateName="专利号模板下载"
        templateNameUrl="/template/专利号列表模板.xls"
        @success="success"
        @error="error"
      />
      <sync-patent-modal ref="syncPatent" @ok="syncPatent" />
      <editType-modal ref="createModal" @ok="setCustomerId"></editType-modal>
      <addPatent-modal ref="addModal" @ok="handleOk"></addPatent-modal>
      <payable-modal ref="payableModal"></payable-modal>
      <PatentFilesModal ref="patentFilesModal"/>
    </a-card>
  </a-spin>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, UploadModal, Ellipsis } from '@/components'
import SyncPatentModal from './modules/SyncPatentModal'
import EditTypeModal from './modules/EditTypeModal'
import AddPatentModal from './modules/AddPatentModal'
import PayableModal from './modules/PayableModal'
import PatentFilesModal from './modules/PatentFilesModal.vue'
import moment from 'moment'

export default {
  name: 'Patent',
  components: {
    ystable,
    STable,
    UploadModal,
    Ellipsis,
    SyncPatentModal,
    EditTypeModal,
    AddPatentModal,
    PayableModal,
    PatentFilesModal
  },
  data () {
    return {
      syncMap: new Set(),
      addToIds: [],
      first: true,
      spin: false,
      syncObj: { syncCount: undefined, total: 0, msg: '', done: true, fail: false },
      selectedRowKeys: [],
      hasHeart: false,
      form: this.$form.createForm(this),
      endOpen: false,
      expiryDateOpen: false,
      pagination: [],
      queryParams: {},
      templateName: '专利模板',
      // initializeData: parameter => {
      //   return this.$http.get('/patent/queryPatentList', { params: Object.assign(parameter, this.queryParams) })
      //     .then(res => {
      //       return res.data
      //     })
      // },
      options: {}
    }
  },
  created () {
    this.validHeart()
  },
  methods: {
    moment,
    validHeart () {
      this.$http.post('/rsPatent/validHeart', { hasSearch: false })
        .then(res => {
          if (res.success && res.data) {
            this.hasHeart = true
            this.getSyncCount()
          } else {
            this.hasHeart = false
          }
        })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('编辑成功')
      }
      this.search(flag)
    },
    setCustomerId (flag) {
      this.$message.success('关联客户成功')
      this.search(flag)
    },
    openUploadModal () {
      this.$refs.uploadModal.show()
    },
    success (fileName, resData) {
      if (resData) {
        this.$message.success('导入成功')
      }
      if (resData.errorMessage) {
        this.$message.error(fileName + '导入失败')
      }
      this.search(true)
    },
    error (fileName, message) {
      this.$message.error(fileName + '导入失败')
    },
    handleDel (record) {
      this.$http.post('/rsPatent/delPatent', { id: record.id, patentNo: record.patentNo }).then(res => {
        if (!res.success) {
          this.$message.error(res.errorMessage)
          return
        }
        this.$message.success('删除成功')
      }).finally(res => {
        this.search(false)
      })
    },
    syncPatent (hasHeart, syncObj) {
      this.hasHeart = hasHeart
      this.syncObj = syncObj
      if (hasHeart) {
        this.search(false)
        this.getSyncCount()
      }
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    disabledStartDate (applyDateStartTime) {
      const endValue = this.queryParams.applyDateEndTime
      if (!applyDateStartTime || !endValue) {
        return false
      }
      return applyDateStartTime.valueOf() > endValue.valueOf()
    },
    disabledEndDate (applyDateEndTime) {
      const startValue = this.queryParams.applyDateStartTime
      if (!applyDateEndTime || !startValue) {
        return false
      }
      return startValue.valueOf() >= applyDateEndTime.valueOf()
    },
    handleStartOpenChange (open) {
      if (!open) {
        this.endOpen = true
      }
    },
    handleEndOpenChange (open) {
      this.endOpen = open
    },
    disabledstartExpiryDate (startExpiryDate) {
      const endDate = this.queryParams.endExpiryDate
      if (!startExpiryDate || !endDate) {
        return false
      }
      return startExpiryDate.valueOf() > endDate.valueOf()
    },
    disabledEndExpiryDate (endExpiryDate) {
      const startDate = this.queryParams.startExpiryDate
      if (!endExpiryDate || !startDate) {
        return false
      }
      return startDate.valueOf() >= endExpiryDate.valueOf()
    },
    handleStartChange (open) {
      if (!open) {
        this.expiryDateOpen = true
      }
    },
    handleEndChange (open) {
      this.expiryDateOpen = open
    },
    onSelectChange (rowKeys) {
      this.selectedRowKeys = rowKeys
      const arr = []
      if (!this.syncObj.done && !this.syncObj.suspend && !this.syncObj.fail) {
        for (let i = 0; i < rowKeys.length; i++) {
          if (!this.syncMap.has(rowKeys[i])) {
            arr.push(rowKeys[i])
          }
        }
      }
      this.addToIds = arr
    },
    getSyncCount (once) {
      if (this && this._isDestroyed) {
        return
      }
      this.$http.get('/rsPatent/getSyncCount').then(res => {
        if (res.success && res.data) {
          // 无任何异常状态，循环5秒读取信息
          this.syncObj = res.data
          if (!this.syncObj.done && !this.syncObj.fail && !this.syncObj.suspend && !this.syncObj.stop) {
            if (!once) {
              setTimeout(() => { this.getSyncCount() }, 5000)
            }
            // 失败完成同时成立，数据同步失败
          } else if (this.syncObj.done && this.syncObj.fail) {
            this.$message.warning(this.syncObj.msg)
            this.hasHeart = false
          } else {
            if (!this.first) {
              // 正常状态，提示同步信息
              if (this.syncObj.done && !this.syncObj.fail && !this.syncObj.suspend && !this.syncObj.stop) {
                if (this.syncObj.msg) {
                  this.$message.success(this.syncObj.msg)
                  this.search(false)
                }
                this.selectedRowKeys = []
              } else {
                // 存在其他信息，提示其他信息
                if (this.syncObj.msg) {
                  this.$message.info(this.syncObj.msg)
                }
              }
            }
          }
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '获取同步信息失败')
        }
        this.first = false
      })
    },
    exitPatent () {
      this.$http.get('/rsPatent/exitPatent')
        .then(res => {
          if (res.success && res.data) {
            this.hasHeart = false
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '断开失败，请稍后重试')
          }
        })
    },
    beginSync (ids, once) {
      this.spin = true
      this.syncObj.suspend = false
      ids.forEach(id => this.syncMap.add(id))
      this.$http.post('/rsPatent/validHeart', { hasSearch: true, ids: ids })
        .then(res => {
          if (res.success && res.data) {
            this.getSyncCount(once)
          } else {
            this.hasHeart = false
          }
        }).finally(() => {
          this.spin = false
        })
    },
    suspendPatent () {
      this.spin = true
      this.$http.get('/rsPatent/suspendPatent')
        .then(res => {
          if (res.success && res.data) {
            this.syncObj = res.data
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '暂停失败')
          }
        }).finally(() => {
          this.spin = false
        })
    },
    addTo () {
      this.beginSync(this.addToIds, true)
      this.addToIds = []
    },
    stopPatent () {
      this.spin = true
      const self = this
      this.$confirm({
        title: '您确定要终止当前同步吗?',
        onOk () {
          return self.$http.get('/rsPatent/stopPatent')
            .then(res => {
              if (res.success && res.data) {
                self.syncObj = res.data
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '终止失败')
              }
              self.spin = false
            })
        },
        onCancel () {
          self.spin = false
        }
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      const rowKeys = this.selectedRowKeys = records.map((item) => { return item.id })
      const arr = []
      if (!this.syncObj.done && !this.syncObj.suspend && !this.syncObj.fail) {
        for (let i = 0; i < rowKeys.length; i++) {
          if (!this.syncMap.has(rowKeys[i])) {
            arr.push(rowKeys[i])
          }
        }
      }
      this.addToIds = arr
    },
    completed () {
      this.selectedRowKeys = []
      this.addToIds = []
    },
    onShowAttachments (row) {
      const { patentFilesModal: modal } = this.$refs
      modal.show(row.patentNo, row.patentName)
    }
  }
}
</script>
<style scoped>
</style>
