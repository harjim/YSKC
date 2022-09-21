<!--
 * @Author: ldx
 * @Date: 2021-06-23 08:17:58
 * @LastEditTime: 2021-06-23 09:58:01
 * @LastEditors: ldx
 * @Description: 项目详情
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\ProjectDetail.vue
-->
<template>
  <tab-layout>
    <template #up>
      <project-info :record="projectInfo"></project-info>
    </template>
    <template #down>
      <audit-log ref="auditLog" @updataStatus="updataStatus" :params="logGetParams" :title="projectInfo.pname"></audit-log>
    </template>
  </tab-layout>
</template>
<script>
import moment from 'moment'
import AuditLog from './AuditLog'
import TabLayout from './TabLayout.vue'
import { statusMap } from '@/utils/processDoc/auditStatus'
import { getProjectInfo } from '@/api/project'
import ProjectInfo from './ProjectInfo.vue'
export default {
  name: 'ProjectDetail',
  components: {
    AuditLog,
    TabLayout,
    ProjectInfo
  },
  props: {
    record: {
      type: Object,
      default: () => { return {} }
    },
    mode: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    this.logGetParams.rsProjectId = this.record.rsProjectId
    this.handlerGetProjectInfo(this.record.rsProjectId)
  },
  data () {
    return {
      statusMap,
      form: this.$form.createForm(this),
      projectInfo: {},
      logGetParams: {
        rsProjectId: undefined
      }
    }
  },
  methods: {
    moment,
    updataStatus () {},
    handlerGetProjectInfo (projectId) {
      getProjectInfo({ projectId }).then(data => {
        this.projectInfo = data
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
    }
  }
}
</script>

<style lang="less" scoped>
@import '../css/AuditDrawerTab.less';
* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis>.vxe-body--expanded-cell {
  overflow: auto;
}
.form-item {
  margin-bottom: 8px;
}
</style>
