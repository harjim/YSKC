<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    :afterClose="OnAfterClose"
    @ok="handleSubmit"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
    :getContainer="popupContainer"
    :bodyStyle="{ maxHeight: '600px', voerflow: 'auto' }"
  >
    <a-table
      rowKey="id"
      ref="table"
      :loading="loading"
      size="default"
      :dataSource="tableData"
      :pagination="false"
      :scroll="{y:500}"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange,getCheckboxProps:getCheckboxProps }"
    >
      <a-table-column
        title="序号"
        data-index="index"
        key="index"
        align="left"
        class="word-wrap"
        :width="60"
      >
        <template slot-scope="text,record,index">
          <span>{{ index+1 }}</span>
        </template>
      </a-table-column>
      <a-table-column
        title="文档"
        data-index="docName"
        key="docName"
        align="left"
        class="word-wrap"
      />
      <a-table-column
        title="数量"
        data-index="docNum"
        key="docNum"
        align="left"
        class="word-wrap"
        :width="100"
      >
        <template slot-scope="text,record">
          <a-input-number
            v-if="record.multiple"
            :value="text"
            :precision="0"
            :min="1"
            :defaultValue="1"
            :key="`docNum${record.key}`"
            @change="(val)=>onCellChange(Number(val),record,'docNum')"
          />
          <span v-else>1</span>
        </template>
      </a-table-column>
      <a-table-column
        title="备注"
        data-index="remark"
        key="remark"
        align="left"
        class="word-wrap"
      />
      <a-table-column
        title="模板"
        data-index="docFileTemplateId"
        key="docFileTemplateId"
        align="left"
        class="word-wrap"
        :width="120"
      >
        <template slot-scope="text,record">
          <a-select
            style="width:100px"
            :defaultValue="[record.docTplList[0].id]"
            @change="val=>onSelChange(val,record,'docFileTemplateId')"
            v-if="record.docTplList"
          >
            <a-select-option
              v-for="item in record.docTplList"
              :key="item.id"
              :title="item.templateName"
            >{{ item.templateName }}</a-select-option>
          </a-select>
        </template>
      </a-table-column>
    </a-table>
  </a-modal>
</template>

<script>
import { STable } from '@/components'
import { popupContainer } from '@/docTemplate/Templates/js/screenFullMountDom'
export default {
  components: {
    STable
  },
  data () {
    return {
      title: '',
      loading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      confirmLoading: false,
      projectId: 0,
      visible: false,
      form: this.$form.createForm(this),
      tableData: [],
      selectedRowKeys: [],
      docList: [],
      stageKey: undefined,
      propsMap: {},
      templateIds: [],
      chooseFileIds: []
    }
  },
  methods: {
    popupContainer,
    queryDocFile () {
      this.$http.get('/docFile/queryDocFile', { params: { stage: this.stageKey } })
        .then(res => {
          if (res.data && res.data.length) {
            res.data.forEach(e => {
              e.docNum = 1
              this.propsMap[e.id] = { disabled: false, multiple: e.multiple }
            })
            this.tableData = res.data.sort((a, b) => { return a.order - b.order })
          }
          this.templateIds.forEach((id) => { if (this.propsMap[id] && !this.propsMap[id].multiple) { this.propsMap[id].disabled = true } })
        })
    },
    onCellChange (value, record, name) {
      record[name] = value
    },
    onSelChange (value, record, name) {
      record[name] = value
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.docList = selectedRows
    },
    add (projectId, stage, templateIds, chooseFileIds) {
      if (!projectId) {
        this.$message.error('添加过程文件操作失败，请联系管理员！')
        return
      }
      this.title = '[' + stage.stageType + ']' + '添加文档'
      this.projectId = projectId
      this.confirmLoading = false
      this.chooseFileIds = chooseFileIds
      this.stageKey = stage.stageKey
      this.templateIds = templateIds
      this.visible = true
      this.queryDocFile()
    },
    getCheckboxProps (record) {
      return {
        props: this.propsMap[record.id] // record.multiple === false && this.templateIds.indexOf(record.templateId) != -1
      }
    },
    OnAfterClose () {
      this.propsMap = {}
      this.templateIds = []
      this.chooseFileIds = []
      this.stageKey = undefined
      this.projectId = 0
      this.form.resetFields()
      this.tableData = []
      this.selectedRowKeys = []
      this.docList = []
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      if (this.docList.length < 1) {
        this.$message.info('请选中要添加的文档')
        return
      }
      validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          const dList = []
          this.docList.forEach(e => {
            var d = {}
            if (e.docFileTemplateId) {
              d = { id: e.id, docName: e.docName, docFileTemplateId: e.docFileTemplateId, docNum: e.docNum, seq: e.seq, order: e.order }
            } else {
              d = { id: e.id, docName: e.docName, docFileTemplateId: e.docTplList ? e.docTplList[0].id : 0, docNum: e.docNum, seq: e.seq, order: e.order }
            }
            dList.push(d)
          })
          values.docList = dList
          values.stage = this.stageKey
          values.pDocFileIds = this.chooseFileIds // 选择的文档ID，用于插入文档在此ID文档下面
          values.projectId = this.projectId
          this.$http.post('/projectDocFile/addDocList', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
              }
            }).finally(res => {
              this.chooseFileIds = []
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>
