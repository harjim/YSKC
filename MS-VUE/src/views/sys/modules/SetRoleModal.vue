<template>
  <a-modal
    :width="1100"
    :bodyStyle="{ height: '80vh', overflow: 'auto'}"
    centered
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="closeModal"
    :confirmLoading="loading"
    :maskClosable="false"
  >
    <div>
      <a-table
        class="table"
        rowKey="value"
        size="small"
        ref="table"
        :loading="loading"
        :columns="columns"
        :dataSource="tableData"
        :pagination="false"
        bordered
      >
        <span slot="child3" slot-scope="text,record">
          <template v-if="record.col === 2">
            <a-row>
              <a-col :span="12" v-for="(node,index) in record.childs" :key="index">
                <a-checkbox
                  :checked="allChecks[node.value]"
                  @change="handleChecks(record, node.value)"
                >{{ node.label }}</a-checkbox>
              </a-col>
            </a-row>
          </template>
          <template v-else-if="record.col === 1">
            <a-checkbox
              v-if="record.childs && record.childs.length > 0"
              :indeterminate="halfChecks[record.value]"
              :checked="allChecks[record.value]"
              @change="handleChecks(record, record.value)"
            >{{ record.label }}</a-checkbox>
            <a-checkbox
              v-else
              :checked="allChecks[record.value]"
              @change="handleChecks(record, record.value)"
            >{{ record.label }}</a-checkbox>
          </template>
        </span>
        <!-- <span slot="child4" slot-scope="text,record">
          <template v-if="record.level">
            <a-row>
              <a-col :span="12" v-for="(node,index) in record.childs" :key="index">
                <a-checkbox
                  :checked="allChecks[node.value]"
                  @change="handleChecks(record, node.value)"
                >{{ node.label }}</a-checkbox>
              </a-col>
            </a-row>
          </template>
        </span> -->
        <template slot="actionData" slot-scope="text,record">
          <a-radio-group @change="(e)=>onDataChange(e,record)" :value="dataChecks[record.value]">
            <a-radio :value="0">本人</a-radio>
            <a-radio :value="1">部门</a-radio>
            <a-radio :value="2">公司</a-radio>
          </a-radio-group>
        </template>
      </a-table>
    </div>
  </a-modal>
</template>
<script>
import { STable, Ellipsis } from '@/components'
export default {
  name: 'SetRole',
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      dataChecks: {},
      keyArr: ['rootValue', 'parentValue', 'pValue', 'value'],
      tableData: [],
      columns: [{
        title: '目录',
        dataIndex: 'level',
        key: 'level',
        width: 140,
        // scopedSlots: { customRender: 'child' }
        customRender: (text, row, index) => {
          var spanValue
          const obj = {
            children: spanValue,
            attrs: {}
          }
          if (row.col === 4) {
            spanValue = <a-checkbox checked={this.allChecks[row.value]} onClick={(e) => this.handleChecks(row, row.value)}>{row.label}</a-checkbox>
            obj.attrs.rowSpan = 1
          } else {
            spanValue = <a-checkbox indeterminate={this.halfChecks[row.rootValue]} checked={this.allChecks[row.rootValue]} onClick={(e) => this.handleChecks(row, row.rootValue)}>{row.rootLabel}</a-checkbox>
            if (row.first) {
              obj.attrs.rowSpan = row.rootRowSpan
            } else {
              obj.attrs.rowSpan = 0
            }
          }
          obj.children = spanValue
          return obj
        }
      }, {
        title: '一级',
        dataIndex: 'level1',
        key: 'level1',
        width: 140,
        // scopedSlots: { customRender: 'child1' }
        // ,
        customRender: (text, row, index) => {
          var spanValue
          const obj = {
            children: spanValue,
            attrs: {}
          }
          if (row.col !== 4) {
            if (row.parentValue) {
              spanValue = <a-checkbox indeterminate={this.halfChecks[row.parentValue]} checked={this.allChecks[row.parentValue]} onClick={(e) => this.handleChecks(row, row.parentValue)}>{row.parentLabel}</a-checkbox>
              if (row.second) {
                obj.attrs.rowSpan = row.parentRowSpan
              } else {
                obj.attrs.rowSpan = 0
              }
            } else {
              spanValue = <a-checkbox indeterminate={this.halfChecks[row.value]} checked={this.allChecks[row.value]} onClick={(e) => this.handleChecks(row, row.value)}>{row.label}</a-checkbox>
              obj.attrs.rowSpan = 1
            }
            obj.children = spanValue
          }
          return obj
        }
      }, {
        title: '二级',
        dataIndex: 'level2',
        key: 'level2',
        width: 240,
        customRender: (text, row, index) => {
          var spanValue
          const obj = {
            children: spanValue,
            attrs: {}
          }
          if (row.col === 3) {
            obj.attrs.rowSpan = 1
            spanValue = <a-row>
              {row.childs.map(node => {
                return <a-col span={12}><a-checkbox checked={this.allChecks[node.value]} onClick={(e) => this.handleChecks(row, node.value)}>{node.label}</a-checkbox></a-col>
              })
              }
            </a-row>
          } else if (row.col === 2) {
            spanValue = <a-checkbox indeterminate={this.halfChecks[row.value]} checked={this.allChecks[row.value]} onClick={(e) => this.handleChecks(row, row.value)}>{row.label}</a-checkbox>
            obj.attrs.rowSpan = 1
          } else if (row.col === 1) {
            spanValue = <a-checkbox indeterminate={this.halfChecks[row.pValue]} checked={this.allChecks[row.pValue]} onClick={(e) => this.handleChecks(row, row.pValue)}>{row.pLabel}</a-checkbox>
            if (row.thrid) {
              obj.attrs.rowSpan = row.pRowSpan
            } else {
              obj.attrs.rowSpan = 0
            }
          }
          obj.children = spanValue
          return obj
        }
      }, {
        title: '三级',
        dataIndex: 'level3',
        key: 'level3',
        width: 240,
        scopedSlots: { customRender: 'child3' }
      }, {
        title: '数据权限',
        dataIndex: 'dataPerm',
        key: 'dataPerm',
        width: 220,
        scopedSlots: { customRender: 'actionData' }
      }],
      confirmLoading: false,
      visible: false,
      title: '',
      loading: false,
      roleId: 0,
      allChecks: {},
      halfChecks: {},
      nodeObjSet: {}
    }
  },
  created () {
    this.loading = true
    this.$http.get('/role/getMenuAction')
      .then(res => {
        const data = this.getTableRow(res.data, -1)
        var tempData = []
        var tempHalf = {}
        var level1 = {}
        var level2 = {}
        var level3 = {}
        for (let i = 0; i < data.length; i++) {
          const d = data[i]
          if (d.childs && d.childs.length > 0 && d.childs[0].type !== 2) {
            d.childs.forEach((item) => {
              if (item.childs && item.childs.length > 0 && item.childs[0].type !== 2) {
                item.childs.forEach(d2 => {
                  if (d2.childs && d2.childs.length > 0 && d2.childs[0].type !== 2) {
                    d2.childs.forEach(d3 => {
                      d3.rootLabel = d.label
                      d3.rootValue = d.value
                      d3.parentLabel = item.label
                      d3.parentValue = item.value
                      d3.pLabel = d2.label
                      d3.pValue = d2.value
                      d3.level = d2.childs.length
                      d3.col = 1
                      tempData.push(d3)
                      tempHalf[d3.value] = false
                    })
                    // 第一层行合并
                    if (!level1[d.value]) {
                      level1[d.value] = 0
                    }
                    level1[d.value] += d2.childs.length
                    // 第二层行合并
                    if (!level2[item.value]) {
                      level2[item.value] = 0
                    }
                    level2[item.value] += d2.childs.length
                    // 第三层行合并
                    if (!level3[d2.value]) {
                      level3[d2.value] = 0
                    }
                    level3[d2.value] += d2.childs.length
                  } else {
                    d2.rootLabel = d.label
                    d2.rootValue = d.value
                    d2.parentLabel = item.label
                    d2.parentValue = item.value
                    d2.col = 2
                    tempData.push(d2)
                    tempHalf[d2.value] = false
                    // 第一层行合并
                    if (!level1[d.value]) {
                      level1[d.value] = 0
                    }
                    level1[d.value] += 1
                    // 第二层行合并
                    if (!level2[item.value]) {
                      level2[item.value] = 0
                    }
                    level2[item.value] += 1
                  }
                })
              } else {
                item.rootLabel = d.label
                item.rootValue = d.value
                item.col = 3
                tempData.push(item)
                tempHalf[item.value] = false
                // 第一层行合并
                if (!level1[d.value]) {
                  level1[d.value] = 0
                }
                level1[d.value] += 1
                // 第二层行合并
                if (!level2[item.value]) {
                  level2[item.value] = 0
                }
                level2[item.value] += 1
              }
            })
          } else {
            if (!level1[d.value]) {
              level1[d.value] = 0
            }
            level1[d.value] += 1
            d.col = 4
            tempData.push(d)
          }
        }
        var tempObj = {}
        var nodeObj = {}
        var tempDataCheck = {}
        tempData.forEach((item, index) => {
          tempDataCheck[item.value] = undefined
          if (item.rootValue) {
            if (!tempObj[item.rootValue]) {
              tempObj[item.rootValue] = true
              item.first = true
              item.rootRowSpan = level1[item.rootValue]
            }
            this.getNodeSet(nodeObj, item, 'rootValue')
          }
          if (item.parentValue) {
            if (!tempObj[item.parentValue]) {
              tempObj[item.parentValue] = true
              item.second = true
              item.parentRowSpan = level2[item.parentValue]
            }
            this.getNodeSet(nodeObj, item, 'parentValue')
          }
          if (item.pValue) {
            if (!tempObj[item.pValue]) {
              tempObj[item.pValue] = true
              item.thrid = true
              item.pRowSpan = level3[item.pValue]
            }
            this.getNodeSet(nodeObj, item, 'pValue')
          }
          this.getNodeSet(nodeObj, item, 'value')
        })
        this.tableData = tempData
        this.dataChecks = tempDataCheck
        this.halfChecks = tempHalf
        this.nodeObjSet = nodeObj
        return this.tableData
      }).finally(res => {
        this.loading = false
      })
  },
  methods: {
    getNodeSet (nodeObj, row, key) {
      var tempSet = nodeObj[row[key]]
      var begin = this.keyArr.length
      this.keyArr.forEach((k, i) => {
        if (k === key) {
          begin = i
        }
        if (i > begin && row[k]) {
          if (!tempSet) {
            tempSet = new Set()
          }
          tempSet.add(row[k])
        }
      })
      if (row.childs && row.childs.length > 0) {
        if (!tempSet) {
          tempSet = new Set()
        }
        row.childs.forEach(node => {
          tempSet.add(node.value)
        })
      }
      if (tempSet) {
        nodeObj[row[key]] = tempSet
      }
    },
    handleChecks (row, key) {
      const checked = !this.allChecks[key]
      this.$set(this.allChecks, key, checked)
      if (this.nodeObjSet[key]) {
        this.$set(this.halfChecks, key, false)
        this.nodeObjSet[key].forEach(k => {
          this.$set(this.allChecks, k, checked)
          if (this.nodeObjSet[k]) {
            this.$set(this.halfChecks, k, false)
          }
        })
      }
      this.setChecked(row)
    },
    setChecked (row) {
      // 倒序循环，子节点优先check
      for (let i = this.keyArr.length - 1; i >= 0; i--) {
        const tempKey = row[this.keyArr[i]]
        if (tempKey && this.nodeObjSet[tempKey]) {
          this.setAllAndHalf(tempKey)
        }
      }
    },
    setAllAndHalf (tempKey) {
      var tempHalf = false
      var tempAll = true
      this.nodeObjSet[tempKey].forEach(k => {
        if (this.allChecks[k]) {
          if (!tempHalf) {
            tempHalf = true
          }
        } else {
          tempAll = false
        }
      })
      tempHalf = tempHalf && tempAll ? false : tempHalf
      this.$set(this.halfChecks, tempKey, tempHalf)
      this.$set(this.allChecks, tempKey, tempAll)
    },
    getTableRow (data, parentId) {
      if (data === null || data === undefined || data.length <= 0) {
        return []
      }
      const result = []
      for (let i = 0; i < data.length; i++) {
        if (parentId === data[i].parentId) {
          const node = {}
          node.value = data[i].id
          node.label = data[i].name
          node.type = data[i].type
          this.$set(this.allChecks, node.value, false)
          node.childs = this.getTableRow(data, data[i].id)
          node.rowNum = node.childs.length
          result.push(node)
        }
      }
      return result
    },
    set (record) {
      this.title = `配置角色[${record.roleName}]`
      this.roleId = record.id
      this.visible = true
      this.getRole()
    },
    getRole () {
      this.loading = true
      this.$http.get('/role/getRoleMenuIds', { params: { roleId: this.roleId } })
        .then(res => {
          if (res.success && res.data) {
            if (res.data.length <= 0) {
              return null
            }
            res.data.forEach(item => {
              if (this.allChecks[item.menuId] !== undefined) {
                this.$set(this.allChecks, item.menuId, true)
                if (item.dataType || item.dataType === 0) {
                  this.$set(this.dataChecks, item.menuId, item.dataType)
                }
              }
            })
            for (var k in this.allChecks) {
              if (this.nodeObjSet[k]) {
                this.setAllAndHalf(k)
              }
            }
          } else {
            this.$message.error(res.errorMessage)
          }
        }).finally(res => {
          this.loading = false
        })
    },
    handleSubmit () {
      var menuIdList = new Set()
      for (var k in this.allChecks) {
        if (this.allChecks[k]) {
          menuIdList.add(k)
          continue
        }
        if (this.halfChecks[k]) {
          menuIdList.add(k)
        }
      }
      var dataPerms = []
      menuIdList.forEach(menuId => {
        if (this.dataChecks.hasOwnProperty(menuId)) {
          dataPerms.push({ menuId, dataType: this.dataChecks[menuId] || 0 })
        }
      })
      this.loading = true
      this.$http.post('/role/set', Object.assign({ menuIdList: [...menuIdList], roleId: this.roleId, dataPerms }))
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('配置成功')
            this.closeModal()
            this.$emit('ok', true)
          } else {
            this.$message.error(res.errorMessage)
          }
        }).finally(res => {
          this.loading = false
        })
    },
    onDataChange (e, row) {
      this.$set(this.dataChecks, row.value, e.target.value)
    },
    closeModal () {
      this.visible = false
      for (const k in this.allChecks) {
        this.$set(this.allChecks, k, false)
        if (this.halfChecks[k]) {
          this.$set(this.halfChecks, k, false)
        }
      }
      for (const k in this.dataChecks) {
        if (this.dataChecks[k] || this.dataChecks[k] === 0) {
          this.$set(this.dataChecks, k, undefined)
        }
      }
      this.roleId = undefined
    }
  }
}
</script>
<style lang="less" scoped>
</style>
