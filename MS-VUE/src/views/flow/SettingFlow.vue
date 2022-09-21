<!--
 * @Author: ldx
 * @Date: 2020-12-16 10:41:05
 * @LastEditTime: 2022-03-09 10:53:39
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \MS-VUE\src\views\flow\SettingFlow.vue
-->
<template>
  <div id="flow-wrap" style="height: 100%;">
    <a-spin style="height: 100%;" :spinning="spinning">
      <div class="fd-nav">
        <div class="fd-nav-left">
          <div class="fd-nav-back" @click="switchSetting"><i class="cIcon cIcon-left"></i></div>
          <div class="fd-nav-title">{{ flowName }}</div>
        </div>
        <div class="fd-nav-right">
          <button type="button" class="ant-btn button-publish" @click="comfirmSave"><span>发 布</span></button>
        </div>
      </div>
      <section class="dingflow-design">
        <div class="zoom">
          <div :class="'zoom-out'+ (nowVal==50?' disabled':'')" @click="zoomSize(1)"></div>
          <span>{{ nowVal }}%</span>
          <div :class="'zoom-in'+ (nowVal==200?' disabled':'')" @click="zoomSize(2)"></div>
        </div>
        <div class="box-scale" id="box-scale" :style="'transform: scale('+nowVal/100+'); transform-origin: 50% 0px 0px;'">
          <nodeWrap
            :nodeConfig.sync="nodeConfig"
            :flowPermission.sync="flowPermission"
            :isTried.sync="isTried"
            :directorMaxLevel="directorMaxLevel"
            :deptTree="deptTree"
            :deptMap="deptMap"
            :tableId="tableId">
          </nodeWrap>
          <div class="end-node">
            <div class="end-node-circle"></div>
            <div class="end-node-text">流程结束</div>
          </div>
        </div>
      </section>
      <a-modal v-model="isVisible" title="提示" @ok="handleOk">
        <i class="cIcon cIcon-close-circle" style="color: #f00; font-size: 22px;margin-right: 16px;"></i>
        <span class="ant-confirm-title">当前无法发布</span>
        <div class="ant-confirm-content">
          <div>
            <p class="error-modal-desc">以下内容不完善，需进行修改</p>
            <div class="error-modal-list">
              <div class="error-modal-item" v-for="(item,index) in errorList" :key="index">
                <div class="error-modal-item-label">{{ index +1 }}.流程设计</div>
                <div class="error-modal-item-content">{{ item.name }} {{ item.type === '条件' ? '未选择' : '未设置' }}{{ item.type }}</div>
              </div>
            </div>
          </div>
        </div>
      </a-modal>
    </a-spin>
  </div>
</template>

<script>
import nodeWrap from '@/components/Flow/nodeWrap'
import moment from 'moment'
const flowPermission = []
const directorMaxLevel = 4
const tableId = 1
export default {
  name: 'SettingFlow',
  components: {
    nodeWrap
  },
  props: {
    flowId: {
      type: Number,
      default: -1
    },
    flowName: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      spinning: false,
      nodeConfig: {},
      flowPermission,
      isTried: false,
      directorMaxLevel,
      tableId,
      nowVal: 100,
      errorList: [],
      isVisible: false,
      deptTree: [],
      deptMap: {}
    }
  },
  mounted () {
    this.initFlowData()
    this.$http.get('/dept/tree').then(res => {
      this.deptTree = res.data
      this.getDeptMap(res.data)
    })
  },
  methods: {
    moment,
    getDeptMap (dataAry) {
      if (Array.isArray(dataAry)) {
        dataAry.forEach(item => {
          this.deptMap[item.value] = item.title
          if (item.children && item.children.length) {
            this.getDeptMap(item.children)
          }
        })
      }
    },
    zoomSize (type) {
      if (type === 1) {
        if (this.nowVal === 50) {
          return
        }
        this.nowVal -= 10
      } else {
        if (this.nowVal === 200) {
          return
        }
        this.nowVal += 10
      }
    },
    switchSetting () {
      this.$emit('switchPage')
    },
    handleOk () {
      this.isVisible = false
    },
    // 保存节点数据
    saveFlow () {
      const postData = []
      if (!this.nodeConfig.childNode) { // 删除了全部节点
        return
      }
      this.analysisFlowData(this.nodeConfig.childNode, postData, true)
      const nodeConfig = {
        type: 0,
        nodeName: '开始'
      }
      this.spinning = true
      this.$http.post('/flow/saveFlow', postData).then((res) => {
        if (res.data && res.success) {
          this.nodeConfig = undefined
          this.nodeConfig = this.recoverTreeData(res.data, nodeConfig, 0, res.data.length - 1)
          this.$message.success('操作成功')
          this.errorList = []
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
        this.spinning = false
      })
    },
    // 二次确认保存
    comfirmSave () {
      this.errorList = []
      this.reErr(this.nodeConfig)
      if (this.errorList.length) {
        this.isVisible = true
        return
      }
      const self = this
      this.$confirm({
        title: '提示',
        content: '您确定要发布吗？',
        okText: '确认',
        cancelText: '取消',
        onOk () {
          self.saveFlow()
        }
      })
    },
    // 初始化节点数据
    initFlowData () {
      this.spinning = true
      const nodeConfig = {
        type: 0,
        nodeName: '开始',
        childNode: null
      }
      this.$http.get('/flow/getFlowInfo', { params: { flowId: this.flowId } }).then((res) => {
        if (res.data && res.success) {
          if (res.data.length) {
            this.nodeConfig = this.recoverTreeData(res.data, nodeConfig, 0, res.data.length - 1)
            this.isTried = true
            this.spinning = false
          } else {
            this.nodeConfig = nodeConfig
            this.isTried = true
            this.spinning = false
          }
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinning = false
      })
    },
    // 解析节点数据-用于保存
    analysisFlowData (childNode, returnData, isAddFlowId) {
      if (!Array.isArray(childNode)) {
        const nodeObj = {}
        nodeObj['type'] = [999].includes(childNode.type) ? 3 : childNode.type
        nodeObj['nodeName'] = childNode.nodeName
        nodeObj['nodeNumber'] = childNode.nodeNumber
        nodeObj['nodeExpired'] = childNode.nodeExpired
        nodeObj['flowId'] = childNode.flowId ? childNode.flowId : this.flowId
        nodeObj['auditType'] = childNode.auditType ? childNode.auditType : 0
        nodeObj['userType'] = childNode.userType ? childNode.userType : 0
        nodeObj['userList'] = childNode.userList && childNode.userList.length ? childNode.userList : null
        nodeObj['nodeId'] = childNode.nodeId
        nodeObj['skip'] = Number(childNode.skip)
        nodeObj['remark'] = childNode.remark
        returnData.push(nodeObj)
        if (childNode.childNode) {
          this.analysisFlowData(childNode.childNode, returnData, isAddFlowId)
        }
        if (childNode.conditionNodes && [999, 3, 4].includes(childNode.type)) {
          const ary = []
          nodeObj['list'] = ary
          this.analysisFlowData(childNode.conditionNodes, ary, false)
        }
      } else {
        childNode.forEach((item, index) => {
          if (item.childNode) {
            const ary = []
            returnData.push({ seq: index, nodeId: item.nodeId, branchId: item.branchId, flowId: item.flowId, condition: item.condition ? item.condition.join(',') : undefined, newFlow: ary, type: item.type, newFlowName: item.nodeName, nodeName: item.nodeName })
            this.analysisFlowData(item.childNode, ary, isAddFlowId)
          } else {
            returnData.push({ seq: index, nodeId: item.nodeId, branchId: item.branchId, flowId: item.flowId, condition: item.condition ? item.condition.join(',') : undefined, type: item.type, newFlowName: item.nodeName, nodeName: item.nodeName, newFlow: undefined })
          }
        })
      }
    },
    // 还原树状结构-用于显示
    recoverTreeData (data, nodeConfig, index, maxIndex) {
      const item = data[index]
      item['skip'] = !!item['skip']
      if (item && item.type) {
        const childNode = { }
        Object.assign(childNode, item)
        childNode['type'] = item.nodeName === '大分支节点' && item.type === 3 ? 999 : item.type
        nodeConfig['childNode'] = childNode
        if (item && item.list) {
          const conditionNodes = []
          item.list.forEach(l => {
            const node = { }
            Object.assign(node, l)
            node['type'] = item.type
            if (node.condition) {
              node['condition'] = node.condition.split(',')
            }
            node['newFlowName'] = node['newFlowName'] || node['nodeName'] || (item.type === 3 ? '条件' + (l.seq * 1 + 1) : '分支' + (l.seq * 1 + 1))
            node['nodeName'] = node['newFlowName']
            if (l.newFlow && l.newFlow.length) {
              conditionNodes.push(this.recoverTreeData(l.newFlow, node, 0, l.newFlow.length - 1))
            } else {
              conditionNodes.push(node)
            }
          })
          childNode['conditionNodes'] = conditionNodes
        }
        if (index < maxIndex) {
          this.recoverTreeData(data, childNode, ++index, maxIndex)
        }
      }
      return nodeConfig
    },
    // 递归列出错误信息
    reErr (data) {
      if (data.childNode) {
        if (data.childNode.type === 1) { // 审批人
          if (data.childNode.error) {
            this.errorList.push({ name: data.childNode.nodeName, type: '审核人' })
          }
          this.reErr(data.childNode)
        } else if (data.childNode.type === 2) {
          if (data.childNode.error) {
            this.errorList.push({ name: data.childNode.nodeName, type: '抄送人' })
          }
          this.reErr(data.childNode)
        } else if (data.childNode.type === 999) {
          this.reErr(data.childNode)
          for (var i = 0; i < data.childNode.conditionNodes.length; i++) {
            if (data.childNode.conditionNodes[i].error) {
              this.errorList.push({ name: data.childNode.conditionNodes[i].nodeName, type: '条件' })
            }
            // if (!(data.childNode.conditionNodes[i].childNode && !data.childNode.conditionNodes[i].childNode.length)) {
            //   this.errorList.push({ name: data.childNode.conditionNodes[i].nodeName, type: '流程' })
            // }
            this.reErr(data.childNode.conditionNodes[i])
          }
        } else if (data.childNode.type === 4) {
          // this.reErr(data.childNode)
          // for (let i = 0; i < data.childNode.conditionNodes.length; i++) {
          //   if (!(data.childNode.conditionNodes[i].childNode && !data.childNode.conditionNodes[i].childNode.length)) {
          //     this.errorList.push({ name: data.childNode.conditionNodes[i].nodeName, type: '流程' })
          //   }
          // }
        }
      } else {
        data.childNode = null
      }
    }
  }
}
</script>

<style lang="less" scoped>
@import "./css/workflow.css";
#flow-wrap /deep/ .ant-spin-container {
  height: 100%;
}
</style>
