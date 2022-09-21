<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="isVisible"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="项目名称" :labelCol="{span: 4}" :wrapperCol="{span: 20}" style="margin-bottom: 5px;">
            <a-input v-model="productName" placeholder="项目名称"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="配置阶段" :labelCol="{span: 4}" :wrapperCol="{span: 20}" style="margin-bottom: 5px;">
            <a-row type="flex" justify="space-between" align="top">
              <a-col :span="9">
                <vxe-grid
                  id="source"
                  class="source"
                  ref="table"
                  :data="sourceObj.data"
                  highlight-hover-row
                  show-overflow
                  border="full"
                  auto-resize
                  size="mini"
                  @checkbox-all="(obj) => { selectedChange('source',obj)}"
                  @checkbox-change="(obj) => { selectedChange('source',obj)}">
                  <vxe-table-column
                    type="checkbox"
                    width="40"
                    align="center"
                    header-align="center"></vxe-table-column>
                  <vxe-table-column
                    type="seq"
                    title="序号"
                    width="50"
                    align="center"
                    header-align="center"></vxe-table-column>
                  <vxe-table-column
                    title="可选阶段"
                    field="value"
                    align="left"
                    min-width="100"
                    header-align="center"></vxe-table-column>
                </vxe-grid>
              </a-col>
              <a-col :span="1">
                <div>
                  <a-button
                    style="font-size: 16px"
                    type="primary"
                    size="small"
                    class="button"
                    @click="handleRight"
                    :disabled="isDisable($auth('project:tech:setStage'))">
                    <a-icon style="font-size: 16px" type="right" />
                  </a-button>
                  <br/>
                  <a-button
                    style="font-size: 16px"
                    type="primary"
                    size="small"
                    class="button"
                    @click="onHandleLeft"
                    :disabled="isDisable($auth('project:tech:setStage'))">
                    <a-icon style="font-size: 16px" type="left" />
                  </a-button>
                </div>
              </a-col>
              <a-col :span="9">
                <vxe-grid
                  id="target"
                  class="target"
                  ref="table"
                  :data="targetObj.data"
                  highlight-hover-row
                  show-overflow
                  border="full"
                  auto-resize
                  size="mini"
                  @checkbox-all="(obj) => { selectedChange('target',obj) }"
                  @checkbox-change="(obj) => { selectedChange('target',obj) }">
                  <vxe-table-column
                    type="checkbox"
                    width="40"
                    align="center"
                    header-align="center"></vxe-table-column>
                  <vxe-table-column
                    type="seq"
                    title="序号"
                    width="50"
                    align="center"
                    header-align="center"></vxe-table-column>
                  <vxe-table-column
                    title="已选阶段"
                    min-width="100"
                    field="value"
                    align="lefe"
                    header-align="center">
                    <template v-slot="{ row, rowIndex }">
                      <span></span>
                      <a title="点击向上移动" @click="handleUp(row, rowIndex)" style="margin: 0 5px;">
                        <a-icon type="arrow-up" />
                      </a>
                      <a title="点击向下移动" @click="handleDown(row, rowIndex)" style="maring: 0 5px;">
                        <a-icon type="arrow-down" />
                      </a>
                      <span style="margin-left: 5px">
                        {{ row.value }}
                      </span>
                    </template>
                  </vxe-table-column>
                </vxe-grid>
              </a-col>
            </a-row>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'ConfigStage',
  data () {
    return {
      isVisible: false,
      form: this.$form.createForm(this),
      stages: [],
      targetData: [],
      sourceObj: {
        selectedKeys: [],
        selectedProductKey: [],
        data: []
      },
      targetObj: {
        selectedKeys: [],
        selectedProductKey: [],
        data: []
      },
      productName: '',
      record: {},
      isAdd: true,
      title: '',
      disabledOrder: undefined, // 项目进行到阶段order
      popconfirmVisible: false,
      popconfirmMessage: ''
    }
  },
  methods: {
    show (record, stages) {
      this.resetData()
      this.stages = stages
      this.title = `配置[${record.productName}]阶段`
      this.productName = record.productName
      this.isVisible = true
      this.record = record
      this.$http.get('/productStage/getStageList', { params: { productId: record.productId } }).then((res) => {
        if (res.data.length <= 0) {
          this.isAdd = true
          this.sourceObj.data = Array.from(this.stages)
        } else {
          this.isAdd = false
          const tempAry = [] // 已选择的阶段 key
          const tempObj = {} // 已选择的阶段 key-value
          res.data.forEach((item) => {
            if (item.stageKey === record.stageKey) {
              this.disabledOrder = item.order
            }
            tempAry.push(item.stageKey)
            tempObj[item.stageKey] = item
          })
          // 处理哪些是已选择和可选择阶段
          this.stages.map((item) => {
            if (tempAry.includes(item.key)) {
              this.targetObj.data.push(item)
            } else {
              this.sourceObj.data.push(item)
            }
          })
          // 处理已选阶段赋值id
          for (const key in tempObj) {
            this.targetObj.data.map((item) => {
              if (item.key === key) {
                item.stageId = tempObj[key].id
                item.order = tempObj[key].order - 1
                item['disabled'] = tempObj[key].disabled
              }
            })
          }
          this.targetObj.data = this.targetObj.data.sort((a, b) => {
            return a.order - b.order
          })
        }
      })
    },
    // 重置变量
    resetData () {
      this.isVisible = false
      this.title = ''
      this.sourceObj = {
        selectedKeys: [],
        selectedProductKey: [],
        data: []
      }
      this.targetObj = {
        selectedKeys: [],
        selectedProductKey: [],
        data: []
      }
      this.record = {}
      this.stages = []
      this.productName = ''
      this.isAdd = true
      this.disabledOrder = undefined
      this.popconfirmVisible = false
      this.popconfirmMessage = ''
    },
    // 提交事件
    handleSubmit () {
      const Obj = {}
      Obj.productId = this.record.productId // 项目类型ID
      Obj.projectId = this.record.id // 项目ID
      Obj.productName = this.productName
      const stages = []
      this.targetObj.data.map((item, index) => {
        const stagesObj = {
          order: index + 1,
          stageKey: item.key
        }
        if (!this.isAdd) { // 编辑不用处理stageKeys.id
          stagesObj['id'] = item.stageId
        }
        stages.push(stagesObj)
      })
      Obj.stageKeys = stages
      this.$http.post('/productStage/updateProductStage', Obj).then((res) => {
        if (res.data && res.success) {
          this.isVisible = false
          this.$message.success('操作成功！')
          this.$emit('ok', false)
        } else {
          this.$message.error('操作失败:' + res.errorMessage)
        }
      }).catch((error) => {
        if (error.message.includes('403')) {
          this.$message.error('无此权限，请跟管理员联系！')
        } else {
          this.$message.error('操作失败：' + error.message)
        }
      })
    },
    // 执行向右移动（变回已选阶段）
    handleRight () {
      if (!this.sourceObj.selectedProductKey.length > 0) {
        return
      }
      this.sourceObj.data = this.sourceObj.data.filter((item) => {
        if (!this.sourceObj.selectedKeys.includes(item.id)) {
          return true
        } else {
          delete item.stageId
          this.targetObj.data.push(item)
        }
      })
    },
    // 执行向左移动验证（变回可选阶段）
    onHandleLeft (visible) {
      if (!this.targetObj.selectedProductKey.length > 0) {
        return
      }
      if (visible) {
        this.$http.get('/productStage/checkStageData', { params: { productId: this.record.productId, stageKeys: this.targetObj.selectedProductKey } }).then((res) => {
          if (res.success) {
            if (res.data) {
              const _this = this
              this.$confirm({
                title: res.data,
                onOk () {
                  _this.handleData()
                }
              })
            } else {
              this.handleData()
            }
          } else {
            this.$message.error(res.errorMessage)
          }
        })
      }
    },
    // 处理向左移动数据
    handleData () {
      this.targetObj.data = this.targetObj.data.filter((item) => {
        if (!this.targetObj.selectedKeys.includes(item.id)) {
          return true
        } else {
          delete item.stageId
          this.sourceObj.data.push(item)
        }
      })
    },
    selectedChange (type, { records }) {
      const selectedKeys = [ ]
      const selectedProductKey = [ ]
      records.forEach(item => {
        selectedKeys.push(item.id)
        selectedProductKey.push(item.key)
      })
      if (type === 'source') {
        this.sourceObj['selectedKeys'] = selectedKeys
        this.sourceObj['selectedProductKey'] = selectedProductKey
      } else if (type === 'target') {
        this.targetObj['selectedKeys'] = selectedKeys
        this.targetObj['selectedProductKey'] = selectedProductKey
      }
    },
    // 执行向上移动事件
    handleUp (row, rowIndex) {
      if (this.disabledOrder === rowIndex) {
        this.$message.warning('不能调整到已固定的阶段上面')
        return
      }
      if (this.disabledOrder && this.disabledOrder >= (row.order + 1)) {
        this.$message.warning('阶段已固定不可以移动')
        return
      }
      const dealData = this.dsShift(this.targetObj.data, row.id, 'up')
      this.targetObj.data = [...dealData]
    },
    // 执行向下移动事件
    handleDown (row, rowIndex) {
      if (this.disabledOrder && this.disabledOrder >= (row.order + 1)) {
        this.$message.warning('阶段已固定不可以移动')
        return
      }
      const dealData = this.dsShift(this.targetObj.data, row.id, 'down')
      this.targetObj.data = [...dealData]
    },
    // 设置阶段按钮禁用
    isDisable (auth) {
      if (auth) {
        return false
      } else {
        return true
      }
    },
    // 排序
    dsShift (dealData, dealKey, type) {
      for (let i = 0; i < dealData.length; i++) {
        if (dealData[i].children && dealData[i].children.length > 0) {
          dealData[i].children = this.dsShift(dealData[i].children, dealKey, type)
        }
        if (dealData[i].id === dealKey) {
          if (type === 'up') {
            if (i === 0) {
              this.$message.warning('该行已置顶！')
              break
            }
            const temp = dealData[i - 1]
            dealData[i - 1] = dealData[i]
            dealData[i] = temp
            break
          } else if (type === 'down') {
            if (i === dealData.length - 1) {
              this.$message.warning('该行已置底！')
              break
            }
            const temp = dealData[i + 1]
            dealData[i + 1] = dealData[i]
            dealData[i] = temp
            break
          }
        }
      }
      return dealData
    }
  }
}
</script>

<style lang="less" scoped>

#customTransfer {
  margin: 0 auto;
  .source {
    float: left;
  }
  .target{
    float: left;
  }
  #center {
    float: left;
    width: 112px;
    div {
      display: inline;
      position: relative;
      top: 45%;
      left: 35%;
      button {
        margin-bottom: 8px ;
      }
    }
  }
}
</style>
