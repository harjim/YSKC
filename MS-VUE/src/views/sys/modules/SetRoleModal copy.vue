<template>
  <a-modal
    :width="1100"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    :maskClosable="false"
    @cancel="closeModal"
  >
    <a-spin :spinning="confirmLoading">
      <a-table
        class="table"
        rowKey="menuId"
        size="default"
        ref="table"
        :loading="loading"
        :columns="columns"
        :dataSource="tableData"
        :pagination="false"
        bordered
      >
        <template slot="menuName" slot-scope="text, record">
          <a-checkbox
            :checked="allChecks[record.menuId]"
            :indeterminate="indeterminate[record.menuId]"
            @change="onChange(record.menuId,record)"
          >{{ record.menuName }}</a-checkbox>
        </template>
        <template slot="actionModelList" slot-scope="text, record">
          <a-row>
            <a-col :span="8" v-for="(action, index) in record.actionModelList" :key="index">
              <a-checkbox
                :checked="allChecks[action.menuId]"
                @change="onChange(action.menuId,record)"
              >{{ action.menuName }}</a-checkbox>
            </a-col>
          </a-row>
        </template>
        <template slot="actionData" slot-scope="text,record">
          <a-radio-group @change="(e)=>onDataChange(e,record)" :value="dataChecks[record.menuId]">
            <a-radio :value="0">本人</a-radio>
            <a-radio :value="1">部门</a-radio>
            <a-radio :value="2">公司</a-radio>
          </a-radio-group>
        </template>
      </a-table>
    </a-spin>
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
      keyArr: ['parentId', 'menuId'],
      nodeObj: {},
      allChecks: {},
      indeterminate: {},
      dataChecks: {},
      itemData: {},
      tableData: [],
      roleId: undefined,
      columns: [{
        title: '目录',
        dataIndex: 'catalogName',
        key: 'catalogName',
        width: 110,
        customRender: (value, row, index) => {
          var spanValue = value
          if (row.changeSpan) {
            spanValue = (<span> <a-checkbox indeterminate={this.indeterminate[row.parentId]} checked={this.allChecks[row.parentId]} onClick={(e) => this.onChange(row.parentId, row)} ></a-checkbox>{value}</span >)
          }
          const obj = {
            children: spanValue,
            attrs: {}
          }
          if (row.changeSpan) {
            obj.attrs.rowSpan = row.menuCount
          } else {
            obj.attrs.colSpan = 0
          }
          return obj
        }
      }, {
        title: '菜单',
        dataIndex: 'menuName',
        key: 'menuName',
        width: 130,
        scopedSlots: { customRender: 'menuName' }
      }, {
        title: '功能',
        width: 580,
        dataIndex: 'actionModelList',
        key: 'actionModelList',
        scopedSlots: { customRender: 'actionModelList' }
      }, {
        title: '数据权限',
        width: 260,
        dataIndex: 'dataPerm',
        key: 'dataPerm',
        scopedSlots: { customRender: 'actionData' }
      }],
      confirmLoading: false,
      visible: false,
      title: '',
      loading: false
    }
  },
  created () {
    this.loading = true
    this.$http.get('/role/getMenuAction', { params: this.queryParam })
      .then(res => {
        this.loading = false
        this.tableData = res.data
        this.setHalfAndNode(res.data)
        return res.data
      })
  },
  methods: {
    setHalfAndNode (data) {
      var tempNode = {}
      var tempHalf = {}
      var tempData = {}
      data.forEach(d => {
        if (!tempNode[d.parentId]) {
          tempNode[d.parentId] = []
        }
        tempNode[d.parentId].push(d.menuId)
        if (d.actionModelList && d.actionModelList.length > 0) {
          if (!tempNode[d.menuId]) {
            tempNode[d.menuId] = []
          }
          d.actionModelList.forEach(child => {
            tempNode[d.parentId].push(child.menuId)
            tempNode[d.menuId].push(child.menuId)
          })
        }
        tempHalf[d.parentId] = false
        tempHalf[d.menuId] = false
        tempData[d.menuId] = undefined
      })
      this.indeterminate = tempHalf
      this.dataChecks = tempData
      this.nodeObj = tempNode
    },
    set (record) {
      this.visible = true
      this.roleId = record.id
      this.title = '配置角色权限[' + record.roleName + ']'
      this.loading = true
      this.$http.get('/role/getRoleMenuIds', { params: { roleId: record.id } })
        .then(res => {
          if (res.success && res.data) {
            if (res.data.length <= 0) {
              return null
            }
            res.data.forEach(item => {
              this.$set(this.allChecks, item.menuId, true)
              if (item.dataType || item.dataType === 0) {
                this.$set(this.dataChecks, item.menuId, item.dataType)
              }
            })
            for (var k in this.allChecks) {
              if (this.nodeObj[k]) {
                this.setAllCheck(k)
              }
            }
          }
        }).finally(() => {
          this.loading = false
        })
    },
    handleSubmit () {
      this.confirmLoading = true
      var menuIdList = new Set()
      for (var k in this.allChecks) {
        if (this.allChecks[k]) {
          menuIdList.add(k)
          continue
        }
        if (this.indeterminate[k]) {
          menuIdList.add(k)
        }
      }
      var dataArr = []
      menuIdList.forEach(menuId => {
        if (this.dataChecks.hasOwnProperty(menuId)) {
          dataArr.push({ menuId: menuId, dataType: this.dataChecks[menuId] ? this.dataChecks[menuId] : 0 })
        }
      })
      this.$http.post('/role/set', { roleId: this.roleId, menuIdList: [...menuIdList], dataPerms: dataArr }).then(res => {
        this.confirmLoading = false
        this.$emit('ok', true)
        this.closeModal()
      })
    },
    onChange (key, row) {
      const checked = !this.allChecks[key]
      this.$set(this.allChecks, key, checked)
      if (this.nodeObj[key]) {
        this.nodeObj[key].forEach(k => {
          this.$set(this.allChecks, k, checked)
          if (this.indeterminate[k]) {
            this.$set(this.indeterminate, k, false)
          }
        })
      }
      this.setCheck(row)
    },
    onDataChange (e, row) {
      this.$set(this.dataChecks, row.menuId, e.target.value)
    },
    setCheck (row) {
      // 倒序循环，子节点优先check
      for (let i = this.keyArr.length - 1; i >= 0; i--) {
        const key = row[this.keyArr[i]]
        if (key && this.nodeObj[key]) {
          this.setAllCheck(key)
        }
      }
    },
    setAllCheck (key) {
      var halfCheck = false
      var allCheck = true
      const arr = this.nodeObj[key]
      for (let i = 0; i < arr.length; i++) {
        const temp = arr[i]
        if (this.allChecks[temp]) {
          halfCheck = true
        } else {
          allCheck = false
        }
        if (!allCheck && halfCheck) {
          break
        }
      }
      halfCheck = allCheck ? false : halfCheck
      this.$set(this.indeterminate, key, halfCheck)
      this.$set(this.allChecks, key, allCheck)
    },
    closeModal () {
      this.visible = false
      for (const k in this.allChecks) {
        this.$set(this.allChecks, k, false)
        if (this.indeterminate[k]) {
          this.$set(this.indeterminate, k, false)
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
.setrole {
  width: 100%;
}
</style>
