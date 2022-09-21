<!--
 * @Author: ldx
 * @Date: 2020-11-25 16:36:10
 * @LastEditTime: 2021-05-26 13:50:05
 * @LastEditors: ldx
 * @Description: （停用）
 * @FilePath: \RS-VUE\src\docTemplate\Templates\modules\MaterialModal.vue
-->
<template>
  <a-modal
    :width="1400"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    :confirmLoading="loading"
    @cancel="visible = false"
  >
    <div class="table-page-search-wrapper">
      <a-form>
        <a-row :gutter="24">
          <a-col :span="6">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="物料名称">
              <a-input placeholder="请输入物料名称" v-model="queryParam.mname" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="物料编码">
              <a-input placeholder="请输入物料编码" v-model="queryParam.mcode" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="物料类型">
              <a-select v-model="queryParam.etype" placeholder="请选择物料类型">
                <a-select-option value="-1">请选择物料类型</a-select-option>
                <a-select-option value="20000">研发材料</a-select-option>
                <a-select-option value="20301">中间试制</a-select-option>
                <a-select-option value="20601">修理材料</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门">
              <a-input placeholder="请输入部门" v-model="queryParam.deptName" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="研发部门">
              <select-down
                ref="rdDeptSelet"
                @fullPath="getRdDeptPath"
                treeType="rdDept"
                placeholder="请选择研发部门"
              />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-button @click="search" type="primary">查询</a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <s-table
      ref="table"
      size="small"
      rowKey="id"
      :columns="columns"
      :data="getData"
      :alert="options.alert"
      showPagination="auto"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange,getCheckboxProps:getCheckboxProps}"
    ></s-table>
  </a-modal>
</template>

<script>
import { STable } from '@/components'
import SelectDown from '@/components/SelectDown'
const columns = [{
  dataIndex: 'mcode',
  key: 'mcode',
  title: '资产代码',
  align: 'left'
}, {
  dataIndex: 'mname',
  key: 'mname',
  title: '物料名称',
  align: 'left'
}, {
  dataIndex: 'rdType',
  key: 'etype',
  title: '类型',
  align: 'left',
  customRender: (text, record, index) => {
    if (text === 20000) {
      return '研发材料'
    }
    if (text === 20301) {
      return '中间试制'
    }
    if (text === 20601) {
      return '修理材料'
    }
    return ''
  }
}, {
  dataIndex: 'deptName',
  key: 'deptName',
  title: '部门',
  align: 'left'
}, {
  dataIndex: 'rdDeptName',
  key: 'rdDeptName',
  title: '研发部门',
  align: 'left'
}
]

export default {
  name: 'MaterialModal',
  components: {
    STable,
    SelectDown
  },
  data () {
    return {
      loading: false,
      selectItem: {},
      selectedRowKeys: [],
      selectedRows: [],
      columns,
      title: '',
      pagination: [],
      options: [],
      queryParam: {},
      dataItem: [],
      ids: [],
      isFirst: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      getData: parameter => {
        return this.$http.get('/projectMaterial/getDocMaterialList', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            return res.data
          })
      },
      visible: false,
      confirmLoading: false
    }
  },
  methods: {
    search () {
      this.$refs.table.refresh(true)
    },
    add (ids, projectId) {
      this.ids = ids
      this.selectedRowKeys = []
      this.selectedRows = []
      this.title = '添加物料'
      this.visible = true
      this.loading = false
      this.queryParam = {}
      this.queryParam.projectId = projectId
      if (this.$refs.deptSelet) {
        this.$refs.deptSelet.setValue(0)
      }
      if (this.$refs.rdDeptSelet) {
        this.$refs.rdDeptSelet.setValue(0)
      }
      if (this.isFirst) {
        this.search()
      }
      this.isFirst = true
    },
    handleSubmit () {
      if (this.selectedRows.length <= 0) {
        this.$message.info('未选择任何设备')
        return
      }
      this.$message.success('添加成功')
      this.$emit('ok', this.selectedRows)
      this.visible = false
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    getRdDeptPath (rdDeptPath) {
      this.queryParam.rdDeptPath = rdDeptPath
    },
    getCheckboxProps (record) {
      if (this.ids.indexOf(record.id) >= 0) {
        return {
          props: {
            disabled: true,
            multiple: record.multiple
          }

        }
      }
      return {
        props: {
          disabled: false,
          multiple: record.multiple
        }
      }
    }
  }
}
</script>
