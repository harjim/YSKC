<template>
  <a-spin :spinning="spin" tip="请稍后...">
    <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
      <a-form layout="inline">
        <a-form-item label="项目名称">
          <a-input v-model="projectName" placeholder="请输入项目名称" style="widht:200px" />
        </a-form-item>
        <a-form-item label="扶持部门">
          <a-input v-model="supportDeptName" placeholder="请输入扶持部门" style="widht:200px" />
        </a-form-item>
        <a-form-item label="扶持时间">
          <a-input v-model="supportTime" placeholder="请输入扶持时间" />
        </a-form-item>
        <a-form-item label="验收时间">
          <a-date-picker format="YYYY-MM-DD" v-model="checkTime" placeholder="请输入验收时间" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="initializeData" v-if="$auth('company:support:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <vxe-grid
          id="company:support"
          resizable
          ref="table"
          :data="tableData"
          highlight-hover-row
          show-overflow
          auto-resize
          :toolbar="{zoom:true,custom:true,refresh:{query:initializeData}}"
          :customConfig="{storage: { visible: true, resizable: true } }"
        >
          <template v-slot:buttons>
            <span>
              <a-button type="primary" @click="$refs.creatModal.add()" v-if="$auth('company:support:add')">添加</a-button>
            </span>
          </template>
          <vxe-table-column title="序号" type="seq" :width="50"></vxe-table-column>
          <vxe-table-column title="年份" field="syear" :width="70" align="center" sortable></vxe-table-column>
          <vxe-table-column title="项目名称" field="projectName" :min-width="200" sortable />
          <vxe-table-column title="项目建设期间" field="mainBusinessIncome" :width="180" align="center">
            <template slot-scope="{row}">{{ row.startTime +"~" +row.endTime }}</template>
          </vxe-table-column>
          <vxe-table-column title="扶持时间" field="supportTime" :width="120" align="left" sortable />
          <vxe-table-column title="扶持部门" field="supportDeptName" :width="130" sortable />
          <vxe-table-column
            title="扶持金额（万元）"
            field="supportAmount"
            key="supportAmount"
            align="right"
            :width="146"
            sortable
          />
          <vxe-table-column title="验收时间" field="checkTime" :width="120" align="center" sortable />
          <vxe-table-column title="验收结果" field="checkResult" :width="110" sortable></vxe-table-column>
          <vxe-table-column title="备注" field="remark" :width="110"></vxe-table-column>
          <vxe-table-column title="操作" :width="120" align="center" fixed="right">
            <span slot-scope="{row}">
              <a @click="$refs.creatModal.edit(row)" v-if="$auth('company:support:edit')">编辑</a>
              <a-divider type="vertical" v-if="$auth('company:support:delete')" />
              <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)" v-if="$auth('company:support:delete')">
                <a>删除</a>
              </a-popconfirm>
            </span>
          </vxe-table-column>
        </vxe-grid>
      </div>
      <support-modal ref="creatModal" @ok="handleOk"></support-modal>
    </a-card>
  </a-spin>
</template>
<script>
import moment from 'moment'
import SupportModal from './modules/SupportModal'
export default {
  components: {
    SupportModal,
    moment
  },
  data () {
    return {
      spin: false,
      id: null,
      checkTime: undefined,
      supportTime: undefined,
      supportDeptName: undefined,
      projectName: undefined,
      tableData: []
    }
  },
  created () {
    this.initializeData()
  },
  methods: {
    initializeData () {
      this.spin = true
      this.$http.get('/support/querySupportList', { params: { checkTime: this.checkTime, supportTime: this.supportTime, supportDeptName: this.supportDeptName, projectName: this.projectName } })
        .then(res => {
          this.tableData = res.data
          this.spin = false
          return res.data
        })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.initializeData()
    },
    handleDel (record) {
      this.$http.get('/support/delSupport', { params: { id: record.id } }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.initializeData()
      })
    }
  }
}
</script>
