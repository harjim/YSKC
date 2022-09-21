<template>
  <a-card :bordered="false">
    <a-spin
      :spinning="spinning"
      tip="请稍后..."
    >
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input
            v-model="queryParams.ename"
            placeholder="请输入姓名"
          />
        </a-form-item>
        <a-form-item label="工号">
          <a-input
            v-model="queryParams.enumber"
            placeholder="请输入工号"
          />
        </a-form-item>
        <a-form-item label="研发部门">
          <select-down
            ref="rdDeptSelet"
            placeholder="请选择研发部门"
            style="width:174px;"
            treeType="rdDept"
            @fullPath="rdDeptSelected"
          />
        </a-form-item>
        <a-form-item label="职位">
          <a-input
            v-model="queryParams.position"
            placeholder="请输入职位"
          />
        </a-form-item>
        <a-form-item label="人员类型">
          <a-select
            v-model="queryParams.etypes"
            :maxTagCount="3"
            mode="multiple"
            placeholder="请选择人员类型"
            style="width: 174px;"
          >
            <a-select-option v-for="item in $getEnums('rdEmployeeEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="学历">
          <a-select
            v-model="queryParams.eduLevel"
            :allowClear="true"
            placeholder="请选择学历"
            style="width: 174px;"
          >
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
          <a-button
            v-if="$auth('rdorg:rdEmployeeHour:search')"
            type="primary"
            @click="search()"
          >查询</a-button>
        </span>
      </a-form>
      <div class="table">
        <ystable
          ref="table"
          :params="queryParams"
          :toolbar="tableToolbar"
          auto-resize
          highlight-hover-row
          queryUrl="/rdEmployeeHour/getList"
          resizable
          show-overflow
          max-height="100%"
        >
          <vxe-table-column
            fixed="left"
            title="序号"
            type="seq"
            width="50"
          />
          <vxe-table-column
            align="left"
            field="enumber"
            fixed="left"
            remoteSort
            title="工号"
            width="120"
          ></vxe-table-column>
          <vxe-table-column
            align="left"
            field="ename"
            fixed="left"
            remoteSort
            title="姓名"
            width="80"
          ></vxe-table-column>
          <vxe-table-column
            align="center"
            field="etype"
            title="人员类型"
            width="160"
          >
            <template v-slot="{ row }">
              {{
                row.etype && row.etype !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label : ''
              }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="left"
            field="rdDeptName"
            title="研发部门"
            width="160"
          >
            <template v-slot="{ row }">
              <template>
                {{ row.fullName }}
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="left"
            field="position"
            remoteSort
            title="职位"
            width="130"
          ></vxe-table-column>
          <vxe-table-column
            align="left"
            field="rds"
            title="项目编号"
            width="140"
          />
          <vxe-table-column
            align="left"
            field="eduLevel"
            remoteSort
            title="学历"
            width="80"
          >
            <template v-slot="{ row }">
              <span>{{ getEduLevel(row.eduLevel) }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="center"
            field="entryDate"
            title="加入日期"
            width="110"
          >
          </vxe-table-column>
          <vxe-table-column
            align="center"
            field="rdHour"
            title="科技人员"
            width="100"
          >
            <template v-slot="{ row }">
              <template>{{ Math.floor(row.rdHour / 8) >= 183 ? '是' : '' }}
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            align="right"
            field="rdHour"
            title="研发投入工时"
            width="110"
          >
          </vxe-table-column>
          <vxe-table-column
            align="left"
            field="remark"
            min-width="140"
            remoteSort
            title="备注"
          ></vxe-table-column>
        </ystable>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import { SelectDown } from '@/components'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'

/// 学历， 0:无，1：高中，2：中专，3：大专，4：本科 5：硕士，6：博士, 7 : 初中
const eduLevelArr = ['无', '高中', '中专', '大专', '本科', '硕士', '博士', '初中']
/// 员工类型，0：普通员工， 1： 研发人员， 2：专员
/// 普通员工，研究人员，技术人员，辅助人员

export default {
  mixins: [yearMiXin],
  name: 'RdEmployeeHour',
  components: {
    SelectDown,
    ystable
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
      queryParams: {},
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true
      }
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1890) {
          this.scroll = { x: 1600 }
        }
      })()
    }
  },
  created () {
    if (document.body.clientWidth < 1890) {
      this.scroll = { x: 1600 }
    }
    this.queryParams.year = this.currentYear
  },
  methods: {
    search () {
      this.queryParams.year = this.currentYear
      this.$nextTick(() => {
        if (this.$refs.table && this.$refs.table.refresh) {
          this.$refs.table.refresh(true)
        }
      })
    },
    getEduLevel (index) {
      return eduLevelArr[index]
    },
    moment,
    rdDeptSelected (rdDeptPath) {
      this.queryParams.rdDeptPath = rdDeptPath
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
