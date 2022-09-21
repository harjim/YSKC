<!--
 * @Author: ldx
 * @Date: 2020-10-13 09:14:38
 * @LastEditTime: 2020-11-09 10:14:15
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\DeclareProject.vue
-->
<template>
  <a-card :bordered="false">
    <a-spin
      tip="请稍后..."
      :spinning="spin"
    >
      <a-form layout="inline">
        <a-form-item label="项目名称">
          <a-input
            style="width:160px;"
            v-model="queryParams.productName"
            placeholder="项目名称"
          />
        </a-form-item>
        <a-form-item label="地区">
          <a-cascader
            :changeOnSelect="true"
            @change="(v)=>queryParams.addressCode = v.join('/')"
            placeholder="请输入地区"
            style="width:160px;"
            :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
            :options="addressCode"
          />
        </a-form-item>
        <a-form-item label="年份">
          <!-- <year-select
            style="width:160px;"
            v-model="queryParams.year"
            placeholder="项目年份"
          /> -->
          <a-select
            style="width:160px;"
            v-model="queryParams.year"
            placeholder="项目年份"
            :allowClear="true"
          >
            <a-select-option v-for="y in years" :key="y">{{ y }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="项目类型">
          <a-select
            style="width:160px;"
            v-model="queryParams.pType"
            :options="typeLists"
            placeholder="请选择项目类型"
            allowClear
          ></a-select>
        </a-form-item>
        <a-form-item label="项目级别">
          <a-select
            style="width:160px;"
            v-model="queryParams.pLevel"
            :options="leveLists"
            placeholder="请选择项目级别"
            allowClear
          ></a-select>
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="refresh(false)"
            v-if="$auth('project:declareProject:search')"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <ys-table
        ref="yst"
        :resizable="true"
        auto-resize
        highlight-hover-row
        show-overflow
        show-header-overflow
        :loading="loading"
        queryUrl="/rsProduct/getList"
        :params="queryParams"
        export-config
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        @completed="completed"
        :expand-config="{accordion: true}"
        :toolbar="{
          custom: true,
          zoom:true,
          refresh:true
        }"
      >
        <template v-slot:buttons>
          <a-button
            type="primary"
            @click="openDeclareItemModal(false)"
            style="margin-right: 10px;"
            v-if="$auth('project:declareProject:add')"
          >添加</a-button>
          <a-button
            type="primary"
            @click="hanldeAllotterPerson (selectedRows)"
            style="margin-right: 10px;"
            :disabled="selectedRows.length <= 0"
            v-if="$auth('project:declareProject:setManager')"
          >分配负责人</a-button>
          <a-button
            type="primary"
            @click="showConfirm(selectRowIds)"
            :disabled="selectRowIds.length <= 0"
            style="margin-right: 10px;"
            v-if="$auth('project:declareProject:del')"
          >删除</a-button>
        </template>
        <vxe-table-column
          type="checkbox"
          width="40"
        />
        <vxe-table-column
          type="seq"
          title="序号"
          :width="70"
          align="center"
        ></vxe-table-column>
        <vxe-table-column
          title="项目名称"
          field="productName"
          remoteSort
          :min-width="120"
        ></vxe-table-column>
        <vxe-table-column
          title="地区"
          field="addressCode"
          :width="120"
          remoteSort
        >
          <template v-slot="{ row }">{{ getAddres(row.addressCode) }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="年份"
          field="year"
          :width="100"
          remoteSort
          align="center"
        />
        <vxe-table-column
          type="expand"
          title="方向"
          :width="70"
        >
          <template v-slot:content="{ row }">
            <vxe-grid
              ref="subyst"
              auto-resize
              :resizable="true"
              highlight-hover-row
              show-overflow
              show-header-overflow
              border
              size="small"
              :data="row.directionModels"
            >
              <vxe-table-column
                title="方向"
                field="direction"
                :width="120"
              ></vxe-table-column>
              <vxe-table-column
                title="截止时间"
                field="expiryDate"
                :width="120"
              ></vxe-table-column>
              <vxe-table-column
                title="申请指南地址"
                field="appGuideUrl"
                :width="120"
              ></vxe-table-column>
              <vxe-table-column
                title="是否配置阶段"
                field="hasStage"
                :width="120"
              >
                <template v-slot="{row}">
                  <span v-if="$auth('project:declareProject:setDirection')">
                    <a
                      v-if="row.hasStage"
                      @click="stageModal(row.id,row)"
                    >已配置</a>
                    <a
                      v-else
                      @click="stageModal(row.id,row)"
                    >未配置</a>
                  </span>
                  <span v-else>
                    <span
                      v-if="row.hasStage"
                    >已配置</span>
                    <span
                      v-else
                    >未配置</span>
                  </span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="联系人"
                field="linkName"
                :width="120"
              ></vxe-table-column>
              <vxe-table-column
                title="联系电话"
                field="linkTel"
                :width="120"
              ></vxe-table-column>
              <vxe-table-column
                title="主方向"
                field="mainDirection"
                :width="120"
              ></vxe-table-column>
              <vxe-table-column
                title="通知文号"
                field="noticeNo"
                :width="120"
              ></vxe-table-column>
              <vxe-table-column
                title="通知地址"
                field="noticeUrl"
                :width="120"
              ></vxe-table-column>
              <vxe-table-column
                title="政策文件"
                field="policyPath"
                :min-width="120"
              >
                <template v-slot="{row}">
                  <span
                    v-for="(file,index) in getFilesAry(row.policyPath)"
                    :key="index"
                    style="margin-right:10px;"
                  >
                    <a
                      title="点击下载"
                      @click="download(row,file)"
                      v-if="$auth('project:declareProject:download')"
                    >
                      {{ file.fileName }}
                    </a>
                    <span v-else>
                      {{ file.fileName }}
                    </span>
                  </span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="公示名单地址"
                field="publicItemUrl"
                :min-width="120"
              >
                <template v-slot="{row}">
                  <a
                    href="row.publicItemUrl"
                    target="_blank"
                  >{{ row.publicItemUrl }}</a>
                </template>
              </vxe-table-column>
            </vxe-grid>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="项目类型"
          field="pType"
          :width="120"
          align="center"
          remoteSort
        >
          <template v-slot="{row}">
            {{ typeAry[row.pType] }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="项目级别"
          field="pLevel"
          :width="120"
          align="center"
          remoteSort
        >
          <template v-slot="{row}">
            {{ levelAry[row.pLevel] }}
          </template>
        </vxe-table-column>

        <vxe-table-column
          title="负责人"
          field="realName"
          :width="150"
          align="center"
        />
        <vxe-table-column
          title="所属政府机构"
          field="govName"
          :width="150"
          align="left"
          remoteSort
        />
        <vxe-table-column
          title="办公地址"
          field="address"
          :width="200"
          align="left"
          remoteSort
        />
        <vxe-table-column
          title="操作"
          :width="150"
          align="center"
        >
          <template v-slot="{row}">
            <a @click="openDeclareItemModal(true,row)" v-if="$auth('project:declareProject:edit')">编辑</a>
            <a-divider type="vertical" v-if="$auth('project:declareProject:edit')" />
            <a @click="openCopyDeclareItemModal(false,row)" v-if="$auth('project:declareProject:edit')">复制</a>
            <span v-if="$auth('project:declareProject:del')">
              <a-divider type="vertical" v-if="$auth('project:declareProject:edit')" />
              <a-popconfirm title="是否确定删除?" @confirm="hanldeRow([row.id])" >
                <a>删除</a>
              </a-popconfirm>
            </span>
          </template>
        </vxe-table-column>
      </ys-table>
    </a-spin>
    <handle-declareItem-modal ref="declareItemModal" @ok="refresh"></handle-declareItem-modal>
    <stage-modal ref="stageModal" @ok="refresh" ></stage-modal>
    <copy-declare-item-modal ref="copyModal" @ok="refresh"></copy-declare-item-modal>
    <set-allotter-person ref="allotterPerson" @ok="refresh"></set-allotter-person>
  </a-card>
</template>

<script>
import ysTable from '@/components/Table/ystable'
import { YearSelect } from '@/components/Selects'
import { DeptSelectOnly } from '@/components'
import HandleDeclareItemModal from './modules/HandleDeclareItemModal'
import CopyDeclareItemModal from './modules/CopyDeclareItemModal'
import StageModal from './modules/StageModal'
import setAllotterPerson from './modules/setAllotterPerson'
const startYear = (new Date()).getFullYear() - 4
const endYear = (new Date()).getFullYear() + 6
const years = []
for (let y = startYear; y < endYear; y++) {
  years.unshift(y)
}
export default {
  name: 'DeclareProject',
  components: {
    ysTable,
    YearSelect,
    DeptSelectOnly,
    HandleDeclareItemModal,
    StageModal,
    CopyDeclareItemModal,
    setAllotterPerson
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
    this.$getDictionary(10).then(res => {
      for (const type of res) {
        this.typeLists.push({ value: type.key, label: type.value })
        this.typeAry[type.key] = type.value
      }
    })
    this.$getDictionary(11).then(res => {
      for (const type of res) {
        this.leveLists.push({ value: type.key, label: type.value })
        this.levelAry[type.key] = type.value
      }
    })
  },
  data () {
    return {
      spin: false,
      loading: false,
      data: [],
      queryParams: {},
      selectRowIds: [],
      selectedRows: [],
      form: this.$form.createForm(this),
      addressCode: [],
      typeLists: [],
      typeAry: {},
      leveLists: [],
      levelAry: [],
      years
    }
  },
  methods: {
    // 表格复选框事件
    selectChange ({ records }) {
      this.selectedRows = records
      this.selectRowIds = records.map(item => {
        return item.id
      })
    },
    openDeclareItemModal (isEdit, row) {
      this.$refs.declareItemModal.open(isEdit, row)
    },
    refresh (isEdit, postData) {
      if (isEdit) {
        for (const row of this.data) {
          if (row.id === postData.id) {
            Object.assign(row, postData)
          }
        }
      } else {
        this.$refs.yst.refresh(true)
      }
      // this.$refs.yst.refresh(refresh)
    },
    getAddres (code) {
      if (!code) {
        return ''
      }
      const arr = code.split('/')
      var currentIndex = 0
      var result = ''
      var tempChildren = this.addressCode
      while (currentIndex < arr.length) {
        for (let i = 0; i < tempChildren.length; i++) {
          if (arr[currentIndex] === tempChildren[i].key) {
            result += tempChildren[i].value + '/'
            if (tempChildren[i].children && tempChildren[i].children.length > 0) {
              tempChildren = tempChildren[i].children
            } else {
              tempChildren = []
            }
            break
          }
        }
        currentIndex++
      }
      return result ? result.substr(0, result.length - 1) : result
    },
    stageModal (id, row) {
      this.$refs.stageModal.show(id, row)
    },
    hanldeRow (ids) {
      const models = []
      for (const id of ids) {
        models.push({ id })
      }
      this.$http.post('rsProduct/del', models).then((res) => {
        if (res.data && res.success) {
          this.$message.success('删除成功')
          this.refresh()
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        if (error.message.includes('403')) {
          this.$message.error('无此权限，请跟管理员联系！')
        } else {
          this.$message.error('操作失败：' + error.message)
        }
      })
      this.selectRowIds = []
      this.selectedRows = []
    },
    // 显示确认框
    showConfirm (ids) {
      const self = this
      this.$confirm({
        title: '是否确定删除?',
        onOk () {
          self.hanldeRow(ids)
        }
      })
      this.$refs.yst.clearCheckboxRow()
      this.selectRowIds = []
      this.selectedRows = []
    },
    // 下载文件
    download (record, file) {
      this.$exportData('/rsProduct/download', { filePath: file.filePath, fileName: file.fileName }, file.fileName, this.$message)
    },
    // 通过路径名获取名称
    getFilesAry (path) {
      const ary = path.split(',')
      const files = []
      // /HandleDeclareItemModal/1603071219475test2.png,/HandleDeclareItemModal/1603071219487test1.png
      for (const filePath of ary) {
        const index = filePath.lastIndexOf('/')
        const fileName = filePath.substring(index + 14)
        const file = { fileName: fileName, filePath: filePath }
        files.push(file)
      }
      return files
    },
    completed (res) {
      this.data = res.data.data
      this.selectRowIds = []
      this.selectedRows = []
    },
    // 打开复制申报项目modal
    openCopyDeclareItemModal (isEdit, row) {
      this.$refs.copyModal.show(isEdit, row)
    },
    // 分配负责人按钮
    hanldeAllotterPerson  (selectedRows) {
      this.$refs.allotterPerson.showModal(selectedRows)
      this.$refs.yst.clearCheckboxRow()
      this.selectRowIds = []
      this.selectedRows = []
    }
  }
}
</script>

<style lang="less" scpoed>
</style>
