<!--
 * @Author: ldx
 * @Date: 2020-12-12 09:27:35
 * @LastEditTime: 2021-02-03 17:29:01
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\docTemplate\Templates\modules\ImportReceptionModal.vue
-->
<template>
  <a-modal
    :zIndex="99999"
    :width="400"
    :visible="visible"
    centered
    title="引入会议纪要"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :getContainer="getContainer"
    :confirmLoading="confirmLoading">
    <a-spin :spinning="spinning">
      <template v-if="dataArr.length">
        <a-checkbox-group v-model="chooseValue">
          <a-checkbox v-for="(item,index) in dataArr" :key="index" :value="item.id" :style="checkboxStyle">
            {{ `${stageMap[item.stageKey]}-${item.docFileName}` }}
          </a-checkbox>
        </a-checkbox-group>
      </template>
      <template v-else>
        当前不存在其他会议纪要
      </template>
    </a-spin>
  </a-modal>
</template>
<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
const stageMap = {
  '100': '规划阶段',
  '200': '研究阶段',
  '300': '设计开发',
  '400': '设计验证',
  '500': '实验验证',
  '600': '试验证',
  '700': '试制',
  '800': '项目验收',
  '900': '项目收尾' }
export default {
  data () {
    return {
      stageMap,
      domName,
      visible: false,
      confirmLoading: false,
      chooseValue: undefined,
      projectId: undefined,
      year: undefined,
      dataArr: [],
      checkboxStyle: {
        display: 'block',
        height: '30px',
        lineHeight: '30px',
        marginLeft: '18px'
      },
      fileDate: null,
      spinning: false
    }
  },
  methods: {
    open (fileDate, year, projectId) {
      this.fileDate = fileDate
      this.year = year
      this.projectId = projectId
      this.loadData()
      this.chooseValue = undefined
      this.visible = true
    },
    getContainer () {
      return popupContainer(this.domName)
    },
    handleSubmit () {
      this.confirmLoading = true
      if (this.chooseValue !== undefined && this.chooseValue.length !== 0) {
        this.$http.get('projectDocFileData/getMeetingData', { params: { pDocFileIds: this.chooseValue } }).then(res => {
          if (res.success) {
            if (res.data !== null && JSON.stringify(res.data) !== '{}') {
              this.$emit('importMeetingSummary', res.data)
              this.$message.success('会议纪要引入成功')
            } else {
              this.$message.warning('会议纪要内容为空，请重新选择')
            }
          }
        })
      } else {
        this.$message.warning('未选择会议纪要')
      }
      this.confirmLoading = false
      this.visible = false
    },
    loadData () {
      this.spinning = true
      this.$http.get('projectDocFile/getMeeting', { params: { projectId: this.projectId, year: this.year, fileDate: this.fileDate } })
        .then(res => {
          if (res.success && res.data) {
            this.dataArr = res.data
          }
        }).finally(() => {
          this.spinning = false
        })
    }
  }
}
</script>
