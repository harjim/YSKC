<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="菜单名称">
        <a-input v-model="queryParam.menuName" placeholder="请输入菜单名称" style="width:180px"/>
      </a-form-item>
      <a-form-item label="状态">
        <a-select placeholder="请选择" style="width:180px" v-model="queryParam.status">
          <a-select-option value="-1">请选择</a-select-option>
          <a-select-option value="0">隐藏</a-select-option>
          <a-select-option value="1">显示</a-select-option>
        </a-select>
      </a-form-item>
      <span class="table-page-search-submitButtons" v-if="$auth('sys:menu:search')">
        <a-button type="primary" @click="getData">查询</a-button>
      </span>
      <span>
        <a-button
          style="margin-left:20px"
          type="primary"
          @click="handleadd(menuTree)"
          v-if="$auth('sys:menu:add')"
        >添加</a-button>
      </span>
    </a-form>
    <a-table
      rowKey="id"
      ref="table"
      :loading="loading"
      size="default"
      :columns="columns"
      :dataSource="tableData"
      :pagination="false"
    >
      <span slot="action" slot-scope="text, record">
        <a @click="handleEdit(record, menuTree)" v-if="$auth('sys:menu:edit')">编辑</a>
        <a-divider type="vertical"/>
        <a-popconfirm title="是否确定删除?" @confirm="handleDel(record)" v-if="$auth('sys:menu:del')">
          <a>删除</a>
        </a-popconfirm>
      </span>
    </a-table>
    <menu-modal ref="modal" @ok="handleOk" @afterClose="updateTree"></menu-modal>
  </a-card>
</template>

<script>
import MenuModal from './modules/MenuModal'

const dataList = []
const generateList = (data) => {
  for (let i = 0; i < data.length; i++) {
    const node = data[i]
    const key = node.key
    dataList.push({ key, title: node.title })
    if (node.children) {
      generateList(node.children, node.key)
    }
  }
}

const typeArr = ['目录', '菜单', '按钮']
const statusArr = ['隐藏', '显示']
const platArr = ['电脑端', '移动端']

export default {
  name: 'MsMenuManageTab',
  components: {
    MenuModal
  },
  props: {
    msType: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      menuTree: [],
      menuId: 0,
      // 查询参数
      queryParam: {},
      tableData: [],
      // 表头
      columns: [{
        title: '菜单名称',
        dataIndex: 'name',
        key: 'name'
      }, {
        title: '层级',
        dataIndex: 'level',
        key: 'level'
      }, {
        title: '排序',
        dataIndex: 'seq',
        key: 'seq'
      }, {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        customRender: function (text, record, index) {
          if (text < 3) {
            return typeArr[text]
          }
        }
      }, {
        title: '请求地址',
        dataIndex: 'url',
        key: 'url'
      }, {
        title: '图标',
        dataIndex: 'icon',
        key: 'icon'
      }, {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        customRender: function (text, record, index) {
          if (text < 3) {
            return statusArr[text]
          }
        }
      }, {
        title: '有无年份',
        dataIndex: 'hasYearSelect',
        key: 'hasYearSelect',
        customRender: function (text, record, index) {
          if (text === true) {
            return '有'
          } else {
            return '无'
          }
        }
      }, {
        title: '备注',
        dataIndex: 'remark',
        key: 'remark'
      }, {
        title: '操作',
        dataIndex: 'action',
        scopedSlots: { customRender: 'action' }
      }],
      permissionList: [],
      visible: false,
      title: '',
      loading: false,
      form: this.$form.createForm(this, { scroll: {} })
    }
  },
  mounted () {
    this.loadTree()
    if (this.msType === 1) {
      this.columns = [{
        title: '菜单名称',
        dataIndex: 'name',
        key: 'name'
      }, {
        title: '层级',
        dataIndex: 'level',
        key: 'level'
      }, {
        title: '排序',
        dataIndex: 'seq',
        key: 'seq'
      }, {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        customRender: function (text, record, index) {
          if (text < 3) {
            return typeArr[text]
          }
        }
      }, {
        title: '请求地址',
        dataIndex: 'url',
        key: 'url'
      }, {
        title: '图标',
        dataIndex: 'icon',
        key: 'icon'
      }, {
        title: '平台',
        dataIndex: 'platform',
        key: 'platform',
        customRender: function (text, record, index) {
          if (text < 3) {
            return platArr[text]
          }
        }
      }, {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        customRender: function (text, record, index) {
          if (text < 3) {
            return statusArr[text]
          }
        }
      }, {
        title: '备注',
        dataIndex: 'remark',
        key: 'remark'
      }, {
        title: '操作',
        dataIndex: 'action',
        scopedSlots: { customRender: 'action' }
      }]
    } else if (this.msType === 3) {
      this.columns = [{
        title: '菜单名称',
        dataIndex: 'name',
        key: 'name'
      }, {
        title: '层级',
        dataIndex: 'level',
        key: 'level'
      }, {
        title: '排序',
        dataIndex: 'seq',
        key: 'seq'
      }, {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        customRender: function (text, record, index) {
          if (text < 3) {
            return typeArr[text]
          }
        }
      }, {
        title: '请求地址',
        dataIndex: 'url',
        key: 'url'
      }, {
        title: '图标',
        dataIndex: 'icon',
        key: 'icon'
      }, {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        customRender: function (text, record, index) {
          if (text < 3) {
            return statusArr[text]
          }
        }
      }, {
        title: '备注',
        dataIndex: 'remark',
        key: 'remark'
      }, {
        title: '操作',
        dataIndex: 'action',
        scopedSlots: { customRender: 'action' }
      }]
    } else {
      this.columns = [{
        title: '菜单名称',
        dataIndex: 'name',
        key: 'name'
      }, {
        title: '层级',
        dataIndex: 'level',
        key: 'level'
      }, {
        title: '排序',
        dataIndex: 'seq',
        key: 'seq'
      }, {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        customRender: function (text, record, index) {
          if (text < 3) {
            return typeArr[text]
          }
        }
      }, {
        title: '请求地址',
        dataIndex: 'url',
        key: 'url'
      }, {
        title: '图标',
        dataIndex: 'icon',
        key: 'icon'
      }, {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        customRender: function (text, record, index) {
          if (text < 3) {
            return statusArr[text]
          }
        }
      }, {
        title: '菜单类型',
        dataIndex: 'menuType',
        key: 'menuType',
        customRender: function (text, record, index) {
          if (text === 1) {
            return '集团菜单'
          }
          return '普通菜单'
        }
      }, {
        title: '有无年份',
        dataIndex: 'hasYearSelect',
        key: 'hasYearSelect',
        customRender: function (text, record, index) {
          if (text === true) {
            return '有'
          } else {
            return '无'
          }
        }
      }, {
        title: '备注',
        dataIndex: 'remark',
        key: 'remark'
      }, {
        title: '操作',
        dataIndex: 'action',
        scopedSlots: { customRender: 'action' }
      }]
    }
  },
  watch: {
    msType (key) {
      this.loadTree()
    }
  },
  methods: {
    loadTree () {
      this.$http.get('/menu/tree', { params: { menuManageType: this.msType } })
        .then(res => {
          this.menuTree = [...res.data]
          this.menuId = this.menuTree[0].key // 获取第一个菜单id
          generateList(this.menuTree)
          this.getData()
        })
    },
    handleDel (record) {
      this.$http.post('/menu/del', { id: record.id, menuManageType: this.msType }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.loadTree()
      })
    },
    handleEdit (record, menuTree) {
      record.menuManageType = this.msType
      this.$refs.modal.edit(record, menuTree, this.msType)
    },
    handleadd (menuTree) {
      this.$refs.modal.add(this.msType, menuTree)
    },
    async updateTree () {
      await this.loadTree()
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
        this.loadTree()
      } else {
        this.$message.success('更新成功')
        this.getData()
      }
    },
    getData () {
      this.queryParam.menuManageType = this.msType
      this.$http.get('/menu/getAppMenuList', { params: this.queryParam })
        .then(res => {
          this.tableData = res.data
          return this.tableData
        })
    }
  }
}
</script>
