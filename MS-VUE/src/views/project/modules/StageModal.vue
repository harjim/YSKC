<!--
 * @Author: ldx
 * @Date: 2020-10-14 09:08:04
 * @LastEditTime: 2020-10-31 11:29:05
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\modules\StageModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="isVisible = false"
    okText="关闭"
  >
    <a-spin :spinning="spinning">
      <a-layout>
        <a-layout-sider class="bkg" :width="140">
          <a-row class="bkg" type="flex" justify="start" align="middle">
            <a-button type="primary" @click="OpenSettingStage" v-if="$auth('project:declareProject:setDirection')" >配置阶段</a-button>
          </a-row>
          <a-menu mode="inline" v-model="activeKey" @click="menuClick">
            <a-menu-item v-for="(item,index) in stageTableData" :key="item.stageKey">
              <span>{{ index+1 }}、{{ stagesKV[item.stageKey] }}</span>
            </a-menu-item>
          </a-menu>
        </a-layout-sider>
        <a-layout>
          <a-layout-content class="bkg" style="height:500px;">
            <a-row v-if="stageTableData.length === 0" type="flex" justify="center" align="middle">
              <h3>请配置阶段 </h3>
            </a-row>
            <a-tabs v-else type="card" style="height: 100%" @change="tablesChange">
              <a-tab-pane key="1" tab="对接">
                <!-- TODO 功能选删除和单个阶段要实现要实现 -->
                <vxe-grid
                  id="SettingStageModal-DockingData"
                  ref="ystDockingData"
                  auto-resize
                  height="410px;"
                  :resizable="true"
                  highlight-hover-row
                  show-overflow
                  show-header-overflow
                  export-config
                  :data="dockingData"
                  :toolbar="{}"
                  @checkbox-change="selectChange"
                  @checkbox-all="selectChange"
                >
                  <template v-slot:buttons>
                    <a-button
                      size="small"
                      type="primary"
                      style="margin-left: 3px; margin-right:10px;"
                      @click="handleSaveStage(1)"
                      :disabled="spinning"
                      v-if="$auth('project:declareProject:setData')">保存</a-button>
                    <!-- <a-popconfirm title="是否确定删除?" @confirm="handleDelItem(1)" style="margin-left: 3px; margin-right:10px;" v-if="$auth('project:declareProject:delData')">
                      <a-button size="small" type="primary" @click="showConfirm(1)" :disabled="selectedRows.length <= 0 || spinning">删除</a-button>
                    </a-popconfirm> -->
                    <a-button size="small" type="primary" @click="showConfirm(1)" :disabled="selectedRows.length <= 0 || spinning">删除</a-button>
                  </template>
                  <vxe-table-column type="checkbox" width="40" align="center"></vxe-table-column>
                  <vxe-table-column title="序号" :width="50" type="seq" align="center" ></vxe-table-column>
                  <vxe-table-column title="资料名*" field="itemName" align="center" :width="150">
                    <template v-slot:header="{rwo}">
                      <span style="color:red;">*</span>资料名
                    </template>
                    <template v-slot="{row}">
                      <a-input placeholder="请输入资料名" v-model="row['itemName']" v-if="$auth('project:declareProject:setData')"></a-input>
                      <span v-else>{{ row.itemName }}</span>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column title="文件格式*" field="pattern" align="center" :min-width="220">
                    <template v-slot:header="{rwo}">
                      <span style="color:red;">*</span>文件格式
                    </template>
                    <template v-slot="{row}">
                      <a-select
                        v-if="$auth('project:declareProject:setData')"
                        mode="multiple"
                        option-label-prop="value"
                        :options="patternOptions"
                        v-model="row['pattern']"
                        placeholder="请选择文件格式"
                        style="width: 100%"></a-select>
                      <span v-else>
                        <span v-for=" (item ,index) in row.pattern" :key="index">
                          {{ ' ' + item + ' ' }}
                        </span>
                      </span>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column title="说明/备注" field="remark" align="center" :width="150" >
                    <template v-slot="{row}">
                      <a-textarea placeholder="请输入说明/备注" v-model="row['remark']" :rows="1" v-if="$auth('project:declareProject:setData')"/>
                      <span v-else> {{ row.remark }}</span>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column title="是否必须" field="required" align="center" :width="130" >
                    <template v-slot="{row}">
                      <a-radio-group v-model="row['required']" size="small" :disabled="!$auth('project:declareProject:setData')">
                        <a-radio :value="true">是</a-radio>
                        <a-radio :value="false">否</a-radio>
                      </a-radio-group>
                    </template>
                  </vxe-table-column>
                </vxe-grid>
                <a-button type="dashed" style="width: 100%;font-weight: bolder;" @click="addItem(1)" v-if="$auth('project:declareProject:setData')">+添加</a-button>
              </a-tab-pane>
              <a-tab-pane key="0" tab="交付">
                <vxe-grid
                  id="SettingStageModal-DeliverData"
                  ref="ystDeliverData"
                  auto-resize
                  height="410px;"
                  :resizable="true"
                  highlight-hover-row
                  show-overflow
                  show-header-overflow
                  export-config
                  :data="deliverData"
                  :toolbar="{}"
                  @checkbox-change="selectChange"
                  @checkbox-all="selectChange"
                >
                  <template v-slot:buttons>
                    <a-button
                      size="small"
                      type="primary"
                      :disabled="spinning"
                      style="margin-left: 3px; margin-right:10px;"
                      @click="handleSaveStage(0)"
                      v-if="$auth('project:declareProject:setData')">保存</a-button>
                    <!-- <a-popconfirm title="是否确定删除?" @confirm="handleDelItem(0)" style="margin-left: 3px; margin-right:10px;" v-if="$auth('project:declareProject:delData')">
                      <a-button size="small" type="primary" @click="showConfirm(1)" :disabled="selectedRows.length <= 0 || spinning">删除</a-button>
                    </a-popconfirm> -->
                    <a-button size="small" type="primary" @click="showConfirm(0)" :disabled="selectedRows.length <= 0 || spinning">删除</a-button>
                  </template>
                  <vxe-table-column type="checkbox" width="40" align="center"></vxe-table-column>
                  <vxe-table-column title="序号" :width="50" type="seq" align="center" ></vxe-table-column>
                  <vxe-table-column title="资料名*" field="itemName" align="center" :width="150">
                    <template v-slot:header="{rwo}">
                      <span style="color:red;">*</span>资料名
                    </template>
                    <template v-slot="{row}">
                      <a-input placeholder="请输入资料名" v-model="row['itemName']" v-if="$auth('project:declareProject:setData')"></a-input>
                      <span v-else>{{ row.itemName }}</span>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column title="文件格式*" field="pattern" align="center" :min-width="220">
                    <template v-slot:header="{rwo}">
                      <span style="color:red;">*</span>文件格式
                    </template>
                    <template v-slot="{row}">
                      <a-select
                        mode="multiple"
                        v-if="$auth('project:declareProject:setData')"
                        option-label-prop="value"
                        :options="patternOptions"
                        v-model="row['pattern']"
                        placeholder="请选择文件格式"
                        style="width: 100%"></a-select>
                      <span v-else>
                        <span v-for=" (item ,index) in row.pattern" :key="index">
                          {{ ' ' + item + ' ' }}
                        </span>
                      </span>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column title="说明/备注" field="remark" align="center" :width="150">
                    <template v-slot="{row}">
                      <a-textarea placeholder="请输入说明/备注" v-model="row['remark']" :rows="1" v-if="$auth('project:declareProject:setData')"/>
                      <span v-else> {{ row.remark }}</span>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column title="是否必须" field="required" align="center" :width="130">
                    <template v-slot="{row}">
                      <a-radio-group v-model="row['required']" size="small" :disabled="!$auth('project:declareProject:setData')">
                        <a-radio :value="true">是</a-radio>
                        <a-radio :value="false">否</a-radio>
                      </a-radio-group>
                    </template>
                  </vxe-table-column>
                </vxe-grid>
                <a-button type="dashed" style="width: 100%;font-weight: bolder;" @click="addItem(0)" v-if="$auth('project:declareProject:setData')">+添加</a-button>
              </a-tab-pane>
            </a-tabs>
          </a-layout-content>
        </a-layout>
      </a-layout>
    </a-spin>
    <setting-stage-modal ref="settingStageModal" @refreshStage="refreshStage"></setting-stage-modal>
  </a-modal>
</template>
<script>
import SettingStageModal from './SettingStageModal'
import { cloneDeep } from 'lodash'
import moment from 'moment'
export default {
  name: 'StageModal',
  components: {
    // StageListsModal,
    SettingStageModal
  },
  created () {
    // 获取阶段字典
    this.$getDictionary(7).then(res => {
      for (const stage of res) {
        this.stagesKV[stage.key] = stage.value
      }
    })
    // 获取文件类型
    this.$getFileTypes().then(res => {
      for (const fileType of res) {
        this.fileTypes.push({ value: fileType.label, label: fileType.label })
      }
      this.patternOptions = this.fileTypes
    })
  },
  data () {
    return {
      isVisible: false,
      stagesKV: {},
      stagesMap: {},
      fileTypes: [],
      patternOptions: [],
      activeKey: [],
      isEdit: false,
      directionId: undefined,
      row: undefined,
      stageTableData: [],
      dockingData: [],
      deliverData: [],
      title: '',
      selectedRows: [],
      spinning: true,
      isDisabled: false
    }
  },
  methods: {
    show (directionId, row) {
      this.afterClose()
      this.row = row
      this.title = `配置阶段材料[${row.direction}]`
      this.isVisible = true
      this.directionId = directionId

      this.getStageList()
    },
    afterClose () {
      this.isVisible = false
      this.stagesMap = {}
      this.activeKey = []
      this.isEdit = false
      this.directionId = undefined
      this.dockingData = []
      this.deliverData = []
      this.stageTableData = []
      this.row = undefined
      this.title = ''
      this.selectedRows = []
      this.spinning = true
      this.isDisabled = false
    },
    // 菜单点击事件
    menuClick ({ item, key, keyPath }) {
      this.activeKey = [key]
      this.dockingData = this.stagesMap[this.activeKey[0]].dockingData
      this.deliverData = this.stagesMap[this.activeKey[0]].deliverData
    },
    // 获取阶段列表
    getStageList () {
      this.$http.get('/rsProduct/getDirectionStage', { params: { directionId: this.directionId } }).then((res) => {
        if (res.data && res.success) {
          this.stageTableData = []
          if (res.data.length) {
            this.stageTableData = [...res.data]
            this.stageTableData.sort((a, b) => a.seq - b.seq)
            this.stagesMap = {}
            for (const stage of this.stageTableData) {
              stage['expiryDate'] = moment(stage['expiryDate'])
              if (!this.stagesMap[stage.stageKey]) {
                let dockingData = []
                let deliverData = []
                if (stage.models) {
                  dockingData = this.filterItemType(stage.models, 1)
                  deliverData = this.filterItemType(stage.models, 0)
                }
                stage['dockingData'] = dockingData
                stage['deliverData'] = deliverData
                this.$set(this.stagesMap, stage.stageKey, stage)
              }
            }
            if (this.activeKey.length === 0 || !this.stagesMap[this.activeKey[0]]) {
              this.activeKey[0] = this.stageTableData[0].stageKey
            }
            this.dockingData = this.stagesMap[this.activeKey[0]].dockingData
            this.deliverData = this.stagesMap[this.activeKey[0]].deliverData
          }
          this.spinning = false
        } else {
          this.spinning = false
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.spinning = false
        this.$message.error(error.message)
      })
      // this.spinning = false
    },
    // 过滤是交付还是对接
    filterItemType (data, itemType) {
      return data.filter((item, index) => {
        if (item['pattern'] && !Array.isArray(item['pattern'])) {
          const ary = item['pattern'].split(',')
          item['pattern'] = ary
        }
        return item.itemType === itemType
      })
    },
    // 单个阶段保存按钮
    handleSaveStage (itemType) {
      this.spinning = true
      // const stageFiles = []
      const saveAry = itemType ? this.dockingData : this.deliverData
      if (saveAry && saveAry.length > 0) {
        const verifyObj = this.verifyTableData(saveAry)
        if (verifyObj && verifyObj.result) {
          this.$message.warning(verifyObj.message)
          this.spinning = false
          return
        }
        if (this.isRepeat(saveAry)) {
          this.$message.warning('资料名存在重复的。')
          this.spinning = false
          return
        }
      } else {
        this.spinning = false
        return
      }
      // stageFiles = stageFiles.concat(this.dockingData, this.deliverData)
      // stageFiles = saveAry
      const postStageFile = cloneDeep(saveAry)
      for (const stage of postStageFile) {
        if (stage['pattern'] && Array.isArray(stage['pattern'])) {
          const str = stage['pattern'].join(',')
          stage['pattern'] = str
        }
      }
      this.$http.post('/rsProduct/SetStageList', postStageFile).then((res) => {
        if (res.data && res.success) {
          this.getStageList()
          this.spinning = false
          this.$message.success('操作成功')
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinning = false
      })
    },
    verifyTableData (tableData) {
      let count = 0
      for (const item of tableData) {
        count++
        const verifyAry = ['itemName', 'pattern']
        for (const value of verifyAry) {
          if (!item[value]) {
            return {
              result: true,
              message: `当前阶段: ${item.itemType ? '对接' : '交付'}:第${count}行,标题带*的，要必填！！`
            }
          }
        }
      }
    },
    // 添加阶段刷新事件
    refreshStage (stageTableData) {
      this.getStageList()
      if (stageTableData.length > 0) {
        this.row.hasStage = true
      } else {
        this.row.hasStage = false
      }
    },
    // 表格添加一行
    addItem (itemType) {
      this.spinning = true
      const row = {
        id: undefined,
        itemName: undefined,
        pattern: undefined,
        remark: undefined,
        required: true,
        itemType,
        directionId: this.directionId,
        stageId: this.stagesMap[this.activeKey[0]]['id'],
        seq: itemType ? this.stagesMap[this.activeKey[0]]['dockingData'].length : this.stagesMap[this.activeKey[0]]['deliverData'].length
      }
      if (itemType) {
        this.stagesMap[this.activeKey[0]]['dockingData'].push(row)
      } else {
        this.stagesMap[this.activeKey[0]]['deliverData'].push(row)
      }
      this.spinning = false
    },
    // 表格删除一行
    handleDelItem (itemType) {
      this.spinning = true
      // 已在技改项目中使用，不能删除
      const postRows = []
      const delIds = []
      this.selectedRows.forEach((row) => {
        delIds.push(row._XID)
        if (row.id) {
          if (row.limitChange) {
            this.$message.warning(`${row.itemName}已在技改项目中使用，不能删除`)
            return
          }
          const tempRow = cloneDeep(row)
          if (tempRow['pattern'] && Array.isArray(tempRow['pattern'])) {
            const str = tempRow['pattern'].join(',')
            tempRow['pattern'] = str
            postRows.push(tempRow)
          }
        }
      })
      if (postRows.length > 0) {
        this.$http.post('/rsProduct/delStageList', postRows).then((res) => {
          if (res.data && res.success) {
            this.handleDel(itemType, delIds)
            this.spinning = false
            this.$message.success('操作成功')
          } else {
            this.$message.error(res.errorMessage)
          }
        }).catch((error) => {
          this.$message.error(error.message)
        })
      } else {
        this.handleDel(itemType, delIds)
        this.spinning = false
      }
    },
    handleDel (itemType, delIds) {
      const fieldName = itemType ? 'dockingData' : 'deliverData'
      const tempAry = this[fieldName]
      for (const id of delIds) {
        tempAry.forEach((item, index) => {
          if (item._XID === id) {
            tempAry.splice(index, 1)
          }
        })
      }
    },
    // 打开配置阶段modal
    OpenSettingStage () {
      this.$refs.settingStageModal.show(this.directionId, this.stageTableData)
    },
    // 验证一个数组是否有重复
    isRepeat (arr) {
      const verifyAry = []
      for (const item of arr) {
        verifyAry.push(item.itemName)
      }
      const hash = {}
      for (const i in verifyAry) {
        if (hash[verifyAry[i]]) {
          return true
        }
        hash[verifyAry[i]] = true
      }
      return false
    },
    // 表格复选框事件
    selectChange ({ records }) {
      this.selectedRows = records
    },
    tablesChange () {
      if (this.$refs.ystDeliverData) {
        this.$refs.ystDeliverData.clearCheckboxRow()
      }
      if (this.$refs.ystDockingData) {
        this.$refs.ystDockingData.clearCheckboxRow()
      }
      this.selectedRows = []
    },
    showConfirm (itemType) {
      const self = this
      this.$confirm({
        title: '是否确定删除?',
        onOk () {
          self.handleDelItem(itemType)
        }
      })
    }
  }
}
</script>
<style lang='less' scoped>
  .bkg {
    background-color: #fff;
  }
  .layout {
    padding-left: 4px;
  }
  .saveDiv {
    display:flex;

  }
  * /deep/ .ant-tabs-card-content {
    height: calc(100% - 56px);
    overflow-y: auto;
  }
  #tabs /deep/ .ant-tabs-left-bar {
    margin-bottom: 4px;
  }
  .customAddBtn {
    // margin-top: 25px;
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
    color: #fff;
    font-size: 18px;
    font-weight: bolder;
    text-align: center;
    background-color: #1890ff;
  }
.customAddBtn:hover {
  background-color: #40A9FF;
  cursor: pointer;
}
</style>
