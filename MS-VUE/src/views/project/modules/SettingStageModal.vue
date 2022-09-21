<!--
 * @Author: ldx
 * @Date: 2020-10-19 10:24:42
 * @LastEditTime: 2021-06-11 14:29:36
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\modules\SettingStageModal.vue
-->
<template>
  <a-modal
    title="配置阶段"
    :width="700"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-spin :spinning="spinning">
      <vxe-grid
        ref="yst"
        auto-resize
        :resizable="true"
        id="HandleDeclareItemModal"
        highlight-hover-row
        show-overflow
        show-header-overflow
        border
        size="small"
        :data="stageTableData"
      >
        <vxe-table-column title="序号" :width="60" type="seq" align="center" ></vxe-table-column>
        <vxe-table-column title="阶段*" field="stageKey" align="left" :min-width="150">
          <template v-slot:header>
            <span style="color:red;">*</span>阶段
          </template>
          <template v-slot="{row,rowIndex}">
            <span v-if="$auth('project:declareProject:editStage')">
              <a-select
                :options="stages"
                v-model="row['stageKey']"
                placeholder="请选择阶段"
                @change="selectChange"
                style="width: 85%; ">
              </a-select>
              <span >
                <span v-if="rowIndex > 0" class="btn" style="margin-right: 3px;" @click="hanldeSeq('up',row,rowIndex)"><a-icon type="arrow-up" /></span>
                <span v-if="rowIndex+1 < stageTableData.length && stageTableData.length >=0 " class="btn" style="margin-right: 3px;" @click="hanldeSeq('down',row,rowIndex)"><a-icon type="arrow-down" /></span>
              </span>
            </span>
            <span v-else>
              <span>{{ stagesKV[row.stageKey] }}</span>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="截止日期*" field="expiryDate" align="center" :width="150" >
          <template v-slot:header>
            <span style="color:red;">*</span>截止日期
          </template>
          <template v-slot="{row}">
            <span v-if="$auth('project:declareProject:editStage')">
              <a-date-picker placeholder="截止日期" v-model="row['expiryDate']"></a-date-picker>
            </span>
            <span v-else>
              {{ moment(row.expiryDate).format('YYYY-MM-DD') }}
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="操作" align="center" :width="100" >
          <template v-slot="{ row ,rowIndex}">
            <a-popconfirm v-if="!row.limitChange && $auth('project:declareProject:delStage')" title="是否确定删除?" @confirm="handleDel(row,rowIndex)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </vxe-grid>
      <a-button type="dashed" style="width: 100%;font-weight: bolder" @click="addStage()" v-if="$auth('project:declareProject:editStage')">+</a-button>
    </a-spin>
  </a-modal>
</template>
<script>
import { cloneDeep } from 'lodash'
import moment from 'moment'
export default {
  name: 'SettingStageModal',
  created () {
    // 获取阶段字典
    this.$getDictionary(7).then(res => {
      res.forEach((item, index) => {
        this.stages.push({ value: item.key, label: item.value })
        this.stagesKV[item.key] = item.value
      })
    })
  },
  data () {
    return {
      isVisible: false,
      stageTableData: [],
      directionId: undefined,
      stages: [],
      stagesKV: {},
      selectidStage: [],
      spinning: true,
      confirmLoading: false
    }
  },
  methods: {
    moment,
    show (directionId, stageTableData) {
      this.spinning = true
      this.stageTableData = []
      this.stageTableData = [...stageTableData]
      this.isVisible = true
      this.directionId = directionId
      for (const stage of this.stages) {
        delete stage['disabled']
      }
      for (const item of this.stageTableData) {
        for (const stage of this.stages) {
          if (item.stageKey === stage.value) {
            this.$set(stage, 'disabled', true)
          }
        }
      }
      this.spinning = false
    },
    afterClose () {
    },
    handleSubmit () {
      if (!this.$auth('project:declareProject:editStage')) {
        this.isVisible = false
        return
      }
      this.confirmLoading = true
      this.spinning = true
      const verifyObj = this.verifyTableData()
      if (verifyObj && verifyObj.result) {
        this.$message.warning(verifyObj.message)
        return
      }
      this.stageTableData.forEach((item, index) => {
        item.seq = index
      })
      this.$http.post('/rsProduct/editStages', this.stageTableData).then((res) => {
        if (res.data && res.success) {
          this.$emit('refreshStage', this.stageTableData)
          this.spinning = false
          this.$message.success('操作成功')
          this.isVisible = false
          this.confirmLoading = false
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
          this.confirmLoading = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinning = false
        this.confirmLoading = false
      })
    },
    verifyTableData () {
      let count = 0
      for (const item of this.stageTableData) {
        count++
        const verifyAry = ['stageKey', 'expiryDate']
        for (const value of verifyAry) {
          if (!item[value]) {
            return {
              result: true,
              message: `第${count}行: 标题带*的，要必填！`
            }
          }
        }
      }
    },
    addStage () {
      const row = {
        id: undefined,
        directionId: this.directionId,
        stageKey: undefined, // 阶段
        expiryDate: undefined, // 截止日期
        seq: this.stageTableData.length // 顺序
      }
      this.stageTableData.push(row)
    },
    // 处理排序
    hanldeSeq (type, row, rowIndex) {
      if (type === 'up') {
        const temp = cloneDeep(this.stageTableData[rowIndex - 1]) // 上一行数据
        const upSeq = temp.seq // 上一行数据 seq
        temp.seq = row.seq
        row.seq = upSeq
        Object.assign(this.stageTableData[rowIndex - 1], row)
        Object.assign(this.stageTableData[rowIndex], temp) // 当前行数据
      }
      if (type === 'down') {
        const temp = cloneDeep(this.stageTableData[rowIndex + 1])
        const upSeq = temp.seq // 上一行数据 seq
        temp.seq = row.seq
        row.seq = upSeq
        Object.assign(this.stageTableData[rowIndex + 1], row)
        Object.assign(this.stageTableData[rowIndex], temp)
      }
    },
    selectChange (value, option) {
      // this.stageTableData
      for (const stage of this.stages) {
        delete stage['disabled']
      }
      for (const item of this.stageTableData) {
        for (const stage of this.stages) {
          if (item.stageKey === stage.value) {
            this.$set(stage, 'disabled', true)
          }
        }
      }
    },
    // 删除
    handleDel (row, rowIndex) {
      this.spinning = true
      this.confirmLoading = true
      row.expiryDate = moment(row.expiryDate)
      if (row.id) {
        const postData = cloneDeep(row)
        this.$http.post('/rsProduct/delStages', postData).then((res) => {
          if (res.data && res.success) {
            this.hanldeTableData(row)
            this.$emit('refreshStage', this.stageTableData)
            this.spinning = false
            this.confirmLoading = false
          } else {
            this.$message.error(res.errorMessage)
            this.spinning = false
            this.confirmLoading = false
          }
        }).catch((error) => {
          this.$message.error(error.message)
          this.spinning = false
          this.confirmLoading = false
        })
      } else {
        this.hanldeTableData(row)
        this.spinning = false
        this.confirmLoading = false
      }
    },
    // 删除stageTableData的Item
    hanldeTableData (row) {
      let index = -1
      this.stageTableData.forEach((item, i) => {
        if (row.stageKey === item.stageKey) {
          index = i
        }
      })
      this.stageTableData.splice(index, 1)
      for (const stage of this.stages) {
        if (stage.value === row.stageKey) {
          delete stage['disabled']
        }
      }
    }
  }
}
</script>
<style lang='less' scoped>
  .customAddBtn {
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
  .btn {
    color: #000;
    padding: 0 3px;
    // border-radius: 6px;
    // background-color: #1890ff;
  }
  .btn:hover {
    // color:#fff;
    // background-color: #40A9FF;
    cursor: pointer;
  }
</style>
