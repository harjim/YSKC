<template>
  <a-modal
    :width="900"
    :visible="visible"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false"
    :footer="null"
    :confirmLoading="confirmLoading"
    :getContainer="getContainer"
  >
    <a-spin tip="请稍后..." :spinning="spinning">
      <div>
        <a-table
          bordered
          rowKey="id"
          :dataSource="list"
          :pagination="false"
          size="small"
          :scroll="{x:800, y:480}"
          :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange,getCheckboxProps:getCheckboxProps }"
        >
          <a-table-column title="试制日期" data-index="trialDate" :width="150" align="center">
            <template slot-scope="text">{{ moment(text).format('YYYY-MM-DD') }}</template>
          </a-table-column>
          <a-table-column title="开始时间" data-index="startTime" :width="150" align="center">
            <template slot-scope="text" >
              {{ text === null || text ==='' || text === 'undefined' ? '-' : moment(text).format('HH:mm') }}
            </template>
          </a-table-column>
          <a-table-column title="结束时间" data-index="endTime" :width="150" align="center">
            <template slot-scope="text" >
              {{ text === null || text ==='' || text === 'undefined' ? '-' : moment(text).format('HH:mm') }}
            </template>
          </a-table-column>
          <a-table-column title="计划试制量" data-index="planYield" :width="150" align="center">
            <template slot-scope="text" >
              {{ text === null || text ==='' || text === 'undefined' ? 0 : text }}
            </template>
          </a-table-column>
          <a-table-column title="实际试制量" data-index="rdYield" :width="150" align="center">
            <template slot-scope="text" >
              {{ text === null || text ==='' || text === 'undefined' ? 0 : text }}
            </template>
          </a-table-column>
          <a-table-column title="单位" data-index="unit" :width="150" align="center"></a-table-column>
          <a-table-column title="地点" data-index="deptName" :width="150" align="center"></a-table-column>
        </a-table>
        <div style="text-align: right;margin-top:10px">
          <a-button type="primary" @click="addSubmit">添加</a-button>
        </div>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import { STable, Ellipsis, SelectDown } from '@/components'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import moment from 'moment'

export default {
  name: 'EmployeeModal',
  components: {
    STable,
    Ellipsis,
    SelectDown
  },
  watch: {
    allSelectedRows (newRows) {
    }
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    docFileId: {
      type: Number,
      default: 0
    },
    stage: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      domName,
      list: [],
      spinning: false,
      mySelectedKeys: [],
      selectItem: {},
      selectedRowKeys: [],
      selectedRows: [],
      allSelectedRows: [],
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      queryParam: { deptId: undefined, isMember: '-1', month: this.month === '' ? null : moment(this.month + '-' + '01' + ' 00:00:00'), projectId: 0, ename: null, enumber: null, rdDeptId: null },
      visible: false,
      confirmLoading: false,
      ids: []
    }
  },
  created () {
  },
  methods: {
    loadData () {
      this.spinning = true
      const { projectId, docFileId, stage } = this
      return this.$http.get('/trialProd/getDocFileTrials', { params: { projectId, docFileId, stage } })
        .then(res => {
          this.list = res.data
          this.list = this.list.map((item, index) => {
            item['num'] = index + 1
            return item
          })
          return res.data
        }).finally(res => {
          this.spinning = false
        })
    },
    getContainer () {
      return popupContainer(this.domName)
    },
    addSubmit () {
      if (this.selectedRows.length > 0) {
        const rows = this.selectedRows
        const saveAry = []
        const pdocFileId = this.docFileId
        rows.forEach((item, intext) => {
          const tempObj = { pdocFileId, trialId: item.id }
          saveAry.push(tempObj)
        })
        this.$http.post('/docFileTrial/addDocFileTrial', saveAry).then(res => {
          this.$emit('addMemberList', this.selectedRows)
          this.visible = false
        })
      }
      this.visible = false
    },
    onSelect (value) {
      this.queryParam.etype = value
    },
    search () {
      this.selectedRowKeys = []
      this.selectedRows = []
      this.selectItem = {}
      this.allSelectedRows = []
      this.mySelectedKeys = []
    },
    moment,
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
      this.concatSelectRows(selectedRows)
    },
    concatSelectRows (selectedRows) {
      this.allSelectedRows.push(...selectedRows)
      this.allSelectedRows = Array.from(new Set(this.allSelectedRows))
    },
    getPostItems () {
      var itemRows = []
      for (var i = 0; i < this.allSelectedRows.length; i++) {
        var item = this.allSelectedRows[i]
        if (this.selectedRowKeys.indexOf(item.enumber) !== -1) {
          itemRows.push(item)
        }
      }
      return itemRows
    },
    open (ids) {
      this.search()
      this.title = '试制信息'
      this.visible = true
      this.loadData()
      this.ids = ids
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

<style scoped>
</style>
