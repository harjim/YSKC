<template>
  <div>
    <a-card :bordered="false" v-show="!isShowTreeTable">
      <a-form layout="inline">
        <a-form-item label="项目类型">
          <a-input v-model="queryParam.productName" placeholder="请输入项目类型"/>
        </a-form-item>
        <span class="table-page-search-submitButtons" style="padding-top:5px;">
          <a-button
            type="primary"
            @click="$refs.table.refresh(true)"
            v-if="$auth('sys:product:search')"
          >查询</a-button>
        </span>
      </a-form>
      <ystable
        queryUrl="/product/queryProduct"
        :params="queryParam"
        header-align="center"
        highlight-hover-row
        show-overflow
        resizable
        ref="table"
        :toolbar="tableToolbar"
      >
        <template v-slot:toolbar_buttons>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            @click="$refs.productModal.add()"
            v-if="$auth('sys:product:add')"
          >添加
          </a-button>
        </template>
        <vxe-table-column
          type="seq"
          title="序号"
          width="60"
          align="center"
          fixed="left"
        />
        <vxe-table-column
          field="productName"
          title="项目类型"
          min-width="200"
          align="left"
          remoteSort
          fixed="left"
        ></vxe-table-column>
        <vxe-table-column field="addressStr" title="所属地" width="240" align="center" remoteSort>
          <template v-slot="{ row }">{{ getAddres(row.address) }}</template>
        </vxe-table-column>
        <vxe-table-column field="year" title="年份" width="150" align="center" remoteSort></vxe-table-column>
        <vxe-table-column
          field="declareType"
          title="申报项目类型"
          min-width="140"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">{{ declareTypeMap[row.declareType] }}</template>
        </vxe-table-column>
        <vxe-table-column
          field="lastUpdateTime"
          title="最后更新时间"
          width="180"
          align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column title="操作" width="220" align="center" fixed="right">
          <template v-slot="{ row }">
            <a @click="$refs.productModal.edit(row)" v-if="$auth('sys:product:edit')">编辑</a>
            <a-divider type="vertical" v-if="$auth('sys:product:del')"/>
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)" v-if="$auth('sys:product:del')">
              <a>删除</a>
            </a-popconfirm>
            <a-divider type="vertical" v-if="(row.productType === 0) && $auth('sys:product:set')"/>
            <a
              @click="showTreeTable(row)"
              v-if="(row.productType === 0) && $auth('sys:product:set')"
            >配置</a>
          </template>
        </vxe-table-column>
      </ystable>
      <product-modal ref="productModal" @ok="handleOk"></product-modal>
    </a-card>
    <a-card v-show="isShowTreeTable">
      <a-form layout="inline">
        <a-form-item>
          <a-button type="primary" @click="showTreeTable(null)">返回</a-button>
        </a-form-item>
        <a-form-item v-if="(status===0 || status===3 || status===null) && $auth('sys:product:save')">
          <a-button type="primary" @click="onStatus('save')">保存</a-button>
        </a-form-item>
        <a-form-item
          v-if="(status===0 || status===3 || status===null) && $auth('sys:product:commit')"
        >
          <a-button type="primary" @click="onStatus('commit')">提交</a-button>
        </a-form-item>
        <a-form-item v-if="(status===1) && $auth('sys:product:audit')">
          <a-button type="primary" @click="onStatus('audit')">审核</a-button>
        </a-form-item>
        <a-form-item v-if="(status===1) && $auth('sys:product:reject')">
          <a-button type="primary" @click="onStatus('reject')">撤回</a-button>
        </a-form-item>
      </a-form>
      <div
        id="Audit"
        v-if="(status === 1 || status ===2 ||status ===3) && !($auth('sys:product:add') || $auth('sys:product:commit'))"
      >
        <a-form-item label="审核意见：">
          <a-textarea
            v-model="opinion"
            placeholder="请输入审核意见"
            :rows="4"
            :auto-size="{ minRows: 4, maxRows: 7 }"
          />
        </a-form-item>
      </div>
      <a-table
        :indentSize="50"
        ref="treeTable"
        id="treeTable"
        :columns="columns"
        :data-source="treeData"
        :pagination="false"
        :defaultExpandAllRows="true"
        @expandedRowsChange="onExpandedRowsChange"
        :expandedRowKeys="expandedRows"
      >
        <template slot="stepName" slot-scope="text, record">
          <editable-cell
            :text="text"
            :oldText="text"
            :status="status"
            @change="onCellChange(record.key, record,'stepName', $event)"
            @del="onCellDel(record.key,record, 'stepName', $event)"
            @plus="onCellPlus(record.key,record, 'stepName', $event)"
            @up="onUp(record.key,record, 'stepName', $event)"
            @down="onDown(record.key,record, 'stepName', $event)"
          />
        </template>
      </a-table>
    </a-card>
    <a-modal v-model="modelVisible" title="添加" @ok="handleModelOk">
      <a-form layout="inline">
        <a-form-item label="步骤名称">
          <a-input v-model="stepName"></a-input>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import { STable } from '@/components'
import ProductModal from './modules/ProductModal'
import EditableCell from './modules/EditableCell'
import ystable from '@/components/Table/ystable'

const columns = [{
  title: '项目步骤',
  width: 200,
  dataIndex: 'stepName',
  key: 'key',
  scopedSlots: { customRender: 'stepName' }
}]
export default {
  name: 'Product',
  components: {
    STable,
    ProductModal,
    EditableCell,
    ystable
  },
  data () {
    return {
      productName: '',
      isShowTreeTable: false,
      columns,
      treeData: [],
      stepName: '',
      productId: '',
      currrentKey: '',
      isShowAudit: true,
      newCount: 0,
      expandedRows: [],
      modelVisible: false,
      loading: false,
      status: undefined,
      opinion: '',
      queryParam: {},
      addressCodes: [],
      declareTypeMap: {},
      form: this.$form.createForm(this, { scroll: {} }),
      getData: parameter => {
        return this.$http.get('/product/queryProduct', { params: Object.assign(parameter, { productName: this.productName }) })
          .then(res => {
            return res.data
          })
      },
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        }
      }
    }
  },
  mounted () {
    this.$getDictionary(1).then(d => {
      this.addressCodes = d
    })
    this.$getDictionary(10).then(res => {
      for (const type of res) {
        this.declareTypeMap[type.key] = type.value
      }
    })
  },
  methods: {
    getAddres (code) {
      if (!code) {
        return ''
      }
      const arr = code.split(',')
      var currentIndex = 0
      var result = ''
      var tempChildren = this.addressCodes
      while (currentIndex < arr.length) {
        for (let i = 0; i < tempChildren.length; i++) {
          if (arr[currentIndex] === tempChildren[i].key) {
            result += tempChildren[i].value + '/'
            if (tempChildren[i].children && tempChildren[i].children.length > 0) {
              tempChildren = tempChildren[i].children
            } else {
              tempChildren = []
            }
            break
          }
        }
        currentIndex++
      }
      return result ? result.substr(0, result.length - 1) : result
    },
    onStatus (type) {
      const { productId } = this
      const paramObj = { productId }
      let statusMessage = ''
      if (type === 'save') {
        paramObj.status = 0
        statusMessage = '保存成功！'
      }
      if (type === 'commit') {
        paramObj.status = 1
        statusMessage = '提交成功！'
      }
      if (type === 'audit') {
        paramObj.status = 2
        paramObj.remake = this.opinion
        statusMessage = '操作成功！'
      }
      if (type === 'reject') {
        paramObj.remake = this.opinion
        paramObj.status = 3
        statusMessage = '撤回成功！'
      }
      this.$http.post('/rsProjectStep/editStepStatus', paramObj)
        .then(res => {
          if (res.data) {
            this.queryData(this.productId, null)
            this.$message.info(statusMessage)
          }
        })
    },
    onExpandedRowsChange (expandedRows) {
      // this.expandedRows = [...expandedRows]
      this.expandedRows = expandedRows
    },
    handleChange (e) {
      const value = e.target.value
      this.value = value
    },
    // 添加回调事件
    onCellPlus (key, record, dataIndex, value) {
      this.expandedRows.push(key)
      this.newCount++
      const treddChlidren = {
        stepName: `新节点-${this.newCount}`,
        key: `newCount${this.newCount}`
      }
      if (!record.children) {
        record['children'] = [treddChlidren]
      } else {
        record.children.push(treddChlidren)
      }
    },
    // 模态框确定事件
    handleModelOk () {
      this.$http.post('/rsProjectStep/addStep', {
        parentId: this.currrentKey,
        productId: this.productId,
        stepName: this.stepName
      })
        .then(res => {
          this.queryData(this.productId)
          this.modelVisible = false
          this.currrentKey = ''
          this.stepName = ''
        })
    },
    // 删除回调事件
    onCellDel (key, record, dataIndex, value) {
      if (key === -1) {
        this.$message.warning('根节点不可以删除！')
        return
      }
      if (record.children) {
        if (record.children.length > 0) {
          this.$message.warning('当前节点,包含子节点，不能删除！')
          return
        }
      }
      const isNumber = Number(key)
      if (!isNaN(isNumber)) {
        this.$http.post('/rsProjectStep/delStep', { id: key })
          .then(res => {
            if (res.data) {
              this.$message.info('删除成功！')
              this.treeData = this.dsFilter(this.treeData, key)
            }
          })
      } else {
        this.treeData = this.dsFilter(this.treeData, key)
        this.$message.info('删除成功！')
      }
    },
    // 编辑回调事件
    onCellChange (key, record, dataIndex, value) {
      if (key === -1) {
        this.$message.warning('根节点不可以编辑！')
        return
      }
      const cachestepName = record.stepName
      if (key && key.search('newCount') < 0) {
        this.$http.post('/rsProjectStep/editStep', { id: this.productId, stepName: value, productId: this.productId })
          .then(res => {
            if (res.data) {
              this.$message.info('修改成功！')
              record.stepName = value
            } else {
              record.stepName = cachestepName
              this.$message.error(res.errorMessage)
            }
          }).catch(error => {
            this.$message.error(error)
            record.stepName = cachestepName
          })
      } else {
        record.stepName = cachestepName
        this.$http.post('/rsProjectStep/addStep', {
          parentId: record.parentId,
          productId: this.productId,
          stepName: value
        })
          .then(res => {
            if (res.data) {
              const data = res.data
              this.$message.info('修改成功！')
              record.stepName = data.stepName
              record.key = data.id
            } else {
              record.stepName = cachestepName
              this.$message.error(res.errorMessage)
            }
          }).catch(error => {
            this.$message.error(error)
            record.stepName = cachestepName
            this.$message.warning(error)
          })
      }
    },
    // 切换显示TreeeTabel
    showTreeTable (record) {
      if (record) {
        this.queryData(record.id, record.productName)
        this.productId = record.id
      } else {
        this.treeData = []
        this.productId = ''
        this.expandedRows = []
      }
      if (!this.isShowTreeTable) {
        setTimeout(() => {
          this.isShowTreeTable = !this.isShowTreeTable
        }, 200)
      } else {
        this.isShowTreeTable = !this.isShowTreeTable
      }
    },
    // 刷新数据
    queryData (productId, productName) {
      this.$http.get('/rsProjectStep/getStep', { params: { productId } })
        .then(res => {
          this.treeData = this.dsRemoveChildren(res.data.data)
          this.status = res.data.status
          this.opinion = res.data.remark
          if (this.treeData.length === 0) {
            this.treeData = [{ stepName: productName, key: -1, productId: this.productId }]
          } else {
            this.treeData = [{ stepName: productName, key: -1, children: res.data.data, productId: this.productId }]
          }
        })
    },
    // 提升排序
    onUp (key, record, dataIndex, value) {
      const newData = this.dsShift(this.treeData, key, 'up')
      this.treeData = newData.filter((item) => {
        return true
      })
    },
    // 降低排序
    onDown (key, record, dataIndex, value) {
      const newData = this.dsShift(this.treeData, key, 'down')
      this.treeData = newData.filter((item) => {
        return true
      })
    },
    // 递归删除数据
    dsFilter (dealData, dealKey) {
      for (let i = 0; i < dealData.length; i++) {
        if (dealData[i].children && dealData[i].children.length > 0) {
          dealData[i].children = this.dsFilter(dealData[i].children, dealKey)
        }
      }
      return dealData.filter(item => item.key !== dealKey)
    },
    // 排序
    dsShift (dealData, dealKey, type) {
      for (let i = 0; i < dealData.length; i++) {
        if (dealData[i].children && dealData[i].children.length > 0) {
          dealData[i].children = this.dsShift(dealData[i].children, dealKey, type)
        }
        if (dealData[i].key === dealKey) {
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
    },
    dsRemoveChildren (dealData) {
      for (let i = 0; i < dealData.length; i++) {
        if (dealData[i].children && dealData[i].children.length > 0) {
          dealData[i].children = this.dsRemoveChildren(dealData[i].children)
        }
        if (dealData[i].children && dealData[i].children.length === 0) {
          delete dealData[i].children
        }
      }
      return dealData
    },
    handleDel (record) {
      this.$http.post('/product/deleteProduct', { id: record.id }).then(res => {
        if (res.data && res.success) {
          this.$refs.table.refresh(true)
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage)
        }
      }).finally(res => {
        this.$refs.table.refresh(false)
      })
    },
    handleOk (flag) {
      this.$getProductList(true)
      if (flag) {
        this.$message.success('添加成功')
        this.$refs.table.refresh(true)
      } else {
        this.$message.success('更新成功')
        this.$refs.table.refresh(false)
      }
    }
  }
}
</script>
<style lang="less">
#treeTable /deep/ .ant-table-row-cell-break-word {
  display: flex;
  align-items: center;
}
</style>
