<!--
 * @Author: lzh
 * @Date: 2021-09-09 13:59:14
 * @LastEditors: hm
 * @LastEditTime: 2022-09-14 11:50:24
 * @Description: 委托项目组编制情况 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\TrustOrgForm.vue
-->
<template>
  <a-card>
    <a-form>
      <div class="title">一、研发组织架构</div>
      <div>
        <upload-img
          rowKey=""
          :projectId="projectId"
          @ok="success"
          :maxLength="1"
          :imgs="content.list"/>
      </div>
      <a-divider/>
      <!-- 项目成员 -->
      <div class="title">二、项目组研发人员</div>
      <template>
        <a-button type="primary" @click="handleAdd">添加</a-button>
        <a-table
          bordered
          style="padding-top:10px; width:100%"
          :dataSource="content.memberData"
          :pagination="false"
          size="small"
          rowKey="index"
          :scroll="{y:480}"
        >
          <a-table-column title="序号" data-index="index" :width="60"></a-table-column>
          <a-table-column title="姓名" data-index="ename" align="center">
            <template slot-scope="text,record,index">
              <a-input
                :value="text"
                @change="(e)=>onCellChange(e.target.value,record,'ename')"
                @paste="handlePaste($event, index)"
              />
            </template>
          </a-table-column>
          <a-table-column title="部门" data-index="deptName" align="center">
            <template slot-scope="text,record">
              <a-input
                :value="text"
                @change="(e)=>onCellChange(e.target.value,record,'deptName')"
              />
            </template>
          </a-table-column>
          <a-table-column title="项目角色" data-index="role" align="center">
            <template slot-scope="text,record">
              <a-input
                :value="text"
                @change="(e)=>onCellChange(e.target.value,record,'role')"
              />
            </template>
          </a-table-column>
          <a-table-column title="操作" data-index="key" align="center">
            <template slot-scope="text,record">
              <a @click="handleDelMember(record.index)">移除</a>
            </template>
          </a-table-column>
        </a-table>
      </template>
    </a-form>
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import ResultUploadModal from '@/views/project/modules/ResultUploadModal'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import upload from '@/components/UploadModal/TrustUpload.vue'
import uploadImg from '@/components/UploadImg/UploadImg.vue'

export default {
  name: 'TrustOrgForm',
  components: {
    ResultUploadModal,
    upload,
    uploadImg
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    docFileId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  mounted () {
    this.$getDictionary(6).then(res => {
      this.stageLists = res
      for (const stage of res) {
        this.stageMap[stage.key] = stage.value
      }
    })
    this.paramData.projectId = this.projectId
  },
  created () {
    this.content = getTemplateContent('TrustOrgForm')
    this.BPContent = getTemplateContent('TrustOrgForm')
  },
  watch: {
    projectId (val) {
      this.paramData.projectId = val
      this.querylist()
    },
    docId () {
      this.content = getTemplateContent('TrustOrgForm')
      this.BPContent = getTemplateContent('TrustOrgForm')
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  data () {
    return {
      content: {
        list: [],
        memberData: []
      },
      BPContent: {},
      project: {},
      fileInfo: {},
      stageMap: {},
      paramData: {
        projectId: undefined
      },
      BkList: []
    }
  },
  methods: {
    // 添加成员
    handleAdd () {
      const newData = {
        index: this.content.memberData ? (this.content.memberData.length) + 1 : 1,
        ename: '',
        deptName: '',
        role: ''
      }
      if (!this.content.memberData) {
        this.content.memberData = [newData]
      } else {
        this.content.memberData.push(newData)
      }
    },
    onCellChange (val, record, fld) {
      if (!val) {
        val = undefined
      }
      this.$set(record, fld, val)
    },
    handleDelMember (rowIndex) {
      const tempRows = []
      var num = 0
      for (var i = 0; i < this.content.memberData.length; i++) {
        const row = this.content.memberData[i]
        if (row.index !== rowIndex) {
          row.index = ++num
          tempRows.push(row)
        }
      }
      this.content.memberData = tempRows
    },
    handleDel (record) {
      const _this = this
      this.$confirm({
        title: '您确定删除吗?',
        onOk () {
          if (_this.content.list <= 0) return
          const idx = _this.content.list.findIndex(value => {
            return value.id === record.id
          })
          _this.content.list.splice(idx, 1)
        }
      })
    },
    downloadFile (row) {
      this.$exportData('/sysDocument/downloadFile', { id: row.id }, row.fileName, this.$message)
    },
    onUpload () {
      this.$refs.upload.show(this.stage)
    },
    success (data) {
      this.content.list = [...data]
    },
    getExtension (path) {
      if (!path) return
      return path.substring(path.lastIndexOf('.'))
    },
    // 从剪切板粘贴
    handlePaste (event, record) {
      event.preventDefault()
      const addArray = event.clipboardData.getData('text').trim().split('\r\n').map(value => {
        const val = value.split('\t')
        return {
          ename: val[0],
          deptName: val[1],
          role: val[2]
        }
      })
      if (this.content.memberData == null) {
        this.content.memberData = []
      }
      this.content.memberData.splice(record, 1, ...addArray)
      this.content.memberData.map((value, index) => {
        return Object.assign(value, { index: index + 1 })
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .title{
    font-size: 18px;
    font-weight: 600;
  }
</style>
