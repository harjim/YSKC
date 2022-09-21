<template>
  <a-modal
    :width="1300"
    :visible="visible"
    :title="title"
    :footer="null"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <div>
      <a-form layout="inline">
        <a-form-item label="设计名称">
          <a-input v-model="selectdName" placeholder="请输入设计名称" />
        </a-form-item>
        <a-form-item label="部门">
          <a-input v-model="deptName" placeholder="请输入部门" />
        </a-form-item>
        <a-form-item label="备注">
          <a-input
            v-model="remark"
            placeholder="请输入备注"
          />
        </a-form-item>
        <a-form-item>
          <span class="table-page-search-submitButtons">
            <a-button type="primary" @click="refreshData">查询</a-button>
          </span>
        </a-form-item></a-form>
    </div>
    <div>
      <ystable
        ref="table"
        size="default"
        rowId="id"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        :toolbar="{zoom:true,custom:true,refresh:true}"
        queryUrl="/design/queryDesignByTerm"
        :params="{ remark: this.remark,name: this.selectdName, projectId: this.id, projectMonth: this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00'), deptName: this.deptName }"
        :columns="columns"
        @completed="({data})=>completed(data)"
        show-overflow="title"
      >
        <template v-slot:buttons>
          <span>
            总计:&nbsp;&nbsp;
            <a>{{ selectCount ? selectCount.toFixed(2) : '-' }}/{{ designCount ? designCount.toFixed(2) : '-' }}</a>
          </span>
        </template>
      </ystable>
    </div>
    <div style="text-align: right;margin-top:10px" v-if="$auth('project:data:agg')">
      <a-tooltip placement="top">
        <template slot="title">
          <span>添加当前条件查询的结果</span>
        </template>
        <a-button type="primary" @click="queryTermSubmit" :loading="confirmLoading">条件添加</a-button>
      </a-tooltip>
      <a-tooltip placement="top">
        <template slot="title">
          <span>添加当前选中的数据</span>
        </template>
        <a-button
          type="primary"
          @click="handleSubmit"
          style="margin-left:20px"
          :loading="confirmLoading"
          :disabled="designList.length <= 0"
        >选中添加</a-button>
      </a-tooltip>
    </div>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
const columns = [
  {
    type: 'checkbox',
    width: 40
  }, {
    field: 'dname',
    title: '设计名称',
    align: 'left',
    minWidth: 200,
    showHeaderOverflow: true,
    showOverflow: 'tooltip',
    remoteSort: true
  }, {
    field: 'designDate',
    title: '设计日期',
    align: 'center',
    width: 160,
    showHeaderOverflow: true,
    showOverflow: 'tooltip',
    remoteSort: true
  }, {
    field: 'deptName',
    title: '部门',
    align: 'left',
    width: 160,
    showHeaderOverflow: true,
    showOverflow: 'tooltip',
    remoteSort: true
  }, {
    field: 'dFee',
    title: '设计费用',
    align: 'right',
    width: 160,
    showHeaderOverflow: true,
    showOverflow: 'tooltip',
    remoteSort: true
  }, {
    field: 'remark',
    title: '备注',
    align: 'left',
    width: 160,
    showHeaderOverflow: true,
    showOverflow: 'tooltip',
    remoteSort: true
  }]
export default {
  name: 'ProjectDesignModal',
  components: {
    ystable
  },
  data () {
    return {
      id: undefined,
      form: this.$form.createForm(this),
      selectdName: '',
      remark: undefined,
      columns,
      first: true,
      title: '',
      visible: false,
      designList: [],
      deptName: undefined,
      projectMonth: undefined,
      designCount: 0,
      selectCount: 0,
      confirmLoading: false
    }
  },
  methods: {
    completed (data) {
      this.selectCount = 0
      this.designList = []
      this.designCount = 0
      if (data) {
        this.designCount = data.header
      }
    },
    moment,
    add (projectId, projectMonth) {
      this.title = '添加设计费用'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = projectId
      this.projectMonth = projectMonth
      this.designCount = 0
      this.selectCount = 0
      this.selectdName = undefined
      this.deptName = undefined
      this.remark = undefined
      this.designList = []
      this.$nextTick(() => {
        if (!this.first) {
          this.refreshData()
        }
        this.first = false
      })
    },
    selectChange ({ records }) {
      this.designList = records
      this.selectCount = 0
      for (let i = 0; i < records.length; i++) {
        this.selectCount += parseFloat(records[i].dFee)
      }
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.projectId = this.id
          if (this.designList.length === undefined || this.designList.length < 1) {
            this.$message.info('请选择数据')
            return
          }
          values.designEntityList = this.designList
          values.projectMonth = moment(this.projectMonth + '-' + '01' + ' 00:00:00')
          this.$http.post('/designProject/addProjectDesign', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    queryTermSubmit () {
      this.confirmLoading = true
      this.$http.post('/designProject/addProjectDesignByTerm', { name: this.selectdName, projectId: this.id, deptName: this.deptName, projectMonth: this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00') }).then(res => {
        if (res.success && res.data) {
          this.visible = false
          this.confirmLoading = false
          this.$emit('ok', true)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
        }
      }).finally(res => {
        this.confirmLoading = false
      })
    },
    refreshData () {
      this.$refs.table.refresh(true)
    }
  }
}
</script>

<style>
.word-wrap {
  word-break: break-all;
}
</style>
