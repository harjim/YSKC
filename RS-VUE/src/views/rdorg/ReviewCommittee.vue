<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input v-model="queryParams.ename" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item label="工号">
          <a-input v-model="queryParams.enumber" placeholder="请输入工号" />
        </a-form-item>
        <a-form-item label="职称">
          <a-input v-model="queryParams.title" placeholder="请输入职称" autosize="true" />
        </a-form-item>
        <a-form-item label="身份证号">
          <a-input v-model="queryParams.idNumber" placeholder="请输入身份证号" />
        </a-form-item>
        <a-form-item label="职位">
          <a-input v-model="queryParams.position" placeholder="请输入职位" />
        </a-form-item>
        <a-form-item label="专业">
          <a-input v-model="queryParams.specialities" placeholder="请输入专业" />
        </a-form-item>
        <a-form-item label="学历">
          <a-select v-model="queryParams.eduLevel" :allowClear="true" placeholder="请选择学历" style="width: 174px;">
            <a-select-option value="-1">请选择</a-select-option>
            <a-select-option value="0">无</a-select-option>
            <a-select-option value="7">初中</a-select-option>
            <a-select-option value="1">高中</a-select-option>
            <a-select-option value="2">中专</a-select-option>
            <a-select-option value="3">大专</a-select-option>
            <a-select-option value="4">本科</a-select-option>
            <a-select-option value="5">硕士</a-select-option>
            <a-select-option value="6">博士</a-select-option>
          </a-select>
        </a-form-item>
        <span class="table-page-search-submitButtons">
          <a-button v-if="$auth('rdorg:reviewCommittee:search')" type="primary" @click="search(true)">查询</a-button>
        </span>
      </a-form>
      <div class="table">
        <ystable
          ref="table"
          queryUrl="/reviewCommittee/getReviews"
          :params="queryParams"
          highlight-hover-row
          highlight-current-row
          show-overflow
          resizable
          auto-resize
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :seq-config="{ startIndex: 1 }"
          :toolbar="tableToolbar"
          max-height="100%"
        >
          <template v-slot:buttons>
            <a-button
              type="primary"
              @click="$refs.createModal.add(currentYear, '添加评审委员')"
              style="margin-right:10px"
              v-if="$auth('rdorg:reviewCommittee:add')"
            >
              添加
            </a-button>
            <a-button
              type="primary"
              @click="delRdEmployee"
              style="margin-right:10px"
              :disabled="selectedRowKeys.length <= 0"
              v-if="$auth('rdorg:reviewCommittee:del')"
            >
              删除</a-button
            >
            <a-button
              type="primary"
              @click="$refs.SelectDeptPosiModal.open(selectedRowKeys)"
              style="margin-right:10px"
              :disabled="selectedRowKeys.length <= 0"
              v-if="$auth('rdorg:reviewCommittee:dept')"
            >
              设置部门职位
            </a-button>
          </template>
          <vxe-table-column type="checkbox" width="40" fixed="left" />
          <vxe-table-column title="排序" :width="100" field="seq" align="center">
            <template slot-scope="{ row }">
              <template v-if="$auth('rdorg:reviewCommittee:editSeq')">
                <a-input-number
                  style="width: 100%"
                  size="small"
                  :value="row.seq"
                  :min="1"
                  :max="$store.state.maxOrder"
                  @change="v => valueChange(v, row)"
                  @blur="saveValue(row)"
                />
              </template>
              <template v-else>{{ row.seq }}</template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="工号" field="enumber" width="120" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="姓名" field="ename" width="80" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="部门" field="deptName" width="140" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="职位" field="position" width="120" align="left" remoteSort></vxe-table-column>
          <vxe-table-column title="职称" field="title" width="120" align="left" remoteSort></vxe-table-column>
          <vxe-table-column title="身份证号" field="idNumber" width="200" align="center" remoteSort></vxe-table-column>
          <vxe-table-column title="学历" field="eduLevel" width="100" remoteSort align="center">
            <template v-slot="{ row }">
              <span>{{ getEduLevel(row.eduLevel) }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="专业" field="specialities" width="140" align="left" remoteSort></vxe-table-column>
          <vxe-table-column title="入职日期" field="edate" width="120" remoteSort align="center">
            <template v-slot="{ row }">{{ row.edate | formatData }}</template>
          </vxe-table-column>
          <vxe-table-column title="离职日期" field="leaveDate" width="120" align="center" remoteSort>
            <template v-slot="{ row }">{{ row.leaveDate | formatData }}</template>
          </vxe-table-column>
          <vxe-table-column title="备注" field="remark" min-width="140" align="left" remoteSort></vxe-table-column>
        </ystable>
      </div>
      <selectEmployee-modal
        ref="createModal"
        @ok="handleOk"
        saveUrl="/reviewCommittee/addReviews"
        queryUrl="/employee/selectEmployees"
      ></selectEmployee-modal>
      <SelectDeptPosiModal ref="SelectDeptPosiModal" @ok="handleOk" />
    </a-spin>
  </a-card>
</template>

<script>
import { SelectDown } from '@/components'
import moment from 'moment'
import selectEmployeeModal from './modules/selectEmployeeModal'
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'
import SelectDeptPosiModal from './modules/SelectDeptPosiModal.vue'
const eduLevelArr = ['无', '高中', '中专', '大专', '本科', '硕士', '博士', '初中']
let flag = true
export default {
  mixins: [yearMiXin],
  name: 'RdEmployee',
  components: {
    selectEmployeeModal,
    ystable,
    SelectDown,
    SelectDeptPosiModal
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 10 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      spinning: false,
      form: this.$form.createForm(this),
      queryParams: {},
      employeeList: [],
      selectedRowKeys: [],
      tableToolbar: {
        refresh: true,
        zoom: true,
        custom: true
      },
      paramData: { year: this.currentYear },
      // initializeData: parameter => {
      //   this.queryParams.year = this.currentYear
      //   return this.$http.get('/reviewCommittee/getReviews', { params: Object.assign(parameter, this.queryParams) })
      //     .then(res => {
      //       return res.data
      //     })
      // },
      options: {}
    }
  },
  created () {
    this.paramData.year = this.currentYear
    this.queryParams.year = this.currentYear
  },
  filters: {
    formatData (value) {
      if (!value) {
        return ''
      }
      return moment(value).format('YYYY-MM-DD')
    }
  },
  methods: {
    moment,
    saveValue (row) {
      if (flag) {
        var value = {}
        value.seq = row.seq
        value.id = row.id
        this.$http.post('/reviewCommittee/editSeq ', [value]).then(res => {
          if (!res.success && !res.data) {
            this.$message.error(`排序保存失败`)
          }
          this.search(false)
        })
      }
    },
    valueChange (v, record) {
      if (Number(v) && v > 0) {
        this.$set(record, `seq`, v)
        flag = true
      } else {
        flag = false
      }
    },
    search (refresh) {
      this.queryParams.year = this.currentYear
      this.$refs.table.refresh(refresh)
    },
    getEduLevel (index) {
      return eduLevelArr[index]
    },
    handleOk (flag) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(flag)
    },
    // handleDel (record) {
    //   this.$http.post('/rdEmployee/delRdEmployee', { id: record.id }).then(res => {
    //     if (res.success) {
    //       this.$message.success('删除成功')
    //       this.$refs.table.refresh()
    //     } else {
    //       this.$message.error(`[${record.ename}]${res.errorMessage ? res.errorMessage : '删除失败'}`)
    //     }
    //   })
    // },
    delRdEmployee () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的人员吗?',
        // content: <b>已被加计扣除使用的人员不会删除。</b>,
        onOk () {
          return self.$http.post('/reviewCommittee/delReview', { reviewIds: self.selectedRowKeys }).then(res => {
            if (res.success) {
              self.$message.success('删除成功')
              self.selectedRowKeys.length = 0
              self.employeeList = {}
              self.$refs.table.refresh()
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
    selectChange ({ records }) {
      this.employeeList = records
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-spin-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 220px);
}
/deep/ .ant-form-inline {
  height: auto;
  flex-shrink: 0;
}

/deep/ .table {
  flex: 1;
  overflow-y: hidden;
}
</style>
