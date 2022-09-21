<!--
    * @Author:
    * @Date: 2022-08-26 13:50:07
    * @Description:
-->
<template>
  <div style="height: calc(100vh - 333px);">
    <vxe-grid
      ref="xTable"
      showOverflow="tooltip"
      max-height="90%"
      auto-resize
      header-align="center"
      :loading="tableLoading"
      :data="tableData"
    >
      <vxe-table-column
        title="序号"
        type="seq"
        width="50"
        align="center"
        showOverflow="tooltip"
      ></vxe-table-column>
      <vxe-table-column
        title="变更类型"
        field="changeType"
        resizable
        align="center"
        showOverflow
        showHeaderOverflow
        :header-class-name="requireShow"
      >
        <template #default="{ row }">
          <a-select v-if="row.editStatus && !row.id" v-model="row.changeType">
            <a-select-option v-for="(value, key) in changeType" :key="key" :value="+key">{{ value }}</a-select-option>
          </a-select>
          <span v-else>{{ changeType[row.changeType] }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="变更内容"
        field="content"
        resizable
        align="center"
        showOverflow
        showHeaderOverflow
        :header-class-name="requireShow"
      >
        <template #default="{ row }">
          <template v-if="row.editStatus">
            <a-select
              v-if="row.changeType === 1"
              show-search
              placeholder="请输入名字搜索"
              style="width: 100%"
              :filter-option="false"
              :defaultActiveFirstOption="false"
              :showArrow="false"
              @search="handleSearch"
              @change="(v, event) => onHandleChange(v, event, row)"
              v-model="row.enumber"
            >
              <a-select-option
                v-for="(item, index) in employeeList"
                :row="row"
                :key="index"
                :ename="item.ename"
                :enumber="item.enumber"
                :value="item.enumber">
                {{ `${item.ename}(${item.enumber})` }}
              </a-select-option>
            </a-select>
            <a-input
              v-else
              type="text"
              placeholder="请输入项目名称"
              :maxLength="200"
              v-model="row.pname" />
          </template>
          <template v-else>
            <template v-if="row.changeType === 0">
              <!-- // 项目名称 -->
              {{ row.pname }}
            </template>
            <template v-else>
              <!-- 负责人选择 -->
              {{ `${row.ename}(${row.enumber})` }}
            </template>
          </template>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="变更时间"
        field="changeTime"
        resizable
        align="center"
        showOverflow
        showHeaderOverflow
        :header-class-name="requireShow"
      >
        <template #default="{ row }">
          <a-date-picker
            v-if="row.editStatus"
            :allowClear="false"
            placeholder="请选择变更时间"
            v-model="row.changeTime"
            :disabledDate="disabledDate"
          />
          <span v-else>{{ row.changeTime.format('YYYY/MM/DD') }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="备注"
        field="remark"
        align="left"
        width="160px"
        :showOverflow="false"
      >
        <template #default="{ row }">
          <a-textarea
            v-if="row.editStatus"
            placeholder="请输入备注"
            allow-clear
            :max-length="200"
            :rows="1"
            v-model="row.remark" />
          <span v-else>{{ row.remark }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="操作"
        width="150"
        align="left"
      >
        <template #default="{ row, rowIndex }">
          <template v-if="row.id && !row.editStatus">
            <a v-if="$auth('project:report:changeLog:add')" @click="editChange(row)">编辑</a>
            <a-divider v-if="$auth('project:report:changeLog:del') && $auth('project:report:changeLog:add')" type="vertical" />
            <a v-if="$auth('project:report:changeLog:del')" @click="delChange(row, rowIndex)">删除</a>
          </template>
          <template v-else>
            <a v-if="$auth('project:report:changeLog:add')" @click="saveChange(row)">保存</a>
            <a-divider v-if="$auth('project:report:changeLog:add')" type="vertical" />
            <a v-if="$auth('project:report:changeLog:add')" @click="cancel(row, rowIndex)">取消</a>
          </template>
        </template>
      </vxe-table-column>
    </vxe-grid>
    <a-button style="width: 100%;" type="dashed" @click="addName">新  增</a-button>
  </div>
</template>

<script>
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'

const changeType = { 0: '项目名称', 1: '负责人' }
export default {
  mixins: [yearMiXin],
  data () {
    return {
      tableLoading: false,
      tableData: [],
      employeeList: [],
      projectId: this.projectData.id,
      changeType,
      controller: {
        add: this.$auth('project:report:changeLog:add'),
        del: this.$auth('project:report:changeLog:del')
      },
      isEdit: false
    }
  },
  props: {
    projectData: {
      type: Object,
      default: () => {}
    }
  },
  watch: {
    projectData: {
      immediate: true,
      deep: true,
      handler (newValue, oldValue) {
        if (newValue.id) {
          this.$http.get('/project/getProjectChangeRecord', { params: { projectId: newValue.id } }).then(res => {
            if (res.success) {
              res.data.forEach(item => {
                item.changeTime = moment(item.changeTime)
                item.editStatus = false
                if (item.changeType) {
                  const people = JSON.parse(item.content)
                  item.ename = people.ename
                  item.enumber = people.enumber
                } else {
                  item.pname = item.content
                }
              })
              this.tableData = res.data
              this.isEdit = false
            } else {
              this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
            }
          }).catch(res => {
            this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
          })
        }
      } }
  },

  methods: {
    moment,
    addName () {
      if (this.isEdit) {
        this.$message.warning('请完成当前行操作后重试!')
        return
      }
      const newRow = {
        id: null,
        changeTime: moment(),
        // 0:项目名称，1:负责人
        changeType: 0,
        content: null,
        remark: null,
        projectId: this.projectId,
        editStatus: true
      }
      this.tableData.push(newRow)
      this.isEdit = true
    },
    disabledDate (current) {
      return current > moment()
    },
    resetDate (date) {
      return date.millisecond(0).second(0).minute(0).hour(0)
    },
    isNullObj (obj) {
      return obj === null || obj === undefined || obj === '' || JSON.stringify(obj) === '{}' || JSON.stringify(obj) === '[]'
    },
    saveChange (row) {
      row.changeTime = this.resetDate(row.changeTime)

      if (row.changeType) {
        if (this.isNullObj(row.ename) || this.isNullObj(row.enumber)) {
          this.$message.error('请输入变更内容!')
          return
        }
        row.content = JSON.stringify({ ename: row.ename, enumber: row.enumber })
      } else {
        if (this.isNullObj(row.pname)) {
          this.$message.error('请输入变更内容!')
          return
        }
        row.content = row.pname
      }

      for (let i = 0; i < this.tableData.length; i++) {
        const item = this.tableData[i]
        if (!item.id || item.id === row.id) {
          continue
        }
        let rowData = item
        if (item.old) {
          rowData = item.old
        }
        if (rowData.changeType === 0 && rowData.pname === row.pname) {
          this.$message.error(`项目名称【${row.pname}】已存在，不能保存!`)
          return
        }
        if (!(rowData.changeTime.diff(row.changeTime, 'days')) && rowData.changeType === row.changeType) {
          this.$message.error(`存在 ${row.changeTime.format('YYYY/MM/DD')} 的 ${row.changeType ? '负责人' : '项目名称'} 变更记录，不能保存!`)
          return
        }
      }
      this.tableLoading = true
      const saveRow = { ...row }
      delete saveRow.old
      this.$http.post('/project/saveChangeRecord', saveRow).then(res => {
        if (res.success && res.data) {
          row.id = res.data
          row.editStatus = false
          this.$message.success('保存成功!')
          delete row.old
          this.isEdit = false
          this.$emit('update:isEditChange', true)
        } else {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
        }
      }).catch(res => {
        this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
      }).finally(() => {
        this.tableLoading = false
      })
    },
    delChange (row, rowIndex) {
      if (row.id) {
        const self = this
        this.$confirm({
          title: '提示',
          content: '真的要删除当前记录吗 ?',
          onOk () {
            self.tableLoading = true
            return self.$http.post('/project/delChangeRecord', { id: row.id, projectId: self.projectId }).then(res => {
              if (res.success) {
                if (res.data) {
                  self.$message.error(`当前项目删除后项目名称【${res.data}】与其他项目名称重复，不能保存!`)
                } else {
                  self.tableData.splice(rowIndex, 1)
                  self.$message.success('删除成功!')
                }
              } else {
                self.$message.error(res.errorMessage || '删除失败!')
              }
            }).catch(res => {
              self.$message.error(res.errorMessage || '系统异常，请联系管理员!')
            }).finally(() => {
              self.tableLoading = false
            })
          },
          onCancel () {
          }
        })
      } else {
        this.tableData.splice(rowIndex, 1)
      }
    },
    handleSearch (value) {
      this.$http.get('/project/getEmployeeList', { params: { ename: value, year: this.currentYear } }).then(res => {
        this.employeeList = res.data
      })
    },
    onHandleChange (value, event) {
      const row = event.data.attrs.row
      row.enumber = value
      row.ename = event.data.attrs.ename
    },
    editChange (row) {
      if (!this.isEdit) {
        row.old = { ...row, changeTime: row.changeTime.clone() }
        row.editStatus = true
        this.isEdit = true
      } else {
        this.$message.warning('请完成当前行操作后重试')
      }
    },
    cancel (row, rowIndex) {
      if (row.id) {
        row.editStatus = false
        Object.keys(row.old).forEach(key => {
          row[key] = row.old[key]
        })
        delete row.old
      } else {
        this.tableData.splice(rowIndex, 1)
      }
      this.isEdit = false
    },
    requireShow () {
      return 'isRequired'
    }
  }
}
</script>

<style lang='less' scoped>
/deep/ .ant-select-enabled {
  width: 80%;
  margin: auto;
}
/deep/ .isRequired .vxe-cell--title::before {
  content: '*';
  color: red;
}
</style>
