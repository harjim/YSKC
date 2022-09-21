<!--
    * @Author:
    * @Date: 2022-08-08 10:08:55
    * @Description:
-->
<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后" :spinning="spinning">
      <a-form class="form" labelAlign="right" layout="inline">
        <!-- <a-row>
          <a-col :span="6"> -->
        <a-form-item label="工号">
          <a-input v-model="params.enumber" placeholder="请输入工号" allowClear/>
        </a-form-item>
        <!-- </a-col>
          <a-col :span="6"> -->
        <a-form-item label="姓名">
          <a-input v-model="params.ename" placeholder="请输入姓名" allowClear/>
        </a-form-item>
        <!-- </a-col>
          <a-col :span="6"> -->
        <a-form-item label="部门">
          <a-input v-model="params.deptName" placeholder="请输入部门" allowClear/>
          <!-- <SelectDown
            ref="deptSelect"
            treeType="dept"
            placeholder="请选择部门"
            @select="(path, title) => {params.deptId = path}"
          /> -->
        </a-form-item>
        <!-- </a-col>
          <a-col :span="6"> -->
        <a-form-item label="职位">
          <a-input v-model="params.position" placeholder="请输入职位" allowClear/>
        </a-form-item>
        <!-- </a-col>
          <a-col :span="6"> -->
        <a-form-item label="职称">
          <a-input v-model="params.title" placeholder="请输入职称" allowClear/>
        </a-form-item>
        <!-- </a-col>
          <a-col :span="6"> -->
        <a-form-item label="身份证号">
          <a-input v-model="params.idNumber" placeholder="请输入身份证号" allowClear/>
        </a-form-item>
        <!-- </a-col>
          <a-col :span="6"> -->
        <a-form-item label="学历">
          <a-select
            v-model="params.eduLevel"
            :allowClear="true"
            placeholder="请选择学历"
            style="width: 174px;"
          >
            <a-select-option v-for="level in eduLevel" :key="level.key" :value="Number(level.key)">{{ level.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <!-- </a-col>
          <a-col :span="6"> -->
        <a-form-item label="专业">
          <a-input v-model="params.specialities" placeholder="请输入专业" allowClear/>
        </a-form-item>
        <!-- </a-col>
          <a-col :span="6"> -->
        <div class="table-page-search-submitButtons buttonDiv">
          <!-- v-if="$auth('rdorg:rdEmployee:search')" -->
          <a-button
            type="primary"
            @click="search()"
            v-if="$auth('rdorg:stEmployee:search')"
          >查询</a-button>
        </div>
        <!-- </a-col>
        </a-row> -->
      </a-form>
      <!-- @completed="({data:{footer}}) => completed(footer)" -->
      <div class="table">
        <ystable
          ref="ysTable"
          queryUrl="stEmployee/getList"
          :params="params"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          :toolbar="tableToolbar"
          :columns="columns"
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          max-height="100%"
        >
          <template #buttons>
            <div>
              <a-button
                type="primary"
                @click="$refs.addModel.add(currentYear, '添加科技管理人员')"
                style="margin-right: 10px;"
                v-if="$auth('rdorg:stEmployee:add')"
              >添加</a-button>
              <a-button
                type="primary"
                :disabled="selectedRowKeys.length === 0"
                style="margin-right: 10px;"
                v-if="$auth('rdorg:stEmployee:del')"
                @click="delCheck()"
              >删除</a-button>
              <a-button
                type="primary"
                @click="openUploadModal"
                style="margin-right: 10px;"
                v-if="$auth('rdorg:stEmployee:import')"
              >导入</a-button>
              <a-button
                type="primary"
                @click="exportRdEmp"
                style="margin-right: 10px;"
                v-if="$auth('rdorg:stEmployee:export')"
                :loading="exportSpin"
              >导出</a-button>
            </div>
          </template>
          <template #operate="{ row }">
            <a-popconfirm
              v-if="$auth('rdorg:stEmployee:del')"
              title="是否确定删除?"
              @confirm="debounceDel([row.id])"
            >
              <a>删除</a>
            </a-popconfirm>
          </template>
        </ystable>
      </div>
      <SelectEmployeeModal ref="addModel" @ok="handleOk" queryUrl="employee/selectStEmployees" saveUrl="stEmployee/addStEmployee"></SelectEmployeeModal>
      <uploadModal
        :sampleData="sampleData"
        :showProgress="true"
        :paramData="params"
        paramKey="tableField"
        title="导入科技管理人员"
        templateName="科技管理人员模板"
        ref="uploadModal"
        action="/doc/stEmployee/importStEmployee"
        @onSuccess="success"
        @error="error"
      />
    </a-spin>
  </a-card>
</template>

<script>
import { SelectDown, UploadModal } from '@/components'
import ystable from '@/components/Table/ystable'
import SelectEmployeeModal from './modules/selectEmployeeModal.vue'
import yearMiXin from '@/utils/yearMixin'

export default {
  data () {
    return {
      spinning: false,
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true
      },
      params: { },
      eduLevel: [
        { key: '-1', label: '请选择' },
        { key: '0', label: '无' },
        { key: '7', label: '初中' },
        { key: '1', label: '高中' },
        { key: '2', label: '中专' },
        { key: '3', label: '大专' },
        { key: '4', label: '本科' },
        { key: '5', label: '硕士' },
        { key: '6', label: '博士' }
      ],
      columns: [
        {
          type: 'checkbox',
          width: 60,
          headerAlign: 'center',
          align: 'center'
        },
        {
          field: 'enumber',
          title: '工号',
          width: 80,
          align: 'left',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'ename',
          title: '姓名',
          width: 100,
          align: 'left',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'deptName',
          title: '部门',
          width: 120,
          align: 'left',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'position',
          title: '职位',
          width: 120,
          align: 'left',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'birthday',
          title: '出生日期',
          width: 130,
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'gender',
          title: '性别',
          width: 80,
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'title',
          title: '职称',
          width: 130,
          align: 'left',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'idNumber',
          title: '身份证号',
          width: 150,
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'eduLevel',
          title: '学历',
          width: 150,
          align: 'left',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true,
          formatter: this.eduLevelFormatter
        },
        {
          field: 'specialities',
          title: '专业',
          width: 130,
          align: 'left',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'edate',
          title: '入职日期',
          width: 130,
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: 'leaveDate',
          title: '离职日期',
          width: 130,
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          remoteSort: true
        },
        {
          field: '',
          title: '操作',
          width: 80,
          align: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          slots: { default: 'operate' }
        }
      ],
      selectedRowKeys: [],
      sampleData: [{
        enumber: '格式：2020或AC20191128',
        ename: '格式：张三',
        deptName: '格式：/xxxx研发中心/xxxx组/',
        position: '格式：管理人员职位',
        birthday: '格式：2019-6-6',
        gender: '格式：男',
        title: '格式：xxx',
        idNumber: '格式：xxx',
        eduLevel: '格式：xx',
        specialities: '格式：xx',
        edate: '格式：2019-6-6',
        leaveDate: '格式：2019-6-6'
      }],
      tableField: {
        tableId: 'importStEmployeeTable',
        fieldTitleObject: {
          enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
          ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
          deptName: { title: '部门', required: false, defaultTitle: '部门', importField: true },
          position: { title: '职位', required: false, defaultTitle: '职位', importField: true },
          birthday: { title: '出生日期', required: false, defaultTitle: '出生日期', importField: true },
          gender: { title: '性别', required: false, defaultTitle: '性别', importField: true },
          title: { title: '职称', required: false, defaultTitle: '职称', importField: true },
          idNumber: { title: '身份证号', required: false, defaultTitle: '身份证号', importField: true },
          eduLevel: { title: '学历', required: false, defaultTitle: '学历', importField: true },
          specialities: { title: '专业', required: false, defaultTitle: '专业', importField: true },
          edate: { title: '入职日期', required: false, defaultTitle: '入职日期', importField: true },
          leaveDate: { title: '离职日期', required: false, defaultTitle: '离职日期', importField: true }
        }
      },
      exportSpin: false
    }
  },
  mixins: [yearMiXin],

  components: {
    SelectDown,
    ystable,
    SelectEmployeeModal,
    UploadModal
  },
  mounted () {
    this.params.year = this.currentYear

    /**
     * 删除防抖
     */
    this.debounceDel = this.$debounce(this.handleDel)
  },
  methods: {
    search (flag = true) {
      if (this.$refs.ysTable && this.$refs.ysTable.refresh) {
        this.params.year = this.currentYear
        this.$refs.ysTable.refresh(flag)
      }
    },
    addEmployee () {
      this.$refs.addModel.add(this.currentYear)
    },
    handleOk (flag) {
      this.selectedRowKeys = []

      Object.keys(this.params).forEach(key => {
        this.params[key] = null
      })
      this.params.year = this.currentYear
      this.search(flag)
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    },
    handleDel (ids = this.selectedRowKeys) {
      this.spinning = true
      this.$http.post('stEmployee/delStEmployee', { year: this.currentYear, ids }).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.handleOk(true)
        } else {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    delCheck () {
      // 提示确认删除框
      const self = this
      this.$confirm({
        title: '您确认要删除选择的科技管理人员吗？',
        onOk () {
          return self.debounceDel()
        },
        onCancel () {
        }
      })
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {
          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.handleOk(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    openUploadModal () {
      this.params.year = this.currentYear
      this.$refs.uploadModal.show(this.tableField)
    },
    exportRdEmp () {
      this.exportSpin = true
      this.$exportData('/stEmployee/export', this.params,
        '', this.$message).finally(() => {
        this.exportSpin = false
      })
    },
    eduLevelFormatter ({ cellValue, row, column }) {
      const level = this.eduLevel.find(elem => Number(elem.key) === cellValue)
      if (level) {
        return level.label
      }
      return ''
    }
  }
}
</script>

<style lang='less' scoped>
.form {
  /* display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr)); */
  display: flex;
  flex-wrap: wrap;
}
// /deep/ .ant-form-item {
//   display: flex;
//   margin: 0;
// }
// /deep/ .ant-form-item-label {
//   width: 80px;
// }
/deep/ .ant-form-item-control-wrapper {
  width: 174px;
}
// .buttonDiv {
//   // display: flex;
//   // grid-column-end: 5;
//   // justify-content: flex-end;
//     margin: 4px 0 0 20px;
// }

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
