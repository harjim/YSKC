<!--
 * @Author: ldx
 * @Date: 2020-11-25 16:36:10
 * @LastEditTime: 2021-05-26 13:49:55
 * @LastEditors: ldx
 * @Description: (停用)
 * @FilePath: \RS-VUE\src\docTemplate\Templates\modules\EquipmentModal.vue
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
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="设备名称">
              <a-input placeholder="请输入设备名称" v-model="queryParam.ename" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="设备编码">
              <a-input placeholder="请输入设备编码" v-model="queryParam.ecode" />
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="设备类型">
              <a-select v-model="queryParam.etype" placeholder="请选择设备类型">
                <a-select-option value="-1">请选择设备类型</a-select-option>
                <a-select-option v-for="item in $getEnums('equipmentEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
                <!-- <a-select-option value="30000">设备</a-select-option>
                <a-select-option value="30100">仪器</a-select-option> -->
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
                @fullPath="rdDeptSelected"
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
      rowKey="ecode"
      :columns="columns"
      :data="getData"
      :alert="options.alert"
      showPagination="auto"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange,getCheckboxProps:getCheckboxProps}"
    >
      <template slot="rdDeptRender" slot-scope="text,record">
        <span v-if="record.rdDeptId">
          <tree-key-map :treeKey="record.rdDeptId" :year="rdYear" treeType="rdDept" />
        </span>
      </template>
    </s-table>
  </a-modal>
</template>

<script>
import { STable } from '@/components'
import SelectDown from '@/components/SelectDown'
import yearMixin from '@/utils/yearMixin'
import { mapState } from 'vuex'
const columns = [{
  dataIndex: 'ecode',
  key: 'ecode',
  title: '资产代码',
  align: 'left'
}, {
  dataIndex: 'ename',
  key: 'ename',
  title: '设备名称',
  align: 'left'
}, {
  dataIndex: 'etype',
  key: 'etype',
  title: '类型',
  align: 'left',
  customRender: (text, record, index) => {
    return text ? this.$getEnums('equipmentEnum').find(elem => elem.value === Number(text)) : ''
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
  align: 'left',
  scopedSlots: { customRender: 'rdDeptRender' }
}
]

export default {
  mixins: [yearMixin],
  name: 'EquipmentModal',
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
      ecodes: [],
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
        this.queryParam.year = this.currentYear
        return this.$http.get('/rdEquipment/getDocEquipmentList', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            return res.data
          })
      },
      visible: false,
      confirmLoading: false
    }
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
    search () {
      this.$refs.table.refresh(true)
    },
    add (ecodes, projectId) {
      this.ecodes = ecodes
      this.selectedRowKeys = []
      this.selectedRows = []
      this.title = '添加设备'
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
    rdDeptSelected (rdDeptPath) {
      this.queryParam.rdDeptPath = rdDeptPath
    },
    getCheckboxProps (record) {
      if (this.ecodes.indexOf(record.ecode) >= 0) {
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
