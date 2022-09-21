<template>
  <div>
    <div class="node-wrap" v-if="![999, 4].includes(nodeConfig.type)">
      <div class="node-wrap-box" :class="(nodeConfig.type==0 ? 'start-node ':'')+(isTried&&nodeConfig.error?'active error':'')">
        <div>
          <!-- 节点标题  -->
          <div class="title" :style="'background: rgb('+ ['87, 106, 149','255, 148, 62','50, 150, 250'][nodeConfig.type] +');'">
            <span class="iconfont" v-show="nodeConfig.type==1"></span>
            <span class="iconfont" v-show="nodeConfig.type==2"></span>
            <span v-if="nodeConfig.type==0">{{ nodeConfig.nodeName }}</span>
            <input
              type="text"
              class="ant-input editable-title-input"
              v-if="nodeConfig.type!=0 && isInput "
              @blur="blurEvent()"
              @focus="$event.currentTarget.select()"
              v-focus
              v-model="nodeConfig.nodeName"
              :placeholder="placeholderList[nodeConfig.type]">
            <span class="editable-title" @click="clickEvent()" v-if="nodeConfig.type!=0 &&!isInput">{{ nodeConfig.nodeName }}{{ nodeConfig.nodeNumber == null ? '' : `（${nodeConfig.nodeNumber}）` }}</span>
            <i class="cIcon cIcon-close close" v-if="nodeConfig.type!=0 " @click="delNode()"></i>
          </div>
          <!-- 节点内容区 -->
          <div class="content" @click="setPerson">
            <div class="text" v-if="nodeConfig.type==0">{{ arrToStr(flowPermission)?arrToStr(flowPermission):'发起人' }}</div>
            <div class="text" v-if="nodeConfig.type==1">
              <span class="placeholder" v-if="!setApproverStr(nodeConfig)">请选择{{ placeholderList[nodeConfig.type] }}</span>
              {{ setApproverStr(nodeConfig) }}
            </div>
            <div class="text" v-if="nodeConfig.type==2">
              <span class="placeholder" v-if="!copyerStr(nodeConfig)">请选择{{ placeholderList[nodeConfig.type] }}</span>
              {{ copyerStr(nodeConfig) }}
            </div>
            <i class="anticon anticon-right arrow"></i>
          </div>
          <div class="error_tip" v-if="isTried && nodeConfig.error">
            <i class="cIcon cIcon-exclamation-circle" style="color: rgb(242, 86, 67);"></i>
          </div>
        </div>
      </div>
      <addNode :childNode="nodeConfig" :childNodeP.sync="nodeConfig.childNode"></addNode>
    </div>
    <div class="branch-wrap" v-if="[999, 4].includes(nodeConfig.type)">
      <div class="branch-box-wrap ">
        <div class="branch-box">
          <button class="add-branch" @click="addTerm">{{ nodeConfig.type === 999 ? '添加条件' : '添加分支' }}</button>
          <!-- <span v-else class="span-branch">条件分支</span> -->
          <div class="col-box" v-for="(item,index) in nodeConfig.conditionNodes" :key="index">
            <div class="condition-node">
              <div class="condition-node-box">
                <div class="auto-judge" :class="item.error?'error active':''">
                  <!-- 左移按钮 -->
                  <!-- TODO: 原 !nodeConfig.nodeId， 未知 nodeId 含义 -->
                  <div class="sort-left" v-if="index!=0 && !nodeConfig.nodeId " @click="arrTransfer(index,-1)">&lt;</div>
                  <!-- title -->
                  <div :class="type3 ? 'title-wrapper-3' : 'title-wrapper-4'" class="title-wrapper">
                    <input
                      type="text"
                      class="ant-input editable-title-input"
                      v-if="isInputList[index]"
                      @blur="blurEvent(index)"
                      @focus="$event.currentTarget.select()"
                      v-focus
                      v-model="item.nodeName">
                    <!-- <span @click="clickEvent(index)" v-if="!isInputList[index]">{{ nodeConfig.type === 999 ? '条件' : '分支' }}{{ item.seq+1 }}</span> -->
                    <!-- :class="[item.childNode && item.childNode.length ? 'editable-title' : '', type3 ? 'editable-title-3' : 'editable-title-4']" -->
                    <span
                      @click="clickEvent(index, !!item.childNode)"
                      :class="{'editable-title': item.childNode, 'editable-title-3': type3, 'editable-title-4': !type3}"
                      v-if="!isInputList[index]">{{ item.nodeName }}</span>
                    <!-- 删除按钮 -->
                    <i class="cIcon cIcon-close close" @click="delTerm(index)"></i>
                  </div>
                  <!-- 右移按钮 -->
                  <div
                    class="sort-right"
                    v-if="index!=nodeConfig.conditionNodes.length-1 && !nodeConfig.nodeId"
                    @click="arrTransfer(index)">&gt;</div>
                  <!-- 内容 -->
                  <div class="content" @click="setPerson(index, true)">
                    {{ nodeConfig.conditionNodes[index].condition ? setConditionStr(nodeConfig.conditionNodes,index) : nodeConfig.type === 999 ? '请设置条件' : '' }}
                  </div>
                  <!-- 错误提示图标 -->
                  <!--  || !(nodeConfig.conditionNodes[index].childNode && !nodeConfig.conditionNodes[index].childNode.length && nodeConfig.type === 4) 并行节点不为空 -->
                  <div class="error_tip" v-if="nodeConfig.conditionNodes[index].error">
                    <i class="cIcon cIcon-exclamation-circle" style="color: rgb(242, 86, 67);"></i>
                  </div>
                  <!-- <span v-if="!nodeConfig.conditionNodes[index].childNode && nodeConfig.type === 4">当前分支无流程，发布后该分支将不会保存！</span> -->
                </div>
                <addNode :childNode="nodeConfig" :childNodeP.sync="item.childNode"></addNode>
              </div>
            </div>
            <nodeWrap
              v-if="item.childNode && item.childNode"
              :nodeConfig.sync="item.childNode"
              :tableId="tableId"
              :isTried.sync="isTried"
              :directorMaxLevel="directorMaxLevel"
              :deptTree="deptTree"
              :deptMap="deptMap"
            ></nodeWrap>
            <div class="top-left-cover-line" v-if="index==0"></div>
            <div class="bottom-left-cover-line" v-if="index==0"></div>
            <div class="top-right-cover-line" v-if="index==nodeConfig.conditionNodes.length-1"></div>
            <div class="bottom-right-cover-line" v-if="index==nodeConfig.conditionNodes.length-1"></div>
          </div>
        </div>
        <addNode :childNode="nodeConfig" :childNodeP.sync="nodeConfig.childNode"></addNode>
      </div>
    </div>
    <nodeWrap
      v-if="nodeConfig.childNode && nodeConfig.childNode"
      :nodeConfig.sync="nodeConfig.childNode"
      :tableId="tableId"
      :isTried.sync="isTried"
      :directorMaxLevel="directorMaxLevel"
      :deptTree="deptTree"
      :deptMap="deptMap"
    >
    </nodeWrap>
    <!-- 发起人抽屉 -->
    <!-- 审批人抽屉 -->
    <a-drawer
      title="审批设置"
      :visible.sync="approverDrawer"
      placement="right"
      width="550px"
      @close="() => {approverDrawer = false}">
      <a-form :form="form" layout="inline">
        <a-form-item label="名称" required>
          <a-input
            v-decorator="[ 'nodeName', { rules: [{ required: true , message: '请输入名称' }] }]"
            placeholder="请输入名称"
            style="width: 110px"
          ></a-input>
        </a-form-item>
        <a-form-item label="编号">
          <a-input-number v-model="nodeConfig.nodeNumber" :min="0" placeholder="请输入编号" style="width: 100px"></a-input-number>
        </a-form-item>
        <a-form-item label="期限(天)" style="margin-right: 0px;">
          <a-input-number v-model="nodeConfig.nodeExpired" :min="0" placeholder="请输入期限" style="width: 100px"></a-input-number>
        </a-form-item>
      </a-form>
      <a-divider></a-divider>
      <a-radio-group v-model="auditRadioVal" :options="auditOptions" @change="auditRadioChange" />
      <a-divider></a-divider>
      <div v-if="auditRadioVal !== 2" style="margin: 24px 0;">
        <div style="margin-bottom: 12px;">多人审批时采用的审批方式:</div>
        <a-radio-group v-model="auditTypeVal" :options="auditTypeOptions" />
      </div>
      <div v-if="auditRadioVal === 0">
        <a-form>
          <a-form-item label="指定成员" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <search-select
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              :multiple="true"
              placeholder="请选择添加人员"
              style="width: 100%"
              v-model="postIds"
            />
          </a-form-item>
        </a-form>
      </div>
      <div v-if="auditRadioVal === 2">
        <div style="margin: 24px 0;">
          <span >发起人的：
            <a-select :options="selectOptions" :default-value="selectVal" style="width: 120px" @change="handleSelectChange"></a-select>
            <!-- <span style="color:red;">直接主管</span> -->
          </span>
        </div>
        <a-radio-group v-model="auditTypeVal" :options="auditTypeOptions" />
      </div>
      <div v-if="auditRadioVal === 1">
        <a-form>
          <a-form-item label="指定角色" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <search-select
              url="/flow/getRoleList"
              searchField="roleName"
              sTitle="roleName"
              :multiple="true"
              placeholder="请选择添加角色"
              style="width: 100%"
              v-model="postIds"
            />
          </a-form-item>
        </a-form>
      </div>
      <div v-if="auditRadioVal === 3">
        <a-form>
          <a-form-item label="指定角色(同时是主管线上的主管)" :labelCol="{span:11}" :wrapperCol="{span:13}">
            <search-select
              url="/flow/getRoleList"
              searchField="roleName"
              sTitle="roleName"
              :multiple="true"
              placeholder="请选择添加角色"
              style="width: 100%"
              v-model="postIds"
            />
          </a-form-item>
          <a-form-item label="不超过发起人向上的主管" :labelCol="{span:11}" :wrapperCol="{span:13}">
            <a-select
              :options="selectOptions"
              :default-value="level"
              @change="(v,o)=>level = v"
              style="width: 100%"></a-select>
          </a-form-item>
        </a-form>
      </div>
      <div v-if="auditRadioVal === 4">
        <a-form layout="inline">
          <a-form-item label="备注">
            <a-input v-model="nodeConfig.remark" placeholder="请输入备注"></a-input>
          </a-form-item>
        </a-form>
      </div>
      <div style="margin: 24px 0;">重复审批自动跳过：<a-checkbox v-model="nodeConfig.skip"></a-checkbox></div>
      <div
        :style="{
          position: 'absolute',
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e8e8e8',
          padding: '10px 16px',
          textAlign: 'right',
          left: 0,
          background: '#fff',
          borderRadius: '0 0 4px 4px',
        }"
      >
        <a-button style="marginRight: 8px" @click="() => {approverDrawer = false}">
          取消
        </a-button>
        <a-button type="primary" @click="saveApprover">
          {{ nodeConfig.nodeId ? '保存': '确定' }}
        </a-button>
      </div>
    </a-drawer>
    <!-- 抄送人抽屉 -->
    <a-drawer
      title="抄送人设置"
      :visible.sync="copyerDrawer"
      placement="right"
      width="550px"
      @close="() => { copyerDrawer = false }">
      <a-radio-group v-model="auditRadioVal" :options="auditOptions" @change="auditRadioChange" />
      <a-divider></a-divider>
      <div v-if="auditRadioVal === 0">
        <a-form>
          <a-form-item label="指定成员" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <search-select
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              :multiple="true"
              placeholder="请选择添加人员"
              style="width: 100%"
              v-model="postIds"
            />
          </a-form-item>
        </a-form>
      </div>
      <div v-if="auditRadioVal === 2">
        <div style="margin: 24px 0;">
          <span >发起人的：
            <a-select :options="selectOptions" :default-value="selectVal" style="width: 120px" @change="handleSelectChange"></a-select>
          </span>
        </div>
        <!-- <a-radio-group v-model="auditTypeVal" :options="auditTypeOptions" /> -->
      </div>
      <div v-if="auditRadioVal === 1">
        <a-form>
          <a-form-item label="指定角色" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <search-select
              url="/flow/getRoleList"
              searchField="roleName"
              sTitle="roleName"
              :multiple="true"
              placeholder="请选择添加角色"
              style="width: 100%"
              v-model="postIds"
            />
          </a-form-item>
        </a-form>
      </div>

      <div v-if="auditRadioVal === 3">
        <a-form>
          <a-form-item label="指定角色(同时是主管线上的主管)" :labelCol="{span:11}" :wrapperCol="{span:13}">
            <search-select
              url="/flow/getRoleList"
              searchField="roleName"
              sTitle="roleName"
              :multiple="true"
              placeholder="请选择添加角色"
              style="width: 100%"
              v-model="postIds"
            />
          </a-form-item>
          <a-form-item label="不超过发起人向上的主管" :labelCol="{span:11}" :wrapperCol="{span:13}">
            <a-select
              :options="selectOptions"
              :default-value="level"
              @change="(v,o)=>level = v"
              style="width: 100%"></a-select>
          </a-form-item>
        </a-form>
      </div>
      <div
        :style="{
          position: 'absolute',
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e8e8e8',
          padding: '10px 16px',
          textAlign: 'right',
          left: 0,
          background: '#fff',
          borderRadius: '0 0 4px 4px',
        }"
      >
        <a-button style="marginRight: 8px" @click="() => { copyerDrawer=false }">
          取消
        </a-button>
        <a-button type="primary" @click="saveCopyer">
          {{ nodeConfig.nodeId ? '保存': '确定' }}
        </a-button>
      </div>
    </a-drawer>
    <!-- 条件抽屉 -->
    <a-drawer
      :title="nodeConfig.type === 999 ? '条件设置' : '分支设置'"
      :visible.sync="conditionDrawer"
      placement="right"
      width="550px"
      @close="() => { conditionDrawer = false}">
      <div>
        <a-form>
          <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-tree-select
              showSearch
              :dropdownStyle="{ maxHeight: '250px', overflow: 'scroll' }"
              :treeData="deptTree"
              treeNodeFilterProp="title"
              placeholder="请选择部门"
              treeDefaultExpandAll
              multiple
              v-model="conditions[conditionIndex]"
            ></a-tree-select>
          </a-form-item>
        </a-form>
      </div>
      <div
        :style="{
          position: 'absolute',
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e8e8e8',
          padding: '10px 16px',
          textAlign: 'right',
          left: 0,
          background: '#fff',
          borderRadius: '0 0 4px 4px',
        }"
      >
        <a-button style="marginRight: 8px" @click="() => { conditionDrawer = false }">
          取消
        </a-button>
        <a-button type="primary" @click="saveCondition">
          确定
        </a-button>
      </div>
    </a-drawer>
  </div>
</template>
<script>
import addNode from './addNode'
// import { cloneDeep } from 'lodash'
import { SearchSelect } from '@/components/Selects'
import { selectLabel, selectOptions, auditOptions, auditTypeOptions } from './nodeWrapConfig'
export default {
  name: 'NodeWrap',
  // TODO sign: true可以修改 false 不可以修改
  // eslint-disable-next-line vue/require-prop-types
  props: ['nodeConfig', 'flowPermission', 'directorMaxLevel', 'isTried', 'tableId', 'deptTree', 'deptMap'],
  components: {
    addNode,
    SearchSelect
  },
  directives: {
    focus: {
    // 指令的定义
      inserted: function (el) {
        el.focus()
      }
    }
  },
  data () {
    return {
      level: 1,
      form: this.$form.createForm(this),
      placeholderList: ['发起人', '审核人', '抄送人'],
      auditRadioVal: 0, // 审核单选的值 0成员1角色2主管
      auditOptions, // 审核单选集合 0成员1角色2主管
      auditTypeOptions, // 主管审核模式 1：会签 0：或签
      auditTypeVal: 0, // 主管审核模式单选的值 1：会签 0：或签
      postIds: [], // 指定人员ID或角色ID
      selectOptions, // 主管级别集合
      conditionIndex: 0, // 条件索引
      conditions: {},
      selectLabel,
      selectVal: 1,
      labelCol: { sm: { span: 3 } },
      wrapperCol: { sm: { span: 21 } },
      isInputList: [],
      isInput: false,
      approverDrawer: false,
      approverVisible: false,
      approverRoleVisible: false,
      copyerDrawer: false,
      conditionDrawer: false,
      bPriorityLevel: '',
      conditionList: []
    }
  },
  mounted () {
    if (this.nodeConfig.type === 1) {
      this.setApproverStr(this.nodeConfig)
    } else if (this.nodeConfig.type === 2) {
      this.setApproverStr(this.nodeConfig)
    } else if ([999, 4].includes(this.nodeConfig.type)) {
      for (let i = 0; i < this.nodeConfig.conditionNodes.length; i++) {
      }
    }
  },
  computed: {
    type3 () {
      return [999, 3].includes(this.nodeConfig.type)
    }
  },
  methods: {
    handleSelectChange (value, option) {
      this.selectVal = value
    },
    // 切换类型
    auditRadioChange (e) {
      const userList = []
      let auditTypeVal = 0
      if (e.target.value === this.nodeConfig.userType) {
        auditTypeVal = this.nodeConfig.auditType
        if (this.nodeConfig.userList) {
          this.nodeConfig.userList.forEach(item => {
            if (e.target.value === 0) {
              userList.push({ id: item.dataId, deptId: null, realName: item.userName, userName: item.userName })
            } else if (e.target.value === 1) {
              userList.push({ id: item.dataId, roleName: item.userName })
            }
          })
        }
      }
      this.$nextTick(() => {
        this.auditTypeVal = auditTypeVal
        this.postIds = userList
      })
    },
    addOrChangeUser () {

    },
    clickEvent (index, change = true) {
      if (!change) {
        return
      }
      if (index || index === 0) {
        this.$set(this.isInputList, index, true)
      } else {
        this.isInput = true
      }
    },
    // 失焦事件
    blurEvent (index) {
      if (index || index === 0) {
        this.$set(this.isInputList, index, false)
        this.nodeConfig.conditionNodes[index].nodeName = this.nodeConfig.conditionNodes[index].nodeName ? this.nodeConfig.conditionNodes[index].nodeName : `${[3, 999].includes(this.nodeConfig.type) ? '条件' : '分支'}${index + 1}`
        this.nodeConfig.conditionNodes[index].newFlowName = this.nodeConfig.conditionNodes[index].nodeName
      } else {
        this.isInput = false
        this.nodeConfig.nodeName = this.nodeConfig.nodeName ? this.nodeConfig.nodeName : this.placeholderList[this.nodeConfig.type]
      }
    },
    saveCondition () {
      this.saveApprover()
      this.conditionDrawer = false
    },
    // 保存抄送人
    saveCopyer () {
      this.saveApprover()
      this.copyerDrawer = false
    },
    /**
     * @description:  显示抄送节点内容字符串
     * @param {*} nodeConfig
     * @return {*}
     */
    copyerStr (nodeConfig) {
      const { userList } = nodeConfig
      let str = ''
      if (nodeConfig.userList) {
        userList.forEach(user => {
          if (!user.userName && nodeConfig.type !== 999) {
            str = '直接主管,'
            return
          }
          str += user.userName + ','
        })
        this.$set(nodeConfig, 'error', false)
      }
      if (!nodeConfig.userList) {
        this.$set(nodeConfig, 'error', true)
      }
      str = str.substring(0, str.lastIndexOf(','))
      return str
    },
    /**
     * @description: 设置审批节点内容
     * @param {*} nodeConfig
     * @return {*} string
     */
    setApproverStr (nodeConfig) {
      const { userList } = nodeConfig
      let str = ''
      if (nodeConfig.userList) {
        userList.forEach(user => {
          if (nodeConfig.userType === 4) {
            str = nodeConfig.remark ? nodeConfig.remark + ',' : '表单用户,'
          } else {
            str += user.userName + ','
          }
        })
        this.$set(nodeConfig, 'error', false)
      }
      if (!nodeConfig.userList) {
        this.$set(nodeConfig, 'error', true)
      }
      str = str.substring(0, str.lastIndexOf(','))
      return str
    },
    /**
     * @description: 设置条件节点的显示内容
     * @param {*}
     * @return {*}
     */
    setConditionStr (conditionNodes, index) {
      let str = ''
      if (conditionNodes && conditionNodes[index].condition && Array.isArray(conditionNodes[index].condition) && conditionNodes[index].condition.length) {
        conditionNodes[index].condition.forEach(item => {
          str += this.deptMap[item] + ','
        })
        this.$set(conditionNodes[index], 'error', false)
      } else {
        this.$set(conditionNodes[index], 'error', true)
        return '请设置条件'
      }
      return str.substring(0, str.lastIndexOf(','))
    },
    /**
     * @description:  保存审批抽屉信息
     * @param {*}
     * @return {*}
     */
    saveApprover () {
      new Promise((resolve, reject) => {
        if (this.nodeConfig.type === 1) {
          this.form.validateFields((err) => {
            if (err) {
              return reject(new Error())
            }
            return resolve()
          })
        }
        resolve()
      }).then(() => {
        if (this.nodeConfig.type === 1) {
          this.nodeConfig.nodeName = this.form.getFieldValue('nodeName')
        }
        // const backupsData = cloneDeep(this.nodeConfig)
        const userList = []
        // userType 0人员 1角色 2主管
        this.nodeConfig['userType'] = this.auditRadioVal
        this.nodeConfig['auditType'] = this.auditTypeVal
        if (this.postIds && this.postIds.length) {
          this.postIds.forEach(item => {
            if (this.auditRadioVal === 2) {
              userList.push({ dataId: this.selectVal, userName: selectLabel[this.selectVal] })
            } else if (this.auditRadioVal === 3) {
              userList.push({ dataId: item.id, userName: item.realName ? item.realName : item.roleName, level: this.level })
            } else {
              userList.push({ dataId: item.id, userName: item.realName ? item.realName : item.roleName })
            }
          })
          this.nodeConfig['userList'] = userList
        } else {
          if (this.auditRadioVal === 4) {
            userList.push({ dataId: 0, userName: '表单用户' })
          } else {
            userList.push({ dataId: this.selectVal, userName: selectLabel[this.selectVal] })
          }
          this.nodeConfig['userList'] = userList
        }
        if ([999, 3].includes(this.nodeConfig.type)) {
          this.nodeConfig.conditionNodes[this.conditionIndex].condition = this.conditions[this.conditionIndex]
        }
        this.$emit('update:nodeConfig', this.nodeConfig)
        this.approverDrawer = false
        // if (this.nodeConfig.nodeId) {
        //   const postParam = {
        //     flowId: this.nodeConfig.flowId,
        //     nodeId: this.nodeConfig.nodeId,
        //     auditType: this.auditTypeVal,
        //     type: this.auditRadioVal,
        //     userIds: null
        //   }
        //   if (this.postIds && this.postIds.length && this.auditRadioVal !== 2) {
        //     const userIds = []
        //     this.postIds.forEach(item => {
        //       userIds.push(item.id)
        //     })
        //     postParam['userIds'] = userIds
        //   } else {
        //     postParam['userIds'] = [this.selectVal]
        //   }
        //   this.$http.post('/flow/setFlowUser', postParam).then((res) => {
        //     if (res.data && res.success) {
        //       this.$message.success('操作成功')
        //       this.$emit('update:nodeConfig', this.nodeConfig)
        //       this.approverDrawer = false
        //     } else {
        //       Object.assign(this.nodeConfig, backupsData)
        //       this.$message.error(res.errorMessage)
        //     }
        //   }).catch((error) => {
        //     Object.assign(this.nodeConfig, backupsData)
        //     this.$message.error(error.message)
        //   })
        // } else {
        //   this.$emit('update:nodeConfig', this.nodeConfig)
        //   this.approverDrawer = false
        // }
      }).catch(() => {})
    },
    arrToStr (arr) {
      if (arr) {
        return arr.map(item => { return item.name }).toString()
      }
    },
    // TODO 不能删除时：1不显示删除图标和禁用点击事件；2.不能显示发布按钮
    // TODO 有nodeId 不能编辑节点名称 此处仅删除非分支节点
    // 删除节点
    delNode () {
      switch (this.nodeConfig.type) {
        case 1:
        case 2:
          this.$emit('update:nodeConfig', this.nodeConfig.childNode)
          break
        case 3:
        case 4:
          // this.$emit('update:nodeConfig', this.nodeConfig.childNode)
          break
        default:
          break
      }
      // this.$emit('update:nodeConfig', this.nodeConfig.childNode || this.nodeConfig.conditionNodes)
    },
    // 添加条件
    addTerm () {
      const len = this.nodeConfig.conditionNodes.length
      const type3 = [3, 999].includes(this.nodeConfig.type)
      this.nodeConfig.conditionNodes.push({
        'type': this.nodeConfig.type !== 999 ? this.nodeConfig.type : 3,
        'seq': len,
        'nodeName': `${type3 ? '条件' : '分支'}${len + 1}`,
        'error': type3,
        'priorityLevel': len + 1,
        'conditionList': [],
        'nodeUserList': [],
        'condition': undefined,
        'childNode': null
      })
      // for (let i = 0; i < this.nodeConfig.conditionNodes.length; i++) {
      // }
      this.$emit('update:nodeConfig', this.nodeConfig)
    },
    // 删除条件
    // FIXME: 删除按钮
    delTerm (index) {
      // for (let i = 0; i < this.nodeConfig.conditionNodes.length; i++) {
      // }
      if (this.nodeConfig.conditionNodes.length === 1) {
        // 当前只有一个分支
        // if (this.nodeConfig.childNode) {
        if (this.nodeConfig.conditionNodes[0].childNode) {
          this.reData(this.nodeConfig.conditionNodes[0].childNode, this.nodeConfig.childNode)
        } else {
          this.nodeConfig.conditionNodes[0].childNode = this.nodeConfig.childNode
        }
        this.$emit('update:nodeConfig', this.nodeConfig.conditionNodes[0].childNode)
        // }
      } else {
        // 当前存在多个分支
        this.nodeConfig.conditionNodes.splice(index, 1)
        this.nodeConfig.conditionNodes.forEach((elem, index) => {
          if (elem.nodeName.includes('条件')) {
            elem.nodeName = '条件' + (index + 1)
          } else if (elem.nodeName.includes('分支')) {
            elem.nodeName = '分支' + (index + 1)
          }
        })
        this.$emit('update:nodeConfig', this.nodeConfig)
      }
    },
    reData (data, addData) {
      if (!data.childNode) {
        data.childNode = addData
      } else {
        this.reData(data.childNode, addData)
      }
    },
    // 打开抽屉设置人员
    setPerson (priorityLevel, isConditionNodes) {
      // const { type } = this.nodeConfig
      // if (type === 0) {
      // } else if (type === 1) { // 审核节点
      //   this.auditTypeVal = this.nodeConfig.auditType ? this.nodeConfig.auditType : 0
      //   this.auditRadioVal = this.nodeConfig.userType ? this.nodeConfig.userType : 0
      //   const userList = []
      //   if (this.nodeConfig.userList) {
      //     this.nodeConfig.userList.forEach(item => {
      //       // userType 0人员 1角色 2主管
      //       if (this.nodeConfig.userType === 0) {
      //         userList.push({ id: item.dataId, deptId: null, realName: item.userName, userName: item.userName })
      //       } else if (this.nodeConfig.userType === 1) {
      //         userList.push({ id: item.dataId, roleName: item.userName })
      //       } else if (this.nodeConfig.userType === 2) {
      //         userList.push({ id: item.dataId, userName: item.userName })
      //         this.selectVal = item.dataId
      //       } else if (this.nodeConfig.userType === 3) {
      //         this.level = item.level
      //         userList.push({ id: item.dataId, roleName: item.userName })
      //       } else if (this.nodeConfig.userType === 4) {
      //         userList.push({ id: item.dataId, roleName: item.userName })
      //       }
      //     })
      //   }
      //   this.approverDrawer = true
      //   this.$nextTick(() => {
      //     this.postIds = userList
      //     this.form.setFieldsValue({ nodeName: this.nodeConfig.nodeName })
      //   })
      // } else if (type === 2) {
      //   this.auditTypeVal = this.nodeConfig.auditType ? this.nodeConfig.auditType : 0
      //   this.auditRadioVal = this.nodeConfig.userType ? this.nodeConfig.userType : 0
      //   const userList = []
      //   if (this.nodeConfig.userList) {
      //     this.nodeConfig.userList.forEach(item => {
      //       // userType 0人员 1角色 2主管
      //       if (this.nodeConfig.userType === 0) {
      //         userList.push({ id: item.dataId, deptId: null, realName: item.userName, userName: item.userName })
      //       } else if (this.nodeConfig.userType === 1) {
      //         userList.push({ id: item.dataId, roleName: item.userName })
      //       } else if (this.nodeConfig.userType === 2) {
      //         userList.push({ id: item.dataId, userName: item.userName })
      //         this.selectVal = item.dataId
      //       } else if (this.nodeConfig.userType === 3) {
      //         this.level = item.level
      //         userList.push({ id: item.dataId, roleName: item.userName })
      //       } else if (this.nodeConfig.userType === 4) {
      //         userList.push({ id: item.dataId, roleName: item.userName })
      //       }
      //     })
      //   }
      //   this.copyerDrawer = true
      //   this.$nextTick(() => {
      //     this.postIds = userList
      //   })
      // } else {
      //   // 是否显示分支抽屉
      //   this.conditionDrawer = true
      //   this.conditionIndex = priorityLevel
      //   this.$nextTick(() => {
      //     this.$set(this.conditions, this.conditionIndex, this.nodeConfig.conditionNodes[priorityLevel].condition ? this.nodeConfig.conditionNodes[priorityLevel].condition : [])
      //   })
      // }
      const userList = []
      switch (this.nodeConfig.type) {
        case 1:
          this.auditTypeVal = this.nodeConfig.auditType ? this.nodeConfig.auditType : 0
          this.auditRadioVal = this.nodeConfig.userType ? this.nodeConfig.userType : 0

          if (this.nodeConfig.userList) {
            this.nodeConfig.userList.forEach(item => {
            // userType 0人员 1角色 2主管
              if (this.nodeConfig.userType === 0) {
                userList.push({ id: item.dataId, deptId: null, realName: item.userName, userName: item.userName })
              } else if (this.nodeConfig.userType === 1) {
                userList.push({ id: item.dataId, roleName: item.userName })
              } else if (this.nodeConfig.userType === 2) {
                userList.push({ id: item.dataId, userName: item.userName })
                this.selectVal = item.dataId
              } else if (this.nodeConfig.userType === 3) {
                this.level = item.level
                userList.push({ id: item.dataId, roleName: item.userName })
              } else if (this.nodeConfig.userType === 4) {
                userList.push({ id: item.dataId, roleName: item.userName })
              }
            })
          }
          this.approverDrawer = true
          this.$nextTick(() => {
            this.postIds = userList
            this.form.setFieldsValue({ nodeName: this.nodeConfig.nodeName })
          })
          break

        case 2:
          this.auditTypeVal = this.nodeConfig.auditType ? this.nodeConfig.auditType : 0
          this.auditRadioVal = this.nodeConfig.userType ? this.nodeConfig.userType : 0
          if (this.nodeConfig.userList) {
            this.nodeConfig.userList.forEach(item => {
            // userType 0人员 1角色 2主管
              if (this.nodeConfig.userType === 0) {
                userList.push({ id: item.dataId, deptId: null, realName: item.userName, userName: item.userName })
              } else if (this.nodeConfig.userType === 1) {
                userList.push({ id: item.dataId, roleName: item.userName })
              } else if (this.nodeConfig.userType === 2) {
                userList.push({ id: item.dataId, userName: item.userName })
                this.selectVal = item.dataId
              } else if (this.nodeConfig.userType === 3) {
                this.level = item.level
                userList.push({ id: item.dataId, roleName: item.userName })
              } else if (this.nodeConfig.userType === 4) {
                userList.push({ id: item.dataId, roleName: item.userName })
              }
            })
          }
          this.copyerDrawer = true
          this.$nextTick(() => {
            this.postIds = userList
          })
          break

        case 3:
        case 999:
        case 4:
          this.conditionDrawer = true
          this.conditionIndex = priorityLevel
          this.$nextTick(() => {
            this.$set(this.conditions, this.conditionIndex, this.nodeConfig.conditionNodes[priorityLevel].condition ? this.nodeConfig.conditionNodes[priorityLevel].condition : [])
          })
          break
        default:
          break
      }
    },
    // 条件交换位置
    arrTransfer (index, type = 1) { // 向左-1,向右1
      this.nodeConfig.conditionNodes[index] = this.nodeConfig.conditionNodes.splice(index + type, 1, this.nodeConfig.conditionNodes[index])[0]
      this.nodeConfig.conditionNodes.map((item, index) => {
        item.priorityLevel = index + 1
      })
      this.$emit('update:nodeConfig', this.nodeConfig)
    }
  }
}
</script>
<style scoped>
.demo-drawer__footer .ant-btn {
    float: right;
    margin-right: 10px;
}

.error_tip {
    position: absolute;
    top: 0px;
    right: 0px;
    transform: translate(150%, 0px);
    font-size: 24px;
}
.add-node-popover-body {
    display: flex;
}
.promoter_content {
    padding: 0 20px;
}
.condition_content .el-button,
.copyer_content .el-button,
.approver_self_select .el-button,
.promoter_content .el-button,
.approver_content .el-button {
    margin-bottom: 20px;
}
.promoter_content p {
    padding: 18px 0;
    font-size: 14px;
    line-height: 20px;
    color: #000000;
}
.promoter_person .el-dialog__body {
    padding: 10px 20px 14px 20px;
}
.person_body {
    border: 1px solid #f5f5f5;
    height: 500px;
}
.person_tree {
    padding: 10px 12px 0 8px;
    width: 280px;
    height: 100%;
    border-right: 1px solid #f5f5f5;
}
.person_tree input {
    padding-left: 22px;
    width: 210px;
    height: 30px;
    font-size: 12px;
    border-radius: 2px;
    border: 1px solid #d5dadf;
    background: url(~@/assets/images/list_search.png) no-repeat 10px center;
    background-size: 14px 14px;
    margin-bottom: 14px;
}
.tree_nav span {
    display: inline-block;
    padding-right: 10px;
    margin-right: 5px;
    max-width: 6em;
    color: #38adff;
    font-size: 12px;
    cursor: pointer;
    background: url(~@/assets/images/jiaojiao.png) no-repeat right center;
}
.tree_nav span:last-of-type {
    background: none;
}
.person_tree ul,
.has_selected ul {
    height: 420px;
    overflow-y: auto;
}
.person_tree li {
    padding: 5px 0;
}
.person_tree li i {
    float: right;
    padding-left: 24px;
    padding-right: 10px;
    color: #3195f8;
    font-size: 12px;
    cursor: pointer;
    background: url(~@/assets/images/next_level_active.png) no-repeat 10px
        center;
    border-left: 1px solid rgb(238, 238, 238);
}
.person_tree li a.active + i {
    color: rgb(197, 197, 197);
    background-image: url(~@/assets/images/next_level.png);
    pointer-events: none;
}
.person_tree img {
    width: 14px;
    vertical-align: middle;
    margin-right: 5px;
}
.has_selected {
    width: 276px;
    height: 100%;
    font-size: 12px;
}
.has_selected ul {
    height: 460px;
}
.has_selected p {
    padding-left: 19px;
    padding-right: 20px;
    line-height: 37px;
    border-bottom: 1px solid #f2f2f2;
}
.has_selected p a {
    float: right;
}
.has_selected ul li {
    margin: 11px 26px 13px 19px;
    line-height: 17px;
}
.has_selected li span {
    vertical-align: middle;
}
.has_selected li img:first-of-type {
    width: 14px;
    vertical-align: middle;
    margin-right: 5px;
}
.has_selected li img:last-of-type {
    float: right;
    margin-top: 2px;
    width: 14px;
}
el-radio-group {
    padding: 20px 0;
}
.approver_content {
    padding-bottom: 10px;
    border-bottom: 1px solid #f2f2f2;
}
.approver_content .el-radio,
.approver_some .el-radio,
.approver_self_select .el-radio {
    width: 27%;
    margin-bottom: 20px;
}
.copyer_content .el-checkbox {
    margin-bottom: 20px;
}
.el-checkbox__label {
    font-size: 12px;
}
.condition_content,
.copyer_content,
.approver_self_select,
.approver_manager,
.approver_content,
.approver_some {
    padding: 20px 20px 0;
}
.approver_manager p:first-of-type,
.approver_some p {
    line-height: 19px;
    font-size: 14px;
    margin-bottom: 14px;
}

.approver_manager p {
    line-height: 32px;
}
.approver_manager select {
    width: 420px;
    height: 32px;
    background: rgba(255, 255, 255, 1);
    border-radius: 4px;
    border: 1px solid rgba(217, 217, 217, 1);
}
.approver_manager p.tip {
    margin: 10px 0 22px 0;
    font-size: 12px;
    line-height: 16px;
    color: #f8642d;
}
.approver_self {
    padding: 28px 20px;
}
.selected_list {
    margin-bottom: 20px;
    line-height: 30px;
}
.selected_list span {
    margin-right: 10px;
    padding: 3px 6px 3px 9px;
    line-height: 12px;
    white-space: nowrap;
    border-radius: 2px;
    border: 1px solid rgba(220, 220, 220, 1);
}
.selected_list img {
    margin-left: 5px;
    width: 7px;
    height: 7px;
    cursor: pointer;
}
.approver_self_select h3 {
    margin: 5px 0 20px;
    font-size: 14px;
    font-weight: bold;
    line-height: 19px;
}
.condition_copyer .el-drawer__body select {
    position: absolute;
    top: 11px;
    right: 30px;
    width: 100px;
    height: 32px;
    background: rgba(255, 255, 255, 1);
    border-radius: 4px;
    border: 1px solid rgba(217, 217, 217, 1);
}
.condition_content p.tip {
    margin: 20px 0;
    width: 510px;
    text-indent: 17px;
    line-height: 45px;
    background: rgba(241, 249, 255, 1);
    border: 1px solid rgba(64, 163, 247, 1);
    color: #46a6fe;
    font-size: 14px;
}
.condition_content ul {
    max-height: 500px;
    overflow-y: scroll;
    margin-bottom: 20px;
}
.condition_content li > span {
    float: left;
    margin-right: 8px;
    width: 70px;
    line-height: 32px;
    text-align: right;
}
.condition_content li > div {
    display: inline-block;
    width: 370px;
}
.condition_content li:not(:last-child) > div > p {
    margin-bottom: 20px;
}
.condition_content li > div > p:not(:last-child) {
    margin-bottom: 10px;
}
.condition_content li > a {
    float: right;
    margin-right: 10px;
    margin-top: 7px;
}
.condition_content li select,
.condition_content li input {
    width: 100%;
    height: 32px;
    background: rgba(255, 255, 255, 1);
    border-radius: 4px;
    border: 1px solid rgba(217, 217, 217, 1);
}
.condition_content li select + input {
    width: 260px;
}
.condition_content li select {
    margin-right: 10px;
    width: 100px;
}
.condition_content li p.selected_list {
    padding-left: 10px;
    border-radius: 4px;
    min-height: 32px;
    border: 1px solid rgba(217, 217, 217, 1);
}
.condition_content li p.check_box {
    line-height: 32px;
}
.condition_list .el-dialog__body {
    padding: 16px 26px;
}
.condition_list p {
    color: #666666;
    margin-bottom: 10px;
}
.condition_list p.check_box {
    margin-bottom: 0;
    line-height: 36px;
}
.start-node {
  cursor: default;
}
</style>
