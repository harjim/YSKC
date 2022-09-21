<!--
 * @Author: ldx
 * @Date: 2020-12-15 13:56:50
 * @LastEditTime: 2022-02-22 14:24:03
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \MS-VUE\src\components\Flow\addNode.vue
-->
<template>
  <div class="add-node-btn-box">
    <div class="add-node-btn">
      <div>
        <a-popover placement="rightTop" v-model="visible" trigger="click">
          <template slot="content">
            <div class="add-node-popover-body">
              <a class="add-node-popover-item approver" @click="addType(1)">
                <div class="item-wrapper">
                  <span class="iconfont">&#xe66e;</span>
                </div>
                <p>审批人</p>
              </a>
              <a class="add-node-popover-item notifier" @click="addType(2)">
                <div class="item-wrapper">
                  <span class="iconfont">&#xe605;</span>
                </div>
                <p>抄送人</p>
              </a>
              <a class="add-node-popover-item condition" @click="addType(3)">
                <div class="item-wrapper">
                  <span class="iconfont">&#xe99d;</span>
                </div>
                <p>条件分支</p>
              </a>
              <a class="add-node-popover-item parallelBranches" @click="addType(4)">
                <div class="item-wrapper">
                  <span class="iconfont">&#xe852;</span>
                  <!-- <span class="iconfont"><img src="@/assets/icons/ParallelBranches.svg" style="width: 55%;"></span> -->
                </div>
                <p>并行分支</p>
              </a>
            </div>
          </template>
          <button class="btn" type="button" >
            <a-icon style="color: #fff;" type="plus" />
          <!-- <span class="iconfont"></span> -->
          </button>
        </a-popover>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'AddNode',
  // eslint-disable-next-line vue/require-prop-types
  props: ['childNodeP', 'childNode'],
  data () {
    return {
      visible: false
    }
  },
  methods: {
    addType (type) {
      this.visible = false
      let date
      switch (type) {
        case 1:
          date = {
            'nodeName': '审核节点',
            'error': true,
            'type': 1,
            'settype': 1,
            'selectMode': 0,
            'selectRange': 0,
            'directorLevel': 1,
            'replaceByUp': 0,
            'examineMode': 1,
            'noHanderAction': 1,
            'examineEndDirectorLevel': 0,
            'childNode': this.childNodeP,
            'nodeUserList': [],
            'skip': true
          }
          break
        case 2:
          date = {
            'nodeName': '抄送节点',
            'type': 2,
            'ccSelfSelectFlag': 1,
            'childNode': this.childNodeP,
            'nodeUserList': []
          }
          break
        case 3:
          date = {
            'nodeName': '大分支节点',
            'error': true,
            'type': 999,
            'childNode': null,
            'conditionNodes': [{
              'nodeName': '条件1',
              'error': true,
              'type': 3,
              'priorityLevel': 1,
              'seq': 0,
              'conditionList': [],
              'nodeUserList': [],
              'childNode': this.childNodeP
            }, {
              'nodeName': '条件2',
              'error': true,
              'type': 3,
              'priorityLevel': 2,
              'seq': 1,
              'conditionList': [],
              'nodeUserList': [],
              'childNode': null
            }]
          }
          break
        case 4:
          // 添加并行节点
          date = {
            'nodeName': '并行节点',
            'error': false,
            'type': 4,
            'childNode': null,
            'conditionNodes': [{
              'nodeName': '分支1',
              'error': false,
              'type': 4,
              'priorityLevel': 1,
              'seq': 0,
              'conditionList': [],
              'nodeUserList': [],
              'childNode': this.childNodeP
            }, {
              'nodeName': '分支2',
              'error': false,
              'type': 4,
              'priorityLevel': 2,
              'seq': 1,
              'conditionList': [],
              'nodeUserList': [],
              'childNode': null
            }]
          }
          break
        default:
          break
      }
      this.$emit('update:childNodeP', date)
    }
  }
}
</script>
