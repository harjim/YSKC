<template>
  <a-modal
    :width="900"
    :visible="visible"
    :title="title"
    style="top: 47px"
    destroyOnClose
    @ok="false"
    @cancel="visible = false"
    :maskClosable="false"
    :footer="null"
    :confirmLoading="confirmLoading"
    :getContainer="getContainer"
  >
    <a-spin tip="请稍后..." :spinning="spinning">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="6" :sm="24">
              <a-form-item label="姓名" >
                <a-input v-model="ename" placeholder="请输入姓名"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="工号">
                <a-input v-model="enumber" placeholder="请输入工号"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item label="人员类型">
                <a-select placeholder="请选择" v-model="etype">
                  <a-select-option :value="-1">请选择</a-select-option>
                  <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-button type="primary" @click="search">查询</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <div>
        <a-tabs type="card" @change="changeAddType">
          <a-tab-pane :key="1" tab="研发人员">
            <ystable
              ref="table1"
              :columns="columns"
              :params="{ pDocFileId: pDocFileId, docDate: this.moment(docDate), ename, enumber, etype }"
              queryUrl="rdEmployee/getEmployeeInfoList"
              :checkbox-config="{ checkMethod: disabledCheck }"
              border
              show-overflow
              highlight-hover-row
              auto-resize
              resizable
              :column-config="{resizable: true}"
            >
            </ystable>
          </a-tab-pane>
          <a-tab-pane :key="2" tab="评审人员" :force-render="false">
            <ystable
              ref="table2"
              :columns="columns"
              :params="{ pDocFileId: pDocFileId, docDate: this.moment(docDate), ename, enumber, etype }"
              queryUrl="reviewCommittee/getReviewList"
              :checkbox-config="{ checkMethod: disabledCheck }"
              border
              show-overflow
              highlight-hover-row
              auto-resize
              resizable
              :column-config="{resizable: true}"
            >
            </ystable>
          </a-tab-pane>
        </a-tabs>
        <div style="text-align: right;margin-top:10px">
          <a-button type="primary" @click="addSubmit">添加</a-button>
        </div>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import moment from 'moment'
import { ystable } from '@/components/index'
export default {
  name: 'AddEmployeeModal',
  components: {
    ystable
  },
  watch: {
    allSelectedRows (newRows) {
    }
  },
  data () {
    return {
      domName,
      spinning: false,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      enumber: null,
      ename: null,
      etype: -1,
      pDocFileId: undefined,
      docDate: undefined,
      addType: 1,
      checkedArr: [],
      columns: [
        {
          type: 'checkbox',
          width: 60,
          headerAlign: 'center',
          align: 'center'
        },
        {
          field: 'ename',
          title: '姓名',
          Width: 60,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center',
          remoteSort: true
        },
        {
          field: 'enumber',
          title: '工号',
          Width: 60,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center',
          remoteSort: true
        },
        {
          field: 'deptName',
          title: '部门',
          Width: 60,
          headerAlign: 'center',
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          align: 'center',
          remoteSort: true
        }
      ]
    }
  },
  created () {
  },
  props: {
    month: {
      type: String,
      default: ''
    },
    isShowEType: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    moment,
    open (docDate, pDocFileId, checkedArr) {
      this.visible = true
      this.title = '添加人员'
      this.docDate = docDate
      this.pDocFileId = pDocFileId
      this.confirmLoading = false
      this.checkedArr = checkedArr
    },
    changeAddType (value) {
      if ((this.ename !== null && this.ename !== '') || (this.enumber !== null && this.enumber !== '') || this.etype !== -1) {
        this.ename = null
        this.enumber = null
        this.etype = -1
        // 视图加载后更新表格
        this.$nextTick(() => {
          this.$refs[`table${3 - value}`].refresh(true)
        })
      }
      this.addType = value
    },
    disabledCheck ({ row }) {
      return !this.checkedArr.includes(row.enumber)
    },
    getContainer () {
      return popupContainer(this.domName)
    },
    addSubmit () {
      const table = this.addType === 1 ? 'table1' : 'table2'
      const selectRecords = this.$refs[table].getCheckboxRecords()
      this.$emit('addEmployeeList', selectRecords)
      this.visible = false
    },
    search () {
      const table = this.addType === 1 ? 'table1' : 'table2'
      this.$refs[table].refresh(true)
    }
  }
}
</script>

<style scoped>
</style>
