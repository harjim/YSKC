<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline" :form="form">
        <a-form-item label="项目年份">
          <a-select
            placeholder="请选择年份"
            v-model="queryParams.pyear"
            :allowClear="true"
            :options="years"
            style="width:174px;"
          ></a-select>
        </a-form-item>
        <a-form-item label="项目名称">
          <a-input v-model="queryParams.pname" placeholder="请输入项目名称" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" v-if="$auth('tech:pro:search')" @click="search(true)">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/techProject/getTechProjects"
          :params="queryParams"
          highlight-current-row
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          @checkbox-all="selectChange"
          @checkbox-change="selectChange"
          :toolbar="{ refresh:true, zoom: true, custom: true, slots: { buttons: 'toolbar_buttons' } }" >
          <template v-slot:toolbar_buttons>
            <!-- <a-button type="primary" v-if="$auth('tech:pro:add')" @click="showProjectMoadl()" style="margin-right: 10px;">添加</a-button> -->
            <!-- <a-button
              style="margin-right: 10px;"
              type="primary"
              :disabled="selectedRowKeys.length <= 0"
              v-if="$auth('tech:pro:del')"
              @click="delList"
            >删除</a-button> -->
          </template>
          <vxe-table-column type="checkbox" width="50"/>
          <vxe-table-column
            title="项目名称"
            field="pname"
            min-width="150"
            align="left"
            header-align="center"
            remoteSort>
            <template v-slot="{ row }">
              <a @click="showProjectMoadl(row)">{{ row.pname }}</a>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="年份"
            field="pyear"
            width="90"
            align="center"
            header-align="center"
            remoteSort></vxe-table-column>
          <vxe-table-column
            title="申报方向"
            field="reportType"
            width="150"
            align="left"
            header-align="center"
            remoteSort></vxe-table-column>
          <vxe-table-column
            title="扶持方式"
            field="aidType"
            width="150"
            align="left"
            header-align="center"
            remoteSort></vxe-table-column>
          <vxe-table-column
            title="负责人"
            field="masterName"
            width="100"
            align="left"
            header-align="center"
            remoteSort></vxe-table-column>
          <vxe-table-column
            title="负责人电话"
            field="masterTel"
            width="130"
            align="left"
            header-align="center"
            remoteSort></vxe-table-column>
          <vxe-table-column
            title="联系人"
            field="linkName"
            width="100"
            align="left"
            header-align="center"
            remoteSort></vxe-table-column>
          <vxe-table-column
            title="联系人电话"
            field="linkTel"
            width="130"
            align="left"
            header-align="center"
            remoteSort></vxe-table-column>
          <vxe-table-column title="起止日期" width="190" align="center" header-align="center" remoteSort>
            <template v-slot="{row}">
              {{ moment(row.beginDate).format("YYYY-MM-DD") }}  ~  {{ moment(row.endDate).format("YYYY-MM-DD") }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="申请日期"
            field="applyDate"
            width="120"
            align="center"
            header-align="center"
            remoteSort>
            <template v-slot="{row}">
              {{ moment(row.applyDate).format("YYYY-MM-DD") }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="备注"
            field="remark"
            width="150"
            align="left"
            header-align="center"
            remoteSort></vxe-table-column>
        </ystable>
      </div>
    </a-spin>
    <project-modal ref="modifyModal" @ok="search" />
  </a-card>
</template>

<script>
import { Ellipsis } from '@/components'
import ProjectModal from './modules/ProjectModal'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
export default {
  name: 'Energy',
  components: {
    Ellipsis,
    ProjectModal,
    ystable
  },
  data () {
    return {
      years: [],
      spinning: false,
      form: this.$form.createForm(this),
      queryParams: { pyear: undefined, pname: undefined },
      selectedRowKeys: [],
      record: {}
    }
  },
  mounted () {
    const year = new Date().getFullYear() + 3
    this.years = []
    for (var i = 6; i > 0; i--) {
      var node = { key: (year - i), label: (year - i) }
      this.years.push(node)
    }
  },
  methods: {
    moment,
    handleDel (id) {
      this.spinning = true
      this.$http.post('/techProject/del', { id: id })
        .then(res => {
          if (res.success) {
            this.$message.success('删除成功')
            this.search(true)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
          }
          this.spinning = false
        })
    },
    delList () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的项目吗?',
        onOk () {
          var values = []
          self.selectedRowKeys.forEach(id => {
            values.push({ id: id })
          })

          return self.$http.post('/techProject/dels', values)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.search(true)
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    search (isRefresh, record) {
      this.selectedRowKeys = []
      if (isRefresh) {
        this.$refs.table.refresh(true)
      } else {
        this.record = record
      }
    },
    showProjectMoadl (record) {
      this.record = record
      this.$refs.modifyModal.show(record)
      // this.$refs.modifyModal.edit(record)
    },
    // 表格复选框事件
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>

<style scoped>
</style>
