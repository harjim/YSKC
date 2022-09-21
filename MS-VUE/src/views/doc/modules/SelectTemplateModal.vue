<template>
  <a-modal
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :title="title"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="文件名">
                <a-input v-model="selectEname" placeholder="请输入文件名" />
              </a-form-item>
            </a-col>
            <span style="padding-left:30px;">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
            </span>
          </a-row>
        </a-form>
      </div>
      <div>
        <s-table
          ref="table"
          size="default"
          rowKey="id"
          :columns="columns"
          :data="initializeData"
          :alert="options.alert"
          showPagination="auto"
          :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        ></s-table>
      </div>
    </a-card>
  </a-modal>
</template>
<script>
import { STable } from '@/components'
const columns = [{
  dataIndex: 'docName',
  key: 'docName',
  title: '模板名称',
  align: 'left'
}, {
  dataIndex: 'version',
  key: 'version',
  title: '版本',
  align: 'left'
}, {
  dataIndex: 'createTime',
  key: 'createTime',
  title: '创建时间',
  align: 'center'
}, {
  dataIndex: 'lastUpdateTime',
  key: 'lastUpdateTime',
  title: '最后更新时间',
  align: 'center'
}]

export default {
  name: 'SelectTemplate',
  components: {
    STable
  },
  data () {
    return {
      title: '',
      visible: false,
      form: this.$form.createForm(this),
      selectEname: '',
      confirmLoading: false,
      columns,
      pagination: [],
      selectedRowKeys: [],
      templateList: [],
      id: 0,
      initializeData: parameter => {
        return this.$http.get('/docTemlate/queryDocTemplateList', { params: Object.assign(parameter, { docName: this.selectEname }) })
          .then(res => {
            return res.data
          })
      },
      options: {}
    }
  },
  methods: {
    add (initializeData) {
      this.title = '添加文件模板'
      this.confirmLoading = false
      this.visible = true
      this.flag = -1
      this.form.resetFields()
      this.templateList = initializeData
      this.$nextTick(() => {
        this.templateList.forEach(ele => {
          this.selectedRowKeys.push(ele.templateId)
          // this.selectedRowKeys = ele.id
        })
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.templateList = selectedRows
    },
    handleSubmit () {
      this.visible = false
      this.confirmLoading = false
      this.$emit('ok', this.templateList)
      this.selectedRowKeys = []
      // const { form: { validateFields } } = this
      // this.visible = true
      // this.confirmLoading = true
      // validateFields((errors, values) => {
      //   if (!errors) {
      //     if (this.flag === -1) {
      //       values.templateList = this.templateList
      //       values.seq = this.templateList.length
      //       console.log(values)
      //       this.$http.post('/docTemlate/addProcessDocTemplate', values)
      //         .then(res => { b
      //           if (res.success && res.data) {
      //             this.$refs.table.refresh(true)
      //           } else {
      //             this.$message.info(res.errorMessage)
      //           }
      //         }).finally(res => {
      //           this.confirmLoading = false
      //         })
      //     }
      //   } else {
      //     this.confirmLoading = false
      //   }
      // })
    }
  }
}
</script>
