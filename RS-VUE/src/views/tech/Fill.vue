<template>
  <a-card>
    <a-form layout="inline">
      <a-form-item label="年份">
        <a-select
          placeholder="请选择年份"
          v-model="ryear"
          @change="onYearChange"
          style="width:150px;float:left"
        >
          <a-select-option
            v-for="item in yearSelectOption"
            :key="item"
          >{{ item }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="项目">
        <a-select
          placeholder="请选择项目"
          style="width:500px;float:left"
          :value="projectId"
          @change="OnProjectChange"
        >
          <a-select-option
            v-for="item in projectList"
            :key="item.id"
          >
            <span v-if="item!=null">{{ item.pname }}</span>
          </a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
    <a-layout
      style="background: #fff; padding: 0"
      v-if="selectProject!=null"
    >
      <a-layout-sider
        :trigger="null"
        style="max-width: 175px; min-width: 10px; width: 175px;background: rgb(255, 255, 255);"
        collapsible
        v-model="collapsed"
      >
        <a-menu
          mode="inline"
          class="menu-style"
          :defaultSelectedKeys="['10']"
          @click="menuClick"
        >
          <a-menu-item
            v-for="(v,k) in keyTypeMap"
            :key="k"
          >
            <span>{{ v.name }}</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-content :style="{ margin: '0px 10px', padding: '24px', background: '#fff', minHeight: '280px' }">
          <FillCard
            :ref="`fillCard${selectKey}`"
            :year="ryear"
            :projectId="projectId"
            :key="`${selectKey}`"
          ></FillCard>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-card>
</template>
<script>
import { PageView } from '@/layouts'
import moment from 'moment'
import BaseInfo from './fill/BaseInfo'
import Implement from './fill/Implement'
import Investment from './fill/Investment'
import OtherInfo from './fill/OtherInfo'
import Quota from './fill/Quota'
import Appendix from './fill/Appendix'
import Declaration from './fill/Declaration'
import Cost from './fill/Cost'
import AuditReport from './fill/AuditReport'
const keyTypeMap = {
  '10': {
    'name': '项目基本情况',
    'component': BaseInfo
  },
  '20': {
    'name': '项目投资情况',
    'component': Investment
  },
  '30': {
    'name': '项目实施效果',
    'component': Implement
  },

  '40': {
    'name': '改造类指标数据',
    'component': Quota
  },
  '50': {
    'name': '审计报告',
    'component': Appendix
  },
  '60': {
    'name': '现场考察信息',
    'component': OtherInfo
  },
  '70': {
    'name': '项目申请报告',
    'component': Declaration
  },
  '80': {
    'name': '项目支出清单',
    'component': Cost
  },
  '90': {
    'name': '附件列表',
    'component': AuditReport
  }
}
export default {
  name: 'Fill',
  components: {
    PageView,
    'FillCard': {
      functional: true,
      render: function (createElement, context) {
        const d = context.data
        return createElement(
          keyTypeMap[d.key].component,
          context.data,
          context.children
        )
      }
    }
  },
  data () {
    return {
      keyTypeMap: keyTypeMap,
      rdSwitch: false,
      ryear: undefined,
      collapsed: false,
      selectKey: '10',
      month: '',
      projectList: [],
      projectId: undefined,
      selectProjectId: '0',
      selectProject: null,
      project: {},
      allRdDataMap: {},
      allRdColumns: [],
      yearSelectOption: [],
      projectMap: {}
    }
  },
  created () {
    this.$http.get('/techProject/getYears')
      .then(res => {
        this.yearSelectOption = res.data
      })
  },
  computed: {
    rdTotalProjectList: function () {
      return [...this.projectList, { 'rdIndex': -1 }]
    },
    rdColumns: function () {
      if (this.keyTypeMap[this.selectKey].columns) {
        return this.keyTypeMap[this.selectKey].columns
      } else {
        return [this.keyTypeMap[this.selectKey]]
      }
    }
  },
  methods: {
    menuClick (e) {
      if (this.selectKey !== e.key) {
        this.selectKey = e.key
      }
    },
    moment,
    OnProjectChange (value) {
      this.projectId = value
      this.selectProject = this.project[value]
    },
    onYearChange (value, option) {
      this.rdYear = value
      this.selectKey = '10'
      this.$http.get('/techProject/getTechProjectsByYear', { params: { year: this.rdYear } })
        .then(res => {
          if (res.success && res.data !== null && res.data.length > 0) {
            this.selectProject = null
            this.projectList = res.data
            this.project = {}
            if (this.projectList.length > 0) {
              this.projectList.map(item => {
                this.project[item.id] = item
              })
              this.projectId = undefined
            } else {
              this.projectId = undefined
              this.project = {}
              this.projectList = []
            }
          } else {
            this.projectList = []
            this.projectId = undefined
            this.project = {}
            this.$message.warning('当前年份：' + value + '没有项目，请重新选择年份')
          }
          return res.data
        })
    }
  }
}
</script>
<style lang="less" scoped>
.menu-style {
  border: '0';
  width: 'auto';
  padding-top: 12px;
}
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>
